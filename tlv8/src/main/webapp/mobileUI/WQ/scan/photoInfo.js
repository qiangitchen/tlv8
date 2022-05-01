var rowid;
$(document).bind("pagebeforecreate", function(event, data) {
	initPage();
});

function initPage() {
		var sql = "select FFILEID,FFILENAME,FSIZE,FTEXT "
				+ " from WQ_BASE_IMGFILEINFO where FBILLID  is null";
		tlv8.sqlQueryActionforJson("wq", sql, function(result) {
			var data = result.data;
			if (typeof data == "string") {
				data = window.eval("(" + data + ")");
			}
			var html = "";
			for (var i = 0; i < data.length; i++) {
				
				html += "<li>";
				html += "<img src='/DocServer/repository/file/view/"
						+ data[i].FFILEID + "/last/content'  onclick='LookPhoto("
						+ "\"/repository/file/view/" + data[i].FFILEID
						+ "/last/content\",\"" + data[i].FFILENAME + "\")'/>";
				html += "<h3><a href='javascript:LookPhoto("
						+ "\"/repository/file/view/" + data[i].FFILEID
						+ "/last/content\",\"" + data[i].FFILENAME + "\")'>"
						+ data[i].FFILENAME + "</a></h3>";
				html += "<p>" +data[i].FTEXT + "</p>";
				html += "</li>";
			}
			$("#list_view").html(html);
		}, false);
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

function Cameracalbackqqq(result) {
}

// 拍照
function addphotograph() {
	try {
		justepYnApp.getCamera(rowid, justep.Context.getCurrentPersonID(),
				"Cameracalbackqqq");// 业务ID，人员ID，回调函数
	} catch (e) {
		alert("调用相机出错!");
	}
}

function ensure() {
//	var url = "mainActivity.html?rowid=" + rowid;
//	window.open(url, '_self');
}