package com.tlv8.jgrid;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringWriter;
import java.sql.Blob;
import java.sql.Clob;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.naming.NamingException;

import com.tlv8.base.Sys;
import com.tlv8.base.db.DBUtils;
import com.tlv8.base.CodeUtils;
import com.tlv8.base.utils.NumberUtils;

/**
 * 
 * @author 陈乾
 *
 */
public class Grid {
	/*
	 * @备注：用于创建grid形式的表格 支持ResultSet 和 HashMap<String,List<String>>
	 * 
	 * @创建日期：2011-10-27
	 * 
	 * @陈乾
	 */
	public static String createGrid(ResultSet rs, boolean master, boolean showindex, int startrow, String gridID)
			throws SQLException {
		StringBuilder result = new StringBuilder();
		ResultSetMetaData rsmd = rs.getMetaData();
		int size = rsmd.getColumnCount();
		startrow = (startrow == 0) ? 1 : startrow;
		while (rs.next()) {
			String VERSION = rs.getString("VERSION");
			if (VERSION == null) {
				VERSION = "0";
			}
			result.append("<tr id='" + rs.getString(1) + "' version='" + VERSION + "'>");
			if (master)
				result.append("<td align='center' class='grid_td'><input type='checkbox' id='" + rs.getString(1)
						+ "_checkbox'></td>");
			if (showindex)
				result.append("<td align='center' class='grid_td'><div class='gridValue'>" + startrow + "</div></td>");
			for (int i = 2; i <= size; i++) {
				String type = rsmd.getColumnTypeName(i);
				if ("VERSION".equals(rsmd.getColumnLabel(i).toUpperCase())) {
					continue;
				}
				if ("INTEGER".equals(type.toUpperCase()) || "INT".equals(type.toUpperCase())
						|| "LONG".equals(type.toUpperCase())) {
					String Cellvalue = rs.getString(i) != null ? String.valueOf(rs.getLong(i)) : "";
					result.append("<td class='grid_td' title='" + Cellvalue + "' align='right'>"
							+ "<div class='gridValue'>" + Cellvalue + "</div></td>");
				} else if ("NUMBER".equals(type.toUpperCase()) || "FLOAT".equals(type.toUpperCase())
						|| "DECIMAL".equals(type.toUpperCase()) || "DOUBLE".equals(type.toUpperCase())) {
					String Cellvalue = rs.getString(i) != null ? NumberUtils.d2s(rs.getDouble(i)) : "";
					result.append("<td class='grid_td' title='" + Cellvalue + "' align='right'>"
							+ "<div class='gridValue'>" + Cellvalue + "</div></td>");
				} else if ("DATE".equals(type.toUpperCase()) || "DATETIME".equals(type.toUpperCase()) || "TIMESTAMP".equals(type.toUpperCase())) {
					String Cellvalue = rs.getString(i) != null ? rs.getString(i) : "";
					try {
						Cellvalue = dateTimeFormat(Cellvalue);
					} catch (Exception e) {
						try {
							Cellvalue = dateFormat(Cellvalue);
						} catch (Exception te) {
						}
					}
					result.append(
							"<td class='grid_td' title='" + (Cellvalue != null ? Cellvalue : "") + "' align='center'>"
									+ "<div class='gridValue'>" + (Cellvalue != null ? Cellvalue : "") + "</div></td>");
				} else if ("BLOB".equals(type.toUpperCase())) {
					try {
						Blob Cellvalue = rs.getBlob(i);
						result.append("<td class='grid_td' title='" + (Cellvalue != null ? Cellvalue : "")
								+ "' align='left'>" + "<div class='gridValue'>" + (Cellvalue != null ? Cellvalue : "")
								+ "</div></td>");
					} catch (Exception e) {
						result.append("<td class='grid_td' align='left'></td>");
						Sys.printMsg(e.getMessage());
					}
				} else if ("CLOB".equals(type.toUpperCase())) {
					Clob clob = rs.getClob(i);
					String content = "";
					String Cellvalue = "";
					if (clob != null) {
						BufferedReader in = new BufferedReader(clob.getCharacterStream());
						StringWriter out = new StringWriter();
						int c;
						try {
							while ((c = in.read()) != -1) {
								out.write(c);
							}
						} catch (IOException e) {
							e.printStackTrace();
						}
						content = out.toString();
					}
					if (!"".equals(content)) {
						Cellvalue = CodeUtils.decodeSpechars(content);
					}
					result.append("<td class='grid_td' title='" + Cellvalue + "' align='left'>"
							+ "<div class='gridValue'>" + Cellvalue + "</div></td>");
				} else {
					String Cellvalue = "";
					Cellvalue = rs.getString(i);
					if (Cellvalue != null) {
						Cellvalue = CodeUtils.decodeSpechars(Cellvalue);
						Cellvalue = Cellvalue.replace("<", "&lt;");
						Cellvalue = Cellvalue.replace(">", "&gt;");
						Cellvalue = Cellvalue.replace("#apos;", "'");
					}
					SimpleDateFormat fmt = new SimpleDateFormat("yyyy-MM-dd");
					try {
						fmt.parse(Cellvalue);
						result.append("<td class='grid_td' title='" + (Cellvalue != null ? Cellvalue : "")
								+ "' align='center'>" + "<div class='gridValue'>" + (Cellvalue != null ? Cellvalue : "")
								+ "</div></td>");
					} catch (Exception e) {
						result.append("<td class='grid_td' title='" + (Cellvalue != null ? Cellvalue : "")
								+ "' align='left'>" + "<div class='gridValue'>" + (Cellvalue != null ? Cellvalue : "")
								+ "</div></td>");
					}
				}
			}
			result.append("<td class='grid_td'/>");
			result.append("</tr>");
			startrow++;
		}
		return result.toString();
	}
	
	/*
	 * 格式化为日期时间字符串{yyyy-MM-dd HH:mm:ss}
	 */
	public static String dateTimeFormat(String dv) throws Exception {
		Date CellvalueD = null;
		String Cellvalue = "";
		SimpleDateFormat fmt = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		try {
			CellvalueD = fmt.parse(dv);
			Cellvalue = fmt.format(CellvalueD);
		} catch (Exception e) {
			SimpleDateFormat nfmt = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
			try {
				CellvalueD = nfmt.parse(dv);
				Cellvalue = fmt.format(CellvalueD);
			} catch (ParseException e1) {
				throw e;
			}
		}
		return Cellvalue;
	}

	/*
	 * 格式化为日期字符串 {yyyy-MM-dd}
	 */
	public static String dateFormat(String dv) throws Exception {
		Date CellvalueD = null;
		String Cellvalue = "";
		SimpleDateFormat fmt = new SimpleDateFormat("yyyy-MM-dd");
		try {
			CellvalueD = fmt.parse(dv);
			Cellvalue = fmt.format(CellvalueD);
		} catch (Exception e) {
			SimpleDateFormat nfmt = new SimpleDateFormat("MM/dd/yyyy");
			try {
				CellvalueD = nfmt.parse(dv);
				Cellvalue = fmt.format(CellvalueD);
			} catch (ParseException e1) {
				throw e;
			}
		}
		return Cellvalue;
	}

	@Deprecated
	public static String createGrid(Map<String, List<String>> m, boolean master, boolean showindex, int startrow,
			String gridID) throws SQLException {
		StringBuilder result = new StringBuilder();
		Set<String> set = m.keySet();
		Iterator<String> it = set.iterator();
		while (it.hasNext()) {
			String key = it.next();
			result.append("<tr id='" + key + "'>");
			if (master)
				result.append(
						"<td align='center' class='grid_td'><input type='checkbox' id='" + key + "_checkbox'></td>");
			if (showindex)
				result.append("<td align='center' class='grid_td'><div class='gridValue'>" + startrow + "</div></td>");
			final List<String> lit = (List<String>) m.get(key);
			for (int i = 0; i < lit.size(); i++) {
				String Cellvalue = lit.get(i);
				if (Cellvalue != null) {
					Cellvalue = CodeUtils.decodeSpechars(Cellvalue);
					Cellvalue = Cellvalue.replace("<", "&lt;");
					Cellvalue = Cellvalue.replace(">", "&gt;");
				}
				result.append("<td class='grid_td' title='" + (Cellvalue != null ? Cellvalue : "") + "' align='center'>"
						+ "<div class='gridValue'>" + (Cellvalue != null ? Cellvalue : "") + "</div></td>");
			}
			result.append("<td class='grid_td'/>");
			result.append("</tr>");
		}
		return result.toString();
	}

	/*
	 * @陈乾
	 * 
	 * @备注：通过sql生成grid
	 * 
	 * @参数：dbkey：数据连接，sql：查询语句，master：grid是否多选，showindex：是否显示序号，startrow：开始行数
	 */
	public static String createGridBysql(String dbkey, String sql, boolean master, boolean showindex, int startrow,
			String gridID) throws SQLException, NamingException {
		String result = "";
		Connection conn = null;
		Statement stm = null;
		ResultSet rs = null;
		try {
			conn = DBUtils.getSession(dbkey).getConnection();
			stm = conn.createStatement();
			rs = stm.executeQuery(sql);
			result = createGrid(rs, master, showindex, startrow, gridID);
		} catch (SQLException e) {
			System.err.println("SQL:" + sql);
			throw new SQLException(e.getMessage());
		} finally {
			try {
				DBUtils.closeConn(conn, stm, rs);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return result;
	}

}