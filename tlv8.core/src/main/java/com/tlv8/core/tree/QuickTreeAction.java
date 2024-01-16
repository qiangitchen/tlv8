package com.tlv8.core.tree;

import java.net.URLDecoder;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.tlv8.base.ActionSupport;
import com.tlv8.base.db.DBUtils;
import com.tlv8.base.utils.AesEncryptUtil;

@Controller
@Scope("prototype")
public class QuickTreeAction extends ActionSupport {
	private String quicktext;
	private String cloums;
	private String quickCells;
	private String jsonResult;

	@ResponseBody
	@PostMapping(value = "/QuickTreeAction", produces = "application/json;charset=UTF-8")
	public Object execute() throws Exception {
		exeQuickAction();
		JSONObject json = new JSONObject();
		json.put("jsonResult", AesEncryptUtil.encrypt(jsonResult));
		return json;
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public String exeQuickAction() {
		String sql = "";
		String[] pamms = this.quicktext.split(",");
		String id = pamms[0].toString();
		String name = pamms[1].toString();
		String tableName = pamms[2].toString();
		String databaseName = pamms[3].toString();
		String parent = pamms[4].toString();
		String text = pamms[5].toString();
		String path = pamms[6].toString();
		String rootFilter = pamms[7].toString();
		String filter = pamms[8].toString();
		String rootpath = "";
		SqlSession session = DBUtils.getSession(databaseName);
		Connection conn = null;
		Statement stm = null;
		ResultSet rs = null;
		try {
			conn = session.getConnection();
			String queryRoot = "select " + path + " from " + tableName + " where " + rootFilter;
			if (filter != null && !"".equals(filter.trim())) {
				queryRoot += " and (" + filter + ")";
			}
			stm = conn.createStatement();
			rs = stm.executeQuery(queryRoot);
			int i = 0;
			while (rs.next()) {
				if (i > 0) {
					rootpath += " or ";
				}
				rootpath += " " + path + " like '" + rs.getString(path) + "%' ";
				i++;
			}
		} catch (Exception e) {
		} finally {
			DBUtils.closeConn(null, null, stm, rs);
		}
		sql = "select " + id + "," + parent + "," + name + "," + this.cloums + " from " + tableName + " where (upper("
				+ name + ") like upper('%" + text + "%') or " + id + " = '" + text + "' ";
		if (quickCells != null && !"".equals(quickCells)) {
			String[] filcell = quickCells.split(",");
			for (int i = 0; i < filcell.length; i++) {
				if (!filcell[i].toUpperCase().equals(id.toUpperCase())) {
					sql += " or upper(" + filcell[i] + ") like upper('%" + text + "%')";
				}
			}
		}
		sql += ")";
		if (!"".equals(rootpath)) {
			sql += " and (" + rootpath + ")";
		}
		if (filter != null && !"".equals(filter.trim())) {
			sql += " and (" + filter + ")";
		}
		try {
			JSONArray jsonArray = new JSONArray();
			stm = conn.createStatement();
			rs = stm.executeQuery(sql);
			ResultSetMetaData rsmd = rs.getMetaData();
			while (rs.next()) {
				Map item = new HashMap();
				item.put("id", rs.getString(id));
				item.put("name", rs.getString(name));
				item.put("parent", rs.getString(parent));
				item.put("fid", rs.getString(path));
				int size = rsmd.getColumnCount();
				for (int i = 1; i <= size; i++) {
					String keytext = rsmd.getColumnLabel(i);
					item.put(keytext, rs.getString(keytext));
				}
				item.put(path, rs.getString(path));
				jsonArray.add(item);
			}
			this.jsonResult = jsonArray.toString();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DBUtils.closeConn(session, conn, stm, rs);
		}
		return this.jsonResult;
	}

	public String getQuicktext() {
		return quicktext;
	}

	public void setQuicktext(String quicktext) {
		try {
			this.quicktext = URLDecoder.decode(quicktext, "UTF-8");
		} catch (Exception e) {
			this.quicktext = quicktext;
		}
	}

	public String getCloums() {
		return cloums;
	}

	public void setCloums(String cloums) {
		try {
			this.cloums = URLDecoder.decode(cloums, "UTF-8");
		} catch (Exception e) {
			this.cloums = cloums;
		}
	}

	public String getQuickCells() {
		return quickCells;
	}

	public void setQuickCells(String quickCells) {
		try {
			this.quickCells = URLDecoder.decode(quickCells, "UTF-8");
		} catch (Exception e) {
			this.quickCells = quickCells;
		}
	}

}