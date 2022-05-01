/*==数据源===此项为必须定义==*/
var data = new tlv8.Data();
data.setDbkey("system");// 指定使用数据库连接
data.setTable("sa_task_timelimit");// 指定grid对应的表

/* ==== */
var currentgrid;
function getData() {
	var d = document.getElementById("main-grid-view");
	var labelid = "No,SPROCESSID,SPROCESSNAME,SACTIVITY,SACTIVITYNAME,SDLIMIT";// 设置字段
	var labels = "No.,流程标识,流程名称,环节标识,环节名称,时限（天）";// 设置标题
	var labelwidth = "40,100,180,100,280,80";// 设置宽度
	var datatype = "ro,ro,ro,ro,ro,number";// 设置字段类型
	var dataAction = {
		"queryAction" : "getGridAction",// 查询动作
		"savAction" : "saveAction",// 保存动作
		"deleteAction" : "deleteAction"// 删除动作
	};
	var maingrid = new tlv8.createGrid(d, labelid, labels, labelwidth,
			dataAction, "100%", "100%", data, 20, "", "", "", datatype,
			"false", "true");
	// 设置按钮显示{新增、保存、刷新、删除}
	maingrid.grid.settoolbar(true, true, true, true);
	// 设置是否可编辑
	maingrid.grid.seteditModel(true);
	currentgrid = maingrid.grid;
	currentgrid.insertNum = true;// 新增向下(默认新增在第一行)
	currentgrid.refreshData();// 刷新数据
	document.getElementById(d.id + "_insertItem").onclick = function() {
		tlv8.portal.dailog.openDailog("选择流程环节",
				"/SA/task/timeLimitset/dialog/SelectFlowListDialog.html", 800,
				500, addCallback);
	};
}

function addCallback(rdata) {
	var rowid = currentgrid.insertData();
	currentgrid.setValueByName("SPROCESSID", rowid, rdata.SPROCESSID);
	currentgrid.setValueByName("SPROCESSNAME", rowid, rdata.SPROCESSNAME);
	currentgrid.setValueByName("SACTIVITY", rowid, rdata.activityID);
	currentgrid.setValueByName("SACTIVITYNAME", rowid, rdata.activityName);
}