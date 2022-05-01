$(document).ready(
		function() {
			var param = new tlv8.RequestParam();
			param.set("offerset", "0");
			param.set("limit", "8");
			tlv8.XMLHttpRequest("getWaitTaskAction", param, "post", true,
					createList);
		});

function createList(result) {
	var data = result.data.data;
	if (typeof data == "string") {
		data = window.eval("(" + data + ")");
	}
	var html = [];
	for ( var i = 0; i < data.length; i++) {
		var ms = data[i];
		html
				.push('<tr><td style="width:12px"><img name="flagImg" src="images/dot.png"/></td>');
		var sCode = ms.SCODE;
		var datetime = ms.TIME;
		datetime = tlv8.System.Date.strToDate(datetime);
		datetime = datetime.format('yyyy-MM-dd');
		html.push(' <td style="width:45px;">');
		if (sCode == '' || sCode == '普通') {
			html
					.push('<div class="className" style="cursor: default;color:#000;">');
			sCode = '普通';
		} else if (sCode == '平急') {
			html
					.push('<div class="className" style="cursor: default; color:green;">');
		} else if (sCode == '加急') {
			html
					.push('<div class="className" style="cursor: default;color:blue;">');
		} else if (sCode == '特急') {
			html
					.push('<div class="className" style="cursor: default; color:red;">');
		} else if (sCode == '特提') {
			html
					.push('<div class="className" style="cursor: default; color:red; font-weight:bold;">');
		}
		html.push('[' + sCode + ']</div></td>');
		html.push('<td class="className" width="100%">');
		html
				.push('<a class="className" href="javascript:void(0);" title="' + ms.NAME + '">');
		html.push(ms.NAME + "</a></td>");
		html.push('<td style="width:1px"></td>');
		html.push('<td style="width:61px;" class="className">');
		html.push(ms.PSNNAME + "</td>");
		html.push('<td style="width:1px"></td>');
		html.push('<td style="width:70px;" class="className">');
		html.push(datetime + '</td></tr>');
	}
	$("#taskListTable").html(html.join(""));
}
