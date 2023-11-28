package com.tlv8.base.datasource;

public class TLv8DataSource {
	private String driver;
	private String url;
	private String username;
	private String password;
	private String poolPingEnabled;
	private String poolPingQuery;
	private String poolPingConnectionsNotUsedFor;

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

}
