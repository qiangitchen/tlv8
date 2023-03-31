package com.tlv8.system;

import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Iterator;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.HttpMethod;
import org.apache.commons.httpclient.HttpStatus;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.io.SAXReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.tlv8.base.utils.ServletUtils;
import com.tlv8.system.action.GetSysParams;
import com.tlv8.system.bean.ContextBean;
import com.tlv8.system.bean.HttpBean;
import com.tlv8.system.help.MessageResource;
import com.tlv8.system.help.MsgStatus;
import com.tlv8.system.help.RenderStatus;
import com.tlv8.system.help.ResponseProcessor;
import com.tlv8.system.help.SessionHelper;
import com.tlv8.system.help.UserResponse;
import com.tlv8.system.service.TokenService;

public class BaseController {
	@Autowired
	protected HttpServletRequest request;
	@Autowired
	protected HttpServletResponse response;

	protected TokenService tokenService;

	private UserResponse uesrResponse;

	private ContextBean contextbean;

	protected void renderData() {
		if (this.uesrResponse == null) {
			this.uesrResponse = new UserResponse(request);
		}
		try {
			ResponseProcessor.renderText(this.response, this.uesrResponse.toString());
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			this.uesrResponse.reset();
		}
	}

	private void renderData(RenderStatus status, String data) {
		if (this.uesrResponse == null) {
			this.uesrResponse = new UserResponse(request);
		}
		this.uesrResponse.put(status, data);
		renderData();
	}

	protected void renderData(Boolean success, String data) {
		renderData(success.booleanValue() ? RenderStatus.SUCCESS : RenderStatus.FAILURE, data);
	}

	protected void renderData(Boolean success) {
		renderData(success, null);
	}

	protected void renderData(String data) {
		renderData(Boolean.valueOf(true), data);
	}

	protected void prepareMsg(MsgStatus status, String code, String title, String text) {
		if (this.uesrResponse == null) {
			this.uesrResponse = new UserResponse(request);
		}
		this.uesrResponse.put(status, code, title, text);
	}

	protected void prepareMsg(MsgStatus status, String code, String title, String text, Object[] params) {
		if (this.uesrResponse == null) {
			this.uesrResponse = new UserResponse(request);
		}
		this.uesrResponse.put(status, code, title, text, params);
	}

	public void setDependency(HttpServletRequest request, HttpServletResponse response) {
		this.request = request;
		this.response = response;
		this.uesrResponse = new UserResponse(this.request);
	}

	private void initRequest() {
		if (this.request == null) {
			request = ((ServletRequestAttributes) (RequestContextHolder.currentRequestAttributes())).getRequest();
		}
	}

	public String p(String name) {
		initRequest();
		return this.request.getParameter(name);
	}

	public Object a(String name) {
		initRequest();
		return this.request.getAttribute(name);
	}

	public String r(String key) {
		initRequest();
		return MessageResource.getMessage(SessionHelper.getLocale(this.request), key);
	}

	public String r(String key, Object[] params) {
		initRequest();
		return MessageResource.getMessage(SessionHelper.getLocale(this.request), key, params);
	}

	public String r(String key, Iterable<Object> params) {
		initRequest();
		return MessageResource.getMessage(SessionHelper.getLocale(this.request), key, params);
	}

	public void setContext(ContextBean contextbean) {
		this.contextbean = contextbean;
	}

	public ContextBean getContext() {
		if (request == null) {
			request = ServletUtils.getRequest();
		}
		if (this.contextbean == null) {
			if (tokenService == null) {
				tokenService = TokenService.getTokenService();
			}
			this.contextbean = tokenService.getContextBean(request);
		}
		if (this.contextbean == null) {
			this.contextbean = new ContextBean();
		}
		return this.contextbean;
	}

	/**
	 * 
	 * */
	private HttpBean http = new HttpBean();

	public void setHttp(HttpBean http) {
		this.http = http;
	}

	public int executeMethod(String key, HttpMethod method) throws HttpException, IOException {
		HttpClient client = this.http.getClient(key);
		return client.executeMethod(method);
	}

	public void clearHttpClient() {
		this.http.clear();
	}

	public Document executeMethodAsDocument(String key, HttpMethod method)
			throws HttpException, IOException, DocumentException {
		int r = this.executeMethod(key, method);
		if (r == HttpStatus.SC_OK) {
			SAXReader reader = new SAXReader();
			return reader.read(new InputStreamReader(method.getResponseBodyAsStream(), "UTF-8"));
		} else {
			return null;
		}
	}

	public Document executeMethodAsDocument(HttpMethod method) throws HttpException, IOException, DocumentException {
		return executeMethodAsDocument("BusinessServer", method);
	}

	public String genAction(Hashtable<String, String> attributes, Hashtable<String, String> parameters) {
		StringBuffer str = new StringBuffer();
		str.append("<action xmlns:rdf=\"http://www.w3.org/1999/02/22-rdf-syntax-ns#\"\r\n");
		str.append("	xmlns:justep=\"http://www.justep.com/x5#\"\r\n");
		str.append("	xmlns:oxf=\"http://www.orbeon.com/oxf/processors\"\r\n");
		str.append("	xmlns:p=\"http://www.orbeon.com/oxf/pipeline\"\r\n");
		str.append("	xmlns:xslt=\"http://www.orbeon.com/oxf/processors\"\r\n");
		Iterator<String> attrs = attributes.keySet().iterator();
		while (attrs.hasNext()) {
			String key = attrs.next().toString();
			String value = attributes.get(key);
			str.append("	" + key + "=\"" + value + "\"\r\n");
		}
		if (attributes.get("activity") == null) {
			str.append("	activity=\"startActivity\"\r\n");
		}
		str.append("	>\r\n");
		str.append("<parameter>\r\n");
		Iterator<String> params = parameters.keySet().iterator();
		while (params.hasNext()) {
			String key = params.next().toString();
			String value = parameters.get(key);
			String type = "string";
			if (key.lastIndexOf("#") >= 0) {
				type = key.replaceAll(".*#", "");
				key = key.replaceAll("#.*", "");
			}

			if (value == null) {
				str.append("<" + key + "/>\r\n");
			} else {
				str.append("<" + key + "><constant rdf:type=\"http://www.w3.org/2001/XMLSchema#" + type + "\">" + value
						+ "</constant></" + key + ">\r\n");
			}
		}
		str.append("</parameter>\r\n");
		str.append("</action>\r\n");
		return str.toString();
	}

	protected String getRemoteAddr(HttpServletRequest req) {
		String ip = req.getHeader("X-Forwarded-For");
		if ((ip == null) || (ip.length() == 0) || ("unknown".equalsIgnoreCase(ip))) {
			ip = req.getHeader("Proxy-Client-IP");
		}
		if ((ip == null) || (ip.length() == 0) || ("unknown".equalsIgnoreCase(ip))) {
			ip = req.getHeader("WL-Proxy-Client-IP");
		}
		if ((ip == null) || (ip.length() == 0) || ("unknown".equalsIgnoreCase(ip))) {
			ip = req.getHeader("HTTP_CLIENT_IP");
		}
		if ((ip == null) || (ip.length() == 0) || ("unknown".equalsIgnoreCase(ip))) {
			ip = req.getHeader("HTTP_X_FORWARDED_FOR");
		}
		if ((ip == null) || (ip.length() == 0) || ("unknown".equalsIgnoreCase(ip))) {
			ip = req.getRemoteAddr();
		}
		return ip;
	}

	protected void getSysParams(HttpServletRequest req, HashMap<String, String> params)
			throws HttpException, IOException, DocumentException {
		try {
			GetSysParams.getSysParamsFunc(params);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}