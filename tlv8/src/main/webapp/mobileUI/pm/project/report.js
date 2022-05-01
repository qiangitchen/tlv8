var mainData;
function initBody() {
	mainData = new tlv8.Data();
	mainData.setDbkey("pm");
	mainData.setTable("pm_project");
	mainData.setFormId("MAIN_DATA_FORM");
}

$(document).ready(function() {
	initBody();
	var sData1 = getParamValueFromUrl("sData1");
	$("#MAIN_DATA_FORM").attr("rowid", sData1);
	mainData.refreshData();
	$("#barview").attr("src","rssum.echt?p1="+sData1);
	$("#barview2").attr("src","typesum.echt?p1="+sData1);
});

function afRefresh() {
	// 数据刷新之后处理
}

function doDataSaveaAtion() {
	mainData.saveData();
}

function addData(){
	window.open("../cost/deatailActivity.html?projectid="+mainData.rowid,"_self");
}