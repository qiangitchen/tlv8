package com.tlv8.oa.utils;

import java.util.List;
import java.util.Map;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tlv8.base.Data;
import com.tlv8.base.db.DBUtils;
import com.alibaba.fastjson.JSON;
import com.tlv8.base.ActionSupport;

@Controller
@Scope("prototype")
public class getNoticePerson extends ActionSupport {
	private String fid = "";
	private Data data;

	public String getFid() {
		return fid;
	}

	public void setFid(String fid) {
		this.fid = fid;
	}

	public Data getData() {
		return data;
	}

	public void setData(Data data) {
		this.data = data;
	}

	@ResponseBody
	@RequestMapping("/getNoticePersonAction")
	public Object execute() throws Exception {
		data = new Data();
		try {
			String sql = "SELECT FID,FPERSONNAME,FBROWSE,FREADDATE FROM OA_NOTICE_PERSON WHERE FMAINID='" + fid + "'";
			List<Map<String, String>> list = DBUtils.execQueryforList("oa", sql);
			data.setData(JSON.toJSONString(list));
			data.setFlag("true");
		} catch (Exception e) {
			data.setFlag("false");
			e.printStackTrace();
		}
		return this;
	}
}
