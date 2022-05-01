$(function() {
	var url = tlv8.RequestURLParam.getParam("taget");
	url = J_u_decode(url);
	$("#testframe").attr("src", url);
});

function changestate(){
	$("#testframe").show();
	$("#showstate").hide();
}