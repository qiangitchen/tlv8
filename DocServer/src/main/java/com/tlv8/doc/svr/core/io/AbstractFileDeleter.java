package com.tlv8.doc.svr.core.io;

import java.io.File;
import java.io.IOException;

import com.tlv8.doc.svr.core.TransePath;
import com.tlv8.doc.svr.core.inter.IFileDeleter;
import com.tlv8.doc.svr.core.utils.MD5Util;

public abstract class AbstractFileDeleter implements IFileDeleter {
	/*
	 * 获取文件
	 */
	protected File getFile(String docID, String docPath) {
		String fileDir = TransePath.docPath2FilePath(docPath);
		return new File(fileDir + "/" + MD5Util.encode(docID));
	}

	/*
	 * 真删除文件
	 */
	protected void deleteFile(File file) {
		try {
			if (file.exists()) {
				file.delete();
			}
		} catch (Exception e) {
		}
		try {
			clearEmptyFolder(file);// 清除空文件夹
		} catch (Exception e) {
		}
	}

	/*
	 * 直接删除文件
	 */
	protected void deleteFile(String docID, String docPath) {
		File file = getFile(docID, docPath);
		deleteFile(file);
	}

	/*
	 * 清空空文件夹
	 */
	private void clearEmptyFolder(File file) throws IOException {
		File folder = file.getParentFile();
		while (!folder.getCanonicalPath().equals(
				new File(TransePath.getSavePath() + "/document")
						.getCanonicalPath())) {
			try {
				if (folder.listFiles().length < 1) {
					folder.delete();
				}
			} catch (Exception e) {
			}
			folder = folder.getParentFile();
		}
	}
}
