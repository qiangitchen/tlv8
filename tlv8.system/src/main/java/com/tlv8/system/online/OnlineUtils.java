package com.tlv8.system.online;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.jdbc.SQL;
import org.apache.ibatis.session.SqlSession;

import com.tlv8.base.db.DBUtils;

public class OnlineUtils {
	public static void deleteOnlie(String token) {
		SqlSession session = DBUtils.getSession("system");
		Connection conn = null;
		PreparedStatement ps = null;
		try {
			conn = session.getConnection();
			SQL sql = new SQL().DELETE_FROM("SA_ONLINEINFO").WHERE("SSESSIONID=?");
			ps = conn.prepareStatement(sql.toString());
			ps.setString(1, token);
			ps.executeUpdate();
			session.commit(true);
		} catch (SQLException e) {
			session.rollback(true);
			e.printStackTrace();
		} finally {
			DBUtils.closeConn(session, conn, ps, null);
		}
	}

	public static int getOnlineCount() {
		int r = -1;
		SqlSession session = DBUtils.getSession("system");
		Connection conn = null;
		Statement stm = null;
		ResultSet rs = null;
		try {
			conn = session.getConnection();
			String sql = "select count(*) CON from SA_ONLINEINFO";
			stm = conn.createStatement();
			rs = stm.executeQuery(sql);
			if (rs.next()) {
				r = rs.getInt(1);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DBUtils.closeConn(session, conn, stm, rs);
		}
		return r;
	}

	public static List<Map<String, Object>> getOnlineList() {
		List<Map<String, Object>> relist = new ArrayList<>();
		SqlSession session = DBUtils.getSession("system");
		Connection conn = null;
		Statement stm = null;
		ResultSet rs = null;
		try {
			SQL sql = new SQL();
			sql.SELECT("SID,SUSERID,SUSERNAME,SUSERFID,SUSERFNAME,SLOGINIP");
			sql.SELECT(",SLOGINDATE,SSESSIONID,SSERVICEIP,SMACHINECODE,VERSION");
			sql.FROM("SA_ONLINEINFO");
			conn = session.getConnection();
			stm = conn.createStatement();
			rs = stm.executeQuery(sql.toString());
			while (rs.next()) {
				Map<String, Object> jsonMap = new HashMap<>();
				jsonMap.put("rowid", rs.getString("SID"));
				jsonMap.put("userid", rs.getString("SUSERID"));
				jsonMap.put("username", rs.getString("SUSERNAME"));
				jsonMap.put("userfullid", rs.getString("SUSERFID"));
				jsonMap.put("userfullname", rs.getString("SUSERFNAME"));
				jsonMap.put("loginIP", rs.getString("SLOGINIP"));
				jsonMap.put("loginDate", rs.getString("SLOGINDATE"));
				jsonMap.put("sessionID", rs.getString("SSESSIONID"));
				relist.add(jsonMap);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DBUtils.closeConn(session, conn, stm, rs);
		}
		return relist;
	}
}
