package com.tlv8.opm;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import com.tlv8.base.db.DBUtils;
import com.tlv8.system.bean.ContextBean;
import com.tlv8.system.utils.ContextUtils;

public class OPMOrgUtils {
	private String orgid;
	private String orgcode;
	private String orgname;
	private String orgfid;
	private String orgfcode;
	private String orgfname;
	private String ognid;
	private String ogncode;
	private String ognname;
	private String ognfid;
	private String ognfcode;
	private String ognfname;
	private String dptid;
	private String dptcode;
	private String dptname;
	private String dptfid;
	private String dptfcode;
	private String dptfname;
	private String posid;
	private String poscode;
	private String posname;
	private String posfid;
	private String posfcode;
	private String posfname;
	private String personid;

	public OPMOrgUtils() {
		ContextBean context = ContextUtils.getContext();
		setPersonid(context.getPersonID());
		setOrgid(context.getCurrentOrgID());
		setOrgcode(context.getCurrentOrgCode());
		setOrgname(context.getCurrentOrgName());
		setOrgfid(context.getCurrentOrgFullID());
		setOrgfcode(context.getCurrentOrgFullCode());
		setOrgfname(context.getCurrentOrgFullName());
		setOgnid(context.getCurrentOgnID());
		setOgncode(context.getCurrentOgnCode());
		setOgnname(context.getCurrentOgnName());
		setOgnfid(context.getCurrentOgnFullID());
		setOgnfcode(context.getCurrentOgnFullCode());
		setOgnfname(context.getCurrentOgnFullName());
		setDptid(context.getCurrentDeptID());
		setDptcode(context.getCurrentDeptCode());
		setDptname(context.getCurrentDeptName());
		setDptfid(context.getCurrentDeptFullID());
		setDptfcode(context.getCurrentDeptFullCode());
		setDptfname(context.getCurrentDeptFullName());
	}

	public OPMOrgUtils(String Orgid) {
		try {
			List<Map<String, String>> orglist = DBUtils.execQueryforList("system",
					"select SID,SCODE,SNAME,SFID,SFCODE,SFNAME,SPERSONID " + "from SA_OPORG where SID = '" + Orgid
							+ "' or SPERSONID = '" + Orgid + "'");
			Map<String, String> context = orglist.get(0);
			setPersonid(context.get("SPERSONID"));
			setOrgid(context.get("SID"));
			setOrgcode(context.get("SCODE"));
			setOrgname(context.get("SNAME"));
			setOrgfid(context.get("SFID"));
			setOrgfcode(context.get("SFCODE"));
			setOrgfname(context.get("SFNAME"));
			String ognsql = "select SID,SCODE,SNAME,SFID,SFCODE,SFNAME " + "from SA_OPORG where '" + orgfid
					+ "' like SFID||'%' and SORGKINDID = 'ogn' and rownum =1 order by SFID desc";
			if (DBUtils.IsMSSQLDB("system")) {
				ognsql = "select top 1 SID,SCODE,SNAME,SFID,SFCODE,SFNAME " + "from SA_OPORG where '" + orgfid
						+ "' like SFID+'%' and SORGKINDID = 'ogn' order by SFID desc";
			} else if (DBUtils.IsMySQLDB("system")) {
				ognsql = "select SID,SCODE,SNAME,SFID,SFCODE,SFNAME " + "from SA_OPORG where '" + orgfid
						+ "' like concat(SFID,'%') and SORGKINDID = 'ogn' and order by SFID desc limit 1";
			}
			List<Map<String, String>> ognlist = DBUtils.execQueryforList("system", ognsql);
			if (ognlist.size() > 0) {
				context = ognlist.get(0);
				setOgnid(context.get("SID"));
				setOgncode(context.get("SCODE"));
				setOgnname(context.get("SNAME"));
				setOgnfid(context.get("SFID"));
				setOgnfcode(context.get("SFCODE"));
				setOgnfname(context.get("SFNAME"));
			}
			String dptsql = "select SID,SCODE,SNAME,SFID,SFCODE,SFNAME " + "from SA_OPORG where '" + orgfid
					+ "' like SFID||'%' and SORGKINDID = 'dpt' and rownum =1 order by SFID desc";
			if (DBUtils.IsMSSQLDB("system")) {
				dptsql = "select top 1 SID,SCODE,SNAME,SFID,SFCODE,SFNAME " + "from SA_OPORG where '" + orgfid
						+ "' like SFID+'%' and SORGKINDID = 'dpt' order by SFID desc";
			} else if (DBUtils.IsMySQLDB("system")) {
				dptsql = "select SID,SCODE,SNAME,SFID,SFCODE,SFNAME " + "from SA_OPORG where '" + orgfid
						+ "' like concat(SFID,'%') and SORGKINDID = 'dpt' and order by SFID desc limit 1";
			}
			List<Map<String, String>> dptlist = DBUtils.execQueryforList("system", dptsql);
			if (dptlist.size() > 0) {
				context = dptlist.get(0);
				setDptid(context.get("SID"));
				setDptcode(context.get("SCODE"));
				setDptname(context.get("SNAME"));
				setDptfid(context.get("SFID"));
				setDptfcode(context.get("SFCODE"));
				setDptfname(context.get("SFNAME"));
			}
			String possql = "select SID,SCODE,SNAME,SFID,SFCODE,SFNAME " + "from SA_OPORG where '" + orgfid
					+ "' like SFID||'%' and SORGKINDID = 'pos' and rownum =1 order by SFID desc";
			if (DBUtils.IsMSSQLDB("system")) {
				possql = "select top 1 SID,SCODE,SNAME,SFID,SFCODE,SFNAME " + "from SA_OPORG where '" + orgfid
						+ "' like SFID+'%' and SORGKINDID = 'pos' order by SFID desc";
			} else if (DBUtils.IsMySQLDB("system")) {
				possql = "select SID,SCODE,SNAME,SFID,SFCODE,SFNAME " + "from SA_OPORG where '" + orgfid
						+ "' like concat(SFID,'%') and SORGKINDID = 'pos' and order by SFID desc limit 1";
			}
			List<Map<String, String>> poslist = DBUtils.execQueryforList("system", possql);
			if (poslist.size() > 0) {
				context = poslist.get(0);
				setPosid(context.get("SID"));
				setPoscode(context.get("SCODE"));
				setPosname(context.get("SNAME"));
				setPosfid(context.get("SFID"));
				setPosfcode(context.get("SFCODE"));
				setPosfname(context.get("SFNAME"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public String getOrgid() {
		return orgid;
	}

	public void setOrgid(String orgid) {
		this.orgid = orgid;
	}

	public String getOrgcode() {
		return orgcode;
	}

	public void setOrgcode(String orgcode) {
		this.orgcode = orgcode;
	}

	public String getOrgname() {
		return orgname;
	}

	public void setOrgname(String orgname) {
		this.orgname = orgname;
	}

	public String getOrgfid() {
		return orgfid;
	}

	public void setOrgfid(String orgfid) {
		this.orgfid = orgfid;
	}

	public String getOrgfcode() {
		return orgfcode;
	}

	public void setOrgfcode(String orgfcode) {
		this.orgfcode = orgfcode;
	}

	public String getOrgfname() {
		return orgfname;
	}

	public void setOrgfname(String orgfname) {
		this.orgfname = orgfname;
	}

	public String getOgnid() {
		return ognid;
	}

	public void setOgnid(String ognid) {
		this.ognid = ognid;
	}

	public String getOgncode() {
		return ogncode;
	}

	public void setOgncode(String ogncode) {
		this.ogncode = ogncode;
	}

	public String getOgnname() {
		return ognname;
	}

	public void setOgnname(String ognname) {
		this.ognname = ognname;
	}

	public String getOgnfid() {
		return ognfid;
	}

	public void setOgnfid(String ognfid) {
		this.ognfid = ognfid;
	}

	public String getOgnfcode() {
		return ognfcode;
	}

	public void setOgnfcode(String ognfcode) {
		this.ognfcode = ognfcode;
	}

	public String getOgnfname() {
		return ognfname;
	}

	public void setOgnfname(String ognfname) {
		this.ognfname = ognfname;
	}

	public String getDptid() {
		return dptid;
	}

	public void setDptid(String dptid) {
		this.dptid = dptid;
	}

	public String getDptcode() {
		return dptcode;
	}

	public void setDptcode(String dptcode) {
		this.dptcode = dptcode;
	}

	public String getDptname() {
		return dptname;
	}

	public void setDptname(String dptname) {
		this.dptname = dptname;
	}

	public String getDptfid() {
		return dptfid;
	}

	public void setDptfid(String dptfid) {
		this.dptfid = dptfid;
	}

	public String getDptfcode() {
		return dptfcode;
	}

	public void setDptfcode(String dptfcode) {
		this.dptfcode = dptfcode;
	}

	public String getDptfname() {
		return dptfname;
	}

	public void setDptfname(String dptfname) {
		this.dptfname = dptfname;
	}

	public String getPosid() {
		return posid;
	}

	public void setPosid(String posid) {
		this.posid = posid;
	}

	public String getPoscode() {
		return poscode;
	}

	public void setPoscode(String poscode) {
		this.poscode = poscode;
	}

	public String getPosname() {
		return posname;
	}

	public void setPosname(String posname) {
		this.posname = posname;
	}

	public String getPosfid() {
		return posfid;
	}

	public void setPosfid(String posfid) {
		this.posfid = posfid;
	}

	public String getPosfcode() {
		return posfcode;
	}

	public void setPosfcode(String posfcode) {
		this.posfcode = posfcode;
	}

	public String getPosfname() {
		return posfname;
	}

	public void setPosfname(String posfname) {
		this.posfname = posfname;
	}

	public String getPersonid() {
		return personid;
	}

	public void setPersonid(String personid) {
		this.personid = personid;
	}

}
