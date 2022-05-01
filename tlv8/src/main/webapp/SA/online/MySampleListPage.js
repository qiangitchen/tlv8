/*==数据源===此项为必须定义==*/
var currentgrid;
var data = new tlv8.Data();
data.setDbkey("system");// 指定使用数据库连接
data.setTable("SA_ONLINEINFO_VIEW");// 指定grid对应的表

/* ==== */
var currentgrid;
function getData() {
	var d = document.getElementById("main-grid-view");
	var labelid = "No,SNAME,smobilephone,SFNAME,SLOGINIP,SSERVICEIP,SLOGINDATE,STITLE";// 设置字段
	var labels = "No.,姓名,电话,单位名,登陆IP,服务器IP,登陆时间,职务";// 设置标题
	var labelwidth = "40,180,180,280,100,100,120,100";// 设置宽度
	var datatype = "ro,string,string,string,string,string,datetime,string";// 设置字段类型
	var dataAction = {
			"queryAction" : "getGridAction",// 查询动作
			"savAction" : "saveAction",// 保存动作
			"deleteAction" : "deleteAction"// 删除动作
	};
	var maingrid = new tlv8.createGrid(d, labelid, labels, labelwidth,
			dataAction, "100%", "100%", data, 10, "", "", "", datatype,
			"false", "true");
	// 设置按钮显示{新增、保存、刷新、删除}
	maingrid.grid.settoolbar(false, false, true, false);
	// 设置是否可编辑
	maingrid.grid.seteditModel(false);
	document.getElementById("ExcelExp").onclick = function() {
					tlv8.ExcelExp(data.dbkay, data.table, labelid, labels,
							currentgrid.billfilter, data.orderby);
				};
	currentgrid = maingrid.grid;
	currentgrid.insertNum = true;// 新增向下(默认新增在第一行)
//	$(".toolbar_item").parent().hide();
	currentgrid.refreshData();// 刷新数据
}

function fafterRefresh(event) {
	// $("#onlineCount").text(event.getLength());
	// 系统总在线人数不根据grid行数统计
	try {
		topparent.$.jpolite.Data.system.User.getOnlineCount(function(data) {
			if (data && data.status) {
				$("#onlineCount").text(data.count);
			}
		});
	} catch (e) {
	}
}

function query(){
	var filter="";
	if($("#name").val()!=""){
		filter+=" and SNAME='"+$("#name").val()+"'";
	}
	if($("#ogn").val()!=""){
		filter+=" and SFNAME like '%"+$("#ogn").val()+"%'";
	}
	if($("#ognxq").val()!=""){
		filter+=" and SFNAME like '%"+$("#ognxq").val()+"%'";
	}
	if($("#start").val()!=""){
		filter+=" and SLOGINDATE>=to_date('"+$("#start").val()+"','yyyy-mm-dd hh24:mi:ss')";
	}
	if($("#end").val()!=""){
		filter+=" and SLOGINDATE<=to_date('"+$("#end").val()+"','yyyy-mm-dd hh24:mi:ss')";
	}
	filter=filter.replace("and","");
	currentgrid.refreshData(filter);
}