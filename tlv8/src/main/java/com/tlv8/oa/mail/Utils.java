package com.tlv8.oa.mail;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import com.tlv8.base.db.DBUtils;

public class Utils {
	@SuppressWarnings("rawtypes")
	public static String getCount(String db, String sql) {
		String result = "0";
		try {
			List cl = DBUtils.execQueryforList(db, "select count(*) as COUNT from (" + sql + ") a");
			if (cl.size() > 0) {
				Map m = (Map) cl.get(0);
				result = (String) m.get("COUNT");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return result;
	}

	public static String getLimitSql(String limit, String offerset, String sql) {
		String result = "";
		if (limit != null && !"".equals(limit)) {
			if (offerset == null) {
				offerset = "0";
			} else if (offerset.equals("")) {
				offerset = "0";
			}
			result = "select * from (select rownum srownu,r.* from (" + sql + ") r where rownum<=" + limit
					+ ") a where a.srownu >" + offerset;
			if (DBUtils.IsMySQLDB("oa")) {
				result = sql + " limit " + offerset + "," + limit;
			} else if (DBUtils.IsMSSQLDB("oa")) {
				result = sql.replaceFirst("SELECT ", "select top " + limit + " ");
				// result = "select TOP " + limit + " * from (" + sql + ")";
			}
		} else {
			result = "select * from (" + sql + ") where rownum <= 20";
			if (DBUtils.IsMySQLDB("oa")) {
				result = sql + " limit 0,20";
			} else if (DBUtils.IsMSSQLDB("oa")) {
				// result = "select TOP 20 * from (" + sql + ")";
				result = sql.replaceFirst("SELECT ", "select top 20 ");
			}
		}
		return result;
	}
}
