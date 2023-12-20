package com.tlv8.opm;

import java.net.URLDecoder;
import java.sql.Connection;
import java.sql.PreparedStatement;

import org.apache.ibatis.session.SqlSession;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tlv8.base.ActionSupport;
import com.tlv8.base.Data;
import com.tlv8.base.db.DBUtils;
import com.tlv8.common.domain.AjaxResult;

/**
 * 启用组织
 * 
 * @author chenqian
 *
 */
@Controller
@Scope("prototype")
public class EnableOrganization extends ActionSupport {
	private String rowid;
	private Data data;

	public void setData(Data data) {
		this.data = data;
	}

	public Data getData() {
		return data;
	}

	@ResponseBody
	@RequestMapping(value = "/enableOrganization", produces = "application/json;charset=UTF-8", method = RequestMethod.POST)
	public Object execute() throws Exception {
		data = new Data();
		String r = "";
		String m = "success";
		String f = "false";
		String sql = "update SA_OPOrg  set SVALIDSTATE='1' where SID = ? or SPARENT = ? or SFID like ?";
		SqlSession session = DBUtils.getSession("system");
		Connection conn = null;
		PreparedStatement ps = null;
		try {
			if (rowid != null && !"".equals(rowid) && !"%".equals(rowid)) {
				conn = session.getConnection();
				ps = conn.prepareStatement(sql);
				ps.setString(1, rowid);
				ps.setString(2, rowid);
				ps.setString(3, "%" + rowid + "%");
				ps.executeUpdate();
				session.commit(true);
			}
			f = "true";
		} catch (Exception e) {
			m = "操作失败：" + e.getMessage();
			f = "false";
			session.rollback(true);
			e.printStackTrace();
		} finally {
			DBUtils.closeConn(session, conn, ps, null);
		}
		data.setData(r);
		data.setFlag(f);
		data.setMessage(m);
		return AjaxResult.success(data);
	}

	public void setRowid(String rowid) {
		try {
			this.rowid = URLDecoder.decode(rowid, "UTF-8");
		} catch (Exception e) {
			this.rowid = rowid;
		}
	}

	public String getRowid() {
		return rowid;
	}
}
