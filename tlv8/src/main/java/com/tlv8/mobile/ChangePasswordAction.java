package com.tlv8.mobile;

import java.util.List;
import java.util.Map;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tlv8.base.Data;
import com.tlv8.base.db.DBUtils;
import com.tlv8.common.domain.AjaxResult;
import com.tlv8.base.ActionSupport;
import com.tlv8.system.bean.ContextBean;

/**
 * 修改密码
 */
@Controller
@Scope("prototype")
@SuppressWarnings({ "rawtypes" })
public class ChangePasswordAction extends ActionSupport {
	private String opwd;
	private String npwd;
	private Data data = new Data();

	public void setData(Data data) {
		this.data = data;
	}

	public Data getData() {
		return data;
	}

	@ResponseBody
	@RequestMapping("/mChangePasswordAction")
	public Object execute() throws Exception {
		ContextBean context = ContextBean.getContext(request);
		String personid = context.getPersonID();
		String sql = "select SID from SA_OPperson where sPassword = '" + opwd
				+ "' and sID = '" + personid + "'";
		String upSql = "update SA_OPperson p set p.sPassword = '" + npwd
				+ "',sPasswordModifyTime = sysdate where p.sID = '" + personid
				+ "'";
		List list = DBUtils.execQueryforList("system", sql);
		Map m = null;
		try {
			m = (Map) list.get(0);
		} catch (Exception e) {
		}
		if (m == null || m.isEmpty()) {
			data.setFlag("false");
			data.setMessage("密码错误,修改失败!");
		} else {
			try {
				DBUtils.execUpdateQuery("system", upSql);
				data.setFlag("true");
			} catch (Exception e) {
				data.setFlag("false");
				data.setMessage(e.getMessage());
			}
		}
		return AjaxResult.success(data);
	}

	public void setOpwd(String opwd) {
		this.opwd = opwd;
	}

	public String getOpwd() {
		return opwd;
	}

	public void setNpwd(String npwd) {
		this.npwd = npwd;
	}

	public String getNpwd() {
		return npwd;
	}

}
