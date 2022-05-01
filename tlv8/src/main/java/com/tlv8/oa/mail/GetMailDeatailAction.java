package com.tlv8.oa.mail;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.List;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tlv8.base.Data;
import com.tlv8.base.db.DBUtils;
import com.alibaba.fastjson.JSON;
import com.tlv8.base.ActionSupport;
import com.tlv8.system.bean.ContextBean;

@Controller
@Scope("prototype")
public class GetMailDeatailAction extends ActionSupport {
	private Data data = new Data();
	private String rowid;
	private String type;

	public void setData(Data data) {
		this.data = data;
	}

	public Data getData() {
		return data;
	}

	@ResponseBody
	@RequestMapping("/getMailDeatailAction")
	@SuppressWarnings("rawtypes")
	public Object execute() throws Exception {
		ContextBean context = ContextBean.getContext(request);
		String personid = context.getPersonID();
		if (personid == null || "null".equals(personid)) {
			data.setFlag("false");
			data.setMessage("未登录，请重新登录!");
		}
		String filter = "";
		if (rowid == null) {
			filter = "1=2";
		} else {
			filter = "e.FID = '" + rowid + "'";
		}
		String sql = "";
		if (type.equals("收件箱") || type.equals("receive")) {
			sql = "SELECT * FROM OA_EM_RECEIVEEMAIL E WHERE " + filter;
			String sql_update = "update OA_EM_RECEIVEEMAIL set FQUREY = '已查看' where FID ='" + rowid
					+ "' and FQUREY != '已查看'";
			DBUtils.execUpdateQuery("oa", sql_update);
		} else {
			sql = "SELECT * FROM OA_EM_SENDEMAIL E WHERE " + filter;
		}
		try {
			List list = DBUtils.execQueryforList("oa", sql);
			data.setData(JSON.toJSONString(list));
			data.setFlag("true");
		} catch (Exception e) {
			data.setFlag("false");
			e.printStackTrace();
		}
		return this;
	}

	public void setRowid(String rowid) {
		try {
			this.rowid = URLDecoder.decode(rowid, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
	}

	public String getRowid() {
		return rowid;
	}

	public void setType(String type) {
		try {
			this.type = URLDecoder.decode(type, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
	}

	public String getType() {
		return type;
	}
}
