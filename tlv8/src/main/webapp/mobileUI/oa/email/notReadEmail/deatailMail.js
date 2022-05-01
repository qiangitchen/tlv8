var type, rowid, status;
var Operator = "";
$(document).ready(
		function() {
			var title = deCode(getParamValueFromUrl("title"));
			rowid = getParamValueFromUrl("rowid");
			type = getParamValueFromUrl("type");
			status = deCode(getParamValueFromUrl("status"));
			var name = deCode(getParamValueFromUrl("name"));
			var orgname = deCode(getParamValueFromUrl("orgname"));
			var time = deCode(getParamValueFromUrl("time"));
			if (type == "send") {
				if (status == "已发送") {
					Operator = "transmit";
					$("#mailAitem").find(".ui-btn-text").text("转发");
				} else
					$("#mailAitem").find(".ui-btn-text").text("编辑");
			}
			initTitle(title, name, orgname, time);
			$("#contenView").height(
					$(document).height() - $("#titleView").height() - 135);
			loadDeatail(rowid, type);
		});

function initTitle(title, name, orgname, time) {
	var html = '<div style="padding:10px;">';
	html += '<p class="ui-li-desc">主题：' + title + '</p>';
	html += '<p class="ui-li-desc">时间：' + time + '</p>';
	html += '<p class="ui-li-desc">发件人：' + name + '</p>';
//	html += '<p class="ui-li-desc">职务：' + orgname + '</p>';
	html += '</div>';
	$("#titleView").html(html);
}

function loadDeatail(rowid, type) {
	var param = new tlv8.RequestParam();
	param.set("rowid", rowid);
	param.set("type", type);
	var re = tlv8.XMLHttpRequest("getMailDeatailAction", param, "post");
	if (re.data.flag == "true") {
		var r = re.data.data;
		if (typeof r == "string") {
			r = window.eval("(" + r + ")");
		}
		$("#contenView").html(r[0].FTEXT);
		var FATTACHMENT = r[0].FFJID;
		var html = "";
		if (FATTACHMENT && FATTACHMENT != "") {
			FATTACHMENT = translateDocInfo(FATTACHMENT);
			for (var i = 0; i < FATTACHMENT.length; i++) {
				var docName = FATTACHMENT[i].filename;
				var fileID = FATTACHMENT[i].fileID;
				html += i + 1 + ".<label class='ui-lable-info'>" + docName
						+ "&nbsp;&nbsp;&nbsp;&nbsp;</label>"
						+ "<a href=\"#popupMenu\" onclick=\"browseSourceDoc('"
						+ fileID + "')\">查看文件</a><br/>";
			}
			$("#_content").html(html);
		}
	}
}

function mailAction() {
	var acurl = "../writeEmail/mainActivity.html?type=" + type + "&rowid="
			+ rowid + "&Operator=" + Operator;
	window.open(acurl + '&temp=' + new Date().valueOf(), '_self');
}

function goback() {
	if (type == "send") {
		history.back();
	} else {
		var acurl = "mainActivity.html?temp=" + new Date().valueOf();
		window.open(acurl, '_self');
	}
}