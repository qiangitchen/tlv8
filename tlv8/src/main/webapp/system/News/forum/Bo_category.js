var currentgrid="";
var data=new tlv8.Data();
data.setDbkey("system");
data.setTable("bo_category");
var $maingridview;
$(document).ready(function(){
	$maingridview=$("#maingridview");	
	var labelid="No,DISPLAY_ORDER,NAME,DESCRIPTION";
	var labels="No.,排序号,分类名称,简介描述";
	var labelwidth="40,120,150,180";
	var datatype = "ro,int,string,textarea";//设置字段类型
	var dataAction = {
		"queryAction" : "getGridAction",
		"savAction" : "saveAction",
		"deleteAction" : "deleteAction"
	};
	//创建grid
	var gridmain=new tlv8.createGrid($maingridview[0], labelid, labels, labelwidth,
		dataAction, "100%", "100%", data, 20, "", "", "", datatype, "false","true");
	//设置toobar显示{新增、保存、刷新、删除}
	gridmain.grid.settoolbar(true, true, true, true);
	//设置是否可编辑
	currentgrid = gridmain.grid;
	gridmain.grid.seteditModel(true);
	currentgrid.setExcelimpBar(false);//导入
	currentgrid.setExcelexpBar(false);//导出
	currentgrid.refreshData();
});