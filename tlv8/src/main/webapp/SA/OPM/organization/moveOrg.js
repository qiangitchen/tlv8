var currenttreeID = null;
var rowid = tlv8.RequestURLParam.getParam("rowid");

/*创建树*/
var param = {
	cell : {
		id : "SID",//设置构建树的id
		name : "SNAME",//树显示的名称
		parent : "SPARENT",//表示树的层级
		other : "SFID,SFNAME,SORGKINDID,SCODE,SFCODE",
		tableName : "SA_OPOrg",//对应的表名
		databaseName : "system",//数据库
		rootFilter : " SCODE<>'SYSTEM' and SPARENT is null ", //跟节点条件
		filter : " SVALIDSTATE != -1 and SORGKINDID != 'psm' and SID !='"+rowid+"'", //加上类型过滤 防止人员移动到人员下面
		orderby : "SSEQUENCE asc" //排序字段
	}
};
//设置树的属性
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
	edit : {
		enable : false
	},
	callback : {
		onClick : treeselected
	}
};

function treeselected(event, treeId, treeNode) {
	currenttreeID = treeNode.id;
}

/*
 * 确认返回
 * @param {Object} param
 * @return {TypeName} 
 */
function dailogEngin(param) {
	return currenttreeID;
}