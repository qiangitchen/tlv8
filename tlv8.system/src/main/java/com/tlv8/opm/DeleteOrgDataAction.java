package com.tlv8.opm;

import java.net.URLDecoder;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import org.apache.ibatis.session.SqlSession;
import org.apache.log4j.Logger;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tlv8.base.ActionSupport;
import com.tlv8.base.Data;
import com.tlv8.base.db.DBUtils;
import com.tlv8.system.BaseController;

/**
 * 彻底删除组织数据
 * 
 * @author 陈乾
 * 
 * @update 2021-04-01
 */
@Controller
@Scope("prototype")
public class DeleteOrgDataAction extends ActionSupport {
	Logger log = Logger.getLogger(getClass());
	private Data data;
	private String rowid;

	public void setData(Data data) {
		this.data = data;
	}

	public Data getData() {
		return data;
	}

	@ResponseBody
	@RequestMapping(value = "/deleteOrgDataAction", produces = "application/json;charset=UTF-8", method = RequestMethod.POST)
	public Object execute() throws Exception {
		data = new Data();
		String userfname = new BaseController().getContext().getCurrentPersonFullName();
		String[] rowids = rowid.split(",");
		String sqlo = "delete from SA_OPorg where SID = ? and SID != 'ORG01' and SID != 'PSN01@ORG01'";
		String sqlp = "delete from SA_OPPerson where SID != 'PSN01' and SID = ?";
		String qsql = "select SPERSONID from SA_OPorg o where o.SID = ? and o.SORGKINDID = 'psm' and (o.SNODEKIND !='nkLimb' or o.SNODEKIND is null)";
		SqlSession session = DBUtils.getSession("system");
		Connection conn = null;
		try {
			conn = session.getConnection();
			for (int i = 0; i < rowids.length; i++) {
				PreparedStatement ps1 = conn.prepareStatement(qsql);
				ps1.setString(1, rowids[i]);
				ResultSet rs = ps1.executeQuery();
				if (rs.next()) {
					PreparedStatement ps2 = conn.prepareStatement(sqlp);
					ps2.setString(1, rs.getString(1));
					ps2.executeUpdate();
					DBUtils.CloseConn(null, null, ps2, null);
				}
				DBUtils.CloseConn(null, null, ps1, rs);
				PreparedStatement ps3 = conn.prepareStatement(sqlo);
				ps3.setString(1, rowids[i]);
				ps3.executeUpdate();
				DBUtils.CloseConn(null, null, ps3, null);
			}
			session.commit(true);
			data.setFlag("true");
			log.error(userfname + "-删除组织数据：" + rowid + "-成功！");
			log.error(userfname + "-删除人员数据：" + rowid + "-成功！");
		} catch (Exception e) {
			session.rollback(true);
			data.setFlag("false");
			data.setMessage(e.getMessage());
			log.error(userfname + "-删除组织数据：" + rowid + "-失败！");
			log.error(userfname + "-删除人员数据：" + rowid + "-失败！");
			e.printStackTrace();
		} finally {
			DBUtils.CloseConn(session, conn, null, null);
		}
		return this;
	}

	public void setRowid(String rowid) {
		try {
			this.rowid = URLDecoder.decode(rowid, "UTF-8");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public String getRowid() {
		return rowid;
	}
}
