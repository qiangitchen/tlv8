$(document).bind("pagebeforecreate", function(event, data) {
});
var flwCompent;
var mainData = new tlv8.Data();
mainData.setDbkey("wq");
mainData.setTable("WQ_LEAVE_APPLY");
// 页面初始化
var option = getParamValueFromUrl("option");
function initPage() {
	mainData.setFormId("leave_form");
	var rowid = getParamValueFromUrl("sData1");
	if (rowid && rowid != "") {
		$("#leave_form").attr("rowid", rowid);
		mainData.refreshData();
		getOpinionData(rowid);
		if (option && option == "view") {
			$("#submitItem").hide();
			$("#readtitle").html("请假记录");
			setReadonly();
		}
	} else {
		
//		flwCompent = new tlv8.flw("flowToolbar", mainData, setting);
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
/*		$("#FCREATEDATE").val(tlv8.System.Date.sysDateTime());*/
	}
}

// 保存
function saveData(goback) {
	openProcessSelectDialog(true,false,true,false);
	return true;
}

// 是这所有字段为只读
function setReadonly() {
	$("#FCREATORNAME").attr("readonly", "readonly");
}

function backhistory(){
if (option && option == "view") {
		var url = "listActivity.html";
		window.open(url, '_self');
	}else{
		var url = "../attendindex.html";
		window.open(url, '_self');
	}
}

