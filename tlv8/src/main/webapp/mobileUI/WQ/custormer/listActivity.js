$(document).bind("pagebeforecreate", function(event, data) {
	initPage();
});

function initPage() {
	$.ajax({
		type : "post",
		async : false,
		url : "getVisitedInfo",
		data : "",
		success : function(result, textStatus) {
			if (result.data.flag == "true") {
				var data = result.data.data;
				if (typeof data == "string") {
					data = window.eval("(" + data + ")");
				}
				$("#clockInCount").text(data.length);
				var html = "";
				html+="<ul data-role=\"listview\" id=\"list_view\">"
				for (var i in data) {
					html += "<li id='" + data[i].FID
							+ "' onclick='viewDeatail(this)'>";
					html += " <h3>" + data[i].FCUSTOMER
							+ "<span style='float:right;'>"
							+ data[i].FCREATEDATE.substring(0, 11)
							+ "</span></h3>";
					html += "<p><strong>" + data[i].FVISITRESOURCE
							+ "</strong></p>";
					html += "<p>" + data[i].FRESUINFO + "</p>";
					html += "</li>";
				}
				html+="</ul>"
				$("#contenView").html(html);
			} else {
			}
		},
		error : function() {
		}
	});
}

var filter;
var limit = 2, mr = 1, offerset;
function loadMord(){
//	$("#contenView").remove();
	offerset = 2 * mr;
	mr++;
	limit = 2 * mr;
	var html = "";
	$.mobile.showPageLoadingMsg();
	$.ajax({
		type : "post",
		async : false,
		url : "getVisitedInfo",
		data : "offerset=" + offerset + "&limit=" + limit,
		success : function(result, textStatus) {
			if (result.data.flag == "true") {
				var data = result.data.data;
				if (typeof data == "string") {
					data = window.eval("(" + data + ")");
				}
				$("#clockInCount").text(data.length);
//				alert(justep.creatListMutiView(data, true));
//				$("#contenView").html(justep.creatListMutiView(data, true));
//				html+="<ul data-role=\"listview\" id=\"list_view\">"
				for (var i in data) {
					html += "<li id='" + data[i].FID
							+ "' onclick='viewDeatail(this)'>";
					html += " <h3>" + data[i].FCUSTOMER
							+ "<span style='float:right;'>"
							+ data[i].FCREATEDATE.substring(0, 11)
							+ "</span></h3>";
					html += "<p><strong>" + data[i].FVISITRESOURCE
							+ "</strong></p>";
					html += "<p>" + data[i].FRESUINFO + "</p>";
					html += "</li>";
				}
			} else {
			}
		},
		error : function() {
		}
	});
	$("#contenView").append(html);
	$.mobile.hidePageLoadingMsg();
	
}

function viewDeatail(obj) {
	var rowid = $(obj).attr("id");
	var url = "mainActivity.html?rowid=" + rowid + "&option=view";
	window.open(url, "_self");
}


function onExcutorSelect(obj) {
	var checked = obj.checked;
	if (checked) {
		exePersons.put(obj.id, $(obj).attr("label"));
		exwcuteActivity.put(aFactivity, exePersons);
	} else {
		exePersons.remove(obj.id);
	}
}

var ListDataMap = new Map();
justep.creatListMutiView = function(data, mrbtn) {
	
	if (typeof data == "string") {
		data = window.eval("(" + data + ")");
	}
	var html = '<ul data-role="listview" class="ui-listview">';
	for ( var i = 0; i < data.length; i++) {
		alert(3);
		if (data[i].FID || data[i].FID != "") {
			html += '<li class="ui-li ui-li-static ui-btn-up-c ui-first-child" id="'
					+ data[i].FID
					+ '" rel="list" type="'
					+ data[i].FCUSTOMER
					+ '" DOCTYPE="'
					+ data[i].FCUSTOMER
					+ '" NAME="'
					+ data[i].FCUSTOMER + '" TITLE="' + data[i].FCUSTOMER + '">';
			html += '<h3 class="ui-li-heading">';
			html += '<a class="ui-link" style="text-decoration: none;">' + data[i].FCUSTOMER
					.toString().trim() + '</a></h3>';
			html += '<p class="ui-li-desc" style="color:#666;"><strong style="font-size:14px;">' + data[i].FCUSTOMER + '</strong>';
			html += '<strong style="float:right;">' + data[i].FCUSTOMER + '</strong></p>';
			html += '<p class="ui-li-desc"></p>';
			html += '</li>';
			ListDataMap.put(data[i].FID, data[i]);
		}
	}
	alert(3);
	if (mrbtn && data.length >= 10) {
		html += '<li class=" ui-btn-up-c ui-first-child" style="align:center;" id="moreBtn" onclick="loadMore()">';
		html += '<p><a class="ui-link" style="margin-left:40%;text-decoration: none;" href="#">加载更多...</a></p></li>';
	}
	if(data.length <10){
		$("#moreBtn").hide();
	}
	html += '</ul>';
	return html;
};