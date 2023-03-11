//工具条
var toolbarItem;
function init_toolbar() {
	var bardiv = J$("stander_bar");
	var toobar = new tlv8.toolbar(bardiv, false, true, "readonly", true);
	toolbarItem = toobar.items;
}

// 数据配置
var datamian;
function initDocumentPage() {
	datamian = new tlv8.Data();
	datamian.setDbkey("system");
	datamian.setTable("MAS_WRITEMESSAGE");
	datamian.setFormId("MAIN_DATA_FORM");
	datamian.setonDataValueChanged(data_datamian_Changed);
}

function data_datamian_Changed() {
	toolbarItem.setItemStatus("no", true, "no", "no");// 设置按钮状态
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
	var rowid = datamian.saveData();
	if(!rowid){
		return false;
	}
	J$("sendBtn").disabled = false;
	toolbarItem.setItemStatus("no", "readonly", true, "no");// 设置按钮状态
}

// 数据刷新
function dataRefresh() {
	datamian.refreshData();
}

// 数据删除
function dataDeleted() {
	if (datamian.deleteData()) {
		dataRefresh();
	}
}

function selectSendGroup() {
	var currentgrpid = $("#SRANGEID").val();
	var url = "/SA/shortmessage/dialog/groupSelect.html?currentgrpid="
			+ currentgrpid;
	tlv8.portal.dailog.openDailog("选择群组", url, 600, 400, selgroupcallback);
}

function selgroupcallback(rdata) {
	$("#SRANGEID").val(rdata.id);
	$("#SRANGENAME").val(rdata.name);
}

function selectSendPerson() {
	var url = "/comon/SelectDialogPsn/SelectChPsm.html";
	tlv8.portal.dailog.openDailog("选择接收人", url, 600, 400,
			selpersoncallback);
}

function selpersoncallback(rdata) {
	$("#SPERSONIDS").val(rdata.id);
	$("#SPERSONNAMES").val(rdata.name);
}

function sendMessage() {
	J$("sendBtn").disabled = true;
	var rangid = $("#SRANGEID").val();
	var psms = $("#SPERSONIDS").val();
	var phones = $("#SPHONES").val();
	if (rangid != "" || psms != "" || phones != "") {
		var param = new tlv8.RequestParam();
		param.set("rowid", $("#MAIN_DATA_FORM").attr("rowid"));
		var r = tlv8.XMLHttpRequest("sendMessageByWriteAction", param,
				"post", false);
		var sdata = r.data;
		if (sdata.flag == "false") {
			//alert(sdata.message);
			$.messager.alert('错误',sdata.message,'error');
		} else {
			//alert("发送成功!");
			$.messager.alert('信息', "发送成功!", 'info');
		}
	} else {
		//alert("请先选择发送给：群组、指定的人员或手机号码!");
		$.messager.alert('警告', "请先选择发送给：群组、指定的人员或手机号码!", 'warning');
	}
}