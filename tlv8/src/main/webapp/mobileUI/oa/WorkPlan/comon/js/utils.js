/**
 * 获取审核人列表信息
 * 
 * @param rowid
 */
function viewPersonlist(rowid, candel) {
	var html = '<ul data-role="listview" class="ui-listview mui-table-view">';
	var parm = new tlv8.RequestParam();
	parm.set("fid", rowid);
	var result = tlv8.XMLHttpRequest("selpersonAction?temp="
			+ new Date().getMilliseconds(), parm);
	if (result.data.flag == "true") {
		var data = result.data.data;
		if (typeof (data) == "string") {
			data = window.eval("(" + data + ")");
		}
		for (var i = 0; i < data.length; i++) {
			var opinion = data[i].FOPINION == "" ? "未录入意见" : data[i].FOPINION;
			html += '<li class="ui-li ui-li-static ui-btn-up-c ui-first-child" rel="list" >';
			html += '<p class="ui-li-desc" ><strong  style="font-size:14px;">审&nbsp;&nbsp;核&nbsp;&nbsp;人：<label class="ui-link">('
					+ data[i].FPERSONNAME + ')</label></strong>';
			if(candel){
				html += '&nbsp;&nbsp;<a href="javascript:delPerson(\''+data[i].FID+'\')">删除</a>';
			}
			html += '</p><p class="ui-li-desc" ><strong  style="font-size:14px;">审核意见：<label class="ui-link">('
					+ opinion + ')</label></strong></p><p class="ui-li-desc">';
			html += '<strong style="font-size:14px;">是否查阅：<label class="ui-link">('
					+ data[i].FBROWSE
					+ ')</label></strong></p><p class="ui-li-desc">';
			html += '<strong style="font-size:14px;">查阅时间：<label class="ui-link">('
					+ data[i].FREADDATE + ')</label></strong></p>';
			html += '</li>';
		}
		$("#lookWorkPlanPerson").html(html);
	}
}

/**
 * 跟新查看状态
 * 
 * @param rowid
 */
function refreshBrowse(rowid) {
	var param = new tlv8.RequestParam();
	param.set("rowid", rowid);
	tlv8.XMLHttpRequest("UpdateWorkPlanBrowseStateAction", param, "post");
}

/**
 * 获取工作计划详细
 * 
 * @param div
 *            显示结果的div id
 * @param rowid
 *            主数据id
 * @param querytype
 *            查询类型：this：本周;temp：临时工作;last：上周未完成，同时需要，主表开始时间和主表创建人id
 * @param vdate
 *            主表开始时间
 * @param creatorid
 *            主表创建人id
 */
function getDetailInfo(div, rowid, querytype, vdate, creatorid) {
	var param = new tlv8.RequestParam();
	param.set("rowid", rowid);
	param.set("vdate", vdate);
	param.set("querytype", querytype);
	param.set("creatorid", creatorid);
	var re = tlv8.XMLHttpRequest("getWeekWorkPlanDetailAction", param,
			"post");
	if (re.data.flag == "true") {
		var data = re.data.data;
		if (typeof data == "string") {
			data = window.eval("(" + data + ")");
		}
		if (data.length > 0) {
			$("#title_val").html("(" + data.length + ")");
			$("#" + div).html(createDetailList(data, querytype));
		} else {
			$("#" + div)
					.html(
							"<div style='color:red;width:100%;padding:10px;text-align:center;'>未录入或没有满足条件的工作计划安排!</div>");
		}
	} else {
		alert("获取计划详细信息失败");
	}
}

/**
 * 创建工作计划明细列表
 * 
 * @param data
 * @returns {String}
 */
function createDetailList(data, type) {
	if (typeof data == "string") {
		data = window.eval("(" + data + ")");
	}
	var showType = "";
	if (type == "this") {
		showType = "本周工作内容安排";
	} else if (type == "temp") {
		showType = "临时工作安排";
	} else if (type == "last") {
		showType = "上周未完成工作";
	} else {

	}
	var html = '<ul class="mui-table-view">';
	for (var i = 0; i < data.length; i++) {
		if (data[i].TITLE || data[i].TITLE != "") {
			html += '<li class="mui-table-view-divider" dataid="'+data[i].ID+'"><label style="font-size: 17px;">'
					+ showType + " " + (i + 1) + '</label></li>';
			html += '<li class="mui-input-group" dataid="'+data[i].ID+'" style="padding: 10px;line-height: 25px;"><table><tr><td width="100px">'
					+ '<label style="font-weight: bold;">工作内容:</label>'
					+ '</td><td>'
					+ '<label class="ui-lable-info">'
					+ data[i].CONTENT + '</label>' + '</td></tr>';
			html += '<tr><td width="100px">'
					+ '<label style="font-weight: bold;">协作部门:</label>'
					+ '</td><td>' + '<label class="ui-lable-info">'
					+ data[i].PERSONS + '</label>' + '</td></tr>';
			html += '<tr><td width="100px">'
					+ '<label style="font-weight: bold;">完成预估:</label>'
					+ '</td><td>' + '<label class="ui-lable-info">'
					+ data[i].COMEV + '</label>' + '</td></tr>';
			html += '<tr><td width="100px">'
					+ '<label style="font-weight: bold;">完成情况:</label>'
					+ '</td><td>' + '<label class="ui-lable-info">'
					+ data[i].COMCON + '</label>' + '</td></tr>';
			html += '<tr><td width="100px">'
					+ '<label style="font-weight: bold;">未完成原因:</label>'
					+ '</td><td>' + '<label class="ui-lable-info">'
					+ data[i].UNCOM + '</label>' + '</td></tr></table></li>';
		}
	}
	html += '</ul>';
	return html;
}

/**
 * 创建工作计划显示列表
 * 
 * @param data
 * @param mrbtn
 *            是否创建更多按钮
 * @returns {String}
 */
function createWeekMainPlanList(data, mrbtn) {
	if (typeof data == "string") {
		data = window.eval("(" + data + ")");
	}
	var html = '';
	var length = data.length;
	for (var i = 0; i < length; i++) {
		var title = "";
		if (data[i].FBROWSE == "否") {
			title = "(未查看) ";
		}
		title += data[i].STARTTIME + " 至 " + data[i].FINISHTIME + " -- "
				+ data[i].PSNNAME;
		html += '<li class="mui-table-view-cell" id="' + data[i].ID
				+ '" rel="list" >';
		html += '<div class="mui-table">';
		html += '<div class="mui-table-cell mui-col-xs-12">';
		html += '<h5 class="mui-ellipsis">';
		html += '<a style="text-decoration: none;">标题：' + title + '</a></h5>';
		html += '<div class="mui-table-cell mui-col-xs-6">';
		html += '<p style="color:#666;"><strong style="font-size:14px;">发布人：'
				+ data[i].PSNNAME + '</strong>';
		html += '</p></div>';
		html += '<div class="mui-table-cell mui-col-xs-6">';
		html += '<p class="mui-h6 mui-ellipsis">发布时间：<strong>' + data[i].TIME
				+ '</strong></p>';
		html += '</div>';
		html += '</div>';
		html += '</div></li>';
	}
	if (mrbtn && length >= 10) {
		html += '<li class="mui-table-view-cell" style="align:center;" id="moreBtn" onclick="loadMore()">';
		html += '<p><a class="ui-link" style="margin-left:40%;text-decoration: none;" href="#">加载更多...</a></p></li>';
		html = '<li class="ui-btn-up-c ui-first-child" ><h3 class="ui-li-heading" style="margin-left:10px;">第 '
				+ (offerset + 1) + ' - ' + (mr * 10) + ' 条信息</h3></li>' + html;
	} else {
		// if (length > 1) {
		// html = '<li class="ui-btn-up-c ui-first-child" ><h3
		// class="ui-li-heading" style="margin-left:10px;">第 '
		// + (offerset + 1)
		// + ' - '
		// + (((mr - 1) * 10) + length)
		// + ' 条信息</h3></li>' + html;
		// } else {
		// html = '<li class="ui-btn-up-c ui-first-child" ><h3
		// class="ui-li-heading" style="margin-left:10px;">第 '
		// + (offerset + 1) + ' 条信息</h3></li>' + html;
		// }
	}
	if (length < 10) {
		$("#moreBtn").hide();
	}
	return html;
}
/**
 * 创建工作计划显示列表
 * 
 * @param data
 * @param mrbtn
 *            是否创建更多按钮
 * @returns {String}
 */
function createMonthMainPlanList(data, mrbtn) {
	if (typeof data == "string") {
		data = window.eval("(" + data + ")");
	}
	var html = '';
	var length = data.length;
	for (var i = 0; i < length; i++) {
		var title = "";
		if (data[i].FBROWSE == "否") {
			title = "(未查看) ";
		}
		title += data[i].TIME + " 月工作计划 -- " + data[i].PSNNAME;
		html += '<li class="mui-table-view-cell" id="' + data[i].ID
				+ '" rel="list" >';
		html += '<div class="mui-table">';
		html += '<div class="mui-table-cell mui-col-xs-12">';
		html += '<h5 class="mui-ellipsis">';
		html += '<a style="text-decoration: none;">标题：' + title + '</a></h5>';
		html += '<div class="mui-table-cell mui-col-xs-6">';
		html += '<p style="color:#666;"><strong style="font-size:14px;">发布人：'
				+ data[i].PSNNAME + '</strong>';
		html += '</p></div>';
		html += '<div class="mui-table-cell mui-col-xs-6">';
		html += '<p class="mui-h6 mui-ellipsis">发布时间：<strong>' + data[i].TIME
				+ '</strong></p>';
		html += '</div>';
		html += '</div>';
		html += '</div></li>';
	}
	if (mrbtn && length >= 10) {
		html += '<li class="mui-table-view-cell" style="align:center;" id="moreBtn" onclick="loadMore()">';
		html += '<p><a class="ui-link" style="margin-left:40%;text-decoration: none;" href="#">加载更多...</a></p></li>';
		html = '<li class="ui-btn-up-c ui-first-child" ><h3 class="ui-li-heading" style="margin-left:10px;">第 '
				+ (offerset + 1) + ' - ' + (mr * 10) + ' 条信息</h3></li>' + html;
	} else {
		// if (length > 1) {
		// html = '<li class="ui-btn-up-c ui-first-child" ><h3
		// class="ui-li-heading" style="margin-left:10px;">第 '
		// + (offerset + 1)
		// + ' - '
		// + (((mr - 1) * 10) + length)
		// + ' 条信息</h3></li>' + html;
		// } else {
		// html = '<li class="ui-btn-up-c ui-first-child" ><h3
		// class="ui-li-heading" style="margin-left:10px;">第 '
		// + (offerset + 1) + ' 条信息</h3></li>' + html;
		// }
	}
	if (length < 10) {
		$("#moreBtn").hide();
	}
	return html;
}

// 新增人员
function addPerson() {
	if (dataSave()) {
		justep.dialog.openFullScreenDialog("选择人员", {
			url : cpath+"/mobileUI/oa/common/dialog/psnSelect.html",
			callback : "initPersonlist"
		});
	}
}

// 插入人员
function initPersonlist(data) {
	curOgnID = justep.Context.getCurrentOgnID();
	curOrgID = justep.Context.getCurrentOrgID();
	curPersonID = justep.Context.getCurrentPersonID();
	curOgnName = justep.Context.getCurrentOgnName();
	curOrgName = justep.Context.getCurrentOrgName();
	curPersongName = justep.Context.getCurrentPersonName();
	var param = new tlv8.RequestParam();
	param.set("ognId", curOgnID);
	param.set("ognName", curOgnName);
	param.set("orgId", curOrgID);
	param.set("orgName", curOrgName);
	param.set("createId", curPersonID);
	param.set("createName", curPersongName);
	param.set("instanceId", rowid);
	param.set("personId", data.id);
	param.set("personName", data.name);
	var result = tlv8.XMLHttpRequest("addPersonAction?temp="
			+ new Date().getMilliseconds(), param);
	if (result.data.flag == "true") {
		viewPersonlist(rowid, true);
	} else {
		alert("新增人员没有成功!");
	}
}

// 删除人员
function delPerson(fid) {
	if (confirm("你确定要删除当前选择人员吗?")) {
		var fid1 = encodeURIComponent(encodeURIComponent(fid));
		var param1 = new tlv8.RequestParam();
		param1.set("fid", fid1);
		var result = tlv8.XMLHttpRequest("delPersonAction?temp="
				+ new Date().getMilliseconds(), param1);
		if (result.data.flag == "true") {
			viewPersonlist(rowid,true);
		} else {
			alert("删除人员没有成功！");
		}
	}
}

// 得到每周的第一天(周一)
function getFirstDateOfWeek(theDate) {
	var firstDateOfWeek;
	var day = theDate.getDay();
	theDate.setDate(theDate.getDate() - day + 1); //	 
	firstDateOfWeek = theDate.format("yyyy-MM-dd");// .toLocaleString();
	// firstDateOfWeek = firstDateOfWeek.substring(0,11);
	return firstDateOfWeek;
}

// 得到每周的最后一天(周六)
function getLastDateOfWeek(theDate) {
	var lastDateOfWeek;
	theDate.setDate(theDate.getDate() + 6 - theDate.getDay()); //	 
	lastDateOfWeek = theDate.format("yyyy-MM-dd");// .toLocaleString();
	// lastDateOfWeek = lastDateOfWeek.substring(0,11);
	return lastDateOfWeek;
}

// 得到前一星期第一天(周一)
function getFirstDateOfWeek2(theDate) {
	var firstDateOfWeek;
	theDate.setDate(theDate.getDate() - theDate.getDay() + 1 - 7); //
	firstDateOfWeek = theDate.format("yyyy-MM-dd");// toLocaleString();
	return firstDateOfWeek;
}

// 得到前一星期最后一天(周六)
function getLastDateOfWeek2(theDate) {
	var lastDateOfWeek;
	theDate.setDate(theDate.getDate() + 6 - theDate.getDay()); //	
	lastDateOfWeek = theDate.format("yyyy-MM-dd");// toLocaleString();
	return lastDateOfWeek;
}

function getLastMonth(date) {
	if (typeof (date) == "string") {
		date = tlv8.System.Date.strToDate(date);
	}
	var month = date.getMonth();
	date.setMonth(month - 1);
	date = date.format("yyyy-MM");
	return date;
}

function checkHavePersons(rowid) {
	var param1 = new tlv8.RequestParam();
	param1.set("fid", rowid);
	var result = tlv8.XMLHttpRequest("checkPersonAction?temp="
			+ new Date().getMilliseconds(), param1);
	if (result.data.flag == "true") {
		return result.data.data;
	}
	return 0;
}