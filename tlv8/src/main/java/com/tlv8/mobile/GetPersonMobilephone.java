package com.tlv8.mobile;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tlv8.base.Data;
import com.tlv8.base.db.DBUtils;
import com.tlv8.common.domain.AjaxResult;
import com.tlv8.base.ActionSupport;

/**
 * 获取手机通讯录
 * 
 * @author 陈乾
 *
 */
@Controller
@Scope("prototype")
public class GetPersonMobilephone extends ActionSupport {
	private Data data = new Data();
	private String mes = "";

	public void setData(Data data) {
		this.data = data;
	}

	public Data getData() {
		return data;
	}

	@ResponseBody
	@RequestMapping("/GetPersonMobilephone")
	public Object execute() throws Exception {
		String sql = "select sname,sid from SA_OPORG where sparent is null order by SSequence";
		try {
			List<Map<String, String>> list = DBUtils.execQueryforList("system", sql);
			for (int i = 0; i < list.size(); i++) {
				Map<String, String> map = list.get(i);
				String children = "";
				mes += ",{\"SID\":\"" + map.get("SID") + "\",\"SNAME\":\"" + map.get("SNAME") + "\",\"CHILD\":["
						+ children + "]}";
			}
			mes = mes.replaceFirst(",", "");
			System.out.println(mes);
			data.setData("[" + mes + "]");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return AjaxResult.success(data);
	}

	public String Childrens(String sparent) {
		String childrens = "";
		try {
			String sql = "select sname,sid,sorgkindid,smobilephone from SA_OPORG_VIEW where sparent='" + sparent
					+ "'  order by SSequence desc";
			List<Map<String, String>> children = DBUtils.execQueryforList("system", sql);
			if (children.size() > 0) {
				for (int i = 0; i < children.size(); i++) {
					Map<String, String> map = children.get(i);
					String children1 = "[" + Childrens(map.get("SID").toString()) + "]";
					childrens += ",{\"SID\":\"" + map.get("SID") + "\",\"SNAME\":\"" + map.get("SNAME")
							+ "\",\"SORGKINDID\":\"" + map.get("SORGKINDID") + "\",\"SMOBILEPHONE\":\""
							+ map.get("SMOBILEPHONE") + "\",\"CHILD\":" + children1 + "}";
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		childrens = childrens.replaceFirst(",", "");
		return childrens;
	}
}
