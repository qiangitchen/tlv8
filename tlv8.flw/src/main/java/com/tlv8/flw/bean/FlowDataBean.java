package com.tlv8.flw.bean;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import com.alibaba.fastjson.JSONObject;
import com.tlv8.flw.helper.FlowBaseHelper;

/**
 * @author ChenQian
 */
@SuppressWarnings({ "unchecked", "rawtypes" })
public class FlowDataBean extends FlowBaseHelper {
	protected String processID;// 流程标识
	protected String processName;// 流程名称
	protected String flowID;// 当前流程标识
	protected String taskID;// 任务标识
	protected String taskName;// 任务名称
	protected String billid; // 单据ID
	protected String sdata1; // 单据ID
	protected String curl;// 提交功能路径
	protected String eurl;// 执行功能路径
	protected String status;// 处理状态
	protected String cPersonID;// 提交人ID
	protected String cPersonName;// 提交人名称
	protected String cPersonFID;// 提交人全ID
	protected String cPersonFName;// 提交人全名称
	protected String ePersonID;// 执行人ID
	protected String ePersonName;// 执行人名称
	protected String ePersonFID;// 执行人全ID
	protected String ePersonFName;// 执行人全名称
	protected String epersonids;// 执行人
	protected String currentUrl;// 当前页面

	public void setProcessID(String processID) {
		try {
			this.processID = URLDecoder.decode(processID, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
	}

	public String getProcessID() {
		return processID;
	}

	public void setFlowID(String flowID) {
		this.flowID = flowID;
	}

	public String getFlowID() {
		return flowID;
	}

	public void setProcessName(String processName) {
		try {
			this.processName = URLDecoder.decode(processName, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
	}

	public String getProcessName() {
		return processName;
	}

	public void setTaskID(String taskID) {
		this.taskID = taskID;
	}

	public String getTaskID() {
		return taskID;
	}

	public void setTaskName(String taskName) {
		try {
			this.taskName = URLDecoder.decode(taskName, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
	}

	public String getTaskName() {
		return taskName;
	}

	public void setCurl(String curl) {
		try {
			this.curl = URLDecoder.decode(curl, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
	}

	public String getCurl() {
		return curl;
	}

	public void setEurl(String eurl) {
		try {
			this.eurl = URLDecoder.decode(eurl, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
	}

	public String getEurl() {
		return eurl;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getStatus() {
		return status;
	}

	public void setcPersonID(String cPersonID) {
		this.cPersonID = cPersonID;
	}

	public String getcPersonID() {
		return cPersonID;
	}

	public void setcPersonName(String cPersonName) {
		try {
			this.cPersonName = URLDecoder.decode(cPersonName, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
	}

	public String getcPersonName() {
		return cPersonName;
	}

	public void setcPersonFID(String cPersonFID) {
		try {
			this.cPersonFID = URLDecoder.decode(cPersonFID, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
	}

	public String getcPersonFID() {
		return cPersonFID;
	}

	public void setcPersonFName(String cPersonFName) {
		try {
			this.cPersonFName = URLDecoder.decode(cPersonFName, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
	}

	public String getcPersonFName() {
		return cPersonFName;
	}

	public void setePersonID(String ePersonID) {
		this.ePersonID = ePersonID;
	}

	public String getePersonID() {
		return ePersonID;
	}

	public void setePersonName(String ePersonName) {
		try {
			this.ePersonName = URLDecoder.decode(ePersonName, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
	}

	public String getePersonName() {
		return ePersonName;
	}

	public void setePersonFID(String ePersonFID) {
		try {
			this.ePersonFID = URLDecoder.decode(ePersonFID, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
	}

	public String getePersonFID() {
		return ePersonFID;
	}

	public void setePersonFName(String ePersonFName) {
		try {
			this.ePersonFName = URLDecoder.decode(ePersonFName, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
	}

	public String getePersonFName() {
		return ePersonFName;
	}

	public void setBillid(String billid) {
		this.billid = billid;
	}

	public String getBillid() {
		return billid;
	}

	public String getSdata1() {
		return sdata1;
	}

	public void setSdata1(String sdata1) {
		this.sdata1 = sdata1;
	}

	public String getEpersonids() {
		return epersonids;
	}

	public void setEpersonids(String epersonids) {
		try {
			this.epersonids = URLDecoder.decode(epersonids, "UTF-8");
		} catch (Exception e) {
		}
	}

	public String toJSONString() {
		JSONObject json = new JSONObject();
		json.put("processID", this.processID);// 流程标识
		json.put("processName", processName);// 流程名称
		json.put("flowID", flowID);// 当前流程标识
		json.put("taskID", taskID);// 任务标识
		json.put("taskName", taskName);// 任务名称
		json.put("billid", billid); // 单据ID
		json.put("sData1", sdata1); // 单据ID
		json.put("cURL", curl);// 提交功能路径
		json.put("eURL", eurl);// 执行功能路径
		json.put("status", status);// 处理状态
		json.put("cPersonID", cPersonID);// 提交人ID
		json.put("cPersonName", cPersonName);// 提交人名称
		json.put("cPersonFID", cPersonFID);// 提交人全ID
		json.put("cPersonFName", cPersonFName);// 提交人全名称
		json.put("ePersonID", ePersonID);// 执行人ID
		json.put("ePersonName", ePersonName);// 执行人名称
		json.put("ePersonFID", ePersonFID);// 执行人全ID
		json.put("ePersonFName", ePersonFName);// 执行人全名称
		json.put("epersonids", epersonids);// 执行人
		json.put("date", new SimpleDateFormat("yyyy-MM-dd").format(new Date()));
		return json.toString();
	}

	public Map toMap() {
		Map map = new HashMap();
		map.put("processID", this.processID);// 流程标识
		map.put("processName", processName);// 流程名称
		map.put("flowID", flowID);// 当前流程标识
		map.put("taskID", taskID);// 任务标识
		map.put("taskName", taskName);// 任务名称
		map.put("billid", billid); // 单据ID
		map.put("sData1", sdata1); // 单据ID
		map.put("cURL", curl);// 提交功能路径
		map.put("eURL", eurl);// 执行功能路径
		map.put("status", status);// 处理状态
		map.put("cPersonID", cPersonID);// 提交人ID
		map.put("cPersonName", cPersonName);// 提交人名称
		map.put("cPersonFID", cPersonFID);// 提交人全ID
		map.put("cPersonFName", cPersonFName);// 提交人全名称
		map.put("ePersonID", ePersonID);// 执行人ID
		map.put("ePersonName", ePersonName);// 执行人名称
		map.put("ePersonFID", ePersonFID);// 执行人全ID
		map.put("ePersonFName", ePersonFName);// 执行人全名称
		map.put("epersonids", epersonids);// 执行人
		map.put("date", new SimpleDateFormat("yyyy-MM-dd").format(new Date()));
		return map;
	}

	public String getCurrentUrl() {
		return currentUrl;
	}

	public void setCurrentUrl(String currentUrl) {
		try {
			this.currentUrl = URLDecoder.decode(currentUrl, "UTF-8");
		} catch (Exception e) {
		}
	}

}
