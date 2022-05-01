package com.tlv8.doc.clt.doc;

import java.sql.Timestamp;
import java.util.Date;
import java.util.UUID;

public class CommonUtils {
	public static String createGUID() {
		return UUID.randomUUID().toString().replaceAll("-", "");
	}

	public static Timestamp getCurrentDateTime() {
		return new Timestamp(new Date().getTime());
	}

	public static Float toFloat(String string) {
		return Float.parseFloat(string);
	}

}
