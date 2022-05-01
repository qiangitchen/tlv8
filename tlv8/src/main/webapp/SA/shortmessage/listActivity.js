/*==数据源===此项为必须定义==*/
var data = new tlv8.Data();
data.setDbkey("system");// 指定使用数据库连接
data.setTable("MAS_SENDMESSAGE");// 指定grid对应的表
data.setOrderby("SCREATEDATE,SSENDDATE desc");// 指定grid对应的表

/* ==== */
var currentgrid;
function getData() {
	var d = document.getElementById("main-grid-view");
	var labelid = "No,SPHONENUMBER,SMESSAGETEXT,SSTATEBANE,SSENDDATE,SCREATORNAME,SCREATEDATE,STYPE,SSENDTYPE,SERROINFO";// 设置字段
	var labels = "No.,手机号,短信内容,发送状态,发送时间,创建人名称,创建时间,短信类型,发送方式,发送返回状态信息";// 设置标题
	var labelwidth = "40,80,280,80,120,80,120,80,80,160";// 设置宽度
	var datatype = "ro,ro,textarea,ro,ro,ro,ro,ro,ro,textarea";// 设置字段类型
	var dataAction = {
		"queryAction" : "getGridAction",// 查询动作
		"savAction" : "saveAction",// 保存动作
		"deleteAction" : "deleteAction"// 删除动作
	};
	var swhere = "SCREATORID='" + tlv8.Context.getCurrentPersonID() + "'";
	if ("PSN01" == tlv8.Context.getCurrentPersonID()) {
		swhere = ""; // 超级管理员可以修改所有人的配置
	}
	var maingrid = new tlv8.createGrid(d, labelid, labels, labelwidth,
			dataAction, "100%", "100%", data, 20, swhere, "", "", datatype,
			"false", "true");
	// 设置按钮显示{新增、保存、刷新、删除}
	maingrid.grid.settoolbar(false, false, true, false);
	// 设置是否可编辑
	maingrid.grid.seteditModel(false);
	currentgrid = maingrid.grid;
	currentgrid.insertNum = true;// 新增向下(默认新增在第一行)
	currentgrid.refreshData();// 刷新数据
}