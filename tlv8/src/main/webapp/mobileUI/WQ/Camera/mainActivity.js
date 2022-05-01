var rowid;
$(document).bind("pagebeforecreate", function(event, data) {
	initPage();
});

function initPage() {
	rowid = getParamValueFromUrl("rowid");
	var option = getParamValueFromUrl("option");
	if (option && option == "view") {
		$("#readtitle").text("照片信息");
		$("#enSureItem").hide();
		$("#addphoto").hide();
	}
	if (rowid && rowid != "") {
		var sql = "select FFILEID,FFILENAME,FSIZE,FTEXT "
				+ " from WQ_BASE_IMGFILEINFO where FBILLID = '" + rowid + "'";
		tlv8.sqlQueryActionforJson("wq", sql, function(result) {
			var data = result.data;
			if (typeof data == "string") {
				data = window.eval("(" + data + ")");
			}
			var html = "";
			for (var i = 0; i < data.length; i++) {
				html += "<li>";
				html += "<img src='/DocServer/repository/file/view/"
						+ data[i].FFILEID + "/last/content'/>";
				html += "<h3><a href='javascript:LookPhoto("
						+ "\"/repository/file/view/" + data[i].FFILEID
						+ "/last/content\",\"" + data[i].FFILENAME + "\")'>"
						+ data[i].FFILENAME + "</a></h3>";
				html += "<p>" + data[i].FSIZE || data[i].FTEXT + "</p>";
				html += "</li>";
			}
			$("#list_view").html(html);
		}, false);
	}
}

// 查看照片
function LookPhoto(url, name) {
	try {
		justepYnApp.LookPhoto(getdocServer() + url, name);
	} catch (e) {
		window.open(url, "_blank");
	}
}

function getdocServer() {
	var dHost = window.location.host;
	var Url = window.location.protocol + "//" + dHost + "/DocServer";
	return Url;
}

function cameracalback(result) {
	//
	//ensure();
	
	alert(result);
}

// 拍照
function addphotograph() {
	try {
		justepYnApp.getCamera(rowid, justep.Context.getCurrentPersonID(),
				"cameracalback");// 业务ID，人员ID，回调函数
	} catch (e) {
		alert("调用相机出错!");
	}
}

function ensure() {
	var url = "../custormer/mainActivity.html?rowid=" + rowid;
	window.open(url, '_self');
}