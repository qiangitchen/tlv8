package com.tlv8.core.controller;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;

@Controller
@Scope("prototype")
public class GetSystemParamController {
	private String sysdate;

	public void setSysdate(String sysdate) {
		this.sysdate = sysdate;
	}

	@ResponseBody
	@RequestMapping("/getSystemDate")
	public Object getDate() throws Exception {
		setSysdate("");
		setSysdate(new SimpleDateFormat("yyyy-MM-dd").format(new Date()));
		JSONObject json = new JSONObject();
		json.put("sysdate", sysdate);
		return json;

	}

	@ResponseBody
	@RequestMapping("/getSystemDateTime")
	public Object getDateTime() throws Exception {
		setSysdate("");
		setSysdate(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
		JSONObject json = new JSONObject();
		json.put("sysdate", sysdate);
		return json;
	}
}
