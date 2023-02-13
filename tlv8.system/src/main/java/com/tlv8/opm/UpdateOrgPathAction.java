package com.tlv8.opm;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import org.apache.ibatis.session.SqlSession;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tlv8.base.db.DBUtils;
import com.tlv8.base.ActionSupport;

/**
 * 更新机构路径信息
 * 
 * @author chenqian
 * @update 2021/4/21
 */
@Controller
@Scope("prototype")
public class UpdateOrgPathAction extends ActionSupport {
	private String rowid;
	private String sparent;
	private String scode;
	private String sname;

	@ResponseBody
	@RequestMapping(value = "/updateOrgPathAction", produces = "application/json;charset=UTF-8", method = RequestMethod.POST)
	public Object execute() throws Exception {
		SqlSession session = DBUtils.getSession("system");
		Connection conn = null;
		try {
			conn = session.getConnection();
			String upSQL = "update SA_OPORG set SFID=?,SFCODE=?,SFNAME=? where sID = ?";
			String fid = "", fcode = "", fname = "";
			if (sparent != null && !"".equals(sparent)) {
				String sql = "select SFID,SFCODE,SFNAME from SA_OPORG where SID = ?";
				PreparedStatement ps = conn.prepareStatement(sql);
				ps.setString(1, sparent);
				ResultSet rs = ps.executeQuery();
				if (rs.next()) {
					fid = rs.getString(1);
					fcode = rs.getString(2);
					fname = rs.getString(3);
				}
				DBUtils.closeConn(null, null, ps, rs);
			}
			String sql1 = "select SID,SCODE,SNAME,SORGKINDID from SA_OPORG where SID = ?";
			PreparedStatement ps1 = conn.prepareStatement(sql1);
			ps1.setString(1, rowid);
			ResultSet rs1 = ps1.executeQuery();
			if (rs1.next()) {
				fid += "/" + rs1.getString(1) + "." + rs1.getString(4);
				fcode += "/" + rs1.getString(2);
				fname += "/" + rs1.getString(3);
			}
			DBUtils.closeConn(null, null, ps1, rs1);
			PreparedStatement ps2 = conn.prepareStatement(upSQL);
			ps2.setString(1, fid);
			ps2.setString(2, fcode);
			ps2.setString(3, fname);
			ps2.setString(4, rowid);
			ps2.executeUpdate();
			DBUtils.closeConn(null, null, ps2, null);
			updataChild(session, rowid, fid, fcode, fname);
			session.commit(true);
		} catch (Exception e) {
			session.rollback(true);
			e.printStackTrace();
		} finally {
			DBUtils.closeConn(session, conn, null, null);
		}
		return this;
	}

	private void updataChild(SqlSession session, String sid, String fid, String fcode, String fname) throws Exception {
		String sql = "select SID,SORGKINDID,SCODE,SNAME from SA_OPORG where SPARENT = ? ";
		Connection conn = session.getConnection();
		PreparedStatement ps = conn.prepareStatement(sql);
		ps.setString(1, sid);
		ResultSet rs = ps.executeQuery();
		while (rs.next()) {
			String upSQL = "update SA_OPORG set SFID=?,SFCODE=?,SFNAME=? where sID = ?";
			String id = rs.getString(1);
			String sfid = fid + "/" + rs.getString(1) + "." + rs.getString(2);
			String sfcode = fcode + "/" + rs.getString(3);
			String sfname = fname + "/" + rs.getString(4);
			PreparedStatement ps2 = conn.prepareStatement(upSQL);
			ps2.setString(1, sfid);
			ps2.setString(2, sfcode);
			ps2.setString(3, sfname);
			ps2.setString(4, id);
			ps2.executeUpdate();
			DBUtils.closeConn(null, null, ps2, null);
			updataChild(session, id, sfid, sfcode, sfname);
		}
		DBUtils.closeConn(null, null, ps, rs);
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

	public void setSparent(String sparent) {
		try {
			this.sparent = URLDecoder.decode(sparent, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
	}

	public String getSparent() {
		return sparent;
	}

	public void setScode(String scode) {
		try {
			this.scode = URLDecoder.decode(scode, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
	}

	public String getScode() {
		return scode;
	}

	public void setSname(String sname) {
		try {
			this.sname = URLDecoder.decode(sname, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
	}

	public String getSname() {
		return sname;
	}

}
