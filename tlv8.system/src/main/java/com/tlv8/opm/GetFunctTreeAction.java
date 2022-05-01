package com.tlv8.opm;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tlv8.base.Data;
import com.tlv8.base.ActionSupport;

/**
 * @author ChenQain
 * @category 获取功能树信息
 * @C 2011-11-29
 */
@Controller
@Scope("prototype")
public class GetFunctTreeAction extends ActionSupport {
	private Data data = new Data();

	public void setData(Data data) {
		this.data = data;
	}

	public Data getData() {
		return data;
	}

	@ResponseBody
	@RequestMapping("/GetFunctTreeAction")
	public Object execute() throws Exception {
		data = new Data();
		String r = "true";
		String m = "success";
		String f = "";
		try {
			f = "true";
			r = getFuncList();
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

	public static String getFuncList() throws Exception {
		String result = "";
		try {
			result = new FuncTreeController().index("WEB-INF/funtree/");// 功能树的相对目录;
		} catch (Exception e) {
			throw new Exception(e);
		}
		return result;
	}

	public static void main(String[] args) {
		try {
			System.out.println(getFuncList());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
