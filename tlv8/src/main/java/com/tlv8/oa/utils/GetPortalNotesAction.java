package com.tlv8.oa.utils;

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
public class GetPortalNotesAction extends ActionSupport {
	private Data data;

	@ResponseBody
	@RequestMapping("/getPortalNotesAction")
	@Override
	public Object execute() throws Exception {
		data = new Data();
		String sqlStr = "select distinct(t.FID),t.FTITLE,t.fpushdatetime FPUSHDATETIME from oa_notice_person_view t ";
		sqlStr += " where fpushdatetime is not null and (t.ftype = '集体发布')or (t.ftype = '限制发布' and t.fpersonid='"
				+ ContextBean.getContext(request).getCurrentPersonID() + "')";
		sqlStr += " order by fpushdatetime desc";
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
