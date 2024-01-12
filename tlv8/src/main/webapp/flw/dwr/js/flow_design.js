/**
 * 打开流程图
 */
function openFlow() {
	var url = "/flw/dwr/dialog/process-select.html";
	tlv8.portal.dailog.openDailog("打开流程", url, 1000, 700, flowdrowopenback);
}

// 打开回调（加载流程图数据）
function flowdrowopenback(rdata) {
	var param = new tlv8.RequestParam();
	param.set("processID", rdata.id);
	tlv8.XMLHttpRequest("flowloadIocusXAction", param, "post", true,
			function(r) {
				if (r.data.flag == "false") {
					layui.layer.alert(r.data.message);
					return false;
				} else {
					var resData = JSONC.decode(r.data.data);
					drow_init(rdata.id, rdata.name, resData.jsonStr);
					OperationHistory.init();
					var group = document.getElementById('group');
					var Love = group.bindClass;
					window.modeljson = Love.toJson();
				}
			});
}

/**
 * 保存流程图数据
 */
function saveFlowData(Love) {
	var param = new tlv8.RequestParam();
	param.set("sprocessid", Love.id);
	param.set("sprocessname", Love.name);
	param.set("sdrawlg", CryptoJS.AESEncrypt(document.getElementById("group").innerHTML));
	param.set("sprocessacty", CryptoJS.AESEncrypt(Love.toJson().toString().encodeSpechars()));
	tlv8.XMLHttpRequest("saveFlowDrawLGAction", param, "post", true,
			function(r) {
				layui.layer.msg("保存成功!");
			});
}

/*
 * 选择执行页面
 */
function selectexePage() {
	var url = "/flw/dwr/dialog/func-tree-select.html";
	tlv8.portal.dailog.openDailog("选择执行页面", url, 300, 400, function(data) {
		if (data) {
			var win = document.getElementById('propWin');
			$("#" + win.type + '_p_exepage').val(data.surl);
		}
	});
}

/*
 * 选择执行人
 */
function selectexePerson() {
	var url = "/comon/SelectDialogPsn/SelectChPsm.html";
	tlv8.portal.dailog.openDailog("选择执行人", url, 1000, 700, function(data) {
		if (data) {
			var win = document.getElementById('propWin');
			$("#" + win.type + '_p_roleID').val(data.id);
			$("#" + win.type + '_p_role').val(data.name);
		}
	});
}

/*
 * 环节标题
 */
function selectexeLabel() {
	var win = document.getElementById('propWin');
	var Olexpression = document.getElementById(win.type + '_p_label').value;
	if (Olexpression) {
		Olexpression = tlv8.encodeURIComponent(Olexpression.toString()
				.decodeSpechars());
	} else {
		Olexpression = "";
	}
	var url = "/flw/dwr/dialog/expression-editer.html?Olexpression="
			+ Olexpression;
	tlv8.portal.dailog.openDailog("环节标题-表达式编辑器", url, 1000, 700,
			function(data) {
				try {
					$("#" + win.type + '_p_label').val(data ? data : "");
				} catch (e) {
				}
			});
}

/*
 * 选择执行人范围
 */
function selectRange() {
	var win = document.getElementById('propWin');
	var Olexpression = $("#" + win.type + '_p_group').val();
	if (Olexpression) {
		Olexpression = tlv8.encodeURIComponent(Olexpression.toString()
				.decodeSpechars());
	} else {
		Olexpression = "";
	}
	var url = "/flw/dwr/dialog/expression-editer.html?Olexpression="
			+ Olexpression;
	tlv8.portal.dailog.openDailog("执行人范围-表达式编辑器", url, 1000, 700,
			function(data) {
				try {
					$("#" + win.type + '_p_group').val(data ? data : "");
				} catch (e) {
				}
			});
}

// 转发规则
function selectRangeTran() {
	var win = document.getElementById('propWin');
	var Olexpression = $("#" + win.type + '_r_transe').val();
	if (Olexpression) {
		Olexpression = tlv8.encodeURIComponent(Olexpression.toString()
				.decodeSpechars());
	} else {
		Olexpression = "";
	}
	var url = "/flw/dwr/dialog/expression-editer.html?Olexpression="
			+ Olexpression;
	tlv8.portal.dailog.openDailog("转发规则-表达式编辑器", url, 1000, 700,
			function(data) {
				try {
					$("#" + win.type + '_r_transe').val(data ? data : "");
				} catch (e) {
				}
			});
}

/*
 * 编辑条件表达式
 */
function selectConditionExpression() {
	var win = document.getElementById('propWin');
	var Olexpression = $("#" + win.type + '_p_expression').val();
	if (Olexpression) {
		Olexpression = tlv8.encodeURIComponent(Olexpression.toString()
				.decodeSpechars());
	} else {
		Olexpression = "";
	}
	var url = "/flw/dwr/dialog/expression-editer.html?operatType=condition&Olexpression="
			+ Olexpression;
	tlv8.portal.dailog.openDailog("转发规则-表达式编辑器", url, 1000, 700,
			function(data) {
				try {
					$("#" + win.type + '_p_expression').val(data ? data : "");
				} catch (e) {
				}
			});
}