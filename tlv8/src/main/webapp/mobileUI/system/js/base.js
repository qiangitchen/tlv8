var cpath = "/tlv8";
var J$ = function(id) {
	return document.getElementById(id);
};
var J$n = function(name) {
	return document.getElementsByTagName(name);
};
var J_u_encode = function(str) {
	return encodeURIComponent(encodeURIComponent(str));
};
var J_u_decode = function(str) {
	try {
		return decodeURIComponent(decodeURIComponent(str));
	} catch (e) {
		return str;
	}
};
var $dpjspath = null;
var scripts = document.getElementsByTagName("script");
for (i = 0; i < scripts.length; i++) {
	if (scripts[i].src.substring(scripts[i].src.length - 11).toLowerCase() == '/js/base.js') {
		$dpjspath = scripts[i].src.substring(0, scripts[i].src.length - 7);
		break;
	}
}
var $dpcsspath = $dpjspath ? $dpjspath.replace("/js/", "/css/") : null;
var $dpimgpath = $dpjspath ? $dpjspath.replace("/js/", "/images/") : null;
/*
 * 添加JS引用
 */
var createJSSheet = function(jsPath) {
	var head = document.getElementsByTagName('HEAD')[0];
	var script = document.createElement('script');
	script.src = jsPath;
	script.type = 'text/javascript';
	head.appendChild(script);
};
/*
 * 添加CSS引用
 */
var createStyleSheet = function(cssPath) {
	var head = document.getElementsByTagName('HEAD')[0];
	var style = document.createElement('link');
	style.href = cssPath;
	style.rel = 'stylesheet';
	style.type = 'text/css';
	head.appendChild(style);
};
/*
 * 检查引用文件是否已存在
 */
var checkPathisHave = function(path) {
	var Hhead = document.getElementsByTagName('HEAD')[0];
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
if (!justep)
	var justep = {};
justep.Context = {
	userInfo : {},
	getAllRoles : function() {
		var result = tlv8.XMLHttpRequest("getAllRolesAction");
		if (result.data == "false") {
			alert(result.data.flag);
		} else {
			var data = result.data.data;
			if (typeof data == "string") {
				data = window.eval("(" + data + ")");
			}
			return data;
		}
	},
	getCurrentDeptID : function() {
		this.checklogin();
		return this.userInfo.deptid;
	},
	getCurrentDeptCode : function() {
		this.checklogin();
		return this.userInfo.deptcode;
	},
	getCurrentDeptFCode : function() {
		this.checklogin();
		return this.userInfo.deptfcode;
	},
	getCurrentDeptFID : function() {
		this.checklogin();
		return this.userInfo.deptfid;
	},
	getCurrentDeptFName : function() {
		this.checklogin();
		return this.userInfo.deptfname;
	},
	getCurrentDeptName : function() {
		this.checklogin();
		return this.userInfo.deptname;
	},
	getCurrentOgnCode : function() {
		this.checklogin();
		return this.userInfo.ogncode;
	},
	getCurrentOgnFCode : function() {
		this.checklogin();
		return this.userInfo.ognfcode;
	},
	getCurrentOgnFID : function() {
		this.checklogin();
		return this.userInfo.ognfid;
	},
	getCurrentOgnFName : function() {
		this.checklogin();
		return this.userInfo.ognfname;
	},
	getCurrentOgnID : function() {
		this.checklogin();
		return this.userInfo.ognid;
	},
	getCurrentOgnName : function() {
		this.checklogin();
		return this.userInfo.ognname;
	},
	getCurrentOrgCode : function() {
		this.checklogin();
		return this.userInfo.orgcode;
	},
	getCurrentOrgFCode : function() {
		this.checklogin();
		return this.userInfo.orgfcode;
	},
	getCurrentOrgFID : function() {
		this.checklogin();
		return this.userInfo.orgfid;
	},
	getCurrentOrgFName : function() {
		this.checklogin();
		return this.userInfo.orgfname;
	},
	getCurrentOrgID : function() {
		this.checklogin();
		return this.userInfo.orgid;
	},
	getCurrentOrgName : function() {
		this.checklogin();
		return this.userInfo.orgname;
	},
	getCurrentPersonCode : function() {
		this.checklogin();
		return this.userInfo.personcode;
	},
	getCurrentPersonID : function() {
		this.checklogin();
		return this.userInfo.personid;
	},
	getCurrentPersonMemberFNameWithAgent : function() {

	},
	getCurrentPersonMemberNameWithAgent : function() {

	},
	getCurrentPersonName : function() {
		this.checklogin();
		return this.userInfo.personName;
	},
	getCurrentPersonFID : function() {
		this.checklogin();
		return this.userInfo.personfid;
	},
	getCurrentPersonFCode : function() {
		this.checklogin();
		return this.userInfo.personfcode;
	},
	getCurrentPersonFName : function() {
		this.checklogin();
		return this.userInfo.personfname;
	},
	getCurrentPosCode : function() {
		this.checklogin();
		return this.userInfo.positioncode;
	},
	getCurrentPosFCode : function() {
		this.checklogin();
		return this.userInfo.positionfcode;
	},
	getCurrentPosFID : function() {
		this.checklogin();
		return this.userInfo.positionfid;
	},
	getCurrentPosFName : function() {
		this.checklogin();
		return this.userInfo.positionfname;
	},
	getCurrentPosID : function() {
		this.checklogin();
		return this.userInfo.positionid;
	},
	getCurrentPosName : function() {
		this.checklogin();
		return this.userInfo.positionname;
	},
	getCurrentProcess : function() {
		return getParamValueFromUrl("process");
	},
	getCurrentActivity : function() {
		return getParamValueFromUrl("activity");
	},
	getCurrentProcessLabel : function(process) {
		var param = new tlv8.RequestParam();
		param.set(process || this.getCurrentProcess());
		var result = tlv8.XMLHttpRequest("getProcessLabelAction", param);
		return result.processlabel;
	},
	getExecuteContext : function() {

	},
	getExecutor : function() {

	},
	getExecutorPerson : function() {

	},
	getLanguage : function() {
		return this.userInfo.locale;
	},
	getLoginDate : function() {
		return this.userInfo.loginDate;
	},
	getProcessData1 : function() {
		return getParamValueFromUrl("sData1") || getParamValueFromUrl("rowid");
	},
	getProcessData2 : function() {

	},
	getProcessData3 : function() {

	},
	getProcessData4 : function() {

	},
	init : function() {
		var result = tlv8.XMLHttpRequest("system/User/initPortalInfo");
		if (typeof result == "string") {
			result = window.eval("(" + result + ")");
		}
		this.userInfo = window["eval"]("(" + result[0].data + ")");
		if (this.userInfo.personid == "null" || this.userInfo.personid == "") {
			try {
				justepYnApp.reloadLoginPage("连接中断，请重新登录.");
			} catch (e) {
				alert("连接中断，请重新登录.");
				window.open(cpath+'/mobileUI/portal/login.html', '_self');
			}
		}
	},
	checklogin : function() {
		if (!this.userInfo.personid || this.userInfo.personid == ""
				|| this.userInfo.personid == "null") {
			this.init();
		}
	}
};
$(document).ready(function(event, data) {
	// var param = new tlv8.RequestParam();
	// param.set("personid", getParamValueFromUrl("personid"));
	// tlv8.XMLHttpRequest("initPersonInfoAction", param, "POST", false);
});
$(function(){
	justep.Context.init();
	writeLog();
});
function UUID() {
	this.id = this.createUUID();
	return this;
}
UUID.prototype.valueOf = function() {
	return this.id;
};
UUID.prototype.toString = function() {
	return this.id;
};
UUID.prototype.createUUID = function() {
	var dg = new Date(1582, 10, 15, 0, 0, 0, 0);
	var dc = new Date();
	var t = dc.getTime() - dg.getTime();
	var tl = UUID.getIntegerBits(t, 0, 31);
	var tm = UUID.getIntegerBits(t, 32, 47);
	var thv = UUID.getIntegerBits(t, 48, 59) + '1';
	var csar = UUID.getIntegerBits(UUID.rand(4095), 0, 7);
	var csl = UUID.getIntegerBits(UUID.rand(4095), 0, 7);
	var n = UUID.getIntegerBits(UUID.rand(8191), 0, 7)
			+ UUID.getIntegerBits(UUID.rand(8191), 8, 15)
			+ UUID.getIntegerBits(UUID.rand(8191), 0, 7)
			+ UUID.getIntegerBits(UUID.rand(8191), 8, 15)
			+ UUID.getIntegerBits(UUID.rand(8191), 0, 15);
	return tl + tm + thv + csar + csl + n;
};
UUID.getIntegerBits = function(val, start, end) {
	var base16 = UUID.returnBase(val, 16);
	var quadArray = new Array();
	var quadString = '';
	var i = 0;
	for (i = 0; i < base16.length; i++) {
		quadArray.push(base16.substring(i, i + 1));
	}
	for (i = Math.floor(start / 4); i <= Math.floor(end / 4); i++) {
		if (!quadArray[i] || quadArray[i] == '')
			quadString += '0';
		else
			quadString += quadArray[i];
	}
	return quadString;
};
UUID.returnBase = function(number, base) {
	return (number).toString(base).toUpperCase();
};
UUID.rand = function(max) {
	return Math.floor(Math.random() * (max + 1));
};

var topparent = function() {
	var nowwdw = window;
	var parentwdw = window.parent;
	while (nowwdw != parentwdw) {
		nowwdw = parentwdw;
		parentwdw = nowwdw.parent;
	}
	return nowwdw;
}();
$(function() {
	if (parent != window) {
		parent.window = parent.window;
		parent.document = parent.document;
		parent.document.body = parent.document.body;
		parent.J$ = parent.document.getElementById;
		parent.J$n = parent.document.getElementsByTagName;
	}
});

if(!tlv8)
	var tlv8 = {};

tlv8.RequestParam = function() {
	var params = new Map();
	this.params = params;
	var set = function(key, value) {
		this.params.put(key, value);
	};
	this.set = set;
	var get = function(key) {
		return this.params.gut(key);
	};
	this.get = get;
};

tlv8.getRequestURI = function() {
	var url = window.location.pathname;
	return url.substring(0, url.lastIndexOf("/"));
};
function getHost() {
	var surl = window.location.href;
	var host = surl.substring(0, surl.indexOf(cpath)) + cpath;
	return host;
}
tlv8.showSate = function(state) {
	try {
		if (topparent.tlv8.showSate) {
			topparent.tlv8.showSate(state);
		}
	} catch (e) {
	}
};
function mAlert(msg,title){
	try{
		mui.alert(msg,title);
	}catch (e) {
		try{
			$.messager.alert(title||'提示',msg);
		}catch (ee) {
			alert(msg);
		}
	}
}

tlv8.XMLHttpRequest = function(actionName, param, post, ayn, callBack,
		unShowState) {
	var localurl = window.location.href;
	if (localurl.indexOf("file://") > -1) {
		return;
	}
	if (!unShowState) {
		tlv8.showModelState(true);
		window.actiondoing = true;
	}
	var paramMap = param ? param.params : new Map();
	var params = "";
	if (paramMap) {
		if (!paramMap.isEmpty()) {
			var list = paramMap.keySet();
			for (var i = 0; i < list.length; i++) {
				var sk = list[i];
				if (i > 0)
					params += "&";
				params += sk + "=" + J_u_encode(paramMap.get(sk));
			}
		}
	}
	actionName = (actionName.startWith(cpath)?actionName:(cpath+"/"+actionName));
	var rs;
	$.ajax({
		type : post ? post : "post",
		async : ayn ? ayn : false,
		url : actionName,
		data : params,
		success : function(result, textStatus) {
			tlv8.showModelState(false);
			rs = result;
			try{
				rs = window.eval("("+rs+")");
			}catch (e) {
			}
			if (callBack && typeof callBack == "function") {
				try{
					callBack(rs);
				}catch (e) {
				}
			}
		},
		error : function() {
			tlv8.showModelState(false);
			// alert("action："+actionName+",执行失败");
		},
		complete : function() {
			tlv8.showModelState(false);
		}
	});
	return rs;
};

function writeLog(ev, actionName, discription) {
	if (window.isWriteLog == false)
		return;
	try {
		var HTMLhead = document.getElementsByTagName('HEAD')[0];
		var activateName = HTMLhead.getElementsByTagName("title")[0].innerHTML;
		var srcPath = window.location.pathname;
		if (srcPath.indexOf("?") > 0)
			srcPath = srcPath.substring(0, srcPath.indexOf("?"));
		srcPath = srcPath.replace("/mobileUI/", "/");
		var param = new tlv8.RequestParam();
		param.set("personID", justep.Context.getCurrentPersonID());
		param.set("processName", "");
		param.set("activateName", activateName);
		param.set("actionName", actionName ? actionName : "查看");
		param.set("srcPath", srcPath);
		param.set("discription", discription ? discription : "");
		tlv8.XMLHttpRequest("WriteSystemLogAction", param, "post", true,
				null, true);
	} catch (e) {
	}
}

/**
 * @name tlv8.Queryaction
 * @function
 * @description 公用函数java调用动作针对查询
 * @param actionName
 * @param post
 * @param callBack
 * @param data
 * @param where
 * @param ays
 * @returns {JSON}
 */
tlv8.Queryaction = function(actionName, post, callBack, data, where, ays) {
	var table = data ? data.table : "";
	where = where ? where : "";
	var dbkay = data ? data.dbkay : "";
	var relation = data ? data.relation : "";
	var orderby = data ? data.orderby : "";
	if (!where || where == "") {
		where = tlv8.RequestURLParam.getParamByURL(actionName, "where");
	}
	var param = new tlv8.RequestParam();
	param.set("dbkay", dbkay);
	param.set("table", table);
	param.set("relation", relation);
	param.set("orderby", orderby);
	if (actionName.indexOf("getGridActionBySQL") > -1) {
		actionName += "&where=" + J_u_encode(CryptoJS.AESEncrypt(where));
	} else {
		param.set("where", CryptoJS.AESEncrypt(where));
	}
	var isay = (ays == false) ? ays : true;
	var rscallBack = function(r) {
		if (callBack)
			callBack(r.data);
	};
	var result = tlv8.XMLHttpRequest(actionName, param, post, isay,
			rscallBack);
	if (ays == false) {
		if (callBack)
			callBack(result.data);
		return result.data;
	}
};
/**
 * @name tlv8.Deleteaction
 * @function
 * @description 公用函数：java调用动作{针对带参数操作 删除}
 * @param actionName
 * @param post
 * @param callBack
 * @param rowid
 * @param data
 * @param ays
 * @returns {JSON}
 */
tlv8.Deleteaction = function(actionName, post, callBack, rowid, data, ays) {
	if (!rowid || rowid == "") {
		alert("rowid不能为空！");
		return;
	}
	var table = data ? data.table : "";
	var dbkay = data ? data.dbkay : "";
	var Cascade = data ? data.Cascade : "";
	var param = new tlv8.RequestParam();
	param.set("dbkay", dbkay);
	param.set("table", table);
	param.set("rowid", rowid);
	param.set("Cascade", Cascade);
	var isay = (ays == false) ? ays : true;
	var rscallBack = function(r) {
		if (callBack)
			callBack(r.data);
	};
	var result = tlv8.XMLHttpRequest(actionName, param, post, isay,
			rscallBack);
	if (ays == false) {
		if (callBack)
			callBack(result.data);
		return result.data;
	}
};
/**
 * @name tlv8.saveAction
 * @function
 * @description 公用函数：java调用动作{针对带参数操作 保存}
 * @param actionName
 * @param post
 * @param callBack
 * @param data
 * @param allreturn
 * @param ays
 * @returns {JSON}
 */
tlv8.saveAction = function(actionName, post, callBack, data, allreturn,
		ays) {
	if (!data || data == "") {
		return;
	}
	var table = data.table;
	var cells = data.cells;
	var dbkay = data ? data.dbkay : "";
	var param = new tlv8.RequestParam();
	param.set("dbkay", dbkay);
	param.set("table", table);
	param.set("cells", cells);
	var ays_true = (ays == false) ? ays : true;
	var rscallBack = function(r) {
		if (callBack && allreturn)
			callBack(r);
		else if (callBack) {
			callBack(r.data);
		}
	};
	var result = tlv8.XMLHttpRequest(actionName, param, post, ays_true,
			rscallBack);
	if (ays_true == false && result) {
		if (callBack && allreturn)
			callBack(result);
		else if (callBack) {
			callBack(result.data);
		}
		return result;
	}
};
tlv8.strToXML = function(str) {
	str = str.toString().replaceAll("&", "&amp;");
	var x = "<?xml version=\"1.0\" encoding='UTF-8' ?>\n<root>\n" + str
			+ "</root>";
	if (window.ActiveXObject) {
		var xmlDom = new ActiveXObject("Microsoft.XMLDOM");
		xmlDom.async = "false";
		xmlDom.loadXML(x);
		return xmlDom;
	} else if (document.implementation
			&& document.implementation.createDocument) {
		var parser = new DOMParser();
		var xmlDom = parser.parseFromString(x, "text/xml");
		return xmlDom;
	}
};
tlv8.sqlQueryAction = function(dbkey, sql, callBack, ayn) {
	var type = getOs();
	var rsultData = function(r) {
		this.r = r;
		this.data = (r) ? r.data : {};
		this.getNode = function(tag) {
			var s = "";
			if (tag == "data") {
				s = r.data.data;
			} else if (tag == "flag") {
				s = r.data.flag;
			} else {
				s = r.data.message;
			}
			return s;
		};
		this.getCount = function() {
			var txt = r.data.data;
			var xmlDoc = tlv8.strToXML(txt);
			return (type == "MSIE") ? xmlDoc.getElementsByTagName("count")[0].childNodes[0].nodeValue
					: xmlDoc.getElementsByTagName("count")[0].childNodes
							.item(0).textContent;
		};
		this.getColumns = function() {
			var txt = r.data.data;
			var xmlDoc = tlv8.strToXML(txt);
			return (type == "MSIE") ? xmlDoc.getElementsByTagName("columns")[0].childNodes[0].nodeValue
					: xmlDoc.getElementsByTagName("columns")[0].childNodes
							.item(0).textContent;
		};
		this.getValueByName = function(columnName) {
			if (this.getCount() == 0) {
				return "";
			}
			var txt = r.data.data;
			var xmlDoc = tlv8.strToXML(txt);
			var redata = (type == "MSIE") ? xmlDoc.getElementsByTagName("rows")
					.item(0).text
					: xmlDoc.getElementsByTagName("rows").item(0).textContent;
			try {
				redata = redata.replaceAll("&amp;", "&");
			} catch (e) {
			}
			var $dval = (redata && redata != "") ? redata.split(";") : [];
			var colums = this.getColumns();
			var colum = colums.split(",");
			var index = 0;
			var have = false;
			for (var i = 0; i < colum.length; i++) {
				if (colum[i] == columnName) {
					index = i;
					have = true;
					break;
				}
			}
			if (have == false) {
				alert("指定的列名无效，请注意大小写！");
				return false;
			}
			return $dval[index];
		};
		this.getDatas = function() {
			var $dval = r.data.data;
			if (!$dval || $dval == "")
				return [ "" ];
			var xmldata = tlv8.strToXML($dval);
			if (!xmldata)
				return [ "" ];
			try {
				var datas = (type == "MSIE") ? xmldata.getElementsByTagName(
						"rows").item(0).text : xmldata.getElementsByTagName(
						"rows").item(0).textContent;
				datas = datas.replaceAll("&amp;", "&");
			} catch (e) {
			}
			if (!datas)
				return [ "" ];
			var $dval = datas.split(";");
			return $dval;
		};
	};
	ayn = (ayn == true) ? true : false;
	var param = new tlv8.RequestParam();
	param.set("dbkey", CryptoJS.AESEncrypt(dbkey));
	param.set("querys", CryptoJS.AESEncrypt(sql));
	var recallback = function(r) {
		if (callBack) {
			var res = new rsultData(r);
			callBack(res);
		}
	};
	var result = tlv8.XMLHttpRequest("sqlQueryAction", param, "POST", ayn,
			recallback);
	if (ayn == false) {
		var res = new rsultData(result);
		return res;
	}
};
tlv8.sqlQueryActionforJson = function(dbkey, sql, callBack, ayn) {
	ayn = (ayn == true) ? true : false;
	var param = new tlv8.RequestParam();
	param.set("dbkey", CryptoJS.AESEncrypt(dbkey));
	param.set("querys", CryptoJS.AESEncrypt(sql));
	var recallback = function(r) {
		if (callBack) {
			var reData = (r.data.data) ? (window.eval("(" + r.data.data + ")"))
					: [];
			r.data.data = reData;
			callBack(r.data);
		}
	};
	var r = tlv8.XMLHttpRequest("sqlQueryActionforJson", param, "POST",
			ayn, recallback);
	if (ayn == false) {
		try {
			var reData = (r.data.data) ? (window.eval("(" + r.data.data + ")"))
					: [];
			r.data.data = reData;
			return r.data;
		} catch (e) {
			return {};
		}
	}
};
tlv8.sqlUpdateAction = function(dbkey, sql, callBack, ayn) {
	ayn = (ayn == true) ? true : false;
	var param = new tlv8.RequestParam();
	param.set("dbkey", CryptoJS.AESEncrypt(dbkey));
	param.set("querys", CryptoJS.AESEncrypt(sql));
	var recallback = function(r) {
		if (callBack) {
			callBack(r.data);
		}
	};
	var r = tlv8.XMLHttpRequest("sqlUpdateAction", param, "POST", ayn,
			recallback);
	if (ayn == false) {
		var res = r.data;
		return res;
	}
};
tlv8.callProcedureAction = function(dbkey, ProduceName, Param, callBack,
		ayn) {
	ayn = (ayn == true) ? true : false;
	var param = new tlv8.RequestParam();
	param.set("dbkey", dbkey);
	param.set("ProduceName", ProduceName);
	param.set("Param", Param);
	var recallback = function(r) {
		if (callBack) {
			callBack(r.data);
		}
	};
	var r = tlv8.XMLHttpRequest("callProcedureAction", param, "POST", ayn,
			recallback);
	if (ayn == false) {
		var res = r.data;
		return res;
	}
};
tlv8.callFunctionAction = function(dbkey, ProduceName, Param, callBack,
		ayn) {
	ayn = (ayn == true) ? true : false;
	var param = new tlv8.RequestParam();
	param.set("dbkey", dbkey);
	param.set("ProduceName", ProduceName);
	param.set("Param", Param);
	var recallback = function(r) {
		if (callBack) {
			callBack(r.data);
		}
	};
	var r = tlv8.XMLHttpRequest("callFunctionAction", param, "POST", ayn,
			recallback);
	if (ayn == false) {
		var res = r.data;
		return res;
	}
};
function getNevType() {
	try {
		if ((navigator.userAgent.indexOf('MSIE') >= 0)
				&& (navigator.userAgent.indexOf('Opera') < 0)) {
			return 'IE';
		} else if (navigator.userAgent.indexOf('Firefox') >= 0) {
			return 'Firefox';
		} else if (navigator.userAgent.indexOf("Opera") >= 0) {
			return 'Opera';
		} else if (navigator.userAgent.indexOf("Camino") >= 0) {
			return 'Camino';
		} else {
			return 'Other';
		}
	} catch (e) {
		return false;
	}
}
function getOs() {
	var OsObject = "";
	if (navigator.userAgent.indexOf("MSIE") > 0) {
		return "MSIE";
	}
	if (isFirefox = navigator.userAgent.indexOf("Firefox") > 0) {
		return "Firefox";
	}
	if (isSafari = navigator.userAgent.indexOf("Safari") > 0) {
		return "Safari";
	}
	if (isCamino = navigator.userAgent.indexOf("Camino") > 0) {
		return "Camino";
	}
	if (isMozilla = navigator.userAgent.indexOf("Gecko/") > 0) {
		return "Gecko";
	}
}
var Map = function() {
	var struct = function(key, value) {
		this.key = key;
		this.value = value;
	};
	var put = function(key, value) {
		for (var i = 0; i < this.arr.length; i++) {
			if (this.arr[i].key === key) {
				this.arr[i].value = value;
				return;
			}
		}
		this.arr[this.arr.length] = new struct(key, value);
	};
	var get = function(key) {
		for (var i = 0; i < this.arr.length; i++) {
			if (this.arr[i].key === key) {
				return this.arr[i].value;
			}
		}
		return null;
	};
	var remove = function(key) {
		var v;
		for (var i = 0; i < this.arr.length; i++) {
			v = this.arr.pop();
			if (v.key === key) {
				continue;
			}
			this.arr.unshift(v);
		}
	};
	var size = function() {
		return this.arr.length;
	};
	var isEmpty = function() {
		return this.arr.length <= 0;
	};
	var containsKey = function(key) {
		var result = false;
		for (var i = 0; i < this.arr.length; i++) {
			if (this.arr[i].key === key) {
				result = true;
			}
		}
		return result;
	};
	var containsValue = function(value) {
		var result = false;
		for (var i = 0; i < this.arr.length; i++) {
			if (this.arr[i].value === value) {
				result = true;
			}
		}
		return result;
	};
	var keySet = function() {
		var result = new Array();
		for (var i = 0; i < this.arr.length; i++) {
			result.push(this.arr[i].key);
		}
		return result;
	};
	this.arr = new Array();
	this.get = get;
	this.put = put;
	this.remove = remove;
	this.size = size;
	this.isEmpty = isEmpty;
	this.containsKey = containsKey;
	this.containsValue = containsValue;
	this.keySet = keySet;
};
Map.prototype.toString = function() {
	var rs = "{";
	for (var i = 0; i < this.arr.length; i++) {
		if (i > 0)
			rs += ",";
		rs += "\"" + this.arr[i].key + "\":\"" + this.arr[i].value + "\"";
	}
	rs += "}";
	return rs;
};

function trim(text) {
	if (text == undefined) {
		return "";
	} else {
		return text.replace(/(^\s*)|(\s*$)/g, "");
	}
}
String.prototype.trim = function() {
	return this.replace(/(^\s+)|(\s+$)/g, "");
};
String.prototype.ltrim = function() {
	return this.replace(/^\s+/, "");
};
String.prototype.rtrim = function() {
	return this.replace(/\s+$/, "");
};
/**
 * @name startWith
 * @description 是否以某个字符开始
 * @returns {string}
 */
String.prototype.startWith = function(str) {
	return this.indexOf(str)==0;
};
var reMoveStr = function(str1, str2) {
	if (str1.indexOf(str2) > -1)
		str1 = str1.replace(str2, "");
	return str1;
};
var replaceFirst = function(str, p, m) {
	if (str.indexOf(p) == 0)
		str = str.replace(p, m);
	return str;
};
String.prototype.replaceFirst = function(p, m) {
	if (this.indexOf(p) == 0) {
		return this.replace(p, m);
	}
	return this;
};
String.prototype.replaceAll = function(p, m) {
	return this.replace(new RegExp(p, "gm"), m);
};
Date.prototype.format = function(fmt) {
	var o = {
		"M+" : this.getMonth() + 1,
		"d+" : this.getDate(),
		"h+" : this.getHours() % 24 == 0 ? 24 : this.getHours() % 24,
		"H+" : this.getHours(),
		"m+" : this.getMinutes(),
		"s+" : this.getSeconds(),
		"q+" : Math.floor((this.getMonth() + 3) / 3),
		"S" : this.getMilliseconds()
	};
	var week = {
		"0" : "\u65e5",
		"1" : "\u4e00",
		"2" : "\u4e8c",
		"3" : "\u4e09",
		"4" : "\u56db",
		"5" : "\u4e94",
		"6" : "\u516d"
	};
	if (/(y+)/.test(fmt)) {
		fmt = fmt.replace(RegExp.$1, (this.getFullYear() + "")
				.substr(4 - RegExp.$1.length));
	}
	if (/(E+)/.test(fmt)) {
		fmt = fmt
				.replace(
						RegExp.$1,
						((RegExp.$1.length > 1) ? (RegExp.$1.length > 2 ? "\u661f\u671f"
								: "\u5468")
								: "")
								+ week[this.getDay() + ""]);
	}
	for ( var k in o) {
		if (new RegExp("(" + k + ")").test(fmt)) {
			fmt = fmt.replace(RegExp.$1, (RegExp.$1.length == 1) ? (o[k])
					: (("00" + o[k]).substr(("" + o[k]).length)));
		}
	}
	return fmt;
};

function sAlert(str, time) {
	try{
		mui.toast(str);
		return;
	}catch (e) {
	}
	if (topparent != window && topparent.sAlert)
		topparent.sAlert(str, time);
	return;
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
	var msgObj = document.createElement("div");
	msgObj.setAttribute("id", "msgDiv");
	msgObj.setAttribute("align", "center");
	msgObj.style.background = titlecolor;
	msgObj.style.border = "1px solid " + bordercolor;
	msgObj.style.position = "absolute";
	msgObj.style.left = "88%";
	msgObj.style.top = "1%";
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

tlv8.showModelState = function(state) {
	if (state) {
// var mod_allv = document.createElement("div");
// $(mod_allv).attr("id", "mod_allv");
// $(mod_allv).css("position", "absolute");
// $(mod_allv).css("left", "0px");
// $(mod_allv).css("top", "0px");
// $(mod_allv).css("width",$(document.body).width());
// $(mod_allv).css("height", $(document.body).height());
// $(mod_allv).css("right", "0px");
// $(mod_allv).css("bottom", "0px");
// $(mod_allv).css("background", "#eee");
// $(mod_allv).css("filter", "alpha(opacity=30)");
// $(mod_allv).css("opacity", "0.3");
// $(mod_allv).css("zIndex", "1");
// $(document.body).append(mod_allv);
		try{
			mui.showLoading("正在加载..","div");
		}catch (e) {
		}
	} else {
		try {
			// $("#mod_allv").remove();
			mui.hideLoading();
		} catch (e) {
		}
	}
};

function addEvent(elm, evType, fn, useCapture) {
	if (elm.addEventListener) {
		elm.addEventListener(evType, fn, useCapture);
		return true;
	} else if (elm.attachEvent) {
		var r = elm.attachEvent('on' + evType, fn);
		return r;
	} else {
		elm['on' + evType] = fn;
	}
}
function removeEvent(obj, type, fn, cap) {
	var cap = cap || false;
	if (obj.removeEventListener) {
		obj.removeEventListener(type, fn, cap);
	} else {
		obj.detachEvent("on" + type, fn);
	}
}

/**
 * @class tlv8.Data
 * @description 公用对象用于构建提交数据
 */
tlv8.Data = function() {
	this.table = "";
	this.relation = null;
	this.cells = "";
	this.rowid = null;
	this.formid = "";
	this.savAction = "";
	this.dbkay = "";
	this.version = 0;
	this.Cascade = "";
	this.filter = "";
	this.orderby = "";
	this.readonly = false;
	this.childrenData = new Map();
	this.setVersion = function(vi) {
		this.version = vi;
	};
	/**
	 * @name setReadonly
	 * @description 设置表单只读状态
	 * @param {boolean}
	 *            sta
	 */
	this.setReadonly = function(sta) {
		try {
			this.readonly = sta;
			if (this.relation && this.relation != "") {
				var relations = this.relation.split(",");
				for (var i = 0; i < relations.length; i++) {
					var revalueEl = document.getElementById(relations[i]);
					if (revalueEl) {
						if (this.readonly == true) {
							revalueEl.readOnly = true;
							if (revalueEl.parentNode.Check
									|| revalueEl.parentNode.Radio) {
								revalueEl.parentNode.disabled = true;
							}
							if (revalueEl.tagName == "DIV"
									|| revalueEl.tagName == "SELECT") {
								revalueEl.disabled = true;
							}
						} else {
							revalueEl.readOnly = false;
							if (revalueEl.parentNode.Check
									|| revalueEl.parentNode.Radio) {
								revalueEl.parentNode.disabled = false;
							}
							if (revalueEl.tagName == "DIV"
									|| revalueEl.tagName == "SELECT") {
								revalueEl.disabled = false;
							}
						}
					}
					try {
						// 只读设置取消单击事件
						$(revalueEl).removeAttr("onclick");
					} catch (e) {
					}
				}
			} else if (this.formid) {
				$("#" + this.formid).find("input").attr("readonly", "readonly");
				$("#" + this.formid).find("textarea").attr("readonly",
						"readonly");
				$("#" + this.formid).find("select")
						.attr("disabled", "disabled");
				$("#" + this.formid).find("input[type='radio']").attr(
						"disabled", "disabled");
			}
		} catch (e) {
		}
	};
	/**
	 * @name setOrderby
	 * @description 设置数据排序
	 * @param {string}
	 *            ob
	 * @example data.setOrderby("FCODE asc,fNAME desc");
	 */
	this.setOrderby = function(ob) {
		if (typeof (ob) == "string")
			this.orderby = ob;
		else
			this.orderby = ob.toString();
	};
	/**
	 * @name setFilter
	 * @description 设置过滤条件
	 * @param {string}
	 *            fil
	 * @example data.setFilter("FCODE = '123'");
	 */
	this.setFilter = function(fil) {
		if (typeof (fil) == "string")
			this.filter = fil;
		else
			this.filter = fil.toString();
	};
	/**
	 * @name setCascade
	 * @description 设置级联删除{表名:外键,表名:外键,...}
	 * @param {string}
	 *            cas
	 * @example data.setCascade("OA_ALREADYBUY:fBillID");
	 * @example data.setCascade("OA_ALREADYBUY:fBillID,OA_ALREADYBUY1:fBillID1");
	 */
	this.setCascade = function(cas) {
		if (typeof (cas) == "string")
			this.Cascade = cas;
		else
			this.Cascade = cas.toString();
	};
	/**
	 * @name setRowId
	 * @description 设置主键，记录表单当前正在编辑的数据主键。
	 * @param {string}
	 *            rowid
	 */
	this.setRowId = function(id) {
		try {
			if (typeof (id) == "string")
				this.rowid = id;
			else
				this.rowid = id.toString();
		} catch (e) {
		}
		try{
			J$(this.formid).rowid = this.rowid;
			J$(this.formid).setAttribute("rowid", this.rowid);
			$("#"+this.formid).attr("rowid", this.rowid);
		}catch (e) {
		}
	};
	/**
	 * @name setDbkey
	 * @description 设置关联数据库
	 * @param {string}
	 *            dbkey
	 * @example data.setDbkey("oa");
	 */
	this.setDbkey = function(k) {
		if (typeof (k) == "string")
			this.dbkay = k;
		else {
			try {
				this.dbkay = k.toString();
			} catch (e) {
			}
		}
	};
	this.setSaveAction = function(a) {
		if (typeof (a) == "string")
			this.savAction = a;
		else
			this.savAction = a.toString();
	};
	/**
	 * @name setFormId
	 * @description 设置数据对应的FORM表单ID
	 * @param {string}
	 *            s
	 * @example data.setFormId("main_form");
	 */
	this.setFormId = function(s) {
		if (typeof (s) == "string")
			this.formid = s;
		else
			this.formid = s.toString();
		try {
			document.getElementById(this.formid).data = this;
		} catch (e) {
		}
	};
	var onDataValueChanged;
	var isEdited = false;
	/**
	 * @name setonDataValueChanged
	 * @description 设置表单值改变事件回调函数
	 * @param {function}
	 *            fn
	 * @example data.setonDataValueChanged(function(event){});
	 */
	this.setonDataValueChanged = function(fn) {
		onDataValueChanged = fn;
		this.registerChangeEvent();
		document.getElementById(this.formid).data = this;
	};
	/**
	 * @name registerChangeEvent
	 * @description 注册表单值改变事件,一般不需要主动调用
	 * @param {string}
	 *            formid
	 * @example data.registerChangeEvent();
	 */
	this.registerChangeEvent = function(formid) {
		var dataform = formid || this.formid;
		var mainform = document.getElementById(dataform);
		var $JromTag = function(tagname) {
			return mainform.getElementsByTagName(tagname);
		};
		var inputs = $JromTag("INPUT");
		for (var i = 0; i < inputs.length; i++) {
			if (inputs[i].type == "text" || inputs[i].type == "textarea"
					|| inputs[i].type == "password" || inputs[i].type == "number" 
					|| inputs[i].type == "date" || inputs[i].type == "datetime" 
					|| inputs[i].type == "hidden") {
				var $rid = inputs[i].id;
				if ($rid && $rid != ""
						&& $rid.toUpperCase().substr(4, 12) != "_FIXFFCURSOR"
						&& $rid.indexOf("_quick_text") < 0
						&& $rid.indexOf("_page") < 0
						&& $rid.indexOf("_editgridipt") < 0) {
					addEvent(inputs[i], "change", function(evt) {
						isEdited = true;
						var event = evt || window.event;
						var objEdit = event.srcElement ? event.srcElement
								: event.target;
						var _dchg = onDataValueChanged;
						if (_dchg && typeof (_dchg) == "function") {
							_dchg(evt, objEdit.id, objEdit
									.getAttribute("value"), objEdit.type);
						} else if (_dchg && _dchg != "") {
							_dchg = eval(_dchg);
							_dchg(evt, objEdit.id, objEdit
									.getAttribute("value"), objEdit.type);
						}
					}, true);
				}
			}
		}
		var textareas = $JromTag("TEXTAREA");
		for (var i = 0; i < textareas.length; i++) {
			var $rid = textareas[i].id;
			var $rval = textareas[i].getAttribute("value")
					|| textareas[i].innerHTML;
			if ($rid && $rid != ""
					&& $rid.toUpperCase().substr(4, 12) != "_FIXFFCURSOR") {
				addEvent(textareas[i], "change", function(evt) {
					isEdited = true;
					var event = evt || window.event;
					var objEdit = event.srcElement ? event.srcElement
							: event.target;
					var _dchg = onDataValueChanged;
					if (_dchg && typeof (_dchg) == "function") {
						_dchg(evt, objEdit.id, objEdit.getAttribute("value"),
								objEdit.type);
					} else if (_dchg && _dchg != "") {
						_dchg = eval(_dchg);
						_dchg(evt, objEdit.id, objEdit.getAttribute("value"),
								objEdit.type);
					}
				}, true);
			}
		}
		var selects = $JromTag("SELECT");
		for (var i = 0; i < selects.length; i++) {
			var $rid = selects[i].id;
			var $rval = selects[i].getAttribute("value");
			if ($rid && $rid != ""
					&& $rid.toUpperCase().substr(4, 12) != "_FIXFFCURSOR") {
				addEvent(selects[i], "change", function(evt) {
					isEdited = true;
					var event = evt || window.event;
					var objEdit = event.srcElement ? event.srcElement
							: event.target;
					var _dchg = onDataValueChanged;
					if (_dchg && typeof (_dchg) == "function") {
						_dchg(evt, objEdit.id, objEdit.getAttribute("value"),
								objEdit.type);
					} else if (_dchg && _dchg != "") {
						_dchg = eval(_dchg);
						_dchg(evt, objEdit.id, objEdit.getAttribute("value"),
								objEdit.type);
					}
				}, true);
			}
		}
		var labels = $JromTag("LABEL");
		for (var i = 0; i < labels.length; i++) {
			var $rid = labels[i].id;
			var $rval = labels[i].innerHTML;
			if ($rid && $rid != ""
					&& $rid.toUpperCase().substr(4, 12) != "_FIXFFCURSOR") {
				addEvent(labels[i], "change", function(evt) {
					isEdited = true;
					var event = evt || window.event;
					var objEdit = event.srcElement ? event.srcElement
							: event.target;
					var _dchg = onDataValueChanged;
					if (_dchg && typeof (_dchg) == "function") {
						_dchg(evt, objEdit.id, objEdit.getAttribute("value"),
								objEdit.type);
					} else if (_dchg && _dchg != "") {
						_dchg = eval(_dchg);
						_dchg(evt, objEdit.id, objEdit.getAttribute("value"),
								objEdit.type);
					}
				}, true);
			}
		}
	};
	/**
	 * @name setTable
	 * @description 设置表单关联数据库表名
	 * @param {string}
	 *            tablename
	 * @example data.setTable("SA_OPPERSON");
	 */
	this.setTable = function(t) {
		if (typeof (t) == "string")
			this.table = t;
		else {
			try {
				this.table = t.toString();
			} catch (e) {
			}
		}
	};
	/**
	 * @name setCells
	 * @description 设置列的值
	 * @param {Map}
	 *            cells
	 * @example var cell=new Map(); cell.put("a","a"); cell.put("b","b");
	 *          data.setCells(cell);
	 */
	this.setCells = function(cell) {
		if (cell && typeof (cell) == "object") {
			var keys = cell.keySet();
			for (i in keys) {
				var key = keys[i];
				var value = cell.get(key);
				try {
					value = value.replaceAll("<", "#lt;");
					value = value.replaceAll(">", "#gt;");
					value = value.replaceAll("&nbsp;", "#160;");
					value = value.replaceAll("'", "#apos;");
					value = value.replaceAll("&", "#amp;");
				} catch (e) {
				}
				this.cells += "<" + key + "><![CDATA[" + value + "]]></" + key
						+ ">";
			}
		} else if (typeof (cell) == "function") {
			alert("传入的类型不能为：function");
		} else if (!cell) {
			this.cells = "";
		} else {
			this.cells += cell;
		}
	};
	/**
	 * @name saveData
	 * @description 保存数据
	 * @returns {string}
	 * @example var rowid = data.saveData();
	 */
	this.saveData = function() {
		if (!this.formid || this.formid == "") {
			alert("未指定保存内容！");
			return false;
		}
		var bfSave = document.getElementById(this.formid).getAttribute(
				"beforeSave");
		if (bfSave && bfSave != "") {
			if (typeof bfSave == "function") {
				var bfck = bfSave(this);
				if(bfck==false)
					return false;
			} else {
				var bfS = window.eval(bfSave);
				if (typeof bfS == "function") {
					var bfck = bfS(this);
					if(bfck==false)
						return false;
				}
			}
		}
		var mainform = document.getElementById(this.formid);
		mainform.data = this;
		var cell = new Map();
		var $JromTag = function(tagname) {
			return mainform.getElementsByTagName(tagname);
		};
		var inputs = $JromTag("INPUT");
		for (var i = 0; i < inputs.length; i++) {
			if (inputs[i].type == "text" || inputs[i].type == "textarea"
					|| inputs[i].type == "password" || inputs[i].type == "number" 
					|| inputs[i].type == "date" || inputs[i].type == "datetime"  
					|| inputs[i].type == "hidden") {
				var $rid = inputs[i].id;
				var $rval = $(inputs[i]).val() || inputs[i].value
						|| inputs[i].getAttribute("value");
				if ($(inputs[i]).attr("rel") == 'remind'
						&& $(inputs[i]).attr("rel-value") == $rval) {
					$rval = "";
				}
				if ($rid && $rid != ""
						&& $rid.toUpperCase().substr(4, 12) != "_FIXFFCURSOR"
						&& $rid.indexOf("_quick_text") < 0
						&& $rid.indexOf("_page") < 0
						&& $rid.indexOf("_editgridipt") < 0) {
					if ($(inputs[i]).attr("format")
							&& $(inputs[i]).attr("format") != "") {
						try {
							$rval = $rval.replaceAll(",", "");
						} catch (e) {
							$rval = "";
						}
					}
					cell.put($rid, $rval);
				}
			}
		}
		var textareas = $JromTag("TEXTAREA");
		for (var i = 0; i < textareas.length; i++) {
			var $rid = textareas[i].id;
			var $rval = $(textareas[i]).val()
					|| textareas[i].getAttribute("value")
					|| textareas[i].innerHTML;
			if ($(textareas[i]).attr("rel") == 'remind'
					&& $(textareas[i]).attr("rel-value") == $rval) {
				$rval = "";
			}
			if ($rid && $rid != ""
					&& $rid.toUpperCase().substr(4, 12) != "_FIXFFCURSOR") {
				if ($(textareas[i]).attr("format")
						&& $(textareas[i]).attr("format") != "") {
					try {
						$rval = $rval.replaceAll(",", "");
					} catch (e) {
						$rval = "";
					}
				}
				cell.put($rid, $rval);
			}
		}
		var selects = $JromTag("SELECT");
		for (var i = 0; i < selects.length; i++) {
			var $rid = selects[i].id;
			var $rval = $(selects[i]).val() || selects[i].value
					|| selects[i].getAttribute("value");
			if ($rid && $rid != ""
					&& $rid.toUpperCase().substr(4, 12) != "_FIXFFCURSOR") {
				if ($(selects[i]).attr("format")
						&& $(selects[i]).attr("format") != "") {
					try {
						$rval = $rval.replaceAll(",", "");
					} catch (e) {
						$rval = "";
					}
				}
				cell.put($rid, $rval);
			}
		}
		var labels = $JromTag("LABEL");
		for (var i = 0; i < labels.length; i++) {
			var $rid = labels[i].id;
			var $rval = labels[i].innerHTML;
			if ($rid && $rid != ""
					&& $rid.toUpperCase().substr(4, 12) != "_FIXFFCURSOR") {
				if ($(labels[i]).attr("format")
						&& $(labels[i]).attr("format") != "") {
					try {
						$rval = $rval.replaceAll(",", "");
					} catch (e) {
						$rval = "";
					}
				}
				cell.put($rid, $rval);
			}
		}
		var rowid = $(mainform).attr("rowid") || mainform.getAttribute("rowid")
				|| mainform.rowid;
		if (rowid) {
			cell.put("rowid", rowid);
		}
		cell.put("VERSION", (this.version + 1) + "");
		this.setCells();
		this.setCells("<root>");
		this.setCells(cell);
		this.setCells("</root>");
		if (!this.savAction || this.savAction == "") {
			this.setSaveAction("saveAction");
		} else {
			this.setSaveAction(this.savAction);
		}
		var self = this;
		var r = tlv8.saveAction(this.savAction, "post", null, this, true,
				false);
		writeLog(window.event, "保存数据", "操作的表:" + this.dbkay + "." + this.table);
		if (r) {
			isEdited = false;
			var msessage = "操作成功!";
			if (r.data.flag != "true") {
				msessage = r.data.message;
				// 截取java异常
				if (msessage.indexOf("Exception:") > 0) {
					msessage = msessage.substring(msessage
							.indexOf("Exception:") + 10);
				}
				alert(msessage);
				return false;
			} else {
				this.version++;// 保存成功记录新的版本号
			}
			if (parent.justep && parent.tlv8) {
				parent.sAlert(msessage, 500);
			} else {
				sAlert(msessage, 500);
			}
			var rRowID = r.data.rowid;
			self.setRowId(rRowID);
			try {
				mainform.setAttribute("rowid", rRowID);
			} catch (e) {
				mainform.rowid = rRowID;
			}
			if (!self.childrenData.isEmpty()) {
				var keyset = self.childrenData.keySet();
				for (i in keyset) {
					key = keyset[i];
					var childData = self.childrenData.get(key);
					var isCsave = childData.saveData(event, self.refreshData);
					if (!isCsave || isCsave == false) {
						break;
					}
				}
			}
			var afSave = mainform.getAttribute("afterSave");
			if (afSave && afSave != "") {
				if (typeof afSave == "function") {
					afSave(this, r.data);
				} else {
					var afS = window.eval(afSave);
					if (typeof afS == "function") {
						afS(this, r.data);
					}
				}
			}
			return rRowID;
		}
	};
	this.deleteAction = "";
	this.setDeleteAction = function(del) {
		if (typeof (del) == "string")
			this.deleteAction = del;
		else
			this.deleteAction = del.toString();
	};
	/**
	 * @name deleteData
	 * @description 删除数据
	 * @param {boolean}
	 *            isconfirm -是否提示确认[默认是]
	 * @example data.deleteData();
	 */
	this.deleteData = function(isconfirm) {
		var bfDelete = document.getElementById(this.formid).getAttribute(
				"beforeDelete");
		if (bfDelete && bfDelete != "") {
			if (typeof bfDelete == "function") {
				bfDelete(this);
			} else {
				var bfd = window.eval(bfDelete);
				if (typeof bfd == "function") {
					bfd(this);
				}
			}
		}
		if (!this.deleteAction || this.deleteAction == "") {
			this.setDeleteAction("deleteAction");
		} else {
			this.setDeleteAction(this.deleteAction);
		}
		document.getElementById(this.formid).data = this;
		var self = this;
		if (isconfirm == false) {
			tlv8.Deleteaction(this.deleteAction, "post", function() {
				self.afdelete(self);
			}, this.rowid, this);
			isEdited = false;
			return true;
		} else if (confirm("确定删除数据吗?")) {
			tlv8.Deleteaction(this.deleteAction, "post", function() {
				self.afdelete(self);
			}, this.rowid, this);
			isEdited = false;
			return true;
		}
		return false;
	};
	this.afdelete = function(self) {
		writeLog(window.event, "删除数据", "操作的表:" + self.dbkay + "." + self.table);
		try {
			document.getElementById(self.formid).reset();
		} catch (e) {
		}
		var afDelete = document.getElementById(self.formid).getAttribute(
				"afterDelete");
		if (afDelete && afDelete != "") {
			if (typeof afDelete == "function") {
				afDelete(document.getElementById(self.formid).data);
			} else {
				var afd = window.eval(afDelete);
				if (typeof afd == "function") {
					afd(document.getElementById(self.formid).data);
				}
			}
		}
	};
	/**
	 * @name refreshData
	 * @param {boolean}
	 *            isconfirm -是否提示确认
	 * @param {boolean}
	 *            isrefreshSub -是否刷新关联的子表数据
	 * @description 刷新数据
	 * @description 只是重加载表单的数据，不刷新页面
	 * @example data.refreshData();
	 */
	this.refreshData = function(isconfirm, isrefreshSub) {
		if (isEdited == true) {
			if (isconfirm != false)
				if (!confirm("当前数据已更改，刷新数据更改后的数据将丢失，是否确定刷新?")) {
					return false;
				}
		}
		var bfrefresh = document.getElementById(this.formid).getAttribute(
				"beforeRefresh");
		if (bfrefresh && bfrefresh != "") {
			if (typeof bfrefresh == "function") {
				bfrefresh(this);
			} else {
				var bfr = window.eval(bfrefresh);
				if (typeof bfr == "function") {
					bfr(this);
				}
			}
		}
		this.relation = "";
		var mainform = document.getElementById(this.formid);
		mainform.reset();
		var $JromTag = function(tagname) {
			return mainform.getElementsByTagName(tagname);
		};
		mainform.isrefreshSub = isrefreshSub;
		mainform.data = this;
		var rowid = $(mainform).attr("rowid") || mainform.getAttribute("rowid")
				|| mainform.rowid;
		if (rowid && rowid != "") {
			this.setRowId(trim(rowid));
		}
		if (!this.filter || this.filter == "") {
			if (this.dbkay && this.dbkay != "system") {
				this.setFilter("fID='" + this.rowid + "'");
			} else {
				this.setFilter("sID='" + this.rowid + "'");
			}
		}
		if (this.dbkay && this.dbkay != "system") {
			this.relation += ",FID";
		} else {
			this.relation += ",SID";
		}
		var inputs = $JromTag("INPUT");
		for (var i = 0; i < inputs.length; i++) {
			if (inputs[i].type == "text" || inputs[i].type == "textarea"
					|| inputs[i].type == "password" || inputs[i].type == "number" 
					|| inputs[i].type == "date" || inputs[i].type == "datetime" 
					|| inputs[i].type == "hidden") {
				var $rid = inputs[i].id;
				if ($rid && $rid != ""
						&& $rid.toUpperCase().substr(4, 12) != "_FIXFFCURSOR"
						&& $rid.indexOf("_editgridipt") < 0
						&& $rid.indexOf("_quick_text") < 0
						&& $rid.indexOf("_page") < 0) {
					this.relation += "," + $rid;
				}
			}
		}
		var textareas = $JromTag("TEXTAREA");
		for (var i = 0; i < textareas.length; i++) {
			var $rid = textareas[i].id;
			if ($rid && $rid != ""
					&& $rid.toUpperCase().substr(4, 12) != "_FIXFFCURSOR") {
				this.relation += "," + $rid;
			}
		}
		var selects = $JromTag("SELECT");
		for (var i = 0; i < selects.length; i++) {
			var $rid = selects[i].id;
			if ($rid && $rid != ""
					&& $rid.toUpperCase().substr(4, 12) != "_FIXFFCURSOR") {
				this.relation += "," + $rid;
			}
		}
		var labels = $JromTag("LABEL");
		for (var i = 0; i < labels.length; i++) {
			var $rid = labels[i].id;
			if ($rid && $rid != ""
					&& $rid.toUpperCase().substr(4, 12) != "_FIXFFCURSOR") {
				this.relation += "," + $rid;
			}
		}
		this.relation += ",VERSION";
		this.relation = this.relation.replace(",", "");
		var self = this;
		tlv8.Queryaction("queryAction", "post", function(rd) {
			self.setData(rd, self.formid);
		}, this, this.filter, true);
		return true;
	};
	this.setData = function(data, nowformid) {
		var taptt = tlv8.RequestURLParam.getParam("activity-pattern");
		var isTasksub = (taptt == "detail");
		if (isTasksub) {
			this.setReadonly(true);
		}
		var message = "操作成功!";
		if (data.flag == "false") {
			msessage = data.message;
			if (msessage.indexOf("Exception:") > 0) {
				msessage = msessage
						.substring(msessage.indexOf("Exception:") + 10);
			}
			alert(msessage);
		} else {
			try {
				var xmlElem = tlv8.strToXML(data.data);
				var relations = data.relation.split(",");
				var type = getOs();
				for (var i = 0; i < relations.length; i++) {
					var revalueEl = document.getElementById(nowformid)[relations[i]]
							|| document.getElementById(relations[i]);
					if (data.data && data.data != "") {
						var sValue = (type == "MSIE") ? xmlElem
								.getElementsByTagName(relations[i])[0].text
								: xmlElem.getElementsByTagName(relations[i])
										.item(0).textContent;
						if (relations[i] == "SID" || relations[i] == "FID") {
							document.getElementById(nowformid).rowid = sValue;
							document.getElementById(nowformid).setAttribute("rowid", sValue);
							$("#"+nowformid).attr("rowid", sValue);
							document.getElementById(nowformid).data.setRowId(sValue);
						}
						if (relations[i] == "VERSION") {
							document.getElementById(nowformid).data
									.setVersion(parseInt(sValue) || 0);
						}
					} else {
						document.getElementById(nowformid).rowid = "";
						document.getElementById(nowformid).data.setRowId("");
					}
					if (revalueEl && data.data && data.data != "") {
						var $dval = "";
						try {
							$dval = (type == "MSIE") ? xmlElem
									.getElementsByTagName(relations[i])[0].text
									: xmlElem
											.getElementsByTagName(relations[i])
											.item(0).textContent
											|| "";
							$dval = $dval.replaceAll("&amp;", "&");
						} catch (e) {
						}
						var elformat = "";
						try {
							elformat = revalueEl.getAttribute("format");
						} catch (e) {
						}
						if (elformat && elformat != "") {
							var Sdate = tlv8.System.Date.strToDate($dval);
							if (Sdate && elformat == "yyyy-MM-dd") {
								$dval = Sdate.format("yyyy-MM-dd");
							} else if (Sdate
									&& elformat == "yyyy-MM-dd hh:mm:ss") {
								$dval = Sdate.format("yyyy-MM-dd hh:mm:ss");
							} else if (Sdate
									&& elformat.indexOf("yyyy-MM-dd") > -1) {
								$dval = Sdate.format(elformat);
							} else if ($dval) {
								revalueEl.olvalue = $dval;
								$dval = tlv8.numberFormat($dval, elformat);
								if (!revalueEl.getAttribute("readonly")) {
									revalueEl.onfocus = function() {
										this
												.setAttribute("value",
														this.olvalue);
									};
									revalueEl.onblur = function() {
										this.olvalue = this
												.getAttribute("value");
										this.setAttribute("value", tlv8
												.numberFormat(this
														.getAttribute("value"),
														this.format));
									};
								}
							}
						} else {
							$dval = $dval.replaceAll("#160;", "&nbsp;");
							$dval = $dval.replaceAll("#lt;", "<");
							$dval = $dval.replaceAll("#gt;", ">");
							$dval = $dval.replaceAll("#apos;", "'");
							$dval = $dval.replaceAll("#amp;", "&");
						}
						if ("LABEL" == revalueEl.tagName) {
							try {
								revalueEl.innerHTML = $dval;
								revalueEl.title = $dval;
								revalueEl.setAttribute("value", $dval);
							} catch (e) {
							}
						} else if ("TEXTAREA" == revalueEl.tagName) {
							try {
								revalueEl.value = $dval;
								revalueEl.title = $dval;
								revalueEl.setAttribute("value", $dval);
								$(revalueEl).val($dval);
							} catch (e) {
							}
						} else {
							try {
								if (revalueEl.tagName == "DIV") {
									revalueEl = revalueEl
											.getElementsByTagName("input")[0] ? revalueEl
											.getElementsByTagName("input")[0]
											: revalueEl
													.getElementsByTagName("select")[0];
									revalueEl.setAttribute("value", $dval);
									revalueEl.title = $dval;
									revalueEl.value = $dval;
								} else {
									revalueEl.setAttribute("value", $dval);
									revalueEl.title = $dval;
									revalueEl.value = $dval;
								}
								$(revalueEl).val($dval);
							} catch (e) {
							}
							try {
								// 单选框赋值
								document.getElementById(revalueEl.id
										+ "_compent").Radio.initData(revalueEl);
							} catch (e) {
							}
							try {
								// 多选框赋值
								document.getElementById(revalueEl.id
										+ "_compent").Check.initData(revalueEl);
							} catch (e) {
							}
							try{
								// easyUI下拉选择
								$("#"+relations[i]).combo("setValue",$dval);
							}catch (e) {
							}
							try{
								// easyUI下拉选择
								$("#"+relations[i]).combobox("setValue",$dval);
							}catch (e) {
							}
							try{
								// easyUI下拉选择
								$("#"+relations[i]).combotree("setValue",$dval);
							}catch (e) {
							}
							try{
								// easyUI下拉选择
								$("#"+relations[i]).combogrid("setValue",$dval);
							}catch (e) {
							}
						}
					}
				}
			} catch (e) {
				alert(e.name + ":" + e.message);
			}
		}
		isEdited = false;
		var childrenData = document.getElementById(nowformid).data.childrenData;
		var isrefreshSub = document.getElementById(nowformid).isrefreshSub;
		if (!childrenData.isEmpty() && isrefreshSub != false) {
			var keyset = childrenData.keySet();
			for (i in keyset) {
				key = keyset[i];
				var childData = childrenData.get(key);
				var isCsave = childData.refreshData(null, false);
			}
		}
		var afrefresh = document.getElementById(nowformid).getAttribute(
				"afterRefresh");
		if (afrefresh && afrefresh != "") {
			if (typeof afrefresh == "function") {
				afrefresh(document.getElementById(nowformid).data);
			} else {
				var afr = window.eval(afrefresh);
				if (typeof afr == "function") {
					afr(document.getElementById(nowformid).data);
				}
			}
		}
		if (parent.justep && parent.tlv8) {
			parent.sAlert(message, 500);
		} else {
			sAlert(message, 500);
		}
	};
	/**
	 * @name getValueByName
	 * @param {string}
	 *            cellname -字段名（表单中INPUT、TEXTAREA等字段id）
	 * @description 获取指定字段的值
	 * @returns {string}
	 * @example var nmv = data.getValueByName("FNAME");
	 */
	this.getValueByName = function(name) {
		try {
			var revalueEl = document.getElementById(this.formid)[name]
					|| document.getElementById(name);
			var elformat = revalueEl.getAttribute("format");
			if (revalueEl) {
				if (revalueEl.tagName == "INPUT"
						|| revalueEl.tagName == "SELECT") {
					var $dval = $(revalueEl).val()
							|| revalueEl.getAttribute("value");
					try {
						if (elformat && elformat == "0,000.00") {
							$dval = $dval.toString().replaceAll(",", "");
						}
					} catch (e) {
					}
					return $dval;
				} else if (revalueEl.tagName == "LABEL") {
					var $dval = revalueEl.innerHTML;
					try {
						if (elformat && elformat == "0,000.00") {
							$dval = $dval.toString().replaceAll(",", "");
						}
					} catch (e) {
					}
					return $dval;
				} else {
					var $dval = $(revalueEl).val()
							|| revalueEl.getAttribute("value");
					return $dval;
				}
			} else {
				alert("指定的列名无效：" + name);
			}
		} catch (e) {
			alert("指定的列名无效：" + name);
		}
		return "";
	};
	/**
	 * @name setValueByName
	 * @param {string}
	 *            cellname -字段名（表单中INPUT、TEXTAREA等字段id）
	 * @param {string}
	 *            value -需要给的值
	 * @description 给指定字段赋值
	 * @example data.setValueByName("FNAME","test");
	 */
	this.setValueByName = function(name, value) {
		try {
			var revalueEl = document.getElementById(this.formid)[name]
					|| document.getElementById(name);
			if (revalueEl) {
				revalueEl.title = value;
				revalueEl.value = value;
				if (revalueEl.tagName == "LABEL") {
					revalueEl.innerHTML = value;
					revalueEl.setAttribute("value", value);
				} else {
					$(revalueEl).val(value);
				}
				try {
					// 单选框赋值
					document.getElementById(name + "_compent").Radio.initData(revalueEl);
				} catch (e) {
				}
				try {
					// 多选框赋值
					document.getElementById(name + "_compent").Check.initData(revalueEl);
				} catch (e) {
				}
				try{
					// easyUI下拉选择
					$("#"+name).combo("setValue", value);
				}catch (e) {
				}
				try{
					// easyUI下拉选择
					$("#"+name).combobox("setValue", value);
				}catch (e) {
				}
				try{
					// easyUI下拉选择
					$("#"+name).combotree("setValue", value);
				}catch (e) {
				}
				try{
					// easyUI下拉选择
					$("#"+name).combogrid("setValue", value);
				}catch (e) {
				}
				isEdited = true;
			} else {
				alert("指定的列名无效：" + name);
			}
		} catch (e) {
			alert("指定的列名无效：" + name);
		}
	};
	return this;
};

tlv8.RequestURLParam = {
	getParam : function(name) {
		var lurl = window.location.href;
		if (lurl.indexOf(name) < 0)
			return;
		if (lurl.indexOf("?") < 0) {
			return "";
		} else {
			var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)", "i");
			var r = window.location.search.substr(1).match(reg);
			if (r != null)
				return J_u_decode(r[2]);
			var par;
			if (lurl.indexOf("?" + name) > 0) {
				par = lurl.substring(lurl.indexOf("?" + name) + 1, lurl.length);
			}
			if (lurl.indexOf("&" + name) > 0) {
				par = lurl.substring(lurl.indexOf("&" + name) + 1, lurl.length);
			}
			if (!par)
				return;
			var start = name.length + 1;
			var len = (par.indexOf("&") > 0) ? par.indexOf("&") : lurl.length;
			var pav = par.substring(start, len);
			return J_u_decode(pav);
		}
	},
	getParamByURL : function(lurl, name) {
		if (lurl.indexOf(name) < 0)
			return;
		if (lurl.indexOf("?") < 0) {
			return "";
		} else {
			var par;
			if (lurl.indexOf("?" + name) > 0) {
				par = lurl.substring(lurl.indexOf("?" + name) + 1, lurl.length);
			}
			if (lurl.indexOf("&" + name) > 0) {
				par = lurl.substring(lurl.indexOf("&" + name) + 1, lurl.length);
			}
			if (!par)
				return;
			var start = name.length + 1;
			var len = (par.indexOf("&") > 0) ? par.indexOf("&") : lurl.length;
			return J_u_decode(par.substring(start, len));
		}
	}
};

if (!tlv8.System)
	tlv8.System = {};
tlv8.System.Date = {
	sysDate : function() {
		var param = new tlv8.RequestParam();
		var r = tlv8.XMLHttpRequest("getSystemDate", param, "post", false,
				null, true);
		var rdate = tlv8.System.Date.strToDate(r.sysdate);
		return rdate.format("yyyy-MM-dd");
	},
	sysDateTime : function() {
		var param = new tlv8.RequestParam();
		var r = tlv8.XMLHttpRequest("getSystemDateTime", param, "post",
				false, null, true);
		return r.sysdate;
	},
	strToDate : function(str) {
		if (!str || str == "" || str.indexOf("-") < 0) {
			return;
		}
		str = trim(str);
		var val = str.split(" ");
		var newDate;
		if (val.length > 1) {
			var sdate = val[0].split("-");
			var sTime = val[1].split(":");
			newDate = new Date(sdate[0], sdate[1] - 1, sdate[2], sTime[0],
					sTime[1], sTime[2]);
		} else {
			var sdate = val[0].split("-");
			newDate = new Date(sdate[0], sdate[1] - 1, sdate[2]);
		}
		return newDate;
	}
};

/*
 * 客户端推送消息 @personids 人员IDs @title 消息标题 @msg 消息内容
 */
tlv8.sendAppMessageAction = function(personids, title, msg) {
	var param = new tlv8.RequestParam();
	param.set("personid", personids);
	param.set("msgtitle", title);
	param.set("msgcontent", msg);
	tlv8.XMLHttpRequest("sendAppMessageAction", param, "post", true);
};

/*
 * 返回首页
 */
function goHome() {
	window.open(cpath+"/mobileUI/portal/index.html", "_self");
}

function searchData() {
	if(typeof mui != "undefined"){
		var btnArray = [ '取消', '确定' ];
		mui.prompt('请输入查询关键字：', '请输入...', '模糊查询', btnArray, function(e) {
			if (e.index == 1) {
				doSearch(e.value);
			}
		});
	}else{
		  var html = "<input type='text' id='search_text' style='width:180px;height:30px;'/>" 
			  + "&nbsp;<a id='seearchItem' class='ui-btn-right ui-btn ui-btn-up-b ui-shadow ui-btn-corner-all'>" 
			  + "<span class='ui-btn-inner'>" 
			  + "<span class='ui-btn-text'>查询<span></span></a><br/><br/>";
		  justep.dialog.openDialog("模糊查询", { width : "280px", height : "260px",
		  text : html }); 
		  $("#search_text").keydown(function(event) { 
			  if(event.keyCode == 13) { 
				  doSearch($("#search_text").val()); 
				  return false; 
				  } 
		  });
		  $("#seearchItem").click(function() { doSearch($("#search_text").val()); });
	}
}

// 获取文档服务地址
function getDocHost() {
	// var dHost = window.location.host;
	// var port = "";
	// if (dHost.indexOf(":") > 0) {
	// var hostall = dHost.split(":");
	// dHost = hostall[0];
	// port = ":" + hostall[1];
	// }
	// var Url = window.location.protocol + "//" + dHost + port;
	var sql = "select SURL from SA_DOCNAMESPACE";
	var re = tlv8.sqlQueryAction("system", sql, null, false);
	Url = re.getValueByName("SURL");
	return trim(Url);
}

// 查看原文件
function browseSourceDoc(fileID) {
	var url = getDocHost() + "/repository/file/view/" + fileID
			+ "/last/content";
	try {
		justepYnApp.browseDoc(url);
	} catch (e) {
		window.open(url);
	}
}
tlv8.fileComponent = function(div, data, cellname, docPath, canupload, candelete, canedit, viewhistory, limit, download) {
	var dbkey = data.dbkay;
	var tablename = data.table;
	var rowid = data.rowid;
	var sID = (dbkey == "system" || !dbkey) ? "SID" : "fID";
	var $refreshFileComp = function(){	
		var random = Math.random();
		var sql = "select " + cellname + " FILECOMPE from " + tablename + " where "
				+ sID + " = '" + rowid + "' and " + random + "=" + random;
		var r = tlv8.sqlQueryActionforJson(dbkey, sql);
		var dilelist = [];
		var transeJson = function(str) {
			str = str.toString().replaceAll(":", ":\"");
			str = str.toString().replaceAll(",", "\",");
			str = str.toString().replaceAll("}", "\"}");
			str = str.toString().replaceAll("}{", "},{");
			str = str.toString().replaceAll(";", "\",");
			var filelist = eval("([" + str + "])");
			return filelist;
		};
		try {
			if (r.data != "") {
				var datas = r.data[0];
				datas = datas.FILECOMPE;
				if (datas && datas != "") {
					try {
						dilelist = eval("(" + datas + ")");
					} catch (e) {
						dilelist = transeJson(datas);
					}
				}
			}
		} catch (e) {
			alert(e.message);
		}
		var html = "<table style='width:100%;table-layout:fixed;'>";
		if(canupload){
			html += "<tr><td style='padding-bottom:5px;'><a style='position:relative;'>上传文件<input type='file' id='file_upload_input' style='position:absolute;left:0px;top:0px;width:100%;height:100%;opacity:0;font-size:0;cursor:pointer;'></a><br/></td></tr>";
		}
		for (var i = 0; i < dilelist.length; i++) {
			html += "<tr><td><div style='width:100%;height:100%;overflow:hidden;line-height:25px;'>";
			var docName = dilelist[i].filename;
			var fileID = dilelist[i].fileID;
			html += i + 1 + ".<label class='ui-lable-info'>" + docName + "</label>&nbsp;&nbsp;";
			if(candelete||canedit||viewhistory){
				html += "<br/>";
			}
			html += "<a href=\"#popupMenu\" onclick=\"browseSourceDoc('" + fileID + "')\"><font color='green'>查看文件</font></a>";
			if(candelete){
				html += "&nbsp;&nbsp;";
				html += "<a href='javascript:;' onclick=\"tlv8.deletefile('" + fileID + "','"+docName+"','"+dbkey+"','"+tablename+"','"+cellname+"','"+rowid+"',function(){document.getElementById('"+ div.id + "').refreshFileComp();})\"><font color='red'>删除文件</font></a>";
			}
			html += "</div>";
			html += "</td>";
			html += "</tr>";
		}
		html += "</table>";
		$(div).html(html);
		$(div).css("padding","11px");
		$("#file_upload_input").change(function(){
			// alert(this.files);
			if (this.files.length < 1) { // 简单判断是否选择文件
	            // alert('未选择上传文件！');
	            return;
	        }
			if(!rowid||rowid==""){// 自动保存数据
				rowid = data.saveData();
			}
			if(!rowid||rowid==""){// 数据保存校验失败时退出
				alert("数据未保存，请先保存数据!");
				return;
			}
	        var file = this.files[0];// 获得文件
	        var formData=new FormData();
	        formData.append("Filedata", file);
	        formData.append("uploadInfo", "uploadInfo");
	        try{
	        	mui.showLoading("正在上传文件..", "div");
	        }catch (e) {
			}
	        $.ajax({
	    	  url: cpath+"/docUploadAction"
	    	  ,type: "POST"
	    	  ,async:true
	    	  ,data: formData
	    	  ,dataType:'json'
	    	  ,processData: false  // 告诉jQuery不要去处理发送的数据
	    	  ,contentType: false   // 告诉jQuery不要去设置Content-Type请求头
	    	  ,success : function(rjson, textStatus) {
	    		  if(rjson.cacheName && rjson.cacheName!=""){
	    			  var wlog = [{
	    				  dbkey:dbkey,
	    				  docPath:docPath,
	    				  tablename:tablename,
	    				  cellname:cellname,
	    				  rowid:rowid,
	    				  docName:rjson.docName,
	    				  kind:rjson.kind,
	    				  size:rjson.size,
	    				  cacheName:rjson.cacheName
	    			  }];
	    			  $.ajax({
	    		    	  url: cpath+"/writeUploadDataAction"
	    		    	  ,type: "POST"
	    		    	  ,async:true
	    		    	  ,data:{writelog:JSON.stringify(wlog)}
	    			  	  ,success : function(res, textStatus) {
	    			  		  try{
	    			  			  mui.hideLoading();
	    			  		  }catch (e) {
	    			  		  }
	    			  		  div.refreshFileComp(); // 重加载内容
	    			  	  }
	    			  });
	    		  }
	    	  }
	    	});
		});
	};
	this.$refreshFileComp = $refreshFileComp;
	div.refreshFileComp = $refreshFileComp;
	$refreshFileComp();
};
/**
 * 删除附件
 */
tlv8.deletefile = function(fileID, filename, dbkey, tablename, cellname,rowid, callback) {
	if (!fileID || fileID == "" || !filename)
		return;
	if (confirm("确定删除文件'" + filename + "'吗？")) {
		var url = cpath+"/deleteFileAction?fileID=" + fileID;
		url += "&filename=" + J_u_encode(filename);
		url += "&dbkey=" + dbkey;
		url += "&tablename=" + tablename;
		url += "&cellname=" + cellname;
		url += "&rowid=" + rowid;
		$.ajax({
	    	  url: url
	    	  ,type: "POST"
	    	  ,async:true
	    	  ,dataType:'json'
		  	  ,success : function(rjson, textStatus) {
		  		  sAlert("删除成功!");
		  		  if (callback){
					callback(rjson);
		  		  }
		  	  }
		  });
	}
};

// 加载审核意见
tlv8.loadOption = function(viewID, sData1) {
	var param = new tlv8.RequestParam();
	param.set("fbillID", sData1);
	param.set("fopviewID", viewID);
	var re = tlv8.XMLHttpRequest("LoadAuditOpinionAction", param, "POST", false);
	var redata = re.data.data;
	try{
		redata = window.eval("("+redata+")");
	}catch (e) {
	}
	var viewHTml = "<ul style='width:100%;font-size:14px;display: block;overflow:hidden;'>";
	for (var i = 0; i < redata.length; i++) {
		var opinion = redata[i].FAGREETEXT;
		var writeDate = redata[i].FCREATETIME;
		var personid = redata[i].FCREATEPERID;
		var personname = redata[i].FCREATEPERNAME;
		var psign  = redata[i].FSIGN;
		if (writeDate && writeDate != "") {
			writeDate = tlv8.System.Date.strToDate(writeDate);
			writeDate = writeDate.format("yyyy-MM-dd HH:mm");
		}
		var hdws = viewID + "_handwrite";
		if(psign && psign != ""){
			writpsm = '<img src="data:image/png;base64,'+psign+'" style="height:30px;">&nbsp;&nbsp;';
		}else{
			var param1 = new tlv8.RequestParam();
			param1.set("personid", personid);
			var pcre = tlv8.XMLHttpRequest("LoadAuditOpinionAction", param1, "POST", false);
			var picID = "";
			if (pcre.data.length > 0) {
				picID = pcre.data[0].SID;
			}
			var url = cpath+"/comon/picCompant/Pic-read.jsp?dbkey=system"
					+ "&tablename=SA_HANDWR_SIGNATURE&cellname=SHSPIC&fID=" + picID
					+ "&Temp=" + new UUID().toString();
			var image = "<img src='" + url
					+ "' style='width:100px;;height:30px;'></img>";
			var writpsm = personname;
			if (picID && picID != "") {
				writpsm = image;
			}
		}
		viewHTml += "<li style='margin-bottom:10px;width:100%;float:left;'>";
		viewHTml += "<ul style='width:100%;display: block;overflow:hidden;'>";
		viewHTml += "<li style='float:left;'>"
				+ opinion + "</li>";
		viewHTml += "<li style='float:right;width:130px;'>"
				+ writeDate + "</li>";
		viewHTml += "<li style='float:right;width:100px;' id='"
			+ hdws + "'>"
			+ writpsm
			+ "</li>";
		viewHTml += "</ul>";
		viewHTml += "</li>";
	}
	viewHTml += "</ul>";
	var oph = redata.length * 30 + 30;
	$("#" + viewID).css("width", "100%");
	$("#" + viewID).css("min-height", oph+"px");
	$("#" + viewID).html(viewHTml);
	if ($("#" + viewID).parent().height() < $("#" + viewID).height()) {
		$("#" + viewID).parent().height($("#" + viewID).height() + 10);
	}
};

// 填写审批意见
tlv8.writeOpinion = function(view) {
	var sData1 = getParamValueFromUrl("sData1");
	var flowID = getParamValueFromUrl("flowID");
	var taskID = getParamValueFromUrl("taskID");
	var url = cpath+"/mobileUI/oa/common/flw/processAudit.html";
	url += "?flowID=" + flowID;
	url += "&taskID=" + taskID;
	url += "&sData1=" + sData1;
	url += "&opviewID=" + view;
	window.open(url, "_self");
};

if (!$.mobile)
	$.mobile = {};

$.mobile = $.extend($.mobile, {
	showPageLoadingMsg : function() {
		var muvi = $("<div></div>");
		muvi.attr("id", "page_mod_allv");
		muvi.css("position", "absolute");
		muvi.css("left", "0px");
		muvi.css("top", "0px");
		muvi.css("width", $(document.body).width());
		muvi.css("height", $(document.body).height());
		muvi.css("right", "0px");
		muvi.css("bottom", "0px");
		muvi.css("background", "#eee");
		muvi.css("filter", "alpha(opacity=30)");
		muvi.css("opacity", "0.3");
		muvi.css("zIndex", "1");
		$(document.body).append(muvi);
	},
	hidePageLoadingMsg : function() {
		$("#page_mod_allv").remove();
	}
});

/**
*以下为了兼容云捷代码
*/
justep.yn = tlv8;