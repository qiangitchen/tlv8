package com.tlv8.system.action;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.tlv8.base.Data;
import com.tlv8.base.db.DBUtils;
import com.tlv8.system.utils.ContextUtils;

@Controller
@Scope("prototype")
public class GetPortalReadAction {

	private Data data = new Data();

	public void setData(Data data) {
		this.data = data;
	}

	public Data getData() {
		return data;
	}

	@ResponseBody
	@RequestMapping("/getPortalReadAction")
	public Object execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String querySql = "select * from SA_MSNALERT where SSTATE = 0 and SRPERSONID = '"
				+ ContextUtils.getContext().getCurrentPersonID() + "'";
		try {
			List<Map<String, String>> list = DBUtils.execQueryforList("system", querySql);
			data.setData(JSON.toJSONString(list));
			data.setFlag("true");
		} catch (Exception e) {
			data.setFlag("false");
			e.printStackTrace();
		}
		return data;
	}

}
