$(document).ready(
		function() {
			var param = new tlv8.RequestParam();
			param.set("type", "month");
			var re = tlv8.XMLHttpRequest("getSelfWorkPlanAction?tmp"
					+ new Date().getMilliseconds(), param, "post");
			if (re.data.flag == "true") {
				var data = re.data.data;
				if (re.count) {
					$("#title_val").text("(" + re.count + "条)");
				}
				if (re.count > 0) {
					$("#contenView").html(createList(data));
					$("li[rel='list']").click(function() {
						var m = $(this).attr("id");
						openDetail(m);
					});
				} else {
					$("#contenView").html(
							"<div style='color:red;width:100%;padding:10px;text-align:center;'>未查询到您创建过的月工作计划!</div>");
				}
			} else {
				alert("获取月计划信息失败");
			}
		});

function newData() {
	alert("手机版新增功能暂未开放.");
}

function doSearch(name) {
	if (!name || name == "") {
		name = "";
	}
	filter = "T.FTITLE like '%" + name + "%'  or T.FCREATENAME like '%" + name
			+ "%'";
	$.mobile.showPageLoadingMsg();

	var param = new tlv8.RequestParam();
	param.set("filter", filter);
	param.set("type", "week");
	var result = tlv8.XMLHttpRequest("getWeekWorkPlanAction", param,
			"post");
	if (result.data.flag == "true") {
		if (result.count) {
			$("#title_val").text("(" + result.count + "条)");
		}
		var data = result.data.data;
		if (result.count > 0) {
			$("#contenView").html(createList(data));
			$("li[rel='list']").click(function() {
				var m = $(this).attr("id");
				openDetail(m);
			});
		} else {
			$("#contenView").html(
					"<font color='red'>未找到与' " + name + " '相关的周计划!</font>");
		}
		$.mobile.hidePageLoadingMsg();
	}
	justep.dialog.close();
}

function createList(data) {
	if (typeof data == "string") {
		data = window.eval("(" + data + ")");
	}
	var html = '<ul data-role="listview" class="ui-listview mui-table-view">';
	for (var i = 0; i < data.length; i++) {
		var title = data[i].TIME + " 月工作计划";
		html += '<li class="mui-table-view-cell" id="' + data[i].ID
				+ '" rel="list" >';
		html += '<div class="mui-table">';
		html += '<div class="mui-table-cell mui-col-xs-12">';
		html += '<h5 class="mui-ellipsis">';
		html += '<a style="text-decoration: none;">标题：' + title + '</a></h5>';
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
	var url = "MonthWorkPlan.html?rowid=" + id;
	window.open(url, "_self");
}
var limit = 10, mr = 1, offerset, filter = "";
function loadMore() {
	offerset = 10 * mr;
	mr++;
	limit = 10 * mr;
	$.mobile.showPageLoadingMsg();
	var param = new tlv8.RequestParam();
	param.set("type", "month");
	param.set("offerset", offerset);
	param.set("limit", limit);
	if (filter != "" && filter != null) {
		param.set("filter", filter);
	}
	var re = tlv8.XMLHttpRequest("getSelfWorkPlanAction", param);
	if (re.data.flag == "true") {
		var data = re.data.data;
		$(createList(data)).insertBefore($("#moreBtn"));
		$.mobile.hidePageLoadingMsg();
	} else {
		$.mobile.hidePageLoadingMsg();
	}
}
