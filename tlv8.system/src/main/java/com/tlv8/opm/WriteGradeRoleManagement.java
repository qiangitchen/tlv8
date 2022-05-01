package com.tlv8.opm;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.Date;

import org.apache.ibatis.session.SqlSession;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tlv8.base.Data;
import com.tlv8.base.db.DBUtils;
import com.tlv8.base.utils.IDUtils;
import com.tlv8.base.ActionSupport;
import com.tlv8.system.BaseController;
import com.tlv8.system.bean.ContextBean;

/**
 * 分级管理分配角色
 * 
 * @author chenqian
 */
@Controller
@Scope("prototype")
public class WriteGradeRoleManagement extends ActionSupport {
	private String orgid;
	private String roleids;
	private Data data = new Data();

	@ResponseBody
	@RequestMapping("/writeGradeRoleManagement")
	public Object execute() throws Exception {
		ContextBean context = new BaseController().getContext();
		if (context.getPersonID() == null || "".equals(context.getPersonID())) {
			data.setFlag("false");
			data.setMessage("未登录或登录已超时，不允许操作!");
			return SUCCESS;
		}
		SqlSession session = DBUtils.getSession("system");
		Connection conn = null;
		PreparedStatement ps = null;
		try {
			String[] role = roleids.split(",");
			OPMOrgUtils org = new OPMOrgUtils(orgid);
			String sql = "insert into SA_OPRoleManagement"
					+ "(SID,SROLEID,SORGID,SORGNAME,SORGFID,SORGFNAME,SCREATORFID,SCREATORFNAME,SCREATETIME,VERSION)"
					+ "values(?,?,?,?,?,?,?,?,?,?)";
			conn = session.getConnection();
			for (int i = 0; i < role.length; i++) {
				String roleid = role[i];
				String query = "select SID from SA_OPRoleManagement where SORGID = '" + orgid + "' and SROLEID='"
						+ roleid + "'";
				ResultSet rs = conn.createStatement().executeQuery(query);
				if (rs.next()) {
					rs.close();
					continue;
				}
				ps = conn.prepareStatement(sql);
				ps.setString(1, IDUtils.getGUID());
				ps.setString(2, roleid);
				ps.setString(3, org.getOrgid());
				ps.setString(4, org.getOgnname());
				ps.setString(5, org.getOrgfid());
				ps.setString(6, org.getOrgfname());
				ps.setString(7, context.getCurrentPersonFullID());
				ps.setString(8, context.getCurrentPersonFullName());
				ps.setTimestamp(9, new Timestamp(new Date().getTime()));
				ps.setInt(10, 0);
				ps.executeUpdate();
				rs.close();
			}
			session.commit(true);
			data.setFlag("true");
		} catch (Exception e) {
			session.rollback(true);
			data.setFlag("false");
			data.setMessage(e.getMessage());
			e.printStackTrace();
		} finally {
			DBUtils.CloseConn(session, conn, ps, null);
		}
		return this;
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

	public void setRoleids(String roleids) {
		try {
			this.roleids = URLDecoder.decode(roleids, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
	}

	public String getRoleids() {
		return roleids;
	}

	public void setData(Data data) {
		this.data = data;
	}

	public Data getData() {
		return data;
	}

}
