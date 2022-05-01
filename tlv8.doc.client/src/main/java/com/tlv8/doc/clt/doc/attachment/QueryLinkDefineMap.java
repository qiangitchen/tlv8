package com.tlv8.doc.clt.doc.attachment;

import java.net.URLDecoder;
import java.util.Map;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tlv8.base.Data;
import com.alibaba.fastjson.JSONObject;
import com.tlv8.base.ActionSupport;

@Controller
@Scope("prototype")
public class QueryLinkDefineMap extends ActionSupport {
	private String linkProcess;
	private String linkActivity;
	private Data data = new Data();

	public void setData(Data data) {
		this.data = data;
	}

	public Data getData() {
		return data;
	}

	@ResponseBody
	@RequestMapping("/QueryLinkDefineMap")
	@Override
	public Object execute() throws Exception {
		try {
			Map<String, Object> m = (Map<String, Object>) AttachmentDefine.queryLinkDefineMap(linkProcess,
					linkActivity);
			JSONObject json = new JSONObject(m);
			data.setData(json.toString());
			data.setFlag("true");
		} catch (Exception e) {
			data.setFlag("false");
			data.setMessage(e.toString());
		}
		return data;

	}

	public void setLinkProcess(String linkProcess) {
		try {
			this.linkProcess = URLDecoder.decode(linkProcess, "UTF-8");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public String getLinkProcess() {
		return linkProcess;
	}

	public void setLinkActivity(String linkActivity) {
		try {
			this.linkActivity = URLDecoder.decode(linkActivity, "UTF-8");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public String getLinkActivity() {
		return linkActivity;
	}

}
