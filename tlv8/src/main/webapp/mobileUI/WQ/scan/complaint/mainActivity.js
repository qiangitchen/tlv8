$(document).bind("pagebeforecreate", function(event, data) {
});

var mainData = new tlv8.Data();
mainData.setDbkey("wq");
mainData.setTable("WQ_COMPLAINT");
// 页面初始化
var option = getParamValueFromUrl("option");
function initPage() {
	mainData.setFormId("complaint_form");
	var rowid = getParamValueFromUrl("sData1");
	if (rowid && rowid != "") {
		$("#complaint_form").attr("rowid", rowid);
		mainData.refreshData();
		if (option && option == "view") {
//			$("#addphoto").hide();
			$("#submitItem").hide();
//			$("#viewphoto").show();
			setReadonly();
		}
	} else {
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
		try {
			var location = justepYnApp.getLocatin();
			var locobj;
			if (typeof location == "string") {
				locobj = window.eval("(" + location + ")");
			}
			$("#FADDRESS").val(locobj.addr);
		} catch (e) {
		}
	}
}

// 保存
function saveData(goback) {
if($("#FTITLE").val()==""){
		alert("请填标题");
		return;
	}
	if($("#FCOMPLAINTOPINION").val()==""){
		alert("请填写内容");
		return;
	}


	var rowid = mainData.saveData();
	$("#complaint_form").attr("rowid", rowid);
	//if (goback) {
		//history.go(-1);
		var url = "../scanindex.html";
		window.open(url, '_self');
	//}
	return true;
}

// 是这所有字段为只读
function setReadonly() {
	$("#FADDRESS").attr("readonly", "readonly");
}

function photograph() {
	var rowid = mainData.saveData();
	$("#complaint_form").attr("rowid", rowid);
//	if (saveData()) {
//		var rowid = $("#complaint_form").attr("rowid");
		var url = "camera.html?rowid=" + rowid;
		window.open(url, '_self');
//	}
}
/**
 * 扫描条码
 */
function scanBarCode() {
	try {
		var code = justepYnApp.scanBarCode("scanBarCodeCalback");
	} catch (e) {
	}
}
// 扫描条码{回调}
function scanBarCodeCalback(result) {
	var sql="select FNAME from wq_base_barcode where FBARCODE='"+result+"'";
	var res=tlv8.sqlQueryActionforJson("wq", sql);
	res=res.data;
	if(res.length>0){
		$("#FCOMMODITYNAME").val(res[0].FNAME);
	}


}
function lookphotograph() {
	var rowid = $("#complaint_form").attr("rowid");
	var url = "camera.html?rowid=" + rowid + "&option=view";
	window.open(url, '_self');
}
function backhistory(){
if (option && option == "view") {
		var url = "listActivity.html";
		window.open(url, '_self');
	}else{
		var url = "../scanindex.html";
		window.open(url, '_self');
	}
}


