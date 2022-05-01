var data = new tlv8.Data();
data.setTable("SA_OPORG");
data.setDbkey("system");
var maingrid = null;
var currentgrid = null;
var $maingridview;

var checkData = new Map();
/* 创建树 */
var param = {
	cell : {
		id : "SID",// 设置构建树的id
		name : "SNAME",// 树显示的名称
		parent : "SPARENT",// 表示树的层级
		other : "SFID,SFNAME,SORGKINDID",
		tableName : "SA_OPOrg",// 对应的表名
		databaseName : "system",// 数据库
		rootFilter : " SPARENT is null and SORGKINDID <> 'pos'  ",// 跟节点条件
		orderby : "SSEQUENCE asc" // 排序字段
	}
};
// 设置树的属性
var setting = {
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
		onClick : treeselected
	}

};
// 元素加载
$(function() {
	$maingridview = $("#maingridview");
	var labelid = "master_check,No,SCODE,SNAME,SFID,SFCODE,SFNAME,SORGKINDID";
	var labels = "master_check,No.,编号,姓名,SFID,SFCODE,SFNAME,类型";
	var labelwidth = "40,40,120,120,0,0,0,0";
	var datatype = "null,ro,ro,ro,ro,ro,ro,ro";// 设置字段类型
	var dataAction = {
		"queryAction" : "getPersonInfo",
		"savAction" : "savePersonInfo",
		"deleteAction" : "deletePersonInfo"
	};
	// 创建grid
	maingrid = new tlv8.createGrid($maingridview[0], labelid, labels,
			labelwidth, dataAction, "100%", "100%", data, 20, "", "", "",
			datatype, "true", "true");
	// 设置toobar显示{新增、保存、刷新、删除}
	maingrid.grid.settoolbar(false, false, true, false);
	// 设置是否可编辑
	currentgrid = maingrid.grid;
	currentgrid.setExcelimpBar(false);// 导入
	currentgrid.setExcelexpBar(false);// 导出
	// currentgrid.refreshData();

});
// 单击树
function treeselected(event, treeId, node, clickFlag) {
	var treeID = node.id;
	var KindId = node.SORGKINDID;
	var FullId = node.SFID;
	currentgrid.refreshData("SFID like '" + FullId + "%'"
			+ " AND SORGKINDID='pos'");

}

function gridChecked(g) {
	// currentgrid.getCheckedRowIds()
	// alert(currentgrid.getCheckedRowIds());
	var checkedRowIds = g.checkedRowIds.split(",");
	for ( var i in checkedRowIds) {
		if (checkedRowIds && checkedRowIds != "")
			checkData.put(checkedRowIds[i], g.getValueByName("SNAME",
					checkedRowIds[i]));
	}
	var kset = checkData.keySet();
	var html = "";
	for ( var i in kset) {
		var value = checkData.get(kset[i]);
		html += "<span id='" + kset[i] + "'><input id='" + kset[i] + "' name='"
				+ kset[i] + ",' type='checkbox' value='" + value
				+ ",'  onclick='checkall()'>" + value + "</input></span>";
	}
	$("#Chtext").html(html);
}
// 删除
var chid = '';
function deleteBills() {
	$("span[id='" + chid + "']").remove();
	checkData.remove(chid);
}
function checkall() {
	$("input").bind("click", function() {
		// var rid=$(this).attr("id")
		// if($(this).checked=='checked'){
		chid = $(this).attr("id");
		// }else{}
	});

}
function deleteAll() {
	$("#Chtext").html("");
	checkData = new Map();
}
// 确定
function dailogEngin() {
	var txt = '';
	var tid = '';
	$("#Chtext").find("input[type='checkbox']").each(function() {
		txt += ($(this).attr("value"));
		tid += ($(this).attr("name"));
	});
	// alert(tid);
	var text = txt.substring(0, txt.length - 1);
	var textid = tid.substring(0, tid.length - 1);
	var checked = text.replaceAll("on", "");

	var prams = {
		"id" : textid,
		"name" : checked
	};
	return prams;
}