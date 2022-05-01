package com.tlv8.doc.svr.generator.utils;

import java.util.UUID;

public class IDUtils {
	public static String getUUID() {
		return UUID.randomUUID().toString();
	}

	public static String getGUID() {
		return getUUID().toUpperCase().replaceAll("-", "");
	}

	public static double getRandom() {
		return Math.random();
	}

	public static long getRandomLong() {
		double r = getRandom();
		long ins = Math.round(r * 100000000);// 八位数
		return ins;
	}

	public static long getRandomByLength(int len) {
		double r = getRandom();
		long ins = Math.round(r * Math.pow(10, len));
		return ins;
	}

	public static void main(String[] args) {
		System.out.println(getUUID());
		System.out.println(getGUID());
		System.out.println(getRandomLong());
		System.out.println(getRandomByLength(6));
	}
}
