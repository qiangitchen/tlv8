package com.tlv8.doc.clt.upload;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tlv8.base.db.DBUtils;
import com.tlv8.base.ActionSupport;
import com.tlv8.doc.clt.doc.Doc;
import com.tlv8.doc.clt.doc.DocUtils;
import com.tlv8.doc.clt.doc.Docs;

@Controller
@Scope("prototype")
public class DocCommitFile extends ActionSupport {

	private String docid;

	public void setDocid(String docid) {
		this.docid = docid;
	}

	public String getDocid() {
		return docid;
	}

	@ResponseBody
	@RequestMapping("/docCommitFile")
	@Override
	public Object execute() throws Exception {
		try {
			Doc doc = Docs.queryDocById(docid);
			doc.commitFile();
			DocUtils.saveDocFlag("/root", doc.getsKind(), doc.getsFileID(), doc.getScacheName(), false);
			String fileID = doc.getsFileID();
			DBUtils.execUpdateQuery("system",
					"update SA_docNode set SFILEID = '" + fileID + "' where sID = '" + docid + "'");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return this;
	}

}
