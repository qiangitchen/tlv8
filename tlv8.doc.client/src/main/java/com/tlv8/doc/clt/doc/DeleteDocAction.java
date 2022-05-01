package com.tlv8.doc.clt.doc;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tlv8.base.Data;
import com.tlv8.base.ActionSupport;

@Controller
@Scope("prototype")
public class DeleteDocAction extends ActionSupport {
	private String changeLog;
	private Data data = new Data();

	public void setData(Data data) {
		this.data = data;
	}

	public Data getData() {
		return data;
	}

	@ResponseBody
	@RequestMapping("/deleteDocAction")
	@Override
	public Object execute() throws Exception {
		data = new Data();
		try {
			int i = DocAdapter.deleteDocAdapter(changeLog);
			data.setData(String.valueOf(i));
			data.setFlag("true");
		} catch (Exception e) {
			data.setFlag("false");
			data.setMessage(e.toString());
		}
		return data;

	}

	public void setChangeLog(String changeLog) {
		this.changeLog = changeLog;
	}

	public String getChangeLog() {
		return changeLog;
	}

}
