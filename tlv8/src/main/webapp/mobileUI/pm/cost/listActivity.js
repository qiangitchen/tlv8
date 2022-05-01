var setting = {
	databaseName : "pm",// 数据库
	tableName : "pm_expense",// 对应的表名
	idcolumn : "fID",// 设置id字段
	title : {
		label : "",// 字段描述
		column : "fBEIZHU"
	},
	texts : {
		label : "",// 字段描述
		column : "fJINE"
	},
	ellips : {
		label : "",// 字段描述
		column : "fFASHENGRIQI"
	},
	staticfilter : " FCREATEOGNID='" + justep.Context.getCurrentOgnID() + "' and fRAET='支出'",// 过滤条件
	orderby : "FCREATETIME desc",// 排序
	pagelimit : 10,
	onclick : function(id) {
		// 点击列表时响应
		window.open("deatailActivity.html?sData1="+id,"_self");
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
	window.open("deatailActivity.html?","_self");
}