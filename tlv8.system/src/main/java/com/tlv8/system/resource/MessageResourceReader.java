package com.tlv8.system.resource;

import java.util.Locale;

public abstract class MessageResourceReader {
	public abstract void load(String paramString);

	public abstract String getProperty(String paramString, Locale paramLocale);

	public abstract String getProperty(String paramString, Locale paramLocale, Object[] paramArrayOfObject);

	public abstract String getProperty(String paramString, Locale paramLocale, Iterable<Object> paramIterable);

	public static MessageResourceReader getResourceReader() {
		return new PropertyMessageResourceReader();
	}
}