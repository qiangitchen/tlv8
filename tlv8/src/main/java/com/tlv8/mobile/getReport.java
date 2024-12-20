package com.tlv8.mobile;

import java.net.URLDecoder;
import java.util.List;
import java.util.Map;

import com.tlv8.base.Data;
import com.tlv8.base.db.DBUtils;
import com.tlv8.common.domain.AjaxResult;
import com.alibaba.fastjson.JSON;
import com.tlv8.base.ActionSupport;
import com.tlv8.system.bean.ContextBean;

public class getReport extends ActionSupport {
	private Data data = new Data();
	private String count;
	private String offerset;
	private String limit;
	private String filter;
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
		String where = "";
		if (filter != null) {
			where = " and (" + filter + ") ";
		} else {
			where = "";
		}
		String table = "";
		if ("day".equals(type)) {
			table = "SHOW_OA_RE_DAYREPORT";
		} else if ("week".equals(type)) {
			table = "SHOW_OA_RE_WEEKREPORT";
		} else {
			table = "SHOW_OA_RE_MONTHREPORT";
		}
		String sql = "SELECT T.FID ID,T.FTITLE TITLE,date_format(T.FPUSHDATETIME,'%Y-%m-%d %H:%i:%s') TIME,T.FCREATEPERSONNAME PSNNAME,T.FBROWSE FBROWSE FROM "
				+ table + " T " + " WHERE (FPERSONID='" + context.getCurrentPersonID()
				+ "' and FPUSHDATETIME is not null)" + where + " ORDER BY FPUSHDATETIME DESC";
		try {
			List cl = DBUtils.execQueryforList("oa", "select count(1) as COUNT from (" + sql + ") AS A");
			if (cl.size() > 0) {
				Map m = (Map) cl.get(0);
				count = String.valueOf(m.get("COUNT"));
			}
			if (limit != null && !"".equals(limit)) {
				if (DBUtils.IsOracleDB("oa") || DBUtils.IsDMDB("oa")) {
					sql = "select * from (select rownum srownu,r.* from (" + sql + ")r where rownum<=" + limit
							+ ")a where a.srownu >" + offerset;
				} else if (DBUtils.IsMySQLDB("oa")) {
					sql = "select * from (select r.* from (" + sql + ")r where limit " + offerset + "," + limit;
				}
			} else if (DBUtils.IsOracleDB("oa") || DBUtils.IsDMDB("oa")) {
				sql = "select * from(" + sql + ") AS A where rownum <=10";
			} else if (DBUtils.IsMySQLDB("oa")) {
				sql = "select * from(" + sql + ") AS A limit 0,10";
			}
			List list = DBUtils.execQueryforList("oa", sql);
			data.setData(JSON.toJSONString(list));
			data.setFlag("true");
		} catch (Exception e) {
			data.setFlag("false");
			e.printStackTrace();
		}
		return AjaxResult.success(data);
	}

	public void setCount(String count) {
		this.count = count;
	}

	public String getCount() {
		return count;
	}

	public void setOfferset(String offerset) {
		this.offerset = offerset;
	}

	public String getOfferset() {
		return offerset;
	}

	public void setLimit(String limit) {
		this.limit = limit;
	}

	public String getLimit() {
		return limit;
	}

	public void setFilter(String filter) {
		try {
			this.filter = URLDecoder.decode(filter, "UTF-8");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public String getFilter() {
		return filter;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getType() {
		return type;
	}
}
