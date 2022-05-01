//工具条
var flwCompent;
function init_toolbar() {
	var bardiv = J$("stander_bar");
	new tlv8.toolbar(bardiv, false, true, false, true);
}

// 流程配置
var setting = {
	autosaveData: true, // 自动保存数据
	autoclose: true, // 自动关闭页面
	autofilter: true, // 自动过滤数据
	autorefresh: true, // 自动刷新数据
	autoselectext: true, // 自动获取执行人
	item: {// 按钮配置
		audit: "readonly", // 审批
		back: false,// 流转按钮
		out: "readonly",// 流转按钮
		transmit: false,// 转发按钮
		pause: false,// 暂停按钮
		stop: "readonly"
		// 终止按钮
	},
	auditParam: {// 审批信息配置
		busiDataKey: "oa", // 业务库数据连接
		busiTable: "OA_PULICNOTICE", // 业务表名
		auditTable: "OA_FLOWRECORD", // 审核意见表
		billidRe: "FBILLID", // 外键字段
		FAGREETEXTRe: "FAGREETEXT", // 意见字段
		isRequired: false
		// 是否为必须填写意见
	}
};

// 数据配置
var datamian = new tlv8.Data();
datamian.setDbkey("oa");
datamian.setTable("OA_PULICNOTICE");
var rowid = "";
function initDocumentPage() {
	//init_toolbar();
	datamian.setFormId("MAIN_DATA_FORM");
	// flwCompent = new tlv8.flw("flowToolbar", datamian, setting);
	rowid = tlv8.RequestURLParam.getParam("sData1"); // 获取url参数
	if (rowid != null && rowid != "") {
		document.getElementById("MAIN_DATA_FORM").rowid = rowid;
		J$("MAIN_DATA_FORM").setAttribute("rowid", rowid);
		datamian.refreshData();
		showSelectPersonInfo(rowid);
		//		changType();
	} else {
		dataInsert();
		var curOgnID = tlv8.Context.getCurrentOgnID();
		var curOgnName = tlv8.Context.getCurrentOgnName();
		var curPersonID = tlv8.Context.getCurrentPersonID();
		var curPersongName = tlv8.Context.getCurrentPersonName();
		var curOrgName = tlv8.Context.getCurrentOrgName();
		var curOrgID = tlv8.Context.getCurrentOrgID();
		datamian.setValueByName("FOGNID", curOgnID);
		datamian.setValueByName("FOGNNAME", curOgnName);
		datamian.setValueByName("FORGID", curOrgID);
		datamian.setValueByName("FORGNAME", curOrgName);
		datamian.setValueByName("FCREATEID", curPersonID);
		datamian.setValueByName("FCREATENAME", curPersongName);
		datamian.setValueByName("FCREATEDATE", tlv8.System.Date.sysDateTime());
		datamian.setValueByName("FCREATEDATETIME", tlv8.System.Date
			.sysDateTime());
		opef();
		//		$("#FCONTENT")
		//				.xheditor(
		//						"{tools:'simple',﻿﻿﻿﻿upImgUrl:\"xhUpload\",upImgExt:\"jpg,jpeg,gif,png\"}");

		creatTextEditor();
	}
	showSelectPersonInfo(rowid);

	new tlv8.fileComponent(document
		.getElementById("fileCompDiv"), datamian, "FATTFILE",
		"/root/通知公告/附件/" + (new Date().getFullYear()) + "/"
		+ (new Date().getMonth() + 1), true, true, false);
	// init_toolbar();
}

var kindEditor1;
function creatTextEditor() {
	kindEditor1 = KindEditor.create('textarea[name="FCONTENT"]', {
		cssPath: cpath + '/comon/kindeditor/plugins/code/prettify.css',
		uploadJson: cpath + '/kindEditorUploadAction',
		fileManagerJson: cpath + '/comon/kindeditor/jsp/file_manager_json.jsp',
		allowFileManager: true,
		afterCreate: function() {
			var self = this;
			KindEditor.ctrl(document, 13, function() {
				self.sync();
			});
			KindEditor.ctrl(self.edit.doc, 13, function() {
				self.sync();
			});
		}
	});
	prettyPrint();
}

// 新增数据
function dataInsert() {
	J$("MAIN_DATA_FORM").reset();
	J$("MAIN_DATA_FORM").rowid = "";
	J$("MAIN_DATA_FORM").setAttribute("rowid", "");
	$("#MAIN_DATA_FORM").attr("rowid", "");
}

// 数据保存
function dataSave() {
	var ftitle = $("#FTITLE").val();
	if (strTrim(ftitle).length == 0) {
		alert("标题不能为空！");
		$("#FTITLE").focus();
		return false;
	}
	// var item = {
	// audit : true,
	// out : false,// 流转按钮
	// stop : false
	// };
	// flwCompent.setItemStatus(item);
	// flwCompent.setting.item = {
	// audit : true, // 审批
	// back : false,// 流转按钮
	// out : true,// 流转按钮
	// transmit : false,// 转发按钮
	// pause : false,// 暂停按钮
	// stop : true
	// };

	$("#FCONTENT").val(kindEditor1.html());

	rowid = datamian.saveData();
	// var task = flwCompent.taskID;
	// if (task == null || task == "") {
	// flwCompent.flowstart(rowid);
	// }
	//J$("MAIN_DATA_FORM").rowid = rowid;
	//J$("MAIN_DATA_FORM").setAttribute("rowid", rowid);
	//$("#MAIN_DATA_FORM").attr("rowid", rowid);
	tlv8.portal.callBack(tlv8.RequestURLParam.getParam("tabID"),
		"defRefreshData", "ok");
	return rowid;
	// dataRefresh();
}

function SaveDelait() {
	$("#submitdata").click();
}

// 数据刷新
function dataRefresh() {
	datamian.refreshData();
}

function addPerson() {
	if (dataSave()) {
		openSelectPersonDalog(initPersonlist);
	}
}
function initPersonlist(data) {
	var param = new tlv8.RequestParam();
	param.set("sdata1", rowid);
	param.set("fids", data.id);
	param.set("names", data.name);
	var result = tlv8.XMLHttpRequest("insertNoticePersonAction?temp="
		+ new Date().getMilliseconds(), param);
	if (result.data.flag == "true") {
		showSelectPersonInfo(rowid);
	} else {
		alert("新增人员失败!");
	}
}

function delPerson(fid) {
	if (confirm("你确定要删除当前选择人员吗?")) {
		var param = new tlv8.RequestParam();
		param.set("fid", fid);
		var result = tlv8.XMLHttpRequest("deleteNoticePersonAction?temp="
			+ new Date().getMilliseconds(), param);
		if (result.data.flag == "true") {
			showSelectPersonInfo(rowid);
		} else {
			alert("删除人员失败！");
		}
	}
}

function changType() {
	// var task = flwCompent.taskID;
	// if (task != null && task != "") {
	// var item = {
	// audit : true,
	// out : true,// 流转按钮
	// stop : true
	// };
	// flwCompent.setItemStatus(item);
	// flwCompent.setting.item = {
	// audit : true, // 审批
	// back : false,// 流转按钮
	// out : true,// 流转按钮
	// transmit : false,// 转发按钮
	// pause : false,// 暂停按钮
	// stop : true
	// };
	// }
	var type = $("#FTYPE").val();
	if (type == "限制发布") {
		$("#mainLayoutTable3").show();
	} else {
		$("#mainLayoutTable3").hide();
	}
}

// 刷新后触发
function afterDatarefresh() {
	opef();
	//	$("#FCONTENT")
	//			.xheditor(
	//					"{tools:'simple',﻿﻿﻿﻿upImgUrl:\"xhUpload\",upImgExt:\"jpg,jpeg,gif,png\"}");

	creatTextEditor();

	changType();
}
// 发布
function pushData() {
	var TITLE = $('#TITLE').val();
	if (TITLE == "" || TITLE == "undefined") {
		alert("请填写标题和数据，保存后在发布!");
	} else {
		datamian.setValueByName("FPUSHDATETIME", tlv8.System.Date
			.sysDateTime());
		datamian.setValueByName("FPUSHID", tlv8.Context
			.getCurrentPersonID());
		datamian.setValueByName("FPUSHNAME", tlv8.Context
			.getCurrentPersonName());
		// var rowid=datamian.saveData();
		// alert(rowid);
		// $("#MAIN_DATA_FORM").attr("rowid", rowid);
		// datamian.refreshData();
		rowid = datamian.saveData();
		opef();
		datamian.refreshData();
		tlv8.portal.callBack(tlv8.RequestURLParam.getParam("tabID"),
			"defRefreshData2", "ok");
	}
}

// 取消发布
function unpushData() {
	datamian.setValueByName("FPUSHDATETIME", "");
	datamian.setValueByName("FPUSHID", "");
	datamian.setValueByName("FPUSHNAME", "");
	rowid = datamian.saveData();
	opef();
	datamian.refreshData();
	tlv8.portal.callBack(tlv8.RequestURLParam.getParam("tabID"),
		"defRefreshData2", "ok");
}

// 改变发布状态
function opef() {
	var publishid = datamian.getValueByName("FPUSHID");
	if (publishid == null || publishid == "") {
		$("#push").find(".l-btn-text").text("发布");
		$("#push").attr("onclick", "pushData()");
	} else {
		$("#push").find(".l-btn-text").text("取消发布");
		$("#push").attr("onclick", "unpushData()");
	}
}