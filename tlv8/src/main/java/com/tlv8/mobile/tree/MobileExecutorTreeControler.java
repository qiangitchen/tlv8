package com.tlv8.mobile.tree;

import java.net.URLDecoder;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tlv8.base.db.DBUtils;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.tlv8.base.ActionSupport;
import com.tlv8.system.help.ResponseProcessor;

/**
 * @date 2012-6-8
 * @see 获取流程执行人机构树
 * @author 陈乾
 */
@Controller
@Scope("prototype")
@SuppressWarnings({ "rawtypes", "unchecked" })
public class MobileExecutorTreeControler extends ActionSupport {
	private final String org_table = "SA_OPORG";
	private String sql = null;
	private String filter = null;
	private String pid;

	@ResponseBody
	@RequestMapping("/mobileExecutorTreeControler")
	public void execute1() throws Exception {
		JSONArray res = new JSONArray();
		try {
			res = getOrgInfo();
			ResponseProcessor.renderText(this.response, res.toString());
		} catch (Exception e) {
		}
	}

	public JSONArray getOrgInfo() throws SQLException {
		sql = "select distinct a.SPARENT,a.SID,a.SCODE,a.SNAME,a.SFID,a.SORGKINDID,a.sSequence from " + org_table
				+ " a where 1=1 ";
		if (!"".equals(filter) && filter != null) {
			sql += " and(" + filter + ")";
		}
		sql += " order by a.sSequence asc";
		List<Map<String, String>> reList = DBUtils.execQueryforList("system", sql);
		JSONArray treedata = new JSONArray();
		try {
			for (int i = 0; i < reList.size(); i++) {
				Map<String, String> m = (Map<String, String>) reList.get(i);
				if (m.get("SPARENT") == null || "".equals(m.get("SPARENT")) || "null".equals(m.get("SPARENT"))) {
					m.put("id", m.get("SID"));
					m.put("text", m.get("SNAME"));
					m.put("parentId", m.get("SPARENT"));
					m.put("type", m.get("SORGKINDID"));
					setICON(m);
					JSONObject json = JSON.parseObject(JSON.toJSONString(m));
					getChildern(json, reList, m.get("SID"));
					treedata.add(json);
				} else if (pid != null && pid.equals(m.get("SID"))) {
					m.put("id", m.get("SID"));
					m.put("text", m.get("SNAME"));
					m.put("parentId", "");
					m.put("type", m.get("SORGKINDID"));
					setICON(m);
					JSONObject json = JSON.parseObject(JSON.toJSONString(m));
					getChildern(json, reList, m.get("SID"));
					treedata.add(json);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return treedata;
	}

	private void getChildern(JSONObject json, List<Map<String, String>> reList, String prantid) throws Exception {
		JSONArray treedata = new JSONArray();
		for (int i = 0; i < reList.size(); i++) {
			Map<String, String> m = (Map<String, String>) reList.get(i);
			if (prantid.equals(m.get("SPARENT"))) {
				m.put("id", m.get("SID"));
				m.put("text", m.get("SNAME"));
				m.put("parentId", m.get("SPARENT"));
				m.put("type", m.get("SORGKINDID"));
				setICON(m);
				JSONObject jsonchil = JSON.parseObject(JSON.toJSONString(m));
				getChildern(jsonchil, reList, m.get("SID"));
				treedata.add(jsonchil);
			}
		}
		if (treedata.size() > 0) {
			json.put("children", treedata);
		}
	}

	public void setICON(Map inM) {
		if ("psm".equals(inM.get("type"))) {
			inM.put("iconCls", "icon-org-psm");
		} else if ("pos".equals(inM.get("type")) || "post".equals(inM.get("type"))) {
			inM.put("iconCls", "icon-org-pos");
		} else if ("dpt".equals(inM.get("type")) || "dept".equals(inM.get("type"))) {
			inM.put("iconCls", "icon-org-dpt");
		} else {
			inM.put("iconCls", "icon-org-org");
		}
	}

	public void setFilter(String filter) {
		try {
			this.filter = URLDecoder.decode(filter, "UTF-8");
		} catch (Exception e) {
		}
	}

	public String getFilter() {
		return filter;
	}

	public void setSql(String sql) {
		this.sql = sql;
	}

	public String getSql() {
		return sql;
	}

	public String getPid() {
		return pid;
	}

	public void setPid(String pid) {
		this.pid = pid;
	}
}
