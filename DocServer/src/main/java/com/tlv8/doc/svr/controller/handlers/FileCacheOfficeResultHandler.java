package com.tlv8.doc.svr.controller.handlers;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.tlv8.doc.svr.generator.service.DocService;
import com.tlv8.doc.svr.controller.impl.AbstractRequestHandler;

public class FileCacheOfficeResultHandler extends AbstractRequestHandler {

	@Override
	public String getPathPattern() {
		return "/resultInfo/*";
	}

	@Override
	public void initHttpHeader(HttpServletResponse paramHttpServletResponse) {
		paramHttpServletResponse.setCharacterEncoding("utf-8");
	}

	@Override
	public void handleRequest(HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
			throws Exception {
		Map<String, String> localparam = getParams(paramHttpServletRequest);
		String Field = localparam.get("1");
		String Text = DocService.getCustomField(Field);
		paramHttpServletResponse.setContentType("application/json;charset=UTF-8");
		ServletOutputStream localServletOutputStream = null;
		try {
			localServletOutputStream = paramHttpServletResponse.getOutputStream();
			localServletOutputStream.write(Text.getBytes());
		} catch (Exception localException) {
			localException.printStackTrace();
		}
	}

	private Map<String, String> getParams(HttpServletRequest request) {
		Map<String, String> rmap = new HashMap<String, String>();
		String pathinfo = request.getPathInfo();
		pathinfo = pathinfo.replace("/repository/resultInfo/", "");
		if (pathinfo.indexOf("?") > 0) {
			pathinfo = pathinfo.substring(0, pathinfo.indexOf("?"));
		}
		rmap.put("1", pathinfo.split("/")[0]);
		Map<String, String[]> pmap = request.getParameterMap();
		for (String k : pmap.keySet()) {
			rmap.put(k, pmap.get(k)[0]);
		}
		return rmap;
	}

}
