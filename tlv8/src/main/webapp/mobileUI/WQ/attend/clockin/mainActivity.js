//查看地图
function loockMap() {
	try {
		justepYnApp.getMapLocatin();
	} catch (e) {
		window.open('../../map/maplayout.html', '_self');
	}
}

$(document).ready(function() {
	var deptID = justep.Context.getCurrentDeptID();
	var deptName = justep.Context.getCurrentDeptName();
	if (!deptID || deptID == "") {
		deptID = justep.Context.getCurrentOrgID();
		deptName = justep.Context.getCurrentOrgName();
	}
	$("#signDeptID").val(deptID);
	$("#signDeptname").val(deptName);
	$("#signUserID").val(justep.Context.getCurrentPersonID());
	$("#signUsername").val(justep.Context.getCurrentPersonName());
	try {
		var location = justepYnApp.getLocatin();
		var locobj;
		if (typeof location == "string") {
			locobj = window.eval("(" + location + ")");
		}
		$("#mapPoint").val(location);
		$("#signAddress").val(locobj.addr);
	} catch (e) {
	}
});

var sigType = "上班";
function setType(obj) {
	if ($(obj).attr("id") == "fOnofficework") {
		sigType = "上班";
	}
	if ($(obj).attr("id") == "fEndWork") {
		sigType = "下班";
	}
	if ($(obj).attr("id") == "fGoout") {
		sigType = "外出";
	}
}

// 字符编码
function encodeParam(str) {
	return encodeURIComponent(encodeURIComponent(str));
}

// 获取页面值
function getSubData() {
	var paramList = [];
	paramList.push("faddress=" + encodeParam($("#signAddress").val()));
	paramList.push("fmappoint=" + encodeParam($("#mapPoint").val()));
	paramList.push("fpersonid=" + encodeParam($("#signUserID").val()));
	paramList.push("fpersonname=" + encodeParam($("#signUsername").val()));
	paramList.push("fdeptid=" + encodeParam($("#signDeptID").val()));
	paramList.push("fdeptname=" + encodeParam($("#signDeptname").val()));
	paramList.push("ftype=" + encodeParam(sigType));
	paramList.push("fcustomerid=" + encodeParam($("#CUSTOMERID").val()));
	paramList.push("fcustomer=" + encodeParam($("#CUSTOMER").val()));
	paramList.push("fcustomeraddr=" + encodeParam($("#CUSTOMERADDR").val()));
	paramList.push("fcustomerpoint=" + encodeParam($("#FCUSTOMERPOINT").val()));
	return paramList.join("&");
}

// 打卡提交
function signSbmit() {
	var addr = $("#signAddress").val();
	if (addr == "" || addr == null) {
		alert("获取当前用户地址错误,打卡失败,请关闭重试.");
		return;
	}
	$.ajax({
		type : "post",
		async : true,
		url : "clockinAction",
		data : getSubData(),
		success : function(result, textStatus) {
			if (result.data.flag == "false") {
				alert(result.data.message);
			} else {
				alert("打卡成功!");
				history.go(-1);
			}
		},
		error : function() {
		}
	});
}
