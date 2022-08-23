var flwCompent;// 流程组件
function init_toolbar() {
	var bardiv = J$("stander_bar");
	new tlv8.toolbar(bardiv, false, true, false, true);
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
		back : false,// 流转按钮
		out : true,// 流转按钮
		transmit : false,// 转发按钮
		pause : false,// 暂停按钮
		stop : true
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
	init_toolbar();
	datamian = new tlv8.Data();
	datamian.setDbkey("oa");
	datamian.setTable("OA_RE_MONTHREPORT");
	datamian.setFormId("MAIN_DATA_FORM");
	rowid = tlv8.RequestURLParam.getParam("sData1");
	if (rowid == "" || rowid == null) {
		alert("数据传输失败");
	} else {
		document.getElementById("MAIN_DATA_FORM").rowid = rowid;
		J$("MAIN_DATA_FORM").setAttribute("rowid", rowid);
		datamian.refreshData();
	}
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
	rowid = datamian.saveData();
	return true;
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

function afterDatarefresh() {
	new tlv8.fileComponent(document.getElementById("fileCompDiv"),
			datamian, "FFILE", "/root/报表管理/月报/" + getCurentYearandMonth());
	showSelectPersonInfo(rowid);
	flwCompent = new tlv8.flw("flowToolbar", datamian, setting);
}

// 流程结束
function onAdvanceCommitDef(event) {
	datamian.setValueByName("FPUSHDATETIME", tlv8.System.Date
			.sysDateTime());
	datamian.saveData();
}