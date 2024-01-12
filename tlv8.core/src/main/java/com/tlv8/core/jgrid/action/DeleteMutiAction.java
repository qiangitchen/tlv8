package com.tlv8.core.jgrid.action;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.sql.SQLException;

import javax.naming.NamingException;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tlv8.base.ActionSupport;
import com.tlv8.base.Data;
import com.tlv8.base.db.DBUtils;
import com.tlv8.common.domain.AjaxResult;
import com.tlv8.system.bean.ContextBean;

/**
 * 多行删除
 * 
 * @author chenqian
 */
@Controller
@Scope("prototype")
public class DeleteMutiAction extends ActionSupport {
	protected Data data = new Data();
	protected String dbkay = "system";// 默认值system
	protected String table;
	protected String rowids;
	protected String cascade = "";// 级联删除配置{表名：外键,表名：外键...}

	public Data getData() {
		return this.data;
	}

	public void setData(Data data) {
		this.data = data;
	}

	@ResponseBody
	@PostMapping(value = "/deleteMutiAction", produces = "application/json;charset=UTF-8")
	@Override
	public Object execute() throws Exception {
		String userid = ContextBean.getContext(request).getCurrentPersonID();
		if (userid == null || "".equals(userid)) {
			throw new Exception("未登录或登录已超时，不允许操作!");
		}
		String r = "";
		String m = "success";
		String f = "";
		try {
			String dRows = "";
			if (rowids != null && !"".equals(rowids)) {
				dRows = "'" + rowids.replace(",", "','") + "'";
			}
			String perKey = ("system".equals(dbkay)) ? "SID" : "FID";
			String dSql = "delete from " + table + " where " + perKey + " in (" + dRows + ")";
			if ("SA_OPORG".equals(table.toUpperCase())) {
				// 机构信息逻辑删除
				dSql = "update SA_OPORG set SVALIDSTATE = -1 where SID in (" + dRows + ")";
			}
			if ("SA_OPPERSON".equals(table.toUpperCase())) {
				// 人员信息逻辑删除
				dSql = "update SA_OPPERSON set SVALIDSTATE = -1 where SID in (" + dRows + ")";
			}
			DBUtils.execdeleteQuery(dbkay, dSql);
			String billTable = "";
			String BillID = "";
			if (!"".equals(cascade) && cascade != null) {
				String[] cas = cascade.split(",");
				for (int n = 0; n < cas.length; n++) {
					billTable = cas[n].split(":")[0];
					BillID = cas[n].split(":")[1];
					String dsql = "delete from " + billTable + " where " + BillID + " in (" + dRows + ")";
					deleteBillData(dsql);// 级联删除
				}
			}
			f = "true";
		} catch (Exception e) {
			m = "操作失败：" + e.getMessage();
			f = "false";
			e.printStackTrace();
		}
		data.setData(r);
		data.setFlag(f);
		data.setMessage(m);
		return AjaxResult.success(data);
	}

	public String deleteBillData(String sql) throws SQLException, NamingException, Exception {
		String result = "";
		try {
			DBUtils.execdeleteQuery(dbkay, sql);
		} catch (SQLException e) {
			throw new SQLException(e);
		}
		return result;
	}

	public String getDbkay() {
		return dbkay;
	}

	public void setDbkay(String dbkay) {
		try {
			if (dbkay != null && !"".equals(dbkay))
				this.dbkay = URLDecoder.decode(dbkay, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
	}

	public String getTable() {
		return table;
	}

	public void setTable(String table) {
		try {
			this.table = URLDecoder.decode(table, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
	}

	public String getRowids() {
		return rowids;
	}

	public void setRowids(String rowids) {
		try {
			this.rowids = URLDecoder.decode(rowids, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
	}

	public String getCascade() {
		return cascade;
	}

	public void setCascade(String cascade) {
		try {
			this.cascade = URLDecoder.decode(cascade, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			this.cascade = cascade;
		}
	}

}
