package com.tlv8.base;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URLDecoder;
import java.net.URLEncoder;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.ResponseEntity.BodyBuilder;
import org.springframework.web.bind.annotation.ModelAttribute;

@Scope("prototype")
public class ActionSupport {
	@Autowired
	protected HttpServletRequest request;
	@Autowired
	protected HttpServletResponse response;

	protected String SUCCESS = "success";

	@ModelAttribute
	public void executeBefor() throws Exception {
		RequestSetObject.setData(request, this);
	}

	public Object execute() throws Exception {
		return this;
	}

	protected String getDecode(String str) {
		try {
			return URLDecoder.decode(str, "UTF-8");
		} catch (Exception e) {
		}
		if (str != null)
			return str;
		return "";
	}

	protected ResponseEntity<byte[]> getByteResponseEntity(InputStream inpbs, MediaType mediaType, String userAgent,
			String filename) {
		ByteArrayOutputStream outs = new ByteArrayOutputStream();
		try {
			int bytesRead;
			while ((bytesRead = inpbs.read()) != -1) {
				outs.write(bytesRead);
			}
			outs.flush();
			outs.close();
			inpbs.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		ResponseEntity<byte[]> re = getByteResponseEntity(outs, mediaType, filename, filename);
		return re;
	}

	protected ResponseEntity<byte[]> getByteResponseEntity(ByteArrayOutputStream outs, MediaType mediaType,
			String userAgent, String filename) {
		// ok表示http请求中状态码200
		BodyBuilder builder = ResponseEntity.ok();
		try {
			// 内容长度
			builder.contentLength(outs.size());
			builder.contentType(mediaType);
			// 使用URLEncoding.decode对文件名进行解码
			filename = URLEncoder.encode(filename, "UTF-8");
			// 根据浏览器类型，决定处理方式
			if (userAgent.indexOf("MSIE") > 0) {
				builder.header("Content-Disposition", "attachment; filename=" + filename);
			} else {
				builder.header("Content-Disposition", "attacher; filename*=UTF-8''" + filename);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		ResponseEntity<byte[]> re = builder.body(outs.toByteArray());
		return re;
	}

	protected ResponseEntity<byte[]> getByteResponseEntity(File file, MediaType mediaType, String userAgent,
			String filename) throws IOException {
		// ok表示http请求中状态码200
		BodyBuilder builder = ResponseEntity.ok();
		try {
			// 内容长度
			builder.contentLength(file.length());
			builder.contentType(mediaType);
			// 使用URLEncoding.decode对文件名进行解码
			filename = URLEncoder.encode(filename, "UTF-8");
			// 根据浏览器类型，决定处理方式
			if (userAgent.indexOf("MSIE") > 0) {
				builder.header("Content-Disposition", "attachment; filename=" + filename);
			} else {
				builder.header("Content-Disposition", "attacher; filename*=UTF-8''" + filename);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		ResponseEntity<byte[]> re = builder.body(FileUtils.readFileToByteArray(file));
		return re;
	}
}
