var $ajax = function(k) {
	var url = "";
	var async = true;
	var dataType = "";
	var type = "POST";//post类型出错，不知道为什么
	var backType = "json";
	var fun;
	var backData;
	var isNeedStoreData = k;//是否需要存储数据
	var hasBackFun = false;//是否指定回调函数
	this.getBackData = function() {
		return backData;
	};
	/*
	 * 设置回调函数
	 * @param {Object} funLocal
	 */
	this.setFun = function(funLocal) {
		if (typeof (funLocal) == "function") {
			fun = funLocal;
			hasBackFun = true;
		}
	}
	/*
	 * 
	 * @param {Object} url 相对应用的地址
	 */
	this.setURL = function(urlLocal) {
		url = psnCommon.changeUrl(urlLocal);
	}
	/*
	 * @descripton 发送ajax请求
	 * @return {TypeName} 
	 * @exception {TypeName} 
	 */
	this.send = function(asyncP, typeP, backTypeP, dataP) {
		var asyncL = (typeof (asyncP) == "boolean") ? asyncP : async;
		var typeL = (typeof (typeP) == "string") ? typeP : type;
		var backTypeL = (typeof (backTypeP) == "string") ? backTypeP : backType;
		var dataL = (typeof (dataP) == "string") ? dataP : "";
		if (hasBackFun && typeof (fun) != "function") {
			alert("请正确指定回调函数!");
			return;
		}
		$.ajax( {
			async : asyncL,
			url : url,
			type : typeL,
			dataType : backTypeL,
			data : dataL,//这个属性一定要指定，不然会报不支持post或者get方法的错误
			beforeSend : function() {
				if (url == null || url == "") {
					this.result = "请求的url为空！";
					return false;
				}
				return true;
			},
			success : function(data) {
				var k = data;
				if (typeof (fun) == "function") {
					if (backType == "json") {
						try {
							if (typeof (data) == "string") {
								k = eval("(" + data + ")");
							} else if (typeof (data) == "object"
									&& data.length > 0
									&& data[0].status == "SUCCESS") {
								k = eval("(" + data[0].data + ")");
							} else {
								k = null;
							}
						} catch (e) {
							//alert(e);
						}
					}
					if (isNeedStoreData == true) {
						backData = k;
					}
					fun(k);
				}
				if (isNeedStoreData) {
					backData = k;
				}
			},
			error : function(data) {
				if (typeof (fun) == "function")
					fun( {
						"error" : data.responseText
					});
			}
		});
	}
};
var psnCommon = {
	changeUrl : function(localUrl) {
		if (typeof (localUrl) != "string")
			return;
		if (localUrl == "")
			return;
		if (localUrl.indexOf("http://") != -1 || localUrl.indexOf("www.") != -1) {
			return url;
		}
		var loc = document.location;
		var host = loc.host;
		var basepath = loc.pathname.substring(loc.pathname.indexOf('/') + 1);
		basepath = basepath.substring(0, basepath.indexOf('/'));
		var url;
		if (localUrl.indexOf(basepath) != -1) {
			url = "http://" + host + localUrl;
		} else {
			url = "http://" + host + "/" + basepath + localUrl;
		}
		return url
	},
	getUrlParam : function(name) {
		var lurl = window.location.href;
		if (lurl.indexOf(name) < 0)
			return;
		if (lurl.indexOf("?") < 0) {
			return "";
		} else {
			var p;
			var k = lurl.indexOf("?" + name + "=");
			if (k != -1) {
				p = lurl.substring(k, lurl.length);
				if (p.indexOf("&") != -1) {
					return p.substring(name.length + 2, p.indexOf("&"));
				} else {
					return p.substring(name.length + 2, p.length);
				}
			}
			k = lurl.indexOf("&" + name + "=");
			if (k != -1) {
				p = lurl.substring(k + 1, lurl.length);
				if (p.indexOf("&") != -1) {
					return p.substring(name.length + 1, p.indexOf("&"));
				} else {
					return p.substring(name.length + 1, p.length);
				}
			}
		}
	},
	checkIsHasParam : function(name) {
		var lurl = window.location.href;
		if (lurl.indexOf("?") < 0) {
			return false;
		}
		if (lurl.indexOf(name) < 0) {
			return false;
		}
		return true;
	}
};
var ItemBox = function(obj, width, height) {
	var _div;
	var _obj = obj;
	var _divWidth = width;
	var _divheight = height;
	var _h = false;
	var _i = false;
	function creatDiv() {
		_div = $("div[id='" + obj.id + "']");
		var pos = $(obj).offset();
		if (_div.length == 0) {
			_div = $(
					"<div onmouseover=\"this.style.display='block'\" onmouseout='this.style.display=\"none\"' class=\"attach_div small\" id=\""
							+ obj.id
							+ "\"style=\"display: block;left:"
							+ pos.left
							+ ";top:"
							+ (pos.top + obj.offsetHeight)
							+ ";\"></div>").appendTo($("body"));
		} else {
			_div.css("display", "block");
		}
	}
	this.addItem = function(arr) {
		var html = "";
		if (_h)
			return;
		for ( var i = 0; i < arr.length; i++) {
			html += "<a onclick=\"" + arr[i].onclick
					+ ";\" href=\"#\"><span class=\"" + arr[i].className
					+ "\">" + arr[i].text + "</span></a>";
		}
		$(html).appendTo(_div);
		_h = true;
	}
	this.addImagItem = function(arr) {
		var html = "";
		if (_i)
			return;
		for ( var i = 0; i < arr.length; i++) {
			html += "<a title=\"" + arr[i].title + "\" href=\"javascript:"
					+ arr[i].onclick + ";\"" + " class=\"" + arr[i].className
					+ "\"><img align=\"absMiddle\"" + "src=\"" + arr[i].imge
					+ "\">" + arr[i].text + "</a>"
		}
		$(html).appendTo(_div);
		_i = true;
	}
	this.showBox = function() {
		var display = _div.css("display");
		if (display == "none") {
			_div.css("display", "block");
		}
	}
	creatDiv();
}
var ListTable = function(headArray, tablePos, p, k) {
	var _tableWidth = "100%";//头的
	var _divId = tablePos;//list所在的div
	var _listBodyId = p;//"listcontainer";//body所在的布局
	var _listContainerId = k;//body在布局中的位置
	var _data;//数据json
	var _updata = [];//更新数据集
	var _headSet = headArray;
	var _filter;//data过滤
	this.setValue = function(rowID, relations, values) {
		var arrRelation = eval(relations);
		var arrValue = eval(values);
		var w = {};
		eval("w.SID='" + rowID + "'");
		var k = "";
		for ( var i = 0; i < arrRelation.length; i++) {
			k += arrRelation[i] + "='" + arrValue[i] + "',";
		}
		if (k != "") {
			k = k.substring(0, k.length - 1);
		}
		eval("w.text=\"" + k + "\"");
		_updata[_updata.length] = w;
	}
	this.saveData = function(saveAction, isNeedDelete) {
		var text = JSON.stringify(_updata);
		var k = new $ajax();
		var backFun = function(dataJson) {
			if (dataJson == "perfect") {
				var listBody = $("#_skyWindTableList_>tbody");
				if (isNeedDelete) {
					for ( var i = 0; i < _updata.length; i++) {
						listBody.children("tr[rowid='" + _updata[i].SID + "']")
								.replaceWith("");
					}
				}
			}
			_updata = [];
		}
		k.setFun(backFun);
		k.setURL(saveAction);
		k.send(null, null, null, "updateStr=" + text);
	}
	this.deleteRowsByIDs = function(rowIds, deleteAction) {
		if (!rowIds)
			return;
		var k = new $ajax();
		var backFun = function(dataJson) {
			if (dataJson == "perfect") {
				var listBody = $("#_skyWindTableList_>tbody");
				var arrRow = rowIds.split(";");
				for ( var j = 0; j < arrRow.length; j++) {
					listBody.children("tr[rowid='" + arrRow[j] + "']")
							.replaceWith("");
				}
			}
		}
		k.setFun(backFun);
		k.setURL(deleteAction);
		k.send(null, null, null, "delete=" + rowIds);
	}
	this.getCheckedRowIDs = function(ckName) {
		var rows = "";
		var k = $("#_skyWindTableList_>tbody>tr>td>input[name='" + ckName
				+ "'][checked='true']");
		for ( var i = 0; i < k.length; i++) {
			rows += k[i].parentNode.parentNode.rowid + ";";
		}
		if (rows.length > 0) {
			rows = rows.substring(0, rows.length - 1);
		}
		return rows;
	}
	this.setFilter = null;
	this.afterLoad = null;
	this.getBindData = function() {
		return _data;
	}
	this.getActiveRowIndex = function() {
		return $("#_skyWindTableList_>tbody>.active").attr("index");
	}
	this.onRowSelected = null;
	var _onRowSelected = null;
	ListTable.prototype.setActiveRow = function(index) {
		$("#_skyWindTableList_>tbody>tr").removeClass("active");
		$("#_skyWindTableList_>tbody>tr[index='" + index + "']").addClass(
				"active");
		if (typeof (this.onRowSelected) != "undefined") {
			_onRowSelected = this.onRowSelected;
		}
		if (typeof (_onRowSelected) == "function") {
			_onRowSelected();
		}
	}
	function order_by(relation) {

	}
	function _getheadHtml(clomune) {
		var type = clomune.hType;
		var html = "";
		switch (type) {
		case "checkbox":
			html = "<input type=\"checkbox\"  id=\"allbox_skyWind_for\" name=\"allbox\">";
			break;
		case "img":
			html = "<u><img src=\"" + clomune.src + "\"> </u>";
			break;
		case "label":
			html = "<u>" + clomune.value + "</u>";
			break;
		default:
			html = "";
		}
		return "<td width=\""
				+ clomune.width
				+ "\" nowrap=\"\" style=\"cursor: hand;\"onclick=\"order_by('"
				+ clomune.ref
				+ "','1');\" class=\"center BlockTopTdline\">"
				+ html
				+ "<img style='display:none;'width=\"11\" height=\"10\" border=\"0\" src=\"/tlv8/system/personal/EMail/imags/bottonImags/arrow_up.gif\"></td>";
	}
	/*
	 * 得到td数据
	 * @param {Object} row
	 * @param {Object} clomune
	 * @return {TypeName} 
	 */
	function _getBodyData(row, clomune) {
		var type = clomune.type;
		var ref = clomune.ref;
		var html;
		switch (type) {
		case "checkbox":
			html = "<td align=\"center\" width=\"" + clomune.width
					+ "\" nowrap=\"\"> <input type=\"checkbox\" value=\"175\""
					+ " name=\"" + clomune.name + "\"></td>";
			break;
		case "img":
			html = "<a hidefocus=\"true\""
					+ "href=\"javascript:;\" id=\"star_sign_175\" class=\"\"><img "
					+ "align=\"absmiddle\" width=\"16\" height=\"16\" alt=\"无\""
					+ " src=\"" + clomune.src + "\"> </a>";
			if (ref) {
				html = "<input type=\"hidden\" value='" + eval(row + "." + ref)
						+ "'/>" + html;
			}
			html = "<td align=\"center\" width=\"" + clomune.width + "\">"
					+ html + "</td>"
			break;
		case "label":
			html = "<td width=\"" + clomune.width + "\" title=\""
					+ eval("row." + ref) + "\" class=\"from\">"
					+ eval("row." + ref) + " </td>";
			break;
		case "time":
			var Q = eval("row." + ref);
			k = (Q) ? Q.substring(0, 8) : "";
			html = "<td width=\"" + clomune.width + "\" title=\"" + Q
					+ "\" class=\"from\">" + k + " </td>";
			break;
		case "label-img":
			html = "<td width=\""
					+ clomune.width
					+ "\" title=\"[无主题]\" class=\"subject\"><span class=\"operation\"> <a title=\"新窗口打开\" href=\"javascript:;\" class=\"open\"></a> </span> "
					+ eval("row." + ref) + "</td>";
			break;
		default:
			html = "";
			break;
		}
		return html;
	}
	;
	function _setHead() {
		var html = "<table width=\""
				+ _tableWidth
				+ "\" class=\"TableTop\"><tbody><tr><td nowrap=\"\" class=\"left\">";
		for ( var i = 0; i < _headSet.length; i++) {
			html += _getheadHtml(_headSet[i]);
		}
		html += "</td></tr></tbody></table>";
		$("#" + _divId).append(html);
		$("#allbox_skyWind_for").bind(
				{
					"click" : function() {
						var p = $("#_skyWindTableList_>tbody>tr>td").children(
								"[name='mailSelect']").attr("checked",
								this.checked);
						return;
					}
				});
	}
	this.setExternalHead = function(html) {
		$("#" + _divId + ">.TableTop>tbody>tr").append(html);
	};
	function _listBody() {
		var table = $("<table width=\"100%\" class=\"mail_list\" id='_skyWindTableList_'></table>");
		var data = _data.rows;
		if (!data) {
			alert(_data.error);
			return;
		}
		var w = this;
		for ( var i = 0; i < data.length; i++) {
			var html = "<tr rowid=\"" + data[i].SID + "\"index=\"" + i
					+ "\" ><td nowrap=\"\" style=\"width: 4px;\">";
			for ( var j = 0; j < _headSet.length; j++) {
				html += _getBodyData(data[i], _headSet[j]);
			}
			html += "</tr>";
			$(html).appendTo(table);
		}
		return table;
	}
	function _setBottom() {
		var html = "<table width=\"100%\" class=\"BlockBottom3\">                                              "
				+ "	<tr>                                                                                    "
				+ "		<td class=\"left\"></td>                                                            "
				+ "		<td style=\"text-align: left;\" class=\"center\">                                   "
				+ "			<div class=\"pageArea\" id=\"pageArea\">                                        "
				+ "				第                                                                           "
				+ "				<span class=\"pageNumber\" id=\"pageNumber\">1/1</span>页                    "
				+ "				<a title=\"首页\" class=\"pageFirstDisable\" id=\"pageFirst\"                 "
				+ "					href=\"javascript:;\"></a>                                              "
				+ "				<a title=\"上一页\" class=\"pagePreviousDisable\" id=\"pagePrevious\"          "
				+ "					href=\"javascript:;\"></a><a title=\"下一页\" class=\"pageNextDisable\"    "
				+ "					id=\"pageNext\" href=\"javascript:;\"></a>                              "
				+ "				<a title=\"末页\" class=\"pageLastDisable\" id=\"pageLast\"                   "
				+ "					href=\"javascript:;\"></a>转到 第                                          "
				+ "				<input type=\"text\" style=\"text-align: center;\"                          "
				+ "					onkeypress=\"input_page_no()\" id=\"page_no\" name=\"page_no\"          "
				+ "					class=\"SmallInput\" size=\"3\">                                        "
				+ "				页                                                                           "
				+ "				<a title=\"转到\" class=\"pageGoto\" id=\"pageGoto\"                          "
				+ "					href=\"javascript:goto_page();\"></a>                                   "
				+ "			</div>                                                                          "
				+ "		</td>                                                                               "
				+ "		<td class=\"right\"></td>                                                           "
				+ "	</tr>                                                                                   "
				+ "</table>                                                                                   ";
		$("#" + _divId).append(html);
	}
	this.afterLoad = null;
	this.loadData = function(dataurl) {
		var w = this;
		var k = new $ajax();
		var msgArea = $("#msgArea");
		msgArea.css("display", "block");
		var backFun = function(dataJson) {
			_data = dataJson;
			msgArea.css("display", "none");
			if (_listBodyId && _listContainerId) {
				$("#" + _listBodyId).append(_listBody());
				var html = $("#" + _listContainerId).clone().css("display",
						"block")[0].outerHTML;
				$("#" + _divId).append(html);
			} else {
				$("#" + _divId).append(_listBody());
			}
			$("#_skyWindTableList_>tbody>tr").bind( {
				"click" : function() {
					ListTable.prototype.setActiveRow(this.index);
				}
			});
			_setBottom();
			w.setActiveRow(0);
			if (typeof (w.afterLoad) == "function") {
				w.afterLoad();
			}
		};
		var filter;
		if (typeof (this.setFilter) == "function") {
			filter = _filter = this.setFilter();
		}
		k.setURL(dataurl);
		k.setFun(backFun);
		filter = (filter) ? "filter=" + filter : "filter=1=1";
		k.send(true, null, null, filter);//"toCode=system"

	}
	function _intil() {
		var htmlk = "<div style=\"display: block; position: absolute; left: 300px; top: 200px;\" id=\"msgArea\"> "
				+ "<img align=\"absMiddle\" src=\"/tlv8/comon/image/loading.gif\"></div>";
		$("#" + _divId).append(htmlk);
		_setHead();
	}
	_intil();
}