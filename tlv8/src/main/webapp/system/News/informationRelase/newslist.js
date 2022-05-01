//发布新闻
var xheditor = null;
var random = Math.random();
var refresh = random + "=" + random;//页面刷新

var data = new tlv8.Data();
data.setTable("cyea_news_release");//TMJ_NEWS_RELEASE
data.setDbkey("system");//tmjcrm
data.setFormId("news_release");// 设置提交的表单
var currentgrid = null;

//新闻列表
function news_list() {
	var div = document.getElementById("news_list_grid");
	var labelid = "No,FNEWSTITLE,FSTATE,FPEOPLE,FTIME,FOPENSCOPE";
	var labels = "序号,新闻标题,状态,发布人,发布时间,发布范围";
	var labelwidth = "60,200,60,100,100,350";
	var datatype = "ro,html:reader,string,ro,date,textarea";//设置字段类型
	var dataAction = {
		"queryAction" : "getGridAction",
		"savAction" : "saveAction",
		"deleteAction" : "deleteAction"
	};
	var maingrid = new tlv8.createGrid(div, labelid, labels, labelwidth,
			dataAction, "100%", "100%", data, 20, "", "", "", datatype,
			"false", "true");
	// 设置按钮显示{新增、保存、刷新、删除}
	maingrid.grid.settoolbar(true, false, true, true);
	// 设置是否可编辑
	maingrid.grid.seteditModel(false);
	currentgrid = maingrid.grid;
	//重写新增事件
	document.getElementById(div.id + "_insertItem").onclick = function() {
		tlv8.portal.openWindow('发布新闻',
				'/system/News/informationRelase/newcolumn.html');
	}
	document.getElementById("news_list_grid" + "_quick_text").style.display = "none";
	document.getElementById("news_list_grid" + "_quick_button").style.display = "none";
	//自定义工具条_取消发布
	stopRoomItem = currentgrid.insertSelfBar("取消发布", "51px", "offstatus()",
			"image/recycle_task.gif");
	maingrid.grid.refreshData(refresh);
}

function reader(event) {
	var html = "<a href='javascript:void(0);' onclick='news_griddbclick(this)'>"
			+ event.value + "</a>";
	return html;
}

function news_griddbclick(obj) {
	setTimeout('QueryDetailAction()', 5);
}

function QueryDetailAction() {
	var rowid = currentgrid.CurrentRowId;
	tlv8.portal
			.openWindow('编辑新闻',
					"/system/News/informationRelase/newcolumn.html?rowid="
							+ rowid);

}

function offstatus() {
	var mygrid = currentgrid;
	var newlistrowid = mygrid.getCurrentRowId();
	mygrid.setValueByName("FSTATE", newlistrowid, "编辑中");
	mygrid.saveData();
	mygrid.refreshData(refresh);
}

function griddbclick(event) {
	var rowid = event.CurrentRowId;
	tlv8.portal
			.openWindow("修改新闻",
					"/system/News/informationRelase/newcolumn.html?rowid="
							+ rowid);
}

//查询新闻
function news_select() {
	var maingrid = document.getElementById("news_list_grid").grid;
	var srcFNEWSTITLE = document.getElementById("srcFNEWSTITLE").value;
	var srcFPEOPLE = document.getElementById("srcFPEOPLE").value;
	var srcFTIME = document.getElementById("srcFTIME").value;
	var filter = " 1=1 ";
	if (srcFNEWSTITLE && srcFNEWSTITLE != "") {
		filter += " and FNEWSTITLE like '%" + trim(srcFNEWSTITLE) + "%' "
	}
	if (srcFPEOPLE && srcFPEOPLE != "") {
		filter += " and FPEOPLE like '%" + trim(srcFPEOPLE) + "%' "
	}
	if (srcFTIME && srcFTIME != "")

	{
		filter += " and  FTIME>='" + trim(srcFTIME) + "'";
	}
	maingrid.refreshData(filter);
}

//行选中事件
function selectedItem(event) {
	var rowid = event.CurrentRowId;
	var state = currentgrid.getValueByName("FSTATE", rowid);
	if (state == "编辑中") {
		currentgrid.settoolbar(true, false, true, true);
	} else {
		currentgrid.settoolbar(true, false, true, "readonly");
	}
	document.getElementById("news_list_grid_insertItem").onclick = function() {
		tlv8.portal.openWindow('发布新闻',
				'/system/News/informationRelase/newcolumn.html');
	}
}

//更多
function onhistory() {
	tlv8.portal.openWindow('新闻列表',
			'/system/News/informationRelase/newsManageLists.html');
}
