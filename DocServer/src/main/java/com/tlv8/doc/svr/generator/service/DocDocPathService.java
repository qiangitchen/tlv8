package com.tlv8.doc.svr.generator.service;

import java.util.Date;
import java.util.List;

import com.tlv8.doc.svr.generator.beans.DocDocPath;
import com.tlv8.doc.svr.generator.dao.IDocDocPathDao;
import com.tlv8.doc.svr.generator.utils.IDUtils;

public class DocDocPathService {
	private static IDocDocPathDao docdocpathdao;

	public void setDocdocpathdao(IDocDocPathDao docdocpathdao) {
		DocDocPathService.docdocpathdao = docdocpathdao;
	}

	public static String addDocDocPath(String fFileID, String fFilePath,
			float fFileSize, int fVersion) {
		String ndpid = IDUtils.getGUID();
		DocDocPath docpath = new DocDocPath();
		docpath.setFID(ndpid);
		docpath.setFFileID(fFileID);
		docpath.setFFilePath(fFilePath);
		docpath.setFFileSize(fFileSize);
		docpath.setFVersion(fVersion);
		docpath.setFAddTime(new Date());
		docpath.setVersion(0);
		docdocpathdao.insert(docpath);
		return ndpid;
	}

	public static void addDocDocPath(DocDocPath docpath) {
		docdocpathdao.insert(docpath);
	}

	public static void updateDocDocPath(String fID, String fFileID,
			String fFilePath, float fFileSize, int fVersion) {
		DocDocPath docpath = docdocpathdao.getByPrimaryKey(fID);
		docpath.setFFileID(fFileID);
		docpath.setFFilePath(fFilePath);
		docpath.setFFileSize(fFileSize);
		docpath.setFVersion(fVersion);
		docpath.setFAddTime(new Date());
		docpath.setVersion(docpath.getVersion() + 1);
		docdocpathdao.update(docpath);
	}

	public static void updateDocDocPath(DocDocPath docpath) {
		docdocpathdao.update(docpath);
	}

	public static int deleteDocDocPath(String fID) {
		return docdocpathdao.deleteByPrimaryKey(fID);
	}

	public static int deleteDocDocPathByFileID(String fFileID) {
		return docdocpathdao.deleteByFileID(fFileID);
	}

	public static DocDocPath getDocDocPath(String fID) {
		return docdocpathdao.getByPrimaryKey(fID);
	}

	public static DocDocPath getDocDocPathByFileID(String fFileID) {
		return docdocpathdao.getByFileID(fFileID);
	}

	public static List<DocDocPath> getDocDocPathList() {
		return docdocpathdao.getList();
	}

	public static List<DocDocPath> getDocDocPathListByFileID(String fFileID) {
		return docdocpathdao.getListByFileID(fFileID);
	}

	public static DocDocPath getDocDocPathByFileIDVersion(String fFileID,
			long fVersion) {
		List<DocDocPath> flist = getDocDocPathListByFileID(fFileID);
		long maxversion = 1;
		DocDocPath rdpath = flist.get(0);
		for (int i = 0; i < flist.size(); i++) {
			if (flist.get(i).getFVersion() > maxversion) {
				maxversion = flist.get(i).getFVersion();
				rdpath = flist.get(i);
			}
			if (flist.get(i).getFVersion() == fVersion) {
				return flist.get(i);
			}
		}
		return rdpath;
	}

}
