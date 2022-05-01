package com.tlv8.mobile.tree;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.List;
import java.util.Map;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.tlv8.base.db.DBUtils;
import com.tlv8.base.ActionSupport;
import com.tlv8.system.help.ResponseProcessor;

/**
 * easyUI Tree 人员选择
 * 
 * @author 陈乾
 */
@Controller
@Scope("prototype")
public class PersonSelecteTree extends ActionSupport {
	private String rootFilter;
	private String filter;

	@ResponseBody
	@RequestMapping("/getPersonSelecteTree")
	public void execute1() throws Exception {
		String sql = "select SID,SNAME,SPARENT,SFID,SFNAME,SORGKINDID,SCODE,SFCODE from SA_OPORG_VIEW where 1=1 ";
		if (rootFilter != null && !"".equals(rootFilter)) {
			sql += " and (" + rootFilter + ")";
		} else {
			sql += " and SPARENT is null ";
		}
		if (filter != null && !"".equals(filter)) {
			sql += " and (" + filter + ")";
		}
		sql += " order by SSEQUENCE asc";
		JSONArray res = new JSONArray();
		try {
			List<Map<String, String>> rootlist = DBUtils.execQueryforList("system", sql);
			for (int i = 0; i < rootlist.size(); i++) {
				Map<String, String> map = rootlist.get(i);
				JSONObject json = JSON.parseObject(JSON.toJSONString(map));
				json.put("id", map.get("SID"));
				json.put("text", map.get("SNAME"));
				json.put("iconCls", getIcon(map.get("SORGKINDID")));
				json.put("children", getChilds(map.get("SID")));
				res.add(json);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		try {
			ResponseProcessor.renderText(this.response, res.toString());
		} catch (Exception e) {
		}
	}

	private JSONArray getChilds(String pid) throws Exception {
		JSONArray res = new JSONArray();
		String sql = "select SID,SNAME,SPARENT,SFID,SFNAME,SORGKINDID,SCODE,SFCODE from SA_OPORG_VIEW where SPARENT='"
				+ pid + "' order by SSEQUENCE asc";
		List<Map<String, String>> list = DBUtils.execQueryforList("system", sql);
		for (int i = 0; i < list.size(); i++) {
			Map<String, String> map = list.get(i);
			JSONObject json = JSON.parseObject(JSON.toJSONString(map));
			json.put("id", map.get("SID"));
			json.put("text", map.get("SNAME"));
			json.put("iconCls", getIcon(map.get("SORGKINDID")));
			json.put("children", getChilds(map.get("SID")));
			res.add(json);
		}
		return res;
	}

	private String getIcon(String kind) {
		String icon = "";
		if ("ogn".equals(kind) || "org".equals(kind)) {
			icon = "icon-org-org";
		} else if ("dpt".equals(kind) || "dept".equals(kind)) {
			icon = "icon-org-dpt";
		} else if ("pos".equals(kind) || "post".equals(kind)) {
			icon = "icon-org-pos";
		} else if ("group".equals(kind)) {
			icon = "icon-org-group";
		} else if ("psm".equals(kind)) {
			icon = "icon-org-psm";
		}
		return icon;
	}

	public String getRootFilter() {
		return rootFilter;
	}

	public void setRootFilter(String rootFilter) {
		try {
			this.rootFilter = URLDecoder.decode(rootFilter, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			this.rootFilter = rootFilter;
		}
	}

	public String getFilter() {
		return filter;
	}

	public void setFilter(String filter) {
		try {
			this.filter = URLDecoder.decode(filter, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			this.filter = filter;
		}
	}
}
