$(document).bind("pagebeforecreate", function(event, data) {
	initPage();
});

function initPage() {
	var sql = "select FID,FTITLE,FCREATORNAME,FCREATEDATE from WQ_TOUR where FCREATORID = '"
			+ justep.Context.getCurrentPersonID()
			+ "'  order by FCREATEDATE desc";
	tlv8.sqlQueryActionforJson("wq", sql,
			function(result) {
				var data = result.data;
				if (typeof data == "string") {
					data = window.eval("(" + data + ")");
				}
				$("#clockInCount").text(data.length);
				var html = "";
				for (var i = 0; i < data.length; i++) {
					html += "<li id='" + data[i].FID
							+ "' onclick='viewDeatail(this)'>";
					html += " <h3>" + data[i].FTITLE
							+ "<span style='float:right;'>"
							+ data[i].FCREATEDATE.substring(0, 11)
							+ "</span></h3>";
					html += "<p><strong>" + data[i].FCREATORNAME
							+ "</strong></p>";
					html += "</li>";
				}
				$("#list_view").html(html);
			}, false);
}

function viewDeatail(obj) {
	var rowid = $(obj).attr("id");
	var url = "tour_detail.html?rowid=" + rowid + "&option=view";
	window.open(url, "_self");
}