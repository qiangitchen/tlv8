//工具条
var toolbarItem;
function init_toolbar() {
	var bardiv = J$("stander_bar");
	var toobar = new tlv8.toolbar(bardiv, false, true, false, true);
	toolbarItem = toobar.items;
}
var ok = false;
// 数据配置
var datamian;
function initDocumentPage() {
	datamian = new tlv8.Data();
	datamian.setDbkey("system");
	datamian.setTable("sa_oprole");
	datamian.setFormId("MAIN_DATA_FORM");
	init_toolbar();
	var rowid = tlv8.RequestURLParam.getParam("rowid");
	if (rowid && rowid != "") {
		J$("MAIN_DATA_FORM").rowid = rowid;
		J$("MAIN_DATA_FORM").setAttribute("rowid", rowid);
		$("#MAIN_DATA_FORM").attr("rowid", rowid);
		datamian.setRowId(rowid);
		datamian.refreshData();
	}
	layui.form.on('submit(mainform)', function(data) {
		// console.log(data.field);
		datamian.saveData(data.field);
		if (ok) {
			tlv8.portal.dailog.dailogEngin();
		}
		return false;// 阻止表单跳转。如果需要表单跳转，去掉这段即可。
	});
}

// 新增数据
function dataInsert() {
	J$("MAIN_DATA_FORM").reset();
	J$("MAIN_DATA_FORM").rowid = "";
	J$("MAIN_DATA_FORM").setAttribute("rowid", "");
	$("#MAIN_DATA_FORM").attr("rowid", "");
}

// 数据保存
function dataSave() {
	ok = false;
	$("#mainfsub").click();
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

function dailogEngin() {
	ok = true;
	$("#mainfsub").click();
	return false;
}