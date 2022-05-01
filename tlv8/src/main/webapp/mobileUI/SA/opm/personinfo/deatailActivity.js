var mainData;
function initBody() {
	mainData = new tlv8.Data();
	mainData.setDbkey("system");
	mainData.setTable("sa_opperson");
	mainData.setFormId("MAIN_DATA_FORM");
}

$(document).ready(function() {
	initBody();
	//var sData1 = getParamValueFromUrl("sData1");
	var rowid = justep.Context.getCurrentPersonID();
	$("#MAIN_DATA_FORM").attr("rowid", rowid);
	mainData.setRowId(rowid);
	mainData.setFilter("");
	mainData.refreshData();
});

function afRefresh() {
	// 数据刷新之后处理
}

function doDataSaveaAtion() {
	mainData.saveData();
}