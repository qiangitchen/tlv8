package com.tlv8.system.action;

import com.tlv8.base.db.DBUtils;
import java.sql.Connection;
import java.sql.PreparedStatement;
import org.apache.ibatis.session.SqlSession;

public class Signm {
	public static void addSign(String personid, String signm) {
		String sql = "update sa_opperson set fsignm=? where sid = ?";
		SqlSession session = DBUtils.getSession("system");
		Connection conn = null;
		PreparedStatement ps = null;
		try {
			conn = session.getConnection();
			ps = conn.prepareStatement(sql);
			ps.setString(1, signm);
			ps.setString(2, personid);
			ps.executeUpdate();
			session.commit(true);
		} catch (Exception e) {
			session.rollback(true);
			e.printStackTrace();
		} finally {
			DBUtils.CloseConn(session, conn, ps, null);
		}
	}
}