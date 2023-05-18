package com.tlv8.doc.clt.doc;

import com.tlv8.system.utils.ContextUtils;

public class ActionUtils {

	public static Object getRequestContext() {
		return ContextUtils.getContext();
	}

	public static String getSessionID() {
		return ContextUtils.getContext().getBusinessID();
	}

}
