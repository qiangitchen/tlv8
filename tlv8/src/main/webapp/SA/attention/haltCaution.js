var data = new tlv8.Data();
data.setTable("SA_HALTCAUTION");
data.setDbkey("system");

$(document).ready(function() {
	data.setFormId("halt_caution_form");
	document.getElementById("halt_caution_form").rowid = "";
	document.getElementById("halt_caution_form").reset();
	initCutData();
});

function initCutData() {
	data.setValueByName("SCREATERID", tlv8.Context.getCurrentPersonID());
	data.setValueByName("SCREATERNAME", tlv8.Context
			.getCurrentPersonName());
	data.setValueByName("SCREATETIME", tlv8.System.Date.sysDateTime());
	data.setValueByName("SISPUSHED", 0);
}

/*
 * 确定发布
 */
function enginAndPlush() {
	var SSTARTTIME = data.getValueByName("SSTARTTIME");
	var SENDTIME = data.getValueByName("SENDTIME");
	var SATTENTION = data.getValueByName("SATTENTION");
	var STITLE = data.getValueByName("STITLE");
	var sTitles = "";
	if (SSTARTTIME) {
		if (SENDTIME) {
			SSTARTTIMEd = tlv8.System.Date.strToDate(SSTARTTIME);
			SENDTIMEd = tlv8.System.Date.strToDate(SENDTIME);
			if (SSTARTTIMEd.getTime() > SENDTIMEd.getTime()) {
				alert("开始时间不能大于结束时间!");
				return;
			}
			sTitles = "系统将在" + SSTARTTIME + "到" + SENDTIME + "期间停机维护，请您注意保存数据。";
		} else {
			alert("结束时间不能为空!");
			return;
		}
	} else if (SATTENTION && SATTENTION != "") {
		if (Number(SATTENTION) < 1) {
			alert("紧急提醒时间不能小于1分钟");
			return;
		}
		sTitles = "系统将在" + SATTENTION + "分钟之后停机维护，请您注意保存数据。";
	} else {
		alert("开始时间和紧急提醒时间不能同时为空!");
		return;
	}
	if (!STITLE || STITLE == "") {
		data.setValueByName("STITLE", sTitles);
	}
	document.getElementById("halt_caution_form").rowid = data.saveData();
	if (confirm("发布成功，是否关闭页面?")) {
		tlv8.portal.closeWindow();
	}
}

/*
 * 取消
 */
function cancell() {
	var rowid = document.getElementById("halt_caution_form").rowid;
	if (rowid && rowid != "") {
		data.deleteData();
	}
	tlv8.portal.closeWindow();
}