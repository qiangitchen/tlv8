// 流程配置
var setting = {
	autosaveData : false, // 自动保存数据
	autoclose : false, // 自动关闭页面
	autofilter : false, // 自动过滤数据
	autorefresh : false, // 自动刷新数据
	autoselectext : false, // 自动获取执行人
	item : {// 按钮配置
		audit : false, // 审批
		back : false,// 流转按钮
		out : false,// 流转按钮
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
	// flwCompent = new tlv8.flw("flowToolbar", datamian, setting);
	rowid = tlv8.RequestURLParam.getParam("rowid"); // 获取url参数
	readonly = tlv8.RequestURLParam.getParam("readonly"); // 获取url参数
	document.getElementById("MAIN_DATA_FORM").rowid = rowid;
	J$("MAIN_DATA_FORM").setAttribute("rowid", rowid);
	if (readonly != "true") {
		updateLookState(rowid);
	}
	datamian.refreshData();
	showSelectPersonInfo(rowid, true);
	var fileComp = new tlv8.fileComponent(document
			.getElementById("fileCompDiv"), datamian, "FATTFILE",
			"/root/通知公告/附件/" + (new Date().getFullYear()) + "/"
					+ (new Date().getMonth() + 1), false, false, false);
}
// 刷新后触发
function afterDatarefresh() {
	var content = $("#FCONTENT").val();
	$("#show_info").html(content);
}