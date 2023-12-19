package com.tlv8.system.help.converter;

import java.text.SimpleDateFormat;
import java.util.Date;
import org.apache.commons.beanutils.Converter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DateConverter implements Converter {
	private String format;
	private static Logger logger = LoggerFactory.getLogger(DateConverter.class);

	public DateConverter(String format) {
		this.format = format;
	}

	public DateConverter() {
		this("yyyy-MM-dd");
	}

	@SuppressWarnings("rawtypes")
	public Object convert(Class clazz, Object value) {
		try {
			String valueToConvert = value.toString();
			Date date = new SimpleDateFormat(this.format).parse(valueToConvert);
			return date;
		} catch (Exception e) {
			if (logger.isDebugEnabled())
				logger.debug(e.toString());
		}
		return null;
	}
}