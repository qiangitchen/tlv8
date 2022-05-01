/*
 * 加载菜单
 */
$(document).ready(function() {
	//待办批示件
	$.ajax({
		type : "post",
		async : true,
		url : "GetWaitInstructionsCountAction",
		success : function(result, textStatus){
			if(result.count){
				if(result.count>0){
					$("#empty_backlog_Instructions").remove();
					var count = result.count;
					$("<div class='countTixview'>" + count + "</div>")
					.insertBefore($("#dbpsjlist"));
				}
			}
		}
	});
});
