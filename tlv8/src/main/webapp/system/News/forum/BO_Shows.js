var random = Math.random();
var refresh = random + "=" + random;

$(document).ready(function() {
	show_news();
});

function show_news() {
	var param = new tlv8.RequestParam();
	var res = tlv8.XMLHttpRequest("getPortalBofoAction", param, "post",
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
	for (var n = 0; n < news.data.length; n++) {
		if (n >= 5)
			break;
		var cells = news.data;
		html += "<div id='"
				+ cells[n].SID
				+ "' class='mydiv'>"
				+ "<img src='image/symbol.png'><a href='javascript:void(0);' "
				+ "onClick='tlv8.portal.openWindow(\"浏览帖子\",\"/system/News/forum/velocityentry.html?rowid="
				+ cells[n].SID + "&title=" + cells[n].TITLE + "\")'>" + " "
				+ cells[n].TITLE + "&nbsp;&nbsp;" + cells[n].ONESELF
				+ "&nbsp;&nbsp;" + cells[n].CREATED_TIME + "  </a></div>";
	}
	$("#addnewsload").html(html);
}

function onhistory() {
	tlv8.portal.openWindow("浏览", "/system/News/forum/BO_talkl.html",
			null);
}