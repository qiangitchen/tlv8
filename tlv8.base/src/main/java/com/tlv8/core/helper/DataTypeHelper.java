package com.tlv8.core.helper;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.tlv8.base.db.DBUtils;

@SuppressWarnings({ "unchecked", "rawtypes" })
public class DataTypeHelper {

	/**
	 * @since 获取表字段数据类型
	 * @param dbkey
	 * @param table
	 * @param column
	 * @return String
	 */
	@Deprecated
	public static String getColumnDataType(String dbkey, String table, String column) {
		String result = "";
		String sql = "";
		if (DBUtils.IsOracleDB(dbkey)) {
			sql = "select TABLE_NAME,COLUMN_NAME,DATA_TYPE from USER_TAB_COLS t where t.table_name = '"
					+ table.toUpperCase() + "' and COLUMN_NAME = '" + column.toUpperCase() + "'";
		} else if (DBUtils.IsMySQLDB(dbkey)) {
			sql = "select TABLE_NAME,COLUMN_NAME,DATA_TYPE,column_comment as COMMENTS "
					+ "from information_schema.columns where upper(table_name)='" + table.toUpperCase()
					+ "' and upper(COLUMN_NAME)='" + column.toUpperCase() + "'";
		} else {
			sql = "select b.name as TABLE_NAME,a.name as COLUMN_NAME,c.name as DATA_TYPE,"
					+ "d.value as COMMENTS from dbo.syscolumns as a " + "inner join dbo.sysobjects as b on b.id = a.id "
					+ "inner join dbo.systypes as c on a.xtype = c.xtype and c.xusertype = c.xtype "
					+ "left join sys.extended_properties d on d.major_id = a.id and d.minor_id = a.colid "
					+ "where upper(b.name) = '" + table.toUpperCase() + "' and upper(a.name) = '" + column.toUpperCase()
					+ "'";
		}
		try {
			List<Map<String, String>> list = DBUtils.selectStringList(dbkey, sql);
			if (list.size() > 0) {
				Map<String, String> m = list.get(0);
				result = m.get("DATA_TYPE");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	/**
	 * @since 获取表字段数据类型
	 * @param conn
	 * @param table
	 * @param column
	 * @return String
	 */
	public static String getColumnDataType(Connection conn, String table, String column) {
		String result = "";
		String sql = "select " + column + " from " + table + " where 1=2";
		Statement stm = null;
		ResultSet rs = null;
		try {
			stm = conn.createStatement(1004, 1007);
			rs = stm.executeQuery(sql);
			ResultSetMetaData rsmd = rs.getMetaData();
			result = rsmd.getColumnTypeName(1);
		} catch (Exception e) {
		} finally {
			try {
				DBUtils.CloseConn(null, stm, rs);
			} catch (SQLException e) {
			}
		}
		return result;
	}

	/**
	 * @since 获取表字段数据类型
	 * @param dbkey
	 * @param table
	 * @param columns
	 * @return Map<String, String>
	 */
	public static Map<String, String> getColumnsDataType(Connection conn, String table, String[] columns) {
		Map resmap = new HashMap<String, String>();
		for (int i = 0; i < columns.length; i++) {
			String datatype = getColumnDataType(conn, table, columns[i]);
			resmap.put(columns[i], datatype);
		}
		return resmap;
	}

	/**
	 * @since 重复获取数据库连接效率太低从3.8开始弃用
	 * @param dbkey
	 * @param table
	 * @param columns
	 * @return Map
	 */
	@Deprecated
	public static Map<String, String> getColumnsDataType(String dbkey, String table, String[] columns) {
		Map resmap = new HashMap<String, String>();
		for (int i = 0; i < columns.length; i++) {
			String datatype = getColumnDataType(dbkey, table, columns[i]);
			resmap.put(columns[i], datatype);
		}
		return resmap;
	}

	public static String dataValuePase(String dataType, String inval) {
		String resval = null;
		if (("DATE".equals(dataType.toUpperCase()) || "DATETIME".equals(dataType.toUpperCase())) && !"".equals(inval)) {
			try {
				DateFormat dataTimeFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				Date vl1 = dataTimeFormat.parse(inval);
				resval = dataTimeFormat.format(vl1);
			} catch (Exception e) {
				try {
					DateFormat dataFormat = new SimpleDateFormat("yyyy-MM-dd");
					Date vl2 = dataFormat.parse(inval);
					resval = dataFormat.format(vl2);
				} catch (Exception e2) {

				}
			}
		} else if ("INT".equals(dataType.toUpperCase()) || "INTEGER".equals(dataType.toUpperCase())) {
			try {
				int inum = Integer.parseInt(inval);
				resval = String.valueOf(inum);
			} catch (Exception e) {
			}
		} else if ("FLOAT".equals(dataType.toUpperCase()) || "NUMBER".equals(dataType.toUpperCase())) {
			try {
				double inum = Double.parseDouble(inval);
				resval = String.valueOf(inum);
			} catch (Exception e) {
			}
		} else {
			if (inval != null && !"".equals(inval.trim()) && !"null".equals(inval)) {
				resval = inval;
			}
		}
		return resval;
	}
}
