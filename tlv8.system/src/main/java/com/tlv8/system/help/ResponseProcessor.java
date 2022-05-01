package com.tlv8.system.help;

import javax.servlet.http.HttpServletResponse;
import org.apache.log4j.Logger;

public final class ResponseProcessor {
	private static Logger logger = Logger.getLogger(ResponseProcessor.class);

	public static void renderText(HttpServletResponse res, String text) {
		try {
			res.setHeader("Content-Type", "text/html;charset=UTF-8");
			res.getWriter().print(text);
			res.getWriter().close();
		} catch (Exception e) {
			//e.printStackTrace();
			logger.debug(e);
		}
	}
}
