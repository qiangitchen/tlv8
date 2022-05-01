var togofullScreen = function(othis) {
	var SCREEN_FULL = 'layui-icon-screen-full', SCREEN_REST = 'layui-icon-screen-restore', iconElem = $(
			othis).children("i");

	if (iconElem.hasClass(SCREEN_FULL)) {
		fullScreen();
		iconElem.addClass(SCREEN_REST).removeClass(SCREEN_FULL);
	} else {
		exitScreen();
		iconElem.addClass(SCREEN_FULL).removeClass(SCREEN_REST);
	}
};
function fullScreen() {
	var ele = document.documentElement, reqFullScreen = ele.requestFullScreen
			|| ele.webkitRequestFullScreen || ele.mozRequestFullScreen
			|| ele.msRequestFullscreen;
	if (typeof reqFullScreen !== 'undefined' && reqFullScreen) {
		reqFullScreen.call(ele);
	}
	;
}
function exitScreen() {
	var ele = document.documentElement
	if (document.exitFullscreen) {
		document.exitFullscreen();
	} else if (document.mozCancelFullScreen) {
		document.mozCancelFullScreen();
	} else if (document.webkitCancelFullScreen) {
		document.webkitCancelFullScreen();
	} else if (document.msExitFullscreen) {
		document.msExitFullscreen();
	}
}
$(document).ready(function() {
	$("#top").bind("mouseout", function() {
		$("#screenbar").hide();
	});
	$("#top").bind("mouseover", function() {
		$("#screenbar").show();
	});
	$("#screenbar").hide();
	$("iframe").each(function() {
		var win = this.contentWindow;
		$(win.document).css("overflow", "hidden");
	});
	$(window).resize(function() {
		var tpcenter = $(".top-center");
		var lheight = tpcenter.parent().height();
		tpcenter.css("line-height", lheight + "px");
		tpcenter.css("font-size", (lheight * 36 / 60) + "px");
		$(".p15").each(function() {
			$(this).css("font-size", ($(this).height() * 15 / 46) + "px");
		});
	});
});