package com.tlv8.doc.clt.doc;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tlv8.base.Data;
import com.tlv8.base.ActionSupport;

@Controller
@Scope("prototype")
public class SyncCustomFiledsAction extends ActionSupport {
	private String sdocID;
	private String isHttps;
	private Data data = new Data();

	public void setData(Data data) {
		this.data = data;
	}

	public Data getData() {
		return data;
	}

	@ResponseBody
	@RequestMapping("/syncCustomFiledsAction")
	@Override
	public Object execute() throws Exception {
		data = new Data();
		try {
			DocAdapter.syncCustomFileds(sdocID, Boolean.valueOf(isHttps));
			data.setFlag("true");
		} catch (Exception e) {
			data.setFlag("false");
			data.setMessage(e.toString());
			e.printStackTrace();
		}
		return data;

	}

	public void setSdocID(String sdocID) {
		this.sdocID = sdocID;
	}

	public String getSdocID() {
		return sdocID;
	}

	public void setIsHttps(String isHttps) {
		this.isHttps = isHttps;
	}

	public String getIsHttps() {
		return isHttps;
	}

}
