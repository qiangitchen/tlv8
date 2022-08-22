var currentgrid = null;
/*==数据源===此项为必须定义==*/
var data = new tlv8.Data();
data.setTable("SA_LOGINLOG");
data.setDbkey("system");
data.setOrderby("SLOGINTIME desc");
/*====*/
function getData() {
	var d = document.getElementById("maingridview");
	var labelid = "No,SUSERNAME,SLOGINIP,SSERVICEIP,SDAY,SLOGINTIME";
	var labels = "No.,登录名,登录IP,服务器IP,星期,登录时间";
	var labelwidth = "40,100,140,140,100,150";
	var datatype = "ro,string,string,string,string,datetime";//设置字段类型
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
	var la_count = document.createElement("div");
	la_count.style.textAlign = 'center';
	la_count.innerHTML = "<b>&nbsp;&nbsp;&nbsp;&nbsp;总登录次数:&nbsp;"
			+ getCount() + "次.</b>";
	document.getElementById(d.id + '-grid-item').parentNode.appendChild(la_count);
}

function getCount() {
	var sql = "select count(*) SCOUNT from SA_LOGINLOG";
	var r = tlv8.sqlQueryAction("system", sql);
	return r.getValueByName("SCOUNT");
}
