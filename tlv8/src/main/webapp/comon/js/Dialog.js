/*
 * @对话框
 * @样式模拟X5对话框
 * @依赖jQuery
 * @创建日期:2013-11-21
 * @打开方法：justep.Dialog.openDailog(name, url, param, callback)
 * @参数说明:{
 * name:对话框标题
 * url:对话框页面地址
 * param:对话框配置参数JSON{width,height,top,left}
 * callback:回调函数
 * }
 * 其他调用函数参考API标准对话框[注意域名空间]
 */
justep.Dialog = {
	id : "",
	title : "",
	width : 450,
	height : 200,
	top : 100,
	left : 200,
	callback : null,
	openDailog : function(name, url, param, callback) {
		var width = param.width || 450;
		var height = param.height || 200;
		var cuScrollTop = (document.body.scrollTop || document.documentElement.scrollTop);
		var top = (param.top || 30) + cuScrollTop;
		var left = param.left || ($(document.body).width() - width) / 2;
		this.width = width;
		this.height = height;
		this.top = top;
		this.left = left;
		this.callback = callback;
		var id = "di_" + new Date().getTime().toString();
		this.id = id;
		this.title = name;
		var rowpview = "<div id='rowpview' "
				+ "style='position:absolute;left:0px;top:0px;z-index:998;width:"
				+ ($(document.body).width())
				+ "px;height:"
				+ ($(document).height()+30)
				+ "px;background:#ddd;"
				+ "filter:alpha(opacity=20);-moz-opacity:0.2;opacity: 0.2;'></div>";
		$(document.body).append($(rowpview));// 添加笼罩层
		var dialogView = "<div class='mainDialog' id='" + id
				+ "' style='width:" + width + "px;height:" + height + "px;top:"
				+ top + "px;left:" + left + "px;text-align:center;"
				+ "vertical-align:top;position:absolute;z-index:999;'>";
		dialogView += "<div style='width:100%;height:22px;"
				+ "background:url("+cpath+"/comon/image/dialog/dialog_tbg.png);'>";
		dialogView += "<div style='margin-left:3px;margin-top:3px;float:left;width:16px;height:16px;"
				+ "background:url("+cpath+"/comon/image/dialog/dialog_ioc.png);'></div>";
		dialogView += "<b style='float:left;margin-left:5px;margin-top:4px;"
				+ "font-size:11px;color:#055a78;" + "cursor:move;width:"
				+ (width - 100) + "px;text-align:left;' " + "id='" + id
				+ "_topLine'>" + name + "</b>";
		dialogView += "<div style='margin-right:5px;margin-top:1px;float:right;width:14px;height:16px;"
				+ "background:url("+cpath+"/comon/image/dialog/dialog_col.png);cursor:hand;' id='"
				+ id + "_close'></div>";
		dialogView += "<div style='margin-right:1px;margin-top:1px;float:right;width:14px;height:16px;"
				+ "background:url("+cpath+"/comon/image/dialog/dialog_max.png);cursor:hand;' id='"
				+ id + "_max'></div>";
		dialogView += "<div style='margin-right:5px;margin-top:1px;float:right;width:14px;height:16px;"
				+ "background:url("+cpath+"/comon/image/dialog/dialog_min.png);cursor:hand;' id='"
				+ id + "_min'></div>";
		dialogView += "</div>";
		dialogView += "<div style='width:100%;height:" + (height - 25)
				+ "px;' id='" + id + "_content_main'>";
		dialogView += "<div style='width:2px;height:100%;background:#d3e2e5;float:left;' "
				+ "id='" + id + "_leftLine'></div>";
		dialogView += "<div style='width:"
				+ (width - 4)
				+ "px;height:100%;background:#fff;float:left;overflow:hidden;' "
				+ "id='" + id + "_conetnt'></div>";
		dialogView += "<div style='width:2px;height:100%;background:#d3e2e5;float:right;cursor:e-resize;' "
				+ "id='" + id + "_rightLine'></div>";
		dialogView += "</div>";
		dialogView += "<div style='width:100%;height:2px;background:#d3e2e5;line-height:2px;font-size:0px;cursor:s-resize;' "
				+ "id='" + id + "_bottomLine'>";
		dialogView += "<div style='width:2px;height:100%;line-height:2px;font-size:0px;float:left;' "
				+ "id='" + id + "_bottomLeft'></div>";
		dialogView += "<div style='width:2px;height:100%;line-height:2px;font-size:0px;cursor:se-resize;float:right;' "
				+ "id='" + id + "_bottomRight'></div>";
		dialogView += "</div>";
		dialogView += "</div>";
		$(document.body).append($(dialogView));// 创建对话框层

		var iframes = "<iframe frameborder='0' style='width:100%;height:"
				+ ($("#" + id + "_content_main").height() - 2)
				+ "px;border:0px;' id='dialog_frame'></iframe>";
		$("#" + id + "_conetnt").append($(iframes));// 创建对话框frame
		this.bindEvent(id);// 绑定相关事件

		url = this.transeUrl(url);
		$("#dialog_frame").attr("src", url);
		document.getElementById(id).callback = callback;
	},
	transeUrl : function(url){
		//路径转换[utf-8编码]
		var sURL = window.location.href;
		sURL = window.location.protocol + "//" +window.location.host + "/" + sURL.split("/")[3] + url;
		url = (url.indexOf("http") == 0) ? url : sURL;
		if(url.indexOf("?")>0){
			var reUrl = url.substring(0,url.indexOf("?"));
			var rePar = url.substring(url.indexOf("?")+1);
			var parPe = rePar.split("&");
			for(var i =0; i < parPe.length; i++){
				var perP = parPe[i];
				parPe[i] = perP.split("=")[0] + "=" + J_u_encode(J_u_decode(perP.split("=")[1]));
			}
			url = reUrl + "?" + parPe.join("&");
		}
		return url;
	},
	dailogCancel : function(dialogId) {// 关闭对话框
		var id = dialogId || $(".mainDialog").attr("id");
		var pid = parent.$(".mainDialog").attr("id"); // 这里可能出现对话框自己操作
		if (id && id != "") {
			$("#rowpview").remove();
			$("#dialog_frame").remove();
			$("#" + id).remove();
		} else {
			parent.$("#rowpview").remove();
			parent.$("#" + pid).remove();
			try {
				parent.$("#dialog_frame").remove();
			} catch (e) {
			}
		}
	},
	dailogEngin : function(data, unclose) {// 对话框确定
		var id = $(".mainDialog").attr("id");
		var pid = parent.$(".mainDialog").attr("id"); // 这里可能出现对话框自己操作
		if (id && id != "") {
			var callback = document.getElementById(pid).callback
					|| $("#" + id).attr("callback");
			if (callback && callback != "") {
				if (typeof callback == "function") {
					callback(data);
				}
			}
			if (!unclose) {
				$("#rowpview").remove();
				$("#dialog_frame").remove();
				$("#" + id).remove();
			}
		} else {
			var callback = parent.document.getElementById(pid).callback
					|| parent.$("#" + pid).attr("callback");
			if (callback && callback != "") {
				if (typeof callback == "function") {
					callback(data);
				}
			}
			if (!unclose) {
				parent.$("#rowpview").remove();
				parent.$("#" + pid).remove();
				try {
					parent.$("#dialog_frame").remove();
				} catch (e) {
				}
			}
		}
	},
	calbackFunction : function() {// 确定时执行对话框页面dailogEngin函数
		try {
			var dailogEngin = document.getElementById("dialog_frame").contentWindow.dailogEngin;
		} catch (e) {
		}
		if (dailogEngin) {
			var reData = dailogEngin();
			if (reData == false)
				return;
			this.dailogEngin(reData);
		} else {
			this.dailogEngin(null);
		}
	},
	dailogReload : function() {// 对跨框页面刷新
		try {
			parent.document.getElementById("dialog_frame").contentWindow.location
					.reload();
		} catch (e) {
			document.getElementById("dialog_frame").contentWindow.location
					.reload();
		}
	},
	bindEvent : function(id) {
		var dialog = this;
		$("#" + id + "_close").click(function() {
			dialog.dailogCancel(id);
		});
		$("#" + id + "_min").click(function() {
			dialog.minAction(id);
		});
		$("#" + id + "_max").click(function() {
			dialog.maxAction(id);
		});
		$("#" + id + "_topLine").bind("mousedown", function(event) {
			dialog.topmousedown(event, id, this);
			return false;
		});
		$("#" + id + "_topLine").bind("mouseup", function(event) {
			$("#" + id).attr("canmove", false);
			return true;
		});
		$("#" + id + "_bottomLine").bind("mousedown", function(event) {
			dialog.bottommousedown(event, id, this);
			return false;
		});
		$("#" + id + "_bottomLine").bind("mouseup", function(event) {
			$("#" + id).attr("resize", false);
			document.onselectstart = null;
			document.body.style.cursor = "default";
			return true;
		});
		$("#" + id + "_rightLine").bind("mousedown", function(event) {
			dialog.rightmousedown(event, id, this);
			return false;
		});
		$("#" + id + "_rightLine").bind("mouseup", function(event) {
			$("#" + id).attr("r-resize", false);
			document.onselectstart = null;
			document.body.style.cursor = "default";
			return true;
		});
		$("#" + id + "_bottomRight").bind("mousedown", function(event) {
			dialog.bottommousedown(event, id, this);
			dialog.rightmousedown(event, id, this);
			return false;
		});
		$("#" + id + "_bottomRight").bind("mouseup", function(event) {
			$("#" + id).attr("r-resize", false);
			$("#" + id).attr("resize", false);
			document.onselectstart = null;
			document.body.style.cursor = "default";
			return true;
		});
	},
	minAction : function(dialogId) {// 最小化
		var id = dialogId || $(".mainDialog").attr("id");
		var isMin = $("#" + id).attr("ismin");
		if (!isMin) {
			$("#" + id + "_content_main").hide();
			$("#" + id).attr("ismin", true);
		} else {
			$("#" + id + "_content_main").show();
			$("#" + id).attr("ismin", false);
		}
	},
	maxAction : function(dialogId) {// 最大化
		var id = dialogId || $(".mainDialog").attr("id");
		var isMin = $("#" + id).attr("ismax");
		if (!isMin) {
			$("#" + id).width($(document.body).width());
			$("#" + id + "_content_main")
					.height($(document.body).height() - 10);
			$("#dialog_frame").height($(document.body).height() - 12);
			$("#" + id).css("left", "0px");
			$("#" + id).css("top", "0px");
			$("#" + id).attr("ismax", true);
		} else {
			$("#" + id).width(this.width);
			$("#" + id + "_content_main").height(this.height - 24);
			$("#dialog_frame").height(this.height - 26);
			$("#" + id).css("left", this.left + "px");
			$("#" + id).css("top", this.top + "px");
			$("#" + id).attr("ismax", false);
		}
		$("#" + id + "_conetnt").width($("#" + id).width() - 4);
		$("#" + id + "_topLine").width($("#" + id).width() - 100);
	},
	maximize : function() {
		var id = $(".mainDialog").attr("id");
		$(".mainDialog").attr("width", $("#" + id).width());
		$(".mainDialog").attr("height", $("#" + id).height());
		$(".mainDialog").attr("left", $("#" + id).css("left"));
		$(".mainDialog").attr("top", $("#" + id).css("top"));
		$("#" + id).width($(document.body).width());
		$("#" + id + "_content_main").height($(document.body).height() - 10);
		$("#dialog_frame").height($(document.body).height() - 12);
		$("#" + id).css("left", "0px");
		$("#" + id).css("top", "0px");
		$("#" + id).attr("ismax", true);
		$("#" + id + "_conetnt").width($("#" + id).width() - 4);
		$("#" + id + "_topLine").width($("#" + id).width() - 100);
	},
	minimize : function() {
		var id = $(".mainDialog").attr("id");
		$("#" + id).width($(".mainDialog").attr("width"));
		$("#" + id + "_content_main").height(
				parseInt($(".mainDialog").attr("height")) - 24);
		$("#dialog_frame").height(
				parseInt($(".mainDialog").attr("height")) - 26);
		$("#" + id).css("left", $(".mainDialog").attr("left") + "px");
		$("#" + id).css("top", $(".mainDialog").attr("top") + "px");
		$("#" + id).attr("ismax", false);
		$("#" + id + "_conetnt").width($("#" + id).width() - 4);
		$("#" + id + "_topLine").width($("#" + id).width() - 100);
	},
	topmousedown : function(event, dialogId, ho) {
		var dialog = this;
		var a = event || window.Event;
		var id = dialogId || $(".mainDialog").attr("id");
		var o = $("#" + id);
		var lf = o.offset().left;
		var tp = o.offset().top;
		var mus = dialog.getMouseCoords(event);
		var dfleft = mus.x - lf;
		var dftop = mus.y - tp;
		$(document).bind("mousemove", function(event) {
			var canmove = $("#" + id).attr("canmove");
			if (!canmove)
				return;
			var a = event || window.Event;
			var mus = dialog.getMouseCoords(a);
			var tx = mus.x - dfleft, ty = mus.y - dftop;
			var newW = parseInt(o.width());
			var newH = parseInt(o.height());
			var sWidth = $(document.body).width();
			if (tx > (sWidth - newW))
				tx = (sWidth - newW);
			if (tx < 0)
				tx = 0;
			var Height = $(document.body).height();
			if (ty > (Height - newH))
				ty = (Height - newH);
			if (ty < 0)
				ty = 0;
			o.css("left", tx + "px");
			o.css("top", ty + "px");
		});
		$(document).bind("mouseup", function() {
			$("#" + id).attr("canmove", false);
			$(document).unbind("mousemove");
			document.onselectstart = null;
		});
		document.onselectstart = function() {
			return false;
		};
		$("#" + id).attr("canmove", true);
	},
	bottommousedown : function(event, dialogId, ho) {
		var dialog = this;
		var a = event || window.Event;
		var id = dialogId || $(".mainDialog").attr("id");
		var o = $("#" + id);
		var winSelf = o;
		var MCD = dialog.getMouseCoords(a);
		winSelf.startY = MCD.y;
		winSelf.startH = $("#" + id + "_content_main").height();
		dialog.addrawp();
		$(document).bind("mousemove", function(event) {
			var resize = $("#" + id).attr("resize");
			if (!resize)
				return;
			var a = event || window.Event;
			var mus = dialog.getMouseCoords(a);
			var newH = (winSelf.startH + (mus.y - winSelf.startY));
			$("#rDrawp").height(newH);
			$("#" + id + "_content_main").height(newH);
			$("#dialog_frame").height(newH);
		});
		$(document).bind("mouseup", function() {
			$("#" + id).attr("resize", false);
			document.onselectstart = null;
			document.body.style.cursor = "default";
			dialog.removerawp();
			$(document).unbind("mousemove");
			return true;
		});
		document.onselectstart = function() {
			return false;
		};
		document.body.style.cursor = "s-resize";
		$("#" + id).attr("resize", true);
	},
	rightmousedown : function(event, dialogId, ho) {
		var dialog = this;
		var a = event || window.Event;
		var id = dialogId || $(".mainDialog").attr("id");
		var o = $("#" + id);
		var winSelf = o;
		var MCD = dialog.getMouseCoords(a);
		winSelf.startX = MCD.x;
		winSelf.startW = o.width();
		dialog.addrawp();
		$(document).bind("mousemove", function(event) {
			var resize = $("#" + id).attr("r-resize");
			if (!resize)
				return;
			var a = event || window.Event;
			var mus = dialog.getMouseCoords(a);
			var newW = (winSelf.startW + (mus.x - winSelf.startX));
			var title = dialog.title;
			var mLen = parseInt(title.length) * 16;
			if (parseInt(newW) < 100 + mLen) {// 对话框缩到不能显示标题时去掉标题
				$("#" + id + "_topLine").text("");
			} else {
				$("#" + id + "_topLine").text(dialog.title);
			}
			if (parseInt(newW) < 100)
				return;
			$("#" + id + "_conetnt").width(newW - 4);
			$("#" + id + "_topLine").width(newW - 100);
			o.width(newW);
		});
		$(document).bind("mouseup", function() {
			$("#" + id).attr("r-resize", false);
			document.onselectstart = null;
			document.body.style.cursor = "default";
			dialog.removerawp();
			$(document).unbind("mousemove");
			return true;
		});
		document.onselectstart = function() {
			return false;
		};
		document.body.style.cursor = "e-resize";
		$("#" + id).attr("r-resize", true);
	},
	addrawp : function() {
		var dialog = this;
		var id = dialog.id;
		var h = $("#" + id + "_content_main").height();
		var r = "<div style='position:absolute;left:2px;top:22px;width:99%;height:"
				+ h
				+ "px;z-index:2;background:#eee;"
				+ "filter:alpha(opacity=30);-moz-opacity:0.3;opacity: 0.3;' id='rDrawp'></div>";
		$("#" + id + "_content_main").append($(r));
		$("#rDrawp").bind("mouseup", function() {
			$("#" + id).attr("r-resize", false);
			$("#" + id).attr("resize", false);
			document.onselectstart = null;
			document.body.style.cursor = "default";
			dialog.removerawp();
		});
	},
	removerawp : function() {
		$("#rDrawp").remove();
	},
	getMouseCoords : function(e) {
		var xx = e.originalEvent.x || e.originalEvent.layerX || 0;
		var yy = e.originalEvent.y || e.originalEvent.layerY || 0;
		var mus = {
			x : xx,
			y : yy
		};
		return mus;
	}
};