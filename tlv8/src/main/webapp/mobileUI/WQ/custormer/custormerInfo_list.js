$(document).bind("pagebeforecreate", function(event, data) {
	initPage();
});
var sqlwhere = "";
function initPage() {
	var sql = "select FID,FCUSTOMERNAME,FCREATEDATE,FCUSTOMERADDR from WQ_CUSTOMR_BASEINFO where FCREATORID = '"
			+ justep.Context.getCurrentPersonID()
			+ "' and rownum<=10"
			+ " order by FCREATEDATE desc";
	var html = "";
	tlv8.sqlQueryActionforJson("wq", sql, function(result) {
		var data = result.data;
		if (typeof data == "string") {
			data = window.eval("(" + data + ")");
		}
		if (data.length > 0) {
			$("#contenView").html(justep.creatCustomList(data, true));
		} else {
			$("#contenView").html(
					"<font color='red' align='center'>没有找到可用的客户信息!</font>");
		}
	}, false);
}

function viewDeatail(obj) {
	var rowid = $(obj).attr("id");
	var url = "custormerInfo.html?sData1=" + rowid + "&option=view";
	window.open(url, "_self");
}
function selectDetail() {
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
	$(div_bg).attr("id", "dialog_bg");
	$(div_bg).attr("style", bg_style);
	var div_content = document.createElement("div");// 内容层
	var conten_style = "width:130px;z-index:1001;position:absolute;top:45px;;right:10px;border:0;";
	$(div_content).attr("id", "dialog_content");
	$(div_content).attr("class", "ui-page-active ui-corner-all");
	$(div_content).attr("style", conten_style);
	var html = [];
	html
			.push("<div data-role='content' class='ui-content ui-body-c' style='padding:0 10px;'>");
	html
			.push("<a href='#' onclick='searchData()' class='ui-btn ui-btn-up-b  ui-btn-corner-all'>");
	html.push("<span style='padding:0.5em;display:block'>查询</span></a>");
	html
			.push("<a href='#' onclick='insertCustormer()' class='ui-btn ui-btn-up-b  ui-btn-corner-all'>");
	html.push("<span style='padding:0.5em;display:block'>新增</span></a>");
	html.push("</div>");
	$(div_content).html(html.join(""));
	$(document.body).append(div_bg);
	$(document.body).append(div_content);
	$(div_bg).bind("click", function() {
		$("#dialog_bg").remove();
		$("#dialog_content").remove();
	});

}
// 编辑客户信息
function editCustormer() {
	var url = "custormerInfo.html";
	window.open(url, '_self');
}
// 新增客户信息
function insertCustormer() {
	var url = "custormerInfo.html";
	window.open(url, '_self');
}
// 查询
function searchData() {
	$("#dialog_bg").remove();
	$("#dialog_content").remove();
	var html = "<br/>"
			+ "<input type='text' id='search_text' style='width:180px;height:30px;'/>"
			+ "&nbsp;<a id='seearchItem' class='ui-btn-right ui-btn ui-btn-up-b ui-shadow ui-btn-corner-all'>"
			+ "<sapn class='ui-btn-inner'>"
			+ "<span class='ui-btn-text'>查询<span></span></a>";
	justep.dialog.openDialog("查询", {
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
function doSearch(name) {
	if (!name || name == "") {
		name = "";
	}
	sqlwhere = " and FCUSTOMERNAME like '%" + name + "%'";
	justep.dialog.close();
	var sql = "select FID,FCUSTOMERNAME,FCREATEDATE,FCUSTOMERADDR from WQ_CUSTOMR_BASEINFO where FCREATORID = '"
			+ justep.Context.getCurrentPersonID()
			+ "' "
			+ sqlwhere
			+ " order by FCREATEDATE desc";
	tlv8.sqlQueryActionforJson("wq", sql, function(result) {
		var data = result.data;
		if (typeof data == "string") {
			data = window.eval("(" + data + ")");
		}
		if (data.length > 0) {
			$("#contenView").html(justep.creatCustomList(data, true));
		} else {
			$("#contenView").html(
					"<font color='red' align='center'>没有找到可用的客户信息!</font>");
		}
	}, false);

}
var limit = 10, mr = 1, offerset;
function loadMore() {
	offerset = 10 * mr;
	mr++;
	limit = 10 * mr;
	$.mobile.showPageLoadingMsg();
	var sql = "select * from(select rownum ro,FID,FCUSTOMERNAME,FCREATEDATE,FCUSTOMERADDR from WQ_CUSTOMR_BASEINFO where FCREATORID = '"
			+ justep.Context.getCurrentPersonID()
			+ "' and rownum<="
			+ limit
			+ "" + " order by FCREATEDATE desc ) where ro>" + offerset;
	tlv8.sqlQueryActionforJson("wq", sql, function(result) {
		var data = result.data;
		if (typeof data == "string") {
			data = window.eval("(" + data + ")");
		}
		if (data.length > 0) {
			$("#contenView").html(justep.creatCustomList(data, true));
		} else {
			$("#contenView").html(
					"<font color='red' align='center'>没有找到可用的客户信息!</font>");
		}
	}, false);
	$.mobile.hidePageLoadingMsg();
}
