package com.tlv8.flw.base;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.apache.ibatis.session.SqlSession;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.tlv8.base.Sys;
import com.tlv8.base.db.DBUtils;
import com.tlv8.base.utils.IDUtils;
import com.tlv8.flw.expression.BooleanExpression;
import com.tlv8.flw.helper.FlowStringtoJSON;
import com.tlv8.system.BaseController;
import com.tlv8.system.bean.ContextBean;
import com.tlv8.system.utils.OrgUtils;

@SuppressWarnings({ "unchecked", "rawtypes" })
public class TaskData {
	public static final String task_table = "SA_TASK";

	/*
	 * 启动流程
	 */
	public static String startFlow(String processID, String processName, String sData1) throws SQLException {
		String flowID = IDUtils.getGUID();
		String taskID = flowID;
		ContextBean context = new BaseController().getContext();// 获取当前登录人员信息
		OrgUtils euser = new OrgUtils(processID);// 构建执行人信息
		StringBuilder sqlStr = new StringBuilder();
		sqlStr.append("insert into ");
		sqlStr.append(task_table);
		sqlStr.append("(SID,SFLOWID,SNAME,SPROCESS");
		sqlStr.append(",SCPERSONID,SCPERSONCODE,SCPERSONNAME");
		sqlStr.append(",SCPOSID,SCPOSCODE,SCPOSNAME");
		sqlStr.append(",SCDEPTID,SCDEPTCODE,SCDEPTNAME");
		sqlStr.append(",SCOGNID,SCOGNCODE,SCOGNNAME");
		sqlStr.append(",SEPERSONID,SEPERSONCODE,SEPERSONNAME");
		sqlStr.append(",SEPOSID,SEPOSCODE,SEPOSNAME");
		sqlStr.append(",SEDEPTID,SEDEPTCODE,SEDEPTNAME");
		sqlStr.append(",SEOGNID,SEOGNCODE,SEOGNNAME");
		sqlStr.append(",SCFID,SCFNAME,SEFID,SEFNAME");
		sqlStr.append(",SDATA1,SSTATUSID,SSTATUSNAME,sAStartTime");
		sqlStr.append(",VERSION)values(");
		sqlStr.append("'" + taskID + "','" + flowID + "'");
		sqlStr.append(",'" + processName + "','" + processID + "'");
		sqlStr.append(",'" + context.getCurrentPersonID() + "','" + context.getCurrentPersonCode() + "','"
				+ context.getCurrentPersonName() + "'");
		sqlStr.append(",'" + context.getCurrentPositionID() + "','" + context.getCurrentPositionCode() + "','"
				+ context.getCurrentPositionName() + "'");
		sqlStr.append(",'" + context.getCurrentDeptID() + "','" + context.getCurrentDeptCode() + "','"
				+ context.getCurrentDeptName() + "'");
		sqlStr.append(",'" + context.getCurrentOgnID() + "','" + context.getCurrentOgnCode() + "','"
				+ context.getCurrentOgnName() + "'");
		sqlStr.append(",'" + euser.getPersonID() + "','" + euser.getPersonCode() + "','" + euser.getPersonName() + "'");
		sqlStr.append(
				",'" + euser.getPositionID() + "','" + euser.getPositionCode() + "','" + euser.getPositionName() + "'");
		sqlStr.append(",'" + euser.getDeptID() + "','" + euser.getDeptCode() + "','" + euser.getDeptName() + "'");
		sqlStr.append(",'" + euser.getOgnID() + "','" + euser.getOgnCode() + "','" + euser.getOgnName() + "'");
		sqlStr.append(",'" + context.getCurrentPersonFullID() + "','" + context.getCurrentPersonFullName() + "','"
				+ euser.getPersonFullID() + "','" + euser.getPersonFullName() + "'");
		sqlStr.append(",'" + sData1 + "','tesExecuting','正在处理',CURRENTDATE1,'0')");
		String sql = sqlStr.toString();
		if (DBUtils.IsMSSQLDB("system")) {
			sql = sql.replace("CURRENTDATE1", "getdate()");
		} else if (DBUtils.IsMySQLDB("system")) {
			sql = sql.replace("CURRENTDATE1", "now()");
		} else {
			sql = sql.replace("CURRENTDATE1", "sysdate");
		}
		DBUtils.execInsertQuery("system", sql);
		return flowID;
	}

	/*
	 * 流转
	 */
	public static String FlowOut(String processID, String processName, String Activity, String flowID, String taskID,
			String sData1, List<OrgUtils> ePersonList, HttpServletRequest request) throws Exception {
		Map m = checktesReady(taskID);
		if (!"tesReady".equals(m.get("SSTATUSID")) && !"tesExecuting".equals(m.get("SSTATUSID"))) {
			throw new Exception("当前任务:" + (String) m.get("SSTATUSNAME") + ",不能流转.");
		}
		// String actType = (new FlowActivity(processID, Activity)
		// .getBeforeActivity().get(0).getType());
		FlowActivity act = new FlowActivity(processID, Activity);
		String beforeActivity = getCurrentActivity(taskID); // 流转时参考当前环节
		FlowActivity beforeAct = new FlowActivity(processID, beforeActivity);
		String actType = beforeAct.getType();
		String SSTATUSID = "tesReady";
		String SSTATUSNAME = "尚未处理";
		String newtaskIDs = "";
		if ("end".equals(act.getType())) {
			SSTATUSID = "tesFinished";
			SSTATUSNAME = "已完成";
		}
		// processName = act.getActivityname() + ":" + processName;// 任务标题
		String activitylabel = act.getsActivityLabel();
		if ((activitylabel == null) || ("".equals(activitylabel)))
			processName = act.getActivityname() + ":" + act.getProcessName();
		else {
			activitylabel = activitylabel.replaceAll("getProcessID\\(\\)", flowID);
			activitylabel = activitylabel.replaceAll("getTaskID\\(\\)", taskID);
			activitylabel = activitylabel.replaceAll("getProcesssData1\\(\\)", sData1);
			processName = BooleanExpression
					.getScriptExpressionVal(BooleanExpression.resolutionExpression(activitylabel, request));
		}
		String eURL = act.getUrl();
		String cURL = beforeAct.getUrl();
		ContextBean context = new BaseController().getContext();
		String excutorIDs = "";
		String excutorNames = "";
		SqlSession session = DBUtils.getSession("system");
		for (int i = 0; i < ePersonList.size(); i++) {
			String newtaskID = IDUtils.getGUID();
			newtaskIDs = newtaskID;
			OrgUtils euser = ePersonList.get(i);
			if ("".equals(euser.getPersonID()) || euser.getPersonID() == null) {
				throw new Exception("流转失败,指定的执行人错误!\n 提示:请注意选择执行人.");
			}
			StringBuilder sqlStr = new StringBuilder();
			sqlStr.append("insert into ");
			sqlStr.append(task_table);
			sqlStr.append("(SID,SPARENTID,SFLOWID,SNAME,SCURL,SEURL,SPROCESS");
			sqlStr.append(",SACTIVITY,SCPERSONID,SCPERSONCODE,SCPERSONNAME");
			sqlStr.append(",SCPOSID,SCPOSCODE,SCPOSNAME");
			sqlStr.append(",SCDEPTID,SCDEPTCODE,SCDEPTNAME");
			sqlStr.append(",SCOGNID,SCOGNCODE,SCOGNNAME");
			sqlStr.append(",SEPERSONID,SEPERSONCODE,SEPERSONNAME");
			sqlStr.append(",SEPOSID,SEPOSCODE,SEPOSNAME");
			sqlStr.append(",SEDEPTID,SEDEPTCODE,SEDEPTNAME");
			sqlStr.append(",SEOGNID,SEOGNCODE,SEOGNNAME");
			sqlStr.append(",SCFID,SCFNAME,SEFID,SEFNAME");
			sqlStr.append(",SDATA1,SSTATUSID,SSTATUSNAME");
			sqlStr.append(",SCREATETIME,sAStartTime,VERSION)values(");
			sqlStr.append("'" + newtaskID + "','" + taskID + "','" + flowID + "'");
			sqlStr.append(
					",'" + processName + "','" + cURL + "','" + eURL + "','" + processID + "','" + Activity + "'");
			sqlStr.append(",'" + context.getCurrentPersonID() + "','" + context.getCurrentPersonCode() + "','"
					+ context.getCurrentPersonName() + "'");
			sqlStr.append(",'" + context.getCurrentPositionID() + "','" + context.getCurrentPositionCode() + "','"
					+ context.getCurrentPositionName() + "'");
			sqlStr.append(",'" + context.getCurrentDeptID() + "','" + context.getCurrentDeptCode() + "','"
					+ context.getCurrentDeptName() + "'");
			sqlStr.append(",'" + context.getCurrentOgnID() + "','" + context.getCurrentOgnCode() + "','"
					+ context.getCurrentOgnName() + "'");
			sqlStr.append(
					",'" + euser.getPersonID() + "','" + euser.getPersonCode() + "','" + euser.getPersonName() + "'");
			sqlStr.append(",'" + euser.getPositionID() + "','" + euser.getPositionCode() + "','"
					+ euser.getPositionName() + "'");
			sqlStr.append(",'" + euser.getDeptID() + "','" + euser.getDeptCode() + "','" + euser.getDeptName() + "'");
			sqlStr.append(",'" + euser.getOgnID() + "','" + euser.getOgnCode() + "','" + euser.getOgnName() + "'");
			sqlStr.append(",'" + context.getCurrentPersonFullID() + "','" + context.getCurrentPersonFullName() + "','"
					+ euser.getPersonFullID() + "','" + euser.getPersonFullName() + "'");
			sqlStr.append(",'" + sData1 + "','" + SSTATUSID + "','" + SSTATUSNAME + "',CURRENTDATE1,CURRENTDATE2,'0')");
			String sql = sqlStr.toString();
			if (DBUtils.IsOracleDB("system") || DBUtils.IsDMDB("system")) {
				sql = sql.replace("CURRENTDATE1", "sysdate");
				sql = sql.replace("CURRENTDATE2", "sysdate");
			} else if (DBUtils.IsMySQLDB("system")) {
				sql = sql.replace("CURRENTDATE1", "now()");
				sql = sql.replace("CURRENTDATE2", "now()");
			} else {
				sql = sql.replace("CURRENTDATE1", "getdate()");
				sql = sql.replace("CURRENTDATE2", "getdate()");
			}
			DBUtils.execInsertQuery("system", sql);
			excutorIDs += ("," + euser.getPersonID());
			excutorNames += ("," + euser.getPersonName());
			String qupSql = "select SPROJECTNAME,SCUSTOMERNAME,SPLANNAME,SFMAKERNAME from " + task_table
					+ " where SID ='" + taskID + "'";
			List<Map<String, String>> plist = DBUtils.selectStringList(session, qupSql);
			if (plist.size() > 0) {
				String billInfoSql = "update " + task_table + " set sProjectName = '" + plist.get(0).get("SPROJECTNAME")
						+ "',sCustomerName = '" + plist.get(0).get("SCUSTOMERNAME") + "',sPlanName = '"
						+ plist.get(0).get("SPLANNAME") + "',SFMAKERNAME = '" + plist.get(0).get("SPLANNAME") + "' "
						+ " where SID = '" + newtaskID + "'";
				DBUtils.excuteUpdate(session, billInfoSql);
			}
		}
		if (ePersonList.size() > 0) {
			if (!"".equals(actType) && !"start".equals(actType)) {
				String sql = "update " + task_table
						+ " set SSTATUSID='tesFinished' ,SSTATUSNAME='已完成',SEXECUTETIME=CURRENTDATE1,sAFinishTime=CURRENTDATE2, VERSION=VERSION+1 where SID = '"
						+ taskID + "' and SSTATUSID != 'tesExecuting'";
				String ptaskID = getCurrentTaskParentID(taskID);
				String sql_1 = "update " + task_table
						+ " set SSTATUSID='tesCanceled' ,SSTATUSNAME='已取消',SEXECUTETIME=CURRENTDATE1,sAFinishTime=CURRENTDATE2, VERSION=VERSION+1 where (SID != '"
						+ taskID + "' and SPARENTID = '" + ptaskID + "') and (SACTIVITY = '" + beforeActivity
						+ "' and SFLOWID ='" + flowID + "' and SSTATUSID = 'tesReady')";
				if (DBUtils.IsOracleDB("system") || DBUtils.IsDMDB("system")) {
					sql = sql.replace("CURRENTDATE1", "sysdate");
					sql_1 = sql_1.replace("CURRENTDATE1", "sysdate");
					sql = sql.replace("CURRENTDATE2", "sysdate");
					sql_1 = sql_1.replace("CURRENTDATE2", "sysdate");
				} else if (DBUtils.IsMySQLDB("system")) {
					sql = sql.replace("CURRENTDATE1", "now()");
					sql_1 = sql_1.replace("CURRENTDATE1", "now()");
					sql = sql.replace("CURRENTDATE2", "now()");
					sql_1 = sql_1.replace("CURRENTDATE2", "now()");
				} else {
					sql = sql.replace("CURRENTDATE1", "getdate()");
					sql_1 = sql_1.replace("CURRENTDATE1", "getdate()");
					sql = sql.replace("CURRENTDATE2", "getdate()");
					sql_1 = sql_1.replace("CURRENTDATE2", "getdate()");
				}
				DBUtils.excuteUpdate(session, sql);
				// 非共同模式时取消其他待办[当前环节]
				if (!"together".equals(beforeAct.getGrapModle())) {
					DBUtils.excuteUpdate(session, sql_1);
				}
			}
			// 是否为空或结束环节[下一环节]
			if (act.getAfterActivity(request).size() == 0 || "end".equals(act.getType())) {// 流程结束 完成所有待办
				String sql = "update " + task_table
						+ " set SSTATUSID='tesFinished' ,SSTATUSNAME='已完成',SEXECUTETIME=CURRENTDATE1, VERSION=VERSION+1 where SFLOWID = '"
						+ flowID + "' and (SSTATUSID = 'tesReady' or SSTATUSID = 'tesExecuting')";
				if (DBUtils.IsOracleDB("system") || DBUtils.IsDMDB("system")) {
					sql = sql.replace("CURRENTDATE1", "sysdate");
				} else if (DBUtils.IsMySQLDB("system")) {
					sql = sql.replace("CURRENTDATE1", "now()");
				} else {
					sql = sql.replace("CURRENTDATE1", "getdate()");
				}
				List li = DBUtils.selectStringList(session, "select SID from " + task_table
						+ " where SSTATUSID = 'tesReady' and SFLOWID ='" + flowID + "'");
				if (li.size() == 0) {
					DBUtils.excuteUpdate(session, sql);
				}
				session.commit();
				session.close();
				return "";
			}
			String traID = UUID.randomUUID().toString().replaceAll("-", "").toUpperCase();
			String traSql = "insert into SA_FLOWTRACE"
					+ "(SID,SOPERATORID,SOPERATORCODE,SOPERATORNAME,SCURL,SEURL,SCHECKPSN,version)" + "values('" + traID
					+ "','" + context.getCurrentPersonID() + "','" + context.getCurrentPersonCode() + "','"
					+ context.getCurrentPersonName() + "','" + cURL + "','" + eURL + "','{\"excutorIDs\":\""
					+ excutorIDs + "\",\"excutorNames\":\"" + excutorNames + "\"}',0)";
			JSONObject json = getExcutor(eURL);
			if (json == null) {
				DBUtils.excuteUpdate(session, traSql);
			} else {
				traSql = "update SA_FLOWTRACE set SCHECKPSN = '{\"excutorIDs\":\"" + excutorIDs
						+ "\",\"excutorNames\":\"" + excutorNames + "\"}' where SOPERATORID = '"
						+ context.getCurrentPersonID() + "' and SEURL = '" + eURL + "'";
				DBUtils.excuteUpdate(session, traSql);
			}
		}
		session.commit();
		session.close();
		return newtaskIDs;
	}

	/*
	 * 转发
	 */
	public static String FlowTransmit(String processID, String processName, String Activity, String flowID,
			String taskID, String sData1, List<OrgUtils> ePersonList) throws SQLException {
		FlowActivity act = new FlowActivity(processID, Activity);
		processName = act.getActivityname() + ":" + processName;// 任务标题
		String eURL = act.getUrl();
		String cURL = eURL;
		String newtaskID = UUID.randomUUID().toString().toUpperCase().replaceAll("-", "");
		ContextBean context = new BaseController().getContext();
		for (int i = 0; i < ePersonList.size(); i++) {
			OrgUtils euser = ePersonList.get(i);
			StringBuilder sqlStr = new StringBuilder();
			sqlStr.append("insert into ");
			sqlStr.append(task_table);
			sqlStr.append("(SID,SPARENTID,SFLOWID,SNAME,SCURL,SEURL,SPROCESS");
			sqlStr.append(",SACTIVITY,SCPERSONID,SCPERSONCODE,SCPERSONNAME");
			sqlStr.append(",SCPOSID,SCPOSCODE,SCPOSNAME");
			sqlStr.append(",SCDEPTID,SCDEPTCODE,SCDEPTNAME");
			sqlStr.append(",SCOGNID,SCOGNCODE,SCOGNNAME");
			sqlStr.append(",SEPERSONID,SEPERSONCODE,SEPERSONNAME");
			sqlStr.append(",SEPOSID,SEPOSCODE,SEPOSNAME");
			sqlStr.append(",SEDEPTID,SEDEPTCODE,SEDEPTNAME");
			sqlStr.append(",SEOGNID,SEOGNCODE,SEOGNNAME");
			sqlStr.append(",SCFID,SCFNAME,SEFID,SEFNAME");
			sqlStr.append(",SDATA1,SSTATUSID,SSTATUSNAME,SCREATETIME,sAStartTime,VERSION)values(");
			sqlStr.append("'" + newtaskID + "','" + taskID + "','" + flowID + "'");
			sqlStr.append(
					",'" + processName + "','" + cURL + "','" + eURL + "','" + processID + "','" + Activity + "'");
			sqlStr.append(",'" + context.getCurrentPersonID() + "','" + context.getCurrentPersonCode() + "','"
					+ context.getCurrentPersonName() + "'");
			sqlStr.append(",'" + context.getCurrentPositionID() + "','" + context.getCurrentPositionCode() + "','"
					+ context.getCurrentPositionName() + "'");
			sqlStr.append(",'" + context.getCurrentDeptID() + "','" + context.getCurrentDeptCode() + "','"
					+ context.getCurrentDeptName() + "'");
			sqlStr.append(",'" + context.getCurrentOgnID() + "','" + context.getCurrentOgnCode() + "','"
					+ context.getCurrentOgnName() + "'");
			sqlStr.append(
					",'" + euser.getPersonID() + "','" + euser.getPersonCode() + "','" + euser.getPersonName() + "'");
			sqlStr.append(",'" + euser.getPositionID() + "','" + euser.getPositionCode() + "','"
					+ euser.getPositionName() + "'");
			sqlStr.append(",'" + euser.getDeptID() + "','" + euser.getDeptCode() + "','" + euser.getDeptName() + "'");
			sqlStr.append(",'" + euser.getOgnID() + "','" + euser.getOgnCode() + "','" + euser.getOgnName() + "'");
			sqlStr.append(",'" + context.getCurrentPersonFullID() + "','" + context.getCurrentPersonFullName() + "','"
					+ euser.getPersonFullID() + "','" + euser.getPersonFullName() + "'");
			sqlStr.append(",'" + sData1 + "','tesReady','尚未处理',CURRENTDATE1,CURRENTDATE2,'0')");

			String isql = sqlStr.toString();
			if (DBUtils.IsOracleDB("system") || DBUtils.IsDMDB("system")) {
				isql = isql.replace("CURRENTDATE1", "sysdate");
				isql = isql.replace("CURRENTDATE2", "sysdate");
			} else if (DBUtils.IsMySQLDB("system")) {
				isql = isql.replace("CURRENTDATE1", "now()");
				isql = isql.replace("CURRENTDATE2", "now()");
			} else {
				isql = isql.replace("CURRENTDATE1", "getdate()");
				isql = isql.replace("CURRENTDATE2", "getdate()");
			}

			DBUtils.execInsertQuery("system", isql);
		}
		if (ePersonList.size() > 0) {
			String sql = "update " + task_table
					+ " set SSTATUSID='tesTransmit' ,SSTATUSNAME='已转发',SEXECUTETIME=CURRENTDATE1, VERSION=VERSION+1 where SID = '"
					+ taskID + "'";
			if (DBUtils.IsOracleDB("system") || DBUtils.IsDMDB("system")) {
				sql = sql.replace("CURRENTDATE1", "sysdate");
			} else if (DBUtils.IsMySQLDB("system")) {
				sql = sql.replace("CURRENTDATE1", "now()");
			} else {
				sql = sql.replace("CURRENTDATE1", "getdate()");
			}
			DBUtils.execUpdateQuery("system", sql);
		}
		return newtaskID;
	}

	/*
	 * 回退
	 */
	public static String FlowBack(String processID, String processName, String Activity, String flowID, String taskID,
			String sData1, List<OrgUtils> ePersonList) throws SQLException {
		FlowActivity act = new FlowActivity(processID, Activity);
		String nprocessName = act.getActivityname();// 任务标题
		processName = nprocessName + "←回退-" + processName;
		String eURL = act.getUrl();
		String cuActivity = getCurrentActivity(taskID);
		FlowActivity Cuact = new FlowActivity(processID, cuActivity);
		String cURL = Cuact.getUrl();
		String newtaskID = UUID.randomUUID().toString().toUpperCase().replaceAll("-", "");
		ContextBean context = new BaseController().getContext();
		for (int i = 0; i < ePersonList.size(); i++) {
			OrgUtils euser = ePersonList.get(i);
			StringBuilder sqlStr = new StringBuilder();
			sqlStr.append("insert into ");
			sqlStr.append(task_table);
			sqlStr.append("(SID,SPARENTID,SFLOWID,SNAME,SCURL,SEURL,SPROCESS");
			sqlStr.append(",SACTIVITY,SCPERSONID,SCPERSONCODE,SCPERSONNAME");
			sqlStr.append(",SCPOSID,SCPOSCODE,SCPOSNAME");
			sqlStr.append(",SCDEPTID,SCDEPTCODE,SCDEPTNAME");
			sqlStr.append(",SCOGNID,SCOGNCODE,SCOGNNAME");
			sqlStr.append(",SEPERSONID,SEPERSONCODE,SEPERSONNAME");
			sqlStr.append(",SEPOSID,SEPOSCODE,SEPOSNAME");
			sqlStr.append(",SEDEPTID,SEDEPTCODE,SEDEPTNAME");
			sqlStr.append(",SEOGNID,SEOGNCODE,SEOGNNAME");
			sqlStr.append(",SCFID,SCFNAME,SEFID,SEFNAME");
			sqlStr.append(",SDATA1,SSTATUSID,SSTATUSNAME,SCREATETIME,sAStartTime,VERSION)values(");
			sqlStr.append("'" + newtaskID + "','" + taskID + "','" + flowID + "'");
			sqlStr.append(
					",'" + processName + "','" + cURL + "','" + eURL + "','" + processID + "','" + Activity + "'");
			sqlStr.append(",'" + context.getCurrentPersonID() + "','" + context.getCurrentPersonCode() + "','"
					+ context.getCurrentPersonName() + "'");
			sqlStr.append(",'" + context.getCurrentPositionID() + "','" + context.getCurrentPositionCode() + "','"
					+ context.getCurrentPositionName() + "'");
			sqlStr.append(",'" + context.getCurrentDeptID() + "','" + context.getCurrentDeptCode() + "','"
					+ context.getCurrentDeptName() + "'");
			sqlStr.append(",'" + context.getCurrentOgnID() + "','" + context.getCurrentOgnCode() + "','"
					+ context.getCurrentOgnName() + "'");
			sqlStr.append(
					",'" + euser.getPersonID() + "','" + euser.getPersonCode() + "','" + euser.getPersonName() + "'");
			sqlStr.append(",'" + euser.getPositionID() + "','" + euser.getPositionCode() + "','"
					+ euser.getPositionName() + "'");
			sqlStr.append(",'" + euser.getDeptID() + "','" + euser.getDeptCode() + "','" + euser.getDeptName() + "'");
			sqlStr.append(",'" + euser.getOgnID() + "','" + euser.getOgnCode() + "','" + euser.getOgnName() + "'");
			sqlStr.append(",'" + context.getCurrentPersonFullID() + "','" + context.getCurrentPersonFullName() + "','"
					+ euser.getPersonFullID() + "','" + euser.getPersonFullName() + "'");
			sqlStr.append(",'" + sData1 + "','tesReady','尚未处理',CURRENTDATE1,CURRENTDATE2,'0')");
			String isql = sqlStr.toString();
			if (DBUtils.IsOracleDB("system") || DBUtils.IsDMDB("system")) {
				isql = isql.replace("CURRENTDATE1", "sysdate");
				isql = isql.replace("CURRENTDATE2", "sysdate");
			} else if (DBUtils.IsMySQLDB("system")) {
				isql = isql.replace("CURRENTDATE1", "now()");
				isql = isql.replace("CURRENTDATE2", "now()");
			} else {
				isql = isql.replace("CURRENTDATE1", "getdate()");
				isql = isql.replace("CURRENTDATE2", "getdate()");
			}
			DBUtils.execInsertQuery("system", isql);
		}
		if (ePersonList.size() > 0) {
			String sql = "update " + task_table
					+ " set SSTATUSID='tesReturned' ,SSTATUSNAME='已回退',SEXECUTETIME=CURRENTDATE1, VERSION=VERSION+1 where SID = '"
					+ taskID + "'";
			String ptaskID = getCurrentTaskParentID(taskID);
			String sql_2 = "update " + task_table
					+ " set SSTATUSID='tesCanceled' ,SSTATUSNAME='已取消',SEXECUTETIME=CURRENTDATE1, VERSION=VERSION+1 where (SID != '"
					+ taskID + "' and SPARENTID = '" + ptaskID + "') and (SACTIVITY ='" + cuActivity
					+ "' and SFLOWID ='" + flowID + "' and SSTATUSID = 'tesReady')";
			if (DBUtils.IsOracleDB("system") || DBUtils.IsDMDB("system")) {
				sql = sql.replace("CURRENTDATE1", "sysdate");
				sql_2 = sql_2.replace("CURRENTDATE1", "sysdate");
			} else if (DBUtils.IsMySQLDB("system")) {
				sql = sql.replace("CURRENTDATE1", "now()");
				sql_2 = sql_2.replace("CURRENTDATE1", "now()");
			} else {
				sql = sql.replace("CURRENTDATE1", "getdate()");
				sql_2 = sql_2.replace("CURRENTDATE1", "getdate()");
			}
			DBUtils.execUpdateQuery("system", sql);
			DBUtils.execUpdateQuery("system", sql_2);
		}
		return newtaskID;
	}

	/*
	 * 取消
	 */
	public static void FlowCancel(String flowID, String taskID) throws SQLException {
		String sql = "update " + task_table
				+ " set SSTATUSID='tesCanceled' ,SSTATUSNAME='已取消', SEXECUTETIME=CURRENTDATE1 ,VERSION=VERSION+1 where SID = '"
				+ taskID + "' and SSTATUSID = 'tesReady'";
		if (DBUtils.IsOracleDB("system") || DBUtils.IsDMDB("system")) {
			sql = sql.replace("CURRENTDATE1", "sysdate");
		} else if (DBUtils.IsMySQLDB("system")) {
			sql = sql.replace("CURRENTDATE1", "now()");
		} else {
			sql = sql.replace("CURRENTDATE1", "getdate()");
		}
		DBUtils.execUpdateQuery("system", sql);
	}

	/*
	 * 终止
	 */
	public static void FlowAbort(String flowID, String taskID) throws SQLException {
		String sql = "update " + task_table
				+ " set SSTATUSID='tesAborted' ,SSTATUSNAME='已终止', SEXECUTETIME=CURRENTDATE1 ,VERSION=VERSION+1 where SID = '"
				+ taskID + "'";
		String msql = "update " + task_table
				+ " set SSTATUSID='tesAborted' ,SSTATUSNAME='已终止', SEXECUTETIME=CURRENTDATE1 ,VERSION=VERSION+1 where SID = '"
				+ flowID + "'";
		if (DBUtils.IsOracleDB("system") || DBUtils.IsDMDB("system")) {
			sql = sql.replace("CURRENTDATE1", "sysdate");
			msql = msql.replace("CURRENTDATE1", "sysdate");
		} else if (DBUtils.IsMySQLDB("system")) {
			sql = sql.replace("CURRENTDATE1", "now()");
			msql = msql.replace("CURRENTDATE1", "now()");
		} else {
			sql = sql.replace("CURRENTDATE1", "getdate()");
			msql = msql.replace("CURRENTDATE1", "getdate()");
		}
		DBUtils.execUpdateQuery("system", sql);
		DBUtils.execUpdateQuery("system", msql);
	}

	/*
	 * 暂停
	 */
	public static void FlowPause(String flowID, String taskID) throws SQLException {
		String sql = "update " + task_table + " set SSTATUSID='tesPause' ,SSTATUSNAME='已暂停' where SID = '" + taskID
				+ "'";
		String msql = "update " + task_table + " set SSTATUSID='tesPause' ,SSTATUSNAME='已暂停' where SID = '" + flowID
				+ "'";
		DBUtils.execUpdateQuery("system", sql);
		DBUtils.execUpdateQuery("system", msql);
	}

	/*
	 * 激活
	 */
	public static void FlowRestart(String flowID, String taskID) throws SQLException {
		String sql = "update " + task_table + " set SSTATUSID='tesReady' ,SSTATUSNAME='尚未处理' where SID = '" + taskID
				+ "'";
		String msql = "update " + task_table + " set SSTATUSID='tesExecuting' ,SSTATUSNAME='正在处理' where SID = '"
				+ flowID + "'";
		DBUtils.execUpdateQuery("system", sql);
		DBUtils.execUpdateQuery("system", msql);
	}

	/*
	 * 验证状态
	 */
	public static Map checktesReady(String taskID) throws SQLException {
		Map rsmap = new HashMap();
		String sql = "select  SSTATUSID,SSTATUSNAME from " + task_table + " where SID = '" + taskID + "'";
		List list = DBUtils.execQueryforList("system", sql);
		if (list.size() > 0) {
			rsmap = (Map) list.get(0);
		}
		return rsmap;
	}

	/*
	 * 获取当前流程标识
	 */
	public static String getCurrentProcessID(String taskID) throws SQLException {
		String SPROCESS = null;
		String sql = "select SPROCESS from " + task_table + " where SID = '" + taskID + "'";
		List li = DBUtils.execQueryforList("system", sql);
		if (li.size() > 0) {
			SPROCESS = (String) ((Map) li.get(0)).get("SPROCESS");
		}
		return SPROCESS;
	}

	/*
	 * 获取流程标识
	 */
	public static String getProcessID(String flowID) throws SQLException {
		String SPROCESS = null;
		String sql = "select SPROCESS from " + task_table + " where SFLOWID = '" + flowID + "'";
		List li = DBUtils.execQueryforList("system", sql);
		if (li.size() > 0) {
			SPROCESS = (String) ((Map) li.get(0)).get("SPROCESS");
		}
		return SPROCESS;
	}

	/*
	 * 获取流程flow标识
	 */
	public static String getFlowID(String taskID) throws SQLException {
		String SPROCESS = null;
		String sql = "select SFLOWID from " + task_table + " where SID = '" + taskID + "'";
		List li = DBUtils.execQueryforList("system", sql);
		if (li.size() > 0) {
			SPROCESS = (String) ((Map) li.get(0)).get("SFLOWID");
		}
		return SPROCESS;
	}

	/*
	 * 获取当前所在环节标识
	 */
	public static String getCurrentActivity(String taskID) throws SQLException {
		String activity = "start";
		String sql = "select SACTIVITY from " + task_table + " where SID = '" + taskID + "'";
		List li = DBUtils.execQueryforList("system", sql);
		if (li.size() > 0) {
			activity = (String) ((Map) li.get(0)).get("SACTIVITY");
		}
		return activity;
	}

	/*
	 * 获取当前所在环节任务名称
	 */
	public static String getCurrentActivityName(String taskID) throws SQLException {
		String activity = "";
		String sql = "select SNAME from " + task_table + " where SID = '" + taskID + "'";
		List li = DBUtils.execQueryforList("system", sql);
		if (li.size() > 0) {
			activity = (String) ((Map) li.get(0)).get("SNAME");
		}
		return activity;
	}

	/*
	 * 获取当前所在环节sData1
	 */
	public static String getCurrentActivitysData1(String taskID) throws SQLException {
		String sdata = "";
		String sql = "select SDATA1 from " + task_table + " where SID = '" + taskID + "'";
		List li = DBUtils.execQueryforList("system", sql);
		if (li.size() > 0) {
			sdata = (String) ((Map) li.get(0)).get("SDATA1");
		}
		return sdata;
	}

	/*
	 * 获取提交环节标识
	 */
	public static String getBeforeActivity(String taskID) throws SQLException {
		String activity = "startActivity";
		String sql = "select SACTIVITY from " + task_table + " where SID in (select SPARENTID from " + task_table
				+ " where SID = '" + taskID + "') and SSTATUSID != 'tesReturned'";
		List li = DBUtils.execQueryforList("system", sql);
		if (li.size() > 0) {
			activity = (String) ((Map) li.get(0)).get("SACTIVITY");
		}
		return activity;
	}

	/*
	 * 获取提交环节执行人
	 * 
	 * @flowID 流程ID
	 * 
	 * @url 执行地址
	 */
	public static List<OrgUtils> getBeforeActivityExecutor(String flowID, String url) throws SQLException {
		List list = new ArrayList();
		String sql = "select SEPERSONID from " + task_table + " where sFlowID = '" + flowID + "' and sEURL = '" + url
				+ "'";
		List li = DBUtils.execQueryforList("system", sql);
		if (li.size() > 0) {
			String personid = (String) ((Map) li.get(0)).get("SEPERSONID");
			OrgUtils org = new OrgUtils(personid);
			list.add(org);
		}
		return list;
	}

	/*
	 * 获取提指定环节执行人
	 * 
	 * @flowID 流程ID
	 * 
	 * @activity 环节
	 */
	public static List<OrgUtils> getActivityExecutor(String flowID, String activity) throws SQLException {
		List list = new ArrayList();
		String sql = "select SEPERSONID from " + task_table + " where sFlowID = '" + flowID + "' and SACTIVITY = '"
				+ activity + "' and SSTATUSID = 'tesFinished' order by SCREATETIME desc";
		List li = DBUtils.execQueryforList("system", sql);
		if (li.size() > 0) {
			String personid = (String) ((Map) li.get(0)).get("SEPERSONID");
			OrgUtils org = new OrgUtils(personid);
			list.add(org);
		}
		return list;
	}

	/*
	 * 获取提交环节执行人
	 * 
	 * @taskID 任务ID
	 */
	public static List<OrgUtils> getBeforeActivityExecutor(String taskID) throws SQLException {
		List list = new ArrayList();
		String sql = "select SEPERSONID from " + task_table + " where SID in (select SPARENTID from " + task_table
				+ " where SID = '" + taskID + "') and SSTATUSID = 'tesFinished'"; // and SSTATUSID !=
																					// 'tesReturned'
		List li = DBUtils.execQueryforList("system", sql);
		if (li.size() > 0) {
			String personid = (String) ((Map) li.get(0)).get("SEPERSONID");
			OrgUtils org = new OrgUtils(personid);
			list.add(org);
		}
		return list;
	}

	/*
	 * 检测流程是否已经结束
	 */
	public static boolean checkisfinished(String flowID) throws SQLException {
		String sql = "select SEPERSONID from " + task_table + " where SID = '" + flowID
				+ "' and SSTATUSID='tesFinished'";
		List li = DBUtils.execQueryforList("system", sql);
		if (li.size() > 0) {
			return true;
		}
		return false;
	}

	/*
	 * 获取任务信息{标题、执行页面、单据ID、执行人ID、提交人ID}
	 */
	public static Map<String, String> getTaskInfor(String taskID, String executor) throws SQLException {
		String sql = "select SNAME,SEURL,SDATA1,SCPERSONID,SEPERSONID,SFLOWID,SPROCESS,SACTIVITY from " + task_table
				+ " where SID = '" + taskID + "'";
		List<Map<String, String>> li = DBUtils.execQueryforList("system", sql);
		if (li.size() > 0) {
			Map m = li.get(0);
			HashMap hm = new HashMap();
			hm.put("name", m.get("SNAME"));
			hm.put("url", m.get("SEURL"));
			hm.put("sData1", m.get("SDATA1"));
			hm.put("flowID", m.get("SFLOWID"));
			hm.put("process", m.get("SPROCESS"));
			hm.put("activity", m.get("SACTIVITY"));
			return hm;
		}
		return null;
	}

	/*
	 * 获取已走过的环节
	 */
	public static Map<String, String> getHistoryActivity(String flowID) throws SQLException {
		Map<String, String> activityList = new HashMap();
		String sql = "select SACTIVITY,SNAME from " + task_table + " where SFLOWID= '" + flowID + "'";

		List li = DBUtils.execQueryforList("system", sql);
		for (int i = 0; i < li.size(); i++) {
			String activity = (String) ((Map) li.get(i)).get("SACTIVITY");
			String activityName = (String) ((Map) li.get(i)).get("SNAME");
			activityList.put(activity, activityName);
		}
		return activityList;
	}

	public static JSONObject getExcutor(String eUrl) {
		String sql = "select SCHECKPSN from SA_FLOWTRACE where SOPERATORID = '"
				+ new BaseController().getContext().getCurrentPersonID() + "' and SEURL ='" + eUrl + "'";
		try {
			List<Map<String, String>> li = DBUtils.execQueryforList("system", sql);
			if (li.size() > 0) {
				Map m = li.get(0);
				String chid = (String) m.get("SCHECKPSN");
				return JSON.parseObject(chid);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/*
	 * 获取当前任务的父ID
	 */
	public static String getCurrentTaskParentID(String taskID) {
		String sql = "select SPARENTID from " + task_table + " where SID = '" + taskID + "'";
		String ptid = "";
		try {
			List<Map<String, String>> li = DBUtils.execQueryforList("system", sql);
			if (li.size() > 0) {
				ptid = li.get(0).get("SPARENTID");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return ptid;
	}

	public static void main(String[] args) {
		/*
		 * OrgUtils euser = new OrgUtils("PSN01"); Sys.printMsg(euser.getPersonID());
		 * Sys.printMsg(euser.getPersonName()); Sys.printMsg(euser.getPersonFullID());
		 * Sys.printMsg(euser.getPersonFullName());
		 */
		Map map = FlowStringtoJSON.parseMap("{\"key\":\"value\"}");
		Sys.printMsg(map.get("key"));
		List array = FlowStringtoJSON.ParseMapList("[{\"G30801\":\"网页设计\",\"G30701\":\"数学\"}]");
		Map m = (Map) array.get(0);
		Sys.printMsg(m.get("G30801"));
		/*
		 * FlowActivity a = new FlowActivity("C540FACAB03000016FBA1BF01FD21EAB",
		 * "bizActivity2"); Sys.printMsg(a.getProcessName());
		 * Sys.printMsg(a.getBeforeActivity().get(0).getActivityname());
		 * Sys.printMsg(a.getAfterActivity().get(0).getActivityname());
		 */
	}
}
