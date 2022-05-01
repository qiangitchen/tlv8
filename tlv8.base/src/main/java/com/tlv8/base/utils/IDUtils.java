package com.tlv8.base.utils;

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
		long ins = Math.round(r * 100000000.0D);
		return ins;
	}

	public static void main(String[] args) {
		System.out.println(getUUID());
		for (int i = 0; i < 10; i++) {
			System.out.println(getGUID());
		}
		System.out.println(getRandomLong());
	}
}
