package com.tlv8.core.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtils {
	public static String date2str(Date date) {
		String result = "";
		try {
			SimpleDateFormat formater = new SimpleDateFormat(
					"yyyy-MM-dd HH:mm:ss");
			result = formater.format(date);
		} catch (Exception e) {
			try {
				SimpleDateFormat formater = new SimpleDateFormat("yyyy-MM-dd");
				result = formater.format(date);
			} catch (Exception e1) {
			}
		}
		return result;
	}

	public static String date2str(Date date, String formatstr) {
		String result = "";
		try {
			SimpleDateFormat formater = new SimpleDateFormat(formatstr);
			result = formater.format(date);
		} catch (Exception e) {
		}
		return result;
	}

	public static Date str2date(String datestr) {
		Date result = null;
		try {
			SimpleDateFormat formater = new SimpleDateFormat(
					"yyyy-MM-dd HH:mm:ss");
			result = formater.parse(datestr);
		} catch (Exception e) {
			try {
				SimpleDateFormat formater = new SimpleDateFormat("yyyy-MM-dd");
				result = formater.parse(datestr);
			} catch (Exception e1) {
			}
		}
		return result;
	}

	public static Date str2date(String datestr, String formatstr) {
		Date result = null;
		try {
			SimpleDateFormat formater = new SimpleDateFormat(formatstr);
			result = formater.parse(datestr);
		} catch (Exception e) {
		}
		return result;
	}
}
