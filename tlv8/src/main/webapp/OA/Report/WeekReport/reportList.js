/*==数据源===此项为必须定义==*/
var currentgrid;
var data = new tlv8.Data();
data.setDbkey("oa");// 指定使用数据库连接
data.setTable("OA_RE_WEEKREPORT");// 指定grid对应的表
data.setOrderby("FCREATETIME desc");// 指定grid对应的表
/* ==== */

var currentgrid;
function getData() {
	var d = document.getElementById("main-grid-view");
	var labelid = "No,FTITLE,FPUSHDATETIME,FCREATEDEPTNAME,FCREATEPERSONNAME,FCREATETIME,FCREATEPERSONID";// 设置字段
	var labels = "No.,标题,发布时间,登记部门,登记人,登记时间,操作";// 设置标题
	var labelwidth = "40,500,150,100,100,150,100";// 设置宽度
	var datatype = "ro,html:lookviewf,ro,ro,ro,ro,html:opef";// 设置字段类型
	var dataAction = {
		"queryAction" : "getGridAction",// 查询动作
		"savAction" : "saveAction",// 保存动作
		"deleteAction" : "deleteAction"// 删除动作
	};
	var createid = tlv8.Context.getCurrentPersonID();
	var maingrid = new tlv8.createGrid(d, labelid, labels, labelwidth,
			dataAction, "100%", "100%", data, 20, "FCREATEPERSONID = '"
					+ createid + "'", "", "", datatype, "false", "true");
	// 设置按钮显示{新增、保存、刷新、删除}
	maingrid.grid.settoolbar(true, false, true, true);
	currentgrid = maingrid.grid;
	currentgrid.refreshData();// 刷新数据
	document.getElementById("main-grid-view_insertItem").onclick = function() {
		tlv8.portal.openWindow('周报登记',
				'/OA/Report/WeekReport/reportAdd.html?temp='
						+ new Date().getMilliseconds() + "&tabID="
						+ tlv8.portal.currentTabId(), 'newwindow');
	};
}
function opef(event) {
	var pushdate = currentgrid.getValueByName("FPUSHDATETIME", event.rowid);
	var urlLink = "<div style='width:100%;text-align:center;'>";
	if (pushdate == null || pushdate == "") {
		urlLink += "<a href='###' onclick=\"pushData('" + event.rowid
				+ "');\" >发布</a>&nbsp;<a href='###' onclick=\"lookview('"
				+ event.rowid + "')\" >编辑</a>";
	} else {
		urlLink += "<a href='###' onclick=\"unpushData('" + event.rowid
				+ "');\" >取消发布</a>&nbsp;<a href='###' onclick=\"lookview('"
				+ event.rowid + "')\" >编辑</a>";
	}
	return urlLink + "</div>";
}
function deldata(id) {
	currentgrid.deleteData();
}
function lookviewf(event) {
	return "<div style='width:100%;text-align:left;'><a href='###' onclick=\"lookview('"
			+ event.rowid + "')\" >" + event.value + "</a></div>";
}
function lookview(id) {
	tlv8.portal.openWindow('编辑计划',
			'/OA/Report/WeekReport/reportAdd.html?temp='
					+ new Date().getMilliseconds() + "&tabID="
					+ tlv8.portal.currentTabId() + "&rowid=" + id,
			'newwindow');
}
function pushData(id) {
	currentgrid.setValueByName("FPUSHDATETIME", id, tlv8.System.Date
			.sysDateTime());
	currentgrid.saveData();
}
function unpushData(id) {
	currentgrid.setValueByName("FPUSHDATETIME", id, "");
	currentgrid.saveData();
}

// 回调刷新
function defRefreshData(data) {
	currentgrid.refreshData();
}
