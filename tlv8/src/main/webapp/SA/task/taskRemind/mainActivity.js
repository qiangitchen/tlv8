/*==数据源===此项为必须定义==*/
var data = new tlv8.Data();
data.setDbkey("system");//指定使用数据库连接
data.setTable("sa_task");//指定grid对应的表
data.setOrderby("SCREATETIME desc");
		
/*====*/
var currentgrid;
function getData() {
	var d = document.getElementById("main-grid-view");
	var labelid = "No,SNAME,SFLOWID,SPROCESS,SACTIVITY,SCREATETIME,SWARNINGTIME,SLIMITTIME,SDATA1,SEURL";//设置字段
	var labels = "No.,任务标题,SFLOWID,SPROCESS,SACTIVITY,提交时间,办理时限,办理截止时间,SDATA1,SEURL";//设置标题
	var labelwidth = "40,480,0,0,0,140,0,140,0,0";//设置宽度
	var datatype = "ro,string,string,string,string,datetime,ro,date,string,string";//设置字段类型
	var dataAction = {
		"queryAction" : "getGridAction",//查询动作
		"savAction" : "saveAction",//保存动作
		"deleteAction" : "deleteAction"//删除动作
	};
	var currentPersonID = tlv8.Context.getCurrentPersonID();
	var shwere = "SSTATUSID='tesReady' and SEPERSONID='" + currentPersonID + "'";
	var maingrid = new tlv8.createGrid(d, labelid, labels, labelwidth,
		dataAction, "100%", "100%", data, 10, shwere, "", "", datatype,
		"false", "true");
	//设置按钮显示{新增、保存、刷新、删除}
	maingrid.grid.settoolbar(false, false, true, false);
	//设置是否可编辑
	maingrid.grid.seteditModel(false);
	currentgrid = maingrid.grid;
	currentgrid.refreshData();//刷新数据
}

/**
@param {object} event 
*/
function griddbclick(event){
	var taskID = currentgrid.getCurrentRowId();
	tlv8.task.openTask(taskID);
	try{
		top.closeReminds();
	}catch (e) {
	}
}