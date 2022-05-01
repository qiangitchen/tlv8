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
public class HandwrSignature {

	@ResponseBody
	@RequestMapping("/loadHandwrSignature")
	public Object load(String personid) throws Exception {
		Map<String, Object> res = new HashMap<String, Object>();
		try {
			String sql = "select SID from SA_HANDWR_SIGNATURE where SPERSONID = ?";
			List<Object> params = new ArrayList<Object>();
			params.add(personid);
			List<Map<String, String>> li = DBUtils.selectStringList("system", sql, params);
			res.put("data", li);
			res.put("flag", true);
		} catch (Exception e) {
			res.put("flag", false);
		}
		return res;
	}

}
