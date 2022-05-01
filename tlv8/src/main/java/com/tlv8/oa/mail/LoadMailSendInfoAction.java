package com.tlv8.oa.mail;

import java.util.List;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tlv8.base.Data;
import com.tlv8.base.db.DBUtils;
import com.alibaba.fastjson.JSON;
import com.tlv8.base.ActionSupport;

@Controller
@Scope("prototype")
public class LoadMailSendInfoAction extends ActionSupport {
	private Data data = new Data();
	private String type;
	private String rowid;

	public void setData(Data data) {
		this.data = data;
	}

	public Data getData() {
		return data;
	}

	@ResponseBody
	@RequestMapping("/LoadMailSendInfoAction")
	@SuppressWarnings("rawtypes")
	public Object execute() throws Exception {
		String sql = "select FID,FSENDPERID,FSENDPERCODE,FSENDPERNAME,FEMAILNAME,FTEXT" + " from OA_EM_ReceiveEmail  e "
				+ " where FID = '" + rowid + "'";
		if ("send".equals(type)) {
			sql = "select FEMAILNAME,FCONSIGNEEID,FCONSIGNEECODE,FCONSIGNEE,FTEXT "
					+ " from OA_EM_SendEmail where FID = '" + rowid + "'";
		}
		try {
			List list = DBUtils.execQueryforList("oa", sql);
			data.setData(JSON.toJSONString(list));
			data.setFlag("true");
		} catch (Exception e) {
			data.setFlag("false");
			data.setMessage(e.toString());
			e.printStackTrace();
		}
		return this;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getType() {
		return type;
	}

	public void setRowid(String rowid) {
		this.rowid = rowid;
	}

	public String getRowid() {
		return rowid;
	}

}
