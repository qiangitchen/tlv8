package com.tlv8.doc.svr.controller.handlers;

import java.io.Writer;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.tlv8.doc.svr.core.io.FileUploader;
import com.tlv8.doc.svr.core.io.centent.FileIOContent;
import com.tlv8.doc.svr.controller.data.FileUploadData;
import com.tlv8.doc.svr.controller.impl.AbstractRequestHandler;
import com.tlv8.doc.svr.controller.impl.DoupDoc;

public class FileUploadHandler extends AbstractRequestHandler {

	public String getPathPattern() {
		return "/file/upload";
	}

	public void initHttpHeader(HttpServletResponse paramHttpServletResponse) {
		paramHttpServletResponse.setCharacterEncoding("utf-8");
		paramHttpServletResponse.setContentType("text/xml");
	}

	public void handleRequest(HttpServletRequest paramHttpServletRequest,
			HttpServletResponse paramHttpServletResponse) throws Exception {
		if (paramHttpServletRequest.getMethod().equals("POST")) {
			try {
				FileIOContent rdoc = FileUploader.fileUpload(
						paramHttpServletRequest, new DoupDoc());// 保存文件
				FileUploadData.newDocSave(rdoc);// 保存数据
				SimpleDateFormat format = new SimpleDateFormat(
						"yyyy-MM-dd'T'HH:mm:ss.SSSZ");
				Date cdate = new Date();
				String retstr = "<ns:document id=\""
						+ rdoc.getFileID()
						+ "\" lastModified=\""
						+ format.format(cdate)
						+ "\" lastModifier=\"2\" created=\""
						+ format.format(cdate)
						+ "\" owner=\"3\" private=\"false\" updateCount=\"1\" referenceLanguageId=\"-1\" variantLastModified=\""
						+ format.format(cdate)
						+ "\" variantLastModifier=\"2\" liveVersionId=\"1\" branchId=\"1\" languageId=\"1\" typeId=\"3\" lastVersionId=\"1\" retired=\"false\" newVersionState=\"publish\" newChangeType=\"major\" variantUpdateCount=\"1\" createdFromBranchId=\"-1\" createdFromLanguageId=\"-1\" createdFromVersionId=\"-1\" documentTypeChecksEnabled=\"true\" lastMajorChangeVersionId=\"-1\" liveMajorChangeVersionId=\"-1\" dataVersionId=\"-1\" name=\""
						+ rdoc.getFileName()
						+ "\" validateOnSave=\"true\" xmlns:ns=\"http://outerx.org/daisy/1.0\"><ns:newChangeComment xsi:nil=\"true\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\"/><ns:customFields/><ns:lockInfo hasLock=\"false\"/><ns:collectionIds/><ns:fields/><ns:parts><ns:part typeId=\"1\" size=\""
						+ rdoc.getFileSize()
						+ "\" mimeType=\""
						+ rdoc.getFileType()
						+ "\" dataChangedInVersion=\"1\"/></ns:parts><ns:links/></ns:document>";
				Writer writer = paramHttpServletResponse.getWriter();
				writer.write(retstr);
				writer.close();
			} catch (Exception localException) {
				StringBuilder localObject1 = new StringBuilder(
						"<?xml version=\"1.0\" encoding=\"UTF-8\"?><root>");
				localObject1.append("<flag>false</flag>");
				localObject1.append("<message>");
				localObject1.append("upload doc failure");
				localObject1.append(localException.getMessage());
				localObject1.append("</message>");
				localObject1.append("</root>");
				Writer localObject2 = paramHttpServletResponse.getWriter();
				localObject2.write(localObject1.toString());
				localObject2.close();
			}
		} else {
			paramHttpServletResponse.sendError(405);
		}
	}
}
