var currentgrid;
var data = new tlv8.Data();
data.setDbkey("system");
data.setTable("SA_DocNameSpace");
function inintData() {
	var gird_view = document.getElementById("doc-item-view");
	var labelid = "sDisplayName,sHost,sPort,SURL";
	var labels = "名称,主机,端口,文档服务地址";
	var labelwidth = "100,100,60,360";
	var datatype = "string,string,string,string";
	var dataAction = {
		"queryAction" : "getGridAction",
		"savAction" : "saveAction",
		"deleteAction" : "deleteAction"
	};
	var maingrid = new tlv8.createGrid(gird_view, labelid, labels,
			labelwidth, dataAction, "100%", "100%", data, 20, "sID != 'root'",
			"", "", datatype);
	// 设置按钮显示{新增、保存、刷新、删除}
	maingrid.grid.settoolbar(false, true, true, false);
	// 设置是否可编辑
	maingrid.grid.seteditModel(true);
	currentgrid = maingrid.grid;
	currentgrid.insertSelfBar("测试配置", "60px", "testPermision()");

	currentgrid.refreshData();
}
function testPermision() {
	var url = currentgrid.getValueByName("SURL", currentgrid.getCurrentRowId());
	tlv8.portal.dailog.openDailog("文档服务配置测试",
			"/SA/doc/docSetting/settingtest.html?taget=" + J_u_encode(url),
			400, 300, null, false);
}