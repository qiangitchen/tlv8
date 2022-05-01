var showpicHTML;
// 播放图片
function getXml() {
	var picRa = "<?xml version='1.0' encoding='utf-8'?>" + "<root>"
			+ showpicHTML + "</root>";
	return picRa;
}

function show_news() {
	var param = new tlv8.RequestParam();
	tlv8.XMLHttpRequest("loadNewsAction", param, "POST", true,
			loadcallback);
}

function loadcallback(re) {
	var html = "";
	showpicHTML = "";
	var news = re.data;
	try {
		news.data = window.eval("(" + news.data + ")");
	} catch (e) {
	}
	if (news.data.length > 0) {
		for (var n = 0; n < news.data.length; n++) {
			if (n >= 5)
				break;
			var cells = news.data;
			html += "<div style=\"width:100%; size:10\" id='"
					+ cells[n].FID
					+ "' class='mydiv'>"
					+ "<img src='image/symbol.png'><a href='javascript:void(0);' onClick='tlv8.portal.openWindow(\"新闻\",\"/system/News/informationRelase/news.html?rowid="
					+ cells[n].FID
					+ "\")' title='"
					+ cells[n].FTITLE
					+ "'>"
					+ " "
					+ ((cells[n].FTITLE.length > 10) ? cells[n].FTITLE
							.substring(0, 10)
							+ "..." : cells[n].FTITLE)
					+ "&nbsp;"
					+ cells[n].FPEOPLE
					+ "&nbsp;"
					+ (tlv8.System.Date.strToDate(cells[n].FPEOPLEDATE)
							.format('yyyy-MM-dd')) + "  </a></div>";
		}
		$("#addnewsload").html(html);
	}
	;
}

function selectData(coname, tbname, whe) {
	var result = tlv8.sqlQueryActionforJson("system", " select " + coname
			+ " from " + tbname + " " + whe);
	return result.data;
}

// 更多
function onhistory() {
	tlv8.portal.openWindow('新闻列表',
			'/system/News/informationRelase/newsManageLists.html');
};
