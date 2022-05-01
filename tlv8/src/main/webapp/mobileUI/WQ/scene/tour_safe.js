$(document).bind("pagebeforecreate", function(event, data) {
	initPage();
	initType();
});

function initPage() {
	var rowid = getParamValueFromUrl("rowid");
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
							+  data[i].FCREATEDATE.substring(0,11)+"<a href=\"javascript:deleteData('"+data[i].FID+"')\">删除</a>"
							+ "</span></h3>";
					html += "<p><strong>" + data[i].FDESCRIBE
							+ "</strong></p>";
					html += "</li>";
				}
				
				$("#list_view").html(html);
			}, false);
}
//添加数据
function insertData(){
	$.ajax({
		type : "post",
		async : false,
		url : "insertSafeAction",
		data : getSubData(),
		success : function(result, textStatus) {
			if (result.data.flag == "false") {
				alert(result.data.message);
			} else {
				window.location.reload(); 
			}
		},
		error : function() {
		}
	});
	
}


function deleteData(fid){
	$.ajax({
		type : "post",
		async : false,
		url : "deleteSafeAction",
		data :"fid="+fid+"",
		success : function(result, textStatus) {
			if (result.data.flag == "false") {
				alert(result.data.message);
			} else {
				window.location.reload(); 
				//initPage();
			}
		},
		error : function() {
		}
	});
	
}
//加载选项类型
function initType() {
	var html = "";
	var sql = "select FID,FTYPENAME from WQ_BASE_TOUR_SAFE_TYPE where FSTATUS=1";
	var result=tlv8.sqlQueryActionforJson("wq", sql)
		var data = result.data;
		if (typeof data == "string") {
			data = window.eval("(" + data + ")");
		}
		for (var i = 0; i < data.length; i++) {
			html += "<option value='" + data[i].FTYPENAME + "'>"
					+ data[i].FTYPENAME + "</option>";
		}
	$("#FCONTENT").html(html);
  }

//字符编码
function encodeParam(str) {
	return encodeURIComponent(encodeURIComponent(str));
}

// 获取页面值
function getSubData() {
	var rowid = getParamValueFromUrl("rowid");
	var paramList = [];
	paramList.push("mainid=" + encodeParam(rowid));
	paramList.push("createname=" + encodeParam(justep.Context.getCurrentPersonName()));
	paramList.push("createid=" + encodeParam(justep.Context.getCurrentPersonID()));
	paramList.push("content=" + encodeParam($("#FCONTENT").val()));
	paramList.push("isconform=" + encodeParam($("#FISCONFORM").val()));
	paramList.push("describe=" + encodeParam($("#DESCRIBE").val()));
	return paramList.join("&");
}


function back(){
	var rowid = getParamValueFromUrl("rowid");
	var url = "tour.html?rowid="+rowid;
	window.open(url, '_self');
	
	
}

