var fileID;
$(document).bind("pagebeforecreate", function(event, data) {
	fileID = getParamValueFromUrl("fileID");
	loadDeatail();
	name = decodeURI(decodeURI(getParamValueFromUrl("name")));
	$("#readtitle").text(name);
});

function loadDeatail() {
	var Url = getdocServer() + ":85/doc/FileGet" + "?fileID=" + fileID;
	try {
		justepYnApp.showToast("正在转换文件，请稍候...");
	} catch (e) {
	}
	document.getElementById("docLook").src = Url;
}

function getdocServer() {
	var dHost = window.location.host;
	if (dHost.indexOf(":") > 0) {
		dHost = dHost.split(":")[0];
	}
//	if (dHost.indexOf("59.216") > -1) {
//		dHost = "59.216.1.3";
//	} else {
//		dHost = "10.0.0.12";
//	}
	var Url = window.location.protocol + "//" + dHost;
	return Url;
}

function browserDoc() {
	var url = getdocServer() + ":8080/DocServer/repository/file/view/" + fileID
			+ "/last/content";
	try {
		justepYnApp.browseDoc(url);
	} catch (e) {
		window.open(url);
	}
}
