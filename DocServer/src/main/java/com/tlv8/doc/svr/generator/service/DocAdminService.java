package com.tlv8.doc.svr.generator.service;

import java.util.List;

import com.tlv8.doc.svr.generator.beans.DocAdmin;
import com.tlv8.doc.svr.generator.dao.IDocAdminDao;
import com.tlv8.doc.svr.generator.utils.IDUtils;

public class DocAdminService {
	private static IDocAdminDao docadmindao;

	public static void setDocadmindao(IDocAdminDao docadmindao) {
		DocAdminService.docadmindao = docadmindao;
	}

	/*
	 * 添加
	 */
	public static String addDocAdmin(int fLeve, String fName) {
		String naid = IDUtils.getGUID();
		DocAdmin docadmin = new DocAdmin();
		docadmin.setFID(naid);
		docadmin.setFLeve(fLeve);
		docadmin.setFName(fName);
		docadmin.setVersion(0);
		docadmindao.insert(docadmin);
		return naid;
	}

	/*
	 * 添加
	 */
	public static void addDocAdmin(DocAdmin docadmin) {
		docadmindao.insert(docadmin);
	}

	/*
	 * 更新
	 */
	public static void updateDocAdmin(String fID, int fLeve, String fName) {
		DocAdmin docadmin = docadmindao.getByPrimaryKey(fID);
		docadmin.setFID(fID);
		docadmin.setFLeve(fLeve);
		docadmin.setFName(fName);
		docadmin.setVersion(docadmin.getVersion() + 1);
		docadmindao.update(docadmin);
	}

	/*
	 * 更新
	 */
	public static void updateDocAdmin(DocAdmin docadmin) {
		docadmindao.update(docadmin);
	}

	/*
	 * 删除
	 */
	public static int deleteDocAdmin(String fID) {
		return docadmindao.deleteByPrimaryKey(fID);
	}

	/*
	 * 获取指定ID的数据
	 */
	public static DocAdmin getDocAdminByFID(String fID) {
		return docadmindao.getByPrimaryKey(fID);
	}

	/*
	 * 获取所有数据
	 */
	public static List<DocAdmin> getDocAdminList() {
		return docadmindao.getList();
	}
}
