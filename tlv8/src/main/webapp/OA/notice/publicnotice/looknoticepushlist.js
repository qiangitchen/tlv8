/*==数据源===此项为必须定义==*/
var currentgrid;
var data = new tlv8.Data();
data.setDbkey("oa");// 指定使用数据库连接
data.setTable("OA_PULICNOTICE");// 指定grid对应的表
data.setOrderby("FPUSHDATETIME desc");// 指定grid对应的表
/* ==== */

var currentgrid;
function getData() {
	var d = document.getElementById("main-grid-view");
	var labelid = "No,FCREATEID,FTITLE,FCREATENAME,FTYPE,FPUSHDATETIME";// 设置字段
	var labels = "No.,操作,标题,创建人,发布类型,发布时间";// 设置标题
	var labelwidth = "40,80,600,100,100,150";// 设置宽度
	var datatype = "ro,html:opef,html:lookviewf,ro,ro,ro";// 设置字段类型
	var dataAction = {
		"queryAction" : "getGridAction",// 查询动作
		"savAction" : "saveAction",// 保存动作
		"deleteAction" : "deleteAction"// 删除动作
	};
	var maingrid = new tlv8.createGrid(d, labelid, labels, labelwidth,
			dataAction, "100%", "100%", data, 20, "FPUSHDATETIME is not null", "", "", datatype,
			"false", "true");
	maingrid.grid.settoolbar(false, false, true, false);
	currentgrid = maingrid.grid;
	currentgrid.refreshData();// 刷新数据
}
function lookviewf(event) {
	return "<div style='width:100%;text-align:left;'><a href='###' onclick=\"lookview('"
			+ event.rowid + "')\" >" + event.value + "</a></div>";
}
function lookview(id) {
	tlv8.portal.openWindow('查看通知',
			'/OA/notice/publicnotice/publicnoticeview1.html?&rowid=' + id,
			'newwindow');
}

function opef(event) {
	var urlLink = "<div style='width:100%;text-align:center;'>"
			+ "<a href='javascript:void(0);' onclick=\"lookview('"
			+ event.rowid
			+ "')\"><img src=\"../../../comon/image/toolbar/search.gif\"/>查看通知</a>";
	return urlLink + "</div>";
}
