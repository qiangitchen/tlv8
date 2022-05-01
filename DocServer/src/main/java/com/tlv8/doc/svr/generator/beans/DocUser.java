package com.tlv8.doc.svr.generator.beans;

public class DocUser {
	private String fID;
	private String fLoginID;
	private String fUserName;
	private int fEnable = 1;
	private int version;
	public String getFID() {
		return fID;
	}
	public void setFID(String fID) {
		this.fID = fID;
	}
	public String getFLoginID() {
		return fLoginID;
	}
	public void setFLoginID(String fLoginID) {
		this.fLoginID = fLoginID;
	}
	public String getFUserName() {
		return fUserName;
	}
	public void setFUserName(String fUserName) {
		this.fUserName = fUserName;
	}
	public int getFEnable() {
		return fEnable;
	}
	public void setFEnable(int fEnable) {
		this.fEnable = fEnable;
	}
	public int getVersion() {
		return version;
	}
	public void setVersion(int version) {
		this.version = version;
	}
	
}
