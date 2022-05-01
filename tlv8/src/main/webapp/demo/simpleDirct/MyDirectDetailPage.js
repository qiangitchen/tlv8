//工具条
var toolbarItem;
function init_toolbar() {
	var bardiv = J$("stander_bar");
	var toobar = new tlv8.toolbar(bardiv, "readonly", true, "readonly",
			true);
	toolbarItem = toobar.items;
}

/*=======主表配置======*/
var MainData = new tlv8.Data();
function getData() {
	MainData.setDbkey("oa");//指定使用数据库连接
	MainData.setTable("oa_re_dayreport");
	MainData.setFormId("MAIN_DATA_FORM");//主表关联form
	init_toolbar();
	getData2();
}

/*======从表配置======*/
var SubData = new tlv8.Data();
function getData2() {
	SubData.setDbkey("oa");//指定使用数据库连接
	SubData.setTable("oa_re_dayreportdetail");
	var d = document.getElementById("direct_from_subgrid");
	var labelid = "No,FCONTENT,FPLANDATE,FOTHERPERSON";//设置字段
	var labels = "No.,内容,时间,参与";//设置标题
	var labelwidth = "40,80,80,80";//设置宽度
	var datatype = "ro,string,date,string";//设置字段类型
	var dataAction = {
		"queryAction" : "getGridAction",
		"savAction" : "saveAction",
		"deleteAction" : "deleteAction"
	};
	var maingrid = new tlv8.createGrid(d, labelid, labels, labelwidth,
			dataAction, "100%", "100%", SubData, 20, "", "MAIN_DATA_FORM", "FMASTERID", datatype,
			"false", "true");
	maingrid.grid.settoolbar(true, false, true, true);//设置按钮显示{新增、保存、刷新、删除}
	maingrid.grid.seteditModel(true);//设置是否可编辑
	maingrid.grid.refreshData();
}

/*
*从表值改变时改变主表按钮状态
*/
function cheng_stae_bar(){
	toolbarItem.setItemStatus("no", true, "no", "no");//no 代表不改变
}

//新增数据
function dataInsert(){
	J$("MAIN_DATA_FORM").reset();
	J$("MAIN_DATA_FORM").rowid = "";
	J$("MAIN_DATA_FORM").setAttribute("rowid", "");
	$("#MAIN_DATA_FORM").attr("rowid", "");
}

//数据保存
function dataSave() {
	var rowid = MainData.saveData();
	J$("MAIN_DATA_FORM").rowid = rowid;
	J$("MAIN_DATA_FORM").setAttribute("rowid", rowid);
}

//数据刷新
function dataRefresh(){
	MainData.refreshData();
}

//数据删除
function dataDeleted(){
	if(MainData.deleteData()){
		dataRefresh();
	}
}

function bodyResize(){
	getData2();
}