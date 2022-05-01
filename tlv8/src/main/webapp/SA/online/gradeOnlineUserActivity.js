/*==数据源===此项为必须定义==*/
var currentgrid;
var data = new tlv8.Data();
data.setDbkey("system");// 指定使用数据库连接
data.setTable("SA_ONLINEINFO");// 指定grid对应的表

/* ==== */
var currentgrid;
function getData() {
	var d = document.getElementById("main-grid-view");
	var labelid = "No,SUSERID,SUSERNAME,SUSERFID,SUSERFNAME,SLOGINIP,SLOGINDATE,SSESSIONID,SSERVICEIP";// 设置字段
	var labels = "No.,登陆者ID,登陆者,登陆者FID,登陆者全称,登陆IP,登陆时间,登陆session,服务器IP";// 设置标题
	var labelwidth = "40,100,80,180,180,100,120,160,100";// 设置宽度
	var datatype = "ro,string,string,string,string,string,datetime,string,string";// 设置字段类型
	var dataAction = {
		"queryAction" : "getGridAction",// 查询动作
		"savAction" : "saveAction",// 保存动作
		"deleteAction" : "deleteAction"// 删除动作
	};
	var swhere = "SUSERFID like '"+tlv8.Context.getCurrentOgnFID()+"%'";
	var maingrid = new tlv8.createGrid(d, labelid, labels, labelwidth,
			dataAction, "100%", "100%", data, 20, swhere, "", "", datatype,
			"false", "true");
	// 设置按钮显示{新增、保存、刷新、删除}
	maingrid.grid.settoolbar(false, false, true, false);
	// 设置是否可编辑
	maingrid.grid.seteditModel(false);
	currentgrid = maingrid.grid;
	currentgrid.insertNum = true;// 新增向下(默认新增在第一行)
	$(
			"<td width='125px'><div style='border:1px solid #dfe8f6;height:30px;padding:5px;'> 当前在线人数：<span id='onlineCount'> <span></div></td>")
			.insertAfter($("#main-grid-view_queryItem"));
	currentgrid.refreshData();// 刷新数据
}

function fafterRefresh(event) {
	$("#onlineCount").text(event.getLength());
}