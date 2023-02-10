package com.tlv8.doc.svr.core.io;

import java.io.File;
import java.io.IOException;
import java.net.URI;

import com.tlv8.doc.svr.core.TransePath;
import com.tlv8.doc.svr.core.inter.IFileDeleter;

import jcifs.smb.SmbFile;

public abstract class AbstractFileDeleter implements IFileDeleter {
	/**
	 * 直接删除文件
	 */
	protected void deleteFile(String docID, String docPath) {
		try {
			String fileabspath = TransePath.getFileAbsolutePath(docID, docPath);
			if (fileabspath.indexOf("smb://") == 0) {
				SmbFile remoteFile = new SmbFile(fileabspath);
				remoteFile.connect();
				remoteFile.delete();
			} else if (fileabspath.indexOf("file:/") == 0) {
				File file = new File(new URI(fileabspath).getPath());
				file.delete();
			} else {
				File file = new File(fileabspath);
				file.delete();
				clearEmptyFolder(file);
			}
		} catch (Exception e) {
		}
	}

	/**
	 * 清空空文件夹
	 */
	private void clearEmptyFolder(File file) throws IOException {
		File folder = file.getParentFile();
		while (!folder.getCanonicalPath().equals(new File(TransePath.getSavePath() + "/document").getCanonicalPath())) {
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
