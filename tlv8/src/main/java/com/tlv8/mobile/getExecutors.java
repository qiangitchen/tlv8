package com.tlv8.mobile;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.List;
import java.util.Map;

import com.tlv8.base.Data;
import com.tlv8.base.db.DBUtils;
import com.alibaba.fastjson.JSON;
import com.tlv8.base.ActionSupport;

public class getExecutors extends ActionSupport {
	private Data data = new Data();
	private String ids;

	public Data getData() {
		return data;
	}

	public void setData(Data data) {
		this.data = data;
	}

	public String getIds() {
		return ids;
	}

	public void setIds(String ids) {
		try {
			this.ids = URLDecoder.decode(ids, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
	}

	public String execute() throws Exception {
		String sql = "select SID,SNAME,v.SFID from SA_OPORG_VIEW v,(select SFID from SA_OPORG_VIEW where SID in (" + ids
				+ ") or SFID in (" + ids
				+ ")) o where v.SFID like o.SFID || '%' and v.SORGKINDID = 'psm' order by SSEQUENCE asc";
		try {
			List<Map<String, String>> list = DBUtils.execQueryforList("system", sql);
			data.setData(JSON.toJSONString(list));
			data.setFlag("true");
		} catch (Exception e) {
			data.setFlag("false");
			e.printStackTrace();
		}
		return SUCCESS;
	}
}
