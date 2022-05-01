var rowid;
function initBody() {
	getDetailInfo("detailInfo", rowid, "temp");
	$("li.mui-input-group").each(function() {
		if ($(this).attr("dataid")) {
			$(this).click(function() {
				editData($(this).attr("dataid"));
			});
		}
	});
}

$(document).ready(function() {
	rowid = getParamValueFromUrl("rowid");
	var option = getParamValueFromUrl("option");
	if (!option) {
		$("#adddatabtn").remove();
	}
	initBody();
});

function newData() {
	justep.dialog.openFullScreenDialog("添加临时工作事项", {
		url : "addTempWeekPlan.html?mainid=" + rowid,
		callback : "addEditCallback"
	});
}

function editData(id) {
	justep.dialog.openFullScreenDialog("编辑临时工作事项", {
		url : "addTempWeekPlan.html?mainid=" + rowid + "&sData1=" + id,
		callback : "addEditCallback"
	});
}

function addEditCallback() {
	initBody();
}
