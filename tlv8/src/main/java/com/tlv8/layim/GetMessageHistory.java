package com.tlv8.layim;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/layim")
public class GetMessageHistory {

	@ResponseBody
	@RequestMapping("/getMessageHistory")
	public Object execute(String id, String type) throws Exception {
		Map<String, Object> json = new HashMap<String, Object>();
		json.put("code", 0);
		json.put("msg", "");
		List<Map<String, Object>> data = IMDBUtils.getMessageHistoryList(id, type);
		json.put("data", data);
		return json;
	}

}
