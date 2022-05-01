// 需要从主页面传递的参数
// 流程ID、任务ID,业务单据ID
var flowID, taskID, sData1, opviewID;
// 流程按钮配置

var sn = "TTr", sign = "";
var mainData = new tlv8.Data();
function pageInit() {
	mainData.setDbkey("oa");
	mainData.setTable("OA_FLOWCONCLUSION");
	mainData.setFormId("maindata_form");
	dataInit();

	flowID = tlv8.RequestURLParam.getParam("flowID");
	taskID = tlv8.RequestURLParam.getParam("taskID");
	sData1 = tlv8.RequestURLParam.getParam("sData1");
	opviewID = tlv8.RequestURLParam.getParam("opviewID");

	Send();
	loadOption();
	
	$("#opinionData").focus();
}

/**
 * 加载常用意见 2021-06-16 （陈乾-去除）
 */
function dataInit() {
	var sql = "select t.FCONCLUSIONNAME from OA_FLOWCONCLUSION t where t.fcreatorid = '"
			+ tlv8.Context.getCurrentPersonID() + "' order by t.forder asc";
	var re = tlv8.sqlQueryActionforJson("oa", sql, null, false);
	createSelectList(re.data);
}

function loadOption() {
	var sql = "select t.FAGREETEXT from OA_FLOWRECORD t where FTASKID='"
			+ taskID + "' and FBILLID='" + sData1 + "' and t.FCREATEPERID = '"
			+ tlv8.Context.getCurrentPersonID() + "'";
	var re = tlv8.sqlQueryAction("oa", sql, null, false);
	if (re.getCount() > 0) {
		$("#opinionData").val(re.getValueByName("FAGREETEXT"));
	}
}

function createSelectList(data) {
	var listHtml = "<table style='width:100%;'>";
	for (var i = 0; i < data.length; i++) {
		listHtml += "<tr><td style='font-size:13px;cursor:pointer;border-bottom: 1px solid #ddd;padding-bottom:5px;' "
				+ "onmouseover='mouseover(this)' onmouseout='mouseout(this)' onclick='selectList(this)'>"
				+ data[i].FCONCLUSIONNAME + "</td><tr>";
	}
	listHtml += "</table>";
	$("#selectListView").html(listHtml);
}

function mouseover(obj) {
	obj.style.background = "blue";
	obj.style.color = "#ffffff";
}

function mouseout(obj) {
	obj.style.background = "#ffffff";
	obj.style.color = "#000000";
}

function selectList(obj) {
	$("#opinionData").val(obj.innerText);
}

function clearData() {
	$("#opinionData").val("");
}

function saveDatatoMyOp() {
	var opt = $("#opinionData").val();
	if (!opt || opt == "") {
		alert("意见内容不能为空！");
		return;
	}
	$("#FCONCLUSIONNAME").val(opt);
	$("#FCREATORID").val(tlv8.Context.getCurrentPersonID());
	$("#FCREATOR").val(tlv8.Context.getCurrentPersonName());
	var rowid = mainData.saveData();
	J$("maindata_form").rowid = rowid;
	$("#maindata_form").attr("rowid", rowid);
	dataInit();
}

/*
 * 保存审批意见
 */
function saveAuditOpinion() {
	var auditOp = $("#opinionData").val();
	if (!auditOp || auditOp == "") {
		alert("审核意见不能为空!");
		return false;
	}
	var param = new tlv8.RequestParam();
	param.set("dbkey", "oa");
	param.set("audittable", "OA_FLOWRECORD");
	param.set("billidRe", "FBILLID");
	param.set("agreettextRe", "FAGREETEXT");
	param.set("opinion", $("#opinionData").val());
	param.set("flowid", flowID);
	param.set("taskID", taskID);
	param.set("sdata1", sData1);
	param.set("opviewID", opviewID);
	param.set("sign", sign);
	tlv8.XMLHttpRequest("SaveAuditOpinionSignAction", param, "post", false,
			function(r) {
				if (r.data.flag == "false") {
					alert(r.data.message);
					return false;
				}
			});
	return true;
}

function dailogEngin() {
	return saveAuditOpinion();
}

var socket;
function Connect() {
	try {
		socket = new WebSocket("ws://127.0.0.1:17121/oes");
	} catch (e) {
		// alert('error');
		return;
	}
	socket.onopen = sOpen;
	socket.onerror = sError;
	socket.onmessage = sMessage;
	socket.onclose = sClose;
}
function sOpen() {
	// alert('connect success!');
	var requestJson = {
		"function" : "getPersonalSeal",
		"inParams" : {
			"certSN" : sn
		}
	};
	socket.send(JSON.stringify(requestJson));
}
function sError(e) {
	 console.log("error " + e);
}
function sMessage(msg) {
	var t = typeof msg;
	if (t == "object") {
		// alert('server says:' + JSON.stringify(msg));
		try {
			var jo = JSON.parse(msg.data);
			sign = jo.outParams.sealImageBase64;
			// alert('server says:' + jo.outParams.sealImageBase64);
			// var img = document.getElementById('sealimg');
			// img.src = "data:image/png;base64," +
			// jo.outParams.sealImageBase64;
		} catch (e) {
			console.log('获取签名失败:' + msg.data);
		}
	} else {
		console.log('server says:' + msg);
	}
}
function sClose(e) {
	console.log("connect closed:" + e.code);
}
function Send() {
	// socket.send(document.getElementById("msg").value);
	// var param = new tlv8.RequestParam();
	// sn = tlv8.XMLHttpRequest("loadCASN", param, "post", false);
	// if (sn && sn != "") {
	// Connect();
	// }
	Connect();
}
function Close() {
	socket.close();
}

function managemyop(){
	tlv8.portal.openWindow("审批意见设置", "/OA/flowset/myOpinion/mainActivity.html");
}