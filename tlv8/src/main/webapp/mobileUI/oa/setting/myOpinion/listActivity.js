var setting = {
	databaseName : "oa",// 数据库
	tableName : "oa_flowconclusion",// 对应的表名
	idcolumn : "FID",// 设置id字段
	title : {
		label : "审批意见：",// 字段描述
		column : "FCONCLUSIONNAME"
	},
	texts : {
		label : "显示序号：",// 字段描述
		column : "FORDER"
	},
	ellips : {
		label : "",// 字段描述
		column : "FCREATOR"
	},
	staticfilter : " FCREATORID ='"+justep.Context.getCurrentPersonID()+"'",// 过滤条件
	orderby : "FORDER asc",// 排序
	pagelimit : 10,
	onclick : function(id) {
		// 点击列表时响应
		window.open("mainActivity.html?sData1="+id,"_self");
	}
};

var moblist;
$(document).ready(
		function() {
			moblist = new tlv8.MobileList(document
					.getElementById("contenView"), setting);
			moblist.init();
		});

function doSearch(searchText) {
	moblist.doSearch(searchText);
}

function addData(){
	window.open("mainActivity.html","_self");
}