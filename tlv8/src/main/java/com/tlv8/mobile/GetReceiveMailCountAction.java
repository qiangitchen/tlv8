package com.tlv8.mobile;

import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.tlv8.base.Data;
import com.tlv8.base.db.DBUtils;
import com.tlv8.common.domain.AjaxResult;
import com.alibaba.fastjson.JSON;
import com.tlv8.base.ActionSupport;
import com.tlv8.system.bean.ContextBean;

public class GetReceiveMailCountAction extends ActionSupport {
	private Data data = new Data();
	private String count;
	private String filter;

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
				+ "to_char(FSENDTIME,'yyyy-MM-dd HH24:mi') as TIME, " + "o.sfname as ORGNAME "
				+ "from OA_EM_ReceiveEmail  e " + " left join mboa_sys.sa_oporg o on e.FSENDPERID = o.spersonid "
				+ " where e.fConsigneeID = '" + personid + "' " + " and (" + filter + ")"
				+ " order by  FQUREY, FSENDTIME desc";

		try {
			List yl = DBUtils.execQueryforList("oa",
					"select count(*) as COUNT from (" + sql + ") where STATUS = '未查看'");
			if (yl.size() > 0) {
				Map m = (Map) yl.get(0);
				count = String.valueOf(m.get("COUNT"));
			}
			List list = new ArrayList();
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

}
