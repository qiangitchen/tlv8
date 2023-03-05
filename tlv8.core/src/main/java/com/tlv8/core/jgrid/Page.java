package com.tlv8.core.jgrid;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.naming.NamingException;

import com.tlv8.base.db.DBUtils;

/**
 * 
 * @author 陈乾
 *
 */
public class Page {
	/*
	 * @chq
	 * 
	 * @u 计算页数
	 */
	@SuppressWarnings("deprecation")
	public static int getCount(String dbkey, String sql, int rows) throws SQLException, NamingException {
		int p = 0;
		Connection conn = null;
		Statement stm = null;
		ResultSet rs = null;
		try {
			conn = DBUtils.getAppConn(dbkey);
			stm = conn.createStatement();
			rs = stm.executeQuery(sql);
			if (rs.next()) {
				p = Math.round(rs.getInt(1) / rows);
				float t = rs.getFloat(1) / (float) rows - p;
				// System.out.println(rs.getInt(1));
				// System.out.println(p);
				// System.out.println(t);
				if (t > 0)
					p = p + 1; // 不满一页按一页算
			}
		} catch (SQLException e) {
			throw new SQLException(e + ":" + sql);
		} finally {
			try {
				DBUtils.CloseConn(conn, stm, rs);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		p = (p == 0) ? 1 : p;
		return p;
	}

	public static int getCount(Connection conn, String sql) {
		Statement stm = null;
		ResultSet rs = null;
		int res = 0;
		try {
			stm = conn.createStatement();
			rs = stm.executeQuery(sql);
			if (rs.next()) {
				res = rs.getInt(1);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				DBUtils.closeConn(null, stm, rs);
			} catch (SQLException e) {
			}
		}
		return res;
	}
}
