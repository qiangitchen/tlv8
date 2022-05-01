var taskData, histData;
var taskDataGrid, histDataGrid;

/*
 * 初始化页面
 */
function initPage() {
	initTaskDataGrid();
}

/*
 * 初始化任务列表
 */
function initTaskDataGrid() {
	taskData = new tlv8.Data();
	taskData.setTable("SA_TASK");
	taskData.setDbkey("system");
	taskData.setOrderby("SCREATETIME desc");
	var d = document.getElementById("grid_TaskView");
	var labelid = "master_check,No,SNAME,SSTATUSNAME,STYPENAME"
			+ ",SCPERSONID,SCPERSONNAME,SCDEPTNAME,SCREATETIME"
			+ ",SEPERSONID,SEPERSONNAME,SEDEPTNAME,sAStartTime,sAFinishTime"
			+ ",SPROJECTNAME,SCUSTOMERNAME,SPLANNAME,sFMAKERNAME"
			+ ",SCURL,SEURL,sData1,SFLOWID";
	var labels = "master_check,NO.,任务标题,环节状态,任务类型" + ",cid,提交人,提交部门,提交时间"
			+ ",eid,执行人,执行部门,开始时间,结束时间" + ",客户编码,客户名称,单据编号,制单人"
			+ ",SCURL,SEURL,sData1,SFLOWID";
	var labelwidth = "40,40,160,80,80" + ",0,60,80,120" + ",0,60,80,120,120"
			+ ",80,80,80,80" + ",0,0,0,0";
	var datatype = "ro,ro,string,string,string"
			+ ",string,string,string,datetime"
			+ ",string,string,string,datetime,datetime"
			+ ",string,string,string,string" + ",ro,ro,ro,ro";// 设置字段类型
	var dataAction = {
		"queryAction" : "getGridAction",
		"savAction" : "saveAction",
		"deleteAction" : "deleteAction"
	};
	var currentPersonID = tlv8.Context.getCurrentPersonID();
	var where = "(STYPEID is not null or SCATALOGID = 'tkTask' or STYPENAME is not null)";
	// where += " and (EXISTS(select 0 from SA_OPMANAGEMENT m where SPERSONID =
	// '"
	// + currentPersonID
	// + "' and (sepersonid = SMANAGEPERSONID "
	// + " or SEFID like m.SMANAGEORGFID||'%' " // 管理权限内
	// + " or EXISTS(select 1 from sa_oporg_view v where v.sfid like
	// m.SMANAGEORGFID||'%' and v.sid=sepersonid)"
	// + " ) and SMANAGETYPEID=(select sid from sa_opmanagetype where scode =
	// 'feiliuchengrenwuchuli' and rownum=1)"
	// + ") or SEPERSONID = '" // 自己的任务
	// + currentPersonID + "' )";
	var maingrid = new tlv8.createGrid(d, labelid, labels, labelwidth,
			dataAction, "100%", "100%", taskData, 20, where, "", "", datatype,
			"true", "true");
	// 设置按钮显示{新增、保存、刷新、删除}
	maingrid.grid.settoolbar(false, false, true, true);
	// 设置是否可编辑
	maingrid.grid.seteditModel(false);
	taskDataGrid = maingrid.grid;
	var myQuickItem = "<form class='layui-form' style='width:100px;margin-right:5px;'>"
			+ "<select onchange='selectStatusFilter(this)' id='Status_select' lay-filter='Status_select1' "
			+ "value='tesReady'>"
			+ "<option value='tesReady'>尚未处理</option>"
			+ "<option value='tesFinished'>已完成</option></select></form>";
	document.getElementById("grid_TaskView-grid-item").innerHTML = myQuickItem;
	layui.form.render("select");
	layui.form.on('select(Status_select1)', function(data){
	  selectStatusFilter(data.elem);
	});
	
	document.getElementById("grid_TaskView_deleteItem").onclick = cugriddeleteData;
	taskDataGrid.refreshData("SSTATUSID='tesReady'");
}

function selectStatusFilter(obj) {
	taskDataGrid.setStaticFilter("SSTATUSID='" + obj.value + "'");
	taskDataGrid.refreshData();
}

/*
 * 删除
 */
function cugriddeleteData() {
	var rowids = taskDataGrid.checkedRowIds;
	if (!rowids || rowids == "") {
		$.messager.alert('警告', '你还未选中数据！', 'warning');
		return;
	}
	$.messager.confirm('确认', '删除数据后不能恢复，确定删除吗？', function(r) {
		if (r) {
			doDeleteActfn();
		}
	});
}

function doDeleteActfn() {
	var rowids = taskDataGrid.checkedRowIds;
	var chrowids = rowids.split(",");
	rowids = "";
	for ( var i in chrowids) {
		if (i > 0)
			rowids += ",";
		rowids += "'" + chrowids[i] + "'";
	}
	var iSql = "insert into SA_TASK_HISTORY(select * from SA_TASK where sID in("
			+ rowids + "))";
	tlv8.sqlUpdateAction("system", iSql, function() {
		var sql = "delete from SA_TASK where sID in(" + rowids + ")";
		tlv8.sqlUpdateAction("system", sql, function(r) {
			taskDataGrid.refreshData();
		});
	});
}

/*
 * 初始化历史列表
 */
function initHistoryGrid() {
	histData = new tlv8.Data();
	histData.setTable("SA_TASK_HISTORY");
	histData.setDbkey("system");
	histData.setOrderby("SCREATETIME desc");
	var histD = document.getElementById("grid_historyView");
	var hlabelid = "No,SNAME,STYPENAME"
			+ ",SCPERSONID,SCPERSONNAME,SCDEPTNAME,SCREATETIME"
			+ ",SEPERSONID,SEPERSONNAME,SEDEPTNAME,sAStartTime,sAFinishTime"
			+ ",SPROJECTNAME,SCUSTOMERNAME,SPLANNAME,sFMAKERNAME"
			+ ",SCURL,SEURL,sData1,SFLOWID";
	var hlabels = "NO.,任务标题,任务类型" + ",cid,提交人,提交部门,提交时间"
			+ ",eid,执行人,执行部门,开始时间,结束时间" + ",客户编码,客户名称,单据编号,制单人"
			+ ",SCURL,SEURL,sData1,SFLOWID";
	var hlabelwidth = "40,160,60" + ",0,60,80,120" + ",0,60,80,120,120"
			+ ",80,80,80,80" + ",0,0,0,0";
	var hdatatype = "ro,string,string" + ",string,string,string,datetime"
			+ ",string,string,string,datetime,datetime"
			+ ",string,string,string,string" + ",ro,ro,ro,ro";// 设置字段类型
	var hdataAction = {
		"queryAction" : "getGridAction",
		"savAction" : "saveAction",
		"deleteAction" : "deleteAction"
	};
	var currentPersonID = tlv8.Context.getCurrentPersonID();
	var maingrid = new tlv8.createGrid(histD, hlabelid, hlabels,
			hlabelwidth, hdataAction, "100%", "100%", histData, 20, "", "", "",
			hdatatype, "false", "true");
	// 设置按钮显示{新增、保存、刷新、删除}
	maingrid.grid.settoolbar(false, false, true, false);
	// 设置是否可编辑
	maingrid.grid.seteditModel(false);
	histDataGrid = maingrid.grid;
	histDataGrid.refreshData();
}

/*
 * 任务列表
 */
function tab_TaskView() {
	if (taskDataGrid)
		taskDataGrid.refreshData();
}
/*
 * 历史数据
 */
function tab_historyView() {
	initHistoryGrid();
}

function tabsselected(title, index) {
	switch (index) {
	case 0:
		tab_TaskView();
		break;
	case 1:
		tab_historyView();
		break;
	default:
		break;
	}
}