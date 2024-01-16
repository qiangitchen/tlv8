package com.tlv8.core.tree;

import java.net.URLDecoder;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.naming.NamingException;

import org.apache.ibatis.jdbc.SQL;
import org.apache.ibatis.session.SqlSession;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.tlv8.base.ActionSupport;
import com.tlv8.base.db.DBUtils;
import com.tlv8.base.utils.AesEncryptUtil;

/**
 * jtree数据加载通用接口
 * 
 * @author 陈乾
 *
 */
@Controller
@Scope("prototype")
public class TreeSelectAction extends ActionSupport {
	private static final Logger logger = LoggerFactory.getLogger(TreeSelectAction.class);
	private String currenid;
	private String quicktext;
	private String params;
	private String orderby;
	private String jsonResult;

	@ResponseBody
	@PostMapping("/TreeSelectAction")
	public Object execute() throws Exception {
		logger.debug("params:" + params);
		if (this.params != null && !"".equals(this.params)) {
			exeCreateTreeAction();
			JSONObject json = new JSONObject();
			json.put("jsonResult",  AesEncryptUtil.encrypt(jsonResult));
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
		SQL sql = new SQL();
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
			if ((!"".equals(columns)) && (columns != null)) {
				sql.SELECT(columns);
			}
			sql.SELECT(id);
			sql.SELECT(name);
			sql.SELECT(parent);
			sql.FROM(table);
			if ((rootFilter != null) && (!"".equals(rootFilter)) && (currenid == null || "".equals(currenid))) {
				sql.WHERE(rootFilter);
			}
			if ((quicktext != null) && (!"".equals(quicktext))) {
				sql.WHERE(name + " like '" + quicktext + "'");
			} else if ((currenid != null) && (!"".equals(currenid))) {
				sql.WHERE(parent + "='" + currenid + "'");
			}

			if (filter != null && !"".equals(filter)) {
				sql.WHERE(filter);
			}

			if ((this.orderby != null) && (!"".equals(this.orderby))) {
				sql.ORDER_BY(orderby);
			}
			SqlSession session = DBUtils.getSession(databaseName);
			Connection conn = null;
			Statement stm = null;
			ResultSet rs = null;
			JSONArray jsonArray = new JSONArray();
			try {
				conn = session.getConnection();
				stm = conn.createStatement();
				rs = stm.executeQuery(sql.toString());
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
						if (isParentById(rs.getString(id), id, table, parent, conn)) {
							item.put("isParent", "true");
//							item.put("icon", createIcon("folder"));
						} else {
							item.put("isParent", "false");
//							item.put("icon", "toolbar/org/iconText.gif");
						}
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
			logger.error(e.toString());
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

	public boolean isParentById(String rowid, String id, String table, String parent, Connection conn) {
		boolean res = false;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			SQL sql = new SQL();
			sql.SELECT(id);
			sql.FROM(table);
			sql.WHERE(parent + "=?");
			ps = conn.prepareStatement(sql.toString());
			ps.setString(1, rowid);
			rs = ps.executeQuery();
			if (rs.next()) {
				res = true;
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				DBUtils.closeConn(null, ps, rs);
			} catch (SQLException e) {
			}
		}
		return res;
	}

	public String getCurrenid() {
		return currenid;
	}

	public void setCurrenid(String currenid) {
		try {
			this.currenid = URLDecoder.decode(currenid, "UTF-8");
		} catch (Exception e) {
			this.currenid = currenid;
		}
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

	public String getParams() {
		return params;
	}

	public void setParams(String params) {
		try {
			this.params = URLDecoder.decode(params, "UTF-8");
		} catch (Exception e) {
			this.params = params;
		}
	}

	public String getOrderby() {
		return orderby;
	}

	public void setOrderby(String orderby) {
		try {
			this.orderby = URLDecoder.decode(orderby, "UTF-8");
		} catch (Exception e) {
			this.orderby = orderby;
		}
	}

}