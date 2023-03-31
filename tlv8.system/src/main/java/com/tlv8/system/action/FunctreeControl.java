package com.tlv8.system.action;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;

import com.tlv8.base.Sys;
import com.tlv8.base.CodeUtils;
import com.tlv8.base.db.DBUtils;

public class FunctreeControl {
	private static Map<String, Map<String, String>> haveAutherMaps = new HashMap<String, Map<String, String>>();

	public static void initData(String personfID, String psnid) {
		Map<String, String> have = new HashMap<String, String>();
		String sql = "";
		if (DBUtils.IsOracleDB("system") || DBUtils.IsDMDB("system")) {
			sql = "select m.SPROCESS,nvl(m.SACTIVITY,'')SACTIVITY from sa_oppermission m  where EXISTS"
					+ "(select 1 from sa_opauthorize a where m.SPERMISSIONROLEID = a.SAUTHORIZEROLEID "
					+ " and a.sOrgID in (select SID from sa_oporg where nvl(SVALIDSTATE,'1') != '0')" + " and (upper('"
					+ personfID + "') like upper('%' || a.sOrgID || '%') or a.sOrgID like '%" + psnid + "%')" + ")";
		} else if (DBUtils.IsMySQLDB("system")) {
			sql = "select m.SPROCESS,ifnull(m.SACTIVITY,'')SACTIVITY from sa_oppermission m "
					+ " where EXISTS(select 1 from sa_opauthorize a where m.SPERMISSIONROLEID = a.SAUTHORIZEROLEID "
					+ " and (upper('" + personfID + "') like upper(concat('%',a.sOrgID,'%'))  or a.sOrgID like '%"
					+ psnid + "%'))";
		} else if (DBUtils.IsPostgreSQL("system")) {
			sql = "select m.SPROCESS,COALESCE(m.SACTIVITY,'')SACTIVITY from sa_oppermission m "
					+ " where EXISTS(select SID from sa_opauthorize a where m.SPERMISSIONROLEID = a.SAUTHORIZEROLEID "
					+ " and ('" + personfID + "' like concat('%',a.sOrgID,'%')  or a.sOrgID like '%" + psnid + "%'))";
		} else {
			sql = "select m.SPROCESS,isNull(m.SACTIVITY,'')SACTIVITY from sa_oppermission m "
					+ " where EXISTS(select 1 from sa_opauthorize a where m.SPERMISSIONROLEID = a.SAUTHORIZEROLEID "
					+ " and ('" + personfID + "' like concat('%',a.sOrgID,'%')  or a.sOrgID like '%" + psnid + "%'))";
		}
		sql = sql.toUpperCase();
		SqlSession session = DBUtils.getSession("system");
		Connection conn = null;
		Statement stm = null;
		ResultSet rs = null;
		try {
			conn = session.getConnection();
			stm = conn.createStatement();
			rs = stm.executeQuery(sql);
			while (rs.next()) {
				String process = rs.getString(1);
				String activity = rs.getString(2);
				activity = (activity == null || "null".equals(activity)) ? "" : activity;
				have.put(process + activity, activity);
			}
		} catch (SQLException e) {
			Sys.printMsg(sql);
			e.printStackTrace();
		} finally {
			DBUtils.closeConn(session, conn, stm, rs);
		}
		haveAutherMaps.put(personfID, have);
	}

	public static void remove(String personfID) {
		haveAutherMaps.remove(personfID);
	}

	public static boolean haveAuther(String url, String personfID, String personid) {
		Map<String, String> havemap = haveAutherMaps.get(personfID);
		if (havemap != null) {
			return havemap.containsKey(url);
		}
		boolean have = false;
		String sql = "";
		if (url != null) {
			url = CodeUtils.encodeSpechars(url);
		}
		if (DBUtils.IsOracleDB("system") || DBUtils.IsDMDB("system")) {
			sql = "select * from sa_opauthorize a "
					+ "left join sa_oppermission m on m.SPERMISSIONROLEID = a.SAUTHORIZEROLEID "
					+ " where upper(m.SPROCESS) = upper('" + url + "') " + " and upper('" + personfID
					+ "') like upper('%' || sOrgID || '%') and rownum=1";
		} else if (DBUtils.IsMySQLDB("system") || DBUtils.IsPostgreSQL("system")) {
			sql = "select * from sa_opauthorize a "
					+ "left join sa_oppermission m on m.SPERMISSIONROLEID = a.SAUTHORIZEROLEID "
					+ " where upper(m.SPROCESS) = upper('" + url + "') " + " and upper('" + personfID
					+ "') like upper(concat('%',sOrgID,'%')) limit 1";
		} else {
			sql = "select TOP1 * from sa_opauthorize a "
					+ "left join sa_oppermission m on m.SPERMISSIONROLEID = a.SAUTHORIZEROLEID "
					+ " where upper(m.SPROCESS) = upper('" + url + "') " + " and upper('" + personfID
					+ "') like upper('%' + sOrgID + '%')";
		}
		SqlSession session = DBUtils.getSession("system");
		Connection conn = null;
		Statement stm = null;
		ResultSet rs = null;
		try {
			conn = session.getConnection();
			stm = conn.createStatement();
			rs = stm.executeQuery(sql.toUpperCase());
			if (rs.next()) {
				return true;
			} else {
				return false;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBUtils.closeConn(session, conn, stm, rs);
		}
		return have;
	}

	public static Map<String, String> gethaveAuther(String personfID, String psnid) {
		if (haveAutherMaps.get(personfID) == null) {
			initData(personfID, psnid);
		}
		return haveAutherMaps.get(personfID);
	}

}
