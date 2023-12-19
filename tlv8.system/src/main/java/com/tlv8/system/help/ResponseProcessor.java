package com.tlv8.system.help;

import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class ResponseProcessor {
	private static Logger logger = LoggerFactory.getLogger(ResponseProcessor.class);

	public static void renderText(HttpServletResponse res, String text) {
		try {
			res.setHeader("Content-Type", "text/html;charset=UTF-8");
			res.getWriter().print(text);
			res.getWriter().close();
		} catch (Exception e) {
			//e.printStackTrace();
			logger.debug(e.toString());
		}
	}
}
