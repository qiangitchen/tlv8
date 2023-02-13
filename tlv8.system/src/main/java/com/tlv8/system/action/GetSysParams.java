package com.tlv8.system.action;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

import javax.naming.NamingException;

import org.apache.ibatis.session.SqlSession;

import com.tlv8.base.db.DBUtils;

public class GetSysParams {
	private static String sql = ("select o.sID,o.sName,o.sCode,o.sFID,o.sFName,o.sFCode from SA_OPOrg o ")
			.toLowerCase();

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static HashMap<String, String> getSysParamsFunc(HashMap<String, String> params) throws Exception {
		String loginDate = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
		SqlSession session = DBUtils.getSession("system");
		Connection conn = null;
		try {
			conn = session.getConnection();
			params.put("loginDate", loginDate);
			getOrgInfo(conn, params);
			getOgnInfo(conn, params);
			getUserInfo(conn, params);
			getPersonInfo(conn, params);
			getDeptInfo(conn, params);
			getPositionInfo(conn, params);
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (NamingException e) {
			e.printStackTrace();
		} finally {
			DBUtils.closeConn(session, conn, null, null);
		}
		HashMap m = params;
		return m;
	}

	private static void getOrgInfo(Connection conn, HashMap<String, String> params) throws Exception {
		String sqls = "";
		sqls = sql + " where upper(sid) = upper('" + params.get("orgID") + "') and o.SVALIDSTATE =1";
		Statement stm = null;
		ResultSet rs = null;
		try {
			stm = conn.createStatement();
			rs = stm.executeQuery(sqls.toUpperCase());
			if (rs.next()) {
				params.put("currentOrgID",
						("null".equals(rs.getString(1)) || rs.getString(1) == null) ? "" : rs.getString(1));
				params.put("currentOrgName",
						("null".equals(rs.getString(2)) || rs.getString(2) == null) ? "" : rs.getString(2));
				params.put("currentOrgCode",
						("null".equals(rs.getString(3)) || rs.getString(3) == null) ? "" : rs.getString(3));
				params.put("currentOrgFullID",
						("null".equals(rs.getString(4)) || rs.getString(4) == null) ? "" : rs.getString(4));
				params.put("currentOrgFullName",
						("null".equals(rs.getString(5)) || rs.getString(5) == null) ? "" : rs.getString(5));
				params.put("currentOrgFullCode",
						("null".equals(rs.getString(6)) || rs.getString(6) == null) ? "" : rs.getString(6));
			} else {
				params.put("currentOrgID", "");
				params.put("currentOrgName", "");
				params.put("currentOrgCode", "");
				params.put("currentOrgFullID", "");
				params.put("currentOrgFullName", "");
				params.put("currentOrgFullCode", "");
			}
			sqls = "";
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBUtils.closeConn(null, stm, rs);
		}
	}

	private static void getOgnInfo(Connection conn, HashMap<String, String> params) throws Exception {
		String sqls = "";
		if (DBUtils.IsOracleDB("system") || DBUtils.IsDMDB("system")) {
			sqls = sql + " where upper('" + params.get("currentOrgFullID")
					+ "') like upper(o.sFID||'%') and (upper(o.sorgkindid) = upper('ogn') or upper(o.sorgkindid) = upper('org')) and o.SVALIDSTATE =1";
		} else if (DBUtils.IsMSSQLDB("system")) {
			sqls = sql + " where upper('" + params.get("currentOrgFullID")
					+ "') like upper(convert(nvarchar(1024),o.sFID+'%')) and  (upper(o.sorgkindid) = upper('ogn') or upper(o.sorgkindid) = upper('org')) and o.SVALIDSTATE =1";
		} else {
			sqls = sql + " where upper('" + params.get("currentOrgFullID")
					+ "') like upper(concat(o.sFID,'%')) and (upper(o.sorgkindid) = upper('ogn') or upper(o.sorgkindid) = upper('org')) and o.SVALIDSTATE =1";
		}
		sqls += " order by SFID desc";
		Statement stm = null;
		ResultSet rs = null;
		try {
			stm = conn.createStatement();
			rs = stm.executeQuery(sqls);
			if (rs.next()) {
				params.put("currentOgnID",
						("null".equals(rs.getString(1)) || rs.getString(1) == null) ? "" : rs.getString(1));
				params.put("currentOgnName",
						("null".equals(rs.getString(2)) || rs.getString(2) == null) ? "" : rs.getString(2));
				params.put("currentOgnCode",
						("null".equals(rs.getString(3)) || rs.getString(3) == null) ? "" : rs.getString(3));
				params.put("currentOgnFullID",
						("null".equals(rs.getString(4)) || rs.getString(4) == null) ? "" : rs.getString(4));
				params.put("currentOgnFullName",
						("null".equals(rs.getString(5)) || rs.getString(5) == null) ? "" : rs.getString(5));
				params.put("currentOgnFullCode",
						("null".equals(rs.getString(6)) || rs.getString(6) == null) ? "" : rs.getString(6));
			} else {
				params.put("currentOgnID", params.get("currentOrgID"));
				params.put("currentOgnName", params.get("currentOrgName"));
				params.put("currentOgnCode", params.get("currentOrgCode"));
				params.put("currentOgnFullID", params.get("currentOrgFullID"));
				params.put("currentOgnFullName", params.get("currentOrgFullName"));
				params.put("currentOgnFullCode", params.get("currentOrgFullCode"));
			}
			sqls = "";
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBUtils.closeConn(null, stm, rs);
		}
	}

	private static void getUserInfo(Connection conn, HashMap<String, String> params) throws Exception {
		String sqls = "select p.sid,p.sName,p.sCode,concat(o.sFID,'/',p.sid),concat(o.sFName,'/',p.sName),concat(o.sFCode,'/',p.sCode) from SA_OPPERSON p , SA_OPOrg o where o.sid ='"
				+ params.get("orgID") + "' and p.sid = '" + params.get("personID") + "' and o.SVALIDSTATE =1 and p.SVALIDSTATE =1";
		if (DBUtils.IsOracleDB("system")) {
			sqls = "select p.sid,p.sName,p.sCode,o.sFID||'/'||p.sid,o.sFName||'/'||p.sName,o.sFCode||'/'||p.sCode from SA_OPPERSON p , SA_OPOrg o where o.sid = '"
					+ params.get("orgID") + "' and p.sid = '" + params.get("personID") + "' and o.SVALIDSTATE =1 and p.SVALIDSTATE =1";
		}
		Statement stm = null;
		ResultSet rs = null;
		try {
			stm = conn.createStatement();
			rs = stm.executeQuery(sqls);
			if (rs.next()) {
				params.put("currentUserID",
						("null".equals(rs.getString(1)) || rs.getString(1) == null) ? "" : rs.getString(1));
				params.put("currentUserName",
						("null".equals(rs.getString(2)) || rs.getString(2) == null) ? "" : rs.getString(2));
				params.put("currentUserCode",
						("null".equals(rs.getString(3)) || rs.getString(3) == null) ? "" : rs.getString(3));
				params.put("currentUserFullID",
						("null".equals(rs.getString(4)) || rs.getString(4) == null) ? "" : rs.getString(4));
				params.put("currentUserFullName",
						("null".equals(rs.getString(5)) || rs.getString(5) == null) ? "" : rs.getString(5));
				params.put("currentUserFullCode",
						("null".equals(rs.getString(6)) || rs.getString(6) == null) ? "" : rs.getString(6));
			} else {
				params.put("currentUserID", "");
				params.put("currentUserName", "");
				params.put("currentUserCode", "");
				params.put("currentUserFullID", "");
				params.put("currentUserFullName", "");
				params.put("currentUserFullCode", "");
			}
			sqls = "";
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBUtils.closeConn(null, stm, rs);
		}
	}

	private static void getPersonInfo(Connection conn, HashMap<String, String> params) throws Exception {
		String sqls = "select p.SID,p.SNAME,p.SCODE,o.SFID,o.SFNAME,o.SFCODE from SA_OPPERSON p inner join SA_OPORG o on o.SPERSONID=p.SID and p.SMAINORGID=o.SPARENT where p.SID = '"
				+ params.get("personID") + "' and o.SVALIDSTATE =1 and p.SVALIDSTATE =1";
		Statement stm = null;
		ResultSet rs = null;
		try {
			stm = conn.createStatement();
			rs = stm.executeQuery(sqls);
			if (rs.next()) {
				params.put("currentPersonID",
						("null".equals(rs.getString(1)) || rs.getString(1) == null) ? "" : rs.getString(1));
				params.put("currentPersonName",
						("null".equals(rs.getString(2)) || rs.getString(2) == null) ? "" : rs.getString(2));
				params.put("currentPersonCode",
						("null".equals(rs.getString(3)) || rs.getString(3) == null) ? "" : rs.getString(3));
				params.put("currentPersonFullID",
						("null".equals(rs.getString(4)) || rs.getString(4) == null) ? "" : rs.getString(4));
				params.put("allMemberOfOrgFullID",
						("null".equals(rs.getString(4)) || rs.getString(4) == null) ? "" : rs.getString(4));
				params.put("currentPersonFullName",
						("null".equals(rs.getString(5)) || rs.getString(5) == null) ? "" : rs.getString(5));
				params.put("currentPersonFullCode",
						("null".equals(rs.getString(6)) || rs.getString(6) == null) ? "" : rs.getString(6));
				params.put("currentProcessLabel", "系统调用");
				params.put("currentActivityLabel", "系统调用活动");
			} else {
				params.put("currentPersonID", "");
				params.put("currentPersonName", "");
				params.put("currentPersonCode", "");
				params.put("currentPersonFullID", "");
				params.put("allMemberOfOrgFullID", "");
				params.put("currentPersonFullName", "");
				params.put("currentPersonFullCode", "");
				params.put("currentProcessLabel", "系统调用");
				params.put("currentActivityLabel", "系统调用活动");
			}
			sqls = "";
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBUtils.closeConn(null, stm, rs);
		}
	}

	private static void getDeptInfo(Connection conn, HashMap<String, String> params) throws Exception {
		String sqls = "";
		if (DBUtils.IsOracleDB("system") || DBUtils.IsDMDB("system")) {
			sqls = sql + " where upper('" + params.get("currentOrgFullID") + "') like upper(o.sFID||'%') "
					+ "and (upper(o.sorgkindid) = upper('dept') or  upper(o.sorgkindid) = upper('dpt'))"
					+ "and sfid is not null and o.SVALIDSTATE =1 order by sfid desc";
		} else if (DBUtils.IsMSSQLDB("system")) {
			sqls = sql + " where upper('" + params.get("currentOrgFullID")
					+ "') like upper(convert(nvarchar(1024),o.sFID+'%')) and (upper(o.sorgkindid) = upper('dept') or  upper(o.sorgkindid) = upper('dpt'))"
					+ "and sfid is not null and o.SVALIDSTATE =1 order by sfid desc";
		} else {
			sqls = sql + " where upper('" + params.get("currentOrgFullID")
					+ "') like upper(concat(o.sFID,'%')) and (upper(o.sorgkindid) = upper('dept') or  upper(o.sorgkindid) = upper('dpt')) "
					+ "and sfid is not null and o.SVALIDSTATE =1 order by sfid desc";
		}
		Statement stm = null;
		ResultSet rs = null;
		try {
			stm = conn.createStatement();
			rs = stm.executeQuery(sqls);
			if (rs.next()) {
				params.put("currentDeptID",
						("null".equals(rs.getString(1)) || rs.getString(1) == null) ? "" : rs.getString(1));
				params.put("currentDeptName",
						("null".equals(rs.getString(2)) || rs.getString(2) == null) ? "" : rs.getString(2));
				params.put("currentDeptCode",
						("null".equals(rs.getString(3)) || rs.getString(3) == null) ? "" : rs.getString(3));
				params.put("currentDeptFullID",
						("null".equals(rs.getString(4)) || rs.getString(4) == null) ? "" : rs.getString(4));
				params.put("currentDeptFullName",
						("null".equals(rs.getString(5)) || rs.getString(5) == null) ? "" : rs.getString(5));
				params.put("currentDeptFullCode",
						("null".equals(rs.getString(6)) || rs.getString(6) == null) ? "" : rs.getString(6));
			} else {
				params.put("currentDeptID", "");
				params.put("currentDeptName", "");
				params.put("currentDeptCode", "");
				params.put("currentDeptFullID", "");
				params.put("currentDeptFullName", "");
				params.put("currentDeptFullCode", "");
			}
			sqls = "";
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBUtils.closeConn(null, stm, rs);
		}
	}

	private static void getPositionInfo(Connection conn, HashMap<String, String> params) throws Exception {
		String sqls = "";
		if (DBUtils.IsOracleDB("system") || DBUtils.IsDMDB("system")) {
			sqls = sql + " where upper('" + params.get("currentOrgFullID")
					+ "') like upper(o.sFID||'%') and  upper(o.sorgkindid) = upper('pos') and o.SVALIDSTATE =1";
		} else if (DBUtils.IsMSSQLDB("system")) {
			sqls = sql + " where upper('" + params.get("currentOrgFullID")
					+ "') like upper(convert(nvarchar(1024),o.sFID+'%')) and  upper(o.sorgkindid) = upper('pos') and o.SVALIDSTATE =1";
		} else {
			sqls = sql + " where upper('" + params.get("currentOrgFullID")
					+ "') like upper(concat(o.sFID,'%')) and  upper(o.sorgkindid) = upper('pos') and o.SVALIDSTATE =1";
		}
		Statement stm = null;
		ResultSet rs = null;
		try {
			stm = conn.createStatement();
			rs = stm.executeQuery(sqls);
			if (rs.next()) {
				params.put("currentPositionID",
						("null".equals(rs.getString(1)) || rs.getString(1) == null) ? "" : rs.getString(1));
				params.put("currentPositionName",
						("null".equals(rs.getString(2)) || rs.getString(2) == null) ? "" : rs.getString(2));
				params.put("currentPositionCode",
						("null".equals(rs.getString(3)) || rs.getString(3) == null) ? "" : rs.getString(3));
				params.put("currentPositionFullID",
						("null".equals(rs.getString(4)) || rs.getString(4) == null) ? "" : rs.getString(4));
				params.put("currentPositionFullName",
						("null".equals(rs.getString(5)) || rs.getString(5) == null) ? "" : rs.getString(5));
				params.put("currentPositionFullCode",
						("null".equals(rs.getString(6)) || rs.getString(6) == null) ? "" : rs.getString(6));
			} else {
				params.put("currentPositionID", "");
				params.put("currentPositionName", "");
				params.put("currentPositionCode", "");
				params.put("currentPositionFullID", "");
				params.put("currentPositionFullName", "");
				params.put("currentPositionFullCode", "");
			}
			sqls = "";
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBUtils.closeConn(null, stm, rs);
		}
	}
}
