package com.tlv8.doc.svr.generator.service;

import java.util.List;

import com.tlv8.doc.svr.generator.beans.DocUser;
import com.tlv8.doc.svr.generator.dao.IDocUserDao;
import com.tlv8.doc.svr.generator.utils.IDUtils;

public class DocUserService {
	private static IDocUserDao docuserdao;

	public void setDocuserdao(IDocUserDao docuserdao) {
		DocUserService.docuserdao = docuserdao;
	}

	public static String addDocUser(String fLoginID, String fUserName,
			int fEnable) {
		String nusid = IDUtils.getGUID();
		DocUser docuser = new DocUser();
		docuser.setFID(nusid);
		docuser.setFLoginID(fLoginID);
		docuser.setFUserName(fUserName);
		docuser.setFEnable(fEnable);
		docuser.setVersion(0);
		docuserdao.insert(docuser);
		return nusid;
	}

	public static void addDocUser(DocUser docuser) {
		docuserdao.insert(docuser);
	}

	public static void updateDocUser(String fID, String fLoginID,
			String fUserName, int fEnable) {
		DocUser docuser = docuserdao.getByPrimaryKey(fID);
		docuser.setFLoginID(fLoginID);
		docuser.setFUserName(fUserName);
		docuser.setFEnable(fEnable);
		docuser.setVersion(docuser.getVersion() + 1);
		docuserdao.update(docuser);
	}
	
	public static int deleteDocUser(String fID){
		return docuserdao.deleteByPrimaryKey(fID);
	}
	
	public static int deleteDocUserByLoginID(String fLoginID){
		return docuserdao.deleteByLoginID(fLoginID);
	}
	
	public static DocUser getDocUser(String fID){
		return docuserdao.getByPrimaryKey(fID);
	}
	
	public static DocUser getDocUserByLoginID(String fLoginID){
		return docuserdao.getByLoginID(fLoginID);
	}
	
	public static List<DocUser> getDocUserList(){
		return docuserdao.getList();
	}
}
