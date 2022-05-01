package com.tlv8.system.action;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import javax.naming.NamingException;

import org.apache.ibatis.session.SqlSession;

import com.tlv8.base.db.DBUtils;

@SuppressWarnings({ "rawtypes", "unchecked" })
public class Login {
	private String message;
	private static String sql = "select p.sName username,p.sID personID,p.sName personName,p.sCode personCode,o.sID orgID,o.sName orgName,o.sFID orgPath,p.sID agentPersonID,p.sName agentPersonName,p.sCode agentPersonCode,o.sID agentOrgID,o.sName agentOrgName,o.sFID agentOrgPath , p.spassword,p.SMOBILEPHONE, case when (p.SVALIDSTATE=1) then o.SVALIDSTATE else p.SVALIDSTATE end SVALIDSTATE  from SA_OPPerson p left join SA_OPOrg o on p.SMAINORGID = o.SID "
			.toUpperCase();

	public void setMessage(String message) {
		this.message = message;
	}

	public String getMessage() {
		return this.message;
	}

	public static HashMap<String, String> doLogin(String username, String password, String ip, String logintype)
			throws Exception {
		String sqls = "";
		sqls = sql + " where (upper(p.sCode) = upper(?) or upper(p.sloginname) = upper(?)" + " or p.SMOBILEPHONE=?)";
		HashMap m = new HashMap();
		SqlSession session = DBUtils.getSession("system");
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			conn = session.getConnection();
			ps = conn.prepareStatement(sqls.toUpperCase());
			ps.setString(1, username);
			ps.setString(2, username);
			ps.setString(3, username);
			rs = ps.executeQuery();
			if (rs.next()) {
				m.put("username", rs.getString(1));
				m.put("personID", rs.getString(2));
				m.put("personName", rs.getString(3));
				m.put("personCode", rs.getString(4));
				m.put("orgID", rs.getString(5));
				m.put("orgName", rs.getString(6));
				m.put("orgPath", rs.getString(7));
				m.put("agentPersonID", rs.getString(8));
				m.put("agentPersonName", rs.getString(9));
				m.put("agentPersonCode", rs.getString(10));
				m.put("agentOrgID", rs.getString(11));
				m.put("agentOrgName", rs.getString(12));
				m.put("agentOrgPath", rs.getString(13));
				m.put("mobilephone", rs.getString(15));
				if (!password.equals(rs.getString(14))) {
					throw new Exception("用户名或密码错误!");
				}
				if (!"1".equals(rs.getString(16))) {
					throw new Exception("您的账号暂时无法登陆，请联系管理员。");
					// throw new Exception("非中国移动专线用户，不能访问协同办公系统！");
//					throw new Exception("非安全认证的电子政务外网数据专线用户，严禁访问！");
				}
				if ("-1".equals(rs.getString(16)))
					throw new Exception("用户已被删除!");
			} else {
				throw new Exception("用户名或密码错误!");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (NamingException e) {
			e.printStackTrace();
		} finally {
			DBUtils.CloseConn(session, conn, ps, rs);
		}
		return m;
	}

	public static HashMap<String, String> MD5doLogin(String username, String password) throws Exception {
		String sqls = "";
		sqls = sql + " where (upper(p.sCode) = upper(?) or upper(p.sloginname) = upper(?)" + "or p.SMOBILEPHONE=?)";
		HashMap m = new HashMap();
		SqlSession session = DBUtils.getSession("system");
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			conn = session.getConnection();
			ps = conn.prepareStatement(sqls.toUpperCase());
			ps.setString(1, username);
			ps.setString(2, username);
			ps.setString(3, username);
			rs = ps.executeQuery();
			if (rs.next()) {
				m.put("username", rs.getString(1));
				m.put("personID", rs.getString(2));
				m.put("personName", rs.getString(3));
				m.put("personCode", rs.getString(4));
				m.put("orgID", rs.getString(5));
				m.put("orgName", rs.getString(6));
				m.put("orgPath", rs.getString(7));
				m.put("agentPersonID", rs.getString(8));
				m.put("agentPersonName", rs.getString(9));
				m.put("agentPersonCode", rs.getString(10));
				m.put("agentOrgID", rs.getString(11));
				m.put("agentOrgName", rs.getString(12));
				m.put("agentOrgPath", rs.getString(13));
				m.put("mobilephone", rs.getString(15));
				if (!password.equals(rs.getString(14))) {
					throw new Exception("用户名或密码错误!");
				}
				if ("0".equals(rs.getString(16)))
					throw new Exception("您的账号暂时无法登陆，请联系管理员。");
			} else {
				throw new Exception("用户名或密码错误!");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (NamingException e) {
			e.printStackTrace();
		} finally {
			DBUtils.CloseConn(session, conn, ps, rs);
		}

		return m;
	}

	public static HashMap<String, String> CAdoLogin(String sn) throws Exception {
		String sqls = "";
		sqls = sql + " where fCASN = ?";
		HashMap m = new HashMap();
		SqlSession session = DBUtils.getSession("system");
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			conn = session.getConnection();
			ps = conn.prepareStatement(sqls.toUpperCase());
			ps.setString(1, sn);
			rs = ps.executeQuery();
			if (rs.next()) {
				m.put("username", rs.getString(1));
				m.put("personID", rs.getString(2));
				m.put("personName", rs.getString(3));
				m.put("personCode", rs.getString(4));
				m.put("orgID", rs.getString(5));
				m.put("orgName", rs.getString(6));
				m.put("orgPath", rs.getString(7));
				m.put("agentPersonID", rs.getString(8));
				m.put("agentPersonName", rs.getString(9));
				m.put("agentPersonCode", rs.getString(10));
				m.put("agentOrgID", rs.getString(11));
				m.put("agentOrgName", rs.getString(12));
				m.put("agentOrgPath", rs.getString(13));
				m.put("mobilephone", rs.getString(15));
				if ("0".equals(rs.getString(16)))
					throw new Exception("您的账号暂时无法登陆，请联系管理员。");
			} else {
				throw new Exception("证书无效或未配置!");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (NamingException e) {
			e.printStackTrace();
		} finally {
			DBUtils.CloseConn(session, conn, ps, rs);
		}
		return m;
	}

	public static HashMap<String, String> sCAdoLogin(String signm) throws Exception {
		String sqls = "";
		sqls = sql + " where fsignm = ?";
		HashMap m = new HashMap();
		SqlSession session = DBUtils.getSession("system");
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			conn = session.getConnection();
			ps = conn.prepareStatement(sqls.toUpperCase());
			ps.setString(1, signm);
			rs = ps.executeQuery();
			if (rs.next()) {
				m.put("username", rs.getString(1));
				m.put("personID", rs.getString(2));
				m.put("personName", rs.getString(3));
				m.put("personCode", rs.getString(4));
				m.put("orgID", rs.getString(5));
				m.put("orgName", rs.getString(6));
				m.put("orgPath", rs.getString(7));
				m.put("agentPersonID", rs.getString(8));
				m.put("agentPersonName", rs.getString(9));
				m.put("agentPersonCode", rs.getString(10));
				m.put("agentOrgID", rs.getString(11));
				m.put("agentOrgName", rs.getString(12));
				m.put("agentOrgPath", rs.getString(13));
				m.put("mobilephone", rs.getString(15));
				if ("0".equals(rs.getString(16)))
					throw new Exception("您的账号暂时无法登陆，请联系管理员。");
			} else {
				throw new Exception("证书无效或未配置!");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (NamingException e) {
			e.printStackTrace();
		} finally {
			DBUtils.CloseConn(session, conn, ps, rs);
		}
		DBUtils.CloseConn(session, conn, ps, rs);
		return m;
	}
}