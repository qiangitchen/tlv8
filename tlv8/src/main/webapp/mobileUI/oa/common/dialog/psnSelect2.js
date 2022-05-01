/* 创建树 */
var param = {
	cell : {
		id : "SID",// 设置构建树的id
		name : "SNAME",// 树显示的名称
		parent : "SPARENT",// 表示树的层级
		other : "SFID,SFNAME,SORGKINDID,SCODE,SFCODE",
		tableName : "SA_OPORG_VIEW",// 对应的表名
		databaseName : "system",// 数据库
		rootFilter : " SPARENT is null ", // 跟节点条件
		orderby : "SSEQUENCE asc" // 排序字段
	}
};
// 设置树的属性
var setting = {
	view : {
		dblClickExpand : false
	},
	check : {
		enable : true
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
		onClick : treeselected,
		beforeClick : function(treeId, treeNode) {
			var zTree = $.fn.zTree.getZTreeObj("maintree");
			if (treeNode.isParent) {
				zTree.expandNode(treeNode);
				return false;
			}
		},
		beforeCheck : zTreeBeforeCheck
	}
};
// 单击树
function treeselected(event, treeId, node, clickFlag) {
	var treeID = node.id;
	if (node.SORGKINDID == "psm") {

	}
}
function zTreeBeforeCheck(treeId, treeNode) {
	if (treeNode.SORGKINDID == "psm" || treeNode.children) {
		return true;
	}
	return false;// 返回false禁止选中
}

var mainJtree = new Jtree();
$(document).ready(function() {
	mainJtree.init("maintree", setting, param);
});

function selectPsn() {
	var nodes = mainJtree.tree.getCheckedNodes(true);
	var callback = getParamValueFromUrl("callback");
	if (callback && callback != "") {
		var re = {
			"id" : getMapValue(nodes, "SID"),
			"name" : getMapValue(nodes, "SNAME"),
			"sfid" : getMapValue(nodes, "SFID"),
			"sfname" : getMapValue(nodes, "SFNAME")
		};
		justep.dialog.ensureDialog(callback, re);
	} else {
		windowCancel();
	}
}

function getMapValue(obj, celname) {
	var re = "";
	for (var int = 0; int < obj.length; int++) {
		if (obj[int].SORGKINDID == "psm") {
			re += "," + obj[int][celname];
		}
	}
	re = re.replace(",", "");
	return re;
}

function windowCancel() {
	justep.dialog.closeDialog();
}