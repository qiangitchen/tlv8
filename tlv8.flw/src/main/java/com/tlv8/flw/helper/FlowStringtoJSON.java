package com.tlv8.flw.helper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

@SuppressWarnings({ "unchecked", "rawtypes" })
public class FlowStringtoJSON {
	/*
	 * 将JSON字符串转换成Java对象
	 */
	public static List<Map> ParseMapList(String jsonStr) {
		List list = new ArrayList();
		JSONArray array;
		try {
			array = JSON.parseArray(jsonStr);
			for (int i = 0; i < array.size(); i++) {
				JSONObject obj = array.getJSONObject(i);
				Map m = (Map) JSON.parse(obj.toString());
				list.add(m);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	public static Map parseMap(String jsonStr) {
		Map map = new HashMap();
		try {
			map = (Map) JSON.parse(jsonStr);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return map;
	}
}
