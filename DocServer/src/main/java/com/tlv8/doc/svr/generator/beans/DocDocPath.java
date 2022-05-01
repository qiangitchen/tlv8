package com.tlv8.doc.svr.generator.beans;

import java.util.Date;

public class DocDocPath {
	private String fID;
	private String fFileID;
	private String fFilePath;
	private float fFileSize;
	private long fVersion = 1;
	private Date fAddTime;
	private int version;

	public String getFID() {
		return fID;
	}

	public void setFID(String fID) {
		this.fID = fID;
	}

	public String getFFileID() {
		return fFileID;
	}

	public void setFFileID(String fFileID) {
		this.fFileID = fFileID;
	}

	public String getFFilePath() {
		return fFilePath;
	}

	public void setFFilePath(String fFilePath) {
		this.fFilePath = fFilePath;
	}

	public float getFFileSize() {
		return fFileSize;
	}

	public void setFFileSize(float fFileSize) {
		this.fFileSize = fFileSize;
	}

	public long getFVersion() {
		return fVersion;
	}

	public void setFVersion(long fVersion) {
		this.fVersion = fVersion;
	}

	public Date getFAddTime() {
		return fAddTime;
	}

	public void setFAddTime(Date fAddTime) {
		this.fAddTime = fAddTime;
	}

	public int getVersion() {
		return version;
	}

	public void setVersion(int version) {
		this.version = version;
	}

}
