var currentgrid = "";
var data = new tlv8.Data();
data.setDbkey("system");
data.setTable("sa_worklog");
function Init() {
	var Workd = document.getElementById("WorkList");
	var Worklabelid = "master_check,No,FEXTEND01,SLOCK,SNAME,SCUSTOMERNAME,SIMPORTANCENAME,SPLANNAME,SPROJECTNAME,SEMERGENCYNAME,SLIMITTIME,SCREATOFNAME,SCREATETIME";
	var Worklabels = "master_check,No.,操作,编号,标题,客户,重要性,计划/工作项,项目,紧迫度,限制时间,提交者,提交时间";
	var Worklabelwidth = "40,40,165,160,160,50,100,100,60,100,100,100,130";
	var Workdatatype = "ro,ro,html:HANDLE,ro,ro,ro,ro,ro,ro,ro,ro,ro,ro";// 设置字段类型
	var dataAction = {
		"queryAction" : "getGridAction",
		"savAction" : "saveAction",
		"deleteAction" : "deleteAction"
	};
	var maingrid = new tlv8.createGrid(Workd, Worklabelid, Worklabels,
			Worklabelwidth, dataAction, "100%", "100%", data, 16, "", "", "",
			Workdatatype, "true", "true");
	// 设置按钮显示{新增、保存、刷新、删除}
	maingrid.grid.settoolbar(true, false, true, true);
	// 设置是否可编辑
	currentgrid = maingrid.grid;
	currentgrid.setExcelimpBar(true);// 导入
	currentgrid.setExcelexpBar(true);// 导出
	currentgrid.refreshData();

	document.getElementById(Workd.id + "_insertItem").onclick = function() {
		tlv8.portal.openWindow("填写工作日志", "/system/workLog/WorkLog.html?ctbid="
				+ tlv8.portal.currentTabId(), null);
	};
}
function callBackFn() {
	currentgrid.refreshData();
}
function HANDLE() {
	return "<a href='javascript:void(0)' class='toobar_item' style='width:20px;height:20px;float:left;margin-left:2px;'><img alt='查看' title='详细信息' src='../../comon/css/formDetail/img/mail_find.png' style='height:20px' onclick='YC()' /></a> &nbsp;"
			+ "<a href='javascript:void(0)' class='toobar_item' style='width:20px;height:20px;float:left;margin-left:2px;'><img alt='编辑' title='修改内容' src='../../comon/css/formDetail/img/easymoblog.png' style='height:20px' onclick='ET()' /></a> &nbsp;"
			+ "<a href='javascript:void(0)' class='toobar_item' style='width:20px;height:20px;float:left;margin-left:2px;'><img alt='跟踪' title='查看详细记录' src='../../comon/css/formDetail/img/toggle_log.png' style='height:20px' onclick='YC()' /></a> &nbsp;"
			+ "<a href='javascript:void(0)' class='toobar_item' style='width:20px;height:20px;float:left;margin-left:2px;'><img alt='删除' title='删除信息' src='../../comon/css/formDetail/img/err.gif' style='height:20px' onclick='DL()' /></a>";
}
function YC() {
	setTimeout("QueryDetailAction()", 100);
}
function QueryDetailAction() {
	var rowid = currentgrid.getCurrentRowId();
	tlv8.portal.openWindow("销售机会详细",
			"/system/workLog/WorkLog.html?opentype=query&rowid=" + rowid, null);
}

// 删除数据
function DL() {
	setTimeout("DeleteDetailAction()", 100);
}
function DeleteDetailAction() {
	var rowid = currentgrid.getCurrentRowId();
	var sql = "DELETE FROM sa_worklog WHERE SID='" + rowid + "'";
	var param = new tlv8.RequestParam();
	param.set("sql",sql);
	param.set("dbkey","system");
	tlv8.XMLHttpRequest("deleteSystemAction", param, "post", false);
	mAlert("删除成功！！");
	currentgrid.refreshData();
}
// 编辑数据
function ET() {
	setTimeout("EditDetailAction()", 100);
}
function EditDetailAction() {
	var rowid = currentgrid.getCurrentRowId();
	tlv8.portal.openWindow("修改信息",
			"/system/workLog/WorkLog.html?opentype=edit&rowid=" + rowid
					+ "&ctbid=" + tlv8.portal.currentTabId(), null);
}