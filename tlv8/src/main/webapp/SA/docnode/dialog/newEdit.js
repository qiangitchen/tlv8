var path;
var saverowid;
var maindata = new tlv8.Data();
maindata.setDbkey("system");// 指定使用数据库连接
maindata.setTable("PERSONALDOCNODE");// 设置保存的表

$(function() {
	maindata.setFormId("org_create_form");// 设置提交的表单
	var rowid = tlv8.RequestURLParam.getParam("treeRowid");
	var treeid = tlv8.RequestURLParam.getParam("Rowid");
	path = tlv8.RequestURLParam.getParam("spath");
	$("#SPARENTID").val(rowid || "");
	$("#SCREATORID").val(tlv8.Context.getCurrentPersonID());
	$("#SCREATORNAME").val(tlv8.Context.getCurrentPersonName());
	$(".ReadOnly").keyup(function() {
		var value = $(this).val();
		if (value == "") {
			$(this).addClass("ReadOnly");
		} else {
			$(this).removeClass("ReadOnly");
		}
	});
	if (treeid && treeid != "") {
		J$("org_create_form").rowid = treeid;
		maindata.setFilter("SID='" + treeid + "'");
		maindata.refreshData();
		return;
	}
});

function saveDatafn() {
	if ($(".ReadOnly").val() != "") {
		saverowid = maindata.saveData();
		if(!saverowid){
			return false;
		}
		if (!path || path == "undefined") {
			path = "";
		}
		$("#SPATH").val(path + "/" + saverowid);
		maindata.saveData();
		return saverowid;
	} else {
		alert("名称不能为空");
		return false;
	}
}

// 单击确定对话框
function dailogEngin() {
	saveDatafn();
	return saverowid;
}