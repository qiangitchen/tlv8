package com.tlv8.doc.svr.controller.impl;

import com.tlv8.doc.svr.core.DocSupport;
import com.tlv8.doc.svr.generator.beans.DocDocPath;
import com.tlv8.doc.svr.generator.service.DocDocPathService;
import com.tlv8.doc.svr.generator.service.DocService;

public class DoupDoc extends DocSupport {
	/*
	 * 对doc接口的实现
	 */
	private String docID;

	public DoupDoc() {
	}

	public DoupDoc(String docID) {
		this.docID = docID;
	}

	public String getDocID() {
		return docID;
	}

	public String getDocPath() {
		DocDocPath docp = DocDocPathService.getDocDocPathByFileID(docID);
		return docp.getFFilePath();
	}

	public String getNewDocID() {
		return DocService.getNewDocID();
	}

}
