var data = new tlv8.Data();
var xheditor = null;
var rowid;
var title;
$(document).ready(function() {
	pageInit();
});

function pageInit() {
	data.setDbkey("system");
	data.setTable("bo_comment");
	data.setFormId("bo_comment");// 设置提交的表单
	rowid = tlv8.RequestURLParam.getParam("rowid");
	var title1 = decodeURIComponent(tlv8.RequestURLParam.getParam("title"));
	if (title1 != "" && title1 != "undefined") {
		$("#TITLE").html("<img src='image/sucaiwcom (2).gif'/>" + title1);// 设置标题
	} else {
		$("#TITLE").html("<img src='image/sucaiwcom (2).gif'/>");
	}
	editcell();// 讨论内容初始化
	// 判断帖子是否锁定
	var sql = "SELECT STATUS,ONESELF FROM BO_ENTRY WHERE SID='" + rowid + "'";
	var cells = tlv8.sqlQueryActionforJson("system", sql);
	var ONESELF = cells.data[0].ONESELF;
	var STATUS = cells.data[0].STATUS;
	if (STATUS != 1) {
		replyContent();// 回复内容初始化
		document.getElementById("bo_comment").reset();
	} else {
		$("#OPENSCOPE").attr("title", function() {
			return this.src;
		});
		$("#OPENSCOPE").removeAttr("onclick");// 如果帖子被锁定禁用回复功能
		disableContent();
	}
}
// 讨论内容初始化
function editcell() {
	var sqlstr = "SELECT CONTENT FROM BO_ENTRY WHERE SID='" + rowid + "'";
	var cells = SqlQueryActionforJson("system", sqlstr);
	cells = cells[0].CONTENT;
	cells = cells.replaceAll("#lt;", "<").replaceAll("#gt;", ">").replaceAll(
			"#160;", "&nbsp;");
	var sql = "SELECT SID,TITLE,CREATED_TIME,ONESELF FROM BO_ENTRY WHERE SID='"
			+ rowid + "'";
	var news = tlv8.sqlQueryAction("system", sql);
	title = news.getValueByName("TITLE");
	$("#BO_ENTRY_title").html(
			"<img src='image/sucaiwcom.gif'/>[讨论]标题："
					+ news.getValueByName("TITLE"));
	$("#span_0").html(cells);
	$("#span_1").html(
			"<img src='image/icon_06.gif'/>发布者：["
					+ news.getValueByName("ONESELF") + "]&nbsp;&nbsp;"
					+ news.getValueByName("CREATED_TIME"));
}
// 查询回复内容
function replyContent() {
	var sql = "SELECT SID,AUTHOR,ENTRY_TITLE,CREATED_TIME,CONTENT,ENTRY_ID FROM BO_COMMENT WHERE  ENTRY_ID='"
			+ rowid + "' ORDER BY CREATED_TIME ASC ";
	var comment = tlv8.sqlQueryActionforJson("system", sql, null, false);
	var cells = comment.data;
	for (var i = 0; i < cells.length; i++) {
		var sid = cells[i].SID;
		var status = cells[i].AUTHOR;// 回复人
		var status1 = cells[i].ENTRY_TITLE;// 标题
		var status2 = cells[i].CREATED_TIME;// 回复时间
		var status3 = cells[i].CONTENT;// 内容
		$("#bbsMain")
				.after(
						"<div id='"
								+ sid
								+ "'><div class='main_contment' style='border: 1 dashed #987cb9'><br/><br/>"
								+ "<font color='#003399'>※</font><b>"
								+ status1
								+ "</b><br>"
								+ "<div id='content_1' class='java' style='font-size: 10pt;'>"
								+ status3
								+ "</div><br>"
								+ "<span class='info_span' id='span_2'><img src='image/icon_06.gif'/>评论者:"
								+ status
								+ "&nbsp;"
								+ "&nbsp;"
								+ status2
								+ "</span></div>"
								+ "<div class='op_bar'>"
								+ "<img id='"
								+ sid
								+ "' "
								+ "onClick='deleteComment(\""
								+ sid
								+ "\")' src='image/delete.gif' title='删除' style='cursor: hand;'/></div></div>");
	}
}
// 查询回复内容
function disableContent() {
	var sql = "SELECT SID,AUTHOR,ENTRY_TITLE,CREATED_TIME,CONTENT,ENTRY_ID FROM BO_COMMENT WHERE  ENTRY_ID='"
			+ rowid + "' ORDER BY CREATED_TIME ASC ";
	var comment = tlv8.sqlQueryActionforJson("system", sql, null, false);
	var cells = comment.data;
	for (var i = 0; i < cells.length; i++) {
		var sid = cells[i].SID;
		var status = cells[i].AUTHOR;// 回复人
		var status1 = cells[i].ENTRY_TITLE;// 标题
		var status2 = cells[i].CREATED_TIME;// 回复时间
		var status3 = cells[i].CONTENT;// 内容
		$("#bbsMain")
				.after(
						"<div id='"
								+ sid
								+ "'><div class='main_contment' style='border: 1 dashed #987cb9'>"
								+ "<font color='#003399'>※</font><b>"
								+ status1
								+ "</b><br>"
								+ "<span id='content_1' style='font-size: 10pt;'>"
								+ status3
								+ "</span><br>"
								+ "<span class='info_span' id='span_2'><img src='image/icon_06.gif'/>评论者:"
								+ status + "&nbsp;" + "&nbsp;" + status2
								+ "</span></div></div>");
	}
}
// 快速回复
function soonAnswer() {
	$("#bo_comment").show();// 显示快速回复单
	// 显示快速回复
	$('#bo_comment').dialog({
		title : '快速回复',
		width : 900,
		closed : false,
		cache : false,
		modal : true,
		collapsible : true,
		maximizable : true,
		buttons : [ {
			text : '确定',
			handler : function() {
				BtnsaveData();
				$('#bo_comment').dialog('close');
			}
		}, {
			text : '取消',
			handler : function() {
				$('#bo_comment').dialog('close');
			}
		} ]
	});
	// 初始化编辑框
	xheditor = $("#CONTENT")
			.xheditor(
					"{tools:'full',skin:'o2007silver',﻿﻿﻿﻿upImgUrl:'xhUpload',upImgExt:'jpg,jpeg,gif,png'}");
	// 添加默认值
	$("#ENTRY_TITLE").val(title);
	$("#CREATED_TIME").val(tlv8.System.Date.sysDateTime());
	$("#AUTHOR").val(tlv8.Context.getCurrentPersonName());
	$("#ENTRY_ID").val(rowid);
}
// 回复功能
function BtnsaveData() {
	var saveid = data.saveData();
	if(!saveid){
		return false;
	}
	$("#bbsMain")
			.after(
					"<div id='"
							+ saveid
							+ "'><div class='main_contment'>"
							+ "<font color='#000099'>※</font><b>"
							+ $("#ENTRY_TITLE").val()
							+ "</b><br>"
							+ "<span id='content_1' style='font-size: 10pt;'>"
							+ $("#CONTENT").val()
							+ "</span><br>"
							+ "<span class='info_span' id='span_2'><img src='image/icon_06.gif'/>评论者:"
							+ $("#AUTHOR").val()
							+ "&nbsp;"
							+ "&nbsp;"
							+ $("#CREATED_TIME").val()
							+ "</span></div>"
							+ "<div class='op_bar'>"
							+ "<img id='"
							+ saveid
							+ "' "
							+ "onClick='deleteComment(\""
							+ saveid
							+ "\")' src='image/delete.gif' title='删除' style='cursor: hand;'/></div></div>");
	document.getElementById("bo_comment").reset();
	document.getElementById("bo_comment").rowid = '';
}

// 删除评论
function deleteComment(rowid) {
	var SID = rowid;
	var sql = "SELECT AUTHOR FROM BO_COMMENT WHERE SID='" + SID + "'";
	var news = tlv8.sqlQueryAction("system", sql);
	var author = news.getValueByName("AUTHOR");
	if (author == tlv8.Context.getCurrentPersonName()) {
		var sql = "DELETE FROM BO_COMMENT WHERE SID='" + SID + "'";
		var pam = new tlv8.RequestParam();
		pam.set("sql", sql);
		tlv8.XMLHttpRequest("updateCeaseOrganAction", pam, "post", true,
				function(data) {
					var r = eval(data.data);
					if (r.flag == "false") {
						alert(r.message);
					} else {
						sAlert("操作成功!", 500);
						$("div[id='" + SID + "']").remove();
					}
				});

	} else {
		sAlert("不能删除该评论!", 500);
	}
}

/*
 * @ 执行sql查询动作JSON
 */
function SqlQueryActionforJson(dbkey, sql, callBack) {
	var r = tlv8.sqlQueryActionforJson(dbkey, sql, callBack, false);
	var reData = r.data ? r.data : [];
	for (i in reData) {
		try {
			reData[i] = eval("(" + reData[i] + ")");
		} catch (e) {
		}
	}
	r.data = reData;
	return reData;
};