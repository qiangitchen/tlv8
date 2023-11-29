package com.tlv8.oa.controller;

import com.tlv8.oa.pojo.OaTest1;
import com.tlv8.oa.service.OaTest1Service;

import com.tlv8.common.domain.AjaxResult;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
* Created by TLv8 IDE on 2023/11/29.
*/
@Controller
@RequestMapping("/oaTest1")
public class OaTest1Controller {
    @Autowired
    private OaTest1Service oaTest1Service;

	@ResponseBody
    @RequestMapping("/deleteByPrimaryKey")
	public Object deleteByPrimaryKey(String id){
		return AjaxResult.success(oaTest1Service.deleteByPrimaryKey(id));
	}
	
	@ResponseBody
    @RequestMapping("/insert")
	public Object insert(OaTest1 row){
		return AjaxResult.success(oaTest1Service.insert(row));
	}
	
	@ResponseBody
    @RequestMapping("/selectByPrimaryKey")
	public Object selectByPrimaryKey(String id){
		return AjaxResult.success(oaTest1Service.selectByPrimaryKey(id));
	}
	
	@ResponseBody
    @RequestMapping("/selectAll")
	public Object selectAll(){
		return AjaxResult.success(oaTest1Service.selectAll());
	}
	
	@ResponseBody
    @RequestMapping("/updateByPrimaryKey")
	public Object updateByPrimaryKey(OaTest1 row){
		return AjaxResult.success(oaTest1Service.updateByPrimaryKey(row));
	}

}
