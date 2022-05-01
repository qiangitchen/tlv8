package com.tlv8.system;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.tlv8.base.Data;
import com.tlv8.base.db.DBUtils;
import com.tlv8.system.bean.ContextBean;

/**
 * 获取当前登录人所有角色
 */
@Controller
@Scope("prototype")
@SuppressWarnings({ "rawtypes", "unchecked" })
public class GetAllRolesAction {
	private Data data = new Data();

	public void setData(Data data) {
		this.data = data;
	}

	public Data getData() {
		return data;
	}

	@ResponseBody
	@RequestMapping("/getAllRolesAction")
	public Object execute() throws Exception {
		ContextBean context = new BaseController().getContext();
		String persinFID = context.getCurrentPersonFullID();
		String sql = "select r.SID,r.SCODE,r.SNAME,t.SORGID,t.SORGNAME from "
				+ "sa_opauthorize t left join sa_oprole r on r.sid = t.sauthorizeroleid ";
		if (DBUtils.IsOracleDB("system") || DBUtils.IsDMDB("system")) {
			sql += "where '" + persinFID + "' like t.SORGFID||'%'";
		} else {
			sql += "where '" + persinFID + "' like CONCAT(t.SORGFID,'%')";
		}
		try {
			List list = DBUtils.execQueryforList("system", sql);
			List<String> rolelist = new ArrayList();
			for (int i = 0; i < list.size(); i++) {
				rolelist.add((String) ((Map) list.get(i)).get("SCODE"));
			}
			data.setData(JSON.toJSONString(rolelist));
			data.setFlag("true");
		} catch (Exception e) {
			data.setFlag("false");
			data.setMessage(e.getMessage());
			e.printStackTrace();
		}
		return data;
	}
}
