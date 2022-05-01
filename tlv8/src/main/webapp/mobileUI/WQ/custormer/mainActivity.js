$(document).bind("pagebeforecreate", function(event, data) {
//	initCustomer();
//	$('input:jqmData(role="datebox")').mobiscroll().date();

});

function initCustomer() {
	var html = "";
	var sql = "select FID,FCUSTOMERNAME from WQ_CUSTOMR_BASEINFO where FSTATUS=1";
	var result=tlv8.sqlQueryActionforJson("wq", sql)
		var data = result.data;
		if (typeof data == "string") {
			data = window.eval("(" + data + ")");
		}
		for (var i = 0; i < data.length; i++) {
			html += "<option value='" + data[i].FCUSTOMERNAME + "'>"
					+ data[i].FCUSTOMERNAME + "</option>";
		}
	$("#FCUSTOMER").html(html);
  }

var mainData = new tlv8.Data();
mainData.setDbkey("wq");
mainData.setTable("WQ_CUSTOMER_VISIT");
// 页面初始化
var option = getParamValueFromUrl("option");
function initPage() {
	mainData.setFormId("visit_costomer_form");
	var rowid = getParamValueFromUrl("rowid");
	if (rowid && rowid != "") {
		$("#visit_costomer_form").attr("rowid", rowid);
		initCustomer();
		mainData.refreshData();
		if (option && option == "view") {
			$("#addphoto").hide();
			$("#submitItem").hide();
			$("#viewphoto").show();
			setReadonly();
		}
	} else {
		mainData.refreshData();
		var deptID = justep.Context.getCurrentDeptID();
		var deptName = justep.Context.getCurrentDeptName();
		if (!deptID || deptID == "") {
			deptID = justep.Context.getCurrentOrgID();
			deptName = justep.Context.getCurrentOrgName();
		}
		$("#FCREATORID").val(justep.Context.getCurrentPersonID());
		$("#FCREATORNAME").val(justep.Context.getCurrentPersonName());
		$("#FCREATORDEPTID").val(deptID);
		$("#FCREATORDEPT").val(deptName);
		$("#FCREATEDATE").val(tlv8.System.Date.sysDateTime());
//		$("#FVISITTIME").val(justep.Context.getCurrentPersonName());
//		$("#FVISITPERSON").val(tlv8.System.Date.sysDateTime());
		initCustomer();
	}
}

// 保存
function saveData(goback) {
	if ($("#FCUSTOMER").val() == "") {
		alert("客户名称不能为空!");
		return false;
	}
	var rowid = mainData.saveData();
	$("#visit_costomer_form").attr("rowid", rowid);
	if (goback) {
		history.go(-1);
	}
	return true;
}

// 是这所有字段为只读
function setReadonly() {
	$("#FCUSTOMER").attr("readonly", "readonly");
	$("#FVISITPERSON").attr("readonly", "readonly");
	$("#FVISITTIME").attr("readonly", "readonly");
	$("#FVISITRESOURCE").attr("readonly", "readonly");
	$("#FRESUINFO").attr("readonly", "readonly");
}

function photograph() {
	if (saveData()) {
		var rowid = $("#visit_costomer_form").attr("rowid");
		var url = "../Camera/mainActivity.html?rowid=" + rowid;
		window.open(url, '_self');
	}
}

function lookphotograph() {
	var rowid = $("#visit_costomer_form").attr("rowid");
	var url = "../Camera/mainActivity.html?rowid=" + rowid + "&option=view";
	window.open(url, '_self');
}

function backhistory(){
	if (option && option == "view") {
		var url = "listActivity.html";
		window.open(url, '_self');
	}else{
		var url = "custormerindex.html";
		window.open(url, '_self');
	}
}
