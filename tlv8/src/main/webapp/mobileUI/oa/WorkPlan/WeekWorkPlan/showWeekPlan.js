$(document).ready(function() {
	loadCount();
});

function loadCount() {
	var param = new tlv8.RequestParam();
	param.set("type", "weekPlan");
	var re = tlv8.XMLHttpRequest("getPortalCountAction", param, "post");
	if (re.data.flag == "true") {
		var wpcount = re.wp_count;// 周计划
		if (wpcount > 0) {
			$("#empty_look").remove();
			$("<div class='countTixview'>" + wpcount + "</div>")
					.insertBefore($("#weekPlan"));
		}
	}
}