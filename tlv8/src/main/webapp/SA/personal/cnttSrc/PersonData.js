var mainData = new tlv8.Data();
mainData.setDbkey("system");
mainData.setTable("SA_OPPERSON");// TODO:设置保存的表
$(document).ready(function() {
	mainData.setFormId("sa_opperson_form");// TODO:设置提交的表单
	var personid = tlv8.Context.getCurrentPersonID();
	document.getElementById("sa_opperson_form").rowid = personid;
	mainData.setFilter("SID='" + personid + "'");
	mainData.refreshData();
	var cellname = "SPHOTO";// 设置附件相关字段
	var div = document.getElementById("picDemo");
	tlv8.picComponent(div, mainData, cellname, true);
	return;
});

// 保存
function PICsaveData() {
	return mainData.saveData();
}

function afRefresh() {
	var cellname = "SPHOTO";// 设置附件相关字段
	var div = document.getElementById("picDemo");
	tlv8.picComponent(div, mainData, cellname, true);
}
