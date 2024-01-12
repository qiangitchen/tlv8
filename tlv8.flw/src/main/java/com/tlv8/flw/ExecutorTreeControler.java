package com.tlv8.flw;

import java.net.URLDecoder;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tlv8.base.Data;
import com.tlv8.base.db.DBUtils;
import com.tlv8.common.domain.AjaxResult;
import com.alibaba.fastjson.JSON;
import com.tlv8.base.ActionSupport;
import com.tlv8.system.bean.ContextBean;

/**
 * @author ChenQian
 * @date 2012-6-8
 * @see 获取流程执行人机构树
 */
@Controller
@Scope("prototype")
@SuppressWarnings({ "unchecked", "rawtypes" })
public class ExecutorTreeControler extends ActionSupport {
	private Data data = new Data();
	private final String org_table = "SA_OPORG";
	private String filter = null;

	public void setData(Data data) {
		this.data = data;
	}

	public Data getData() {
		return data;
	}

	@ResponseBody
	@PostMapping("/getExecutorTree")
	public Object execute() throws Exception {
		try {
			data.setData(findOrgInfo());
			data.setFlag("true");
		} catch (Exception e) {
			data.setFlag("false");
			data.setMessage(e.toString());
			e.printStackTrace();
		}
		return AjaxResult.success(data);
	}

	public String findOrgInfo() throws SQLException {
		String result = "";
		ContextBean context = ContextBean.getContext(request);
		String ognfid = context.getCurrentOgnFullID();
		String sql = "select distinct a.SPARENT,a.SID,a.SCODE,a.SNAME,a.SFID,a.SORGKINDID,a.sSequence from " + org_table
				+ " a inner join (select SFID from " + org_table + " where SVALIDSTATE=1 ";
		if (!"".equals(filter) && filter != null) {
			sql += " and(" + filter + ")";
		}
		if (DBUtils.IsMSSQLDB("system")) {
			sql += ")b on b.SFID like a.SFID + '%' where SVALIDSTATE=1 order by a.sSequence asc";
		} else {
			sql += ")b on b.SFID like concat(a.SFID,'%') where SVALIDSTATE=1 order by a.sSequence asc";
		}
		List<Map<String, String>> reList = DBUtils.execQueryforList("system", sql);
		List orgLi = new ArrayList();
		for (int i = 0; i < reList.size(); i++) {
			Map<String, String> m = (Map<String, String>) reList.get(i);
			String ssfid = m.get("SFID").replace(ognfid, "");
			if (ssfid.indexOf(".ogn") > 0 || ssfid.indexOf(".org") > 0) {// 忽略子(下属)单位
				continue;
			}
			m.put("id", m.get("SID"));
			m.put("name", m.get("SNAME"));
			m.put("pId", m.get("SPARENT"));
			m.put("type", m.get("SORGKINDID"));
			setICON(m);
			orgLi.add(m);
		}
		if (orgLi.size() > 0) {
			result = JSON.toJSONString(orgLi);
		}
		return result;
	}

	public void setICON(Map inM) {
		String cpath = request.getContextPath();
		if ("psm".equals(inM.get("type"))) {
			inM.put("icon", cpath + "/comon/image/toolbar/org/person.gif");
		} else if ("pos".equals(inM.get("type"))) {
			inM.put("icon", cpath + "/comon/image/toolbar/org/pos.gif");
			inM.put("open", "true");
		} else if ("dept".equals(inM.get("type"))) {
			inM.put("icon", cpath + "/comon/image/toolbar/org/dept.gif");
			inM.put("open", "true");
		} else {
			inM.put("icon", cpath + "/comon/image/toolbar/org/org.gif");
			inM.put("open", "true");
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

}
