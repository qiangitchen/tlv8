package com.tlv8.doc.svr.generator.beans;

import java.util.Date;

public class DocLog {
	private String fID;
	private String fUserID;
	private Date fAddTime;
	private String fAction;
	private String fMessage;
	private int version;
	public String getFID() {
		return fID;
	}
	public void setFID(String fID) {
		this.fID = fID;
	}
	public String getFUserID() {
		return fUserID;
	}
	public void setFUserID(String fUserID) {
		this.fUserID = fUserID;
	}
	public Date getFAddTime() {
		return fAddTime;
	}
	public void setFAddTime(Date fAddTime) {
		this.fAddTime = fAddTime;
	}
	public String getFAction() {
		return fAction;
	}
	public void setFAction(String fAction) {
		this.fAction = fAction;
	}
	public String getFMessage() {
		return fMessage;
	}
	public void setFMessage(String fMessage) {
		this.fMessage = fMessage;
	}
	public int getVersion() {
		return version;
	}
	public void setVersion(int version) {
		this.version = version;
	}
	
}
