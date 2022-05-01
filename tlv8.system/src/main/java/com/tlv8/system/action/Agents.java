package com.tlv8.system.action;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.JSON;
import com.tlv8.base.db.DBUtils;
import com.tlv8.base.utils.StringArray;
import com.tlv8.system.bean.ContextBean;

@SuppressWarnings({ "rawtypes" })
public class Agents {

	/**
	 * 获取代理人列表
	 */
	public static String getPrincipalList(String userid) {
		String sql = "select t.SCREATORID,t.SCREATORNAME from SA_OPAGENT t  where t.SAGENTID = '" + userid
				+ "' and t.SVALIDSTATE = 1";
		try {
			List<Map<String, String>> list = DBUtils.execQueryforList("system", sql);
			List<Map<String, String>> relist = new ArrayList<Map<String, String>>();
			for (int i = 0; i < list.size(); i++) {
				Map<String, String> m = new HashMap<String, String>();
				m.put("id", list.get(i).get("SCREATORID"));
				m.put("name", list.get(i).get("SCREATORNAME"));
				relist.add(m);
			}
			return JSON.toJSONString(relist);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return "[]";
	}

	public static String getPrincipalInfo(String principalID, ContextBean Context) {
		String sql = "select t.SORGFNAME,t.SORGFID,o.SFCODE,o.SFID,o.SCODE,t.SCREATORID,t.SCREATORNAME from SA_OPAGENT t left join SA_OPORG o on t.SORGFID = o.SFID where t.SAGENTID = '"
				+ Context.getCurrentPersonID() + "' and t.SCREATORID = '" + principalID + "' and t.SVALIDSTATE = 1";
		try {
			List<Map<String, String>> list = DBUtils.execQueryforList("system", sql);
			if (list.size() > 0) {
				Map<String, String> m = new HashMap<String, String>();
				m.put("currentPersonMemberFName", list.get(0).get("SORGFNAME"));
				m.put("operatorCode", Context.getUsername());
				m.put("allPersonMemberFIDs", Context.getCurrentPersonFullID() + "," + list.get(0).get("SORGFID"));
				m.put("currentPersonMemberFCode", list.get(0).get("SFCODE"));
				Map ognM = getOgnInfo(list.get(0).get("SORGFID"));
				m.put("currentOgnID", (String) ognM.get("SID"));
				m.put("currentPersonMemberNameWithAgent",
						list.get(0).get("SCREATORNAME") + "(" + Context.getCurrentPersonName() + " 代)");
				m.put("currentPersonName", list.get(0).get("SCREATORNAME"));
				m.put("currentOrgFID",
						list.get(0).get("SORGFID").substring(0, list.get(0).get("SORGFID").lastIndexOf("/")));
				m.put("currentPersonMemberFID", list.get(0).get("SORGFID"));
				m.put("currentProcessLabel", "系统调用");
				m.put("currentOgnFCode", (String) ognM.get("SFCODE"));
				m.put("currentActivityLabel", "系统");
				m.put("currentOgnName", (String) ognM.get("SNAME"));
				m.put("allRoles", getAllRoles(Context.getCurrentPersonFullID(), m.get("SORGFID")));
				Map orgMap = getOrgInfo(m.get("currentOrgFID"));
				m.put("currentOrgCode", (String) orgMap.get("SCODE"));
				m.put("currentOgnFID", (String) ognM.get("SFID"));
				m.put("loginDate", Context.getLoginDate());
				m.put("currentPersonMemberFNameWithAgent",
						list.get(0).get("SORGFNAME") + "(" + Context.getCurrentPersonName() + " 代)");
				m.put("currentPersonMemberCode", list.get(0).get("SCODE"));
				m.put("currentPersonCode", list.get(0).get("SCODE"));
				m.put("currentPersonID", list.get(0).get("SCREATORID"));
				m.put("currentOrgFName", (String) orgMap.get("SFNAME"));
				m.put("currentPersonMemberName", list.get(0).get("SCREATORNAME"));
				m.put("currentOrgName", (String) orgMap.get("SNAME"));
				m.put("currentOgnCode", (String) orgMap.get("SCODE"));
				m.put("operatorName", Context.getCurrentPersonName());
				m.put("currentPersonMemberID", list.get(0).get("SORGFID"));
				m.put("currentOgnFName", (String) ognM.get("SFNAME"));
				m.put("currentOrgFCode", (String) orgMap.get("SFCODE"));
				m.put("currentOrgID", (String) orgMap.get("SID"));
				m.put("operatorID", Context.getCurrentPersonID());
				return JSON.toJSONString(m);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "{}";
	}

	public static Map getOgnInfo(String FID) throws SQLException {
		String sql = "select * from SA_OPORG o where '" + FID
				+ "' like CONCAT(o.SFID,'%') and o.SORGKINDID = 'ogn' order by o.SFID desc";
		if (DBUtils.IsOracleDB("system")) {
			sql = "select * from SA_OPORG o where '" + FID
					+ "' like o.SFID||'%' and o.SORGKINDID = 'ogn' order by o.SFID desc";
		}
		List list = DBUtils.execQueryforList("system", sql);
		return (Map) list.get(0);
	}

	public static Map getOrgInfo(String FID) throws SQLException {
		String sql = "select * from SA_OPORG o where '" + FID + "' = o.SFID";
		List list = DBUtils.execQueryforList("system", sql);
		return (Map) list.get(0);
	}

	public static String getAllRoles(String psn1, String psn2) throws SQLException {
		String sql = "select r.SID,r.SCODE,r.SNAME,t.SORGID,t.SORGNAME from "
				+ "sa_opauthorize t left join sa_oprole r on r.sid = t.sauthorizeroleid ";
		if (DBUtils.IsOracleDB("system")) {
			sql += "where '" + psn1 + "' like t.SORGFID||'%' or '" + psn2 + "' like t.SORGFID||'%' ";
		} else {
			sql += "where '" + psn1 + "' like CONCAT(t.SORGFID,'%') or '" + psn2 + "' like CONCAT(t.SORGFID,'%')";
		}
		List list = DBUtils.execQueryforList("system", sql);
		StringArray rolelist = new StringArray();
		for (int i = 0; i < list.size(); i++) {
			rolelist.push((String) ((Map) list.get(i)).get("SID"));
		}
		return rolelist.join(",");
	}
}
