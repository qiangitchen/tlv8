$(document).ready(function() {
	$.ajax({
		type : "post",
		async : true,
		url : "getNoticeInfoAction",
		success : function(result, textStatus) {
			if (result.count) {
				$("#list_count").text(result.count);
			}
			var data = result.data.data;
			if (result.count > 0) {
				$("#contenView").html(createList(data));
				$("li[rel='list']").click(function() {
					var m = $(this).attr("id");
					openDetail(m);
				});
			} else {
				$("#contenView").html("<font color='red'>您当前没有新的通知!</font>");
			}
		},
		error : function() {
			// 请求出错处理
		}
	});
});

function doSearch(name) {
	if (!name || name == "") {
		name = "";
	}
	filter = "T.FTITLE like '%" + name + "%'  or T.FCREATENAME like '%" + name
			+ "%'";
	$.mobile.showPageLoadingMsg();
	$.ajax({
		type : "post",
		async : true,
		url : "getNoticeInfoAction",
		data : "filter=" + enCode(filter),
		success : function(result, textStatus) {
			if (result.count) {
				$("#list_count").text(result.count);
			}
			var data = result.data.data;
			if (result.count > 0) {
				$("#contenView").html(createList(data));
				$("li[rel='list']").click(function() {
					var m = $(this).attr("id");
					openDetail(m);
				});
			} else {
				$("#contenView").html("<font color='red'>您当前没有新的通知!</font>");
			}
			$.mobile.hidePageLoadingMsg();
		},
		error : function() {
		}
	});
	// justep.dialog.close();
}

function createList(data) {
	if (typeof data == "string") {
		data = window.eval("(" + data + ")");
	}
	var html = '<ul class="mui-table-view">';
	for (var i = 0; i < data.length; i++) {
		html += '<li class="mui-table-view-cell" id="' + data[i].ID
				+ '" rel="list" >';
		html += '<div class="mui-table">';
		html += '<div class="mui-table-cell mui-col-xs-12">';
		html += '<h5 class="mui-ellipsis">';
		html += '<a style="text-decoration: none;">标题：' + data[i].TITLE
				+ '</a></h5>';
		html += '<div class="mui-table-cell mui-col-xs-6">';
		html += '<p style="color:#666;"><strong style="font-size:14px;">发布人：'
				+ data[i].PSNNAME + '</strong>';
		html += '</p></div>';
		html += '<div class="mui-table-cell mui-col-xs-6">';
		html += '<p class="mui-h6 mui-ellipsis">发布时间：<strong>' + data[i].TIME
				+ '</strong></p>';
		html += '</div>';
		html += '</div>';
		html += '</div></li>';
	}
	if (data.length >= 10) {
		html += '<li class="mui-table-view-cell" style="align:center;" id="moreBtn" onclick="loadMore()">';
		html += '<p><a class="ui-link" style="margin-left:40%;text-decoration: none;" href="#">加载更多...</a></p></li>';
	}
	if (data.length < 10) {
		$("#moreBtn").hide();
	}
	html += '</ul>';
	return html;
}

function openDetail(id) {
	var url = cpath+"/mobileUI/oa/notice/publicnotice/show_view.html?rowid="
			+ id;
	window.open(url, "_self");
}
var limit = 10, mr = 1, offerset, filter = "";
function loadMore() {
	offerset = 10 * mr;
	mr++;
	limit = 10 * mr;
	$.mobile.showPageLoadingMsg();
	var param = new tlv8.RequestParam();
	param.set("type", "week");
	param.set("offerset", offerset);
	param.set("limit", limit);
	if (filter != "" && filter != null) {
		param.set("filter", filter);
	}
	var re = tlv8.XMLHttpRequest("getNoticeInfoAction", param);
	if (re.data.flag == "true") {
		var data = re.data.data;
		$(createList(data)).insertBefore($("#moreBtn"));
		$.mobile.hidePageLoadingMsg();
	} else {
		$.mobile.hidePageLoadingMsg();
	}
}
