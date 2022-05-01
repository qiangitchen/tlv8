var data = new tlv8.Data();
data.setTable("bo_entry");
data.setDbkey("system");
var currentgrid = null;
var xheditor=null;
var callTabID;
var random=Math.random();
var refresh=random+"="+random;// 页面刷新
// 页面加载
$(document).ready(function(){
	data.setFormId("bo_entry");// 设置提交的表单
	// 初始化帖子分类信息
	var gdiv = document.getElementById("CATEGORY_NAME");
	var sql = "select NAME from bo_category";
	new tlv8.GridSelect(gdiv, "system", sql);
	var rowid=tlv8.RequestURLParam.getParam("rowid");
	callTabID= tlv8.RequestURLParam.getParam("tabID");
	// 初始化编辑器
	xheditor=$("#CONTENT").xheditor({tools:"full",skin:"o2007silver",﻿﻿﻿﻿upImgUrl:"xhUpload",upImgExt:"jpg,jpeg,gif,png"});
	$("#CREATED_TIME").val(tlv8.System.Date.sysDateTime()) ;
	$("#ONESELF").val(tlv8.Context.getCurrentPersonName()); 
	$("#BO_BLOGID").val(rowid);
	if(rowid&&rowid!=""){
		//refreshMainData("SID = '" + rowid + "'"+" and "+refresh);
		//document.getElementById("bo_entry").rowid = rowid;
		//data.setFilter("SID='" + rowid + "'");
		data.rowid = rowid;
		data.refreshData();
		// 附件组件数据动态加载
		//var cellname = "ACCESSORIES";// 设置附件相关字段
		//var fileComp = new tlv8.fileComponent(document.getElementById("fileCompDiv"), data, cellname);
		//var sqlstr="select CONTENT from bo_entry where SID='"+rowid+"'";
		//var result=tlv8.sqlQueryAction("system",sqlstr);
		//var result_budget=tlv8.strToXML(result.data);
		//var cells=result_budget.getElementsByTagName('root/rows/row')[0].text;
		//cells=cells.replaceAll("#lt;","<").replaceAll("#gt;",">");
		//document.getElementById("CONTENT").value=cells;		
		// 动态加载栏目信息
		//var gdiv = document.getElementById("CATEGORY_NAME");
		//var sql="select CATEGORY_NAME from bo_entry where "+refresh;
		//var GridSelect = new tlv8.GridSelect(gdiv,"system",sql);
		return ;		
	}
	// 初始化附件组件
	var cellname = "ACCESSORIES";// 设置附件相关字段
	var fileComp = new tlv8.fileComponent(document.getElementById("fileCompDiv"), data, cellname);	
});
function pushSaveDelaitData(){
	var rowid =SaveDelaitData();
	callBackFn();
	tlv8.portal.closeWindow();	
}
function callBackFn() {
	tlv8.portal.callBack(callTabID, "callBackFn", null);
}
function refreshMainData(filter) {
	var maindata = new tlv8.Data();
	maindata.setTable("bo_entry");
	maindata.setFormId("bo_entry");
	maindata.setDbkey("system");
	document.getElementById("bo_entry").rowid = "";// 清空ID
	document.getElementById("bo_entry").reset();// 重置表单
	filter = filter ? filter : "";
	maindata.setFilter(filter);
	maindata.refreshData();
}
// 保存
function SaveDelait(){
	$("#submitdata").click();
//	var title=document.getElementById('TITLE').value;
//	if(title==""){
//		alert("文章标题不能为空");
//		return;
//	}
//	return SaveDelaitData();
}

function SaveDelaitData(){
	var content=xheditor.getSource();
	$("#CONTENT").val(content);
	return data.saveData();
}