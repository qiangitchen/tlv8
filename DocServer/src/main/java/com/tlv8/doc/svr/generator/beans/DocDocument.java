package com.tlv8.doc.svr.generator.beans;

import java.util.Date;

public class DocDocument {
	private String fID;
	private String fDocID;
	private String fDocName;
	private String fExtName;
	private float fDocSize;
	private String fDocType;
	private Date fAddTime;
	private Date fUpdateTime;
	private int version;
	
	public String getFID() {
		return fID;
	}
	public void setFID(String fID) {
		this.fID = fID;
	}
	public String getFDocID() {
		return fDocID;
	}
	public void setFDocID(String fDocID) {
		this.fDocID = fDocID;
	}
	public String getFDocName() {
		return fDocName;
	}
	public void setFDocName(String fDocName) {
		this.fDocName = fDocName;
	}
	public String getFExtName() {
		return fExtName;
	}
	public void setFExtName(String fExtName) {
		this.fExtName = fExtName;
	}
	public float getFDocSize() {
		return fDocSize;
	}
	public void setFDocSize(float fDocSize) {
		this.fDocSize = fDocSize;
	}
	public String getFDocType() {
		return fDocType;
	}
	public void setFDocType(String fDocType) {
		this.fDocType = fDocType;
	}
	public Date getFAddTime() {
		return fAddTime;
	}
	public void setFAddTime(Date fAddTime) {
		this.fAddTime = fAddTime;
	}
	public Date getFUpdateTime() {
		return fUpdateTime;
	}
	public void setFUpdateTime(Date fUpdateTime) {
		this.fUpdateTime = fUpdateTime;
	}
	public int getVersion() {
		return version;
	}
	public void setVersion(int version) {
		this.version = version;
	}
	
}
