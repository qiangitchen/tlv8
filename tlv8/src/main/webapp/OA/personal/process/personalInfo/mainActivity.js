//工具条
var toolbarItem;
function init_toolbar() {
	var bardiv = J$("stander_bar");
	var toobar = new justep.yn.toolbar(bardiv, false, true, false,
			true);
	toolbarItem = toobar.items;
}

//数据配置
var datamian;
function initDocumentPage() {
	datamian = new justep.yn.Data();
	datamian.setDbkey("system");
	datamian.setTable("SA_OPPERSON");
	datamian.setFormId("MAIN_DATA_FORM");
	var rowid = justep.yn.Context.getCurrentPersonID();
	J$("MAIN_DATA_FORM").rowid = rowid;
	J$("MAIN_DATA_FORM").setAttribute("rowid", rowid);
	$("#MAIN_DATA_FORM").attr("rowid", rowid);
	datamian.rowid = rowid;
	datamian.refreshData();
	justep.yn.picComponent(J$("picphotoview"),datamian,"SPHOTO",true,true);
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
	var rowid = datamian.saveData();
	J$("MAIN_DATA_FORM").rowid = rowid;
	J$("MAIN_DATA_FORM").setAttribute("rowid", rowid);
	$("#MAIN_DATA_FORM").attr("rowid", rowid);
}

//数据刷新
function dataRefresh(){
	datamian.refreshData();
}

//数据删除
function dataDeleted(){
	if(datamian.deleteData()){
		dataRefresh();
	}
}