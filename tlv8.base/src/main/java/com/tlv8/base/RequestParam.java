package com.tlv8.base;

public class RequestParam {
	private String name;
	private String value;

	public RequestParam(String expstr) {
		name = expstr.substring(0, expstr.indexOf("=")).trim();
		value = expstr.substring(expstr.indexOf("=") + 1).trim();
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}
}
