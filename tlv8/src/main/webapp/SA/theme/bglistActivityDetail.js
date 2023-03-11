//工具条
var toolbarItem;
function init_toolbar() {
	var bardiv = $("#stander-bar")[0];
	var toobar = new tlv8.toolbar(bardiv, false, true, false,
			true);
	toolbarItem = toobar.items;
}

// 数据配置
var datamian;
function initDocumentPage() {
	datamian = new tlv8.Data();
	datamian.setDbkey("system");
	datamian.setTable("SA_POTALTHEMELGBG");
	datamian.setFormId("MAIN_DATA_FORM");
	var rowid = tlv8.RequestURLParam.getParam("rowid");
	if (rowid && rowid != "") {
		J$("MAIN_DATA_FORM").rowid = rowid;
		J$("MAIN_DATA_FORM").setAttribute("rowid", rowid);
		dataRefresh();
	}

	var fileComp = new tlv8.fileComponent(document
			.getElementById("imageviewer"), datamian, "SIMAGE",
			"/root", true, true);

	var option = tlv8.RequestURLParam.getParam("option");
	if (option == "new") {
		datamian.setValueByName("SENABLED", "0");
		datamian.setValueByName("SCREATORID", tlv8.Context
				.getCurrentPersonID());
		datamian.setValueByName("SCREATORNAME", tlv8.Context
				.getCurrentPersonName());
	}
}

// 数据保存
function dataSave() {
	datamian.saveData();
}

// 数据刷新
function dataRefresh() {
	datamian.refreshData();
}

// 数据删除
function dataDeleted() {
	if (datamian.deleteData()) {
		dataRefresh();
	}
}