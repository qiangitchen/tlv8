package com.tlv8.doc.svr.generator.dao.impl;

import java.util.List;

import org.mybatis.spring.support.SqlSessionDaoSupport;

import com.tlv8.doc.svr.generator.beans.DocDocPath;
import com.tlv8.doc.svr.generator.dao.IDocDocPathDao;

public class DocDocPathDaoImpl extends SqlSessionDaoSupport implements
		IDocDocPathDao {

	public DocDocPath getByPrimaryKey(String fID) {
		return this
				.getSqlSession()
				.selectOne(
						"com.tlv8.doc.svr.generator.dao.IDocDocPathDao.getByPrimaryKey",
						fID);
	}

	public DocDocPath getByFileID(String fFileID) {
		return this
				.getSqlSession()
				.selectOne(
						"com.tlv8.doc.svr.generator.dao.IDocDocPathDao.getByFileID",
						fFileID);
	}

	public List<DocDocPath> getList() {
		return this.getSqlSession().selectList(
				"com.tlv8.doc.svr.generator.dao.IDocDocPathDao.getList");
	}

	public int insert(DocDocPath DocDocPath) {
		return this.getSqlSession().insert(
				"com.tlv8.doc.svr.generator.dao.IDocDocPathDao.insert",
				DocDocPath);
	}

	public int update(DocDocPath DocDocPath) {
		return this.getSqlSession().update(
				"com.tlv8.doc.svr.generator.dao.IDocDocPathDao.update",
				DocDocPath);
	}

	public int deleteByPrimaryKey(String fID) {
		return this
				.getSqlSession()
				.delete("com.tlv8.doc.svr.generator.dao.IDocDocPathDao.deleteByPrimaryKey",
						fID);
	}

	public int deleteByFileID(String fFileID) {
		return this
				.getSqlSession()
				.delete("com.tlv8.doc.svr.generator.dao.IDocDocPathDao.deleteByFileID",
						fFileID);
	}

	@Override
	public List<DocDocPath> getListByFileID(String fFileID) {
		return this
				.getSqlSession()
				.selectList(
						"com.tlv8.doc.svr.generator.dao.IDocDocPathDao.getListByFileID",
						fFileID);
	}

}
