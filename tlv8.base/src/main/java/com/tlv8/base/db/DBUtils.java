package com.tlv8.base.db;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.io.StringWriter;
import java.sql.Blob;
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
import java.util.Base64;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.ExecutorType;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.apache.log4j.Logger;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import com.tlv8.base.Sys;
import com.tlv8.base.db.dao.UtilsMapper;
import com.tlv8.base.utils.NumberUtils;

/**
 * 数据库操作基础类
 * 
 * @author 陈乾
 *
 */
@SuppressWarnings({ "unchecked", "rawtypes" })
public class DBUtils {
	public static String cfgext = ".mybatis.xml";

	private static Map<String, SqlSessionFactory> dbsource = new HashMap<String, SqlSessionFactory>();

	private static Map<String, Map<String, String>> dbconfig = new HashMap<String, Map<String, String>>();

	private static Map<Connection, SqlSession> openedconn = new HashMap<Connection, SqlSession>();

	private static Logger logger = Logger.getLogger(DBUtils.class);

	static {
		String path = DBUtils.class.getClassLoader().getResource("").getFile();
		File folder = new File(path);
		String[] cfgname = folder.list(new FilenameFilter() {
			public boolean accept(File dir, String name) {
				return name.endsWith(cfgext);
			}
		});
		for (int i = 0; i < cfgname.length; i++) {
			try {
				String key = cfgname[i].replace(cfgext, "");
				Reader reader = Resources.getResourceAsReader(cfgname[i]);
				SqlSessionFactory sessionFactory = new SqlSessionFactoryBuilder().build(reader);
				dbsource.put(key, sessionFactory);
				SAXReader saxreader = new SAXReader();
				Document document = saxreader.read(Resources.getResourceAsReader(cfgname[i]));
				List<Element> pps = document.getRootElement().element("environments").element("environment")
						.element("dataSource").elements("property");
				Map<String, String> prpt = new HashMap<String, String>();
				for (int p = 0; p < pps.size(); p++) {
					Element pel = pps.get(p);
					prpt.put(pel.attributeValue("name"), pel.attributeValue("value"));
				}
				dbconfig.put(key, prpt);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	public static Map<String, Map<String, String>> getDBConfig() {
		return dbconfig;
	}

	/**
	 * 获取数据库连接 Session
	 * 
	 * @param dbkey
	 * @return SqlSession
	 * @see org.apache.ibatis.session.SqlSession
	 */
	public static SqlSession getSession(String dbkey) {
		logger.debug(dbkey);
		if (dbsource.containsKey(dbkey)) {
			return dbsource.get(dbkey).openSession(ExecutorType.BATCH, false);
		}
		return null;
	}

	/**
	 * 判断数据库类型PostgreSQL数据库
	 * 
	 * @param key
	 * @return boolean
	 */
	public static boolean IsPostgreSQL(String key) {
		Map<String, String> m = dbconfig.get(key);
		String url = (String) m.get("url");
		return url.toUpperCase().indexOf((":postgresql:").toUpperCase()) > 0 ? true : false;
	}

	/**
	 * 判断数据库类型达梦数据库
	 * 
	 * @param key
	 * @return boolean
	 */
	public static boolean IsDMDB(String key) {
		Map<String, String> m = dbconfig.get(key);
		String url = (String) m.get("url");
		return url.toUpperCase().indexOf((":dm:").toUpperCase()) > 0 ? true : false;
	}

	/**
	 * 判断数据库类型金仓数据库
	 * 
	 * @param key
	 * @return boolean
	 */
	public static boolean IsKingDB(String key) {
		Map<String, String> m = dbconfig.get(key);
		String url = (String) m.get("url");
		return url.toUpperCase().indexOf(("kingbase").toUpperCase()) > 0 ? true : false;
	}

	/**
	 * 判断数据库类型ORACLE
	 * 
	 * @param key
	 * @return boolean
	 */
	public static boolean IsOracleDB(String key) {
		Map<String, String> m = dbconfig.get(key);
		String url = m.get("url");
		return url.toUpperCase().indexOf(("oracle").toUpperCase()) > 0 ? true : false;
	}

	/**
	 * 判断数据库类型MYSQL
	 * 
	 * @param key
	 * @return boolean
	 */
	public static boolean IsMySQLDB(String key) {
		Map<String, String> m = dbconfig.get(key);
		String url = (String) m.get("url");
		return url.toUpperCase().indexOf(("mysql").toUpperCase()) > 0 ? true : false;
	}

	/**
	 * 判断数据库类型SQL Server
	 * 
	 * @param key
	 * @return boolean
	 */
	public static boolean IsMSSQLDB(String key) {
		Map<String, String> m = dbconfig.get(key);
		String url = (String) m.get("url");
		return url.toUpperCase().indexOf(("jtds").toUpperCase()) > 0 ? true : false;
	}

	/**
	 * 获取连接
	 * 
	 * @param key
	 * @return Connection
	 * @throws SQLException
	 * @see java.sql.Connection
	 */
	@Deprecated
	public static Connection getAppConn(String key) throws SQLException {
		SqlSession session = getSession(key);
		Connection conn = session.getConnection();
		conn.setAutoCommit(true);
		openedconn.put(conn, session);
		return conn;
	}

	/**
	 * 查询操作JDBC
	 * 
	 * @param key
	 * @param sql
	 * @return List
	 * @throws SQLException
	 * @see java.util.List
	 */
	public static List<Map<String, String>> execQueryforList(String key, String sql) throws SQLException {
		ResultSet rs = null;
		ResultSetMetaData rsmd = null;
		List li = new ArrayList();
		SqlSession session = getSession(key);
		Connection aConn = session.getConnection();
		Statement qry = aConn.createStatement(1004, 1007);
		try {
			rs = qry.executeQuery(sql);
			rsmd = rs.getMetaData();
			int size = rsmd.getColumnCount();
			while (rs.next()) {
				Map<String, String> sm = new HashMap<String, String>();
				for (int i = 1; i <= size; i++) {
					String cellType = rsmd.getColumnTypeName(i);
					String columnName = rsmd.getColumnLabel(i);
					if ("DATE".equals(cellType) || "DATETIME".equals(cellType) || "TIMESTAMP".equals(cellType)) {
						try {
							try {
								SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
								String v_l = format.format(format.parse(rs.getString(i)));
								sm.put(columnName, v_l);
							} catch (Exception e) {
								try {
									SimpleDateFormat format = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
									SimpleDateFormat nformat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
									String v_l = nformat.format(format.parse(rs.getString(i)));
									sm.put(columnName, v_l);
								} catch (Exception er) {
									SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
									String v_l = format.format(format.parse(rs.getString(i)));
									sm.put(columnName, v_l);
								}
							}
						} catch (Exception e) {
							sm.put(columnName, "");
						}
					} else if ("BLOB".equals(cellType)) {
						try {
							sm.put(columnName, convertBlobToBase64String(rs.getBlob(i)));
						} catch (Exception e) {
							sm.put(columnName, null);
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
						sm.put(columnName, content);
					} else if ("INTEGER".equals(cellType) || "INT".equals(cellType) || "LONG".equals(cellType)) {
						sm.put(columnName, (rs.getString(i) == null) ? "" : String.valueOf(rs.getLong(i)));
					} else if ("NUMBER".equals(cellType) || "FLOAT".equals(cellType) || "DECIMAL".equals(cellType)
							|| "DOUBLE".equals(cellType)) {
						sm.put(columnName, (rs.getString(i) == null) ? "" : NumberUtils.d2s(rs.getDouble(i)));
					} else {
						sm.put(columnName, (rs.getString(i) == null) ? "" : String.valueOf(rs.getString(i)));
					}
				}
				li.add(sm);
			}
		} catch (SQLException e) {
			Sys.printMsg(e.toString());
			session.close();
			throw new SQLException(e.toString() + ">>\n sql:" + sql);
		} finally {
			closeConn(session, aConn, qry, rs);
		}
		return li;
	}

	/**
	 * blob转base64
	 * 
	 * @param blob
	 * @return String
	 */
	public static String convertBlobToBase64String(Blob blob) {
		String result = "";
		if (null != blob) {
			try {
				InputStream msgContent = blob.getBinaryStream();
				ByteArrayOutputStream output = new ByteArrayOutputStream();
				byte[] buffer = new byte[100];
				int n = 0;
				while (-1 != (n = msgContent.read(buffer))) {
					output.write(buffer, 0, n);
				}
				result = new String(Base64.getEncoder().encode(output.toByteArray()));
				output.close();
			} catch (SQLException e) {
				e.printStackTrace();
			} catch (Exception e) {
				e.printStackTrace();
			}
			return result;
		} else {
			return null;
		}
	}

	/**
	 * 查询数据返回list
	 * 
	 * @param key
	 * @param sql
	 * @return list
	 * @throws SQLException
	 * @see java.util.List
	 */
	public static List<Map<String, Object>> selectForList(String key, String sql) throws SQLException {
		SqlSession session = getSession(key);
		List li = new ArrayList();
		try {
			li = session.selectList(UtilsMapper.select, sql);
		} catch (Exception e) {
			Sys.printErr(e);
			throw new SQLException(RegexUtil.getSubOraex(e.getMessage()));
		} finally {
			session.close();
		}
		return li;
	}

	/**
	 * 获取通用查询列表。
	 * 
	 * @param sql     查询sql
	 * @param listStr 显示数据字段名
	 * @return List&lt;Map&lt;String, String&gt;&gt;
	 * @see java.util.List
	 * @see java.util.Map
	 */
	public static List<Map<String, String>> execQueryList(String dbkey, String sql, List<String> listStr,
			List<String> listprms, List<String> listprms1) {
		List result = new ArrayList<Map<String, String>>();
		SqlSession session = getSession(dbkey);
		Connection connection = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			connection = session.getConnection();
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
			closeConn(session, connection, ps, rs);
		}
		return result;
	}

	/**
	 * 查詢操作[为了兼容老版本]不推荐使用
	 * 
	 * @param aConn
	 * @param aSQL
	 * @return ResultSet
	 * @throws SQLException
	 * @see java.sql.ResultSet
	 */
	@Deprecated
	public static ResultSet execQuery(Connection aConn, String aSQL) throws SQLException {
		ResultSet result = null;
		Statement qry = aConn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
		try {
			result = qry.executeQuery(aSQL);
			return result;
		} catch (SQLException e) {
			throw new SQLException(e);
		}
	}

	/**
	 * 更新操作
	 * 
	 * @param dbkey
	 * @param sql
	 * @return String
	 * @throws SQLException
	 */
	public static String execUpdateQuery(String dbkey, String sql) throws SQLException {
		String result = "success";
		SqlSession session = getSession(dbkey);
		try {
			session.update(UtilsMapper.update, sql);
			session.commit();
		} catch (Exception e) {
			Sys.printErr(e);
			throw new SQLException(RegexUtil.getSubOraex(e.getMessage()));
		} finally {
			session.close();
		}
		return result;
	}

	/**
	 * 更新操作
	 * 
	 * @param dbkey
	 * @param sql
	 * @param list
	 * @return String
	 * @throws SQLException
	 */
	public static String execUpdate(String dbkey, String sql, List<String> list) throws SQLException {
		SqlSession session = getSession(dbkey);
		Connection connection = null;
		PreparedStatement ps = null;
		String result = "false";
		try {
			connection = session.getConnection();
			if (connection != null) {
				ps = connection.prepareStatement(sql);
				for (int i = 0; i < list.size(); i++) {
					ps.setString((i + 1), list.get(i));
				}
				boolean flag = ps.execute();
				result = String.valueOf(flag);
			}
		} catch (Exception e) {
			Sys.printErr(e);
			throw new SQLException(RegexUtil.getSubOraex(e.getMessage()));
		} finally {
			closeConn(session, connection, ps, null);
		}
		return result;
	}

	/**
	 * 插入操作 -单表操作可用
	 * 
	 * @param dbkey
	 * @param sql
	 * @return String
	 * @throws SQLException
	 */
	public static String execInsertQuery(String dbkey, String sql) throws SQLException {
		String result = "success";
		SqlSession session = getSession(dbkey);
		try {
			session.insert(UtilsMapper.insert, sql);
			session.commit();
		} catch (Exception e) {
			Sys.printErr(e);
			throw new SQLException(RegexUtil.getSubOraex(e.getMessage()));
		} finally {
			session.close();
		}
		return result;
	}

	/**
	 * 删除操作 -单表操作可用
	 * 
	 * @param dbkey
	 * @param sql
	 * @return String
	 * @throws SQLException
	 */
	public static String execdeleteQuery(String dbkey, String sql) throws SQLException {
		String result = "success";
		SqlSession session = getSession(dbkey);
		try {
			session.delete(UtilsMapper.delete, sql);
			session.commit();
		} catch (Exception e) {
			Sys.printErr(e);
			throw new SQLException(RegexUtil.getSubOraex(e.getMessage()));
		} finally {
			session.close();
		}
		return result;
	}



	public static void closeConn(Connection conn, Statement stm, ResultSet rs) throws SQLException {
		try {
			if (rs != null)
				rs.close();
			if (stm != null)
				stm.close();
			if (conn != null)
				conn.close();
		} catch (SQLException e) {
			throw new SQLException(e.toString());
		} finally {
			try {
				if (openedconn.containsKey(conn)) {
					openedconn.get(conn).close();
					openedconn.remove(conn);
				}
			} catch (Exception e) {
			}
		}
	}

	/**
	 * 关闭数据库连接
	 *
	 * @param conn
	 * @param stm
	 * @param rs
	 * @throws SQLException
	 */
	@Deprecated
	public static void CloseConn(Connection conn, Statement stm, ResultSet rs) throws SQLException {
		closeConn(conn, stm, rs);
	}

	/**
	 * 关闭数据库连接
	 * 
	 * @param session
	 * @param conn
	 * @param stm
	 * @param rs
	 * @see org.apache.ibatis.session.SqlSession
	 * @see java.sql.Connection
	 * @see java.sql.Statement
	 * @see java.sql.ResultSet
	 */
	public static void closeConn(SqlSession session, Connection conn, Statement stm, ResultSet rs) {
		try {
			if (session != null) {
				session.commit(true);
			}
		} catch (Exception e) {
		}
		try {
			if (rs != null)
				rs.close();
		} catch (Exception e) {
		}
		try {
			if (stm != null)
				stm.close();
		} catch (Exception e) {
		}
		try {
			if (conn != null)
				conn.close();
		} catch (Exception e) {
		}
		try {
			if (session != null)
				session.close();
		} catch (Exception e) {
		}
	}

	/**
	 * 关闭数据库连接
	 * @param session
	 * @param conn
	 * @param stm
	 * @param rs
	 */
	@Deprecated
	public static void CloseConn(SqlSession session, Connection conn, Statement stm, ResultSet rs) {
		closeConn(session, conn, stm, rs);
	}

	/**
	 * 调用存储过程; aParams为分号分割的字符串参数值列表, 所有参数只能是字符串类型, 且只能是in类型
	 * 
	 * @param aProcName
	 * @param aParamValues
	 * @return String
	 */
	public static String callProc(String aProcName, String aParamValues) throws Exception {
		return callProc("system", aProcName, aParamValues);
	}

	/**
	 * 调用存储过程; aParams为分号分割的字符串参数值列表, 所有参数只能是字符串类型, 且只能是in类型
	 * 
	 * @param dbkey
	 * @param aProcName
	 * @param aParamValues
	 * @return String
	 */
	public static String callProc(String dbkey, String aProcName, String aParamValues) throws Exception {
		String result;
		result = "调用存储过程";
		logger.debug(String.format("com.tlv8.base.db.DBUtils.callProc(aProcName:%s, aParamValues:%s)", aProcName,
				aParamValues));

		String[] values = aParamValues.split(";");
		try {
			SqlSession session = getSession(dbkey);
			Connection conn = session.getConnection();
			CallableStatement proc = null;
			try {
				String s1 = "";
				for (int i = 1; i <= values.length; i++) {
					s1 += ",?";
				}
				if (s1.length() > 0)
					s1 = s1.substring(1);
				String sql = String.format("{ call %s(%s,?) }", aProcName, s1);
				proc = conn.prepareCall(sql);
				for (int i = 1; i <= values.length; i++) {
					proc.setString(i, values[i - 1]);
					proc.registerOutParameter(3, Types.VARCHAR);
				}
				proc.execute();
				result += "->" + proc.getString(3);
				logger.debug(result);
			} finally {
				closeConn(session, conn, proc, null);
			}
		} catch (Exception e) {
			throw new Exception(e);
		}
		return result;
	}

	/**
	 * 查询数据
	 * 
	 * @param session
	 * @param sql
	 * @return List&lt;Map&lt;String, Object&gt;&gt;
	 */
	public static List<Map<String, Object>> selectList(SqlSession session, String sql) {
		List<Map<String, Object>> rlist = session.selectList(UtilsMapper.select, sql);
		return rlist;
	}

	/**
	 * 查询数据 -带参数
	 * 
	 * @param session
	 * @param sql
	 * @param params
	 * @return List&lt;Map&lt;String, Object&gt;&gt;
	 * @throws Exception
	 */
	public static List<Map<String, Object>> selectList(SqlSession session, String sql, Object[] params)
			throws Exception {
		List<Object> ps = new ArrayList<Object>();
		for (Object o : params) {
			ps.add(o);
		}
		List<Map<String, Object>> rlist = selectList(session, sql, ps);
		return rlist;
	}

	/**
	 * 查询数据 -带参数
	 * 
	 * @param session
	 * @param sql
	 * @param params
	 * @return List&lt;Map&lt;String, Object&gt;&gt;
	 * @throws Exception
	 */
	private static List<Map<String, Object>> selectList(SqlSession session, String sql, List<Object> params)
			throws Exception {
		List<Map<String, Object>> rlist = new ArrayList<Map<String, Object>>();
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			conn = session.getConnection();
			logger.debug(sql);
			ps = conn.prepareStatement(sql);
			for (int i = 0; i < params.size(); i++) {
				ps.setObject(i + 1, params.get(i));
			}
			logger.debug(params);
			rs = ps.executeQuery();
			ResultSetMetaData rsmd = rs.getMetaData();
			int cns = rsmd.getColumnCount();
			while (rs.next()) {
				Map<String, Object> map = new HashMap<String, Object>();
				for (int n = 1; n <= cns; n++) {
					map.put(rsmd.getColumnLabel(n), rs.getObject(n));
				}
				rlist.add(map);
			}
			logger.debug(rlist);
		} catch (Exception e) {
			logger.error(e);
			throw e;
		}
		return rlist;
	}

	/**
	 * 查询数据 -字段内容返回String类型
	 * 
	 * @param dbname
	 * @param sql
	 * @return List&lt;Map&lt;String, Object&gt;&gt;
	 * @throws Exception
	 */
	public static List<Map<String, String>> selectStringList(String dbname, String sql) throws Exception {
		return selectStringList(dbname, sql, null);
	}

	/**
	 * 查询数据 -字段内容返回String类型
	 * 
	 * @param session
	 * @param sql
	 * @return List&lt;Map&lt;String, String&gt;&gt;
	 * @throws Exception
	 */
	public static List<Map<String, String>> selectStringList(SqlSession session, String sql) throws Exception {
		return selectStringList(session, sql, null);
	}

	/**
	 * 查询数据 -带参数 字段内容返回String类型
	 * 
	 * @param dbname
	 * @param sql
	 * @param params
	 * @return List&lt;Map&lt;String, Object&gt;&gt;
	 * @throws Exception
	 */
	public static List<Map<String, String>> selectStringList(String dbname, String sql, List<Object> params)
			throws Exception {
		SqlSession session = getSession(dbname);
		List<Map<String, String>> rlist = new ArrayList<Map<String, String>>();
		try {
			logger.debug(dbname);
			rlist = selectStringList(session, sql, params);
		} catch (Exception e) {
			logger.error(e);
			throw e;
		} finally {
			session.close();
		}
		return rlist;
	}

	/**
	 * 
	 * @param session
	 * @param sql
	 * @param params
	 * @return List&lt;Map&lt;String, Object&gt;&gt;
	 * @throws Exception
	 */
	public static List<Map<String, String>> selectStringList(SqlSession session, String sql, List<Object> params)
			throws Exception {
		List<Map<String, String>> rlist = new ArrayList<Map<String, String>>();
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			conn = session.getConnection();
			logger.debug(sql);
			ps = conn.prepareStatement(sql);
			if (params != null) {
				for (int i = 0; i < params.size(); i++) {
					ps.setObject(i + 1, params.get(i));
				}
			}
			logger.debug(params);
			rs = ps.executeQuery();
			ResultSetMetaData rsmd = rs.getMetaData();
			int cns = rsmd.getColumnCount();
			while (rs.next()) {
				Map<String, String> map = new HashMap<String, String>();
				for (int n = 1; n <= cns; n++) {
					map.put(rsmd.getColumnLabel(n), rs.getString(n));
				}
				rlist.add(map);
			}
			logger.debug(rlist);
		} catch (Exception e) {
			logger.error(e);
			throw e;
		} finally {
			closeConn(null, ps, rs);
		}
		return rlist;
	}

	/**
	 * 查询数据 -带参数
	 * 
	 * @param dbname
	 * @param sql
	 * @param params
	 * @return List&lt;Map&lt;String, Object&gt;&gt;
	 */
	public static List<Map<String, Object>> selectList(String dbname, String sql, List<Object> params) {
		SqlSession session = getSession(dbname);
		List<Map<String, Object>> rlist = new ArrayList<Map<String, Object>>();
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			logger.debug(dbname);
			conn = session.getConnection();
			logger.debug(sql);
			ps = conn.prepareStatement(sql);
			for (int i = 0; i < params.size(); i++) {
				ps.setObject(i + 1, params.get(i));
			}
			logger.debug(params);
			rs = ps.executeQuery();
			ResultSetMetaData rsmd = rs.getMetaData();
			int cns = rsmd.getColumnCount();
			while (rs.next()) {
				Map<String, Object> map = new HashMap<String, Object>();
				for (int n = 1; n <= cns; n++) {
					map.put(rsmd.getColumnLabel(n), rs.getObject(n));
				}
				rlist.add(map);
			}
			logger.debug(rlist);
		} catch (Exception e) {
			logger.error(e);
			e.printStackTrace();
		} finally {
			closeConn(session, conn, ps, rs);
		}
		return rlist;
	}

	/**
	 * 插入数据 并自动提交
	 * 
	 * @param dbname
	 * @param sql
	 * @return int
	 */
	public static int excuteInsert(String dbname, String sql) throws Exception {
		SqlSession session = getSession(dbname);
		int r = 0;
		try {
			r = session.update(UtilsMapper.insert, sql);
			session.commit();
		} catch (Exception e) {
			Sys.printErr(e);
			throw new SQLException(RegexUtil.getSubOraex(e.getMessage()));
		} finally {
			session.close();
		}
		return r;
	}

	/**
	 * 插入数据
	 * 
	 * @param session
	 * @param sql
	 * @return int
	 */
	public static int excuteInsert(SqlSession session, String sql) throws Exception {
		try {
			int r = session.update(UtilsMapper.insert, sql);
			return r;
		} catch (Exception e) {
			Sys.printErr(e);
			throw new SQLException(RegexUtil.getSubOraex(e.getMessage()));
		}
	}

	/**
	 * 数据插入 统一事务
	 * 
	 * @param session
	 * @param sql
	 * @param params
	 * @return int
	 */
	public static int excuteInsert(SqlSession session, String sql, List<Object> params) throws Exception {
		return excuteUpdate(session, sql, params);
	}

	/**
	 * 删除数据 并自动提交
	 * 
	 * @param dbname
	 * @param sql
	 * @return int
	 */
	public static int excuteDelete(String dbname, String sql) throws Exception {
		SqlSession session = getSession(dbname);
		int r = 0;
		try {
			r = session.update(UtilsMapper.update, sql);
			session.commit();
		} catch (Exception e) {
			Sys.printErr(e);
			throw new SQLException(RegexUtil.getSubOraex(e.getMessage()));
		} finally {
			session.close();
		}
		return r;
	}

	/**
	 * 删除数据
	 * 
	 * @param session
	 * @param sql
	 * @return int
	 */
	public static int excuteDelete(SqlSession session, String sql) throws Exception {
		try {
			int r = session.update(UtilsMapper.update, sql);
			return r;
		} catch (Exception e) {
			Sys.printErr(e);
			throw new SQLException(RegexUtil.getSubOraex(e.getMessage()));
		}
	}

	/**
	 * 更新数据 并自动提交
	 * 
	 * @param dbname
	 * @param sql
	 * @return int
	 */
	public static int excuteUpdate(String dbname, String sql) throws Exception {
		SqlSession session = getSession(dbname);
		int r = 0;
		try {
			r = session.update(UtilsMapper.update, sql);
			session.commit();
		} catch (Exception e) {
			Sys.printErr(e);
			throw new SQLException(RegexUtil.getSubOraex(e.getMessage()));
		} finally {
			session.close();
		}
		return r;
	}

	/**
	 * 更新数据
	 * 
	 * @param session
	 * @param sql
	 * @return int
	 */
	public static int excuteUpdate(SqlSession session, String sql) throws Exception {
		try {
			int r = session.update(UtilsMapper.update, sql);
			return r;
		} catch (Exception e) {
			Sys.printErr(e);
			throw new SQLException(RegexUtil.getSubOraex(e.getMessage()));
		}
	}

	/**
	 * 数据操作 -自动提交 -带参数
	 * 
	 * @param dbname
	 * @param sql
	 * @param params
	 * @return int
	 */
	public static int excuteUpdate(String dbname, String sql, List<Object> params) throws Exception {
		SqlSession session = getSession(dbname);
		int r = -1;
		Connection conn = null;
		PreparedStatement ps = null;
		try {
			logger.debug(dbname);
			conn = session.getConnection();
			logger.debug(sql);
			ps = conn.prepareStatement(sql);
			for (int i = 0; i < params.size(); i++) {
				ps.setObject(i + 1, params.get(i));
			}
			logger.debug(params);
			r = ps.executeUpdate();
			conn.commit();
		} catch (Exception e) {
			logger.error(e);
			throw new SQLException(RegexUtil.getSubOraex(e.getMessage()));
		} finally {
			closeConn(session, conn, ps, null);
		}
		return r;
	}

	/**
	 * 数据操作 -非自动提交(需程序自己提交) -带参数 适合用于统一事物操作
	 * 
	 * @param session
	 * @param sql
	 * @param params
	 * @return int
	 */
	public static int excuteUpdate(SqlSession session, String sql, List<Object> params) throws Exception {
		int r = -1;
		Connection conn = null;
		PreparedStatement ps = null;
		try {
			conn = session.getConnection();
			logger.debug(sql);
			ps = conn.prepareStatement(sql);
			for (int i = 0; i < params.size(); i++) {
				ps.setObject(i + 1, params.get(i));
			}
			logger.debug(params);
			r = ps.executeUpdate();
		} catch (Exception e) {
			logger.error(e);
			throw new SQLException(RegexUtil.getSubOraex(e.getMessage()));
		}
		return r;
	}

	/**
	 * 调用存储过程; aParams为分号分割的字符串参数值列表, 所有参数只能是字符串类型, 且只能是in类型
	 * 
	 * @param dbkey
	 * @param sql
	 * @return String
	 */
	public static String executeCommand(String dbkey, String sql) throws Exception {
		String result;
		result = "执行命令";
		logger.debug(String.format("com.tlv8.base.db.DBUtils.executeCommand(dbkey:%s, sql:%s)", dbkey, sql));
		try {
			SqlSession session = getSession(dbkey);
			Connection conn = session.getConnection();
			CallableStatement proc = null;
			try {
				proc = conn.prepareCall(sql);
				proc.registerOutParameter(3, Types.VARCHAR);
				proc.execute();
				result += "->" + proc.getString(3);
				logger.debug(result);
			} finally {
				closeConn(session, conn, proc, null);
			}
		} catch (Exception e) {
			throw new Exception(e);
		}
		return result;
	}

}