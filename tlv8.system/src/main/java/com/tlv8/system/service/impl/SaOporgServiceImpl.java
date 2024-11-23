package com.tlv8.system.service.impl;

import com.tlv8.system.mapper.SaOporgMapper;
import com.tlv8.system.pojo.SaOporg;
import com.tlv8.system.service.SaOporgService;

import org.springframework.stereotype.Service;

import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class SaOporgServiceImpl implements SaOporgService {
	@Autowired
	SaOporgMapper sSaOpOrgMapper;

	public List<SaOporg> selectList() {
		return sSaOpOrgMapper.selectList();
	}

	public List<SaOporg> selectRootList() {
		return sSaOpOrgMapper.selectRootList();
	}

	public List<SaOporg> selectListByParentID(String parent) {
		return sSaOpOrgMapper.selectListByParentID(parent);
	}

	@Override
	public SaOporg selectByPrimaryKey(String sid) {
		return sSaOpOrgMapper.selectByPrimaryKey(sid);
	}

	@Override
	public int insertData(SaOporg obj) {
		return sSaOpOrgMapper.insertData(obj);
	}

	@Override
	public int updateData(SaOporg obj) {
		return sSaOpOrgMapper.updateData(obj);
	}

	@Override
	public int deleteData(SaOporg obj) {
		return sSaOpOrgMapper.deleteData(obj);
	}

	@Override
	public int deleteDataByPrimaryKey(String sid) {
		return sSaOpOrgMapper.deleteDataByPrimaryKey(sid);
	}

	@Override
	public int logicDeleteDataByPrimaryKey(String sid) {
		return sSaOpOrgMapper.logicDeleteDataByPrimaryKey(sid);
	}

	@Override
	public int logicRecoveryByPrimaryKey(String sid) {
		return sSaOpOrgMapper.logicRecoveryByPrimaryKey(sid);
	}

	@Override
	public List<SaOporg> selectRecycleList() {
		return sSaOpOrgMapper.selectRecycleList();
	}

	public List<SaOporg> selectAllByParentID(String parent) {
		return sSaOpOrgMapper.selectAllByParentID(parent);
	}

	@Override
	public SaOporg selectByParentIdPersonId(String parent, String personid) {
		Map<String, String> param = new HashMap<String, String>();
		param.put("parent", parent);
		param.put("personid", personid);
		return sSaOpOrgMapper.selectByParentIdPersonId(param);
	}

}
