package com.tlv8.doc.clt.doc;

import java.net.URLDecoder;
import java.util.Map;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tlv8.base.Data;
import com.alibaba.fastjson.JSON;
import com.tlv8.base.ActionSupport;

@Controller
@Scope("prototype")
public class QueryDocInfoByIdAction extends ActionSupport {
	private String isHttps;
	private String host;
	private String fileId;
	private String docVersion;
	private Data data = new Data();

	public void setData(Data data) {
		this.data = data;
	}

	public Data getData() {
		return data;
	}

	@ResponseBody
	@RequestMapping("/queryDocInfoByIdAction")
	@Override
	public Object execute() throws Exception {
		data = new Data();
		try {
			Map<String, Object> m = DocAdapter.queryDocInfoById(Boolean.valueOf(isHttps), host, fileId, docVersion);
			data.setData(JSON.toJSONString(m));
			data.setFlag("true");
		} catch (Exception e) {
			data.setFlag("false");
			data.setMessage(e.toString());
			e.printStackTrace();
		}
		return data;

	}

	public void setIsHttps(String isHttps) {
		this.isHttps = isHttps;
	}

	public String getIsHttps() {
		return isHttps;
	}

	public void setHost(String host) {
		try {
			this.host = URLDecoder.decode(host, "UTF-8");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public String getHost() {
		return host;
	}

	public void setFileId(String fileId) {
		this.fileId = fileId;
	}

	public String getFileId() {
		return fileId;
	}

	public void setDocVersion(String docVersion) {
		this.docVersion = docVersion;
	}

	public String getDocVersion() {
		return docVersion;
	}

}
