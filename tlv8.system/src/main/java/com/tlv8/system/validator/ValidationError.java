package com.tlv8.system.validator;

import com.tlv8.system.help.MessageResource;

public class ValidationError {
	private String code;
	private String nameKey;
	private String messageKey;
	private Object[] params;

	public ValidationError(String code, String nameKey, String messageKey) {
		this.code = code;
		this.nameKey = nameKey;
		this.messageKey = messageKey;
	}

	public ValidationError(String code, String nameKey, String messageKey, Object[] params) {
		this(code, nameKey, messageKey);
		this.params = params;
	}

	public String getCode() {
		return this.code;
	}

	public String getNameKey() {
		return (this.nameKey != null) && (this.nameKey != "") ? this.nameKey : "";
	}

	public String getMessageKey() {
		return this.messageKey;
	}

	public Object[] getParams() {
		return this.params;
	}

	public String toString(String locale) {
		return MessageResource.getMessage(locale, this.messageKey, this.params);
	}
}
