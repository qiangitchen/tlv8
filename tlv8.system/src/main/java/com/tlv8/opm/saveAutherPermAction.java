package com.tlv8.opm;

import java.net.URLDecoder;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tlv8.base.Data;
import com.tlv8.base.db.DBUtils;
import com.tlv8.base.utils.IDUtils;
import com.tlv8.base.ActionSupport;

/**
 * @C TODO 新增保存授权信息
 * @author ChenQain
 */
@Controller
@Scope("prototype")
public class saveAutherPermAction extends ActionSupport {
	private Data data = new Data();
	private String orgID = "";
	private String roleIDs = "";
	private String creatorfID = "";
	private String creatorFNAME = "";

	@ResponseBody
	@RequestMapping("/saveAutherPermAction")
	public Object execute() throws Exception {
		data = new Data();
		String r = "";
		String m = "success";
		String f = "";
		try {
			r = setAutherP();
			f = "true";
		} catch (Exception e) {
			m = "操作失败：" + e.getMessage();
			f = "false";
			e.printStackTrace();
		}
		data.setData(r);
		data.setFlag(f);
		data.setMessage(m);
		return this;
	}

	private String setAutherP() throws Exception {
		String result = "";
		String addrole = "";
		String[] roleid = roleIDs.split(",");
		for (int i = 0; i < roleid.length; i++) {
			String chSql = "select SID from SA_OPAUTHORIZE where SORGID='" + orgID + "' and SAUTHORIZEROLEID='"
					+ roleid[i] + "'";
			List<Map<String, String>> li = DBUtils.execQueryforList("system", chSql);
			if (li.size() < 1) {
				addrole += ",'" + roleid[i] + "'";
			} else {
				addrole += ",''";
			}
		}
		addrole = addrole.replaceFirst(",", "");
		String sql = "insert into SA_OPAUTHORIZE"
				+ "(SID,SORGID,SORGNAME,SORGFID,SORGFNAME,SAUTHORIZEROLEID,SDESCRIPTION,SCREATORFID,SCREATORFNAME,SCREATETIME,version)"
				+ "values(?,?,?,?,?,?,?,?,?,?,?)";
		if (!"".equals(roleIDs) && !"".equals(orgID)) {
			SqlSession session = DBUtils.getSession("system");
			Connection conn = null;
			Statement stm = null;
			ResultSet rs = null;
			try {
				String query = "select o.SNAME,o.SFID,o.SFNAME,r.SID ROLEID,r.SNAME ROLENAME FROM SA_OPROLE r, SA_OPORG o WHERE  r.SID in ("
						+ addrole + ") and o.SID = '" + orgID + "'";
				conn = session.getConnection();
				stm = conn.createStatement();
				rs = stm.executeQuery(query);
				while (rs.next()) {
					PreparedStatement ps = conn.prepareStatement(sql);
					ps.setString(1, IDUtils.getGUID());
					ps.setString(2, orgID);
					ps.setString(3, rs.getString("SNAME"));
					ps.setString(4, rs.getString("SFID"));
					ps.setString(5, rs.getString("SFNAME"));
					ps.setString(6, rs.getString("ROLEID"));
					ps.setString(7, rs.getString("ROLENAME"));
					ps.setString(8, creatorfID);
					ps.setString(9, creatorFNAME);
					ps.setTimestamp(10, new Timestamp(new Date().getTime()));
					ps.setInt(11, 0);
					ps.executeUpdate();
					DBUtils.CloseConn(null, null, ps, null);
				}
				session.commit(true);
			} catch (Exception e) {
				session.rollback(true);
				throw e;
			} finally {
				DBUtils.CloseConn(session, conn, stm, rs);
			}
		}
		return result;
	}

	public Data getData() {
		return data;
	}

	public void setData(Data data) {
		this.data = data;
	}

	public String getOrgID() {
		return orgID;
	}

	public void setOrgID(String orgID) {
		try {
			this.orgID = URLDecoder.decode(orgID, "UTF-8");
		} catch (Exception e) {

		}
	}

	public String getRoleIDs() {
		return roleIDs;
	}

	public void setRoleIDs(String roleIDs) {
		try {
			this.roleIDs = URLDecoder.decode(roleIDs, "UTF-8");
		} catch (Exception e) {

		}
	}

	public String getCreatorFNAME() {
		return creatorFNAME;
	}

	public void setCreatorFNAME(String creatorFNAME) {
		try {
			this.creatorFNAME = URLDecoder.decode(creatorFNAME, "UTF-8");
		} catch (Exception e) {

		}

	}

	public void setCreatorfID(String creatorfID) {
		try {
			this.creatorfID = URLDecoder.decode(creatorfID, "UTF-8");
		} catch (Exception e) {

		}
	}

	public String getCreatorfID() {
		return creatorfID;
	}

}
