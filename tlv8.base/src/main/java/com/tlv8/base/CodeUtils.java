package com.tlv8.base;

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
}
