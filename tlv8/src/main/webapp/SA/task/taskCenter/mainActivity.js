var autoreload = true;// 显示页面时自动刷新

var Data = new tlv8.Data();
Data.setTable("SA_TASK");
Data.setOrderby("SCREATETIME desc");
var currentgrid, do_task_item, recycle_task_item;
function initPage() {
	var d = document.getElementById("task_list");
	var labelid = "No,SFLOWID,SNAME,SSTATUSID,SSTATUSNAME"
			+ ",SCPERSONID,SCPERSONNAME,SCDEPTNAME,SCREATETIME"
			+ ",SEPERSONID,SEPERSONNAME,SEDEPTNAME,sAStartTime,sAFinishTime"
			+ ",SCURL,SEURL,sData1";
	var labels = "NO.,SFLOWID,任务标题,环节状态ID,环节状态" + ",cid,提交人,提交部门,提交时间"
			+ ",eid,执行人,执行部门,开始时间,结束时间" + ",SCURL,SEURL,sData1";
	var labelwidth = "40,0,290,0,80" + ",0,60,80,140" + ",0,60,80,140,140"
			+ ",0,0,0";
	var datatype = "ro,ro,string,ro,string" + ",string,string,string,datetime"
			+ ",string,string,string,datetime,datetime" + ",ro,ro,ro";// 设置字段类型
	var dataAction = {
		"queryAction" : "getGridAction",
		"savAction" : "",
		"deleteAction" : ""
	};
	var currentPersonID = tlv8.Context.getCurrentPersonID();
	var where = "( SEPERSONID='" // SCPERSONID='" + currentPersonID + "' or
			+ currentPersonID
			+ "') and SSTATUSID != 'tesExecuting' and SEURL !='null' ";
	var maingrid = new tlv8.createGrid(d, labelid, labels, labelwidth,
			dataAction, "100%", "100%", Data, 20, where, "", "", datatype,
			"false", "true");
	// 设置按钮显示{新增、保存、刷新、删除}
	maingrid.grid.settoolbar(false, false, true, false);
	// 设置是否可编辑
	maingrid.grid.seteditModel(false);
	currentgrid = maingrid.grid;
	recycle_task_item = currentgrid.insertSelfBar("撤回", 60, "",
			"../../../comon/image/toolbar/recycle_task.gif");
	do_task_item = currentgrid.insertSelfBar("处理任务", 90, "",
			"../../../comon/image/toolbar/flw/do_task.gif");
	document.getElementById(d.id + "-grid-item").innerHTML = "<form class='layui-form' style='width:80px;margin-right:5px;'>"
			+ "<select onchange='selectFilter(this)' "
			+ " id='status_select' lay-filter='status_select1'>"
			+ "<option value='all'>全部</option>"
			+ "<option value='waiting'>待办</option>"
			+ "<option value='finished'>已办</option>"
			+ "<option value='tesReturned'>已回退</option>"
			+ "<option value='tesCanceled'>已取消</option>"
			+ "<option value='tesAborted'>已终止</option>" + "</select>"
			+ "</form>";// 嵌入自定义工具条内容
	layui.form.render("select");
	layui.form.on('select(status_select1)', function(data){
	  selectFilter(data.elem);
	});
	var init_filter = "";
	var opation = tlv8.RequestURLParam.getParam("opation");
	if (opation == "waitting") {
		document.getElementById("status_select").value = "waiting";
		init_filter = "SSTATUSID='tesReady' and SEPERSONID='" + currentPersonID
				+ "'";
	} else if (opation == "finished") {
		document.getElementById("status_select").value = "finished";
		init_filter = "SSTATUSID='tesFinished' and SEPERSONID='"
				+ currentPersonID + "'";
	}
	currentgrid.setStaticFilter(init_filter);
	currentgrid.refreshData();
}

/*
 * 下拉过滤 @param {Object} obj
 */
function selectFilter(obj) {
	var status = obj.value;
	var currentPersonID = tlv8.Context.getCurrentPersonID();
	// alert(status);
	var state_filter_val = "SSTATUSID='" + status + "'";
	if (status == "all") {
		state_filter_val = "";
	} else if (status == "waiting") {
		state_filter_val = "SSTATUSID='tesReady' and SEPERSONID='"
				+ currentPersonID + "'";
	} else if (status == "finished") {
		state_filter_val = "SSTATUSID='tesFinished' and SEPERSONID='"
				+ currentPersonID + "'";
		// and exists(select 1 from sa_task t where "
		// + " t.sstatusid = 'tesExecuting' and t.sid = sa_task.SFLOWID)";
	}
	currentgrid.setStaticFilter(state_filter_val);
	currentgrid.refreshData();
}
/*
 * 选择grid行 @param {Object} event
 */
function gridSelected(event) {
	var SSTATUSNAME = event.getValueByName("SSTATUSNAME", event.CurrentRowId);
	var SCPERSONID = event.getValueByName("SCPERSONID", event.CurrentRowId);
	var SEPERSONID = event.getValueByName("SEPERSONID", event.CurrentRowId);
	var currentPersonID = tlv8.Context.getCurrentPersonID();
	if (SSTATUSNAME == "尚未处理") {
		/*
		 * if (SCPERSONID == currentPersonID) {
		 * document.getElementById(recycle_task_item).src =
		 * "../../../comon/image/toolbar/recycle_task.gif";
		 * document.getElementById(recycle_task_item).disabled = false;
		 * document.getElementById(recycle_task_item).onclick = function() {
		 * recycle_task(event.CurrentRowId); }; } else
		 */{
			document.getElementById(recycle_task_item).src = "../../../comon/image/toolbar/un_recycle_task.gif";
			document.getElementById(recycle_task_item).disabled = true;
			document.getElementById(recycle_task_item).onclick = "";
		}
		if (SEPERSONID == currentPersonID) {
			document.getElementById(do_task_item).src = "../../../comon/image/toolbar/flw/do_task.gif";
			document.getElementById(do_task_item).onclick = function() {
				do_task(event.CurrentRowId);
			};
		} else {
			document.getElementById(do_task_item).src = "../../../comon/image/toolbar/flw/un_do_task.gif";
			document.getElementById(do_task_item).onclick = "";
		}
	} else {
		if (SEPERSONID == currentPersonID) {
			document.getElementById(recycle_task_item).src = "../../../comon/image/toolbar/recycle_task.gif";
			document.getElementById(recycle_task_item).disabled = false;
			document.getElementById(recycle_task_item).onclick = function() {
				recycle_task(event.CurrentRowId);
			};
		} else {
			document.getElementById(recycle_task_item).src = "../../../comon/image/toolbar/un_recycle_task.gif";
			document.getElementById(recycle_task_item).disabled = true;
			document.getElementById(recycle_task_item).onclick = "";
		}
		document.getElementById(do_task_item).src = "../../../comon/image/toolbar/flw/un_do_task.gif";
		document.getElementById(do_task_item).onclick = "";
	}
}
/*
 * 撤回任务 @param {Object} rowid
 */
function recycle_task(rowid) {
	var param = new tlv8.RequestParam();
	param.set("taskID", rowid);
	tlv8.XMLHttpRequest("RecycleTaskAction", param, "POST", true,
			function(r) {
				if (r.data.flag == "false") {
					$.messager.alert('错误',r.data.message,'error');    
				} else {
					currentgrid.refreshData(currentgrid.billfilter);
				}
			});
}
/*
 * 处理任务 @param {Object} rowid
 */
function do_task(rowid) {
	// alert(rowid);
	tlv8.task.openTask(rowid);
}

// 双击grid查看详细
function griddbclick(event) {
	var taskID = event.CurrentRowId;
	var flowID = event.getValueByName("SFLOWID", event.CurrentRowId);
	var scURL = event.getValueByName("SCURL", event.CurrentRowId);
	var sURL = event.getValueByName("SEURL", event.CurrentRowId);
	var sData1 = event.getValueByName("sData1", event.CurrentRowId);
	var SEPERSONID = event.getValueByName("SEPERSONID", event.CurrentRowId);
	var SSTATUSID = event.getValueByName("SSTATUSID", event.CurrentRowId);
	var name = event.getValueByName("SNAME", event.CurrentRowId);
	if (SSTATUSID != "tesReady") {
		sURL = event.getValueByName("SEURL", event.CurrentRowId);
		sURL += "?flowID=" + flowID + "&taskID=" + taskID + "&sData1=" + sData1
				+ "&task=" + taskID + "&activity-pattern=detail";
		tlv8.portal.openWindow(name, sURL);
	} else {
		tlv8.task.openTask(taskID);
	}
}