$(document).ready(
		function() {
			var param = new tlv8.RequestParam();
			param.set("type", "month");
			var re = tlv8.XMLHttpRequest("getWorkPlanAction?tmp"
					+ new Date().getMilliseconds(), param, "post");
			if (re.data.flag == "true") {
				var data = re.data.data;
				if (re.count) {
					$("#title_val").text("(" + re.count + "条)");
				}
				if (re.count > 0) {
					$("#contenView").html(createMonthMainPlanList(data, true));
					$("li[rel='list']").click(function() {
						var m = $(this).attr("id");
						openDetail(m);
					});
				} else {
					$("#contenView").html(
							"<div style='color:red;width:100%;padding:10px;text-align:center;'>您当前没有新的待查看月工作计划!</div>");
				}
			} else {
				alert("获取计划信息失败");
			}
		});

function doSearch(name) {
	if (!name || name == "") {
		name = "";
	}
	filter = "T.FTITLE like '%" + name + "%'  or T.FCREATENAME like '%" + name
			+ "%'";
	$.mobile.showPageLoadingMsg();
	mr = 1;// 清空
	offerset = 0;
	var param = new tlv8.RequestParam();
	param.set("type", "month");
	param.set("filter", filter);
	var result = tlv8.XMLHttpRequest("getWorkPlanAction", param, "post");
	if (result.data.flag == "true") {
		if (result.count) {
			$("#title_val").text(" 查找到: ( " + result.count + " 条)");
		}
		var data = result.data.data;
		if (result.count > 0) {
			$("#contenView").html(createMonthMainPlanList(data, true));
			$("li[rel='list']").click(function() {
				var m = $(this).attr("id");
				openDetail(m);
			});
		} else {
			$("#contenView").html(
					"<font color='red'>未找到与' " + name + " '相关的月计划!</font>");
		}
		$.mobile.hidePageLoadingMsg();
	}
	justep.dialog.close();
}

function openDetail(id) {
	var url = "MonthWorkPlanView.html?rowid=" + id;
	window.open(url, "_self");
}
var limit = 10, mr = 1, offerset = 0, filter = "";
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
	var re = tlv8.XMLHttpRequest("getWorkPlanAction", param);
	if (re.data.flag == "true") {
		var data = re.data.data;
		$(createMonthMainPlanList(data, false)).insertBefore($("#moreBtn"));
		$.mobile.hidePageLoadingMsg();
	} else {
		$.mobile.hidePageLoadingMsg();
	}
}
