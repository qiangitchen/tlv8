
$(document).ready(function(){
	var rowid = tlv8.RequestURLParam.getParam("rowid");
	if (rowid && rowid != "") {
		var mainData = new tlv8.Data();
		mainData.setDbkey("system");
		mainData.setTable("CYEA_NEWS_RELEASE");// TODO:设置保存的表
		mainData.setFormId("cyea_news_release_form");// TODO:设置提交的表单
		document.getElementById("cyea_news_release_form").rowid = rowid;
		mainData.setFilter("SID='" + rowid + "'");
		mainData.refreshData();
		var cellname = "SMINIPIC";//设置附件相关字段
		var div = document.getElementById("picDemo");
		tlv8.picComponent(div, mainData, cellname, true);
		return;
	}

});