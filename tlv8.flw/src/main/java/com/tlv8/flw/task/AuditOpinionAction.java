package com.tlv8.flw.task;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tlv8.base.db.DBUtils;

@Controller
public class AuditOpinionAction {

	@ResponseBody
	@RequestMapping("/checkAuditOpinionAction")
	public Object checkOp(String flowID, String taskID) {
		String sql = "select FID from OA_FLOWRECORD t where  FFLOWID=? and FTASKID=?";
		Map<String, Object> res = new HashMap<String, Object>();
		try {
			List<Object> params = new ArrayList<Object>();
			params.add(flowID);
			params.add(taskID);
			List<Map<String, String>> li = DBUtils.selectStringList("oa", sql, params);
			res.put("data", li);
			res.put("flag", true);
		} catch (Exception e) {
			res.put("flag", false);
		}
		return res;
	}

	@ResponseBody
	@RequestMapping("/loadFlowconclusion")
	public Object loadFlowconclusion(String fcreatorid) {
		String sql = "select t.FCONCLUSIONNAME from OA_FLOWCONCLUSION t where t.fcreatorid =? order by t.forder asc";
		Map<String, Object> res = new HashMap<String, Object>();
		try {
			List<Object> params = new ArrayList<Object>();
			params.add(fcreatorid);
			List<Map<String, String>> li = DBUtils.selectStringList("oa", sql, params);
			res.put("data", li);
			res.put("flag", true);
		} catch (Exception e) {
			res.put("flag", false);
		}
		return res;
	}

	@ResponseBody
	@RequestMapping("/loadFagreetext")
	public Object loadFagreetext(String taskID, String sdata1, String fcreatorid) {
		String sql = "select t.FAGREETEXT from OA_FLOWRECORD t where FTASKID=? and FBILLID=? and t.FCREATEPERID=?";
		Map<String, Object> res = new HashMap<String, Object>();
		try {
			List<Object> params = new ArrayList<Object>();
			params.add(taskID);
			params.add(sdata1);
			params.add(fcreatorid);
			List<Map<String, String>> li = DBUtils.selectStringList("oa", sql, params);
			res.put("data", li);
			res.put("flag", true);
		} catch (Exception e) {
			res.put("flag", false);
		}
		return res;
	}
}
