/**
 * 基础参数，主要用于数据翻页
 */
var limit = 10;
var current_page = 1;

/*
 * 列表对象
 */
createMailList = function(compent, json, mailType) {
	this.compent = (typeof (compent) == "string") ? document
			.getElementById(compent) : compent;
	this.date = new Date();
	this.data = (typeof (json) == "string") ? window.eval(json) : json;
	this.getImage = function(state) {
		if (state != '未查看' && state != '已保存') {
			return '<img src="image/yidu.gif" alt="已读邮件"/>';
		} else {
			return '<img src="image/weidu.gif" alt="未读邮件" />';
		}
	};
	this.checkRead = function(state) {
		if (state != '未查看' && state != '已保存') {
			return false;
		} else {
			return true;
		}
	};
	this.clear = function() {
		try {
			var tb = this.compent;
			var rowNum = tb.rows.length;
			for (var i = 0; i < rowNum; i++) {
				tb.deleteRow(0);
			}
		} catch (e) {
		}
	};
	this.init = function() {
		if (this.data != undefined) {
			var count = this.data.length;
			if (count > 0) {
				var html = "";
				for (var i = 0; i < count; i++) {
					var row = this.data[i];
					html += this.createWeek(row);
				}
				this.compent.innerHTML = html;
				this.setEvent();
			} else {
				this.compent.innerHTML = '<div class="nomail"><b>没有邮件</b></div>';
			}
		}
	};
	this.initSearch = function(value) {
		if (this.data != undefined) {
			var count = this.data.length;
			if (count > 0) {
				var html = "";
				for (var i = 0; i < count; i++) {
					var row = this.data[i];
					html += this.createSearchWeek(row, value);
				}
				this.compent.innerHTML = html;
				this.setEvent();
			} else {
				this.compent.innerHTML = '<div class="nomail"><b>未查找到主题与 "'
						+ value + '" 相关的邮件,请重新输入查询条件或返回收件箱。</b></div>';
			}
		}
	};
	this.initCollect = function() {
		if (this.data != undefined) {
			var count = this.data.length;
			if (count > 0) {
				var html = "";
				for (var i = 0; i < count; i++) {
					var row = this.data[i];
					html += this.createCollectWeek(row);
				}
				this.compent.innerHTML = html;
				this.setEvent();
			} else {
				this.compent.innerHTML = '<div class="nomail"><b>没有收藏的邮件</b></div>';
			}
		}
	};
	this.setEvent = function() {
		$(".selectCheckBox").bind('click', function(event) {
			if (this.checked) {
				checkedRow.put($(this).attr("row"), $(this).attr("mailType"));
			} else {
				checkedRow.remove($(this).attr("row"));
			}
		});
		$(".ShowdatalistRow,.ShowdatalistRowNoread").bind('mouseover',
				function(event) {
					this.style.background = "#eee";
				});
		$(".ShowdatalistRow,.ShowdatalistRowNoread").bind('mouseout',
				function(event) {
					this.style.background = "";
				});
		$(".ShowdatalistRow").bind('click', function(event) {
			var objEdit = event.srcElement ? event.srcElement : event.target;
			if (objEdit.tagName == "INPUT") {
				return;
			}
			var rowid = this.id;
			var mailTypeLook = $(this).attr("mailType");
			lookMailDetail(rowid, mailTypeLook);
			if (mailType == "收藏箱")
				gobacktype = "收藏箱";
		});
		$(".ShowdatalistRowNoread").bind('click', function(event) {
			var objEdit = event.srcElement ? event.srcElement : event.target;
			if (objEdit.tagName == "INPUT") {
				return;
			}
			var rowid = this.id;
			lookNoReadMailDetail(rowid, mailType);
		});
	};
	this.translateDate = function(date) {
		var c_date = tlv8.System.Date.strToDate(date);
		var c_f_date = c_date.format("yyyy/MM/dd");
		var this_f_date = this.date.format("yyyy/MM/dd");
		var re = "";
		if (this_f_date == c_f_date) {
			re = c_date.format("hh:mm:ss");
		} else {
			var year = c_date.getYear();
			var c_year = this.date.getYear();
			if (year == c_year) {
				re = c_date.format("MM月dd日");
			} else {
				re = c_f_date;
			}
		}
		return re;
	};
	this.createWeek = function(row) {
		var count = row.count;
		var html = '<div class="bd title"><a class="cur_default"><label><b>'
				+ row.week + '</b>（<span class="underline">' + count
				+ ' &nbsp;封</span>）</label><a/></div>';
		var html_show = '<div>';
		if (count > 0) {
			var data = this.translateJSON(row.data);
			for (var int = 0; int < data.length; int++) {
				var info = data[int];
				html_show += this.createRow(info);
			}
		}
		html_show += "</div>";
		return html + html_show;
	};
	this.createSearchWeek = function(row, value) {
		var count = row.count;
		var html = '<div class="title"><a class="cur_default"><label><b>'
				+ row.week
				+ '</b>（<span class="underline">'
				+ count
				+ ' &nbsp;封</span>）</label><a/></div><hr noshade style="border:1px solid #6fbce2;cursor:default;"/>';
		var html_show = '<div>';
		if (count > 0) {
			var data = this.translateJSON(row.data);
			for (var int = 0; int < data.length; int++) {
				var info = data[int];
				html_show += this.createSearchRow(info, value);
			}
		}
		html_show += "</div>";
		return html + html_show;
	};
	this.createCollectWeek = function(row) {
		var count = row.count;
		var html = '<div class="title"><a class="cur_default"><label><b>'
				+ row.week
				+ '</b>（<span class="underline">'
				+ count
				+ ' &nbsp;封</span>）</label><a/></div><hr noshade style="border:1px solid #6fbce2;cursor:default;"/>';
		var html_show = '<div>';
		if (count > 0) {
			var data = this.translateJSON(row.data);
			for (var int = 0; int < data.length; int++) {
				var info = data[int];
				html_show += this.createCollectRow(info);
			}
		}
		html_show += "</div>";
		return html + html_show;
	};
	this.translateJSON = function(datas) {
		datas = datas.toString().replaceAll('"', '\"');
		datas = datas.toString().replaceAll("{'", '{"');
		datas = datas.toString().replaceAll("':'", '":"');
		datas = datas.toString().replaceAll("','", '","');
		datas = datas.toString().replaceAll("'}", '"}');
		return eval("(" + datas + ")");
	};
	this.createRow = function(row) {
		var name = row.FSENDPERNAME == "" ? "(收件人未填写)" : row.FSENDPERNAME;
		var title = row.FEMAILNAME == "" ? "<span style=\"color:#2131a1\" >(无主题)</span>"
				: row.FEMAILNAME;
		var read = this.checkRead(row.FQUREY);
		var showClass = "ShowdatalistRow";
		if (read)
			showClass = "ShowdatalistRowNoread";
		var collect = row.FCOLLECT;
		if (collect == "1")
			title = "<span style=\"color:#2131a1\" >(已收藏) </span>" + title;
		var html = '<table cellspacing="0" class="M"><tr id="'
				+ row.FID
				+ '" class="'
				+ showClass
				+ '"  mailType="'
				+ mailType
				+ '" ><td style="width:25px;"><input type="checkbox" row="'
				+ row.FID
				+ '" mailType="'
				+ mailType
				+ '" class = "selectCheckBox" ></input></td><td style="width:30px;">'
				+ this.getImage(row.FQUREY)
				+ '</td><td style="width:8%;" class="showSenderTheme" title="'
				+ name
				+ '">'
				+ name
				+ '</td><td style="width:20px;"/><td class="showSenderTheme">'
				+ title
				+ '</td><td style="width:50px;"></td><td style="width:120px;" >'
				+ row.FSENDDEPT + '</td><td style="width:90px;" >'
				+ this.translateDate(row.FSENDTIME) + '</td></tr></table>';
		return html;
	};
	this.createSearchRow = function(row, value) {
		var name = row.FSENDPERNAME == "" ? "(收件人未填写)" : row.FSENDPERNAME;
		var title = row.FEMAILNAME.replace(value,
				'<span style="background:#ffff00" >' + value + '</span>');
		var read = this.checkRead(row.FQUREY);
		var showClass = "ShowdatalistRow";
		if (read)
			showClass = "ShowdatalistRowNoread";
		var collect = row.FCOLLECT;
		if (collect == "1")
			title = "<span style=\"color:#2131a1\" >(已收藏) </span>" + title;
		var html = '<table cellspacing="0" class="M"><tr id="'
				+ row.FID
				+ '" class="'
				+ showClass
				+ '" mailType="'
				+ row.MAILIN
				+ '" ><td style="width:25px;"><input type="checkbox" row="'
				+ row.FID
				+ '" mailType="'
				+ mailType
				+ '" class = "selectCheckBox" ></input></td><td style="width:30px;">'
				+ this.getImage(row.FQUREY)
				+ '</td><td style="width:8%;" class="showSenderTheme" title="'
				+ name + '">' + name
				+ '</td><td style="width:20px;"/><td style="width:80px" >'
				+ row.MAILIN + '</td><td  class="showSenderTheme" title="'
				+ row.FEMAILNAME + '" >' + title
				+ '</td><td style="width:50px;"/><td style="width:120px;" >'
				+ row.FSENDDEPT + '</td><td style="width:90px;" >'
				+ this.translateDate(row.FSENDTIME) + '</td></tr></table>';
		return html;
	};
	this.createCollectRow = function(row) {
		var name = row.FSENDPERNAME == "" ? "(收件人未填写)" : row.FSENDPERNAME;
		var title = row.FEMAILNAME;
		var read = this.checkRead(row.FQUREY);
		var showClass = "ShowdatalistRow";
		if (read)
			showClass = "ShowdatalistRowNoread";
		var html = '<table cellspacing="0" class="M"><tr id="'
				+ row.FID
				+ '" class="'
				+ showClass
				+ '"  mailType="'
				+ row.MAILIN
				+ '" ><td style="width:25px;"><input type="checkbox" row="'
				+ row.FID
				+ '" mailType="'
				+ mailType
				+ '" class = "selectCheckBox" ></input></td><td style="width:30px;">'
				+ this.getImage(row.FQUREY)
				+ '</td><td style="width:8%;" class="showSenderTheme" title="'
				+ name + '">' + name
				+ '</td><td style="width:20px;"/><td style="width:80px" >'
				+ row.MAILIN + '</td><td  class="showSenderTheme" title="'
				+ row.FEMAILNAME + '" >' + title
				+ '</td><td style="width:50px;"/><td style="width:120px;" >'
				+ row.FSENDDEPT + '</td><td style="width:90px;" >'
				+ this.translateDate(row.FSENDTIME) + '</td></tr></table>';
		return html;
	};
	this.afterCreate = function(count) {
		$("#mail_title")
				.html(
						"<span style=\"color:#00334d;font-weight:bold;font-size:17px;margin-left:10px;\">"
								+ mailType + "</span> (共 " + count + " 封邮件)");
	};
};

/**
 * 加载收信箱
 */
function loadRecvMail() {
	var param = new tlv8.RequestParam();
	param.set("limit", limit);
	var re = tlv8.XMLHttpRequest("LoadRecvMailAction", param);
	if (re.data.flag == "true") {
		var list = new createMailList("receiveListData", re.data.data, "收件箱");
		list.init();
		var count = re.count == null ? "0" : re.count;
		list.afterCreate(count);
		initfirstPage("recv_page_info", count, "收件箱");
		checkedRow = new Map();
		$("#writeMailView").hide();
		$("#lookMailView").hide();
		$("#searchList").hide();
		$(".funcListTR").each(function(ev) {
			if ($(this).attr("rel") != "receiveList") {
				this.style.background = "";
				$(this).attr("bk", "");
				$("#" + $(this).attr("rel")).hide();
			} else {
				this.style.background = "#eee";
				$(this).attr("bk", "#eee");
				$("#" + $(this).attr("rel")).show();
			}
		});
		$("#ipt_search").val("");
		$("#div_search").show();
		current_page = 1;
	} else {
		tlv8.showMessage("收信箱加载出错,请重试.");
	}
}
/**
 * 收信箱下一页
 */
function nextPage(type) {
	if (type == "收件箱") {
		loadMorePage("LoadRecvMailAction", "receiveListData", type,
				"recv_page_info", "next");
	} else if (type == "发件箱") {
		loadMorePage("LoadSendMailAction", "sendListData", type,
				"send_page_info", "next");
	} else if (type == "草稿箱") {
		loadMorePage("LoadTempletMailAction", "templetListData", type,
				"temp_page_info", "next");
	} else if (type == "收藏箱") {
		loadMorePage("LoadCollectMailAction", "CollectListData", type,
				"collect_page_info", "next");
	} else {
		loadSearchMorePage("next");
	}
}
/**
 * 上一页
 */
function lastPage(type) {
	if (type == "收件箱") {
		loadMorePage("LoadRecvMailAction", "receiveListData", type,
				"recv_page_info", "last");
	} else if (type == "发件箱") {
		loadMorePage("LoadSendMailAction", "sendListData", type,
				"send_page_info", "last");
	} else if (type == "草稿箱") {
		loadMorePage("LoadTempletMailAction", "templetListData", type,
				"temp_page_info", "last");
	} else if (type == "收藏箱") {
		loadMorePage("LoadCollectMailAction", "CollectListData", type,
				"collect_page_info", "last");
	} else {
		loadSearchMorePage("last");
	}
}
/**
 * 加载发信箱
 */
function loadSendMail() {
	var param = new tlv8.RequestParam();
	param.set("limit", limit);
	var re = tlv8.XMLHttpRequest("LoadSendMailAction", param, "post");
	if (re.data.flag == "true") {
		var list = new createMailList("sendListData", re.data.data, "发件箱");
		list.init();
		var count = re.count == null ? "0" : re.count;
		list.afterCreate(count);
		initfirstPage("send_page_info", count, "发件箱");
		$("#writeMailView").hide();
		$("#lookMailView").hide();
		$(".funcListTR").each(function(ev) {
			if ($(this).attr("rel") != "sendList") {
				this.style.background = "";
				$(this).attr("bk", "");
				$("#" + $(this).attr("rel")).hide();
			} else {
				this.style.background = "#eee";
				$(this).attr("bk", "#eee");
				$("#" + $(this).attr("rel")).show();
			}
		});
		$("#div_search").show();
		checkedRow = new Map();
		current_page = 1;
	}
}
/**
 * 加载收藏夹
 */
function loadCollectMail() {
	var param = new tlv8.RequestParam();
	param.set("limit", limit);
	var re = tlv8.XMLHttpRequest("LoadCollectMailAction", param, "post");
	if (re.data.flag == "true") {
		var list = new createMailList("CollectListData", re.data.data, "收藏箱");
		list.initCollect();
		var count = re.count == null ? "0" : re.count;
		list.afterCreate(count);
		initfirstPage("collect_page_info", count, "收藏箱");
		$("#writeMailView").hide();
		$("#lookMailView").hide();
		$(".funcListTR").each(function(ev) {
			if ($(this).attr("rel") != "collectList") {
				this.style.background = "";
				$(this).attr("bk", "");
				$("#" + $(this).attr("rel")).hide();
			} else {
				this.style.background = "#eee";
				$(this).attr("bk", "#eee");
				$("#" + $(this).attr("rel")).show();
			}
		});
		$("#div_search").show();
		$("#CollectList").show();
		checkedRow = new Map();
		current_page = 1;
	}
}
/**
 * 加载草稿箱
 */
function loadTempletMail() {
	var param = new tlv8.RequestParam();
	param.set("limit", limit);
	var re = tlv8.XMLHttpRequest("LoadTempletMailAction", param, "post");
	if (re.data.flag == "true") {
		var list = new createMailList("templetListData", re.data.data, "草稿箱");
		list.init();
		var count = re.count == null ? "0" : re.count;
		initfirstPage("temp_page_info", count, "草稿箱");
		list.afterCreate(count);
		$("#writeMailView").hide();
		$("#lookMailView").hide();
		$("#CollectList").hide();
		$(".funcListTR").each(function(ev) {
			if ($(this).attr("rel") != "templetList") {
				this.style.background = "";
				$(this).attr("bk", "");
				$("#" + $(this).attr("rel")).hide();
			} else {
				this.style.background = "#eee";
				$(this).attr("bk", "#eee");
				$("#" + $(this).attr("rel")).show();
			}
		});
		$("#div_search").show();
		checkedRow = new Map();
		current_page = 1;
	}
}
/**
 * 邮件搜索
 */

var searchValue = "";
function loadSearchMail() {
	var value = $("#ipt_search").val();
	if (value != "") {
		if (value == searchValue) {// 主要用于判断，在值相同的情况下，不再进行查找，否则用户在多次点击查询时造成不必要延时.
			return;
		}
		searchValue = value;
		var param = new tlv8.RequestParam();
		param.set("limit", limit);
		param.set("value", searchValue);
		var re = justep.yn
				.XMLHttpRequest("LoadSearchMailAction", param, "post");
		if (re.data.flag == "true") {
			var count = re.count == null ? "0" : re.count;
			var list = new createMailList("SearchListData", re.data.data,
					"搜索结果");
			list.initSearch(searchValue);
			list.afterCreate(count);
			initfirstPage("search_page_info", count, "搜索结果");
			$("#SearchListValue").text(searchValue);
			$("#SearchListCount").text(count);
			$("#SearchListTime").text(re.cost);
			$("#receiveList").hide();
			$("#sendList").hide();
			$("#templetList").hide();
			$("#writeMailView").hide();
			$("#searchList").show();
			checkedRow = new Map();
			current_page = 1;
		} else {
			tlv8.showMessage("邮件查找出错，请重试或重新打开邮件功能.");
		}
	} else {
		searchValue = value;
	}
}

/**
 * 查看邮件详细
 * 
 * @param rowid
 * @param mailType
 */
var gobacktype = "收件箱";
function lookMailDetail(rowid, type) {
	if (type == "草稿箱") {
		gobacktype = "草稿箱";
		writeMail(rowid);
		return;
	}
	if (type == "收件箱") {
		data_info.setTable("OA_EM_RECEIVEEMAIL");
		gobacktype = "收件箱";
		$("#show_resend_agin").hide();
		$("#show_reply").show();
	} else if (type == "发件箱") {
		$("#show_resend_agin").show();
		$("#show_reply").hide();
		data_info.setTable("OA_EM_SENDEMAIL");
		gobacktype = "发件箱";
	}
	$("#div_search").hide();
	$(".lookViewer").hide();
	$("#writeMailView").hide();
	$("#lookMailView").show();
	J$("mail_look").rowid = rowid;
	J$("mail_look").setAttribute("rowid", rowid);
	$("#mail_look").attr("rowid", rowid);
	J$("mail_look").setAttribute("mailType", type);
	$("#mail_look").attr("mailType", type);
	data_info.setFilter("");
	data_info.refreshData();
}

/**
 * 检测邮件的收藏状态
 */
function checkCollectState() {
	var collect = data_info.getValueByName("FCOLLECT");
	if (collect == "1") {
		$("#unCollectMail").show();
		$("#collectMail").hide();
	} else {
		$("#unCollectMail").hide();
		$("#collectMail").show();
	}
}

/**
 * 查看未读邮件详细
 * 
 * @param rowid
 * @param mailType
 */
function lookNoReadMailDetail(rowid, type) {
	if (type == "收件箱") {
		data_info.setTable("OA_EM_RECEIVEEMAIL");
		updateLookState(rowid);
		gobacktype = "收件箱";
		$("#show_resend_agin").hide();
		$("#show_reply").show();
		$("#div_search").hide();
		$(".lookViewer").hide();
		$("#writeMailView").hide();
		$("#lookMailView").show();
		J$("mail_look").rowid = rowid;
		J$("mail_look").setAttribute("rowid", rowid);
		$("#mail_look").attr("rowid", rowid);
		J$("mail_look").setAttribute("mailType", type);
		$("#mail_look").attr("mailType", type);
		data_info.setFilter("");
		data_info.refreshData();
	}
	checkCollectState();
}

function updateLookState(rowid) {
	var param = new tlv8.RequestParam();
	param.set("rowid", rowid);
	tlv8.XMLHttpRequest("receiveEmail/upQurey", param, "post");
}

function lookviewback() {
	new tlv8.fileComponent(document.getElementById("fj_view"), data_info,
			"FFJID", "/root/邮箱/" + getCurentYearandMonth(), false, false,
			false, false);
	var bh = $(document.body).height();
	bh = bh - 240;
	$("#show_ftext").html($(".look_content").val());
	var height = $("#show_ftext").height();
	if (height <= bh) {
		$("#show_ftext").attr("style",
				"width:100%;height:" + bh + "px;margin-top:1px;");
	}
	checkCollectState();
}

// 删除所选的邮件
function deleteCheckedRow(type) {
	var keyset = checkedRow.keySet();
	var ids = [];
	if (keyset.length > 0) {
		for (var i = 0; i < keyset.length; i++) {
			ids.push("'" + keyset[i] + "'");
		}
		ids.join(",");
		if (confirm("删除后将不能再回复，确定删除吗?")) {
			var param = new tlv8.RequestParam();
			param.set("rowid", ids);
			param.set("type", type);
			var re = justep.yn
					.XMLHttpRequest("DeleteMailAction", param, "post");
			if (re.data.flag == "true") {
				if (type == "发件箱")
					loadSendMail();// 加载发件
				else if (type == "草稿箱")
					loadTempletMail();// 加载草稿箱
				else
					loadRecvMail();// 加载收件
				tlv8.showMessage("邮件删除成功");
			} else {
				alert("删除邮件出错");
			}
		}
	} else {
		tlv8.showMessage("请勾选需要删除的邮件");
	}
}

function goback() {
	if (gobacktype == "收件箱") {
		if(current_page > 1){
			$("#writeMailView").hide();
			$("#lookMailView").hide();
			$("#searchList").hide();
			$(".funcListTR").each(function(ev) {
				if ($(this).attr("rel") != "receiveList") {
					this.style.background = "";
					$(this).attr("bk", "");
					$("#" + $(this).attr("rel")).hide();
				} else {
					this.style.background = "#eee";
					$(this).attr("bk", "#eee");
					$("#" + $(this).attr("rel")).show();
				}
			});
			$("#ipt_search").val("");
			$("#div_search").show();
			current_page--;
			nextPage("收件箱");
		}else{
			loadRecvMail();
		}
	} else if (gobacktype == "发件箱") {
		if(current_page > 1){
			$("#writeMailView").hide();
			$("#lookMailView").hide();
			$(".funcListTR").each(function(ev) {
				if ($(this).attr("rel") != "sendList") {
					this.style.background = "";
					$(this).attr("bk", "");
					$("#" + $(this).attr("rel")).hide();
				} else {
					this.style.background = "#eee";
					$(this).attr("bk", "#eee");
					$("#" + $(this).attr("rel")).show();
				}
			});
			$("#div_search").show();
			current_page--;
			nextPage("收件箱");
		}else{
			loadSendMail();
		}
	} else if (gobacktype == "草稿箱") {
		if(current_page > 1){
			$("#writeMailView").hide();
			$("#lookMailView").hide();
			$("#CollectList").hide();
			$(".funcListTR").each(function(ev) {
				if ($(this).attr("rel") != "templetList") {
					this.style.background = "";
					$(this).attr("bk", "");
					$("#" + $(this).attr("rel")).hide();
				} else {
					this.style.background = "#eee";
					$(this).attr("bk", "#eee");
					$("#" + $(this).attr("rel")).show();
				}
			});
			$("#div_search").show();
			current_page--;
			nextPage("收件箱");
		}else{
			loadTempletMail();
		}
	} else if (gobacktype == "收藏箱") {
		if(current_page > 1){
			$("#writeMailView").hide();
			$("#lookMailView").hide();
			$(".funcListTR").each(function(ev) {
				if ($(this).attr("rel") != "collectList") {
					this.style.background = "";
					$(this).attr("bk", "");
					$("#" + $(this).attr("rel")).hide();
				} else {
					this.style.background = "#eee";
					$(this).attr("bk", "#eee");
					$("#" + $(this).attr("rel")).show();
				}
			});
			$("#div_search").show();
			$("#CollectList").show();
			current_page--;
			nextPage("收件箱");
		}else{
			loadCollectMail();
		}
	}
	$("#div_search").show();

}

/**
 * 获取分页信息
 * 
 * @param action
 *            查询动作
 * @param listdata
 *            显示数据的div id
 * @param type
 *            类别：发件箱、收件箱、草稿箱、搜索（默认为空）
 * @param page
 *            显示分页信息的div id
 * @param loadtype
 *            加载方式，last：上一页，next：下一页
 */
function loadMorePage(action, listdata, type, page, loadtype) {
	var recv_begin, recv_end, currentpage;
	if (loadtype == "next") {
		var param = new tlv8.RequestParam();
		recv_begin = current_page * limit;
		currentpage = ++current_page;
		recv_end = current_page * limit;
		param.set("limit", recv_end);
		param.set("offerset", recv_begin);
		var re = tlv8.XMLHttpRequest(action, param);
		if (re.data.flag == "true") {
			new createMailList(listdata, re.data.data, type).init();
		}
		var count = re.count == null ? "0" : re.count;
		initnextPage(page, count, type, currentpage);
	} else {
		var param = new tlv8.RequestParam();
		currentpage = --current_page;
		recv_end = current_page * limit;
		recv_begin = (current_page - 1) * limit;
		param.set("limit", recv_end);
		param.set("offerset", recv_begin);
		var re = tlv8.XMLHttpRequest(action, param);
		if (re.data.flag == "true") {
			new createMailList(listdata, re.data.data, type).init();
		}
		var count = re.count == null ? "0" : re.count;
		initlastPage(page, count, type, currentpage);
	}
}

function loadSearchMorePage(loadtype) {
	if (searchValue == "" || searchValue == null)
		return;
	var currentpage, recv_begin, recv_end;
	if (loadtype == "next") {
		recv_begin = current_page * limit;
		currentpage = ++current_page;
		recv_end = current_page * limit;
	} else {
		currentpage = --current_page;
		recv_end = current_page * limit;
		recv_begin = (current_page - 1) * limit;
	}
	var param = new tlv8.RequestParam();
	param.set("value", searchValue);
	param.set("limit", recv_end);
	param.set("offerset", recv_begin);
	var re = tlv8.XMLHttpRequest("LoadSearchMailAction", param, "post");
	if (re.data.flag == "true") {
		new createMailList("SearchListData", re.data.data, "搜索结果")
				.initSearch(searchValue);
	}
	var count = re.count == null ? "0" : re.count;
	if (loadtype == "next") {
		initnextPage("search_page_info", count, "搜索", currentpage);
	} else {
		initlastPage("search_page_info", count, "搜索", currentpage);
	}
}

/**
 * 首次加载时获取分页信息
 * 
 * @param div
 *            显示分页的dv
 * @param count
 *            总数
 * @param type
 *            类别
 */

function initfirstPage(div, count, type) {
	if (count < 1) {
		$("#" + div).html("0/0 页");
	} else if (count >= 1 && count < limit) {
		$("#" + div)
				.html("共(" + count + ")封邮件,每页显示(" + limit + ")封. 当前: 1/1 页");
	} else {
		var page = (Math.floor(count / limit)) + (count % limit == 0 ? 0 : 1);
		$("#" + div)
				.html(
						"共("
								+ count
								+ ")封邮件,每页显示("
								+ limit
								+ ")封. 当前: 1/"
								+ page
								+ " 页 <a href=\"javascript:void(0);\" onclick=\"nextPage('"
								+ type + "')\" title=\"下一页\">下一页&gt;&gt;</a>");
	}
}

/**
 * 指定某div id后，显示下一页信息
 * 
 * @param div
 *            显示分页div
 * @param count
 * @param type
 * @param currentpage
 */
function initnextPage(div, count, type, currentpage) {
	var page = (Math.floor(count / limit)) + (count % limit == 0 ? 0 : 1);
	if (currentpage < page) {
		$("#" + div)
				.html(
						"共("
								+ count
								+ ")封邮件,每页显示("
								+ limit
								+ ")封. 当前: "
								+ currentpage
								+ "/"
								+ page
								+ " 页 <a href=\"javascript:void(0);\" onclick=\"lastPage('"
								+ type
								+ "')\" title=\"上一页\">&lt;&lt;上一页</a> <a href=\"javascript:void(0);\" onclick=\"nextPage('"
								+ type + "')\" title=\"下一页\">下一页&gt;&gt;</a>");
	} else if (currentpage == page) {
		$("#" + div)
				.html(
						"共("
								+ count
								+ ")封邮件,每页显示("
								+ limit
								+ ")封. 当前: "
								+ currentpage
								+ "/"
								+ page
								+ " 页 <a href=\"javascript:void(0);\" onclick=\"lastPage('"
								+ type + "')\" title=\"上一页\">&lt;&lt;上一页</a>");
	}
}
/**
 * 指定某div id后，显示上一页信息
 * 
 * @param div
 *            显示分页div
 * @param count
 *            数据总数
 * @param type
 *            类别
 * @param currentpage
 *            当前页
 */
function initlastPage(div, count, type, currentpage) {
	var page = (Math.floor(count / limit)) + (count % limit == 0 ? 0 : 1);
	if (currentpage == 1) {
		$("#" + div)
				.html(
						"共("
								+ count
								+ ")封邮件,每页显示("
								+ limit
								+ ")封. 当前: "
								+ currentpage
								+ "/"
								+ page
								+ " 页 <a href=\"javascript:void(0);\" onclick=\"nextPage('"
								+ type + "')\" title=\"下一页\">下一页&gt;&gt;</a>");
	} else if (currentpage < page) {
		$("#" + div)
				.html(
						"共("
								+ count
								+ ")封邮件,每页显示("
								+ limit
								+ ")封. 当前: "
								+ currentpage
								+ "/"
								+ page
								+ " 页 <a href=\"javascript:void(0);\" onclick=\"lastPage('"
								+ type
								+ "')\" title=\"上一页\">&lt;&lt;上一页</a> <a href=\"javascript:void(0);\" onclick=\"nextPage('"
								+ type + "')\" title=\"下一页\">下一页&gt;&gt;</a>");
	}
}