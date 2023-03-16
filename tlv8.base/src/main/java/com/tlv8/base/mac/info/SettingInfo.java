package com.tlv8.base.mac.info;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.alibaba.fastjson.JSONObject;
import com.tlv8.base.mac.encode.DesUtils;
import com.tlv8.base.mac.utils.CryptUtils;

public class SettingInfo {

	private static int def_onl = 10;
	private static int def_rgs = 1000;

	public static String getLisens() {
		String str = null;
		try {
			HttpServletRequest request = ((ServletRequestAttributes) (RequestContextHolder.currentRequestAttributes()))
					.getRequest();
			String licenseRealPath = request.getServletContext().getRealPath("/WEB-INF/license");
			File file = new File(licenseRealPath);
			BufferedReader strbff = new BufferedReader(new FileReader(file));
			while ((str = strbff.readLine()) != null) {
				if (str != null && !"".equals(str)) {
					break;
				}
			}
			strbff.close();
		} catch (Exception e) {
		}
		return str;
	}

	public static JSONObject getLisensJson() {
		String obj = getLisens();
		if (obj != null) {
			try {
				DesUtils des = new DesUtils();
				String rsInfo = des.decrypt(obj);
				JSONObject json = JSONObject.parseObject(rsInfo);
				return json;
			} catch (Exception e) {
			}
		}
		return null;
	}

	public static boolean checkLog(int onl) throws Exception {
		try {
			String str = getLisens();
			return checkRegister(str, onl);
		} catch (Exception e) {
			return checkRegister(null, onl);
		}
	}

	public static boolean checkRegister(String rsmac, int onl) throws Exception {
		try {
			DesUtils des = new DesUtils();
			if (rsmac != null && !"".equals(rsmac)) {
				try {
					String rsInfo = des.decrypt(rsmac);
					JSONObject json = JSONObject.parseObject(rsInfo);
					if (CryptUtils.getMachineCode().equals(json.get("MachineCode"))) {
						printRegistInfo(json);
						SimpleDateFormat fmt = new SimpleDateFormat("yyyy-MM-dd");
						Date end = fmt.parse((String) json.get("EndTime"));
						long diff = end.getTime() - new Date().getTime();
						long diffDay = diff / (24 * 3600 * 1000);
						if (diffDay < 5) {
							System.out.println("距到期时间还有: " + diffDay + "天");
							System.out.println("---------------------------------------------------------");
						}
						if (diffDay > 0 && ((Integer) json.get("Onlines")) >= onl) {
							return true;
						} else if (diffDay < 0) {
							throw new Exception("注册码已过期,请联系管理员!");
						} else {
							throw new Exception("在线用户数已经超过最大在线用户数,请联系管理员!");
						}
					} else {
						System.out.println("机器码不正确>>>>>>>>>>");
						System.out.println(
								"注册机器码为: " + json.get("MachineCode") + ",当前机器码为:" + CryptUtils.getMachineCode());
						printDefualt();
						if (def_onl >= onl) {
							return true;
						} else {
							throw new Exception("在线用户数已经超过最大在线用户数,请联系管理员!");
						}
					}
				} catch (Exception e) {
					System.out.println("<<<<<<<<<<<<<<无效注册码>>>>>>>>>>");
				}
			}
			printDefualt();
			if (5 >= onl) {
				return true;
			} else {
				throw new Exception("在线用户数已经超过最大在线用户数,请联系管理员!");
			}
		} catch (Exception e) {
			// throw e;
			System.out.println("<<<<<<<<<<<<<<无效注册码>>>>>>>>>>");
			printDefualt();
			if (5 >= onl) {
				return true;
			} else {
				throw new Exception("在线用户数已经超过最大在线用户数,请联系管理员!");
			}
		}
	}

	public static void printRegistInfo(JSONObject json) throws Exception {
		System.out.println("---------------------------------------------------------");
		System.out.println("注册模式: >>");
		System.out.println("机器码为: " + json.get("MachineCode"));
		System.out.println("允许同时在线人数: " + json.get("Onlines") + "人");
		System.out.println("允许注册用户数: " + json.get("Registers") + "个");
		System.out.println("注册时间: " + json.get("StartTime"));
		System.out.println("注册到期时间: " + json.get("EndTime"));
	}

	public static void printDefualt() {
		System.out.println("---------------------------------------------------------");
		System.out.println("当前为默认模式: >>");
		System.out.println("当前机器码为: " + CryptUtils.getMachineCode());
		System.out.println("允许同时在线人数: " + def_onl + "人");
		System.out.println("允许注册用户数: " + def_rgs + "个");
		System.out.println("---------------------------------------------------------");
	}
}
