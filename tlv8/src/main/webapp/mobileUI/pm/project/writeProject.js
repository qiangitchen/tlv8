var mainData;
function initBody() {
	mainData = new tlv8.Data();
	mainData.setDbkey("pm");
	mainData.setTable("pm_project");
	mainData.setFormId("MAIN_DATA_FORM");
	var pr = tlv8.XMLHttpRequest("pm/loadCustomerList", null, "get", false);
	var ps = [];
	ps.push("<option></option>");
	for(var p in pr){
		ps.push('<option value="'+pr[p].fID+'">'+pr[p].fMINGCHENG+'</option>');
	}
	$("#fKEHUID").html(ps.join("\n"));
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

function selectCustomer(obj){
	$("#fKEHU").val($(obj).find("option:selected").text());
}