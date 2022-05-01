package com.tlv8.doc.svr.controller.handlers;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.tlv8.doc.svr.core.inter.IDoc;
import com.tlv8.doc.svr.core.io.FileDownloader;
import com.tlv8.doc.svr.core.io.FileUploader;
import com.tlv8.doc.svr.core.io.centent.FileIOContent;
import com.tlv8.doc.svr.generator.service.DocService;
import com.tlv8.doc.svr.controller.connector.RequestHandlerSupport;
import com.tlv8.doc.svr.controller.connector.UploadItem;
import com.tlv8.doc.svr.controller.data.FileUploadData;
import com.tlv8.doc.svr.controller.impl.AbstractRequestHandler;
import com.tlv8.doc.svr.controller.impl.DoupDoc;

public class FileCacheOfficeHandler extends AbstractRequestHandler {

	@Override
	public String getPathPattern() {
		return "/file/cache/office/*";
	}

	@Override
	public void initHttpHeader(HttpServletResponse paramHttpServletResponse) {

	}

	@SuppressWarnings({ "unused", "rawtypes" })
	@Override
	public void handleRequest(HttpServletRequest paramHttpServletRequest,
			HttpServletResponse paramHttpServletResponse) throws Exception {
		if (paramHttpServletRequest.getMethod().equals("POST")) {
			Map localHashMap = RequestHandlerSupport.parseMultipartRequest(
					paramHttpServletRequest, paramHttpServletResponse);
			if ((localHashMap.get("EDA_GETSTREAMDATA") != null)
					&& ("EDA_YES".equals(localHashMap.get("EDA_GETSTREAMDATA")))) {
				String FileID = (String) localHashMap.get("FileID");
				String VersionID = (String) localHashMap.get("VersionID");
				String PartType = (String) localHashMap.get("PartType");
				InputStream inputsteream = FileDownloader.download(new DoupDoc(
						FileID));
				downloadStream(inputsteream,
						paramHttpServletResponse.getOutputStream());
			} else if (localHashMap.get("trackdata") != null) {
				String partType = (String) localHashMap.get("partType");
				String cacheName = (String) localHashMap.get("cacheName");
				String fileID = (String) localHashMap.get("fileID");
				String resultID = (String) localHashMap.get("resultID");
				UploadItem uploaditem = (UploadItem) localHashMap
						.get("trackdata");
				FileIOContent iocontent = new FileIOContent();
				if ("content".equals(partType)) {
					String changes = (String) localHashMap.get("changes");
					FileUploadData.saveDocNewVersion(fileID, cacheName);
					iocontent.setFileID("");
				} else {
					IDoc ndoc = new DoupDoc();
					String nfileid = ndoc.getNewDocID();
					String nfilepath = ndoc.getNewDocPath();
					FileUploader.upload(uploaditem.getInputStream(), nfileid,
							nfilepath);
					iocontent.setExtName(uploaditem.getExtName());
					iocontent.setFileID(nfileid);
					iocontent.setFilePath(nfilepath);
					iocontent.setFileName(uploaditem.getFileName());
					iocontent.setFileSize(uploaditem.getSize());
					iocontent.setFileType(uploaditem.getContentType());
					FileUploadData.newDocSave(iocontent);
				}
				if (resultID != null) {
					String localObject2 = "{\"size\":" + uploaditem.getSize()
							+ ",\"cacheName\":\"" + iocontent.getFileID()
							+ "\",\"mediatype\":\""
							+ getMediaType(uploaditem.getExtName()) + "\"}";
					DocService.setCustomField(resultID, localObject2);
				}
				BufferedOutputStream localBufferedOutputStream = null;
				try {
					localBufferedOutputStream = new BufferedOutputStream(
							paramHttpServletResponse.getOutputStream());
					localBufferedOutputStream.write("EDA_STREAMBOUNDARY"
							.getBytes());
					String str5 = "{\"size\":" + uploaditem.getSize()
							+ ",\"cacheName\":\"" + iocontent.getFileID()
							+ "\",\"mediatype\":\""
							+ getMediaType(uploaditem.getExtName()) + "\"}";
					localBufferedOutputStream.write(str5.getBytes());
					localBufferedOutputStream.write("EDA_STREAMBOUNDARY"
							.getBytes());
					localBufferedOutputStream.flush();
				} catch (Exception localException) {
					localException.printStackTrace();
				} finally {
					if (localBufferedOutputStream != null)
						localBufferedOutputStream.close();
				}
			} else {
				throw new Exception(
						"[OfficeViewer Error] 400 Field not found! Request:");
			}
		} else {
			paramHttpServletResponse.sendError(405);
		}
	}

	private String getMediaType(String paramString) {
		String str = "application/octet-stream";
		if (paramString.equalsIgnoreCase(".doc"))
			str = "application/msword";
		else if (paramString.equalsIgnoreCase(".docx"))
			str = "application/vnd.openxmlformats-officedocument.wordprocessingml.document";
		else if (paramString.equalsIgnoreCase(".xls"))
			str = "application/vnd.ms-excel";
		else if (paramString.equalsIgnoreCase(".xlsx"))
			str = "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet";
		else if (paramString.equalsIgnoreCase(".ppt"))
			str = "application/vnd.ms-powerpoint";
		else if (paramString.equalsIgnoreCase(".pptx"))
			str = "application/vnd.openxmlformats-officedocument.presentationml.presentation";
		return str;
	}

	private void downloadStream(InputStream paramInputStream,
			OutputStream paramOutputStream) throws IOException {
		byte[] arrayOfByte = new byte[4096];
		BufferedOutputStream localBufferedOutputStream = null;
		BufferedInputStream localBufferedInputStream = null;
		try {
			localBufferedOutputStream = new BufferedOutputStream(
					paramOutputStream);
			localBufferedInputStream = new BufferedInputStream(paramInputStream);
			localBufferedOutputStream.write("EDA_STREAMBOUNDARY".getBytes());
			int i = -1;
			while ((i = localBufferedInputStream.read(arrayOfByte, 0,
					arrayOfByte.length)) > -1)
				localBufferedOutputStream.write(arrayOfByte, 0, i);
			localBufferedOutputStream.write("EDA_STREAMBOUNDARY".getBytes());
			localBufferedOutputStream.flush();
		} finally {
			if (localBufferedOutputStream != null)
				localBufferedOutputStream.close();
			if (localBufferedInputStream != null)
				localBufferedInputStream.close();
		}
	}

}
