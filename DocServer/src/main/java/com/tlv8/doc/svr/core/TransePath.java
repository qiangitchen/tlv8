package com.tlv8.doc.svr.core;

import com.tlv8.doc.svr.core.utils.MD5Util;

public class TransePath {
	// 文档服务文件位置"data/doc"
	private static String baseDocPath = "E:/TuLin1.0_All/TuLinv8_win64/data/doc";

	// 提供设置方法，方便系统初始化
	public static void setBaseDocPath(String baseDocPath) {
		TransePath.baseDocPath = baseDocPath;
	}

	public static String getSavePath() {
		return baseDocPath;
	}

	public static String docPath2FilePath(String docPath) {
		return getSavePath() + "/document/" + docPath;
	}

	// 为lucene提供索引文件存储位置
	public static String getIndexPath() {
		return getSavePath() + "/lucene";
	}

	/*
	 * 获取文件的绝对位置
	 */
	public static String getFileAbsolutePath(String fileID, String docPath) {
		return docPath2FilePath(docPath) + "/" + MD5Util.encode(fileID);
	}
}
