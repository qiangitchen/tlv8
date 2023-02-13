package com.tlv8.core.jgrid.action;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.naming.NamingException;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.tlv8.base.ActionSupport;
import com.tlv8.base.Data;
import com.tlv8.base.Sys;
import com.tlv8.base.db.DBUtils;
import com.tlv8.base.utils.StringArray;
import com.tlv8.system.bean.ContextBean;

/**
 * 
 */
@Controller
@Scope("prototype")
public class GetGridSelectDataAction extends ActionSupport {
	private Data data = new Data();
	private String dbkey = null;
	private String sql = null;

	@ResponseBody
	@RequestMapping(value = "/getGridSelectDataAction", produces = "application/json;charset=UTF-8")
	public Object execute() throws Exception {
		data = new Data();
		String userid = ContextBean.getContext(request).getCurrentPersonID();
		if (userid == null || "".equals(userid)) {
			data.setFlag("timeout");
			Sys.packErrMsg("未登录或登录已超时，不允许操作!");
			return this;
		}
		String r = "true";
		String m = "success";
		String f = "";
		try {
			r = exeQueryAction();
			f = "true";
		} catch (Exception e) {
			m = "操作失败：" + e.getMessage();
			f = "false";
			e.printStackTrace();
		}
		data.setData(r);
		data.setFlag(f);
		data.setMessage(m);
		return this;
	}

	@SuppressWarnings("deprecation")
	private String exeQueryAction() throws SQLException, NamingException {
		String result = "";
		Connection conn = null;
		Statement stm = null;
		ResultSet rs = null;
		ResultSetMetaData rsmd;
		String db = getDbkey();
		String sql = getSql();
		if (DBUtils.IsOracleDB(db)) {
			if (sql.indexOf("limit ") > 0) {
				String le = sql.substring(sql.indexOf("limit ") + 5).trim();
				sql = sql.substring(0, sql.indexOf("limit "));
				if (sql.indexOf(" where ") > 0) {
					try {
						sql = sql.replace(" where ", " where rownum <= " + le.split(",")[1] + " and ");
					} catch (Exception e) {
					}
				}
			}
		} else if (!DBUtils.IsMySQLDB(db)) {
			if (sql.indexOf("limit ") > 0) {
				String le = sql.substring(sql.indexOf("limit ") + 5).trim();
				sql = sql.substring(0, sql.indexOf("limit "));
				if (sql.indexOf(" where ") > 0) {
					try {
						sql = sql.replace("select", "select top " + le.split(",")[1] + " ");
					} catch (Exception e) {

					}
				}
			}
			// SQLServer将concat转为+号连接
			try {
				Pattern pat = Pattern.compile("concat\\((.*?)\\)");
				Matcher mat = pat.matcher(sql);
				while (mat.find()) {
					sql = sql.replace(mat.group(0), mat.group(1).replace(",", "+"));
				}
			} catch (Exception e) {
			}
		}
		List<Map<String, String>> list = new ArrayList<Map<String, String>>();
		try {
			conn = DBUtils.getAppConn(db);
			stm = conn.createStatement();
			rs = stm.executeQuery(sql);
			rsmd = rs.getMetaData();
			int size = rsmd.getColumnCount();
			while (rs.next()) {
				Map<String, String> m = new HashMap<String, String>();
				m.put("value", (rs.getString(1) == null) ? "" : rs.getString(1));
				StringArray arr = new StringArray();
				for (int i = 2; i <= size; i++) {
					String value = (rs.getString(i) == null) ? "" : rs.getString(i);
					arr.push(value);
				}
				if (arr.getLength() < 1) {
					m.put("text", m.get("value"));
				} else {
					m.put("text", arr.join(" "));
				}
				list.add(m);
			}
		} catch (SQLException e) {
			throw new SQLException(e + "dbkey:" + dbkey + ",sql:" + sql);
		} finally {
			try {
				DBUtils.closeConn(conn, stm, rs);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		result = JSON.toJSONString(list);
		return result;
	}

	public Data getData() {
		return data;
	}

	public void setData(Data data) {
		this.data = data;
	}

	public String getDbkey() {
		return dbkey;
	}

	public void setDbkey(String dbkey) {
		try {
			this.dbkey = URLDecoder.decode(dbkey, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
	}

	public String getSql() {
		return sql;
	}

	public void setSql(String sql) {
		try {
			this.sql = URLDecoder.decode(sql, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
	}

}
