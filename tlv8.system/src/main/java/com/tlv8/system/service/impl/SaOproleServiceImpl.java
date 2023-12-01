package com.tlv8.system.service.impl;

import com.tlv8.system.mapper.SaOproleMapper;
import com.tlv8.system.pojo.SaOprole;
import com.tlv8.system.service.SaOproleService;

import org.springframework.stereotype.Service;

import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
/**
 * Created by TLv8 IDE on 2023/12/01.
 */
@Service
public class SaOproleServiceImpl implements SaOproleService {
    @Autowired
    private SaOproleMapper saOproleMapper;
    
    public int deleteByPrimaryKey(String id){
    	return saOproleMapper.deleteByPrimaryKey(id);
    }
	
	public int insert(SaOprole row){
		return saOproleMapper.insert(row);
	}
	
	public SaOprole selectByPrimaryKey(String id){
		return saOproleMapper.selectByPrimaryKey(id);
	}
	
	public List<SaOprole> selectAll(){
		return saOproleMapper.selectAll();
	}
	
	public int updateByPrimaryKey(SaOprole row){
		return saOproleMapper.updateByPrimaryKey(row);
	}

}
