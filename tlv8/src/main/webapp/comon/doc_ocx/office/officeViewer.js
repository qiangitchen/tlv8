/**
 * OfficeViewer JavaScript, version 0.0.0.7 (c) 2000-2009 Justep
 * 
 * @author : CG
 * @date : 2009-09-10
 */

/**
 * Assert					断言函数
 * OfficeViewerClassID		获取OfficeViewer的classid
 * IsOfficeViewer			判断是否是OfficeViewer
 * CreateOfficeViewer		创建OfficeViewer
 * DestroyOfficeViewer		释放OfficeViewer
 * OfficeViewerEval			执行OfficeViewer指令
 * CheckViewer				检查source是否为OfficeViewer
 * CheckInstalled			检查操作系统是否安装了MSOffice中某个应用程序
 * CreateDoc				创建MSOffice中某个应用程序的文档
 * CreateWord				创建MSWord
 * CreateExcel				创建MSExcel
 * CreatePowerPoint			创建MSPowerPoint
 * CreateVisio				创建MSVisio
 * CreateProject			创建MSProject
 * IsDocOpened				判断是否打开了MSOffice中某个应用程序的文档
 * IsWordOpened				判断是否打开了MSWord
 * IsExcelOpened			判断是否打开了MSExcel
 * IsPowerPointOpened		判断是否打开了MSPowerPoint
 * IsVisioOpened			判断是否打开了MSVisio
 * IsProjectOpened			判断是否打开了MSProject
 * WordGetStatistic			获取MSWord统计数据
 * WordToggleShowCodes		显示或隐藏域代码
 * WordFieldSelected		是否选择了指定域
 * WordFieldSelect			选择指定域
 * WordUpdateField			更新指定域
 * WordInsertField			在当前选择位置插入域
 * WordGetNewFieldID		获取新的域标识
 * WordGetField				获取域对象
 * WordGetFieldData			获取域数据
 * WordGetFieldID			获取域标识
 * WordGetFieldName			获取域名称
 * WordGetFieldValue		获取域值
 * WordGetFieldRange		获取域名范围对象
 * WordAutoFillField		自动填充某个域的值
 * WordAutoFillFields		自动填充多个域的值
 * WordDeleteFields			删除当前选择区域中的域定义
 * WordDeleteAllFields		删除所有域定义
 * WordGetFieldJSON			获取JSON格式域信息
 * WordGetFieldInfo			获取域信息
 * WordInsertWebPicture		插入图片
 * WordInsertWebFile		插入文件内容
 * WordRevisionInit			初始化MSWord的痕迹保留
 * WordRevisionNone			复位MSWord的痕迹保留状态
 * WordGetRevisionJSON		获取MSWord痕迹保留的JSON格式修订记录
 * WordGetRevisionInfo		获取MSWord痕迹保留的修订记录
 * WordRevisionTypeToStr	转换痕迹保留的修订类型
 * InitOfficeViewer			控件初始化处理过程
 * DisableOfficeButton		禁止工具栏按钮
 * DisableFileCommand 		设置菜单项(the File menu)按钮disable属性
 * DisableOfficeReviewingBar 是否显示审阅工具栏中对应的属性
 */
var $OV = _OV =  function (element) {
	element = OV.getElementByID(element);
	if (element.OV_OfficeViewer_extended) return element;
	element.OV_OfficeViewer_extended = true;
	element.resCellRegExp_t = /\.\w*$/;
	element.resCellRegExp_f = /\.[^\.]+$/;
	return OV.extend(element, OV.OfficeViewer);
};

if (!this.OV) this.OV = (function() {
	var ProgramID = {
		Word:		"Word.Document",
		Excel:		"Excel.Sheet",
		PowerPoint:	"PowerPoint.Show",
		Visio:		"Visio.Drawing",
		Project:	"MSProject.Project"
	};
	var OpenedFileType = {
		oaUnknown:		-1,
		oaNoOpened:		0,
		oaWord:			1,
		oaExcel:		2,
		oaPowerPoint:	3,
		oaVisio:		4,
		oaProject:		5
	};
	var WdRangeType = {
		wdRangeAll:		0,
		wdRangeStart:	1,
		wdRangeEnd:		2
	};
	var WdStatistic = {
		wdStatisticWords:					0,
		wdStatisticLines:					1,
		wdStatisticPages:					2,
		wdStatisticCharacters:				3,
		wdStatisticParagraphs:				4,
		wdStatisticCharactersWithSpaces:	5,
		wdStatisticFarEastCharacters:		6
	};
	var WdBreakType = {
		wdSectionBreakNextPage:		2,
		wdSectionBreakContinuous:	3,
		wdSectionBreakEvenPage:		4,
		wdSectionBreakOddPage:		5,
		wdLineBreak:				6,
		wdPageBreak:				7,
		wdColumnBreak:				8,
		wdLineBreakClearLeft:		9,
		wdLineBreakClearRight:		10,
		wdTextWrappingBreak:		11
	};
	var WdSelectionType = {
		wdSelectionInlineShape:	7,
		wdSelectionShape:		8
	};
	var WdFieldType = {
		wdFieldAddin:	81
	};
	var WdWrapType = {
		wdWrapNone:	3
	};
	var MsoZOrderCmd = {
		msoBringInFrontOfText:	4
	};
	function extend(original, extended) {
		for (var key in (extended || {})) original[key] = extended[key];
		return original;
	}
	function getMultiLine(fn) {
		var lines = new String(fn);
		lines = lines.substring(lines.indexOf("/*") + 2, lines.lastIndexOf("*/"));
		return lines;
	}
	function trim(str) {
		return str.replace(/^\s+|\s+$/g, '');
	}
	function substitute(str, object, regexp) {
		return str.replace(regexp || (/\\?\{([^{}]+)\}/g), function(match, name) {
			if (match.charAt(0) == '\\') return match.slice(1);
			return OV.isClear(object[name]) ? '' : object[name];
		});
	}
	function truncate(str, length, truncation) {
		length = length || 30;
		truncation = OV.isClear(truncation) ? '...' : truncation;
		return str.length > length ?
			str.slice(0, length - truncation.length) + truncation : String(str);
	}
	function tryThese(source) {
		for (var i = OV.isFunction(source) ? 0 : 1, l = arguments.length; i < l; i++) {
			try {
				return arguments[i](OV.isFunction(source) ? undefined : source);
			} catch(e) {}
		}
		return null;
	}
	function escape(str) {
		var lines = new String(str);
		return lines.replace(/\&/g, "&amp;")
			.replace(/\ /g, "&nbsp;")
			.replace(/\t/g, "&nbsp;&nbsp;&nbsp;&nbsp;")
			.replace(/\=/g, "&#61;")
			.replace(/\</g, "&lt;")
			.replace(/\>/g, "&gt;")
			.replace(/\r\n/g, "<br/>")
			.replace(/\r/g, "<br/>")
			.replace(/\n/g, "<br/>")
			.replace(/\'/g, "&#39;")
			.replace(/\"/g, "&quot;");
	}
	function getClass(object) {
		return Object.prototype.toString.call(object)
			.match(/^\[object\s(.*)\]$/)[1];
	}
	function isArray(object) {
		return OV.getClass(object) === "Array";
	}
	function isString(object) {
		return OV.getClass(object) === "String";
	}
	function isNumber(object) {
		return OV.getClass(object) === "Number";
	}
	function isFunction(object) {
		return typeof object === "function";
	}
	function isObject(object) {
		return typeof object === 'object';
	}
	function isUndefined(object) {
		return typeof object === "undefined";
	}
	function isNull(object) {
		return object == null;
	}
	function isClear(object) {
		return OV.isUndefined(object) || OV.isNull(object);
	}
	function isJSON(str) {
		return OV.isString(str) && Boolean(str.length) && 
			/^[\],:{}\s]*$/.test(str.replace(/\\(?:["\\\/bfnrt]|u[0-9a-fA-F]{4})/g, '@').
			replace(/"[^"\\\n\r]*"|true|false|null|-?\d+(?:\.\d*)?(?:[eE][+\-]?\d+)?/g, ']').
			replace(/(?:^|:|,)(?:\s*\[)+/g, '')) && !/^[\d\s\+\-\*\/]*$/.test(str);
	}
	function getElementByID(element) {
		if (OV.isString(element)) element = document.getElementById(element);
		if (OV.isClear(element)) return undefined;
		return element;
	}
	function getElementsByClassName(className, tag, elm) {
		tag = tag || "*";
		elm = elm || document;
		var classes = className.split(" "),
			classesToCheck = [],
			elements = (tag === "*" && elm.all)? elm.all : elm.getElementsByTagName(tag),
			current,
			returnElements = [],
			match;
		for(var k = 0, kl = classes.length; k < kl; k += 1){
			classesToCheck.push(new RegExp("(^|\\s)" + classes[k] + "(\\s|$)"));
		}
		for(var l = 0, ll = elements.length; l < ll; l += 1){
			current = elements[l];
			match = false;
			for(var m = 0, ml = classesToCheck.length; m < ml; m += 1){
				match = classesToCheck[m].test(current.className);
				if (!match) {
					break;
				}
			}
			if (match) {
				returnElements.push(current);
			}
		}
		return returnElements;
	}
	function addEvent(element, type, fn) {
		element = OV.getElementByID(element);
		if (element.addEventListener) {
			element.addEventListener(type, fn, false);
		} else if (element.attachEvent) {
			element["e" + type + fn] = fn;
			element[type + fn] = function() {
				element["e" + type + fn](window.event);
			};
			element.attachEvent("on" + type, element[type + fn]);
		} else {
			element["on" + type] = element["e" + type + fn];
		}
	}
	var OfficeViewer = (function() {
		/**
		 * 断言函数
		 * @expr 断言表达式
		 * @msg 错误信息
		 * @return 返回断言表达式的值
		 */
		function Assert(expr, msg) {
			if (expr === false) alert("Assert::" + msg);
			return expr;
		}
		/**
		 * 获取OfficeViewer的classid
		 * @retrun 返回OfficeViewer的classid
		 */
		function OfficeViewerClassID() {
			return "clsid:6BA21C22-53A5-463F-BBE8-5CF7FFA0132B";
		}
		/**
		 * 判断是否是OfficeViewer
		 * @retrun 返回是否是OfficeViewer
		 */
		function IsOfficeViewer() {
			return this.classid == this.OfficeViewerClassID();
		}
		/**
		 * 创建OfficeViewer
		 * @width 宽度，默认650
		 * @height 高度，默认450
		 * @return 返回是否执行了创建操作
		 */
		function CreateOfficeViewer(width, height) {
			var fn = function() {
				/*
				<object id="{id}" classid="{classid}" width="{width}" height="{height}" 
					codebase="/UI/system/service/doc/office/officeviewer.cab#version=${version}">
					<param name="BorderColor" value="-2147483632"/>
					<param name="BackColor" value="-2147483643"/>
					<param name="ForeColor" value="-2147483640"/>
					<param name="TitlebarColor" value="-2147483635"/>
					<param name="TitlebarTextColor" value="-2147483634"/>
					<param name="BorderStyle" value="0"/>
					<param name="Titlebar" value="0"/>
					<param name="Toolbars" value="1"/>
					<param name="LicenseName" value="Justep"/>
					<param name="LicenseKey" value="ED99-5508-1219-ABBD"/>
					<param name="Menubar" value="0"/>
					<param name="ActivationPolicy" value="1"/>
					<param name="FrameHookPolicy" value="0"/>
					<param name="MenuAccelerators" value="1"/>
				</object>
				*/
			};
			if (!this.IsOfficeViewer()) {
				this.removeAttribute('OV_OfficeViewer_extended');
				var s = this.outerHTML;
				this.outerHTML = OV.substitute(OV.getMultiLine(fn), {
					id: this.id,
					classid: this.OfficeViewerClassID(),
					width: OV.isClear(width) ? 650 : width,
					height: OV.isClear(height) ? 450 : height,
					version: OV.isClear(_ocx_version) ? _ocx_version : 0		
				});
				$OV(this.id).$outerHTML = s;
				return true;
			} else {
				return false;
			}
		}
		/**
		 * 释放OfficeViewer
		 * @return 返回是否执行了释放操作
		 */
		function DestroyOfficeViewer() {
			if (this.IsOfficeViewer() && this.IsOpened) this.Close();
			if (this.$outerHTML) {
				this.outerHTML = this.$outerHTML;
				return true;
			} else {
				return false;
			}
		}
		/**
		 * 执行OfficeViewer指令
		 * 例如：
		 * 		<div style="width=0;height=0;"><div id="ElementID"></div></div>
		 * 		......
		 * 		$OV("ElementID").OfficeViewerEval(function(source){
		 * 			source.Open("C:\\test.doc");
		 * 			......
		 * 			source.Save();
		 * 		});
		 * @fn 包含OfficeViewer指令的函数
		 * @return 返回是否成功执行
		 */
		function OfficeViewerEval(fn) {
			var id = this.id;
			$OV(id).CreateOfficeViewer(0, 0);
			try {
				fn($OV(id));
			} finally {
				$OV(id).DestroyOfficeViewer();
			}
			return true;
		}
		/**
		 * 检查source是否为OfficeViewer
		 * @return 返回是否为OfficeViewer的布尔值
		 */
		function CheckViewer() {
			return this.Assert(
				this.IsOfficeViewer(),
				OV.substitute("The {nodeName}[{id}] is not OfficeViewer.", {nodeName: this.nodeName, id: this.id}));
		}
		/**
		 * 检查操作系统是否安装了MSOffice中某个应用程序
		 * @programID 应用程序标识，参考ProgramID
		 * @return 返回是否安装了MSOffice中某个应用程序的布尔值
		 */
		function CheckInstalled(programID) {
			return this.CheckViewer() && this.Assert(
				this.OfficeProgramIsInstalled(programID),
				OV.substitute("The computer hasn't installed Microsoft {pid}.", {pid: programID.replace(/MS|\.\w*/, "")}));
		}
		/**
		 * 创建MSOffice中某个应用程序的文档
		 * @programID 应用程序标识，参考ProgramID
		 * @return 返回是否安装了该程序
		 */
		function CreateDoc(programID) {
			if (this.CheckViewer() && this.CheckInstalled(programID)) {
				this.CreateNew(programID);
				this.Activate();
				return true;
			} else {
				return false;
			}
		}
		/**
		 * 创建MSWord
		 * @return 返回是否安装了MSWord
		 */
		function CreateWord() {
			return this.CreateDoc(OV.ProgramID.Word);
		}
		/**
		 * 创建MSExcel
		 * @return 返回是否安装了MSExcel
		 */
		function CreateExcel() {
			return this.CreateDoc(OV.ProgramID.Excel);
		}
		/**
		 * 创建MSPowerPoint
		 * @return 返回是否安装了MSPowerPoint
		 */
		function CreatePowerPoint() {
			return this.CreateDoc(OV.ProgramID.PowerPoint);
		}
		/**
		 * 创建MSVisio
		 * @return 返回是否安装了MSVisio
		 */
		function CreateVisio() {
			return this.CreateDoc(OV.ProgramID.Visio);
		}
		/**
		 * 创建MSProject
		 * @return 返回是否安装了MSProject
		 */
		function CreateProject() {
			return this.CreateDoc(OV.ProgramID.Project);
		}
		/**
		 * 判断是否打开了MSOffice中某个应用程序的文档
		 * @programID 应用程序标识，参考ProgramID
		 * @return 返回是否打开了该文档
		 */
		function IsDocOpened(programID) {
			if (!this.CheckViewer()) {
				return false;
			} else if (OV.isClear(programID)) {
				return this.IsOpened;
			} else if (OV.isNumber(programID)) {
				return this.GetOpenedFileType() == programID;
			} else if (OV.isString(programID)) {
				switch (programID) {
					case OV.ProgramID.Word : this.IsDocOpened(OV.OpenedFileType.oaWord);
					case OV.ProgramID.Excel : this.IsDocOpened(OV.OpenedFileType.oaExcel);
					case OV.ProgramID.PowerPoint : this.IsDocOpened(OV.OpenedFileType.oaPowerPoint);
					case OV.ProgramID.Visio : this.IsDocOpened(OV.OpenedFileType.oaVisio);
					case OV.ProgramID.Project : this.IsDocOpened(OV.OpenedFileType.oaProject);
					default : return this.Assert(false,
						OV.substitute("The {ID} is not ProgramID.", {ID: programID}));
				}
			} else {
				return false;
			}
		}
		/**
		 * 判断是否打开了MSWord
		 * @return 返回是否打开了MSWord
		 */
		function IsWordOpened() {
			return this.IsDocOpened(OV.OpenedFileType.oaWord);
		}
		/**
		 * 判断是否打开了MSExcel
		 * @return 返回是否打开了MSExcel
		 */
		function IsExcelOpened() {
			return this.IsDocOpened(OV.OpenedFileType.oaExcel);
		}
		/**
		 * 判断是否打开了MSPowerPoint
		 * @return 返回是否打开了MSPowerPoint
		 */
		function IsPowerPointOpened() {
			return this.IsDocOpened(OV.OpenedFileType.oaPowerPoint);
		}
		/**
		 * 判断是否打开了MSVisio
		 * @return 返回是否打开了MSVisio
		 */
		function IsVisioOpened() {
			return this.IsDocOpened(OV.OpenedFileType.oaVisio);
		}
		/**
		 * 判断是否打开了MSProject
		 * @return 返回是否打开了MSProject
		 */
		function IsProjectOpened() {
			return this.IsDocOpened(OV.OpenedFileType.oaProject);
		}
		/**
		 * 获取MSWord统计数据
		 * @type 统计数据类型，参考WdStatistic
		 * @return 返回统计数据结果，undefined表示获取失败
		 */
		function WordGetStatistic(type){
			if (this.CheckViewer() && this.IsWordOpened()) {
				return this.ActiveDocument.Range().ComputeStatistics(type);
			}
		}
		/**
		 * 显示或隐藏域代码
		 * @value true为显示，false为隐藏，undefined为自动匹配显示或隐藏
		 * @return 返回是否打开了MSWord
		 */
		function WordToggleShowCodes(value) {
			if (this.CheckViewer() && this.IsWordOpened()) {
				if (OV.isClear(value)) {
					this.ActiveDocument.Fields.ToggleShowCodes();
				} else {
					for (var i = 1; i <= this.ActiveDocument.Fields.Count; i++) {
						var field = this.WordGetField(i);
						if (!OV.isClear(field) && (field.Type == OV.WdFieldType.wdFieldAddin)) {
							field.ShowCodes = value;
						}
					}
				}
				return true;
			} else {
				return false;
			}		
		}
		/**
		 * 是否选择了指定域，域类型为wdFieldAddin
		 * @fieldID 域标识
		 * @return 返回是否选中
		 */
		function WordFieldSelected(fieldID) {
			var field = this.WordGetField(fieldID);
			if (!OV.isClear(field) && (field.Type == OV.WdFieldType.wdFieldAddin)) {
				var range = this.WordGetFieldRange(field);
				var selection = this.Application.Selection;
				if ((selection.Start <= range.Start) && (selection.End >= range.End) || 
					(selection.Start > range.Start) && (selection.Start < range.End) || 
					(selection.End > range.Start) && (selection.End < range.End)) {
						return true;
				}
			}
			return false;
		}
		/**
		 * 选择指定域，域类型为wdFieldAddin
		 * @fieldID 域标识
		 * @return 返回指定域
		 */
		function WordFieldSelect(fieldID) {
			var fieldRange = this.WordGetFieldRange(fieldID);
			if (!OV.isClear(fieldRange)) {
				fieldRange.Select();
			}
			return false;
		}
		/**
		 * 更新指定域
		 * @fieldID 域标识
		 * @newFieldID 新的域标识
		 * @newFieldName 新的域名称
		 * @newFieldValue 新的域值
		 * @return 返回是否更新成功
		 */
		function WordUpdateField(fieldID, newFieldID, newFieldName, newFieldValue) {
			var field = this.WordGetField(fieldID);
			if (!OV.isClear(field) && (field.Type == OV.WdFieldType.wdFieldAddin)) {
				field.Data = newFieldID + '|' + (newFieldName || '') + '|' + (newFieldValue || '');
				return true;
			}
			return false;
		}
		/**
		 * 在当前选择位置插入域，域类型为wdFieldAddin，不允许在图层上插入域
		 * @fieldID 域标识
		 * @fieldName 域名称
		 * @return 返回是否打开了MSWord，是否插入成功
		 */
		function WordInsertField(fieldID, fieldName) {
			if (this.CheckViewer() && this.IsWordOpened()) {
				var selection = this.Application.Selection;
				if ((selection.Type == OV.WdSelectionType.wdSelectionInlineShape) ||
					(selection.Type == OV.WdSelectionType.wdSelectionShape)) {
					return false;
				} else {
					var field = this.ActiveDocument.Fields.Add(selection.Range, OV.WdFieldType.wdFieldAddin);
					this.WordUpdateField(field, fieldID, fieldName);
					return true;
				}
			} else {
				return false;
			}
		}
		/**
		 * 获取新的域标识
		 * @prefix 域标识前缀
		 * @return 返回前缀加数字的唯一标识符
		 */
		function WordGetNewFieldID(prefix) {
			if (OV.isClear(prefix)) {
				prefix = "Field_";
			}
			var i = 1;
			var newFieldID = prefix + i;
			while (!OV.isClear(this.WordGetField(newFieldID))) {
				i++;
				newFieldID = prefix + i;
			}
			return newFieldID;
		}
		/**
		 * 获取域对象
		 * @fieldID 域标识、域索引、域对象
		 * @return 返回域对象，undefined表示获取失败
		 */
		function WordGetField(fieldID) {
			if (this.CheckViewer() && this.IsWordOpened()) {
				if (OV.isNumber(fieldID)) {
					return this.ActiveDocument.Fields(fieldID);
				} else if (OV.isString(fieldID)) {
					for (var i = 1; i <= this.ActiveDocument.Fields.Count; i++) {
						var field = this.WordGetField(i);
						if (this.WordGetFieldID(field) == fieldID) {
							return field;
						}
					}
				} else if (OV.isObject(fieldID) && !OV.isClear(fieldID.ShowCodes)) {
					return fieldID;
				} else if (OV.isClear(fieldID)) {
					for (var i = this.ActiveDocument.Fields.Count; i >= 1; i--) {
						var field = this.WordGetField(i);
						if (this.WordFieldSelected(field)) {
							return field;
						}
					}
				}
			}
		}
		/**
		 * 获取域数据
		 * @fieldIndex 域索引、域对象
		 * @dataIndex 数据索引，undefined全部数据
		 * @return 返回域数据，undefined表示获取失败
		 */
		function WordGetFieldData(fieldIndex, dataIndex){
			var field = this.WordGetField(fieldIndex);
			if (!OV.isClear(field) && (field.Type == OV.WdFieldType.wdFieldAddin)) {
				if (!OV.isClear(dataIndex)) {
					return field.Data.split('|')[dataIndex] || '';
				} else {
					return field.Data || '';
				}
			}
		}
		/**
		 * 获取域标识
		 * @fieldIndex 域索引、域对象
		 * @return 返回域标识，undefined表示获取失败
		 */
		function WordGetFieldID(fieldIndex){
			return this.WordGetFieldData(fieldIndex, 0);
		}
		/**
		 * 获取域名称
		 * @fieldIndex 域索引、域对象
		 * @return 返回域标识，undefined表示获取失败
		 */
		function WordGetFieldName(fieldIndex){
			return this.WordGetFieldData(fieldIndex, 1);
		}
		/**
		 * 获取域值
		 * @fieldIndex 域索引、域对象
		 * @return 返回域值，undefined表示获取失败
		 */
		function WordGetFieldValue(fieldIndex){
			return this.WordGetFieldData(fieldIndex, 2);
		}
		/**
		 * 获取域名范围对象
		 * @fieldID 域标识、域索引、域对象
		 * @type 域范围类型，参考WdRangeType
		 * @return 返回域范围对象，undefined表示获取失败
		 */
		function WordGetFieldRange(fieldID, type) {
			var field = this.WordGetField(fieldID);
			if (!OV.isClear(field) && (field.Type == OV.WdFieldType.wdFieldAddin)) {
				var fieldCode = '{' + field.Code + '}';
				var start = field.Result.End - fieldCode.length - 1;
				var end = field.Result.End;
				type = type || OV.WdRangeType.wdRangeAll;
				switch (type) {
					case OV.WdRangeType.wdRangeAll : return this.ActiveDocument.Range(start, end);
					case OV.WdRangeType.wdRangeStart : return this.ActiveDocument.Range(start, start);
					case OV.WdRangeType.wdRangeEnd : return this.ActiveDocument.Range(end, end);
					default : return this.ActiveDocument.Range(start, end);
				}
			}
		}
		/**
		 * 自动填充某个域的值
		 * @fieldID 域标识、域索引、域对象
		 * @json 填充值，可以是简单类型或JSON格式表述的特殊类型
		 * 1、填充字符串：
		 *		WordAutoFillField("Value1", "HELLO WORD!")
		 * 2、填充数字：
		 *		WordAutoFillField("Value2", 1024)
		 * 3、填充日期时间：
		 *		WordAutoFillField("Value3", '1979-10-24T19:00:00Z')
		 *		WordAutoFillField("Value3", '1979-10-24T')
		 *		WordAutoFillField("Value3", '19:00:00Z')
		 *		WordAutoFillField("Value3", '19:00Z')
		 * 4、填充图片：
		 *		WordAutoFillField("Value4", '{"Type": "Picture", "URL": "http://localhost:8080/ov/download/seal.bmp"}')
		 *		WordAutoFillField("Value4", '{"T": "P", "U": "http://localhost:8080/ov/download/seal.bmp"}')
		 *		WordAutoFillField("Value4", '{"Type": "Picture", "Local": "C:\\seal.bmp"}')
		 *		WordAutoFillField("Value4", '{"T": "P", "L": "C:\\seal.bmp"}')
		 * 5、填充文件内容：
		 *		WordAutoFillField("Value5", '{"Type": "File", "URL": "http://localhost:8080/ov/download/template.doc"}')
		 *		WordAutoFillField("Value5", '{"T": "F", "U": "http://localhost:8080/ov/download/template.doc"}')
		 *		WordAutoFillField("Value5", '{"Type": "File", "Local": "C:\\template.doc"}')
		 *		WordAutoFillField("Value5", '{"T": "F", "L": "C:\\template.doc"}')
		 * 6、填充分隔符：
		 *		分隔符参考WdBreakType，例如WdBreakType.wdPageBreak = 7
		 *		WordAutoFillField("Value6", '{"Type": "Break", "Value": 7}')
		 *		WordAutoFillField("Value6", '{"T": "B", "V": 7}')
		 * 7、填充域代码：
		 *		例如填充作者，对应的域代码为"INFO Author"
		 *		WordAutoFillField("Value7", '{"Type": "Code", "Value": "INFO Author"}')
		 *		WordAutoFillField("Value7", '{"T": "C", "V": "INFO Author"}')
		 * @return 返回是否打开了MSWord
		 */
		function WordAutoFillField(fieldID, json) {
			
			function localFile(source, data) {
				return (OV.isClear(data.URL || data.U)) ?
					(data.Local || data.L) : (source.HttpDownloadFileToTempDir((data.URL || data.U), '', '') || (data.URL || data.U));
			}
			function getType(data) {
				return (data.Type || data.T);
			}
			function getValue(data) {
				return (data.Value || data.V);
			}
			function isJSONDate(data) {
				var T = "^((((1[6-9]|[2-9]\\d)\\d{2})-(0?[13578]|1[02])-(0?[1-9]" +
					"|[12]\\d|3[01]))|(((1[6-9]|[2-9]\\d)\\d{2})-(0?[13456789]|1" +
					"[012])-(0?[1-9]|[12]\\d|30))|(((1[6-9]|[2-9]\\d)\\d{2})-0?2" +
					"-(0?[1-9]|1\\d|2[0-8]))|(((1[6-9]|[2-9]\\d)(0[48]|[2468][04" +
					"8]|[13579][26])|((16|[2468][048]|[3579][26])00))-0?2-29-))T";
				var Z = "(20|21|22|23|[0-1]?\\d)(:[0-5]?\\d){1,2}Z";
				return new RegExp(T + Z + '$').test(data) ||
					new RegExp(T + '$').test(data) || new RegExp(Z + '$').test(data);
			}
			function insertPicture(source, field, data) {
				var shape = source.ActiveDocument.InlineShapes.AddPicture(
					localFile(source, data), false, true, source.WordGetFieldRange(field, OV.WdRangeType.wdRangeStart));
				if (!OV.isClear(data.Width)) {
					shape.Width = data.Width;
				}
				if (!OV.isClear(data.Height)) {
					shape.Height = data.Height;
				}
			}
			function insertFile(source, field, data) {
				var range = source.WordGetFieldRange(field, OV.WdRangeType.wdRangeStart);
				if (!OV.isClear(range)) {
					range.InsertFile(localFile(source, data), "", false, false, false);
				}
			}
			function insertCode(source, field, data) {
				field.Code.Text = getValue(data);
				field.Update();
			}
			function insertBreak(source, field, data) {
				var range = source.WordGetFieldRange(field, OV.WdRangeType.wdRangeStart);
				if (!OV.isClear(range)) {
					range.InsertBreak(getValue(data));
				}
			}
			function insertSimple(source, field, data) {
				var value = source.WordGetFieldValue(field);
				data = isJSONDate(data) ? OV.trim(data.replace(/[T|Z]/g, ' ')) : data;
				if (value != data) {
					if (!OV.isClear(value) && (value.length > 0)) {
						var start = field.Result.End;
						var end = start + value.length;
						source.ActiveDocument.Range(start, end).Text = "";
					}
					field.Result.Text = data;
					source.WordUpdateField(field, source.WordGetFieldID(field), source.WordGetFieldName(field), data);
				}
			}
			
			if (this.CheckViewer() && this.IsWordOpened()) {
				var field = this.WordGetField(fieldID);
				if (!OV.isClear(field) && (field.Type == OV.WdFieldType.wdFieldAddin)) {
					var data = (OV.isString(json) && OV.isJSON(json)) ? OV.JSON.parse(json) : json;
					if (!OV.isClear(data)) {
						switch (getType(data)) {
							case "P" : case "Picture" : insertPicture(this, field, data); break;
							case "F" : case "File" : insertFile(this, field, data); break;
							case "C" : case "Code" : insertCode(this, field, data); break;
							case "B" : case "Break" : insertBreak(this, field, data); break;
							default : insertSimple(this, field, data);
						}
						if (!OV.isClear(data.URL || data.U)) {
							this.ClearTempFiles();
						}
					}
				}
				return true;
			} else {
				return false;
			}
		}
		/**
		 * 自动填充多个域的值
		 * @json JSON格式表述的域-值列表
		 * 1、简写格式：WordAutoFillFields(
		 * 		'{' +
		 * 		'"Value1": "HELLO WORD!", ' +
		 * 		'"Value2": 1024, ' +
		 * 		'"Value3": "1979-10-24T19:00:00Z", ' +
		 * 		'"Value4": {"T": "P", "U": "http://localhost:8080/ov/download/seal.bmp"}, ' +
		 * 		'"Value5": {"T": "F", "U": "http://localhost:8080/ov/download/template.doc"}, ' +
		 * 		'"Value6": {"T": "B", "V": 7}, ' +
		 * 		'"Value7": {"T": "C", "V": "INFO Author"}' +
		 * 		'}');
		 * 2、完整格式：WordAutoFillFields(
		 * 		'{' +
		 * 		'"Value1": "HELLO WORD!", ' +
		 * 		'"Value2": 1024, ' +
		 * 		'"Value3": "1979-10-24T19:00:00Z", ' +
		 * 		'"Value4": {"Type": "Picture", "URL": "http://localhost:8080/ov/download/seal.bmp"}, ' +
		 * 		'"Value5": {"Type": "File", "URL": "http://localhost:8080/ov/download/template.doc"}, ' +
		 * 		'"Value6": {"Type": "Break", "Value": 7}, ' +
		 * 		'"Value7": {"Type": "Code", "Value": "INFO Author"}' +
		 * 		'}');
		 * @return 返回是否打开了MSWord
		 */
		function WordAutoFillFields(json) {
			if (this.CheckViewer() && this.IsWordOpened()) {
				var data = (OV.isString(json) && OV.isJSON(json)) ? OV.JSON.parse(json) : json;
				if (!OV.isClear(data)) {
					for (var i = 1; i <= this.ActiveDocument.Fields.Count; i++) {
						var field = this.WordGetField(i);
						if (field.Type == OV.WdFieldType.wdFieldAddin) {
							var fieldID = this.WordGetFieldID(field);
							this.WordAutoFillField(field, data[fieldID]);
						}
					}
					/*添加页眉页脚*/
					for (var i = 1; i <= this.Application.ActiveDocument.Sections.Count; i++){
						var Section =  this.Application.ActiveDocument.Sections(i);
						for(var j = 1; j <= Section.Headers.Count;j++){
							var head = this.Application.ActiveDocument.Sections(i).Headers(j);
							for(var m = 1; m <= head.Range.Fields.Count; m++){
								var field = this.WordGetHeadField(i,j,m);
								if (field.Type == OV.WdFieldType.wdFieldAddin) {
									var fieldID = this.WordGetHeadFieldID(Section,head,field);
									this.WordAutoFillHeadField(i,j,field, data[fieldID]);
								}
							}							
						}
						for(var j = 1; j <= Section.Footers.Count;j++){
							var foot = this.Application.ActiveDocument.Sections(i).Footers(j);
							for(var n = 1; n <= foot.Range.Fields.Count; n++){
								var field = this.WordGetHeadField(i,j,n,true);
								if (field.Type == OV.WdFieldType.wdFieldAddin) {
									var fieldID = this.WordGetHeadFieldID(Section,head,field ,true);
									this.WordAutoFillHeadField(i,j,field, data[fieldID],true);
								}
							}	
						}
					}
															
				}
				return true;
			} else {
				return false;
			}
		}
		
		/*页眉页脚扩展*/
		function WordGetHeadField(Section,head,fieldID,isFoot) {
			if (this.CheckViewer() && this.IsWordOpened()) {
				if (OV.isNumber(fieldID)) {
					return isFoot ? this.ActiveDocument.Sections(Section).Footers(head).Range.Fields(fieldID)
							: this.ActiveDocument.Sections(Section).Headers(head).Range.Fields(fieldID);
					
				} else if (OV.isString(fieldID)) {
					for (var i = 1; i <= this.ActiveDocument.Fields.Count; i++) {
						var field = this.WordGetField(i);
						if (this.WordGetFieldID(field) == fieldID) {
							return field;
						}
					}
				} else if (OV.isObject(fieldID) && !OV.isClear(fieldID.ShowCodes)) {
					return fieldID;
				} else if (OV.isClear(fieldID)) {
					for (var i = this.ActiveDocument.Fields.Count; i >= 1; i--) {
						var field = this.WordGetField(i);
						if (this.WordFieldSelected(field)) {
							return field;
						}
					}
				}
			}
		}
		
		function WordGetHeadFieldData(Section,head,fieldIndex, dataIndex ,isFoot){
			var field = this.WordGetHeadField(Section,head,fieldIndex ,isFoot);
			if (!OV.isClear(field) && (field.Type == OV.WdFieldType.wdFieldAddin)) {
				if (!OV.isClear(dataIndex)) {
					return field.Data.split('|')[dataIndex] || '';
				} else {
					return field.Data || '';
				}
			}
		}
		
		function WordGetHeadFieldID(Section,head,fieldIndex ,isFoot){
			return this.WordGetHeadFieldData(Section,head,fieldIndex, 0);
		}
		
		function WordGetHeadFieldName(Section,head,fieldIndex ,isFoot){
			return this.WordGetHeadFieldData(Section,head,fieldIndex, 1 ,isFoot);
		}
		
		function WordGetHeadFieldValue(Section,head,fieldIndex,isFoot){
			return this.WordGetHeadFieldData(Section,head,fieldIndex, 2 ,isFoot);
		}
		
		
		function WordAutoFillHeadField(Section,head,fieldID, json ,isFoot) {
			
			function localFile(source, data) {
				return (OV.isClear(data.URL || data.U)) ?
					(data.Local || data.L) : (source.HttpDownloadFileToTempDir((data.URL || data.U), '', '') || (data.URL || data.U));
			}
			function getType(data) {
				return (data.Type || data.T);
			}
			function getValue(data) {
				return (data.Value || data.V);
			}
			function isJSONDate(data) {
				var T = "^((((1[6-9]|[2-9]\\d)\\d{2})-(0?[13578]|1[02])-(0?[1-9]" +
					"|[12]\\d|3[01]))|(((1[6-9]|[2-9]\\d)\\d{2})-(0?[13456789]|1" +
					"[012])-(0?[1-9]|[12]\\d|30))|(((1[6-9]|[2-9]\\d)\\d{2})-0?2" +
					"-(0?[1-9]|1\\d|2[0-8]))|(((1[6-9]|[2-9]\\d)(0[48]|[2468][04" +
					"8]|[13579][26])|((16|[2468][048]|[3579][26])00))-0?2-29-))T";
				var Z = "(20|21|22|23|[0-1]?\\d)(:[0-5]?\\d){1,2}Z";
				return new RegExp(T + Z + '$').test(data) ||
					new RegExp(T + '$').test(data) || new RegExp(Z + '$').test(data);
			}
			function insertPicture(source, field, data) {
				var shape = source.ActiveDocument.InlineShapes.AddPicture(
					localFile(source, data), false, true, source.WordGetFieldRange(field, OV.WdRangeType.wdRangeStart));
				if (!OV.isClear(data.Width)) {
					shape.Width = data.Width;
				}
				if (!OV.isClear(data.Height)) {
					shape.Height = data.Height;
				}
			}
			function insertFile(source, field, data) {
				var range = source.WordGetFieldRange(field, OV.WdRangeType.wdRangeStart);
				if (!OV.isClear(range)) {
					range.InsertFile(localFile(source, data), "", false, false, false);
				}
			}
			function insertCode(source, field, data) {
				field.Code.Text = getValue(data);
				field.Update();
			}
			function insertBreak(source, field, data) {
				var range = source.WordGetFieldRange(field, OV.WdRangeType.wdRangeStart);
				if (!OV.isClear(range)) {
					range.InsertBreak(getValue(data));
				}
			}
			function insertSimple(source, field, data ,isFoot) {
				var value = source.WordGetHeadFieldValue(Section,head,field,isFoot);
				data = isJSONDate(data) ? OV.trim(data.replace(/[T|Z]/g, ' ')) : data;
				if (value != data) {
					if (!OV.isClear(value) && (value.length > 0)) {
						var start = field.Result.End;
						var end = start + value.length;
						if(isFoot)
							source.Application.ActiveDocument.Sections(Section).Footers(head).Range(start, end).Text = "";
						else
							source.Application.ActiveDocument.Sections(Section).Headers(head).Range(start, end).Text = "";
					}
					field.Result.Text = data;
					source.WordUpdateField(field, source.WordGetFieldID(field), source.WordGetFieldName(field), data);
				}
			}
			
			if (this.CheckViewer() && this.IsWordOpened()) {
				var field = this.WordGetHeadField(Section,head,fieldID ,isFoot);
				if (!OV.isClear(field) && (field.Type == OV.WdFieldType.wdFieldAddin)) {
					var data = (OV.isString(json) && OV.isJSON(json)) ? OV.JSON.parse(json) : json;
					if (!OV.isClear(data)) {
						switch (getType(data)) {
							case "P" : case "Picture" : insertPicture(this, field, data); break;
							case "F" : case "File" : insertFile(this, field, data); break;
							case "C" : case "Code" : insertCode(this, field, data); break;
							case "B" : case "Break" : insertBreak(this, field, data); break;
							default : insertSimple(this, field, data ,isFoot);
						}
						if (!OV.isClear(data.URL || data.U)) {
							this.ClearTempFiles();
						}
					}
				}
				return true;
			} else {
				return false;
			}
		}
		
		/*页眉页脚扩展 end */
		
		/**
		 * 删除当前选择区域中的域定义，只删除wdFieldAddin类型的域
		 * @fieldID 域标识或域标识数组，为undefined或null时删除当前选中的域
		 * @return 返回是否打开了MSWord
		 */
		function WordDeleteFields(fieldID) {
			if (this.CheckViewer() && this.IsWordOpened()) {
				if (OV.isClear(fieldID)) {
					for (var i = this.ActiveDocument.Fields.Count; i >= 1; i--) {
						var field = this.WordGetField(i);
						if ((field.Type == OV.WdFieldType.wdFieldAddin) && this.WordFieldSelected(field)) {
							field.Delete();
						}
					}
				} else if (OV.isArray(fieldID)) {
					for (var i in fieldID) {
						this.WordDeleteFields(fieldID[i]);
					}
				} else {
					var field = this.WordGetField(fieldID);
					if (!OV.isClear(field) && (field.Type == OV.WdFieldType.wdFieldAddin)) {
						field.Delete();
					}
				}
				return true;
			} else {
				return false;
			}
		}
		/**
		 * 删除所有域定义，只删除wdFieldAddin类型的域
		 * @return 返回是否打开了MSWord
		 */
		function WordDeleteAllFields() {
			if (this.CheckViewer() && this.IsWordOpened()) {
				for (var i = this.ActiveDocument.Fields.Count; i >= 1; i--) {
					var field = this.WordGetField(i);
					if (field.Type == OV.WdFieldType.wdFieldAddin) {
						field.Delete();
					}
				}
				return true;
			} else {
				return false;
			}
		}
		/**
		 * 获取JSON格式域信息
		 * @return 返回JSON格式域信息，undefined表示获取失败
		 */
		function WordGetFieldJSON() {
			if (this.CheckViewer() && this.IsWordOpened()) {
				var result = [];
				var index = 0;
				for (var i = 1; i <= this.ActiveDocument.Fields.Count; i++) {
					var field = this.WordGetField(i);
					if (!OV.isClear(field) && (field.Type == OV.WdFieldType.wdFieldAddin)) {
						result[index] = {
							Index: field.Index,
							FieldID: this.WordGetFieldID(field),
							FieldName: this.WordGetFieldName(field),
							Result: field.Result.Text
						};
						index++;
					}
				}
				return OV.JSON.stringify(result);
			}
		}
		/**
		 * 获取域信息
		 * @json JSON格式域信息，如果为undefined则获取当前打开MSWORD文档的域信息
		 * @template 用于格式化域信息，为undefined则使用默认模板，模板参数：
		 * 		#{Index}		索引序号
		 * 		#{FieldID}		域标识
		 * 		#{FieldName}	域名称
		 * 		#{Result}		返回结果
		 * 		#{FullResult}	完整返回结果
		 * 		#{HTMLResult}	HTML返回结果
		 * @textLength 简化返回结果的长度，默认30字符
		 * @truncation 简化返回结果的后缀，默认为...
		 * @return 返回域信息，undefined表示获取失败
		 */
		function WordGetFieldInfo(json, template, textLength, truncation) {
			var fields = OV.tryThese(this, function(source){
				return OV.isClear(json) ?
					OV.JSON.parse(source.WordGetFieldJSON()) :
					(OV.isString(json) && OV.isJSON(json)) ? 
						OV.JSON.parse(json) :
						json;
			}, function(source){
				return json;
			});
			if (OV.isArray(fields)) {
				template = OV.isClear(template) ?
					'<fieldinfo index="{Index}" fieldID="{FieldID}" fieldName="{FieldName}">{Result}</fieldinfo>\r\n' :
					template;
				var result = "";
				for (var i = 0, length = fields.length; i < length; i++) {
					var field = fields[i];
					result += OV.substitute(template, {
						Index: field.Index,
						FieldID: field.FieldID,
						FieldName: field.FieldName,
						Result: OV.truncate(field.Result.replace(/\s+/g, ' '), textLength, truncation),
						FullResult: field.Result,
						HTMLResult: OV.escape(field.Result)});
				}
				return result;
			} else {
				return fields;
			}
		}
		/**
		 * 插入图片，如果left和top参数均为undefined则已嵌入的方式插入图片，否则已浮动方式插入
		 * @url 图片地址
		 * @left 图片的左起始位置，当top有值时left默认为0
		 * @top 图片的上起始位置，当left有值时top默认为0
		 * @trans 是否透明处理，默认为false
		 * @trans 是否透明处理，默认为白色
		 * @return 返回是否打开了MSWord
		 */
		function WordInsertWebPicture(url, left, top, trans, transColor) {
			if (this.CheckViewer() && this.IsWordOpened()) {
				var localFileName = this.HttpDownloadFileToTempDir(url, '', '') || url;
				var inLine = OV.isClear(left) && OV.isClear(top);
				var shape = inLine ?
					this.ActiveDocument.InlineShapes.AddPicture(
						localFileName, false, true, this.Application.Selection.Range) :
					this.ActiveDocument.Shapes.AddPicture(
						localFileName, false, true, this.ActiveDocument.Range(0, 0));
				if (!inLine) {
					shape.WrapFormat.Type = OV.WdWrapType.wdWrapNone;
					shape.ZOrder(OV.MsoZOrderCmd.msoBringInFrontOfText);
					shape.Left = left || 0;
					shape.Top = top || 0;
					shape.PictureFormat.TransparentBackground = trans || false;
					shape.PictureFormat.TransparencyColor = transColor || 0xFFFFFF;
				}
				this.ClearTempFiles();
				return true;
			} else {
				return false;
			}
		}
		/**
		 * 插入文件内容
		 * @url 文件地址
		 * @range 用于描述文件内容范围，默认为""
		 * 		当指定的文档是MSWord文档时，此参数代表书签；
		 * 		当该文档为MSExcel时，此参数代表已命名的区域或单元格区域（例如 R1C1:R3C4）
		 * @confirm 如果值为True，则MSWord将在插入非MSWord文档格式的文档时提示确认转换
		 * @link 如果值为True，则可用INCLUDETEXT域插入该文档
		 * @attachment 为True时将该文件作为附件插入电子邮件消息中
		 * @return 返回是否打开了MSWord
		 */
		function WordInsertWebFile(url, range, confirm, link, attachment) {
			if (this.CheckViewer() && this.IsWordOpened()) {
				var localFileName = this.HttpDownloadFileToTempDir(url, '', '') || url;
				range = OV.isClear(range) ? "" : range;
				confirm = confirm || false;
				link = link || false;
				attachment = attachment || false;
				this.Application.Selection.InsertFile(localFileName, range, confirm, link, attachment);
				this.ClearTempFiles();
				return true;
			} else {
				return false;
			}
		}
		/**
		 * 初始化MSWord的痕迹保留，主要用于保留MSWord修订历史，具体操作如下：
		 * 		接受当前文件的所有修订内容
		 * 		不显示修订痕迹
		 * 		启动痕迹保留
		 * @acceptAllRevisions 是否接受所有修订
		 * @userName 用户名，为undefined则保留原来的用户名
		 * @userInitials 简称，为undefined则保留原来的简称
		 * @return 返回是否打开了MSWord
		 */
		function WordRevisionInit(acceptAllRevisions, userName, userInitials) {
			if (this.CheckViewer() && this.IsWordOpened()) {
				if (acceptAllRevisions) {
					this.ActiveDocument.AcceptAllRevisions();
				}
				this.ActiveDocument.ShowRevisions = false;
				this.ActiveDocument.TrackRevisions = true;
				if (!OV.isClear(userName)) {
					this.Application.UserName = userName;
				}
				if (!OV.isClear(userInitials)) {
					this.Application.UserInitials = userInitials;
				}
				return true;
			} else {
				return false;
			}
		}
		/**
		 * 复位MSWord的痕迹保留状态，主要用于查看MSWord修订历史，具体操作如下：
		 * 		关闭痕迹保留
		 * 		显示修订痕迹
		 * @return 返回是否打开了MSWord
		 */
		function WordRevisionNone() {
			if (this.CheckViewer() && this.IsWordOpened()) {
				this.ActiveDocument.TrackRevisions = false;
				this.ActiveDocument.ShowRevisions = true;
				return true;
			} else {
				return false;
			}
		}
		/**
		 * 获取MSWord痕迹保留的JSON格式修订记录
		 * @return 返回JSON格式修订记录，undefined表示获取失败
		 */
		function WordGetRevisionJSON() {
			if (this.CheckViewer() && this.IsWordOpened()) {
				var result = OV.tryThese(this, function(source){
					var r = [];
					for (var i = 1; i <= source.ActiveDocument.Revisions.Count; i++) {
						var revision = source.ActiveDocument.Revisions.Item(i);
						r[i-1] = {
							Author: revision.Author,
							Date: new Date(revision.Date),
							Index: revision.Index,
							Text: revision.Range.Text,
							Type: revision.Type
						};
					}
					return r;
				}, function(source){
					return [];
				});
				return OV.JSON.stringify(result);
			}
		}
		/**
		 * 获取MSWord痕迹保留的修订记录
		 * @json JSON格式修订记录，如果为undefined则获取当前打开MSWORD文档的修订记录
		 * @template 用于格式化修订内容的模板，为undefined则使用默认模板，模板参数：
		 * 		#{Index}	索引序号
		 * 		#{Author}	修订此内容的作者
		 * 		#{Type}		修订类型，参考WordRevisionTypeToStr方法
		 * 		#{Date}		修订时间
		 * 		#{Text}		简化修订内容，默认只有30字符并且不包含多余空行和换行符等
		 * 		#{FullText}	完整修订内容
		 * 		#{HTMLText}	HTML修订内容
		 * @textLength 简化修订内容的长度，默认30字符
		 * @truncation 简化修订内容的后缀，默认为...
		 * @return 返回修订记录，undefined表示获取失败
		 */
		function WordGetRevisionInfo(json, template, textLength, truncation) {
			var revisions = OV.tryThese(this, function(source){
				return OV.isClear(json) ?
					OV.JSON.parse(source.WordGetRevisionJSON()) :
					(OV.isString(json) && OV.isJSON(json)) ?
						OV.JSON.parse(json) :
						json;
			}, function(source){
				return json;
			});
			if (OV.isArray(revisions)) {
				template = OV.isClear(template) ?
					'<revisioninfo index="{Index}" author="{Author}" type="{Type}" date="{Date}">{Text}</revisioninfo>\r\n' :
					template;
				var result = "";
				for (var i = 0, length = revisions.length; i < length; i++) {
					var revision = revisions[i];
					result += OV.substitute(template, {
						Author: revision.Author,
						Date: OV.trim(revision.Date.replace(/\"|[T|Z]/g, ' ')),
						Index: revision.Index,
						Text: OV.truncate(revision.Text.replace(/\s+/g, ' '), textLength, truncation),
						FullText: revision.Text,
						HTMLText: OV.escape(revision.Text),
						Type: this.WordRevisionTypeToStr(revision.Type)});
				}
				return result;
			} else {
				return revisions;
			}
		}
		/**
		 * 转换痕迹保留的修订类型
		 * @type 修订类型，整数0~18，参考WdRevisionType
		 * @return 返回修订类型的文字描述，undefined表示转换失败
		 */
		function WordRevisionTypeToStr(type) {
			return ['None', 'Insert', 'Delete', 'Property', 'ParagraphNumber',
					'DisplayField', 'Reconcile', 'Conflict', 'Style', 'Replace',
					'ParagraphProperty', 'TableProperty', 'SectionProperty',
					'StyleDefinition', 'MovedFrom', 'MovedTo', 'CellInsertion',
					'CellDeletion', 'CellMerge'][type];
		}
		/**
		 * 控件初始化处理过程
		 */
		function InitOfficeViewer(){
			this.ActivationPolicy = 4;
			this.FrameHookPolicy = 1;
		}
		/**
		 * 禁止工具栏按钮
		 */
		function DisableOfficeButton(isPrint){
			if (this.CheckViewer() && this.IsOpened) {					
				switch (this.GetOpenedFileType()) {
					case OV.OpenedFileType.oaUnknown :
					case OV.OpenedFileType.oaNoOpened :
						break;
					case OV.OpenedFileType.oaWord :
					case OV.OpenedFileType.oaExcel :
					case OV.OpenedFileType.oaPowerPoint :
						this.ActiveDocument.CommandBars("Standard").Controls(1).Visible = false;
						this.ActiveDocument.CommandBars("Standard").Controls(2).Visible = false;
						this.ActiveDocument.CommandBars("Standard").Controls(3).Visible = false;
						this.ActiveDocument.CommandBars("Standard").Controls(4).Visible = false;
						this.ActiveDocument.CommandBars("Standard").Controls(5).Visible = false;
						break;
					case OV.OpenedFileType.oaVisio :
						break;
					case OV.OpenedFileType.oaProject :
						break;
					default : break;
				}
				return true;
			} else {
				return false;
			}
		}
		
		function DisableFileCommand(isPrint){
			if (this.CheckViewer()) {
				this.EnableFileCommand(0) = true;
				this.EnableFileCommand(1) = true;
				this.EnableFileCommand(2) = true;
				this.EnableFileCommand(3) = true;
				this.EnableFileCommand(4) = true;
				if(!OV.isClear(isPrint)){
					this.EnableFileCommand(5) = isPrint;
					this.EnableFileCommand(8) = isPrint;
				}
			}	
		}
		
		function DisableOfficeReviewingBar(isShow){
			if (this.CheckViewer() && this.IsWordOpened()) {
				//显示或隐藏批注框
				this.ActiveDocument.ActiveWindow.View.ShowComments=isShow;
				//显示或隐藏插入和删除标记批注框
				this.ActiveDocument.ActiveWindow.View.ShowInsertionsAndDeletions=isShow;
				//显示或隐藏墨迹注释
				this.ActiveDocument.ActiveWindow.View.ShowInkAnnotations=isShow;
				//正在格式化
				this.ActiveDocument.ActiveWindow.View.ShowFormatChanges=isShow;
			}else{
				return false;
			}
		}
		/**
		 * 以下方法用于测试
		 */
		function test1(value) {
		}
		function test2(value) {
		}
		function test3(value) {
		}
		return {
			Assert:					Assert,
			OfficeViewerClassID:	OfficeViewerClassID,
			IsOfficeViewer:			IsOfficeViewer,
			CreateOfficeViewer:		CreateOfficeViewer,
			DestroyOfficeViewer:	DestroyOfficeViewer,
			OfficeViewerEval:		OfficeViewerEval,
			CheckViewer:			CheckViewer,
			CheckInstalled:			CheckInstalled,
			CreateDoc:				CreateDoc,
			CreateWord:				CreateWord,
			CreateExcel:			CreateExcel,
			CreatePowerPoint:		CreatePowerPoint,
			CreateVisio:			CreateVisio,
			CreateProject:			CreateProject,
			IsDocOpened:			IsDocOpened,
			IsWordOpened:			IsWordOpened,
			IsExcelOpened:			IsExcelOpened,
			IsPowerPointOpened:		IsPowerPointOpened,
			IsVisioOpened:			IsVisioOpened,
			IsProjectOpened:		IsProjectOpened,
			WordGetStatistic:		WordGetStatistic,
			WordToggleShowCodes:	WordToggleShowCodes,
			WordFieldSelected:		WordFieldSelected,
			WordFieldSelect:		WordFieldSelect,
			WordUpdateField:		WordUpdateField,
			WordInsertField:		WordInsertField,
			WordGetNewFieldID:		WordGetNewFieldID,
			WordGetField:			WordGetField,
			WordGetFieldData:		WordGetFieldData, 
			WordGetFieldID:			WordGetFieldID,
			WordGetFieldName:		WordGetFieldName,
			WordGetFieldValue:		WordGetFieldValue,
			WordGetFieldRange:		WordGetFieldRange,
			WordAutoFillField:		WordAutoFillField,
			WordAutoFillFields:		WordAutoFillFields,
			WordDeleteFields:		WordDeleteFields,
			WordDeleteAllFields:	WordDeleteAllFields,
			WordGetFieldJSON:		WordGetFieldJSON,
			WordGetFieldInfo:		WordGetFieldInfo,
			WordInsertWebPicture:	WordInsertWebPicture,
			WordInsertWebFile:		WordInsertWebFile,
			WordRevisionInit:		WordRevisionInit,
			WordRevisionNone:		WordRevisionNone,
			WordGetRevisionJSON:	WordGetRevisionJSON,
			WordGetRevisionInfo:	WordGetRevisionInfo,
			WordRevisionTypeToStr:	WordRevisionTypeToStr,
			InitOfficeViewer:		InitOfficeViewer,
			DisableOfficeButton:	DisableOfficeButton,
			DisableFileCommand:		DisableFileCommand,
			DisableOfficeReviewingBar:  DisableOfficeReviewingBar,
			WordGetHeadField :		WordGetHeadField,
			WordAutoFillHeadField :	WordAutoFillHeadField,
			WordGetHeadFieldData : WordGetHeadFieldData,
			WordGetHeadFieldName : WordGetHeadFieldName,
			WordGetHeadFieldID : WordGetHeadFieldID,
			WordGetHeadFieldValue : WordGetHeadFieldValue,
			test1:					test1,
			test2:					test2,
			test3:					test3
		};
	})();
	/**
	 * 历史修订模板
	 * OV.RevisionTemplate.defaultHTMLContent 默认的HTML格式修订存储模板
	 */
	var RevisionTemplate = (function() {
		var defaultHTMLContent = (function() {
			var fn = function() {
				/*
				<div class='revision_item'>
					<div class='revision_title'>
						<div class='revision_left'>{Index}.{Type}</div>
						<div class='revision_center'>{Author}</div>
						<div class='revision_right'>{Date}</div>
					</div>
					<div class='revision_content'>{Text}</div>
					<div class='revision_content_full'>{HTMLText}</div>
				</div>
				*/
			};
			return getMultiLine(fn);
		})();
		return {
			defaultHTMLContent:	defaultHTMLContent
		};
	})();
	return {
		JSON:	this.JSON || {},
		Base64:	this.Base64 || {},
		
		ProgramID:			ProgramID,
		OpenedFileType:		OpenedFileType,
		WdRangeType:		WdRangeType,
		WdStatistic:		WdStatistic,
		WdBreakType:		WdBreakType,
		WdSelectionType:	WdSelectionType,
		WdFieldType:		WdFieldType,
		WdWrapType:			WdWrapType,
		MsoZOrderCmd:		MsoZOrderCmd,
		
		extend:					extend,
		getMultiLine:			getMultiLine,
		trim:					trim,
		substitute:				substitute,
		truncate:				truncate,
		tryThese:				tryThese,
		escape:					escape,
		getClass:				getClass,
		isArray:				isArray,
		isString:				isString,
		isNumber:				isNumber,
		isFunction:				isFunction,
		isObject:				isObject,
		isUndefined:			isUndefined,
		isNull:					isNull,
		isClear:				isClear,
		isJSON:					isJSON,
		getElementByID:			getElementByID,
		getElementsByClassName:	getElementsByClassName,
		addEvent:				addEvent,
		
		OfficeViewer:		OfficeViewer,
		RevisionTemplate:	RevisionTemplate
	};
})();