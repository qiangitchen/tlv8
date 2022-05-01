var setting = {
	databaseName : "pm",// 数据库
	tableName : "pm_customer",// 对应的表名
	idcolumn : "fID",// 设置id字段
	title : {
		label : "",// 字段描述
		column : "fMINGCHENG"
	},
	texts : {
		label : "",// 字段描述
		column : "fLEIXING"
	},
	ellips : {
		label : "",// 字段描述
		column : "fBIANHAO"
	},
	staticfilter : "FCREATEOGNID='"+justep.Context.getCurrentOgnID()+"'",// 过滤条件
	orderby : "",// 排序
	pagelimit : 10,
	onclick : function(id) {
		// 点击列表时响应
		window.open("detailActivity.html?sData1="+id,"_self");
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
	window.open("detailActivity.html","_self");
}