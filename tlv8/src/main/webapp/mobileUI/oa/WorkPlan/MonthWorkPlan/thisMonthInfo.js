$(document).ready(function() {
	var sData1 = getParamValueFromUrl("rowid");
	getDetailInfo(sData1);
});

function getDetailInfo(rowid) {
	var param = new tlv8.RequestParam();
	param.set("rowid", rowid);
	var re = tlv8.XMLHttpRequest("getMonthWorkPlanDetailAction", param,
			"post");
	if (re.data.flag == "true") {
		var data = re.data.data;
		if (typeof data == "string") {
			data = window.eval("(" + data + ")");
		}
		if (data.length > 0) {
			$("#title_val").html("(" + data.length + ")");
			$("#detailInfo").html(createDetailList(data));
		} else {
			$("#detailInfo")
					.html("<div style='color:red;width:100%;padding:10px;text-align:center;'>未录入或没有满足条件的工作计划安排!</div>");
		}
	} else {
		alert("获取计划详细信息失败");
	}
}

/**
 * 创建工作计划明细列表
 * 
 * @param data
 * @returns {String}
 */
function createDetailList(data) {
	if (typeof data == "string") {
		data = window.eval("(" + data + ")");
	}
	var html = '<ul data-role="listview" class="ui-listview mui-table-view">';
	for (var i = 0; i < data.length; i++) {
		var count = i + 1;
		html += '<li class="mui-table-view-divider"><label style="font-size: 17px;">计划指标'
				+ count + '</label></li>';
		html += '<li class="mui-input-group" style="padding: 10px;line-height: 25px;"><table><tr><td width="100px">'
				+ '<label style="font-weight: bold;">重要级别:</label>'
				+ '</td><td>'
				+ '<label class="ui-lable-info">'
				+ data[i].IMP
				+ '</label>' + '</td></tr>';
		html += '<tr><td width="100px">'
				+ '<label style="font-weight: bold;">目标内容:</label>'
				+ '</td><td>'
				+ '<label class="ui-lable-info">'
				+ data[i].CONTENT + '</label>' + '</td></tr>';
		html += '<tr><td width="100px">'
				+ '<label style="font-weight: bold;">方法和措施:</label>'
				+ '</td><td>'
				+ '<label class="ui-lable-info">'
				+ data[i].FUN
				+ '</label>' + '</td></tr>';
		html += '<tr><td width="100px">'
				+ '<label style="font-weight: bold;">完成情况:</label>'
				+ '</td><td>'
				+ '<label class="ui-lable-info">'
				+ data[i].COM
				+ '</label>' + '</td></tr></table></li>';
	}
	html += '</ul>';
	return html;
}