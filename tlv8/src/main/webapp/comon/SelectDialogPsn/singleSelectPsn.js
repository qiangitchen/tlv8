var currentgrid = null;
var resetPassItem;
var disableUserItem;
var ableUserItem;

/* 创建树 */
var param = {
	cell : {
		id : "SID",// 设置构建树的id
		name : "SNAME",// 树显示的名称
		parent : "SPARENT",// 表示树的层级
		other : "SFID,SFNAME,SORGKINDID,SCODE,SFCODE",
		tableName : "sa_oporg_view",// 对应的表名
		databaseName : "system",// 数据库
		rootFilter : " SPARENT is null ", // 跟节点条件
		orderby : "SSEQUENCE asc" // 排序字段
	}
};
// 设置树的属性
var setting = {
	view : {
		selectedMulti : false, // 设置是否允许同时选中多个节点。默认值: true
		autoCancelSelected : false,
		dblClickExpand : true
	},
	data : {
		simpleData : {
			enable : true
		}
	},
	async : {
		enable : true,
		url : "TreeSelectAction",
		autoParam : [ "id=currenid" ],
		type : "post"
	},
	isquickPosition : {
		enable : true, // 是否有快速查询框
		url : "QuickTreeAction",
		quickCells : "SCODE,SNAME",// 用于快速查询的字段
		path : "SFID"// 查询路径字段
	},
	callback : {
		beforeClick : beforeClick
	}

};

/*
 * 初始化
 */
var mainJtree = new Jtree();
var staticGrid;
var gridparam = [ {
	id : 'fName',
	name : '名称',
	width : 100
}, {
	id : 'fCode',
	name : '编码',
	width : 80
} ];
$(document).ready(function() {
	mainJtree.init("JtreeDemo", setting, param);
	staticGrid = new StaticGrid("main-grid-view"); // 实例化类
	staticGrid.init(gridparam); // 初始化
	staticGrid.setRowDbclick(staticGridDbclick); // 设置行双击事件
	staticGrid.clearData();
});

/*
 * 树单击 @param {Object} treeId @param {Object} treeNode
 */
function beforeClick(treeId, treeNode) {
	if (treeNode.SORGKINDID == "psm") {
		staticGrid.clearData();
		var json = {};
		json.rowid = treeNode.id;
		json.fName = treeNode.SNAME;
		json.fCode = treeNode.SCODE;
		json.SFID = treeNode.SFID;
		staticGrid.addData(json);
		staticGrid.selectRowById(json.rowid);
	}
}

/*
 * 列表双击 @param {Object} obj @param {Object} stGrid
 */
function staticGridDbclick(obj, stGrid) {
	staticGrid.removeData(obj.id);
}

/*
 * 确定 @return {TypeName}
 */
function dailogEngin() {
	if (staticGrid.data.length > 0) {
		return staticGrid.data[0];
	} else {
		alert("未选择执行人!");
		return false;
	}
}
