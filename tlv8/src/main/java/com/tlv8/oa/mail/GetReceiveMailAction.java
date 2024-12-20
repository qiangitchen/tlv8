package com.tlv8.oa.mail;

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
import com.tlv8.system.bean.ContextBean;

@Controller
@Scope("prototype")
public class GetReceiveMailAction extends ActionSupport {
	private Data data = new Data();
	private String weichakan;
	private String count;
	private String filter;
	private String offerset;
	private String limit;

	public void setData(Data data) {
		this.data = data;
	}

	public Data getData() {
		return data;
	}

	@ResponseBody
	@RequestMapping("/getReceiveMailAction")
	@SuppressWarnings("rawtypes")
	public Object execute() throws Exception {
		ContextBean context = ContextBean.getContext(request);
		String personid = context.getPersonID();
		if (filter == null) {
			filter = "1=1";
		} else {
			filter = "e.FSENDPERNAME like '%" + filter + "%' or e.FEMAILNAME like '%" + filter + "%' or e.FTEXT like '%"
					+ filter + "%'";
		}
		String sql = "select FID as ID,FQUREY as STATUS," + "FEMAILNAME as TITLE,FSENDPERNAME as NAME,"
				+ "FSENDTIME as TIME, " + "e.FREPLYSTATE, e.fsenddept as ORGNAME "
				+ "from OA_EM_ReceiveEmail  e where e.fConsigneeID = '" + personid + "' " + " and (" + filter + ")"
				+ " order by  FQUREY, FSENDTIME desc";
		try {
			String yd = "";
			List yl = DBUtils.execQueryforList("oa",
					"select count(1) as COUNT from (" + sql + ")a where STATUS = '未查看'");
			if (yl.size() > 0) {
				Map m = (Map) yl.get(0);
				weichakan = String.valueOf(m.get("COUNT"));
				yd = String.valueOf(m.get("COUNT")) + "未读";
			}
			List cl = DBUtils.execQueryforList("oa", "select count(1) as COUNT from (" + sql + ")c");
			if (cl.size() > 0) {
				Map m = (Map) cl.get(0);
				count = yd + "/共" + String.valueOf(m.get("COUNT"));
			}
			if (DBUtils.IsOracleDB("oa") || DBUtils.IsDMDB("oa")) {
				if (limit != null && !"".equals(limit)) {
					sql = "select * from (select rownum srownu,r.* from (" + sql + ")r where rownum<=" + limit
							+ ")a where a.srownu >" + offerset;
				} else {
					sql = "select * from(" + sql + ")a where rownum <=10";
				}
			} else if (DBUtils.IsMySQLDB("oa")) {
				if (limit != null && !"".equals(limit)) {
					sql = "select * from (" + sql + ")r limit " + offerset + "," + limit;
				} else {
					sql = "select * from(" + sql + ")a limit 0,10";
				}
			} else {
				sql = "select * from (" + sql + ") where ID in (select top " + limit + " ID from (" + sql
						+ ") ) and ID not in (select top " + offerset + " ID from (" + sql + ") )";
			}
			List list = DBUtils.execQueryforList("oa", sql);
			data.setFlag("true");
			data.setData(JSON.toJSONString(list));
		} catch (Exception e) {
			data.setFlag("false");
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

	public void setFilter(String filter) {
		try {
			this.filter = URLDecoder.decode(filter, "UTF-8");
		} catch (Exception e) {

		}
	}

	public String getFilter() {
		return filter;
	}

	public void setWeichakan(String weichakan) {
		this.weichakan = weichakan;
	}

	public String getWeichakan() {
		return weichakan;
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

}
