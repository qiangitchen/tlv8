package com.tlv8.doc.clt.doc;

import java.net.URLDecoder;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tlv8.base.Data;
import com.tlv8.base.Sys;
import com.tlv8.base.ActionSupport;

@Controller
@Scope("prototype")
public class CreateVersionAction extends ActionSupport {
	private String sdocID;
	private boolean isSaveDocLink;
	private boolean isHttps;
	private Data data = new Data();

	public void setData(Data data) {
		this.data = data;
	}

	public Data getData() {
		return data;
	}

	@ResponseBody
	@RequestMapping("/CreateVersionAction")
	@Override
	public Object execute() throws Exception {
		try {
			Sys.printMsg("CreateVersionAction sdocID:"+sdocID);
			String re = DocAdapter
					.createVersion(sdocID, isSaveDocLink, isHttps);
			Sys.printMsg("re:"+re);
			data.setData(re);
			data.setFlag("true");
		} catch (Exception e) {
			data.setFlag("false");
			data.setMessage(e.toString());
			e.printStackTrace();
		}
		return data;

	}

	public void setSdocID(String sdocID) {
		try {
			this.sdocID = URLDecoder.decode(sdocID, "UTF-8");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public String getSdocID() {
		return sdocID;
	}

	public void setSaveDocLink(String isSaveDocLink) {
		try {
			this.isSaveDocLink = Boolean.valueOf(isSaveDocLink);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public boolean isSaveDocLink() {
		return isSaveDocLink;
	}

	public void setHttps(String isHttps) {
		try {
			this.isHttps = Boolean.valueOf(isHttps);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public boolean isHttps() {
		return isHttps;
	}

}
