function init_toolbar() {
	var bardiv = J$("stander_bar");
	new tlv8.toolbar(bardiv, false, true, false, true);
}
// 数据配置
var datamian;
var rowid = "";
function initDocumentPage() {
	datamian = new tlv8.Data();
	datamian.setDbkey("oa");
	datamian.setTable("OA_RE_WEEKREPORT");
	datamian.setFormId("MAIN_DATA_FORM");
	rowid = tlv8.RequestURLParam.getParam("rowid");
	readonly = tlv8.RequestURLParam.getParam("readonly");
	if (readonly == null || readonly == "") {
		if (rowid == "" || rowid == null) {
			dataInsert();
			setCreatorInfo();
		} else {
			document.getElementById("MAIN_DATA_FORM").rowid = rowid;
			J$("MAIN_DATA_FORM").setAttribute("rowid", rowid);
			datamian.refreshData();
		}
		new tlv8.fileComponent(document.getElementById("fileCompDiv"),
				datamian, "FFILE", "/root/报表管理/周报/" + getCurentYearandMonth());
		changePushState();
		showSelectPersonInfo(rowid);
		init_toolbar();
	} else {
		if (rowid == "" || rowid == null) {
			alert("数据传输错误,请关闭此页面后，重新打开.");
		} else {
			document.getElementById("MAIN_DATA_FORM").rowid = rowid;
			J$("MAIN_DATA_FORM").setAttribute("rowid", rowid);
			datamian.refreshData();
			new tlv8.fileComponent(document.getElementById("fileCompDiv"),
					datamian, "FFILE", "/root/报表管理/周报/"
							+ getCurentYearandMonth(), false, false, false,
					false, false);
			$("#newperson_btn").hide();
			$("#toolbar").hide();
			updateLookState(rowid, "false");
			showSelectPersonInfo(rowid, true);
			datamian.setReadonly(true);
		}
	}
}

// 新增数据
function dataInsert() {
	J$("MAIN_DATA_FORM").reset();
	J$("MAIN_DATA_FORM").rowid = "";
	J$("MAIN_DATA_FORM").setAttribute("rowid", "");
	$("#MAIN_DATA_FORM").attr("rowid", "");
}

// 数据保存
function dataSave() {
	var ftitle = $("#FTITLE").val();
	if (strTrim(ftitle).length == 0) {
		alert("标题不能为空！");
		$("#FTITLE").focus();
		return false;
	}
	rowid = datamian.saveData();
	J$("MAIN_DATA_FORM").rowid = rowid;
	J$("MAIN_DATA_FORM").setAttribute("rowid", rowid);
	$("#MAIN_DATA_FORM").attr("rowid", rowid);
	tlv8.portal.callBack(tlv8.RequestURLParam.getParam("tabID"),
			"defRefreshData");
	return true;
}

// 数据刷新
function dataRefresh() {
	datamian.refreshData();
}

function addPerson() {
	if (dataSave()) {
		openSelectPersonDalog(initPersonlist);
	}
}

function initPersonlist(data) {
	if(data.id==""){
		alert("未选中人员！");
	}else{
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

function delPerson(fid) {
	if (confirm("你确定要删除当前选择人员吗?")) {
		var param = new tlv8.RequestParam();
		param.set("fid", fid);
		var result = tlv8.XMLHttpRequest("deleteNoticePersonAction?temp="
				+ new Date().getMilliseconds(), param);
		if (result.data.flag == "true") {
			showSelectPersonInfo(rowid);
		} else {
			alert("删除人员失败！");
		}
	}
}

// 改变发布状态
function changePushState() {
	var pushtime = datamian.getValueByName("FPUSHDATETIME");
	var urlLink = "<table style=' height: 22px; text-align: left;'><tr><td width='53px'>";
	if (pushtime == null || pushtime == "") {
		urlLink += "<a class='toobar_item' href='javascript:void(0);' class=\"toobar_item\" onclick=\"pushData();\" title=\"发布\"><img src=\"../../../comon/image/toolbar/release.gif\" height=\"22px\"/></a>";
	} else {
		urlLink += "<a class='toobar_item' href='javascript:void(0);' class=\"toobar_item\" onclick=\"unpushData();\" title=\"取消发布\"><img src=\"../../../comon/image/toolbar/cancel_release.gif\" height=\"22px\"/></a>";
	}
	urlLink += "</td></tr></table>";
	$("#push").html(urlLink);
}
// 发布
function pushData() {
	if (dataSave()) {
		datamian.setValueByName("FPUSHDATETIME", tlv8.System.Date
				.sysDateTime());
		datamian.saveData();
		changePushState();
		tlv8.portal.callBack(tlv8.RequestURLParam.getParam("tabID"),
				"defRefreshData", "ok");
	}
}

// 取消发布
function unpushData(id) {
	datamian.setValueByName("FPUSHDATETIME", "");
	datamian.saveData();
	changePushState();
	tlv8.portal.callBack(tlv8.RequestURLParam.getParam("tabID"),
			"defRefreshData", "ok");
}
