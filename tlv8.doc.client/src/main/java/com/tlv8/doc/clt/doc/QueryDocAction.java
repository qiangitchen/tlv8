package com.tlv8.doc.clt.doc;

import java.net.URLDecoder;
import java.util.List;
import java.util.Map;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tlv8.base.Data;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.tlv8.base.ActionSupport;

@Controller
@Scope("prototype")
@SuppressWarnings({ "unchecked" })
public class QueryDocAction extends ActionSupport {
	private String docID;
	private String docPath;
	private String pattern;
	private String orderBy;
	private String custom;
	private Data data = new Data();

	public Data getdata() {
		return this.data;
	}

	public void setdata(Data data) {
		this.data = data;
	}

	@ResponseBody
	@RequestMapping("/QueryDocAction")
	@Override
	public Object execute() throws Exception {
		data = new Data();
		String r = "";
		String m = "success";
		String f = "";
		try {
			Docs ds = DocAdapter.queryDoc(docID, docPath, pattern, orderBy, custom);
			List<Map<String, String>> li = ds.getContainer();
			if (li.size() > 0) {
				JSONArray json = JSON.parseArray(JSON.toJSONString(li));
				r = json.toString();
				f = "true";
			} else {
				m = "操作失败：找不到文档信息!";
				f = "false";
			}
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
		try {
			this.docID = URLDecoder.decode(docID, "UTF-8");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public String getDocPath() {
		return docPath;
	}

	public void setDocPath(String docPath) {
		try {
			this.docPath = URLDecoder.decode(docPath, "UTF-8");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public String getPattern() {
		return pattern;
	}

	public void setPattern(String pattern) {
		try {
			this.pattern = URLDecoder.decode(pattern, "UTF-8");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public String getOrderBy() {
		return orderBy;
	}

	public void setOrderBy(String orderBy) {
		try {
			this.orderBy = URLDecoder.decode(orderBy, "UTF-8");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public String getCustom() {
		return custom;
	}

	public void setCustom(String custom) {
		try {
			this.custom = URLDecoder.decode(custom, "UTF-8");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
