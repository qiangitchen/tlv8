package com.tlv8.base.db;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Map;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import com.tlv8.base.Sys;

/**
 * 
 * @author 陈乾
 *
 */
public class DataSourceUtils {
	/**
	 * 获取中间件 如Tomcat配置的数据库连接
	 * 
	 * @param key
	 * @return Connection
	 * @throws SQLException
	 * @throws NamingException
	 * @throws InstantiationException
	 * @throws IllegalAccessException
	 * @throws ClassNotFoundException
	 * @see java.sql.Connection
	 */
	public static Connection getAppConn(String key) throws SQLException, NamingException, InstantiationException,
			IllegalAccessException, ClassNotFoundException {
		Context context = new InitialContext();
		DataSource ds = (DataSource) context.lookup("java:comp/env/" + key);
		Connection cn = ds.getConnection();
		if (cn != null)
			System.out.println("Connection ok");
		return cn;
	}
	
	/**
	 * 数据库连接检测监听
	 */
	protected static void startListener() {
		new Thread(new Runnable() {

			@Override
			public void run() {
				while (true) {
					Sys.printMsg("开始检测数据库连接");
					Map<String, Map<String, String>> dbconfig = DBUtils.getDBConfig();
					for (String key : dbconfig.keySet()) {
						String sql = "select 1";
						if (DBUtils.IsOracleDB(key)) {
							sql += " from dual";
						}
						try {
							DBUtils.selectStringList(key, sql);
							Sys.printMsg(key + "连接正常");
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
					Sys.printMsg("开始检测数据库连接");
					try {
						// 每10分钟检测一次
						Thread.sleep(10 * 60 * 1000);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}
		}).start();
	}
}
