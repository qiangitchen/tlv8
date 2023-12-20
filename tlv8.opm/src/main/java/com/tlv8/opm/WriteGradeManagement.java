package com.tlv8.opm;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
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
import com.tlv8.common.domain.AjaxResult;
import com.tlv8.base.ActionSupport;
import com.tlv8.system.bean.ContextBean;
import com.tlv8.system.utils.ContextUtils;

/**
 * 分级管理分配权限
 * 
 * @author chenqian
 */
@Controller
@Scope("prototype")
public class WriteGradeManagement extends ActionSupport {
	private String orgid;
	private String orgids;
	private Data data = new Data();

	@ResponseBody
	@RequestMapping("/writeGradeManagement")
	public Object execute() throws Exception {
		ContextBean context = ContextUtils.getContext();
		if (context.getPersonID() == null || "".equals(context.getPersonID())) {
			data.setFlag("false");
			data.setMessage("未登录或登录已超时，不允许操作!");
			return SUCCESS;
		}
		SqlSession session = DBUtils.getSession("system");
		Connection conn = null;
		try {
			String[] selorg = orgids.split(",");
			OPMOrgUtils org = new OPMOrgUtils(orgid);
			String sql = "insert into SA_OPManagement"
					+ "(SID,SORGID,SORGNAME,SORGFID,SORGFNAME,SMANAGEORGID,SMANAGEORGNAME,SMANAGEORGFID,SMANAGEORGFNAME,SMANAGETYPEID,SCREATORFID,SCREATORFNAME,SCREATETIME,VERSION)"
					+ "values(?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
			conn = session.getConnection();
			for (int i = 0; i < selorg.length; i++) {
				String sorgid = selorg[i];
				String query = "select SID from SA_OPManagement where SORGID = '" + orgid + "' and SMANAGEORGID='"
						+ sorgid + "'";
				Statement stm = conn.createStatement();
				ResultSet rs = conn.createStatement().executeQuery(query);
				if (rs.next()) {
					DBUtils.closeConn(null, null, stm, rs);
					continue;
				}
				DBUtils.closeConn(null, null, stm, rs);
				OPMOrgUtils morg = new OPMOrgUtils(sorgid);
				PreparedStatement ps = conn.prepareStatement(sql);
				ps.setString(1, IDUtils.getGUID());
				ps.setString(2, org.getOrgid());
				ps.setString(3, org.getOgnname());
				ps.setString(4, org.getOrgfid());
				ps.setString(5, org.getOrgfname());
				ps.setString(6, sorgid);
				ps.setString(7, morg.getOgnname());
				ps.setString(8, morg.getOrgfid());
				ps.setString(9, morg.getOrgfname());
				ps.setString(10, "systemManagement");
				ps.setString(11, context.getCurrentPersonFullID());
				ps.setString(12, context.getCurrentPersonFullName());
				ps.setTimestamp(13, new Timestamp(new Date().getTime()));
				ps.setInt(14, 0);
				ps.executeUpdate();
				DBUtils.closeConn(null, null, ps, null);
			}
			session.commit(true);
			data.setFlag("true");
		} catch (Exception e) {
			session.rollback(true);
			data.setFlag("false");
			data.setMessage(e.getMessage());
			e.printStackTrace();
		} finally {
			DBUtils.closeConn(session, conn, null, null);
		}
		return AjaxResult.success(data);
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

	public void setData(Data data) {
		this.data = data;
	}

	public Data getData() {
		return data;
	}

	public String getOrgids() {
		return orgids;
	}

	public void setOrgids(String orgids) {
		try {
			this.orgids = URLDecoder.decode(orgids, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
	}

}
