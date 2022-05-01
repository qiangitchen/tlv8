package com.tlv8.doc.clt.doc;

import com.tlv8.system.controller.UserController;

public class ActionUtils {

	public static Object getRequestContext() {
		return new UserController().getContext();
	}

	public static String getSessionID() {
		return new UserController().getContext().getBusinessID();
	}

}
