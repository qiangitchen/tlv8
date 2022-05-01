/*
 * 默认环节单选选 multipleModel = false
 */
var multipleModel = false;
var executeMode, activity, flowID, taskID, sData1, aFactivity;
var exwcuteActivity;

$(document).ready(function() {
	exwcuteActivity = new Map();
});

$(document).bind("pagebeforecreate", function(event, data) {
	executeMode = getParamValueFromUrl("executeMode");
	flowID = getParamValueFromUrl("flowID");
	taskID = getParamValueFromUrl("taskID");
	sData1 = getParamValueFromUrl("sData1");
	activity = getParamValueFromUrl("activity");
	if (executeMode == "advance") {
		$("#title_val").text("任务流转");
		tlv8.task.loadOut(flowID, taskID, sData1, advancecalBack);
	} else if (executeMode == "back") {
		$("#title_val").text("任务回退");
		$("#contenView").hide();
		$("#congirminfo").text("确认回退到上一环节吗？").show();
	} else if (executeMode == "abort") {
		$("#title_val").text("任务终止");
		$("#contenView").hide();
		$("#congirminfo").text("终止流程将会被作废不能再操作，确认终止吗？").show();
	} else if (executeMode == "transfer") {
		$("#title_val").text("任务转发");
		tlv8.task.loadTransfer(taskID, sData1, advancecalBack);
	}
});

function advancecalBack(activityListStr) {
	// alert(activityListStr);
	$("#activitylist").append(tlv8.task.initActivityList(activityListStr));
}

var exeuctorList = new Map();

// 意见框赋值，主要用于快速选择
function setOpinion(value) {
	if (value == "同意") {
		$("#isnotagree").attr("checked", false).checkboxradio("refresh");
	} else {
		$("#isagree").attr("checked", false).checkboxradio("refresh");
	}
	$("#opinions").text(value);
}

// 加载上一次处理人列表
function initLastExecutor() {
	var html = "";
	$("#last_Executors").html(html);
}

function SaveApprovalOpinion(state, statename) {
	var param = new tlv8.RequestParam();
	param.set("sdata1", sData1);
	param.set("taskID", taskID);
	param.set("opinion", $("#opinions").val());
	param.set("state", state);
	param.set("statename", statename);
	tlv8.XMLHttpRequest("saveApprovalOpinionAction", param);
}

function ensure() {
	var isSend = $("#isSendMessage").val();
	// var opinion = $("#opinions").val();
	if (executeMode == "advance" && exwcuteActivity.isEmpty()) {
		alert("请选择处理环节!");
		return;
	}
	if (executeMode == "advance") {
		var keySet = exwcuteActivity.keySet();
		for (var k = 0; k < keySet.length; k++) {
			var Factivity = keySet[k];
			var actype = $("#" + Factivity).attr("actype");
			var exePerson = exwcuteActivity.get(Factivity);
			if (executeMode == "advance" && !Factivity) {
				alert("请选择处理环节!");
				return;
			}
			if ((exePerson == "" || exePerson.isEmpty()) && actype != "end") {
				alert("请选择环节的处理者!");
				return;
			}
			// SaveApprovalOpinion("esFinished", "已完成");
			var exepersons = [];
			if (actype == "end") {
				exepersons[0] = justep.Context.getCurrentPersonID();
			} else {
				exepersons = exePerson.keySet();
			}
			tlv8.task
					.flowout(
							flowID,
							taskID,
							exepersons.join(","),
							Factivity,
							sData1,
							function() {
								windowEnsure();
							});
		}
	} else if (executeMode == "transfer") {
		var keySet = exwcuteActivity.keySet();
		for (var k = 0; k < keySet.length; k++) {
			var Factivity = keySet[k];
			var actype = $("#" + Factivity).attr("actype");
			var exePerson = exwcuteActivity.get(Factivity);
			if (executeMode == "advance" && !Factivity) {
				alert("请选择处理环节!");
				return;
			}
			if (exePerson == "" || exePerson.isEmpty()) {
				alert("请选择环节的处理者!");
				return;
			}
			SaveApprovalOpinion("esTransfered", "已转发");
			var exepersons = exePerson.keySet();
			tlv8.task
					.flowtransmit(
							taskID,
							exepersons.join(","),
							Factivity,
							sData1,
							function() {
								windowEnsure();
							});
		}
	} else if (executeMode == "back") {
		SaveApprovalOpinion("esReturned", "已回退");
		tlv8.task.flowback(flowID, taskID, function() {
			windowEnsure();
		});
	} else if (executeMode == "abort") {
		SaveApprovalOpinion("esAborted", "已终止");
		tlv8.task.flowstop(flowID, taskID, function() {
			windowEnsure();
		});
	}

}

function windowEnsure() {
	var callback = getParamValueFromUrl("callback");
	if (callback && callback != "") {
		justep.dialog.ensureDialog(callback);
	} else {
		windowCancel();
	}
}

function windowCancel() {
	justep.dialog.closeDialog();
}

function clearExecuteor(obj) {
	exeuctorList.remove(obj.id);
	$("#" + obj.id).remove();
}

function onActivitySelect(obj) {
	if (!multipleModel) {
		exwcuteActivity = new Map();
		aFactivity = $(obj).attr("id");
		$(".activitypsmlist").each(function() {
			if ($(this).attr("id") != aFactivity + "_Executors") {
				$(this).hide();
			} else {
				$(this).show();
			}
		});
		exwcuteActivity.put(aFactivity, "");
		$(".activity").each(function() {
			if ($(this).attr("id") != $(obj).attr("id")) {
				$(this).attr("checked", false).checkboxradio("refresh");
			} else {
				$(obj).attr("checked", true);
			}
		});
	} else {
		// 环节不支持多选
	}
}

function onExcutorSelect(obj) {
	var checked = obj.checked;
	var exePerson = exwcuteActivity.get(aFactivity);
	if (exePerson == "" || exePerson.isEmpty()) {
		exePerson = new Map();
	}
	if (checked) {
		exePerson.put(obj.id, $(obj).attr("label"));
		exwcuteActivity.put(aFactivity, exePerson);
	} else {
		exePerson.remove(obj.id);
	}
}

/*
 * 发送短信
 */
function sendMessage(fids, activityName) {
	var param = new tlv8.RequestParam();
	param.set("ids", fids);
	param.set("content", activityName);
	param.set("istask", "true");
	tlv8.XMLHttpRequest("sendMessageByIDContentAction", param, "post",
			true);
}