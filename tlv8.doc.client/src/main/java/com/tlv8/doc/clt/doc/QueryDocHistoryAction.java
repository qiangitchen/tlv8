package com.tlv8.doc.clt.doc;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.jdbc.SQL;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.tlv8.base.ActionSupport;
import com.tlv8.base.Data;
import com.tlv8.base.db.DBUtils;

@Controller
@Scope("prototype")
public class QueryDocHistoryAction extends ActionSupport {
	private String docID;
	private Data data = new Data();

	public Data getdata() {
		return this.data;
	}

	public void setdata(Data data) {
		this.data = data;
	}

	@ResponseBody
	@RequestMapping("/queryDocHistoryAction")
	public Object execute() throws Exception {
		data = new Data();
		String r = "";
		String m = "success";
		String f = "";
		try {
			List<Object> param = new ArrayList<>();
			param.add(docID);
			SQL sql = new SQL().SELECT("SDOCVERSIONID,SDOCNAME,SSIZE,SPERSONNAME,SDEPTNAME,STIME");
			sql.FROM("SA_DOCLOG").WHERE("SDOCID=? and SACCESSTYPE != 'download'").ORDER_BY("SDOCVERSIONID asc");
			List<Map<String, String>> li = DBUtils.selectStringList("system", sql.toString(), param);
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
		this.docID = docID;
	}

}
