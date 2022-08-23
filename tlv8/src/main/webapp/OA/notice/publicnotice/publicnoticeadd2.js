//工具条
var toolbarItem, flwCompent;
function init_toolbar() {
	var bardiv = J$("stander_bar");
	var toobar = new tlv8.toolbar(bardiv, false, true, false, true);
	toolbarItem = toobar.items;
}

// 流程配置
var setting = {
	autosaveData : true, // 自动保存数据
	autoclose : true, // 自动关闭页面
	autofilter : true, // 自动过滤数据
	autorefresh : true, // 自动刷新数据
	autoselectext : true, // 自动获取执行人
	item : {// 按钮配置
		audit : true, // 审批
		back : true,// 流转按钮
		out : true,// 流转按钮
		transmit : false,// 转发按钮
		pause : false,// 暂停按钮
		stop : false
	// 终止按钮
	},
	auditParam : {// 审批信息配置
		busiDataKey : "oa", // 业务库数据连接
		busiTable : "OA_PULICNOTICE", // 业务表名
		auditTable : "OA_FLOWRECORD", // 审核意见表
		billidRe : "FBILLID", // 外键字段
		FAGREETEXTRe : "FAGREETEXT", // 意见字段
		isRequired : false
	// 是否为必须填写意见
	}
};

// 数据配置
var datamian;
var rowid = "";
function initDocumentPage() {
	datamian = new tlv8.Data();
	datamian.setDbkey("oa");
	datamian.setTable("OA_PULICNOTICE");
	datamian.setFormId("MAIN_DATA_FORM");
	flwCompent = new tlv8.flw("flowToolbar", datamian, setting);
	var sData1 = tlv8.RequestURLParam.getParam("sData1");
	document.getElementById("MAIN_DATA_FORM").rowid = sData1;
	J$("MAIN_DATA_FORM").setAttribute("rowid", sData1);
	datamian.refreshData();
}

// 数据保存
function dataSave() {
	var rowid = datamian.saveData();
	J$("MAIN_DATA_FORM").rowid = rowid;
	J$("MAIN_DATA_FORM").setAttribute("rowid", rowid);
	$("#MAIN_DATA_FORM").attr("rowid", rowid);
}

// 刷新后触发
function afterDatarefresh() {
	$("#FCONTENT")
			.xheditor(
					"{tools:'simple',﻿﻿﻿﻿upImgUrl:\"xhUpload\",upImgExt:\"jpg,jpeg,gif,png\"}");

}
//数据刷新
function dataRefresh() {
	datamian.refreshData();
}