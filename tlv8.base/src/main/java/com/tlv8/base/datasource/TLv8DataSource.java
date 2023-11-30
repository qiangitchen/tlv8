package com.tlv8.base.datasource;

import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.SQLFeatureNotSupportedException;

import javax.sql.DataSource;

import org.apache.ibatis.session.SqlSessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 自定义数据源
 */
public class TLv8DataSource implements DataSource {
	private static final Logger LOGGER = LoggerFactory.getLogger(TLv8DataSource.class);
	private String driver;
	private String url;
	private String username;
	private String password;
	private String poolPingEnabled;
	private String poolPingQuery;
	private String poolPingConnectionsNotUsedFor;

	protected SqlSessionFactory sqlSessionFactory;

	public TLv8DataSource(SqlSessionFactory sqlSessionFactory) {
		this.sqlSessionFactory = sqlSessionFactory;
	}

	public TLv8DataSource() {
	}

	public String getDriver() {
		return driver;
	}

	public void setDriver(String driver) {
		this.driver = driver;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getPoolPingEnabled() {
		return poolPingEnabled;
	}

	public void setPoolPingEnabled(String poolPingEnabled) {
		this.poolPingEnabled = poolPingEnabled;
	}

	public String getPoolPingQuery() {
		return poolPingQuery;
	}

	public void setPoolPingQuery(String poolPingQuery) {
		this.poolPingQuery = poolPingQuery;
	}

	public String getPoolPingConnectionsNotUsedFor() {
		return poolPingConnectionsNotUsedFor;
	}

	public void setPoolPingConnectionsNotUsedFor(String poolPingConnectionsNotUsedFor) {
		this.poolPingConnectionsNotUsedFor = poolPingConnectionsNotUsedFor;
	}

	@Override
	public java.util.logging.Logger getParentLogger() throws SQLFeatureNotSupportedException {
		throw new SQLFeatureNotSupportedException();
	}

	@Override
	@SuppressWarnings("unchecked")
	public <T> T unwrap(Class<T> iface) throws SQLException {
		if (iface.isInstance(this)) {
			return (T) this;
		}
		throw new SQLException("Wrapped DataSource is not an instance of " + iface);
	}

	@Override
	public boolean isWrapperFor(Class<?> iface) throws SQLException {
		if (iface.isInstance(this)) {
			return true;
		}
		return false;
	}

	@Override
	public Connection getConnection() throws SQLException {
		if (sqlSessionFactory != null) {
			return sqlSessionFactory.openSession().getConnection();
		}
		Connection connection = null;
		try {
			Class.forName(driver); // 加载数据库驱动程序
			connection = DriverManager.getConnection(url, username, password); // 获取数据库连接
			LOGGER.debug("Connected to the database successfully!");
		} catch (ClassNotFoundException | SQLException e) {
			throw new SQLException(e);
		}
		return connection;
	}

	@Override
	public Connection getConnection(String username, String password) throws SQLException {
		throw new SQLFeatureNotSupportedException();
	}

	@Override
	public PrintWriter getLogWriter() throws SQLException {
		// TODO 自动生成的方法存根
		return null;
	}

	@Override
	public void setLogWriter(PrintWriter out) throws SQLException {
		// TODO 自动生成的方法存根
	}

	@Override
	public void setLoginTimeout(int seconds) throws SQLException {
		// TODO 自动生成的方法存根
	}

	@Override
	public int getLoginTimeout() throws SQLException {
		// TODO 自动生成的方法存根
		return 0;
	}

}
