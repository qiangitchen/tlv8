package com.tlv8.opm.org;

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
				String upSql = "update SA_OPOrg o set (o.sfid,o.sfcode,o.sfname) =(select t.sFID||'/'||o.SID||'.'||o.SORGKINDID,t.sFCode||'/'||o.sCode,t.sFName||'/'||o.sName from SA_OPOrg t where t.sID = '"
						+ parent + "') where sID  = '" + orgid + "'";
				DBUtils.excuteUpdate(session, upSql);
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
		String sql = "update SA_OPAUTHORIZE set (SORGID, SORGFID, SORGFNAME) = (select '" + neworgid
				+ "',sFID,sFName from SA_OPOrg where sID = '" + neworgid + "') where SORGID  = '" + oldorgid + "'";
		try {
			DBUtils.excuteUpdate(session, sql);
		} catch (Exception e) {
			e.printStackTrace();
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
