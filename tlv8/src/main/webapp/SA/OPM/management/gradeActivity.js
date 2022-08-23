var mainGrid, currentgrid, currentNode;
/*
 * 初始化
 */
function initPage() {
	var data = new tlv8.Data();
	data.setTable("SA_OPMANAGEMENT");
	data.setDbkey("system");
	var d = document.getElementById("main-grid-view");
	var labelid = "master_check,No,SORGID,SORGNAME,SORGFID,SMANAGEORGNAME,SCREATORFID,SCREATORFNAME,SMANAGEORGFNAME,SORGFNAME,SCREATETIME,SMANAGEORGID,SMANAGEORGFID,SMANAGETYPEID";
	var labels = "master_check,No.,d,d,d,d,d,d,管理的组织,所属组织,创建时间,s,s,s";
	var labelwidth = "40,40,0,0,0,0,0,0,300,160,120,0,0,0";
	var datatype = "ro,ro,ro,ro,ro,ro,ro,ro,string,string,datetime,ro,ro,ro";
	var dataAction = {
		"queryAction" : "getGridAction",
		"savAction" : "saveAction",
		"deleteAction" : "deleteAction"
	};
	maingrid = new tlv8.createGrid(d, labelid, labels, labelwidth,
			dataAction, "100%", "100%", data, 20, "", "main_org_trr", "SORGID",
			datatype, true, true);
	maingrid.grid.settoolbar("readonly", false, true, "readonly");
	maingrid.grid.seteditModel(false);
	currentgrid = maingrid.grid;

	var managaTypeSelectOption = "<table><tr><td><form class='layui-form' style='width:120px;margin-right:5px;'>";
	managaTypeSelectOption += "<select onchange='selectManagaType(this)' id='managatype_select' lay-filter='managatype_select1'>";
	managaTypeSelectOption += "<option value='systemManagement'>系统管理</option>";
	managaTypeSelectOption += "</select></form></td>";
	var managaTypePer = "<td style='padding-right:5px;'><button class='btn btn-default btn-sm' onclick='managaTypePerMision()'>管理类型维护</button></td></tr></table>";
	document.getElementById(d.id + "-grid-item").innerHTML = managaTypeSelectOption + managaTypePer;// 嵌入自定义工具条内容
	layui.form.render("select");
	layui.form.on('select(managatype_select1)', function(data){
		selectManagaType(data.elem);
	});
	initManagaTypeSelectData();
	$("#managatype_select").val("systemManagement");
}

/*
 * 管理权限类型 @param {Object} obj
 */
function selectManagaType(obj) {
	if (currentNode) {
		currentgrid.setStaticFilter("SORGID='" + currentNode.id
				+ "' and SMANAGETYPEID  = '" + obj.value + "'");
		currentgrid.refreshData();
	}
}

function initManagaTypeSelectData() {
	var magSql = "select distinct SID,SNAME from sa_opmanagetype order by SID";
	var mgResult = tlv8.sqlQueryActionforJson("system", magSql);
	var managaTypeSelectOptions = "";
	for ( var i in mgResult.data) {
		managaTypeSelectOptions += "<option value='" + mgResult.data[i].SID
				+ "'>" + mgResult.data[i].SNAME + "</option>";
	}
	$("#managatype_select").html(managaTypeSelectOptions);
}

/*
 * 管理类型维护
 */
function managaTypePerMision() {
	tlv8.portal.dailog.openDailog("管理类型",
			"/SA/OPM/management/dialog/managetype-permision.html", 560, 350,
			function(r) {
				initManagaTypeSelectData();
			});
}

// 选择树
function treeselected(treeId, treeNode) {
	if (treeNode.SORGKINDID == "psm") {
		maingrid.grid.settoolbar(true, false, true, true);
	} else {
		maingrid.grid.settoolbar("readonly", false, true, "readonly");
	}
	document.getElementById("main-grid-view_insertItem").onclick = function() {
		// 自定义新增事件
		new_permition(treeNode.id);
	};
	document.getElementById("main-grid-view_deleteItem").onclick = function() {
		cancelStFunc();
	};
	currentNode = treeNode;
	var obj = document.getElementById("managatype_select");
	document.getElementById("main_org_trr").rowid = treeNode.id;
	$("#main_org_trr").attr("rowid", treeNode.id);
	currentgrid.setStaticFilter("SORGID='" + currentNode.id
			+ "' and SMANAGETYPEID = '" + obj.value + "'");
	currentgrid.refreshData();
}

/*
 * 选择管理范围 可分配至人员 @param {Object} orgID
 */
function new_permition(orgID) {
	tlv8.portal.dailog.openDailog("选择管理范围 可分配至人员",
			"/SA/OPM/management/dialog/grade-select-org-dialog.html", 600, 350,
			new_permition_back, null);
}
function new_permition_back(dataMap) {
	var now = tlv8.System.Date.sysDateTime();
	var TreePanal = $.fn.zTree.getZTreeObj("JtreeDemo");
	var keySet = dataMap.keySet();
	for ( var i in keySet) {
		var data = dataMap.get(keySet[i]);
		var rowid = currentgrid.insertData();
		currentgrid.setValueByName("SORGID", rowid, currentNode.id);
		currentgrid.setValueByName("SORGFID", rowid, currentNode.SFID);
		currentgrid.setValueByName("SORGNAME", rowid, currentNode.name);
		currentgrid.setValueByName("SORGFNAME", rowid, currentNode.SFNAME);
		currentgrid.setValueByName("SCREATORFID", rowid, tlv8.Context
				.getCurrentPersonFID());
		currentgrid.setValueByName("SCREATORFNAME", rowid, tlv8.Context
				.getCurrentPersonFName());
		currentgrid.setValueByName("SMANAGEORGID", rowid, data.SID);
		currentgrid.setValueByName("SMANAGEORGFID", rowid, data.SFID);
		currentgrid.setValueByName("SMANAGEORGNAME", rowid, data.SNAME);
		currentgrid.setValueByName("SMANAGEORGFNAME", rowid, data.SFNAME);
		currentgrid.setValueByName("SCREATETIME", rowid, now);
		currentgrid.setValueByName("SMANAGETYPEID", rowid, $(
				"#managatype_select").val());
	}
	currentgrid.saveData();
}

function cancelStFunc() {
	if (!confirm("确定删除所选数据吗？"))
		return;
	var param = new tlv8.RequestParam();
	param.set("checkedIDs", currentgrid.getCheckedRowIds());
	var r = tlv8.XMLHttpRequest("deleteManagement", param, "post", false);
	if (r.flag == "false") {
		alert(r.message);
	} else {
		currentgrid.refreshData();
	}
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
}