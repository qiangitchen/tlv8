package com.tlv8.doc.svr.controller.connector;

import java.io.InputStream;

public class UploadItem {
	private String name;
	private String fileName;
	private String extName;
	private String contentType;
	private long size;
	private InputStream inputStream;

	public String getName() {
		return name;
	}

	public InputStream getInputStream() {
		return inputStream;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setInputStream(InputStream inputStream) {
		this.inputStream = inputStream;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String filename) {
		this.fileName = filename;
	}

	public String getContentType() {
		return contentType;
	}

	public void setContentType(String contentType) {
		this.contentType = contentType;
	}

	public long getSize() {
		return size;
	}

	public void setSize(long size) {
		this.size = size;
	}

	public String getExtName() {
		return extName;
	}

	public void setExtName(String extName) {
		this.extName = extName;
	}
}
