package com.tlv8.doc.svr.controller.data;

import java.util.Date;

import com.tlv8.doc.svr.core.io.FileUploader;
import com.tlv8.doc.svr.core.io.centent.FileIOContent;
import com.tlv8.doc.svr.generator.beans.DocDocPath;
import com.tlv8.doc.svr.generator.beans.DocDocument;
import com.tlv8.doc.svr.generator.service.DocDocPathService;
import com.tlv8.doc.svr.generator.service.DocDocumentService;
import com.tlv8.doc.svr.generator.utils.IDUtils;

public class FileUploadData {
	/*
	 * 新文件数据保存
	 */
	public static void newDocSave(FileIOContent content) {
		Date addtime = new Date();
		DocDocument doc = new DocDocument();
		doc.setFID(IDUtils.getGUID());
		doc.setFDocID(content.getFileID());
		doc.setFDocName(content.getFileName());
		doc.setFExtName(content.getExtName());
		doc.setFDocSize(content.getFileSize());
		doc.setFDocType(content.getFileType());
		doc.setFAddTime(addtime);
		doc.setVersion(0);
		DocDocumentService.addDocument(doc);

		DocDocPath docpath = new DocDocPath();
		docpath.setFID(IDUtils.getGUID());
		docpath.setFFileID(content.getFileID());
		docpath.setFFilePath(content.getFilePath());
		docpath.setFFileSize(content.getFileSize());
		docpath.setFVersion(1);
		docpath.setFAddTime(addtime);
		docpath.setVersion(0);
		DocDocPathService.addDocDocPath(docpath);
	}

	/*
	 * 保存新版本
	 */
	public static void saveDocNewVersion(String fileID, String cachename) {
		DocDocPath docpath = DocDocPathService.getDocDocPathByFileID(fileID);
		DocDocPath cachepath = DocDocPathService
				.getDocDocPathByFileID(cachename);
		cachepath.setFVersion(docpath.getFVersion() + 1);
		cachepath.setFFileID(docpath.getFFileID());
		DocDocPathService.updateDocDocPath(cachepath);

		DocDocument doc = DocDocumentService.getDocumentByDocID(fileID);
		DocDocument cachedoc = DocDocumentService.getDocumentByDocID(cachename);
		doc.setFDocName(cachedoc.getFDocName());
		doc.setFDocSize(cachedoc.getFDocSize());
		doc.setFDocType(cachedoc.getFDocType());
		doc.setFExtName(cachedoc.getFExtName());
		doc.setFUpdateTime(cachedoc.getFAddTime());
		DocDocumentService.updateDocument(doc);// 更新版本信息

		DocDocumentService.deleteDocumentByDocID(cachename);// 删除临时版本

		FileUploader.ChangeFileID(fileID, cachename, cachepath.getFFilePath());
	}
}
