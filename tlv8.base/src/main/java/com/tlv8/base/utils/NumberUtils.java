package com.tlv8.base.utils;

import java.text.DecimalFormat;

/**
 * 数字处理公用类
 * 
 * @author chenqian
 *
 */
public class NumberUtils {
	/**
	 * Double 转换成字符串
	 * 
	 * @return
	 */
	public static String d2s(Double dabo) {
		DecimalFormat df = new DecimalFormat();
		// df.setMaximumFractionDigits(2);// 这里是小数位
		String format = df.format(dabo);
		String removeStr = ",";// 去掉千分符号
		String replace = format.replace(removeStr, "");
		return replace;
	}

	/**
	 * 字符串转换成Double
	 * 
	 * @return
	 */
	public static Double s2d(String num) {
		try {
			DecimalFormat df = new DecimalFormat();
			return df.parse(num).doubleValue();
		} catch (Exception e) {
		}
		return 0d;
	}

	/**
	 * 字符串转换成Long
	 * 
	 * @return
	 */
	public static Long s2l(String num) {
		try {
			DecimalFormat df = new DecimalFormat();
			return df.parse(num).longValue();
		} catch (Exception e) {
		}
		return 0l;
	}

	/**
	 * 数字格式化为千分保留两位小数
	 * 
	 * @param num
	 * @return
	 */
	public static String format(Object num) {
		DecimalFormat df = new DecimalFormat();
		df.setMaximumFractionDigits(2);// 这里是小数位
		return df.format(num);
	}

	public static void main(String[] args) {
		System.out.println(format(100));
		System.out.println(format(10000.5232d));
		System.out.println(format(2323.23232f));
	}
}
