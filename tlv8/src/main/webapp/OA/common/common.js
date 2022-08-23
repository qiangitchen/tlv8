/**
 * 设置当前用户信息
 */
function setCreatorInfo() {
	var deptid = tlv8.Context.getCurrentDeptID() == "" ? tlv8.Context
			.getCurrentOgnID() : tlv8.Context.getCurrentDeptID();
	var deptname = tlv8.Context.getCurrentDeptName() == "" ? tlv8.Context
			.getCurrentOgnName()
			: tlv8.Context.getCurrentDeptName();
	$("#FCREATEOGNID").val(tlv8.Context.getCurrentOgnID());
	$("#FCREATEOGNNAME").val(tlv8.Context.getCurrentOgnName());
	$("#FCREATEDEPTID").val(deptid);
	$("#FCREATEDEPTNAME").val(deptname);
	$("#FCREATEPOSID").val(tlv8.Context.getCurrentPostID());
	$("#FCREATEPOSNAME").val(tlv8.Context.getCurrentPostName());
	$("#FCREATEPERSONID").val(tlv8.Context.getCurrentPersonID());
	$("#FCREATEPERSONNAME").val(tlv8.Context.getCurrentPersonName());
	$("#FCREATEPERSONFID").val(tlv8.Context.getCurrentPersonFID());
	$("#FCREATEPERSONFNAME").val(tlv8.Context.getCurrentPersonFName());
	$("#FCREATETIME").val(tlv8.System.Date.sysDateTime());
}

/**
 * 
 * @param rowid
 * @param divID
 */
function initOpinionCom(rowid, divID) {
	var parm = new tlv8.RequestParam();
	parm.set("sData1", rowid);
	var re = tlv8.XMLHttpRequest("UpdBrowseAction", parm);
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
	var news = tlv8.sqlQueryActionforJson("app", sqlStr);
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
	var date = tlv8.System.Date.strToDate(tlv8.System.Date.sysDate());
	var year = date.getFullYear();
	var month = date.getMonth() + 1;
	return year + "/" + month;
}

function openSelectPersonDalog(callback, height, width) {
	if (height == null || height == "")
		height = 450;
	if (width == null || width == "")
		width = 600;
	tlv8.portal.dailog.openDailog("选择人员",
			"/comon/SelectDialogPsn/SelectChPsm.html?temp="
					+ new Date().getUTCMilliseconds(), width, height, callback);
}

function showSelectPersonInfo(rowid, isRead) {
	if (rowid == null || rowid == "")
		return;
	if (isRead == null || isRead == "")
		isRead = false;
	var personListtxt = "";
	var parm = new tlv8.RequestParam();
	parm.set("fid", rowid);
	var result = tlv8.XMLHttpRequest("getNoticePersonAction?temp="
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
	var parm = new tlv8.RequestParam();
	parm.set("rowid", rowid);
	parm.set("isnew", isnew);
	tlv8.XMLHttpRequest("updateNoticeBrowseAction", parm);
}