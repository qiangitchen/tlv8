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

import com.tlv8.base.ActionSupport;
import com.tlv8.base.db.DBUtils;

/**
 * 更新组织机构及人员信息
 * 
 * @author 陈乾
 * @update 2022-10-18
 */
@Controller
@Scope("prototype")
public class UpdateOrgPersonInfo extends ActionSupport {
	private String personid;
	private String orgid;

	@ResponseBody
	@RequestMapping(value = "/updateOrgPersonInfo", produces = "application/json;charset=UTF-8", method = RequestMethod.POST)
	public Object execute() throws Exception {
		SqlSession session = DBUtils.getSession("system");
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			conn = session.getConnection();
			String qsql = "select SID,SCODE,SNAME,SMOBILEPHONE,SFAMILYADDRESS,SZIP from SA_OPPERSON where SID = ?";
			PreparedStatement ps1 = conn.prepareStatement(qsql);
			ps1.setString(1, personid);
			rs = ps1.executeQuery();
			if (rs.next()) {
				String chSql = "select SID,SFID,SFCODE,SFNAME from SA_OPORG where SPERSONID = ? and SPARENT = ?";
				PreparedStatement ps2 = conn.prepareStatement(chSql);
				ps2.setString(1, personid);
				ps2.setString(2, orgid);
				ResultSet rs2 = ps2.executeQuery();
				String scode = rs.getString("SCODE");
				String sname = rs.getString("SNAME");
				String SPHONE = rs.getString("SMOBILEPHONE");
				String SADDRESS = rs.getString("SFAMILYADDRESS");
				String SZIP = rs.getString("SZIP");
				String newid = personid + "@" + orgid;
				String porgSql = "select SFID,SFCODE,SFNAME,SLEVEL from SA_OPORG where SID = ?";
				PreparedStatement ps3 = conn.prepareStatement(porgSql);
				ps3.setString(1, orgid);
				ResultSet rs3 = ps3.executeQuery();
				if (rs3.next()) {
					String sfid = rs3.getString("SFID");
					String sfcode = rs3.getString("SFCODE");
					String sfname = rs3.getString("SFNAME");
					String level = rs3.getString("SLEVEL");
					if (rs2.next()) {
						String upSql = "update SA_OPORG set SCODE=?,SNAME=?,SFID=?,SFCODE=?,SFNAME=?,SLEVEL=?,VERSION=VERSION+1 where SPERSONID = ? and SPARENT = ?";
						ps = conn.prepareStatement(upSql);
						ps.setString(1, scode);
						ps.setString(2, sname);
						ps.setString(3, sfid + "/" + newid + ".psm");
						ps.setString(4, sfcode + "/" + scode);
						ps.setString(5, sfname + "/" + sname);
						ps.setInt(6, Integer.valueOf(level) + 1);
						ps.setString(7, personid);
						ps.setString(8, orgid);
						ps.executeUpdate();
					} else {
						String sql = "insert into SA_OPORG(SID,SPARENT,SCODE,SNAME,SPHONE,SADDRESS,SZIP,SFID,SFCODE,SFNAME,SORGKINDID,SPERSONID,SVALIDSTATE,SLEVEL,VERSION)values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
						ps = conn.prepareStatement(sql);
						ps.setString(1, newid);
						ps.setString(2, orgid);
						ps.setString(3, scode);
						ps.setString(4, sname);
						ps.setString(5, SPHONE);
						ps.setString(6, SADDRESS);
						ps.setString(7, SZIP);
						ps.setString(8, sfid + "/" + newid + ".psm");
						ps.setString(9, sfcode + "/" + scode);
						ps.setString(10, sfname + "/" + sname);
						ps.setString(11, "psm");
						ps.setString(12, personid);
						ps.setInt(13, 1);
						ps.setInt(14, Integer.valueOf(level) + 1);
						ps.setInt(15, 0);
						ps.executeUpdate();
					}
				}
			}
			session.commit(true);
		} catch (Exception e) {
			session.rollback(true);
			e.printStackTrace();
		} finally {
			DBUtils.CloseConn(session, conn, ps, rs);
		}
		return this;
	}

	public void setPersonid(String personid) {
		try {
			this.personid = URLDecoder.decode(personid, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
	}

	public String getPersonid() {
		return personid;
	}

	public void setOrgid(String orgid) {
		try {
			this.orgid = URLDecoder.decode(orgid, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
	}

	public String getOrgid() {
		return orgid;
	}

}
