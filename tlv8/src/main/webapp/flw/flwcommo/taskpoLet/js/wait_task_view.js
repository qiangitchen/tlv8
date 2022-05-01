/*
 * 打开任务列表（更多）
 * @param {Object} obj
 */
function goToMyTaskList(obj) {
	var url = "/SA/task/taskCenter/mainActivity.html?opation=waitting";
	tlv8.portal.openWindow("任务列表", url);
}

/*
 * 加载代办任务
 */
var cuintervalID, rCount = 0, remindCount = 0;
function loadMyTask() {
	var personID = tlv8.Context.getCurrentPersonID();
	var param = new tlv8.RequestParam();
	param.set("psmId", personID);
	param.set("temp", new UUID().toString());
	remindCount = 0;
	tlv8.XMLHttpRequest("GetTaskListAction", param, "POST", true,
			function(r) {
				document.getElementById("taskListView").innerHTML = "";
				if (r.data.flag == "false") {
					return;
				} else {
					try {
						var cells = eval("(" + r.data.data + ")");
						var currentDept = tlv8.Context
								.getCurrentDeptName();
						var currentPsm = tlv8.Context
								.getCurrentPersonName();
						createList("taskListView", cells);
					} catch (e) {
					}
				}
			});
	rCount++;
	if (rCount > 60) {
		clearInterval(cuintervalID);
	}
}

function createList(comp, cells) {
	var dataTable = "<table style='width:100%;border-collapse: collapse;table-layout: fixed; cursor:normal;'>";
	for (var n = 0; n < cells.length; n++) {
		if (n < 8) {
			dataTable += "<tr id='"
					+ cells[n].SID
					+ "' style='border-bottom:1px dashed #eee; line-height:25px;'><td title='"
					+ cells[n].SNAME
					+ "'><a >"
					+ "<img src='"+cpath+"/flw/flwcommo/taskpoLet/image/symbol.png'/>"
					+ "<a href='javascript:void(0);' onClick='tlv8.task.openTask(\""
					+ cells[n].SID + "\")'>" + " " + cells[n].SNAME + (cells[n].SLIMITTIME!=""?"<font color='red'>【办理截止时间：" + cells[n].SLIMITTIME.substring(0, 10) +"】</font>":"")
					+ "</a></td><td title='" + cells[n].SCDEPTNAME
					+ "' width='120px'>" + cells[n].SCDEPTNAME
					+ "</td><td title='" + cells[n].SCPERSONNAME
					+ "' width='70px' align='center'>" + cells[n].SCPERSONNAME
					+ "</td><td align='left' title='"
					+ cells[n].SCREATETIME.substring(0, 19)
					+ "' width='140px'>"
					+ cells[n].SCREATETIME.substring(0, 19) + "</td><tr>";
		}
		if (cells[n].SLOCK == "" || cells[n].SLOCK == "null") {
			remindCount++;
		}
	}
	dataTable += "<tr><td colspan='4'/></tr></table>";
	document.getElementById(comp).innerHTML = dataTable;
}

/*
 * 加载提醒代办任务
 */
var p, cuintervalRedID, rCountR = 0;
function loadMyRemindTask() {
	if (remindCount > 0) {
		openRemindDialog();
		rCountR++;
		if (rCountR > 60) {
			clearInterval(cuintervalRedID);
		}
	}
}

function initPageL() {
	rCount = 0;
	loadMyTask();
	cuintervalID = setInterval(loadMyTask, 5 * 60 * 1000);// 5分钟刷新一次任务
	if (parent.isonelog) {
		setTimeout(loadMyRemindTask, 1000);
		parent.isonelog = false;
	}
	rCountR = 0;
	cuintervalRedID = setInterval(loadMyRemindTask, 5 * 60 * 1000);// 5分钟刷新一次任务
}

function openRemindDialog() {
	var w = "600px";
	var h = "300px";
	var l = (screen.availWidth - 600) / 2;
	var t = (screen.availHeight - 300) / 2;
	var options = "toolbar=no,location=no,directions=no,status=no,menubar=no,srcollbars=no,revisable=no,resizable=no";
	options += ",width=" + w + ",height=" + h + ",left=" + l + ",top=" + t;
	var url = cpath+"/system/task_remind/task_remind.jsp?temp="
			+ new UUID().toString();
	if (!p || p == null) {
		p = window.open(url, "", options);
	}
}

function reMindTaskOpen(taskID) {
	tlv8.task.openTask(taskID);
}