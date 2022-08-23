function show_news() {
	var param = new tlv8.RequestParam();
	var res = tlv8.XMLHttpRequest("getPortalNotesAction", param, "post",
			false);
	if (res.data.flag != "true") {
		$("#addnewsload").html(res.data.message);
		return;
	}
	var news = res.data;
	if (typeof news.data == "string") {
		news.data = window.eval("(" + news.data + ")");
	}
	var html = "";
	showpicHTML = "";
	if (news.data.length > 0) {
		for (var n = 0; n < news.data.length; n++) {
			if (n >= 5)
				break;
			var cells = news.data;
			html += "<table cellSpacing='0' cellPadding='0' style=\"table-layout: fixed;width:100%;\" border='0'>";
			html += "<tr>";
			html += "<td style='white-space:nowrap;overflow:hidden;line-height:22px;'>"
					+ "<img src='symbol.png'> <a href='javascript:void(0);' title='"
					+ cells[n].FTITLE
					+ "' onClick='tlv8.portal.openWindow(\"通知详细\",\"/OA/notice/publicnotice/publicnoticeview1.html?rowid="
					+ cells[n].FID + "\")'>" + cells[n].FTITLE;
			html += "&nbsp;</td>";
			html += "<td width='70px;' style='text-align:center;'>"
					+ (tlv8.System.Date.strToDate(cells[n].FPUSHDATETIME)
							.format("yyyy-MM-dd"));
			html += "</td>";
			html += "</tr>";
			html += "</table>";
		}
		$("#addnewsload").html(html);
	}
}
// 更多
function onhistory() {
	tlv8.portal.openWindow('通知列表',
			'/OA/notice/publicnotice/looknoticepushlist.html');
}
