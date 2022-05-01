package com.tlv8.doc.svr.controller.handlers;

import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.tlv8.doc.svr.controller.data.DocLoadData;
import com.tlv8.doc.svr.controller.impl.AbstractRequestHandler;

public class DocFileLoadHandler extends AbstractRequestHandler {

	@Override
	public String getPathPattern() {
		return "/doc/init";
	}

	@Override
	public void initHttpHeader(HttpServletResponse paramHttpServletResponse) {

	}

	@Override
	public void handleRequest(HttpServletRequest paramHttpServletRequest,
			HttpServletResponse paramHttpServletResponse) throws Exception {
		paramHttpServletResponse.setCharacterEncoding("utf-8");
		paramHttpServletResponse.setContentType("text/html");
		new DocLoadData().load(paramHttpServletRequest);
		PrintWriter writer = paramHttpServletResponse.getWriter();
		writer.write("<HTML>\n<HEAD>\n<meta http-equiv=\"Content-Type\" content=\"text/html; charset=utf-8\"><TITLE>文档同步");
		writer.write("</TITLE></HEAD>\n<BODY>\n<H2>文档同步完成.</H2>\n</BODY>\n</HTML>");
		writer.close();
	}

}
