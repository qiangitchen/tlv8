$(function() {
	/*
	 * 验证登陆
	 */
	$.jpolite.Data.system.User.check(function(data) {
		if (data && !data.status) {
			alert("连接中断，请重新登录.");
			window.location.href = cpath + "/";
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
	
	$.jpolite.Data.system.Layout.loadTheme(function(data) {
		var theme = data.data;
		if(!theme||theme=="")return;
		$('head').find('link').each(function(){
			var ucss = $(this).attr('href');
			if(ucss.indexOf("/easyui.css")>0){
				$(this).attr('href', cpath+'/resources/jquery-easyui/themes/'+theme+'/easyui.css');
			}
		});
	});

	// 加载功能树
	$.jpolite.Data.system.FuncTree3(function(data) {
		$("#LeftMenu").sidemenu({
			data : data.children,
			onSelect : onSideMenuSelect,
			border : false
		});
	});

	$("#main_btn_del").click(function() {
		$.messager.confirm('确认', '确认关闭所有打开的功能吗?', function(r) {
			if (r) {
				var tabs = $('#mainTab').tabs('tabs');
				var m = tabs.length;
				for (var t = 1; t < m; t++) {
					$('#mainTab').tabs('close', 1);
				}
			}
		});
	});

	$("#main_btn_set").click(function() {
		$("#setdd").dialog({
			title : '设置',
			iconCls : 'icon-man',
			width : 620,
			height : 350,
			left : ($(document.body).width() - 550) / 2,
			top : ($(document.body).height() - 350) / 2,
			closed : false,
			href : 'system/options/options.layouts.html',
			modal : true,
			buttons : [ {
				text : '确定',
				handler : function() {
					window.location.reload();
				}
			}, {
				text : '取消',
				handler : function() {
					$('#setdd').dialog('close');
				}
			} ]
		});
	});

	$("#main_btn_ext").click(function() {
		$.messager.confirm('确认', '确认当前登录吗?', function(r) {
			if (r) {
				$.logout();
			}
		});
	});

	// 初始化portal
	initPortallet(portal_index);

	// 首次延时监测在线人数 1s=1秒
	$("#footer_status_online_count").oneTime("1s", function() {
		function getOnlineCount(source) {
			$.jpolite.Data.system.User.getOnlineCount(function(data) {
				if (data && data.status) {
					$(source).text(data.count);
				}
			});
		}
		;
		getOnlineCount(this);
		// 定时监测在线人数 60s=60秒
		$(this).everyTime("60s", function() {
			getOnlineCount(this);
		});
	});

});

function funviewresize(width, height) {
	try {
		$("#LeftMenu").sidemenu("resize", {
			width : width - 8
		});
	} catch (e) {
	}
}

function onTabSelect(title, index) {
	var tabs = $('#mainTab');
	var tab = tabs.tabs('getTab', index);
	var menus = $('#LeftMenu');
	if (menus.hasClass('sidemenu')) {
		var opts = menus.sidemenu("options");
		changeMenuSelect(menus, opts, tab[0].id);
	}
}

function onWestCollapse() {
	var opts = $('#LeftMenu').sidemenu('options');
	if (opts.collapsed != 'collapse') {
		$('#LeftMenu').sidemenu('collapse');
		$('#LeftMenu').sidemenu('resize', {
			width : 40
		});
	}
}

function onWestExpand() {
	var opts = $('#LeftMenu').sidemenu('options');
	if (opts.collapsed != 'expand') {
		$('#LeftMenu').sidemenu('expand');
		$('#LeftMenu').sidemenu('resize', {
			width : 200
		});
	}
}

function onSideMenuSelect(item) {
	if (item.url && item.url != "") {
		$.X.runFunc(item);
	}
}

function changeMenuSelect(menus, opts, selectId) {
	var menutrees = menus.find(".sidemenu-tree");
	menutrees.each(function() {
		var menuItem = $(this);
		changeMenuStyle(menuItem, opts, selectId);
	});

	var tooltips = menus.find(".tooltip-f");
	tooltips.each(function() {
		var menuItem = $(this);
		var tip = menuItem.tooltip("tip");
		if (tip) {
			tip.find(".sidemenu-tree").each(function() {
				changeMenuStyle($(this), opts, selectId);
			});
			menuItem.tooltip("reposition");
		}
	});
}

function changeMenuStyle(menuItem, opts, selectId) {
	menuItem.find("div.tree-node-selected").removeClass("tree-node-selected");
	var node = menuItem.tree("find", selectId);
	if (node) {
		$(node.target).addClass("tree-node-selected");
		opts.selectedItemId = node.id;
		menuItem.trigger("mouseleave.sidemenu");
	}

	changeMenuSelect(menuItem);
}

function rightmenuSelected(item) {
	if ("tab_menu_tabcloaseall" == item.id) {
		var tabs = $('#mainTab').tabs('tabs');
		var m = tabs.length;
		for (var t = 1; t < m; t++) {
			$('#mainTab').tabs('close', 1);
		}
	} else if ("tab_menu_tabcloseother" == item.id) {
		var tab = $('#mainTab').tabs('getSelected');
		var index = $('#mainTab').tabs('getTabIndex', tab);
		for (var t = 1; t < index; t++) {
			$('#mainTab').tabs('close', 1);
		}
		var tabs = $('#mainTab').tabs('tabs');
		var m = tabs.length;
		for (var t = 2; t < m; t++) {
			$('#mainTab').tabs('close', 2);
		}
	} else if ("tab_menu_tabclose" == item.id) {
		var tab = $('#mainTab').tabs('getSelected');
		var index = $('#mainTab').tabs('getTabIndex', tab);
		$('#mainTab').tabs('close', index);
	} else if ("tab_menu_tabcloseleft" == item.id) {
		var tab = $('#mainTab').tabs('getSelected');
		var index = $('#mainTab').tabs('getTabIndex', tab);
		for (var t = 1; t < index; t++) {
			$('#mainTab').tabs('close', 1);
		}
	} else if ("tab_menu_tabcloseright" == item.id) {
		var tab = $('#mainTab').tabs('getSelected');
		var index = $('#mainTab').tabs('getTabIndex', tab);
		var tabs = $('#mainTab').tabs('tabs');
		var m = tabs.length;
		var tt = index + 1;
		for (var t = tt; t < m; t++) {
			$('#mainTab').tabs('close', tt);
		}
	} else if ("tab_menu_tabrefresh" == item.id) {
		var tab = $('#mainTab').tabs('getSelected');
		tab.panel("refresh");
	}
}

if (!tlv8)
	var tlv8 = {};

tlv8.portal = {};
tlv8.portal.callBack = function(tabID, FnName, param) {
	try {
		var pTabIframe = $("iframe", "#" + tabID).get(0).contentWindow;
		var callFn = pTabIframe[FnName];
		callFn(param);
	} catch (e) {
	}
};