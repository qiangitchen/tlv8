package com.tlv8.doc.svr.generator.dao.impl;

import java.util.List;

import org.mybatis.spring.support.SqlSessionDaoSupport;

import com.tlv8.doc.svr.generator.beans.DocLog;
import com.tlv8.doc.svr.generator.dao.IDocLogDao;

public class DocLogDaoImpl extends SqlSessionDaoSupport implements IDocLogDao {

	@Override
	public DocLog getByPrimaryKey(String fID) {
		return this
				.getSqlSession()
				.selectOne(
						"com.tlv8.doc.svr.generator.dao.IDocLogDao.getByPrimaryKey",
						fID);
	}

	@Override
	public DocLog getByUserID(String fUserID) {
		return this.getSqlSession().selectOne(
				"com.tlv8.doc.svr.generator.dao.IDocLogDao.getByUserID",
				fUserID);
	}

	@Override
	public List<DocLog> getList() {
		return this.getSqlSession().selectList(
				"com.tlv8.doc.svr.generator.dao.IDocLogDao.getList");
	}

	@Override
	public int insert(DocLog doclog) {
		return this
				.getSqlSession()
				.insert("com.tlv8.doc.svr.generator.dao.IDocLogDao.insert",
						doclog);
	}

	@Override
	public int update(DocLog doclog) {
		return this
				.getSqlSession()
				.update("com.tlv8.doc.svr.generator.dao.IDocLogDao.update",
						doclog);
	}

	@Override
	public int deleteByPrimaryKey(String fID) {
		return this
				.getSqlSession()
				.delete("com.tlv8.doc.svr.generator.dao.IDocLogDao.deleteByPrimaryKey",
						fID);
	}

	@Override
	public int deleteByUserID(String fUserID) {
		return this
				.getSqlSession()
				.delete("com.tlv8.doc.svr.generator.dao.IDocLogDao.deleteByUserID",
						fUserID);
	}

	@Override
	public void clearData() {
		this.getSqlSession().delete(
				"com.tlv8.doc.svr.generator.dao.IDocLogDao.clearData");
	}

}
