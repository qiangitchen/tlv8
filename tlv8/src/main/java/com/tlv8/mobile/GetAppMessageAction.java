package com.tlv8.mobile;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.tlv8.base.Data;
import com.tlv8.base.db.DBUtils;
import com.alibaba.fastjson.JSON;
import com.tlv8.base.ActionSupport;
import com.tlv8.system.bean.ContextBean;

public class GetAppMessageAction extends ActionSupport {
	private Data data = new Data();

	public Data getData() {
		return data;
	}

	public void setData(Data data) {
		this.data = data;
	}

	public String execute() throws Exception {
		String psmid = ContextBean.getContext(request).getPersonID();
		String sql = "select SID,STITLE,SMESSAGE from APP_PUSHMESSAGE where SPERSONID = '" + psmid
				+ "' and SSENDSTATE = 0 and SRESTATE = 0 and rownum <=1 order by SSDATE";
		if (DBUtils.IsMySQLDB("system")) {
			sql = "select SID,STITLE,SMESSAGE from APP_PUSHMESSAGE where SPERSONID = '" + psmid
					+ "' and SSENDSTATE = 0 and SRESTATE = 0 order by SSDATE limit 1";
		} else if (DBUtils.IsMSSQLDB("system")) {
			sql = "select top 1 SID,STITLE,SMESSAGE from APP_PUSHMESSAGE where SPERSONID = '" + psmid
					+ "' and SSENDSTATE = 0 and SRESTATE = 0 order by SSDATE";
		}
		List<Map<String, String>> list = new ArrayList<Map<String, String>>();
		try {
			list = DBUtils.execQueryforList("system", sql);
			data.setData(JSON.toJSONString(list));
			data.setFlag("true");
		} catch (Exception e) {
			data.setFlag("false");
			data.setMessage(e.getMessage());
		} finally {
			if (list.size() > 0) {
				DBUtils.execUpdateQuery("system",
						"update APP_PUSHMESSAGE set SSENDSTATE = 1 where SID = '" + list.get(0).get("SID") + "'");
			}
		}
		return SUCCESS;
	}
}
