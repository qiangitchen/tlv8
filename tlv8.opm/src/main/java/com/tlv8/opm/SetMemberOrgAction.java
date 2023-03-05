package com.tlv8.opm;

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
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tlv8.base.Data;
import com.tlv8.base.db.DBUtils;
import com.tlv8.base.ActionSupport;

/**
 * 设置所属部门
 * 
 * @author 陈乾
 */
@Controller
@Scope("prototype")
public class SetMemberOrgAction extends ActionSupport {
	private String rowid;
	private Data data;

	@ResponseBody
	@RequestMapping(value = "/setMemberOrgAction", produces = "application/json;charset=UTF-8", method = RequestMethod.POST)
	public Object execute() throws Exception {
		data = new Data();
		SqlSession session = DBUtils.getSession("system");
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			conn = session.getConnection();
			List<Map<String, String>> list = DBUtils.selectStringList(session,
					"select SPERSONID,SPARENT from SA_OPOrg where SID = '" + rowid + "' and SORGKINDID = 'psm'");
			if (list.size() > 0) {
				String personid = list.get(0).get("SPERSONID");
				String orgID = list.get(0).get("SPARENT");
				String neworgid = personid + "@" + orgID;
				String qsql = new SQL() {
					{
						SELECT("o.SFID,c.SORGKINDID,o.SFCODE,c.SCODE,o.SFNAME,c.SNAME");
						FROM("SA_OPOrg o,SA_OPOrg c");
						WHERE("o.sID = ? and c.sID = ?");
					}
				}.toString();
				String upSQL = new SQL() {
					{
						UPDATE("SA_OPOrg o");
						SET("o.SID=?,o.sparent=?,o.sfid=?,o.sfcode=?,o.sfname=?,o.SNODEKIND='nkLeaf'");
						WHERE("o.sID = ?");
					}
				}.toString();
				ps = conn.prepareStatement(qsql);
				ps.setString(1, orgID);
				ps.setString(2, rowid);
				rs = ps.executeQuery();
				if (rs.next()) {
					// 更新机构路径
					PreparedStatement ps1 = conn.prepareStatement(upSQL);
					ps1.setString(1, neworgid);
					ps1.setString(2, orgID);
					ps1.setString(3, rs.getString("SFID") + "/" + neworgid + "." + rs.getString("SORGKINDID"));
					ps1.setString(4, rs.getString("SFCODE") + "/" + rs.getString("SCODE"));
					ps1.setString(5, rs.getString("SFNAME") + "/" + rs.getString("SNAME"));
					ps1.setString(6, rowid);
					ps1.executeUpdate();
					DBUtils.closeConn(null, null, ps1, null);
					// 更新其他部门下的人员为（分配）
					DBUtils.excuteUpdate(session, "update SA_OPOrg set SNODEKIND='nkLimb' where SPERSONID='" + personid
							+ "' and SID !='" + neworgid + "' and SORGKINDID = 'psm'");
					// 更新人员机构ID
					String upPsm = "update SA_OPPERSON set SMAINORGID = '" + orgID + "' where SID ='" + personid + "'";
					DBUtils.excuteUpdate("system", upPsm);
					session.commit(true);
					data.setFlag("true");
				}
			} else {
				data.setFlag("false");
				data.setMessage("指定的人员不存在!");
			}
		} catch (Exception e) {
			session.rollback(true);
			data.setFlag("false");
			data.setMessage(e.getMessage());
			e.printStackTrace();
		} finally {
			DBUtils.closeConn(session, conn, ps, rs);
		}
		return this;
	}

	public String getRowid() {
		return rowid;
	}

	public void setRowid(String rowid) {
		this.rowid = rowid;
		try {
			this.rowid = URLDecoder.decode(rowid, "UTF-8");
		} catch (Exception e) {
		}
	}

	public Data getData() {
		return data;
	}

	public void setData(Data data) {
		this.data = data;
	}
}
