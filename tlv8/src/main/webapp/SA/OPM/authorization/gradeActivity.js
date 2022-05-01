var currentgrid = null;
var currenttreeID = null;
var currentORGKINDID = null;
var currenttreeName = null;
var currentNode = null;
var maingrid = null;
var data = new tlv8.Data();
data.setDbkey("system");
data.setTable("SA_OPAUTHORIZE");
var $maingridview;
function getData() {
	$maingridview = $("#main-grid-view");
	var labelid = "master_check,No,SAUTHORIZEROLEID,SDESCRIPTION,SCREATORFNAME,SCREATETIME";
	var labels = "master_check,No.,角色ID,角色名称,创建人,创建时间";
	var labelwidth = "40,40,100,150,200,120";
	var datatype = "ro,ro,ro,ro,ro,ro";// 设置字段类型
	var dataAction = {
		"queryAction" : "getGridAction",
		"savAction" : "saveAction",
		"deleteAction" : "deleteAction"
	};
	// 创建grid
	var gridmain = new tlv8.createGrid($maingridview[0], labelid, labels,
			labelwidth, dataAction, "100%", "100%", data, 20, "", "", "",
			datatype, "true", "true");
	// 设置toobar显示{新增、保存、刷新、删除}
	gridmain.grid.settoolbar("readonly", false, true, "readonly");
	// 设置是否可编辑
	currentgrid = gridmain.grid;
	currentgrid.setStaticFilter("1=2");//默认不能加载数据
	// currentgrid.setExcelimpBar(true);//导入
	currentgrid.setExcelexpBar(true);// 导出
}

function cancelStFunc() {
	if (!confirm("确定删除所选数据吗？"))
		return;
	var checkedID = currentgrid.getCheckedRowIds().split(",");
	var dRowId = "";
	for (var i = 0; i < checkedID.length; i++) {
		dRowId += ",'" + checkedID[i] + "'";
	}
	dRowId = dRowId.replaceFirst(",", "");
	var sql = "delete from SA_OPAUTHORIZE where sID in(" + dRowId + ")";
	var r = tlv8.sqlUpdateAction("system", sql);
	if (r.flag == "false") {
		alert(r.message);
	} else {
		currentgrid.refreshData();
	}
}

function newOrgData() {
	tlv8.portal.dailog.openDailog('角色选择-分级',
			'/SA/OPM/dialogs/selectMultiRoles/gradeselectMultiRoles.html', 800,
			520, dailogcallback, null);
}

function dailogcallback(event) {
	var param = new tlv8.RequestParam();
	var TreePanal = $.fn.zTree.getZTreeObj("JtreeDemo");
	param.set("orgID", currenttreeID);
	param.set("roleIDs", event);
	param.set("creatorfID", tlv8.Context.getCurrentPersonFID());
	param.set("creatorFNAME", tlv8.Context.getCurrentPersonFName());
	tlv8.XMLHttpRequest("saveAutherPermAction", param, "post", true,
			sCallback);
}

function sCallback(r) {
	currentgrid.refreshData();
}

function treeselected(treeId, treeNode) {
	currentNode = treeNode;
	currentORGKINDID = treeNode.SORGKINDID;
	document.getElementById("main_org_trr").rowid = treeNode.id;
	var filter = "(SORGID = '" + treeNode.id + "')";
	currentgrid.setStaticFilter(filter);
	currentgrid.refreshData();
	currenttreeID = treeNode.id;
	currenttreeName = treeNode.name;
	currentgrid.settoolbar(true, false, true, true);
	document.getElementById("main-grid-view_insertItem").onclick = function() {
		newOrgData();
	};
	document.getElementById("main-grid-view_deleteItem").onclick = function() {
		cancelStFunc();
	};
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