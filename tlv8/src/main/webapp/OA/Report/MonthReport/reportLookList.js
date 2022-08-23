var currentgrid;
function getData() {
	var data = new tlv8.Data();
	data.setDbkey("oa");
	data.setTable("SHOW_OA_RE_MONTHREPORT");
	data.setOrderby("FPUSHDATETIME desc");
	var d = document.getElementById("main-grid-view");
	var labelid = "No,FCREATEPERSONID,FTITLE,FPUSHDATETIME,FCREATEDEPTNAME,FCREATEPERSONNAME,FCREATETIME";// 设置字段
	var labels = "No.,操作,标题,发布时间,登记部门,登记人,登记时间";// 设置标题
	var labelwidth = "40,100,500,150,100,100,150";// 设置宽度
	var datatype = "ro,html:opef,html:lookviewf,ro,ro,ro,ro";// 设置字段类型
	var dataAction = {
		"queryAction" : "getGridAction",// 查询动作
		"savAction" : "saveAction",// 保存动作
		"deleteAction" : "deleteAction"// 删除动作
	};
	var createid = tlv8.Context.getCurrentPersonID();
	var maingrid = new tlv8.createGrid(d, labelid, labels, labelwidth,
			dataAction, "100%", "100%", data, 20, "FPERSONID = '" + createid
					+ "' and FPUSHDATETIME is not null", "", "", datatype,
			"false", "true");
	// 设置按钮显示{新增、保存、刷新、删除}
	maingrid.grid.settoolbar(false, false, true, false);
	currentgrid = maingrid.grid;
	currentgrid.refreshData();// 刷新数据
}
function lookviewf(event) {
	return "<div style='width:100%;text-align:left;'><a href='javascript:void(0);' onclick=\"lookview('"
			+ event.rowid + "')\" >" + event.value + "</a></div>";
}
function lookview(id) {
	tlv8.portal.openWindow('月报详细',
			'/OA/Report/MonthReport/reportAdd.html?temp='
					+ new Date().getMilliseconds() + "&sData1=" + id
					+ "&readonly=true", 'newwindow');
}

function opef(event) {
	var urlLink = "<div style='width:100%;text-align:center;'>"
			+ "<a href='javascript:void(0);' onclick=\"lookview('"
			+ event.rowid
			+ "')\"><img src=\"../../../comon/image/toolbar/search.gif\"/>查看此月报</a>";
	return urlLink + "</div>";
}
