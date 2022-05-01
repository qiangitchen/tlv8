package com.tlv8.core.data;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;

import com.tlv8.base.Data;
import com.tlv8.base.db.DBUtils;
import com.tlv8.base.utils.AesEncryptUtil;
import com.tlv8.base.ActionSupport;
import com.tlv8.base.CodeUtils;
import com.tlv8.system.bean.ContextBean;

/**
 * 
 */
public class BaseQueryAction extends ActionSupport {
	protected Data data = new Data();
	protected String dbkay = null;
	protected String table = null;
	protected String relation = null;
	protected String gridID = null;
	protected String sql = null;
	protected String where = null;
	protected String orderby = null;

	public Data getData() {
		return this.data;
	}

	public void setData(Data data) {
		this.data = data;
	}

	@SuppressWarnings("rawtypes")
	public String getInfo(String sql) throws Exception {
		if (relation == null || "".equals(relation)) {
			throw new Exception("查询的列不能为空!");
		}
		String userid = ContextBean.getContext(request).getCurrentPersonID();
		if (userid == null || "".equals(userid)) {
			throw new Exception("未登录或登录已超时，不允许操作!");
		}
		StringBuilder strb = new StringBuilder();
		List rs = null;
		String crelation[] = relation.split(",");
		dbkay = ("undefined".equals(dbkay) || "".equals(dbkay) || dbkay == null) ? "system" : dbkay;
		String csql = "";
		String filter = getWhere();
		String param = (filter != null && !("").equals(filter)) ? " and (" + filter + ")" : "";
		if (sql != null && !"".equals(sql)) {
			csql = sql;
		} else {
			StringBuilder str1 = new StringBuilder();
			str1.append("select ");
			str1.append(relation);
			str1.append(" from " + table);
			str1.append(" where 1=1 ");
			str1.append(param);
			csql = str1.toString();
		}
		try {
			rs = DBUtils.execQueryforList(dbkay, csql);
			for (int i = 0; i < rs.size(); i++) {
				HashMap m = (HashMap) rs.get(i);
				for (int j = 0; j < crelation.length; j++) {
					String cel = crelation[j];
					if (DBUtils.IsOracleDB(dbkay)) {
						cel = cel.toUpperCase();
					}else if (DBUtils.IsPostgreSQL(dbkay)) {
						cel = cel.toLowerCase();
					}
					String celValue = String.valueOf(m.get(cel));
					String covalue = (celValue == null || "".equals(celValue.replaceAll("null", ""))) ? "" : celValue;
					covalue = CodeUtils.encodeSpechars(covalue);
					strb.append("<" + crelation[j] + "><![CDATA[" + covalue + "]]></" + crelation[j] + ">");
				}
			}
		} catch (SQLException e) {
			System.err.println("SQL:"+csql);
			throw new SQLException(e.getMessage());
		}
		return strb.toString();
	}

	public void setWhere(String where) {
		try {
			this.where = URLDecoder.decode(where, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		this.where = AesEncryptUtil.desEncrypt(this.where);
	}

	public String getWhere() {
		return where;
	}

	public void setSql(String sql) {
		try {
			this.sql = URLDecoder.decode(sql, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
	}

	public String getSql() {
		return sql;
	}

	public void setDbkay(String dbkay) {
		this.dbkay = dbkay;
	}

	public String getDbkay() {
		return dbkay;
	}

	public void setGridID(String gridID) {
		try {
			this.gridID = URLDecoder.decode(gridID, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
	}

	public String getGridID() {
		return gridID;
	}

	public void setTable(String table) {
		try {
			this.table = URLDecoder.decode(table, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			this.table = table;
		}
	}

	public String getTable() {
		return table;
	}

	public void setRelation(String relation) {
		try {
			this.relation = URLDecoder.decode(relation, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
	}

	public String getRelation() {
		return relation;
	}

	public void setOrderby(String orderby) {
		try {
			this.orderby = URLDecoder.decode(orderby, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
	}

	public String getOrderby() {
		return orderby;
	}

}
