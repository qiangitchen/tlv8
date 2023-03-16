var maingrid = null;
var currentgrid = null;
// 角色列表
function getData() {
	var data = new tlv8.Data();
	data.setTable("SA_OPROLE");
	data.setDbkey("system");
	data.setOrderby("SSEQUENCE asc");
	data.setCascade("SA_OPPERMISSION:SPERMISSIONROLEID");
	var d = document.getElementById("main-grid-view");
	var labelid = "No,sName,sCode,SCATALOG,SDESCRIPTION,SSEQUENCE";
	var labels = "序号,名称,编码,类型,描述,排序";
	var labelwidth = "40,120,100,100,120,100";
	var datatype = "ro,string,string,string,string,number";
	var dataAction = {
		"queryAction" : "getGridAction",
		"savAction" : "saveAction",
		"deleteAction" : "deleteAction"
	};
	maingrid = new tlv8.createGrid(d, labelid, labels, labelwidth, dataAction,
			"100%", "100%", data, 20, "", "", "", datatype, false, true);
	maingrid.grid.settoolbar(true, false, true, true);
	maingrid.grid.seteditModel(false);
	currentgrid = maingrid.grid;
	currentgrid.setExcelexpBar(true);// 导出
	initAuthGrid();
	currentgrid.refreshData();

	// 重写新增事件
	document.getElementById(d.id + "_insertItem").onclick = function() {
		tlv8.portal.dailog.openDailog("新增角色",
				"/SA/OPM/role/dialog/editRole.html", 600, 460, function() {
					currentgrid.refreshData();// 刷新数据
				});
	};

	currentgrid
			.insertSelfBar(
					"<i class=\"layui-icon layui-icon-edit\" style=\"font-size: 12px; color: blue;\"></i> 编辑",
					70, "dbselrow()");
}

/**
 * @param {object}
 *            event
 */
function dbselrow(event) {
	var rowid = currentgrid.getCurrentRowId();
	tlv8.portal.dailog.openDailog("编辑角色",
			"/SA/OPM/role/dialog/editRole.html?rowid=" + rowid, 600, 460,
			function() {
				currentgrid.refreshData();// 刷新数据
			});
}

var isnewDataFilter = "";
function afterInertFn(event) {
	isnewDataFilter = "SID = '" + event.CurrentRowId + "'";
	currentgrid.setStaticFilter(isnewDataFilter);
}

function afterRefreshFn(event) {
	currentgrid.setStaticFilter("");
}

function initWeb() {
	// $("#main-grid-view").height($("body").height() - 35);
	// $("#auth-grid-view").height($("body").height() - 35);
	// tlv8.standardPartition(document.getElementById("MyDiv"));
	getData();
}

// 功能列表
var auTherGid = null;
function initAuthGrid() {
	var aTdata = new tlv8.Data();
	aTdata.setTable("SA_OPPERMISSION");
	aTdata.setDbkey("system");
	var div = document.getElementById("auth-grid-view");
	var labelid = "master_check,No,SACTIVITYFNAME,SPROCESS,SACTIVITY";
	var labels = "master_check,序号,功能名称,process,activity";
	var labelwidth = "40,40,150,280,100";
	var datatype = "null,ro,ro,ro,ro";
	var dataAction = {
		"queryAction" : "getGridAction",
		"savAction" : "savePermitionAction",
		"deleteAction" : "deleteAction"
	};
	maingrid = new tlv8.createGrid(div, labelid, labels, labelwidth,
			dataAction, "100%", "100%", aTdata, 20, "", "main-auther-form",
			"SPERMISSIONROLEID", datatype, true, true);
	maingrid.grid.settoolbar(true, false, true, true);
	maingrid.grid.seteditModel(false);
	auTherGid = maingrid.grid;
	auTherGid.setExcelexpBar(true);// 导出
	document.getElementById("auth-grid-view_insertItem").onclick = function() {
		allocatingFunc();
	};
}

function readNameLabel(cgrid, rowObj) {
	var rowid = cgrid.CurrentRowId;
	var label = rowObj.cells(3).innerHTML;
	var SACTIVITYFNAME = rowObj.cells(2).innerHTML;
	if (!label || label == "") {
		label = SACTIVITYFNAME;
	}
	rowObj.cells(3).innerHTML = label;
	rowObj.cells(3).title = $(label).text();
}

// 联动刷新
function mainGridselected(g) {
	if (g.CurrentRowId == "systemManage") {// 禁止删除'系统管理‘角色
		g.settoolbar(true, "readonly", true, false);
	}
	document.getElementById("main-auther-form").rowid = g.CurrentRowId;
	setTimeout(function() {
		auTherGid.refreshData();
	}, 500);
}

// 分配功能
function allocatingFunc() {
	tlv8.portal.dailog.openDailog("分配功能",
			"/SA/OPM/role/dialog/func-tree-select.html", "300", "500",
			cocationCallback, null);
}

function cocationCallback(data) {
	// alert("ok"+data);//确定回传
	var kSet = data.keySet();
	for (k in kSet) {
		var rData = data.get(kSet[k]);
		var sLabels = rData.label;
		var sprocess = rData.process;
		var sactivity = rData.activity;
		var newID = auTherGid.insertData();
		auTherGid.setValueByName("SACTIVITYFNAME", newID, sLabels);
		auTherGid.setValueByName("SPROCESS", newID, sprocess);
		auTherGid.setValueByName("SACTIVITY", newID, sactivity);
	}
	auTherGid.saveData();
}

// 拖动分隔线事件
function standardPartitionResize(event) {
	$("#main-grid-view_grid_label").fixTable({
		fixColumn : 0,// 固定列数
		fixColumnBack : "#ccc",// 固定列数
		width : $("#main-grid-view_body_layout").width(),// 显示宽度
		height : $("#main-grid-view_body_layout").height()
	// 显示高度
	});
	$("#auth-grid-view_grid_label").fixTable({
		fixColumn : 0,// 固定列数
		fixColumnBack : "#ccc",// 固定列数
		width : $("#auth-grid-view_body_layout").width(),// 显示宽度
		height : $("#auth-grid-view_body_layout").height()
	// 显示高度
	});
}