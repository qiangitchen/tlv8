$(function() {
	/*
	 * 验证登陆
	 */
	$.jpolite.Data.system.User.check(function(data) {
		if (data && !data.status) {
			alert("连接中断，请重新登录.");
			window.location.href = "login.html?temp=" + new Date().getTime();
			return false;
		}
	});

	/*
	 * 注销函数
	 */
	$.logout = function() {
		$.jpolite.Data.system.User.logout(function() {
			var loginPage = "login.html?temp=" + new Date().getTime();
			window.location.href = loginPage;
		});
	};

	// 初始化登录人员信息
	$.jpolite.clientInfo.init();
	$.jpolite.clientInfo.initClientInfo();

	$(".layui-badge-dot").oneTime("1s", function() {
		function getSysRemindCount(source) {
			$.ajax({
				type : "post",
				async : false,
				url : "getSysRemindCount",
				dataType: "json",
				success : function(rd, textStatus) {
					if(rd.data.flag="true"){
						var srmcount = parseInt(rd.data.data);
						//$(source).text(srmcount);
						if(srmcount>0){
							$(source).show();
						}else{
							$(source).hide();
						}
					}
					delete rd;
				}
			});
		}
		//getSysRemindCount(this);
		// 定时监测 60s=60秒
		$(this).everyTime("60s", function() {
			//getSysRemindCount(this);
		});
	});

	// 接管功能发过来的消息
	if ($.crossdomain) {
		$.crossdomain.bindFromClient(function(cmd) {
			if (cmd == "removeTab") {
				$.jpolite.removeTab($.jpolite.getTabID());
			}
		});
	}
	
	// 绑定首页按钮事件
	$.bindItemEvent = function() {
		$(".login-out").click(function() {
			layui.layer.confirm('确定注销登录吗?', function(r) {
				if (r) {
					$.logout();
				}
			});
		});
	};
	$.bindItemEvent();

	// 禁止回退键“返回”页面
	$.disbackspace = function() {
		document.onkeydown = function(event) {
			event = event || window.event;
			var elem = event.srcElement ? event.srcElement : event.target;
			if (event.keyCode == 8) {
				var name = elem.nodeName;
				if (name != 'INPUT' && name != 'TEXTAREA') {
					if (event.preventDefault) {
						event.preventDefault();
					} else {
						event.returnValue = false;
					}
					return false;
				}
				var type_e = elem.type.toUpperCase();
				if (name == 'INPUT'
						&& (type_e != 'TEXT' && type_e != 'TEXTAREA'
								&& type_e != 'PASSWORD' && type_e != 'FILE')) {
					if (event.preventDefault) {
						event.preventDefault();
					} else {
						event.returnValue = false;
					}
					return false;
				}
				if (name == 'INPUT'
						&& (elem.readOnly == true || elem.disabled == true)) {
					if (event.preventDefault) {
						event.preventDefault();
					} else {
						event.returnValue = false;
					}
					return false;
				}
			}
		};
	};
	$.disbackspace();
});

function selecthome() {
	//$('#mainTabs').tabs('select', "t001");
}

tlv8.portal = {};
tlv8.portal.callBack = function(tabID, FnName, param) {
	try {
		var ff = $(".layui-tab-content").find("iframe[id='"+tabID+"']");
		if (ff.length > 0) {
			var fm = ff[0].contentWindow;
			var callFn = fm[FnName];
			callFn(param);
		}
	} catch (e) {
	}
};

/**
 * dialog
 */
tlv8.portal.dailog = {
	transeUrl : function(url) {
		// 路径转换[utf-8编码]
		if (url.indexOf("http://") < 0 && url.indexOf("https://") < 0
				&& url.indexOf(cpath) != 0) {
			url = cpath + url;
		}
		if (url.indexOf("?") > 0) {
			var reUrl = url.substring(0, url.indexOf("?"));
			var rePar = url.substring(url.indexOf("?") + 1);
			var parPe = rePar.split("&");
			for (var i = 0; i < parPe.length; i++) {
				var perP = parPe[i];
				parPe[i] = perP.split("=")[0] + "="
						+ J_u_encode(J_u_decode(perP.split("=")[1]));
			}
			url = reUrl + "?" + parPe.join("&");
		}
		return url;
	},
	/**
	 * @name tlv8.portal.dailog.openDailog
	 * @description 打开对话框
	 * @param {string}
	 *            name
	 * @param {string}
	 *            url
	 * @param {number}
	 *            width
	 * @param {number}
	 *            height
	 * @param {function}
	 *            callback
	 * @param {object}
	 *            itemSetInit -{refreshItem:true,enginItem:true,CanclItem:true}
	 * @param {boolean}
	 *            titleItem -为false时掩藏标题栏
	 * @param {object}
	 *            urlParam -JS任意类型可以直接传递到对话框页面 对话框页面通过函数getUrlParam获取
	 */
	openDailog : function(name, url, width, height, callback, itemSetInit,
			titleItem, urlParam) {
		url = this.transeUrl(url);
		tlv8.portal.dailog.callback = callback;
		var dwin = window;
		if (window.top.$("#windowdialogIframe").size() == 0) {
			dwin = window.top;
		}
		dwin.$("#msgObjDiv").remove();
		dwin.$("#dailogmsgDiv").remove();
		if (dwin.$("<div></div>").dialog) {
			dwin.$("#windowdialogIframe").remove();
			dwin.$("#gldd").remove();
			var dihtml = '<iframe id="windowdialogIframe" frameborder="0" style="width: 100%;height: 100%;"></iframe>';
			var globaldialog = dwin
					.$('<div id="gldd" style="width:0px;height:0px;overflow:hidden;">'
							+ dihtml + '</div>');
			dwin.$(dwin.document.body).append(globaldialog);
			var dlwidth = parseInt(width);
			var dlheight = parseInt(height);
			if (dlwidth > dwin.$(dwin.document).width()) {
				dlwidth = dwin.$(dwin.document).width() - 40;
			}
			if (dlheight > dwin.$(dwin.document).height()) {
				dlheight = dwin.$(dwin.document).height() - 40;
			}
			var dlparam = {
				title : name,
				width : dlwidth,
				height : dlheight,
				closed : false,
				cache : false,
				modal : true,
				collapsible : true,
				minimizable : false,
				maximizable : true,
				resizable : true,
				onResize : function(w, h) {
					if ($.browser.msie) {
						var fIEVersion = parseFloat($.browser.version);
						if (fIEVersion < 9) {
							var dlfram = dwin
									.$("iframe[id='windowdialogIframe']");
							dlfram.height(dlfram.parent().height());
						}
					}
				},
				onClose : function() {
					dwin.$("#windowdialogIframe").remove();
					dwin.$("#gldd").remove();
				}
			};
			if (itemSetInit == false) {
				dlparam.buttons = undefined;
			} else {
				dlparam.buttons = [
						{
							text : '确定',
							handler : function() {
								var dlw = dwin.document
										.getElementById("windowdialogIframe").contentWindow;
								if (dlw.dailogEngin) {
									var re = dlw.dailogEngin();
									if ((typeof re == "boolean") && re == false) {

									} else {
										if (callback) {
											callback(re);
										}
										dwin.$('#gldd').dialog('close');
										dwin.$("#windowdialogIframe").remove();
										dwin.$("#gldd").remove();
									}
								} else {
									if (callback) {
										callback(re);
									}
									dwin.$('#gldd').dialog('close');
									dwin.$("#windowdialogIframe").remove();
									dwin.$("#gldd").remove();
								}
							}
						}, {
							text : '取消',
							handler : function() {
								dwin.$('#gldd').dialog('close');
								dwin.$("#windowdialogIframe").remove();
								dwin.$("#gldd").remove();
							}
						} ];
			}
			globaldialog.dialog(dlparam);
			dwin.$("#windowdialogIframe").attr("src", url);
			try {
				var func_iframe = dwin.document
						.getElementById("windowdialogIframe");
				if (func_iframe.attachEvent) {
					func_iframe.attachEvent("onload", function() {
						try {
							var dialogWin = func_iframe.contentWindow;
							if (dialogWin.getUrlParam) {
								dialogWin.getUrlParam(urlParam);
							}
						} catch (e) {
						}
					});
				} else {
					func_iframe.onload = function() {
						try {
							var dialogWin = func_iframe.contentWindow;
							if (dialogWin.getUrlParam) {
								dialogWin.getUrlParam(urlParam);
							}
						} catch (e) {
						}
					};
				}
			} catch (e) {
			}
		}
	}
};

function sAlert(str, time){
	layui.layer.msg(str);
}

/*
 * 操作状态 -loading
 */
tlv8.showSate = function(state) {
	if (state && state == true) {
		tlv8.loadindex = layui.layer.load(2);
	} else {
		if(tlv8.loadindex){
			layui.layer.close(tlv8.loadindex);
			delete tlv8.loadindex;
		}
	}
};

/**
 * @name J_u_encode
 * @description 将字符串UTF-8编码
 * @param {string}
 *            str
 * @returns {string}
 */
var J_u_encode = function(str) {
	return encodeURIComponent(encodeURIComponent(str));
};

/**
 * @name J_u_decode
 * @description 将字符串UTF-8解码
 * @param {string}
 *            str
 * @returns {string}
 */
var J_u_decode = function(str) {
	try {
		return decodeURIComponent(decodeURIComponent(str));
	} catch (e) {
		return str;
	}
};