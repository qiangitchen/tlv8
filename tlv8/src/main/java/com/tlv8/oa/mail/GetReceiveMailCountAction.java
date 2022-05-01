package com.tlv8.oa.mail;

import java.util.List;
import java.util.Map;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tlv8.base.Data;
import com.tlv8.base.db.DBUtils;
import com.tlv8.base.ActionSupport;
import com.tlv8.system.bean.ContextBean;

@Controller
@Scope("prototype")
public class GetReceiveMailCountAction extends ActionSupport {
	private Data data = new Data();
	private String count;

	public void setData(Data data) {
		this.data = data;
	}

	public Data getData() {
		return data;
	}

	@ResponseBody
	@RequestMapping("/getReceiveMailCountAction")
	@SuppressWarnings({ "rawtypes" })
	public Object execute() throws Exception {
		ContextBean context = ContextBean.getContext(request);
		String personid = context.getPersonID();
		String sql = "SELECT count(1) COUNT FROM OA_EM_RECEIVEEMAIL WHERE FCONSIGNEEID = '"
				+ personid
				+ "' AND FQUREY = '未查看' ORDER BY FQUREY, FSENDTIME DESC";

		try {
			List yl = DBUtils.execQueryforList("oa", sql);
			if (yl.size() > 0) {
				Map m = (Map) yl.get(0);
				count = String.valueOf(m.get("COUNT"));
			}
			data.setFlag("true");
		} catch (Exception e) {
			data.setFlag("false");
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
}
