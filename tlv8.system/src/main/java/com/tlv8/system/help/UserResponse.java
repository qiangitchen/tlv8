package com.tlv8.system.help;

import java.util.ArrayList;
import java.util.Iterator;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

@SuppressWarnings({ "unchecked", "rawtypes" })
public class UserResponse {
	private static Logger logger = LoggerFactory.getLogger(UserResponse.class);

	private HttpServletRequest request;
	private ArrayList<JSONObject> jsonList = new ArrayList();

	private static JSONObject genRenderJSON(String status, String code, String title, String text, String data) {
		JSONObject json = new JSONObject();
		try {
			if ((status != null) && (status != ""))
				json.put("status", status);
			if ((code != null) && (code != ""))
				json.put("code", code);
			if ((title != null) && (title != ""))
				json.put("title", title);
			if ((text != null) && (text != ""))
				json.put("text", text);
			if ((data != null) && (data != "")) {
				json.put("data", data);
				try {
					JSONObject djson = JSONObject.parseObject(data);
					for (String key : djson.keySet()) {
						json.put(key, djson.get(key));
					}
				} catch (Exception e) {

				}
			}
		} catch (Exception e) {
			logger.debug(e.toString());
		}
		return json;
	}

	private static JSONArray genRenderJSON(ArrayList<JSONObject> jsonList) {
		Iterator list = jsonList.iterator();
		JSONArray array = new JSONArray();
		while (list.hasNext()) {
			array.add(list.next());
		}
		return array;
	}

	public UserResponse(HttpServletRequest request) {
		this.request = request;
	}

	public void put(RenderStatus status, String data) {
		this.jsonList.add(genRenderJSON(status.toString(), null, null, null, data));
	}

	public void put(MsgStatus status, String code, String title, String text, Object[] params) {
		this.jsonList.add(genRenderJSON(status.toString(), code,
				MessageResource.getMessage(SessionHelper.getLocale(this.request), title),
				MessageResource.getMessage(SessionHelper.getLocale(this.request), text, params), null));
	}

	public void put(MsgStatus status, String code, String title, String text) {
		this.jsonList.add(genRenderJSON(status.toString(), code,
				MessageResource.getMessage(SessionHelper.getLocale(this.request), title),
				MessageResource.getMessage(SessionHelper.getLocale(this.request), text), null));
	}

	public String toString() {
		return genRenderJSON(this.jsonList).toString();
	}

	public void reset() {
		this.jsonList.clear();
	}
}