/**
 * 设置当前用户信息
 */
function setCreatorInfo() {
	var deptid = justep.yn.Context.getCurrentDeptID() == "" ? justep.yn.Context
			.getCurrentOgnID() : justep.yn.Context.getCurrentDeptID();
	var deptname = justep.yn.Context.getCurrentDeptName() == "" ? justep.yn.Context
			.getCurrentOgnName()
			: justep.yn.Context.getCurrentDeptName();
	$("#FCREATEOGNID").val(justep.yn.Context.getCurrentOgnID());
	$("#FCREATEOGNNAME").val(justep.yn.Context.getCurrentOgnName());
	$("#FCREATEDEPTID").val(deptid);
	$("#FCREATEDEPTNAME").val(deptname);
	$("#FCREATEPOSID").val(justep.yn.Context.getCurrentPostID());
	$("#FCREATEPOSNAME").val(justep.yn.Context.getCurrentPostName());
	$("#FCREATEPERSONID").val(justep.yn.Context.getCurrentPersonID());
	$("#FCREATEPERSONNAME").val(justep.yn.Context.getCurrentPersonName());
	$("#FCREATEPERSONFID").val(justep.yn.Context.getCurrentPersonFID());
	$("#FCREATEPERSONFNAME").val(justep.yn.Context.getCurrentPersonFName());
	$("#FCREATETIME").val(justep.yn.System.Date.sysDateTime());
}

/**
 * 
 * @param rowid
 * @param divID
 */
function initOpinionCom(rowid, divID) {
	var parm = new justep.yn.RequestParam();
	parm.set("sData1", rowid);
	var re = justep.yn.XMLHttpRequest("UpdBrowseAction", parm);
}
/**
 * 去空格
 * 
 * @param v
 * @returns
 */
function strTrim(v) {
	v = v + "";// 暂时未找到原因
	return v.replace(/(^\s*)|(\s*$)/g, "");
}

// 检测是否能够取消发布
function canUnPush(fid) {
	var sqlStr = "select count(0) as COUNT from OA_NOTICE_PERSON WHERE FMAINID = '"
			+ fid + "' and fbrowse = '是'";
	var news = justep.yn.sqlQueryActionforJson("app", sqlStr);
	if (news.data.length > 0) {
		var co = news.data[0].COUNT;
		if (co > 0) {
			return false;
		} else {
			return true;
		}
	} else {
		return true;
	}
}

/**
 * 获取当前服务器的年月，主要用于处理文件上传分类
 * 
 * @returns {String} 2013/12
 */
function getCurentYearandMonth() {
	var date = justep.yn.System.Date.strToDate(justep.yn.System.Date.sysDate());
	var year = date.getFullYear();
	var month = date.getMonth() + 1;
	return year + "/" + month;
}

function openSelectPersonDalog(callback, height, width) {
	if (height == null || height == "")
		height = 450;
	if (width == null || width == "")
		width = 600;
	justep.yn.portal.dailog.openDailog("选择人员",
			"/comon/SelectDialogPsn/SelectChPsm.html?temp="
					+ new Date().getUTCMilliseconds(), width, height, callback);
}

function showSelectPersonInfo(rowid, isRead) {
	if (rowid == null || rowid == "")
		return;
	if (isRead == null || isRead == "")
		isRead = false;
	var personListtxt = "";
	var parm = new justep.yn.RequestParam();
	parm.set("fid", rowid);
	var result = justep.yn.XMLHttpRequest("getNoticePersonAction?temp="
			+ new Date().getMilliseconds(), parm);
	if (result.data.flag == "true") {
		var Data = result.data.data;
		if (typeof (Data) == "string") {
			Data = window.eval("(" + Data + ")");
		}
		if (Data.length > 0) {
			if (isRead) {
				personListtxt = "<table class=\"layui-table\" style=\"table-layout: fixed;cellSpacing:0;cellPadding:0; font-size:13px; text-align:left;\" ><tr style=\"height:22px;\">";
				personListtxt += "<th>姓名</th>";
				personListtxt += "<th style=\"width:140px;\">是否已查看</th>";
				personListtxt += "<th style=\"width:180px;\">查看时间</th>";
				personListtxt += "</tr>";
				for (var i = 0; i < Data.length; i++) {
					var name = Data[i].FPERSONNAME; // 人员名字
					var browse = Data[i].FBROWSE;// 是否查看
					var browsedate = Data[i].FREADDATE;// 查看时间
					personListtxt += "<tr>";
					personListtxt += "<td>" + name + "</td>";
					personListtxt += "<td>" + browse + "</td>";
					personListtxt += "<td>" + browsedate + "</td>";
					personListtxt += "</tr>";
				}
				personListtxt += "</table>";
				$("#personList").html(personListtxt);
			} else {
				personListtxt = "<table class=\"layui-table\" style=\"table-layout: fixed;cellSpacing:0;cellPadding:0; font-size:13px; text-align:left;\" ><tr style=\"height:22px;\">";
				personListtxt += "<th style=\"width:80px;\" ><span style=\"margin-left:10px;\">操作<span></th>";
				personListtxt += "<th>姓名</th>";
				personListtxt += "<th style=\"width:140px;\">是否已查看</th>";
				personListtxt += "<th style=\"width:180px;\">查看时间</th>";
				personListtxt += "</tr>";
				for (var i = 0; i < Data.length; i++) {
					var name = Data[i].FPERSONNAME; // 人员名字
					var fid = Data[i].FID;// 删除时的 id
					var browse = Data[i].FBROWSE;// 是否查看
					var browsedate = Data[i].FREADDATE;// 查看时间
					personListtxt += "<tr>";
					personListtxt += "<td><a href=\"javascript:void(0);\" onclick=\"delPerson('"
							+ fid + "')\">删除</a></td>";
					personListtxt += "<td>" + name + "</td>";
					personListtxt += "<td>" + browse + "</td>";
					personListtxt += "<td>" + browsedate + "</td>";
					personListtxt += "</tr>";
				}
				personListtxt += "</table>";
				$("#personList").html(personListtxt);
			}
		} else {
			$("#personList").html(personListtxt);
		}
	} else {
		$("#personList").html(personListtxt);
	}
}

/**
 * 
 * @param rowid
 * @param isnew
 */
function updateLookState(rowid, isnew) {
	if (isnew == null || isnew == "") {
		isnew = "true";
	}
	var parm = new justep.yn.RequestParam();
	parm.set("rowid", rowid);
	parm.set("isnew", isnew);
	justep.yn.XMLHttpRequest("updateNoticeBrowseAction", parm);
}