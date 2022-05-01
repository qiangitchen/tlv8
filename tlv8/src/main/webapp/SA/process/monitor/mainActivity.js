var grid_flow, grid_task, grid_flowChart, grid_flowData;

var calweckupItem, restartItem;

var today;

function initPage() {
	init_grid_flow();
	today = tlv8.System.Date.strToDate(tlv8.System.Date.sysDate());
}

/*
 * 初始化流程实例列表
 */
function init_grid_flow() {
	var data = new tlv8.Data();
	data.setTable("SA_TASK_MONITOR");
	data.setDbkey("system");
	data.setOrderby("sCreateTime desc");
	var det = document.getElementById("grid_flowView");
	var labelid = "No,sName,sflowName,sCreateTime,SEPERSONName,sAStartTime,sAFinishTime,sData1,sStatusID,sStatusName"
			+ ",sKindID,SCPERSONID,SCPOSID,SCDEPTID,SCOGNID,SCFID,sEURL,sActivity"
			+ ",sProjectName,sCustomerName,sPlanName,sFMAKERNAME,SFLOWID";
	var labels = "No.,流程名称,文件名称,创建时间,创建人,sAStartTime,sAFinishTime,单据ID,状态,状态名称"
			+ ",sKindID,sCreatorPersonID,sCreatorPosID,sCreatorDeptID,sCreatorOgnID,sCreatorFID,sEURL,sActivity"
			+ ",客户编码,客户名称,单据编号,制单人,流程ID";
	var datatype = "ro,string,ro,datetime,ro,datetime,datetime,string,string,string"
			+ ",ro,ro,ro,ro,ro,ro,ro,ro" + ",string,string,string,string,ro";
	var labelwidth = "40,80,600,130,80,0,0,0,0,80" + ",0,0,0,0,0,0,0,0"
			+ ",0,0,0,0,0";
	var dataAction = {
		"queryAction" : "getGridAction",
		"savAction" : "saveAction",
		"deleteAction" : "deleteAction"
	};
	var maingrid = new tlv8.createGrid(det, labelid, labels, labelwidth,
			dataAction, "100%", "100%", data, 20, "", "", "", datatype,
			"false", "true");
	grid_flow = maingrid.grid;
	grid_flow.seteditModel(false);
	grid_flow.settoolbar(false, false, true, false);

	calweckupItem = grid_flow.insertSelfBar("唤醒", "50px", "");
	restartItem = grid_flow.insertSelfBar("启动流程", "80px", "");

	var myQuickItem = "<form class='layui-form'><table><tr><td style='width:100px;padding-right:5px;'>";
	myQuickItem += "<select onchange='selectDateFilter(this)' id='Time_select' lay-filter='Time_select1'>"
			+ "<option value='all'>全部</option>"
			+ "<option value='today'>今天</option>"
			+ "<option value='yestoday'>昨天</option>"
			+ "<option value='theWeek'>本周</option>"
			+ "<option value='beforeWeek'>上周</option>"
			+ "<option value='theMonth'>本月</option>"
			+ "<option value='beforeMonth'>上月</option>"
			+ "<option value='theYear'>今年</option>"
			+ "<option value='beforeYear'>上年</option>"
			+ "<option value='myConfirm'>自定义...</option></select></td>";// 嵌入自定义工具条内容

	myQuickItem += "<td style='width:120px;padding-right:5px;'>"
			+ "<select onchange='selectStatusFilter(this)' id='status_select' lay-filter='status_select1'>"
			+ "<option value='tesReady'>正在处理</option>"
			+ "<option value='tesFinished'>已完成</option>"
			+ "<option value='all'>全部</option>"
			+ "<option value='tesReturned'>已回退</option>"
			+ "<option value='tesCanceled'>已取消</option>"
			+ "<option value='tesPause'>已暂停</option>"
			+ "<option value='tesAborted'>已终止</option></select></td>";

	var currentPsnId = tlv8.Context.getCurrentPersonID();
	myQuickItem += "<td style='width:180px;padding-right:5px;'>"
			+ "<select onchange='selectOrgFilter(this)' id='Org_select' lay-filter='Org_select1' "
			+ "value='本人' filterval=\"(SCPERSONID ='"
			+ currentPsnId
			+ "' or SEPERSONID = '"
			+ currentPsnId
			+ "' or EXISTS(select 1 from SA_Task ts where ts.sflowid = SA_TASK_MONITOR.sflowid"
			+ " and (ts.SCPERSONID = '"
			+ currentPsnId
			+ "' or SEPERSONID = '"
			+ currentPsnId
			+ "')) and SEPERSONID is not null)\">"
			+ "<option value='本人'>本人</option>"
			+ "<option value='select'>选择...</option></select></td>";
	myQuickItem += "</td></tr></table>";
	myQuickItem += "</form>";
	document.getElementById(det.id + "-grid-item").innerHTML = myQuickItem;
	layui.form.render("select");
	
	layui.form.on('select(Time_select1)', function(data){
	  selectDateFilter(data.elem);
	});
	layui.form.on('select(status_select1)', function(data){
		selectStatusFilter(data.elem);
	});
	layui.form.on('select(Org_select1)', function(data){
		selectOrgFilter(data.elem);
	});

	var frowid = grid_flow.getCurrentRowId();
	var flowid = grid_flow.getValueByName("SFLOWID",frowid);
	init_grid_taskWindow(flowid);
	grid_flow
			.setStaticFilter("(SCPERSONID ='"
					+ currentPsnId
					+ "' or SEPERSONID = '"
					+ currentPsnId
					+ "' or EXISTS(select 1 from SA_Task ts where ts.sflowid = SA_TASK_MONITOR.sflowid"
					+ " and (ts.SCPERSONID = '"
					+ currentPsnId
					+ "' or SEPERSONID = '"
					+ currentPsnId
					+ "')) and SEPERSONID is not null) and (SSTATUSID = 'tesExecuting' or SSTATUSID = 'tesReady')");
	grid_flow.refreshData();
}

function activetyname(event) {
	var rowid = event.rowid;
	var label = grid_flow.getValueByName("sName", rowid);
	return label.split(":")[0];
}

/*
 * 时间过滤 @param {Object} obj
 */
var dateFilter = "";
var begin = null;
var end = null;
function selectDateFilter(obj) {
	var temp = new Date(today.getFullYear(), today.getMonth(), today.getDate());
	if (obj.value == "all") {
		begin = null;
		end = null;
		dateFilter = "1=1";
	} else if (obj.value == "today") {// 今天
		begin = temp;
		end = tlv8.DateAdd("d", begin, 1);
	} else if (obj.value == "yestoday") {// 昨天
		end = temp;
		begin = tlv8.DateAdd("d", end, -1);
	} else if (obj.value == "theWeek") {// 本周
		begin = tlv8.DateAdd("d", temp, -temp.getDay());
		end = tlv8.DateAdd("w", begin, 1);
	} else if (obj.value == "beforeWeek") {// 上周
		begin = tlv8.DateAdd("d", temp, -(temp.getDay() + 7));
		end = tlv8.DateAdd("w", begin, 1);
	} else if (obj.value == "theMonth") {// 本月
		begin = new Date(today.getFullYear(), today.getMonth(), 1);
		end = new Date(today.getFullYear(), today.getMonth() + 1, 1);
	} else if (obj.value == "beforeMonth") {// 上月
		begin = new Date(today.getFullYear(), today.getMonth() - 1, 1);
		end = new Date(today.getFullYear(), today.getMonth(), 1);
	} else if (obj.value == "theYear") {// 今年
		begin = new Date(today.getFullYear(), 0, 1);
		end = new Date(today.getFullYear() + 1, 0, 1);
	} else if (obj.value == "beforeYear") {// 上年
		begin = new Date(today.getFullYear() - 1, 0, 1);
		end = new Date(today.getFullYear(), 0, 1);
	} else if (obj.value == "myConfirm") {// 自定义...
		tlv8.portal.dailog.openDailog("自定义...",
				"/system/task/dialog/MyDateConfig.html", 300, 100,
				myConfigDatebackFn, {
					refreshItem : false,
					enginItem : true,
					CanclItem : true
				});
		return;
	}
	if (begin && end) {
		dateFilter = "sCreateTime >= to_date('" + begin.format('yyyy-MM-dd')
				+ "','yyyy-MM-dd') and sCreateTime <= to_date('"
				+ end.format('yyyy-MM-dd') + "','yyyy-MM-dd')";
	}
	var temp_dateFilter = dateFilter;
	if (state_filter_val && state_filter_val != "" && dateFilter
			&& dateFilter != "") {
		temp_dateFilter = state_filter_val + " and " + dateFilter;
	} else if (state_filter_val && state_filter_val != "") {
		temp_dateFilter = state_filter_val;
	}
	var filterval = $("#Org_select").attr("filterval");
	if (temp_dateFilter && temp_dateFilter != "") {
		temp_dateFilter += " and " + filterval;
	} else {
		temp_dateFilter = filterval;
	}
	grid_flow.setStaticFilter(temp_dateFilter);// 设置过滤条件
	// grid_flow.refreshData();
}
function myConfigDatebackFn(redata) {
	if (redata) {
		dateFilter = "sCreateTime >= to_date('"
				+ redata.begin.format('yyyy-MM-dd')
				+ "','yyyy-MM-dd') and sCreateTime <= to_date('"
				+ redata.end.format('yyyy-MM-dd') + "','yyyy-MM-dd')";
		grid_flow.setStaticFilter(dateFilter);
		// grid_flow.refreshData();
	}
}

/*
 * 状态选择 @param {Object} obj
 */
var state_filter_val = "";
function selectStatusFilter(obj) {
	var status = obj.value;
	var temp_state_filter_val = "";
	if (status == "all") {
		state_filter_val = "";
	} else if ('tesReady' == status) {
		state_filter_val = "SSTATUSID = 'tesExecuting' or SSTATUSID = 'tesReady'";
	} else {
		state_filter_val = "SSTATUSID = '" + status + "'";
	}
	if (dateFilter && dateFilter != "" && state_filter_val
			&& state_filter_val != "") {
		temp_state_filter_val = dateFilter + " and " + state_filter_val;
	} else if (dateFilter && dateFilter != "") {
		temp_state_filter_val = dateFilter;
	} else {
		temp_state_filter_val = state_filter_val;
	}

	var filterval = $("#Org_select").attr("filterval");
	if (temp_state_filter_val && temp_state_filter_val != "") {
		temp_state_filter_val += " and " + filterval;
	} else {
		temp_state_filter_val = filterval;
	}
	grid_flow.setStaticFilter(temp_state_filter_val);
	// grid_flow.refreshData();
}

/*
 * 机构选择
 */
var org_Select_filter = "(1=1)";
function selectOrgFilter(obj) {
	if (obj.value == "select") {
		tlv8.portal.dailog.openDailog("机构选择",
				"/comon/SelectDialogPsn/SelectChOrgView.html", 500, 350,
				function(rData) {
					org_Select_filter = "(";
					var sOrg_Value = "";
					for ( var i in rData) {
						if (i > 0) {
							org_Select_filter += " or ";
							sOrg_Value += ",";
						}
						org_Select_filter += " SCFID like '" + rData[i].SFID
								+ "%' or SEFID like '" + rData[i].SFID + "%'";
						sOrg_Value += rData[i].name;
					}
					org_Select_filter += ")";
					$("#Org_select").attr("filterval", org_Select_filter);
					$("#Org_select").html(
							"<option value='本人'>本人</option>"
									+ "<option value='select'>选择...</option>"
									+ "<option value='" + sOrg_Value + "'>"
									+ sOrg_Value + "</option>");
					$("#Org_select").val(sOrg_Value);
					layui.form.render("select");
					$("#Org_select")[0].title = sOrg_Value;
					var tem_org_Select_filter = "";
					if (dateFilter && dateFilter != "") {
						tem_org_Select_filter += dateFilter + " and "
								+ org_Select_filter;
					} else {
						tem_org_Select_filter = org_Select_filter;
					}
					if (state_filter_val && state_filter_val != "") {
						tem_org_Select_filter += state_filter_val + " and "
								+ tem_org_Select_filter;
					}
					grid_flow.setStaticFilter(tem_org_Select_filter);
					// grid_flow.refreshData();
				});
	} else {
		var currentPsnId = tlv8.Context.getCurrentPersonID();
		org_Select_filter = "(SCPERSONID ='"
				+ currentPsnId
				+ "' or SEPERSONID = '"
				+ currentPsnId
				+ "' or EXISTS(select 1 from SA_Task ts where ts.sflowid = SA_TASK_MONITOR.sflowid"
				+ " and (ts.SCPERSONID = '" + currentPsnId
				+ "' or SEPERSONID = '" + currentPsnId
				+ "')) and SEPERSONID is not null)";
		$("#Org_select").attr("filterval", org_Select_filter);
		var tem_org_Select_filter = "";
		if (dateFilter && dateFilter != "") {
			tem_org_Select_filter += dateFilter + " and " + org_Select_filter;
		} else {
			tem_org_Select_filter = org_Select_filter;
		}
		if (state_filter_val && state_filter_val != "") {
			tem_org_Select_filter += state_filter_val + " and "
					+ tem_org_Select_filter;
		}
		grid_flow.setStaticFilter(tem_org_Select_filter);
		// grid_flow.refreshData();
	}

}

/*
 * 流程实例选择事件
 */
function grid_flow_selected(event) {
	var cRowid = event.CurrentRowId;
	var flowStatus = event.getValueByName("sStatusID", cRowid);
	if (flowStatus != "tesPause") {// 暂停状态
		document.getElementById(calweckupItem).disabled = true;
	} else {
		document.getElementById(calweckupItem).disabled = false;
	}
	if (flowStatus != "tesAborted") {// 终止状态
		document.getElementById(restartItem).disabled = true;
	} else {
		document.getElementById(restartItem).disabled = false;
	}
}

var do_task_Item, recycle_Item, SpecFlowoutItem, changeExcutor_Item, puse_Item;
/*
 * 初始化任务列表 @param {Object} flowid
 */
function init_grid_taskWindow(flowid) {
	var data = new tlv8.Data();
	data.setTable("SA_Task");
	data.setDbkey("system");
	data.setOrderby("SCREATETIME asc");
	var det = document.getElementById("grid_taskWindow");
	var labelid = "No,sName,SEPERSONName,SCPERSONName,sAStartTime,sAFinishTime,sData1,sStatusID,sStatusName"
			+ ",sKindID,SCPERSONID,SCPOSID,SCDEPTID,SCOGNID,SCFID,sEURL,sActivity,SEPERSONID";
	var labels = "No.,名称,执行人,提交人,开始时间,结束时间,单据ID,状态,状态名称"
			+ ",sKindID,sCreatorPersonID,sCreatorPosID,sCreatorDeptID,sCreatorOgnID,sCreatorFID,sEURL,sActivity,SEPERSONID";
	var datatype = "ro,string,string,string,datetime,datetime,string,string,string,ro"
			+ ",ro,ro,ro,ro,ro,ro,ro,ro";
	var labelwidth = "40,180,80,80,120,120,0,0,80" + ",0,0,0,0,0,0,0,0,0";
	var dataAction = {
		"queryAction" : "getGridAction",
		"savAction" : "saveAction",
		"deleteAction" : "deleteAction"
	};
	var filter = "1=2";
	if (flowid && flowid != "") {
		filter = "sflowId = '" + flowid + "' and SID != '" + flowid + "'";
	}
	var maingrid = new tlv8.createGrid(det, labelid, labels, labelwidth,
			dataAction, "100%", "100%", data, 20, filter, "", "", datatype,
			"false", "true");
	grid_task = maingrid.grid;
	grid_task.seteditModel(false);
	grid_task.settoolbar(false, false, true, false);
	do_task_Item = grid_task.insertSelfBar("处理任务", "90px", "do_task()",
			"../../../comon/image/toolbar/flw/do_task.gif");
	// recycle_Item = grid_task.insertSelfBar("撤回", 40, "recycle_task()",
	// "../../comon/image/toolbar/recycle_task.gif");
	SpecFlowoutItem = grid_task.insertSelfBar("特送", "50px", "SpecFlowout()");
	changeExcutor_Item = grid_task.insertSelfBar("修改执行人", "100px",
			"changeExcutor()");
	puse_Item = grid_task.insertSelfBar("取消待办", "80px", "");
}

function grid_task_selected(event) {
	var cRowid = event.CurrentRowId;
	var sEURL = event.getValueByName("sEURL", cRowid);
	var flowStatus = event.getValueByName("sStatusID", cRowid);
	if (flowStatus != "tesReady") {// 尚未处理状态
		document.getElementById(do_task_Item).src = "../../../comon/image/toolbar/flw/un_do_task.gif";
		// document.getElementById(recycle_Item).disabled = true;
		document.getElementById(SpecFlowoutItem).disabled = true;
		document.getElementById(changeExcutor_Item).disabled = true;
		if (flowStatus == "tesCanceled") {
			document.getElementById(puse_Item).value = "激活待办";
			document.getElementById(puse_Item).onclick = reMpuse_ItemFlow;
			document.getElementById(puse_Item).disabled = false;
		} else {
			document.getElementById(puse_Item).disabled = true;
		}
	} else {
		document.getElementById(SpecFlowoutItem).disabled = false;
		document.getElementById(do_task_Item).src = "../../../comon/image/toolbar/flw/do_task.gif";
		// document.getElementById(recycle_Item).disabled = false;
		document.getElementById(changeExcutor_Item).disabled = false;

		document.getElementById(puse_Item).value = "取消待办";
		document.getElementById(puse_Item).disabled = false;
		document.getElementById(puse_Item).onclick = puse_ItemFlow;

	}
}

/*
 * 处理任务 @param {Object} rowid
 */
function do_task() {
	var rowid = grid_task.getCurrentRowId();
	var sStatusName = grid_task.getValueByName("sStatusName", rowid);
	var SEPERSONID = grid_task.getValueByName("SEPERSONID", rowid);
	var cupsnID = tlv8.Context.getCurrentPersonID();
	if (sStatusName == "尚未处理" && SEPERSONID == cupsnID) {
		tlv8.task.openTask(rowid);
	}
}

/*
 * 撤回任务 @param {Object} rowid
 */
function recycle_task() {
	var rowid = grid_task.getCurrentRowId();
	var sStatusName = grid_task.getValueByName("sStatusName", rowid);
	if (sStatusName != "尚未处理") {
		return;
	}
	var param = new tlv8.RequestParam();
	param.set("taskID", rowid);
	tlv8.XMLHttpRequest("RecycleTaskAction", param, "POST", true,
			function(r) {
				if (r.data.flag == "false") {
					alert(r.data.message);
				} else {
					grid_task.refreshData(grid_task.billfilter);
				}
			});
}

/*
 * 特送 @return {TypeName}
 */
function SpecFlowout() {
	var taskID = grid_task.getCurrentRowId();
	var sStatusName = grid_task.getValueByName("sStatusName", taskID);
	if (sStatusName != "尚未处理") {
		return;
	}
	var param = new tlv8.RequestParam();
	param.set("taskID", taskID);
	tlv8
			.XMLHttpRequest(
					"GetSpecFlowoutInfoAction",
					param,
					"post",
					true,
					function(r) {
						if (r.data.flag == "true") {
							try {
								var reActData = eval("(" + r.data.data + ")");
								var activityListStr = reActData.activityListStr;
								if (activityListStr == "[]")
									return false;
								var exe_selct_url = "/flw/flwcommo/flowDialog/Select_executor.html";
								exe_selct_url += "?flowID=" + reActData.flowID;
								exe_selct_url += "&taskID=" + reActData.taskID;
								exe_selct_url += "&sData1=" + reActData.sData1;
								exe_selct_url += "&isflowMonitor=true";

								tlv8.portal.dailog.openDailog('流程信息',
										exe_selct_url, 650, 380, flowEngion,
										null, null, activityListStr);
							} catch (e) {
								alert("流转失败!m:" + e.message);
							}
						} else {
							alert(r.data.message);
						}
					});
	var flowEngion = function(backData) {
		param.set("flowID", backData.flowID);
		param.set("taskID", backData.taskID);
		param.set("afterActivity", backData.activity);
		param.set("epersonids", backData.epersonids);
		param.set("sdata1", backData.sData1);
		tlv8.XMLHttpRequest("flowoutAction", param, "post", true,
				function(r) {
					if (r.data.flag == "false") {
						alert(r.data.message);
						return false;
					} else {
						grid_task.refreshData(grid_task.billfilter);
					}
				});
	};
}

/*
 * 取消
 */
function puse_ItemFlow() {
	grid_task
			.setValueByName("sStatusID", grid_task.CurrentRowId, "tesCanceled");
	grid_task.setValueByName("sStatusName", grid_task.CurrentRowId, "已取消");
	grid_task.saveData();
}

/*
 * 激活
 */
function reMpuse_ItemFlow() {
	grid_task.setValueByName("sStatusID", grid_task.CurrentRowId, "tesReady");
	grid_task.setValueByName("sStatusName", grid_task.CurrentRowId, "尚未处理");
	grid_task.saveData();
}

/*
 * 修改执行人
 */
function changeExcutor() {
	var taskID = grid_task.getCurrentRowId();
	var sStatusName = grid_task.getValueByName("sStatusName", taskID);
	if (sStatusName != "尚未处理") {
		return;
	}
	tlv8.portal.dailog.openDailog("选择执行人",
			"/comon/SelectDialogPsn/singleSelectPsn.html", 500, 350, function(
					re) {
				var param = new tlv8.RequestParam();
				param.set("taskID", taskID);
				param.set("epersonids", re.rowid);
				tlv8.XMLHttpRequest("ChangeFlowExcutorAction", param,
						"POST", true, function(r) {
							if (r.data.flag == "false") {
								alert(r.data.message);
							} else {
								grid_task.refreshData(grid_task.billfilter);
							}
						});
			});
}

/*
 * 终止流程
 */
function StopFlow() {

}

// 流程实例
function tab_flowView() {
	if (grid_flow) {
		// grid_flow.refreshData();
	}
}

// 任务列表
function tab_taskWindow() {
	var frowid = grid_flow.getCurrentRowId();
	var flowid = grid_flow.getValueByName("SFLOWID",frowid);
	init_grid_taskWindow(flowid);
	grid_task.refreshData();
}

// 流程轨迹
function tab_flowChart() {
	var frame_flowChart = document.getElementById("frame_flowChart");
	var url = cpath+"/flw/viewiocusbot/yj_iocus_bot.html";
	var frowid = grid_flow.getCurrentRowId();
	var flowID = grid_flow.getValueByName("SFLOWID",frowid);
	var taskID = grid_task ? grid_task.getCurrentRowId() : flowID;
	url += "?flowID=" + flowID + "&taskID=" + taskID;
	frame_flowChart.src = url;
}

// 流程数据
function tab_flowData() {
	var frame_flowData = document.getElementById("frame_flowData");
	var rowid = grid_task.getCurrentRowId();
	var sEURL = grid_task.getValueByName("sEURL", rowid);
	var url = sEURL;
	var frowid = grid_flow.getCurrentRowId();
	var flowID = grid_flow.getValueByName("SFLOWID",frowid);
	var taskID = grid_task ? grid_task.getCurrentRowId() : flowID;
	var sData1 = grid_task.getValueByName("sData1", taskID);
	var SEPERSONID = grid_task.getValueByName("SEPERSONID", taskID);
	if (url && url != "") {
		url += "?flowID=" + flowID + "&taskID=" + taskID + "&sData1=" + sData1
				+ "&task=" + taskID + "&activity-pattern=detail";
		if (url.indexOf(cpath+"/") < 0) {
			url = cpath + url
					+ "&activity-pattern=detail&bsessionid="
					+ parent.$.jpolite.clientInfo.businessid
					+ "&language=zh_CN&personid=" + SEPERSONID
					+ "&$portlet-state=portler:p";
		}
	}
	if (!sEURL || sEURL == "") {
		frame_flowData.src = "";
		return;
	}
	frame_flowData.src = url;
	var dataPageWin = frame_flowData.contentWindow;
	setTimeout(function() {
		var btns = dataPageWin.document.getElementsByTagName("input");
		for ( var i in btns) {
			btns[i].onclick = null;
		}
		var imgs = dataPageWin.document.getElementsByTagName("img");
		for ( var i in imgs) {
			imgs[i].onclick = null;
		}
		var as = dataPageWin.document.getElementsByTagName("a");
		for ( var i in as) {
			as[i].onclick = null;
		}
	}, 300);
	document.getElementById("frame_flowData").style.height = document.body.scrollHeight - 30;
}

function tabsselected(title, index) {
	switch (index) {
	case 0:
		tab_flowView();
		break;
	case 1:
		tab_taskWindow();
		break;
	case 2:
		tab_flowChart();
		break;
	case 3:
		tab_flowData();
		break;
	default:
		break;
	}
}