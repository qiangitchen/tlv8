package com.tlv8.core.treegrid;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.tlv8.base.ActionSupport;
import com.tlv8.base.db.DBUtils;
import com.tlv8.base.utils.StringArray;

@Controller
@Scope("prototype")
public class QueryTreegridDataAction extends ActionSupport {
	private String dbkey;
	private String table;
	private String idField;
	private String treeField;
	private String parentField;
	private String columns;
	private String filter;

	@ResponseBody
	@RequestMapping(value = "/queryTreegridDataAction", produces = "application/json;charset=UTF-8")
	public Object execute() throws Exception {
		List<Map<String, String>> rlist = new ArrayList<>();
		StringArray array = new StringArray();
		if (columns != null && !"".equals(columns)) {
			JSONArray jsona = JSONArray.parseArray(columns);
			if ("system".equals(dbkey)) {
				array.push("SID");
			} else {
				array.push("FID");
			}
			for (int i = 0; i < jsona.size(); i++) {
				JSONObject json = JSONObject.parseObject(jsona.get(i).toString());
				array.push(json.getString("field"));
			}
		}
		array.push(parentField);
		String sql = "select " + array.join(",") + " from " + table;
		if (filter != null && !"".equals(filter)) {
			sql += " where " + filter;
		}
		try {
			List<Map<String, String>> list = DBUtils.selectStringList(dbkey, sql);
			for (int i = 0; i < list.size(); i++) {
				Map<String, String> m = list.get(i);
				m.put("_parentId", m.get(parentField));
				rlist.add(m);
			}
		} catch (Exception e) {
			System.out.println(sql);
			e.printStackTrace();
		}
		return rlist;
	}

	public void setDbkey(String dbkey) {
		try {
			this.dbkey = URLDecoder.decode(dbkey, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
	}

	public String getDbkey() {
		return dbkey;
	}

	public void setTable(String table) {
		try {
			this.table = URLDecoder.decode(table, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
	}

	public String getTable() {
		return table;
	}

	public void setIdField(String idField) {
		try {
			this.idField = URLDecoder.decode(idField, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
	}

	public String getIdField() {
		return idField;
	}

	public void setTreeField(String treeField) {
		try {
			this.treeField = URLDecoder.decode(treeField, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
	}

	public String getTreeField() {
		return treeField;
	}

	public void setColumns(String columns) {
		try {
			this.columns = URLDecoder.decode(columns, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
	}

	public String getColumns() {
		return columns;
	}

	public void setParentField(String parentField) {
		try {
			this.parentField = URLDecoder.decode(parentField, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
	}

	public String getParentField() {
		return parentField;
	}

	public void setFilter(String filter) {
		try {
			this.filter = URLDecoder.decode(filter, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
	}

	public String getFilter() {
		return filter;
	}
}
