package com.tlv8.flw.task;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tlv8.base.Data;
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
public class SaveAuditOpinionAction extends ActionSupport {
	private String dbkey;
	private String audittable;
	private String billidRe;
	private String agreettextRe;
	private String opinion;
	private String flowid;
	private String taskID;
	private String sdata1;
	private String opviewID;
	Data data = new Data();

	public void setData(Data data) {
		this.data = data;
	}

	public Data getData() {
		return this.data;
	}

	@ResponseBody
	@RequestMapping("/SaveAuditOpinionAction")
	@SuppressWarnings({ "rawtypes", "deprecation" })
	public Object execute() throws Exception {
		Connection conn = null;
		try {
			String FNODEID = "";
			String FNODENAME = "";
			ContextBean context = ContextBean.getContext(request);
			String excutorid = null;
			String excutorname = null;
			String currentpsnid = context.getCurrentPersonID();
			String currentpsnname = context.getCurrentPersonName();
			try {
				String processID = TaskData.getCurrentProcessID(this.taskID);
				String Activity = TaskData.getCurrentActivity(this.taskID);
				FlowActivity flwA = new FlowActivity(processID, Activity);
				FNODEID = flwA.getActivity();
				FNODENAME = flwA.getActivityname();
			} catch (Exception e) {
			}
			String tsSql = "select SEPERSONID,SEPERSONNAME from SA_TASK where SID = '" + this.taskID
					+ "' and SEPERSONID is not null";
			List tslist = DBUtils.execQueryforList("system", tsSql);
			if (tslist.size() > 0) {
				excutorid = (String) ((Map) tslist.get(0)).get("SEPERSONID");
				excutorname = (String) ((Map) tslist.get(0)).get("SEPERSONNAME");
			}
			if ((excutorid != null) && (!"".equals(excutorid)) && (!excutorid.equals(currentpsnid))) {
				currentpsnname = excutorname + "(" + currentpsnname + "代)";
			}
			String sql = "select FID from " + this.audittable + " where " + this.billidRe + " = " + "'" + this.sdata1
					+ "' and FCREATEPERID='" + currentpsnid + "' and FOPVIEWID='" + this.opviewID + "' and FTASKID = '"
					+ this.taskID + "'";
			List list = DBUtils.execQueryforList(this.dbkey, sql);
			conn = DBUtils.getAppConn(this.dbkey);
			if (list.size() > 0) {
				Map m = (Map) list.get(0);
				sql = "update " + this.audittable + " set " + this.agreettextRe + "='" + this.opinion
						+ "', FCREATETIME = ?,FOPVIEWID='" + this.opviewID + "' where fID='" + m.get("FID") + "'";
				PreparedStatement ps = conn.prepareStatement(sql);
				ps.setTimestamp(1, new Timestamp(new Date().getTime()));
				ps.executeUpdate();
			} else {
				String opid = IDUtils.getGUID();
				if (this.opviewID.equals("chuanyue")) {
					sql = "insert into " + this.audittable + "(FID,FTASKID,FFLOWID," + this.agreettextRe + ","
							+ this.billidRe + ",FCREATEDEPTID,FCREATEDEPTNAME,FCREATEPERID,FCREATEPERNAME,FCREATETIME"
							+ ",FNODEID,FNODENAME,FOPVIEWID,version)values('" + opid + "','" + this.taskID + "','"
							+ this.flowid + "','" + this.opinion + "','" + this.sdata1 + "','"
							+ context.getCurrentDeptID() + "','" + context.getCurrentDeptName() + "','"
							+ context.getCurrentPersonID() + "','" + context.getCurrentPersonName() + "',?,'" + FNODEID
							+ "','" + FNODENAME + "','" + this.opviewID + "',0)";
				} else {
					sql = "insert into " + this.audittable + "(FID,FTASKID,FFLOWID," + this.agreettextRe + ","
							+ this.billidRe + ",FCREATEDEPTID,FCREATEDEPTNAME,FCREATEPERID,FCREATEPERNAME,FCREATETIME"
							+ ",FNODEID,FNODENAME,FOPVIEWID,version)values('" + opid + "','" + this.taskID + "','"
							+ this.flowid + "','" + this.opinion + "','" + this.sdata1 + "','"
							+ context.getCurrentDeptID() + "','" + context.getCurrentDeptName() + "','" + currentpsnid
							+ "','" + currentpsnname + "',?,'" + FNODEID + "','" + FNODENAME + "','" + this.opviewID
							+ "',0)";
				}
				PreparedStatement ps = conn.prepareStatement(sql);
				ps.setTimestamp(1, new Timestamp(new Date().getTime()));
				ps.executeUpdate();
			}
			this.data.setFlag("true");
		} catch (Exception e) {
			this.data.setFlag("false");
			this.data.setMessage(e.toString());
			e.printStackTrace();
		} finally {
			DBUtils.CloseConn(conn, null, null);
		}
		return this;
	}

	public String getDbkey() {
		return this.dbkey;
	}

	public void setDbkey(String dbkey) {
		this.dbkey = dbkey;
	}

	public void setAudittable(String audittable) {
		this.audittable = audittable;
	}

	public String getAudittable() {
		return this.audittable;
	}

	public void setOpinion(String opinion) {
		try {
			this.opinion = URLDecoder.decode(opinion, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
	}

	public String getOpinion() {
		return this.opinion;
	}

	public void setFlowid(String flowid) {
		this.flowid = flowid;
	}

	public String getFlowid() {
		return this.flowid;
	}

	public void setBillidRe(String billidRe) {
		this.billidRe = billidRe;
	}

	public String getBillidRe() {
		return this.billidRe;
	}

	public void setAgreettextRe(String agreettextRe) {
		this.agreettextRe = agreettextRe;
	}

	public String getAgreettextRe() {
		return this.agreettextRe;
	}

	public void setTaskID(String taskID) {
		this.taskID = taskID;
	}

	public String getTaskID() {
		return this.taskID;
	}

	public void setOpviewID(String opviewID) {
		try {
			this.opviewID = URLDecoder.decode(opviewID, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
	}

	public String getOpviewID() {
		return this.opviewID;
	}

	public void setSdata1(String sdata1) {
		this.sdata1 = sdata1;
	}

	public String getSdata1() {
		return sdata1;
	}
}