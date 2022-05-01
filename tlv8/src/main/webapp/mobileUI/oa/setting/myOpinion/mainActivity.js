var mainData;
function initBody() {
	mainData = new tlv8.Data();
	mainData.setDbkey("oa");
	mainData.setTable("oa_flowconclusion");
	mainData.setFormId("MAIN_DATA_FORM");
}

$(document).ready(function() {
	initBody();
	var sData1 = getParamValueFromUrl("sData1");
	if(sData1&&sData1!=""){
		$("#MAIN_DATA_FORM").attr("rowid", sData1);
		mainData.setRowId(sData1);
		mainData.setFilter("");
		mainData.refreshData();
	}else{
		$("#FCREATORID").val(justep.Context.getCurrentPersonID());
		$("#FCREATOR").val(justep.Context.getCurrentPersonName());
	}
});

function afRefresh() {
	// 数据刷新之后处理
}

function doDataSaveaAtion() {
	var rowid = mainData.saveData();
	$("#MAIN_DATA_FORM").attr("rowid", rowid);
	mainData.setRowId(rowid);
}