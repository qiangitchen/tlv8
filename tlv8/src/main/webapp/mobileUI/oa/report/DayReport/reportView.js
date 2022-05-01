var mainData;
function initBody() {
	mainData = new tlv8.Data();
	mainData.setDbkey("oa");
	mainData.setTable("OA_RE_DAYREPORT");
	mainData.setFormId("DATA_FORM");
}
var sData1 = "";
$(document).ready(function() {
	initBody();
	sData1 = getParamValueFromUrl("sData1");
	$("#DATA_FORM").attr("rowid", sData1);
	mainData.refreshData();
	var attfile = new tlv8.fileComponent(J$("_content"),
			mainData, "FFILE");
});

function afRefresh() {
	refreshBrowse(sData1, "false");
	showSelectPersonInfo(sData1);
}
