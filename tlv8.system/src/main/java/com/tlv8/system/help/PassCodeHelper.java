package com.tlv8.system.help;

import com.tlv8.base.Sys;

public class PassCodeHelper {
	// 加密
	public static String enCode(String s) {
		String str = "";
		String key = "12345678";
		int ch;
		if (key.length() == 0) {
			return s;
		} else if (!s.equals(null)) {
			for (int i = 0, j = 0; i < s.length(); i++, j++) {
				if (j > key.length() - 1) {
					j = j % key.length();
				}
				ch = s.codePointAt(i) + key.codePointAt(j);
				if (ch > 65535) {
					ch = ch % 65535;
				}
				str += getSp(i) + (char) ch;
			}
		}
		return str;

	}

	// 解密
	public static String deCode(String s) {
		String str = "";
		String key = "12345678";
		int ch;
		if (key.length() == 0) {
			return s;
		} else if (!s.equals(key)) {
			for (int i = 0, j = 0; i < s.length(); i++, j++) {
				s = s.replace(getSp(i), "");
				if (j > key.length() - 1) {
					j = j % key.length();
				}
				ch = (s.codePointAt(i) + 65535 - key.codePointAt(j));
				if (ch > 65535) {
					ch = ch % 65535;
				}
				str += (char) ch;
			}
		}
		return str;
	}

	public static String getSp(int i) {
		String[] spstr = { "", "!", "@", "#", "$", "%", "^", "&", "*" };
		if (i < 9) {
			return spstr[i];
		}
		return "";
	}

	public static void main(String[] args) {
		Sys.printMsg(enCode("qq123456!!"));
		Sys.printMsg(deCode(enCode("qq123456!!")));
	}
}
