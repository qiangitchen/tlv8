/*==数据源===此项为必须定义==*/
var currentgrid;
var data = new tlv8.Data();
data.setDbkey("system");// 指定使用数据库连接
data.setTable("Sa_Oporg_recycled");// 指定grid对应的表

/* ==== */
var currentgrid;
function getData() {
	var d = document.getElementById("main-grid-view");
	var labelid = "master_check,No,SCODE,SNAME,SORGKINDID,SFCODE,SFNAME";// 设置字段
	var labels = "master_check,No.,编码,名称,类型,编码全路径,名称全路径";// 设置标题
	var labelwidth = "40,40,80,80,80,350,380";// 设置宽度
	var datatype = "null,ro,ro,ro,ro,ro,ro";// 设置字段类型
	var dataAction = {
		"queryAction" : "getOrgrecycledInfo",// 查询动作
		"savAction" : "",// 保存动作
		"deleteAction" : ""// 删除动作
	};
	var maingrid = new tlv8.createGrid(d, labelid, labels, labelwidth,
			dataAction, "100%", "100%", data, 20, "SVALIDSTATE=-1", "", "",
			datatype, "true", "true");
	// 设置按钮显示{新增、保存、刷新、删除}
	maingrid.grid.settoolbar(false, false, true, false);
	// 设置是否可编辑
	currentgrid = maingrid.grid;
	currentgrid.insertSelfBar("取消删除", "30px", "resetOrgData()",
			"../image/recycle_org.gif");
	currentgrid.insertSelfBar("彻底删除", "30px", "deleteOrgData()",
			"../image/delete_rec.gif");
	currentgrid.refreshData();// 刷新数据
}

// 还原
function resetOrgData() {
	var rowids = currentgrid.checkedRowIds;
	if (!rowids || rowids == "")
		return;
	var param = new tlv8.RequestParam();
	param.set("rowid", rowids);
	tlv8.XMLHttpRequest("resetOrgDataAction", param, "POST", true,
			function(result) {
				if (result.data.flag == "true") {
					currentgrid.refreshData();
				} else {
					alert(result.data.message);
				}
			});
}
// 彻底删除
function deleteOrgData() {
	var rowids = currentgrid.checkedRowIds;
	if (!rowids || rowids == "")
		return;
	if (!confirm("数据将被彻底删除，删除后将不能恢复。确定删除吗？"))
		return;
	var param = new tlv8.RequestParam();
	param.set("rowid", rowids);
	tlv8.XMLHttpRequest("deleteOrgDataAction", param, "POST", true,
			function(result) {
				if (result.data.flag == "true") {
					currentgrid.refreshData();
				} else {
					alert(result.data.message);
				}
			});
}