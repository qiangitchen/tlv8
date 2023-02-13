package com.tlv8.opm;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.naming.NamingException;

import org.apache.ibatis.session.SqlSession;
import org.apache.log4j.Logger;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tlv8.base.ActionSupport;
import com.tlv8.base.Data;
import com.tlv8.base.db.DBUtils;
import com.tlv8.system.BaseController;

/**
 * @author ChenQian
 */
@Controller
@Scope("prototype")
public class DeleteOrgGridInfo extends ActionSupport {
	Logger log = Logger.getLogger(getClass());
	private Data data;
	private String rowid;

	public void setData(Data data) {
		this.data = data;
	}

	public Data getData() {
		return data;
	}

	@ResponseBody
	@RequestMapping(value = "/deleteOrgGridInfo", produces = "application/json;charset=UTF-8", method = RequestMethod.POST)
	public Object execute() throws Exception {
		data = new Data();
		String userfname = new BaseController().getContext().getCurrentPersonFullName();
		String r = "";
		String m = "success";
		String f = "";
		try {
			if (rowid != null && ("ORG01".equals(rowid) || rowid.startsWith("PSN01@"))) {
				f = "false";
				m = "操作失败：超管信息不可以删除!";
			} else if (rowid != null && !"".equals(rowid)) {
				r = deleteData();
				f = "true";
			} else {
				f = "false";
				m = "操作失败：未正确指定需要删除的ID.";
			}
		} catch (Exception e) {
			m = "操作失败：" + e.getMessage();
			f = "false";
			e.printStackTrace();
		}
		log.error(userfname + "-删除组织数据：" + rowid + "！");
		data.setData(r);
		data.setFlag(f);
		data.setMessage(m);
		return this;
	}

	public String deleteData() throws SQLException, NamingException {
		String result = "";
		SqlSession session = DBUtils.getSession("system");
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		String kiSQL = "select SORGKINDID,SID,SFID,SPERSONID from SA_OPOrg where SID = ?";
		String sql = "update SA_OPOrg o set o.SVALIDSTATE = -1 where (o.SID = ?";
		String pSQL = "update SA_OPPerson p set p.SVALIDSTATE = -1 where p.SID <> 'PSN01' and p.SID =?";
		String cSQL = "select SID from SA_OPOrg o where o.SID <> ? and o.SPERSONID = ?";
		try {
			conn = session.getConnection();
			ps = conn.prepareStatement(kiSQL);
			ps.setString(1, getRowid());
			rs = ps.executeQuery();
			if (rs.next()) {
				String sordkind = rs.getString(1);
				if ("psm".equals(sordkind.toLowerCase())) {
					PreparedStatement ps0 = conn.prepareStatement(cSQL);
					ps0.setString(1, getRowid());
					ps0.setString(2, rs.getString(4));
					ResultSet rs0 = ps0.executeQuery();
					if (!rs0.next()) {
						PreparedStatement ps1 = conn.prepareStatement(pSQL);
						ps1.setString(1, rs.getString(4));
						ps1.executeUpdate();
						DBUtils.closeConn(null, null, ps1, null);
					}
					DBUtils.closeConn(null, null, ps0, rs0);
				}
				sql += " or SFID like '" + rs.getString(3) + "%') and o.SID <> 'ORG01' and o.SID <> 'PSN01@ORG01'";
			}
			PreparedStatement ps2 = conn.prepareStatement(sql);
			ps2.setString(1, getRowid());
			ps2.executeUpdate();
			DBUtils.closeConn(null, null, ps2, null);
			session.commit(true);
		} catch (SQLException e) {
			session.rollback(true);
			throw new SQLException(e);
		} finally {
			DBUtils.closeConn(session, conn, ps, rs);
		}
		return result;
	}

	public void setRowid(String rowid) {
		try {
			this.rowid = URLDecoder.decode(rowid, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
	}

	public String getRowid() {
		return rowid;
	}

}
