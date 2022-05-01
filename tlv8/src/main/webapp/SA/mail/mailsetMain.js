/*==数据源===此项为必须定义==*/
var data = new tlv8.Data();
data.setDbkey("system");// 指定使用数据库连接
data.setTable("sa_mailset");// 指定grid对应的表

/* ==== */
var currentgrid;
function getData() {
	var d = document.getElementById("main-grid-view");
	var labelid = "No,SMAIL,SNAME,STYPE,SSENDHOST,SSENDPOST,SISSSL,SRECHOST,SRECPORT,SRECSSL,SISPUB,FCREATEPSNNAME,FCREATETIME";// 设置字段
	var labels = "No.,邮箱地址,发信名称,邮箱类型,发件服务器,发件端口,发件是否启用SSL,收件服务器,收件端口,收件是否启用SSL,是否公用,创建人名称,创建时间";// 设置标题
	var labelwidth = "40,180,120,80,150,80,130,180,80,130,80,100,140";// 设置宽度
	var datatype = "ro,string,string,string,string,string,string,string,string,string,string,string,datetime";// 设置字段类型
	var dataAction = {
		"queryAction" : "getGridAction",// 查询动作
		"savAction" : "saveAction",// 保存动作
		"deleteAction" : "deleteAction"// 删除动作
	};
	var maingrid = new tlv8.createGrid(d, labelid, labels, labelwidth,
			dataAction, "100%", "100%", data, 20, "", "", "", datatype,
			"false", "true");
	// 设置按钮显示{新增、保存、刷新、删除}
	maingrid.grid.settoolbar(true, false, true, false);
	// 设置是否可编辑
	maingrid.grid.seteditModel(false);
	currentgrid = maingrid.grid;
	currentgrid.refreshData();// 刷新数据

	document.getElementById(d.id + "_insertItem").onclick = function() {
		tlv8.portal.dailog.openDailog("添加/修改配置", "/SA/mail/writeActivity.html",
				600, 500, function(re) {
					currentgrid.refreshData();// 刷新数据
				});
	};
}

/**
 * @param {object}
 *            event
 */
function rowdbcal(event) {
	var rowid = currentgrid.getCurrentRowId();
	tlv8.portal.dailog.openDailog("添加/修改配置",
			"/SA/mail/writeActivity.html?rowid=" + rowid, 600, 500,
			function(re) {
				currentgrid.refreshData();// 刷新数据
			});
}

/**
@param {object} event 
*/
function rgridsel(event){
	currentgrid.settoolbar(false, false, true, false);
}