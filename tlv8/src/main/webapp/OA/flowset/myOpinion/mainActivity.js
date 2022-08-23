/*==数据源===此项为必须定义==*/
var data = new tlv8.Data();
data.setDbkey("oa");// 指定使用数据库连接
data.setTable("OA_FLOWCONCLUSION");// 指定grid对应的表

/* ==== */
var currentgrid;
function getData() {
	var d = document.getElementById("main-grid-view");
	var labelid = "FORDER,FCONCLUSIONNAME,FCREATORID,FCREATOR";// 设置字段
	var labels = "排序,审批意见,创建人ID,创建人";// 设置标题
	var labelwidth = "80,380,0,80";// 设置宽度
	var datatype = "number,string,string,string";// 设置字段类型
	var dataAction = {
		"queryAction" : "getGridAction",// 查询动作
		"savAction" : "saveAction",// 保存动作
		"deleteAction" : "deleteAction"// 删除动作
	};
	var maingrid = new tlv8.createGrid(d, labelid, labels, labelwidth,
			dataAction, "100%", "100%", data, 20, "FCREATORID='"
					+ tlv8.Context.getCurrentPersonID() + "'", "", "",
			datatype, "false", "false");
	// 设置按钮显示{新增、保存、刷新、删除}
	maingrid.grid.settoolbar(true, true, true, true);
	// 设置是否可编辑
	maingrid.grid.seteditModel(true);
	currentgrid = maingrid.grid;
	currentgrid.insertNum = true;// 新增向下(默认新增在第一行)
	currentgrid.refreshData();// 刷新数据
}

function fInsert(event) {
	currentgrid.setValueByName("FCREATORID", currentgrid.getCurrentRowId(),
			tlv8.Context.getCurrentPersonID());
	currentgrid.setValueByName("FCREATOR", currentgrid.getCurrentRowId(),
			tlv8.Context.getCurrentPersonName());
}