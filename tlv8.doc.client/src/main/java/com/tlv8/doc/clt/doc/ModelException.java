package com.tlv8.doc.clt.doc;

import com.tlv8.base.Sys;

public class ModelException extends Throwable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7656395706468625411L;

	public ModelException(String s) {
		try {
			throw new Exception(s);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public ModelException(Object e) {
		Sys.packErrMsg(e.toString());
	}

}
