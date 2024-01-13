package com.tlv8.base;

import java.net.URLDecoder;

public class CodeUtils {
	/*
	 * 特殊字符编码
	 */
	public static String encodeSpechars(String str) {
		str = str.replaceAll("<", "#lt;");
		str = str.replaceAll(">", "#gt;");
		str = str.replaceAll("&nbsp;", "#160;");
		str = str.replaceAll("'", "#apos;");
		return str;
	}

	/*
	 * 特殊字符反编码
	 */
	public static String decodeSpechars(String str) {
		str = str.replaceAll("&lt;", "<");
		str = str.replaceAll("&gt;", ">");
		str = str.replaceAll("#lt;", "<");
		str = str.replaceAll("#gt;", ">");
		str = str.replaceAll("#quot;", "\"");
		str = str.replaceAll("#160;", "&nbsp;");
		str = str.replaceAll("#amp;", "&");
		// str = str.replaceAll("#apos;", "'");
		return str;
	}

	/**
	 * UTF-8解码
	 * 
	 * @param str
	 * @return
	 */
	public static String getDecode(String str) {
		try {
			return URLDecoder.decode(str, "UTF-8");
		} catch (Exception e) {
		}
		if (str != null)
			return str;
		return "";
	}

	/**
	 * 两次UTF-8解码
	 * 
	 * @param str
	 * @return
	 */
	public static String getDoubleDecode(String str) {
		return getDecode(getDecode(str));
	}
}
