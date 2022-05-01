package com.tlv8.flw;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tlv8.base.Data;
import com.tlv8.base.db.DBUtils;
import com.tlv8.base.utils.StringArray;
import com.tlv8.flw.base.FlowActivity;
import com.tlv8.flw.base.TaskData;
import com.tlv8.flw.bean.FlowDataBean;
import com.tlv8.flw.expression.BooleanExpression;
import com.tlv8.system.bean.ContextBean;
import com.tlv8.system.utils.OrgUtils;

/**
 * @author ChenQian
 * @see 流程通用操作接口
 */
@Controller
@Scope("prototype")
@SuppressWarnings({ "rawtypes" })
public class FlowControler extends FlowDataBean {
	private Data data = new Data();
	private String srcPath;
	private String afterActivity;

	public Data getData() {
		return this.data;
	}

	public void setData(Data data) {
		this.data = data;
	}

	public static String seachProcessID(String path, HttpServletRequest request) {
		String result = null;
		path = path.replace(request.getContextPath(), "");
		path = path.replace("/", "\\/");
		if (DBUtils.IsMySQLDB("system")) {
			path = path.replace("\\", "\\\\\\\\");
			path = path.substring(1);
		}
		String sql = "SELECT SPROCESSID FROM sa_flowdrawlg WHERE SPROCESSACTY like '%" + path + "%'";
		try {
			List<Map<String, String>> list = DBUtils.execQueryforList("system", sql);
			if (list.size() > 0) {
				Map m = list.get(0);
				return (String) m.get("SPROCESSID");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return result;
	}

	/**
	 * 启动流程
	 */
	@ResponseBody
	@RequestMapping("/flowstartAction")
	public Object start() {
		try {
			if (processID == null || "".equals(processID) || "undefined".equals(flowID)) {
				processID = seachProcessID(srcPath, request);
			}
			if (processID == null) {
				data.setFlag("false");
				data.setMessage("启动流程失败！找不到" + srcPath + "对应的流程图。");
				return this;
			}
			flowID = startflow(processID, sdata1);
			List<OrgUtils> ePersonList = new ArrayList<OrgUtils>();
			ePersonList.add(new OrgUtils(request));
			FlowActivity flwA = new FlowActivity(processID, "start");
			taskID = flowout(flowID, flowID, sdata1, ePersonList, flwA.getAfterActivity(request));
			data.setFlag("true");
			data.setData(toJSONString());
		} catch (Exception e) {
			data.setFlag("false");
			data.setMessage(e.toString());
			e.printStackTrace();
		}
		return this;
	}

	/**
	 * 流转流程
	 */
	@ResponseBody
	@RequestMapping("/flowoutAction")
	public Object out() {
		try {
			if ("".equals(flowID) || flowID == null || "undefined".equals(flowID)) {
				start();
			}
			if ("".equals(taskID) || taskID == null || "undefined".equals(taskID)) {
				return this;
			}
			String processID = TaskData.getCurrentProcessID(taskID);
			String Activity = TaskData.getCurrentActivity(taskID);
			FlowActivity flwA = new FlowActivity(processID, Activity);
			List<FlowActivity> aftAList = new ArrayList<FlowActivity>();
			if (!"".equals(afterActivity) && afterActivity != null) {
				aftAList = new ArrayList<FlowActivity>();
				aftAList.add(new FlowActivity(processID, afterActivity));
			} else {
				aftAList = flwA.getAfterActivity(flowID, taskID, sdata1, request);
			}
			List<OrgUtils> ePersonList = new ArrayList<OrgUtils>();
			if (aftAList.size() == 1 && "end".equals(aftAList.get(0).getType())
					&& (!"together".equals(flwA.getGrapModle()) || !"merge".equals(flwA.getGrapWay()))) {
				// 如果下一环节为结束环节时 不做流转确认
				OrgUtils OrgU = new OrgUtils(request);
				ePersonList.add(OrgU);
				setTaskID(flowout(flowID, taskID, sdata1, ePersonList, aftAList));
				data.setFlag("end");
				data.setData(toJSONString());
				return this;
			}
			// 共同模式且需要合并
			if ("together".equals(flwA.getGrapModle()) && "merge".equals(flwA.getGrapWay())) {
				List lis = DBUtils.execQueryforList("system",
						"select SID from SA_TASK where SFLOWID = '" + flowID + "' and SACTIVITY = '"
								+ flwA.getActivity() + "' and SID != '" + taskID + "' and SSTATUSID = 'tesReady'");
				if (lis.size() > 0) {
					String sql = "update SA_TASK set SSTATUSID='tesFinished' ,SSTATUSNAME='已完成',SEXECUTETIME=CURRENTDATE1,sAFinishTime=CURRENTDATE2, VERSION=VERSION+1 where SID = '"
							+ taskID + "' and SSTATUSID != 'tesExecuting'";
					if (DBUtils.IsOracleDB("system")) {
						sql = sql.replace("CURRENTDATE1", "sysdate");
						sql = sql.replace("CURRENTDATE2", "sysdate");
					} else if (DBUtils.IsMySQLDB("system")) {
						sql = sql.replace("CURRENTDATE1", "now()");
						sql = sql.replace("CURRENTDATE2", "now()");
					} else {
						sql = sql.replace("CURRENTDATE1", "getdate()");
						sql = sql.replace("CURRENTDATE2", "getdate()");
					}
					DBUtils.execUpdateQuery("system", sql);
					data.setFlag("msg");
					data.setMessage("请等待其他人处理!");
					return this;
				}
			}
			if (aftAList.size() == 1 && "no".equals(aftAList.get(0).getOutquery())) {// 不需要流转确认时处理
				String epersonid = aftAList.get(0).getExcutorIDs("self");
				if (epersonid != null && !"".equals(epersonid)) {// 指定固定执行人
					if ((epersonid.indexOf(",") > 0)) {
						String[] excutors = epersonid.split(",");
						for (int i = 0; i < excutors.length; i++) {
							if (!"".equals(excutors[i].trim())) {
								OrgUtils OrgU = new OrgUtils(excutors[i]);
								ePersonList.add(OrgU);
							}
						}
					} else {
						OrgUtils OrgU = new OrgUtils(epersonid);
						ePersonList.add(OrgU);
					}
					setTaskID(flowout(flowID, taskID, sdata1, ePersonList, aftAList));
					data.setFlag("true");
					data.setData(toJSONString());
					return this;
				} else {// 未指定固定执行人
					if (epersonids != null && !"".equals(epersonids)) {
						// 不需要做什么【已经选择执行人】
					} else {
						String exeGroup = aftAList.get(0).getExcutorGroup();
						if (exeGroup != null && !"".equals(exeGroup)) {
							exeGroup = exeGroup.replaceAll("getProcessID\\(\\)", processID);
							exeGroup = exeGroup.replaceAll("getFlowID\\(\\)", flowID);
							exeGroup = exeGroup.replaceAll("getTaskID\\(\\)", taskID);
							exeGroup = exeGroup.replaceAll("getProcesssData1\\(\\)", sdata1);
						}
						String excutorGroup = "";
						if (aftAList.get(0).getExcutorGroup() == null || "".equals(aftAList.get(0).getExcutorGroup())) {
							excutorGroup = BooleanExpression.getScriptExpressionVal(
									BooleanExpression.resolutionExpression("getOrgUnitHasActivity(\"" + processID
											+ "\",\"" + aftAList.get(0).getId() + "\",\"FALSE\",\"FALSE\")", request));
						} else {
							excutorGroup = BooleanExpression
									.getScriptExpressionVal(BooleanExpression.resolutionExpression(exeGroup, request));
						}
						StringArray excutorGroupfilter = new StringArray();
						String[] exGroups = excutorGroup.split(",");
						for (int i = 0; i < exGroups.length; i++) {
							if (exGroups[i] != null && !"".equals(exGroups[i])) {
								excutorGroupfilter.push(" SFID like '%" + exGroups[i] + "%'");
							}
						}
						if (excutorGroupfilter.getLength() > 0) {
							String excutorGroupfilters = "(" + excutorGroupfilter.join(" or ") + ")";
							excutorGroupfilters += " and SFID like '"
									+ ContextBean.getContext(request).getCurrentOgnFullID() + "%'";
							String sql = "select SID from SA_OPORG where SORGKINDID='psm' and (" + excutorGroupfilters
									+ ")";
							List<Map<String, String>> reList = DBUtils.execQueryforList("system", sql);
							for (int i = 0; i < reList.size(); i++) {
								if (i > 0)
									epersonids += ",";
								epersonids += reList.get(i).get("SID");
							}
						}
					}
				}
			}
			if (epersonids != null && !"".equals(epersonids)) {
				if ((epersonids.indexOf(",") > 0)) {
					String[] excutors = epersonids.split(",");
					for (int i = 0; i < excutors.length; i++) {
						OrgUtils OrgU = new OrgUtils(excutors[i]);
						ePersonList.add(OrgU);
					}
				} else {
					OrgUtils OrgU = new OrgUtils(epersonids);
					ePersonList.add(OrgU);
				}
			} else {
				if (processID != null) {
					if (aftAList.size() == 1) {
						// 如果下一环节为结束环节时 不做流转确认
						if ("end".equals(aftAList.get(0).getType())) {
							OrgUtils OrgU = new OrgUtils(request);
							ePersonList.add(OrgU);
							setTaskID(flowout(flowID, taskID, sdata1, ePersonList, aftAList));
							data.setFlag("end");
							data.setData(toJSONString());
							return SUCCESS;
						}
					}
					data.setFlag("select");
					StringBuffer afterActList = new StringBuffer();
					afterActList.append("[");
					for (int i = 0; i < aftAList.size(); i++) {
						if (i > 0)
							afterActList.append(",");
						String exeGroup = aftAList.get(i).getExcutorGroup();
						if (exeGroup != null && !"".equals(exeGroup)) {
							exeGroup = exeGroup.replaceAll("getProcessID\\(\\)", processID);
							exeGroup = exeGroup.replaceAll("getFlowID\\(\\)", flowID);
							exeGroup = exeGroup.replaceAll("getTaskID\\(\\)", taskID);
							exeGroup = exeGroup.replaceAll("getProcesssData1\\(\\)", sdata1);
						}
						String excutorGroup = "";
						if (aftAList.get(i).getExcutorGroup() == null || "".equals(aftAList.get(i).getExcutorGroup())) {
							excutorGroup = BooleanExpression.getScriptExpressionVal(
									BooleanExpression.resolutionExpression("getOrgUnitHasActivity(\"" + processID
											+ "\",\"" + aftAList.get(i).getId() + "\",\"FALSE\",\"FALSE\")", request));
						} else {
							excutorGroup = BooleanExpression
									.getScriptExpressionVal(BooleanExpression.resolutionExpression(exeGroup, request));
						}
						String activitylabel = aftAList.get(i).getsActivityLabel();
						if (activitylabel == null || "".equals(activitylabel)) {
							activitylabel = aftAList.get(i).getActivityname() + ":" + aftAList.get(i).getProcessName();
						} else {
							activitylabel = activitylabel.replaceAll("getProcessID\\(\\)", processID);
							activitylabel = exeGroup.replaceAll("getFlowID\\(\\)", flowID);
							activitylabel = activitylabel.replaceAll("getTaskID\\(\\)", taskID);
							activitylabel = activitylabel.replaceAll("getProcesssData1\\(\\)", sdata1);
							activitylabel = BooleanExpression.getScriptExpressionVal(
									BooleanExpression.resolutionExpression(activitylabel, request));
						}
						afterActList.append("{id:\"" + aftAList.get(i).getId() + "\",name:\""
								+ aftAList.get(i).getActivityname() + "\",label:\"" + activitylabel + "\",type:\""
								+ aftAList.get(i).getType() + "\",excutorGroup:\"" + excutorGroup + "\",excutorIDs:\""
								+ aftAList.get(i).getExcutorIDs() + "\",excutorNames:\""
								+ aftAList.get(i).getExcutorNames() + "\"}");
					}
					afterActList.append("]");
					// System.out.println(afterActList);

					data.setData("{activityListStr:'" + afterActList.toString() + "',flowID:'" + flowID + "',taskID:'"
							+ taskID + "'}");
					return this;
				}
			}
			setTaskID(flowout(flowID, taskID, sdata1, ePersonList, aftAList));
			data.setFlag("true");
			data.setData(toJSONString());
		} catch (Exception e) {
			data.setFlag("false");
			data.setMessage(e.toString());
			e.printStackTrace();
		}
		return this;
	}

	/**
	 * 回退流程
	 */
	@ResponseBody
	@RequestMapping("/flowbackAction")
	public Object back() {
		try {
			setTaskID(flowback(flowID, taskID));
			data.setFlag("true");
			data.setData(toJSONString());
		} catch (Exception e) {
			data.setFlag("false");
			data.setMessage(e.toString());
			e.printStackTrace();
		}
		return this;
	}

	/**
	 * 转发流程
	 */
	@ResponseBody
	@RequestMapping("/flowtransmitAction")
	public Object transmit() {
		try {
			String processID = TaskData.getCurrentProcessID(taskID);
			String Activity = TaskData.getCurrentActivity(taskID);
			FlowActivity flwA = new FlowActivity(processID, Activity);
			List<OrgUtils> ePersonList = new ArrayList<OrgUtils>();
			if (epersonids != null && !"".equals(epersonids)) {
				if ((epersonids.indexOf(",") > 0)) {
					String[] excutors = epersonids.split(",");
					for (int i = 0; i < excutors.length; i++) {
						OrgUtils OrgU = new OrgUtils(excutors[i]);
						ePersonList.add(OrgU);
					}
				} else {
					OrgUtils OrgU = new OrgUtils(epersonids);
					ePersonList.add(OrgU);
				}
			} else {
				if (processID != null) {
					data.setFlag("select");
					StringBuffer afterActList = new StringBuffer();
					afterActList.append("[");
					String exeGroup = flwA.getTranseRole();
					if (exeGroup != null) {
						exeGroup = exeGroup.replaceAll("getProcessID\\(\\)", processID);
						exeGroup = exeGroup.replaceAll("getFlowID\\(\\)", flowID);
						exeGroup = exeGroup.replaceAll("getTaskID\\(\\)", taskID);
						exeGroup = exeGroup.replaceAll("getProcesssData1\\(\\)", sdata1);
					}
					String excutorGroup = "";
					if (flwA.getTranseRole() == null || "".equals(flwA.getExcutorGroup())) {
						excutorGroup = BooleanExpression.getScriptExpressionVal(
								BooleanExpression.resolutionExpression("getOrgUnitHasActivity(\"" + processID + "\",\""
										+ flwA.getId() + "\",\"FALSE\",\"FALSE\")", request));
					} else {
						excutorGroup = BooleanExpression
								.getScriptExpressionVal(BooleanExpression.resolutionExpression(exeGroup, request));
					}
					afterActList.append("{id:\"" + flwA.getId() + "\",name:\"" + flwA.getActivityname() + "\",type:\""
							+ flwA.getType() + "\",excutorGroup:\"" + excutorGroup + "\",excutorIDs:\""
							+ flwA.getExcutorIDs() + "\",excutorNames:\"" + flwA.getExcutorNames() + "\"}");
					afterActList.append("]");
					data.setData("{activityListStr:'" + afterActList.toString() + "',flowID:'" + flowID + "',taskID:'"
							+ taskID + "'}");
					return this;
				}
			}
			setTaskID(flowforward(flowID, taskID, ePersonList));
			data.setFlag("true");
			data.setData(toJSONString());
		} catch (Exception e) {
			data.setFlag("false");
			data.setMessage(e.toString());
			e.printStackTrace();
		}
		return this;
	}

	/**
	 * 取消流程
	 */
	@ResponseBody
	@RequestMapping("/flowcancelAction")
	public Object cancel() {
		try {
			flowcancel(flowID, taskID);
			data.setFlag("true");
			data.setData(toJSONString());
		} catch (Exception e) {
			data.setFlag("false");
			data.setMessage(e.toString());
			e.printStackTrace();
		}
		return this;
	}

	/**
	 * 终止流程
	 */
	@ResponseBody
	@RequestMapping("/flowstopAction")
	public Object stop() {
		try {
			flowstop(flowID, taskID);
			data.setFlag("true");
			data.setData(toJSONString());
		} catch (Exception e) {
			data.setFlag("false");
			data.setMessage(e.toString());
			e.printStackTrace();
		}
		return this;
	}

	/**
	 * 暂停流程
	 */
	@ResponseBody
	@RequestMapping("/flowpauseAction")
	public Object pause() {
		try {
			flowpause(flowID, taskID);
			data.setFlag("true");
			data.setData(toJSONString());
		} catch (Exception e) {
			data.setFlag("false");
			data.setMessage(e.toString());
			e.printStackTrace();
		}
		return this;
	}

	/**
	 * 激活流程
	 */
	@ResponseBody
	@RequestMapping("/flowrestartAction")
	public Object restart() {
		try {
			flowrestart(flowID, taskID);
			data.setFlag("true");
			data.setData(toJSONString());
		} catch (Exception e) {
			data.setFlag("false");
			data.setMessage(e.toString());
			e.printStackTrace();
		}
		return this;
	}

	public void setSrcPath(String srcPath) {
		try {
			this.srcPath = URLDecoder.decode(srcPath, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
	}

	public String getSrcPath() {
		return srcPath;
	}

	public void setAfterActivity(String afterActivity) {
		try {
			this.afterActivity = URLDecoder.decode(afterActivity, "UTF-8");
		} catch (Exception e) {
		}
	}

	public String getAfterActivity() {
		return afterActivity;
	}

}
