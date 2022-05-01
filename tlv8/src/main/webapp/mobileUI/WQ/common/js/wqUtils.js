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

// 获取文档服务地址
function getDocHost() {
	var dHost = window.location.host;
	if (dHost.indexOf(":") > 0) {
		dHost = dHost.split(":")[0];
	}
	if (dHost.indexOf("59.216") > -1) {
		dHost = "59.216.1.3";
	} else {
		dHost = "10.0.0.12";
	}
	var Url = window.location.protocol + "//" + dHost;
	return Url;
}

// 查看原文件
function browseSourceDoc(fileID) {
	var url = getDocHost() + ":8080/DocServer/repository/file/view/" + fileID
			+ "/last/content";
	try {
		justepYnApp.browseDoc(url);
	} catch (e) {
		window.open(url);
	}
}

// 通过view查看
function browseViewDoc(name, fileID) {
	if ('.doc.docx.xls.xlsx.ppt.pptx.txt'
			.indexOf(String(/\.[^\.]+$/.exec(name)) + '.') >= 0) {
		var url = "/tlv8/mobileUI/OA/common/DocView/mainActivity.html?fileID="
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
							var html = "<ul class=\"ui-listview ui-shadow\">"
									+ "<li class=\"ui-li ui-li-divider ui-bar-c\">"
									+ "<label style=\"font-size:17px;\">各环节处理意见</label></li>";
							for ( var i = 0; i < length; i++) {
								var label = opinions[i].childNodes[0].textContent;
								var psnName = opinions[i].childNodes[1].textContent;
								var ctime = opinions[i].childNodes[2].textContent;
								var state = opinions[i].childNodes[3].textContent;
								var opinion = opinions[i].childNodes[4].textContent;
								opinion = opinion.replaceAll("\n", "<br/>");
								ctime = ctime.substring(0, ctime.length - 2);
								html += "<li class=\"ui-li ui-li-static ui-btn-up-c\">"
										+ "<table><tr><td width=\"80px\">"
										+ "环节名称:</td><td><label class=\"ui-lable-info\">"
										+ label
										+ "</label></td></tr><tr><td>意&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;见:</td><td><label class=\"ui-lable-info\">"
										+ opinion
										+ "</label></td></tr><tr><td>处&nbsp;&nbsp;理&nbsp;&nbsp;人:</td><td><label class=\"ui-lable-info\">"
										+ psnName
										+ "("
										+ state
										+ ")"
										+ "</label></td></tr><tr><td>完成时间:</td><td><label class=\"ui-lable-info\">"
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
function openProcessSelectDialog(advance, transfer, back, abort) {
	var sHeight = document.body.clientHeight;
	if (window.innerHeight && window.scrollMaxY) {
		sHeight = window.innerHeight + window.scrollMaxY;
	} else if (document.body.scrollHeight > document.body.offsetHeight) {
		sHeight = document.body.scrollHeight;
	} else {
		sHeight = document.body.offsetHeight;
	}
	var div_bg = document.createElement("div");// 背景笼罩成
	var bg_style = "width:100%;height:"
			+ sHeight
			+ "px;z-index:1000;background:#161616;opacity: 0.5;position:absolute;top:0px;left:0px;";
	$(div_bg).attr("id", "process_select_dialog_bg");
	$(div_bg).attr("style", bg_style);
	var div_content = document.createElement("div");// 内容层
	var conten_style = "width:130px;z-index:1001;position:absolute;top:45px;;right:10px;border:0;";
	$(div_content).attr("id", "process_select_dialog_content");
	$(div_content).attr("class", "ui-page-active ui-corner-all");
	$(div_content).attr("style", conten_style);
	var html = [];
	html
			.push("<div data-role='content' class='ui-content ui-body-c' style='padding:0 10px;'>");
	if (advance) {
		html
				.push("<a href='#' onclick='processadvance()' class='ui-btn ui-btn-up-b  ui-btn-corner-all'>");
		html.push("<span style='padding:0.5em;display:block'>流转</span></a>");
	}
	if (transfer) {
		html
				.push("<a href='#' onclick='processtransfer()' class='ui-btn ui-btn-up-b ui-btn-corner-all'>");
		html.push("<span style='padding:0.5em;display:block'>转发</span></a>");
	}
	if (back) {
		html
				.push("<a href='#' onclick='processback()' class='ui-btn ui-btn-up-b ui-btn-corner-all'>");
		html.push("<span style='padding:0.5em;display:block'>回退</span></a>");
	}
	if (abort) {
		html
				.push("<a href='#' onclick='processabort()' class='ui-btn ui-btn-up-b ui-btn-corner-all'>");
		html.push("<span style='padding:0.5em;display:block'>终止</span></a>");
	}
	html.push("</div>");
	$(div_content).html(html.join(""));
	$(document.body).append(div_bg);
	$(document.body).append(div_content);
	$(div_bg).bind("click", function() {
		$("#process_select_dialog_bg").remove();
		$("#process_select_dialog_content").remove();
	});
}

//流转
function processadvance() {
	var flowID = getParamValueFromUrl("flowID");
	var taskID = getParamValueFromUrl("taskID");
	var sData1 = getParamValueFromUrl("sData1");
	var activity = getParamValueFromUrl("activity");
	var url = "/tlv8/mobileUI/system/service/process/dialog/processExecuteDialog.html?executeMode=advance";
	url += "&flowID=" + flowID;
	url += "&taskID=" + taskID;
	url += "&sData1=" + sData1;
	url += "&activity=" + activity;
	window.open(url, "_self");
}
// 转发
function processtransfer() {
	var flowID = getParamValueFromUrl("flowID");
	var taskID = getParamValueFromUrl("taskID");
	var sData1 = getParamValueFromUrl("sData1");
	var activity = getParamValueFromUrl("activity");
	var url = "/tlv8/mobileUI/system/service/process/dialog/processExecuteDialog.html?executeMode=transfer";
	url += "&flowID=" + flowID;
	url += "&taskID=" + taskID;
	url += "&sData1=" + sData1;
	url += "&activity=" + activity;
	window.open(url, "_self");
}
// 回退
function processback() {
	var flowID = getParamValueFromUrl("flowID");
	var taskID = getParamValueFromUrl("taskID");
	var sData1 = getParamValueFromUrl("sData1");
	var activity = getParamValueFromUrl("activity");
	var url = "/tlv8/mobileUI/system/service/process/dialog/processExecuteDialog.html?executeMode=back";
	url += "&flowID=" + flowID;
	url += "&taskID=" + taskID;
	url += "&sData1=" + sData1;
	url += "&activity=" + activity;
	window.open(url, "_self");
}
// 终止
function processabort() {
	var flowID = getParamValueFromUrl("flowID");
	var taskID = getParamValueFromUrl("taskID");
	var sData1 = getParamValueFromUrl("sData1");
	var activity = getParamValueFromUrl("activity");
	var url = "/tlv8/mobileUI/system/service/process/dialog/processExecuteDialog.html?executeMode=abort";
	url += "&flowID=" + flowID;
	url += "&taskID=" + taskID;
	url += "&sData1=" + sData1;
	url += "&activity=" + activity;
	window.open(url, "_self");
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
