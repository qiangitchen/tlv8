package com.tlv8.oa.utils;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tlv8.base.Data;
import com.tlv8.base.db.DBUtils;
import com.tlv8.common.domain.AjaxResult;
import com.tlv8.base.ActionSupport;

@Controller
@Scope("prototype")
public class deleteNoticePerson extends ActionSupport {
	private String fid;
	private Data data = new Data();

	public void setFid(String fid) {
		this.fid = fid;
	}

	public String getFid() {
		return fid;
	}

	public void setData(Data data) {
		this.data = data;
	}

	public Data getData() {
		return data;
	}

	@ResponseBody
	@RequestMapping("/deleteNoticePersonAction")
	@Override
	public Object execute() throws Exception {
		try {
			String sql = "DELETE FROM OA_NOTICE_PERSON WHERE FID='" + fid + "'";
			DBUtils.execUpdateQuery("oa", sql);
			data.setFlag("true");
		} catch (Exception e) {
			data.setFlag("false");
		}
		return AjaxResult.success(data);
	}
}
