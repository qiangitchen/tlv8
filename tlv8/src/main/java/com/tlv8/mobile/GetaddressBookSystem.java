package com.tlv8.mobile;

import java.net.URLDecoder;
import java.util.List;

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
 * 系统通讯录
 * 
 * @author 陈乾
 *
 */
@Controller
@Scope("prototype")
@SuppressWarnings({ "rawtypes" })
public class GetaddressBookSystem extends ActionSupport {
	private Data data = new Data();
	private String filter;
	private String offerset;
	private String limit;

	public String getOfferset() {
		return offerset;
	}

	public void setOfferset(String offerset) {
		this.offerset = offerset;
	}

	public void setData(Data data) {
		this.data = data;
	}

	public Data getData() {
		return data;
	}

	@ResponseBody
	@RequestMapping("/getaddressBookSystem")
	public Object execute() throws Exception {
		ContextBean context = ContextBean.getContext(request);
		String ognFID = context.getCurrentOgnFullID();
		if (filter == null) {
			filter = "1=1";
		}
		String sql = "select * from (SELECT P.SID ID,P.SNAME,P.SMOBILEPHONE,O.SFNAME,O.SSEQUENCE FROM SA_OPPERSON P LEFT JOIN SA_OPORG O ON P.SID = O.SPERSONID";
		sql += " where O.sFID like '" + ognFID
				+ "%' and not O.SID in (select sid from SA_OPORG t where t.sfname like '%市长%')";
		sql += " and (" + filter + ")";
		sql += " order by O.SSEQUENCE)";
		if (limit != null && !"".equals(limit)) {
			sql = "select * from (select rownum srownu,r.* from (" + sql + ")r where rownum<=" + limit
					+ ")a where a.srownu >" + offerset;
		} else {
			sql += "where rownum <=10";
		}
		try {
			List list = DBUtils.execQueryforList("system", sql);
			data.setData(JSON.toJSONString(list));
			data.setFlag("true");
		} catch (Exception e) {
			data.setFlag("false");
			data.setMessage(e.getMessage());
		}
		return AjaxResult.success(data);
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

	public void setLimit(String limit) {
		this.limit = limit;
	}

	public String getLimit() {
		return limit;
	}
}
