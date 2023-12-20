package com.tlv8.mobile;

import java.net.URLDecoder;
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

/**
 * 选择送审领导
 * 
 * @author 陈乾
 *
 */
@Controller
@Scope("prototype")
@SuppressWarnings({ "rawtypes" })
public class SelectPersonAction extends ActionSupport {
	private Data data = new Data();
	private String count;
	private String offerset;
	private String limit;
	private String filter;
	private String orgid;

	public void setData(Data data) {
		this.data = data;
	}

	public Data getData() {
		return data;
	}

	@ResponseBody
	@RequestMapping("/selectPersonAction")
	public Object execute() throws Exception {
		String sql = "select t.spersonid,t.sid,t.sname,b.sname as deptname from sa_oporg t "
				+ " join (select sid,sname from sa_oporg )b on b.sid =t.sparent " + " where t.sfid like '%" + orgid
				+ "%' and t.SORGKINDID = 'psm'";
		try {
			List cl = DBUtils.execQueryforList("system", "select count(*) as COUNT from (" + sql + ")");
			if (cl.size() > 0) {
				Map m = (Map) cl.get(0);
				count = String.valueOf(m.get("COUNT"));
			}
			List list = DBUtils.execQueryforList("system", sql);
			data.setData(JSON.toJSONString(list));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return AjaxResult.success(data);
	}

	public void setOrgid(String orgid) {
		this.orgid = orgid;
	}

	public String getOrgid() {
		return orgid;
	}

	public void setCount(String count) {
		this.count = count;
	}

	public String getCount() {
		return count;
	}

	public void setOfferset(String offerset) {
		this.offerset = offerset;
	}

	public String getOfferset() {
		return offerset;
	}

	public void setLimit(String limit) {
		this.limit = limit;
	}

	public String getLimit() {
		return limit;
	}

	public void setFilter(String filter) {
		try {
			this.filter = URLDecoder.decode(filter, "UTF-8");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public String getFilter() {
		return filter;
	}
}
