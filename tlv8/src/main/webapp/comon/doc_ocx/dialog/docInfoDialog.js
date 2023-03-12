var gridData, rowid;
var docInfo = new tlv8.Data();
docInfo.setDbkey("system");
docInfo.setTable("SA_DocNode");
var getUrlParam = function(data) {
	gridData = data;
	docInfo.setFormId("docInfo_form");
	docInfo.setFilter("sfileid='" + data + "'");
	docInfo.refreshData();
};

function getDocInfoData() {
	J$("docInfo_form").blur();
	return docInfo.saveData();
}