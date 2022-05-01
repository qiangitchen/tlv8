package com.tlv8.flw.expression;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;

import com.tlv8.flw.bean.ExpressionBean;
import com.tlv8.flw.helper.ExpressionTreeHelper;

public class ExpressionResolution {
	/**
	 * 获取表达式里的系统函数列表
	 */
	public static List<String> getExpressId(String expression, HttpServletRequest request) {
		expression = expression.trim();
		List<String> result = new ArrayList<String>();
		String resultStr = null;
		String afterStr = expression;
		if (expression.indexOf("(") > 0 && expression.indexOf(")") > 0) {
			while (afterStr.indexOf("(") > 0) {
				resultStr = afterStr.substring(0, afterStr.indexOf("(")).trim();
				afterStr = afterStr.substring(afterStr.indexOf("(") + 1).trim();
				if (resultStr.indexOf(" ") > -1) {
					resultStr = resultStr.substring(resultStr.lastIndexOf(" ")).trim();
				}
				if (resultStr.indexOf("==") > -1) {
					resultStr = resultStr.substring(resultStr.lastIndexOf("==") + 2).trim();
				}
				if (resultStr.indexOf("=") > -1) {
					resultStr = resultStr.substring(resultStr.lastIndexOf("=") + 1).trim();
				}
				if (resultStr.indexOf(">=") > -1) {
					resultStr = resultStr.substring(resultStr.lastIndexOf(">=") + 2).trim();
				}
				if (resultStr.indexOf("<=") > -1) {
					resultStr = resultStr.substring(resultStr.lastIndexOf("<=") + 2).trim();
				}
				if (resultStr.indexOf(">") > -1) {
					resultStr = resultStr.substring(resultStr.lastIndexOf(">") + 1).trim();
				}
				if (resultStr.indexOf("<") > -1) {
					resultStr = resultStr.substring(resultStr.lastIndexOf("<") + 1).trim();
				}
				if (resultStr.indexOf("+") > -1) {
					resultStr = resultStr.substring(resultStr.lastIndexOf("+") + 1).trim();
				}
				if (resultStr.indexOf("*") > -1) {
					resultStr = resultStr.substring(resultStr.lastIndexOf("*") + 1).trim();
				}
				if (resultStr.indexOf("/") > -1) {
					resultStr = resultStr.substring(resultStr.lastIndexOf("/") + 1).trim();
				}
				if (resultStr.indexOf("-") > -1) {
					resultStr = resultStr.substring(resultStr.lastIndexOf("-") + 1).trim();
				}
				if (resultStr.indexOf(",") > 0) {
					resultStr = resultStr.substring(resultStr.lastIndexOf(",") + 1);
				}
				ExpressionBean express = new ExpressionTreeHelper().getExpression(resultStr, request);
				if (express != null) {
					result.add(resultStr);
				}
			}
		}
		return result;
	}

	/**
	 * 获取表达式 的参数
	 */
	public static String[] getExpreesionParam(String expression, String id, HttpServletRequest request) {
		String[] result = {};
		String re = null;
		int index = expression.indexOf(id + "(") + id.length() + 1;
		re = getContent(index, expression);
		if (re.indexOf("(") == 0) {
			re = re.substring(1);
		}
		if (re.indexOf(")") > -1) {
			re = re.substring(0, re.lastIndexOf(")"));
		}
		// re = re.replaceAll("'", "");
		String regstr = "\"[^\"]*\"";
		// Pattern p = Pattern.compile("\"(.*?)\"");
		Pattern p = Pattern.compile(regstr);
		Matcher m = p.matcher(re);
		Map<String, String> map = new HashMap<String, String>();
		while (m.find()) {
			String param = UUID.randomUUID().toString().replace("-", "");
			map.put(param, m.group());
			re = re.replace(m.group(), param);
		}
		if (!"".equals(re.trim()) && re != null) {
			result = re.split(",");
		}
		int i = 0;
		for (String pa : result) {
			if (map.containsKey(pa)) {
				pa = pa.replace(pa, map.get(pa));
			}
			result[i] = ((String) BooleanExpression.resolutionExpression(pa, request)).replace("\"", "");
			i++;
		}
		return result;
	}

	/**
	 * 获取配置的表达字符
	 */
	public static String getFuncExpress(String expression, String id) {
		String result = id;
		int index = expression.indexOf(id + "(") + id.length() + 1;
		result = id + getContent(index, expression);
		return result;
	}

	/**
	 * 获取括号内的内容
	 */
	public static String getContent(int index, String src) {
		char[] cs = src.toCharArray();
		int count = 1, countLeft = 1, countRight = 1;
		String result = "";
		for (int i = 0; i < cs.length; i++) {
			char c = cs[i];
			if (c == '(') {
				if (count == index) {
					for (int j = i; j < cs.length; j++) {
						char c2 = cs[j];
						if (c2 == '(') {
							countLeft++;
						}
						if (c2 == ')') {
							countRight++;
						}
						if (countLeft == countRight) {
							result = src.substring(i, j + 1);
							break;
						}
					}
					break;
				}
			}
			count++;
		}
		return result;
	}

}
