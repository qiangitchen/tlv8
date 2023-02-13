package com.tlv8.opm;

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
import com.tlv8.base.Data;
import com.tlv8.base.db.DBUtils;

/**
 * 取消人员分配
 * 
 * @author 陈乾
 */
@Controller
@Scope("prototype")
public class DisassignPsmAction extends ActionSupport {
	private String rowid;
	private Data data;

	public void setData(Data data) {
		this.data = data;
	}

	public Data getData() {
		return data;
	}

	@ResponseBody
	@RequestMapping(value = "/disassignPsmAction", produces = "application/json;charset=UTF-8", method = RequestMethod.POST)
	public Object execute() throws Exception {
		data = new Data();
		String r = "";
		String m = "success";
		String f = "false";
		String sql = "select SNODEKIND from sa_oporg where sid = ?";
		String delSql = "delete from sa_oporg where sid = ?";
		SqlSession session = DBUtils.getSession("system");
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			conn = session.getConnection();
			conn.setAutoCommit(true);
			ps = conn.prepareStatement(sql);
			ps.setString(1, rowid);
			rs = ps.executeQuery();
			if (rs.next()) {
				String nodekind = rs.getString("SNODEKIND");
				if ("nkLimb".equals(nodekind)) {
					f = "true";
				} else {
					m = "操作失败：当前人员组织为所属组织不能取消分配!";
				}
			} else {
				m = "操作失败：指定的ID不存在!";
			}
			if ("true".equals(f)) {
				PreparedStatement ps1 = conn.prepareStatement(delSql);
				ps1.setString(1, rowid);
				ps1.executeUpdate();
				ps1.close();
			}
		} catch (Exception e) {
			m = "操作失败：" + e.getMessage();
			f = "false";
			e.printStackTrace();
		} finally {
			DBUtils.closeConn(session, conn, ps, rs);
		}
		data.setData(r);
		data.setFlag(f);
		data.setMessage(m);
		return this;
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
