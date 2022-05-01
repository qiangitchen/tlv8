package com.tlv8.doc.svr.generator.beans;

public class SqlParams {
	private String param;

	public String getParam() {
		return param;
	}

	public void setParam(String param) {
		this.param = param == null ? "" : param.trim();
	}
}
