package com.tlv8.doc.clt.doc;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.tlv8.base.db.DBUtils;

public class BizData {

	public static List<Map<String, String>> query(String concept, String idColumn, String select, String from,
			String condition, List<DataPermission> range, String filter, Boolean distinct, int offset, int limit,
			String columns, String orderBy, String aggregate, String aggregateColumns, Map<String, Object> variables,
			String dataModel, String fnModel) {
		String sql = "select sID," + columns + " from " + from + " where " + filter;
		List<Map<String, String>> list = new ArrayList<Map<String, String>>();
		try {
			list = DBUtils.execQueryforList("system", sql);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return list;
	}

	public static int save(List<?> table, String concept, List<DataPermission> insertRange,
			List<DataPermission> deleteRange, List<DataPermission> updateRange, String readOnly, String notNull,
			String dataModel, String fnModel) {
		try {
			int count = 0;
			for (int i = 0; i < table.size(); i++) {
				Docinfo row = (Docinfo) table.get(i);
				String sql = "insert into " + concept
						+ "(sID,sKind,sCreatorFID,sCreatorName,sCreateTime,sLastWriterFID,sLastWriterName,sLastWriteTime,version)"
						+ "values('%s','%s','%s','%s',:sCreateTime,'%s','%s',:sLastWriteTime,0)";
				sql = String.format(sql, row.getString("SA_DocNode"), row.getString("sKind"),
						row.getString("sCreatorFID"), row.getString("sCreatorName"), row.getString("sLastWriterFID"),
						row.getString("sLastWriterName"));
				if (DBUtils.IsOracleDB("system")) {
					sql = sql.replace(":sCreateTime", "sysdate");
					sql = sql.replace(":sLastWriteTime", "sysdate");
				} else if (DBUtils.IsMySQLDB("system")) {
					sql = sql.replace(":sCreateTime", "now()");
					sql = sql.replace(":sLastWriteTime", "now()");
				} else {
					sql = sql.replace(":sCreateTime", "getdate()");
					sql = sql.replace(":sLastWriteTime", "getdate()");
				}
				DBUtils.execInsertQuery("system", sql);
			}
			return count;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return 0;
	}
}
