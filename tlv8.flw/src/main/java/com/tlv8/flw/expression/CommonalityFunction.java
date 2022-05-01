package com.tlv8.flw.expression;

public class CommonalityFunction {
	/**
	 * value1,value2选择
	 * 
	 * @如果value1存在取value1否则取value2
	 */
	public static String electChoice(String value1, String value2) {
		String result = value1;
		if ("".equals(value1) || "null".equals(value1) || value1 == null) {
			result = value2;
		}
		return result;
	}

	/**
	 * 判断是否为空
	 */
	public static String isNull(String values) {
		String result = "FALSE";
		if ("".equals(values) || "null".equals(values) || values == null) {
			result = "TRUE";
		}
		return result;
	}

	/**
	 * 多个字符串连接
	 */
	public static String concat(Object[] values) {
		if (values == null)
			return "";
		StringBuffer sb = new StringBuffer();
		for (Object s : values)
			sb.append(s.toString());
		return sb.toString();
	}

	/**
	 * 字符串转大写
	 * 
	 * @param str
	 * @return
	 */
	public static String upper(String str) {
		return str == null ? null : str.toUpperCase();
	}

	/**
	 * 字符串转小写
	 * 
	 * @param str
	 * @return
	 */
	public static String lower(String str) {
		return str == null ? null : str.toLowerCase();
	}

	/**
	 * 字符串去空格
	 * 
	 * @param str
	 * @return
	 */
	public static String trim(String str) {
		return str == null ? null : str.trim();
	}

	/**
	 * 判断字符串str1中是否包含strs {多个参数逗号分隔}
	 */
	public static String isContain(String str1, String strs) {
		String result = "FALSE";
		for (String str2 : strs.split(",")) {
			if (str1 != null && str2 != null && str1.contains(str2)) {
				result = "TRUE";
			}
		}
		return result;
	}
}
