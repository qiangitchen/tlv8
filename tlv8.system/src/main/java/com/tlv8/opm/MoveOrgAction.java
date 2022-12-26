package com.tlv8.opm;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.jdbc.SQL;
import org.apache.ibatis.session.SqlSession;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tlv8.base.Data;
import com.tlv8.base.db.DBUtils;
import com.tlv8.base.ActionSupport;
import com.tlv8.opm.org.OrgchildsFullpathUpdate;

/**
 * @d 组织机构及人员移动
 * @author 陈乾
 */
@Controller
@Scope("prototype")
public class MoveOrgAction extends ActionSupport {
	private Data data;
	private String rowid;
	private String orgID;
	private String dbkey;
	private String sql;

	public Data getdata() {
		return this.data;
	}

	public void setdata(Data data) {
		this.data = data;
	}

	@ResponseBody
	@RequestMapping(value = "/moveOrgAction", produces = "application/json;charset=UTF-8")
	public Object execute() throws Exception {
		data = new Data();
		String r = "true";
		String m = "success";
		String f = "";
		setDbkey("system");
		String upSQL = "";
		String querypsm = "select SPERSONID from SA_OPOrg where SID = '" + rowid + "' and sorgkindid = 'psm'";
		String personid = "";
		String neworgid = rowid;
		boolean isperson = false;
		SqlSession session = DBUtils.getSession("system");
		Connection conn = session.getConnection();
		List<Map<String, String>> list = DBUtils.selectStringList(session, querypsm);
		if (list.size() > 0) {
			personid = list.get(0).get("SPERSONID");
			neworgid = personid + "@" + orgID;
			isperson = true;
		} else {
			neworgid = rowid;
			isperson = false;
		}
		String qsql = new SQL() {
			{
				SELECT("o.SFID,c.SORGKINDID,o.SFCODE,c.SCODE,o.SFNAME,c.SNAME");
				FROM("SA_OPOrg o,SA_OPOrg c");
				WHERE("o.sID = ? and c.sID = ?");
			}
		}.toString();
		upSQL = new SQL() {
			{
				UPDATE("SA_OPOrg o");
				SET("o.SID=?,o.sparent=?,o.sfid=?,o.sfcode=?,o.sfname=?");
				WHERE("o.sID = ?");
			}
		}.toString();
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			PreparedStatement ps1 = conn.prepareStatement(qsql);
			ps1.setString(1, orgID);
			ps1.setString(2, rowid);
			rs = ps1.executeQuery();
			if (rs.next()) {
				ps = conn.prepareStatement(upSQL);
				ps.setString(1, neworgid);
				ps.setString(2, orgID);
				ps.setString(3, rs.getString("SFID") + "/" + neworgid + "." + rs.getString("SORGKINDID"));
				ps.setString(4, rs.getString("SFCODE") + "/" + rs.getString("SCODE"));
				ps.setString(5, rs.getString("SFNAME") + "/" + rs.getString("SNAME"));
				ps.setString(6, rowid);
				ps.executeUpdate();
				if (isperson) {// 人员类型数据更新主机构ID
					String upPsm = "update SA_OPPERSON set SMAINORGID = '" + orgID + "' where SID ='" + personid + "'";
					DBUtils.excuteUpdate(session, upPsm);
				}
				OrgchildsFullpathUpdate.upOrgpath(session, neworgid);// 更新下级机构路径
				OrgchildsFullpathUpdate.upAutherPermOrgpath(session, rowid, neworgid);// 更新角色授权
				OrgchildsFullpathUpdate.upAutherPermOrgpaths(session, neworgid);// 更新所有子机构授权
				session.commit(true);
				f = "true";
			} else {
				m = "操作失败：指定的机构不存在.";
				f = "false";
			}
		} catch (Exception e) {
			session.rollback(true);
			m = "操作失败：" + e.getMessage();
			f = "false";
			e.printStackTrace();
		} finally {
			DBUtils.CloseConn(session, conn, ps, rs);
		}
		data.setData(r);
		data.setFlag(f);
		data.setMessage(m);
		return this;
	}

	public void setDbkey(String dbkey) {
		this.dbkey = dbkey;
	}

	public String getDbkey() {
		return dbkey;
	}

	public void setSql(String sql) {
		try {
			this.sql = URLDecoder.decode(sql, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
	}

	public String getSql() {
		return sql;
	}

	public void setRowid(String rowid) {
		try {
			this.rowid = URLDecoder.decode(rowid, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			this.rowid = rowid;
		}
	}

	public String getRowid() {
		return rowid;
	}

	public void setOrgID(String orgID) {
		try {
			this.orgID = URLDecoder.decode(orgID, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			this.orgID = orgID;
		}
	}

	public String getOrgID() {
		return orgID;
	}
}
