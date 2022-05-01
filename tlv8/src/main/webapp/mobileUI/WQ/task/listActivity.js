$(document).bind("pagebeforecreate", function(event, data) {
	initPage();
});

var id = getParamValueFromUrl("id");
function initPage() {
	var sql = " select sid ,sname from SA_OPPERSON order by ssequence"
	tlv8.sqlQueryActionforJson("system", sql,
			function(result) {
				var data = result.data;
				if (typeof data == "string") {
					data = window.eval("(" + data + ")");
				}
				$("#clockInCount").text(data.length);
				var html = "";
				for (var i = 0; i < data.length; i++) {
					html += "<label><input name=\"check\" type=\"checkbox\" vaa=\""+data[i].SNAME+"\" id=\""+data[i].SID+"\"/>"+data[i].SNAME+" </label>";
				}
				$("#myfrom").html(html);
				
			}, false);
	var result=tlv8.sqlQueryActionforJson("wq","select FPSNID from WQ_TASK_PSN where FTASKID='"+id+"'");
	result=result.data;
	if(result.length>0){
		for(var i in result){
			$("#"+result[i].FPSNID+"").attr("checked", "true")
		}
	}
}
//返回
function gohistory(){
	var url = "task.html?sData1="+id;
	window.open(url, '_self');
}


function insertCheckData(){
	$("input[checked]").each(function(i){   
		var paramList = [];
		paramList.push("personid=" + encodeParam($(this).attr("id")));
		paramList.push("personname=" + encodeParam($(this).attr("vaa")));
		paramList.push("id=" + encodeParam(id));
		var par=paramList.join("&");
		signSbmit(par);
		gohistory();
	});
}

//新增选择人员
function signSbmit(par) {
	$.ajax({
		type : "post",
		async : true,
		url : "insertTaskPer",
		data : par,
		success : function(result, textStatus) {
//			if (result.data.flag == "false") {
//				alert(result.data.message);
//			} else {
//				history.go(-1);
//			}
		},
		error : function() {
		}
	});
}
function queryPsn(){
//	$("#myfrom").remove();
//	$("#formout").html("<form id=\"myfrom\"> </form> ");
//	initPage();
}

//字符编码
function encodeParam(str) {
	return encodeURIComponent(encodeURIComponent(str));
}