package com.tlv8.mobile;

import java.net.URLDecoder;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.List;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tlv8.base.db.DBUtils;
import com.tlv8.base.utils.IDUtils;
import com.tlv8.base.ActionSupport;
import com.tlv8.flw.base.FlowActivity;
import com.tlv8.flw.base.TaskData;
import com.tlv8.system.bean.ContextBean;

/**
 * 保存审批意见
 * 
 * @author 陈乾
 *
 */
@Controller
@Scope("prototype")
public class SaveApprovalOpinionAction extends ActionSupport {
	private String sdata1;
	private String taskID;
	private String opinion;
	private String state;
	private String statename;

	@ResponseBody
	@RequestMapping("/saveApprovalOpinionAction")
	@SuppressWarnings({ "rawtypes", "deprecation" })
	public Object execute() throws Exception {
		String ProcessID = TaskData.getCurrentProcessID(taskID);
		String ACTIVITY = TaskData.getCurrentActivity(taskID);
		FlowActivity act = new FlowActivity(ProcessID, ACTIVITY);
		String ACTIVITYNAME = act.getUrl().replace(".w", "");
		String FACTIVITYLABEL = act.getActivityname();
		String FID = IDUtils.getGUID();
		ContextBean context = ContextBean.getContext(request);
		String sql_check = "select 1 from OA_PUB_EXECUTE where FTASKID = '" + taskID + "'";
		String sql = "";
		try {
			List cl = DBUtils.execQueryforList("oa", sql_check);
//			System.out.println(cl.size());
			if (cl.size() > 0) {
				sql = "update OA_PUB_EXECUTE set FOPINION = ? where FTASKID = '" + taskID + "'";
			} else {
				sql = "insert into OA_PUB_EXECUTE(" + "FID,FMASTERID,FTASKID,FACTIVITYFNAME,FACTIVITYLABEL,FOPINION"
						+ ",FSTATE,FSTATENAME,FCREATEOGNID,FCREATEOGNNAME,FCREATEDEPTID,"
						+ "FCREATEDEPTNAME,FCREATEPOSID,FCREATEPOSNAME,FCREATEPSNID,FCREATEPSNNAME"
						+ ",FCREATEPSNFID,FCREATEPSNFNAME,FCREATETIME,"
						+ "FUPDATEPSNID,FUPDATEPSNNAME,FUPDATETIME,VERSION)values(" + "'" + FID + "','" + sdata1 + "','"
						+ taskID + "','" + ACTIVITYNAME + "','" + FACTIVITYLABEL + "',?,'" + state + "','" + statename
						+ "','" + context.getCurrentOgnID() + "','" + context.getCurrentOgnName() + "','"
						+ context.getCurrentDeptID() + "','" + context.getCurrentDeptName() + "','"
						+ context.getCurrentPositionID() + "','" + context.getCurrentPositionName() + "','"
						+ context.getCurrentPersonID() + "','" + context.getCurrentPersonName() + "','"
						+ context.getCurrentPersonFullID() + "','" + context.getCurrentPersonFullName() + "',sysdate,'"
						+ context.getCurrentPersonID() + "','" + context.getCurrentPersonName() + "',sysdate,0)";
			}
			Connection conn = null;
			PreparedStatement ps = null;
			try {
				conn = DBUtils.getAppConn("oa");
				conn.setAutoCommit(false);
				ps = conn.prepareStatement(sql);
				ps.setString(1, opinion);
				ps.executeUpdate();
				conn.commit();
			} catch (Exception e) {
				conn.rollback();
				e.printStackTrace();
			} finally {
				conn.setAutoCommit(true);
				try {
					DBUtils.CloseConn(conn, ps, null);
				} catch (Exception e) {
				}
			}
		} catch (Exception e) {
		}
		return this;
	}

	public void setSdata1(String sdata1) {
		this.sdata1 = sdata1;
	}

	public String getSdata1() {
		return sdata1;
	}

	public void setTaskID(String taskID) {
		this.taskID = taskID;
	}

	public String getTaskID() {
		return taskID;
	}

	public void setOpinion(String opinion) {
		try {
			this.opinion = URLDecoder.decode(opinion, "UTF-8");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public String getOpinion() {
		return opinion;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getState() {
		return state;
	}

	public void setStatename(String statename) {
		try {
			this.statename = URLDecoder.decode(statename, "UTF-8");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public String getStatename() {
		return statename;
	}
}
