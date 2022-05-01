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
public class CreateVersionFromJsonStrAction extends ActionSupport {
	private String billID;
	private String jsonStr;
	private boolean isHttps;
	private String process;
	private String activity;
	private Data data = new Data();

	public void setData(Data data) {
		this.data = data;
	}

	public Data getData() {
		return data;
	}

	@ResponseBody
	@RequestMapping("/CreateVersionFromJsonStrAction")
	@Override
	public Object execute() throws Exception {
		try {
			DocUtils.createVersionFromJsonStr(billID, jsonStr, isHttps,
					process, activity);
			data.setFlag("true");
		} catch (Exception e) {
			data.setFlag("false");
			data.setMessage(e.toString());
		}
		return data;

	}

	public void setBillID(String billID) {
		try {
			this.billID = URLDecoder.decode(billID, "UTF-8");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public String getBillID() {
		return billID;
	}

	public void setJsonStr(String jsonStr) {
		try {
			this.jsonStr = URLDecoder.decode(jsonStr, "UTF-8");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public String getJsonStr() {
		return jsonStr;
	}

	public void setHttps(boolean isHttps) {
		this.isHttps = isHttps;
	}

	public boolean isHttps() {
		return isHttps;
	}

	public void setProcess(String process) {
		try {
			this.process = URLDecoder.decode(process, "UTF-8");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public String getProcess() {
		return process;
	}

	public void setActivity(String activity) {
		try {
			this.activity = URLDecoder.decode(activity, "UTF-8");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public String getActivity() {
		return activity;
	}

}
