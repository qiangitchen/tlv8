function initDataGrid() {
	var data = new tlv8.Data();
	data.setTable("SA_HALTCAUTION");
	data.setDbkey("system");
	data.setOrderby("SCREATETIME desc");
	var det = document.getElementById("HALT_CAUTION_LIST");
	var labelid = "No,STITLE,SSTARTTIME,SENDTIME,SATTENTION,SCREATERNAME,SCREATETIME";
	var labels = "No.,标题,开始时间,结束时间,提醒倒计时,创建人名称,创建时间";
	var datatype = "ro,ro,datetime,datetime,ro,html:readcreater,datetime";
	var labelwidth = "40,320,130,130,100,100,130";
	var dataAction = {
		"queryAction" : "getGridAction",
		"savAction" : "saveAction",
		"deleteAction" : "deleteAction"
	};
	var maingrid = new tlv8.createGrid(det, labelid, labels, labelwidth,
			dataAction, "100%", "100%", data, 10, "", "", "", datatype,
			"false", "true");
	maingrid.grid.settoolbar(false, false, true, false);
	maingrid.grid.refreshData();

}

function readcreater(event) {
	return "<span style='width:100%;text-align:center;'>" + event.value
			+ "</span>";
}

$(document).ready(function() {
	initDataGrid();
});