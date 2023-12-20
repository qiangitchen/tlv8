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
import com.tlv8.common.domain.AjaxResult;
import com.alibaba.fastjson.JSON;
import com.tlv8.base.ActionSupport;

/**
 * 列表数据
 * 
 * @author 陈乾
 *
 */
@Controller
@Scope("prototype")
@SuppressWarnings("rawtypes")
public class MobileListDataAction extends ActionSupport {
	private String dbkey;
	private String tableName;
	private String idcolumn;
	private String queryColomn;
	private String staticfilter;
	private String orderby;
	private int pagelimit = -1;
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
	@RequestMapping("/mobileListDataAction")
	public Object execute() throws Exception {
		String sql = "select " + idcolumn + "," + queryColomn + ",VERSION from " + tableName + " where 1=1 ";
		if (staticfilter != null && !"".equals(staticfilter)) {
			sql += " and (" + staticfilter + ") ";
		}
		if (filter != null && !"".equals(filter)) {
			sql += " and (" + filter + ") ";
		}
		if (orderby != null && !"".equals(orderby)) {
			sql += " order by " + orderby;
		}
		String limit = String.valueOf(pagelimit * currentpage);
		int offerset = pagelimit * (currentpage - 1);
		try {
			List cl = DBUtils.execQueryforList(dbkey, "select count(1) as COUNT from (" + sql + ") a");
			if (cl.size() > 0) {
				Map m = (Map) cl.get(0);
				setCount(String.valueOf(m.get("COUNT")));
			}
			if (DBUtils.IsOracleDB(dbkey) || DBUtils.IsDMDB(dbkey)) {
				if (limit != null && !"".equals(limit) && pagelimit != -1) {
					sql = "select a.* from (select rownum srownu,r.* from (" + sql + ")r where rownum<=" + limit
							+ ") a where a.srownu >" + offerset;
				}
			} else if (DBUtils.IsMySQLDB(dbkey)) {
				if (offerset > 0 && pagelimit != -1) {
					sql = "select a.* from  (" + sql + ")a limit " + offerset + "," + pagelimit + " ";
				}
			} else {
				if (pagelimit != -1) {
					sql = "select * from (" + sql + ") where ID in (select top " + limit + " ID from (" + sql
							+ ") ) and ID not in (select top " + offerset + " ID from (" + sql + ") )";
				}
			}
			// System.out.println(sql);
			List list = DBUtils.execQueryforList(dbkey, sql);
			data.setData(JSON.toJSONString(list));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return AjaxResult.success(data);
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

	public String getTableName() {
		return tableName;
	}

	public void setTableName(String tableName) {
		try {
			this.tableName = URLDecoder.decode(tableName, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
	}

	public String getIdcolumn() {
		return idcolumn;
	}

	public void setIdcolumn(String idcolumn) {
		try {
			this.idcolumn = URLDecoder.decode(idcolumn, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
	}

	public String getQueryColomn() {
		return queryColomn;
	}

	public void setQueryColomn(String queryColomn) {
		try {
			this.queryColomn = URLDecoder.decode(queryColomn, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
	}

	public String getStaticfilter() {
		return staticfilter;
	}

	public void setStaticfilter(String staticfilter) {
		try {
			this.staticfilter = URLDecoder.decode(staticfilter, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
	}

	public String getOrderby() {
		return orderby;
	}

	public void setOrderby(String orderby) {
		try {
			this.orderby = URLDecoder.decode(orderby, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
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
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
	}

	public String getCount() {
		return count;
	}

	public void setCount(String count) {
		this.count = count;
	}

}
