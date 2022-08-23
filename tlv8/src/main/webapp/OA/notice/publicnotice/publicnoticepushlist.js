/*==数据源===此项为必须定义==*/
var currentgrid;
var data = new tlv8.Data();
data.setDbkey("oa");// 指定使用数据库连接
data.setTable("OA_PULICNOTICE");// 指定grid对应的表
data.setOrderby(" FCREATEDATETIME desc");// 指定grid对应的表
data.setCascade("OA_NOTICE_PERSON:FMAINID");// 级联删除查看人员表
/* ==== */

var currentgrid;
function getData() {
	var d = document.getElementById("main-grid-view");
	var labelid = "No,FCREATEID,FTITLE,FCREATENAME,FTYPE,FPUSHDATETIME";// 设置字段
	var labels = "No.,操作,标题,创建人,发布类型,发布时间";// 设置标题
	var labelwidth = "40,80,600,100,100,150";// 设置宽度
	var datatype = "ro,html:opef,ro,ro,ro,ro";// 设置字段类型
	var dataAction = {
		"queryAction" : "getGridAction",// 查询动作
		"savAction" : "saveAction",// 保存动作
		"deleteAction" : "deleteAction"// 删除动作
	};
	var maingrid = new tlv8.createGrid(d, labelid, labels, labelwidth,
			dataAction, "100%", "100%", data, 20, "FCREATEID = '"
					+ tlv8.Context.getCurrentPersonID() + "'", "", "",
			datatype, "false", "true");
	// 设置按钮显示{新增、保存、刷新、删除}
	maingrid.grid.settoolbar(true, false, true, true);
	currentgrid = maingrid.grid;
	currentgrid.refreshData();// 刷新数据
	document.getElementById("main-grid-view_insertItem").onclick = function() {
		tlv8.portal.openWindow('新增通知',
				'/OA/notice/publicnotice/publicnoticeadd.html?temp='
						+ new Date().getMilliseconds() + "&tabID="
						+ tlv8.portal.currentTabId(), 'newwindow');
	};
}
function opef(event) {
	var urlLink = "<div style='width:100%;text-align:center;'>"
			+ "<a href='javascript:void(0);' onclick=\"lookview('"
			+ event.rowid
			+ "')\"><img src=\"../../../comon/image/toolbar/search.gif\"/>编辑通知</a>";
	return urlLink + "</div>";
}
function deldata(id) {
	if (canUnPush(id)) {
		currentgrid.deleteData();
	} else {
		alert("此通知已被用户查看，无法取消和删除");
	}
}
function defRefreshData() {
	currentgrid.refreshData();// 刷新数据
}
function lookviewf(event) {
	return "<div style='width:100%;text-align:left;'><a href='javascript:void(0);' onclick=\"lookview('"
			+ event.rowid + "')\" >" + event.value + "</a></div>";
}
function lookview(id) {
	var tabID = tlv8.portal.currentTabId();
	tlv8.portal.openWindow('编辑通知',
			'/OA/notice/publicnotice/publicnoticeadd.html?&sData1=' + id
					+ "&readonly=true&tabID="+tabID, 'newwindow');
}
