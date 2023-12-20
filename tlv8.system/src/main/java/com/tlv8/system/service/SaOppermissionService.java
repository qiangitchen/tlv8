package com.tlv8.system.service;

import com.tlv8.system.pojo.SaOppermission;

import java.util.List;

/**
 * Created by TLv8 IDE on 2023/12/20.
 */
public interface SaOppermissionService {

	int deleteByPrimaryKey(String id);
	
	int insert(SaOppermission row);
	
	SaOppermission selectByPrimaryKey(String id);
	
	List<SaOppermission> selectAll();
	
	int updateByPrimaryKey(SaOppermission row);
	
	List<SaOppermission> selectPermissionByPerson(String personfID, String psnid);
}
