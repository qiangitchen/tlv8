package com.tlv8.oa.service.impl;

import com.tlv8.oa.mapper.OaTest1Mapper;
import com.tlv8.oa.pojo.OaTest1;
import com.tlv8.oa.service.OaTest1Service;

import org.springframework.stereotype.Service;

import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
/**
 * Created by TLv8 IDE on 2023/11/29.
 */
@Service
public class OaTest1ServiceImpl implements OaTest1Service {
    @Autowired
    private OaTest1Mapper oaTest1Mapper;
    
    public int deleteByPrimaryKey(String id){
    	return oaTest1Mapper.deleteByPrimaryKey(id);
    }
	
	public int insert(OaTest1 row){
		return oaTest1Mapper.insert(row);
	}
	
	public OaTest1 selectByPrimaryKey(String id){
		return oaTest1Mapper.selectByPrimaryKey(id);
	}
	
	public List<OaTest1> selectAll(){
		return oaTest1Mapper.selectAll();
	}
	
	public int updateByPrimaryKey(OaTest1 row){
		return oaTest1Mapper.updateByPrimaryKey(row);
	}

}
