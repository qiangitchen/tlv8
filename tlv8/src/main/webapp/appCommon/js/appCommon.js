/**
 * @1 名空间名用小驼峰写法
 * @2 类名用大驼峰写法
 * @3 方法和属性名用小驼峰
 * @4 私用成员用下划线开头
 * @5 我们平台的js代码在全局只有两个名空间justep和xforms，也就是只占两个变量，除此平台代码不允许定义全局变量
 * @6 这种模式和java比较好对应，名空间下不建议（或不允许）放方法，所有的方法都是类方法或类的实例方法
 * @7 应用产品的名空间为appCommon
 */

// 字符串格式化
// 示例: "aaaaa{0},dddafa{1}".format("XX", "YY");
// 结果: "aaaaaXXX,dddafaYYY"
String.prototype.format = function() {
	var args = arguments;
	return this.replace(/\{(\d+)\}/g, function(m, i) {
		return args[i];
	});
};
// 去除字符串左右空格
String.prototype.trim = function() {
	return this.replace(/(^\s*)|(\s*$)/g, "");
};
// 去除字符串中所有空格
String.prototype.atrim = function() {
	return this.replace(/\s/g, '');
};
// 去除字符串左边空格
String.prototype.ltrim = function() {
	return this.replace(/(^\s*)/g, "");
};
// 去除字符串右边空格
String.prototype.rtrim = function() {
	return this.replace(/(\s*$)/g, "");
};

/**
 * appCommon根名空间
 */
if (typeof (appCommon) == "undefined")
	var appCommon = {};

appCommon.ToolUtils = {};

/**
 * 数值转中文金额
 */
appCommon.ToolUtils.num2ChMoney = function(num) {
	var numberValue = new String(Math.round(num * 100)); // 数字金额
	var chineseValue = ""; // 转换后的汉字金额
	var String1 = "零壹贰叁肆伍陆柒捌玖"; // 汉字数字
	var String2 = "万仟佰拾亿仟佰拾万仟佰拾元角分"; // 对应单位
	var len = numberValue.length; // numberValue 的字符串长度
	var Ch1; // 数字的汉语读法
	var Ch2; // 数字位的汉字读法
	var nZero = 0; // 用来计算连续的零值的个数
	var String3; // 指定位置的数值
	if (len > 15) {
		alert("超出计算范围");
		return "";
	}
	if (numberValue == 0) {
		chineseValue = "零元整";
		return chineseValue;
	}
	String2 = String2.substr(String2.length - len, len); // 取出对应位数的STRING2的值
	for ( var i = 0; i < numberValue.length; i++) {
		String3 = parseInt(numberValue.substr(i, 1), 10); // 取出需转换的某一位的值
		// //alert(String3);
		if (i != (len - 3) && i != (len - 7) && i != (len - 11)
				&& i != (len - 15)) {
			if (String3 == 0) {
				Ch1 = "";
				Ch2 = "";
				nZero = nZero + 1;
			} else if (String3 != 0 && nZero != 0) {
				Ch1 = "零" + String1.substr(String3, 1);
				Ch2 = String2.substr(i, 1);
				nZero = 0;
			} else {
				Ch1 = String1.substr(String3, 1);
				Ch2 = String2.substr(i, 1);
				nZero = 0;
			}
		} else { // 该位是万亿，亿，万，元位等关键位
			if (String3 != 0 && nZero != 0) {
				Ch1 = "零" + String1.substr(String3, 1);
				Ch2 = String2.substr(i, 1);
				nZero = 0;
			} else if (String3 != 0 && nZero == 0) {
				Ch1 = String1.substr(String3, 1);
				Ch2 = String2.substr(i, 1);
				nZero = 0;
			} else if (String3 == 0 && nZero >= 3) {
				Ch1 = "";
				Ch2 = "";
				nZero = nZero + 1;
			} else {
				Ch1 = "";
				Ch2 = String2.substr(i, 1);
				nZero = nZero + 1;
			}
			if (i == (len - 11) || i == (len - 3)) { // 如果该位是亿位或元位，则必须写上
				Ch2 = String2.substr(i, 1);
			}
		}
		chineseValue = chineseValue + Ch1 + Ch2;
	}
	if (String3 == 0) { // 最后一位（分）为0时，加上“整”
		chineseValue = chineseValue + "整";
	}
	return chineseValue;
};

/**
 * 创建编号（不占号）
 */
appCommon.ToolUtils.createTempNextSeqString = function(key, format) {
	var param = new justep.Request.ActionParam();
	param.setString("key", key);
	param.setString("format", format);

	var r = justep.Request.sendBizRequest(justep.Context.getCurrentProcess(),
			justep.Context.getCurrentActivity(),
			"createTempNextSeqStringAction", param);
	if (!justep.Request.isBizSuccess(r)) {
		throw new Error(justep.Request.getServerError(r, "编号生成失败"));
	}
	return justep.XML.getNodeText(r.responseXML, "/root/data/*");
};

/**
 * 创建编号（占号）
 */
appCommon.ToolUtils.createNextSeqString = function(key, format) {
	var param = new justep.Request.ActionParam();
	param.setString("key", key);
	param.setString("format", format);

	var r = justep.Request.sendBizRequest(justep.Context.getCurrentProcess(),
			justep.Context.getCurrentActivity(), "createNextSeqStringAction",
			param);
	if (!justep.Request.isBizSuccess(r)) {
		throw new Error(justep.Request.getServerError(r, "编号生成失败"));
	}
	return justep.XML.getNodeText(r.responseXML, "/root/data/*");
};

appCommon.ToolUtils.rdf2json = function(rdfNode) {
	var jObject = new Object();
	jObject[rdfNode.baseName] = this._rdf2json(rdfNode);
	return jObject;
};

appCommon.ToolUtils._rdf2json = function(rdfNode) {
	var type = rdfNode.getAttribute("rdf:type");
	if (type) {
		return rdfNode.nodeTypedValue;
	} else {
		var jObject = new Object();
		var id = rdfNode.getAttribute("rdf:id");
		if (id) {
			jObject.id = id;
		}
		for ( var i = 0; i < rdfNode.childNodes.length; i++) {
			var childNode = rdfNode.childNodes[i];
			if (childNode.getAttribute("rdf:id")) {
				if (!jObject[childNode.baseName])
					jObject[childNode.baseName] = new Array();
				jObject[childNode.baseName].push(this._rdf2json(childNode));

			} else {
				jObject[childNode.baseName] = this._rdf2json(childNode);
			}
		}
		return jObject;
	}
};

/**
 * XML 转义
 */
appCommon.ToolUtils.xmlEncode = function(str) {
	str = str.replace(/&/g, "&amp;");
	str = str.replace(/</g, "&lt;");
	str = str.replace(/>/g, "&gt;");
	str = str.replace(/'/g, "&apos;");
	str = str.replace(/"/g, "&quot;");
	return str;
};

/**
 * 获取上级路径
 * 
 * @param path
 * @return
 */
appCommon.ToolUtils.getParentPath = function(path) {
	if (path.charAt(path.length - 1) == "/"
			|| path.charAt(path.length - 1) == "\\") {
		path = path.substring(0, path.length - 1);
	}

	var idx = Math.max(path.lastIndexOf("/"), path.lastIndexOf("\\"));
	path = path.substring(0, idx);
	return path;
};

appCommon.ToolUtils.getPathOfFile = function(file) {
	return appCommon.ToolUtils.getParentPath(file);
};

appCommon.ToolUtils.getNameOfFile = function(file) {
	var idx = file.lastIndexOf("/") + 1;
	return file.substring(idx);
};

appCommon.ToolUtils.getNameNoExtOfFile = function(file) {
	var name = appCommon.ToolUtils.getNameOfFile(file);
	var idx = name.indexOf(".");
	if (idx < 0) {
		return name;
	} else {
		return name.substring(0, idx);
	}
};

appCommon.ToolUtils.getExtOfFile = function(file) {
	var name = appCommon.ToolUtils.getNameOfFile(file);
	var idx = name.indexOf(".");
	if (idx < 0) {
		return "";
	} else {
		return name.substring(idx + 1);
	}
};

appCommon.ToolUtils.getValueFromBinding = function(binding) {
	var value = null;
	if (binding) {
		value = binding.evaluate();
		if (typeof value != "string") {
			value = value[0].getValue();
		}
	}
	return value;
};

/**
 * 转换URL
 * params json格式的参数，转换函数会对参数进行编码并拼到URL中，在页面中需要用justep.Context.getRequestParameter方法获取参数值
 * complateProcessAndActivity 补全process和activity，如果页面的URL中没有包含process和activity，转换函数会根据默认规则推导
 */
appCommon.ToolUtils.convertURL = function(url, params, complateProcessAndActivity) {
	function getProcessFromURL(url) {
		var s = url;
		if (s.indexOf("?") >= 0) {
			s = s.substring(0, s.indexOf("?"));
		}
		s = s.substring(0, s.lastIndexOf("/"));
		var process = s.substring(s.lastIndexOf("/"));
		return s + process + "Process";
	}
	function getActivityFromURL(url) {
		var s = url;
		if (s.indexOf("?") >= 0) {
			s = s.substring(0, s.indexOf("?"));
		}
		s = s.substring(s.lastIndexOf("/") + 1, s.lastIndexOf("."));
		return s;
	}
	
	var s = url;
	var p = new Array();
	
	// 转换参数
	if (params) {
		for (var o in params) {
			var paramValue = "";
			if (params[o]) {
				paramValue = encodeURIComponent(params[o]);
			}
			p.push(o + "=" + paramValue);
		}
	}
	
	// 补全process和activity
	if (complateProcessAndActivity && s.indexOf("process=") < 0) {
		p.push("process=" + getProcessFromURL(s));
	}
	if (complateProcessAndActivity && s.indexOf("activity=") < 0) {
		p.push("activity=" + getActivityFromURL(s));
	}
	
	// 拼装URL
	if (p.length > 0) {
		if (s.indexOf("?") < 0) {
			s = s + "?";
		} else {
			s = s + "&";
		}
		s = s + p.join("&");
	}
	return s;
};

appCommon.TaskUtils = {};

appCommon.TaskUtils.isNoticeTask = function(task) {
	var process = justep.Context.getCurrentProcess();
	var activity = justep.Context.getCurrentActivity();
	if (!task)
		task = justep.Context.getTask();
	if (!task)
		return false;

	var param = new justep.Request.ActionParam();
	param.setString("task", task);
	var r = justep.Request.sendBizRequest(process, activity,
			"isNoticeTaskAction", param);
	if (!justep.Request.isBizSuccess(r)) {
		throw new Error(justep.Request.getServerError(r, "获取任务类型失败"));
	}
	return (justep.XML.getNodeText(r.responseXML, "/root/data/*") == "true");
};

appCommon.SysUtils = {};

/**
 * 判断是否有action权限
 * 
 * @actions 可以为单个或多个，支持逗号分割字符串或字符串数组
 * @process 默认为当前环境的process
 * @activity 默认为当前环境的activity
 * result 如果是单个字符串只返回单一的布尔值，如果是数组或逗号分割的多个action，返回json
 */
appCommon.SysUtils.hasActionPermission = function(actions, process, activity) {
	if (!process)
		process = justep.Context.getCurrentProcess();
	if (!activity)
		activity = justep.Context.getCurrentActivity();
	var actionsArray = actions;
	if (typeof(actions) == "string") {
		actionsArray = actions.split(",");
	}

	var permissions = new justep.Request.ListParam();
	for (var i = 0; i < actionsArray.length; i++) {
		var item = new justep.Request.MapParam();
		item.put("process", new justep.Request.SimpleParam(process, justep.XML.Namespaces.XMLSCHEMA_STRING));
		item.put("activity",  new justep.Request.SimpleParam(activity, justep.XML.Namespaces.XMLSCHEMA_STRING));
		item.put("action", new justep.Request.SimpleParam(actionsArray[i], justep.XML.Namespaces.XMLSCHEMA_STRING));
		permissions.add(item);
	}
	var params = new justep.Request.ActionParam();
	params.setList("permissions", permissions);

	var result = justep.Request.sendBizRequest(null, null, "checkPermissionAction", params);
	var items = justep.Request.transform(justep.Request.getData(result.responseXML));
	if (typeof(actions) == "string" && actionsArray.length == 1) {
		return (items[0].hasPermission == "true");
	} else {
		var result = {};
		for (var i = 0; i < items.length; i++) {
			result[items[i].action] = (items[i].hasPermission == "true");
		}
		return result;
	}
};

appCommon.SysUtils.getRequestParameters = function() {
	/*
	var params = justep.XML.eval(justep.Context._data, "/form/input/parameters/*");
	var p = {};
	for (var i = 0; i < params.length; i++) {
		p[params[i].nodeName] = params[i].text;
	}
	return p;
	*/
	var p = {};
	for (var o in justep.Request.URLParams) {
		p[o] = decodeURIComponent(justep.Request.URLParams[o]);
	}
	return p;
};

appCommon.SysUtils.isHttps = function() {
	return (window.location.protocol == "https:");
};

appCommon.OrgUtils = {};

appCommon.OrgUtils.getIDByFID = function(fullID, orgKind) {
	var end = fullID.lastIndexOf("." + orgKind);
	if (end < 0) {
		return null;
	} else {
		var start = fullID.lastIndexOf("/", end) + 1;
		return fullID.substring(start, end);
	}
};

appCommon.OrgUtils.getNameByFName = function(fullID, fullName, orgKind, getFullName) {
	while (fullID && (appCommon.ToolUtils.getExtOfFile(fullID) != orgKind)) {
		fullID = appCommon.ToolUtils.getPathOfFile(fullID);
		fullName = appCommon.ToolUtils.getPathOfFile(fullName);
	}
	var name = appCommon.ToolUtils.getNameOfFile(fullName);
	if (getFullName) {
		while (fullID && (appCommon.ToolUtils.getExtOfFile(fullID) == orgKind)) {
			fullID = appCommon.ToolUtils.getPathOfFile(fullID);
			fullName = appCommon.ToolUtils.getPathOfFile(fullName);
			if (appCommon.ToolUtils.getExtOfFile(fullID) == orgKind) {
				name = appCommon.ToolUtils.getNameOfFile(fullName) + "/" + name;
			}
		}
	}
	return name;
};

appCommon.OrgUtils.getOgnIDByFID = function(fullID) {
	return appCommon.OrgUtils.getIDByFID(fullID, "ogn");
};

appCommon.OrgUtils.getOgnNameByFName = function(fullID, fullName) {
	return appCommon.OrgUtils.getNameByFName(fullID, fullName, "ogn");
};

appCommon.OrgUtils.getDeptIDByFID = function(fullID) {
	return appCommon.OrgUtils.getIDByFID(fullID, "dpt");
};

appCommon.OrgUtils.getDeptNameByFName = function(fullID, fullName) {
	return appCommon.OrgUtils.getNameByFName(fullID, fullName, "dpt", true);
};

appCommon.OrgUtils.getPosIDByFID = function(fullID) {
	return appCommon.OrgUtils.getIDByFID(fullID, "pos");
};

appCommon.OrgUtils.getPosNameByFName = function(fullID, fullName) {
	return appCommon.OrgUtils.getNameByFName(fullID, fullName, "pos");
};

appCommon.OrgUtils.getPersonIDByFID = function(fullID) {
	var psmID = appCommon.OrgUtils.getIDByFID(fullID, "psm");
	var idx = psmID.indexOf("@");
	if (idx < 0) {
		return psmID;
	} else {
		return psmID.substring(0, idx);
	}
};

appCommon.OrgUtils.getPersonNameByFName = function(fullID, fullName) {
	return appCommon.OrgUtils.getNameByFName(fullID, fullName, "psm");
};

appCommon.GridUtils = {};

appCommon.GridUtils.getCellBtnHTML = function(label, clickFunName) {
	return "<table width='100%' cellpadding='0' cellspacing='0' height='100%' border='0' style='table-layout: fixed;margin: 0;'>"
			+ "	<tr><td style='border-style: none'> "
			+ "		<span style='white-space: nowrap; text-decoration: none; display: block; width: 100%;overflow: hidden;'>"
			+ label
			+ "		</span></td>"
			+ "	<td width='30px' style='border-style: none;' valign='left'>"
			+ "		<input onclick='" + clickFunName + "()' type='button' value='…' style='width: 30px; height: 18px; font-size: 9pt; text-align: left;'>"
			+ "	</td></tr></table>";
};

appCommon.DataUtils = {};

/**
 * 复制data.filters.filterList
 */
appCommon.DataUtils.copyDataFilterList = function(data) {
	if (typeof (data) == "string")
		data = justep.xbl(data);

	var filterList = {};
	for (o in data.filters.filterList) {
		filterList[o] = data.filters.filterList[o];
	}
	return filterList;
};

/**
 * 赋值data.filters.filterList
 */
appCommon.DataUtils.setDataFilterList = function(data, filterList) {
	if (typeof (data) == "string")
		data = justep.xbl(data);

	for (o in filterList) {
		data.filters.filterList[o] = filterList[o];
	}
};

/**
 * 转换row data
 */
appCommon.DataUtils.createRowData = function(data, getValueFun) {
	var rowData = [];
	var columns = data.getColumnIds().split(",");
	for ( var i = 0; i < columns.length; i++)
		rowData[i] = getValueFun(columns[i]);
	return rowData;
};

/**
 * 刷新一个或多个rowId的数据
 * 
 * @data
 * @rowIds 逗号分隔的字符串或字符串数组
 * @callback 刷新数据后回调
 */
appCommon.DataUtils.refreshDataByRowIds = function(data, rowIds, callback) {
	if (typeof (data) == "string")
		data = justep.xbl(data);

	var rowIdArray = [];
	if (typeof (rowIds) == "string")
		rowIdArray = rowIds.split(',');
	else if (Object.prototype.toString.apply(rowIds) == "[object Array]")
		rowIdArray = rowIds;
	else {
		data.refreshData();
		return;
	}

	// 备份过滤条件
	var filterListBak = appCommon.DataUtils.copyDataFilterList(data);
	// 记录数据原位置
	var indexBak = {};
	for ( var i = 0; i < rowIdArray.length; i++) {
		var rowId = rowIdArray[i];
		indexBak[rowId] = data.getIndex(rowId);
	}

	// 构造过滤条件
	var rowIdFilter = data.getConceptAliasName() + " IN ('"
			+ rowIdArray.join("','") + "')";
	try {
		// 删除数据
		for ( var i = 0; i < rowIdArray.length; i++) {
			data.getStore().deleteRow(rowIdArray[i]);
		}
		// 修改过滤条件
		data.filters.clear();
		data.filters.setFilter("rowIdFilter", rowIdFilter);
		// 刷新数据
		data._doRefreshData(0, -1, true, callback);
		// 恢复数据位置
		for ( var i = 0; i < rowIdArray.length; i++) {
			var rowId = rowIdArray[i];
			if (indexBak[rowId] >= 0)
				data.getStore().moveRowPro(
						indexBak[rowId] - data.getIndex(rowId), rowId);
			data.setIndex(data.getIndex(rowId));
		}
	} finally {
		// 恢复过滤条件
		data.filters.clear();
		appCommon.DataUtils.setDataFilterList(data, filterListBak);
	}
};

appCommon.FilterUtils = {};

/**
 * 联合过滤条件
 */
appCommon.FilterUtils.joinFilter = function(filter1, filter2, operator) {
	if (!operator)
		operator = "and";

	if (filter1 && filter2)
		return "(" + filter1 + ") " + operator + " (" + filter2 + ")";
	else
		return (filter1 ? filter1 : "") + (filter2 ? filter2 : "");
};

appCommon.FilterUtils.getInFilter = function(field, values, split) {
	if (!split || split == "")
		split = ",";

	var valueArray = [];
	if (typeof (values) == "string")
		valueArray = values.split(split);
	else if (Object.prototype.toString.apply(values) == "[object Array]")
		valueArray = values;
	else
		throw new Error("无效的values");

	if (valueArray.length == 0)
		return "1 = 0";
	else
		return (field + " IN ('" + valueArray.join("','") + "')");
};

/**
 * 生成单选过滤条件
 */
appCommon.FilterUtils.getSingleSelectFilter = function(field, value) {
	return (field + " = '" + value + "'");
};

/**
 * 生成多选过滤条件
 * 
 * @field 过滤的字段
 * @values 过滤的数值，字符串或字符串数组
 * @split 分隔符，默认逗号分隔
 */
appCommon.FilterUtils.getMuiltSelectFilter = function(field, values, split) {
	return appCommon.FilterUtils.getInFilter(field, values, split);
};

/**
 * 生成多字段模糊过滤条件
 * 
 * @fields 过滤的字段，逗号分隔的字符串或字符串数组
 * @value string
 */
appCommon.FilterUtils.getMultiLikeFilter = function(fields, value, split) {
	if (!split)
		split = ",";

	var fieldArray = [];
	if (typeof (fields) == "string")
		fieldArray = fields.split(split);
	else if (Object.prototype.toString.apply(fields) == "[object Array]")
		fieldArray = fields;
	else
		throw new Error("无效的fields");

	value = value.toUpperCase();
	var filter = "";
	for ( var i = 0; i < fieldArray.length; i++) {
		filter = appCommon.FilterUtils.joinFilter(filter, "UPPER("
				+ fieldArray[i] + ") LIKE '%" + value + "%'", "OR");
	}
	return filter;
};

/**
 * 生成日期过滤
 * 
 * @field string
 * @beginDate Date
 * @endDate Date
 */
appCommon.FilterUtils.getDateFilter = function(field, beginDate, endDate) {
	var beginStr = null;
	if (Object.prototype.toString.apply(beginDate) == "[object Date]") {
		beginStr = beginDate ? justep.Date.toString(beginDate, 'YYYY-MM-DD')
				: null;
	}
	var endStr = null;
	if (Object.prototype.toString.apply(endDate) == "[object Date]") {
		endStr = endDate ? justep.Date.toString(justep.Date.increase(endDate,
				1, "d"), 'YYYY-MM-DD') : null;
	}

	if (beginStr && endStr)
		return "(stringToDate('" + beginStr + "') <= " + field
				+ ") AND (stringToDate('" + endStr + "') > " + field + ")";
	else if (beginStr)
		return "(stringToDate('" + beginStr + "') <= " + field + ")";
	else if (endStr)
		return "(stringToDate('" + endStr + "') > " + field + ")";
	else
		return "";
};

/**
 * 生成日期范围过滤
 * 
 * @beginField string
 * @endField string
 * @beginDate Date
 * @endDate Date
 */
appCommon.FilterUtils.getDateRangeFilter = function(beginField, endField,
		beginDate, endDate) {
	var beginStr = null;
	if (Object.prototype.toString.apply(beginDate) == "[object Date]") {
		beginStr = beginDate ? justep.Date.toString(beginDate, 'YYYY-MM-DD')
				: null;
	}
	var endStr = null;
	if (Object.prototype.toString.apply(endDate) == "[object Date]") {
		endStr = endDate ? justep.Date.toString(justep.Date.increase(endDate,
				1, "d"), 'YYYY-MM-DD') : null;
	}

	if (beginStr && endStr)
		return "((" + endField + " IS NULL) OR (stringToDate('" + beginStr
				+ "') <= " + endField + ")) " + " AND ((" + beginField
				+ " IS NULL) OR (stringToDate('" + endStr + "') > "
				+ beginField + "))";
	else if (beginStr)
		return "((" + endField + " IS NULL) OR (stringToDate('" + beginStr
				+ "') <= " + endField + "))";
	else if (endStr)
		return "((" + beginField + " IS NULL) OR (stringToDate('" + endStr
				+ "') > " + beginField + "))";
	else
		return "";
};

/**
 * 生成当前操作员所有人员成员的URL过滤条件
 * 
 * @urlField string
 */
appCommon.FilterUtils.getCurrentMembersURLFilter = function(urlField) {
	var filter = "";

	var psmFIDs = justep.Context.getAllPersonMemberFIDs();
	for ( var i = 0; i < psmFIDs.length; i++) {
		var psmFID = psmFIDs[i];
		var psmFIDSplit = psmFID.split("/");

		var psmFIDPart = "";
		for ( var j = 0; j < psmFIDSplit.length; j++) {
			var splitID = psmFIDSplit[j];
			if (splitID == "")
				continue;

			if (psmFIDPart == "") {
				psmFIDPart = "/" + splitID;
			} else {
				psmFIDPart = psmFIDPart + "/" + splitID;
			}

			var condition = "(" + urlField + " = '" + psmFIDPart + "')";
			if (filter.indexOf(condition) < 0)
				filter = appCommon.FilterUtils.joinFilter(filter, condition,
						"OR");
		}
	}
	return filter;
};

appCommon.PortalUtils = {};

appCommon.PortalUtils.getPortalWindow = function(name) {
	var w = null;
	if (name) {
		var frames = window.parent.frames;
		for ( var i = 0; i < frames.length; i++) {
			if (frames[i].name == name) {
				w = frames[i];
				break;
			}
		}
	}
	return w;
};

appCommon.ConfigUtils = {};

/**
 * 获取BIZ层配置文件
 * 
 * @param fileFullName
 *            BIZ目录下的路径，例如：/appDemo/wjdemo/process/test/processExecuteConfig.xml
 * @return
 */
appCommon.ConfigUtils.getBizConfig = function(fileFullName, process, activity) {
	if (!process)
		process = justep.Context.getCurrentProcess();
	if (!activity)
		activity = justep.Context.getCurrentActivity();

	var param = new justep.Request.ActionParam();
	param.setString("fileFullName", fileFullName);

	var r = justep.Request.sendBizRequest(process, activity,
			"getBizConfigAction", param);
	if (!justep.Request.isBizSuccess(r)) {
		throw new Error(justep.Request.getServerError(r, "获取过程配置失败"));
	}
	return justep.XML.eval(r.responseXML, "/root/data/*/*", "single");
};

/**
 * 获得当前过程的BIZ配置文件
 */
appCommon.ConfigUtils.getCurrentProcessBizConfig = function(fileName) {
	var process = justep.Context.getCurrentProcess();
	var fileFullName = appCommon.ToolUtils.getParentPath(process) + "/"
			+ fileName;
	return appCommon.ConfigUtils.getBizConfig(fileFullName);
};

/**
 * 获取字符串配置项
 * 
 * @param doc
 * @param nodePath
 * @param defaultValue
 * @return
 */
appCommon.ConfigUtils.getStringConfigItem = function(doc, nodePath,
		defaultValue) {
	return justep.XML.getNodeText(doc, nodePath, defaultValue);
};

/**
 * 获取布尔型配置项
 * 
 * @param doc
 * @param nodePath
 * @param defaultValue
 * @return
 */
appCommon.ConfigUtils.getBooleanConfigItem = function(doc, nodePath,
		defaultValue) {
	var configValue = appCommon.ConfigUtils.getStringConfigItem(doc, nodePath,
			null);
	return appCommon.ConfigUtils.checkBooleanConfigItem(configValue,
			defaultValue);
};

appCommon.ConfigUtils.checkBooleanConfigItem = function(configValue,
		defaultValue) {
	if (!configValue)
		return defaultValue;
	return configValue.toUpperCase() == "TRUE";
};

/**
 * 获取环节配置项 见biz层说明
 * 
 * @param doc
 * @param nodePath
 * @param defaultValue
 * @return
 */
appCommon.ConfigUtils.getActivityConfigItem = function(doc, nodePath,
		defaultValue) {
	var configValue = appCommon.ConfigUtils.getStringConfigItem(doc, nodePath,
			null);
	return appCommon.ConfigUtils.checkActivityConfigItem(configValue,
			defaultValue);
};

appCommon.ConfigUtils.checkActivityConfigItem = function(configValue,
		defaultValue) {
	if (!configValue)
		return defaultValue;

	if (configValue.toUpperCase() == "ALL")
		return true;
	if (configValue.toUpperCase() == "NONE")
		return false;

	var activities = configValue.split(",");
	var activityName = justep.Context.getCurrentActivity();
	var activityLabel = justep.Context.getCurrentActivityLabel();

	for ( var i = 0; i < activities.length; i++) {
		if (activities[i] == activityName || activities[i] == activityLabel)
			return true;
	}
	return false;
};

appCommon.ProcessExecute = {};

/**
 * 流程处理配置文件
 */
appCommon.ProcessExecute.PROCESS_EXECUTE_CONFIG_FILE = "processExecuteConfig.xml";

/**
 * 流程查询配置文件
 */
appCommon.ProcessExecute.PROCESS_QUERY_CONFIG_FILE = "processQueryConfig.xml";

/**
 * 处理数据的列定义
 */
appCommon.ProcessExecute.EXECUTE_DATA_COLUMNS = "fMasterID,"
		+ "fTaskID,fActivityFName,fActivityLabel,fOpinion,fVerdict,"
		+ "fState,fStateName,fCreateOgnID,fCreateOgnName,"
		+ "fCreateDeptID,fCreateDeptName,fCreatePosID,fCreatePosName,"
		+ "fCreatePsnID,fCreatePsnName,fCreatePsnFID,fCreatePsnFName,fCreateTime,"
		+ "fUpdatePsnID,fUpdatePsnName,fUpdateTime,version";

/**
 * 获取当前处理数据
 */
appCommon.ProcessExecute.getCurrentExecuteData = function(dataModel,
		executeConcept, bizID, taskID) {
	var param = new justep.Request.ActionParam();
	param.setString("dataModel", dataModel);
	param.setString("executeConcept", executeConcept);
	param.setString("bizID", bizID);
	param.setString("taskID", taskID);

	var r = justep.Request.sendBizRequest(justep.Context.getCurrentProcess(),
			justep.Context.getCurrentActivity(), "getCurrentExecuteDataAction",
			param);
	if (!justep.Request.isBizSuccess(r)) {
		throw new Error(justep.Request.getServerError(r, "获取处理数据失败"));
	}
	var st = new SimpleStore("executeData",
			appCommon.ProcessExecute.EXECUTE_DATA_COLUMNS);
	st.loadData(null, r.responseXML);
	return st;
};

/**
 * 设置当前处理数据
 */
appCommon.ProcessExecute.setCurrentExecuteData = function(dataModel,
		executeConcept, bizID, taskID, fExecuteID, fOpinion, fVerdict) {
	var param = new justep.Request.ActionParam();
	param.setString("dataModel", dataModel);
	param.setString("executeConcept", executeConcept);
	param.setString("bizID", bizID);
	param.setString("taskID", taskID);
	if (!fExecuteID)
		param.setNULL("fExecuteID");
	else
		param.setString("fExecuteID", fExecuteID);
	if (!fOpinion)
		param.setNULL("fOpinion");
	else
		param.setString("fOpinion", fOpinion);
	if (!fVerdict)
		param.setNULL("fVerdict");
	else
		param.setString("fVerdict", fVerdict);

	var r = justep.Request.sendBizRequest(justep.Context.getCurrentProcess(),
			justep.Context.getCurrentActivity(), "setCurrentExecuteDataAction",
			param);
	if (!justep.Request.isBizSuccess(r)) {
		throw new Error(justep.Request.getServerError(r, "保存处理数据失败"));
	}
	var st = new SimpleStore("executeData",
			appCommon.ProcessExecute.EXECUTE_DATA_COLUMNS);
	st.loadData(null, r.responseXML);
	return st;
};

appCommon.ProcessExecute.getExecuteListData = function(process, activity,
		dataModel, executeConcept, bizID, condition, orderBy) {
	var param = new justep.Request.ActionParam();
	param.setString("dataModel", dataModel);
	param.setString("executeConcept", executeConcept);
	param.setString("bizID", bizID);
	if (!condition) {
		param.setNULL("condition");
	} else {
		param.setString("condition", condition);
	}
	if (orderBy) {
		param.setString("orderBy", orderBy);
	}

	var r = justep.Request.sendBizRequest(process, activity,
			"getExecuteListDataAction", param);
	if (!justep.Request.isBizSuccess(r)) {
		throw new Error(justep.Request.getServerError(r, "获取处理列表数据失败"));
	}
	var st = new SimpleStore("executeData",
			appCommon.ProcessExecute.EXECUTE_DATA_COLUMNS);
	st.loadData(null, r.responseXML);
	return st;
};

appCommon.mail = {};
appCommon.mail.TurboMailUtils = {};

appCommon.mail.TurboMailUtils.syncAllPerson = function() {
	var process = justep.Context.getCurrentProcess();
	var activity = justep.Context.getCurrentActivity();

	var r = justep.Request.sendBizRequest(process, activity,
			"turboMailSyncAllPersonAction", null);
	if (!justep.Request.isBizSuccess(r)) {
		throw new Error(justep.Request.getServerError(r, "同步邮件账号失败"));
	}
	alert("同步邮件账号成功");
};

appCommon.mail.TurboMailUtils.getLoginURL = function() {
	var process = justep.Context.getCurrentProcess();
	var activity = justep.Context.getCurrentActivity();

	var r = justep.Request.sendBizRequest(process, activity,
			"turboMailGetLoginURLAction", null);
	if (!justep.Request.isBizSuccess(r)) {
		throw new Error(justep.Request.getServerError(r, "邮件登录失败"));
	}
	return justep.XML.getNodeText(r.responseXML, "/root/data/*");
};

appCommon.mail.TurboMailUtils.getNewMsgNum = function() {
	var process = justep.Context.getCurrentProcess();
	var activity = justep.Context.getCurrentActivity();

	var r = justep.Request.sendBizRequest(process, activity,
			"turboMailGetNewMsgNumAction", null);
	if (!justep.Request.isBizSuccess(r)) {
		throw new Error(justep.Request.getServerError(r, "获取新邮件数量失败"));
	}
	return justep.XML.getNodeText(r.responseXML, "/root/data/*");
};

appCommon.dialogs = {};

appCommon.dialogs.FlowTrackDialog = {};
appCommon.dialogs.flowTrackDialog = appCommon.dialogs.FlowTrackDialog; //兼容

appCommon.dialogs.FlowTrackDialog._url = "/appCommon/dialogs/flowTrack/flowTrack.w";
appCommon.dialogs.FlowTrackDialog._title = "流程轨迹";
appCommon.dialogs.FlowTrackDialog._dialog = null;
appCommon.dialogs.FlowTrackDialog.getDialog = function() {
	if (!this._dialog) {
		this._dialog =	new justep.WindowDialog("__flowTrackDialog__", this._url, this._title, true, "maximize");
	}
	return this._dialog;
};

appCommon.dialogs.FlowTrackDialog.open = function(process, bizID,activity) {
	var params = {
		process: process,
		bizID: bizID
	};
	var url = appCommon.ToolUtils.convertURL(this._url, params);
	if(!!activity) {
		url += "&activity=" + activity;
	}
	this.getDialog().open(params, this._title, url);
};