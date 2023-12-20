package com.tlv8.opm;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

import org.apache.ibatis.session.SqlSession;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tlv8.base.ActionSupport;
import com.tlv8.base.Data;
import com.tlv8.base.db.DBUtils;
import com.tlv8.common.domain.AjaxResult;

/**
 * 分配人员
 * 
 * @author 陈乾
 */
@Controller
@Scope("prototype")
public class AppendPersonMembers extends ActionSupport {
	private String personIds;
	private String orgId;
	private Data data;

	@ResponseBody
	@RequestMapping(value = "/appendPersonMembers", produces = "application/json;charset=UTF-8")
	public Object execute() throws Exception {
		data = new Data();
		String querySql = "select SVALIDSTATE,SPERSONID,SCODE,SNAME,SSEQUENCE from SA_OPORG where SID = ?";
		String checkSql = "select SID from SA_OPORG where SID = ?";
		String addSql = "insert into SA_OPORG(SID,SCODE,SNAME,SPARENT,SFID,SFCODE,SFNAME,SORGKINDID,SVALIDSTATE,SPERSONID,SNODEKIND,SLEVEL,SSEQUENCE,VERSION)"
				+ "values(?,?,?,?,?,?,?,'psm',?,?,?,?,?,1)";
		SqlSession session = DBUtils.getSession("system");
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			conn = session.getConnection();
			conn.setAutoCommit(true);
			String[] persons = personIds.split(",");
			String fid = "";
			String fcode = "";
			String fname = "";
			int level = 1;
			try {
				Statement stm = conn.createStatement();
				ResultSet qs = stm
						.executeQuery("select SFID,SFCODE,SFNAME,SLEVEL from SA_OPORG where SID = '" + orgId + "'");
				if (qs.next()) {
					fid = qs.getString("SFID");
					fcode = qs.getString("SFCODE");
					fname = qs.getString("SFNAME");
					level = qs.getInt("SLEVEL") + 1;
				}
				qs.close();
				stm.close();
			} catch (Exception e) {
			}
			for (int i = 0; i < persons.length; i++) {
				ps = conn.prepareStatement(querySql);
				ps.setString(1, persons[i]);
				rs = ps.executeQuery();
				while (rs.next()) {
					int state = rs.getInt("SVALIDSTATE");
					String personid = rs.getString("SPERSONID");
					String scode = rs.getString("SCODE");
					String sname = rs.getString("SNAME");
					String newpsmid = personid + "@" + orgId;
					int sequence = rs.getInt("SSEQUENCE");
					PreparedStatement ps1 = conn.prepareStatement(checkSql);
					ps1.setString(1, newpsmid);
					ResultSet rs1 = ps1.executeQuery();
					if (rs1.next()) {
						data.setFlag("false");
						data.setMessage("[" + sname + "]已经在当前部门，不需要分配!");
					} else {
						PreparedStatement ps2 = conn.prepareStatement(addSql);
						ps2.setString(1, newpsmid);
						ps2.setString(2, scode);
						ps2.setString(3, sname);
						ps2.setString(4, orgId);
						ps2.setString(5, fid + "/" + newpsmid);
						ps2.setString(6, fcode + "/" + scode);
						ps2.setString(7, fname + "/" + sname);
						ps2.setInt(8, state);
						ps2.setString(9, personid);
						ps2.setString(10, "nkLimb");
						ps2.setInt(11, level);
						ps2.setInt(12, sequence);
						ps2.executeUpdate();
						DBUtils.closeConn(null, null, ps2, null);
					}
					DBUtils.closeConn(null, null, ps1, rs1);
				}
			}
			data.setFlag("true");
		} catch (Exception e) {
			data.setFlag("false");
			e.printStackTrace();
		} finally {
			DBUtils.closeConn(session, conn, ps, rs);
		}
		return AjaxResult.success(data);
	}

	public void setPersonIds(String personIds) {
		try {
			this.personIds = URLDecoder.decode(personIds, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
	}

	public String getPersonIds() {
		return personIds;
	}

	public void setOrgId(String orgId) {
		try {
			this.orgId = URLDecoder.decode(orgId, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
	}

	public String getOrgId() {
		return orgId;
	}

	public void setData(Data data) {
		this.data = data;
	}

	public Data getData() {
		return data;
	}

}
