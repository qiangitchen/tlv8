package com.tlv8.base.mac;

import com.alibaba.fastjson.JSONObject;
import com.tlv8.base.mac.info.SettingInfo;
import com.tlv8.base.mac.utils.CryptUtils;

/**
 * 注册相关
 * 
 * @author chenqian
 *
 */
public class License {
	private static int def_rgs = 1000;

	/**
	 * 获取机器码
	 * 
	 * @return
	 */
	public static String getMachineCode() {
		return CryptUtils.getMachineCode();
	}

	public static int getRegisters() {
		JSONObject json = SettingInfo.getLisensJson();
		if (json != null) {
			try {
				if (json.get("MachineCode").equals(CryptUtils.getMachineCode())) {
					SettingInfo.printRegistInfo(json);
					return (Integer) json.get("Registers");
				} else {
					SettingInfo.printDefualt();
					return def_rgs;
				}
			} catch (Exception e) {
			}
		}
		return def_rgs;
	}

	public static boolean checkLog(int onl) throws Exception {
		try {
			String str = SettingInfo.getLisens();
			return SettingInfo.checkRegister(str, onl);
		} catch (Exception e) {
			return SettingInfo.checkRegister(null, onl);
		}
	}
}
