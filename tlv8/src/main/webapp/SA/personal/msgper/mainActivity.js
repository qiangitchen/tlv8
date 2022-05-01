/*==数据源===此项为必须定义==*/
var data = new tlv8.Data();
data.setDbkey("system");// 指定使用数据库连接
data.setTable("MAS_MSGSENDPERM");// 指定grid对应的表
data.setOrderby(" SPERSONNAME asc");// 指定grid对应的表

/* ==== */
var currentgrid;
function getData() {
	var d = document.getElementById("main-grid-view");
	var labelid = "No,SPERSONID,SSENDTYPE,SRECIVESTATE,SPERSONNAME,SUPDATEDATE,SUPDATORID,SUPDATORNAME,SPHONENUMBER";// 设置字段
	var labels = "No.,人员ID,短信类型,是否接收短信,人员名称,修改时间,修改人ID,修改人名称,短信接收号码";// 设置标题
	var labelwidth = "40,0,80,180,80,120,0,80,0";// 设置宽度
	var datatype = "ro,ro,ro,html:readState,ro,ro,ro,ro,ro";// 设置字段类型
	var dataAction = {
		"queryAction" : "getGridAction",// 查询动作
		"savAction" : "saveAction",// 保存动作
		"deleteAction" : "deleteAction"// 删除动作
	};
	var swhere = "SPERSONID='" + tlv8.Context.getCurrentPersonID() + "'";
	if ("PSN01" == tlv8.Context.getCurrentPersonID()
			|| "30F73CA09E344A118D6E651445B557E1" == tlv8.Context
					.getCurrentPersonID()
			|| "7FEE0F79381C4E0CBD42E46D5AE5A73D" == tlv8.Context
					.getCurrentPersonID()) {
		swhere = "SPERSONID!='PSN01'"; // 超级管理员可以修改所有人的配置
	}
	var maingrid = new tlv8.createGrid(d, labelid, labels, labelwidth,
			dataAction, "100%", "100%", data, 20, swhere, "", "", datatype,
			"false", "true");
	// 设置按钮显示{新增、保存、刷新、删除}
	maingrid.grid.settoolbar(false, true, true, false);
	// 设置是否可编辑
	maingrid.grid.seteditModel(true);
	currentgrid = maingrid.grid;
	currentgrid.insertNum = true;// 新增向下(默认新增在第一行)
	if ("PSN01" == tlv8.Context.getCurrentPersonID()
			|| "30F73CA09E344A118D6E651445B557E1" == tlv8.Context
					.getCurrentPersonID()
			|| "7FEE0F79381C4E0CBD42E46D5AE5A73D" == tlv8.Context
					.getCurrentPersonID()) {
		// 嵌入自定义工具条内容
		var shtml = "<table style='font-size:12px;line-height:25px;'><tr>";
		shtml += "<td>&nbsp;<input type='checkbox' id='managebtn' onclick='manageCheck(event,this)'/></td>";
		shtml += "<td>&nbsp;管理&nbsp;</td></tr></table>";
		$("#main-grid-view-grid-item").html(shtml);
	}
	currentgrid.setStaticFilter("SPERSONID='"
			+ tlv8.Context.getCurrentPersonID() + "'");
	currentgrid.refreshData();// 刷新数据
}

function manageCheck(event, obj) {
	if (obj.checked == true) {
		currentgrid.setStaticFilter("");
		currentgrid.refreshData();// 刷新数据
	} else {
		currentgrid.setStaticFilter("SPERSONID='"
				+ tlv8.Context.getCurrentPersonID() + "'");
		currentgrid.refreshData();// 刷新数据
	}
}

function readState(event) {
	var rowid = event.rowid;
	var vals = event.value;
	var reciv = "";
	var unreciv = "";
	if (vals == "1") {
		reciv = " checked='checked' ";
	} else {
		unreciv = " checked='checked' ";
	}
	var htmls = "<div style='text-align:center;width:100%;'>";
	htmls += "<input type='checkbox' class='send' " + reciv
			+ " value='1' onclick='reciveSelect(event,this)' rowid='" + rowid
			+ "'/>接收";
	htmls += "&nbsp;&nbsp;";
	htmls += "<input type='checkbox' class='unsend' " + unreciv
			+ " value='0' onclick='reciveSelect(event,this)' rowid='" + rowid
			+ "'/>拒绝";
	htmls += "</div>";
	return htmls;
}

function reciveSelect(event, obj) {
	var rowid = $(obj).attr("rowid");
	currentgrid.setValueByName("SRECIVESTATE", rowid, obj.value + "");
	obj.checked = true;
	$("[rowid='" + rowid + "']").each(function() {
		if (this != obj) {
			this.checked = false;
		}
	});
	currentgrid.setValueByName("SUPDATEDATE", rowid, tlv8.System.Date
			.sysDateTime());
	currentgrid.setValueByName("SUPDATORID", rowid, tlv8.Context
			.getCurrentPersonID());
	currentgrid.setValueByName("SUPDATORNAME", rowid, tlv8.Context
			.getCurrentPersonName());
}