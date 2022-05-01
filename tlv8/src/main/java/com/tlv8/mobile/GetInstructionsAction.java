package com.tlv8.mobile;

import java.util.List;
import java.util.Map;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tlv8.base.Data;
import com.tlv8.base.db.DBUtils;
import com.alibaba.fastjson.JSON;
import com.tlv8.base.ActionSupport;
import com.tlv8.system.bean.ContextBean;

/**
 * 批示件查询
 * 
 * @author 陈乾
 *
 */
@Controller
@Scope("prototype")
public class GetInstructionsAction extends ActionSupport {
	private Data data = new Data();
	private String count;
	private String offerset;
	private String limit;

	public void setData(Data data) {
		this.data = data;
	}

	public Data getData() {
		return data;
	}

	@ResponseBody
	@RequestMapping("/getInstructionsAction")
	@SuppressWarnings("rawtypes")
	public Object execute() throws Exception {
		ContextBean context = ContextBean.getContext(request);
		String personID = context.getPersonID();
		String sql = "select distinct s.FID ID,s.fTitle NAME,to_char(s.fDate,'yyyy-MM-dd') TIME,s.FDOCTYPE TYPE,'OA_DC_DocRecvGov' as DOCTYPE,"
				+ "nvl(s.fSourceDept,'领导批示通知') as TITLE from OA_DC_DocRecvGov s "
				+ "join OA_PUB_EXECUTE r on r.fmasterid = s.FID " + "where s.fType = 'ldpsj' and r.fCreatePsnID = '"
				+ personID + "'";
		// if (where != "" && where != null) {
		// sql += "and (" + where + ")";
		// }
		try {
			List cl = DBUtils.execQueryforList("oa", "select count(*) as COUNT from (" + sql + ")");
			if (cl.size() > 0) {
				Map m = (Map) cl.get(0);
				count = String.valueOf(m.get("COUNT"));
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
		return this;
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
}
