/**
 * 
 */
function repointview() {
	$(".loginbox").css("left",
			($(window).width() - $(".loginbox").width()) / 2);
	$(".loginbox").css("top",
			($(window).height() - $(".loginbox").height()) / 2);
}

$(function() {
	repointview();
	$(window).resize(repointview);
});