package com.tlv8.doc.clt.dowload;

import java.io.InputStream;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tlv8.base.ActionSupport;

/**
 * Demonstrates file resource download. Set filePath to the local file resource
 * to download, relative to the application root ("/images/struts.gif").
 * 
 */
@Controller
@Scope("prototype")
public class FileDownloadAction extends ActionSupport {

	private String inputPath;

	public void setInputPath(String value) {
		inputPath = value;
	}

	public InputStream getInputStream() throws Exception {
		return request.getServletContext().getResourceAsStream(inputPath);
	}

	@ResponseBody
	@RequestMapping("/FileDownloadAction")
	public Object execute() throws Exception {
		return this;
	}

}
