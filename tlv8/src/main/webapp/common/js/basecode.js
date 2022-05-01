/**
 * 
 */
/* ==数据源===此项为必须定义== */
var data = new tlv8.Data();
data.setDbkey("oa");// 指定使用数据库连接
data.setTable("oa_pub_basecode");// 指定grid对应的表
data.setOrderby("FSEQUENCE asc");
/* ==== */
var currentgrid;
var setenable, setenableall, setdisable;

var scopertype = "";
function getStaticFilter() {
	var swhere = "fCreateOgnID='" + tlv8.Context.getCurrentOgnID()
			+ "' and fScope = '" + scopertype + "' ";
	return swhere;
}

function getData() {
	var d = document.getElementById("main-grid-view");
	var labelid = "No,FUSESTATUS,FUSESTATUSNAME,FSEQUENCE,fCode,fName,fDescription,fCreateOgnID,fCreateOgnName,fCreateDeptID,fCreateDeptName,fCreatePsnID,fCreatePsnName,fCreatePsnFID,fCreateTime,fUpdatePsnID,fUpdatePsnName,fUpdateTime,fScope";// 设置字段
	var labels = "No.,FUSESTATUS,启用状态,排序,编码,名称,描述,fCreateOgnID,fCreateOgnName,fCreateDeptID,fCreateDeptName,fCreatePsnID,fCreatePsnName,fCreatePsnFID,fCreateTime,fUpdatePsnID,fUpdatePsnName,fUpdateTime,fScope";// 设置标题
	var labelwidth = "40,0,80,80,80,180,280,0,0,0,0,0,0,0,0,0,0,0,0";// 设置宽度
	var datatype = "ro,ro,ro,number,string,string,string,string,string,string,string,string,string,string,datetime,string,string,datetime,string";// 设置字段类型
	var dataAction = {
		"queryAction" : "getGridAction",// 查询动作
		"savAction" : "saveAction",// 保存动作
		"deleteAction" : "deleteAction"// 删除动作
	};
	var maingrid = new tlv8.createGrid(d, labelid, labels, labelwidth,
			dataAction, "100%", "100%", data, 20, getStaticFilter(), "", "",
			datatype, "false", "true");
	// 设置按钮显示{新增、保存、刷新、删除}
	maingrid.grid.settoolbar(true, true, true, "readonly");
	// 设置是否可编辑
	maingrid.grid.seteditModel(true);
	currentgrid = maingrid.grid;
	// currentgrid.insertNum = true;// 新增向下(默认新增在第一行)
	currentgrid.setExcelexpBar(true);
	currentgrid.setRequired("fName");// 名称必填
	currentgrid.refreshData();// 刷新数据

	setenable = currentgrid.insertSelfBar("启用", "40px", "", cpath
			+ "/common/image/un_start_use.gif");
	setenableall = currentgrid.insertSelfBar("全部启用", "40px", "setAllenabled()",
			cpath + "/common/image/all_use.gif");
	setdisable = currentgrid.insertSelfBar("停用", "40px", "", cpath
			+ "/common/image/un_stop_use.gif");
}

/**
 * @param {object}
 *            event
 */
function initInsertDataf(event) {
	var rowid = currentgrid.getCurrentRowId();
	currentgrid.setValueByName("FUSESTATUS", rowid, "0");
	currentgrid.setValueByName("FUSESTATUSNAME", rowid, "未启用");
	currentgrid.setValueByName("FSEQUENCE", rowid, "1");
	currentgrid.setValueByName("fCreateOgnID", rowid, tlv8.Context
			.getCurrentOgnID());
	currentgrid.setValueByName("fCreateOgnName", rowid, tlv8.Context
			.getCurrentOgnName());
	currentgrid.setValueByName("fCreateDeptID", rowid, tlv8.Context
			.getCurrentDeptID());
	currentgrid.setValueByName("fCreateDeptName", rowid, tlv8.Context
			.getCurrentDeptName());
	currentgrid.setValueByName("fCreatePsnID", rowid, tlv8.Context
			.getCurrentPersonID());
	currentgrid.setValueByName("fCreatePsnName", rowid, tlv8.Context
			.getCurrentPersonName());
	currentgrid.setValueByName("fCreatePsnFID", rowid, tlv8.Context
			.getCurrentPersonFID());
	currentgrid.setValueByName("fCreateTime", rowid, tlv8.System.Date
			.sysDateTime());
	currentgrid.setValueByName("fScope", rowid, scopertype);
}

/**
 * @param {object}
 *            event {event.olddata 改变前的值, event.newdata 改变后的值, event.cellname
 *            值改变的列名, event.obj 值改变的列(TD)对象, event.rowid 值改变的行id, event非grid对象}
 */
function editbasecodechange(event) {
	var rowid = currentgrid.getCurrentRowId();
	currentgrid.setValueByName("fUpdatePsnID", rowid, tlv8.Context
			.getCurrentPersonID());
	currentgrid.setValueByName("fUpdatePsnName", rowid, tlv8.Context
			.getCurrentPersonName());
	currentgrid.setValueByName("fUpdateTime", rowid, tlv8.System.Date
			.sysDateTime());
}

/**
 * @param {object}
 *            event
 */
function dateselectrow(event) {
	var rowid = currentgrid.getCurrentRowId();
	var status = currentgrid.getValueByName("FUSESTATUS", rowid);
	if (status == "1") {
		currentgrid.setrowState(rowid, "readonly");
		currentgrid.settoolbar("no", "no", "no", "readonly");
		$("#" + setenable)
				.attr("src", cpath + "/common/image/un_start_use.gif");
		document.getElementById(setenable).onclick = "";

		$("#" + setdisable).attr("src", cpath + "/common/image/stop_use.gif");
		document.getElementById(setdisable).onclick = function() {
			currentgrid.setValueByName("FUSESTATUS", rowid, "2");
			currentgrid.setValueByName("FUSESTATUSNAME", rowid, "已停用");
			currentgrid.saveData();
		};
	} else {
		currentgrid.setrowState(rowid, "edit");
		currentgrid.settoolbar("no", "no", "no", true);
		$("#" + setenable).attr("src", cpath + "/common/image/start_use.gif");
		document.getElementById(setenable).onclick = function() {
			currentgrid.setValueByName("FUSESTATUS", rowid, "1");
			currentgrid.setValueByName("FUSESTATUSNAME", rowid, "已启用");
			currentgrid.saveData();
		};

		$("#" + setdisable)
				.attr("src", cpath + "/common/image/un_stop_use.gif");
		document.getElementById(setdisable).onclick = "";
	}
}

function setAllenabled() {
	var len = currentgrid.getLength();
	for (var i = 0; i < len; i++) {
		currentgrid.setValueByName("FUSESTATUS", i, "1");
		currentgrid.setValueByName("FUSESTATUSNAME", i, "已启用");
	}
	currentgrid.saveData();
}