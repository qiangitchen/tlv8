//工具条
var toolbarItem;
function init_toolbar() {
	var bardiv = J$("stander_bar");
	var toobar = new tlv8.toolbar(bardiv, false, "readonly", "readonly",
			true);
	toolbarItem = toobar.items;
}

/*==数据源===此项为必须定义==*/
var currentgrid;
var Maindata = new tlv8.Data();
Maindata.setDbkey("oa");//指定使用数据库连接
Maindata.setTable("oa_leave");//指定grid对应的表
		
/*====*/
var currentgrid;
function getData() {
	Maindata.setFormId("MAIN_DATA_FORM");
	var d = document.getElementById("main_grid_view");
	var labelid = "No,FCREATORNAME,FCREATEDATE,FDAY,FLEAVETYPE";//设置字段
	var labels = "No.,申请人名称,申请时间,请假天数,请假类型";//设置标题
	var labelwidth = "40,100,80,80,80";//设置宽度
	var datatype = "ro,string,datetime,number,string";//设置字段类型
	var dataAction = {
		"queryAction" : "getGridAction",//查询动作
		"savAction" : "saveAction",//保存动作
		"deleteAction" : "deleteAction"//删除动作
	};
	var maingrid = new tlv8.createGrid(d, labelid, labels, labelwidth,
		dataAction, "100%", "100%", Maindata, 20, "", "", "", datatype,
		"false", "true");
	//设置按钮显示{新增、保存、刷新、删除}
	maingrid.grid.settoolbar(false, false, true, false);
	//设置是否可编辑
	maingrid.grid.seteditModel(false);
	currentgrid = maingrid.grid;
	currentgrid.refreshData();//刷新数据
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
	Maindata.saveData();
}

//数据刷新
function dataRefresh(){
	Maindata.refreshData();
}

//数据删除
function dataDeleted(){
	if(Maindata.deleteData()){
		dataRefresh();
	}
}

//选择"列表"事件
function tab_MainForm(){
	
}

//选择"详细"事件
function tab_FileInfo(){
	var rowid = currentgrid.getCurrentRowId();
	J$("MAIN_DATA_FORM").rowid = rowid;
	J$("MAIN_DATA_FORM").setAttribute("rowid", rowid);
	$("#MAIN_DATA_FORM").attr("rowid", rowid);
	Maindata.setFilter("");
	dataRefresh();
}

//双击grid跳到详细
function griddbclick(event){
	$('#tabveiw1').tabs('select', 1);
}

function tabsselected(title, index){
	switch (index) {
	case 0:
		tab_MainForm();
		break;
	case 1:
		tab_FileInfo();
		break;
	default:
		break;
	}
}