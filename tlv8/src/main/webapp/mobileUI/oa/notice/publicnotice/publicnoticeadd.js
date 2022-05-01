var mainData;
function initBody() {
	mainData = new tlv8.Data();
	mainData.setDbkey("oa");
	mainData.setTable("OA_PULICNOTICE");
	mainData.setFormId("DATA_FORM");
}

$(document).ready(function() {
	initBody();
	var sData1 = getParamValueFromUrl("sData1");
	$("#DATA_FORM").attr("rowid", sData1);
	mainData.refreshData();
	getOpinionData(sData1);
});

function afRefresh() {
	$("._content").html($("#FCONTENT").val());
	var href = location.href.indexOf("publicnoticeadd3.html");
	if (href > 0) {// 由于手机版目前无法回调，所以在加载第三个页面时，判断为已通过领导审核，可以发布
		mainData.setValueByName("FPUSHDATETIME", tlv8.System.Date
				.sysDateTime());
		mainData.saveData();
	} else {// 考虑回退等情况,把发布时间清空
		mainData.setValueByName("FPUSHDATETIME", "");
		mainData.saveData();
	}
}
