var mainData;
function initBody() {
	mainData = new tlv8.Data();
	mainData.setDbkey("oa");
	mainData.setTable("OA_WORK_PLAN_PERSON");
	mainData.setFormId("DATA_FORM");
	mainData.setFilter("FMAINID = '" + sData1 + "' and FPERSONID='"
			+ justep.Context.getCurrentPersonID() + "'");
	mainData.refreshData();
}
var sData1 = "";
$(document).ready(function() {
	sData1 = getParamValueFromUrl("rowid");
	initBody();
});
function ensure() {
	try {
		var rowid = mainData.rowid;
		var op = $("#FOPINION").val();
		if (rowid == "" || rowid == null) {
			alert("数据读取错误,请重试");
			return false;
		}
		javascript: history.go(-1);
	} catch (e) {
		alert("保存意见出现错误，请重试");
	}
}

function afRefresh() {
	var rowid = mainData.rowid;
	$("#DATA_FORM").attr("rowid", rowid);
}