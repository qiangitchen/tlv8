var operator;
var maindata = new tlv8.Data();
function saveDatafn(formfield) {
	if (operator != "edit") {
		rowid = maindata.rowid || new UUID().toString(); // 记住当前rowid
		J$("org_create_form").rowid = rowid;
		J$("org_create_form").setAttribute("rowid", rowid);
		$("#org_create_form").attr("rowid", rowid);
		$("#SFID").val(sfid + "/" + rowid+"."+type);
		formfield.SFID = $("#SFID").val();
		maindata.saveData(formfield);
	} else {
		maindata.saveData(formfield);
		var param = new tlv8.RequestParam();
		param.set("rowid", gridrowid);
		param.set("sparent", $("#SPARENT").val());
		param.set("scode", $("#SCODE").val());
		param.set("sname", $("#SNAME").val());
		tlv8.XMLHttpRequest("updateOrgPathAction", param, "post", false);
	}
}

// 单击确定对话框
function dailogEngin() {
	if (operator != "edit") {
		$("#SFNAME").val(sfname + "/" + $("#SNAME").val());
		$("#SFCODE").val(sfcode + "/" + $("#SCODE").val());
	}
	$("#mainfsub").click();
	return false;
}

// body 初始化
var name, rowid, scode, sfid, sfname, sfcode, sparent, type, gridrowid;
function initDialog() {
	maindata.setDbkey("system");// 指定使用数据库连接
	maindata.setTable("SA_OPOrg");// 设置保存的表
	maindata.setFormId("org_create_form");// 设置提交的表单

	name = J_u_decode(tlv8.RequestURLParam.getParam("name"));
	rowid = J_u_decode(tlv8.RequestURLParam.getParam("rowid"));
	scode = J_u_decode(tlv8.RequestURLParam.getParam("scode"));
	sfname = J_u_decode(tlv8.RequestURLParam.getParam("sfname"));
	sfcode = J_u_decode(tlv8.RequestURLParam.getParam("sfcode"));
	sparent = J_u_decode(tlv8.RequestURLParam.getParam("sparent"));
	sfid = J_u_decode(tlv8.RequestURLParam.getParam("sfid"));
	type = J_u_decode(tlv8.RequestURLParam.getParam("type"));
	operator = J_u_decode(tlv8.RequestURLParam.getParam("operator"));
	if (!scode || scode == undefined || scode=="null")
		scode = "";
	if (!sfname || sfname == undefined || sfname=="null")
		sfname = "";
	if (!sfcode || sfcode == undefined || sfcode=="null")
		sfcode = "";
	if (!sfid || sfid == undefined || sfid=="null")
		sfid = "";
	if (operator != "edit") {
		$("#SORGKINDID").val(type);
		$("#SVALIDSTATE").val("1");
		$("#SORGKINDID").attr("disabled","disabled");
		layui.form.render("select");
	}
	if(rowid && rowid != "" && rowid != "undefined"){
		$("#SPARENT").val(rowid);
		$("#SLEVEL").val(getOrgLEVEL(rowid));
		$("#SSEQUENCE").val(getOrgIndex(rowid));
	}
	gridrowid = tlv8.RequestURLParam.getParam("gridrowid");
	if (gridrowid && gridrowid != "") {
		J$("org_create_form").rowid = gridrowid;
		J$("org_create_form").setAttribute("rowid", gridrowid);
		$("#org_create_form").attr("rowid", gridrowid);
		maindata.setFilter("");
		maindata.refreshData();
	}
	
	layui.form.on('submit(mainform)', function(data) {
		//console.log(JSON.stringify(data.field));
		var nCode = $("#SCODE").val();
		saveDatafn(data.field);
		tlv8.portal.dailog.dailogEngin(nCode);
		return false;
	});
}

function afRefresh(event) {
	
}

function validateboxdacg(obj){

}