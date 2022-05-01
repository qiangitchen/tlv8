$(document).bind("pagebeforecreate", function(event, data) {
	initPage();
});

function initPage() {
	var sql = "select FID,FADDRESS,FRESOURCE,FCREATEDATE from WQ_SCENE_VISIT where FCREATORID = '"
			+ justep.Context.getCurrentPersonID()
			+ "' and trunc(FCREATEDATE) = trunc(FCREATEDATE) order by FCREATEDATE desc";
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
					html += " <h3>" + data[i].FADDRESS
							+ "<span style='float:right;'>"
							+ data[i].FCREATEDATE.substring(0, 11)
							+ "</span></h3>";
					html += "<p><strong>" + data[i].FRESOURCE
							+ "</strong></p>";
					html += "<p>" + data[i].FCREATEDATE + "</p>";
					html += "</li>";
				}
				$("#list_view").html(html);
			}, false);
}

function viewDeatail(obj) {
	var rowid = $(obj).attr("id");
	var url = "mainActivity.html?rowid=" + rowid + "&option=view";
	window.open(url, "_self");
}