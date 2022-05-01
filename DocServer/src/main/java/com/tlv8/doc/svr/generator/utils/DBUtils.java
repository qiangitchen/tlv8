package com.tlv8.doc.svr.generator.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringWriter;
import java.sql.CallableStatement;
import java.sql.Clob;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.naming.NamingException;

import com.tlv8.doc.svr.generator.dao.IConnectionDao;

@SuppressWarnings({ "unchecked", "rawtypes" })
public class DBUtils {

	// 获取连接
	public static Connection getAppConn() throws Exception {
		IConnectionDao connDao = (IConnectionDao) SpringBeanFactoryUtils.getBean("connectionDao");
		return connDao.getConnection();
	}

	// 查询操作JDBC
	public static List execQueryforList(String sql) throws SQLException {
		ResultSet rs = null;
		ResultSetMetaData rsmd = null;
		List li = new ArrayList();
		Connection aConn = null;
		try {
			aConn = getAppConn();
		} catch (Exception e1) {
			e1 = new NamingException(e1.toString() + "<< SQL:" + sql);
			e1.printStackTrace();
		}
		Statement qry = aConn.createStatement(1004, 1007);
		try {
			rs = qry.executeQuery(sql);
			rsmd = rs.getMetaData();
			int size = rsmd.getColumnCount();
			while (rs.next()) {
				Map sm = new HashMap();
				for (int i = 1; i <= size; i++) {
					String cellType = rsmd.getColumnTypeName(i);
					if ("DATE".equals(cellType) || "DATETIME".equals(cellType)) {
						try {
							try {
								SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
								String v_l = format.format(format.parse(rs.getString(i)));
								sm.put(rsmd.getColumnName(i), v_l);
							} catch (Exception e) {
								try {
									SimpleDateFormat format = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
									SimpleDateFormat nformat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
									String v_l = nformat.format(format.parse(rs.getString(i)));
									sm.put(rsmd.getColumnName(i), v_l);
								} catch (Exception er) {
									SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
									String v_l = format.format(format.parse(rs.getString(i)));
									sm.put(rsmd.getColumnName(i), v_l);
								}
							}
						} catch (Exception e) {
							sm.put(rsmd.getColumnName(i), "");
						}
					} else if ("BLOB".equals(cellType)) {
						try {
							sm.put(rsmd.getColumnName(i), rs.getBlob(i));
						} catch (Exception e) {
							sm.put(rsmd.getColumnName(i), null);
							Sys.printMsg(e.toString());
						}
					} else if ("CLOB".equals(cellType)) {
						Clob clob = rs.getClob(i);
						String content = "";
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
						sm.put(rsmd.getColumnName(i), content);
					} else {
						sm.put(rsmd.getColumnName(i), (rs.getString(i) == null) ? "" : String.valueOf(rs.getString(i)));
					}
				}
				li.add(sm);
			}
		} catch (SQLException e) {
			Sys.printMsg(e.toString());
			throw new SQLException(e.toString() + ">>\n sql:" + sql);
		} finally {
			CloseConn(aConn, qry, rs);
		}
		return li;
	}

	/**
	 * 获取通用查询列表。
	 * 
	 * @param sql
	 *            查询sql
	 * @param listStr
	 *            显示数据字段名
	 * @return 查询字符串
	 */
	public static List<Map<String, String>> execQueryList(String sql, List<String> listStr, List<String> listprms,
			List<String> listprms1) {
		Connection connection = null;
		List result = new ArrayList<Map<String, String>>();
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			connection = getAppConn();
			if (connection != null) {
				ps = connection.prepareStatement(sql);
				if (listprms != null && listprms.size() > 0) {
					for (int k = 0; k < listprms.size(); k++) {
						if (listprms1 != null && listprms1.size() > 0) {
							if (listprms1.get(k).equals("1")) {
								ps.setString(k + 1, "%" + listprms.get(k) + "%");
							} else if (listprms1.get(k).equals("int")) {
								ps.setInt(k + 1, Integer.parseInt(listprms.get(k)));
							} else if (listprms1.get(k).equals("dou")) {
								ps.setDouble(k + 1, Double.parseDouble(listprms.get(k)));
							} else {
								ps.setString(k + 1, listprms.get(k));
							}
						} else {
							ps.setString(k + 1, listprms.get(k));
						}
					}
				}
				rs = ps.executeQuery();
				while (rs.next()) {
					Map msp = new HashMap();
					for (int i = 0; i < listStr.size(); i++) {
						String key = listStr.get(i).toString();
						msp.put(key, rs.getString(key));
					}

					result.add(msp);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				CloseConn(connection, ps, rs);
			} catch (SQLException e) {
				// e.printStackTrace();
			}
		}
		return result;
	}

	// 查詢操作[为了兼容老版本]不推荐使用
	public static ResultSet execQuery(Connection aConn, String aSQL) throws SQLException {
		ResultSet result = null;
		Statement qry = aConn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
		try {
			result = qry.executeQuery(aSQL);
			return result;
		} catch (SQLException e) {
			throw new SQLException(e);
		} finally {
		}
	}

	// 跟新操作
	public static String execUpdateQuery(String sql) throws SQLException {
		String result = "success";
		Connection conn = null;
		PreparedStatement ps = null;
		try {
			conn = getAppConn();
			ps = conn.prepareStatement(SqlToprepare.transeSql(sql));
			StringArray rearray = SqlToprepare.getParamValues(sql);
			for (int i = 0; i < rearray.getLength(); i++) {
				ps.setString(i + 1, rearray.get(i));
			}
			ps.executeUpdate();
		} catch (Exception e) {
			result = e.toString();
			Sys.packErrMsg("sql:" + sql);
			throw new SQLException(e);
		} finally {
			CloseConn(conn, ps, null);
		}
		return result;
	}

	// 跟新操作
	public static String execUpdate(String sql, List list) throws SQLException {
		Connection connection = null;
		PreparedStatement ps = null;
		String result = "false";
		try {
			// 获取数据源 得到数据库连接
			connection = getAppConn();
			if (connection != null) {
				ps = connection.prepareStatement(sql);
				for (int i = 0; i < list.size(); i++) {
					ps.setString((i + 1), list.get(i).toString());
				}
				boolean flag = ps.execute();
				result = String.valueOf(flag);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			CloseConn(connection, ps, null);
		}
		return result;
	}

	// 插入操作
	public static String execInsertQuery(String sql) throws SQLException {
		String result = "success";
		Connection conn = null;
		PreparedStatement ps = null;
		try {
			conn = getAppConn();
			ps = conn.prepareStatement(SqlToprepare.transeSql(sql));
			StringArray rearray = SqlToprepare.getParamValues(sql);
			for (int i = 0; i < rearray.getLength(); i++) {
				ps.setString(i + 1, rearray.get(i));
			}
			ps.executeUpdate();
		} catch (Exception ep) {
			result = ep.toString();
			Sys.packErrMsg("sql:" + sql);
			throw new SQLException(ep);
		} finally {
			try {
				CloseConn(conn, ps, null);
			} catch (SQLException er) {
			}
		}
		return result;
	}

	// 删除操作
	public static String execdeleteQuery(String sql) throws SQLException {
		String result = "success";
		Connection conn = null;
		Statement stm = null;
		try {
			conn = getAppConn();
			stm = conn.createStatement();
			stm.executeUpdate(sql);
		} catch (Exception e) {
			result = e.toString();
			Sys.packErrMsg("sql:" + sql);
			throw new SQLException(e);
		} finally {
			CloseConn(conn, stm, null);
		}
		return result;
	}

	// 关闭连接
	public static void CloseConn(Connection conn, Statement stm, ResultSet rs) throws SQLException {
		try {
			if (rs != null)
				rs.close();
			if (stm != null)
				stm.close();
			if (conn != null)
				conn.close();
		} catch (SQLException e) {
			throw new SQLException(e.toString());
		}
	}

	// 调用存储过程; aParams为分号分割的字符串参数值列表, 所有参数只能是字符串类型, 且只能是in类型
	public static String callProc(String aProcName, String aParamValues) {
		String result;
		result = "调用存储过程";
		System.out.println(String.format("DBUtils.callProc(aProcName:%s, aParamValues:%s)", aProcName, aParamValues));
		String[] values = aParamValues.split(";");
		try {
			Connection conn = DBUtils.getAppConn();
			try {
				String s1 = "";
				for (int i = 1; i <= values.length; i++) {
					s1 += ",?";
				}
				if (s1.length() > 0)
					s1 = s1.substring(1);
				String sql = String.format("{ call %s(%s,?) }", aProcName, s1);
				CallableStatement proc = conn.prepareCall(sql);

				try {
					for (int i = 1; i <= values.length; i++) {
						proc.setString(i, values[i - 1]);
						proc.registerOutParameter(3, Types.VARCHAR);
					}
					proc.execute();
					result += "->" + proc.getString(3);
//					System.out.println(result);
				} finally {
					proc.close();
				}
				return result;
			} finally {
				conn.close();
			}
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

}
