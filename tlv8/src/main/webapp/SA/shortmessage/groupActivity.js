/*=======主表配置======*/
var Maingrid;
var MainData = new tlv8.Data();
function getData() {
	MainData.setDbkey("system");// 指定使用数据库连接
	MainData.setTable("MAS_SENDGROUP");
	MainData.setFormId("direct_form");// 主表关联form
	var d = document.getElementById("direct_from_grid");
	var labelid = "SNAME,SCREATORID,SCREATORNAME,SCREATEDATE";// 设置字段
	var labels = "组群名称,创建人ID,创建人,创建时间";// 设置标题
	var labelwidth = "200,0,0,0";// 设置宽度
	var datatype = "string,ro,ro,ro";// 设置字段类型
	var dataAction = {
		"queryAction" : "getGridAction",
		"savAction" : "saveAction",
		"deleteAction" : "deleteAction"
	};
	var maingrid = new tlv8.createGrid(d, labelid, labels, labelwidth,
			dataAction, "100%", "100%", MainData, 20, "", "", "", datatype,
			"false", "false");
	maingrid.grid.settoolbar(true, true, true, true);// 设置按钮显示{新增、保存、刷新、删除}
	maingrid.grid.seteditModel(true);// 设置是否可编辑
	Maingrid = maingrid.grid;
	Maingrid.refreshData();
	$("#direct_from_grid_queryItem").hide();
	$("#direct_from_grid-grid-item").parent().hide();
}

/* ======从表配置====== */
var SubData = new tlv8.Data();
var subGrid;
function getData2() {
	SubData.setDbkey("system");// 指定使用数据库连接
	SubData.setTable("MAS_SENDGROUPLIST");
	var d = document.getElementById("direct_from_subgrid");
	var labelid = "No,SDEPTID,SDEPTNAME,SPERSONID,SPERONNAME,SPHONENUMBER";// 设置字段
	var labels = "No.,部门ID,科室名称,人员ID,人员名称,手机号";// 设置标题
	var labelwidth = "40,0,100,0,80,160";// 设置宽度
	var datatype = "ro,ro,ro,ro,ro,ro";// 设置字段类型
	var dataAction = {
		"queryAction" : "getGridAction",
		"savAction" : "saveAction",
		"deleteAction" : "deleteAction"
	};
	var maingrid = new tlv8.createGrid(d, labelid, labels, labelwidth,
			dataAction, "100%", "100%", SubData, 20, "", "direct_form",
			"SBILLID", datatype, "false", "true");
	maingrid.grid.settoolbar(true, false, true, true);// 设置按钮显示{新增、保存、刷新、删除}
	maingrid.grid.seteditModel(false);// 设置是否可编辑
	subGrid = maingrid.grid;
}

/*
 * 从表值改变时改变主表按钮状态
 */
function cheng_stae_bar() {
	document.getElementById("direct_from_grid").grid.settoolbar("no", true,
			"no", "no");// no 代表不改变
}

/*
 * 主表换行时刷新从表
 */
function refresh_child_data() {
	document.getElementById("direct_from_subgrid").grid.refreshData();
}

function bodyResize() {
	getData();
	getData2();
}

function fRefreshfun(event) {
	J$("direct_from_subgrid_insertItem").onclick = psmSelect;
}

function psmSelect() {
	var mrowid = Maingrid.getCurrentRowId();
	if (mrowid && mrowid != "") {
		tlv8.portal.dailog.openDailog("选择人员",
				"/comon/SelectDialogPsn/SelectChPsm.html", 600, 400,
				selectcallback);
	} else {
		alert("请先创建群组!");
	}
}

function selectcallback(rdata) {
	var ids = rdata.id.split(",");
	var names = rdata.name.split(",");
	for (var i = 0; i < ids.length; i++) {
		var param = new tlv8.RequestParam();
		param.set("personid", ids[i]);
		var r = tlv8.XMLHttpRequest("queryOrgViewByPsmIDAction", param,
				"post", false);
		var orgdata = r.data.data;
		if (typeof orgdata == "string") {
			orgdata = window.eval("(" + orgdata + ")");
		}
		var newid = subGrid.insertData();
		subGrid.setValueByName("SPERSONID", newid, ids[i]);
		subGrid.setValueByName("SPERONNAME", newid, orgdata.personName);
		subGrid.setValueByName("SDEPTID", newid, orgdata.deptID);
		subGrid.setValueByName("SDEPTNAME", newid, orgdata.deptName);
		subGrid.setValueByName("SPHONENUMBER", newid, orgdata.SMOBILEPHONE);
	}
	subGrid.saveData();
}