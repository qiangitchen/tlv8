var count = 8;
var pagenum = 1;
function initDocumentPage() {
	$(".s_t_p").bind("click", changeSelectedTab);
}

// 类型选择
function changeSelectedTab() {
	if (!$(this).is("b")) {
		$("b.s_t_p").replaceWith(
				"<a class=\"s_t_p\">" + $("b.s_t_p").html() + "</a>");
		$(this).replaceWith("<b class=\"s_t_p\">" + $(this).html() + "</b>");
	}
	$(".s_t_p").unbind("click");
	$(".s_t_p").bind("click", changeSelectedTab);
}

// 翻页
function changePagesel(obj) {
	pagenum = $(obj).attr("pg");
	doSearchbtn();
	$("strong.pageitem").replaceWith(
			"<a class=\"pageitem\" pg=\"" + $("strong.pageitem").attr("pg")
					+ "\" onclick=\"changePagesel(this)\">"
					+ $("strong.pageitem").html() + "</a>");
	$(obj).replaceWith(
			"<strong pg=\"" + $(obj).attr("pg") + "\" class=\"pageitem\">"
					+ $(obj).html() + "</strong>");
}

// 创建分页条
function createPagebars(pagecount) {
	var pagehm = "";
	for (var i = 1; i <= pagecount; i++) {
		if (i == pagenum) {
			pagehm += "<strong pg=\"" + i
					+ "\" class=\"pageitem\"><span class=\"pc\">" + i
					+ "</span></strong>";
		} else {
			pagehm += "<a class=\"pageitem\" pg=\"" + i
					+ "\" onclick=\"changePagesel(this)\"><span class=\"pc\">"
					+ i + "</span></a>";
		}
	}
	$("#page").html(pagehm);
}

function searchtextendpre(event) {
	// 输入框回车时自动查询
	if (event.keyCode == 13) {
		doSearchbtn();
	}
}

function doSearchbtn() {
	var stp = $("b.s_t_p").attr("id");
	switch (stp) {
	case "sallrecvFile":
		searchAllRecvFile();
		break;
	default:
		break;
	}
}

function searchAllRecvFile() {
	var param = new tlv8.RequestParam();
	param.set("userid", tlv8.Context.getCurrentPersonID());
	param.set("count", count);
	param.set("pagenum", pagenum);
	param.set("searchTitle", $("#kw").val());
	tlv8.XMLHttpRequest("getAllrecvFileInfoAction", param, "post", true,
			function(re) {
				var data = re.data.data;
				if (typeof data == "string") {
					data = window.eval("(" + data + ")");
				}
				if (data.OK == "OK") {
					pagenum = data.PAGBNUM;
					createPagebars(data.PAGBCOUNT);
					init_sfileli_data(data.Data);
				}
			});
}

function init_sfileli_data(data) {
	var contextHtml = "";
	if (data.length == 0) {
		contextHtml += "<div class='td_title'>当前记录为0条!</div>";
	} else {
		for (var i = 0; i < data.length; i++) {
			var attachcount = data[i].ATTACHCOUNT;
			var fid = data[i].FID;
			var fmaind = data[i].FMAINID;
			var fType = data[i].FTYPE;
			var title = data[i].FTITLE.replace(/[\n\r]/gi, "");
			var forname = data[i].FORMNAME;
			var frecvdate = tlv8.System.Date.strToDate(data[i].FRECVDATE)
					.format("yyyy-MM-dd");
			var fcreatername = data[i].FCREATERNAME;
			var templateid = data[i].TEMPLATEID;
			var tiaction = "";
			var srtype = "";
			var cont = parseInt(attachcount);
			if (fType != "send" && fType != "receive") {
				srtype = "外部来文";
				if(parseInt(tlv8.System.Date.strToDate(data[i].FRECVDATE)
						.format("yyyy"))<new Date().getFullYear()){
					tiaction = "editDocHist('" + fid + "','" + forname + "','"
					+ templateid + "')";
				}else{
					tiaction = "editDoc('" + fid + "','" + forname + "','"
						+ templateid + "')";
				}
			} else {
				srtype = "内部来文";
				if (cont > 1) {// 多个附件
					var type = "";
					if (fType == "send") {
						type = "发文";
					} else if (fType == "receive") {
						type = "收文";
					}
					tiaction = "openArchive('" + fmaind + "','" + type + "','"
							+ fid + "')";
				} else if (cont == 1) {// 一个附件
					tiaction = "auditWord('" + fmaind + "','" + fid + "')";
				}
			}
			contextHtml += "<div class=\"result c-container \" tpl=\"se_com_default\">";
			contextHtml += "<h3 class=\"t\">";
			var stitle = title.replaceAll($("#kw").val(), "<em>"
					+ $("#kw").val() + "</em>");
			contextHtml += "<a href=\"javascript:void(0);\" onclick=\""
					+ tiaction + "\">" + stitle + "</a>";
			contextHtml += "</h3>";
			contextHtml += "<div class=\"c-abstract\">";
			contextHtml += "<span class=\"newTimeFactor_before_abs m\">";
			contextHtml += "类型：" + srtype + ",接收时间：" + frecvdate + ",登记人:"
					+ fcreatername;
			contextHtml += "</span></div><div class=\"f13\">&nbsp;</div></div>";
		}
	}
	$("#sresulttex").html(contextHtml);
}

// 改变对内发文的状态
function changeState(fid) {
	var param = new tlv8.RequestParam();
	param.set("fid", fid);
	tlv8.XMLHttpRequest("updateInnerRecv", param, "post", true, function(
			re) {
		var data = re.data.data;
		if (typeof data == "string") {
			data = window.eval("(" + data + ")");
		}
		if (data.OK == "OK") {
			doSearchbtn();
		}
	});
}

function auditWord(fmainid, fid) {
	innerRecvFid = fid;
	var param = new tlv8.RequestParam();
	param.set("fid", fmainid);
	param.set("prms", "0");
	tlv8.XMLHttpRequest("getAttachJsonById", param, "post", true,
			function(re) {
				var data = re.data.data;
				if (typeof data == "string") {
					data = window.eval("(" + data + ")");
				}
				if (data.OK == "OK" && data.Data.length > 0) {
					var attachInfo = data.Data[0];
					openAttach(attachInfo.FDOCPATH, attachInfo.FFILEID, "",
							attachInfo.FSOURCEFILE);
					changeState(innerRecvFid);
				}
			});
}

function openArchive(id, type, fid) {
	var process = "/OA/docrs/process/receiveDoc/receiveDocProcess";
	var activity = "executeActivity";
	var url = "/OA/docrs/process/dialog/openArchiveActivity.html?process="
			+ process + "&activity=" + activity + "&rowID=" + id + "&type="
			+ type;
	tlv8.portal.openWindow("阅办文件", url);
	changeState(fid);
}
// 审阅文件
function editDoc(id, forname, templateid) {
	var process = "/OA/docrs/process/receiveDoc/receiveDocProcess";
	var activity = "executeActivity";
	var url = "/OA/docrs/process/receiveDoc/receiveDocDetail/" + forname
			+ ".html?process=" + process + "&activity=" + activity + "&rowID="
			+ id + "&tId=" + templateid + "&callerName="
			+ parent.justep.Portal.Tab.getCurrentId() + "&protype=wait";
	tlv8.portal.openWindow("阅办文件", url);
}

// 审阅文件(历史)
function editDocHist(id, forname, templateid) {
	var process = "/OA/docrs/process/receiveDoc/receiveDocProcess";
	var activity = "executeActivity";
	var url = "/OA/docrs/process/receiveDoc/receiveDocDetail/receiveDocDetail_h.html?process="
			+ process
			+ "&activity="
			+ activity
			+ "&rowID="
			+ id
			+ "&tId="
			+ templateid
			+ "&callerName="
			+ parent.justep.Portal.Tab.getCurrentId() + "&protype=wait";
	tlv8.portal.openWindow("阅办文件", url);
}