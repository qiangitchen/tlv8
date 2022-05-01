package com.tlv8.doc.clt.doc.attachment;

import com.tlv8.doc.clt.doc.AbstractDoc;
import com.tlv8.doc.clt.doc.CommonUtils;
import com.tlv8.doc.clt.doc.DocRTException;
import com.tlv8.doc.clt.doc.Docinfo;
import com.tlv8.doc.clt.doc.Utils;

public class Attachment extends AbstractDoc {

	private Attachments attachments;
	private DocLinkAttachments docLinkAttachments;

	public DocLinkAttachments getDocLinkAttachments() {
		return docLinkAttachments;
	}

	public Attachment(Docinfo r, Attachments attachments) {
		super((Docinfo) r);
		this.attachments = attachments;
	}

	public Attachment(Docinfo r, DocLinkAttachments attachments2) {
		super(r);
		this.docLinkAttachments = attachments2;
	}

	public Attachments getAttachments() {
		return attachments;
	}

	public String toJsonString() {
		return "{" + "\"docID\":\"" + getsID() + "\",\"docName\":\"" + getsDocName() + "\",\"size\":" + getsSize() + ",\"fileID\":\"" + getsFileID()
				+ "\",\"docPath\":\"" + getsDocPath() + "\",\"time\":\"" + CommonUtils.getCurrentDateTime().toString() + "\"}";
	}

	protected void checkAccess() {
		if (Utils.isNotNull(attachments)) {
			Integer access = attachments.getDefineItems().getAccess();
			if (access <= 1)
				throw new DocRTException("没有下载的权限");
		}
	}

}
