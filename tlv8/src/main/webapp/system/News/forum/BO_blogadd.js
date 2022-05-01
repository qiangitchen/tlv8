var currentRowID = null;
var callTabID = "";
var data = new tlv8.Data();
data.setDbkey("system");
data.setTable("bo_blog");
// 设置下拉树属性
var zNodes;
var setting = {
	view : {
		dblClickExpand : false
	},
	data : {
		simpleData : {
			enable : true
		}
	},
	async : {
		enable : true,// 异步
		url : "TreeSelectAction",// 异步交互的action
		autoParam : [ "id=currenid" ]
	// 异步需要的参数

	},
	isquickPosition : {
		enable : false, // 是否有快速查询框
		url : "TreeSelectAction"
	},
	callback : {
		onClick : onClick
	}
};
// 设置构建树的参数
var param = {
	cell : {
		id : "SID",// 设置构建树的id
		name : "SNAME",// 树显示的名称
		parent : "SPARENT",// 表示树的层级
		other : "SFID,SFNAME,SORGKINDID",
		tableName : "SA_OPOrg",// 对应的表名
		databaseName : "system"// 数据库
	}
};
$(document).ready(function() {
	data.setFormId("blogDetail");
	callTabID = tlv8.RequestURLParam.getParam("tabID");
	var gridrowid = tlv8.RequestURLParam.getParam("gridrowid");
	// 初始化帖子分类信息
	var gdiv = document.getElementById("BLOGCATEGORY");
	var sql = "select NAME from bo_category";
	var GridSelect = new tlv8.GridSelect(gdiv, "system", sql);
	// 验证必填样式
	$(".ReadOnly").keyup(function() {
		var value = $(this).val();
		if (value == "") {
			$(this).addClass("ReadOnly");
		} else {
			$(this).removeClass("ReadOnly");
		}
	});
	if (gridrowid != "") {
		data.rowid = gridrowid;
		data.refreshData();
	}
});

// 保存数据
function savedata() {
	var title = document.getElementById('NAME').value;
	if (title == "") {
		alert("讨论名称不能为空");
		return false;
	}
	var rowid = data.saveData();
	return true;
}
// 选择组织机构
function SelectDept() {
	tlv8.portal.dailog.openDailog('选择范围',
			'/comon/SelectDialogPsn/SelectChPsm.html', 550, 420,
			DepScopeCallback, null);

}

function DepScopeCallback(event) {
	var html = event;
	$("#OPENSCOPE").val(event.name);
}
// 选择版主
function SelectDeptpsm(DeptpsmCallback) {
	tlv8.portal.dailog.openDailog('选择版主',
			'/system/News/forum/dialog/AddPsmDialog.html', 550, 420,
			DeptpsmCallback, null);
}
function DeptpsmCallback(event) {
	document.getElementById('CHAIRMAN').value = event.name;
	document.getElementById('CHAIRMAN_ID').value = event.sid;
}
// 隐藏树
function hideMenu() {
	$("#menuContent").fadeOut("fast");
	$("body").unbind("mousedown", onBodyDown);
}
function onBodyDown(event) {
	if (!(event.target.id == "menuBtn" || event.target.id == "menuContent" || $(
			event.target).parents("#menuContent").length > 0)) {
		hideMenu();
	}
}
// 赋值给文本框
function onClick(e, treeId, treeNode) {
	var zTree = $.fn.zTree.getZTreeObj("treeDemo"), nodes = zTree
			.getSelectedNodes(), v = "";
	nodes.sort(function compare(a, b) {
		return a.id - b.id;
	});
	for (var i = 0, l = nodes.length; i < l; i++) {
		v += nodes[i].name + ",";
	}
	if (v.length > 0)
		v = v.substring(0, v.length - 1);
	var cityObj = $("#OPENSCOPE");
	cityObj.attr("value", v);

}

function dailogEngin() {
	return savedata();
}