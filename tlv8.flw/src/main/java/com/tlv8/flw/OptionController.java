package com.tlv8.flw;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.ibatis.jdbc.SQL;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.tlv8.base.Data;
import com.tlv8.base.db.DBUtils;
import com.tlv8.system.bean.ContextBean;

@Controller
public class OptionController {

	/**
	 * 获取当前登录人指定环节意见
	 * 
	 * @param request
	 * @param taskID
	 * @param sdata1
	 * @return
	 */
	@RequestMapping("loadOptionByTaskID")
	@ResponseBody
	public Object loadOptionByTaskID(HttpServletRequest request, String taskID, String sdata1) {
		SQL sql = new SQL().SELECT("FAGREETEXT").FROM("OA_FLOWRECORD");
		sql.WHERE("FTASKID=? and FBILLID=? and FCREATEPERID=?");
		Data data = new Data();
		try {
			List<Object> params = new ArrayList<Object>();
			params.add(taskID);
			params.add(sdata1);
			params.add(ContextBean.getContext(request).getCurrentPersonID());
			List<Map<String, String>> res = DBUtils.selectStringList("oa", sql.toString(), params);
			if (res.size() > 0) {
				data.setData(res.get(0).get("FAGREETEXT"));
			} else {
				data.setData("");
			}
			data.setFlag("true");
		} catch (Exception e) {
			data.setFlag("false");
			e.printStackTrace();
		}
		return data;
	}

	/**
	 * 获取当前登录人的常用意见
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping("loadMyComOption")
	@ResponseBody
	public Object loadMyComOption(HttpServletRequest request) {
		SQL sql = new SQL().SELECT("FCONCLUSIONNAME").FROM("OA_FLOWCONCLUSION");
		sql.WHERE("fcreatorid=?");
		sql.ORDER_BY("forder asc");
		Data data = new Data();
		try {
			List<Object> params = new ArrayList<Object>();
			params.add(ContextBean.getContext(request).getCurrentPersonID());
			List<Map<String, String>> res = DBUtils.selectStringList("oa", sql.toString(), params);
			data.setData(JSON.toJSONString(res));
			data.setFlag("true");
		} catch (Exception e) {
			data.setFlag("false");
			e.printStackTrace();
		}
		return data;
	}
}
