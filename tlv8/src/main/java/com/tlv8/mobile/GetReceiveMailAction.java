package com.tlv8.mobile;

import java.net.URLDecoder;
import java.util.List;
import java.util.Map;

import com.tlv8.base.Data;
import com.tlv8.base.db.DBUtils;
import com.tlv8.common.domain.AjaxResult;
import com.alibaba.fastjson.JSON;
import com.tlv8.base.ActionSupport;
import com.tlv8.system.bean.ContextBean;

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
				+ "to_char(FSENDTIME,'yyyy-MM-dd HH24:mi') as TIME, " + "e.FREPLYSTATE, o.sfname as ORGNAME "
				+ "from OA_EM_ReceiveEmail  e " + " left join mboa_sys.sa_oporg o on e.FSENDPERID = o.spersonid "
				+ " where e.fConsigneeID = '" + personid + "' " + " and (" + filter + ")"
				+ " order by  FQUREY, FSENDTIME desc";

		try {
			String yd = "";
			List yl = DBUtils.execQueryforList("oa",
					"select count(*) as COUNT from (" + sql + ") where STATUS = '未查看'");
			if (yl.size() > 0) {
				Map m = (Map) yl.get(0);
				weichakan = String.valueOf(m.get("COUNT"));
				yd = String.valueOf(m.get("COUNT")) + "未读";
			}
			List cl = DBUtils.execQueryforList("oa", "select count(*) as COUNT from (" + sql + ")");
			if (cl.size() > 0) {
				Map m = (Map) cl.get(0);
				count = yd + "/共" + String.valueOf(m.get("COUNT"));
			}
			if (limit != null && !"".equals(limit)) {
				sql = "select * from (select rownum srownu,r.* from (" + sql + ")r where rownum<=" + limit
						+ ")a where a.srownu >" + offerset;
			} else {
				sql = "select * from(" + sql + ") where rownum <=10";
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
