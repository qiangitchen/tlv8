function init() {
	var param = new tlv8.RequestParam();
	var res = tlv8.XMLHttpRequest("loadMailPortalInfo", param, "post",
			false);
	if (res.data.flag != "true") {
		$("#addnewsload").html(res.data.message);
		return;
	}
	var re = res.data;
	if (typeof re.data == "string") {
		re.data = window.eval("(" + re.data + ")");
	}
	var html = "";
	var count = re.data.length;
	if (count > 0) {
		html += "<table><tr><td><div style='width: 100%;border-bottom: 1px solid #A0B8C9;'>"
				+ "<table  cellSpacing='0' cellPadding='0' style=\"table-layout: fixed;width:100%;line-height: 18px;\" border='0'>"
				+ "<tr style='font-weight: bold;'><td>主题</td><td style='width:70px;'>发件人</td><td style='width:100px;'>发件时间</td></tr></table>"
				+ "</div></td></tr></table><table cellSpacing='0' cellPadding='0' style=\"table-layout: fixed;width:100%;\" border='0'>";
		for (var n = 0; n < count; n++) {
			if (n >= 5)
				break;
			var cells = re.data;
			var time = translateTime(cells[n].FSENDTIME);
			var title = cells[n].FEMAILNAME == "" ? "</span style=\"color:green;\">(无主题)</span>"
					: cells[n].FEMAILNAME;
			html += "<tr style='height:19px;'>";
			html += "<td class='showTitle'>"
					+ "<img src='symbol.png'> <a href='javascript:void(0);' title='"
					+ cells[n].FEMAILNAME + "' onClick=\"openWin()\">" + title;
			html += "</a></td>";
			html += "<td width='40px;'/>";
			html += "<td style='width:70px;'>" + cells[n].FSENDPERNAME;
			html += "</td>";
			html += "<td style='width:105px;'>" + time;
			html += "</td>";
			html += "</tr>";
		}
		html += "</table>";
		$("#showlist").html(html);
		var html_count = "<div class='countTixview'><font style='size:14px;color: #000;line-height: 28px;'>"
				+ count + "</font></div>";
		$("#empty_email").remove();
		$(".countTixview").remove();
		$(html_count).insertBefore($("#email"));
	} else {
		html = "<span style='line-height: 23px;font-size:15px;'><br/>当前没有您的新邮件!<br/>建议您可以点击<font style='color:green;'>左侧“图标”</font>,或者从最左边的<font style='size:15px;color:green;'>功能菜单</font>中打开邮箱.<br/>然后给其他人发送邮件吧.&nbsp;&nbsp;&nbsp;&nbsp;^_^</span>";
		$("#showlist").html(html);
	}
	delete res;
	setTimeout(init,60*1000);
}
function openWin() {
	tlv8.portal.openWindow('内部邮件', '/OA/email/mainActivity.html');
}

function translateTime(date) {
	var c_date = tlv8.System.Date.strToDate(date);
	var c_f_date = c_date.format("yyyy/MM/dd");
	var this_date = tlv8.System.Date.strToDate(tlv8.System.Date
			.sysDate());
	var this_f_date = this_date.format("yyyy/MM/dd");
	var re = "";
	if (this_f_date == c_f_date) {
		re = c_date.format("hh:mm:ss") + " (今天)";
	} else {
		var year = c_date.getYear();
		var c_year = this_date.getYear();
		if (year == c_year) {
			re = c_date.format("MM月dd日");
		} else {
			re = c_f_date;
		}
	}
	return re;
}