package com.tlv8.doc.clt.doc;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tlv8.base.Data;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.tlv8.base.ActionSupport;

@Controller
@Scope("prototype")
public class CommitDocAction extends ActionSupport {
	private String changeLog;
	private Data data = new Data();

	public void setData(Data data) {
		this.data = data;
	}

	public Data getData() {
		return data;
	}

	@ResponseBody
	@RequestMapping("/commitDocAction")
	@Override
	public Object execute() throws Exception {
		data = new Data();
		try {
			JSONObject item = JSON.parseObject(changeLog);
			String docID = item.getString("docID");
			Doc doc = Docs.queryDocById(docID);
			doc.commitFile();
			data.setData(doc.getsFileID());
			data.setFlag("true");
		} catch (Exception e) {
			data.setFlag("false");
			data.setMessage(e.toString());
			e.printStackTrace();
		}
		return data;

	}

	public void setChangeLog(String changeLog) {
		try {
			this.changeLog = URLDecoder.decode(changeLog, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
	}

	public String getChangeLog() {
		return changeLog;
	}

}
