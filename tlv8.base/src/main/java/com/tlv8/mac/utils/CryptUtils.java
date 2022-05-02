package com.tlv8.mac.utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Date;
import java.util.Map;

import com.alibaba.fastjson.JSONObject;

public class CryptUtils {
	public static Map<String, String> devInfo = SerialNumberUtil.getAllSn();

	/**
	 * MD5 加密
	 * 
	 * @param info
	 * @return
	 */
	public static String encryptToMD5(String info) {
		byte[] digesta = null;
		try {
			MessageDigest alga = MessageDigest.getInstance("MD5");

			alga.update(info.getBytes());

			digesta = alga.digest();
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}

		String rs = byte2hex(digesta);
		return rs;
	}

	/**
	 * SHA 加密
	 * 
	 * @param info
	 * @return
	 */
	public static String encryptToSHA(String info) {
		byte[] digesta = null;
		try {
			MessageDigest alga = MessageDigest.getInstance("SHA-1");

			alga.update(info.getBytes());

			digesta = alga.digest();
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}

		String rs = byte2hex(digesta);
		return rs;
	}

	/**
	 * byte转16进制
	 * 
	 * @param b
	 * @return
	 */
	public static String byte2hex(byte[] b) {
		String hs = "";
		String stmp = "";
		for (int n = 0; n < b.length; n++) {
			stmp = Integer.toHexString(b[n] & 0xFF);
			if (stmp.length() == 1)
				hs = hs + "0" + stmp;
			else {
				hs = hs + stmp;
			}
		}
		return hs.toUpperCase();
	}

	/**
	 * 获取机器码
	 * 
	 * @return
	 */
	public static String getMachineCode() {
		try {
			return encryptToMD5(getDevicInfo());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 获取设备信息
	 * 
	 * @return
	 */
	protected static String getDevicInfo() {
		JSONObject strb = new JSONObject();
		try {
			strb.put("cpuid", devInfo.get("cpuid")); // CPU
			strb.put("mainboard", devInfo.get("mainboard")); // 主板
			strb.put("diskid", devInfo.get("diskid")); // 硬盘
		} catch (Exception e) {
			e.printStackTrace();
		}

		return strb.toString();
	}

	public static void main(String[] args) {
		long st = new Date().getTime();
		System.out.println(getMachineCode());
		long et = new Date().getTime();
		System.out.println("use time:" + (et - st) + "ms");
	}
}
