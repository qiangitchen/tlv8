var mainData;
function initBody() {
	mainData = new tlv8.Data();
	mainData.setDbkey("oa");
	mainData.setTable("OA_RE_MONTHREPORT");
	mainData.setFormId("MAIN_DATA_FORM");
	var sData1 = getParamValueFromUrl("sData1");
	if (sData1 && sData1 != "") {
		$("#MAIN_DATA_FORM").attr("rowid", sData1);
		J$("MAIN_DATA_FORM").rowid = sData1;
		mainData.setRowId(sData1);
		mainData.setFilter("");
		mainData.refreshData();
		showSelectPersonInfo(sData1);
	} else {
		setCreatorInfo();
	}
	var attfile = new tlv8.fileComponent(J$("FATTACHMENT_showdiv"),
			mainData, "FFILE", "/root/报表管理/日报/" + getCurentYearandMonth(),
			true, true);
}

$(document).ready(function() {
	initBody();
});

function afRefresh() {
	// 数据刷新之后处理
}

function checkSaveBefore(event){
	var ftitle = $("#FTITLE").val();
	if (trim(ftitle).length == 0) {
		alert("标题不能为空！");
		$("#FTITLE").focus();
		return false;
	}
}

function doDataSaveaAtion() {
	return mainData.saveData();
}

function selectPerson() {
	if(!doDataSaveaAtion()){
		return;
	}
	justep.dialog.openFullScreenDialog("选择人员", {
		url : cpath+"/mobileUI/oa/common/dialog/psnSelect.html",
		callback : "selectPersonCallback"
	});

}

function selectPersonCallback(data) {
	if (data.id == "") {
		alert("未选中人员！");
	} else {
		var rowid = mainData.rowid;
		var param = new tlv8.RequestParam();
		param.set("sdata1", rowid);
		param.set("fids", data.id);
		param.set("names", data.name);
		var result = tlv8.XMLHttpRequest("insertNoticePersonAction?temp="
				+ new Date().getMilliseconds(), param);
		if (result.data.flag == "true") {
			showSelectPersonInfo(rowid);
		} else {
			alert("新增人员失败!");
		}
	}
}

// 发布
function pushData() {
	if (doDataSaveaAtion()) {
		mainData.setValueByName("FPUSHDATETIME", tlv8.System.Date
				.sysDateTime());
		mainData.saveData();
	}
}

// 取消发布
function unpushData(id) {
	mainData.setValueByName("FPUSHDATETIME", "");
	mainData.saveData();
}