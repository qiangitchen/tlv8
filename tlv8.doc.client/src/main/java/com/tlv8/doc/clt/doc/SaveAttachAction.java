package com.tlv8.doc.clt.doc;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tlv8.base.Data;
import com.tlv8.base.ActionSupport;

@Controller
@Scope("prototype")
public class SaveAttachAction extends ActionSupport {
	private String changeLog;
	private String isSaveDocLink;
	private Data data = new Data();

	public void setData(Data data) {
		this.data = data;
	}

	public Data getData() {
		return data;
	}

	@ResponseBody
	@RequestMapping("/saveAttachAction")
	@Override
	public Object execute() throws Exception {
		data = new Data();
		try {
			int i = DocAdapter.saveAttachAdapter(changeLog, Boolean
					.valueOf(isSaveDocLink));
			data.setData(String.valueOf(i));
			data.setFlag("true");
		} catch (Exception e) {
			data.setFlag("false");
			data.setMessage(e.toString());
		} catch (ModelException e) {
			data.setFlag("false");
			data.setMessage(e.toString());
		}
		return data;

	}

	public void setChangeLog(String changeLog) {
		try {
			this.changeLog = changeLog;
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public String getChangeLog() {
		return changeLog;
	}

	public void setIsSaveDocLink(String isSaveDocLink) {
		this.isSaveDocLink = isSaveDocLink;
	}

	public String getIsSaveDocLink() {
		return isSaveDocLink;
	}

}
