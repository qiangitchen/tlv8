package com.tlv8.opm;

import java.util.ArrayList;
import java.util.List;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tlv8.base.Data;
import com.tlv8.base.db.DBUtils;
import com.tlv8.base.ActionSupport;

/**
 * 重置用户密码
 * 
 * @author 陈乾
 */
@Controller
@Scope("prototype")
public class ResetPassword extends ActionSupport {
	private Data data;
	private String rowid;

	public void setRowid(String rowid) {
		this.rowid = rowid;
	}

	public String getRowid() {
		return rowid;
	}

	public void setData(Data data) {
		this.data = data;
	}

	public Data getData() {
		return data;
	}

	@ResponseBody
	@RequestMapping(value = "/ResetPassword", produces = "application/json;charset=UTF-8", method = RequestMethod.POST)
	public Object execute() throws Exception {
		data = new Data();
		String r = "";
		String m = "success";
		String f = "";
		String sql = "update sa_opperson set SPASSWORD = 'E10ADC3949BA59ABBE56E057F20F883E' where sID = ?";
		try {
			List<String> paramli = new ArrayList<String>();
			paramli.add(rowid);
			r = DBUtils.execUpdate("system", sql, paramli);
			f = "true";
		} catch (Exception e) {
			m = "操作失败：" + e.getMessage();
			f = "false";
			e.printStackTrace();
		}
		data.setData(r);
		data.setFlag(f);
		data.setMessage(m);
		return this;
	}
}
