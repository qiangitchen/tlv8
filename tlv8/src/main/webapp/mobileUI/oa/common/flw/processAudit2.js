// 需要从主页面传递的参数
// 流程ID、任务ID,业务单据ID
var flowID, taskID, sData1, opviewID;
// 流程按钮配置

$(document).bind("pagebeforecreate", function() {
	// pageInit();
});

var mainData = new tlv8.Data();
function pageInit() {
	mainData.setDbkey("oa");
	mainData.setTable("OA_FLOWCONCLUSION");
	mainData.setFormId("maindata_form");

	dataInit();

	flowID = getParamValueFromUrl("flowID");
	taskID = getParamValueFromUrl("taskID");
	sData1 = getParamValueFromUrl("sData1");
	opviewID = getParamValueFromUrl("opviewID");

	loadOption();
}

function dataInit() {
	var param = new tlv8.RequestParam();
	param.set("fcreatorid", justep.Context.getCurrentPersonID());
	var re = tlv8.XMLHttpRequest("loadFlowconclusion", param, "POST", false);
	createSelectList(re.data);
}

function loadOption() {
	var param = new tlv8.RequestParam();
	param.set("taskID", taskID);
	param.set("sdata1", sData1);
	param.set("fcreatorid", justep.Context.getCurrentPersonID());
	var re = tlv8.XMLHttpRequest("loadFagreetext", param, "POST", false);
	var redata = re.data;
	if (redata.length > 0) {
		$("#opinionData").val(trim(redata[0].FAGREETEXT));
	}
}

function createSelectList(data) {
	var listHtml = "<ul data-role='listview' data-inset='true' "
			+ "class='ui-listview ui-listview-inset ui-corner-all ui-shadow'>";
	var corcss = "";
	for (var i = 0; i < data.length; i++) {
		/*
		 * if (i == 0) { corcss = "ui-first-child"; } if (i == data.length - 1) {
		 * corcss = "ui-last-child"; }
		 */

		listHtml += "<li style='' class='ui-btn ui-btn-icon-right ui-li-has-arrow ui-li "
				+ corcss
				+ " ui-btn-up-c'>"
				+ "<div class='ui-btn-inner ui-li'>"
				+ "<a class='ui-link-inherit' href='javascript:void(0);' onclick='selectList(this)'>"
				+ data[i].FCONCLUSIONNAME
				+ "</a></div>"
				+ "<span class='ui-icon ui-icon-arrow-r ui-icon-shadow'></span></li>";
	}
	listHtml += "</ul>";
	$("#selectListView").html(listHtml);
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
	$("#FCREATORID").val(justep.Context.getCurrentPersonID());
	$("#FCREATOR").val(justep.Context.getCurrentPersonName());
	rowid = mainData.saveData();
	J$("maindata_form").rowid = rowid;
	J$("maindata_form").setAttribute("rowid", rowid);
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
	param.set("opinion", trim($("#opinionData").val()));
	param.set("flowid", flowID);
	param.set("taskID", taskID);
	param.set("sdata1", sData1);
	param.set("opviewID", opviewID);
	tlv8.XMLHttpRequest("SaveAuditOpinionAction", param, "post", false,
			function(r) {
				if (r.data.flag == "false") {
					alert(r.data.message);
					return false;
				}
			});
	return true;
}

function dailogEngin() {
	saveAuditOpinion();
	window.history.back();
	return true;
}