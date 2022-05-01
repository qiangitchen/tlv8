//获取栏目列表
var columndata = new tlv8.Data();
columndata.setTable("CYEA_NEWSCOLUMN");// TMJ_NEWSCOLUMN
columndata.setDbkey("system");// 指定使用数据库连接
var columnmaingrid;
var columncurrentgrid = null;
// 页面刷新
var random = Math.random();
var refresh = random + "=" + random;
var disablestateItem;// 禁用状态按钮
var ablestateItem;// 启用状态按钮
function getcolumnlist() {
	var det = document.getElementById("getcolumnlist");
	var labelid = "FCOLUMNNAME,FCOLUMNDESCRIBES,FCOLUMNSTATE";
	var labels = "栏目名称,栏目描述,栏目状态";
	var labelwidth = "145,270,90";
	var datatype = "string,string,string";// 设置字段类型
	var dataAction = {
		"queryAction" : "getGridAction",
		"savAction" : "saveAction",
		"deleteAction" : "deleteAction"
	};
	columnmaingrid = new tlv8.createGrid(det, labelid, labels, labelwidth,
			dataAction, "100%", "100%", columndata, 10, "", "", "", datatype);// 主从设置：指定主表的formid

	// 设置按钮显示{新增、保存、刷新、删除}
	columnmaingrid.grid.refreshData(refresh);
	columnmaingrid.grid.settoolbar(false, false, true, true);
	// 设置是否可编辑
	columnmaingrid.grid.seteditModel(false);
	columncurrentgrid = columnmaingrid.grid;
	disablestateItem = columncurrentgrid.insertSelfBar("禁用", "30px",
			"disablestate()", "../../../comon/image/statusaction/disable.gif");
	ablestateItem = columncurrentgrid.insertSelfBar("启用", "30px",
			"ablestate()", "../../../comon/image/statusaction/enable.gif");
	document.getElementById("getcolumnlist" + "_quick_text").style.display = "none";
	document.getElementById("getcolumnlist" + "_quick_button").style.display = "none";
}
// 保存栏目事件
function savacolumn() {
	var maindata = new tlv8.Data();
	maindata.setTable("CYEA_NEWSCOLUMN");// 设置保存的表TMJ_NEWSCOLUMN
	maindata.setFormId("savacolumn");// 设置提交的表单
	maindata.setDbkey("system");// 指定使用数据库连接tmjcrm
	columnmaingrid.grid.refreshData(refresh);
	var rowid = maindata.saveData();
	document.getElementById("savacolumn").rowid = rowid;// 记住当前rowid
}
// 行选中事件
function selectedItem(event) {
	var rowid = event.CurrentRowId;
	var state = columncurrentgrid.getValueByName("FCOLUMNSTATE", rowid);
	if (state == "已关闭") {
		columncurrentgrid.settoolbar(false, false, true, true);
		document.getElementById(disablestateItem).src = "../../../comon/image/statusaction/un_disable.gif";
		document.getElementById(ablestateItem).src = "../../../comon/image/statusaction/enable.gif";
	} else {
		columncurrentgrid.settoolbar(false, false, true, "readonly");
		document.getElementById(disablestateItem).src = "../../../comon/image/statusaction/disable.gif";
		document.getElementById(ablestateItem).src = "../../../comon/image/statusaction/un_enable.gif";
	}
}
// 启动和禁用状态设置
function disablestate() {
	if (!confirm("确定禁用当前选中的栏目吗?"))
		return;
	var rowid = columncurrentgrid.CurrentRowId;
	var sqlstr = "update CYEA_NEWSCOLUMN set FCOLUMNSTATE='已关闭' where SID = '"
			+ rowid + "'";// TMJ_NEWSCOLUMN
	var r = tlv8.sqlUpdateAction("system", sqlstr);// tmjcrm
	if (r.flag == "false") {
		alert(r.message);
	} else {
		sAlert("操作成功!", 500);
		document.getElementById(disablestateItem).src = "../../../comon/image/statusaction/un_disable.gif";
		document.getElementById(disablestateItem).onclick = "";
		document.getElementById(ablestateItem).src = "../../../comon/image/statusaction/enable.gif";
		document.getElementById(ablestateItem).onclick = ablestate;
	}
	columnmaingrid.grid.refreshData(refresh);
}
function ablestate() {
	var rowid = columncurrentgrid.CurrentRowId;
	var sqlstr = "update CYEA_NEWSCOLUMN set FCOLUMNSTATE='已启用' where SID = '"
			+ rowid + "'";// TMJ_NEWSCOLUMN
	var r = tlv8.sqlUpdateAction("system", sqlstr);// tmjcrm
	if (r.flag == "false") {
		alert(r.message);
	} else {
		sAlert("操作成功!", 500);
		document.getElementById(ablestateItem).src = "../../../comon/image/statusaction/un_enable.gif";
		document.getElementById(ablestateItem).onclick = "";
		document.getElementById(disablestateItem).src = "../../../comon/image/statusaction/disable.gif";
		document.getElementById(disablestateItem).onclick = disablestate;
	}
	columnmaingrid.grid.refreshData(refresh);
}