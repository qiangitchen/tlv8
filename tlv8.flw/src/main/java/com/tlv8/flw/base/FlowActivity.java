package com.tlv8.flw.base;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.tlv8.base.CodeUtils;
import com.tlv8.flw.expression.BooleanExpression;

@SuppressWarnings({ "unchecked", "rawtypes" })
public class FlowActivity {
	/*
	 * 流程环节处理
	 */
	private JSONArray sActivityList;
	private JSONArray LinesList;
	private JSONObject sActivity;
	private String sActivityLabel;
	private String processID;
	private String processName;
	private String processActy;
	private String id;
	private String type;
	private String activity;
	private String activityname;
	private JSONArray property;
	private String url;
	private String urlname;
	private String excutorGroup;
	private String excutorIDs;
	private String excutorNames;
	private String grapModle;
	private String grapWay;
	private String conditionValue;
	private String trueOutValue;
	private String falseOutValue;
	private String backActivity;
	private String transeRole;
	private String outquery;

	/*
	 * 构造方法
	 * 
	 * @ActivityID type均可
	 */
	public FlowActivity(String ProcessID, String ActivityID) {
		Map actMap = FlowFile.getFlowDraw(ProcessID);
		if (actMap.isEmpty()) {
			new Exception("未找到ProcessID='" + ProcessID + "' 对应的流程信息!").printStackTrace();
		}
		processID = ProcessID;
		processName = (String) actMap.get("SPROCESSNAME");
		processActy = (String) actMap.get("SPROCESSACTY");
		String sprocessacty = null;
		try {
			JSONObject jsonObj = JSON.parseObject(processActy);
			sprocessacty = jsonObj.getString("nodes");
			JSONArray ActivityArray = JSON.parseArray(sprocessacty);
			sActivityList = ActivityArray;
			for (int i = 0; i < ActivityArray.size(); i++) {
				JSONObject Acjson = (JSONObject) ActivityArray.get(i);
				if (ActivityID.equals(Acjson.getString("id")) || ActivityID.equals(Acjson.getString("type"))) {
					sActivity = Acjson;
					id = Acjson.getString("id");
					type = Acjson.getString("type");
					activity = Acjson.getString("id");
					activityname = Acjson.getString("name");
					urlname = Acjson.getString("name");
					try {
						String pstr = Acjson.getString("property");
						if (pstr != null && !"".equals(pstr) && !"null".equals(pstr)) {
							property = JSON.parseArray(Acjson.getString("property"));
							for (int j = 0; j < property.size(); j++) {
								JSONObject propJson = (JSONObject) property.get(j);
								if ("n_p_exepage".equals(propJson.getString("id"))) {
									url = propJson.getString("value");
								}
								if ("n_p_label".equals(propJson.getString("id"))) {
									sActivityLabel = propJson.getString("value");
								}
								if ("n_p_group".equals(propJson.getString("id"))) {
									excutorGroup = propJson.getString("value");
								}
								if ("n_p_roleID".equals(propJson.getString("id"))) {
									excutorIDs = propJson.getString("value");
								}
								if ("n_p_role".equals(propJson.getString("id"))) {
									excutorNames = propJson.getString("value");
								}
								if ("n_r_grab".equals(propJson.getString("id"))) {
									grapModle = propJson.getString("value");
								}
								if ("n_r_grabway".equals(propJson.getString("id"))) {
									grapWay = propJson.getString("value");
								}
								if ("c_p_expression".equals(propJson.getString("id"))) {
									conditionValue = propJson.getString("value");
								}
								if ("c_p_trueOut".equals(propJson.getString("id"))) {
									trueOutValue = propJson.getString("value");
								}
								if ("c_p_falseOut".equals(propJson.getString("id"))) {
									falseOutValue = propJson.getString("value");
								}
								if ("n_p_back".equals(propJson.getString("id"))) {
									backActivity = propJson.getString("value");
								}
								if ("n_r_transe".equals(propJson.getString("id"))) {
									transeRole = propJson.getString("value");
								}
								if ("n_t_queryt".equals(propJson.getString("id"))) {
									outquery = propJson.getString("value");
								}
							}
						}
					} catch (Exception e) {
						e.printStackTrace();
					}
					break;
				}

			}
			LinesList = JSON.parseArray(jsonObj.getString("lines"));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public FlowActivity(Map actMap, String ProcessID, String ActivityID) {
		processID = ProcessID;
		processName = (String) actMap.get("SPROCESSNAME");
		processActy = (String) actMap.get("SPROCESSACTY");
		String sprocessacty = null;
		try {
			JSONObject jsonObj = JSON.parseObject(processActy);
			sprocessacty = jsonObj.getString("nodes");
			JSONArray ActivityArray = JSON.parseArray(sprocessacty);
			sActivityList = ActivityArray;
			for (int i = 0; i < ActivityArray.size(); i++) {
				JSONObject Acjson = (JSONObject) ActivityArray.get(i);
				if (ActivityID.equals(Acjson.getString("id")) || ActivityID.equals(Acjson.getString("type"))) {
					sActivity = Acjson;
					id = Acjson.getString("id");
					type = Acjson.getString("type");
					activity = Acjson.getString("id");
					activityname = Acjson.getString("name");
					urlname = Acjson.getString("name");
					try {
						String pstr = Acjson.getString("property");
						if (pstr != null && !"".equals(pstr) && !"null".equals(pstr)) {
							property = JSON.parseArray(Acjson.getString("property"));
							for (int j = 0; j < property.size(); j++) {
								JSONObject propJson = (JSONObject) property.get(j);
								if ("n_p_exepage".equals(propJson.getString("id"))) {
									url = propJson.getString("value");
								}
								if ("n_p_label".equals(propJson.getString("id"))) {
									sActivityLabel = propJson.getString("value");
								}
								if ("n_p_group".equals(propJson.getString("id"))) {
									excutorGroup = propJson.getString("value");
								}
								if ("n_p_roleID".equals(propJson.getString("id"))) {
									excutorIDs = propJson.getString("value");
								}
								if ("n_p_role".equals(propJson.getString("id"))) {
									excutorNames = propJson.getString("value");
								}
								if ("n_r_grab".equals(propJson.getString("id"))) {
									grapModle = propJson.getString("value");
								}
								if ("n_r_grabway".equals(propJson.getString("id"))) {
									grapWay = propJson.getString("value");
								}
								if ("c_p_expression".equals(propJson.getString("id"))) {
									conditionValue = propJson.getString("value");
								}
								if ("c_p_trueOut".equals(propJson.getString("id"))) {
									trueOutValue = propJson.getString("value");
								}
								if ("c_p_falseOut".equals(propJson.getString("id"))) {
									falseOutValue = propJson.getString("value");
								}
								if ("n_r_transe".equals(propJson.getString("id"))) {
									transeRole = propJson.getString("value");
								}
								if ("n_t_queryt".equals(propJson.getString("id"))) {
									outquery = propJson.getString("value");
								}
							}
						}
					} catch (Exception e) {
						e.printStackTrace();
					}
					break;
				}

			}
			LinesList = JSON.parseArray(jsonObj.getString("lines"));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public JSONObject toJson() {
		Map m = new HashMap<String, String>();
		m.put("id", this.id);
		m.put("type", this.type);
		m.put("processID", this.processID);
		m.put("processName", this.processName);
		m.put("processActy", this.processActy);
		m.put("activity", this.activity);
		m.put("activityname", this.activityname);
		m.put("property", this.property);
		m.put("url", this.url);
		m.put("urlname", this.urlname);
		m.put("excutorGroup", this.excutorGroup);
		m.put("excutorIDs", this.excutorIDs);
		m.put("excutorNames", this.excutorNames);
		m.put("grapModle", this.grapModle);
		m.put("grapWay", this.grapWay);
		m.put("transeRole", this.transeRole);
		m.put("outquery", this.outquery);
		return new JSONObject(m);
	}

	/*
	 * 获取前序环节
	 */
	public List<FlowActivity> getBeforeActivity() {
		List list = new ArrayList();
		try {
			for (int i = 0; i < LinesList.size(); i++) {
				JSONObject LineJson = (JSONObject) LinesList.get(i);
				if (this.id.equals(LineJson.getString("to"))) {
					FlowActivity bfActivity = new FlowActivity(this.processID, LineJson.getString("from"));
					if ("condition".equals(bfActivity.getType())) {
						list = bfActivity.getBeforeActivity();
					} else {
						list.add(bfActivity);
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	/*
	 * 获取后续环节
	 */
	public List<FlowActivity> getAfterActivity(HttpServletRequest request) {
		List list = new ArrayList();
		try {
			for (int i = 0; i < LinesList.size(); i++) {
				JSONObject LineJson = (JSONObject) LinesList.get(i);
				if (LineJson.getString("from").equals(id)) {
					FlowActivity afActivity = new FlowActivity(this.processID, LineJson.getString("to"));
					while ("condition".equals(afActivity.getType())) {
						String express = CodeUtils.decodeSpechars(afActivity.getConditionValue());
						boolean direct = BooleanExpression
								.verdict((String) BooleanExpression.resolutionExpression(express, request));
						FlowActivity afDirectActivity = null;
						if (direct) {
							afDirectActivity = new FlowActivity(this.processID, afActivity.getTrueOutValue());
						} else {
							afDirectActivity = new FlowActivity(this.processID, afActivity.getFalseOutValue());
						}
						afActivity = afDirectActivity;
					}
					list.add(afActivity);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	/*
	 * 获取后续环节
	 */
	public List<FlowActivity> getAfterActivity(String flowID, String taskID, String sData1,
			HttpServletRequest request) {
		List list = new ArrayList();
		try {
			for (int i = 0; i < LinesList.size(); i++) {
				JSONObject LineJson = (JSONObject) LinesList.get(i);
				if (LineJson.getString("from").equals(id)) {
					FlowActivity afActivity = new FlowActivity(this.processID, LineJson.getString("to"));
					while ("condition".equals(afActivity.getType())) {
						String express = CodeUtils.decodeSpechars(afActivity.getConditionValue());
						if (express != null) {
							express = express.replaceAll("getProcessID\\(\\)", flowID);
							express = express.replaceAll("getTaskID\\(\\)", taskID);
							express = express.replaceAll("getProcesssData1\\(\\)", sData1);
						}
						boolean direct = BooleanExpression
								.verdict((String) BooleanExpression.resolutionExpression(express, request));
						FlowActivity afDirectActivity = null;
						if (direct) {
							afDirectActivity = new FlowActivity(this.processID, afActivity.getTrueOutValue());
						} else {
							afDirectActivity = new FlowActivity(this.processID, afActivity.getFalseOutValue());
						}
						afActivity = afDirectActivity;
					}
					list.add(afActivity);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	public void setsActivityList(JSONArray sactivityList) {
		sActivityList = sactivityList;
	}

	public JSONArray getsActivityList() {
		return sActivityList;
	}

	public void setActivity(JSONObject activity) {
		sActivity = activity;
	}

	public JSONObject getsActivity() {
		return sActivity;
	}

	public void setActivity(String activity) {
		this.activity = activity;
	}

	public String getActivity() {
		return activity;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getActivityname() {
		return activityname;
	}

	public void setActivityname(String activityname) {
		this.activityname = activityname;
	}

	public void setProcessName(String processName) {
		this.processName = processName;
	}

	public String getProcessName() {
		return processName;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getUrlname() {
		return urlname;
	}

	public void setUrlname(String urlname) {
		this.urlname = urlname;
	}

	public void setProperty(JSONArray property) {
		this.property = property;
	}

	public JSONArray getProperty() {
		return property;
	}

	public void setProcessActy(String processActy) {
		this.processActy = processActy;
	}

	public String getProcessActy() {
		return processActy;
	}

	public void setLinesList(JSONArray linesList) {
		LinesList = linesList;
	}

	public JSONArray getLinesList() {
		return LinesList;
	}

	public void setProcessID(String processID) {
		this.processID = processID;
	}

	public String getProcessID() {
		return processID;
	}

	public void setExcutorGroup(String excutorGroup) {
		this.excutorGroup = excutorGroup;
	}

	public String getExcutorGroup() {
		return excutorGroup;
	}

	public void setExcutorIDs(String excutorIDs) {
		this.excutorIDs = excutorIDs;
	}

	public String getExcutorIDs() {
		JSONObject json = TaskData.getExcutor(this.getUrl());
		if (json != null) {
			try {
				return json.getString("excutorIDs");
			} catch (Exception e) {
			}
		}
		return excutorIDs;
	}

	public String getExcutorIDs(String o) {
		return this.excutorIDs;
	}

	public void setExcutorNames(String excutorNames) {
		this.excutorNames = excutorNames;
	}

	public String getExcutorNames() {
		JSONObject json = TaskData.getExcutor(this.getUrl());
		if (json != null) {
			try {
				return json.getString("excutorNames");
			} catch (Exception e) {
			}
		}
		return excutorNames;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getType() {
		return type;
	}

	public void setConditionValue(String conditionValue) {
		this.conditionValue = conditionValue;
	}

	public String getConditionValue() {
		return conditionValue;
	}

	public void setTrueOutValue(String trueOutValue) {
		this.trueOutValue = trueOutValue;
	}

	public String getTrueOutValue() {
		return trueOutValue;
	}

	public void setFalseOutValue(String falseOutValue) {
		this.falseOutValue = falseOutValue;
	}

	public String getFalseOutValue() {
		return falseOutValue;
	}

	public void setBackActivity(String backActivity) {
		this.backActivity = backActivity;
	}

	public String getBackActivity() {
		return backActivity;
	}

	public void setGrapModle(String grapModle) {
		this.grapModle = grapModle;
	}

	public String getGrapModle() {
		return grapModle;
	}

	public String getTranseRole() {
		return transeRole;
	}

	public void setTranseRole(String transeRole) {
		this.transeRole = transeRole;
	}

	public void setGrapWay(String grapWay) {
		this.grapWay = grapWay;
	}

	public String getGrapWay() {
		return grapWay;
	}

	public void setsActivityLabel(String sActivityLabel) {
		this.sActivityLabel = sActivityLabel;
	}

	public String getsActivityLabel() {
		return sActivityLabel;
	}

	public void setOutquery(String outquery) {
		this.outquery = outquery;
	}

	public String getOutquery() {
		return outquery;
	}

}
