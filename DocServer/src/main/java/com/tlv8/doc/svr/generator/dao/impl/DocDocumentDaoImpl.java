package com.tlv8.doc.svr.generator.dao.impl;

import java.util.List;

import org.mybatis.spring.support.SqlSessionDaoSupport;

import com.tlv8.doc.svr.generator.beans.DocDocument;
import com.tlv8.doc.svr.generator.dao.IDocDocumentDao;

public class DocDocumentDaoImpl extends SqlSessionDaoSupport implements
		IDocDocumentDao {

	public DocDocument getByPrimaryKey(String fID) {
		return this
				.getSqlSession()
				.selectOne(
						"com.tlv8.doc.svr.generator.dao.IDocDocumentDao.getByPrimaryKey",
						fID);
	}

	public DocDocument getByDocID(String fDocID) {
		return this
				.getSqlSession()
				.selectOne(
						"com.tlv8.doc.svr.generator.dao.IDocDocumentDao.getByDocID",
						fDocID);
	}

	public List<DocDocument> getList() {
		return this.getSqlSession().selectList(
				"com.tlv8.doc.svr.generator.dao.IDocDocumentDao.getList");
	}

	public int insert(DocDocument docdocument) {
		return this.getSqlSession().insert(
				"com.tlv8.doc.svr.generator.dao.IDocDocumentDao.insert",
				docdocument);
	}

	public int update(DocDocument docdocument) {
		return this.getSqlSession().update(
				"com.tlv8.doc.svr.generator.dao.IDocDocumentDao.update",
				docdocument);
	}

	public int deleteByPrimaryKey(String fID) {
		return this
				.getSqlSession()
				.delete("com.tlv8.doc.svr.generator.dao.IDocDocumentDao.deleteByPrimaryKey",
						fID);
	}

	public int deleteByDocID(String fDocID) {
		return this
				.getSqlSession()
				.delete("com.tlv8.doc.svr.generator.dao.IDocDocumentDao.deleteByDocID",
						fDocID);
	}

}
