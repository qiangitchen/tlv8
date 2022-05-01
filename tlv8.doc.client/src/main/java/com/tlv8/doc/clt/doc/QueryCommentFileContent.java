package com.tlv8.doc.clt.doc;

import java.net.URLDecoder;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tlv8.base.Data;
import com.tlv8.base.ActionSupport;

@Controller
@Scope("prototype")
public class QueryCommentFileContent extends ActionSupport {
	private String isHttps;
	private String docPath;
	private String fileID;
	private String docVersionID;
	private Data data = new Data();

	public void setData(Data data) {
		this.data = data;
	}

	public Data getData() {
		return data;
	}

	@ResponseBody
	@RequestMapping("/QueryCommentFileContent")
	@Override
	public Object execute() throws Exception {
		try {
			String rs = DocAdapter.queryCommentFileContent(Boolean
					.valueOf(isHttps), docPath, fileID, docVersionID);
			data.setData(rs);
			data.setFlag("true");
		} catch (Exception e) {
			data.setFlag("false");
			data.setMessage(e.toString());
		}
		return data;

	}

	public void setIsHttps(String isHttps) {
		this.isHttps = isHttps;
	}

	public String getIsHttps() {
		return isHttps;
	}

	public void setDocPath(String docPath) {
		try {
			this.docPath = URLDecoder.decode(docPath, "UTF-8");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public String getDocPath() {
		return docPath;
	}

	public void setFileID(String fileID) {
		try {
			this.fileID = URLDecoder.decode(fileID, "UTF-8");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public String getFileID() {
		return fileID;
	}

	public void setDocVersionID(String docVersionID) {
		try {
			this.docVersionID = URLDecoder.decode(docVersionID, "UTF-8");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public String getDocVersionID() {
		return docVersionID;
	}

}
