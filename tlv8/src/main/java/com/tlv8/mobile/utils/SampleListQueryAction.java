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
import com.tlv8.base.utils.StringArray;
import com.tlv8.common.domain.AjaxResult;
import com.alibaba.fastjson.JSON;
import com.tlv8.base.ActionSupport;

/**
 * @author ChenQian
 * @category Mobile简单列表
 */
@Controller
@Scope("prototype")
public class SampleListQueryAction extends ActionSupport {
	private String dbkey;
	private String table;
	private String cells;
	private String id;
	private String name;
	private String where;
	private int count;
	private Data data = new Data();

	public void setData(Data data) {
		this.data = data;
	}

	public Data getData() {
		return data;
	}

	@ResponseBody
	@RequestMapping("/sampleListQueryAction")
	public Object execute() throws Exception {
		StringArray relation = new StringArray();
		relation.push(id);
		relation.push(name);
		if (cells != null && !"".equals(cells)) {
			String[] cols = cells.split(",");
			for (int i = 0; i < cols.length; i++) {
				if (!"".equals(cols[i]) && id.equals(cols[i]) && name.equals(cols[i])) {
					relation.push(cols[i]);
				}
			}
		}
		String sql = "select " + relation.join(",") + " from " + table;
		if (where != null && !"".equals(where)) {
			sql += " where (" + where + ")";
		}
		// System.out.println(sql);
		try {
			List<Map<String, String>> list = DBUtils.execQueryforList(dbkey, sql);
			count = list.size();
			data.setData(JSON.toJSONString(list));
			data.setFlag("true");
		} catch (Exception e) {
			data.setFlag("false");
			data.setMessage(e.getMessage());
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

	public String getTable() {
		return table;
	}

	public void setTable(String table) {
		try {
			this.table = URLDecoder.decode(table, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
	}

	public String getCells() {
		return cells;
	}

	public void setCells(String cells) {
		try {
			this.cells = URLDecoder.decode(cells, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		try {
			this.id = URLDecoder.decode(id, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		try {
			this.name = URLDecoder.decode(name, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
	}

	public void setWhere(String where) {
		try {
			this.where = URLDecoder.decode(where, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
	}

	public String getWhere() {
		return where;
	}

	public void setCount(int count) {
		this.count = count;
	}

	public int getCount() {
		return count;
	}

}
