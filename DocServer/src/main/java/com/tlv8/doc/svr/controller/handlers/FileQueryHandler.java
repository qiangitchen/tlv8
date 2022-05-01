package com.tlv8.doc.svr.controller.handlers;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import com.tlv8.doc.svr.core.io.atr.DocQueryParam;
import com.tlv8.doc.svr.core.io.atr.FileAttribute;
import com.tlv8.doc.svr.lucene.impl.IndexQuery;
import com.alibaba.fastjson.JSON;
import com.tlv8.doc.svr.controller.impl.AbstractRequestHandler;

public class FileQueryHandler extends AbstractRequestHandler {

	@Override
	public String getPathPattern() {
		return "/file/query";
	}

	@Override
	public void initHttpHeader(HttpServletResponse paramHttpServletResponse) {
		paramHttpServletResponse.setCharacterEncoding("utf-8");
	}

	@Override
	public void handleRequest(HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
			throws Exception {
		if (paramHttpServletRequest.getMethod().equals("POST")) {
			if (("text/xml".equals(paramHttpServletRequest.getContentType()))
					|| ("application/xml".equals(paramHttpServletRequest.getContentType()))) {
				handerOldrequest(paramHttpServletRequest, paramHttpServletResponse);// 保留原来的请求方式
			} else if (paramHttpServletRequest.getParameter("keywords") != null) {
				// 自己扩展的搜索方法
				DocQueryParam sparam = new DocQueryParam();
				sparam.setSearchKey(paramHttpServletRequest.getParameter("keywors").split(" "));// 用空格分词
				sparam.setSeachFolder(paramHttpServletRequest.getParameter("seachFolder"));
				try {
					sparam.setStartTime(
							new SimpleDateFormat().parse(paramHttpServletRequest.getParameter("startTime")));
				} catch (Exception e) {
				}
				try {
					sparam.setEndTime(new SimpleDateFormat().parse(paramHttpServletRequest.getParameter("endTime")));
				} catch (Exception e) {
				}
				List<FileAttribute> searchResult = new IndexQuery().searchByParam(sparam);
				try {
					PrintWriter localPrintWriter = paramHttpServletResponse.getWriter();
					localPrintWriter.write(JSON.toJSONString(searchResult));
					localPrintWriter.close();
				} catch (Exception e) {
				}
			} else {
				paramHttpServletResponse.sendError(400, "没有有效的参数提交,请核对接口说明.");
			}
		}
	}

	/*
	 * 处理老的请求方式
	 */
	@Deprecated
	public void handerOldrequest(HttpServletRequest paramHttpServletRequest,
			HttpServletResponse paramHttpServletResponse) throws IOException {
		ServletInputStream localServletInputStream = paramHttpServletRequest.getInputStream();
		try {
			SAXReader localSAXReader = new SAXReader();
			Document localDocument = localSAXReader.read(localServletInputStream);
			String querystr = getNodeText(localDocument, "//form/querySql");
			String keyword = transeKeys(querystr);
			StringBuffer restring = new StringBuffer();
			restring.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
			restring.append("<ns:searchResult xmlns:ns=\"http://outerx.org/daisy/1.0\">");
			restring.append("<ns:titles><ns:title name=\"id\">ID</ns:title>" + "<ns:title name=\"name\">Name</ns:title>"
					+ "<ns:title name=\"lastModified\">Last Modified</ns:title>"
					+ "<ns:title name=\"#sDocId\">sDocId</ns:title>"
					+ "<ns:title name=\"#sDocPath\">sDocPath</ns:title>"
					+ "<ns:title name=\"#sDocDisplayPath\">sDocDisplayPath</ns:title>"
					+ "<ns:title name=\"#sEditorName\">sEditorName</ns:title>"
					+ "<ns:title name=\"#sCreatorName\">sCreatorName</ns:title>"
					+ "<ns:title name=\"#sKeywords\">sKeywords</ns:title>"
					+ "<ns:title name=\"#sLastWriterName\">sLastWriterName</ns:title>"
					+ "<ns:title name=\"#sDescription\">sDescription</ns:title></ns:titles>");
			restring.append("<ns:rows>");
			List<FileAttribute> searchresult = new IndexQuery().searchByKeyWords(keyword);
			for (int i = 0; i < searchresult.size(); i++) {
				restring.append("<ns:row>");
				restring.append("<ns:value>" + searchresult.get(i).getFileID() + "</ns:value>");
				restring.append("</ns:row>");
			}
			restring.append("</ns:rows>");
			restring.append("<ns:resultInfo size=\"" + searchresult.size()
					+ "\" chunkOffset=\"1\" chunkLength=\"100\" requestedChunkLength=\"100\" requestedChunkOffset=\"1\"/>");
			restring.append("<ns:executionInfo>" + "<ns:query>" + querystr + "</ns:query>"
					+ "<ns:locale>zh_CN</ns:locale><ns:parseAndPrepareTime>1</ns:parseAndPrepareTime>"
					+ "<ns:fullTextQueryTime>5</ns:fullTextQueryTime><ns:aclFilterTime>0</ns:aclFilterTime>"
					+ "<ns:sortTime>0</ns:sortTime><ns:outputGenerationTime>1</ns:outputGenerationTime>"
					+ "</ns:executionInfo>");
			restring.append("</ns:searchResult>");
			PrintWriter localPrintWriter = paramHttpServletResponse.getWriter();
			localPrintWriter.write(restring.toString());
			localPrintWriter.close();
		} catch (Exception localException) {
			paramHttpServletResponse.sendError(400, "The post data is not type of \"xml\".");
		}
	}

	private String getNodeText(Document paramDocument, String paramString) {
		Element localElement = (Element) paramDocument.selectNodes(paramString).get(0);
		return localElement.getText();
	}

	private String transeKeys(String querySql) {
		Pattern p = Pattern.compile("'[^\"]*'");
		Matcher m = p.matcher(querySql);
		if (m.find()) {
			String seakey = m.group();
			if (seakey.length() > 2) {
				seakey = seakey.substring(1, seakey.length() - 1);
			}
			return seakey;
		}
		return null;
	}
}
