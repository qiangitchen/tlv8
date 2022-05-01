var gridManagement, gridRoleManagement, currentNode;
/*
 * 初始化
 */
function initPage() {
	$("#bottomTrTd").height(
			$(document).height() - $("#mainGridViewTd").height() - 10);
	initgridManagement();
	initgridRoleManagement();
	$.unselecttext = function() {
		return false;
	};
	$.currentMouse = {
		x : 0,
		y : 0
	};
	$("#hovjsplit").bind("mousedown", function(e) {
		$.isSplitStart = true;
		$(document).bind('selectstart', $.unselecttext);
		var xx = e.originalEvent.x || e.originalEvent.layerX || 0;
		var yy = e.originalEvent.y || e.originalEvent.layerY || 0;
		$.currentMouse = {
			x : xx,
			y : yy
		};
		$.clefheight = $("#mainGridViewTd").height();
		$.btofheight = $("#bottomTrTd").height();
	});
	$("#hovjsplit").bind("mouseup", function() {
		$.isSplitStart = false;
	});
	$(document).bind("mouseup", function() {
		$.isSplitStart = false;
		$(document).unbind('selectstart', $.unselecttext);
	});
	$(document).bind("mousemove", function(e) {
		if ($.isSplitStart == true) {
			var xx = e.originalEvent.x || e.originalEvent.layerX || 0;
			var yy = e.originalEvent.y || e.originalEvent.layerY || 0;
			var dixf = yy - $.currentMouse.y;
			$("#mainGridViewTd").height($.clefheight + dixf);
			$("#bottomTrTd").height($.btofheight - dixf);
			standardPartitionResize(e);
		}
	});
}

var dManagement = new tlv8.Data();
function initgridManagement() {
	dManagement.setDbkey("system");
	dManagement.setTable("SA_OPManagement");
	var det = document.getElementById("main-grid-view");
	var labelid = "master_check,No,SMANAGETYPEID,SMANAGEORGFNAME,SORGFNAME";// ",SORGID,SORGNAME,SORGFID,SMANAGEORGID,SMANAGEORGNAME,SMANAGEORGFID,SCREATORFID,SCREATORFNAME,SCREATETIME";
	var labels = "master_check,No., ,管理的组织,所属组织";// ",SORGID,SORGNAME,SORGFID,SMANAGEORGID,SMANAGEORGNAME,SMANAGEORGFID,cfid,cfname,ctime";
	var datatype = "null,ro,ro,ro,ro";// ",ro,ro,ro,ro,ro,ro,ro,ro,ro";
	var labelwidth = "40,40,30,200,200";// ",0,0,0,0,0,0,0,0,0";
	var dataAction = {
		"queryAction" : "getGridAction",
		"savAction" : "saveAction",
		"deleteAction" : "deleteAction"
	};
	var maingrid = new tlv8.createGrid(det, labelid, labels, labelwidth,
			dataAction, "100%", "100%", dManagement, 10,
			"(sManageTypeID = 'systemManagement') and ", "main_org_trr",
			"SORGID", datatype, "true", "true");
	gridManagement = maingrid.grid;
	gridManagement.settoolbar(true, false, true, true);
	J$("main-grid-view_insertItem").onclick = null;
	$("#main-grid-view_insertItem").click(function(event) {
		btnInsertManagementsClick();
	});
}
function btnInsertManagementsClick() {
	var surl = "/SA/OPM/dialogs/selectMultiOrgs/gradeselectMultiOrgs.html";
	tlv8.portal.dailog.openDailog("选择管理的组织-分级", surl, 750, 450, function(r) {
		wdSelectManageReceive(r);
	});
}
function wdSelectManageReceive(data) {
	var param = new tlv8.RequestParam();
	param.set("orgid", currentNode.id);
	param.set("orgids", data);
	var r = tlv8.XMLHttpRequest("writeGradeManagement", param, "post",
			false);
	if (r.data.flag == "false") {
		alert(r.data.message);
	}
	gridManagement.refreshData("");
}

var dRoleManagement = new tlv8.Data();
function initgridRoleManagement() {
	dRoleManagement.setDbkey("system");
	dRoleManagement.setTable("SA_OPRoleManagement");
	var det = document.getElementById("gridRoleManagementview");
	var labelid = "master_check,No,SROLEID,SORGFNAME";// ",SORGID,SORGNAME,SORGFID,SCREATORFID,SCREATORFNAME,SCREATETIME";
	var labels = "master_check,No.,管理的角色,所属组织";// ",SORGID,SORGNAME,SORGFID,cfid,cfname,ctime";
	var datatype = "null,ro,html:readRoleValue,ro";// ",ro,ro,ro,ro,ro,ro";
	var labelwidth = "40,40,130,200";// ",0,0,0,0,0,0";
	var dataAction = {
		"queryAction" : "getGridAction",
		"savAction" : "saveAction",
		"deleteAction" : "deleteAction"
	};
	var maingrid = new tlv8.createGrid(det, labelid, labels, labelwidth,
			dataAction, "100%", "100%", dRoleManagement, 10, "",
			"main_org_trr", "SORGID", datatype, "true", "true");
	gridRoleManagement = maingrid.grid;
	gridRoleManagement.settoolbar(true, false, true, true);
	J$("gridRoleManagementview_insertItem").onclick = null;
	$("#gridRoleManagementview_insertItem").click(function(event) {
		btnInsertRoleManagementsClick();
	});
}
function readRoleValue(event) {
	var roleid = event.value;
	var quey = "select SNAME from SA_OPROLE  where SID = '" + roleid + "'";
	var result = tlv8.sqlQueryAction("system", quey, null, false);
	return "<div class='gridValue'>" + result.getValueByName("SNAME")
			+ "</div>";
}
// 选择树
function treeselected(treeId, treeNode) {
	currentNode = treeNode;
	document.getElementById("main_org_trr").rowid = treeNode.id;
	$("#main_org_trr").attr("rowid", treeNode.id);
	gridManagement.refreshData("");
	gridRoleManagement.refreshData("");
}
function btnInsertRoleManagementsClick() {
	var surl = "/SA/OPM/dialogs/selectMultiRoles/gradeselectMultiRoles.html";
	tlv8.portal.dailog.openDailog("选择管理的角色-分级", surl, 650, 450, function(r) {
		wdSelectRolesReceive(r);
	});
}
// 保存选择的角色
function wdSelectRolesReceive(data) {
	var param = new tlv8.RequestParam();
	param.set("orgid", currentNode.id);
	param.set("roleids", data);
	var r = tlv8.XMLHttpRequest("writeGradeRoleManagement", param, "post",
			false);
	if (r.data.flag == "false") {
		alert(r.data.message);
	}
	gridRoleManagement.refreshData("");
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
	$("#gridRoleManagementview_grid_label").fixTable({
		fixColumn : 0,// 固定列数
		fixColumnBack : "#ccc",// 固定列数
		width : $("#gridRoleManagementview_layout").width(),// 显示宽度
		height : $("#gridRoleManagementview_layout").height()
	// 显示高度
	});
}