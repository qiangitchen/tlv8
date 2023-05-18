package com.tlv8.doc.clt.doc;

import com.tlv8.system.bean.ContextBean;
import com.tlv8.system.utils.ContextUtils;

public class ContextHelper {

	public static ContextBean getPersonMember() {
		return ContextUtils.getContext();
	}

	public String getPersonFID() {
		return ContextUtils.getContext().getCurrentPersonFullID();
	}

	public String getPersonName() {
		return ContextUtils.getContext().getCurrentPersonName();
	}
}
