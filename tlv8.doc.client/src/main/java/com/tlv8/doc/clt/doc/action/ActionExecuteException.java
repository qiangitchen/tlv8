package com.tlv8.doc.clt.doc.action;

public class ActionExecuteException extends RuntimeException {
	private static final long serialVersionUID = -5831321402618435127L;

	public ActionExecuteException() {
	}

	public ActionExecuteException(String paramString) {
		super(paramString);
	}

	public ActionExecuteException(Throwable paramThrowable) {
		super(paramThrowable);
	}

	public ActionExecuteException(String paramString, Throwable paramThrowable) {
		super(paramString, paramThrowable);
	}
}