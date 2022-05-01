/*=========创建树==========*/
var param = {
	cell : {
		databaseName : "system",// 数据库
		tableName : "SA_FAVORITE",// 对应的表名
		id : "SID",// 设置构建树的id
		name : "SNAME",// 树显示的名称
		parent : "SPARENT",// 表示树的层级
		other : "SFID,SFNAME",// 树中所带字段信息
		rootFilter : " SPARENT is null", // 跟节点条件
		filter : "SCREATORID = '" + tlv8.Context.getCurrentPersonID()
				+ "'",
		orderby : "" // 排序字段
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
		quickCells : "SNAME",// 用于快速查询的字段
		path : "SFID"// 查询路径字段
	},
	callback : {
		onClick : treeselected
	}
};

// 工具条
var toolbarItem;
function init_toolbar() {
	var bardiv = J$("stander_bar");
	var toobar = new tlv8.toolbar(bardiv, true, "readonly", "readonly",
			true);
	toolbarItem = toobar.items;
}

// 数据配置
var datamian;
function initDocumentPage() {
	datamian = new tlv8.Data();
	datamian.setDbkey("system");
	datamian.setTable("SA_FAVORITE");
	datamian.setFormId("MAIN_DATA_FORM");
}

// 新增数据
function dataInsert() {
	J$("MAIN_DATA_FORM").reset();
	J$("MAIN_DATA_FORM").rowid = "";
	J$("MAIN_DATA_FORM").setAttribute("rowid", "");
	$("#MAIN_DATA_FORM").attr("rowid", "");
	$("#SCREATORID").val(tlv8.Context.getCurrentPersonID());
	$("#SCREATORNAME").val(tlv8.Context.getCurrentPersonName());
	$("#SCREATEDATE").val(tlv8.System.Date.sysDateTime());
	if (currentNode) {
		$("#SPARENT").val(currentNode.id);
	}
	toolbarItem.setItemStatus("readonly", true, "readonly", "readonly");
}

// 数据保存
function dataSave() {
	var rowid = datamian.saveData();
	J$("MAIN_DATA_FORM").rowid = rowid;
	J$("MAIN_DATA_FORM").setAttribute("rowid", rowid);
	$("#MAIN_DATA_FORM").attr("rowid", rowid);
	toolbarItem.setItemStatus(true, "readonly", true, true);
}

// 数据刷新
function dataRefresh() {
	datamian.refreshData();
}

// 数据删除
function dataDeleted() {
	if (datamian.deleteData()) {
		dataRefresh();
	}
}

var currentNode;
// 选中树
function treeselected(event, treeId, node, clickFlag) {
	currentNode = node;
	J$("MAIN_DATA_FORM").rowid = node.id;
	J$("MAIN_DATA_FORM").setAttribute("rowid", node.id);
	$("#MAIN_DATA_FORM").attr("rowid", node.id);
	datamian.setFilter("");
	datamian.refreshData();
}