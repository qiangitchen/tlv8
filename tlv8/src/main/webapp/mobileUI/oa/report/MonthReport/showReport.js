$(document).ready(function() {
	loadCount();
});

function loadCount() {
	var param = new tlv8.RequestParam();
	param.set("type", "monthReport");
	var re = tlv8.XMLHttpRequest("getPortalCountAction", param, "post");
	if (re.data.flag == "true") {
		var count = re.mr_count;// 周计划
		if (count > 0) {
			$("#empty_look").remove();
			$("<div class='countTixview'>" + count + "</div>")
					.insertBefore($("#report"));
		}
	}
}