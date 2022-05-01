$(function(){
	var rowid=tlv8.RequestURLParam.getParam("rowid");
	var title=tlv8.RequestURLParam.getParam("title");
	var name=tlv8.RequestURLParam.getParam("name");
	var scope=tlv8.RequestURLParam.getParam("scope");
	var lookperson=tlv8.RequestURLParam.getParam("lookperson");
	var number=tlv8.RequestURLParam.getParam("number");
	
	$("#history1").html(title);
	$("#history2").html(name);
	$("#history3").html(scope);
	$("#history4").html(lookperson);
	$("#history5").html(number);
	
})