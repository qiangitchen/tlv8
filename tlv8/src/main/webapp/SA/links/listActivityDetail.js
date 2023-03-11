//工具条
var toolbarItem;
function init_toolbar() {
	var bardiv = $("#stander-bar")[0];
	var toobar = new tlv8.toolbar(bardiv, false, true, false, true);
	toolbarItem = toobar.items;
}

// 数据配置
var datamian;
function initDocumentPage() {
	datamian = new tlv8.Data();
	datamian.setDbkey("system");
	datamian.setTable("SA_LINKS");
	datamian.setFormId("MAIN_DATA_FORM");
	var rowid = tlv8.RequestURLParam.getParam("rowid");
	J$("MAIN_DATA_FORM").rowid = rowid;
	J$("MAIN_DATA_FORM").setAttribute("rowid", rowid);
	dataRefresh();
}

// 数据保存
function dataSave() {
	datamian.setValueByName("SCREATID", tlv8.Context.getCurrentPersonID());
	datamian.setValueByName("SCREATER", tlv8.Context
			.getCurrentPersonName());
	datamian.setValueByName("SCREATEDATE", tlv8.System.Date.sysDateTime());
	datamian.saveData();
}

// 数据刷新
function dataRefresh() {
	datamian.refreshData();
}

// 数据删除
function dataDeleted() {
	if (datamian.deleteData()) {
		dataRefresh();
	}
}