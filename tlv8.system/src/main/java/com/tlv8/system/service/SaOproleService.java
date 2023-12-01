package com.tlv8.system.service;

import com.tlv8.system.pojo.SaOprole;

import java.util.List;

/**
 * Created by TLv8 IDE on 2023/12/01.
 */
public interface SaOproleService {

	int deleteByPrimaryKey(String id);
	
	int insert(SaOprole row);
	
	SaOprole selectByPrimaryKey(String id);
	
	List<SaOprole> selectAll();
	
	int updateByPrimaryKey(SaOprole row);
}
