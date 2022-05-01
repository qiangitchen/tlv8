package com.tlv8.system.help;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.tlv8.system.bean.ContextBean;

public final class SessionHelper {
	public static void invalidate(HttpServletRequest req) {
		HttpSession session = req.getSession(false);
		if (session != null) {
			try {
				session.removeAttribute("jpolite_key_ses_username");
				// session.invalidate();
			} catch (Exception e) {
			}
		}
	}

	public static String getLocale(HttpServletRequest req) {
		if (req.getSession().getAttribute("jpolite_key_ses_locale") != null) {
			return req.getSession().getAttribute("jpolite_key_ses_locale")
					.toString();
		}
		return "zh_CN";
	}

	public static void setLocale(HttpServletRequest req, String locale) {
		if ((locale == null) || (locale.equals("")))
			req.getSession().removeAttribute("jpolite_key_ses_locale");
		else
			req.getSession().setAttribute("jpolite_key_ses_locale", locale);
	}

	public static String getUsername(HttpServletRequest req) {
		return req.getSession().getAttribute("jpolite_key_ses_username") != null ? req
				.getSession().getAttribute("jpolite_key_ses_username")
				.toString()
				: "";
	}

	public static void setUsername(HttpServletRequest req, String username) {
		req.getSession().setAttribute("jpolite_key_ses_username", username);
	}

	public static String getSessionID(HttpServletRequest req) {
		return req.getSession().getAttribute("jpolite_key_ses_session") != null ? req
				.getSession().getAttribute("jpolite_key_ses_session")
				.toString()
				: "";
	}

	public static void setSessionID(HttpServletRequest req, String session) {
		req.getSession().setAttribute("jpolite_key_ses_session", session);
	}

	/*
	 * 用户信息
	 */
	public static void setContext(HttpServletRequest req, ContextBean context) {
		req.getSession().setAttribute("jpolite_key_ses_context", context);
	}

	public static ContextBean getContext(HttpServletRequest req) {
		return req.getSession().getAttribute("jpolite_key_ses_context") != null ? (ContextBean) req
				.getSession().getAttribute("jpolite_key_ses_context")
				: new ContextBean();
	}

	public static Boolean isLogged(HttpServletRequest req) {
		return Boolean.valueOf(!getUsername(req).equals(""));
	}

	public static String getAttrString(HttpServletRequest req, String key) {
		return req.getSession().getAttribute(key) != null ? req.getSession()
				.getAttribute(key).toString() : "";
	}
}
