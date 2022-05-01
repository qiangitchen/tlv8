if (!justep)
	var justep = {};
/*
 * 邮箱-收件箱
 */
justep.createReceiveMailListView = function(data) {
	if (typeof data == "string") {
		data = window.eval("(" + data + ")");
	}
	var html = '<ul data-role="listview" class="ui-listview mui-table-view mui-table-view-striped mui-table-view-condensed">';
	for (var i = 0; i < data.length; i++) {
		if (data[i].TITLE || data[i].TITLE != "") {
			html += '<li class="ui-li ui-li-static ui-btn-up-c ui-first-child mui-table-view-cell mui-media" id="'
					+ data[i].ID
					+ '" rel="list_recv" title="'
					+ data[i].TITLE
					+ '" name="'
					+ data[i].NAME
					+ '" orgname="'
					+ data[i].ORGNAME + '" time="' + data[i].TIME + '">';
			html += '<a class="ui-link" href="javascript:void(0);" style="text-decoration : none;">';
			if (data[i].STATUS == "未查看") {
				html += '<img src="image/weidu_h.png" class="ui-li-icon ui-li-thumb mui-media-object mui-pull-left"/>';
			} else {
				html += '<img src="image/yidu_h.png" class="ui-li-icon ui-li-thumb mui-media-object mui-pull-left"/>';
			}
			html += data[i].TITLE;
			html += '<div class="mui-media-body">';
			html += '<p class="ui-li-desc"><strong>来自: </strong>'
					+ data[i].ORGNAME + " - " + data[i].NAME + '  '
					+ data[i].TIME + '</p>';
			html == '</div>';
			html += '</a></li>';
		}
	}
	html += '</ul>';
	return html;
};

$(document).ready(
		function() {
			$("#mainbody").height($(document).height() - 50);
			var result = tlv8.XMLHttpRequest("getReceiveMailAction", null,
					"post");
			if (result.count) {
				$("#title_val").text("邮件(" + result.count + "封)");
			}
			var data = result.data.data;
			$("#contenView").html(justep.createReceiveMailListView(data));
			$("li[rel='list_recv']").click(function() {
				var url = "deatailMail.html?rowid=" + $(this).attr("id");
				url += "&title=" + enCode($(this).attr("title"));
				url += "&name=" + enCode($(this).attr("name"));
				url += "&orgname=" + enCode($(this).attr("orgname"));
				url += "&time=" + enCode($(this).attr("time"));
				url += "&type=receive";
				window.open(url, "_self");
			});
		});

function doSearch(name) {
	if (name && name != "") {
		$.mobile.showPageLoadingMsg();
		var param = new tlv8.RequestParam();
		param.set("filter", name);
		var result = tlv8.XMLHttpRequest("getReceiveMailAction", param,
				"post");
		$.mobile.hidePageLoadingMsg();
		var data = result.data.data;
		if (typeof data == "string") {
			data = window.eval("(" + data + ")");
		}
		if (data.length > 0) {
			$("#contenView").html(justep.createReceiveMailListView(data));
		} else {
			$("#contenView").html(
					"<div style='color:red;width:100%;padding:10px;text-align:center;'>未找到与' "
							+ name + " '相关的信息!</div>");
		}
	}
	justep.dialog.close();
}