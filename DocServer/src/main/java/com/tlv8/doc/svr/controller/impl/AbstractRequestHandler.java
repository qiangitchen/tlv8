package com.tlv8.doc.svr.controller.impl;

import org.apache.log4j.Logger;

import com.tlv8.doc.svr.controller.inter.RequestHandler;

public abstract class AbstractRequestHandler implements RequestHandler {
	protected Logger requestErrorLogger = Logger.getLogger(getClass());

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.tlv8.doc.svr.controller.inter.RequestHandler#getNamespace()
	 */
	public String getNamespace() {
		return "repository";
	}

	public boolean isWin() {
		String ns = System.getProperty("os.name");
		return ns.toLowerCase().contains("windows");
	}
}
