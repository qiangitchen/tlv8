package com.tlv8.flw.task;

import java.util.List;
import java.util.Map;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tlv8.base.Data;
import com.tlv8.base.db.DBUtils;
import com.tlv8.base.ActionSupport;

/**
 * @author ChenQian
 * @since 撤回任务
 */
@Controller
@Scope("prototype")
@SuppressWarnings({ "rawtypes" })
public class RecycleTaskAction extends ActionSupport {
	private String taskID;
	Data data = new Data();

	public void setData(Data data) {
		this.data = data;
	}

	public Data getData() {
		return data;
	}

	@ResponseBody
	@RequestMapping("/RecycleTaskAction")
	public Object execute() throws Exception {
		if (taskID == null || "".equals(taskID)) {
			data.setMessage("操作失败，taskID不能为空!");
			data.setFlag("false");
		} else {
			/* 兼容X5 */
			String chSQL = "select SPROCESS,SCURL,SFLOWID from SA_TASK where SID = '" + taskID + "'";
			List li = DBUtils.execQueryforList("system", chSQL);
			if (li.size() > 0) {
				Map mr = (Map) li.get(0);
				String SPROCESS = (String) mr.get("SPROCESS");
				String SCURL = (String) mr.get("SCURL");
				String SFLOWID = (String) mr.get("SFLOWID");
				if (SPROCESS.indexOf("/") > -1) {
					if (SCURL != null && !"".equals(SCURL)) {
						String desql = "delete from sa_task where SFLOWID = '" + SFLOWID
								+ "' and SSTATUSID = 'tesReady' and SID !='" + taskID + "'";
						String reCysql = "update SA_Task set SSTATUSID = 'tesReady',SSTATUSNAME = '尚未处理' "
								+ " where SID  = '" + taskID + "'";
						try {
							DBUtils.execdeleteQuery("system", desql);
							DBUtils.execUpdateQuery("system", reCysql);
						} catch (Exception e) {
							data.setMessage("操作失败，详细:" + e.toString());
							data.setFlag("false");
						}
					} else {
						data.setMessage("操作失败，撤回后没有可执行的页面。");
						data.setFlag("false");
					}
					return this;
				}
			}
			/* end */

			String sql = "select SFLOWID,SCURL,SEURL,SLOCK,SPROCESS from SA_TASK where SPARENTID='" + taskID + "'";
			try {
				List<Map<String, String>> list = DBUtils.execQueryforList("system", sql);
				if (list.size() > 0) {
					Map m = list.get(0);
					String SCURL = (String) m.get("SCURL");
					String SLOCK = (String) m.get("SLOCK");
					if (SLOCK == null || "".equals(SLOCK) || "null".equals(SLOCK)) {
						if (SCURL != null && !"".equals(SCURL)) {
							String reCysql = "update SA_Task set SSTATUSID = 'tesReady',SSTATUSNAME = '尚未处理' "
									+ " where SID  = '" + taskID + "'";
							String desql = "delete from sa_task where SPARENTID = '" + taskID + "'";
							try {
								DBUtils.execUpdateQuery("system", reCysql);
								DBUtils.execdeleteQuery("system", desql);
							} catch (Exception e) {
								data.setMessage("操作失败，详细:" + e.toString());
								data.setFlag("false");
							}
						} else {
							data.setMessage("操作失败，撤回后没有可执行的页面。");
							data.setFlag("false");
						}
					} else {
						data.setMessage("操作失败，执行人正在处理任务不能撤回。");
						data.setFlag("false");
					}
				} else {
					if (li.size() > 0) {// 撤回多人任务 未流转但是已提交的任务
						String reCysql = "update SA_Task set SSTATUSID = 'tesReady',SSTATUSNAME = '尚未处理' "
								+ " where SID  = '" + taskID + "'";
						DBUtils.execUpdateQuery("system", reCysql);
					} else {
						data.setMessage("操作失败，taskID无效!" + taskID);
						data.setFlag("false");
					}
				}
			} catch (Exception e) {
				data.setMessage("操作失败，详细:" + e.toString());
				data.setFlag("false");
			}
		}
		return this;
	}

	public void setTaskID(String taskID) {
		this.taskID = taskID;
	}

	public String getTaskID() {
		return taskID;
	}

}
