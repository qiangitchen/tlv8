jQuery(document).ready(function() {
	justep.checkLogin();
	if (!justep.UserInfo.inited) {
		justep.UserInfo.init();
	}
//	setInterval(positionReport, 1000 * 60 * 5);// 每5分钟上报一次位置信息
});

// 字符编码
function encodeParam(str) {
	return encodeURIComponent(encodeURIComponent(str));
}


/**
 * 扫描条码
 */
function scanBarCode() {
	try {
		var code = justepYnApp.scanBarCode("scanBarCodeCalback");
	} catch (e) {
	}
}
// 扫描条码{回调}
function scanBarCodeCalback(result) {
	alert(result);
//	window.open("/tlv8/mobileUI/WQ/scan/mainActivity_detail.html?barcode="+result);
}
//现场调查记录


// 拍照
function getCamera() {
	try {
		justepYnApp.getCamera('', justep.UserInfo.personid, "Cameracalback");// 业务ID，人员ID，回调函数
	} catch (e) {
		alert("调用相机出错!");
	}
}

function Cameracalback(result) {
	alert(result);
}
