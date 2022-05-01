package com.tlv8.doc.svr.controller.connector;

import java.net.URLDecoder;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import com.tlv8.doc.svr.core.utils.FileExtArray;

@SuppressWarnings({ "rawtypes", "unchecked" })
public class RequestHandlerSupport {
	/*
	 * 
	 */
	public static Map parseMultipartRequest(
			HttpServletRequest paramHttpServletRequest,
			HttpServletResponse paramHttpServletResponse) throws Exception {
		Map rmap = new HashMap();
		paramHttpServletRequest.setCharacterEncoding("utf-8"); // 设置编码
		DiskFileItemFactory factory = new DiskFileItemFactory();
		ServletFileUpload upload = new ServletFileUpload(factory);
		try {
			// 可以上传多个文件
			List<FileItem> list = (List<FileItem>) upload
					.parseRequest(paramHttpServletRequest);
			for (FileItem item : list) {
				// 获取表单的属性名字
				String name = item.getFieldName();
				// 如果获取的 表单信息是普通的 文本 信息
				if (item.isFormField()) {
					// 获取表单字段的值
					String value = item.getString();
					try {
						value = URLDecoder.decode(value, "UTF-8");
					} catch (Exception e) {
					}
					rmap.put(name, value);
				} else {
					UploadItem uploaditem = new UploadItem();
					uploaditem.setName(item.getName());
					uploaditem.setInputStream(item.getInputStream());
					// 获取路径名
					String value = item.getName();
					// 索引到最后一个反斜杠
					int start = value.lastIndexOf("\\");
					// 截取 上传文件的 字符串名字，加1是 去掉反斜杠，
					String filename = value.substring(start + 1);
					try {
						filename = URLDecoder.decode(filename, "UTF-8");
					} catch (Exception e) {
					}
					uploaditem.setFileName(filename);
					uploaditem.setExtName(FileExtArray.getExtName(filename));
					String contentType = item.getContentType();
					if (contentType.indexOf(";") > 0) {
						contentType = contentType.substring(0,
								contentType.indexOf(";"));
					}
					uploaditem.setContentType(contentType);
					uploaditem.setSize(item.getSize());
					rmap.put(item.getFieldName(), uploaditem);
				}
			}
		} catch (Exception e) {
		}
		return rmap;
	}
}
