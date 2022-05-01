var currentGrid, selectSMANAGEORGKINDNAME = new Map();
function initPage() {
	var data = new tlv8.Data();
	data.setTable("sa_opmanagetype");
	data.setDbkey("system");
	selectSMANAGEORGKINDNAME = new Map();
	selectSMANAGEORGKINDNAME.put("机构", "机构");
	selectSMANAGEORGKINDNAME.put("部门", "部门");
	selectSMANAGEORGKINDNAME.put("岗位", "岗位");
	selectSMANAGEORGKINDNAME.put("工作组", "工作组");
	selectSMANAGEORGKINDNAME.put("人员", "人员");
	var d = document.getElementById("managatype-main-grid");
	var labelid = "No,SCODE,SNAME,SMANAGEORGKIND,SMANAGEORGKINDNAME";
	var labels = "No.,编码,名称,kindid,管理组织机构类型";
	var labelwidth = "40,120,120,0,240";
	var datatype = "ro,string,string,ro,checkBoxSp:selectSMANAGEORGKINDNAME"; //checkBoxSp:为以空格分隔的形式
	var dataAction = {
		"queryAction" : "getGridAction",
		"savAction" : "saveAction",
		"deleteAction" : "deleteAction"
	};
	maingrid = new tlv8.createGrid(d, labelid, labels, labelwidth,
			dataAction, "100%", "100%", data, 10, "", "", "", datatype, false,
			true);
	maingrid.grid.settoolbar(true, true, true, true);
	maingrid.grid.seteditModel(true);
	currentGrid = maingrid.grid;
	currentGrid.refreshData();
}