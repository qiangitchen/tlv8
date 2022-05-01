package com.tlv8.doc.svr.controller.svlet;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.tlv8.doc.svr.core.config.ServerConfigInit;

public class LogServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void service(HttpServletRequest paramHttpServletRequest,
			HttpServletResponse paramHttpServletResponse)
			throws ServletException, IOException {
		String str1 = paramHttpServletRequest.getParameter("logName");
		if (str1 == null)
			str1 = "docServer.html";
		String str2 = ServerConfigInit.DOC_HOME;
		File localFile = new File(str2 + File.separator + str1);
		if (localFile.exists()) {
			ServletOutputStream localServletOutputStream = paramHttpServletResponse
					.getOutputStream();
			byte[] arrayOfByte = new byte[131072];
			FileInputStream localFileInputStream = new FileInputStream(
					localFile);
			try {
				int i;
				while ((i = localFileInputStream.read(arrayOfByte)) != -1)
					localServletOutputStream.write(arrayOfByte, 0, i);
			} finally {
				localFileInputStream.close();
			}
		}
	}
}
