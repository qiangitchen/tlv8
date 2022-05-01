var mainData;
function initBody() {
	mainData = new tlv8.Data();
	mainData.setDbkey("pm");
	mainData.setTable("pm_expense");
	mainData.setFormId("MAIN_DATA_FORM");
	var pr = tlv8.XMLHttpRequest("pm/loadProjectList", null, "get", false);
	var ps = [];
	ps.push("<option></option>");
	for(var p in pr){
		ps.push('<option value="'+pr[p].fID+'">'+pr[p].fMINGCHENG+'</option>');
	}
	$("#fXIANGMUID").html(ps.join("\n"));
	var et = tlv8.XMLHttpRequest("pm/loadTypeList", null, "get", false);
	var tps = [];
	tps.push("<option></option>");
	for(var i in et){
		tps.push('<option value="'+et[i].fID+'">'+et[i].fMINGCHENG+'</option>');
	}
	$("#fEXTYPE").html(tps.join("\n"));
	var projectid = getParamValueFromUrl("projectid");
	$("#fXIANGMUID").val(projectid);
}

$(document).ready(function() {
	initBody();
	var sData1 = getParamValueFromUrl("sData1");
	if(!sData1 || sData1==""){
		setCreatorInfo();
	}else{
		$("#MAIN_DATA_FORM").attr("rowid", sData1);
		mainData.refreshData();
	}
});

function afRefresh() {
	// 数据刷新之后处理
}

function doDataSaveaAtion() {
	mainData.saveData();
}

/**
 * 设置当前用户信息
 */
function setCreatorInfo() {
	$("#FCREATEPSNFID").val(justep.Context.getCurrentPersonFID());
	$("#FCREATEPSNID").val(justep.Context.getCurrentPersonID());
	$("#FCREATEPSNNAME").val(justep.Context.getCurrentPersonName());
	$("#FCREATEDEPTID").val(justep.Context.getCurrentDeptID());
	$("#FCREATEDEPTNAME").val(justep.Context.getCurrentDeptName());
	$("#FCREATEOGNID").val(justep.Context.getCurrentOgnID());
	$("#FCREATEOGNNAME").val(justep.Context.getCurrentOgnName());
	$("#FCREATEORGID").val(justep.Context.getCurrentOrgID());
	$("#FCREATEORGNAME").val(justep.Context.getCurrentOrgName());
	$("#FCREATETIME").val(tlv8.System.Date.sysDateTime());
}