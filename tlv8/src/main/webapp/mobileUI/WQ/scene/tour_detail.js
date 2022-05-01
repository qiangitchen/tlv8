$(document).bind("pagebeforecreate", function(event, data) {
	initPage();
});

var mainData = new tlv8.Data();
mainData.setDbkey("wq");
mainData.setTable("WQ_TOUR");
// 页面初始化
var option = getParamValueFromUrl("option");
function initPage() {
	mainData.setFormId("tour_form");
	var rowid = getParamValueFromUrl("rowid");
	if (rowid && rowid != "") {
		$("#tour_form").attr("rowid", rowid);
		mainData.refreshData();
	} 
	var sql = "select FID,FCONTENT,FISCONFORM,FCREATEDATE,FDESCRIBE from WQ_TOUR_SAFE where FMAINID='"+rowid+"'  order by FCREATEDATE desc";
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
							+ "''>";
					html += " <h3>" + data[i].FCONTENT+"("+data[i].FISCONFORM+")"
							+ "<span style='float:right;'>"
							+  data[i].FCREATEDATE.substring(0,11)+""
							+ "</span></h3>";
					html += "<p><strong>" + data[i].FDESCRIBE
							+ "</strong></p>";
					html += "</li>";
				}
				
				$("#list_safe").html(html);
			}, false);
	
	var sql1 = "select FID,FCONTENT,FISCONFORM,FCREATEDATE,FDESCRIBE from WQ_TOUR_QUALITY where FMAINID='"+rowid+"'  order by FCREATEDATE desc";
	tlv8.sqlQueryActionforJson("wq", sql1,
			function(result) {
				var data = result.data;
				if (typeof data == "string") {
					data = window.eval("(" + data + ")");
				}
//				$("#clockInCount").text(data.length);
				var html = "";
				for (var i = 0; i < data.length; i++) {
					html += "<li id='" + data[i].FID
							+ "''>";
					html += " <h3>" + data[i].FCONTENT+"("+data[i].FISCONFORM+")"
							+ "<span style='float:right;'>"
							+  data[i].FCREATEDATE.substring(0,11)
							+ "</span></h3>";
					html += "<p><strong>" + data[i].FDESCRIBE
							+ "</strong></p>";
					html += "</li>";
				}
				
				$("#list_quality").html(html);
			}, false);
	
	
	var sql2 = "select FID,FCONTENT,FISCONFORM,FCREATEDATE,FDESCRIBE from WQ_TOUR_SANITATION where FMAINID='"+rowid+"'  order by FCREATEDATE desc";
	tlv8.sqlQueryActionforJson("wq", sql2,
			function(result) {
				var data = result.data;
				if (typeof data == "string") {
					data = window.eval("(" + data + ")");
				}
//				$("#clockInCount").text(data.length);
				var html = "";
				for (var i = 0; i < data.length; i++) {
					html += "<li id='" + data[i].FID
							+ "''>";
					html += " <h3>" + data[i].FCONTENT+"("+data[i].FISCONFORM+")"
							+ "<span style='float:right;'>"
							+  data[i].FCREATEDATE.substring(0,11)
							+ "</span></h3>";
					html += "<p><strong>" + data[i].FDESCRIBE
							+ "</strong></p>";
					html += "</li>";
				}
				
				$("#list_sanitation").html(html);
			}, false);
}

// 保存
function saveData(goback) {
	var rowid = mainData.saveData();
	$("#tour_form").attr("rowid", rowid);
		var url = "sceneindex.html";
		window.open(url, '_self');
	return true;
}
function backhistory(){
		var url = "tourQuery.html";
		window.open(url, '_self');
}
var isshow1=true;
function dis1(){
	if(isshow1){
		$("#list_safe").hide();
		isshow1=false;
	}else{
		$("#list_safe").show();
		isshow1=true;
	}
}
var isshow2=true;
function dis2(){
	if(isshow2){
		$("#list_quality").hide();
		isshow2=false;
	}else{
		$("#list_quality").show();
		isshow2=true;
	}
}
var isshow3=true;
function dis3(){
	if(isshow3){
		$("#list_sanitation").hide();
		isshow3=false;
	}else{
		$("#list_sanitation").show();
		isshow3=true;
	}
}