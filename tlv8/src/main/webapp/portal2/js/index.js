$(function() {
	/*
	 * 验证登陆
	 */
	$.jpolite.Data.system.User.check(function(data) {
		if (data && !data.status) {
			alert("连接中断，请重新登录.");
			window.location.href = cpath+"/";
			return false;
		}
	});

	/*
	 * 注销函数
	 */
	$.logout = function() {
		$.jpolite.Data.system.User.logout(function() {
			var loginPage = "login.html?temp="
					+ new Date().getTime();
			window.location.href = loginPage;
		});
	};

	// 初始化登录人员信息
	$.jpolite.clientInfo.init();
	$.jpolite.clientInfo.initClientInfo();

	/*
	 * 初始化功能树信息
	 */
	$.initFunctionTree = function() {
		$.jpolite.Data.system.FuncTree2(function(data) {
			var funs = data.data;
			if(typeof funs=="string"){
				funs = window.eval("("+funs+")");
			}
			initFunctionNav(funs);
		});
	};
	$.initFunctionTree();

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
		$("#sys_logout").click(function() {
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

function initFunctionNav(data) {
	$("#LAY-system-side-menu").html("");
	var html = "";
	for ( var i in data) {
		if (data[i].pId == "") {
			html = "<li class='layui-nav-item' id='"+data[i].id+"'>"
					+ "<a href='javascript:;' lay-tips='" + data[i].label
					+ "' lay-direction='2'><i class='layui-icon "+(data[i].layuiIcon?data[i].layuiIcon:'layui-icon-app')+"'></i>"
					+ " <cite>"+data[i].label+"</cite> "
					+ "</a></li>";
			$("#LAY-system-side-menu").append(html);
		} else {
			if (data[i].path) {
				var atar = [];
				for(var k in data[i]){
					atar.push(k+"='"+data[i][k]+"'");
				}
				html = "<dl class='layui-nav-child'>"
					+ "<dd id='"+data[i].id+"'><a href='javascript:;' lay-href='"+data[i].path+"' "+atar.join(" ")+">"+data[i].label+"</a></dd></dl>";
				$("#" + data[i].pId).append(html);
			} else {
				html = "<dl class='layui-nav-child'>"
					+ "<dd id='"+data[i].id+"'><a href='javascript:;'>"
					+ data[i].label
					+ "</a></dd></dl>";
				$("#" + data[i].pId).append(html);
			}
		}
	}
}

tlv8.portal = {};
tlv8.portal.callBack = function(tabID, FnName, param) {
	try {
		var pTabIframe = layui.index.getTabsPageWin(tabID);
		var callFn = pTabIframe[FnName];
		callFn(param);
	} catch (e) {
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