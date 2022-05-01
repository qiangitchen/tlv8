/*==数据源===此项为必须定义==*/
var data = new tlv8.Data();
data.setDbkey("system");// 指定使用数据库连接
data.setTable("SA_LINKS");// 指定grid对应的表

/* ==== */
var currentgrid;
function getData() {
	var d = document.getElementById("main-grid-view");
	var labelid = "No,STITLE,SURL,SUSERNAME,SPASSWORD,SEXPARAMS,SOPENTYPE";// 设置字段
	var labels = "No.,标题,链接,用户名,密码,扩展参数,打开方式";// 设置标题
	var labelwidth = "40,80,180,80,80,80,80";// 设置宽度
	var datatype = "ro,ro,ro,ro,html:passreader,ro,ro";// 设置字段类型
	var dataAction = {
		"queryAction" : "getGridAction",// 查询动作
		"savAction" : "saveAction",// 保存动作
		"deleteAction" : "deleteAction"// 删除动作
	};
	var stlwhere = "SCREATID='" + tlv8.Context.getCurrentPersonID() + "'";
	var maingrid = new tlv8.createGrid(d, labelid, labels, labelwidth,
			dataAction, "100%", "100%", data, 20, stlwhere, "", "", datatype,
			"false", "true");
	// 设置按钮显示{新增、保存、刷新、删除}
	maingrid.grid.settoolbar(true, false, true, true);
	// 设置是否可编辑
	maingrid.grid.seteditModel(false);
	currentgrid = maingrid.grid;
	currentgrid.refreshData();// 刷新数据
	// 重写新增事件
	document.getElementById(d.id + "_insertItem").onclick = adddataact;
}

function passreader(event) {
	return "******";
}

function adddataact() {
	var rowid = new UUID().toString();
	tlv8.portal.openWindow('新增链接',
			'/SA/links/listActivityDetail.html?rowid=' + rowid + "&option=new");
}

function griddbclick(event) {
	var rowid = currentgrid.getCurrentRowId();
	tlv8.portal
			.openWindow('编辑链接', '/SA/links/listActivityDetail.html?rowid='
					+ rowid + "&option=edit");
}