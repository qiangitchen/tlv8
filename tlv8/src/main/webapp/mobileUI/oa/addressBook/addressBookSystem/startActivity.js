var setting = {
	databaseName : "system",// 数据库
	tableName : "SA_OPORG_MPHONE_VIEW",// 对应的表名
	id : "SID", // 设置构建树的id
	parentid : "SPARENT", // 父节点字段
	name : "SNAME", // 树要显示的名称字段
	other : "SFID,SFNAME,SORGKINDID,SCODE,SFCODE,SMOBILEPHONE",
	rootFilter : " SID = '"+justep.Context.getCurrentOgnID()+"'", // 跟节点条件
	path : "SFID",
	otherConditions : "SVALIDSTATE='1' and SCODE != 'SYSTEM'", // 其他条件
	orderby : "SSEQUENCE asc", // 排序字段
	rootDefaulExpand : 0,// 根节点是否默认展开,1=是，0=否 或者摄者为true或者false
	context : "#tree_content", // 树加载在哪个div
	closeicon : "../../../system/images/tree_ez.png", // 关闭的图标
	openicon : "../../../system/images/tree_ex.png", // 展开的图标
	callback : {
		onClick : treeselected
	}
};

// 单击树
function treeselected(event, treeId, node) {
	// var treeID = node.id;
	if (node.SORGKINDID == "psm" && node.SMOBILEPHONE != "") {
		window.open("tel:" + node.SMOBILEPHONE);
	}
}

var mainJtree = new tlv8.MobileTree();
$(document).ready(function() {
	setting.rootFilter=" SID = '"+justep.Context.getCurrentOgnID()+"'";
	mainJtree.initTree(setting);
});

function doSearch(name) {
	mainJtree.quickPosition(name);
}