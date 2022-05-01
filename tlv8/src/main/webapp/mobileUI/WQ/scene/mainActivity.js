$(document).bind("pagebeforecreate", function(event, data) {
});

var mainData = new tlv8.Data();
mainData.setDbkey("wq");
mainData.setTable("WQ_SCENE_VISIT");
// 页面初始化
var option = getParamValueFromUrl("option");
function initPage() {
	mainData.setFormId("visit_SCENE_form");
	var rowid = getParamValueFromUrl("rowid");
	if (rowid && rowid != "") {
		$("#visit_SCENE_form").attr("rowid", rowid);
		mainData.refreshData();
		if (option && option == "view") {
			$("#addphoto").hide();
			$("#submitItem").hide();
			$("#viewphoto").show();
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
	var rowid = mainData.saveData();
	$("#visit_SCENE_form").attr("rowid", rowid);
	//if (goback) {
		//history.go(-1);
		var url = "sceneindex.html";
		window.open(url, '_self');
	//}
	return true;
}

// 是这所有字段为只读
function setReadonly() {
	$("#FADDRESS").attr("readonly", "readonly");
	$("#FRESOURCE").attr("readonly", "readonly");
	$("#FRESUINFO").attr("readonly", "readonly");
}

function photograph() {
	var rowid = mainData.saveData();
	$("#visit_SCENE_form").attr("rowid", rowid);
//	if (saveData()) {
//		var rowid = $("#visit_SCENE_form").attr("rowid");
		var url = "camera.html?rowid=" + rowid;
		window.open(url, '_self');
//	}
}

function lookphotograph() {
	var rowid = $("#visit_SCENE_form").attr("rowid");
	var url = "camera.html?rowid=" + rowid + "&option=view";
	window.open(url, '_self');
}
function backhistory(){
if (option && option == "view") {
		var url = "listActivity.html";
		window.open(url, '_self');
	}else{
		var url = "sceneindex.html";
		window.open(url, '_self');
	}
}

