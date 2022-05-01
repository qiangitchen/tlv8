var data = null;
var maingrid = null;
var currentgrid = null;
var $maingridview;

var checkData = new Map();
/*创建树*/
var param = {
	cell : {
		id : "SID",//设置构建树的id
		name : "SNAME",//树显示的名称
		parent : "SPARENT",//表示树的层级
		other : "SFID,SFNAME,SORGKINDID,SCODE,SFCODE",
		tableName : "SA_OPOrg",//对应的表名
		databaseName : "system",//数据库
		rootFilter : " SID in (select SMANAGEORGID from SA_OPMANAGEMENT where SORGFID = '"+tlv8.Context.getCurrentPersonFID()+"' and SMANAGETYPEID='systemManagement') ", //跟节点条件
		filter : " SORGKINDID != 'psm' ",
		orderby : "SSEQUENCE asc" //排序字段
	}
};
//设置树的属性
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
		enable : true, //是否有快速查询框
		url : "QuickTreeAction",
		quickCells : "SCODE,SNAME",//用于快速查询的字段
		path : "SFID"//查询路径字段
	},
	callback : {
		onClick : treeselected
	}

};
//元素加载
$(function() {
	data = new tlv8.Data();
	data.setTable("SA_OPorg");
	data.setDbkey("system");
	$maingridview = $("#maingridview");
	var labelid = "master_check,No,SCODE,SNAME";
	var labels = "master_check,No.,编号,姓名";
	var labelwidth = "40,40,120,120";
	var datatype = "null,ro,ro,ro";//设置字段类型
	var dataAction = {
		"queryAction" : "getGridAction",
		"savAction" : "",
		"deleteAction" : ""
	};
	//创建grid
	maingrid = new tlv8.createGrid($maingridview[0], labelid, labels,
			labelwidth, dataAction, "100%", "100%", data, 20,
			"sOrgKindID='psm'", "", "", datatype, "true", "true");
	//设置toobar显示{新增、保存、刷新、删除}
	maingrid.grid.settoolbar(false, false, true, false);
	//设置是否可编辑
	currentgrid = maingrid.grid;
	currentgrid.setExcelimpBar(false);//导入
	currentgrid.setExcelexpBar(false);//导出
	$("#maingridview_quick_text").addClass("maininput");
	$(".toobar_item").addClass("toobar_item");
	//currentgrid.refreshData();

});

//单击树
function treeselected(event, treeId, node, clickFlag) {
	var treeID = node.id;
	var filter = "SFID like '" + node.SFID + "%'";
	currentgrid.setStaticFilter(filter);
	currentgrid.refreshData();
}

function gridChecked(event) {
	if (event.checked) {
		checkData.put(event.rowid, event.getValueByName("SNAME", event.rowid));
	} else {
		checkData.remove(event.rowid);
	}
	var kset = checkData.keySet();
	var html = "";
	for ( var i in kset) {
		var value = checkData.get(kset[i]);
		html += "<span id='" + kset[i] + "'><input id='" + kset[i] + "' name='"
				+ kset[i] + "' type='checkbox' value='" + value
				+ "'  onclick='checkall()'>" + value + "</input></span>";
	}
	$("#Chtext").html(html);
}
function gridCheckedAll(event) {
	if (event.checked) {
		for ( var i in event.RowId) {
			checkData.put(event.RowId[i], event.getValueByName("SNAME",
					event.RowId[i]));
		}
	} else {
		for ( var i in event.RowId) {
			checkData.remove(event.RowId[i]);
		}
	}
	var kset = checkData.keySet();
	var html = "";
	for ( var i in kset) {
		var value = checkData.get(kset[i]);
		html += "<span id='" + kset[i] + "'><input id='" + kset[i] + "' name='"
				+ kset[i] + "' type='checkbox' value='" + value
				+ "'  onclick='checkall()'>" + value + "</input></span>";
	}
	$("#Chtext").html(html);
}
//删除
var chid = '';
function deleteBills() {
	$("span[id='" + chid + "']").remove();
	checkData.remove(chid);
}
function checkall() {
	$("input").bind("click", function() {
		chid = $(this).attr("id");
	});
}

function deleteAll() {
	$("#Chtext").html("");
	checkData = new Map();
}
//确定
function dailogEngin() {
	var tid = [];
	var txt = [];
	$("input[type='checkbox']").each(function() {
		tid.push($(this).attr("name"));
		txt.push($(this).attr("value"));
	});
	//alert(tid);
	//var text = txt.substring(0, txt.length - 1);
	//var textid = tid.substring(0, tid.length - 1);
	//var checked = text.replaceAll("on", "");

	var prams = {
		"id" : tid.join(","),
		"name" : txt.join(",")
	};
	return prams;
}