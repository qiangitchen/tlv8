/*=========创建树==========*/
var param = {
	cell : {
		databaseName : "system",// 数据库
		tableName : "SA_OPORG_VIEW",// 对应的表名
		id : "SID",// 设置构建树的id
		name : "SNAME",// 树显示的名称
		parent : "SPARENT",// 表示树的层级
		other : "SORGKINDID,SFID,SFCODE,SFNAME,SCODE",// 树中所带字段信息
		rootFilter : " SPARENT is null", // 跟节点条件
		filter : "SVALIDSTATE != -1",
		orderby : "SSEQUENCE asc" // 排序字段
	}
};
var setting = {
	view : {
		selectedMulti : false, // 设置是否允许同时选中多个节点。默认值: true
		autoCancelSelected : false,
		dblClickExpand : true
	// 双击展开
	},
	data : {
		simpleData : {
			enable : true
		}
	},
	async : {
		enable : true, // 异步加载
		url : "TreeSelectAction",// 加载数据的Action,可以自定义
		autoParam : [ "id=currenid" ]
	},
	isquickPosition : {
		enable : true, // 是否有快速查询框
		url : "QuickTreeAction",
		quickCells : "SID,SCODE,SNAME",// 用于快速查询的字段
		path : "SFID"// 查询路径字段
	},
	callback : {
		beforeClick : beforeClick
	}
};

var PICdata = new tlv8.Data();
PICdata.setDbkey("system");
PICdata.setTable("SA_HANDWR_SIGNATURE");

var currentNode;
function beforeClick(treeId, treeNode) {
	currentNode = treeNode;
	PICdata.rowid = treeNode.id;
	J$("maindata_form").rowid = treeNode.id;
	$("#maindata_form").attr("rowid", treeNode.id);
	// PICdata.setFilter("SPERSONID='" + treeNode.id + "'");
	PICdata.setFilter("");
	if (treeNode.SORGKINDID == "psm") {
		$("#submbtn").removeClass("l-btn-disabled");
		$("#deletbtn").removeClass("l-btn-disabled");
		J$("submbtn").onclick = mainsaveData;
		J$("deletbtn").onclick = deletemData;
	} else {
		$("#submbtn").addClass("l-btn-disabled");
		$("#deletbtn").addClass("l-btn-disabled");
		J$("submbtn").onclick = null;
		J$("deletbtn").onclick = null;
	}
	PICdata.refreshData();
}

var MainJtree = new Jtree();
function pageInit() {
	MainJtree.init("JtreeView", setting, param);
	PICdata.setFormId("maindata_form");
	var cellname = "SHSPIC";// 设置附件相关字段
	var div = document.getElementById("handwrsPic");
	tlv8.picComponent(div, PICdata, cellname, false, false);
}

function checkUpload() {

}

function initInsertData() {
	$("#SPERSONID").val(currentNode.id);
	$("#SPERSONNAME").val(currentNode.name);
	$("#SCREATERID").val(tlv8.Context.getCurrentPersonID());
	$("#SCREATERNAME").val(tlv8.Context.getCurrentPersonName());
	$("#SCREATETIME").val(tlv8.System.Date.sysDateTime());
}

function mainsaveData() {
	$("#SPERSONID").val(currentNode.id);
	$("#SPERSONNAME").val(currentNode.name);
	$("#SCREATERID").val(tlv8.Context.getCurrentPersonID());
	$("#SCREATERNAME").val(tlv8.Context.getCurrentPersonName());
	$("#SCREATETIME").val(tlv8.System.Date.sysDateTime());
	PICdata.saveData();
}

function deletemData(){
	PICdata.deleteData();
	PICdata.refreshData();
}

function dataafRefresh(event) {
	PICdata.rowid = currentNode.id;
	J$("maindata_form").rowid = currentNode.id;
	$("#maindata_form").attr("rowid", currentNode.id);
	var rowid = PICdata.rowid;
	var personid = $("#SPERSONID").val();
	if (!personid || personid == "") {
		initInsertData();
	}
	var cellname = "SHSPIC";// 设置附件相关字段
	var div = document.getElementById("handwrsPic");
	tlv8.picComponent(div, PICdata, cellname, true, true);
}