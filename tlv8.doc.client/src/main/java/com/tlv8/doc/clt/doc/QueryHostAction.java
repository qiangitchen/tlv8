package com.tlv8.doc.clt.doc;

import java.net.URLDecoder;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tlv8.base.Data;
import com.tlv8.base.ActionSupport;

@Controller
@Scope("prototype")
public class QueryHostAction extends ActionSupport {
	private String isHttps;
	private String docPath;
	private String urlPattern;
	private String isFormAction;
	private Data data = new Data();

	public void setData(Data data) {
		this.data = data;
	}

	public Data getData() {
		return data;
	}

	@ResponseBody
	@RequestMapping("/queryHostAction")
	@Override
	public Object execute() throws Exception {
		data = new Data();
		try {
			data.setData(DocAdapter.queryHost(Boolean.valueOf(isHttps),
					docPath, urlPattern, Boolean.valueOf(isFormAction)));
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

	public void setDocPath(String docPath) {
		try {
			this.docPath = URLDecoder.decode(docPath, "UTF-8");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public String getDocPath() {
		return docPath;
	}

	public void setUrlPattern(String urlPattern) {
		try {
			this.urlPattern = URLDecoder.decode(urlPattern, "UTF-8");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public String getUrlPattern() {
		return urlPattern;
	}

	public void setIsFormAction(String isFormAction) {
		this.isFormAction = isFormAction;
	}

	public String getIsFormAction() {
		return isFormAction;
	}

}
