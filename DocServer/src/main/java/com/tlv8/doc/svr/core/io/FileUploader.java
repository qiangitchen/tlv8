package com.tlv8.doc.svr.core.io;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import com.tlv8.doc.svr.core.TransePath;
import com.tlv8.doc.svr.core.inter.IDoc;
import com.tlv8.doc.svr.core.io.centent.FileIOContent;
import com.tlv8.doc.svr.core.utils.FileExtArray;
import com.tlv8.doc.svr.core.utils.MD5Util;

import jcifs.smb.SmbFile;
import jcifs.smb.SmbFileOutputStream;

public class FileUploader {
	/*
	 * 文件上传{将文件流写入指定的位置}
	 */
	public static void upload(InputStream inputstream, String fileID, String docPath) throws Exception {
		String dirPath = TransePath.docPath2FilePath(docPath);
		if (dirPath.indexOf("smb://") == 0) {
			SmbFile remoteFile = new SmbFile(dirPath);
			remoteFile.connect();
			if (!remoteFile.exists()) {
				remoteFile.mkdirs();
			}
			SmbFile ufile = new SmbFile(dirPath + "/" + MD5Util.encode(fileID));
			ufile.connect();
			if (!ufile.exists()) {
				ufile.createNewFile();
			}
			SmbFileOutputStream ous = new SmbFileOutputStream(ufile);
			byte[] b = new byte[1024];
			int n = 0;
			while ((n = inputstream.read(b)) != -1) {
				ous.write(b, 0, n);
			}
			ous.close();
			inputstream.close();
		} else {
			File fileDir = new File(dirPath);
			if (dirPath.indexOf("file:/") == 0) {
				fileDir = new File(new URI(dirPath).getPath());
			}
			if (!fileDir.exists()) {
				fileDir.mkdirs();
			}
			File file = new File(fileDir.getAbsoluteFile(), MD5Util.encode(fileID));
			if (!file.exists()) {
				file.createNewFile();
			}
			FileOutputStream fos = new FileOutputStream(file);
			byte[] b = new byte[1024];
			int n = 0;
			while ((n = inputstream.read(b)) != -1) {
				fos.write(b, 0, n);
			}
			fos.close();
			inputstream.close();
		}
	}

	public static FileIOContent fileUpload(HttpServletRequest request, IDoc doc) throws ServletException, IOException {
		FileIOContent content = new FileIOContent();
		request.setCharacterEncoding("utf-8"); // 设置编码
		// 获得磁盘文件条目工厂
		DiskFileItemFactory factory = new DiskFileItemFactory();
		factory.setRepository(new File(TransePath.getSavePath()));
		// 设置 缓存的大小，当上传文件的容量超过该缓存时，直接放到 暂时存储室
		factory.setSizeThreshold(1024 * 1024);
		// 高水平的API文件上传处理
		ServletFileUpload upload = new ServletFileUpload(factory);
		try {
			// 可以上传多个文件
			List<FileItem> list = (List<FileItem>) upload.parseRequest(request);
			for (FileItem item : list) {
				// 只对单个文件相应
				// 获取表单的属性名字
				String name = item.getFieldName();
				// 如果获取的 表单信息是普通的 文本 信息
				if (item.isFormField()) {
					// 获取用户具体输入的字符串 ，名字起得挺好，因为表单提交过来的是 字符串类型的
					String value = item.getString();
					request.setAttribute(name, value);
				}
				// 对传入的非 简单的字符串进行处理 ，比如说二进制的 图片，电影这些
				else {
					/**
					 * 以下三步，主要获取 上传文件的名字
					 */
					// 获取路径名
					String value = item.getName();
					// 索引到最后一个反斜杠
					int start = value.lastIndexOf("\\");
					// 截取 上传文件的 字符串名字，加1是 去掉反斜杠，
					String filename = value.substring(start + 1);
					content.setFileName(filename);
					request.setAttribute(name + "FileName", filename);
					// 真正写到磁盘上
					// 它抛出的异常 用exception 捕捉
					// item.write( new File(path,filename) );//第三方提供的
					content.setFileSize(item.getSize());
					String contentType = item.getContentType();
					request.setAttribute(name + "ContentType", contentType);
					if (contentType.indexOf(";") > 0) {
						contentType = contentType.substring(0, contentType.indexOf(";"));
					}
					content.setFileType(contentType);
					content.setExtName(FileExtArray.getExtName(filename));
					String fileID = doc.getNewDocID();
					String docPath = doc.getNewDocPath();
					content.setFileID(fileID);
					content.setFilePath(docPath);
					InputStream in = item.getInputStream();
					upload(in, fileID, docPath);
				}
			}
		} catch (FileUploadException e) {
			e.printStackTrace();
		} catch (Exception e) {
		}
		return content;
	}

	public static void ChangeFileID(String nfileID, String ofileID, String docPath) {
		String dirPath = TransePath.docPath2FilePath(docPath);
		File file = new File(dirPath, MD5Util.encode(ofileID));
		File nfile = new File(dirPath, MD5Util.encode(nfileID));
		if (file.exists()) {
			file.renameTo(nfile);
		}
	}
}
