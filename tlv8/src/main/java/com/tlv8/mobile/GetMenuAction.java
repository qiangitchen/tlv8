package com.tlv8.mobile;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tlv8.base.Data;
import com.tlv8.base.ActionSupport;

@Controller
@Scope("prototype")
public class GetMenuAction extends ActionSupport {
	private Data data = new Data();

	public void setData(Data data) {
		this.data = data;
	}

	public Data getData() {
		return data;
	}

	@ResponseBody
	@RequestMapping("/getMenuAction")
	public Object execute() throws Exception {
		String r = "[{id:\"waittask\",ico:\"dbrw.png\",name:\"待办任务\",url:\"/tlv8/mobileUI/SA/task/taskView/waitActivity.html\",action:\"\"},"
				+ "{id:\"waitfile\",ico:\"ccsq.png\",name:\"待阅文件\",url:\"/tlv8/mobileUI/SA/task/taskView/readActivity.html\",action:\"\"},"
				+ "{id:\"waitemail\",ico:\"email.png\",name:\"我的邮件\",url:\"/tlv8/mobileUI/OA/email/process/notReadEmail/mainActivity.html\",action:\"\"},"
				+ "{id:\"readtask\",ico:\"ybrw.png\",name:\"已办任务\",url:\"/tlv8/mobileUI/SA/task/taskView/submitActivity.html\",action:\"\"},"
				+ "{id:\"readfile\",ico:\"default_folder.png\",name:\"已阅文件\",url:\"/tlv8/mobileUI/SA/task/taskView/counterActivity.html\",action:\"\"},"
				+ "{id:\"xttxl\",ico:\"xttxl.png\",name:\"通讯录\",url:\"/tlv8/mobileUI/OA/addressBook/process/addressBookSystem/startActivity.html\",action:\"\"}]";
		data.setData(r);
		return this;
	}

}
