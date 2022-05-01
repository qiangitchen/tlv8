$(document).ready(function() {
	loadData();
});

function loadData() {

	$
			.ajax({
				type : "post",
				async : true,
				url : "getaddressBookSystem",
				success : function(result, textStatus) {
					var data = result.data.data;
					var array = window.eval("(" + data + ")");
					if (array.length > 0) {
						$("#contenView").html(
								justep.creatListAddBookView(data, true));
					} else {
						$("#contenView")
								.html(
										"<font color='red' align='center'>没有找到可用的通讯录信息!</font>");
					}
				},
				error : function() {
				}
			});
}

function searchData() {
	var html = "<br/>"
			+ "<input type='text' id='search_text' style='width:180px;height:30px;'/>"
			+ "&nbsp;<a id='seearchItem' class='ui-btn-right ui-btn ui-btn-up-b ui-shadow ui-btn-corner-all'>"
			+ "<sapn class='ui-btn-inner'>"
			+ "<span class='ui-btn-text'>查询<span></span></a>";
	justep.dialog.openDialog("姓名查询", {
		width : "280px",
		height : "150px",
		text : html
	});
	$("#search_text").keydown(function(event) {
		if (event.keyCode == 13) {
			doSearch($("#search_text").val());
			return false;
		}
	});
	$("#seearchItem").click(function() {
		doSearch($("#search_text").val());
	});
}
var filter;
function doSearch(name) {
	if (!name || name == "") {
		name = "";
	}
	filter = "P.SNAME like '%" + name + "%' or P.SCODE like '" + name + "'";
	$.mobile.showPageLoadingMsg();
	$
			.ajax({
				type : "post",
				async : true,
				url : "getaddressBookSystem",
				data : "filter=" + enCode(filter),
				success : function(result, textStatus) {
					var data = result.data.data;
					var array = window.eval("(" + data + ")");
					if (array.length > 0) {
						$("#contenView").html(
								justep.creatListAddBookView(data, true));
					} else {
						$("#contenView")
								.html(
										"<font color='red' align='center'>没有找到可用的通讯录信息!</font>");
					}
					$.mobile.hidePageLoadingMsg();
				},
				error : function() {
					$.mobile.hidePageLoadingMsg();
				}
			});
	justep.dialog.close();
}

var limit = 10, mr = 1, offerset;
function loadMore() {
	offerset = 10 * mr;
	mr++;
	limit = 10 * mr;
	$.mobile.showPageLoadingMsg();
	$.ajax({
		type : "post",
		async : true,
		url : "getaddressBookSystem",
		data : "offerset=" + offerset + "&limit=" + limit
				+ (filter ? "&filter=" + enCode(filter) : ""),
		success : function(result, textStatus) {
			var data = result.data.data;
			$(justep.creatListAddBookView(data, false)).insertBefore(
					$("#moreBtn"));
			$.mobile.hidePageLoadingMsg();
		},
		error : function() {
			$.mobile.hidePageLoadingMsg();
		}
	});
}
