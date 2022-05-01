$(document).bind("pagebeforecreate", function(event, data) {
	initPage();
});

function initPage() {
	var sql = "select * from wait_task_view where FPSNID = '"
			+ justep.Context.getCurrentPersonID()
			+ "' and FEDITDATE is null order by FCREATEDATE desc";
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
							+ "' fmainid='"+data[i].FMAINID+"' onclick='viewDeatail(this)'>";
					html += " <h3>" + data[i].FTITLE
							+ "<span style='float:right;'>"
							+ data[i].FCREATEDATE.substring(0, 11)
							+ "</span></h3>";
					html += "<p><strong>" + data[i].FTASKCONTENT
							+ "</strong></p>";
					html += "</li>";
				}
				$("#list_view").html(html);
			}, false);
}

function viewDeatail(obj) {
	var rowid = $(obj).attr("id");
	var mainid = $(obj).attr("fmainid");
	var url = "filltask.html?sData1=" + rowid + "&option=view&mainid="+mainid;
	window.open(url, "_self");
}