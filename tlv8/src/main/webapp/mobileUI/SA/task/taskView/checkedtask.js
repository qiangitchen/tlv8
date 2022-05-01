$(document).ready(function() {
	$.ajax({
		type : "post",
		async : true,
		url : "getsubmitTaskAction",
		success : function(result, textStatus) {
			if (result.count) {
				$("#title_val").text("(" + result.count + "条)");
			}
			var data = result.data.data;
			if (result.count > 0) {
				$("#contenView").html(justep.creatListMutiView(data, true));
				$("li[rel='list']").click(function() {
					var m = ListDataMap.get($(this).attr("id"));
					openTask(m);
				});
			} else {
				$("#contenView").html("<font color='red'>您没有已处理的任务!</font>");
			}
		},
		error : function() {
			// 请求出错处理
		}
	});
});

function openTask(d) {
	var sEURL = d.SEURL;
	var taskID = d.ID;
	var pattern = "detail";
	var sName = d.SNAME;
	// var sExecutorFID = d.SEXECUTORFID;
	//var sProcess = d.SFLOWID;
	var flowID = d.SFLOWID;
	var sActivity = d.SACTIVITY;
	var sData1 = d.SDATA1;
	sEURL = sEURL.replace(".w", ".html");
	sEURL = cpath+"/mobileUI"+sEURL;
	sEURL = sEURL + "?flowID=" + flowID + "&taskID=" + taskID + "&sData1="
			+ sData1 + "&activity-pattern=" + pattern + "&name=" + sName
			+ "&activity=" + sActivity;
	window.open(sEURL, "_self");
}

var filter;
var limit = 10, mr = 1, offerset;
function loadMore() {
	offerset = 10 * mr;
	mr++;
	limit = 10 * mr;
	$.mobile.showPageLoadingMsg();
	$.ajax({
		type : "post",
		async : true,
		url : "getsubmitTaskAction",
		data : "offerset=" + offerset + "&limit=" + limit,
		success : function(result, textStatus) {
			if (result.count) {
				$("#title_val").text("(" + result.count + "条)");
			}
			var data = result.data.data;
			$(justep.creatListMutiView(data, false))
					.insertBefore($("#moreBtn"));
			$("li[rel='list']").click(function() {
				var m = ListDataMap.get($(this).attr("id"));
				openTask(m);
			});
			$.mobile.hidePageLoadingMsg();
		},
		error : function() {
			// 请求出错处理
		}
	});
}

function doSearch() {
	var name = $("#search_text").val();
	if (!name || name == "") {
		name = "";
	}
	filter = "T.SNAME like '%" + name + "%'";
	$.mobile.showPageLoadingMsg();
	$.ajax({
		type : "post",
		async : true,
		url : "getsubmitTaskAction",
		data : "filter=" + enCode(filter),
		success : function(result, textStatus) {
			if (result.count) {
				$("#title_val").text("(" + result.count + "条)");
			}
			var data = result.data.data;
			if (result.count > 0) {
				$("#contenView").html(justep.creatListMutiView(data, true));
				$("li[rel='list']").click(function() {
					var m = ListDataMap.get($(this).attr("id"));
					openTask(m);
				});
			} else {
				$("#contenView").html(
						"<font color='red'>没有查到'" + name + "'对应的结果。</font>");
			}
			$.mobile.hidePageLoadingMsg();
		},
		error : function() {
		}
	});
	justep.dialog.close();
}
