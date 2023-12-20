package com.tlv8.system.service.impl;

import com.tlv8.system.mapper.SaOppermissionMapper;
import com.tlv8.system.pojo.SaOppermission;
import com.tlv8.system.service.SaOppermissionService;

import org.springframework.stereotype.Service;

import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by TLv8 IDE on 2023/12/20.
 */
@Service
public class SaOppermissionServiceImpl implements SaOppermissionService {
	@Autowired
	private SaOppermissionMapper saOppermissionMapper;

	public int deleteByPrimaryKey(String id) {
		return saOppermissionMapper.deleteByPrimaryKey(id);
	}

	public int insert(SaOppermission row) {
		return saOppermissionMapper.insert(row);
	}

	public SaOppermission selectByPrimaryKey(String id) {
		return saOppermissionMapper.selectByPrimaryKey(id);
	}

	public List<SaOppermission> selectAll() {
		return saOppermissionMapper.selectAll();
	}

	public int updateByPrimaryKey(SaOppermission row) {
		return saOppermissionMapper.updateByPrimaryKey(row);
	}

	@Override
	public List<SaOppermission> selectPermissionByPerson(String personfID, String psnid) {
		Map<String, String> param = new HashMap<String, String>();
		param.put("personfID", personfID);
		param.put("psnid", psnid);
		return saOppermissionMapper.selectPermissionByPerson(param);
	}

}
