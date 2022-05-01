package com.tlv8.base.db;

import java.sql.Connection;
import java.sql.SQLException;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

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
}
