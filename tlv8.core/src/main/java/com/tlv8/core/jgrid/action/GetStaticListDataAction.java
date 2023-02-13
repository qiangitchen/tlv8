package com.tlv8.core.jgrid.action;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.tlv8.base.ActionSupport;
import com.tlv8.base.Sys;
import com.tlv8.base.db.DBUtils;
import com.tlv8.system.bean.ContextBean;

/**
 * 
 */
@Controller
@Scope("prototype")
public class GetStaticListDataAction extends ActionSupport {
	private String dbkey;
	private String table;
	private String relations;
	private String filter;
	private String orderby;
	private String jsondata;

	@ResponseBody
	@RequestMapping(value = "/getStaticListDataAction", produces = "application/json;charset=UTF-8")
	@SuppressWarnings("deprecation")
	public Object execute() throws Exception {
		String userid = ContextBean.getContext(request).getCurrentPersonID();
		if (userid == null || "".equals(userid)) {
			Sys.packErrMsg("未登录或登录已超时，不允许操作!");
			return this;
		}
		String sql = "select " + relations + " from " + table;
		if (filter != null && !"".equals(filter)) {
			sql += " where " + filter;
		}
		if (orderby != null && !"".equals(orderby)) {
			sql += " order by " + orderby;
		}
		String[] cells = relations.split(",");
		Connection conn = null;
		Statement stm = null;
		ResultSet rs = null;
		try {
			conn = DBUtils.getAppConn(dbkey);
			stm = conn.createStatement();
			rs = stm.executeQuery(sql);
			JSONArray jsona = new JSONArray();
			while (rs.next()) {
				JSONObject json = new JSONObject();
				for (int i = 0; i < cells.length; i++) {
					int c = i + 1;
					String value = (rs.getString(c) == null) ? "" : rs.getString(c);
					json.put(cells[i], value);
				}
				jsona.add(json);
			}
			jsondata = jsona.toString();
		} catch (SQLException e) {
			throw new SQLException(e + "dblink:" + dbkey + ",sql:" + sql);
		} finally {
			try {
				DBUtils.closeConn(conn, stm, rs);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return this;
	}

	public String getDbkey() {
		return dbkey;
	}

	public void setDbkey(String dbkey) {
		try {
			this.dbkey = URLDecoder.decode(dbkey, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			this.dbkey = dbkey;
		}
	}

	public String getTable() {
		return table;
	}

	public void setTable(String table) {
		try {
			this.table = URLDecoder.decode(table, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			this.table = table;
		}
	}

	public String getRelations() {
		return relations;
	}

	public void setRelations(String relations) {
		try {
			this.relations = URLDecoder.decode(relations, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			this.relations = relations;
		}
	}

	public String getFilter() {
		return filter;
	}

	public void setFilter(String filter) {
		try {
			this.filter = URLDecoder.decode(filter, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			this.filter = filter;
		}
	}

	public String getJsondata() {
		return jsondata;
	}

	public void setJsondata(String jsondata) {
		this.jsondata = jsondata;
	}

	public String getOrderby() {
		return orderby;
	}

	public void setOrderby(String orderby) {
		try {
			this.orderby = URLDecoder.decode(orderby, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			this.orderby = orderby;
		}
	}

}
