package com.tlv8.mobile;

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
import com.tlv8.system.bean.ContextBean;

/**
 * 获取表单
 * 
 * @author 陈乾
 *
 */
@Controller
@Scope("prototype")
public class GetTemplataAction extends ActionSupport {
	private Data data = new Data();
	private String count;
	private String orgid;

	public void setData(Data data) {
		this.data = data;
	}

	public Data getData() {
		return data;
	}

	@ResponseBody
	@RequestMapping("/getTemplataAction")
	@SuppressWarnings("rawtypes")
	public Object execute() throws Exception {
		String orgid = ContextBean.getContext(request).getCurrentOgnID();
		String sql = "select * from OA_FormTemplate where fIsRecvDoc='true' and fBelongOrgFID like '%" + orgid + "%'";
		try {
			List cl = DBUtils.execQueryforList("oa", "select count(*) as COUNT from (" + sql + ")");
			if (cl.size() > 0) {
				List sl = DBUtils.execQueryforList("oa", "select count(*) as COUNT from (" + sql + ")");
				Map m = (Map) sl.get(0);
				count = String.valueOf(m.get("COUNT"));
			}
			List list = DBUtils.execQueryforList("oa", sql);
			data.setData(JSON.toJSONString(list));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return AjaxResult.success(data);
	}

	public void setCount(String count) {
		this.count = count;
	}

	public String getCount() {
		return count;
	}

	public void setOrgid(String orgid) {
		this.orgid = orgid;
	}

	public String getOrgid() {
		return orgid;
	}
}
