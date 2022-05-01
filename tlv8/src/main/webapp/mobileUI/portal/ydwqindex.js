$(document).ready(function() {
	tlv8.checkLogin();
	if (!tlv8.UserInfo.inited) {
		tlv8.UserInfo.init();
	}
	setInterval(positionReport, 1000 * 60 * 5);// 每5分钟上报一次位置信息
});
// 位置上报
function positionReport() {
	try {
		var location = tlv8YnApp.getLocatin();
	} catch (e) {
	}
	$.ajax({
		type : "post",
		async : true,
		url : "positionReportAction",
		data : "userID=" + encodeParam(tlv8.UserInfo.personid) + "&position="
				+ encodeParam(location),
		success : function(result, textStatus) {
			if (result.data.flag == "false") {
				alert(result.data.message);
			}
		},
		error : function() {
		}
	});
}

// 字符编码
function encodeParam(str) {
	return encodeURIComponent(encodeURIComponent(str));
}

// 选项
function setPermition() {
	window.open("system/templet/ydwqsetting.html");
}
/**
 * 扫描条码
 */
function scanBarCode() {
	try {
		var code = tlv8YnApp.scanBarCode("scanBarCodeCalback");
	} catch (e) {
	}
}
// 扫描条码{回调}
function scanBarCodeCalback(result) {
//	alert(result);
}
//现场调查记录


// 拍照
function getCamera() {
	try {
		tlv8YnApp.getCamera('', tlv8.UserInfo.personid, "Cameracalback");// 业务ID，人员ID，回调函数
	} catch (e) {
		alert("调用相机出错!");
	}
}

function Cameracalback(result) {
	alert(result);
}
