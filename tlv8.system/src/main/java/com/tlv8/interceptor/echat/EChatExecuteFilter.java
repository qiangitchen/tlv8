package com.tlv8.interceptor.echat;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import com.tlv8.base.utils.FileAndString;

public class EChatExecuteFilter {
	/**
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException
	 * @throws ServletException
	 */
	public static boolean doFilter(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		String requestURI = request.getRequestURI();
		if (isCharts(requestURI)) {
			try {
				handleChart(request, response);
			} catch (Exception e) {
				if (e instanceof DocumentException) {
					response.sendError(404);
				} else {
					response.sendError(500);
				}
			}
			return true;
		}
		return false;
	}

	static boolean isCharts(String requestURI) {
		return requestURI.endsWith(".echt");
	}

	static void handleChart(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String hfilepath = request.getServletContext().getRealPath("/resources/echarts/chartsmodle.em");
		String html = FileAndString.FileToString(hfilepath);
		Document doc = getChartFileDoc(request);
		ModleParse mpas = new ModleParse(doc, request);
		String theme = doc.getRootElement().element("chart").attributeValue("theme");
		html = html.replace("{{theme}}", theme);
		String chartoptions = "";
		if (mpas.toJSON().size() > 0) {
			chartoptions = mpas.toJSONString();
		} else {
			chartoptions = mpas.getChartText();
		}
		if ("".equals(chartoptions)) {
			chartoptions = "''";
		}
		html = html.replace("{{chartoptions}}", chartoptions);
		List<Element> scripts = doc.getRootElement().elements("script");
		StringBuilder scrts = new StringBuilder();
		for (int s = 0; s < scripts.size(); s++) {
			String scrt = scripts.get(s).getText();
			scrt = mpas.valueTranseStr(scrt);
			scrts.append(scrt);
		}
		html = html.replace("{{javascripts}}", scrts.toString());
		response.setContentType("text/html");
		response.setCharacterEncoding("UTF-8");
		PrintWriter pw = response.getWriter();
		pw.print(html);
		pw.close();
	}

	static Document getChartFileDoc(HttpServletRequest request) throws DocumentException {
		Document doc = null;
		String ContextPath = request.getContextPath();
		String requestURI = request.getRequestURI();
		String filePath = request.getServletContext().getRealPath(requestURI.replace(ContextPath, ""));
		SAXReader reader = new SAXReader();
		doc = reader.read(filePath);
		return doc;
	}
}
