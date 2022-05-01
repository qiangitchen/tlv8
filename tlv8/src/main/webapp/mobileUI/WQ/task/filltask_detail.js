$(document).bind("pagebeforecreate", function(event, data) {
});
var rowsid="";
var flwCompent;


var mainData = new tlv8.Data();
mainData.setDbkey("wq");
mainData.setTable("WQ_TASK_PSN");

var taskData = new tlv8.Data();
taskData.setDbkey("wq");
taskData.setTable("wq_task");

// 页面初始化
var option = getParamValueFromUrl("option");
function initPage() {
	taskData.setFormId("task_form");
	var mainid = getParamValueFromUrl("mainid");
	$("#task_form").attr("rowid", mainid);
	
	mainData.setFormId("psn_form");
	var rowid = getParamValueFromUrl("sData1");
	if (rowid && rowid != "") {
		$("#psn_form").attr("rowid", rowid);
		mainData.refreshData();
	}
	taskData.refreshData();
	
}
// 保存
function saveData(goback) {
	$("#FEDITDATE").val(tlv8.System.Date.sysDateTime());
	mainData.saveData();
	
	var url = "taskIndex.html";
	window.open(url, '_self');
	return true;
}
//function initLeaveType() {
//	var html = "";
//	var sql = "select FID,FTYPENAME from wq_base_leavetype where FSTATUS=1";
//	var result=tlv8.sqlQueryActionforJson("wq", sql)
//		var data = result.data;
//		if (typeof data == "string") {
//			data = window.eval("(" + data + ")");
//		}
//		for (var i = 0; i < data.length; i++) {
//			html += "<option value='" + data[i].FTYPENAME + "'>"
//					+ data[i].FTYPENAME + "</option>";
//		}
//	$("#FLEAVETYPE").html(html);
//  }
//// 是这所有字段为只读
//function setReadonly() {
//	$("#FCREATORNAME").attr("readonly", "readonly");
//}

//function backhistory(){
////if (option && option == "view") {
////		var url = "listActivity.html";
////		window.open(url, '_self');
////	}else{
////		var url = "taskIndex.html";
////		window.open(url, '_self');
////	}
//}
////新增人员
//function addPsn(){
//if($("#FTITLE").val()==""){
//		alert("请填写任务标题");
//		return;
//	}
//	var id=$("#psn_form").attr("rowid");
//	if(id&&id!=""&&id!=undefined){}
//	else{
//		id=mainData.saveData();
//	}
//	var url = "listActivity.html?id="+id;
//		window.open(url, '_self');
//}



//function getTaskDetail(){
//	var mainid = getParamValueFromUrl("mainid");
//var param = new tlv8.RequestParam();
//param.set("id", mainid);
//var result = tlv8
//		.XMLHttpRequest(
//				"getTaskDetailInfo",
//				param,
//				"POST",
//				false
//				);
//result=result.data;
//for(var i in result){
//	alert(i);
//}
//
//
//		$("#task_list").html(html);
//}