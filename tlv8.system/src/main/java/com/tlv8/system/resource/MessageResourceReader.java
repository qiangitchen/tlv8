package com.tlv8.system.resource;

import java.util.Locale;
import org.apache.log4j.Logger;

import com.tlv8.system.help.Configuration;

public abstract class MessageResourceReader {
	protected static final Logger logger = Logger
			.getLogger(MessageResourceReader.class);

	public abstract void load(String paramString);

	public abstract String getProperty(String paramString, Locale paramLocale);

	public abstract String getProperty(String paramString, Locale paramLocale,
			Object[] paramArrayOfObject);

	public abstract String getProperty(String paramString, Locale paramLocale,
			Iterable<Object> paramIterable);

	public static MessageResourceReader getResourceReader() {
		try {
			return (MessageResourceReader) Class.forName(
					Configuration.getConfig("jpolite.resource.reader"))
					.newInstance();
		} catch (Exception e) {
			logger
					.info("INFO:: MessageResourceRead.getResourceReader -- 没有找到配置文件中jpolite.resource.reader所对应的资源文件处理器，加载默认的处理器");
		}
		return new PropertyMessageResourceReader();
	}
}