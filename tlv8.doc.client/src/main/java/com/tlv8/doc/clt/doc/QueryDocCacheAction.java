package com.tlv8.doc.clt.doc;

import java.util.Map;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tlv8.base.Data;
import com.alibaba.fastjson.JSON;
import com.tlv8.base.ActionSupport;

@Controller
@Scope("prototype")
public class QueryDocCacheAction extends ActionSupport {
	private String docID;
	private Data data = new Data();

	public Data getdata() {
		return this.data;
	}

	public void setdata(Data data) {
		this.data = data;
	}

	@ResponseBody
	@RequestMapping("/queryDocCacheAction")
	@Override
	public Object execute() throws Exception {
		data = new Data();
		String r = "";
		String m = "success";
		String f = "";
		try {
			Map<String, String> mp = DocAdapter.queryDocCacheById(docID);
			r = JSON.toJSONString(mp);
			f = "true";
		} catch (Exception e) {
			m = "操作失败：" + e.toString();
			f = "false";
			e.printStackTrace();
		}
		data.setData(r);
		data.setFlag(f);
		data.setMessage(m);
		return data;
	}

	public String getDocID() {
		return docID;
	}

	public void setDocID(String docID) {
		this.docID = docID;
	}

}
