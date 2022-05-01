/*=========创建树==========*/
var param = {
	cell : {
		databaseName : "system",// 数据库
		tableName : "PERSONALDOCNODE",// 对应的表名
		id : "SID",// 设置构建树的id
		name : "SPARENTNAME",// 树显示的名称
		parent : "SPARENTID",// 表示树的层级
		other : "",// 树中所带字段信息
		rootFilter : " SPARENTID is null", // 跟节点条件
		filter : "SCREATORID='" + tlv8.Context.getCurrentPersonID() + "'",
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
		quickCells : "SPARENTNAME",// 用于快速查询的字段
		path : "SPATH"// 查询路径字段
	},
	callback : {
		onClick : treecheckedSelect
	}
};

var currentNode;
function treecheckedSelect(event, treeId, node, clickFlag) {
	currentNode = node;
}

function dailogEngin() {
	if (!currentNode) {
		alert("请选择目录!");
		return false;
	} else {
		return currentNode.id;
	}
}