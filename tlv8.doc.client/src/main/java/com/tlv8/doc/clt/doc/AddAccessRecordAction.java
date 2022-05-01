package com.tlv8.doc.clt.doc;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tlv8.base.Data;
import com.tlv8.base.ActionSupport;

@Controller
@Scope("prototype")
public class AddAccessRecordAction extends ActionSupport {
	private String param;
	private Data data = new Data();

	public void setData(Data data) {
		this.data = data;
	}

	public Data getData() {
		return data;
	}

	@ResponseBody
	@RequestMapping("/AddAccessRecordAction")
	@Override
	public Object execute() throws Exception {
		try {
			DocAdapter.addAccessRecord(param);
			data.setFlag("true");
		} catch (Exception e) {
			data.setFlag("false");
			data.setMessage(e.toString());
		}
		return data;

	}

	public void setParam(String param) {
		this.param = param;
	}

	public String getParam() {
		return param;
	}
}
