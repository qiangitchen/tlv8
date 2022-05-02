package com.tlv8.mac;

import com.tlv8.mac.utils.CryptUtils;

/**
 * 注册相关
 * 
 * @author chenqian
 *
 */
public class License {

	/**
	 * 获取机器码
	 * 
	 * @return
	 */
	public static String getMachineCode() {
		return CryptUtils.getMachineCode();
	}
}
