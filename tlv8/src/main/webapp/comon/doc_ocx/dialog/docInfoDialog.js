var gridData, rowid;
var docInfo = new tlv8.Data();
docInfo.setDbkey("system");
docInfo.setTable("SA_DocNode");
var getUrlParam = function(data) {
	gridData = data;
	docInfo.setFormId("docInfo_form");
	var r = tlv8.sqlQueryAction("system",
			"select SID from sa_docnode where (sid='" + data + "' or sfileid='"
					+ data + "')");
	if (r.getCount() > 0) {
		var rowid = r.getValueByName("SID");
		J$("docInfo_form").rowid = rowid;
		J$("docInfo_form").setAttribute("rowid", rowid);
		$("#docInfo_form").attr("rowid", rowid);
		docInfo.setFilter("");
		docInfo.refreshData();
	} else {
		//alert("docInfoDialog.js:参数错误!");
	}
};

function getDocInfoData() {
	J$("docInfo_form").blur();
	return docInfo.saveData();
}