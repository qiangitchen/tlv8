/**
 * 设置当前用户信息
 */
function setCreatorInfo() {
	var deptid = justep.Context.getCurrentDeptID() == "" ? justep.Context
			.getCurrentOgnID() : justep.Context.getCurrentDeptID();
	var deptname = justep.Context.getCurrentDeptName() == "" ? justep.Context
			.getCurrentOgnName() : justep.Context.getCurrentDeptName();
	$("#FCREATEOGNID").val(justep.Context.getCurrentOgnID());
	$("#FCREATEOGNNAME").val(justep.Context.getCurrentOgnName());
	$("#FCREATEDEPTID").val(deptid);
	$("#FCREATEDEPTNAME").val(deptname);
	$("#FCREATEPOSID").val(justep.Context.getCurrentPosID());
	$("#FCREATEPOSNAME").val(justep.Context.getCurrentPosName());
	$("#FCREATEPERSONID").val(justep.Context.getCurrentPersonID());
	$("#FCREATEPERSONNAME").val(justep.Context.getCurrentPersonName());
	$("#FCREATEPERSONFID").val(justep.Context.getCurrentPersonFID());
	$("#FCREATEPERSONFNAME").val(justep.Context.getCurrentPersonFName());
	$("#FCREATETIME").val(tlv8.System.Date.sysDateTime());
}

/**
 * 获取当前服务器的年月，主要用于处理文件上传分类
 * 
 * @returns {String} 2013/12
 */
function getCurentYearandMonth() {
	var date = tlv8.System.Date.strToDate(tlv8.System.Date.sysDate());
	var year = date.getFullYear();
	var month = date.getMonth() + 1;
	return year + "/" + month;
}

function checkActio() {
	var activity_pattern = getParamValueFromUrl("activity-pattern");
	if (activity_pattern == "detail") {
		$("a").each(function() {
			var hr = $(this).attr("href");
			var click = $(this).attr("onclick");
			var data_iconpos = $(this).attr("data-iconpos");
			var data_icon = $(this).attr("data-icon");
			if (hr && hr != "") {
				if (hr.indexOf("openProcessSelectDialog(") > 0) {
					$(this).remove();
				}
			}
			if (click && click != null) {
				if (click.indexOf("openProcessSelectDialog(") > 0) {
					$(this).remove();
				}
			}
			if (data_iconpos == "right" && data_icon == "check") {
				$(this).remove();
			}
		});
	}
}

$(document).bind("pagebeforecreate", function() {
	checkActio();
});

// 通过view查看
function browseViewDoc(name, fileID) {
	if ('.doc.docx.xls.xlsx.ppt.pptx.txt'
			.indexOf(String(/\.[^\.]+$/.exec(name)) + '.') >= 0) {
		var url = cpath+"/mobileUI/OA/common/DocView/mainActivity.html?fileID="
				+ fileID + "&name=" + encodeURI(encodeURI(name));
		window.open(url, "_self");
	} else {
		alert("该文件类型不支持转换，请以原文件方式查看文件内容。");
	}
}

/**
 * 根据主数据ID，创建处理意见列表，需要在页面中自行添加div，id赋为：opinion_list
 * 
 * @param sData1
 * @return
 */
function getOpinionData(sData1) {
	var param = new tlv8.RequestParam();
	param.set("sdata1", sData1);
	var result = tlv8
			.XMLHttpRequest(
					"getExecuteOpinions",
					param,
					"POST",
					true,
					function(result) {
						var dataInfo = tlv8.strToXML(result.result);
						var opinions = dataInfo
								.getElementsByTagName("opinions");
						var length = opinions.length;
						if (length > 0) {
							var html = "<ul class=\"ui-listview ui-shadow mui-table-view\">"
									+ "<li class=\"ui-li ui-li-divider ui-bar-c mui-table-view-divider mui-table-view-cell\">"
									+ "<label style=\"font-size:17px;\">各环节处理意见</label></li>";
							for (var i = 0; i < length; i++) {
								var label = opinions[i].childNodes[0].textContent;
								var psnName = opinions[i].childNodes[1].textContent;
								var ctime = opinions[i].childNodes[2].textContent;
								var opinion = opinions[i].childNodes[3].textContent;
								opinion = opinion.replaceAll("\n", "<br/>");
								ctime = ctime.substring(0, ctime.length - 2);
								html += "<li class=\"ui-li ui-li-static ui-btn-up-c mui-table-view-cell\">"
										+ "<table><tr><td width=\"80px\">"
										+ "环节名称:</td><td><label class=\"ui-lable-info\">"
										+ label
										+ "</label></td></tr><tr><td>意&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;见:</td><td><label class=\"ui-lable-info\">"
										+ opinion
										+ "</label></td></tr><tr><td>处&nbsp;&nbsp;理&nbsp;&nbsp;人:</td><td><label class=\"ui-lable-info\">"
										+ psnName
										+ "</label></td></tr><tr><td>处理时间:</td><td><label class=\"ui-lable-info\">"
										+ ctime
										+ "</label></td></tr></table><li>";
							}
							html += "</ul>";
							$("#opinion_list").html(html);
						}
					});
	var isread = getParamValueFromUrl("activity-pattern");
	if (isread == "detail")
		$("#task_execute").hide();
}

/**
 * 打开流转选择对话框
 * 
 * @param
 * @return
 */
function openProcessSelectDialog(advance, transfer, back, abort, unapproval,commitCalback,backFun) {
	var div_bg = document.createElement("div");// 背景笼罩成
	$(div_bg).attr("id", "process_select_dialog_bg");
	$(div_bg).css("width", "100%");
	$(div_bg).css("height", $(document).height());
	$(div_bg).css("z-index", 1000);
	$(div_bg).css("position", "absolute");
	$(div_bg).css("left", "0px");
	$(div_bg).css("top", "0px");
	$(div_bg).css("right", "0px");
	$(div_bg).css("bottom", "0px");
	$(div_bg).css("filter", "alpha(opacity=50)");
	$(div_bg).css("-moz-opacity", "0.5");
	$(div_bg).css("-khtml-opacity", "0.5");
	$(div_bg).css("opacity", "0.5");
	$(div_bg).css("background", "#555");
	$(div_bg).css("text-align", "center");
	$(document.body).css("overflow", "hidden");
	$(document.body).append(div_bg);
	var cuScrollTop = (document.body.scrollTop || document.documentElement.scrollTop);
	var top = 45 + cuScrollTop;
	var div_content = document.createElement("div");// 内容层
	$(div_content).attr("id", "process_select_dialog_content");
	$(div_content).attr("class", "ui-page-active ui-corner-all mui-card");
	$(div_content).css("width", "130px");
	$(div_content).css("z-index", 1001);
	$(div_content).css("position", "absolute");
	$(div_content).css("right", "10px");
	$(div_content).css("top", top + "px");
	$(div_content).css("border", "0px");
	var html = [];
	html
			.push("<div data-role='content' class='ui-content ui-body-c' style='padding:10px;'>");
	if (!unapproval) {
		html
				.push("<a href='#' onclick='processApproval()' class='ui-btn ui-btn-up-b  ui-btn-corner-all mui-btn mui-btn-purple' style='width:110px; margin:2px;'>");
		html.push("<span style='padding:0.5em;display:block'>填写意见</span></a>");
	}
	if (advance) {
		html
				.push("<a href='#' onclick='processadvance(\""+commitCalback+"\")' class='ui-btn ui-btn-up-b  ui-btn-corner-all mui-btn mui-btn-success' style='width:110px; margin:2px;'>");
		html.push("<span style='padding:0.5em;display:block'>流转</span></a>");
	}
	if (transfer) {
		html
				.push("<a href='#' onclick='processtransfer()' class='ui-btn ui-btn-up-b ui-btn-corner-all mui-btn mui-btn-primary' style='width:110px; margin:2px;'>");
		html.push("<span style='padding:0.5em;display:block'>转发</span></a>");
	}
	if (back) {
		html
				.push("<a href='#' onclick='processback(\""+backFun+"\")' class='ui-btn ui-btn-up-b ui-btn-corner-all mui-btn mui-btn-warning' style='width:110px; margin:2px;'>");
		html.push("<span style='padding:0.5em;display:block'>回退</span></a>");
	}
	if (abort) {
		html
				.push("<a href='#' onclick='processabort()' class='ui-btn ui-btn-up-b ui-btn-corner-all mui-btn mui-btn-danger' style='width:110px; margin:2px;'>");
		html.push("<span style='padding:0.5em;display:block'>终止</span></a>");
	}
	html.push("<a href='#' onclick='viewFlowBot()' class='ui-btn ui-btn-up-b ui-btn-corner-all mui-btn mui-btn-chatbot' style='width:110px; margin:2px;'>");
	html.push("<span style='padding:0.5em;display:block'>查看流程图</span></a>");
	html.push("</div>");
	$(div_content).html(html.join(""));
	$(document.body).append(div_content);
	$(div_bg).bind("click", function() {
		closeRightMenu();
	});
}

function closeRightMenu(){
	$("#process_select_dialog_bg").remove();
	$("#process_select_dialog_content").remove();
	$(document.body).css("overflow", "auto");
}

// 填写审批意见
function processApproval() {
	tlv8.writeOpinion("");
}

// 流转
function processadvance(commitCalback) {
	var flowID = getParamValueFromUrl("flowID");
	var taskID = getParamValueFromUrl("taskID");
	var sData1 = getParamValueFromUrl("sData1");
	var activity = getParamValueFromUrl("activity");
	var url = cpath+"/mobileUI/system/service/process/dialog/processExecuteDialog.html?executeMode=advance";
	url += "&flowID=" + flowID;
	url += "&taskID=" + taskID;
	url += "&sData1=" + sData1;
	url += "&activity=" + activity;
//	window.open(url, "_self");
	closeRightMenu();
	justep.dialog.openFullScreenDialog("", {
		url : url,
		callback : function(re){
			if(typeof commitCalback=="string"){
				var afn = window.eval(commitCalback);
				if(typeof afn == "function"){
					afn();
				}
			}
			processComiitCallback(re);
		}
	});
}
// 转发
function processtransfer() {
	var flowID = getParamValueFromUrl("flowID");
	var taskID = getParamValueFromUrl("taskID");
	var sData1 = getParamValueFromUrl("sData1");
	var activity = getParamValueFromUrl("activity");
	var url = cpath+"/mobileUI/system/service/process/dialog/processExecuteDialog.html?executeMode=transfer";
	url += "&flowID=" + flowID;
	url += "&taskID=" + taskID;
	url += "&sData1=" + sData1;
	url += "&activity=" + activity;
//	window.open(url, "_self");
	closeRightMenu();
	justep.dialog.openFullScreenDialog("", {
		url : url,
		callback : processComiitCallback
	});
}
// 回退
function processback(backFun) {
	var flowID = getParamValueFromUrl("flowID");
	var taskID = getParamValueFromUrl("taskID");
	var sData1 = getParamValueFromUrl("sData1");
	var activity = getParamValueFromUrl("activity");
	var url = cpath+"/mobileUI/system/service/process/dialog/processExecuteDialog.html?executeMode=back";
	url += "&flowID=" + flowID;
	url += "&taskID=" + taskID;
	url += "&sData1=" + sData1;
	url += "&activity=" + activity;
//	window.open(url, "_self");
	closeRightMenu();
	justep.dialog.openFullScreenDialog("", {
		url : url,
		callback : function(re){
			if(typeof backFun=="string"){
				var afn = window.eval(backFun);
				if(typeof afn=="function"){
					afn();
				}
			}
			processComiitCallback(re);
		}
	});
}
// 终止
function processabort() {
	var flowID = getParamValueFromUrl("flowID");
	var taskID = getParamValueFromUrl("taskID");
	var sData1 = getParamValueFromUrl("sData1");
	var activity = getParamValueFromUrl("activity");
	var url = cpath+"/mobileUI/system/service/process/dialog/processExecuteDialog.html?executeMode=abort";
	url += "&flowID=" + flowID;
	url += "&taskID=" + taskID;
	url += "&sData1=" + sData1;
	url += "&activity=" + activity;
//	window.open(url, "_self");
	closeRightMenu();
	justep.dialog.openFullScreenDialog("", {
		url : url,
		callback : processComiitCallback
	});
}

function viewFlowBot(){
	var flowID = getParamValueFromUrl("flowID");
	var taskID = getParamValueFromUrl("taskID");
	var url = cpath+"/mobileUI/system/service/process/flw/viewiocusbot/yj_iocus_bot.html?flowID=" + flowID + "&taskID=" + taskID;
	window.open(url, "_self");
}

function processComiitCallback(){
	window.history.back();
	setTimeout(function(){
		window.history.back();//调用两次回退 防止页面停留
	},100);
}

// 文件大小转换
function translateDocSize(size) {
	size = parseInt(size);
	if (size < 1024) {// B
		return size + "B";
	} else if (size < 1048576) {// KB
		return (size / 1024).toFixed(1) + "KB";
	} else {// MB
		return (size / 1048576).toFixed(2) + "MB";
	}
}

// 转换小平台文档格式
function translateDocInfo(value) {
	var re = "";
	try {
		re = eval("(" + value + ")");
	} catch (e) {
		if (value && value != "") {
			value = value.toString().replaceAll(":", ':"');
			value = value.toString().replaceAll(",", '",');
			value = value.toString().replaceAll("}", '"}');
			value = value.toString().replaceAll("}{", "},{");
			value = value.toString().replaceAll(";", '",');
			re = eval("([" + value + "])");
		} else {
			re = "";
		}
	}
	return re;
}

function showSelectPersonInfo(rowid) {
	var html = '<ul data-role="listview" class="ui-listview">';
	var parm = new tlv8.RequestParam();
	parm.set("fid", rowid);
	var result = tlv8.XMLHttpRequest("getNoticePersonAction?temp="
			+ new Date().getMilliseconds(), parm);
	if (result.data.flag == "true") {
		var data = result.data.data;
		if (typeof (data) == "string") {
			data = window.eval("(" + data + ")");
		}
		for (var i = 0; i < data.length; i++) {
			html += '<li class="ui-li ui-li-static ui-btn-up-c ui-first-child" rel="list" >';
			html += '<p class="ui-li-desc" ><strong  style="font-size:14px;">查&nbsp;&nbsp;阅&nbsp;&nbsp;人：<label class="ui-link">('
					+ data[i].FPERSONNAME
					+ ')</label></strong></p><p class="ui-li-desc">';
			html += '<strong style="font-size:14px;">是否查阅：<label class="ui-link">('
					+ data[i].FBROWSE
					+ ')</label></strong></p><p class="ui-li-desc">';
			if (data[i].FREADDATE) {
				html += '<strong style="font-size:14px;">查阅时间：<label class="ui-link">('
						+ data[i].FREADDATE + ')</label></strong></p>';
			}
			html += '</li>';
		}
		$("#showPersonInfo").html(html);
	}
}

/**
 * 跟新查看状态
 * 
 * @param rowid
 */
function refreshBrowse(rowid, isnew) {
	if (isnew == null || isnew == "") {
		isnew = "true";
	}
	var parm = new tlv8.RequestParam();
	parm.set("rowid", rowid);
	parm.set("isnew", isnew);
	tlv8.XMLHttpRequest("updateNoticeBrowseAction", parm);
}

$(document).ready(function(){
	if (!checkPathisHave($dpjspath + "dialog.js"))
		createJSSheet($dpjspath + "dialog.js");
});
