var data = new tlv8.Data();
data.setTable("bo_comment");
data.setDbkey("system");
data.setFormId("bo_comment");// 设置提交的表单
var xheditor=null;
var prams={};//回复人,回复时间,回复内容
$(document).ready(function(){
	var rowid = tlv8.RequestURLParam.getParam("rowid");
	var title=tlv8.RequestURLParam.getParam("title");
	//初始化编辑器
	xheditor=$("#CONTENT").xheditor({tools:'simple',﻿﻿﻿﻿upImgUrl:"xhUpload",upImgExt:"jpg,jpeg,gif,png"});
	$("#ENTRY_TITLE").val(title);
	$("#CREATED_TIME").val(tlv8.System.Date.sysDateTime()) ;
	$("#AUTHOR").val(tlv8.Context.getCurrentPersonName());
	$("#ENTRY_ID").val(rowid);
	
	
});
function dailogEngin(){
	     var author=$("#AUTHOR").val();
	     var created_time=$("#CREATED_TIME").val();
	     var content=$("#CONTENT").val();
	    prams={
		"author":author,
		"created_time":created_time,
		"content":content
	           };
	    var content=xheditor.getSource();
	    $("#CONTENT").val(content);
	    var saveid =data.saveData();
	    document.getElementById("bo_comment").rowid = saveid;
	    return prams;
	   
	      
         }
