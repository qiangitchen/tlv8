/*==数据源===此项为必须定义==*/
var currentgrid;
var Mrowid;
var Maindata = new tlv8.Data();
Maindata.setDbkey("oa");// 指定使用数据库连接
Maindata.setTable("OA_ADM_MYGROUPMAIN");// 指定grid对应的表
Maindata.setCascade("OA_ADM_MYGROUPFROM:FOUTKEY");

/* ==== */
var currentgrid;
function getData() {
	Maindata.setFormId("MAIN_DATA_FORM");
	var d = document.getElementById("main_grid_view");
	var labelid = "No,FGROUPNAME";// 设置字段
	var labels = "No.,组群名称";// 设置标题
	var labelwidth = "40,80";// 设置宽度
	var datatype = "ro,string";// 设置字段类型
	var dataAction = {
		"queryAction" : "getGridAction",// 查询动作
		"savAction" : "saveAction",// 保存动作
		"deleteAction" : "deleteAction"// 删除动作
	};
	var maingrid = new tlv8.createGrid(d, labelid, labels, labelwidth,
			dataAction, "100%", "100%", Maindata, 10, "FCREATORID='"
					+ tlv8.Context.getCurrentPersonID() + "'", "", "",
			datatype, "false", "true");
	// 设置按钮显示{新增、保存、刷新、删除}
	maingrid.grid.settoolbar(true, false, true, true);
	// 设置是否可编辑
	maingrid.grid.seteditModel(false);
	currentgrid = maingrid.grid;
	currentgrid.refreshData();// 刷新数据
	// 重写新建按钮
	J$("main_grid_view_insertItem").onclick = function() {
		// 你的自定义新增事件写在这里
		// tlv8.portal.openWindow('新建组群',
		// '/OA/PersonUse/MYGROUP/mainActivity.html', 'newwindow');
		tlv8.portal.dailog.openDailog("新建组群",
				"/OA/PersonUse/MYGROUP/mainActivity.html", 1024, 768,
				function() {
					currentgrid.refreshData();
				});
	};
}

/* ======从表配置====== */
var SubData = new tlv8.Data();
SubData.setDbkey("oa");// 指定使用数据库连接
SubData.setTable("OA_ADM_MYGROUPFROM");

/* ==== */
var MaingridGPerson;
function getData2() {
	var d = document.getElementById("direct_from_subgrid");
	var labelid = "No,FPERSONNAME";// 设置字段
	var labels = "No.,姓名";// 设置标题
	var labelwidth = "40,80";// 设置宽度
	var datatype = "ro,string";// 设置字段类型
	var dataAction = {
		"queryAction" : "getGridAction",
		"savAction" : "saveAction",
		"deleteAction" : "deleteAction"
	};
	var maingrid = new tlv8.createGrid(d, labelid, labels, labelwidth,
			dataAction, "100%", "100%", SubData, 10,
			"FOUTKEY='" + Mrowid + "'", "", "", datatype, "false", "true");
	maingrid.grid.settoolbar(true, true, true, true);// 设置按钮显示{新增、保存、刷新、删除}
	maingrid.grid.seteditModel(false);// 设置是否可编辑
	MaingridGPerson = maingrid.grid;
	MaingridGPerson.refreshData();// 刷新数据
	// 重写新建按钮
	J$("direct_from_subgrid_insertItem").onclick = function() {
		// 你的自定义新增事件写在这里
		tlv8.portal.dailog.openDailog("选择人员",
				"/comon/SelectDialogPsn/SelectChPsm.html", 600, 500,
				initPersonlist);
	};
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
	var rowid = Maindata.saveData();
	J$("MAIN_DATA_FORM").rowid = rowid;
	J$("MAIN_DATA_FORM").setAttribute("rowid", rowid);
	$("#MAIN_DATA_FORM").attr("rowid", rowid);
}

// 数据刷新
function dataRefresh() {
	Maindata.refreshData();
}

// 数据删除
function dataDeleted() {
	if (Maindata.deleteData()) {
		dataRefresh();
	}
}

// 选择"列表"事件
function tab_MainForm() {
	try {
		Mrowid = currentgrid.getCurrentRowId();
		Maindata.refreshData();
	} catch (e) {
	}
}

// 选择"详细"事件
function tab_FileInfo() {
	Mrowid = currentgrid.getCurrentRowId();
	// var Mrowid = currentgrid.getCurrentRowId();
	// Maindata.setFilter("");
	getData2();
}

// 双击grid跳到详细
function griddbclick(event) {
	var Mrowid = currentgrid.getCurrentRowId();
	showTab("tabveiw1", 1);
	Maindata.setFilter("");
	getData2();
}

function tabsselected(title, index) {
	switch (index) {
	case 0:
		tab_MainForm();
		break;
	case 1:
		tab_FileInfo();
		break;
	default:
		break;
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
		var newid = MaingridGPerson.insertData();
		MaingridGPerson.setValueByName("FCREATORID", newid, tlv8.Context
				.getCurrentPersonID());
		MaingridGPerson.setValueByName("FCREATOR", newid, tlv8.Context
				.getCurrentPersonName());
		MaingridGPerson.setValueByName("FCREATEDEPTID", newid, tlv8.Context
				.getCurrentDeptID());
		MaingridGPerson.setValueByName("FCREATEDEPT", newid, tlv8.Context
				.getCurrentDeptName());
		MaingridGPerson.setValueByName("FCREATEDATE", newid, tlv8.System.Date
				.sysDateTime());

		MaingridGPerson.setValueByName("FOUTKEY", newid, Mrowid);
		MaingridGPerson.setValueByName("FPERSONID", newid,
				curpersonids[curindex]);
		MaingridGPerson.setValueByName("FPERSONNAME", newid,
				curpersonNames[curindex]);
	}
}