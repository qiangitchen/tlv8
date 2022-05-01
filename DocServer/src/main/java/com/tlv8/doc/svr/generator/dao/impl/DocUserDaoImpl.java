package com.tlv8.doc.svr.generator.dao.impl;

import java.util.List;

import org.mybatis.spring.support.SqlSessionDaoSupport;

import com.tlv8.doc.svr.generator.beans.DocUser;
import com.tlv8.doc.svr.generator.dao.IDocUserDao;

public class DocUserDaoImpl extends SqlSessionDaoSupport implements IDocUserDao {

	@Override
	public DocUser getByPrimaryKey(String fID) {
		return this
				.getSqlSession()
				.selectOne(
						"com.tlv8.doc.svr.generator.dao.IDocUserDao.getByPrimaryKey",
						fID);
	}

	@Override
	public DocUser getByLoginID(String fLoginID) {
		return this.getSqlSession().selectOne(
				"com.tlv8.doc.svr.generator.dao.IDocUserDao.getByLoginID",
				fLoginID);
	}

	@Override
	public List<DocUser> getList() {
		return this.getSqlSession().selectList(
				"com.tlv8.doc.svr.generator.dao.IDocUserDao.getList");
	}

	@Override
	public int insert(DocUser docuser) {
		return this.getSqlSession().insert(
				"com.tlv8.doc.svr.generator.dao.IDocUserDao.insert",
				docuser);
	}

	@Override
	public int update(DocUser docuser) {
		return this.getSqlSession().update(
				"com.tlv8.doc.svr.generator.dao.IDocUserDao.update",
				docuser);
	}

	@Override
	public int deleteByPrimaryKey(String fID) {
		return this
				.getSqlSession()
				.delete("com.tlv8.doc.svr.generator.dao.IDocUserDao.deleteByPrimaryKey",
						fID);
	}

	@Override
	public int deleteByLoginID(String fLoginID) {
		return this
				.getSqlSession()
				.delete("com.tlv8.doc.svr.generator.dao.IDocUserDao.deleteByLoginID",
						fLoginID);
	}

}
