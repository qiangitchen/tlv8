var currentgrid = null;
/*==数据源===此项为必须定义==*/
var data = new tlv8.Data();
data.setTable("SA_Log");
data.setDbkey("system");
//data.setOrderby("sCreateTime desc");
/*====*/
function getData() {
	var d = document.getElementById("maingridview");
	var labelid = "No,sActivityName,sProcessName,sCreatorPersonName,sActionName,sCreateTime,sTypeName,sIP,sDescription";
	var labels = "No.,功能,功能模块,操作者,操作,操作时间,类别,IP地址,描述";
	var labelwidth = "40,120,260,60,60,120,0,110,220";
	var datatype = "ro,string,string,string,string,datetime,string,string,textarea";//设置字段类型
	var dataAction = {
		"queryAction" : "getGridAction",
		"savAction" : "",
		"deleteAction" : ""
	};
	var maingrid = new tlv8.createGrid(d, labelid, labels, labelwidth,
			dataAction, "100%", "100%", data, 20, "", "", "", datatype,
			"false", "true");
	//设置按钮显示{新增、保存、刷新、删除}
	maingrid.grid.settoolbar(false, false, true, false);
	//设置是否可编辑
	maingrid.grid.seteditModel(false);
	currentgrid = maingrid.grid;
	currentgrid.refreshData();
}

function select_body_view(id){
	$(".DetailInfoDiv").hide();
	$("#"+id).show();
	if(id=="base_list"){
		$("#base_list_a").get(0).className = "DetailliAD";
		$("#base_detail_a").get(0).className = "DetailliAS";
		$("#base_list_LI").get(0).className = "Detailli";
		$("#base_detail_LI").get(0).className = "Detailli_un";
	}else{
		$("#base_list_a").get(0).className = "DetailliAS";
		$("#base_detail_a").get(0).className = "DetailliAD";
		if($("#TimeStatisticsFrame").get(0).src=="about:blanck;"){
			$("#TimeStatisticsFrame").get(0).src==cpath+"/system/log/TimeStatistics.jsp";
		}
		$("#base_list_LI").get(0).className = "Detailli_un";
		$("#base_detail_LI").get(0).className = "Detailli";
	}
}
