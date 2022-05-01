var flowAuditOpinion = {};

// 列表data、列表实例
var Audit_list_data, audit_list_grid;

// 需要从主页面传递的参数
// 数据连接标识、业务表名、审核意见表、流程ID、任务ID、执行人ID,业务单据ID
var busiDataKey, busiTable, auditTable, flowID, taskID, ePersonID, sData1;
// 流程按钮配置
var flowItem, auditParam, billidRe, FAGREETEXTRe, isRequired;

/*
 * 页面初始化
 */
function initPage() {
	flowID = tlv8.RequestURLParam.getParam("flowID");
	taskID = tlv8.RequestURLParam.getParam("taskID");
	ePersonID = tlv8.RequestURLParam.getParam("ePersonID");
	sData1 = tlv8.RequestURLParam.getParam("sData1");
	flowItem = J_u_decode(tlv8.RequestURLParam.getParam("flowItem"));
	auditParam = J_u_decode(tlv8.RequestURLParam.getParam("auditParam"));
	if (flowItem && typeof (flowItem) == "string" && flowItem != "") {
		flowItem = flowItem.toString().toJSON();
	}
	if (auditParam && typeof (auditParam) == "string" && auditParam != "") {
		auditParam = auditParam.toString().toJSON();
		busiDataKey = auditParam.busiDataKey;
		busiTable = auditParam.busiTable;
		auditTable = auditParam.auditTable;
		billidRe = auditParam.billidRe;
		FAGREETEXTRe = auditParam.FAGREETEXTRe;
		isRequired = auditParam.isRequired;
	}

	initAuditToolbar();
	initAudit_list_grid();
	initQuickInputSelect();

	var CurrentDeptName = tlv8.Context.getCurrentDeptName();
	if (CurrentDeptName == "null" || CurrentDeptName == "") {
		CurrentDeptName = tlv8.Context.getCurrentOgnName();
	}
	var CurrentPersonName = tlv8.Context.getCurrentPersonName();
	$("#auditorDept").val(CurrentDeptName);
	$("#auditorName").val(CurrentPersonName);
}

/*
 * 初始化处理工具条
 */
function initAuditToolbar() {
	var AuditToolbarHtml = "<table width='100%' boder='0' class='flowBarTable'><tr><td height='40px'></td>";
	var isflowpa = false;
	if (flowItem) {
		var item = flowItem;
		if (item.out == "false") {
		} else {
			isflowpa = true;
			AuditToolbarHtml += "<td width='60px' height='40px'>"
					+ "<button type='button' id='flowOutItem' title='流转' class='layui-btn layui-btn-sm "
			if (item.out == "readonly") {
				AuditToolbarHtml += "layui-btn-disabled' disabled=true ";
			} else {
				AuditToolbarHtml += "' onclick='flowAuditOpinion.flowOut()' ";
			}
			AuditToolbarHtml += ">流转</button></td>";
		}
		if (item.back == "false") {
		} else {
			isflowpa = true;
			AuditToolbarHtml += "<td width='60px'>"
					+ "<button type='button' id='flowBackItem' title='回退' class='layui-btn layui-btn-sm layui-btn-warm "
			if (item.back == "readonly") {
				AuditToolbarHtml += "layui-btn-disabled' disabled=true ";
			} else {
				AuditToolbarHtml += "' onclick='flowAuditOpinion.flowBack()' ";
			}
			AuditToolbarHtml += ">回退</button></td>";
		}
		if (item.transmit == "false") {
		} else {
			isflowpa = true;
			AuditToolbarHtml += "<td width='60px'>"
					+ "<button type='button' id='flowTransmitItem' title='转发' class='layui-btn layui-btn-sm layui-btn-normal "
			if (item.transmit == "readonly") {
				AuditToolbarHtml += "layui-btn-disabled' disabled=true ";
			} else {
				AuditToolbarHtml += "' onclick='flowAuditOpinion.flowTransmit()' ";
			}
			AuditToolbarHtml += ">转发</button></td>";
		}
		if (item.pause == "false") {
		} else {
			isflowpa = true;
			AuditToolbarHtml += "<td width='60px'>"
					+ "<button type='button' id='flowPauseItem' title='暂停' class='layui-btn layui-btn-sm layui-btn-primary "
			if (item.pause == "readonly") {
				AuditToolbarHtml += "layui-btn-disabled' disabled=true ";
			} else {
				AuditToolbarHtml += "' onclick='flowAuditOpinion.flowPause()' ";
			}
			AuditToolbarHtml += ">暂停</button></td>";
		}
		if (item.stop == "false") {
		} else {
			isflowpa = true;
			AuditToolbarHtml += "<td width='60px'>"
					+ "<button type='button' id='flowStopItem' title='终止' class='layui-btn layui-btn-sm layui-btn-danger "
			if (item.stop == "readonly") {
				AuditToolbarHtml += "layui-btn-disabled' disabled=true ";
			} else {
				AuditToolbarHtml += "' onclick='flowAuditOpinion.flowStop()' ";
			}
			AuditToolbarHtml += ">终止</button></td>";
		}
		AuditToolbarHtml += "<td width='60px' style='display:none;'>"
				+ "<button type='button' id='ensureItem' title='确定' class='btn btn-default btn-sm'>确定</button></td>";
		AuditToolbarHtml += "<td width='60px'>"
				+ "<button type='button' id='cancelItem' title='取消' class='btn btn-default btn-sm'>取消</button></td>";
	}
	AuditToolbarHtml += "</tr></table>";
	$("#audit_toolbar_view").html(AuditToolbarHtml);

	document.getElementById("ensureItem").onclick = function() {
		var auditOp = $("#auditOpinion").val();
		if (saveAuditOpinion()) {
			tlv8.portal.dailog.dailogEngin({
				auditOp : auditOp
			});
		}
	};
	document.getElementById("cancelItem").onclick = function() {
		tlv8.portal.dailog.dailogCancel();
	};
	if (!isflowpa) {
		$("#mainlayout").layout("remove","north");
	}
}

/*
 * 初始化审批意见列表
 */
function initAudit_list_grid() {
	Audit_list_data = new tlv8.Data();
	Audit_list_data.setDbkey(busiDataKey);
	Audit_list_data.setTable(auditTable);
	Audit_list_data.setOrderby("FCREATETIME desc");

	var det = document.getElementById("audit_list_grid_view");
	var labelid = "No,fNODENAME," + FAGREETEXTRe + ",FCREATEPERNAME,FCREATEDEPTNAME,FCREATETIME";
	var labels = "No.,处理环节,处理意见,处理人,处理部门,处理时间";
	var datatype = "ro,string,string,string,string,datetime";
	var labelwidth = "40,100,200,80,100,140";
	var dataAction = {
		"queryAction" : "getGridAction",
		"savAction" : "saveAction",
		"deleteAction" : "deleteAction"
	};
	var where = billidRe + "='" + sData1 + "'";
	var maingrid = new tlv8.createGrid(det, labelid, labels, labelwidth,
			dataAction, "100%", "100%", Audit_list_data, 10, where, "", "",
			datatype, "false", "true");
	audit_list_grid = maingrid.grid;
	audit_list_grid.settoolbar(false, false, true, false);

	if (!auditTable || auditTable == "") {
		alert("未指定审核意见表！");
		return false;
	}
	audit_list_grid.refreshData();
}

/*
 * 快速录入
 */
function initQuickInputSelect() {
	var testselect = document.getElementById("quickInput");
	var sql = "select FCONCLUSIONNAME from OA_FLOWCONCLUSION where FCREATORID = '"
			+ tlv8.Context.getCurrentPersonID() + "' order by FORDER";
	tlv8.GridSelect(testselect, "oa", sql, false, false);
	var param = new tlv8.RequestParam();
	param.set("taskID", taskID);
	var r = tlv8.XMLHttpRequest("getActivityByTaskID", param, "post",
			false, null);
	var activity = r.data.data;
	var randid = new UUID().toString();
	var qSql = "select " + FAGREETEXTRe + " from " + auditTable
			+ " where FTASKID='" + taskID + "' and " + billidRe + "='" + sData1
			+ "' and '" + randid + "'='" + randid + "'";
	var result = tlv8.sqlQueryAction(busiDataKey, qSql);
	var value = result.getValueByName(FAGREETEXTRe.toUpperCase());
	$("#auditOpinion").val(trim(value));
	$("select#quickInput").attr("lay-filter","optionsel");
	layui.form.render('select');
	layui.form.on('select(optionsel)', function(data){
		console.log(data);
	  $("#auditOpinion").val(data.value);
	});
}

function QuickInputSelected(obj) {
	$("#auditOpinion").val($(obj).val());
}
/*
 * 保存审批意见
 */
function saveAuditOpinion() {
	var auditOp = $("#auditOpinion").val();
	if (isRequired == "true" && (!auditOp || auditOp == "")) {
		alert("审核意见不能为空!");
		return false;
	}
	var param = new tlv8.RequestParam();
	param.set("dbkey", busiDataKey);
	param.set("audittable", auditTable);
	param.set("billidRe", billidRe);
	param.set("agreettextRe", FAGREETEXTRe);
	param.set("opinion", $("#auditOpinion").val());
	param.set("flowid", flowID);
	param.set("taskID", taskID);
	param.set("sdata1", sData1);
	param.set("opviewID", "audit");
	tlv8.XMLHttpRequest("SaveAuditOpinionAction", param, "post", false,
			function(r) {
				if (r.data.flag == "false") {
					alert(r.data.message);
					return false;
				}
			});
	return true;
}

/*
 * 回退
 */
flowAuditOpinion.flowBack = function() {
	if (saveAuditOpinion())
		tlv8.portal.dailog.dailogEngin("flowBack");
};
/*
 * 流转
 */
flowAuditOpinion.flowOut = function() {
	if (saveAuditOpinion())
		tlv8.portal.dailog.dailogEngin("flowOut");
};
/*
 * 转发
 */
flowAuditOpinion.flowTransmit = function() {
	if (saveAuditOpinion())
		tlv8.portal.dailog.dailogEngin("flowTransmit");
};
/*
 * 暂停
 */
flowAuditOpinion.flowPause = function() {
	if (saveAuditOpinion())
		tlv8.portal.dailog.dailogEngin("flowPause");
};
/*
 * 终止
 */
flowAuditOpinion.flowStop = function() {
	if (saveAuditOpinion())
		tlv8.portal.dailog.dailogEngin("flowStop");
};

$(window).resize(function(){ 
	setTimeout(function(){
		initPage();
	},500);
});