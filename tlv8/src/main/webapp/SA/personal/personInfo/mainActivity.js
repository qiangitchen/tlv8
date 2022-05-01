//工具条
var toolbarItem;
function init_toolbar() {
	var bardiv = J$("stander_bar");
	var toobar = new tlv8.toolbar(bardiv, false, true, false, true);
	toolbarItem = toobar.items;
}

// 数据配置
var datamian = new tlv8.Data();
datamian.setDbkey("system");
datamian.setTable("SA_OPPERSON");
function initDocumentPage() {
	datamian.setFormId("MAIN_DATA_FORM");

	// 创建grid
	getData();
	getworkData();
	getadvancedsData();
	getcultivateData();
	getcertificateData();
	getthesisData();
	getOtherData();
	getprojectData();
	getalterationData();
	getcreditData();

	var personid = tlv8.Context.getCurrentPersonID();
	document.getElementById("MAIN_DATA_FORM").rowid = personid;
	datamian.setFilter("SID='" + personid + "'");
	datamian.refreshData();
	var cellname = "SPHOTO";// 设置附件相关字段
	var div = document.getElementById("picDemo");
	tlv8.picComponent(div, datamian, cellname, true);

	$(window).resize(function() {
		$("#bodymainlayout").layout('resize');
		$("#centerlayout").layout('resize');
		$("#mainlayout").panel('resize');
		$("#infotabs").tabs('resize');
	});
}

// 新增数据
function dataInsert() {
	J$("MAIN_DATA_FORM").reset();
	J$("MAIN_DATA_FORM").rowid = "";
	J$("MAIN_DATA_FORM").setAttribute("rowid", "");
	$("#MAIN_DATA_FORM").attr("rowid", "");
}

// 数据保存
function dataSave() {
	var rowid = datamian.saveData();
	J$("MAIN_DATA_FORM").rowid = rowid;
	J$("MAIN_DATA_FORM").setAttribute("rowid", rowid);
	$("#MAIN_DATA_FORM").attr("rowid", rowid);
}

// 数据刷新
function dataRefresh() {
	datamian.refreshData();
}

// 数据删除
function dataDeleted() {
	if (datamian.deleteData()) {
		dataRefresh();
	}
}

function afRefresh() {
	var cellname = "SPHOTO";// 设置附件相关字段
	var div = document.getElementById("picDemo");
	tlv8.picComponent(div, datamian, cellname, true);
}

function gridfInsert(event) {
	var rowid = event.getCurrentRowId();
	event.setValueByName("STYPE", rowid, currentType);
	event.setValueByName("SUPDATEDATE", rowid, tlv8.System.Date
		.sysDateTime());
	event.setValueByName("SUPDATORID", rowid, tlv8.Context
		.getCurrentPersonID());
	event.setValueByName("SUPDATORNAME", rowid, tlv8.Context
		.getCurrentPersonName());
}

function dataValueChangedAction(event) {
	var rowid = event.rowid;
	currentgrid.setValueByName("SUPDATEDATE", rowid, tlv8.System.Date
		.sysDateTime());
	currentgrid.setValueByName("SUPDATORID", rowid, tlv8.Context
		.getCurrentPersonID());
	currentgrid.setValueByName("SUPDATORNAME", rowid, tlv8.Context
		.getCurrentPersonName());
}

/* ===学习经历= */
var studygrid;
function getData() {
	var data = new tlv8.Data();
	data.setDbkey("system");// 指定使用数据库连接
	data.setTable("SA_OPPERSON_DEATAIL");// 指定grid对应的表
	data.setOrderby(" SSQUNS asc");
	var d = document.getElementById("study_per");
	var labelid = "SSQUNS,SCODE,SNAME,SREMARK,SPERSONID,STYPE,SCLASS,SUPDATEDATE,SUPDATORID,SUPDATORNAME";// 设置字段
	var labels = "序号,学校,专业,描述,人员ID,类型,SCLASS,修改时间,SUPDATORID,修改人";// 设置标题
	var labelwidth = "40,120,100,300,0,0,0,120,0,80";// 设置宽度
	var datatype = "number,string,string,textarea,string,string,string,ro,ro,ro";// 设置字段类型
	var dataAction = {
		"queryAction": "getGridAction",// 查询动作
		"savAction": "saveAction",// 保存动作
		"deleteAction": "deleteAction"// 删除动作
	};
	var maingrid = new tlv8.createGrid(d, labelid, labels, labelwidth,
		dataAction, "100%", "100%", data, 10, "STYPE='学习经历'",
		"MAIN_DATA_FORM", "SPERSONID", datatype, "false", "false");
	// 设置按钮显示{新增、保存、刷新、删除}
	maingrid.grid.settoolbar(true, false, true, true);
	// 设置是否可编辑
	maingrid.grid.seteditModel(true);
	studygrid = maingrid.grid;
	studygrid.insertNum = true;// 新增向下(默认新增在第一行)
}

/* ===工作经历= */
var workpergrid;
function getworkData() {
	var data = new tlv8.Data();
	data.setDbkey("system");// 指定使用数据库连接
	data.setTable("SA_OPPERSON_DEATAIL");// 指定grid对应的表
	data.setOrderby(" SSQUNS asc");
	var d = document.getElementById("work_per");
	var labelid = "SSQUNS,SCODE,SNAME,SREMARK,SPERSONID,STYPE,SCLASS,SUPDATEDATE,SUPDATORID,SUPDATORNAME";// 设置字段
	var labels = "序号,公司（单位）,岗位,描述,人员ID,类型,SCLASS,修改时间,SUPDATORID,修改人";// 设置标题
	var labelwidth = "40,120,100,300,0,0,0,120,0,80";// 设置宽度
	var datatype = "number,string,string,textarea,string,string,string,ro,ro,ro";// 设置字段类型
	var dataAction = {
		"queryAction": "getGridAction",// 查询动作
		"savAction": "saveAction",// 保存动作
		"deleteAction": "deleteAction"// 删除动作
	};
	var maingrid = new tlv8.createGrid(d, labelid, labels, labelwidth,
		dataAction, "100%", "100%", data, 10, "STYPE='工作经历'",
		"MAIN_DATA_FORM", "SPERSONID", datatype, "false", "false");
	// 设置按钮显示{新增、保存、刷新、删除}
	maingrid.grid.settoolbar(true, false, true, true);
	// 设置是否可编辑
	maingrid.grid.seteditModel(true);
	workpergrid = maingrid.grid;
	workpergrid.insertNum = true;// 新增向下(默认新增在第一行)
}

/* ===进修经历= */
var advancedspergrid;
function getadvancedsData() {
	var data = new tlv8.Data();
	data.setDbkey("system");// 指定使用数据库连接
	data.setTable("SA_OPPERSON_DEATAIL");// 指定grid对应的表
	data.setOrderby(" SSQUNS asc");
	var d = document.getElementById("advanceds_per");
	var labelid = "SSQUNS,SCODE,SNAME,SREMARK,SPERSONID,STYPE,SCLASS,SUPDATEDATE,SUPDATORID,SUPDATORNAME";// 设置字段
	var labels = "序号,院校,专业,描述,人员ID,类型,SCLASS,修改时间,SUPDATORID,修改人";// 设置标题
	var labelwidth = "40,120,100,300,0,0,0,120,0,80";// 设置宽度
	var datatype = "number,string,string,textarea,string,string,string,ro,ro,ro";// 设置字段类型
	var dataAction = {
		"queryAction": "getGridAction",// 查询动作
		"savAction": "saveAction",// 保存动作
		"deleteAction": "deleteAction"// 删除动作
	};
	var maingrid = new tlv8.createGrid(d, labelid, labels, labelwidth,
		dataAction, "100%", "100%", data, 10, "STYPE='进修经历'",
		"MAIN_DATA_FORM", "SPERSONID", datatype, "false", "false");
	// 设置按钮显示{新增、保存、刷新、删除}
	maingrid.grid.settoolbar(true, false, true, true);
	// 设置是否可编辑
	maingrid.grid.seteditModel(true);
	advancedspergrid = maingrid.grid;
	advancedspergrid.insertNum = true;// 新增向下(默认新增在第一行)
}

/* ===培训经历= */
var cultivatepergrid;
function getcultivateData() {
	var data = new tlv8.Data();
	data.setDbkey("system");// 指定使用数据库连接
	data.setTable("SA_OPPERSON_DEATAIL");// 指定grid对应的表
	data.setOrderby(" SSQUNS asc");
	var d = document.getElementById("cultivate_per");
	var labelid = "SSQUNS,SCODE,SNAME,SREMARK,SPERSONID,STYPE,SCLASS,SUPDATEDATE,SUPDATORID,SUPDATORNAME";// 设置字段
	var labels = "序号,培训类型,培训机构,培训内容,人员ID,类型,SCLASS,修改时间,SUPDATORID,修改人";// 设置标题
	var labelwidth = "40,120,100,300,0,0,0,120,0,80";// 设置宽度
	var datatype = "number,string,string,textarea,string,string,string,ro,ro,ro";// 设置字段类型
	var dataAction = {
		"queryAction": "getGridAction",// 查询动作
		"savAction": "saveAction",// 保存动作
		"deleteAction": "deleteAction"// 删除动作
	};
	var maingrid = new tlv8.createGrid(d, labelid, labels, labelwidth,
		dataAction, "100%", "100%", data, 10, "STYPE='培训经历'",
		"MAIN_DATA_FORM", "SPERSONID", datatype, "false", "false");
	// 设置按钮显示{新增、保存、刷新、删除}
	maingrid.grid.settoolbar(true, false, true, true);
	// 设置是否可编辑
	maingrid.grid.seteditModel(true);
	cultivatepergrid = maingrid.grid;
	cultivatepergrid.insertNum = true;// 新增向下(默认新增在第一行)
}

/* ===资质证书= */
var certificatepergrid;
function getcertificateData() {
	var data = new tlv8.Data();
	data.setDbkey("system");// 指定使用数据库连接
	data.setTable("SA_OPPERSON_DEATAIL");// 指定grid对应的表
	data.setOrderby(" SSQUNS asc");
	var d = document.getElementById("certificate_per");
	var labelid = "SSQUNS,SCODE,SNAME,SREMARK,SPERSONID,STYPE,SCLASS,SUPDATEDATE,SUPDATORID,SUPDATORNAME";// 设置字段
	var labels = "序号,证书名称,颁发机构,证书描述,人员ID,类型,SCLASS,修改时间,SUPDATORID,修改人";// 设置标题
	var labelwidth = "40,120,100,300,0,0,0,120,0,80";// 设置宽度
	var datatype = "number,string,string,textarea,string,string,string,ro,ro,ro";// 设置字段类型
	var dataAction = {
		"queryAction": "getGridAction",// 查询动作
		"savAction": "saveAction",// 保存动作
		"deleteAction": "deleteAction"// 删除动作
	};
	var maingrid = new tlv8.createGrid(d, labelid, labels, labelwidth,
		dataAction, "100%", "100%", data, 10, "STYPE='资质证书'",
		"MAIN_DATA_FORM", "SPERSONID", datatype, "false", "false");
	// 设置按钮显示{新增、保存、刷新、删除}
	maingrid.grid.settoolbar(true, false, true, true);
	// 设置是否可编辑
	maingrid.grid.seteditModel(true);
	certificatepergrid = maingrid.grid;
	certificatepergrid.insertNum = true;// 新增向下(默认新增在第一行)
}

/* ===著作论文= */
var thesispergrid;
function getthesisData() {
	var data = new tlv8.Data();
	data.setDbkey("system");// 指定使用数据库连接
	data.setTable("SA_OPPERSON_DEATAIL");// 指定grid对应的表
	data.setOrderby(" SSQUNS asc");
	var d = document.getElementById("thesis_per");
	var labelid = "SSQUNS,SCODE,SNAME,SREMARK,SPERSONID,STYPE,SCLASS,SUPDATEDATE,SUPDATORID,SUPDATORNAME";// 设置字段
	var labels = "序号,论文标题,发表媒体,论文描述,人员ID,类型,SCLASS,修改时间,SUPDATORID,修改人";// 设置标题
	var labelwidth = "40,120,100,300,0,0,0,120,0,80";// 设置宽度
	var datatype = "number,string,string,textarea,string,string,string,ro,ro,ro";// 设置字段类型
	var dataAction = {
		"queryAction": "getGridAction",// 查询动作
		"savAction": "saveAction",// 保存动作
		"deleteAction": "deleteAction"// 删除动作
	};
	var maingrid = new tlv8.createGrid(d, labelid, labels, labelwidth,
		dataAction, "100%", "100%", data, 10, "STYPE='著作论文'",
		"MAIN_DATA_FORM", "SPERSONID", datatype, "false", "false");
	// 设置按钮显示{新增、保存、刷新、删除}
	maingrid.grid.settoolbar(true, false, true, true);
	// 设置是否可编辑
	maingrid.grid.seteditModel(true);
	thesispergrid = maingrid.grid;
	thesispergrid.insertNum = true;// 新增向下(默认新增在第一行)
}

/* ===其他信息= */
var otherpergrid;
function getOtherData() {
	var data = new tlv8.Data();
	data.setDbkey("system");// 指定使用数据库连接
	data.setTable("SA_OPPERSON_DEATAIL");// 指定grid对应的表
	data.setOrderby(" SSQUNS asc");
	var d = document.getElementById("other_per");
	var labelid = "SSQUNS,SCODE,SNAME,SREMARK,SPERSONID,STYPE,SCLASS,SUPDATEDATE,SUPDATORID,SUPDATORNAME";// 设置字段
	var labels = "序号,类型,名称,描述,人员ID,类型,SCLASS,修改时间,SUPDATORID,修改人";// 设置标题
	var labelwidth = "40,120,100,300,0,0,0,120,0,80";// 设置宽度
	var datatype = "number,string,string,textarea,string,string,string,ro,ro,ro";// 设置字段类型
	var dataAction = {
		"queryAction": "getGridAction",// 查询动作
		"savAction": "saveAction",// 保存动作
		"deleteAction": "deleteAction"// 删除动作
	};
	var maingrid = new tlv8.createGrid(d, labelid, labels, labelwidth,
		dataAction, "100%", "100%", data, 10, "STYPE='其他信息'",
		"MAIN_DATA_FORM", "SPERSONID", datatype, "false", "false");
	// 设置按钮显示{新增、保存、刷新、删除}
	maingrid.grid.settoolbar(true, false, true, true);
	// 设置是否可编辑
	maingrid.grid.seteditModel(true);
	otherpergrid = maingrid.grid;
	otherpergrid.insertNum = true;// 新增向下(默认新增在第一行)
}

/* ===科研项目= */
var projectpergrid;
function getprojectData() {
	var data = new tlv8.Data();
	data.setDbkey("system");// 指定使用数据库连接
	data.setTable("SA_OPPERSON_DEATAIL");// 指定grid对应的表
	data.setOrderby(" SSQUNS asc");
	var d = document.getElementById("project_per");
	var labelid = "SSQUNS,SCODE,SNAME,SREMARK,SPERSONID,STYPE,SCLASS,SUPDATEDATE,SUPDATORID,SUPDATORNAME";// 设置字段
	var labels = "序号,项目类型,项目名称,项目描述,人员ID,类型,SCLASS,修改时间,SUPDATORID,修改人";// 设置标题
	var labelwidth = "40,120,100,300,0,0,0,120,0,80";// 设置宽度
	var datatype = "number,string,string,textarea,string,string,string,ro,ro,ro";// 设置字段类型
	var dataAction = {
		"queryAction": "getGridAction",// 查询动作
		"savAction": "saveAction",// 保存动作
		"deleteAction": "deleteAction"// 删除动作
	};
	var maingrid = new tlv8.createGrid(d, labelid, labels, labelwidth,
		dataAction, "100%", "100%", data, 10, "STYPE='科研项目'",
		"MAIN_DATA_FORM", "SPERSONID", datatype, "false", "false");
	// 设置按钮显示{新增、保存、刷新、删除}
	maingrid.grid.settoolbar(true, false, true, true);
	// 设置是否可编辑
	maingrid.grid.seteditModel(true);
	projectpergrid = maingrid.grid;
	projectpergrid.insertNum = true;// 新增向下(默认新增在第一行)
}

/* ===技术职务变更= */
var alterationpergrid;
function getalterationData() {
	var data = new tlv8.Data();
	data.setDbkey("system");// 指定使用数据库连接
	data.setTable("SA_OPPERSON_DEATAIL");// 指定grid对应的表
	data.setOrderby(" SSQUNS asc");
	var d = document.getElementById("alteration_per");
	var labelid = "SSQUNS,SCODE,SNAME,SREMARK,SPERSONID,STYPE,SCLASS,SUPDATEDATE,SUPDATORID,SUPDATORNAME";// 设置字段
	var labels = "序号,变更前,变更后,描述,人员ID,类型,SCLASS,修改时间,SUPDATORID,修改人";// 设置标题
	var labelwidth = "40,120,100,300,0,0,0,120,0,80";// 设置宽度
	var datatype = "number,string,string,textarea,string,string,string,ro,ro,ro";// 设置字段类型
	var dataAction = {
		"queryAction": "getGridAction",// 查询动作
		"savAction": "saveAction",// 保存动作
		"deleteAction": "deleteAction"// 删除动作
	};
	var maingrid = new tlv8.createGrid(d, labelid, labels, labelwidth,
		dataAction, "100%", "100%", data, 10, "STYPE='技术职务变更'",
		"MAIN_DATA_FORM", "SPERSONID", datatype, "false", "false");
	// 设置按钮显示{新增、保存、刷新、删除}
	maingrid.grid.settoolbar(true, false, true, true);
	// 设置是否可编辑
	maingrid.grid.seteditModel(true);
	alterationpergrid = maingrid.grid;
	alterationpergrid.insertNum = true;// 新增向下(默认新增在第一行)
}

/* ===学分完成情况= */
var creditpergrid;
function getcreditData() {
	var data = new tlv8.Data();
	data.setDbkey("system");// 指定使用数据库连接
	data.setTable("SA_OPPERSON_DEATAIL");// 指定grid对应的表
	data.setOrderby(" SSQUNS asc");
	var d = document.getElementById("credit_per");
	var labelid = "SSQUNS,SCODE,SNAME,SREMARK,SPERSONID,STYPE,SCLASS,SUPDATEDATE,SUPDATORID,SUPDATORNAME";// 设置字段
	var labels = "序号,应修学分,已修学分,说明,人员ID,类型,SCLASS,修改时间,SUPDATORID,修改人";// 设置标题
	var labelwidth = "40,120,100,300,0,0,0,120,0,80";// 设置宽度
	var datatype = "number,string,string,textarea,string,string,string,ro,ro,ro";// 设置字段类型
	var dataAction = {
		"queryAction": "getGridAction",// 查询动作
		"savAction": "saveAction",// 保存动作
		"deleteAction": "deleteAction"// 删除动作
	};
	var maingrid = new tlv8.createGrid(d, labelid, labels, labelwidth,
		dataAction, "100%", "100%", data, 10, "STYPE='学分完成情况'",
		"MAIN_DATA_FORM", "SPERSONID", datatype, "false", "false");
	// 设置按钮显示{新增、保存、刷新、删除}
	maingrid.grid.settoolbar(true, false, true, true);
	// 设置是否可编辑
	maingrid.grid.seteditModel(true);
	creditpergrid = maingrid.grid;
	creditpergrid.insertNum = true;// 新增向下(默认新增在第一行)
}

var currentgrid;
var currentType;
function tabselected(title, index) {
	currentType = title;
	try {
		switch (index) {
			case 0:
				currentgrid = studygrid;
				currentgrid.refreshData();
				break;
			case 1:
				currentgrid = workpergrid;
				workpergrid.refreshData();
				break;
			case 2:
				currentgrid = advancedspergrid;
				advancedspergrid.refreshData();
				break;
			case 3:
				currentgrid = cultivatepergrid;
				cultivatepergrid.refreshData();
				break;
			case 4:
				currentgrid = certificatepergrid;
				certificatepergrid.refreshData();
				break;
			case 5:
				currentgrid = thesispergrid;
				thesispergrid.refreshData();
				break;
			case 6:
				currentgrid = otherpergrid;
				otherpergrid.refreshData();
				break;
			case 7:
				currentgrid = projectpergrid;
				projectpergrid.refreshData();
				break;
			case 8:
				currentgrid = alterationpergrid;
				alterationpergrid.refreshData();
				break;
			case 9:
				currentgrid = creditpergrid;
				creditpergrid.refreshData();
				break;
			default:
				break;
		}
	} catch (e) {
	}
}