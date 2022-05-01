package com.tlv8.oa.mail;

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

@Controller
@Scope("prototype")
public class LoadMailPortalInfo extends ActionSupport {

	private Data data = new Data();

	@ResponseBody
	@RequestMapping("/loadMailPortalInfo")
	@Override
	public Object execute() throws Exception {
		String sqlStr = "SELECT FID,FQUREY,FEMAILNAME,FSENDPERNAME,FSENDTIME FROM OA_EM_RECEIVEEMAIL T  WHERE T.FCONSIGNEEID='"
				+ ContextBean.getContext(request).getCurrentPersonID() + "' AND FQUREY='未查看' ORDER BY FSENDTIME DESC";
		try {
			List<Map<String, String>> list = DBUtils.execQueryforList("oa", sqlStr);
			data.setData(JSON.toJSONString(list));
			data.setFlag("true");
		} catch (Exception e) {
			data.setFlag("false");
			data.setMessage("加载数据错误!");
			e.printStackTrace();
		}
		return this;
	}

	public Data getData() {
		return data;
	}

	public void setData(Data data) {
		this.data = data;
	}

}
