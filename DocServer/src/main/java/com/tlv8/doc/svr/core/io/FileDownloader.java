package com.tlv8.doc.svr.core.io;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.URI;
import java.net.URLEncoder;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import com.tlv8.doc.svr.core.TransePath;
import com.tlv8.doc.svr.core.inter.IDoc;

import jcifs.smb.SmbFile;
import jcifs.smb.SmbFileInputStream;

public class FileDownloader {
	/*
	 * 根据 fileID, docPath获取文件流
	 */
	public static InputStream download(String fileID, String docPath) throws Exception {
		String fileabspath = TransePath.getFileAbsolutePath(fileID, docPath);
		if (fileabspath.indexOf("smb://") == 0) {
			SmbFile remoteFile = new SmbFile(fileabspath);
			remoteFile.connect(); // 尝试连接
			InputStream in = new BufferedInputStream(new SmbFileInputStream(remoteFile));
			return in;
		}
		if (fileabspath.indexOf("file:/") == 0) {
			File file = new File(new URI(fileabspath).getPath());
			FileInputStream inputStream = new FileInputStream(file);
			return inputStream;
		} else {
			File file = new File(fileabspath);
			FileInputStream inputStream = new FileInputStream(file);
			return inputStream;
		}
	}

	public static int getFileSize(String fileID, String docPath) throws Exception {
		String fileabspath = TransePath.getFileAbsolutePath(fileID, docPath);
		if (fileabspath.indexOf("smb://") == 0) {
			SmbFile remoteFile = new SmbFile(fileabspath);
			remoteFile.connect(); // 尝试连接
			return (int) remoteFile.length();
		}
		if (fileabspath.indexOf("file:/") == 0) {
			File file = new File(new URI(fileabspath).getPath());
			return (int) file.length();
		} else {
			File file = new File(fileabspath);
			return (int) file.length();
		}
	}

	public static File getFile(String fileID, String docPath) throws Exception {
		String fileabspath = TransePath.getFileAbsolutePath(fileID, docPath);
		if (fileabspath.indexOf("smb://") == 0) {
			SmbFile remoteFile = new SmbFile(fileabspath);
			remoteFile.connect(); // 尝试连接
			File file = new File(fileID);
			FileOutputStream ous = new FileOutputStream(file);
			BufferedInputStream ins = new BufferedInputStream(new SmbFileInputStream(remoteFile));
			byte[] arrayOfByte = new byte[2048];
			try {
				int i;
				while ((i = ins.read(arrayOfByte)) != -1) {
					ous.write(arrayOfByte, 0, i);
				}
			} finally {
				ins.close();
				ous.close();
			}
			return file;
		}
		if (fileabspath.indexOf("file:/") == 0) {
			File file = new File(new URI(fileabspath).getPath());
			return file;
		} else {
			File file = new File(fileabspath);
			return file;
		}
	}

	/*
	 * 直接将文件流写入HttpServletResponse
	 */
	public void fileDownload(HttpServletResponse response, String fileID, String docPath, String fileName)
			throws Exception {
		InputStream inputStream = download(fileID, docPath);
		// 申明文件大小
		response.setContentLength(getFileSize(fileID, docPath));
		// 1.设置文件ContentType类型，这样设置，会自动判断下载文件类型
		response.setContentType("multipart/form-data");
		// 设置编码方式为utf-8
		response.setCharacterEncoding("utf-8");
		// 2.设置文件头：最后一个参数是设置下载文件名
		response.setHeader("Content-Disposition", "attachment;fileName=" + URLEncoder.encode(fileName, "utf-8"));
		// 3.通过response获取ServletOutputStream对象(out)
		ServletOutputStream out = response.getOutputStream();
		byte[] arrayOfByte = new byte[2048];
		try {
			int i;
			while ((i = inputStream.read(arrayOfByte)) != -1)
				out.write(arrayOfByte, 0, i);
		} finally {
			if (inputStream != null) {
				inputStream.close(); // 关闭输入流
			}
			if (out != null) {
				out.close(); // 关闭输出流
			}
		}
	}

	/*
	 * 
	 */
	public static InputStream download(IDoc doc) throws Exception {
		return download(doc.getDocID(), doc.getDocPath());
	}
}
