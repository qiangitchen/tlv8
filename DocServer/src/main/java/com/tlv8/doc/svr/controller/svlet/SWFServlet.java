package com.tlv8.doc.svr.controller.svlet;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class SWFServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void service(HttpServletRequest paramHttpServletRequest,
			HttpServletResponse paramHttpServletResponse)
			throws ServletException, IOException {
		paramHttpServletResponse.setContentType("text/xml");
		ServletOutputStream localServletOutputStream = paramHttpServletResponse
				.getOutputStream();
		String str = "<?xml version=\"1.0\"?><cross-domain-policy>\t<allow-access-from domain=\"*\" />\t<allow-http-request-headers-from domain=\"*\" headers=\"Authorization\" /></cross-domain-policy>";
		localServletOutputStream.write(str.getBytes());
	}
}
