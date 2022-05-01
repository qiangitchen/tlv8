var reData, currentNode, redataMap = new Map();

/*
 * 对话框打开时执行
 * @param {Object} param
 */
function getUrlParam(param) {
	redataMap = new Map();
	var FOPENSCOPEIDs = param.FOPENSCOPEID.split(",");
	var FOPENSCOPEs = param.FOPENSCOPE.split(",");
	staticGrid.clearData();
	for ( var i in FOPENSCOPEIDs) {
		if (FOPENSCOPEIDs[i] && FOPENSCOPEIDs[i] != "") {
			redataMap.put(FOPENSCOPEIDs[i], FOPENSCOPEs[i]);
			var json = {};
			json.rowid = FOPENSCOPEIDs[i];
			json.sFName = FOPENSCOPEs[i];
			if (!staticGrid.checkRowId(FOPENSCOPEIDs[i])) {
				staticGrid.addData(json);
			}
		}
	}
}

/*创建树*/
var param = {
	cell : {
		id : "SID",//设置构建树的id
		name : "SNAME",//树显示的名称
		parent : "SPARENT",//表示树的层级
		other : "SFID,SFNAME,SORGKINDID,SCODE,SFCODE",
		tableName : "SA_OPOrg",//对应的表名
		databaseName : "system",//数据库
		rootFilter : "SPARENT is null",//跟节点条件
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
	json.rowid = currentNode.id;
	json.sFName = currentNode.name;
	if (!staticGrid.checkRowId(currentNode.id)) {
		staticGrid.addData(json);
	}
	redataMap.put(currentNode.id, currentNode.name);
	staticGrid.selectRowById(currentNode.id);
}

/*
 * 删除选择
 */
function removeselectData() {
	staticGrid.removeData(staticGrid.selectedRow.id);
	redataMap.remove(staticGrid.selectedRow.id);
}

//确定
function dailogEngin(param) {
	return redataMap;
}