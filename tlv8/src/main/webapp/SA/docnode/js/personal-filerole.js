var data = new tlv8.Data();
data.setTable("PERSONAL_FILEROLE");
data.setDbkey("system");
var $gridveiw;
var currentgrid = "";
$(function() {
	var parentid=tlv8.RequestURLParam.getParam("treeRowid");
	$gridveiw = $("#main_grid");
	var labelid = "No,SAUTHORIZERID,SAUTHORIAERNAME,SAUTHID,SDEPTNAME,SDEPTID,SPARENTID,SACCESS";
	var labels = "No.,授权人id,授权人,人员,部门名称,部门标识,父节点标识,权限类型";
	
	var datatype = "ro,string,string,select:psm_select,select:dept_select,string,string,select:permision_select";
	var labelwidth = "40,0,100,100,120,0,0,150";
	var dataAction = {
		"queryAction" : "getGridAction",
		"savAction" : "saveAction",
		"deleteAction" : "deleteAction"
	};

	var maingrid = new tlv8.createGrid($gridveiw[0], labelid, labels,
			labelwidth, dataAction, "100%", "100%", data, 10, "", "", "",
			datatype, "false", "true");
	maingrid.grid.seteditModel(true);
	maingrid.grid.refreshData();
	currentgrid = maingrid.grid;
	$("#" + $gridveiw[0].id + "_insertItem").get(0).onclick = function() {
		
		var rowid = currentgrid.insertData();
		var currentid=tlv8.Context.getCurrentPersonID();
		var currentname=tlv8.Context.getCurrentPersonName();
		var deptid=tlv8.Context.getCurrentDeptID();
		currentgrid.setValueByName("SAUTHORIAERNAME", rowid, currentname);
		currentgrid.setValueByName("SAUTHORIZERID", rowid, currentid);
		currentgrid.setValueByName("SDEPTID", rowid, deptid);
		currentgrid.setValueByName("SPARENTID", rowid, parentid);

	};

});