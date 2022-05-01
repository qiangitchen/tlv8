package com.tlv8.mobile.tree;

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

@Controller
@Scope("prototype")
public class MobileTree extends ActionSupport {
	private Data data = new Data();
	private String dataSource; /* 数据库 */
	private String id;
	private String name;
	private String other;
	private String parentid;
	private String tablename;
	private String conditions; /* 其他条件 */
	private String orderby;
	private String nowId;
	private String rootFilter;

	@ResponseBody
	@RequestMapping("/MobileTreeLoadAction")
	public Object execute() throws Exception {
		String treeSql = "select ";
		treeSql += id + "," + parentid + "," + name;
		if (other != null && !"".equals(other)) {
			treeSql = treeSql + "," + other;
		}
		treeSql += " from " + tablename;
		treeSql += " where ";
		if (nowId != null && !"".equals(nowId)) {
			treeSql += parentid + "='" + nowId + "'";
		} else {
			treeSql += rootFilter;
		}
		if (conditions != null && !"".equals(conditions)) {
			treeSql += " and " + conditions;
		}
		treeSql += " order by " + orderby;
		try {
			List<Map<String, String>> list = DBUtils.execQueryforList(dataSource, treeSql);
			for (Map<String, String> object : list) {
				String sid = (String) object.get(id);
				if (sid.indexOf("@") > 0) {
					sid = sid.replace("@", "_");
				}
				object.put(id, sid);
				String countSql = "select count(*) count from " + tablename + " where " + parentid + " ='" + sid + "'";
				if (conditions != "" || conditions != null) {
					countSql += " and " + conditions;
				}
				List<Map<String, String>> list1 = DBUtils.execQueryforList(dataSource, countSql);
				for (Map<String, String> object2 : list1) {
					object.put("COUNT", object2.get("COUNT"));
				}
			}
			data.setData(JSON.toJSONString(list));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return this;
	}

	public void setData(Data data) {
		this.data = data;
	}

	public Data getData() {
		return data;
	}

	public String getDataSource() {
		return dataSource;
	}

	public void setDataSource(String dataSource) {
		this.dataSource = dataSource;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getConditions() {
		return conditions;
	}

	public void setConditions(String conditions) {
		try {
			this.conditions = URLDecoder.decode(conditions, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
	}

	public String getParentid() {
		return parentid;
	}

	public void setParentid(String parentid) {
		this.parentid = parentid;
	}

	public String getTablename() {
		return tablename;
	}

	public void setTablename(String tablename) {
		this.tablename = tablename;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getOther() {
		return other;
	}

	public void setOther(String other) {
		try {
			this.other = URLDecoder.decode(other, "UTF-8");
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

	public String getNowId() {
		return nowId;
	}

	public void setNowId(String nowId) {
		try {
			this.nowId = URLDecoder.decode(nowId, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
	}

	public void setRootFilter(String rootFilter) {
		try {
			this.rootFilter = URLDecoder.decode(rootFilter, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
	}

	public String getRootFilter() {
		return rootFilter;
	}

}
