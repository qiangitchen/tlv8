/*
 * 波特图
 * @memberOf {TypeName} 
 */
var botItemMap = new Map();
var tilItemMap = new Map();
var JBot = function() {
	this.itemID; // 当前节点ID
	this.butlenth = 0; // 节点长度(边框+位置)
	this.bots = new Array(); // 波特图id数组
	this.map = new Map(); // 波特图记录
	this.rows = 1; // 波特图的行数
	this.cell = 0;
	this.botData;
};

JBot.prototype.creatTilItem = function(Itdata) {
	var title=Itdata.title;
	var executor=Itdata.executor;
	var excutordpt=Itdata.excutordpt;
	var status=Itdata.status;
	var creator=Itdata.creator;
	var createTime=Itdata.createTime;
	var auditeTime=Itdata.auditeTime;
	var itemid = new UUID().createUUID();
	var itemHTML = '标题:' + title + '\n';
	var len = (title.length / 2 > 7) ? (title.length / 2) : 7;
	for (var i = 0; i < len; i++) {
		itemHTML += '——';
	}
	itemHTML += '\n执行者: ' + executor + '\n' + '所属部门: ' + excutordpt + '\n'
			+ '状态: ' + status + '\n' + '提交人: ' + creator + '\n' + '提交时间: '
			+ createTime + '\n' + '处理时间: ' + auditeTime;
	return itemHTML;
};

/*
 * 渲染波特图 @param {Object} botBody 显示波特图的div的id @param {Object} botData
 * json数据组[{title:"", executor:"", excutordpt:"", status:"",createTime:"",
 * auditeTime:""},{...}]
 */
JBot.prototype.drawBot = function(botBody, botData) {
	if (!botBody || !botData) {
		mAlert("参数不正确:JBot>botBody,botData!");
		return;
	}
	if (typeof botData == "string") {
		try {
			botData = eval("(" + botData + ")");
		} catch (e) {
			mAlert("参数不正确:JBot>botData必须为json数组!");
			return;
		}
	}
	for (var i = 0; i < botData.length; i++) {
		var Itdata = botData[i];
		if (typeof Itdata == "string")
			Itdata = eval("(" + Itdata + ")");
		if (Itdata.activity != "endActivity") {
			var cuActivity = document.getElementById(Itdata.activity);
			if (!cuActivity) {
				continue;
			}
			cuActivity.title = this.creatTilItem(Itdata);
			if (Itdata.status == "已完成") {
				if (SVG.supported) {
					cuActivity.style.background = "green";
					cuActivity.style.border = '1px solid green';
				} else {
					cuActivity.fillcolor = "green";
					cuActivity.strokecolor = 'green';
				}
				document.getElementById(Itdata.activity + "_Label").style.color = "white";
			} else if (Itdata.status == "已终止") {
				if (SVG.supported) {
					cuActivity.style.background = "red";
					cuActivity.style.border = '1px solid red';
				} else {
					cuActivity.fillcolor = "red";
					cuActivity.strokecolor = 'red';
				}
				document.getElementById(Itdata.activity + "_Label").style.color = "white";
			} else if (Itdata.status == "已回退") {
				if (SVG.supported) {
					cuActivity.style.background = "#FFFF00";
					cuActivity.style.border = '1px solid #FFFF00';
				} else {
					cuActivity.fillcolor = "#FFFF00";
					cuActivity.strokecolor = '#FFFF00';
				}
				document.getElementById(Itdata.activity + "_Label").style.color = "#000";
				var ID = Itdata.id;
				var cuActivity = Itdata.activity;
				var sql = "select SACTIVITY from SA_TASK where SPARENTID = '"
						+ ID + "'";
				tlv8.sqlQueryAction("system", sql, function(r) {
					if (r.getCount() > 0) {
						var toActivity = r.getValueByName("SACTIVITY");
						var groupObj = document
								.getElementById('group');
						var Love = groupObj.bindClass;
						var snode = Love.getNodeById(cuActivity);
						var n = Love.getNodeById(toActivity);
						var ln = new PolyLine();// 创建连线
						ln.setShape("polyline");
						ln.init();
						ln.link({
							fromObj : snode,
							toObj : n
						});
						if (ln.obj.pline) {
							ln.obj.pline.stroke({
								color : 'red'
							});
						} else {
							ln.obj.strokecolor = "red";
						}
						ln.obj.title = "回退";
					}
				}, false);
			} else if (Itdata.status == "尚未处理") {
				document.getElementById(Itdata.activity).title = "当前环节:"
						+ document.getElementById(Itdata.activity).title;
				if (SVG.supported) {
					cuActivity.style.background = "#006699";
				} else {
					document.getElementById(Itdata.activity).fillcolor = "#006699";
				}
				document.getElementById(Itdata.activity + "_Label").style.color = "white";
			}
		}
	}
};
/*
 * 加载波特图 @param {Object} flowID
 */
JBot.prototype.loadBot = function(flowID) {
	var param = new tlv8.RequestParam();
	param.set("flowID", flowID);
	tlv8.XMLHttpRequest("flowloadbotAction", param, "post", true,
			function(r) {
				if (r.data.flag == "false") {
					// mAlert(r.data.message);
					return false;
				} else {
					var botData = eval("(" + r.data.data + ")");
					var bot = new JBot();
					bot.botData = botData;
					bot.drawBot("main_bot_body", botData);
					if (botData.length > 0) {
						start.fillcolor = "green";
						start.textObj.style.color = '#fff';
					}
				}
			});
};

/*
 * 加载波特图 @param {Object} flowID
 */
JBot.prototype.loadBotX = function(flowID) {
	var param = new tlv8.RequestParam();
	param.set("flowID", flowID);
	var r = tlv8
			.XMLHttpRequest("flowloadbotXAction", param, "post", false);
	var red = [];
	if (r.data.flag != "false") {
		var botData = eval("(" + r.data.data + ")");
		red = botData;
	}
	return botData;
};

/*
 * 流程轨迹 @memberOf {TypeName}
 */
var JIocus = function() {
	this.drawfg;
	this.drawItems;
	this.iocusData;
	this.flowID; // 流程标识
	this.taskID; // 任务标识
	this.sData1; // 单据主键
};
/*
 * 加载流程数据 @memberOf {TypeName} @return {TypeName}
 */
JIocus.prototype.loadData = function() {
	botItemMap = new Map();
	tilItemMap = new Map();
	this.flowID = tlv8.RequestURLParam.getParam("flowID"); // 流程标识
	this.taskID = tlv8.RequestURLParam.getParam("taskID"); // 任务标识
	this.sData1 = tlv8.RequestURLParam.getParam("sData1"); // 单据主键
	var currentUrl = tlv8.RequestURLParam.getParam("currentUrl");
	var flowID = this.flowID;
	if ((flowID && flowID != "" && flowID != "undefined") || (currentUrl
			&& currentUrl != "" && currentUrl != "undefined")) {
		var JIocus = this;
		var param = new tlv8.RequestParam();
		param.set("flowID", flowID);
		param.set("currentUrl", currentUrl);
		tlv8.XMLHttpRequest("flowloadIocusAction", param, "post", true,
				function(r) {
					if (r.data.flag == "false") {
						alert(r.data.message);
						return false;
					} else {
						var reData = eval("(" + r.data.data + ")");
						drow_init(reData.id, reData.name, reData.jsonStr);
						if (flowID && flowID != "") {
							var bot = new JBot();
							bot.loadBot(flowID);
							JIocus.checkisfinish(flowID);
							initDesigner(bot.loadBotX(flowID));
						}
					}
				});
	} else {
		var processID = tlv8.RequestURLParam.getParam("processID"); // 流程图标识
		var param = new tlv8.RequestParam();
		param.set("sprocessid", processID);
		tlv8.XMLHttpRequest("getFlowDrawAction", param, "post", true,
				function(r) {
					drow_init(r.sprocessid, r.sprocessname, r.sprocessacty);
				});
	}
};

/*
 * 检查流程是否已结束 @param {Object} flowID @return {TypeName}
 */
JIocus.prototype.checkisfinish = function(flowID) {
	var param = new tlv8.RequestParam();
	param.set("flowID", flowID);
	tlv8.XMLHttpRequest("flowcheckfinishAction", param, "post", true,
			function(r) {
				if (r.data.flag == "false") {
					mAlert(r.data.message);
					return false;
				} else {
					if (r.data.data == 'true') {
						if (SVG.supported) {
							end.style.background = "#green";
						} else {
							end.fillcolor = "green";
						}
					}
				}
			});
};
/*
 * 显示提示信息 @param {Object} obj @return {TypeName}
 */
function show_popup_status(obj) {
	var texts = tilItemMap.get(obj.id);
	if (!texts || texts == "")
		return;
	return texts;
}