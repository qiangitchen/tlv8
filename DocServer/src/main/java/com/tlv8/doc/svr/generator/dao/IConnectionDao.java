package com.tlv8.doc.svr.generator.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

public interface IConnectionDao {
	public Connection getConnection() throws Exception;

	public void CloseConnection(Connection conn, Statement stm, ResultSet rs);
}
