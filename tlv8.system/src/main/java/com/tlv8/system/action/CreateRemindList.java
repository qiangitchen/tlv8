package com.tlv8.system.action;

import java.sql.SQLException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.tlv8.base.Data;
import com.tlv8.base.db.DBUtils;
import com.tlv8.system.utils.OrgUtils;

public class CreateRemindList {
	private String count;
	private String filter;

	Data data = new Data();

	public String getCount() {
		return count;
	}

	public void setCount(String count) {
		this.count = count;
	}

	public String getFilter() {
		return filter;
	}

	public void setFilter(String filter) {
		this.filter = filter;
	}

	public Data getData() {
		return data;
	}

	public void setData(Data data) {
		this.data = data;
	}

	@ResponseBody
	@SuppressWarnings({ "rawtypes" })
	public void execute(HttpServletRequest request) throws SQLException {
		String sql = "";
		if (filter != null && !filter.equals("")) {
			filter = "";
		} else {
			filter = "and" + filter;
		}
		OrgUtils orgu = new OrgUtils(request);
		if (DBUtils.IsOracleDB("system") || DBUtils.IsDMDB("system")) {
			sql = "select STITLE,SCONTEXT,SDATETIME,SSTATE from SA_REMINDINFO t " + " where SPERSONID = '"
					+ orgu.getPersonID() + "' and rownum <=" + count + " " + filter
					+ " order by SSTATE asc, SDATETIME asc";
		} else if (DBUtils.IsMySQLDB("system") || DBUtils.IsPostgreSQL("system")) {
			sql = "select STITLE,SCONTEXT,SDATETIME,SSTATE from SA_REMINDINFO t" + " where SPERSONID = '"
					+ orgu.getPersonID() + "' " + filter + " order by SSTATE asc, SDATETIME asc limit " + count;
		} else {
			sql = "select TOP " + count + " * STITLE,SCONTEXT,SDATETIME,SSTATE from SA_REMINDINFO t "
					+ " where SPERSONID = '" + orgu.getPersonID() + "' " + filter
					+ " order by SSTATE asc, SDATETIME asc";
		}
		List list = DBUtils.execQueryforList("system", sql);
		data.setFlag("true");
		data.setData(JSON.toJSONString(list));
	}
}
