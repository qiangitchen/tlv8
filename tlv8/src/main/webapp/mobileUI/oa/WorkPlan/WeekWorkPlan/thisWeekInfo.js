function initBody(rowid) {
	getDetailInfo("detailInfo", rowid, "this");
	$("li.mui-input-group").each(function(){
		if($(this).attr("dataid")){
			$(this).click(function(){
				editData($(this).attr("dataid"));
			});
		}
	});
}
var sData1;
$(document).ready(function() {
	sData1 = getParamValueFromUrl("rowid");
	var option = getParamValueFromUrl("option");
	if (!option) {
		$("#adddatabtn").remove();
	}
	initBody(sData1);
});

function newData() {
	justep.dialog.openFullScreenDialog("添加本周目标", {
		url : "addThisWeekPlan.html?mainid=" + sData1,
		callback : "addEditCallback"
	});
}

function editData(id) {
	justep.dialog.openFullScreenDialog("编辑本周目标", {
		url : "addThisWeekPlan.html?mainid=" + sData1 + "&sData1=" + id,
		callback : "addEditCallback"
	});
}

function addEditCallback() {
	initBody(sData1);
}
