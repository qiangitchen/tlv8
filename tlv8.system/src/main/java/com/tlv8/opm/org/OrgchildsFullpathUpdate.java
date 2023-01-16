package com.tlv8.opm.org;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;

import com.tlv8.base.db.DBUtils;

public class OrgchildsFullpathUpdate {

	/**
	 * 更新机构路径
	 */
	public static void upOrgpath(SqlSession session, String parent) {
		String qySql = "select SID from SA_OPOrg where sparent = '" + parent + "'";
		try {
			List<Map<String, String>> chd = DBUtils.selectStringList(session, qySql);
			for (int i = 0; i < chd.size(); i++) {
				String orgid = chd.get(i).get("SID");
				Connection conn = null;
				PreparedStatement ps = null;
				ResultSet rs = null;
				try {
					conn = session.getConnection();
					ps = session.getConnection().prepareStatement("select t.SFID,o.SID,o.SORGKINDID,t.SFCODE,o.SCODE,t.SFNAME,o.SNAME from SA_OPOrg t,SA_OPOrg o where t.sID=? and o.SID=?");
					ps.setString(1, parent);
					ps.setString(2, orgid);
					rs = ps.executeQuery();
					if(rs.next()) {
						PreparedStatement ps1 = conn.prepareStatement("update SA_OPOrg set SFID=?,SFCODE=?,SFNAME=? where SID=?");
						ps1.setString(1, rs.getString("SFID") + "/" + rs.getString("SID") + "." + rs.getString("SORGKINDID"));
						ps1.setString(2, rs.getString("SFCODE") + "/" + rs.getString("SCODE"));
						ps1.setString(3, rs.getString("SFNAME") + "/" + rs.getString("SNAME"));
						ps1.setString(4, orgid);
						DBUtils.CloseConn(null, null, ps1, null);
					}
				} finally {
					DBUtils.CloseConn(null, null, ps, rs);
				}
				upOrgpath(session, orgid);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 更新授权
	 */
	public static void upAutherPermOrgpath(SqlSession session, String oldorgid, String neworgid) {
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			conn = session.getConnection();
			ps = conn.prepareStatement("select SFID,SFNAME from SA_OPOrg where SID=?");
			ps.setString(1, neworgid);
			rs = ps.executeQuery();
			if (rs.next()) {
				PreparedStatement ps1 = conn
						.prepareStatement("update SA_OPAUTHORIZE set SORGID=?, SORGFID=?, SORGFNAME=? where SORGID=?");
				ps1.setString(1, neworgid);
				ps1.setString(2, rs.getString("SFID"));
				ps1.setString(3, rs.getString("SFNAME"));
				ps1.setString(4, oldorgid);
				ps1.executeUpdate();
				DBUtils.CloseConn(null, null, ps1, null);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DBUtils.CloseConn(null, null, ps, rs);
		}

	}

	/**
	 * 更新所有子机构授权
	 */
	public static void upAutherPermOrgpaths(SqlSession session, String parent) {
		String qySql = "select SID from SA_OPOrg where sparent = '" + parent + "'";
		try {
			List<Map<String, String>> chd = DBUtils.selectStringList(session, qySql);
			for (int i = 0; i < chd.size(); i++) {
				String orgid = chd.get(i).get("SID");
				upAutherPermOrgpath(session, orgid, orgid);
				upAutherPermOrgpaths(session, orgid);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		// upOrgpath("A5DE02EEE83C4A1881583DFF50AE44C4");
	}
}
