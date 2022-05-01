var SDOCPATH;
function initGrid() {
	var data = new tlv8.Data();
	data.setTable("SA_DOCAUTH");
	data.setDbkey("system");
	var det = document.getElementById("main_grid");
	var labelid = "No,sAuthorizeeDeptName,sAuthorizeeName,sAccess,SDOCPATH";
	var labels = "No.,部门,人员,权限,路径";
	var datatype = "ro,button:dept_select,button:psm_select,select:permision_select,string";
	var labelwidth = "40,200,60,100,0";
	var dataAction = {
		"queryAction" : "getGridAction",
		"savAction" : "saveAction",
		"deleteAction" : "deleteAction"
	};
	SDOCPATH = tlv8.RequestURLParam.getParam("DOCPATH");
	var maingrid = new tlv8.createGrid(det, labelid, labels, labelwidth,
			dataAction, "100%", "100%", data, 10, "", "", "", datatype,
			"false", "true");
	maingrid.grid.seteditModel(true);
	maingrid.grid.refreshData("SDOCPATH='" + SDOCPATH + "'");
}

function readPermision(evt) {
	if (evt.value == "0")
		return "不可见";
	if (evt.value == "1")
		return "列表";
	if (evt.value == "3")
		return "读取";
	if (evt.value == "7")
		return "下载";
	if (evt.value == "519")
		return "下载 修改";
	if (evt.value == "1543")
		return "下载 修改 删除";
	if (evt.value == "257")
		return "上传";
	if (evt.value == "263")
		return "下载 上传";
	if (evt.value == "775")
		return "下载 上传 修改";
	if (evt.value == "1799")
		return "下载 上传 修改 删除";
	if (evt.value == "16384")
		return "管理";
	if (evt.value == "32767")
		return "完全控制";
	return "";
}

function afterInertGrid(g) {
	g.setValueByName("SDOCPATH", g.getCurrentRowId(), SDOCPATH);
}

function dept_select() {

}

function psm_select() {

}