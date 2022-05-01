var currentgrid="";
var name;var sid;
var data=new tlv8.Data();
data.setDbkey("system");
data.setTable("sa_opperson");
var $maingridview;
var prams={};
$(document).ready(function(){
	$maingridview=$("#gridmainView");
	var labelid = "No,SCODE,SNAME,SSEX";
	var labels = "No.,编号,姓名,性别";
	var labelwidth = "40,160,80,60";
	var datatype = "ro,string,string,string";//设置字段类型
	var dataAction = {
		"queryAction" : "getPersonInfo",
		"savAction" : "savePersonInfo",
		"deleteAction" : "deletePersonInfo"
	};
	//创建grid
	var gridmain=new tlv8.createGrid($maingridview[0], labelid, labels, labelwidth,
		dataAction, "100%", "200", data, 20, "", "", "", datatype, "false","true");
	gridmain.grid.settoolbar(false, false, true, false);
	//$("#"+$maingridview[0].id+"_toolbar").css("display","none");
	//设置是否可编辑
	gridmain.grid.seteditModel(false);
	currentgrid = gridmain.grid;
	currentgrid.refreshData();

});
function selectedItem(event){
	
	prams={};
	var rowid=currentgrid.getCurrentRowId();
	name=currentgrid.getValueByName("SNAME",rowid);
	sid=rowid
	prams={
		"name":name,
		"sid":sid
	};
}
function dailogEngin(){
	if(prams.sid&&prams.name){
		return prams;
	}else{
		mAlert('请选择数据！！');
	}
}


