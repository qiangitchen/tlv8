package com.tlv8.doc.clt.doc;

import java.sql.Timestamp;

public class DocLog {

	DocLogs docLogs;
	Docinfo info;

	public DocLog(Docinfo r, DocLogs docLogs) {
		this.info = r;
		this.docLogs = docLogs;
	}

	public Docinfo getRow() {
		return this.info;
	}

	public String getsPersonName() {
		return info.getString("sPersonName");
	}

	public void setsPersonName(String sPersonName) {
		info.setString("sPersonName", sPersonName);
	}

	public String getsAccessType() {
		return info.getString("sAccessType");
	}

	public void setsAccessType(String sAccessType) {
		info.setString("sAccessType", sAccessType);
	}

	public String getsDocID() {
		return info.getString("sDocID");
	}

	public void setsDocID(String sDocID) {
		info.setString("sDocID", sDocID);
	}

	public String getsID() {
		return info.getString("SA_DocLog");
	}

	public void setsID(String sID) {
		info.setString("SA_DocLog", sID);
	}

	public Timestamp getsTime() {
		return info.getDateTime("sTime");
	}

	public void setsTime(Timestamp sTime) {
		info.setDateTime("sTime", sTime);
	}

	public String getsPersonFID() {
		return info.getString("sPersonFID");
	}

	public void setsPersonFID(String sPersonFID) {
		info.setString("sPersonFID", sPersonFID);
	}

	public String getsDocName() {
		return info.getString("sDocName");
	}

	public void setsDocName(String sDocName) {
		info.setString("sDocName", sDocName);
	}

	public Integer getsDocVersionID() {
		return info.getInt("sDocVersionID");
	}

	public void setsDocVersionID(int sDocVersionID) {
		info.setInteger("sDocVersionID", sDocVersionID);
	}

	public Float getsSize() {
		return info.getFloat("sSize");
	}

	public void setsSize(float sSize) {
		info.setFloat("sSize", sSize);
	}

	public DocLogs getDocLogs() {
		return docLogs;
	}
}
