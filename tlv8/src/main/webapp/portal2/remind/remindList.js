var stsearchty;
$(function() {
	stsearchty = $(".ssel");
	$(".sstates").click(function() {
		if (stsearchty != $(this)) {
			stsearchty.removeClass("ssel");
			$(this).addClass("ssel");
			stsearchty = $(this);
		}
	});
	getData();
	$(".toolbar").hide();
});

/* ==数据源===此项为必须定义== */
var data = new tlv8.Data();
data.setDbkey("system");// 指定使用数据库连接
data.setTable("SA_REMINDINFO");// 指定grid对应的表
data.setOrderby("SSTATE asc, SDATETIME asc");
/* ==== */
var currentgrid;
function getData() {
	var d = document.getElementById("main-grid-view");
	var labelid = "No,STITLE,SCONTEXT,SDATETIME,SSTATE,SACTION";// 设置字段
	var labels = "No.,标题,描述,时间,状态,操作";// 设置标题
	var labelwidth = "40,150,560,120,80,120";// 设置宽度
	var datatype = "ro,string,string,date,string,html:readActionHm";// 设置字段类型
	var dataAction = {
		"queryAction" : "getGridAction",// 查询动作
		"savAction" : "saveAction",// 保存动作
		"deleteAction" : "deleteAction"// 删除动作
	};
	var swhere = "SPERSONID = '" + tlv8.Context.getCurrentPersonID() + "'";
	var maingrid = new tlv8.createGrid(d, labelid, labels, labelwidth,
			dataAction, "100%", "100%", data, 20, swhere, "", "", datatype,
			"false", "true");
	// 设置按钮显示{新增、保存、刷新、删除}
	maingrid.grid.settoolbar(false, false, true, false);
	// 设置是否可编辑
	maingrid.grid.seteditModel(false);
	currentgrid = maingrid.grid;
	currentgrid.refreshData();// 刷新数据
}

function doSearchAction() {
	var myFilter = [];
	if (stsearchty.text() != "全部") {
		myFilter.push("SSTATE='" + stsearchty.text() + "'");
	}
	var sTiletx = $("#sTile").val();
	if (sTiletx && sTiletx != "") {
		myFilter.push("STITLE like '%" + sTiletx + "%'");
	}
	var sStartDatetx = $("#sStartDate").val();
	if (sStartDatetx && sStartDatetx != "") {
		myFilter.push("SDATETIME >= to_date('" + sStartDatetx
				+ "','yyyy-MM-dd')");
	}
	var sEndDatetx = $("#sEndDate").val();
	if (sEndDatetx && sEndDatetx != "") {
		myFilter
				.push("SDATETIME <= to_date('" + sEndDatetx + "','yyyy-MM-dd')");
	}
	currentgrid.refreshData(myFilter.join(" and "));// 刷新数据
}

function readActionHm(event) {
	var rowid = event.rowid;
	var sstate = currentgrid.getValueByName("SSTATE", rowid);
	var rhtml = "<div style='width:100%;text-align:center;'>";
	if (sstate == "未处理") {
		rhtml += "<a href='javascript:void(0);' onclick='remAct(\"" + rowid
				+ "\")'>处理</a>";
	} else {
		// rhtml += "<a href='javascript:void(0);'>处理</a>";
	}
	rhtml += "</div>";
	return rhtml;
}

function remAct(rowid) {
	currentgrid.setValueByName("SSTATE", rowid, "已处理");
	currentgrid.saveData();
}