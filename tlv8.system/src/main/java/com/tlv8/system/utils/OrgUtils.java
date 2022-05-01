package com.tlv8.system.utils;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;

import com.tlv8.base.db.DBUtils;
import com.tlv8.system.action.GetSysParams;
import com.tlv8.system.bean.ContextBean;

public class OrgUtils {
	/*
	 * 组织信息构建
	 */
	private String FullID;
	private String FullName;
	private String PersonFullID;
	private String PersonFullCode;
	private String PersonFullName;
	private String PersonID;
	private String PersonCode;
	private String PersonName;
	private String PositionID;
	private String PositionCode;
	private String PositionName;
	private String DeptID;
	private String DeptCode;
	private String DeptName;
	private String OgnID;
	private String OgnCode;
	private String OgnName;
	private String OrgID;
	private String OrgCode;
	private String OrgName;

	public OrgUtils(String PersonID) {// 构建指定人员的详细信息
		init(PersonID);
	}

	public OrgUtils(HttpServletRequest request) {// 未指定ID 获取当前登录人信息
		ContextBean context = ContextBean.getContext(request);
		setPersonFullID(context.getCurrentPersonFullID());
		setPersonFullCode(context.getCurrentPersonFullCode());
		setPersonFullName(context.getCurrentPersonFullName());
		setPersonID(context.getCurrentPersonID());
		setPersonCode(context.getCurrentPersonCode());
		setPersonName(context.getCurrentPersonName());
		setPositionID(context.getCurrentPositionID());
		setPositionCode(context.getCurrentPositionCode());
		setPositionName(context.getCurrentPositionName());
		setDeptID(context.getCurrentDeptID());
		setDeptCode(context.getCurrentDeptCode());
		setDeptName(context.getCurrentDeptName());
		setOgnID(context.getCurrentOgnID());
		setOgnCode(context.getCurrentOgnCode());
		setOgnName(context.getCurrentOgnName());
		setOrgID(context.getCurrentOrgID());
		setOrgCode(context.getCurrentOrgCode());
		setOrgName(context.getCurrentOrgName());
	}

	public String getPositionCode() {
		return PositionCode;
	}

	public void setPositionCode(String positionCode) {
		PositionCode = positionCode;
	}

	public String getPositionName() {
		return PositionName;
	}

	public void setPositionName(String positionName) {
		PositionName = positionName;
	}

	public String getPositionID() {
		return PositionID;
	}

	public void setPositionID(String positionID) {
		PositionID = positionID;
	}

	public String getDeptCode() {
		return DeptCode;
	}

	public void setDeptCode(String deptCode) {
		DeptCode = deptCode;
	}

	public String getDeptName() {
		return DeptName;
	}

	public void setDeptName(String deptName) {
		DeptName = deptName;
	}

	public String getDeptID() {
		return DeptID;
	}

	public void setDeptID(String deptID) {
		DeptID = deptID;
	}

	public String getPersonFullCode() {
		return PersonFullCode;
	}

	public void setPersonFullCode(String personFullCode) {
		PersonFullCode = personFullCode;
	}

	public String getPersonFullName() {
		return PersonFullName;
	}

	public void setPersonFullName(String personFullName) {
		PersonFullName = personFullName;
	}

	public String getPersonFullID() {
		return PersonFullID;
	}

	public void setPersonFullID(String personFullID) {
		PersonFullID = personFullID;
	}

	public String getPersonName() {
		return PersonName;
	}

	public void setPersonName(String personName) {
		PersonName = personName;
	}

	public String getPersonCode() {
		return PersonCode;
	}

	public void setPersonCode(String personCode) {
		PersonCode = personCode;
	}

	public String getPersonID() {
		return PersonID;
	}

	public void setPersonID(String personID) {
		PersonID = personID;
	}

	public String getOgnCode() {
		return OgnCode;
	}

	public void setOgnCode(String ognCode) {
		OgnCode = ognCode;
	}

	public String getOgnName() {
		return OgnName;
	}

	public void setOgnName(String ognName) {
		OgnName = ognName;
	}

	public String getOgnID() {
		return OgnID;
	}

	public void setOgnID(String ognID) {
		OgnID = ognID;
	}

	public String getOrgCode() {
		return OrgCode;
	}

	public void setOrgCode(String orgCode) {
		OrgCode = orgCode;
	}

	public String getOrgName() {
		return OrgName;
	}

	public void setOrgName(String orgName) {
		OrgName = orgName;
	}

	public String getOrgID() {
		return OrgID;
	}

	public void setOrgID(String orgID) {
		OrgID = orgID;
	}

	private static String sql = ("select o.sName username,o.SPERSONID personID,o.sName personName,"
			+ "o.sCode personCode,f.sID orgID,f.sName orgName,f.sCode orgCode,o.SPERSONID agentPersonID,"
			+ "o.sName agentPersonName,o.sCode agentPersonCode,o.sID agentOrgID,o.sName agentOrgName,"
			+ "o.sFID agentOrgPath,o.SFCODE,o.SFNAME " + " from  SA_OPOrg o left join SA_OPOrg f on o.SPARENT = f.SID").toUpperCase();

	@SuppressWarnings({ "deprecation", "resource" })
	private void init(String sID) {
		String sqls = sql + " where (upper(o.SPERSONID) = upper('" + sID + "') or upper(o.SFID) = upper('" + sID
				+ "') or upper(o.SID) = upper('" + sID + "')) and upper(o.SORGKINDID) = 'psm'";
		HashMap<String, String> m = new HashMap<String, String>();
		Connection conn = null;
		Statement stm = null;
		ResultSet rs = null;
		boolean isPerson = false;
		try {
			conn = DBUtils.getAppConn("system");
			stm = conn.createStatement();
			rs = stm.executeQuery(sqls.toUpperCase());
			if (rs.next()) {
				m.put("username", rs.getString(1));
				m.put("personID", rs.getString(2));
				m.put("personName", rs.getString(3));
				m.put("personCode", rs.getString(4));
				m.put("orgID", rs.getString(5));
				m.put("orgName", rs.getString(6));
				m.put("orgCode", rs.getString(7));
				m.put("agentPersonID", rs.getString(8));
				m.put("agentPersonName", rs.getString(9));
				m.put("agentPersonCode", rs.getString(10));
				m.put("agentOrgID", rs.getString(11));
				m.put("agentOrgName", rs.getString(12));
				m.put("personFullID", rs.getString(13));
				m.put("personFullCode", rs.getString(14));
				m.put("personFullName", rs.getString(15));
				isPerson = true;
			} else {
				String orgquery = "select SID,SCODE,SNAME,SFID,SFCODE,SFNAME,SORGKINDID,SPERSONID from SA_OPORG where SID = '"
						+ sID + "' or SFID = '" + sID + "'";
				rs = stm.executeQuery(orgquery);
				if (rs.next()) {
					m.put("orgID", rs.getString(1));
					m.put("orgCode", rs.getString(2));
					m.put("orgName", rs.getString(3));
					m.put("orgFullID", rs.getString(4));
					m.put("orgFullCode", rs.getString(5));
					m.put("orgFullName", rs.getString(6));
					setFullID(rs.getString(4));
					setFullName(rs.getString(6));
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				DBUtils.CloseConn(conn, stm, rs);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		try {
			GetSysParams.getSysParamsFunc(m);
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (isPerson) {
			setFullID((String) m.get("personFullID"));
			setFullName((String) m.get("personFullName"));
			setPersonID((String) m.get("personID"));
			setPersonCode((String) m.get("personCode"));
			setPersonName((String) m.get("personName"));
			setPersonFullID((String) m.get("personFullID"));
			setPersonFullCode((String) m.get("personFullCode"));
			setPersonFullName((String) m.get("personFullName"));
		}
		setPositionID((String) m.get("currentPositionID"));
		setPositionCode((String) m.get("currentPositionCode"));
		setPositionName((String) m.get("currentPositionName"));
		setDeptID((String) m.get("currentDeptID"));
		setDeptCode((String) m.get("currentDeptCode"));
		setDeptName((String) m.get("currentDeptName"));
		setOgnID((String) m.get("currentOgnID"));
		setOgnCode((String) m.get("currentOgnCode"));
		setOgnName((String) m.get("currentOgnName"));
		setOrgID((String) m.get("orgID"));
		setOrgCode((String) m.get("orgCode"));
		setOrgName((String) m.get("orgName"));
	}

	public void setFullID(String fullID) {
		FullID = fullID;
	}

	public String getFullID() {
		return FullID;
	}

	public void setFullName(String fullName) {
		FullName = fullName;
	}

	public String getFullName() {
		return FullName;
	}

}
