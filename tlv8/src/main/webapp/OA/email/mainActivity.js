/*==数据源===此项为必须定义==*/
var write_data;
var data_info;
var write_rowid = "";

var checkedRow = new Map();
/*
 * 页面加载事件
 */
function init() {
	$(".funcListTR").bind("mouseover", function(e) {
		this.style.background = "#eee";
	});
	$(".funcListTR").bind("mouseout", function(e) {
		if ($(this).attr("bk") == "")
			this.style.background = "";
	});
	$(".funcListTR").bind("click", function(e) {
		var self = this;
		$("#div_search").show();
		$("#writeMailView").hide();
		$("#searchList").hide();
		$("#lookMailView").hide();
		$("#CollectList").hide();
		$(".funcListTR").each(function(ev) {
			if (this != self) {
				this.style.background = "";
				$(this).attr("bk", "");
				$("#" + $(this).attr("rel")).hide();
			} else {
				this.style.background = "#eee";
				$(this).attr("bk", "#eee");
				$("#" + $(this).attr("rel")).show();
			}
		});
		if ($(this).attr("rel") == "templetList") {
			loadTempletMail();// 加载草稿箱
		}
		if ($(this).attr("rel") == "receiveList") {
			loadRecvMail();// 加载收件
		}
		if ($(this).attr("rel") == "sendList") {
			loadSendMail();// 加载发件
		}
		if ($(this).attr("rel") == "collectList") {
			loadCollectMail();// 加载发件
		}
	});
	loadRecvMail();
	$("textarea").val("");
	$("#ipt_search").val("");
	initData();

	$("#ipt_search").bind("keyup", function(event) {
		if (event.keyCode == 13) {
			loadSearchMail();
		}
	});

	$(window).resize(function() {
		$("#mainlayout").layout('resize');
		$("#panelview").panel('resize');
	});
};

function initData() {
	data_info = new tlv8.Data();
	data_info.setDbkey("oa");
	data_info.setFormId("mail_look");
	write_data = new tlv8.Data();
	write_data.setDbkey("oa");
	write_data.setFormId("WRITE_DATA_FORM");
	write_data.setTable("OA_EM_SENDEMAIL");
	write_data.saveData = saveToTempletAction;

}
/*
 * 写信
 */
function writeMail(rowid, isnew) {
	try {
		$(".write_content").xheditor(false);// 清空编辑器，但由于没有初始化就会报错，因此增加try catch
	} catch (e) {
	}
	if (rowid == "" || rowid == null) {
		wirtedataInsert();
		$("#FSENDPERNAME").text(tlv8.Context.getCurrentPersonName());
		$("#fConsignee").val("");
	} else {
		if (!isnew)
			write_rowid = rowid;
		else
			write_rowid = "";
		write_data.setFilter("");
		J$("WRITE_DATA_FORM").reset();
		J$("WRITE_DATA_FORM").rowid = rowid;
		J$("WRITE_DATA_FORM").setAttribute("rowid", rowid);
		$("#WRITE_DATA_FORM").attr("rowid", rowid);
		write_data.refreshData();
	}
	$(".lookViewer").hide();
	$("#lookMailView").hide();
	$("#searchList").hide();
	$("#div_search").hide();
	$("#writeMailView").show();
	current_recv_page = 1;
	current_send_page = 1;
	current_temp_page = 1;
	current_search_page = 1;
	new tlv8.fileComponent(document.getElementById("write_fj"), write_data,
			"FFJID", "/root/邮箱/" + getCurentYearandMonth(), true, true, false,
			false);
	$(".write_content")
			.xheditor(
					"{tools:'full',﻿﻿﻿﻿upImgUrl:'xhUpload',upImgExt:'jpg,jpeg,gif,png'}");
}

// 新增数据
function wirtedataInsert() {
	write_rowid = "";
	write_data.setFilter("");
	J$("WRITE_DATA_FORM").reset();
	J$("WRITE_DATA_FORM").rowid = "";
	J$("WRITE_DATA_FORM").setAttribute("rowid", "");
	$("#WRITE_DATA_FORM").attr("rowid", "");
	// $("#show_fj_title").show();
	// $("#write_fj").hide();
}

// 删除查看邮件
function deleteCurrentMail() {
	var rowid = $("#mail_look").attr("rowid");
	var type = $("#mail_look").attr("mailType");
	if (confirm("删除后将不能再回复，确定删除吗?")) {
		var param = new tlv8.RequestParam();
		param.set("rowid", rowid);
		param.set("type", type);
		var re = tlv8.XMLHttpRequest("DeleteMailAction", param, "post");
		if (re.data.flag == "true") {
			if (type == "发件箱")
				loadSendMail();// 加载发件
			else if (type == "草稿箱")
				loadTempletMail();// 加载草稿箱
			else
				loadRecvMail();// 加载收件
			tlv8.showMessage("邮件删除成功");
		} else {
			$.messager.alert('警告', '删除邮件出错', 'warning');
		}
	}
}

// 保存到草稿箱
function saveToTempletAction() {
	var param = new tlv8.RequestParam();
	param.set("fconsigneeid", $("#FCONSIGNEEID").val());
	param.set("fconsignee", $("#FCONSIGNEE").val());
	param.set("femailname", $("#FEMAILNAME").val());
	param.set("ftext", $("#FTEXT").val());
	param.set("fjinfo", $(".FJINFO").val());
	param.set("actype", "save");
	param.set("rowid", write_rowid);
	var re = tlv8.XMLHttpRequest("sendMailAction", param, "post", false);
	if (re.data.flag == "true") {
		write_rowid = re.rowid;
		J$("WRITE_DATA_FORM").rowid = write_rowid;
		J$("WRITE_DATA_FORM").setAttribute("rowid", write_rowid);
		$("#WRITE_DATA_FORM").attr("rowid", write_rowid);
		write_data.refreshData();
		tlv8.showMessage("保存草稿成功");
	} else {
		$.messager.alert('错误', re.data.message, 'error');
	}
	return write_rowid;
}
function afterWriteRefersh() {
	// new tlv8.fileComponent(document.getElementById("write_fj"),
	// write_data, "FFJID", "/root/邮箱/" + getCurentYearandMonth(), true,
	// true, false, false);
	// $(".write_content")
	// .xheditor(
	// "{tools:'full',﻿﻿﻿﻿upImgUrl:'xhUpload',upImgExt:'jpg,jpeg,gif,png'}");
	// $("#show_fj_title").hide();
	// $("#write_fj").show();
}

// 发送邮件
function sendMail() {
	var fconsigneeid = $("#FCONSIGNEEID").val();
	if (fconsigneeid == "" || fconsigneeid == null) {
		$.messager.alert('警告', '请先选择收件人后再发送', 'warning');
		return;
	}
	var param = new tlv8.RequestParam();
	param.set("fconsigneeid", fconsigneeid);
	// param.set("fconsigneecode", "");
	param.set("fconsignee", $("#FCONSIGNEE").val());
	param.set("femailname", $("#FEMAILNAME").val());
	param.set("ftext", $("#FTEXT").val());
	param.set("fjinfo", $(".FJINFO").val());
	param.set("actype", "send");
	param.set("rowid", write_rowid);
	var re = tlv8.XMLHttpRequest("sendMailAction", param);
	if (re.data.flag == "true") {
		write_rowid = re.rowid;
		$.messager.alert('提示', '邮件发送成功');
		loadSendMail();
	} else {
		$.messager.alert('错误', re.data.message, 'error');
	}
}

// 转发
function transoutCheckedMail() {
	var keyset = checkedRow.keySet();
	if (keyset.length > 0) {
		var rowid = keyset[0];
		var type = checkedRow.get(rowid);
		var EmailName, Text, JID;
		if (type == "发件箱") {
			var senddata = justep.xbl("sendData");
			senddata.setFilter("_senddata_", "OA_EM_SendEmail = '" + rowid
					+ "'");
			senddata.refreshData();
			EmailName = senddata.getValue("fEmailName");
			Text = senddata.getValue("fText");
			JID = senddata.getValue("fFJID");
		} else {
			var reviceData = justep.xbl("reviceData");
			reviceData.setFilter("_reviceData_", "OA_EM_ReceiveEmail = '"
					+ rowid + "'");
			reviceData.refreshData();
			EmailName = reviceData.getValue("fEmailName");
			Text = reviceData.getValue("fText");
			JID = reviceData.getValue("fFJID");
		}
		var data = justep.xbl("writeData");
		data.newData();
		var fEmailName = "转发：" + EmailName;
		var fText = Text;
		var FJID = JID;
		data.setValue("fEmailName", fEmailName);
		data.setValue("fText", fText);
		data.setValue("fFJID", FJID);
		$(".lookViewer").hide();
		$("#lookMailView").hide();
		$("#writeMailView").show();
		$("#FSENDPERNAME").text(tlv8.Context.getCurrentPersonName());
		$("#fConsignee").val("");
		// $("#fTitle").val(fEmailName);
		// $("#fContent").val(fText);
	} else {
		tlv8.showMessage("请勾选需要转发的邮件");
	}
}

// 转发当前
function transoutCuurrentMail() {
	// var rowid = $("#mail_look").attr("rowid");
	// var type = $("#mail_look").attr("mailType");
	// debugger;
	writeMail();
	var mailname = data_info.getValueByName("FEMAILNAME");
	var content = data_info.getValueByName("FTEXT");
	var fj = data_info.getValueByName("FFJID");
	$("#FEMAILNAME").val("转发：" + mailname);
	$(".write_content").html(content);
	$(".FJINFO").val(fj);
	new tlv8.fileComponent(document.getElementById("write_fj"), data_info,
			"FFJID", "/root/邮箱/" + getCurentYearandMonth(), false, false,
			false, false);
	$(".write_content")
			.xheditor(
					"{tools:'full',﻿﻿﻿﻿upImgUrl:'xhUpload',upImgExt:'jpg,jpeg,gif,png'}");
	// $("#write_fj").show();
	$(".lookViewer").hide();
	$("#lookMailView").hide();
	$("#searchList").hide();
	$("#div_search").hide();
	$("#writeMailView").show();
	current_recv_page = 1;
	current_send_page = 1;
	current_temp_page = 1;
	current_search_page = 1;
}

function collectMail() {
	if (confirm("确认要收藏此邮件吗?")) {
		data_info.setValueByName("FCOLLECT", "1");
		data_info.saveData();
		$.messager.alert('提示', '收藏成功!');
	}
	checkCollectState();
}
function unCollectMail() {
	data_info.setValueByName("FCOLLECT", "0");
	data_info.saveData();
	$.messager.alert('提示', '取消收藏成功!');
	checkCollectState();
}

// 发送选中的草稿
function sendTempMail() {
	var keyset = checkedRow.keySet();
	if (keyset.length > 1) {
		tlv8.showMessage("请只选择一封邮件进行发送.");
	} else if (keyset.length == 1) {
		var rowid = keyset[0];
		writeMail(rowid);
	} else {
		tlv8.showMessage("请选择一封邮件进行发送.");
	}
}

// 全部标记为已读
function martAlltoRead() {
	if (!confirm("确定将所有的未读邮件都标记为已查看吗?"))
		return;
	var param = new tlv8.RequestParam();
	param.set("consigneeid", tlv8.Context.getCurrentPersonID());
	tlv8.XMLHttpRequest("receiveEmail/martAlltoRead", param, "post", false);
	loadRecvMail();// 加载收件
}

function selectReceivePsm() {
	tlv8.portal.dailog.openDailog("选择人员",
			"/comon/SelectDialogPsn/SelectChPsm.html?temp="
					+ new Date().getUTCMilliseconds(), 650, 420,
			selectReceivePsmCallback, null);
}
function selectReceivePsmCallback(data) {
	var ids = data.id;
	var names = data.name;
	$("#FCONSIGNEEID").val(ids);
	$("#FCONSIGNEE").val(names);
}

// 邮件回复
function replyCurrentMail() {
	var rowid = $("#mail_look").attr("rowid");
	var type = $("#mail_look").attr("mailType");
	var param = new tlv8.RequestParam();
	param.set("rowid", rowid);
	param.set("type", type);
	var re = tlv8.XMLHttpRequest("getMailDeatailAction", param);
	if (re.data.flag == "true") {
		writeMail();
		var data = re.data.data;
		data = window.eval("(" + data + ")");
		$("#FCONSIGNEEID").val(data[0].FSENDPERID);
		$("#FCONSIGNEE").val(data[0].FSENDPERNAME);
		$("#FEMAILNAME").val("回复:" + data[0].FEMAILNAME);
		$("#FTEXT").val(
				"<br/><br/><br/>----------原始邮件----------<br/><br/>"
						+ data[0].FTEXT);
	}

}

function replySendMail(type) {
	if (type == "all") {
		var keyset = checkedRow.keySet();
		if (keyset.length > 1) {
			tlv8.showMessage("请只选择一封邮件进行编辑.");
		} else if (keyset.length == 1) {
			var rowid = keyset[0];
			writeMail(rowid, true);
		} else {
			tlv8.showMessage("请选择一封邮件进行编辑.");
		}
	} else {
		var rowid = $("#mail_look").attr("rowid");
		writeMail(rowid, true);
	}
}