package com.tlv8.doc.svr.controller.handlers;

import java.io.Writer;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

import com.tlv8.doc.svr.generator.beans.DocDocument;
import com.tlv8.doc.svr.generator.service.DocDocumentService;
import com.tlv8.doc.svr.controller.impl.AbstractRequestHandler;

public class FileInfoHandler extends AbstractRequestHandler {
	/*
	 * 保留原有的响应方式不做任何处理
	 * 
	 * @see
	 * com.tlv8.doc.svr.controller.inter.RequestHandler#getPathPattern()
	 */

	@Override
	public String getPathPattern() {
		return "/fileinfo/*/*";
	}

	@Override
	public void initHttpHeader(HttpServletResponse paramHttpServletResponse) {
		paramHttpServletResponse.setCharacterEncoding("utf-8");
	}

	@SuppressWarnings("deprecation")
	@Override
	public void handleRequest(HttpServletRequest paramHttpServletRequest,
			HttpServletResponse paramHttpServletResponse) throws Exception {
		try {
			String fileID = getFileID(paramHttpServletRequest);
			DocDocument doc = DocDocumentService.getDocumentByDocID(fileID);
			Document document = DocumentHelper
					.parseText("<?xml version=\"1.0\" encoding=\"UTF-8\"?><root></root>");
			if (doc != null) {
				Element root = document.getRootElement();
				Element dom = root.addElement("document");
				dom.setAttributeValue("fileId", doc.getFDocID());
				dom.setAttributeValue("name", doc.getFDocName());
				Element ver = root.addElement("version");
				ver.setAttributeValue("documentId", doc.getFDocID());
				ver.setAttributeValue("documentName", doc.getFDocName());
				Element parts = root.addElement("parts");
				Element part = parts.addElement("part");
				part.setAttributeValue("typeId", doc.getFExtName());
				part.setAttributeValue("mimeType", doc.getFDocType());
				part.setAttributeValue("size",
						String.valueOf(doc.getFDocSize()));
				part.setAttributeValue("dataChangedInVersion", "last");
			}
			//System.out.println(document.asXML());
			paramHttpServletResponse.setContentType("text/xml");
			Writer writer = paramHttpServletResponse.getWriter();
			writer.write(document.asXML());
			writer.close();
		} catch (Exception e) {
			paramHttpServletResponse.sendError(400, e.toString());
		}
	}

	private String getFileID(HttpServletRequest paramHttpServletRequest) {
		String fileID = null;
		String pathinfo = paramHttpServletRequest.getPathInfo();
		pathinfo = pathinfo.replace("/repository/fileinfo/", "");
		fileID = pathinfo.substring(0, pathinfo.indexOf("/"));
		if (fileID.indexOf("-") < 0) {
			fileID = fileID + "-root";// 只传数字时处理
		}
		return fileID;
	}

}
