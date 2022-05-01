package com.tlv8.doc.clt.doc;

import java.net.URLDecoder;
import java.util.List;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tlv8.base.Data;
import com.alibaba.fastjson.JSON;
import com.tlv8.base.ActionSupport;

@Controller
@Scope("prototype")
@SuppressWarnings("rawtypes")
public class QueryLinkDirAction extends ActionSupport {
	private String rootPath;
	private String subPath;
	private String billID;
	private String process;
	private String activity;
	private String isTree;
	private Data data = new Data();

	public void setData(Data data) {
		this.data = data;
	}

	public Data getData() {
		return data;
	}

	@ResponseBody
	@RequestMapping("/QueryLinkDirAction")
	@Override
	public Object execute() throws Exception {
		data = new Data();
		try {
			List list = DocAdapter.queryLinkDirAdapter(rootPath, subPath, billID, process, activity,
					Boolean.valueOf(isTree));
			data.setData(JSON.toJSONString(list));
			data.setFlag("true");
		} catch (Exception e) {
			data.setFlag("false");
			data.setMessage(e.toString());
		}
		return data;

	}

	public String getRootPath() {
		return rootPath;
	}

	public void setRootPath(String rootPath) {
		try {
			this.rootPath = URLDecoder.decode(rootPath, "UTF-8");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public String getSubPath() {
		return subPath;
	}

	public void setSubPath(String subPath) {
		try {
			this.subPath = URLDecoder.decode(subPath, "UTF-8");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public String getBillID() {
		return billID;
	}

	public void setBillID(String billID) {
		try {
			this.billID = URLDecoder.decode(billID, "UTF-8");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public String getProcess() {
		return process;
	}

	public void setProcess(String process) {
		try {
			this.process = URLDecoder.decode(process, "UTF-8");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public String getActivity() {
		return activity;
	}

	public void setActivity(String activity) {
		try {
			this.activity = URLDecoder.decode(activity, "UTF-8");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public String getIsTree() {
		return isTree;
	}

	public void setIsTree(String isTree) {
		try {
			this.isTree = URLDecoder.decode(isTree, "UTF-8");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
