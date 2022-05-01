/*=========创建树==========*/
var param = {
	cell : {
		databaseName : "system",// 数据库
		tableName : "SA_OPORG",// 对应的表名
		id : "SID",// 设置构建树的id
		name : "SNAME",// 树显示的名称
		parent : "SPARENT",// 表示树的层级
		other : "SFNAME,SFCODE,SFID,SORGKINDID",// 树中所带字段信息
		rootFilter : " SPARENT is null", // 跟节点条件
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
		quickCells : "SNAME",// 用于快速查询的字段
		path : "SFID"// 查询路径字段
	},
	callback : {
		beforeClick : treeselected
	}
};

var currentNode;
// 选择树
function treeselected(treeId, treeNode) {
	currentNode = treeNode;
}

// 确定
function dailogEngin(param) {
	if (!currentNode) {
		alert("请选择！");
		return false;
	}
	return currentNode;
}