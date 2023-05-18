package com.tlv8.core.utils;

import java.util.HashMap;

import com.tlv8.system.bean.ContextBean;
import com.tlv8.system.utils.ContextUtils;

public class UserFullInfo {
	/*
	 * 获取当前登录人员信息
	 */
	@SuppressWarnings("unchecked")
	public static HashMap<String, String> get(String personID) {
		HashMap<String, String> m = new HashMap<String, String>();
		try {
			ContextBean context = ContextUtils.getContext();
			m = (HashMap<String, String>) context.toMap();
		} catch (Exception e) {
		}
		return m;
	}

	@SuppressWarnings("unchecked")
	public static HashMap<String, String> get() {
		HashMap<String, String> m = new HashMap<String, String>();
		try {
			ContextBean context = ContextUtils.getContext();
			m = (HashMap<String, String>) context.toMap();
		} catch (Exception e) {
		}
		return m;
	}
}
