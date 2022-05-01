package com.tlv8.doc.clt.doc;

import com.tlv8.system.bean.ContextBean;
import com.tlv8.system.controller.UserController;

public class ContextHelper {
	private UserController user;

	public ContextHelper() {
		user = new UserController();
	}

	public static ContextBean getPersonMember() {

		return new UserController().getContext();
	}

	public String getPersonFID() {
		return user.getContext().getCurrentPersonFullID();
	}

	public String getPersonName() {
		return user.getContext().getCurrentPersonName();
	}
}
