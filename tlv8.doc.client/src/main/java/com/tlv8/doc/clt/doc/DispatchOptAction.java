package com.tlv8.doc.clt.doc;

import java.net.URLDecoder;

import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tlv8.base.Data;
import com.alibaba.fastjson.JSONArray;
import com.tlv8.base.ActionSupport;

@Controller
@Scope("prototype")
public class DispatchOptAction extends ActionSupport {
	private String param;

	private Data data = new Data();

	public void setData(Data data) {
		this.data = data;
	}

	public Data getData() {
		return data;
	}

	@SuppressWarnings("unchecked")
	@ResponseBody
	@RequestMapping("/dispatchOptAction")
	@Override
	public Object execute() throws Exception {
		data = new Data();
		try {
			Document dom = DocumentHelper.parseText(param);
			JSONArray jsonarray = new JSONArray(DocAdapter.docDispatchOptT(dom));
			data.setData(jsonarray.toString());
			data.setFlag("true");
		} catch (Exception e) {
			data.setFlag("false");
			data.setMessage(e.toString());
			e.printStackTrace();
		}
		return data;

	}

	public void setParam(String param) {
		try {
			this.param = URLDecoder.decode(param, "UTF-8");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public String getParam() {
		return param;
	}

	public static void main(String[] args) {
		DispatchOptAction d = new DispatchOptAction();
		String p = "<data><operate>queryNameSpace</operate></data>";
		d.setParam(p);
		try {
			d.execute();
			System.out.println(d.data.getData());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
