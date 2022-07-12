package com.tlv8.system.resource;

import java.util.Locale;
import org.apache.log4j.Logger;

public abstract class MessageResourceReader {
	protected static final Logger logger = Logger.getLogger(MessageResourceReader.class);

	public abstract void load(String paramString);

	public abstract String getProperty(String paramString, Locale paramLocale);

	public abstract String getProperty(String paramString, Locale paramLocale, Object[] paramArrayOfObject);

	public abstract String getProperty(String paramString, Locale paramLocale, Iterable<Object> paramIterable);

	public static MessageResourceReader getResourceReader() {
		return new PropertyMessageResourceReader();
	}
}