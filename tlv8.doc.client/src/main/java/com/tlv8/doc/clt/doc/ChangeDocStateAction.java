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
public class ChangeDocStateAction extends ActionSupport {
	private String isLockDoc;
	private String sdocID;
	private Data data = new Data();

	public void setData(Data data) {
		this.data = data;
	}

	public Data getData() {
		return data;
	}

	@ResponseBody
	@RequestMapping("/ChangeDocStateAction")
	@Override
	public Object execute() throws Exception {
		try {
			int re = DocDBHelper.updateDocState(Boolean.valueOf(isLockDoc),
					sdocID);
			data.setData(String.valueOf(re));
			data.setFlag("true");
		} catch (Exception e) {
			data.setFlag("false");
			data.setMessage(e.toString());
		}
		return data;

	}

	public void setIsLockDoc(String isLockDoc) {
		this.isLockDoc = isLockDoc;
	}

	public String getIsLockDoc() {
		return isLockDoc;
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

}
