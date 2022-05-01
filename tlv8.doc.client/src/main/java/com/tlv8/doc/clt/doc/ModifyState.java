package com.tlv8.doc.clt.doc;

public class ModifyState {
	public static final String DELETE = "delete";
	public static final String EDIT = "edit";
	public static final String NEW = "new";
	public static final String NONE = "none";

	private String state;

	public ModifyState(String state) {
		this.state = state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getState() {
		return state;
	}
}
