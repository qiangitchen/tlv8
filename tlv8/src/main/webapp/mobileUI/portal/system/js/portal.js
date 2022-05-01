var cpath="/tlv8";
/*
 *portal数据 
 */
if (!$.jpolite)
	$.jpolite = {};
if (!$.jpolite.Data)
	$.jpolite.Data = {
		ajax : function(options) {
			function check(o, p, status, code, title, text) {
				for ( var i in p) {
					if (o[p[i]]) {
						if ($.alert && $.alert( {
							status : status,
							code : code,
							title : title,
							text : text + p[i]
						})) {
							return false;
						} else {
							delete o[p[i]];
						}
					}
				}
				return true;
			}
			function genKey() {
				var k = {
					jpolite_key_req_version : "0.1"
				};
				if (options.bean)
					k.jpolite_key_req_bean = options.bean
							.replace(/\/|\\/g, '.').replace(/^\.+|\.+$/g, "");
				return k;
			}
			options = options || {};

			if (check(options, [ 'url', 'cache', 'contentType', 'global',
					'ifModified', 'jsonp', 'password', 'processData',
					'scriptCharset', 'timeout', 'type', 'username', 'xhr' ],
					'WARN', 'system.data.js.1', 'Ajax Check',
					'Illegal parameter:')) {
				var d = options.data || {};
				if (check(d, [ 'jpolite_key_req_version',
						'jpolite_key_req_bean' ], 'WARN', 'system.data.js.2',
						'Ajax Data Check', 'Illegal property:')) {
					var o = {};
					$
							.extend(
									o,
									options,
									{
										async : options.async || false,
										type : options.type || 'post',
										dataType : options.dataType || 'json',
										url : cpath+ '/' +$.trim(options.action || 'system/Error')
												.replace(/\.|\\/g, '/')
												.replace(/^\/+|\/+$/g, ""),
										data : $.extend(d, genKey()),
										beforeSend : (options.beforeSend) ? function(
												XMLHttpRequest) {
											return options
													.beforeSend(XMLHttpRequest);
										}
												: null,
										complete : (options.complete) ? function(
												XMLHttpRequest, textStatus) {
											return options.complete(
													XMLHttpRequest, textStatus);
										}
												: null,
										dataFilter : function(data, type) {
											if (options.dataFilter) {
												return options.dataFilter(data,
														type);
											} else if (options.success
													&& type == "json") {
												var r = new Array;
												items = window["eval"]("("
														+ data + ")");
												if ($.isArray(items)) {
													for ( var i in items) {
														var item = items[i];
														if (item.status) {
															if (item.status == "SUCCESS"
																	|| item.status == "FAILURE"
																	|| item.status == "NOTLOGIN") {
																r
																		.push(items[i]);
															} else {
																$.alert
																		&& $
																				.alert( {
																					"status" : item.status,
																					"code" : item.code,
																					"title" : item.title,
																					"text" : item.text
																				});
															}
														} else {
															$.alert
																	&& $
																			.alert( {
																				status : "WARN",
																				code : "system.data.js.3",
																				title : "Ajax Data",
																				text : "Returns the wrong data"
																			});
														}
													}
												} else {
													r.push(data);
												}
												if (r.length == 0) {
													return null;
												} else if (r.length == 1) {
													return r[0];
												} else {
													$.alert
															&& $
																	.alert( {
																		status : "WARN",
																		code : "system.data.js.4",
																		title : "Ajax Data",
																		text : "Returned too much data"
																	});
													return r[0];
												}
											} else {
												return data;
											}
										},
										error : (options.error) ? function(
												XMLHttpRequest, textStatus,
												errorThrown) {
											return options.error(
													XMLHttpRequest, textStatus,
													errorThrown);
										} : null,
										success : (options.success) ? function(
												data, textStatus) {
											if (data) {
												var status = data.status;
												if (status == "NOTLOGIN") {
													window.location.href = window.location.href
															.replace(
																	/index.*\.html.*/,
																	'login.yjsf.html');
												}
												if (data.data) {
													data = window["eval"]("("
															+ data.data + ")");
												} else {
													data = {};
												}
												data.status = status == "SUCCESS";
											} else {
												data = {
													status : false
												};
											}
											return options.success(data,
													textStatus);
										}
												: null
									});

					return $.ajax(o);
				}
			}
		},
		send : function(action, data, callback, async, bean) {
			return this.ajax( {
				action : action,
				data : data,
				success : callback,
				bean : bean,
				async : async
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
			$.ajax( {
				type : "post",
				async : true,
				dataType : 'json',
				url : cpath+"/system/User/check",
				success : function(data, textStatus) {
					data = data[0];
					if (data.status == "FAILURE") {
						data.status = false;
					}
					callback(data);
				},
				error : function() {
					// 请求出错处理
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
	FuncTree : function(callback) {
		return $.jpolite.Data.send('system/FuncTree', {}, callback, true);
	},
	en_FuncTree : function(callback) {
		return $.jpolite.Data.send('system/en_FuncTree', {}, callback, true);
	},
	// 20100811 MaDuo 为了让widget并发取页面
	Func : function(func, callback, async) {
		return $.jpolite.Data.send('system/Func', func, callback, async);
	},
	//移动版功能树
	MFuncTree : function(func, callback, async) {
		return $.jpolite.Data.send('system/MFuncTree', func, callback, async);
	}
};

if (!tlv8)
	var tlv8 = {};
tlv8.logout = function() {
	if (confirm("确认要退出系统吗？")) {
		$.jpolite.Data.system.User.logout(function(data) {});
		try {
			window.tlv8YnApp.closeApp();
		} catch (e) {
			window.location.href = cpath+'/mobileUI/portal/login.html';
		}
	}
};
tlv8.checkLogin = function() {
	$.jpolite.Data.system.User.check(function(data) {
		if (data && !data.status) {
			try {
				window.tlv8YnApp.reloadLoginPage("连接中断，请重新登录.");
			} catch (e) {
				alert("连接中断，请重新登录.");
				window.open('login.html', '_self');
			}
			return false;
		}
	});
	return false;
};

tlv8.UserInfo = {
	inited : false
};
tlv8.UserInfo.init = function(options) {
	var o = {};
	$.extend(o, options, {
		url : cpath+"/system/User/initPortalInfo",
		async : false,
		type : "POST",
		dataFilter : function(data, type) {
			var items = window["eval"]("(" + data + ")");
			return items;
		},
		error : function(XMLHttpRequest, textStatus, errorThrown) {
			//alert("error");
		},
		success : function(data, textStatus) {
			var datainfo = window["eval"]("(" + data[0].data + ")");
			tlv8.UserInfo.orgfullname = datainfo.orgFullName;
			tlv8.UserInfo.personid = datainfo.personid;
			tlv8.UserInfo.personname = datainfo.personName;
			tlv8.UserInfo.username = datainfo.username;
			tlv8.UserInfo.personcode = datainfo.personcode;
			tlv8.UserInfo.positionid = datainfo.positionid;
			tlv8.UserInfo.positioncode = datainfo.positioncode;
			tlv8.UserInfo.positionname = datainfo.positionname;
			tlv8.UserInfo.positionfid = datainfo.positionfid;
			tlv8.UserInfo.positionfcode = datainfo.positionfcode;
			tlv8.UserInfo.positionfname = datainfo.positionfname;
			tlv8.UserInfo.deptid = datainfo.deptid;
			tlv8.UserInfo.deptcode = datainfo.deptcode;
			tlv8.UserInfo.deptname = datainfo.deptname;
			tlv8.UserInfo.deptfid = datainfo.deptfid;
			tlv8.UserInfo.deptfcode = datainfo.deptfcode;
			tlv8.UserInfo.deptfname = datainfo.deptfname;
			tlv8.UserInfo.orgid = datainfo.orgid;
			tlv8.UserInfo.orgcode = datainfo.orgcode;
			tlv8.UserInfo.orgname = datainfo.orgname;
			tlv8.UserInfo.orgfid = datainfo.orgfid;
			tlv8.UserInfo.orgfcode = datainfo.orgfcode;
			tlv8.UserInfo.orgfname = datainfo.orgfname;
			tlv8.UserInfo.ognid = datainfo.ognid;
			tlv8.UserInfo.ogncode = datainfo.ogncode;
			tlv8.UserInfo.ognname = datainfo.ognname;
			tlv8.UserInfo.ognfid = datainfo.ognfid;
			tlv8.UserInfo.ognfcode = datainfo.ognfcode;
			tlv8.UserInfo.ognfname = datainfo.ognfname;
			tlv8.UserInfo.personfid = datainfo.personfid;
			tlv8.UserInfo.personfcode = datainfo.personfcode;

			tlv8.UserInfo.personfname = datainfo.personfname;
			tlv8.UserInfo.uiserverremoteurl = datainfo.uiserverremoteurl;

			tlv8.UserInfo.userName = datainfo.username;
			tlv8.UserInfo.businessId = datainfo.businessid;
			tlv8.UserInfo.locale = datainfo.locale;
			tlv8.UserInfo.hostPath = window.location.protocol + "//"
					+ window.location.host + "/"
					+ window.location.pathname.split("/")[1];
			$("#userInfo").text(datainfo.personName);
			tlv8.UserInfo.inited = true;
		}
	});
	$.ajax(o);
	return tlv8.UserInfo;
};