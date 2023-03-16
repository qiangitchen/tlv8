// 工具条
var toolbarItem;
function init_toolbar() {
	var bardiv = J$("stander_bar");
	var toobar = new tlv8.toolbar(bardiv, false, "readonly", false, true);
	toolbarItem = toobar.items;
}

/* =======主表配置====== */
var MainData = new tlv8.Data();
function getData() {
	MainData.setDbkey("oa");// 指定使用数据库连接
	MainData.setTable("OA_ADM_MYGROUPMAIN");
	MainData.setFormId("MAIN_DATA_FORM");// 主表关联form
	init_toolbar();
	$("#FCREATORID").val(tlv8.Context.getCurrentPersonID());
	$("#FCREATOR").val(tlv8.Context.getCurrentPersonName());
	$("#FCREATEDEPTID").val(tlv8.Context.getCurrentDeptID());
	$("#FCREATEDEPT").val(tlv8.Context.getCurrentDeptName());
	$("#FCREATEDATE").val(tlv8.System.Date.sysDateTime());
	getData2();
}

/* ======从表配置====== */
var SubData = new tlv8.Data();
var Maingridperson;
function getData2() {
	SubData.setDbkey("oa");// 指定使用数据库连接
	SubData.setTable("OA_ADM_MYGROUPFROM");
	var d = document.getElementById("direct_from_subgrid");
	var labelid = "No,FPERSONID,FPERSONNAME,FCREATORID,FCREATOR,FCREATEDEPTID,FCREATEDEPT,FCREATEDATE";// 设置字段
	var labels = "No.,组人员ID,组人员名称,FCREATORID,FCREATOR,FCREATEDEPTID,FCREATEDEPT,FCREATEDATE";// 设置标题
	var labelwidth = "40,0,500,0,0,0,0,0";// 设置宽度
	var datatype = "ro,ro,ro,ro,ro,ro,ro,ro";// 设置字段类型
	var dataAction = {
		"queryAction" : "getGridAction",
		"savAction" : "saveAction",
		"deleteAction" : "deleteAction"
	};
	var maingrid = new tlv8.createGrid(d, labelid, labels, labelwidth,
			dataAction, "100%", "100%", SubData, 20, "", "MAIN_DATA_FORM",
			"FOUTKEY", datatype, "false", "true");
	maingrid.grid.settoolbar(true, false, true, true);// 设置按钮显示{新增、保存、刷新、删除}
	maingrid.grid.seteditModel(true);// 设置是否可编辑
	maingrid.grid.refreshData();
	Maingridperson=maingrid.grid;
	// 重写新建按钮
	J$("direct_from_subgrid_insertItem").onclick = function() {
		// 你的自定义新增事件写在这里
		tlv8.portal.dailog.openDailog("选择人员",
				"/comon/SelectDialogPsn/SelectChPsm.html", 600, 500,
				initPersonlist);
	};
}

/*
 * 从表值改变时改变主表按钮状态
 */
function cheng_stae_bar() {
	toolbarItem.setItemStatus("no", true, "no", "no");// no 代表不改变
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
	var rowid = MainData.saveData();
	J$("MAIN_DATA_FORM").rowid = rowid;
	J$("MAIN_DATA_FORM").setAttribute("rowid", rowid);
	$("#MAIN_DATA_FORM").attr("rowid", rowid);
	Maingridperson.refreshData();
}

// 数据刷新
function dataRefresh() {
	MainData.refreshData();
}

// 数据删除
function dataDeleted() {
	if (MainData.deleteData()) {
		dataRefresh();
	}
}

// 员工选择
function initPersonlist(data) {
	if (data.id == "" || data.id == " ") {
		alert("您没有选择人员!");
		return;
	}
	var curpersonids = data.id.split(",");
	var curpersonNames = data.name.split(",");
	for (var curindex = 0; curindex < curpersonids.length; curindex++) {
		var newid = Maingridperson.insertData();
		Maingridperson.setValueByName("FCREATORID", newid,
				tlv8.Context.getCurrentPersonID());
		Maingridperson.setValueByName("FCREATOR", newid, tlv8.Context
				.getCurrentPersonName());
		Maingridperson.setValueByName("FCREATEDEPTID", newid,
				tlv8.Context.getCurrentDeptID());
		Maingridperson.setValueByName("FCREATEDEPT", newid,
				tlv8.Context.getCurrentDeptName());
		Maingridperson.setValueByName("FCREATEDATE", newid,
				tlv8.System.Date.sysDateTime());

		Maingridperson.setValueByName("FPERSONID", newid,
				curpersonids[curindex]);
		Maingridperson.setValueByName("FPERSONNAME", newid,
				curpersonNames[curindex]);
	}
}