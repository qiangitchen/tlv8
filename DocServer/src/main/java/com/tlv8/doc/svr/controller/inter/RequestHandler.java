package com.tlv8.doc.svr.controller.inter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface RequestHandler {
	/*
	 * 请求处理程序接口
	 */
	public String getNamespace();

	public String getPathPattern();

	public void initHttpHeader(HttpServletResponse paramHttpServletResponse);

	public void handleRequest(HttpServletRequest paramHttpServletRequest,
			HttpServletResponse paramHttpServletResponse) throws Exception;
	
	public boolean isWin();
}
