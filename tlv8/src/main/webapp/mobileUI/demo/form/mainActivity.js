var mainData;
function initBody() {
	mainData = new tlv8.Data();
	mainData.setDbkey("oa");
	mainData.setTable("OA_DC_RECVDOC");
	mainData.setFormId("OA_DC_DOC_FORM");
}

$(document).ready(function() {
	initBody();
	var sData1 = getParamValueFromUrl("sData1");
	$("#OA_DC_DOC_FORM").attr("rowid", sData1);
	mainData.refreshData();
});

function afRefresh() {
	// 数据刷新之后处理
}


function doDataSaveaAtion(){
	mainData.saveData();
}
