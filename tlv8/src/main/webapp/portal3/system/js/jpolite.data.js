var cpath = "/tlv8";
/*
 *portal数据 
 */
if (!$.jpolite)
	$.jpolite = {};
if (!$.jpolite.Data)
	$.jpolite.Data = {
		send : function(action, data, callback, async, bean) {
			return $.ajax({
				type : "post",
				async : async ? async : false,
				url : cpath + '/' + action,
				data : data,
				success : function(data, textStatus) {
					try {
						data = window.eval("(" + data + ")");
						data = data[0];
						var status = data.status;
						if (data.data) {
							data = window["eval"]("(" + data.data + ")");
						} else {
							data = {};
						}
						data.status = status == "SUCCESS";
					} catch (e) {
					}
					callback(data);
				},
				bean : bean,
				error : function(XMLHttpRequest, textStatus, errorThrown) {

				}
			});
		}
	};
$.jpolite.Data.system = {
	User : {
		login : function(username, password, params, callback) {
			return $.jpolite.Data.send('system/User/login', $.extend(params
					|| {}, {
				username : username,
				password : password
			}), callback, true);
		},
		logout : function(callback) {
			return $.jpolite.Data.send('system/User/logout', {}, callback);
		},
		check : function(callback) {
			return $.jpolite.Data.send('system/User/check', {}, function(data) {
				if (data.status) {
					delete data.status;
					for ( var i in data) {
						$.jpolite.Data.system.User["$" + i] = data[i];
					}
					data.status = true;
					if ($.isFunction(callback))
						callback.call(this, data);
				} else {
					for ( var i in $.jpolite.Data.system.User) {
						if (i[0] == "$") {
							delete $.jpolite.Data.system.User[i];
						}
					}
					if ($.isFunction(callback))
						callback.call(this, data);
				}
			});
		},
		getAgents : function(username, password, callback) {
			return $.jpolite.Data.send('system/User/getAgents', {
				username : username,
				password : password
			}, callback, true);
		},
		changePassword : function(username, password, new_password, callback) {
			return $.jpolite.Data.send('system/User/changePassword', {
				username : username,
				password : password,
				new_password : new_password
			}, callback, true);
		},
		getOnlineCount : function(callback) {
			return $.jpolite.Data.send('system/User/getOnlineCount', {},
					callback);
		},
		getOnlineUserInfo : function(callback) {
			return $.jpolite.Data.send('system/User/getOnlineUserInfo', {},
					callback);
		},
		getOnceFunc : function(callback) {
			return $.jpolite.Data.send('system/User/getOnceFunc', {}, callback);
		},
		getLanguage : function(callback) {
			return $.jpolite.Data.send('system/User/getLanguage', {}, callback);
		},
		setLanguage : function(language, callback) {
			return $.jpolite.Data.send('system/User/setLanguage', {
				language : language
			}, callback);
		}
	},
	Layout : {
		loadTabs : function(callback) {
			return $.jpolite.Data.send('system/Layout/loadTabs', {}, callback);
		},
		saveTabs : function(layout, callback) {
			return $.jpolite.Data.send('system/Layout/saveTabs', {
				layout : layout || ""
			}, callback, true);
		},
		removeTabs : function(callback) {
			return $.jpolite.Data
					.send('system/Layout/removeTabs', {}, callback);
		},
		loadTheme : function(callback) {
			return $.jpolite.Data.send('system/Layout/loadTheme', {}, callback);
		},
		saveTheme : function(theme, callback) {
			return $.jpolite.Data.send('system/Layout/saveTheme', {
				theme : theme || ""
			}, callback, true);
		},
		removeTheme : function(callback) {
			return $.jpolite.Data.send('system/Layout/removeTheme', {},
					callback);
		},
		loadShortcuts : function(callback) {
			return $.jpolite.Data.send('system/Layout/loadShortcuts', {},
					callback);
		},
		saveShortcuts : function(shortcuts, callback) {
			return $.jpolite.Data.send('system/Layout/saveShortcuts', {
				shortcuts : shortcuts || ""
			}, callback, true);
		},
		removeShortcuts : function(callback) {
			return $.jpolite.Data.send('system/Layout/removeShortcuts', {},
					callback);
		}
	},
	FuncTree : function(callback) {
		return $.jpolite.Data.send('system/FuncTree', {}, callback);
	},
	FuncTree2 : function(callback) {
		return $.jpolite.Data.send('system/FuncTree2', {}, callback);
	},
	FuncTree3 : function(callback) {
		return $.jpolite.Data.send('system/FuncTree3', {}, callback);
	},
	en_FuncTree : function(callback) {
		return $.jpolite.Data.send('system/en_FuncTree', {}, callback);
	},
	// 20100811 MaDuo 为了让widget并发取页面
	Func : function(func, callback, async) {
		return $.jpolite.Data.send('system/Func', func, callback, async);
	}
};

if (!tlv8) {
	var tlv8 = {};
}
var $dpjspath = null;
var scripts = document.getElementsByTagName("script");
for (i = 0; i < scripts.length; i++) {
	if (scripts[i].src.toLowerCase().indexOf('/jquery.min.js') > -1) {
		$dpjspath = scripts[i].src.substring(0, scripts[i].src.length - 20);
		break;
	}
}
var $dpcsspath = $dpjspath ? $dpjspath.replace("/js/", "/css/") : null;
var $dpimgpath = $dpjspath ? $dpjspath.replace("/js/", "/image/") : null;
// alert($dpjspath);

var createJSSheet = function(jsPath) {
	var head = document.getElementsByTagName('HEAD').item(0);
	var script = document.createElement('script');
	script.src = jsPath;
	script.type = 'text/javascript';
	head.appendChild(script);
};
var createStyleSheet = function(cssPath) {
	var head = document.getElementsByTagName('HEAD').item(0);
	var style = document.createElement('link');
	style.href = cssPath;
	style.rel = 'stylesheet';
	style.type = 'text/css';
	head.appendChild(style);
};

var checkPathisHave = function(path) {
	var Hhead = document.getElementsByTagName('HEAD').item(0);
	var Hscript = Hhead.getElementsByTagName("SCRIPT");
	for (var i = 0; i < Hscript.length; i++) {
		if (Hscript[i].src == path)
			return true;
	}
	var Hstyle = Hhead.getElementsByTagName("LINK");
	for (var i = 0; i < Hstyle.length; i++) {
		if (Hstyle[i].href == path)
			return true;
	}
	return false;
};

/*
 * @陈乾 @浅入浅出提示信息 可用于非交互的错误提示 @注意：body的高度不要超过屏幕高度
 */
tlv8.showMessage = function(message) {
	
};

/*
 * 操作状态 -进度条
 */
tlv8.showSate = function(state) {
	if (state && state == true) {
		$('#content_loading').show();
	} else {
		$('#content_loading').hide();
	}
};

/*
 * c@陈乾 d@操作提示信息
 */
function sAlert(str, time) {
	var sAlertDiv = document.getElementById("msgDiv");
	if (sAlertDiv && sAlertDiv.tagName == "DIV") {
		document.body.removeChild(sAlertDiv);
	}
	var msgw, msgh, bordercolor;
	msgw = 100;
	msgh = 20;
	titleheight = 20;
	bordercolor = "#ffffff";
	titlecolor = "#FFFF66";
	var sWidth, sHeight;
	sWidth = document.body.clientWidth;
	sHeight = document.body.clientHeight;
	if (window.innerHeight && window.scrollMaxY) {
		sHeight = window.innerHeight + window.scrollMaxY;
	} else if (document.body.scrollHeight > document.body.offsetHeight) {
		sHeight = document.body.scrollHeight;
	} else {
		sHeight = document.body.offsetHeight;
	}
	var msgObj = document.createElement("div");
	msgObj.setAttribute("id", "msgDiv");
	msgObj.setAttribute("align", "center");
	msgObj.style.background = titlecolor;
	msgObj.style.border = "1px solid " + bordercolor;
	msgObj.style.position = "absolute";
	msgObj.style.left = "92%";
	msgObj.style.top = "100px";
	msgObj.style.font = "12px/1.6em Verdana, Geneva, Arial, Helvetica, sans-serif";
	msgObj.style.width = msgw + "px";
	msgObj.style.height = msgh + "px";
	msgObj.style.textAlign = "center";
	msgObj.style.lineHeight = "2px";
	msgObj.style.zIndex = "101";
	var title = document.createElement("h4");
	title.setAttribute("id", "msgTitle");
	title.setAttribute("align", "center");
	title.style.margin = "0";
	title.style.padding = "3px";
	title.style.background = titlecolor;
	title.style.filter = "progid:DXImageTransform.Microsoft.Alpha(startX=20, startY=20, finishX=100, finishY=100,style=1,opacity=75,finishOpacity=100);";
	title.style.opacity = "0.75";
	title.style.height = "20px";
	title.style.font = "12px Verdana, Geneva, Arial, Helvetica, sans-serif";
	title.style.color = "#000000";
	title.innerHTML = str;
	title.onclick = function() {
		document.getElementById("msgDiv").removeChild(title);
		document.body.removeChild(msgObj);
	};
	document.body.appendChild(msgObj);
	document.getElementById("msgDiv").appendChild(title);
	var txt = document.createElement("p");
	txt.style.margin = "1em 0";
	txt.setAttribute("id", "msgTxt");
	txt.innerHTML = str;
	time = time ? time : 500;
	setTimeout('displayActionMessage()', time);
}
var displayActionMessage = function() {
	if (document.getElementById("msgTitle"))
		document.getElementById("msgDiv").removeChild(
				document.getElementById("msgTitle"));
	if (document.getElementById("msgDiv"))
		document.body.removeChild(document.getElementById("msgDiv"));
};
