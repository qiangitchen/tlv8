//待办事项
function loadTopTip() {
	$.ajax({
		type : "post",
		async : true,
		dataType : "json",
		url : "/tlv8/getWaitTaskAction",//待办任务
		success : function(result, textStatus) {
			if (result.count) {
				$("#taskCount").text(result.count);
			}
			delete result;
		},
		error : function() {
			// 请求出错处理
		}
	});
	$.ajax({
		type : "post",
		async : true,
		dataType : "json",
		url : "/tlv8/getPortalNotesAction",//通知
		success : function(result, textStatus) {
			var news = result.data;
			if (typeof news.data == "string") {
				news.data = window.eval("(" + news.data + ")");
			}
			$("#notesCount").text(news.data.length);
			delete news;
			delete result;
		},
		error : function() {
			// 请求出错处理
		}
	});
	$.ajax({
		type : "post",
		async : true,
		dataType : "json",
		url : "/tlv8/loadNewsAction",//新闻
		success : function(result, textStatus) {
			var news = result.data;
			if (typeof news.data == "string") {
				news.data = window.eval("(" + news.data + ")");
			}
			$("#newsCount").text(news.data.length);
			delete news;
			delete result;
		},
		error : function() {
			// 请求出错处理
		}
	});
	$.ajax({
		type : "post",
		async : true,
		dataType : "json",
		url : "/tlv8/getPortalBofoAction",//发帖
		success : function(result, textStatus) {
			var news = result.data;
			if (typeof news.data == "string") {
				news.data = window.eval("(" + news.data + ")");
			}
			$("#topicCount").text(news.data.length);
			delete news;
			delete result;
		},
		error : function() {
			// 请求出错处理
		}
	});
	setTimeout(loadTopTip, 60 * 1000);
}
loadTopTip();

//实时监控
function refreshMonitorInfo(){
	  $.ajax({
			type : "post",
			async : true,
			dataType : "json",
			url : "/tlv8/monitor/CPUPerc",
			success : function(data, textStatus) {
				try {
					data = data.data;
		  			 if(typeof data == "string"){
		  			 	data = window.eval("("+data+")");
		  			 }
					var usedpex = parseFloat(data.usedpex)||0;
					if(usedpex<30){
						$("#cpuprs").attr("class","layui-progress-bar");
					}else if(usedpex<80){
						$("#cpuprs").attr("class","layui-progress-bar layui-bg-blue");
					}else{
						$("#cpuprs").attr("class","layui-progress-bar layui-bg-red");
					}
					layui.element.progress("progress-cpuprs",usedpex+"%");
				} catch (e) {
				}
				delete data;
			}
		});
	  $.ajax({
			type : "post",
			async : true,
			dataType : "json",
			url : "/tlv8/monitor/MemoryInfo",
			success : function(data, textStatus) {
				try {
					data = data.data;
		  			if(typeof data == "string"){
		  			 	data = window.eval("("+data+")");
		  			}
					var usedpex = parseFloat(data.usedpex)||0;
					if(usedpex<50){
						$("#memprs").attr("class","layui-progress-bar");
					}else if(usedpex<80){
						$("#memprs").attr("class","layui-progress-bar layui-bg-blue");
					}else{
						$("#memprs").attr("class","layui-progress-bar layui-bg-red");
					}
					layui.element.progress("progress-memprs",usedpex+"%");
				} catch (e) {
				}
				delete data;
			}
		});
	  setTimeout(refreshMonitorInfo,1000);
}
refreshMonitorInfo();