package com.tlv8.opm;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.sql.PreparedStatement;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tlv8.base.db.DBUtils;
import com.tlv8.base.ActionSupport;

/**
 * 更新组织机构及人员信息
 * 
 * @author 陈乾
 * @update 2021-04-01
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
		PreparedStatement ps = null;
		try {
			String qsql = "select SID,SCODE,SNAME,SMOBILEPHONE,SFAMILYADDRESS,SZIP from SA_OPPERSON where SID = '"
					+ personid + "'";
			List<Map<String, String>> peli = DBUtils.selectStringList(session, qsql);
			if (peli.size() > 0) {
				String chSql = "select SID,SFID,SFCODE,SFNAME from SA_OPORG where SPERSONID = '" + personid + "'";
				List<Map<String, String>> orli = DBUtils.selectStringList(session, chSql);
				String scode = peli.get(0).get("SCODE");
				String sname = peli.get(0).get("SNAME");
				String SPHONE = peli.get(0).get("SMOBILEPHONE");
				String SADDRESS = peli.get(0).get("SFAMILYADDRESS");
				String SZIP = peli.get(0).get("SZIP");
				String newid = personid + "@" + orgid;
				List<Map<String, String>> oli = DBUtils.selectStringList(session,
						"select SFID,SFCODE,SFNAME,SLEVEL from SA_OPORG where SID = '" + orgid + "'");
				String sfid = oli.get(0).get("SFID");
				String sfcode = oli.get(0).get("SFCODE");
				String sfname = oli.get(0).get("SFNAME");
				String level = oli.get(0).get("SLEVEL");
				if (orli.size() > 0) {
					String upSql = "update SA_OPORG set SCODE=?,SNAME=?,SFID=?,SFCODE=?,SFNAME=?,SLEVEL=?,VERSION=VERSION+1 where SPERSONID = ?";
					ps = session.getConnection().prepareStatement(upSql);
					ps.setString(1, scode);
					ps.setString(2, sname);
					ps.setString(3, sfid + "/" + newid + ".psm");
					ps.setString(4, sfcode + "/" + scode);
					ps.setString(5, sfname + "/" + sname);
					ps.setInt(6, Integer.valueOf(level) + 1);
					ps.setString(7, personid);
					ps.executeUpdate();
				} else {
					String sql = "insert into SA_OPORG(SID,SPARENT,SCODE,SNAME,SPHONE,SADDRESS,SZIP,SFID,SFCODE,SFNAME,SORGKINDID,SPERSONID,SVALIDSTATE,SLEVEL,VERSION)values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
					ps = session.getConnection().prepareStatement(sql);
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
			session.commit(true);
		} catch (Exception e) {
			session.rollback(true);
			e.printStackTrace();
		} finally {
			DBUtils.CloseConn(session, null, ps, null);
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
