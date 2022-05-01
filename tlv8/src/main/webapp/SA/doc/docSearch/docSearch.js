function queryDoc() {// TODO:查询按钮动作
	var queryText = document.getElementById("queryText").value;
	if (queryText && queryText != "") {
		queryDocAction(queryText);
	}
}

function queryDocAction(queryText) {// TODO:查询动作
	var param = new tlv8.RequestParam();
	param.set("keyWord", queryText);
	tlv8.XMLHttpRequest("queryDocSearch?page=1&rows=100", param, "POST",
			true, innerbackData);
}

function EnterIpt(ev, obj) {
	var event = ev || window.event;
	if (event.keyCode == 13) {
		if (obj.value && obj.value != "") {
			queryDocAction(obj.value);
		}
	}
}

function innerbackData(r) {
	var reData = r.data;
	if (reData.flag == "false") {
		alert(reData.essage);
	} else {
		document.getElementById("result-DocList").innerHTML = reData.data;
		$("#result-DocList").find("table").attr("class","layui-table");
		$("#result-DocList").find("table").removeAttr("style");
	}
	// alert(reData.data);
}