package com.tlv8.mobile;

import java.util.List;

import com.tlv8.base.Data;
import com.tlv8.base.db.DBUtils;
import com.alibaba.fastjson.JSON;
import com.tlv8.base.ActionSupport;
import com.tlv8.system.bean.ContextBean;

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
		String sql = "select FTEXT " + "from OA_EM_ReceiveEmail  e " + " where (" + filter + ")";
		if ("send".equals(type)) {
			sql = "select FTEXT " + "from oa_em_sendemail  e " + " where (" + filter + ")";
		}
		try {
			List list = DBUtils.execQueryforList("oa", sql);
			data.setData(JSON.toJSONString(list));
			data.setFlag("true");
			if ("receive".equals(type)) {
				DBUtils.execUpdateQuery("oa",
						"update OA_EM_ReceiveEmail set FQUREY = '已查看' where FID = '" + rowid + "'");
			}
		} catch (Exception e) {
			data.setFlag("false");
			data.setMessage(e.getMessage());
			e.printStackTrace();
		}
		return this;
	}

	public void setRowid(String rowid) {
		this.rowid = rowid;
	}

	public String getRowid() {
		return rowid;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getType() {
		return type;
	}
}
