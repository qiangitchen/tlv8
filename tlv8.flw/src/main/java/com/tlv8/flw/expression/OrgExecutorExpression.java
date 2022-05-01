package com.tlv8.flw.expression;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import com.tlv8.base.db.DBUtils;
import com.tlv8.flw.helper.ExecutorGroupFilter;
import com.tlv8.system.BaseController;
import com.tlv8.system.bean.ContextBean;

@SuppressWarnings({ "rawtypes" })
public class OrgExecutorExpression {

	public static String org_table = "SA_OPORG";

	/**
	 * 获取指定activity权限的组织单元
	 * 
	 * @process: 过程标识,类型是字符串或SYMBOL,为空时表示当前process
	 * 
	 * @activity: 活动标识,类型是字符串
	 * 
	 * @inOrg: 返回值必须在指定组织范围内,值是组织的ID或FID,单值用字符串,多值用cons函数组合
	 * 
	 * @personMember: TRUE表示获取分配了权限的组织单元下的人员成员,FALSE表示只获取到分配了权限的组织单元
	 */
	public static String getOrgUnitHasActivity(String process, String activity, String inOrg, String personMember) {
		String result = "";
		String sql = "select SORGID from sa_opauthorize a inner join sa_oppermission m "
				+ " on m.SPERMISSIONROLEID = a.SAUTHORIZEROLEID where m.SPROCESS = '" + process + "' and SACTIVITY = '"
				+ activity + "'";
		if (inOrg != null && !"".equals(inOrg) && !"FALSE".equals(inOrg) && !"TRUE".equals(inOrg)) {
			sql = "select o.SID SORGID from " + org_table + " o where o.SFID like '%" + inOrg
					+ "%' and o.SID in(select SORGID from sa_opauthorize a inner join sa_oppermission m "
					+ " on m.SPERMISSIONROLEID = a.SAUTHORIZEROLEID where m.SPROCESS = '" + process
					+ "' and SACTIVITY = '" + activity + "')";
		} else if ("FALSE".equals(inOrg) || "TRUE".equals(inOrg)) {// 为false或true时为当前机构下
			ContextBean context = new BaseController().getContext();
			String ognfid = context.getCurrentOgnFullID();
			sql = "select o.SID SORGID from " + org_table + " o where  o.SFID like '" + ognfid + "%' "
					+ " and o.SID in(select SORGID from sa_opauthorize a inner join sa_oppermission m "
					+ " on m.SPERMISSIONROLEID = a.SAUTHORIZEROLEID where m.SPROCESS = '" + process
					+ "' and SACTIVITY = '" + activity + "')";
		}
		if ("TRUE".equals(personMember.toUpperCase())) {
			sql += " and (SORGID like '%@%')";
		}
		try {
			List<Map<String, String>> list = DBUtils.execQueryforList("system", sql);
			for (int i = 0; i < list.size(); i++) {
				Map m = list.get(i);
				if (i > 0)
					result += ",";
				result += (String) m.get("SORGID");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		result = ExecutorGroupFilter.filterSubOgn(result);// 过滤下属机构
		return result;
	}

	/**
	 * 获取指定activity权限的组织单元(跨单位)
	 * 
	 * @process: 过程标识,类型是字符串或SYMBOL,为空时表示当前process
	 * 
	 * @activity: 活动标识,类型是字符串
	 * 
	 * @personMember: TRUE表示获取分配了权限的组织单元下的人员成员,FALSE表示只获取到分配了权限的组织单元
	 */
	public static String getOrgUnitHasActivityInterAgency(String process, String activity, String personMember) {
		String result = "";
		String sql = "select SORGID from sa_opauthorize a inner join sa_oppermission m "
				+ " on m.SPERMISSIONROLEID = a.SAUTHORIZEROLEID where m.SPROCESS = '" + process + "' and SACTIVITY = '"
				+ activity + "'";
		if ("TRUE".equals(personMember.toUpperCase())) {
			sql += " and (SORGID like '%@%')";
		}
		try {
			List<Map<String, String>> list = DBUtils.execQueryforList("system", sql);
			for (int i = 0; i < list.size(); i++) {
				Map m = list.get(i);
				if (i > 0)
					result += ",";
				result += (String) m.get("SORGID");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return result;
	}

	/**
	 * 获取指定组织单元的管理者
	 * 
	 * @org: 组织的ID或FID,单值用字符串或SYMBOL,多值用逗号分隔
	 * 
	 * @manageType: 管理类型的CODE,空表示所有管理类型
	 * 
	 * @cludeParent: FALSE表示直接取ORG上的管理者, TRUE表示ORG的父上的管理者也取
	 * 
	 * @inOrg: 返回值必须在指定组织范围内,值是组织的ID或FID,单值用字符串,多值用cons函数组合
	 * 
	 * @personMember：FALSE表示只获取到org的管理者,TRUE表示获取到org的管理者的人员成员
	 */
	public static String getOrgUnitManager(String org, String manageType, String cludeParent, String inOrg,
			String personMember) {
		String result = "";
		String sql = "select SORGFID from SA_OPMANAGEMENT where 1=1";
		if ("TRUE".equals(cludeParent)) {
			sql += " and (SMANAGEORGID='" + org + "' or '" + org + "' like concat(SMANAGEORGFID,'%'))";
		} else {
			sql += " and (SMANAGEORGID='" + org + "' or SMANAGEORGFID='" + org + "') ";
		}
		if (manageType != null && !"".equals(manageType.trim())) {
			sql += " and SMANAGETYPEID in (select SID from SA_OPMANAGETYPE where SCODE = '" + manageType + "') ";
		}
		try {
			List<Map<String, String>> list = DBUtils.execQueryforList("system", sql);
			for (int i = 0; i < list.size(); i++) {
				Map m = list.get(i);
				if (i > 0)
					result += "," + m.get("SORGFID");
				else
					result += m.get("SORGFID");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return result;
	}

	/**
	 * 获取属于指定角色的组织单元
	 * 
	 * @roleCode: 角色的CODE,单值用字符串,多值用cons函数组合
	 * 
	 * @inOrg: 返回值必须在指定组织范围内,值是组织的ID或FID,单值用字符串,多值用cons函数组合
	 * 
	 * @personMember：是否取到人员成员
	 */
	public static String getOrgUnitHasRoleByCode(String roleCode, String inOrg, String personMember) {
		String result = "";
		String orgUnit = "";
		try {
			ContextBean context = new BaseController().getContext();
			if ("FALSE".equalsIgnoreCase(inOrg) || "TRUE".equalsIgnoreCase(inOrg)) {// 为false或true时为当前机构下
				inOrg = context.getCurrentOgnFullID();
			}
			String sql = "select o.SID SORGID from " + org_table + " o where o.SFID like '%" + inOrg
					+ "%' and EXISTS(select a.SORGID from SA_OPAUTHORIZE a where a.SAUTHORIZEROLEID "
					+ " in (select r.SID from SA_OPROLE r where r.SCODE='" + roleCode
					+ "') and o.SFID like concat(a.SORGFID,'%'))";
			List<Map<String, String>> list = DBUtils.execQueryforList("system", sql);
			for (int i = 0; i < list.size(); i++) {
				Map m = list.get(i);
				if (i > 0)
					orgUnit += ",'" + m.get("SORGID") + "'";
				else
					orgUnit += "'" + m.get("SORGID") + "'";
			}
			if ("TRUE".equalsIgnoreCase(personMember.trim())) {
				sql = "select SID from " + org_table + " o where EXISTS(select SFID from " + org_table
						+ " o1 where SID in (" + orgUnit
						+ ") and o.SFID like concat(o1.SFID,'%')) and SORGKINDID = 'psm'";
				list = DBUtils.execQueryforList("system", sql);
				for (int i = 0; i < list.size(); i++) {
					Map m = list.get(i);
					if (i > 0)
						result += "," + m.get("SID");
					else
						result += m.get("SID");
				}
			} else {
				result = orgUnit.replace("'", "");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		result = ExecutorGroupFilter.filterSubOgn(result);// 过滤下属机构
		return result;
	}

	/**
	 * 获取属于指定角色的组织单元(跨单位)
	 * 
	 * @roleCode: 角色的CODE,单值用字符串,多值用cons函数组合
	 * 
	 * @personMember：是否取到人员成员
	 */
	public static String getOrgUnitHasRoleByCodeInterAgency(String roleCode, String personMember) {
		String result = "";
		String orgUnit = "";
		String sql = "select SORGID from SA_OPAUTHORIZE where SAUTHORIZEROLEID in (select SID from SA_OPROLE where SCODE='"
				+ roleCode + "')";
		try {
			List<Map<String, String>> list = DBUtils.execQueryforList("system", sql);
			for (int i = 0; i < list.size(); i++) {
				Map m = list.get(i);
				if (i > 0)
					orgUnit += ",'" + m.get("SORGID") + "'";
				else
					orgUnit += "'" + m.get("SORGID") + "'";
			}
			if (!"FALSE".equals(personMember.trim()) && !"".equals(orgUnit)) {
				sql = "select SID from " + org_table + " o where EXISTS(select SFID from " + org_table
						+ " o1 where SID in (" + orgUnit
						+ ") and o.SFID like concat(o1.SFID,'%')) and SORGKINDID = 'psm'";
				list = DBUtils.execQueryforList("system", sql);
				for (int i = 0; i < list.size(); i++) {
					Map m = list.get(i);
					if (i > 0)
						result += "," + m.get("SID");
					else
						result += m.get("SID");
				}
			} else {
				result = orgUnit.replace("'", "");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return result;
	}
}
