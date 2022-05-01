$(document).ready(function() {
	loadWaitCount();
});
function loadWaitCount() {
	$.ajax( {
		type : "post",
		async : true,
		url : "getReceiveMailCountAction",
		success : function(result, textStatus) {
			if (result.count) {
				if (result.count > 0) {
					$("#empty_waitemail").remove();
					$("<div class='countTixview'>" + result.count + "</div>")
							.insertBefore($("#waitemail"));
					offCount += parseInt(result.count);
				}
			}
		},
		error : function() {
			// 请求出错处理
	}
	});
}