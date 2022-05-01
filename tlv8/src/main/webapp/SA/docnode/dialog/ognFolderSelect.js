/*=========创建树==========*/
var param = {
	cell : {
		databaseName:"oa",//数据库
		tableName : "OA_DZ_FILENODE",//对应的表名
		id : "FID",//设置构建树的id
		name : "FNAME",//树显示的名称
		parent : "FPARENTID",//表示树的层级
		other:"FNAME",//树中所带字段信息
		rootFilter : " (FPARENTID is null or FPARENTID='root') and FCREATORFID like '%"
			+ tlv8.Context.getCurrentOgnID()+ "%'", //跟节点条件
		orderby : "" //排序字段
	}
};
var setting = {
	view: {
		selectedMulti: false,		//设置是否允许同时选中多个节点。默认值: true
		autoCancelSelected: false,
		dblClickExpand: true //双击展开
	},
	data : {
		simpleData : {
			enable : true
		}
	},
	async : {
		enable : true, //异步加载
		url : "TreeSelectAction",//加载数据的Action,可以自定义
		autoParam: ["id=currenid"]
	},
	isquickPosition :{
		enable : false, //是否有快速查询框
		url:"QuickTreeAction",
		quickCells : "FNAME",//用于快速查询的字段
		path : ""//查询路径字段
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