package com.tlv8.core.data.action;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tlv8.base.Data;
import com.tlv8.base.Sys;
import com.tlv8.core.data.BaseSaveAction;
import com.tlv8.system.bean.ContextBean;

/**
 * @author ChenQian
 * @d 用于公共保存动作
 */
@Controller
@Scope("prototype")
public class SaveAction extends BaseSaveAction {

	public SaveAction() {

	}

	public Data getData() {
		return this.data;
	}

	public void setData(Data data) {
		this.data = data;
	}

	@ResponseBody
	@RequestMapping(value = "/saveAction", produces = "application/json;charset=UTF-8", method = RequestMethod.POST)
	public Object execute() throws Exception {
		data = new Data();
		String userid = ContextBean.getContext(request).getCurrentPersonID();
		if (userid == null || "".equals(userid)) {
			data.setFlag("timeout");
			Sys.packErrMsg("未登录或登录已超时，不允许操作!");
			return this;
		}
		String r = "";
		String m = "success";
		String f = "";
		try {
			r = saveData();
			f = "true";
		} catch (Exception e) {
			m = "操作失败：" + e.getMessage();
			f = "false";
			e.printStackTrace();
		}
		data.setData(r);
		data.setFlag(f);
		data.setMessage(m);
		data.setPage(page);
		data.setAllpage(allpage);
		data.setRowid(rowid);
		return this;
	}

}
