/*-------------------------------------------------------------------------------*\
|  Subject:  J YJsf 流程组件flw
|  Author:   www.tulinxian.com 技术研发部
 * Principal: 陈乾
 * CopyRight: www.tulinxian.com
|  Created:  2012-3-12
|  Version:  v1.1.1
\*-------------------------------------------------------------------------------*/
/*
 setting = {
 autosaveData : true, //自动保存数据
 autoclose : true, //自动关闭页面
 autofilter : true, //自动过滤数据
 autorefresh: true, //自动刷新数据
 autoselectext : true, //自动获取执行人
 item : {//按钮配置
 audit : true, //审批
 back : true,//流转按钮
 out : true,//流转按钮
 transmit : true,//转发按钮
 pause : true,//暂停按钮
 stop : true//终止按钮
 },
 auditParam : {//审批信息配置
 busiDataKey : "dycrm", //业务库数据连接
 busiTable : "", //业务表名
 auditTable : "", //审核意见表
 billidRe : "FFLOWID", //外键字段
 FAGREETEXTRe : "fFLOWOPINION", //意见字段
 isRequired : false //是否为必须填写意见
 }
 }
 */
//-------------------------------------------------------------------------------------------------------------------\\
/*
 * 
 * @param {Object} div 流程组件容器:div @param {Object} data 流程主数据:tlv8.data
 * @param {Object} setting 流程配置{autosaveData : true,autoclose :
 * true,autoselectext : true}
 */
tlv8.flw = function(div, data, setting) {
	if (typeof div == "string") {
		try {
			div = document.getElementById(div);
		} catch (e) {
			mAlert("参数div:" + e.message);
			return false;
		}
	}
	var dsetting = {
		autosaveData : false, // 自动保存数据
		autoclose : true, // 自动关闭页面
		autofilter : true, // 自动过滤数据
		autorefresh : false, // 自动刷新数据
		autoselectext : true,// 自动获取执行人
		item : {
			audit : false, // 审批
			back : true,// 回退按钮
			out : true,// 流转按钮
			transmit : false,// 转发按钮
			pause : false,// 暂停按钮
			stop : false
		// 终止按钮
		}
	};
	$.extend(true, dsetting, setting);
	this.id = div.id;
	this.Dom = div;
	this.setting = dsetting;
	this.data = data;
	this.processID = "";
	this.flowID = getParamValueFromUrl("flowID")
			|| getParamValueFromUrl("process");
	this.taskID = getParamValueFromUrl("taskID")
			|| getParamValueFromUrl("task");
	this.sData1 = getParamValueFromUrl("sData1");
	div.flw = this;
	var self = this;
	$(div).click(function() {
		self.doAction();
	});
};

tlv8.flw.prototype.setItemStatus = function(item) {
	this.setting.item = item;
};

/**
 * 按钮动作
 */
tlv8.flw.prototype.doAction = function() {
	var self = this;
	self.closeRightMenu();// 创建之前先删除确保唯一
	var div_bg = document.createElement("div");// 背景笼罩成
	$(div_bg).attr("id", "process_select_dialog_bg");
	$(div_bg).css("width", "100%");
	$(div_bg).css("height", $(document).height());
	$(div_bg).css("z-index", 1000);
	$(div_bg).css("position", "absolute");
	$(div_bg).css("left", "0px");
	$(div_bg).css("top", "0px");
	$(div_bg).css("right", "0px");
	$(div_bg).css("bottom", "0px");
	$(div_bg).css("filter", "alpha(opacity=50)");
	$(div_bg).css("-moz-opacity", "0.5");
	$(div_bg).css("-khtml-opacity", "0.5");
	$(div_bg).css("opacity", "0.5");
	$(div_bg).css("background", "#555");
	$(div_bg).css("text-align", "center");
	$(document.body).css("overflow", "hidden");
	$(document.body).append(div_bg);
	var cuScrollTop = (document.body.scrollTop || document.documentElement.scrollTop);
	var top = 45 + cuScrollTop;
	var div_content = document.createElement("div");// 内容层
	$(div_content).attr("id", "process_select_dialog_content");
	$(div_content).attr("class", "ui-page-active ui-corner-all mui-card");
	$(div_content).css("width", "130px");
	$(div_content).css("z-index", 1001);
	$(div_content).css("position", "absolute");
	$(div_content).css("right", "10px");
	$(div_content).css("top", top + "px");
	$(div_content).css("border", "0px");
	var flowpaten = getParamValueFromUrl("activity-pattern");
	var html = [];
	html
			.push("<div data-role='content' class='ui-content ui-body-c' style='padding:10px;'>");
	if (self.setting.item.audit && flowpaten != "detail") {
		html
				.push("<a href='#' onclick='document.getElementById(\""
						+ self.Dom.id
						+ "\").flw.flowAudit()' class='ui-btn ui-btn-up-b  ui-btn-corner-all mui-btn mui-btn-purple' style='width:110px; margin:2px;'>");
		html.push("<span style='padding:0.5em;display:block'>填写意见</span></a>");
	}
	if (self.setting.item.out && flowpaten != "detail") {
		html
				.push("<a href='#' onclick='document.getElementById(\""
						+ self.Dom.id
						+ "\").flw.flowout()' class='ui-btn ui-btn-up-b  ui-btn-corner-all mui-btn mui-btn-success' style='width:110px; margin:2px;'>");
		html.push("<span style='padding:0.5em;display:block'>流转</span></a>");
	}
	if (self.setting.item.transmit && flowpaten != "detail") {
		html
				.push("<a href='#' onclick='document.getElementById(\""
						+ self.Dom.id
						+ "\").flw.flowtransmit()' class='ui-btn ui-btn-up-b ui-btn-corner-all mui-btn mui-btn-primary' style='width:110px; margin:2px;'>");
		html.push("<span style='padding:0.5em;display:block'>转发</span></a>");
	}
	if (self.setting.item.back && flowpaten != "detail") {
		html
				.push("<a href='#' onclick='document.getElementById(\""
						+ self.Dom.id
						+ "\").flw.flowback()' class='ui-btn ui-btn-up-b ui-btn-corner-all mui-btn mui-btn-warning' style='width:110px; margin:2px;'>");
		html.push("<span style='padding:0.5em;display:block'>回退</span></a>");
	}
	if (self.setting.item.pause && flowpaten != "detail") {
		html
				.push("<a href='#' onclick='document.getElementById(\""
						+ self.Dom.id
						+ "\").flw.flowpause()' class='ui-btn ui-btn-up-b ui-btn-corner-all mui-btn mui-btn-danger' style='width:110px; margin:2px;'>");
		html.push("<span style='padding:0.5em;display:block'>暂停</span></a>");
	}
	if (self.setting.item.stop && flowpaten != "detail") {
		html
				.push("<a href='#' onclick='document.getElementById(\""
						+ self.Dom.id
						+ "\").flw.flowstop()' class='ui-btn ui-btn-up-b ui-btn-corner-all mui-btn mui-btn-danger' style='width:110px; margin:2px;'>");
		html.push("<span style='padding:0.5em;display:block'>终止</span></a>");
	}
	html
			.push("<a href='#' onclick='document.getElementById(\""
					+ self.Dom.id
					+ "\").flw.viewChart()' class='ui-btn ui-btn-up-b ui-btn-corner-all mui-btn mui-btn-chatbot' style='width:110px; margin:2px;'>");
	html.push("<span style='padding:0.5em;display:block'>查看流程图</span></a>");
	html.push("</div>");
	$(div_content).html(html.join(""));
	$(document.body).append(div_content);
	$(div_bg).bind("click", function() {
		self.closeRightMenu();
	});
};

tlv8.flw.prototype.closeRightMenu = function() {
	$("#process_select_dialog_bg").remove();
	$("#process_select_dialog_content").remove();
	$(document.body).css("overflow", "auto");
};

tlv8.flw.prototype.flowAudit = function() {
	tlv8.writeOpinion("");
};

/*
 * 启动流程
 */
tlv8.flw.prototype.flowstart = function(billid, callback) {
	var flowCompent = this;
	callback = callback || billid;
	var sData1 = (typeof billid == "string") ? billid : this.sData1;
	if (!sData1 || sData1 == "") {
		sData1 = flowCompent.data.saveData();
		if (sData1) {
			flowCompent.sData1 = sData1;
		}else{
			return false;
		}
	}
	var param = new tlv8.RequestParam();
	param.set("sdata1", sData1);
	var srcPath = window.location.pathname;
	if (srcPath.indexOf("?") > 0)
		srcPath = srcPath.substring(0, srcPath.indexOf("?"));
	srcPath = srcPath.replace("/mobileUI/", "/");
	param.set("srcPath", srcPath);
	param.set("processID", this.processID ? this.processID : "");
	var onBeforeStart = this.Dom.getAttribute("onBeforeStart");
	if (onBeforeStart) {
		var inFn = eval(onBeforeStart);
		if (typeof (inFn) == "function") {
			var rEvent = {
				source : this,
				taskID : this.taskID,
				flowID : this.flowID,
				cancel : false
			};
			var bfData = inFn(rEvent);
			try {
				if (bfData.cancel == true) {
					return "cancel";
				}
			} catch (e) {
			}
		} else {
			try {
				if (inFn.cancel == true) {
					return "cancel";
				}
			} catch (e) {
			}
		}
	}
	var onBeforeFlowAction = this.Dom.getAttribute("onBeforeFlowAction");
	if (onBeforeFlowAction) {
		var inFn = eval(onBeforeFlowAction);
		if (typeof (inFn) == "function") {
			var rEvent = {
				source : this,
				taskID : this.taskID,
				flowID : this.flowID,
				cancel : false
			};
			inFn(rEvent);
		}
	}
	tlv8.showModelState(true);
	tlv8.XMLHttpRequest("flowstartAction", param, "post", false, function(r) {
		tlv8.showModelState(false);
		if (r.data.flag == "false") {
			alert("操作失败:" + r.data.message);
		} else {
			var flwData = eval("(" + r.data.data + ")");
			flowCompent.processID = flwData.processID;
			flowCompent.flowID = flwData.flowID;
			flowCompent.taskID = flwData.taskID;
			var onStartCommit = flowCompent.Dom.getAttribute("onStartCommit");
			if (onStartCommit) {
				var inFn = eval(onStartCommit);
				if (typeof (inFn) == "function") {
					var rEvent = {
						source : flowCompent,
						taskID : flowCompent.taskID,
						flowID : flowCompent.flowID,
						cancel : false
					};
					inFn(rEvent);
				}
			}
			var onFlowActionCommit = flowCompent.Dom
					.getAttribute("onFlowActionCommit");
			if (onFlowActionCommit) {
				var inFn = eval(onFlowActionCommit);
				if (typeof (inFn) == "function") {
					var rEvent = {
						source : flowCompent,
						taskID : flowCompent.taskID,
						flowID : flowCompent.flowID,
						cancel : false
					};
					inFn(rEvent);
				}
			}
			if (typeof callback == "function") {
				callback(flwData);
			}
		}
		var onAfterStart = flowCompent.Dom.getAttribute("onAfterStart");
		if (onAfterStart) {
			var inFn = eval(onAfterStart);
			if (typeof (inFn) == "function") {
				var rEvent = {
					source : flowCompent,
					taskID : flowCompent.taskID,
					flowID : flowCompent.flowID,
					cancel : false
				};
				inFn(rEvent);
			}
		}
		var onAfterFlowAction = flowCompent.Dom
				.getAttribute("onAfterFlowAction");
		if (onAfterFlowAction) {
			var inFn = eval(onAfterFlowAction);
			if (typeof (inFn) == "function") {
				var rEvent = {
					source : flowCompent,
					taskID : flowCompent.taskID,
					flowID : flowCompent.flowID,
					cancel : false
				};
				inFn(rEvent);
			}
		}
	});
};

/*
 * 回退 @param {Object} flowID @param {Object} taskID
 */
tlv8.flw.prototype.flowback = function(flowID, taskID) {
	var flowCompent = this;
	flowID = flowID || flowCompent.flowID;
	taskID = taskID || flowCompent.taskID;
	if (this.setting.autosaveData) {
		var rowid = this.data.saveData();
		if (!rowid) {
			return;
		}
		this.sData1 = rowid;
	}
	var param = new tlv8.RequestParam();
	var onBeforeBack = this.Dom.getAttribute("onBeforeBack");
	if (onBeforeBack) {
		var inFn = eval(onBeforeBack);
		if (typeof (inFn) == "function") {
			var rEvent = {
				source : this,
				taskID : taskID,
				flowID : flowID,
				cancel : false
			};
			var bfData = inFn(rEvent);
			try {
				if (bfData.cancel == true) {
					return "cancel";
				}
			} catch (e) {
			}
		} else {
			try {
				if (inFn.cancel == true) {
					return "cancel";
				}
			} catch (e) {
			}
		}
	}
	var onBeforeFlowAction = this.Dom.getAttribute("onBeforeFlowAction");
	if (onBeforeFlowAction) {
		var inFn = eval(onBeforeFlowAction);
		if (typeof (inFn) == "function") {
			var rEvent = {
				source : this,
				taskID : taskID,
				flowID : flowID,
				cancel : false
			};
			inFn(rEvent);
		}
	}
	if (flowID && taskID) {
		var sData1 = getParamValueFromUrl("sData1") || flowCompent.data.rowid;
		var activity = getParamValueFromUrl("activity");
		var url = cpath
				+ "/mobileUI/system/service/process/dialog/processExecuteDialog.html?executeMode=back";
		url += "&flowID=" + flowID;
		url += "&taskID=" + taskID;
		url += "&sData1=" + sData1;
		url += "&activity=" + activity;
		flowCompent.closeRightMenu();
		justep.dialog.openFullScreenDialog("",
				{
					url : url,
					callback : function(re) {
						var flwData = re;
						flowCompent.processID = flwData.processID;
						flowCompent.flowID = flwData.flowID;
						flowCompent.taskID = flwData.taskID;
						var onBackCommit = flowCompent.Dom
								.getAttribute("onBackCommit");
						if (onBackCommit) {
							var inFn = eval(onBackCommit);
							if (typeof (inFn) == "function") {
								var rEvent = {
									source : flowCompent,
									taskID : flowCompent.taskID,
									flowID : flowCompent.flowID,
									cancel : false
								};
								inFn(rEvent);
							}
						}
						var onFlowActionCommit = flowCompent.Dom
								.getAttribute("onFlowActionCommit");
						if (onFlowActionCommit) {
							var inFn = eval(onFlowActionCommit);
							if (typeof (inFn) == "function") {
								var rEvent = {
									source : flowCompent,
									taskID : flowCompent.taskID,
									flowID : flowCompent.flowID,
									cancel : false
								};
								inFn(rEvent);
							}
						}
						if (flowCompent.setting.autoclose == true) {
							window.history.back();
						}

						var onAfterBack = flowCompent.Dom
								.getAttribute("onAfterBack");
						if (onAfterBack) {
							var inFn = eval(onAfterBack);
							if (typeof (inFn) == "function") {
								var rEvent = {
									source : flowCompent,
									taskID : flowCompent.taskID,
									flowID : flowCompent.flowID,
									cancel : false
								};
								inFn(rEvent);
							}
						}
						var onAfterFlowAction = flowCompent.Dom
								.getAttribute("onAfterFlowAction");
						if (onAfterFlowAction) {
							var inFn = eval(onAfterFlowAction);
							if (typeof (inFn) == "function") {
								var rEvent = {
									source : flowCompent,
									taskID : flowCompent.taskID,
									flowID : flowCompent.flowID,
									cancel : false
								};
								inFn(rEvent);
							}
						}

						if (flowCompent.setting.autoclose == true) {
							flowCompent.processComiitCallback();
						}
					}
				});
	} else {
		mAlert("未指定任务!");
	}
};

/*
 * 流转 @param {Object} flowID @param {Object} taskID
 */
tlv8.flw.prototype.flowout = function(flowID, taskID, ePersonID, sData1) {
	var flowCompent = this;
	flowID = flowID || flowCompent.flowID;
	taskID = taskID || flowCompent.taskID;
	if (this.setting.autosaveData) {
		var rowid = this.data.saveData();
		if (!rowid) {
			return;
		}
		this.sData1 = rowid;
	}
	sData1 = sData1 || flowCompent.sData1 || flowCompent.data.rowid;
	var onBeforeAdvance = this.Dom.getAttribute("onBeforeAdvance");
	if (onBeforeAdvance) {
		var inFn = eval(onBeforeAdvance);
		if (typeof (inFn) == "function") {
			var rEvent = {
				source : this,
				taskID : this.taskID,
				flowID : this.flowID,
				cancel : false
			};
			var bfData = inFn(rEvent);
			try {
				if (bfData.cancel == true) {
					return "cancel";
				}
			} catch (e) {
			}
		} else {
			try {
				if (inFn.cancel == true) {
					return "cancel";
				}
			} catch (e) {
			}
		}
	}
	var onBeforeFlowAction = this.Dom.getAttribute("onBeforeFlowAction");
	if (onBeforeFlowAction) {
		var inFn = eval(onBeforeFlowAction);
		if (typeof (inFn) == "function") {
			var rEvent = {
				source : this,
				taskID : this.taskID,
				flowID : this.flowID,
				cancel : false
			};
			inFn(rEvent);
		}
	}
	if (flowID && taskID) {
		var activity = getParamValueFromUrl("activity");
		var url = cpath
				+ "/mobileUI/system/service/process/dialog/processExecuteDialog.html?executeMode=advance";
		url += "&flowID=" + flowID;
		url += "&taskID=" + taskID;
		url += "&sData1=" + sData1;
		url += "&activity=" + activity;
		flowCompent.closeRightMenu();
		justep.dialog.openFullScreenDialog("", {
			url : url,
			callback : function(re) {
				var flwData = re;
				flowCompent.processID = flwData.processID;
				flowCompent.flowID = flwData.flowID;
				flowCompent.taskID = flwData.taskID;
				var onAdvanceCommit = flowCompent.Dom
						.getAttribute("onAdvanceCommit");
				if (onAdvanceCommit) {
					var inFn = eval(onAdvanceCommit);
					if (typeof (inFn) == "function") {
						var rEvent = {
							source : flowCompent,
							taskID : flowCompent.taskID,
							flowID : flowCompent.flowID,
							cancel : false
						};
						inFn(rEvent);
					}
				}
				var onFlowActionCommit = flowCompent.Dom
						.getAttribute("onFlowActionCommit");
				if (onFlowActionCommit) {
					var inFn = eval(onFlowActionCommit);
					if (typeof (inFn) == "function") {
						var rEvent = {
							source : flowCompent,
							taskID : flowCompent.taskID,
							flowID : flowCompent.flowID,
							cancel : false
						};
						inFn(rEvent);
					}
				}

				var onAfterAdvance = flowCompent.Dom
						.getAttribute("onAfterAdvance");
				if (onAfterAdvance) {
					var inFn = eval(onAfterAdvance);
					if (typeof (inFn) == "function") {
						var rEvent = {
							source : flowCompent,
							taskID : flowCompent.taskID,
							flowID : flowCompent.flowID,
							cancel : false
						};
						inFn(rEvent);
					}
				}
				var onAfterFlowAction = flowCompent.Dom
						.getAttribute("onAfterFlowAction");
				if (onAfterFlowAction) {
					var inFn = eval(onAfterFlowAction);
					if (typeof (inFn) == "function") {
						var rEvent = {
							source : flowCompent,
							taskID : flowCompent.taskID,
							flowID : flowCompent.flowID,
							cancel : false
						};
						inFn(rEvent);
					}
				}

				if (flowCompent.setting.autoclose == true) {
					flowCompent.processComiitCallback();
				}
			}
		});
	} else {
		flowCompent.flowstart(function() {
			flowCompent.flowout();
		});
	}
};

/*
 * 转发 @param {Object} flowID @param {Object} taskID
 */
tlv8.flw.prototype.flowtransmit = function(flowID, taskID, ePersonID) {
	var flowCompent = this;
	if (this.setting.autosaveData) {
		var rowid = this.data.saveData();
		if (!rowid) {
			return;
		}
		this.sData1 = rowid;
	}
	flowID = flowID || flowCompent.flowID || getParamValueFromUrl("flowID");
	taskID = flowID || flowCompent.taskID || getParamValueFromUrl("taskID");
	sData1 = flowCompent.sData1 || flowCompent.data.rowid
			|| getParamValueFromUrl("sData1");
	var onBeforeTransfer = this.Dom.getAttribute("onBeforeTransfer");
	if (onBeforeTransfer) {
		var inFn = eval(onBeforeTransfer);
		if (typeof (inFn) == "function") {
			var rEvent = {
				source : this,
				taskID : this.taskID,
				flowID : this.flowID,
				cancel : false
			};
			var bfData = inFn(rEvent);
			try {
				if (bfData.cancel == true) {
					return "cancel";
				}
			} catch (e) {
			}
		} else {
			try {
				if (inFn.cancel == true) {
					return "cancel";
				}
			} catch (e) {
			}
		}
	}
	var onBeforeFlowAction = this.Dom.getAttribute("onBeforeFlowAction");
	if (onBeforeFlowAction) {
		var inFn = eval(onBeforeFlowAction);
		if (typeof (inFn) == "function") {
			var rEvent = {
				source : this,
				taskID : this.taskID,
				flowID : this.flowID,
				cancel : false
			};
			inFn(rEvent);
		}
	}
	var activity = getParamValueFromUrl("activity");
	var url = cpath
			+ "/mobileUI/system/service/process/dialog/processExecuteDialog.html?executeMode=transfer";
	url += "&flowID=" + flowID;
	url += "&taskID=" + taskID;
	url += "&sData1=" + sData1;
	url += "&activity=" + activity;
	flowCompent.closeRightMenu();
	justep.dialog.openFullScreenDialog("", {
		url : url,
		callback : function(re) {
			var flwData = re;
			flowCompent.processID = flwData.processID;
			flowCompent.flowID = flwData.flowID;
			flowCompent.taskID = flwData.taskID;

			var onAfterTransfer = this.Dom.getAttribute("onAfterTransfer");
			if (onAfterTransfer) {
				var inFn = eval(onAfterTransfer);
				if (typeof (inFn) == "function") {
					var rEvent = {
						source : this,
						taskID : this.taskID,
						flowID : this.flowID,
						cancel : false
					};
					inFn(rEvent);
				}
			}
			var onAfterFlowAction = this.Dom.getAttribute("onAfterFlowAction");
			if (onAfterFlowAction) {
				var inFn = eval(onAfterFlowAction);
				if (typeof (inFn) == "function") {
					var rEvent = {
						source : this,
						taskID : this.taskID,
						flowID : this.flowID,
						cancel : false
					};
					inFn(rEvent);
				}
			}

			var onTransferCommit = this.Dom.getAttribute("onTransferCommit");
			if (onTransferCommit) {
				var inFn = eval(onTransferCommit);
				if (typeof (inFn) == "function") {
					var rEvent = {
						source : flowCompent,
						taskID : flowCompent.taskID,
						flowID : flowCompent.flowID,
						cancel : false
					};
					inFn(rEvent);
				}
			}
			var onFlowActionCommit = this.Dom
					.getAttribute("onFlowActionCommit");
			if (onFlowActionCommit) {
				var inFn = eval(onFlowActionCommit);
				if (typeof (inFn) == "function") {
					var rEvent = {
						source : flowCompent,
						taskID : flowCompent.taskID,
						flowID : flowCompent.flowID,
						cancel : false
					};
					inFn(rEvent);
				}
			}

			if (flowCompent.setting.autoclose == true) {
				flowCompent.processComiitCallback();
			}
		}
	});
};

/*
 * 暂停 @param {Object} flowID @param {Object} taskID
 */
tlv8.flw.prototype.flowpause = function(flowID, taskID) {
	var flowCompent = this;
	if (this.setting.autosaveData) {
		var rowid = this.data.saveData();
		if (!rowid) {
			return;
		}
		this.sData1 = rowid;
	}
	if (!confirm("流程暂停后只能到任务中心激活.\n  确定暂停吗?"))
		return;
	var onBeforeSuspend = this.Dom.getAttribute("onBeforeSuspend");
	if (onBeforeSuspend) {
		var inFn = eval(onBeforeSuspend);
		if (typeof (inFn) == "function") {
			var rEvent = {
				source : this,
				taskID : this.taskID,
				flowID : this.flowID,
				cancel : false
			};
			var bfData = inFn(rEvent);
			try {
				if (bfData.cancel == true) {
					return "cancel";
				}
			} catch (e) {
			}
		} else {
			try {
				if (inFn.cancel == true) {
					return "cancel";
				}
			} catch (e) {
			}
		}
	}
	var onBeforeFlowAction = this.Dom.getAttribute("onBeforeFlowAction");
	if (onBeforeFlowAction) {
		var inFn = eval(onBeforeFlowAction);
		if (typeof (inFn) == "function") {
			var rEvent = {
				source : this,
				taskID : this.taskID,
				flowID : this.flowID,
				cancel : false
			};
			inFn(rEvent);
		}
	}
	var param = new tlv8.RequestParam();
	if (flowID && taskID) {
		param.set("flowID", flowID);
		param.set("taskID", taskID);
		tlv8.XMLHttpRequest("flowpauseAction", param, "post", true,
				function(r) {
					tlv8.showModelState(false);
					if (r.data.flag == "false") {
						alert("操作失败:" + r.data.message);
					} else {
						var onSuspendCommit = flowCompent.Dom
								.getAttribute("onSuspendCommit");
						if (onSuspendCommit) {
							var inFn = eval(onSuspendCommit);
							if (typeof (inFn) == "function") {
								var rEvent = {
									source : flowCompent,
									taskID : flowCompent.taskID,
									flowID : flowCompent.flowID,
									cancel : false
								};
								inFn(rEvent);
							}
						}
						var onFlowActionCommit = flowCompent.Dom
								.getAttribute("onFlowActionCommit");
						if (onFlowActionCommit) {
							var inFn = eval(onFlowActionCommit);
							if (typeof (inFn) == "function") {
								var rEvent = {
									source : flowCompent,
									taskID : flowCompent.taskID,
									flowID : flowCompent.flowID,
									cancel : false
								};
								inFn(rEvent);
							}
						}
						if (flowCompent.setting.autoclose)
							window.history.back();
					}
					var onAfterSuspend = flowCompent.Dom
							.getAttribute("onAfterSuspend");
					if (onAfterSuspend) {
						var inFn = eval(onAfterSuspend);
						if (typeof (inFn) == "function") {
							var rEvent = {
								source : flowCompent,
								taskID : flowCompent.taskID,
								flowID : flowCompent.flowID,
								cancel : false
							};
							inFn(rEvent);
						}
					}
					var onAfterFlowAction = flowCompent.Dom
							.getAttribute("onAfterFlowAction");
					if (onAfterFlowAction) {
						var inFn = eval(onAfterFlowAction);
						if (typeof (inFn) == "function") {
							var rEvent = {
								source : flowCompent,
								taskID : flowCompent.taskID,
								flowID : flowCompent.flowID,
								cancel : false
							};
							inFn(rEvent);
						}
					}

					if (flowCompent.setting.autoclose == true) {
						flowCompent.processComiitCallback();
					}
				});
		tlv8.showModelState(true);
	} else if (this.flowID && this.flowID != "" && this.taskID
			&& this.taskID != "") {
		param.set("flowID", this.flowID);
		param.set("taskID", this.taskID);
		tlv8.XMLHttpRequest("flowpauseAction", param, "post", true,
				function(r) {
					tlv8.showModelState(false);
					if (r.data.flag == "false") {
						alert("操作失败:" + r.data.message);
					} else {
						var onSuspendCommit = flowCompent.Dom
								.getAttribute("onSuspendCommit");
						if (onSuspendCommit) {
							var inFn = eval(onSuspendCommit);
							if (typeof (inFn) == "function") {
								var rEvent = {
									source : flowCompent,
									taskID : flowCompent.taskID,
									flowID : flowCompent.flowID,
									cancel : false
								};
								inFn(rEvent);
							}
						}
						var onFlowActionCommit = flowCompent.Dom
								.getAttribute("onFlowActionCommit");
						if (onFlowActionCommit) {
							var inFn = eval(onFlowActionCommit);
							if (typeof (inFn) == "function") {
								var rEvent = {
									source : flowCompent,
									taskID : flowCompent.taskID,
									flowID : flowCompent.flowID,
									cancel : false
								};
								inFn(rEvent);
							}
						}
						if (flowCompent.setting.autoclose)
							window.history.back();
					}
					var onAfterSuspend = flowCompent.Dom
							.getAttribute("onAfterSuspend");
					if (onAfterSuspend) {
						var inFn = eval(onAfterSuspend);
						if (typeof (inFn) == "function") {
							var rEvent = {
								source : flowCompent,
								taskID : flowCompent.taskID,
								flowID : flowCompent.flowID,
								cancel : false
							};
							inFn(rEvent);
						}
					}
					var onAfterFlowAction = flowCompent.Dom
							.getAttribute("onAfterFlowAction");
					if (onAfterFlowAction) {
						var inFn = eval(onAfterFlowAction);
						if (typeof (inFn) == "function") {
							var rEvent = {
								source : flowCompent,
								taskID : flowCompent.taskID,
								flowID : flowCompent.flowID,
								cancel : false
							};
							inFn(rEvent);
						}
					}

					if (flowCompent.setting.autoclose == true) {
						flowCompent.processComiitCallback();
					}
				});
		tlv8.showModelState(true);
	} else {
		mAlert("未指定任务.");
	}
};

/*
 * 终止 @param {Object} flowID @param {Object} taskID
 */
tlv8.flw.prototype.flowstop = function(flowID, taskID) {
	var flowCompent = this;
	if (this.setting.autosaveData) {
		var rowid = this.data.saveData();
		if (!rowid) {
			return;
		}
		this.sData1 = rowid;
	}
	if (!confirm("终止流程,流程将彻底作废.\n  确定终止吗?"))
		return;
	var onBeforeAbort = this.Dom.getAttribute("onBeforeAbort");
	if (onBeforeAbort) {
		var inFn = eval(onBeforeAbort);
		if (typeof (inFn) == "function") {
			var rEvent = {
				source : this,
				taskID : this.taskID,
				flowID : this.flowID,
				cancel : false
			};
			var bfData = inFn(rEvent);
			try {
				if (bfData.cancel == true) {
					return "cancel";
				}
			} catch (e) {
			}
		} else {
			try {
				if (inFn.cancel == true) {
					return "cancel";
				}
			} catch (e) {
			}
		}
	}
	var onBeforeFlowAction = this.Dom.getAttribute("onBeforeFlowAction");
	if (onBeforeFlowAction) {
		var inFn = eval(onBeforeFlowAction);
		if (typeof (inFn) == "function") {
			var rEvent = {
				source : this,
				taskID : this.taskID,
				flowID : this.flowID,
				cancel : false
			};
			inFn(rEvent);
		}
	}
	var param = new tlv8.RequestParam();
	if (flowID && taskID) {
		param.set("flowID", flowID);
		param.set("taskID", taskID);
		tlv8.XMLHttpRequest("flowstopAction", param, "post", true, function(r) {
			tlv8.showModelState(false);
			if (r.data.flag == "false") {
				alert("操作失败:" + r.data.message);
			} else {
				var onAbortCommit = flowCompent.Dom
						.getAttribute("onAbortCommit");
				if (onAbortCommit) {
					var inFn = eval(onAbortCommit);
					if (typeof (inFn) == "function") {
						var rEvent = {
							source : flowCompent,
							taskID : flowCompent.taskID,
							flowID : flowCompent.flowID,
							cancel : false
						};
						inFn(rEvent);
					}
				}
				var onFlowActionCommit = flowCompent.Dom
						.getAttribute("onFlowActionCommit");
				if (onFlowActionCommit) {
					var inFn = eval(onFlowActionCommit);
					if (typeof (inFn) == "function") {
						var rEvent = {
							source : flowCompent,
							taskID : flowCompent.taskID,
							flowID : flowCompent.flowID,
							cancel : false
						};
						inFn(rEvent);
					}
				}
				if (flowCompent.setting.autoclose)
					window.history.back();
			}
			var onAfterAbort = flowCompent.Dom.getAttribute("onAfterAbort");
			if (onAfterAbort) {
				var inFn = eval(onAfterAbort);
				if (typeof (inFn) == "function") {
					var rEvent = {
						source : flowCompent,
						taskID : flowCompent.taskID,
						flowID : flowCompent.flowID,
						cancel : false
					};
					inFn(rEvent);
				}
			}
			var onAfterFlowAction = flowCompents.Dom
					.getAttribute("onAfterFlowAction");
			if (onAfterFlowAction) {
				var inFn = eval(onAfterFlowAction);
				if (typeof (inFn) == "function") {
					var rEvent = {
						source : flowCompent,
						taskID : flowCompent.taskID,
						flowID : flowCompent.flowID,
						cancel : false
					};
					inFn(rEvent);
				}
			}

			if (flowCompent.setting.autoclose == true) {
				flowCompent.processComiitCallback();
			}
		});
		tlv8.showModelState(true);
	} else if (this.flowID && this.flowID != "" && this.taskID
			&& this.taskID != "") {
		param.set("flowID", this.flowID);
		param.set("taskID", this.taskID);
		tlv8.XMLHttpRequest("flowstopAction", param, "post", true, function(r) {
			if (r.data.flag == "false") {
				alert("操作失败:" + r.data.message);
			} else {
				var onAbortCommit = flowCompent.Dom
						.getAttribute("onAbortCommit");
				if (onAbortCommit) {
					var inFn = eval(onAbortCommit);
					if (typeof (inFn) == "function") {
						var rEvent = {
							source : flowCompent,
							taskID : flowCompent.taskID,
							flowID : flowCompent.flowID,
							cancel : false
						};
						inFn(rEvent);
					}
				}
				var onFlowActionCommit = flowCompent.Dom
						.getAttribute("onFlowActionCommit");
				if (onFlowActionCommit) {
					var inFn = eval(onFlowActionCommit);
					if (typeof (inFn) == "function") {
						var rEvent = {
							source : flowCompent,
							taskID : flowCompent.taskID,
							flowID : flowCompent.flowID,
							cancel : false
						};
						inFn(rEvent);
					}
				}
				if (flowCompent.setting.autoclose)
					window.history.back();
			}
			var onAfterAbort = flowCompent.Dom.getAttribute("onAfterAbort");
			if (onAfterAbort) {
				var inFn = eval(onAfterAbort);
				if (typeof (inFn) == "function") {
					var rEvent = {
						source : flowCompent,
						taskID : flowCompent.taskID,
						flowID : flowCompent.flowID,
						cancel : false
					};
					inFn(rEvent);
				}
			}
			var onAfterFlowAction = flowCompent.Dom
					.getAttribute("onAfterFlowAction");
			if (onAfterFlowAction) {
				var inFn = eval(onAfterFlowAction);
				if (typeof (inFn) == "function") {
					var rEvent = {
						source : flowCompent,
						taskID : flowCompent.taskID,
						flowID : flowCompent.flowID,
						cancel : false
					};
					inFn(rEvent);
				}
			}

			if (flowCompent.setting.autoclose == true) {
				flowCompent.processComiitCallback();
			}
		});
	} else {
		mAlert("未指定任务.");
	}
};

/*
 * 查看流程图 @param {Object} flowID @param {Object} taskID
 */
tlv8.flw.prototype.viewChart = function(flowID, taskID) {
	if (!flowID)
		flowID = this.flowID;
	if (!taskID)
		taskID = this.taskID;
	var currentUrl = window.location.pathname;
	if (currentUrl.indexOf("?") > 0)
		currentUrl = currentUrl.substring(0, currentUrl.indexOf("?"));
	var mainFormid = this.data.formid;
	if (mainFormid) {
		var sData1 = document.getElementById(this.data.formid).rowid;
	}
	if ((flowID && taskID) || sData1 == null || !sData1) {
		var url = cpath
				+ "/mobileUI/system/service/process/flw/viewiocusbot/yj_iocus_bot.html?flowID="
				+ flowID + "&taskID=" + taskID;
		window.open(url, "_self");
	} else {
		tlv8.task.viewChart(sData1);
	}
};

tlv8.flw.prototype.processComiitCallback = function() {
	window.history.back();
	setTimeout(function() {
		window.history.back();// 调用两次回退 防止页面停留
	}, 100);
};

// ==========================================================分割=======================================================================================\\\\\
/*
 * 任务相关
 */
tlv8.task = {
	/*
	 * 打开任务 @param {Object} taskID 必须 @param {Object} url 可选 @param {Object}
	 * executor 可选
	 */
	openTask : function(taskID, url, executor) {
		var param = new tlv8.RequestParam();
		if (taskID) {
			param.set("taskID", taskID);
			param.set("executor", executor);
			tlv8.XMLHttpRequest("openTaskAction", param, "post", true,
					function(r) {
						if (r.data.flag == "false") {
							alert("操作失败:" + r.data.message);
						} else {
							var Data = eval("(" + r.data.data + ")");
							var name = Data.name;
							var sURL = url || Data.url;
							var sData1 = Data.sData1;
							if (sURL.indexOf(".w") > 0) {
								sURL = sURL.replace(".w", ".html");
							}
							sURL += "?flowID=" + Data.flowID + "&taskID="
									+ taskID + "&sData1=" + sData1 + "&task="
									+ taskID + "&activity-pattern=do";
							tlv8.portal.openWindow(name, sURL);
							writeLog(event, "处理任务");// 写日志
						}
					});
		}
	},
	processID : "",
	flowID : "",
	taskID : "",
	flowback : function(flowID, taskID, calback) {
		var param = new tlv8.RequestParam();
		if (flowID && taskID) {
			param.set("flowID", flowID);
			param.set("taskID", taskID);
			tlv8.XMLHttpRequest("flowbackAction", param, "post", true,
					function(r) {
						tlv8.showModelState(false);
						if (r.data.flag == "false") {
							alert("操作失败:" + r.data.message);
							return false;
						} else {
							var flwData = eval("(" + r.data.data + ")");
							this.processID = flwData.processID;
							this.flowID = flwData.flowID;
							this.taskID = flwData.taskID;
							sAlert("操作成功！");
							if (calback && typeof calback == "function") {
								calback(flwData);
							} else if (calback && calback != "") {
								try {
									if (typeof calback == "string") {
										calback = window.eval(calback);
									}
									if (typeof calback == "function") {
										calback(flwData);
									}
								} catch (e) {
									alert("给定的回调函数不存在：" + e);
								}
							}
						}
					});
			tlv8.showModelState(true);
		}
	},
	flowout : function(flowID, taskID, ePersonID, activity, sData1, calback) {
		var param = new tlv8.RequestParam();
		if (flowID && taskID) {
			param.set("flowID", flowID);
			param.set("taskID", taskID);
			param.set("epersonids", ePersonID);
			param.set("afterActivity", activity);
			param.set("sdata1", sData1);
			tlv8.XMLHttpRequest("flowoutAction", param, "post", true, function(
					r) {
				if (r.data.flag == "false") {
					alert("操作失败:" + r.data.message);
					return false;
				} else {
					var flwData = eval("(" + r.data.data + ")");
					this.processID = flwData.processID;
					this.flowID = flwData.flowID;
					this.taskID = flwData.taskID;
					sAlert("操作成功！");
					if (calback && typeof calback == "function") {
						calback(flwData);
					} else if (calback && calback != "") {
						try {
							calback = eval(calback);
							if (typeof calback == "function")
								calback(flwData);
						} catch (e) {
							alert("给定的回调函数不存在：" + e);
						}
					}
				}
			});
		}
	},
	flowtransmit : function(taskID, activity, ePersonID) {
		var param = new tlv8.RequestParam();
		param.set("taskID", taskID);
		param.set("afterActivity", activity);
		param.set("epersonids", ePersonID);
		tlv8.XMLHttpRequest("flowtransmitAction", param, "post", true,
				function(r) {
					var flwData = eval("(" + r.data.data + ")");
					flowCompent.processID = flwData.processID;
					flowCompent.flowID = flwData.flowID;
					flowCompent.taskID = flwData.taskID;
				});
	},
	flowpause : function(flowID, taskID, calback) {
		if (!confirm("流程暂停后只能到任务中心激活.\n  确定暂停吗?"))
			return false;
		var param = new tlv8.RequestParam();
		if (taskID) {
			param.set("taskID", taskID);
			tlv8.XMLHttpRequest("flowpauseAction", param, "post", true,
					function(r) {
						tlv8.showModelState(false);
						if (r.data.flag == "false") {
							alert("操作失败:" + r.data.message);
							return false;
						} else {
							sAlert("操作成功！");
							return true;
						}
						if (calback && typeof calback == "function") {
							calback();
						} else if (calback && calback != "") {
							try {
								calback = eval(calback);
								if (typeof calback == "function")
									calback();
							} catch (e) {
								alert("给定的回调函数不存在：" + e);
							}
						}
					});
			tlv8.showModelState(true);
		}
		return true;
	},
	flowrestart : function(taskID, calback) {
		var param = new tlv8.RequestParam();
		if (taskID) {
			param.set("taskID", taskID);
			tlv8.XMLHttpRequest("flowrestartAction", param, "post", true,
					function(r) {
						tlv8.showModelState(false);
						if (r.data.flag == "false") {
							alert("操作失败:" + r.data.message);
							return false;
						} else {
							sAlert("操作成功！");
							return true;
						}
						if (calback && typeof calback == "function") {
							calback();
						} else if (calback && calback != "") {
							try {
								calback = eval(calback);
								if (typeof calback == "function")
									calback();
							} catch (e) {
								alert("给定的回调函数不存在：" + e);
							}
						}
					});
			tlv8.showModelState(true);
		}
		return true;
	},
	flowstop : function(flowID, taskID, calback) {
		if (!confirm("终止流程,流程将彻底作废.\n  确定终止吗?"))
			return false;
		var param = new tlv8.RequestParam();
		if (flowID && taskID) {
			param.set("flowID", flowID);
			param.set("taskID", taskID);
			tlv8.XMLHttpRequest("flowstopAction", param, "post", true,
					function(r) {
						tlv8.showModelState(false);
						if (r.data.flag == "false") {
							alert("操作失败:" + r.data.message);
							return false;
						} else {
							sAlert("操作成功！");
							return true;
						}
						if (calback && typeof calback == "function") {
							calback();
						} else if (calback && calback != "") {
							try {
								calback = eval(calback);
								if (typeof calback == "function")
									calback();
							} catch (e) {
								alert("给定的回调函数不存在：" + e);
							}
						}
					});
			tlv8.showModelState(true);
		}
		return true;
	},
	flowstart : function(sEurl, sData1, calback) {
		var flowCompent = this;
		var param = new tlv8.RequestParam();
		param.set("sdata1", sData1);
		var srcPath = sEurl || window.location.pathname;
		if (srcPath.indexOf("?") > 0)
			srcPath = srcPath.substring(0, srcPath.indexOf("?"));
		srcPath = srcPath.replace("/mobileUI/", "/");
		param.set("srcPath", srcPath);
		tlv8.showModelState(true);
		tlv8.XMLHttpRequest("flowstartAction", param, "post", true,
				function(r) {
					tlv8.showModelState(false);
					if (r.data.flag == "false") {
						alert("操作失败:" + r.data.message);
					} else {
						var flwData = eval("(" + r.data.data + ")");
						flowCompent.processID = flwData.processID;
						flowCompent.flowID = flwData.flowID;
						flowCompent.taskID = flwData.taskID;
					}
					if (calback && typeof calback == "function") {
						calback();
					} else if (calback && calback != "") {
						try {
							calback = eval(calback);
							if (typeof calback == "function")
								calback();
						} catch (e) {
							alert("给定的回调函数不存在：" + e);
						}
					}
				});
	},
	viewChart : function(sData1) {// 查看流程图
		var sql = "select SID, SFLOWID, SPROCESS, SEURL from "
				+ "(select SID, SFLOWID, SPROCESS, SEURL from SA_TASK where sData1 = '"
				+ sData1 + "' order by SCREATETIME desc) where rownum = 1";
		var result = tlv8.sqlQueryAction("system", sql);
		var count = result.getCount();
		if (count > 0) {
			var flowID = result.getValueByName("SFLOWID");
			var taskID = result.getValueByName("SID");
			var url = cpath
					+ "/mobileUI/system/service/process/flw/viewiocusbot/yj_iocus_bot.html?flowID="
					+ flowID + "&taskID=" + taskID;
			window.open(url, "_self");
		} else {
			alert("没有sData1='" + sData1 + "'对应的流程信息!");
		}
	},
	Update_Flowbillinfo : function(tablename, fid, billitem) {// 更新流程单据信息
		var param = new tlv8.RequestParam();
		param.set("tablename", tablename);
		param.set("fid", fid);
		param.set("billitem", billitem);
		var r = tlv8.XMLHttpRequest("Update_Flowbillinfo", param, "post",
				false, null);
		if (r.data.flag == "false") {
			alert(r.data.message);
			return false;
		}
		return r.data.data;
	},
	QueryBySql : function(dbkey, sql) {
		var reData;
		$.ajax({
			type : "post",
			async : false,
			url : "sqlQueryActionforJson",
			data : "dbkey=" + dbkey + "&sql=" + sql,
			success : function(r, textStatus) {
				if (r.data.flag == "false") {
					alert("查询失败:" + r.data.message);
					return [];
				} else {
					reData = (r.data.data) ? (eval("(" + r.data.data + ")"))
							: [];
				}
			},
			error : function() {
			}
		});
		return reData;
	},
	loadOut : function(flowID, taskID, sData1, calBack) {
		$
				.ajax({
					type : "post",
					async : false,
					url : "flowoutAction",
					data : "flowID=" + flowID + "&taskID=" + taskID
							+ "&sdata1=" + sData1,
					success : function(r, textStatus) {
						if (r.data.flag == "false") {
							alert("操作失败:" + r.data.message);
							return false;
						} else if (r.data.flag == "select") {
							try {
								var reActData = eval("(" + r.data.data + ")");
								var activityListStr = reActData.activityListStr;
								var activityList = activityListStr;
								if (typeof activityList == "string") {
									activityList = window.eval("("
											+ activityList + ")");
								}
								if (activityList.length == 1) {
									var actType = activityList[0].type;
									if (actType == "end") {
										var exepersons = [];
										exepersons[0] = justep.Context
												.getCurrentPersonID();
										var process = getParamValueFromUrl("process");
										var Factivity = getParamValueFromUrl("activity");
										var taskID = getParamValueFromUrl("task");
										var sData1 = getParamValueFromUrl("sData1");
										tlv8.task
												.flowout(
														process,
														taskID,
														exepersons.join(","),
														Factivity,
														sData1,
														function() {
															window
																	.open(
																			cpath
																					+ "/mobileUI/SA/task/taskView/waitActivity.html",
																			"_self");
														});
										return false;
									}
								}
								calBack(activityListStr);
							} catch (e) {
								alert("加载失败!m:" + e.message);
							}
						} else {
							calBack();
						}
					},
					error : function() {
					}
				});
	},
	loadTransfer : function(taskID, sData1, calBack) {
		$.ajax({
			type : "post",
			async : false,
			url : "flowtransmitAction",
			data : "taskID=" + taskID + "&sdata1=" + sData1,
			success : function(r, textStatus) {
				if (r.data.flag == "false") {
					alert("操作失败:" + r.data.message);
					return false;
				} else if (r.data.flag == "select") {
					try {
						var reActData = eval("(" + r.data.data + ")");
						var activityListStr = reActData.activityListStr;
						calBack(activityListStr);
					} catch (e) {
						alert("加载失败!m:" + e.message);
					}
				}
			},
			error : function() {
			}
		});
	},
	initActivityList : function(activityList) {
		if (typeof activityList == "string") {
			activityList = window.eval("(" + activityList + ")");
		}
		var htmls = [];
		for (var i = 0; i < activityList.length; i++) {
			var act = activityList[i];
			var id = act.id;
			var name = act.name;
			var label = act.label;
			var actype = act.type;
			var excutorGroup = act.excutorGroup;
			excutorGroup = excutorGroup.replaceAll(",", "','");
			excutorGroup = "'" + excutorGroup + "'";
			var pas = excutorGroup.split(",");
			if (pas.length > 1000) {
				pas = pas.slice(0, 999);
				excutorGroup = pas.join(",");
			}
			var param = new tlv8.RequestParam();
			param.set("ids", excutorGroup);
			var re = tlv8.XMLHttpRequest("getExecutorsAction", param);// this.QueryBySql("system",
			var psms = window.eval("(" + re.data.data + ")");
			htmls.push('<li>');
			htmls.push('<fieldset data-role="controlgroup">');
			htmls
					.push('<input type="checkbox" name="'
							+ id
							+ '" id="'
							+ id
							+ '" label="'
							+ (label || name)
							+ '" actype="'
							+ actype
							+ '" class="custom activity" onclick="onActivitySelect(this)"/>');
			htmls.push('<label for="' + id + '">' + name + '</label>');
			htmls.push('</fieldset>');
			htmls.push('</li>');
			var exts = [];
			exts
					.push('<div style="width:100%;display:none;" class="activitypsmlist"');
			exts.push('id="' + id + '_Executors">');
			for (var j = 0; j < psms.length; j++) {
				exts.push('<li>');
				exts.push('<fieldset data-role="controlgroup">');
				exts.push('<input type="checkbox" name="' + psms[j].SID
						+ '" id="' + psms[j].SID + '" label="' + psms[j].SNAME
						+ '" fid="' + psms[j].SFID
						+ '" onclick="onExcutorSelect(this)"');
				exts.push('class="custom"/>');
				exts.push('<label for="' + psms[j].SID + '">' + psms[j].SNAME
						+ '</label>');
				exts.push('</fieldset>');
				exts.push('</li>');
			}
			exts.push('</div>');
			$("#Executorslist").append(exts.join(""));
		}
		return htmls.join("");
	}
};
$(document).ready(function() {
	if (!checkPathisHave($dpjspath + "dialog.js"))
		createJSSheet($dpjspath + "dialog.js");
	if (!checkPathisHave($dpcsspath + "dialog.css"))
		createStyleSheet($dpcsspath + "dialog.css");
});

/**
 * 以下为了兼容云捷代码
 */
justep.yn = tlv8;