var currentgrid="";
var data=new tlv8.Data();
data.setDbkey("system");
data.setTable("bo_entry");
var $maingridview;
var gridrowid;
var name;
$(document).ready(function(){
	name=tlv8.RequestURLParam.getParam("name");//讨论标题
	$maingridview=$("#gridmainView");
	var labelid = "No,TITLE,CONTENT,CATEGORY_NAME,CREATED_TIME,ONESELF,STATUS";
	var labels = "No.,标题,内容,分类,发表时间,发贴人,锁定状态";
	var labelwidth = "40,220,0,200,150,120,80";
	var datatype = "ro,html:reader,ro,ro,ro,ro,html:getStatus";//设置字段类型
	var dataAction = {
		"queryAction" : "getPersonInfo",
		"savAction" : "savePersonInfo",
		"deleteAction" : "deletePersonInfo"
	};
	//创建grid
	var gridmain=new tlv8.createGrid($maingridview[0], labelid, labels, labelwidth,
		dataAction, "100%", "100%", data, 20, "", "", "", datatype, "false","true");
	//gridmain.grid.settoolbar(false, false, true, false);
	$("#"+$maingridview[0].id+"_toolbar").css("display","none");//隐藏工具条
	//设置是否可编辑
	gridmain.grid.seteditModel(false);
	currentgrid = gridmain.grid;
	//var filter="BO_BLOGID like '%"+trim(gridrowid)+"%' ";
	gridmain.grid.refreshData();
	//currentgrid.refreshData();
	$("#looks").bind("keydown", function(event){
       if (event.keyCode == 13) 
		lookselect();
    });
});

function getStatus(event){
	if(event.value=="0"){
		return "未锁定";
	}else{
		return "已锁定";
	}
}

//快速回复
function velocity(){
	var rowid=currentgrid.getCurrentRowId();
	tlv8.portal.openWindow("快速回复","/system/News/forum/velocityentry.html?rowid="+rowid+"&title="+name,null);  
}
//发帖
function openview(){
	tlv8.portal.openWindow("创建发帖","/system/News/forum/BO_entryPublish.html?rowid="+ gridrowid+ "&tabID="+tlv8.portal.currentTabId(),null);
}
function callBackFn(){
	$maingridview[0].grid.refreshData();
}

//单击标题查看
function reader(event){
	var html="<a href='javascript:void(0);' onclick='news_griddbclick(this)'>"+event.value+"</a>";
		 return html;
}

function news_griddbclick(obj){
	setTimeout('QueryDetailAction()', 10);	
}

function QueryDetailAction(){
	var rowid=currentgrid.getCurrentRowId();
	tlv8.portal.openWindow("帖子浏览","/system/News/forum/velocityentry.html?rowid="+rowid+"&title="+name,null);
}

//根据标题查询
function lookselect(){
	var text=$("#looks").val();
	var filter="TITLE like '%"+trim(text)+"%' ";
	currentgrid.refreshData(filter);
}