package com.tlv8.opm;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tlv8.base.Data;
import com.tlv8.common.domain.AjaxResult;
import com.tlv8.base.ActionSupport;

/**
 * 获取功能树（不带权限控制，用于流程图设设计）
 * 
 * @author chenqian
 */
@Controller
@Scope("prototype")
public class GetFunctionTreeAction extends ActionSupport {
	private Data data = new Data();

	public void setData(Data data) {
		this.data = data;
	}

	public Data getData() {
		return data;
	}

	@ResponseBody
	@RequestMapping(value = "/getFunctionTreeAction", produces = "application/json;charset=UTF-8", method = RequestMethod.POST)
	public Object execute() throws Exception {
		data = new Data();
		String r = "true";
		String m = "success";
		String f = "";
		try {
			f = "true";
			r = new FuncTreeController().zTree("WEB-INF/funtree/");
		} catch (Exception e) {
			m = "操作失败：" + e.toString();
			f = "false";
			e.printStackTrace();
		}
		data.setData(r);
		data.setFlag(f);
		data.setMessage(m);
		return AjaxResult.success(data);
	}
}
