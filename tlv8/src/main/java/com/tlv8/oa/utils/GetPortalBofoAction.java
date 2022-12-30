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
import com.tlv8.system.bean.ContextBean;

@Controller
@Scope("prototype")
public class GetPortalBofoAction extends ActionSupport {
	private Data data = new Data();

	@ResponseBody
	@RequestMapping("/getPortalBofoAction")
	@Override
	public Object execute() throws Exception {
		String personname = ContextBean.getContext(request).getCurrentPersonName();
		String sqlStr = "select SID,TITLE,ONESELF,CREATED_TIME FROM BO_ENTRY where ONESELF LIKE '%" + personname
				+ "%' order by CREATED_TIME desc";
		if (DBUtils.IsOracleDB("system") || DBUtils.IsDMDB("system")) {
			sqlStr = "select SID,TITLE,ONESELF,CREATED_TIME FROM BO_ENTRY where ONESELF LIKE '%" + personname
					+ "%' order by CREATED_TIME desc";
		} else if (DBUtils.IsMSSQLDB("system")) {
			sqlStr = "select SID,TITLE,ONESELF,CREATED_TIME FROM BO_ENTRY where ONESELF LIKE '%" + personname
					+ "%' order by CREATED_TIME desc";
		}
		try {
			List<Map<String, String>> list = DBUtils.execQueryforList("system", sqlStr);
			data.setData(JSON.toJSONString(list));
			data.setFlag("true");
		} catch (Exception e) {
			data.setFlag("false");
			data.setMessage("加载数据错误!");
			e.printStackTrace();
		}
		return this;
	}

	public Data getData() {
		return data;
	}

	public void setData(Data data) {
		this.data = data;
	}
}