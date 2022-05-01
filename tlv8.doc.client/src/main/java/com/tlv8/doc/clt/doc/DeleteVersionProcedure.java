package com.tlv8.doc.clt.doc;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tlv8.base.Data;
import com.tlv8.base.ActionSupport;

@Controller
@Scope("prototype")
public class DeleteVersionProcedure extends ActionSupport {
	private String sDocPath;
	private String sFileID;
	private String sLogID;
	private String sDocVersion;
	private boolean isHttps;
	private Data data = new Data();

	public void setData(Data data) {
		this.data = data;
	}

	public Data getData() {
		return data;
	}

	@ResponseBody
	@RequestMapping("/DeleteVersionProcedure")
	@Override
	public Object execute() throws Exception {
		data = new Data();
		try {
			DocAdapter.deleteVersion(sDocPath, sFileID, sLogID, sDocVersion,
					isHttps);
			data.setFlag("true");
		} catch (Exception e) {
			data.setFlag("false");
			data.setMessage(e.toString());
		}
		return data;

	}

	public void setSDocPath(String sDocPath) {
		this.sDocPath = sDocPath;
	}

	public String getSDocPath() {
		return sDocPath;
	}

	public void setSFileID(String sFileID) {
		this.sFileID = sFileID;
	}

	public String getSFileID() {
		return sFileID;
	}

	public void setSLogID(String sLogID) {
		this.sLogID = sLogID;
	}

	public String getSLogID() {
		return sLogID;
	}

	public void setSDocVersion(String sDocVersion) {
		this.sDocVersion = sDocVersion;
	}

	public String getSDocVersion() {
		return sDocVersion;
	}

	public void setIsHttps(String isHttps) {
		try {
			this.isHttps = Boolean.valueOf(isHttps);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public boolean getIsHttps() {
		return isHttps;
	}

}
