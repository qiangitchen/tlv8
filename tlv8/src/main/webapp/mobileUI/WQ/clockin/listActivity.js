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
			html += " <h3>打卡类型：" + data[i].FTYPE + "</h3><h3>打卡时间："
					+ data[i].FCLOCKTIME.substring(11, 16) + "</h3>";
			html += "<h3>打卡地点：<div style='margin-top:8px;'>" + data[i].FADDRESS + "</div></h3>";
//			html += "<p>" + justep.Context.getCurrentPersonName()
//					|| data[i].FCUSTOMER + "</p>";
			html += "</li>";
		}
		$("#list_view").html(html);
	}, false);
}