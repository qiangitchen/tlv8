var name, rowid, scode, sfname, sfid, sfcode, sparent;

var maindata = new tlv8.Data();
maindata.setTable("SA_OPPERSON");// 设置保存的表
maindata.setDbkey("system");// 指定使用数据库连接
var PICDiv;
$(document).ready(function() {
	maindata.setFormId("psm_create_form");// 设置提交的表单
	PICDiv = document.getElementById("PersonPic");
	name = J_u_decode(tlv8.RequestURLParam.getParam("name"));
	rowid = tlv8.RequestURLParam.getParam("rowid");
	scode = tlv8.RequestURLParam.getParam("scode");
	sfname = J_u_decode(tlv8.RequestURLParam.getParam("sfname"));
	sfcode = tlv8.RequestURLParam.getParam("sfcode");
	sfid = tlv8.RequestURLParam.getParam("sfid");
	sparent = tlv8.RequestURLParam.getParam("sparent");
	if (!scode || scode == undefined || scode=="null")
		scode = "";
	if (!sfname || sfname == undefined || sfname=="null")
		sfname = "";
	if (!sfcode || sfcode == undefined || sfcode=="null")
		sfcode = "";
	if (!sfid || sfid == undefined || sfid=="null")
		sfid = "";
	$("#sparentName").html(sfname + "/" + $("#SNAME").val());
	$(".SFNAME").val($("#sparentName").html());
	$("#SMAINORGID").val(rowid);
	$("#SNUMB").val("0");
	$("#SVALIDSTATE").val("1");
	$("#SSEQUENCE").val(getPsnIndex(rowid));
	var gridrowid = tlv8.RequestURLParam.getParam("gridrowid");
	var SPERSONID = tlv8.RequestURLParam.getParam("SPERSONID");
	if (SPERSONID && SPERSONID != "") {
		document.getElementById("psm_create_form").rowid = SPERSONID;
		$("#psm_create_form").attr("rowid", SPERSONID);
		maindata.rowid = SPERSONID;
		maindata.setFilter("SID='" + SPERSONID + "'");
		maindata.refreshData();
		tlv8.picComponent(PICDiv, maindata, "SPHOTO", true, true);
		return;
	}
	tlv8.picComponent(PICDiv, maindata, "SPHOTO", true, true);
});

// 帐号验证
function check_code(obj) {
	validateboxdacg(obj);
	var nCode = obj.value;
	var rowid = $("#psm_create_form").attr("rowid");
	if (nCode && nCode != "") {
		var param = new tlv8.RequestParam();
		param.set("value",nCode);
		param.set("id",rowid);
		var r = tlv8.XMLHttpRequest("checkPersonCode", param, "post", false);
		var count = r.count;
		if (count > 0) {
			alert("帐号 ：" + nCode + "已经存在请重新输入！");
			obj.value = "";
			obj.focus();
		}
	}
}

function afRefresh(event) {
	var code = $('#SCODE').val();
	if(code&&code!=""){
		$('#SCODE').validatebox('disableValidation'); // 禁用验证
	}else{
		$('#SCODE').validatebox('enableValidation'); // 禁用验证
	}
	var name = $('#SNAME').val();
	if(name&&name!=""){
		$('#SNAME').validatebox('disableValidation'); // 禁用验证
	}else{
		$('#SNAME').validatebox('enableValidation'); // 禁用验证
	}
}

function validateboxdacg(obj){
	$(obj).validatebox('enableValidation'); // 启用验证
}

// 数据保存
function saveDatafn() {
	$("#sparentName").html(sfname + "/" + $(".SNAME").val());
	if ($("#SNUMB").val() == "" || !$("#SNUMB").val()) {
		$("#SNUMB").val(0);
	}
	if ($("#SPASSWORD").val() == "" || $("#SPASSWORD").val()==null) {
		$("#SPASSWORD").val("E10ADC3949BA59ABBE56E057F20F883E");// 默认密码为123456
	}
	var rowid1 = maindata.saveData();
	if(!rowid1){
		return false;
	}
	tlv8.picComponent(PICDiv, maindata, "SPHOTO", true, true);
	var param = new tlv8.RequestParam();
	param.set("personid", rowid1);
	param.set("orgid", $("#SMAINORGID").val());
	tlv8.XMLHttpRequest("updateOrgPersonInfo", param, "post", false);
	return rowid1;
}

// 单击确定对话框
function dailogEngin() {
	var nCode = document.getElementById("SCODE").value;
	if (!nCode || nCode == "") {
		alert("帐号不能为空!");
		return false;// TODO:暂停返回对话框
	}
	saveDatafn();
	return name;
}

function check_mobile(obj){
	var mobile = obj.value;
	if (mobile.length>0&&mobile.match(/[\x01-\xFF]*/)==false){
		alert('不能输入中文！');
		obj.value="";
		obj.focus();
		return false;
	}
	var rowid = $("#psm_create_form").attr("rowid");
	if (mobile && mobile != "") {
		var param = new tlv8.RequestParam();
		param.set("value",mobile);
		param.set("id",rowid);
		var r = tlv8.XMLHttpRequest("checkPersonMobilePhone", param, "post", false);
		var count = r.count;
		if (count > 0) {
			alert("手机号 ：" + mobile + "已经存在请重新输入！");
			obj.value = "";
			obj.focus();
		}
	}
}