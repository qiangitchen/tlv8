function initPage() {
	var rowid = tlv8.RequestURLParam.getParam("rowid");
	var data = new tlv8.Data();
	data.setDbkey("infolink");
	data.setTable("OA_IS_READER");
	var det = document.getElementById("readorListGridView");
	var labelid = "No,FPERSONNAME,FCREATETIME";
	var labels = "No.,已阅人,查看时间";
	var datatype = "ro,string,datetime";
	var labelwidth = "40,80,120";
	var dataAction = {
		"queryAction" : "getGridAction",
		"savAction" : "saveAction",
		"deleteAction" : "deleteAction"
	};
	var filter = "FMASTERID = '" + rowid + "'"
	var maingrid = new tlv8.createGrid(det, labelid, labels, labelwidth,
			dataAction, "100%", "100%", data, 20, filter, "", "", datatype,
			"false", "true");
	maingrid.grid.settoolbar(false, false, true, false);
	maingrid.grid.refreshData();
}