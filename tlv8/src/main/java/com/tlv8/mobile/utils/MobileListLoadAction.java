package com.tlv8.mobile.utils;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.List;
import java.util.Map;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tlv8.base.Data;
import com.tlv8.base.db.DBUtils;
import com.alibaba.fastjson.JSON;
import com.tlv8.base.ActionSupport;

/**
 * 列表
 * 
 * @author 陈乾
 *
 */
@Controller
@Scope("prototype")
public class MobileListLoadAction extends ActionSupport {
	private String databaseName;
	private String tableName;
	private String idcolumn;
	private String title;
	private String centexts;
	private String ellips;
	private String staticfilter;
	private String orderby;
	private int pagelimit = 10;
	private int currentpage = 1;
	private String filter;

	private String count;
	private Data data = new Data();

	public void setData(Data data) {
		this.data = data;
	}

	public Data getData() {
		return data;
	}

	@ResponseBody
	@RequestMapping("/mobileListLoadAction")
	@SuppressWarnings("rawtypes")
	public Object execute() throws Exception {
		String sql = "select " + idcolumn + "," + title + "," + centexts + "," + ellips + " from " + tableName
				+ " where 1=1 ";
		if (staticfilter != null && !"".equals(staticfilter)) {
			sql += " and (" + staticfilter + ")";
		}
		if (filter != null && !"".equals(filter)) {
			sql += " and (" + filter + ")";
		}
		if (orderby != null && !"".equals(orderby)) {
			sql += " order by " + orderby;
		}
		String limit = String.valueOf(pagelimit * currentpage);
		int offerset = pagelimit * (currentpage - 1);
		try {
			List cl = DBUtils.execQueryforList(databaseName, "select count(1) as COUNT from (" + sql + ") a");
			if (cl.size() > 0) {
				Map m = (Map) cl.get(0);
				setCount(String.valueOf(m.get("COUNT")));
			}
			if (DBUtils.IsOracleDB(databaseName) || DBUtils.IsDMDB(databaseName)) {
				if (limit != null && !"".equals(limit)) {
					sql = "select a.* from (select rownum srownu,r.* from (" + sql + ")r where rownum<=" + limit
							+ ") a where a.srownu >" + offerset;
				} else {
					sql = "select a.* from(" + sql + ") a where rownum <=10 ";
				}
			} else if (DBUtils.IsMySQLDB(databaseName)) {
				if (offerset > 0) {
					sql = "select a.* from  (" + sql + ")a limit " + offerset + "," + pagelimit + " ";
				} else {
					sql = "select a.* from(" + sql + ")a limit 0," + pagelimit;
				}
			} else {
				sql = "select * from (" + sql + ") where ID in (select top " + limit + " ID from (" + sql
						+ ") ) and ID not in (select top " + offerset + " ID from (" + sql + ") )";
			}
			// System.out.println(sql);
			List list = DBUtils.execQueryforList(databaseName, sql);
			data.setData(JSON.toJSONString(list));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return this;
	}

	public String getDatabaseName() {
		return databaseName;
	}

	public void setDatabaseName(String databaseName) {
		this.databaseName = databaseName;
	}

	public String getTableName() {
		return tableName;
	}

	public void setTableName(String tableName) {
		this.tableName = tableName;
	}

	public String getIdcolumn() {
		return idcolumn;
	}

	public void setIdcolumn(String idcolumn) {
		this.idcolumn = idcolumn;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getEllips() {
		return ellips;
	}

	public void setEllips(String ellips) {
		this.ellips = ellips;
	}

	public int getPagelimit() {
		return pagelimit;
	}

	public void setPagelimit(int pagelimit) {
		this.pagelimit = pagelimit;
	}

	public int getCurrentpage() {
		return currentpage;
	}

	public void setCurrentpage(int currentpage) {
		this.currentpage = currentpage;
	}

	public String getFilter() {
		return filter;
	}

	public void setFilter(String filter) {
		try {
			this.filter = URLDecoder.decode(filter, "UTF-8");
		} catch (Exception e) {
			this.filter = filter;
		}
	}

	public String getCentexts() {
		return centexts;
	}

	public void setCentexts(String centexts) {
		this.centexts = centexts;
	}

	public String getCount() {
		return count;
	}

	public void setCount(String count) {
		this.count = count;
	}

	public String getStaticfilter() {
		return staticfilter;
	}

	public void setStaticfilter(String staticfilter) {
		try {
			this.staticfilter = URLDecoder.decode(staticfilter, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			this.staticfilter = staticfilter;
		}
	}

	public String getOrderby() {
		return orderby;
	}

	public void setOrderby(String orderby) {
		this.orderby = orderby;
	}

}
