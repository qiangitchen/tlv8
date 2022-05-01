package com.tlv8.flw.bean;

import com.alibaba.fastjson.JSONObject;

public class ExpressionBean {
	private String nodeName;
	private String pId;
	private String id;
	private String name;
	private String javacode;
	private String helper;

	public ExpressionBean(JSONObject json) {
		try {
			nodeName = json.getString("nodeName");
			pId = json.getString("pId");
			id = json.getString("id");
			name = json.getString("name");
			javacode = json.getString("javacode");
			helper = json.getString("helper");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public String getNodeName() {
		return nodeName;
	}

	public void setNodeName(String nodeName) {
		this.nodeName = nodeName;
	}

	public String getpId() {
		return pId;
	}

	public void setpId(String pId) {
		this.pId = pId;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getJavacode() {
		return javacode;
	}

	public void setJavacode(String javacode) {
		this.javacode = javacode;
	}

	public String getHelper() {
		return helper;
	}

	public void setHelper(String helper) {
		this.helper = helper;
	}

}
