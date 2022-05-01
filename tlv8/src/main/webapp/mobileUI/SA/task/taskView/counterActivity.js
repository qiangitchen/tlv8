$(document).ready(function() {
	loadData();
});

function loadData() {
	$.ajax({
		type : "post",
		async : true,
		url : "getcounteAction",
		success : function(result, textStatus) {
			if (result.count) {
				$("#title_val").text("(" + result.count + "条)");
			}
			var data = result.data.data;
			if (result.count > 0) {
				$("#contenView").html(justep.creatListMutiView(data, true));
				$("li[rel='list']").click(
						function() {
							openReadDetail($(this).attr("id"), $(this).attr(
									"type"), $(this).attr("DOCTYPE"), $(this)
									.attr("NAME"));
						});
			} else {
				$("#contenView").html(
						"<div style='color:red;width:100%;padding:10px;text-align:center;'>您没有已阅的文件!</div>");
			}
		},
		error : function() {
		}
	});
}

// 查看详细
function openReadDetail(rowid, type, docType, name) {
	var url = "";
	if (docType == "OA_DC_DocRecvGov") {
		var url = cpath+"/mobileUI/OA/szf_doc/process/readDocQuery/mainActivity.html"
				+ "?process=/OA/szf_doc/process/readDocQuery/readDocQueryProcess&activity=mainActivity"
				+ "&rowid="
				+ rowid
				+ "&doctype="
				+ type
				+ "&isRead=yes&name="
				+ enCode(name);
	} else {
		var url = cpath+"/mobileUI/OA/doc/process/readDocQuery/readDocDetail.html"
				+ "?process=/OA/doc/process/readDocQuery/readDocQueryProcess&activity=readDocQueryActivity"
				+ "&rowid="
				+ rowid
				+ "&doctype="
				+ type
				+ "&isRead=yes&name="
				+ enCode(name);
	}
	window.open(url, "_self");
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
		url : "getcounteAction",
		data : "offerset=" + offerset + "&limit=" + limit,
		success : function(result, textStatus) {
			if (result.count) {
				$("#title_val").text("(" + result.count + "条)");
			}
			var data = result.data.data;
			$(justep.creatListMutiView(data, false))
					.insertBefore($("#moreBtn"));
			$("li[rel='list']").click(
					function() {
						openReadDetail($(this).attr("id"),
								$(this).attr("type"), $(this).attr("DOCTYPE"),
								$(this).attr("NAME"));
					});
			$.mobile.hidePageLoadingMsg();
		},
		error : function() {
			// 请求出错处理
		}
	});
}

function doSearch(name) {
	if (!name || name == "") {
		name = "";
	}
	filter = "NAME like '%" + name + "%'";
	$.mobile.showPageLoadingMsg();
	$.ajax({
		type : "post",
		async : true,
		url : "getcounteAction",
		data : "filter=" + enCode(filter),
		success : function(result, textStatus) {
			if (result.count) {
				$("#title_val").text("(" + result.count + "条)");
			}
			var data = result.data.data;
			if (result.count > 0) {
				$("#contenView").html(justep.creatListMutiView(data, true));
				$("li[rel='list']").click(
						function() {
							openReadDetail($(this).attr("id"), $(this).attr(
									"type"), $(this).attr("DOCTYPE"), $(this)
									.attr("NAME"));
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
