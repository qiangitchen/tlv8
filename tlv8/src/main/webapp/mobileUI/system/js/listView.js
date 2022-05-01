if (!justep)
	var justep = {};

var ListDataMap = new Map();
/*
 * 时间任务列表
 */
justep.creatListMutiView = function(data, mrbtn) {
	if (typeof data == "string") {
		data = window.eval("(" + data + ")");
	}
	var html = '<ul class="ui-listview mui-table-view mui-table-view-striped mui-table-view-condensed">';
	for (var i = 0; i < data.length; i++) {
		if (data[i].NAME || data[i].NAME != "") {
			html += '<li class="mui-table-view-cell" id="'
					+ data[i].ID
					+ '" rel="list" type="'
					+ data[i].TYPE
					+ '" DOCTYPE="'
					+ data[i].DOCTYPE
					+ '" NAME="'
					+ data[i].NAME + '" TITLE="' + data[i].TITLE + '">';
			html +='<div class="mui-table">';
			html +='<div class="mui-table-cell mui-col-xs-12">';
			html += '<h5 class="mui-ellipsis">';
			html += '<a style="text-decoration: none;">'
					+ data[i].NAME.toString().trim() + '</a></h5>';
			html +='<div class="mui-table-cell mui-col-xs-6">';
			html += '<p style="color:#666;"><strong style="font-size:14px;">'
					+ data[i].PSNNAME + '</strong>';
			html +='</p></div>';
			html +='<div class="mui-table-cell mui-col-xs-6">';
			html += '<p class="mui-h6 mui-ellipsis"><strong>' + data[i].TIME
					+ '</strong></p>';
			html +='</div>';
			html +='</div>';
			html += '</div></li>';
			ListDataMap.put(data[i].ID, data[i]);
		}
	}
	if (mrbtn && data.length >= 10) {
		html += '<li class="mui-table-cell" style="align:center;" id="moreBtn" onclick="loadMore()">';
		html += '<p><a class="mui-h5 mui-ellipsis" style="margin-left:40%;text-decoration: none;" href="#">加载更多...</a></p></li>';
	}
	if (data.length < 10) {
		$("#moreBtn").hide();
	}
	html += '</ul>';
	return html;
};

/*
 * 电话号码列表
 */
justep.creatListAddBookView = function(data, mrbtn) {
	if (typeof data == "string") {
		data = window.eval("(" + data + ")");
	}
	var html = '<ul data-role="listview" class="ui-listview">';
	for (var i = 0; i < data.length; i++) {
		if (data[i].SNAME || data[i].SNAME != "") {
			var mobile = data[i].SMOBILEPHONE == "" ? "未录入手机号码"
					: data[i].SMOBILEPHONE;
			var mobileshort = data[i].MOBILESHORT == "" ? "无"
					: data[i].MOBILESHORT;
			html += '<li class="ui-li ui-li-static ui-btn-up-c ui-first-child" id="'
					+ data[i].ID + '" rel="list">';
			html += "<table style=\"width:100%;\" ><tr><td>姓名:"
					+ data[i].SNAME.toString().trim()
					+ "</td><td align='right'>职务:"
					+ data[i].OGNNAME
					+ "</td></tr><tr><td>电话:<a class=\"ui-link\" style=\"text-decoration: none;\"  href=\"tel:"
					+ data[i].SMOBILEPHONE
					+ "\">"
					+ mobile
					+ "</a></td><td align='right'>短号:"
					+ "<a class=\"ui-link\" style=\"text-decoration: none;\"  href=\"tel:"
					+ data[i].MOBILESHORT + "\">" + mobileshort
					+ "</a></td></tr></table>";
			html += '</li>';
		}
	}
	if (mrbtn && data.length >= 10) {
		html += '<li class=" ui-btn-up-c ui-first-child" style="align:center;" id="moreBtn" onclick="loadMore()">';
		html += '<p><a class="ui-link" style="margin-left:40%;text-decoration: none;" href="#">加载更多...</a></p></li>';
	}
	if (data.length < 10) {
		$("#moreBtn").hide();
	}
	html += '</ul>';
	return html;
};

String.prototype.trim = function() {
	return this.replace(/(^\s+)|(\s+$)/g, "");
};