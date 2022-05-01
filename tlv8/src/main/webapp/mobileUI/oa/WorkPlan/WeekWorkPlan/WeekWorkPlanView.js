var mainData;
function initBody() {
	mainData = new tlv8.Data();
	mainData.setDbkey("oa");
	mainData.setTable("OA_WP_WeekPlan");
	mainData.setFormId("DATA_FORM");
}

var sData1 = "";
$(document).ready(function() {
	initBody();
	sData1 = getParamValueFromUrl("rowid");
	$("#DATA_FORM").attr("rowid", sData1);
	mainData.refreshData();
});

function afRefresh() {
	$("#title_val").html($("#FTITLE").val());
	var vdate = mainData.getValueByName("STARTTIME");
	var createid = mainData.getValueByName("FCREATEID");
	refreshBrowse(sData1);
	viewPersonlist(sData1);
	initDetailLink(sData1, vdate, createid);
	getCount(sData1, vdate, createid);
}

function initDetailLink(rowid, vdate, createid) {
	var a = $(".showDetailInfo");
	var tmp = new Date().getTime();
	for (var i = 0; i < a.length; i++) {
		var htmla = a[i];
		var link = htmla.href;
		link += "?rowid=" + rowid + "&creatorid=" + createid + "&vdate="
				+ vdate + "&tmp=" + tmp;
		htmla.href = link;
	}
}
function getCount(rowid, vdate, createid) {
	var param = new tlv8.RequestParam();
	param.set("rowid", rowid);
	param.set("vdate", vdate);
	param.set("creatorid", createid);
	var re = tlv8.XMLHttpRequest("getWeekWorkPlanDetailCountAction",
			param, "post");
	if (re.data.flag == "true") {
		$("#ls_count").html(re.ls_count);
		$("#last_count").html(re.last_count);
		$("#this_count").html(re.this_count);
	}
}

function newOpinion() {
	var url = cpath+"/mobileUI/oa/WorkPlan/comon/dialog/opinion.html?rowid="
			+ sData1;
	window.open(url, "_self");
}