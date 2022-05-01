/*==数据源===此项为必须定义==*/
var currentgrid;
var Maindata = new tlv8.Data();
Maindata.setDbkey("system");// 指定使用数据库连接
Maindata.setTable("sa_flowdrawlg");// 指定grid对应的表

var activityGrid;
function pageInit() {
	getData();
	activityGrid = new StaticGrid("activitylist");
	activityGrid.init([ {
		id : 'id',
		name : '环节标识',
		width : 120
	}, {
		id : 'name',
		name : '环节名称',
		width : 280
	} ]);
	currentgrid.refreshData();// 刷新数据
}

/* ==== */
function getData() {
	Maindata.setFormId("MAIN_DATA_FORM");
	var d = document.getElementById("main_grid_view");
	var labelid = "No,SPROCESSID,SPROCESSNAME";// 设置字段
	var labels = "No.,流程标识,流程名称";// 设置标题
	var labelwidth = "40,0,280";// 设置宽度
	var datatype = "ro,ro,string";// 设置字段类型
	var dataAction = {
		"queryAction" : "getGridAction",// 查询动作
		"savAction" : "saveAction",// 保存动作
		"deleteAction" : "deleteAction"// 删除动作
	};
	var maingrid = new tlv8.createGrid(d, labelid, labels, labelwidth,
			dataAction, "100%", "100%", Maindata, 10, "", "", "", datatype,
			"false", "true");
	// 设置按钮显示{新增、保存、刷新、删除}
	maingrid.grid.settoolbar(false, false, true, false);
	// 设置是否可编辑
	maingrid.grid.seteditModel(false);
	currentgrid = maingrid.grid;
	// currentgrid.refreshData();//刷新数据
}

function maingriddbclick(event) {
	var rdata = dailogEngin();
	if (rdata && rdata != "")
		tlv8.portal.dailog.dailogEngin(rdata);
}

function dailogEngin() {
	var rowid = currentgrid.getCurrentRowId();
	if (!rowid || rowid == "") {
		alert("请选择!");
		return false;
	}
	var returncells = [ "SPROCESSID", "SPROCESSNAME" ];// 需要返回值的字段
	if (returncells.length > 0) {
		var rdata = {};
		rdata.rowid = rowid;
		for (var i = 0; i < returncells.length; i++) {
			rdata[returncells[i]] = currentgrid.getValueByName(returncells[i],
					rowid);
		}
		var aid = activityGrid.selectedRow.id;
		if(!aid||aid==""){
			alert("请选择环节！");
			return false;
		}
		var aname = activityGrid.getValue(aid, "name");
		rdata.activityID = aid;
		rdata.activityName = aname;
		return rdata;// 指定列时返回json数据
	} else {
		return rowid;// 没有指定返回字段时 默认返回主键
	}
}

/**
 * @param {object}
 *            event
 */
function rowselected(event) {
	activityGrid.clearData();
	var rowid = currentgrid.getCurrentRowId();
	var param = new tlv8.RequestParam();
	param.set("processID", currentgrid.getValueByName("SPROCESSID", rowid));
	tlv8.XMLHttpRequest("flowloadIocusXAction", param, "post", true,
			function(r) {
				if (r.data.flag == "false") {
					alert(r.data.message);
					return false;
				} else {
					var resData = JSON.parse(r.data.data);
					var jsonstr = resData.jsonStr.toString().decodeSpechars();
					var acjson = JSON.parse(jsonstr);
					var nodes = acjson.nodes;
					for (var i = 0; i < nodes.length; i++) {
						var node = nodes[i];
						if(node.type=="node"){
							activityGrid.addData({
								rowid:node.id,
								id:node.id,
								name:node.name
							})
						}
					}
				}
			});
}

/**
 * 特殊字符反编码
 */
String.prototype.decodeSpechars = function() {
	var str = this;
	str = str.toString().replaceAll("#lt;", "<");
	str = str.replaceAll("#gt;", ">");
	str = str.replaceAll("#160;", "&nbsp;");
	str = str.replaceAll("#apos;", "'");
	return str;
};