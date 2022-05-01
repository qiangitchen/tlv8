var srcPath;
var dbkey;
var table;
var relation;
var confirmXmlName;
function getUrlParam(param) {
	srcPath = param.srcPath;
	dbkey = param.dbkey;
	table = param.table;
	relation = param.relation;
	confirmXmlName = param.confirmXmlName;
	setTimeout(initIMP, 100);
}

// 页面初始化
function initIMP() {// 接入参数
	try {
		var personID = tlv8.Context.getCurrentPersonID();
		document.getElementById("personID").value = personID;
	} catch (e) {
	}
	document.getElementById("srcPath").value = srcPath;
	document.getElementById("dbkey").value = dbkey;
	document.getElementById("table").value = table;
	document.getElementById("relation").value = relation;
	document.getElementById("confirmXmlName").value = confirmXmlName;
}

// 使用默认设置
function checknormalset(obj) {
	var startLine = document.getElementById("startLine");
	var endLine = document.getElementById("endLine");
	if (obj.checked) {
//		startLine.disabled = true;
//		endLine.disabled = true;
		document.getElementById("useNormal").value = "true";
	} else {
//		startLine.disabled = false;
//		endLine.disabled = false;
		document.getElementById("useNormal").value = "false";
	}
}

function impinit() {
	document.getElementById("impstatesetting").style.display = "";
	document.getElementById("impMainview").style.display = "none";
}

function cancell() {
	tlv8.portal.dailog.dailogCancel();// 关闭对话框
}