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

function portalonStateChange() {
	var panles = $('#client').portal('getPanels');
	var portallets = [];
	for (var i = 0; i < panles.length; i++) {
		portallets.push(panles[i].panel("options"));
	}
	var panlesStr = JSON.stringify(portallets);
	$.ajax({
		type : "post",
		async : true,
		url : cpath + "/system/Portal/savePortal",
		data : "layout=" + portalConfig.defaultsLid + "&panles="
				+ J_u_encode(panlesStr),
		success : function(result, textStatus) {
		},
		error : function() {
		}
	});
}

function initPortallet(config, ischange) {
	$
			.ajax({
				type : "post",
				async : true,
				url : cpath + "/system/Portal/loadPortal",
				dataType : "json",
				success : function(result, textStatus) {
					var por = result;
					if (config) {
						if (config.stic) {
							var stabs = config.stic;
							var ids = stabs.ids;
							var title = stabs.title;
							if (por.length > 0 && !ischange) {
								if (por[0].SLAYOUTSET
										&& por[0].SLAYOUTSET != "") {
									portalConfig.defaultsLid = por[0].SLAYOUTSET;
								}
							}
							var lauarr = portalConfig.layouts[portalConfig.defaultsLid];
							var boHtml = '';
							for (var n = 0; n < lauarr.length; n++) {
								boHtml += '<div style="' + lauarr[n]
										+ '"></div>';
							}
							$("#client").html(boHtml);
						}
					}
					$("#client").parent().css("overflow", "hidden");
					$('#client').portal({
						border : false,
						fit : true,
						onStateChange : portalonStateChange
					});
					var lauarr = portalConfig.layouts[portalConfig.defaultsLid];
					if (por.length > 0 && por[0].SPANLES
							&& por[0].SPANLES != "") {
						var PortalLet = por[0].SPANLES;
						if (typeof PortalLet == "string") {
							PortalLet = JSON.parse(PortalLet);
						}
						for (var i = 0; i < PortalLet.length; i++) {
							var p = $(
									"<div id='" + PortalLet[i].id + "'></div>")
									.appendTo('body');
							PortalLet[i].onClose = portalonStateChange;
							p.panel(PortalLet[i]);
							if (PortalLet[i].columnIndex >= lauarr.length) {
								PortalLet[i].columnIndex = 0;
							}
							$('#client').portal('add', {
								panel : p,
								columnIndex : PortalLet[i].columnIndex
							});
							var url = $("iframe[id='" + PortalLet[i].id + "']")
									.attr("ref");
							$("iframe[id='" + PortalLet[i].id + "']").attr(
									"src", url);
						}
					} else {
						for (var i = 0; i < PortalLet_DATA.length; i++) {
							var PortalLet = PortalLet_DATA[i];
							var p = $("<div id='" + PortalLet.id + "'></div>")
									.appendTo('body');
							var url = PortalLet.arguments.url + "?PortalLetId="
									+ PortalLet.id;
							if (PortalLet.arguments.process
									&& PortalLet.arguments.process != "") {
								url += "&process="
										+ PortalLet.arguments.process;
							}
							if (PortalLet.arguments.activity
									&& PortalLet.arguments.activity != "") {
								url += "&activity="
										+ PortalLet.arguments.activity;
							}
							var framecontent = "<div style='width:100%;height:100%;padding:2px;'>"
									+ "<iframe scrolling='auto' frameborder='0' "
									+ " class='portal_body_frame' id='"
									+ PortalLet.id
									+ "' name='"
									+ PortalLet.id
									+ "' src='' ref='"
									+ url
									+ "' style='width:100%;height:100%;overflow:auto;'/></div>";
							p.panel({
								id : PortalLet.id,
								title : PortalLet.title,
								columnIndex : PortalLet.psmCount,
								content : framecontent,
								height : PortalLet.height,
								closable : true,
								collapsible : true,
								tools : [ {
									iconCls : 'icon-reload',
									handler : function(e) {
										var surl = $(this).parent().parent()
												.parent().find("iframe").attr(
														"src");
										$(this).parent().parent().parent()
												.find("iframe").attr("src",
														surl);
									}
								} ],
								onClose : portalonStateChange
							});
							$('#client').portal('add', {
								panel : p,
								columnIndex : PortalLet.psmCount
							});
							$("iframe[id='" + PortalLet.id + "']").attr("src",
									url);
						}
					}
				},
				error : function() {
				}
			});
	// $('#client').portal('resize');
	if (ischange) {
		portalonStateChange();
	}
}

function tabresize(width, height) {
	try {
		$('#client').portal('resize');
	} catch (e) {
	}
}

/*
 * 
 * myset_portal 恢复默认布局
 */
function reset_portallite_normal() {
	$.messager.confirm('复位确认', '是否恢复到默认布局?', function(r) {
		if (r) {
			$.ajax({
				type : "post",
				async : false,
				url : cpath + "/system/Portal/deletePortal"
			});
			window.top.location.reload();
		}
	});
}

/*
 * 
 * myset_portal 恢复默认主题
 */
function reset_Theme_normal() {
	$.messager.confirm('复位确认', '是否恢复到默认主题?', function(r) {
		if (r) {
			$.jpolite.Data.system.Layout.removeTheme(function(data) {
				window.top.location.reload();
			});
		}
	});
}