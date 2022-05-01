package com.tlv8.doc.svr.generator.dao.impl;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

import org.mybatis.spring.support.SqlSessionDaoSupport;

import com.tlv8.doc.svr.generator.dao.IConnectionDao;

public class ConnectionDaoImpl extends SqlSessionDaoSupport implements
		IConnectionDao {

	@Override
	public Connection getConnection() throws Exception {
		return this.getSqlSession().getConfiguration().getEnvironment()
				.getDataSource().getConnection();
	}

	@Override
	public void CloseConnection(Connection conn, Statement stm, ResultSet rs) {
		try {
			if (rs != null) {
				rs.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		try {
			if (stm != null) {
				stm.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		try {
			if (conn != null) {
				conn.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
