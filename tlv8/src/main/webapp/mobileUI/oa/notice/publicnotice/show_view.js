var mainData;
function initBody() {
	mainData = new tlv8.Data();
	mainData.setDbkey("oa");
	mainData.setTable("OA_PULICNOTICE");
	mainData.setFormId("DATA_FORM");
}

$(document).ready(function() {
	initBody();
	var sData1 = getParamValueFromUrl("rowid");
	$("#DATA_FORM").attr("rowid", sData1);
	refreshBrowse(sData1);
	mainData.refreshData();
	var attfile = new tlv8.fileComponent(J$("FATTACHMENT_showdiv"),
			mainData, "FATTFILE");
});

function afRefresh() {
	$("._content").html($("#FCONTENT").val());
	// $("#title_val").text($("#FTITLE").val());
}

// 改变查看状态
function refreshBrowse(rowid) {
	var parm = new tlv8.RequestParam();
	parm.set("rowid", rowid);
	parm.set("isnew", "true");
	tlv8.XMLHttpRequest("updateNoticeBrowseAction", parm);
}