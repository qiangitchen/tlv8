var reData, currentNode, redataMap = new Map();

/*创建树*/
var param = {
	cell : {
		id : "SID",//设置构建树的id
		name : "SNAME",//树显示的名称
		parent : "SPARENT",//表示树的层级
		other : "SFID,SFNAME,SORGKINDID,SCODE,SFCODE",
		tableName : "SA_OPOrg",//对应的表名
		databaseName : "system",//数据库
		rootFilter : " SID in (select SMANAGEORGID from SA_OPMANAGEMENT where SORGFID = '"+tlv8.Context.getCurrentPersonFID()+"' and SMANAGETYPEID='systemManagement') ",//跟节点条件
		orderby : "SSEQUENCE asc" //排序字段
	}
};
var setting = {
	view : {
		selectedMulti : false, //设置是否允许同时选中多个节点。默认值: true
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
		enable : true, //是否有快速查询框
		url : "QuickTreeAction",
		quickCells : "SCODE,SNAME",//用于快速查询的字段
		path : "SFID"//查询路径字段
	},
	callback : {
		beforeClick : treeselected
	}

};

//选择树
function treeselected(treeId, treeNode) {
	currentNode = treeNode;
}

/*
 * 增加选择
 */
function addselectData() {
	var json = {};
	if (currentNode.SORGKINDID != "psm") {
		json.rowid = currentNode.id;
		json.sDeptName = currentNode.name;
		json.sPsmName = "";
	} else {
		json.rowid = currentNode.id;
		json.sDeptName = "";
		json.sPsmName = currentNode.name;
	}
	if (!staticGrid.checkRowId(currentNode.id)) {
		staticGrid.addData(json);
	}
	redataMap.put(currentNode.id, currentNode);
	staticGrid.selectRowById(currentNode.id);
}

/*
 * 删除选择
 */
function removeselectData() {
	staticGrid.removeData(staticGrid.selectedRow.id);
	redataMap.remove(staticGrid.selectedRow.id);
}

$(document).ready(function() {
	redataMap = new Map();
});

//确定
function dailogEngin(param) {
	return redataMap;
}