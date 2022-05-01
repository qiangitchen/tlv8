/*
 * 默认环节单选选 multipleModel = false
 */
var multipleModel = false;
var executeMode, activity, flowID, taskID, sData1, aFactivity;
var exwcuteActivity;

$(document).ready(function() {
	exwcuteActivity = new Map();
});

// $(document).bind("pagebeforecreate", function(event, data) {
$(document).ready(function(event, data) {
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
	if (!activityListStr) {
		windowEnsure();
	}
	activityListStr = window.eval(activityListStr);
	// console.log(activityListStr);
	var actylist = [];
	for (var i = 0; i < activityListStr.length; i++) {
		var activity = activityListStr[i];
		if (i == 0) {
			actylist.push('<li class="mui-table-view-cell mui-selected" id="'
					+ activity.id + '" data=\'' + JSON.stringify(activity)
					+ '\'>');
		} else {
			actylist.push('<li class="mui-table-view-cell" id="' + activity.id
					+ '" data=\'' + JSON.stringify(activity) + '\'>');
		}
		actylist.push('<span class="mui-icon mui-icon-compose"></span>');
		actylist
				.push('<a class="mui-navigate-right">' + activity.name + '</a>');
		actylist.push('</li>');
	}
	$("#activitylist").html(actylist.join(""));
	var list = document.querySelector('.mui-table-view.mui-table-view-radio');
	// list.addEventListener('selected', function(e) {
	$(list).bind(
			'selected',
			function(e) {
				// console.log(e.target);
				var thisobj = e.target;// e.detail.el;
				// console.log("当前选中的为：" + thisobj.innerText);
				onActivitySelect(thisobj);
				// console.log(thisobj);

				var checkedAct = window.eval("(" + $(thisobj).attr("data")
						+ ")");
				var excutorGroupfilter = [];
				if (typeof checkedAct.excutorGroup == "string") {
					var exGroup = checkedAct.excutorGroup;
					var exGroups = exGroup.split(",");
					for (var i = 0; i < exGroups.length; i++) {
						if (exGroups[i] && exGroups[i] != "") {
							excutorGroupfilter.push(" SFID like '%"
									+ exGroups[i] + "%'");
						}
					}
				}
				if (excutorGroupfilter.length > 0) {
					excutorGroupfilter = excutorGroupfilter.join(" or ");
				} else {
					excutorGroupfilter = "";
				}
				excutorGroupfilter = "(" + excutorGroupfilter
						+ ") and SFID like '"
						+ justep.Context.getCurrentOgnFID() + "%'";// 只能选择当前机构下的
				// console.log(excutorGroupfilter);
				tlv8.showModelState(true);
				$("#Executorslist").html("");
				$.ajax({
					async : false,
					url : "mobileExecutorTreeControler",
					type : "POST",
					data : {
						filter : encodeURIComponent(excutorGroupfilter)
					},
					dataType : "json",
					success : function(rdata) {
						$("#Executorslist").tree({
							data : rdata,
							checkbox : true,
							onBeforeCheck : onItemBeforeCheck,
							onCheck : onItemCheck
						});
						tlv8.showModelState(false);
					}
				});
			});
	if ($("#activitylist").find("li").length > 0) {
		var firsetli = $("#activitylist").find("li").get(0);
		$(firsetli).trigger("selected");
	}
}

function onItemBeforeCheck(node, checked) {
	if (node.SORGKINDID == "psm" || node.children) {
		return true;
	}
	return false;
}

function onItemCheck(node, checked) {
	// console.log(node);
}

function dosearchText(value) {
	$("#Executorslist").tree("search", value);
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
	try{
		var nodes = $("#Executorslist").tree("getChecked", "checked");
		for (var i = 0; i < nodes.length; i++) {
			var cnode = nodes[i];
			if (cnode.SORGKINDID == "psm") {
				onExcutorSelect(cnode, true);
			}
		}
	}catch (e) {
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
			tlv8.task.flowout(flowID, taskID, exepersons.join(","),
					Factivity, sData1, windowEnsure);
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
			tlv8.task.flowtransmit(taskID, exepersons.join(","),
					Factivity, sData1, windowEnsure);
		}
	} else if (executeMode == "back") {
		SaveApprovalOpinion("esReturned", "已回退");
		tlv8.task.flowback(flowID, taskID, windowEnsure);
	} else if (executeMode == "abort") {
		SaveApprovalOpinion("esAborted", "已终止");
		tlv8.task.flowstop(flowID, taskID, windowEnsure);
	}

}

function windowEnsure(re) {
	// var callback = getParamValueFromUrl("callback");
	// if (callback && callback != "") {
	justep.dialog.ensureDialog(re);
	// } else {
	// windowCancel();
	// }
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
		exwcuteActivity.put(aFactivity, "");
	} else {
		// 环节不支持多选
	}
}

function onExcutorSelect(obj, checked) {
	// var checked = obj.checked;
	var exePerson = exwcuteActivity.get(aFactivity);
	if (exePerson == "" || exePerson.isEmpty()) {
		exePerson = new Map();
	}
	if (checked) {
		exePerson.put(obj.id, obj.text);
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