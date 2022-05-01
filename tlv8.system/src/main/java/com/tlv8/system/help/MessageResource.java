package com.tlv8.system.help;

import java.util.Locale;

import com.tlv8.system.resource.MessageResourceReader;

public final class MessageResource {
	private static MessageResourceReader reader = MessageResourceReader
			.getResourceReader();

	public static String getMessage(String locale, String key, Object[] params) {
		Locale theLocale = null;
		if (locale != null) {
			theLocale = new Locale(locale);
		}
		return reader.getProperty(key, theLocale, params);
	}

	public static String getMessage(String locale, String key,
			Iterable<Object> params) {
		Locale theLocale = null;
		if (locale != null) {
			theLocale = new Locale(locale);
		}
		return reader.getProperty(key, theLocale, params);
	}

	public static String getMessage(String locale, String key) {
		Object[] fake = null;
		return getMessage(locale, key, fake);
	}

	public static String getMessage(String key) {
		Object[] fake = null;
		return getMessage(null, key, fake);
	}
}
