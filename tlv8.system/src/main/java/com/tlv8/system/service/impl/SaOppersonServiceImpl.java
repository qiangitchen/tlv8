package com.tlv8.system.service.impl;

import com.tlv8.system.mapper.SaOppersonMapper;
import com.tlv8.system.pojo.SaOpperson;
import com.tlv8.system.service.SaOppersonService;

import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

@Service
public class SaOppersonServiceImpl implements SaOppersonService {
	@Autowired
	SaOppersonMapper saOpPersonMapper;

	@Override
	public SaOpperson selectByPrimaryKey(String sid) {
		return saOpPersonMapper.selectByPrimaryKey(sid);
	}

	public SaOpperson selectByCode(String scode) {
		return saOpPersonMapper.selectByCode(scode);
	}

	@Override
	public int insertData(SaOpperson obj) {
		return saOpPersonMapper.insertData(obj);
	}

	@Override
	public int updateData(SaOpperson obj) {
		return saOpPersonMapper.updateData(obj);
	}

	@Override
	public int deleteData(SaOpperson obj) {
		return saOpPersonMapper.deleteData(obj);
	}

	@Override
	public int deleteDataByPrimaryKey(String sid) {
		return saOpPersonMapper.deleteDataByPrimaryKey(sid);
	}

	@Override
	public int logicDeleteDataByPrimaryKey(String sid) {
		return saOpPersonMapper.logicDeleteDataByPrimaryKey(sid);
	}

	@Override
	public int logicRecoveryByPrimaryKey(String sid) {
		return saOpPersonMapper.logicRecoveryByPrimaryKey(sid);
	}

}
