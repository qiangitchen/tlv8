$(document).bind("pagebeforecreate", function(event, data) {
	initPage();
});

function initPage() {
	var sql = "select FTYPE,FCLOCKTIME,FADDRESS,FCUSTOMER from WQ_PERSON_CLOCKIN where FPERSONID = '"
			+ justep.Context.getCurrentPersonID()
			+ "' and trunc(FCLOCKTIME) = trunc(sysdate) order by FCLOCKTIME asc";
	tlv8.sqlQueryActionforJson("wq", sql, function(result) {
		var data = result.data;
		if (typeof data == "string") {
			data = window.eval("(" + data + ")");
		}
		$("#clockInCount").text(data.length);
		var html = "";
		for (var i = 0; i < data.length; i++) {
			html += "<li>";
			html += " <h3>" + data[i].FTYPE + "<span style='float:right;'>"
					+ data[i].FCLOCKTIME.substring(11, 16) + "</span></h3>";
			html += "<p><strong>" + data[i].FADDRESS + "</strong></p>";
			html += "<p>" + justep.Context.getCurrentPersonName()
					|| data[i].FCUSTOMER + "</p>";
			html += "</li>";
		}
		$("#list_view").html(html);
	}, false);
}