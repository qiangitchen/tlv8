$(document).bind("pagebeforecreate", function(event, data) {
//	initCustomer();
//	$('input:jqmData(role="datebox")').mobiscroll().date();

});



var mainData = new tlv8.Data();
mainData.setDbkey("wq");
mainData.setTable("WQ_CUSTOMR_BASEINFO");
// 页面初始化
var option = getParamValueFromUrl("option");
function initPage() {
	mainData.setFormId("costomer_form");
	var rowid = getParamValueFromUrl("sData1");
	if (rowid && rowid != "") {
		$("#costomer_form").attr("rowid", rowid);
		mainData.refreshData();
		if (option && option == "view") {
//			$("#submitItem").hide();
//			$("#addrs").hide();
		}
	} else {
		mainData.refreshData();
		$("#FSTATUS").val("1");
		$("#FCREATORID").val(justep.Context.getCurrentPersonID());
		$("#FCREATOR").val(justep.Context.getCurrentPersonName());
		$("#FCREATEDATE").val(tlv8.System.Date.sysDateTime());
		
		$("#FUPDATORID").val(justep.Context.getCurrentPersonID());
		$("#FUPDATOR").val(justep.Context.getCurrentPersonName());
		$("#FUPDATEDATE").val(tlv8.System.Date.sysDateTime());
		
	}
}

// 保存
function saveData(goback) {
	if ($("#FCUSTOMERNAME").val() == "") {
		alert("客户名称不能为空!");
		return false;
	}
	var rowid = mainData.saveData();
	$("#costomer_form").attr("rowid", rowid);
	if (goback) {
		history.go(-1);
	}
	return true;
}

//获取客户地址
function getAddr(){
	try {
		var location = justepYnApp.getLocatin();
		var locobj;
		if (typeof location == "string") {
			locobj = window.eval("(" + location + ")");
		}
		var lng=locobj.lontitude;
		var lat=locobj.latitude;
		$("#FCUSTOMERADDR").val(locobj.addr);
		$("#FCUSTOMERMAP").val("{lng:\""+lng+"\",lat:\""+lat+"\"}");
	} catch (e) {
	}
}

// 是这所有字段为只读
function setReadonly() {
//	$("#FCUSTOMER").attr("readonly", "readonly");
//	$("#FVISITPERSON").attr("readonly", "readonly");
//	$("#FVISITTIME").attr("readonly", "readonly");
//	$("#FVISITRESOURCE").attr("readonly", "readonly");
//	$("#FRESUINFO").attr("readonly", "readonly");
}

function photograph() {
	if (saveData()) {
		var rowid = $("#costomer_form").attr("rowid");
		var url = "../Camera/mainActivity.html?rowid=" + rowid;
		window.open(url, '_self');
	}
}

function lookphotograph() {
	var rowid = $("#costomer_form").attr("rowid");
	var url = "../Camera/mainActivity.html?rowid=" + rowid + "&option=view";
	window.open(url, '_self');
}

function backhistory(){
	if (option && option == "view") {
		var url = "custormerInfo_list.html";
		window.open(url, '_self');
	}else{
		var url = "custormerindex.html";
		window.open(url, '_self');
	}
}
