package com.tlv8.core.data;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.sql.SQLException;

import javax.naming.NamingException;

import org.apache.ibatis.session.SqlSession;

import com.tlv8.base.ActionSupport;
import com.tlv8.base.Data;
import com.tlv8.base.db.DBUtils;
import com.tlv8.system.bean.ContextBean;

/**
 * 
 */
public class BaseDeleteAction extends ActionSupport {
	protected Data data = new Data();
	protected String dbkay = "system"; // 默认值system
	protected String table = "";
	protected String cascade = "";// 级联删除配置{表名：外键,表名：外键...}
	protected String rowid;

	public Data getData() {
		return this.data;
	}

	public void setData(Data data) {
		this.data = data;
	}

	public String deleteData() throws SQLException, NamingException, Exception {
		String userid = ContextBean.getContext(request).getCurrentPersonID();
		if (userid == null || "".equals(userid)) {
			throw new Exception("未登录或登录已超时，不允许操作!");
		}
		String result = "";
		if (table == null || "".equals(table)) {
			throw new Exception("操作的表名不能为空！");
		}
		String sql = "delete from " + table + " where fID = '" + getRowid() + "'";
		if ("system".equals(dbkay))
			sql = sql.replace("fID", "sID");
		SqlSession session = DBUtils.getSession(dbkay);
		try {
			DBUtils.excuteDelete(session, sql);
			String billTable = "";
			String BillID = "";
			if (!"".equals(cascade) && cascade != null) {
				String[] cas = cascade.split(",");
				for (int n = 0; n < cas.length; n++) {
					billTable = cas[n].split(":")[0];
					BillID = cas[n].split(":")[1];
					String dsql = "delete from " + billTable + " where " + BillID + " = '" + getRowid() + "'";
					DBUtils.excuteDelete(session, dsql);// 级联删除
				}
			}
			session.commit();
		} catch (Exception e) {
			session.rollback();
			session.close();
			throw new SQLException(e);
		} finally {
			session.close();
		}
		return result;
	}

	public void setRowid(String rowid) {
		try {
			this.rowid = URLDecoder.decode(rowid, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
	}

	public String getRowid() {
		return rowid;
	}

	public void setDbkay(String dbkay) {
		try {
			if (dbkay != null && !"".equals(dbkay))
				this.dbkay = URLDecoder.decode(dbkay, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
	}

	public String getDbkay() {
		return dbkay;
	}

	public void setTable(String table) {
		try {
			this.table = URLDecoder.decode(table, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
	}

	public String getTable() {
		return table;
	}

	public void setCascade(String cascade) {
		try {
			this.cascade = URLDecoder.decode(cascade, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
	}

	public String getCascade() {
		return cascade;
	}

}
