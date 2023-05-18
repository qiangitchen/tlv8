package com.tlv8.doc.clt.doc;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tlv8.base.Data;
import com.tlv8.base.Sys;
import com.tlv8.core.data.BaseDeleteAction;
import com.tlv8.system.utils.ContextUtils;

@Controller
@Scope("prototype")
public class DocCenterDeleteFileAction extends BaseDeleteAction {
	private Data data = new Data();

	public Data getdata() {
		return data;
	}

	public void setdata(Data data) {
		this.data = data;
	}

	@ResponseBody
	@RequestMapping("/docCenterDeleteFileAction")
	public Object execute() throws Exception {
		String userid = ContextUtils.getContext().getCurrentPersonID();
		if ((userid == null) || ("".equals(userid))) {
			this.data.setFlag("timeout");
			Sys.packErrMsg("未登录或登录已超时，不允许操作!");
			return "success";
		}
		try {
			Doc doc = Docs.queryDocById(rowid);
			doc.deleteFile();
		} catch (Exception localException1) {
		}
		String r = "";
		String m = "success";
		String f = "";
		try {
			r = deleteData();
			f = "true";
		} catch (Exception e) {
			m = "操作失败：" + e.toString();
			f = "false";
			e.printStackTrace();
		}
		data.setData(r);
		data.setFlag(f);
		data.setMessage(m);
		return data;
	}
}
