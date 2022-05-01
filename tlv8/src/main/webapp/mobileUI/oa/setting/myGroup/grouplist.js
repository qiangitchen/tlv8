var setting = {
	databaseName : "oa",// 数据库
	tableName : "oa_adm_mygroupmain",// 对应的表名
	idcolumn : "FID",// 设置id字段
	title : {
		label : "群组名称：",// 字段描述
		column : "FGROUPNAME"
	},
	texts : {
		label : "创建时间：",// 字段描述
		column : "FCREATEDATE"
	},
	ellips : {
		label : "",// 字段描述
		column : "FCREATOR"
	},
	staticfilter : " FCREATORID='"+justep.Context.getCurrentPersonID()+"' ",// 过滤条件
	orderby : "",// 排序
	pagelimit : 10,
	onclick : function(id) {
		// 点击列表时响应
		window.open("groupdeatail.html?sData1="+id,"_self");
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
	window.open("groupdeatail.html","_self");
}