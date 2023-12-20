package com.tlv8.doc.svr.controller.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.tlv8.doc.svr.controller.inter.RequestHandler;

public abstract class AbstractRequestHandler implements RequestHandler {
	protected Logger logger = LoggerFactory.getLogger(getClass());

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
