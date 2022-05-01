/*==数据源===此项为必须定义==*/
var data = new tlv8.Data();
data.setDbkey("oa");//指定使用数据库连接
data.setTable("oa_leave");//指定grid对应的表
		
/*====*/
var currentgrid;
function getData() {
	var d = document.getElementById("main-grid-view");
	var labelid = "No,FCREATORNAME,FCREATEDATE,FDAY,FLEAVETYPE";//设置字段
	var labels = "No.,申请人名称,申请时间,请假天数,请假类型";//设置标题
	var labelwidth = "40,100,80,80,80";//设置宽度
	var datatype = "ro,string,datetime,number,string";//设置字段类型
	var dataAction = {
		"queryAction" : "getGridAction",//查询动作
		"savAction" : "saveAction",//保存动作
		"deleteAction" : "deleteAction"//删除动作
	};
	var maingrid = new tlv8.createGrid(d, labelid, labels, labelwidth,
		dataAction, "100%", "100%", data, 20, "", "", "", datatype,
		"false", "true");
	//设置按钮显示{新增、保存、刷新、删除}
	maingrid.grid.settoolbar(false, false, true, false);
	//设置是否可编辑
	maingrid.grid.seteditModel(false);
	currentgrid = maingrid.grid;
	currentgrid.refreshData();//刷新数据
}

function griddbclick(event){
	var rowid = currentgrid.getCurrentRowId();
	tlv8.portal.openWindow('列表详细', '/demo/listportaldetail/listActivityDetail.html?rowid='+rowid);
}