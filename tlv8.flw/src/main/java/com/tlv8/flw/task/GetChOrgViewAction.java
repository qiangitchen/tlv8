package com.tlv8.flw.task;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.naming.NamingException;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tlv8.base.db.DBUtils;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.tlv8.base.ActionSupport;
import com.tlv8.system.bean.ContextBean;

@Controller
@Scope("prototype")
@SuppressWarnings({ "unchecked", "rawtypes" })
public class GetChOrgViewAction extends ActionSupport {
	private String jsonResult;
	private String currenid;
	private String quicktext;
	private String params;
	private String orderby;

	@ResponseBody
	@RequestMapping("/getChOrgViewAction")
	public Object execute() throws Exception {
		exeCreateTreeAction();
		return this;
	}

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
				} catch (UnsupportedEncodingException e) {
					e.printStackTrace();
				}
			}
			rootFilter = "EXISTS(select SMANAGEORGFID from SA_OPMANAGEMENT m where SPERSONID = '"
					+ ContextBean.getContext(request).getCurrentPersonID()
					+ "' and m.SMANAGEORGID like sa_oporg_view.SID||'%' " + " and SMANAGETYPEID='systemManagement')";

			if ((!"".equals(columns)) && (columns != null))
				sql = "select " + columns + "," + id + "," + name + "," + parent + " from " + table;
			else
				sql = "select " + id + "," + name + "," + parent + " from " + table;
			if ((rootFilter != null) && (!"".equals(rootFilter))
					&& (getCurrenid() == null || "".equals(getCurrenid()))) {
				sql = sql + " where " + rootFilter;
			}
			if ((getQuicktext() != null) && (!"".equals(getQuicktext())))
				sql = sql + " where " + name + " like '" + getQuicktext() + "'";
			else if ((getCurrenid() != null) && (!"".equals(getCurrenid()))) {
				sql = sql + " where " + parent + " = '" + getCurrenid() + "'";
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

			List rslist = DBUtils.execQueryforList(databaseName, sql);
			JSONArray jsonArray = new JSONArray();
			for (int i = 0; i < rslist.size(); i++) {
				Map rs = (Map) rslist.get(i);
				Map item = new HashMap();
				Iterator ti = rs.keySet().iterator();
				while (ti.hasNext()) {
					String keytext = (String) ti.next();
					item.put("id", rs.get(id));
					item.put("name", rs.get(name));
					item.put("parent", rs.get(parent));

					item.put("isParent", isParent((String) rs.get("SORGKINDID")));
					item.put("icon", createIcon((String) rs.get("SORGKINDID")));
					item.put(keytext, rs.get(keytext));
				}
				jsonArray.add(item);
			}
			this.jsonResult = jsonArray.toString();
		} catch (Exception e) {
			System.out.println(sql);
			e.printStackTrace();
		}

		return this.jsonResult;
	}

	public String createIcon(String kind) {
		if (("ogn".equals(kind)) || ("org".equals(kind)))
			return "toolbar/org/org.gif";
		if ("dept".equals(kind))
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

	public String getQuicktext() {
		return this.quicktext;
	}

	public void setQuicktext(String quicktext) {
		try {
			this.quicktext = URLDecoder.decode(quicktext, "UTF-8");
		} catch (Exception localException) {
		}
	}

	public String getCurrenid() {
		return this.currenid;
	}

	public void setCurrenid(String currenid) {
		this.currenid = currenid;
	}

	public String getParams() {
		return this.params;
	}

	public void setParams(String params) {
		try {
			this.params = URLDecoder.decode(params, "UTF-8");
		} catch (Exception localException) {
		}
	}

	public String getJsonResult() {
		return this.jsonResult;
	}

	public void setJsonResult(String jsonResult) {
		this.jsonResult = jsonResult;
	}

	public void setOrderby(String orderby) {
		try {
			this.orderby = URLDecoder.decode(orderby, "UTF-8");
		} catch (Exception localException) {
		}
	}

	public String getOrderby() {
		return this.orderby;
	}
}