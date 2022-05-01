package com.tlv8.doc.svr.generator.dao.impl;

import java.util.List;

import org.mybatis.spring.support.SqlSessionDaoSupport;

import com.tlv8.doc.svr.generator.beans.DocAuthor;
import com.tlv8.doc.svr.generator.dao.IDocAuthorDao;

public class DocAuthorDaoImpl extends SqlSessionDaoSupport implements
		IDocAuthorDao {

	@Override
	public DocAuthor getByPrimaryKey(String fID) {
		return this
				.getSqlSession()
				.selectOne(
						"com.tlv8.doc.svr.generator.dao.IDocAuthorDao.getByPrimaryKey",
						fID);
	}

	@Override
	public DocAuthor getByUserID(String fUserID) {
		return this
				.getSqlSession()
				.selectOne(
						"com.tlv8.doc.svr.generator.dao.IDocAuthorDao.getByUserID",
						fUserID);
	}

	@Override
	public List<DocAuthor> getList() {
		return this.getSqlSession().selectList(
				"com.tlv8.doc.svr.generator.dao.IDocAuthorDao.getList");
	}

	@Override
	public int insert(DocAuthor docauthor) {
		return this.getSqlSession().insert(
				"com.tlv8.doc.svr.generator.dao.IDocAuthorDao.insert",
				docauthor);
	}

	@Override
	public int update(DocAuthor docauthor) {
		return this.getSqlSession().update(
				"com.tlv8.doc.svr.generator.dao.IDocAuthorDao.update",
				docauthor);
	}

	@Override
	public int deleteByPrimaryKey(String fID) {
		return this
				.getSqlSession()
				.delete("com.tlv8.doc.svr.generator.dao.IDocAuthorDao.deleteByPrimaryKey",
						fID);
	}

	@Override
	public int deleteByUserID(String fUserID) {
		return this
				.getSqlSession()
				.delete("com.tlv8.doc.svr.generator.dao.IDocAuthorDao.deleteByUserID",
						fUserID);
	}

}
