package com.tlv8.mac;

import com.tlv8.mac.utils.CryptUtils;

public class License {
	public static String getMachineCode() {
		return CryptUtils.getMachineCode();
	}
}
