//工具条
//var toolbarItem;
var currentRowID = null;
var callTabID = "";
var data = new tlv8.Data();
data.setDbkey("system");
data.setFormId("WorkLog");
data.setTable("sa_worklog");
// 基本信息加载
function Init() {
	var bardiv = document.getElementById("stander_bar");
	//var toobar = new tlv8.toolbar(bardiv, false, true, "readonly", "readonly");
//	toolbarItem = toobar.items;
	var Fname = tlv8.Context.getCurrentPersonFName();
	var floginName = tlv8.Context.getCurrentPersonName();
	var rowid = tlv8.RequestURLParam.getParam("rowid");
	callTabID = tlv8.RequestURLParam.getParam("ctbid");
	var opentype = tlv8.RequestURLParam.getParam("opentype");
	if (rowid != "" && rowid) {
		currentRowID = rowid;
		J$("WorkLog").rowid = rowid;
		J$("WorkLog").setAttribute("rowid", rowid);
		$("#WorkLog").attr("rowid", rowid);
		if (opentype == "query") {
			data.setReadonly(true);
		} else {
//			toolbarItem.setItemStatus("no", true, true, "no");
		}
		data.refreshData("");
	} else {
		var DateTime = tlv8.System.Date.sysDateTime();
		var key = "JOURNAL" + DateTime.split("-").join("").substring(0, 8);
		var Code = autosetCode(key, "0000");
		$("#SCREATORFID").val(tlv8.Context.getCurrentPersonFID());
		$("#SCREATOFNAME").val(Fname);
		$("#SCREATETIME").val(DateTime);
		$("#SLOCK").val(Code);
	}
}

function autosetCode(key, formart) {
	var result = "";
	var param = new tlv8.RequestParam();
	param.set("Key", key);
	var r = tlv8
			.XMLHttpRequest("autoSetCodeAction", param, "post", false, null);
	var value = r.value.toString();
	if (formart == null || formart == "" || formart == "undefined") {
		formart = "0";
	}
	var fl = formart.length;
	var vl = value.length;
	if (vl >= fl) {
		result = value;
	} else {
		var c = fl - vl;
		for (var i = 0; i < fl - vl; i++) {
			result = result + "0";
		}
		result = result + value;
	}
	return key + result;
}

function callBackFn() {
	tlv8.portal.callBack(callTabID, "callBackFn", null);
}
function savedata() {
//	var title = $("#SNAME").val();
//	if (title == "") {
//		$.messager.alert('警告', "标题必须填写", 'warning');
//		$("#SNAME").focus();
//		return false;
//	}
	data.saveData();
	callBackFn();
	tlv8.portal.closeWindow();
}

function deleteMaindata() {
	if (data.deleteData()) {
		callBackFn();
		tlv8.portal.closeWindow();
	}
}