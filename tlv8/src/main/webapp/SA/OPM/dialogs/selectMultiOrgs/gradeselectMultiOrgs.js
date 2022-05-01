/*=========创建树==========*/
var param = {
	cell : {
		databaseName : "system",// 数据库
		tableName : "SA_OPORG",// 对应的表名
		id : "SID",// 设置构建树的id
		name : "SNAME",// 树显示的名称
		parent : "SPARENT",// 表示树的层级
		other : "SPARENT,SCODE,SFID,SORGKINDID,SFNAME",// 树中所带字段信息
		rootFilter : " SID in (select SMANAGEORGID from SA_OPMANAGEMENT where SORGFID = '"+tlv8.Context.getCurrentPersonFID()+"' and SMANAGETYPEID='systemManagement') ", // 跟节点条件
		orderby : "SSEQUENCE asc" // 排序字段
	}
};
var setting = {
	view : {
		selectedMulti : false, // 设置是否允许同时选中多个节点。默认值: true
		autoCancelSelected : false,
		dblClickExpand : false
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
		beforeClick : treeselected,
		onDblClick : zTreeOnDblClick
	}
};
var treeCurrentNode;
function treeselected(treeId, treeNode) {
	treeCurrentNode = treeNode;
}
function zTreeOnDblClick(event, treeId, treeNode) {
	treeCurrentNode = treeNode;
	selectOrg();
};

var staticGrid;
function initPage() {
	staticGrid = new StaticGrid("selectEdStatesGridView"); // 实例化类
	var initLabel = [ {
		id : 'SORGKINDID',
		name : '类型',
		width : 40
	}, {
		id : 'SNAME',
		name : '名称',
		width : 100
	}, {
		id : 'SCODE',
		name : '编码',
		width : 100
	}, {
		id : 'SFNAME',
		name : '路径',
		width : 200
	} ];
	staticGrid.init(initLabel); // 初始化
	staticGrid.setRowDbclick(staticGridDbclick); // 设置行双击事件
}
function staticGridDbclick(event) {

}

function selectAll() {
	var nodes = MainJtree.getNodes();
	staticGrid.clearData();
	for (var i = 0; i < nodes.length; i++) {
		var jsondata = {
			rowid : nodes[i].id,
			SORGKINDID : nodes[i].SORGKINDID,
			SNAME : nodes[i].SNAME,
			SCODE : nodes[i].SCODE,
			SFNAME : nodes[i].SFNAME
		};
		staticGrid.addData(jsondata);
	}
}
function selectOrg() {
	if (treeCurrentNode) {
		var jsondata = {
			rowid : treeCurrentNode.id,
			SORGKINDID : treeCurrentNode.SORGKINDID,
			SNAME : treeCurrentNode.SNAME,
			SCODE : treeCurrentNode.SCODE,
			SFNAME : treeCurrentNode.SFNAME
		};
		staticGrid.addData(jsondata);
	}
}
function unselectOrg() {
	staticGrid.removeData(staticGrid.selectedRow.id);
}
function unselectAll() {
	staticGrid.clearData();
}

// 确认返回
function dailogEngin() {
	return staticGrid.getRowIds();
}