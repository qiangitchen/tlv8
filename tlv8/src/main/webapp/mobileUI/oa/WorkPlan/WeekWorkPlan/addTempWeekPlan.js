var tD = tlv8.System.Date.strToDate(tlv8.System.Date.sysDate());
var mainData;
var mainid;
function initBody() {
	mainData = new tlv8.Data();
	mainData.setDbkey("oa");
	mainData.setTable("oa_wp_weekplan_detail");
	mainData.setFormId("MAIN_DATA_FORM");
}

$(document).ready(function() {
	initBody();
	var sData1 = getParamValueFromUrl("sData1");
	mainid = getParamValueFromUrl("mainid");
	if (sData1 && sData1 != "") {
		$("#MAIN_DATA_FORM").attr("rowid", sData1);
		mainData.refreshData();
	} else {
		initInsertData();
	}
});

function initInsertData() {
	var type = "临时工作事项";
	var stateTime = getFirstDateOfWeek(tD);
	var stopTime = getLastDateOfWeek(tD);
	mainData.setValueByName("MAINID", mainid);
	mainData.setValueByName("COMPLETECONDITION", "未完成");
	mainData.setValueByName("STARTTIME", stateTime);
	mainData.setValueByName("FINISHTIME", stopTime);
	mainData.setValueByName("FCREATEID", justep.Context.getCurrentPersonID());
	mainData.setValueByName("FCREATENAME", justep.Context
			.getCurrentPersonName());
	mainData.setValueByName("FCREATEDATETIME", tlv8.System.Date
			.sysDateTime());
	mainData.setValueByName("FOGNID", justep.Context.getCurrentOgnID());
	mainData.setValueByName("FOGNNAME", justep.Context.getCurrentOgnName());
	mainData.setValueByName("FORGID", justep.Context.getCurrentOrgID());
	mainData.setValueByName("FORGNAME", justep.Context.getCurrentOrgName());
	mainData.setValueByName("SORT", 1);
	mainData.setValueByName("PLANTYPE", type);
}

function afRefresh() {
	// 数据刷新之后处理
}

function doDataSaveaAtion() {
	var callback = getParamValueFromUrl("callback");
	if (mainData.saveData()) {
		justep.dialog.ensureDialog(callback);
	}
}

function windowCancel() {
	justep.dialog.closeDialog();
}