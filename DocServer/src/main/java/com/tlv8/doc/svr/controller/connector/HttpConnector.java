package com.tlv8.doc.svr.controller.connector;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.tlv8.doc.svr.controller.inter.RequestHandler;

@SuppressWarnings({ "rawtypes", "unchecked" })
public class HttpConnector {
	HttpServletRequest httpservletrequest;
	List<RequestHandler> handerList;

	@SuppressWarnings("deprecation")
	public HttpConnector(HttpServletRequest paramHttpServletRequest) {
		this.httpservletrequest = paramHttpServletRequest;
		handerList = new ArrayList<RequestHandler>();
		List<Class> hclist = ClassUtil.getAllClassByInterface(
				RequestHandler.class,
				"com.tlv8.doc.svr.controller.handlers");
		for (Class clas : hclist) {
			try {
				handerList.add((RequestHandler) clas.newInstance());
			} catch (Exception e) {
			}
		}
	}

	/*
	 * 响应类必须实现(RequestHandler接口 获取相应的响应类)
	 */
	public RequestHandler getRequestHandler() {
		RequestHandler requesthandler = null;
		String pathinfo = httpservletrequest.getPathInfo();
		for (RequestHandler hander : handerList) {
			if (isMathen("/" + hander.getNamespace() + hander.getPathPattern(),
					pathinfo)) {
				return hander;
			}
		}
		return requesthandler;
	}

	private boolean isMathen(String str1, String str2) {
		boolean res = false;
		if (str2.indexOf("?") > 0) {
			str2 = str2.substring(0, str2.indexOf("?"));
		}
		String[] paten = str1.split("/");
		String[] vale = str2.split("/");
		if(vale.length<paten.length){
			return false;
		}
		List trueler = new ArrayList();
		for (int i = 0; i < paten.length; i++) {
			if ("*".equals(paten[i])) {
				trueler.add(true);
			} else if (paten[i].equals(vale[i])) {
				trueler.add(true);
			}
		}
		res = trueler.size() == paten.length;
		return res;
	}
}
