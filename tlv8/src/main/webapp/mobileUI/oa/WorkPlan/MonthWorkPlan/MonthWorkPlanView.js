var mainData;
function initBody() {
	mainData = new tlv8.Data();
	mainData.setDbkey("oa");
	mainData.setTable("OA_WP_MONTHPLAN");
	mainData.setFormId("DATA_FORM");
}
var sData1 = "";
$(document).ready(function() {
	initBody();
	sData1 = getParamValueFromUrl("rowid");
	$("#DATA_FORM").attr("rowid", sData1);
	mainData.refreshData();
	window.setTimeout(function() {
		refreshBrowse(sData1);
		viewPersonlist(sData1);
		initDetailLink(sData1);
		getCount(sData1);
	}, 10);
});

function afRefresh() {
	$("#title_val").html($("#FTITLE").val());
}

function initDetailLink(rowid) {
	var a = $(".showDetailInfo")[0];
	var tmp = new Date().getTime();
	var link = a.href;
	link += "?rowid=" + rowid + "&tmp=" + tmp;
	a.href = link;
}
function getCount(rowid) {
	var param = new tlv8.RequestParam();
	param.set("rowid", rowid);
	var re = tlv8.XMLHttpRequest("getMonthWorkPlanDetailCountAction",
			param, "post");
	if (re.data.flag == "true") {
		$("#count").html(re.count);
	}
}

function newOpinion() {
	var url = cpath+"/mobileUI/oa/WorkPlan/comon/dialog/opinion.html?rowid="
			+ sData1;
	window.open(url, "_self");
}