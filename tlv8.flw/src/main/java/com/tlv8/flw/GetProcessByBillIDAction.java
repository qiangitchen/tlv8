package com.tlv8.flw;

import java.io.UnsupportedEncodingException;
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

/**
 * 
 */
@Controller
@Scope("prototype")
public class GetProcessByBillIDAction extends ActionSupport {
	private String sdata1;
	private Data data = new Data();

	public Data getData() {
		return data;
	}

	public void setData(Data data) {
		this.data = data;
	}

	@ResponseBody
	@RequestMapping("/getProcessByBillIDAction")
	public Object execute() throws Exception {
		try {
			String sql = "";
			if (DBUtils.IsMSSQLDB("system")) {
				sql = "select TOP 1 SID, SFLOWID, SPROCESS, SEURL from SA_TASK where sData1 = '" + sdata1
						+ "' order by SCREATETIME desc";
			} else if (DBUtils.IsMySQLDB("system")) {
				sql = "select SID, SFLOWID, SPROCESS, SEURL from SA_TASK where sData1 = '" + sdata1
						+ "' order by SCREATETIME desc limit 0,1";
			} else {
				sql = "select SID, SFLOWID, SPROCESS, SEURL from "
						+ "(select SID, SFLOWID, SPROCESS, SEURL from SA_TASK where sData1 = '" + sdata1
						+ "' order by SCREATETIME desc) where rownum = 1";
			}
			List<Map<String, String>> list = DBUtils.execQueryforList("system", sql);
			data.setFlag("true");
			data.setData(JSON.toJSONString(list));
		} catch (Exception e) {
			data.setFlag("false");
			data.setMessage(e.getMessage());
		}
		return AjaxResult.success(data);
	}

	public void setSdata1(String sdata1) {
		try {
			this.sdata1 = URLDecoder.decode(sdata1, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
	}

	public String getSdata1() {
		return sdata1;
	}
}
