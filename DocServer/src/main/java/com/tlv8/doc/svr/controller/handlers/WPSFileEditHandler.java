package com.tlv8.doc.svr.controller.handlers;

import java.io.InputStream;
import java.io.PrintWriter;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.tlv8.doc.svr.controller.impl.AbstractRequestHandler;
import com.tlv8.doc.svr.controller.impl.DoupDoc;
import com.tlv8.doc.svr.core.io.FileUploader;
import com.tlv8.doc.svr.core.io.centent.FileIOContent;
import com.tlv8.doc.svr.generator.beans.DocDocPath;
import com.tlv8.doc.svr.generator.beans.DocDocument;
import com.tlv8.doc.svr.generator.service.DocDocPathService;
import com.tlv8.doc.svr.generator.service.DocDocumentService;

public class WPSFileEditHandler extends AbstractRequestHandler {

	@Override
	public String getPathPattern() {
		return "/file/wpsedit/*/*";
	}

	@Override
	public void initHttpHeader(HttpServletResponse paramHttpServletResponse) {
		paramHttpServletResponse.setHeader("Cache-Control", "pre-check=0, post-check=0, max-age=0");
	}

	@Override
	public void handleRequest(HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
			throws Exception {
		String fileID = getFileID(paramHttpServletRequest);
		String user = getUser(paramHttpServletRequest);
		if ("tourist".equals(user)) {
			paramHttpServletResponse.sendError(403);
		}
		try {
			String bizAddress = paramHttpServletRequest.getParameter("bizAddress");
			if (bizAddress != null) {
				DoupDoc doc = new DoupDoc(fileID);
				String docpath = doc.getDocPath();
				if (docpath != null) {
					FileIOContent rdoc = new FileIOContent();
					if (paramHttpServletRequest.getContentLength() > 0) {
						InputStream in = paramHttpServletRequest.getInputStream();
						String nfileID = doc.getNewDocID();
						String ndocPath = doc.getNewDocPath();
						FileUploader.upload(in, nfileID, ndocPath);
						rdoc.setFileID(nfileID);
						rdoc.setFilePath(ndocPath);
						rdoc.setFileSize(paramHttpServletRequest.getContentLength());
						if (rdoc.getFileSize() > 0) {
							FileUploader.ChangeFileID(fileID, rdoc.getFileID(), rdoc.getFilePath());
							DocDocPath ddocpath = DocDocPathService.getDocDocPathByFileID(fileID);
							ddocpath.setFFilePath(rdoc.getFilePath());
							ddocpath.setFFileSize(rdoc.getFileSize());
							ddocpath.setFVersion(ddocpath.getFVersion() + 1);
							DocDocPathService.updateDocDocPath(ddocpath);// 更新路径
							DocDocument ddoc = DocDocumentService.getDocumentByDocID(fileID);
							ddoc.setFDocSize(rdoc.getFileSize());
							ddoc.setFUpdateTime(new Date());
							DocDocumentService.updateDocument(ddoc);// 更新版本信息
						}
					}
				}
				PrintWriter localPrintWriter = paramHttpServletResponse.getWriter();
				localPrintWriter.write("{'status':'ok'}");
				localPrintWriter.close();
			} else {
				paramHttpServletResponse.sendError(405);
			}
		} catch (Exception e) {
			this.requestErrorLogger.error(e);
			paramHttpServletResponse.sendError(500);
		}
	}

	protected String getFileID(HttpServletRequest paramHttpServletRequest) {
		String fileID = null;
		String pathinfo = paramHttpServletRequest.getPathInfo();
		pathinfo = pathinfo.replace("/repository/file/wpsedit/", "");
		if (pathinfo.indexOf("/") > 0) {
			fileID = pathinfo.substring(0, pathinfo.indexOf("/"));
		} else {
			fileID = pathinfo;
		}
		if (fileID.indexOf("-") < 0) {
			fileID = fileID + "-root";// 只传数字时处理
		}
		return fileID;
	}

	protected String getUser(HttpServletRequest paramHttpServletRequest) {
		String user = null;
		String pathinfo = paramHttpServletRequest.getPathInfo();
		pathinfo = pathinfo.replace("/repository/file/wpsedit/", "");
		String[] params = pathinfo.split("/");
		if (params.length > 1) {
			user = params[1];
		} else {
			user = "tourist";
		}
		return user;
	}
}
