function init() {
	var sqlStr = "SELECT * FROM SHOW_OA_RE_DAYREPORT T  WHERE T.FPERSONID='"
			+ tlv8.Context.getCurrentPersonID()
			+ "' and T.FPUSHDATETIME IS NOT NULL ORDER BY T.FPUSHDATETIME DESC";
	var re = tlv8.sqlQueryActionforJson("oa", sqlStr);
	var html = "";
	if (re.data.length > 0) {
		for (var n = 0; n < re.data.length; n++) {
			if (n >= 5)
				break;
			var cells = re.data;
			html += "<table cellSpacing='0' cellPadding='0' style=\"table-layout: fixed;width:100%;\" border='0'>";
			html += "<tr>";
			html += "<td style='white-space:nowrap;overflow:hidden;line-height:22px;'>"
					+ "<img src='symbol.png'> <a href='javascript:void(0);' title='"
					+ cells[n].FTITLE
					+ "' onClick=\"openWin('"
					+ cells[n].FID
					+ "')\">" + cells[n].FTITLE;
			html += "&nbsp;</a></td>";
			html += "<td width='70px' style='text-align:center;'>"
					+ cells[n].FCREATEPERSONNAME;
			html += "</td>";
			html += "<td width='70px;' style='text-align:center;'>"
					+ (cells[n].FPUSHDATETIME).substring(0, 10);
			html += "</td>";
			html += "</tr>";
			html += "</table>";
		}
		$("#addnewsload").html(html);
	}
}
function openWin(id) {
	tlv8.portal.openWindow('日报详细',
			'/OA/Report/DayReport/reportAdd.html?temp='
					+ new Date().getMilliseconds() + "&rowid=" + id
					+ "&readonly=true", 'newwindow');
}
// 更多
function more() {
	tlv8.portal.openWindow('日报查看',
			'/OA/Report/DayReport/reportLookList.html');
}
