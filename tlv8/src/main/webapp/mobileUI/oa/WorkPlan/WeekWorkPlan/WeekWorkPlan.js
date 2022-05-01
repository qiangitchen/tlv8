var tD = tlv8.System.Date.strToDate(tlv8.System.Date.sysDate());
var mainData, rowid;
var isCheckDate = false;
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
	if (sData1 && sData1 != "") {
		$("#DATA_FORM").attr("rowid", sData1);
		mainData.refreshData();
	} else {
		sData1 = new UUID().toString();
		$("#DATA_FORM").attr("rowid", sData1);
		isCheckDate = true;
		initInsertData();
		afRefresh();
	}
	rowid = sData1;
});

function initInsertData() {
	mainData.setValueByName("FCREATEID", justep.Context.getCurrentPersonID());
	mainData.setValueByName("FORGNAME", justep.Context.getCurrentDeptName());
	mainData.setValueByName("STARTTIME", getFirstDateOfWeek(tD));
	mainData.setValueByName("FINISHTIME", getLastDateOfWeek(tD));
	mainData
			.setValueByName("PLANPERSON", justep.Context.getCurrentPersonName());
	mainData.setValueByName("FCREATENAME", justep.Context
			.getCurrentPersonName());
	mainData.setValueByName("FCREATEDATETIME", tlv8.System.Date
			.sysDateTime());
}

function afRefresh() {
	// $("#title_val").html($("#FTITLE").val());
	var vdate = mainData.getValueByName("STARTTIME");
	var createid = mainData.getValueByName("FCREATEID");
	refreshBrowse(sData1);
	viewPersonlist(sData1, true);
	initDetailLink(sData1, vdate, createid);
	getCount(sData1, vdate, createid);
}

function dataSave() {
	return mainData.saveData();
}

function initDetailLink(rowid, vdate, createid) {
	var a = $(".showDetailInfo");
	var tmp = new Date().getTime();
	for (var i = 0; i < a.length; i++) {
		var htmla = a[i];
		var link = htmla.href;
		link += "?rowid=" + rowid + "&creatorid=" + createid + "&vdate="
				+ vdate + "&tmp=" + tmp;
		if (isCheckDate) {
			link += "&option=new";
		} else {
			link += "&option=edit";
		}
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

// 发布
function pushData() {
	if (dataSave()) {
		var count = checkHavePersons(rowid);
		if (count > 0) {
			mainData.setValueByName("FPUSHDATETIME", tlv8.System.Date
					.sysDateTime());
			mainData.setValueByName("FPUSHID", justep.Context
					.getCurrentPersonID());
			mainData.setValueByName("FPUSHNAME", justep.Context
					.getCurrentPersonName());
			mainData.saveData();
		} else {
			alert("请先选择范围后再发布");
		}
	}
}