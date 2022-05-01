/*==数据源===此项为必须定义==*/
var currentgrid;
var data = new justep.yn.Data();
data.setDbkey("system");//指定使用数据库连接
data.setTable("SA_LOGINLOG_VIEW");//指定grid对应的表
data.setOrderby("SLOGINTIME desc");

/*====*/
var currentgrid;
function getData() {
	var d = document.getElementById("main-grid-view");
	var labelid = "No,SNAME,SCODE,SFNAME,SLOGINIP,SLOGINTIME";//设置字段
	var labels = "No.,姓名,登录账号,全名,登录IP,登录时间";//设置标题
	var labelwidth = "40,80,80,280,180,150";//设置宽度
	var datatype = "ro,string,string,string,string,string";//设置字段类型
	var dataAction = {
			"queryAction" : "getGridAction",//查询动作
			"savAction" : "saveAction",//保存动作
			"deleteAction" : "deleteAction"//删除动作
	};
	var maingrid = new justep.yn.createGrid(d, labelid, labels, labelwidth,
			dataAction, "100%", "100%", data, 15, "", "", "", datatype,
			"false", "true");
	//设置按钮显示{新增、保存、刷新、删除}
	maingrid.grid.settoolbar(false, false, true, false);
	//设置是否可编辑
	maingrid.grid.seteditModel(false);
	maingrid.grid.setExcelexpBar(true);
	currentgrid = maingrid.grid;

	//var p=$("#main-grid-view_quick_text").parent();
	var html='<table><tr><td>开始时间：</td><td><input type="text" class="Wdate" id="start" onClick="WdatePicker({dateFmt: \'yyyy-MM-dd HH:mm:ss\'})"></td>';    
	html+='<td>结束时间：</td><td><input type="text" class="Wdate" id="end" onClick="WdatePicker({dateFmt: \'yyyy-MM-dd HH:mm:ss\'})"></td></tr></table>'; 
	//p.append(html);
	$("#main-grid-view_quick_button").attr("href","javascript:qurey()"); 
//	$("#main-grid-view_quick_text").remove();
	$("#main-grid-view_quick_text").parent().remove();
	document.getElementById('main-grid-view-grid-item').innerHTML = html;
	currentgrid.refreshData();//刷新数据
}

function qurey(){
    var date=new Date();
    date=date.format('yyyy-MM-dd HH:mm:ss');
    var filter="";
    if($("#start").val()!=""){
       filter+=" and slogintime>=to_date('"+$("#start").val()+"','yyyy-mm-dd hh24:mi:ss')";
    }
    if($("#end").val()!=""){
       filter+=" and slogintime<=to_date('"+$("#end").val()+"','yyyy-mm-dd hh24:mi:ss')";
    }
    filter=filter.replace("and","");
    currentgrid.refreshData(filter);
}