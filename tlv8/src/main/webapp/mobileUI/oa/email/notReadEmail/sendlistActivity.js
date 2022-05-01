if (!justep)
	var justep = {};
/*
 * 邮箱-收件箱
 */
justep.createSendMailListView = function(data) {
	if (typeof data == "string") {
		data = window.eval("(" + data + ")");
	}
	var html = '<ul data-role="listview" class="ui-listview mui-table-view mui-table-view-striped mui-table-view-condensed">';
	for (var i = 0; i < data.length; i++) {
		if (data[i].TITLE || data[i].TITLE != "") {
			html += '<li class="ui-li ui-li-static ui-btn-up-c ui-first-child mui-table-view-cell mui-media" id="'
					+ data[i].ID
					+ '" rel="list_send" title="'
					+ data[i].TITLE
					+ '" name="'
					+ data[i].NAME
					+ '" orgname="'
					+ data[i].ORGNAME
					+ '" time="'
					+ data[i].TIME
					+ '" status="' + data[i].STATUS + '">';
			html += '<a class="ui-link" href="javascript:void(0);" style="text-decoration : none;">';
			if (data[i].STATUS == "已发送") {
				html+= '<img src="image/yidu_h.png" class="ui-li-icon ui-li-thumb mui-media-object mui-pull-left"/>';
				html += data[i].TITLE;
			} else {
				html += '<img src="image/weidu_h.png" class="ui-li-icon ui-li-thumb mui-media-object mui-pull-left"/>';
				html += data[i].TITLE + '--(草稿)';
			}
			html += '<div class="mui-media-body">';
			html += '<p class="ui-li-desc"><strong>发往: </strong>'
					+ data[i].ORGNAME + '  '+data[i].TIME+ '</p>';
			html += '</div>';
			html += '</a></li>';
		}
	}
	html += '</ul>';
	return html;
};

$(document).ready(function(){
	loadSendList();
});

function loadSendList() {
	var result = tlv8.XMLHttpRequest("getSendMailAction", null, "post");
	if (result.count) {
		$("#send_title_val").text("(" + result.count + "封)");
	}
	var data = result.data.data;
	$("#sendContenView").html(justep.createSendMailListView(data));
	$("li[rel='list_send']").click(function() {
		var status = $(this).attr("status");
		var url = "deatailMail.html";
		if(status!="已发送"){
			url = "../writeEmail/mainActivity.html";
		}
		url +="?rowid=" + $(this).attr("id");
		url += "&title=" + enCode($(this).attr("title"));
		url += "&name=" + enCode($(this).attr("name"));
		url += "&orgname=" + enCode($(this).attr("orgname"));
		url += "&time=" + enCode($(this).attr("time"));
		url += "&type=send";
		url += "&status=" + enCode($(this).attr("status"));
		window.open(url, "_self");
	});
}

function writeMail() {
	window.open("../writeEmail/mainActivity.html?type=send", "_self");
}

function doSendSearch() {
	var name = $("#search_text").val();
	if (name && name != "") {
		var param = new tlv8.RequestParam();
		param.set("filter", name);
		var result = tlv8.XMLHttpRequest("getSendMailAction", param,
				"post");
		var data = result.data.data;
		$("#sendContenView").html(justep.createSendMailListView(data));
	}
	justep.dialog.close();
}