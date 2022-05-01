package com.tlv8.system.help;

import java.util.Date;
import java.util.Iterator;
import java.util.Properties;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.beanutils.ConvertUtils;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import com.tlv8.system.help.converter.DateConverter;

@SuppressWarnings({ "rawtypes" })
public final class Configuration {
	private static final String propertyxml = "/WEB-INF/tlv8.xml";
	private static Properties ctxProp = new Properties();

	private static boolean no_cache = false;
	private static boolean captcha_enabled = false;// 需要验证码
	private static boolean md5captcha_enabled = false;// 需要验证码

	private static void loadxml(Properties ctxProp, String url) {
		SAXReader saxreader = new SAXReader();
		try {
			Document doc = saxreader.read(url);
			if (doc.hasContent()) {
				Element root = doc.getRootElement();
				for (Iterator i$ = root.elements().iterator(); i$.hasNext();) {
					Object o = i$.next();
					Element e = (Element) o;
					String elementname = e.getName();
					if (elementname.equals("connection")) {
						String key = e.attributeValue("key");
						ctxProp.setProperty("jpolite.db." + key + ".connection.driver", e.attributeValue("driver"));
						ctxProp.setProperty("jpolite.db." + key + ".connection.url", e.attributeValue("url"));
						ctxProp.setProperty("jpolite.db." + key + ".connection.username", e.attributeValue("username"));
						ctxProp.setProperty("jpolite.db." + key + ".connection.password", e.attributeValue("password"));
						if (e.attribute("create-table-jpolite_profiles") != null)
							ctxProp.setProperty("jpolite.db." + key + ".table."
									+ e.attribute("create-table-jpolite_profiles").getName().split("-")[2] + ".create",
									e.attributeValue("create-table-jpolite_profiles"));
					} else if (elementname.equals("resource")) {
						ctxProp.setProperty("jpolite.resource", e.attributeValue("value"));
						ctxProp.setProperty("jpolite.resource.reader", e.attributeValue("reader"));
					} else if (elementname.equals("business-server")) {
						if ((e.getText() != null) && (e.getText().trim() != ""))
							ctxProp.setProperty("jpolite.x.business.server.url", e.getText());
					} else if (elementname.equals("ui-server")) {
						if ((e.getText() != null) && (e.getText().trim() != ""))
							ctxProp.setProperty("jpolite.x.ui.server.url", e.getText());
					} else if (elementname.equals("config")) {
						ctxProp.setProperty("jpolite.config.cache", e.attributeValue("cache"));
						ctxProp.setProperty("jpolite.config.charset", e.attributeValue("charset"));
						ctxProp.setProperty("jpolite.config.locale", e.attributeValue("locale"));
					} else if (elementname.equals("db")) {
						ctxProp.setProperty("jpolite.db.alias", e.attributeValue("alias"));
					} else if (elementname.equals("control")) {
						ctxProp.setProperty("jpolite.controller.package", e.attributeValue("package"));
					} else if (elementname.equals("bean")) {
						ctxProp.setProperty("jpolite.bean.package", e.attributeValue("package"));
					} else if (elementname.equals("login")) {
						ctxProp.setProperty("jpolite.login.captcha.enabled", e.attributeValue("captcha"));
						ctxProp.setProperty("jpolite.login.md5captcha.enabled", e.attributeValue("md5captcha"));
					}
				}
			}
		} catch (DocumentException e) {
			e.printStackTrace();
			ctxProp.setProperty("jpolite.controller.package", "com.justep.jpolite.controller");
			ctxProp.setProperty("jpolite.bean.package", "com.justep.jpolite.bean");
			ctxProp.setProperty("jpolite.resource", "resourceBundle");
			ctxProp.setProperty("jpolite.resource.reader", "com.justep.jpolite.resource.PropertyMessageResourceReader");
			ctxProp.setProperty("jpolite.config.cache", "true");
			ctxProp.setProperty("jpolite.config.locale", "zh_CN");
			ctxProp.setProperty("jpolite.config.charset", "UTF-8");
			ctxProp.setProperty("jpolite.login.captcha.enabled", "false");
			ctxProp.setProperty("jpolite.login.md5captcha.enabled", "false");
			ctxProp.setProperty("jpolite.login.captcha.session.key", "KAPTCHA_SESSION_KEY");
			ctxProp.setProperty("jpolite.login.captcha.session.date", "KAPTCHA_SESSION_DATE");
			ctxProp.setProperty("jpolite.db.alias", "system");
		}
	}

	public static void processConfiguration(HttpServletRequest request) throws IllegalStateException {
		loadxml(ctxProp, request.getServletContext().getRealPath(propertyxml));
		try {
			no_cache = !Boolean.parseBoolean(ctxProp.getProperty("jpolite.config.cache"));
			captcha_enabled = Boolean.parseBoolean(ctxProp.getProperty("jpolite.login.captcha.enabled"));
			md5captcha_enabled = Boolean.parseBoolean(ctxProp.getProperty("jpolite.login.md5captcha.enabled"));

			ConvertUtils.register(new DateConverter(), Date.class);
		} catch (Exception e) {
			no_cache = false;
			captcha_enabled = false;
			md5captcha_enabled = false;
		}
	}

	public static String getControllerPackage() {
		return ctxProp.getProperty("jpolite.controller.package");
	}

	public static String getBeanPackage() {
		return ctxProp.getProperty("jpolite.bean.package");
	}

	public static boolean isNoCache() {
		return no_cache;
	}

	public static String getSystemDBAlias() {
		return ctxProp.getProperty("jpolite.db.alias");
	}

	public static String getConnectionDriver(String alias) {
		return ctxProp.getProperty("jpolite.db." + alias + ".connection.driver");
	}

	public static String getConnectionURL(String alias) {
		return ctxProp.getProperty("jpolite.db." + alias + ".connection.url");
	}

	public static String getConnectionUserName(String alias) {
		return ctxProp.getProperty("jpolite.db." + alias + ".connection.username");
	}

	public static String getConnectionPassword(String alias) {
		return ctxProp.getProperty("jpolite.db." + alias + ".connection.password");
	}

	public static String getTableCreate(String tableName) {
		return ctxProp.getProperty("jpolite.db." + getSystemDBAlias() + ".table." + tableName + ".create");
	}

	public static String getBusinessServerURL(String url) {
		String s = ctxProp.getProperty("jpolite.x.business.server.url");
		return (s == null) || (s.trim().equals("")) ? url : s.trim();
	}

	public static String getBusinessServerRemoteURL(String url) {
		String s = ctxProp.getProperty("jpolite.x.business.server.remote.url");
		return (s == null) || (s.trim().equals("")) ? url : s.trim();
	}

	public static String getUIServerURL(String url) {
		String s = ctxProp.getProperty("jpolite.x.ui.server.url");
		return (s == null) || (s.trim().equals("")) ? url : s.trim();
	}

	public static String getUIServerRemoteURL(String url) {
		String s = ctxProp.getProperty("jpolite.x.ui.server.remote.url");
		return (s == null) || (s.trim().equals("")) ? url : s.trim();
	}

	public static String getBusinessURLPath(String key) {
		return ctxProp.getProperty("jpolite.x.business." + key + ".path");
	}

	public static String getUIURLPath(String key) {
		return ctxProp.getProperty("jpolite.x.ui." + key + ".path");
	}

	public static String getConfig(String key) {
		return ctxProp.getProperty(key);
	}

	public static String getLocale(String key_suffix) {
		String suffix = "." + key_suffix;
		return ctxProp.getProperty("jpolite.config.locale" + suffix);
	}

	public static String getLocale() {
		return getLocale(null);
	}

	public static String getCharset() {
		return ctxProp.getProperty("jpolite.config.charset");
	}

	public static Boolean getCaptchaEnabled() {
		return Boolean.valueOf(captcha_enabled);
	}

	public static Boolean getMD5CaptchaEnabled() {
		return Boolean.valueOf(md5captcha_enabled);
	}

	public static String getCaptchaSessionKey() {
		return ctxProp.getProperty("jpolite.login.captcha.session.key");
	}

	public static String getCaptchaSessionDate() {
		return ctxProp.getProperty("jpolite.login.captcha.session.date");
	}
}