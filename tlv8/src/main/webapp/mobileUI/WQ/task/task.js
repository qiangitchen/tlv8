$(document).bind("pagebeforecreate", function(event, data) {
});
var rowsid="";
var flwCompent;
var mainData = new tlv8.Data();
mainData.setDbkey("wq");
mainData.setTable("WQ_TASK");
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
	}
//	initLeaveType();
}
function start1(event){
	processadvanceOut(event.processID,"bizActivity2",event.taskID,rowsid,event.flowID);
}
// 保存
function saveData(goback) {
	if($("#FTITLE").val()==""){
		alert("请填写任务标题");
		return;
	}
	mainData.saveData();
	var url = "taskIndex.html";
	window.open(url, '_self');
	return true;
}
function initLeaveType() {
	var html = "";
	var sql = "select FID,FTYPENAME from wq_base_leavetype where FSTATUS=1";
	var result=tlv8.sqlQueryActionforJson("wq", sql)
		var data = result.data;
		if (typeof data == "string") {
			data = window.eval("(" + data + ")");
		}
		for (var i = 0; i < data.length; i++) {
			html += "<option value='" + data[i].FTYPENAME + "'>"
					+ data[i].FTYPENAME + "</option>";
		}
	$("#FLEAVETYPE").html(html);
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
		var url = "taskIndex.html";
		window.open(url, '_self');
	}
}
//新增人员
function addPsn(){
if($("#FTITLE").val()==""){
		alert("请填写任务标题");
		return;
	}
	var id=$("#leave_form").attr("rowid");
	if(id&&id!=""&&id!=undefined){}
	else{
		id=mainData.saveData();
	}
	var url = "listActivity.html?id="+id;
		window.open(url, '_self');
}
