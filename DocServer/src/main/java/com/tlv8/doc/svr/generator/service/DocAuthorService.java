package com.tlv8.doc.svr.generator.service;

import java.util.List;

import com.tlv8.doc.svr.generator.beans.DocAuthor;
import com.tlv8.doc.svr.generator.dao.IDocAuthorDao;
import com.tlv8.doc.svr.generator.utils.IDUtils;

public class DocAuthorService {
	private static IDocAuthorDao docauthordao;

	public static void setDocauthordao(IDocAuthorDao docauthordao) {
		DocAuthorService.docauthordao = docauthordao;
	}

	public static String addDocAuthor(String fUserID, int fAmLeve) {
		String nauid = IDUtils.getGUID();
		DocAuthor docauthor = new DocAuthor();
		docauthor.setFID(nauid);
		docauthor.setFUserID(fUserID);
		docauthor.setFAmLeve(fAmLeve);
		docauthor.setVersion(0);
		docauthordao.insert(docauthor);
		return nauid;
	}

	public static void addDocAuthor(DocAuthor docauthor) {
		docauthordao.insert(docauthor);
	}

	public static void updateDocAuthor(String fID, String fUserID, int fAmLeve) {
		DocAuthor docauthor = docauthordao.getByPrimaryKey(fID);
		docauthor.setFUserID(fUserID);
		docauthor.setFAmLeve(fAmLeve);
		docauthor.setVersion(docauthor.getVersion() + 1);
		docauthordao.update(docauthor);
	}

	public static void updateDocAuthor(DocAuthor docauthor) {
		docauthordao.update(docauthor);
	}

	public static int deleteDocAuthor(String fID) {
		return docauthordao.deleteByPrimaryKey(fID);
	}

	public static int deleteDocAuthorByUserID(String fUserID) {
		return docauthordao.deleteByUserID(fUserID);
	}

	public static DocAuthor getDocAuthor(String fID) {
		return docauthordao.getByPrimaryKey(fID);
	}

	public static DocAuthor getDocAuthorByUserID(String fUserID) {
		return docauthordao.getByUserID(fUserID);
	}

	public static List<DocAuthor> getDocAuthorList() {
		return docauthordao.getList();
	}
}
