package com.tlv8.flw.bean;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.JSON;
import com.tlv8.base.Sys;
import com.tlv8.base.db.DBUtils;

@SuppressWarnings({ "unchecked", "rawtypes" })
public class FlowBotBean {
	protected String id;// 标题
	protected String title;// 标题
	protected String executor;// 执行人
	protected String excutordpt;// 执行人部门
	protected String status;// 状态
	protected String creator;// 提交人
	protected String createTime;// 提交时间
	protected String auditeTime;// 处理时间
	protected String activity;// 环节标识
	private List botdata = new ArrayList();

	public FlowBotBean(String flowID) throws Exception {
		String sql = null;
		if (DBUtils.IsOracleDB("system") || DBUtils.IsDMDB("system") || DBUtils.IsPostgreSQL("system")) {
			sql = "select SID,SNAME,SEPERSONNAME,SEDEPTNAME,SEOGNNAME,SSTATUSNAME,"
					+ "to_char(SCREATETIME,'yyyy-MM-dd hh24:mi:ss')SCREATETIME,"
					+ "to_char(SEXECUTETIME,'yyyy-MM-dd hh24:mi:ss')SEXECUTETIME,"
					+ "SACTIVITY,SCPERSONNAME from SA_TASK "
					+ "where SSTATUSID != 'tesExecuting' and SACTIVITY !=' ' and SFLOWID='" + flowID
					+ "' order by SCREATETIME";
		} else {
			sql = "select SID,SNAME,SEPERSONNAME,SEDEPTNAME,SEOGNNAME,SSTATUSNAME," + "SCREATETIME," + "SEXECUTETIME,"
					+ "SACTIVITY,SCPERSONNAME from SA_TASK "
					+ "where SSTATUSID != 'tesExecuting' and SACTIVITY !=' ' and SFLOWID='" + flowID
					+ "' order by SCREATETIME";
		}
		// Sys.printMsg(sql);
		List<Map<String, String>> rslist = DBUtils.execQueryforList("system", sql);
		if (rslist.size() > 0) {
			botdata = new ArrayList<FlowBotBean>();
			for (int i = 0; i < rslist.size(); i++) {
				Map m = rslist.get(i);
				FlowBotBean botbean = new FlowBotBean();
				botbean.setId((String) m.get("SID"));
				botbean.setTitle((String) m.get("SNAME"));
				botbean.setExecutor((String) m.get("SEPERSONNAME"));
				botbean.setExcutordpt(
						((String) m.get("SEDEPTNAME") == null || "null".equals((String) m.get("SEDEPTNAME"))
								|| "".equals((String) m.get("SEDEPTNAME"))) ? (String) m.get("SEOGNNAME")
										: (String) m.get("SEDEPTNAME") == null ? "" : (String) m.get("SEDEPTNAME"));
				botbean.setStatus((String) m.get("SSTATUSNAME"));
				botbean.setCreateTime((String) m.get("SCREATETIME"));
				botbean.setAuditeTime((String) m.get("SEXECUTETIME"));
				botbean.setActivity((String) m.get("SACTIVITY"));
				botbean.setCreator((String) m.get("SCPERSONNAME"));
				botdata.add(JSON.toJSONString(botbean));
			}
		} else {
			throw new Exception("未找到'" + flowID + "'对应的任务信息，无法加载波特图.");
		}
	}

	public FlowBotBean() {

	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getExecutor() {
		return executor;
	}

	public void setExecutor(String executor) {
		this.executor = executor;
	}

	public String getExcutordpt() {
		return excutordpt;
	}

	public void setExcutordpt(String excutordpt) {
		this.excutordpt = (excutordpt == null || "null".equals(excutordpt)) ? "" : excutordpt;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime == null ? "" : createTime;
	}

	public String getAuditeTime() {
		return auditeTime;
	}

	public void setAuditeTime(String auditeTime) {
		this.auditeTime = auditeTime == null ? "" : auditeTime;
	}

	public void setBotdata(List botdata) {
		this.botdata = botdata;
	}

	public List getBotdata() {
		return botdata;
	}

	public String toJsonStr() throws Exception {
		return JSON.toJSONString(botdata);
	}

	public static void main(String[] args) {
		FlowBotBean bean = new FlowBotBean();
		try {
			bean = new FlowBotBean("2CF12220117F4F80BC8B28D569FE1F00");
			Sys.printMsg(bean.toJsonStr());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public String getActivity() {
		return activity;
	}

	public void setActivity(String activity) {
		this.activity = activity;
	}

	public String getCreator() {
		return creator;
	}

	public void setCreator(String creator) {
		this.creator = creator == null ? "" : creator;
	}

}
