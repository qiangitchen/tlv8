package com.tlv8.flw.bean;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.JSON;
import com.tlv8.base.db.DBUtils;

@SuppressWarnings({ "rawtypes", "unchecked" })
public class FlowBotXBean {
	private String id;
	private List<String> ahead;
	private List<String> next;
	private String name;
	private String executor;
	private String executordepartment;
	private String status;
	private String createtime;
	private String executetime;
	private List redata;

	public FlowBotXBean() {
	}

	public FlowBotXBean(String flowID) throws Exception {
		String sql = null;
		if (DBUtils.IsOracleDB("system") || DBUtils.IsDMDB("system")) {
			sql = "select SID,SPARENTID,SNAME,SEPERSONNAME,SEDEPTNAME,SEOGNNAME,SSTATUSNAME,"
					+ "to_char(SCREATETIME,'yyyy-MM-dd hh24:mi:ss')SCREATETIME,"
					+ "to_char(SEXECUTETIME,'yyyy-MM-dd hh24:mi:ss')SEXECUTETIME,"
					+ "SACTIVITY,SCPERSONNAME from SA_TASK "
					+ "where SSTATUSID != 'tesExecuting' and SACTIVITY !=' ' and SFLOWID='" + flowID
					+ "' order by SCREATETIME";
		} else {
			sql = "select SID,SPARENTID,SNAME,SEPERSONNAME,SEDEPTNAME,SEOGNNAME,SSTATUSNAME," + "SCREATETIME,"
					+ "SEXECUTETIME," + "SACTIVITY,SCPERSONNAME from SA_TASK "
					+ "where SSTATUSID != 'tesExecuting' and SACTIVITY !=' ' and SFLOWID='" + flowID
					+ "' order by SCREATETIME";
		}
		List<Map<String, String>> rslist = DBUtils.execQueryforList("system", sql);
		if (rslist.size() > 0) {
			redata = new ArrayList<FlowBotXBean>();
			for (int i = 0; i < rslist.size(); i++) {
				Map m = rslist.get(i);
				String taskID = m.get("SID").toString();
				if (flowID.equals(taskID)) {
					continue;
				}
				FlowBotXBean botbean = new FlowBotXBean();
				botbean.setId(taskID);
				botbean.setName((String) m.get("SNAME"));
				List<String> ahead = new ArrayList<String>();
				String pId = m.get("SPARENTID").toString();
				if (!flowID.equals(pId)) {
					ahead.add(pId);
				}
				botbean.setAhead(ahead);
				botbean.setExecutor((String) m.get("SEPERSONNAME"));
				botbean.setExecutordepartment(
						((String) m.get("SEDEPTNAME") == null || "null".equals((String) m.get("SEDEPTNAME"))
								|| "".equals((String) m.get("SEDEPTNAME"))) ? (String) m.get("SEOGNNAME")
										: (String) m.get("SEDEPTNAME") == null ? "" : (String) m.get("SEDEPTNAME"));
				botbean.setStatus((String) m.get("SSTATUSNAME"));
				botbean.setCreatetime((String) m.get("SCREATETIME"));
				botbean.setExecutetime((String) m.get("SEXECUTETIME"));
				sql = "select SID from SA_TASK where SPARENTID = '" + m.get("SID").toString() + "' and SFLOWID='"
						+ flowID + "'";
				List<Map<String, String>> ns = DBUtils.execQueryforList("system", sql);
				List<String> anext = new ArrayList<String>();
				for (int j = 0; j < ns.size(); j++) {
					anext.add(ns.get(j).get("SID").toString());
				}
				botbean.setNext(anext);
				redata.add(botbean);
			}
		} else {
			throw new Exception("未找到'" + flowID + "'对应的任务信息，无法加载波特图.");
		}
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public List<String> getAhead() {
		return ahead;
	}

	public void setAhead(List<String> ahead) {
		this.ahead = ahead;
	}

	public List<String> getNext() {
		return next;
	}

	public void setNext(List<String> next) {
		this.next = next;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getExecutor() {
		return executor;
	}

	public void setExecutor(String executor) {
		this.executor = executor;
	}

	public String getExecutordepartment() {
		return executordepartment;
	}

	public void setExecutordepartment(String executordepartment) {
		this.executordepartment = executordepartment;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getCreatetime() {
		return createtime;
	}

	public void setCreatetime(String createtime) {
		this.createtime = createtime;
	}

	public String getExecutetime() {
		return executetime;
	}

	public void setExecutetime(String executetime) {
		this.executetime = executetime;
	}

	public String toJSONStr() {
		return JSON.toJSONString(redata);
	}

	public void setRedata(List redata) {
		this.redata = redata;
	}

	public List getRedata() {
		return redata;
	}
}
