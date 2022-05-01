if (!justep)
	var justep = {};
justep.dialog = {
	selectPsnResult : "",
	getHeight : function() {
		var sHeight = document.body.clientHeight;
		if (window.innerHeight && window.scrollMaxY) {
			sHeight = window.innerHeight + window.scrollMaxY;
		} else if (document.body.scrollHeight > document.body.offsetHeight) {
			sHeight = document.body.scrollHeight;
		} else {
			sHeight = document.body.offsetHeight;
		}
		return sHeight;
	},
	covering : function() {
		$("#dialog_covdiv").remove();

		var covdiv = document.createElement("div");
		$(covdiv).attr("id", "dialog_covdiv");
		$(covdiv).css("width", "100%");
		$(covdiv).css("height", "100%");
		$(covdiv).css("z-index", "9998");
		$(covdiv).css("position", "absolute");
		$(covdiv).css("left", "0px");
		$(covdiv).css("top", "0px");
		$(covdiv).css("right", "0");
		$(covdiv).css("bottom", "0");
		$(covdiv).css("filter", "alpha(opacity=50)");
		$(covdiv).css("-moz-opacity", "0.5");
		$(covdiv).css("-khtml-opacity", "0.5");
		$(covdiv).css("opacity", "0.5");
		$(covdiv).css("background", "#555");
		$(covdiv).css("text-align", "center");
		$(document.body).css({
			"overflow" : "hidden"
		});
		$(document.body).append(covdiv);
	},
	creatContent : function(name, url, width, height, text) {
		if (!checkPathisHave($dpcsspath + "dialog.css"))
			createStyleSheet($dpcsspath + "dialog.css");
		width = width || "300px";
		height = height || "260px";
		var left = ($(document).width() - parseInt(width)) / 2;
		var cuScrollTop = (document.body.scrollTop || document.documentElement.scrollTop);
		var top = ($(document).height() - parseInt(height) - 10) / 2
				+ cuScrollTop;
		$("#dialog_Content").remove();
		height = parseInt(height) + 10;
		var content = document.createElement("div");
		$(content).attr("id", "dialog_Content");
		$(content).css("width", width);
		$(content).css("height", height);
		$(content).css("z-index", "9999");
		$(content).css("position", "absolute");
		$(content).css("left", left);
		$(content).css("top", top + "px");
		$(content).css("display", "none");
		$(document.body).append(content);
		var contHtml = '<ul style="background:#fff;" ';
		contHtml += 'class="mui-table-view">';
		contHtml += '<li class="mui-table-view-divider"><label';
		contHtml += 'style="font-size: 17px;">' + name + '</label></li>';
		contHtml += '<li><div class="dialog_content_view" style="padding:0px;"></div</li></ul>';
		$(content).append(contHtml);
		if (url && url != "") {
			$("#dialog_iframe").remove();
			var iframe = document.createElement("iframe");
			$(iframe).attr("id", "dialog_iframe");
			$(iframe).css("width", "100%");
			$(iframe).css("height", "98%");
			$(iframe).attr("frameborder", "0");
			$(".dialog_content_view").append(iframe);
			$(iframe).attr("src", url);
		} else if (text && text != "") {
			$(".dialog_content_view").html(text);
		}
		$("#dialog_Content").show();
	},
	creatFullScreenContent : function(name, url, callback) {
		if (!checkPathisHave($dpcsspath + "dialog.css"))
			createStyleSheet($dpcsspath + "dialog.css");
		$("#dialog_Content").remove();
		var content = document.createElement("div");
		$(content).attr("id", "dialog_Content");
		$(content).css("width", "100%");
		$(content).css("height", "100%");
		$(content).css("z-index", "9999");
		$(content).css("position", "fixed");
		$(content).css("left", "0");
		$(content).css("top", "0");
		$(content).css("right", "0");
		$(content).css("bottom", "0");
		$(content).css("display", "none");
		$(document.body).append(content);
		if (url && url != "") {
			$("#dialog_iframe").remove();
			var iframe = document.createElement("iframe");
			$(iframe).attr("id", "dialog_iframe");
			$(iframe).css("width", "100%");
			$(iframe).css("height", "100%");
			$(iframe).attr("frameborder", "0");
			$("#dialog_Content").append(iframe);
			if (callback && typeof callback == "string") {
				if (url.indexOf("?") > 0) {
					url += "&callback=" + callback;
				} else {
					url += "?callback=" + callback;
				}
			}
			$(iframe).attr("src", url);
			$("#dialog_Content").show();
		}
	},
	enPageSure : function(fn) {
		if (typeof (fn) == "function") {
			fn(this.selectPsnResult);
			this.closeDialog();
		} else {
			justep.dialog.close();
		}
	},
	close : function() {
		$("#dialog_iframe").remove();
		$("#dialog_Content").remove();
		$("#dialog_covdiv").remove();
		$(document.body).css({
			"overflow" : "auto"
		});
	},
	closeDialog : function() {
		if (window != window.top) {
			window.parent.justep.dialog.close();
		} else {
			justep.dialog.close();
		}
	},
	ensureDialog : function(callfn, result) {
		result = result || callfn;
		if (window != window.top) {
			window.parent.justep.dialog.ensureDialog(callfn, result);
		} else {
			var callbackfn = this.callback || callfn;
			if (typeof callbackfn == "string") {
				callbackfn = window.parent[callbackfn];
			}
			if (typeof callbackfn == "function") {
				callbackfn(result);
			}
		}
		this.callback = null;
		this.closeDialog();
	},
	callback : null
};

justep.dialog.openDialog = function(name, o) {
	this.covering();
	if (!o)
		o = {};
	this.callback = o.callback;
	this.creatContent(name, o.url, o.width, o.height, o.text);
};
justep.dialog.openFullScreenDialog = function(name, o) {
	this.covering();
	if (!o)
		o = {};
	this.callback = o.callback;
	this.creatFullScreenContent(name, o.url, o.callback);
};