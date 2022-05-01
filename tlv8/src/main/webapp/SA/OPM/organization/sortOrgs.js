/*==数据源===此项为必须定义==*/
var data = new tlv8.Data();
data.setDbkey("system");// 指定使用数据库连接
data.setTable("SA_OPORG");// 指定grid对应的表
data.setOrderby("SSEQUENCE asc");

/* ==== */
var currentgrid, sprowid, currenttreeName;
function getData() {
	sprowid = tlv8.RequestURLParam.getParam("rowid");
	currenttreeName = tlv8.RequestURLParam.getParam("name");
	var d = document.getElementById("main-grid-view");
	var labelid = "SCODE,SNAME,SORGKINDID,SFCODE,SFNAME,SSEQUENCE";// 设置字段
	var labels = "编码,名称,类型,编码路径,全名,排序";// 设置标题
	var labelwidth = "80,80,80,80,80,0";// 设置宽度
	var datatype = "string,string,string,string,string,number";// 设置字段类型
	var dataAction = {
		"queryAction" : "getGridAction",// 查询动作
		"savAction" : "saveAction",// 保存动作
		"deleteAction" : ""// 删除动作{在此不能删除}
	};
	var maingrid = new tlv8.createGrid(d, labelid, labels, labelwidth,
			dataAction, "100%", "100%", data, 9999,
			"SVALIDSTATE !=-1 and SPARENT='" + sprowid
					+ "' and SCODE !='SYSTEM'", "", "", datatype, "false",
			"false");// 9999目的是显示所有行
	// 设置按钮显示{新增、保存、刷新、删除}
	maingrid.grid.settoolbar(false, false, true, false);
	// 设置是否可编辑
	maingrid.grid.seteditModel(false);
	currentgrid = maingrid.grid;
	currentgrid.refreshData();// 刷新数据
}
var currentRowID, currentObj;// 当前行
function selectedItem(event) {
	currentRowID = event.getCurrentRowId();
	currentObj = $("tr[id='" + currentRowID + "']");
	itemStatusch();
}

/*
 * 控制按钮状态
 */
function itemStatusch() {
	var gotopbtn = $("#goTopItem");
	var goUpbtn = $("#goUpItem");
	var goDownbtn = $("#goDownItem");
	var goBottombtn = $("#goBottomItem");
	gotopbtn.unbind("click");
	goUpbtn.unbind("click");
	goDownbtn.unbind("click");
	goBottombtn.unbind("click");
	if (currentObj.prev().length > 0) {
		gotopbtn.attr("src", "../image/goTop.gif");
		gotopbtn.bind("click", goTopAction);
		goUpbtn.attr("src", "../image/goUp.gif");
		goUpbtn.click("bind", goUpAction);
	} else {
		gotopbtn.attr("src", "../image/un_goTop.gif");
		goUpbtn.attr("src", "../image/un_goUp.gif");
	}
	if (currentObj.next().length > 0) {
		goDownbtn.attr("src", "../image/goDown.gif");
		goDownbtn.bind("click", goDownAction);
		goBottombtn.attr("src", "../image/goBottom.gif");
		goBottombtn.bind("click", goBottomAction);
	} else {
		goDownbtn.attr("src", "../image/un_goDown.gif");
		goBottombtn.attr("src", "../image/un_goBottom.gif");
	}
}
// 移动到顶端
function goTopAction() {
	var topObj = currentObj.prevAll();
	topObj = topObj.get(topObj.length - 1);
	var tds = currentObj.find("td");
	var toptds = $(topObj).find("td");
	for (var i = 0; i < tds.length; i++) {
		$(tds.get(i)).attr("style", $(toptds.get(i)).attr("style"));
	}
	currentObj = currentObj.insertBefore($(topObj));
	itemStatusch();
}
// 上移
function goUpAction() {
	var pObj = currentObj.prev();
	if (pObj.prev().length < 1) {
		var tds = currentObj.find("td");
		var toptds = $(pObj).find("td");
		for (var i = 0; i < tds.length; i++) {
			$(tds.get(i)).attr("style", $(toptds.get(i)).attr("style"));
		}
	}
	currentObj = currentObj.insertBefore(pObj);
	itemStatusch();
}
// 下移
function goDownAction() {
	var nObj = currentObj.next();
	if (currentObj.prev().length < 1) {
		var tds = currentObj.find("td");
		var toptds = $(nObj).find("td");
		for (var i = 0; i < tds.length; i++) {
			$(toptds.get(i)).attr("style", $(tds.get(i)).attr("style"));
		}
	}
	currentObj = currentObj.insertAfter(nObj);
	itemStatusch();
}
// 移动到底端
function goBottomAction() {
	var bottomObj = currentObj.nextAll();
	bottomObj = bottomObj.get(bottomObj.length - 1);
	if (currentObj.prev().length < 1) {
		var tds = currentObj.find("td");
		var toptds = $(currentObj.next()).find("td");
		for (var i = 0; i < tds.length; i++) {
			$(toptds.get(i)).attr("style", $(tds.get(i)).attr("style"));
		}
	}
	currentObj = currentObj.insertAfter(bottomObj);
	itemStatusch();
}

function UpdateSEQUENCE() {
	var rowids = [];
	var tabody = document.getElementById("main-grid-view_main_grid");
	var trs = tabody.rows;
	for (var i = 0; i < trs.length; i++) {
		rowids.push(trs[i].id);
	}
	var param = new tlv8.RequestParam();
	param.set("idlist", rowids.join(","));
	param.set("orgkind", "SA_OPORG");
	tlv8.XMLHttpRequest("sortOrgAction", param, "post", false, null);
}

function dailogEngin() {
	UpdateSEQUENCE();
	return sprowid;
}