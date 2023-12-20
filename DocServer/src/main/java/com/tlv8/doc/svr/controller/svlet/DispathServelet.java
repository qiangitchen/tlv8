package com.tlv8.doc.svr.controller.svlet;

import java.io.File;
import java.io.IOException;
import java.io.Writer;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.tlv8.doc.svr.core.config.ServerConfigInit;
import com.tlv8.doc.svr.lucene.LuceneService;
import com.tlv8.doc.svr.controller.connector.HttpConnector;
import com.tlv8.doc.svr.controller.connector.ShttpServletRequest;
import com.tlv8.doc.svr.controller.inter.RequestHandler;

/**
 * 基础相应服务
 */
public class DispathServelet implements Filter {
	protected final Logger infolog = LoggerFactory.getLogger(getClass());

	@Override
	public void destroy() {

	}

	@Override
	public void doFilter(ServletRequest paramHttpServletRequest, ServletResponse paramHttpServletResponse,
			FilterChain chain) throws IOException, ServletException {
		try {
			HttpServletRequest request = (HttpServletRequest) paramHttpServletRequest;
			ShttpServletRequest srequest = new ShttpServletRequest(request);
			HttpServletResponse response = (HttpServletResponse) paramHttpServletResponse;
			String baseContextPath = srequest.getPathInfo();
			paramHttpServletResponse.setCharacterEncoding("utf-8");
			if ((baseContextPath != null) && (baseContextPath.startsWith("/")) && (baseContextPath.length() > 1)
					&& ((baseContextPath.indexOf('/', 1)) != -1)) {
				try {
					RequestHandler requesthandler = new HttpConnector(srequest).getRequestHandler();
					requesthandler.initHttpHeader(response);
					requesthandler.handleRequest(srequest, response);
				} catch (Exception e) {
					chain.doFilter(request, response);
				}
			} else if ((baseContextPath != null) && (baseContextPath.startsWith("/"))
					&& (baseContextPath.length() == 1)) {
				paramHttpServletResponse.setContentType("text/html");
				paramHttpServletResponse.setCharacterEncoding("utf-8");
				Writer writer = paramHttpServletResponse.getWriter();
				writer.write(
						"<HTML>\n<HEAD>\n<meta http-equiv=\"Content-Type\" content=\"text/html; charset=utf-8\"><TITLE>连接成功!");
				writer.write("</TITLE></HEAD>\n<BODY>\n<H2>文档服务已经成功启动.</H2>\n</BODY>\n</HTML>");
				writer.close();
			} else {
				chain.doFilter(request, response);
			}
		} catch (IOException e) {
			infolog.error(e.toString());
			e.printStackTrace();
		}
	}

	@Override
	public void init(FilterConfig servletconfig) throws ServletException {
		try {
			String conPath = servletconfig.getServletContext().getRealPath("/");
			File curfile = new File(conPath);
			String homepath = curfile.getParent() + "../../..";
			File localFile = new File(homepath);
			ServerConfigInit.DOC_HOME = localFile.getParentFile().getParentFile().getParentFile().getCanonicalPath();
			String configFilePath = conPath + "/WEB-INF/doc.xml";
			ServerConfigInit.setConfigFilePath(configFilePath);
			ServerConfigInit.init();
			LuceneService.start();// 启动索引服务
		} catch (Throwable e) {
			infolog.error("启动文档服务异常!" + e);
			e.printStackTrace();
		}
	}
}
