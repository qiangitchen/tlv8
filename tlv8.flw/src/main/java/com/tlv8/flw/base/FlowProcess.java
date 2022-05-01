package com.tlv8.flw.base;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

@SuppressWarnings({ "rawtypes" })
public class FlowProcess {

	private Map actMap;
	private String processID;
	private String processName;
	private String processActy;

	public FlowProcess(String processID) {
		this.actMap = FlowFile.getFlowDraw(processID);
		this.processID = processID;
		this.processName = (String) actMap.get("SPROCESSNAME");
		this.processActy = (String) actMap.get("SPROCESSACTY");
	}

	public List<FlowActivity> getProcessActivitys() {
		String sprocessacty = null;
		List<FlowActivity> ActList = new ArrayList<FlowActivity>();
		try {
			JSONObject jsonObj = JSON.parseObject(processActy);
			sprocessacty = jsonObj.getString("nodes");
			JSONArray ActivityArray = JSON.parseArray(sprocessacty);
			for (int i = 0; i < ActivityArray.size(); i++) {
				JSONObject Acjson = (JSONObject) ActivityArray.get(i);
				if (!"condition".equals(Acjson.getString("type")) && !"start".equals(Acjson.getString("type"))
						&& !"end".equals(Acjson.getString("type"))) {
					String actID = Acjson.getString("id");
					FlowActivity act = new FlowActivity(actMap, processID, actID);
					ActList.add(act);
				}

			}
		} catch (Exception e) {
		}
		return ActList;

	}

	public String getProcessID() {
		return processID;
	}

	public void setProcessID(String processID) {
		this.processID = processID;
	}

	public String getProcessName() {
		return processName;
	}

	public void setProcessName(String processName) {
		this.processName = processName;
	}

	public String getProcessActy() {
		return processActy;
	}

	public void setProcessActy(String processActy) {
		this.processActy = processActy;
	}

	public static void main(String[] args) {
		new FlowProcess("C551A33924A00001953AE48270042B90").getProcessActivitys();
	}

	public void setActMap(Map actMap) {
		this.actMap = actMap;
	}

	public Map getActMap() {
		return actMap;
	}

}
