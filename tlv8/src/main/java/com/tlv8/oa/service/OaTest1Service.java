package com.tlv8.oa.service;

import com.tlv8.oa.pojo.OaTest1;

import java.util.List;

/**
 * Created by TLv8 IDE on 2023/11/29.
 */
public interface OaTest1Service {

	int deleteByPrimaryKey(String id);
	
	int insert(OaTest1 row);
	
	OaTest1 selectByPrimaryKey(String id);
	
	List<OaTest1> selectAll();
	
	int updateByPrimaryKey(OaTest1 row);
}
