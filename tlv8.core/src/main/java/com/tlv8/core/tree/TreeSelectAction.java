package com.tlv8.core.tree;

import java.net.URLDecoder;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import javax.naming.NamingException;

import org.apache.ibatis.session.SqlSession;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.tlv8.base.ActionSupport;
import com.tlv8.base.db.DBUtils;

@Controller
@Scope("prototype")
public class TreeSelectAction extends ActionSupport {
	private String currenid;
	private String quicktext;
	private String params;
	private String orderby;
	private String jsonResult;

	@ResponseBody
	@RequestMapping("/TreeSelectAction")
	public Object execute(String currenid, String quicktext, String params, String orderby) throws Exception {
		this.currenid = getDecode(currenid);
		this.quicktext = getDecode(quicktext);
		this.params = getDecode(params);
		this.orderby = getDecode(orderby);
		if (this.params != null && !"".equals(this.params)) {
			exeCreateTreeAction();
			JSONObject json = new JSONObject();
			json.put("jsonResult", jsonResult);
			return json;
		} else {
			return "[]";
		}
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public String exeCreateTreeAction() throws SQLException, NamingException {
		String id = null;
		String name = null;
		String parent = null;
		String table = null;
		String databaseName = null;
		String columns = null;
		String rootFilter = "";
		String filter = "";
		String sql = "";
		try {
			JSONObject obj = JSON.parseObject(this.params);
			Set<String> keyiter = obj.keySet();
			Map map = new HashMap();
			for (String key : keyiter) {
				Object value = obj.get(key);
				map.put(key, value);
			}
			Iterator it = map.keySet().iterator();
			while (it.hasNext()) {
				String key1 = (String) it.next();
				if ("databaseName".equals(key1))
					databaseName = (String) map.get(key1);
				if ("tableName".equals(key1))
					table = (String) map.get(key1);
				if ("id".equals(key1))
					id = (String) map.get(key1);
				if ("name".equals(key1))
					name = (String) map.get(key1);
				if ("parent".equals(key1))
					parent = (String) map.get(key1);
				if ("other".equals(key1))
					columns = (String) map.get(key1);
				if ("rootFilter".equals(key1))
					rootFilter = (String) map.get(key1);
				if ("filter".equals(key1))
					filter = (String) map.get(key1);
				try {
					rootFilter = URLDecoder.decode(rootFilter, "UTF-8");
				} catch (Exception localException) {
				}
				try {
					filter = URLDecoder.decode(filter, "UTF-8");
				} catch (Exception e) {
				}
			}
			if ((!"".equals(columns)) && (columns != null))
				sql = "select " + columns + "," + id + "," + name + "," + parent + " from " + table;
			else
				sql = "select " + id + "," + name + "," + parent + " from " + table;
			if ((rootFilter != null) && (!"".equals(rootFilter)) && (currenid == null || "".equals(currenid))) {
				sql = sql + " where " + rootFilter;
			}
			if ((quicktext != null) && (!"".equals(quicktext)))
				sql = sql + " where " + name + " like '" + quicktext + "'";
			else if ((currenid != null) && (!"".equals(currenid))) {
				sql = sql + " where " + parent + " = '" + currenid + "'";
			}

			if (filter != null && !"".equals(filter)) {
				if (sql.indexOf(" where ") > 0) {
					sql += " and (" + filter + ")";
				} else {
					sql += " where (" + filter + ")";
				}
			}

			if ((this.orderby != null) && (!"".equals(this.orderby))) {
				sql = sql + " order by " + this.orderby;
			}

			try {
				sql = URLDecoder.decode(sql, "UTF-8");
			} catch (Exception e) {
			}

			SqlSession session = DBUtils.getSession(databaseName);
			Connection conn = null;
			Statement stm = null;
			ResultSet rs = null;
			JSONArray jsonArray = new JSONArray();
			try {
				conn = session.getConnection();
				stm = conn.createStatement();
				rs = stm.executeQuery(sql);
				while (rs.next()) {
					Map item = new HashMap();
					item.put("id", rs.getString(id));
					item.put("name", rs.getString(name));
					item.put("parent", rs.getString(parent));
					item.put(id, rs.getString(id));
					item.put(name, rs.getString(name));
					item.put(parent, rs.getString(parent));
					String[] columnsa = columns.split(",");
					for (int i = 0; i < columnsa.length; i++) {
						String keytext = columnsa[i];
						item.put(keytext, rs.getString(keytext));
					}
					try {
						item.put("isParent", isParent(rs.getString("SORGKINDID")));
						item.put("icon", createIcon(rs.getString("SORGKINDID")));
					} catch (Exception e) {
						item.put("isParent", isParent("folder"));
						item.put("icon", createIcon("folder"));
					}
					jsonArray.add(item);
				}
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				DBUtils.closeConn(session, conn, stm, rs);
			}
			this.jsonResult = jsonArray.toString();
		} catch (Exception e) {
			System.out.println(this.params);
			e.printStackTrace();
		}

		return this.jsonResult;
	}

	public String createIcon(String kind) {
		if (("ogn".equals(kind)) || ("org".equals(kind)))
			return "toolbar/org/org.gif";
		if ("dept".equals(kind) || "dpt".equals(kind))
			return "toolbar/org/dept.gif";
		if ("pos".equals(kind))
			return "toolbar/org/pos.gif";
		if ("psm".equals(kind)) {
			return "toolbar/org/person.gif";
		}
		return "toolbar/org/folder-open.gif";
	}

	public static String isParent(String kind) {
		if ("psm".equals(kind)) {
			return "false";
		}
		return "true";
	}

}