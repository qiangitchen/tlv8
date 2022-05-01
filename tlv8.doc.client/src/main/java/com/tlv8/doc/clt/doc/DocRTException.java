package com.tlv8.doc.clt.doc;

public class DocRTException extends RuntimeException {

	private static final long serialVersionUID = 368123379475140395L;

	public DocRTException() {
		super();
	}

	public DocRTException(String message) {
		super(message);
	}

	public DocRTException(String message, Throwable cause) {
		super(message, cause);
	}

	public DocRTException(Throwable cause) {
		super(cause);
	}
}
