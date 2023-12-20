package com.tlv8.flw;

import java.util.Map;
import java.util.UUID;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tlv8.base.Data;
import com.tlv8.base.db.DBUtils;
import com.tlv8.common.domain.AjaxResult;
import com.alibaba.fastjson.JSON;
import com.tlv8.base.ActionSupport;
import com.tlv8.flw.base.FlowActivity;
import com.tlv8.flw.base.TaskData;
import com.tlv8.system.bean.ContextBean;

/**
 * @author ChenQian
 */
@Controller
@Scope("prototype")
public class TaskControler extends ActionSupport {
	private String flowID;
	private String taskID;
	private String executor;
	private Data data;

	public Data getData() {
		return data;
	}

	/**
	 * 打开任务
	 */
	@ResponseBody
	@RequestMapping("/openTaskAction")
	public Object openTask() throws Exception {
		data = new Data();
		try {
			if (executor == null || "".equals(executor) || "undefined".equals(executor)) {
				executor = ContextBean.getContext(request).getCurrentPersonID();
			}
			Map<String, String> m = TaskData.getTaskInfor(taskID, executor);
			if (m != null) {
				flowID = m.get("flowID");
				data.setFlag("true");
				data.setData(JSON.toJSONString(m));
				String sql = "update SA_TASK set SLOCK='"
						+ UUID.randomUUID().toString().toUpperCase().replaceAll("-", "") + "' where SID = '" + taskID
						+ "' and SLOCK is null";
				DBUtils.execUpdateQuery("system", sql);
				String notesql = "update SA_TASK set SSTATUSID = 'tesFinished'" + ",SSTATUSNAME = '已完成' where SID = '"
						+ taskID + "' and (SCATALOGID = 'tsTask' or SCATALOGID = 'tkTask')";
				DBUtils.execUpdateQuery("system", notesql);
				String activity = m.get("activity");
				String processID = m.get("process");
				FlowActivity Act = new FlowActivity(processID, activity);
				if ("whenOpen".equals(Act.getGrapModle())) {
					DBUtils.execUpdateQuery("system",
							"update SA_TASK set SSTATUSID='tesCanceled' ,SSTATUSNAME='已取消' where SID != '" + taskID
									+ "' and SACTIVITY ='" + activity + "' and SFLOWID ='" + flowID
									+ "' and SSTATUSID = 'tesReady'");
				}
			} else {
				data.setFlag("false");
				data.setMessage("ID为'" + taskID + "'的任务不存在或您没有权限处理!");
			}
		} catch (Exception e) {
			data.setFlag("false");
			data.setMessage(e.toString());
		}
		return AjaxResult.success(data);
	}

	public String getFlowID() {
		return flowID;
	}

	public void setFlowID(String flowID) {
		this.flowID = flowID;
	}

	public String getTaskID() {
		return taskID;
	}

	public void setTaskID(String taskID) {
		this.taskID = taskID;
	}

	public String getExecutor() {
		return executor;
	}

	public void setExecutor(String executor) {
		this.executor = executor;
	}

	public void setData(Data data) {
		this.data = data;
	}

}
