/**
 * 
 */
/*
 * 注销函数
 */
$.logout = function() {
	$.jpolite.Data.system.User.logout(function() {
		var loginPage = "login.html?temp=" + new Date().getTime();
		window.location.href = loginPage;
	});
};

$(function() {
	// 初始化登录人员信息
	$.jpolite.clientInfo.init();
	$.jpolite.clientInfo.initClientInfo();

	$.jpolite.Data.system.Layout.loadTheme(function(data) {
		var theme = data.data;
		if (!theme || theme == "")
			return;
		$('head').find('link').each(
				function() {
					var ucss = $(this).attr('href');
					if (ucss.indexOf("/easyui.css") > 0) {
						$(this).attr(
								'href',
								cpath + '/resources/jquery-easyui/themes/'
										+ theme + '/easyui.css');
					}
				});
	});

	var funtree = [];
	// 加载功能树
	$.jpolite.Data.system.FuncTree3(function(data) {
		funtree = data.children;
	});

	$('body').desktop({
		apps : getFunctionApp(funtree, true),
		menus : [ {
			text : $.jpolite.clientInfo.personname,
			iconCls : 'icon-man',
			handler : function() {
				$('body').desktop('openApp', {
					icon : 'images/win.png',
					name : '个人信息',
					width : Math.min($(window).width()-20,1200),
					height : Math.min($(window).height()-40,660),
					id : 'mypsninfor',
					url : '/tlv8/SA/personal/cnttSrc/PersonData.html'
				})
			}
		}, {
			text : '功能菜单',
			iconCls : 'icon-more',
			menus : getFunctionMenu(funtree)
		}, {
			text : '帮助',
			iconCls : 'icon-help',
			handler : function() {
				$('body').desktop('openApp', {
					name : 'Help',
					iconCls : 'icon-help',
					width : Math.min($(window).width()-20,1200),
					height : Math.min($(window).height()-40,660),
					url : 'https://api.tulinxian.com'
				})
			}
		}, {
			text : '重新加载',
			iconCls : 'icon-reload',
			handler : function() {
				window.location.reload();
			}
		}, {
			text : '注销',
			iconCls : 'icon-lock',
			handler : function() {
				$.messager.confirm('确认', '确认当前登录吗?', function(r) {
					if (r) {
						$.logout();
					}
				});
			}
		} ],
		buttons : '#buttons'
	});
});
settingsApp = null;
function settings() {
	if (settingsApp) {
		$('body').desktop('openApp', settingsApp);
		return;
	}
	settingsApp = {
		id : 'settings',
		name : '设置',
		icon : 'images/icon/xtsz.png',
		width : 600,
		height : 400,
		onBeforeClose : function() {
			settingsApp = null;
		}
	};
	$('body').desktop('openApp', settingsApp);
	var template = '<div>'
			+ '<div region="north" style="padding:5px;height:45px;text-align:right"></div>'
			+ '<div region="south" style="text-align:right;height:45px;padding:5px"></div>'
			+ '<div region="west" title="背景" split="true" style="width:200px"><table id="settings-dl"></table></div>'
			+ '<div region="center" title="预览"><img id="settings-img" style="border:0;width:100%;height:100%"></div>'
			+ '</div>';
	var layout = $(template).appendTo('#settings');
	layout.layout({
		fit : true
	});
	var reseta = $('<a style="float:left;">复位主题</a>').appendTo(
			layout.layout('panel', 'north'));
	reseta.linkbutton();
	reseta.click(function() {
		$.messager.confirm('复位确认', '是否恢复到默认主题?', function(r) {
			if (r) {
				$.jpolite.Data.system.Layout.removeTheme(function(data) {
					window.top.location.reload();
				});
			}
		});
	});
	var combo = $('<input>').appendTo(layout.layout('panel', 'north'));
	combo.combobox({
		data : [ {
			value : 'default',
			text : 'Default',
			group : 'Base'
		}, {
			value : 'gray',
			text : 'Gray',
			group : 'Base'
		}, {
			value : 'bootstrap',
			text : 'Bootstrap',
			group : 'Base'
		}, {
			value : 'black',
			text : 'Black',
			group : 'Base'
		}, {
			value : 'metro',
			text : 'Metro',
			group : 'Base'
		}, {
			value : 'material',
			text : 'Material',
			group : 'Base'
		}, {
			value : 'material-teal',
			text : 'Material Teal',
			group : 'Base'
		},
		// {value:'metro',text:'metro',group:'Base'},
		// {value:'metro-blue',text:'metro-blue',group:'Base'},
		// {value:'metro-gray',text:'metro-gray',group:'Base'},
		// {value:'metro-green',text:'metro-green',group:'Base'},
		// {value:'metro-orange',text:'metro-orange',group:'Base'},
		// {value:'metro-red',text:'metro-red',group:'Base'},
		{
			value : 'ui-cupertino',
			text : 'ui-cupertino',
			group : 'Base'
		}, {
			value : 'ui-dark-hive',
			text : 'ui-dark-hive',
			group : 'Base'
		}, {
			value : 'ui-pepper-grinder',
			text : 'ui-pepper-grinder',
			group : 'Base'
		}, {
			value : 'ui-sunny',
			text : 'ui-sunny',
			group : 'Base'
		} ],
		width : 300,
		label : '主题: ',
		value : 'default',
		editable : false,
		panelHeight : 'auto',
		onChange : function(theme) {
			var link = $('head').find('link:first');
			link.attr('href', '../resources/jquery-easyui/themes/' + theme
					+ '/easyui.css');
			$.jpolite.Data.system.Layout.saveTheme(theme, function(data) {
			});
		}
	});
	$.jpolite.Data.system.Layout.loadTheme(function(data) {
		var theme = data.data;
		if (theme && theme != "") {
			combo.combobox('setValue', theme);
		}
	});
	$('#settings-dl').datalist({
		fit : true,
		data : [ {
			"text" : "桌面1",
			"img" : "images/bg.jpg"
		}, {
			"text" : "桌面2",
			"img" : "images/bg2.jpg"
		}, {
			"text" : "桌面3",
			"img" : "images/bg3.jpg"
		} ],
		onLoadSuccess : function() {
			$(this).datalist('selectRow', 0);
		},
		onSelect : function(index, row) {
			$('#settings-img').attr('src', row.img);
		}
	});
	$('<a style="margin-right:10px"></a>').appendTo(
			layout.layout('panel', 'south')).linkbutton(
			{
				text : '确定',
				width : 80,
				onClick : function() {
					$('body').desktop('setWallpaper',
							$('#settings-dl').datalist('getSelected').img);
					$('#settings').window('close');
				}
			})
	$('<a></a>').appendTo(layout.layout('panel', 'south')).linkbutton({
		text : '取消',
		width : 80,
		onClick : function() {
			$('#settings').window('close');
		}
	})
}

function getFunctionMenu(funtree) {
	var mu = [];
	for (var i = 0; i < funtree.length; i++) {
		var m = funtree[i];
		if (m.url && m.url != "") {
			m.handler = function(item) {
				var p = $.X.parseFunc(item);
				p.url = $.X.createFunc(p);
				p.id = $.X.getFuncID(p);
				$('body').desktop(
						'openApp',
						{
							id : p.id,
							name : p.name,
							icon : p.icon ? ("images/icon/" + p.icon)
									: "images/icon/normal.png",
							width : Math.min($(window).width()-20,1200),
							height : Math.min($(window).height()-40,660),
							url : p.url
						});
			}
		}
		if (m.children) {
			m.menus = getFunctionMenu(m.children);
		}
		mu.push(m);
	}
	return mu;
}

/**
 * 
 * @param funtree
 * @param isfolder 以文件夹形式显示
 * @returns
 */
function getFunctionApp(funtree, isfolder) {
	var mu = [];
	for (var i = 0; i < funtree.length; i++) {
		var m = funtree[i];
		if (isfolder) {
			m.name = m.text;
			m.icon = m.icon ? ("images/icon/" + m.icon)
					: "images/icon/normal.png";
			mu.push(m);
		} else {
			if (m.url && m.url != "") {
				var p = $.X.parseFunc(m);
				p.url = $.X.createFunc(p);
				p.id = $.X.getFuncID(p);
				p.name = m.text;
				p.icon = m.icon ? ("images/icon/" + m.icon)
						: "images/icon/normal.png";
				p.width = Math.min($(window).width()-20,1200);
				p.height = Math.min($(window).height()-40,660);
				mu.push(p);
			}
			if (m.children) {
				var aa = getFunctionApp(m.children);
				for (var n = 0; n < aa.length; n++) {
					mu.push(aa[n]);
				}
			}
		}
	}
	return mu;
}