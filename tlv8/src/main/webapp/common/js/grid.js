/**
 * ------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
 * 数据列表
 * 
 * @author chenqian
 * @date 2021-06-28
 * @version 1.0
 * 
 * -------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
 */
(function($) {
	$.fn.tlv8DataGrid = function(options) {
		var _this = this;
		var grid = {
			toolbar : {
				insertItem : true,
				saveItem : true,
				refreshItem : true,
				deleteItem : true
			},
			settoolbar : function(insertItem, saveItem, refreshItem, deleteItem) {
				// 查看流程【已办】时grid设置为“只读”，编辑按钮禁用
				if ("detail" == tlv8.RequestURLParam
						.getParam("activity-pattern")) {
					$("#" + _this.attr("id") + "_insertItem").hide();
					$("#" + _this.attr("id") + "_saveItem").hide();
					$("#" + _this.attr("id") + "_deleteItem").hide();
					return;
				}
				this.toolbar.insertItem = insertItem;
				this.toolbar.saveItem = saveItem;
				this.toolbar.refreshItem = refreshItem;
				this.toolbar.deleteItem = deleteItem;
				if (insertItem == "no") {
					// 不做任何处理
				} else if (this.toolbar.insertItem == true) {
					$("#" + _this.attr("id") + "_insertItem").removeClass(
							"disabled");
					$("#" + _this.attr("id") + "_insertItem").removeAttr(
							"disabled");
					$("#" + _this.attr("id") + "_insertItem").show();
				} else if (this.toolbar.insertItem == "readonly") {
					$("#" + _this.attr("id") + "_insertItem").show();
					$("#" + _this.attr("id") + "_insertItem").addClass(
							"disabled");
					$("#" + _this.attr("id") + "_insertItem").attr("disabled",
							true);
				} else {
					$("#" + _this.attr("id") + "_insertItem").hide();
				}
				if (saveItem == "no") {
					// 不做任何处理
				} else if (this.toolbar.saveItem == true) {
					$("#" + _this.attr("id") + "_saveItem").removeClass(
							"disabled");
					$("#" + _this.attr("id") + "_saveItem").removeAttr(
							"disabled");
					$("#" + _this.attr("id") + "_saveItem").show();
				} else if (this.toolbar.saveItem == "readonly") {
					$("#" + _this.attr("id") + "_saveItem").show();
					$("#" + _this.attr("id") + "_saveItem")
							.addClass("disabled");
					$("#" + _this.attr("id") + "_saveItem").attr("disabled",
							true);
				} else {
					$("#" + _this.attr("id") + "_saveItem").hide();
				}
				if (refreshItem == "no") {
					// 不做任何处理
				} else if (this.toolbar.refreshItem == true) {
					$("#" + _this.attr("id") + "_refreshItem").removeClass(
							"disabled");
					$("#" + _this.attr("id") + "_refreshItem").removeAttr(
							"disabled");
					$("#" + _this.attr("id") + "_refreshItem").show();
				} else if (this.toolbar.refreshItem == "readonly") {
					$("#" + _this.attr("id") + "_refreshItem").show();
					$("#" + _this.attr("id") + "_refreshItem").addClass(
							"disabled");
					$("#" + _this.attr("id") + "_refreshItem").attr("disabled",
							true);
				} else {
					$("#" + _this.attr("id") + "_refreshItem").hide();
				}
				if (deleteItem == "no") {
					// 不做任何处理
				} else if (this.toolbar.deleteItem == true) {
					$("#" + _this.attr("id") + "_deleteItem").removeClass(
							"disabled");
					$("#" + _this.attr("id") + "_deleteItem").removeAttr(
							"disabled");
					$("#" + _this.attr("id") + "_deleteItem").show();
				} else if (this.toolbar.deleteItem == "readonly") {
					$("#" + _this.attr("id") + "_deleteItem").show();
					$("#" + _this.attr("id") + "_deleteItem").addClass(
							"disabled");
					$("#" + _this.attr("id") + "_deleteItem").attr("disabled",
							true);
				} else {
					$("#" + _this.attr("id") + "_deleteItem").hide();
				}
			},
			staticFilter : "",
			setStaticFilter : function(filter) {
				this.staticFilter = filter;
			},
			CurrentPage : 1,
			rows : 10, // 默认每页显示行数
			setPageBar : function(page, allpage) {
				grid.CurrentPage = page;
				grid.allpage = allpage;
				var pagebar = $("#" + _this.attr("id") + '_pagebar');
				pagebar.html("");
				pagebar.append('<li ' + (page == 1 ? 'class="disabled"' : '')
						+ '><a href="#" title="第一页" id="' + _this.attr("id")
						+ '_firstpage">&laquo;</a></li>');
				pagebar.append('<li ' + (page == 1 ? 'class="disabled"' : '')
						+ '><a href="#" title="上一页" id="' + _this.attr("id")
						+ '_prevpage">&lt;</a></li>');
				if (allpage > 10) {
					if (page <= 10) {
						for (var p = 1; p <= Math.min(allpage, 10); p++) {
							if (p == page) {
								pagebar
										.append('<li class="active"><a href="#">'
												+ p
												+ ' <span class="sr-only">(current)</span></a></li>');
							} else {
								pagebar.append('<li class="page"><a href="#">'
										+ p + '</a></li>');
							}
						}
						pagebar
								.append('<li><a href="#">... <span class="sr-only">(current)</span></a></li>');
					} else {
						pagebar
								.append('<li><a href="#">... <span class="sr-only">(current)</span></a></li>');
						for (var p = Math.max(1, page - 5); p <= Math.min(
								allpage, page + 5); p++) {
							if (p == page) {
								pagebar
										.append('<li class="active"><a href="#">'
												+ p
												+ ' <span class="sr-only">(current)</span></a></li>');
							} else {
								pagebar.append('<li class="page"><a href="#">'
										+ p + '</a></li>');
							}
						}
						pagebar
								.append('<li><a href="#">... <span class="sr-only">(current)</span></a></li>');
					}
				} else {
					for (var p = 1; p <= allpage; p++) {
						if (p == page) {
							pagebar
									.append('<li class="active"><a href="#">'
											+ p
											+ ' <span class="sr-only">(current)</span></a></li>');
						} else {
							pagebar.append('<li class="page"><a href="#">' + p
									+ '</a></li>');
						}
					}
				}
				pagebar.append('<li '
						+ (page >= allpage ? 'class="disabled"' : '')
						+ '><a href="#" title="下一页" id="' + _this.attr("id")
						+ '_nextpage">&gt;</a></li>');
				pagebar.append('<li '
						+ (page >= allpage ? 'class="disabled" disabled' : '')
						+ '><a href="#" title="最后一页" id="' + _this.attr("id")
						+ '_lastpage">&raquo;</a></li>');
				$("#" + _this.attr("id") + "_firstpage").click(function() {
					if (grid.CurrentPage > 1) {
						grid.firstPage();
					}
				});
				$("#" + _this.attr("id") + "_prevpage").click(function() {
					if (grid.CurrentPage > 1) {
						grid.previousPage();
					}
				});
				$("#" + _this.attr("id") + "_nextpage").click(function() {
					if (grid.CurrentPage < grid.allpage) {
						grid.nextPage();
					}
				});
				$("#" + _this.attr("id") + "_lastpage").click(function() {
					if (grid.CurrentPage < grid.allpage) {
						grid.lastPage();
					}
				});
				pagebar.find(".page").click(function() {
					var p = $(this).text();
					grid.toPage(p);
				});
			},
			firstPage : function() {
				grid.CurrentPage = 1;
				grid.refreshData();
			},
			previousPage : function() {
				grid.CurrentPage--;
				grid.refreshData();
			},
			nextPage : function() {
				grid.CurrentPage++;
				grid.refreshData();
			},
			lastPage : function() {
				grid.CurrentPage = grid.allpage;
				grid.refreshData();
			},
			allpage : 1,
			getPages : function() {
				// 获取数据总页数
				return this.allpage;
			},
			toPage : function(page) {
				if (parseInt(page) < 0) {
					alert("页码不能小于0!");
					return;
				}
				if (parseInt(page) > grid.allpage) {
					alert("指定的页码不能大于总页数!");
					return;
				}
				grid.CurrentPage = page;
				grid.refreshData();
			},
			beforSortCell : null,
			CellSort : function(obj, cellID) {
				var orDerby = "";
				var cellIndex = (obj.tagName == "TD") ? obj.cellIndex
						: obj.parentNode.cellIndex;
				var datatypes = grid.datatypes;
				var dtype = datatypes[cellIndex];
				if (dtype.toUpperCase() == "CLOB") {
					return;
				}
				if (grid.beforSortCell && obj != grid.beforSortCell) {
					grid.beforSortCell.className = "";
					$(grid.beforSortCell).find("div").attr("class", "");
				}
				grid.sortID = cellID;
				var objlaebl = $(obj).find("div");
				if (obj.className && obj.className == "sortASC") {
					$(objlaebl).attr("class", "sortDESC");
					obj.className = "sortDESC";
					orDerby = cellID + " desc";
					grid.sortSC = "sortDESC";
				} else {
					$(objlaebl).attr("class", "sortASC");
					obj.className = "sortASC";
					orDerby = cellID + " asc";
					grid.sortSC = "sortASC";
				}
				data.setOrderby(orDerby);
				grid.refreshData();
				grid.beforSortCell = obj;
			},
			insertSelfBar : function(label, action, imgc, bid) {
				bid = bid || new UUID().toString();
				var clickfn = '';
				if (action && action != "") {
					clickfn = 'onclick="' + action + '"';
				}
				var imgl = "";
				if (imgc && imgc != "") {
					imgl = '<span class="glyphicon ' + imgc + '"></span>';
				}
				var nbtn = $('<button ' + clickfn + ' id="' + bid
						+ '" class="btn btn-default btn-sm" '
						+ ' style="float:left;margin-right:5px;">' + imgl
						+ label + '</button>');
				$("#" + _this.attr("id") + "_queryItem").before(nbtn);
				return bid;
			},
			setExcelimpBar : function(flag) {
				var excelimpbarID = _this.attr("id") + "_impBar";
				if (flag) {
					grid.insertSelfBar("Excel导入", "", "glyphicon-import",
							excelimpbarID);
					J$(excelimpbarID).onclick = function() {
						tlv8.ExcelImp(grid.data.dbkay, grid.data.table,
								grid.relations.join(","), "", function() {
									grid.refreshData();
								});
					};
				} else {
					$(J$(excelimpbarID)).remove();
				}
			},
			setExcelexpBar : function(flag) {
				var excelexpbarID = _this.attr("id") + "_expBar";
				if (flag) {
					grid.insertSelfBar("导出Excel", "", "glyphicon-export",
							excelexpbarID);
					J$(excelexpbarID).onclick = function() {
						tlv8.ExcelExp(grid.data.dbkay, grid.data.table,
								grid.relations.join(","),
								grid.lables.join(","), grid.billfilter,
								grid.data.orderby);
					};
				} else {
					$(J$(excelexpbarID)).remove();
				}
			},
			billfilter : "",
			datalist : [],
			Length : 0,
			getLength : function() {
				return this.Length;
			},
			setValueByName : function(name, param, value) {
				var setRow;
				if (typeof param == "string") {
					setRow = _this.find("tr[id='" + param + "']").get(0);
				} else {
					setRow = _this.find("tbody").find("tr").get(param);
				}
				var index = grid.getRelationsIndex(name);
				if (index > -1) {
					grid.beforeeditData = grid.getValueByName(name, param);
					var setCell = setRow.getElementsByTagName("td")[index];
					$(setCell).text(grid.encodeValue(value));
					setRow.data[name] = value;
					grid.changeValue($(setRow).attr("id"), name, value,
							grid.beforeeditData, setCell);
				}
			},
			getValueByName : function(name, param) {
				var getRow;
				if (typeof param == "string") {
					getRow = _this.find("tr[id='" + param + "']").get(0);
				} else {
					getRow = _this.find("tbody").find("tr").get(param);
				}
				return getRow.data[name];
			},
			/**
			 * @function
			 * @name locate
			 * @description 查找关系值对应的行ID,crowid除外
			 * @param {string}
			 *            relation -列名
			 * @param {string}
			 *            value -值
			 * @param {string}
			 *            crowid -行rowid
			 * @returns {string}
			 */
			locate : function(relation, value, crowid) {
				var datalist = grid.datalist;
				for (var i = 0; i < datalist.length; i++) {
					if (datalist[i][relation] == value
							&& datalist[i].rowid != crowid) {
						return datalist[i].rowid;
					}
				}
				return "";
			},
			/**
			 * 获取指定行ID对应的行号
			 * 
			 * @return number
			 */
			getRowIndex : function(cId) {
				var datalist = grid.datalist;
				for (var i = 0; i < datalist.length; i++) {
					if (datalist[i].rowid != cId) {
						return i;
					}
				}
				return -1;
			},
			dbclick : function(data) {

			},
			/**
			 * @private
			 * @name setdbclick
			 * @description 设置行双击事件
			 * @param {function}
			 *            fn
			 */
			setdbclick : function(fn) {

			},
			relations : [],
			lables : [],
			datatypes : [],
			/**
			 * @function
			 * @name getRelationsIndex
			 * @description 获取指定列的序号
			 * @param {string}
			 *            relation
			 * @returns {number}
			 */
			getRelationsIndex : function(relation) {
				return grid.relations.indexOf(relation);
			},
			editModel : false,
			editMOdelVal : "readonly",
			rowState : new Map(),
			/**
			 * @function
			 * @name setrowState
			 * @description 设置某行的状态
			 * @param {string}
			 *            rowid
			 * @param {string}
			 *            state
			 * @example grid.setrowState(rowid,"readonly");
			 */
			setrowState : function(rowid, state) {
				this.rowState.put(rowid, state);
			},
			/**
			 * @function
			 * @name seteditModel
			 * @description 设置grid的编辑模式
			 * @param {string}
			 *            editModel
			 * @example grid.seteditModel(true|false); //是否可编辑
			 * @example grid.seteditModel("dbclick"); //双击编辑
			 */
			seteditModel : function(editModel) {
				this.editModel = editModel;
				if (editModel == true) {
					dbclick = null;
				}
				if (this.editModel == true || this.editModel == "dbclcik"
						|| this.editModel == "dbclick") {
					this.editMOdelVal = "edit";
				} else {
					this.editMOdelVal = "readonly";
				}
			},
			editDataRowIds : [],
			beforeeditData : null,
			editedDatas : new Map(),
			RequiredCells : new Map(),
			requiredAlert : "",
			/**
			 * @function
			 * @name setRequired
			 * @description 设置指定的字段必填
			 * @param {string}
			 *            cellName -需要设置必填的列名，多个用逗号分隔
			 * @example grid.setRequired("fCODE");
			 * @example grid.setRequired("fCODE,fNAME");
			 */
			setRequired : function(cellName) {
				var cellsn = cellName.split(",");
				for (var i = 0; i < cellsn.length; i++) {
					this.RequiredCells.put(cellsn[i], true);
				}
			},
			outEditBlur : function() {
				try {
					var temP_ipt_d = J$(_this.attr("id") + "_editgridipt");
					if (temP_ipt_d) {
						grid.editended(temP_ipt_d, false, false);
					}
				} catch (e) {
					console.log(e);
				}
			},
			editData : function(event, obj) {
				if (!grid.editModel)
					return;
				if (grid.rowState.containsKey(obj.id)) {
					if (grid.rowState.get(obj.id) == "readonly") {
						grid.outEditBlur();
						return false;
					}
				}
				event = event ? event : (window.event ? window.event : null);
				var objEdit = event.srcElement ? event.srcElement
						: event.target;
				if (objEdit.tagName == "INPUT") {
					try {
						objEdit.focus();
					} catch (e) {
					}
					return false;
				} else {
					grid.outEditBlur();
				}
				var celldatatype = $(objEdit).attr("datatype");
				if (!celldatatype || celldatatype == "null"
						|| celldatatype == "No" || celldatatype == "ro") {
					return false;
				}
				var Editrow = objEdit.parentNode;
				var TDvalue = $(objEdit).text() ? $(objEdit).text() : "";
				grid.beforeeditData = Editrow.data[$(objEdit).attr("relation")];
				try {
					var valDocument = objEdit.childNodes[0];
					if (valDocument.tagName == "INPUT") {
						return false;
					}
				} catch (e) {
				}
				var ipttype = "text";
				if (celldatatype == "number" || celldatatype == "phone"
						|| celldatatype == "email") {
					ipttype = celldatatype;
				}
				var input = "<input class='form-control' id='"
						+ _this.attr("id")
						+ "_editgridipt' type="
						+ ipttype
						+ " value='' style='width:"
						+ ($(objEdit).width() + 15)
						+ "px;height:"
						+ ($(objEdit).height() + 15)
						+ "px;margin-left:-7px;margin-top:-7px;margin-right:-7px;margin-bottom:-7px;"
						+ "padding:6px 6px;'></input>";
				objEdit.innerHTML = input;
				$("#" + _this.attr("id") + "_editgridipt").val(
						grid.decodeValue(TDvalue));
				var ismmedit = false;
				if (celldatatype == "date") {
					$('#' + _this.attr("id") + "_editgridipt").focus(
							function() {
								var ipt = this;
								WdatePicker({
									dateFmt : "yyyy-MM-dd",
									onpicked : function() {
										grid.editended(ipt);
									}
								});
							});
					ismmedit = true;
				}
				if (celldatatype == "datetime") {
					$('#' + _this.attr("id") + "_editgridipt").focus(
							function() {
								var ipt = this;
								WdatePicker({
									dateFmt : "yyyy-MM-dd HH:mm:ss",
									onpicked : function() {
										grid.editended(ipt);
									}
								});
							});
					ismmedit = true;
				}
				if (celldatatype == "time") {
					$('#' + _this.attr("id") + "_editgridipt").focus(
							function() {
								var ipt = this;
								WdatePicker({
									dateFmt : "HH:mm:ss",
									onpicked : function() {
										grid.editended(ipt);
									}
								});
							});
					ismmedit = true;
				}
				if (celldatatype == "month") {
					$('#' + _this.attr("id") + "_editgridipt").focus(
							function() {
								var ipt = this;
								WdatePicker({
									dateFmt : "MM",
									onpicked : function() {
										grid.editended(ipt);
									}
								});
							});
					ismmedit = true;
				}
				if (celldatatype == "yearmonth") {
					$('#' + _this.attr("id") + "_editgridipt").focus(
							function() {
								var ipt = this;
								WdatePicker({
									dateFmt : "yyyy-MM",
									onpicked : function() {
										grid.editended(ipt);
									}
								});
							});
					ismmedit = true;
				}
				if (celldatatype == "year") {
					$('#' + _this.attr("id") + "_editgridipt").focus(
							function() {
								var ipt = this;
								WdatePicker({
									dateFmt : "yyyy",
									onpicked : function() {
										grid.editended(ipt);
									}
								});
							});
					ismmedit = true;
				}
				try {
					J$(_this.attr("id") + "_editgridipt").focus();
					J$(_this.attr("id") + "_editgridipt").select();
				} catch (e) {
				}
				$("#" + _this.attr("id") + "_editgridipt").blur(function() {
					if (!ismmedit)
						grid.editended(this);
				});
			},
			editended : function(obj) {
				var TDsrc = obj.parentNode;
				while (TDsrc.tagName != "TD") {
					TDsrc = TDsrc.parentNode;
				}
				var TRsrc = TDsrc.parentNode;
				var EditrowID = $(TRsrc).attr("id");
				var value = $(obj).val();
				var dcodeV = grid.encodeValue(value);
				$(TDsrc).html(dcodeV);
				var relation = $(TDsrc).attr("relation");
				var celloldValue = TRsrc.data[relation];
				if (grid.toolbar.saveItem != false && celloldValue != value) {
					grid.settoolbar("no", true, "no", "no");
				}
				if (celloldValue != value) {
					TRsrc.data[relation] = value;
					if (grid.editDataRowIds.indexOf(EditrowID) < 0) {
						grid.editDataRowIds.push(EditrowID);
					}
					grid.editedDatas.put(EditrowID, TRsrc.data);
				}
				grid.changeValue(EditrowID, relation, value, celloldValue,
						TDsrc);
			},
			changeValue : function(EditrowID, relation, value, celloldValue,
					editCell) {
				if (celloldValue != value) {
					var dataValueChanged = _this.attr("dataValueChanged");
					if (dataValueChanged) {
						var dataValueChangedFn = eval(dataValueChanged);
						var vData = {
							olddata : celloldValue,
							newdata : value,
							cellname : relation,
							obj : editCell,
							rowid : EditrowID
						};
						if (typeof (dataValueChangedFn) == "function") {
							dataValueChangedFn(vData);
						}
					}
				}
			},
			encodeValue : function(val) {
				return val.replaceAll("<", "&lt;").replaceAll(">", "&gt;");
			},
			decodeValue : function(val) {
				return val.replaceAll("&lt;", "<").replaceAll("&gt;", ">");
			},
			advancedQueryData : function() {
				var queryUrl = "/common/gridFilter/FilterSet.html?uuid="
						+ new UUID().toString();
				var sendParam = {
					labelids : grid.relations,
					labels : grid.lables
				};
				tlv8.portal.dialog.openDialog('高级查询', queryUrl, 700, 400,
						grid.advancedQueryDataBack, null, true, sendParam);
			},
			advancedQueryDataBack : function(re) {
				if (re && typeof re == "string") {
					grid.refreshData(re);
				}
			},
			CurrentRowId : "",
			/**
			 * @function
			 * @name getCurrentRowId
			 * @description 获取当前选中行的rowid
			 * @returns {string}
			 */
			getCurrentRowId : function() {
				return this.CurrentRowId;
			},
			onselected : function(event, obj) {
				var rid = grid.CurrentRowId;
				if (rid && rid != "") {
					_this.find("[id='" + rid + "']").removeClass("success");
				}
				grid.CurrentRowId = $(obj).attr("id");
				$(obj).addClass("success");
				try {
					// 自动关联主数据主键
					grid.data.rowid = grid.CurrentRowId;
					var formid = grid.data.formid;
					if (formid && formid != "") {
						$(J$(formid)).attr("rowid", grid.CurrentRowId);
					}
				} catch (e) {
				}
				try {
					// 触发选中事件
					var dponselected = _this.attr("onselected");
					if (dponselected && dponselected != "") {
						var clcikFN = eval(dponselected);
						if (typeof (clcikFN) == "function")
							clcikFN(grid);
					}
				} catch (e) {
				}
			},
			checkAll : function() {

			},
			uncheckAll : function() {

			},
			masterselect : function(event) {

			},
			checkedRowIds : [],
			getCheckedRowIds : function() {
				return this.checkedRowIds;
			},
			checkRows : function(event) {

			},
			/**
			 * 设置选择
			 * 
			 * @rowid 行ID
			 * @state 选择状态[true/false]
			 * @triggercal 是否需要触发选择事件（默认不触发）
			 */
			setRowChecked : function(rowid, state, triggercal) {

			},
			gridFooterRow : [],
			addFooterrow : function(param) {

			},
			quickSearch : function(text) {
				grid.refreshData("", true, text);
			},
			showGridTextArea : function(event) {

			},
			insertData : function() {
				var beforeInert = _this.attr("beforeInsert");
				if (beforeInert) {
					var inFn = eval(beforeInert);
					if (typeof (inFn) == "function") {
						inFn(grid);
					}
				}
				var tbody = _this.find("tbody");
				if (tbody.length < 1) {
					tbody = $("<tbody></tbody>");
					_this.append(tbody);
				}
				var dtr = $('<tr></tr>');
				tbody.prepend(dtr);// 添加到第一行
				var rdata = {
					rowid : new UUID().toString(),
					version : 0
				};
				dtr.attr("id", rdata.rowid);
				if (grid.master == true || grid.master == "true") {
					var chtd = $('<td align="center"></td>');
					chtd.append('<input type="checkbox" id="' + rdata.rowid
							+ '_checkbox" rowid="' + rdata.rowid + '">');
					dtr.append(chtd);
				}
				if (grid.showindex == true || grid.showindex == "true") {
					var idxtd = $('<td align="center"></td>');
					dtr.append(idxtd);
				}
				for (var r = 0; r < grid.relations.length; r++) {
					var vtd = $('<td></td>');
					vtd.attr("relation", grid.relations[r]);
					vtd.attr("datatype", grid.datatypes[r]);
					vtd.text("");
					rdata[grid.relations[i]] = "";
					dtr.append(vtd);
				}
				dtr.get(0).data = rdata;
				if (grid.toolbar.saveItem != false)
					grid.settoolbar("no", true, "no", "no");
				var afterInert = _this.attr("afterInert");
				if (afterInert) {
					var ainFn = eval(afterInert);
					if (typeof (ainFn) == "function") {
						ainFn(grid);
					}
				}
				dtr.click(function(event) {
					grid.onselected(event, this);
				});
				tbody.find("td").click(function(event) {
					grid.editData(event, this.parentNode);
				});
				var afterInert = _this.attr("afterInert");
				if (afterInert) {
					var ainFn = eval(afterInert);
					if (typeof (ainFn) == "function") {
						ainFn(grid);
					}
				}
				try {
					dtr.click();
				} catch (e) {
				}
				return rdata.rowid;
			},
			saveData : function(event, gridsavecalback) {
				// console.log(JSON.stringify(grid.editedDatas.values()));
				var beforeSave = _this.attr("beforeSave");
				if (beforeSave) {
					var asavFn = eval(beforeSave);
					if (typeof (asavFn) == "function") {
						asavFn(grid);
					}
				}
				var query = "dbkey=" + grid.data.dbkay;
				query += "&table=" + grid.data.table;
				query += "&datas=" + JSON.stringify(grid.editedDatas.values());
				if (grid.billdataformid && grid.billcell) {
					var billid = $(J$(grid.billdataformid)).attr("rowid");
					if (billid && billid != "") {
						query += "&billid=" + billid;
						query += "&billcell=" + grid.billcell;
					}
				}
				console.log(query);
				var param = new tlv8.RequestParam();
				param.set("query", CryptoJS.AESEncrypt(J_u_encode(query)));
				var res = tlv8.XMLHttpRequest("core/saveGridData", param,
						"post", false);
				if (res.flag == "true") {
					grid.editDataRowIds = [];
					grid.editedDatas = new Map();
					sAlert("操作成功!");
					grid.refreshData();
				} else {
					tlv8.showMsg(res.message);
				}
				if (gridsavecalback && typeof gridsavecalback == "function") {
					try {
						gridsavecalback();
					} catch (e) {
						console.log(e);
					}
				}
				var afterSave = _this.attr("afterSave");
				if (afterSave) {
					var afsavFn = eval(afterSave);
					if (typeof (afsavFn) == "function") {
						afsavFn(grid);
					}
				}
			},
			deleteData : function(deleteRowID, isConfirm, isRefresh) {
				var rowids = deleteRowID || grid.CurrentRowId;
				if (grid.master == true || grid.master == "true") {
					rowids = grid.checkedRowIds;
				}
				var deleted = function() {
					var beforeDelete = _this.attr("beforeDelete");
					if (beforeDelete) {
						var bdelFn = eval(beforeDelete);
						if (typeof (bdelFn) == "function") {
							bdelFn(grid);
						}
					}
					var param = new tlv8.RequestParam();
					param.set("dbkey", grid.data.dbkay);
					param.set("table", grid.data.table);
					param.set("rowids", rowids);
					var res = tlv8.XMLHttpRequest("core/removeGridData", param,
							"post", false);
					if (res.flag == "true") {
						sAlert("操作成功!");
						if (isRefresh != false) {
							grid.refreshData();
						}
					} else {
						tlv8.showMsg(res.message);
					}
					var afterDelete = _this.attr("afterDelete");
					if (afterDelete) {
						var afdelFn = eval(afterDelete);
						if (typeof (afdelFn) == "function") {
							afdelFn(grid);
						}
					}
				};
				if (!rowids || rowids == "") {
					tlv8.showMsg("请选择需要删除的数据!");
				} else {
					if (isConfirm != false) {
						tlv8.showConfirm("数据删除后将无法恢复,确定删除数据吗?", function(e) {
							if (e) {
								deleted();
							}
						});
					} else {
						deleted();
					}
				}
			},
			refreshData : function(filter, isconfirm, searchtext) {
				var refresh = function() {
					var beforeRefresh = _this.attr("beforeRefresh");
					if (beforeRefresh) {
						var brinFn = eval(beforeRefresh);
						if (typeof (brinFn) == "function") {
							brinFn(grid);
						}
					}
					var where = grid.where;
					if (!where)
						where = " 1=1 ";
					if (grid.staticFilter && grid.staticFilter != "") {
						where += " and (" + grid.staticFilter + ")";
					}
					// if (filter && filter != "") {
					// where += " and (" + filter + ")";
					// }
					if (grid.billdataformid && grid.billcell) {
						var billid = $(J$(grid.billdataformid)).attr("rowid");
						billid = billid ? billid : "";
						where += " and (" + grid.billcell + "= '" + billid
								+ "')";
					}
					grid.billfilter = where;
					var query = "dbkey=" + grid.data.dbkay;
					query += "&table=" + grid.data.table;
					query += "&columns=" + grid.relations.join(",");
					query += "&columnstype=" + grid.datatypes.join(",");
					query += "&where=" + where;
					query += "&orderby=" + (grid.orderby ? grid.orderby : "");
					query += "&page=" + grid.CurrentPage;
					query += "&rows=" + grid.rows;
					query += "&filter=" + (filter || "");
					query += "&searchtext=" + (searchtext || "");
					var param = new tlv8.RequestParam();
					param.set("query", CryptoJS.AESEncrypt(J_u_encode(query)));
					tlv8.XMLHttpRequest("core/loadGridData", param, "post",
							true, grid.setDatatoGrid);
				};
				if (grid.editDataRowIds.length > 0) {
					if (isconfirm != false) {
						tlv8.showConfirm("数据已经更改刷新会导致数据丢失,确定刷新吗?", function(e) {
							if (!e) {
								return false;
							}
							grid.editDataRowIds = [];
							grid.editedDatas = new Map();
							refresh();
						});
					}
				} else {
					refresh();
				}
			},
			setDatatoGrid : function(rdata) {
				// console.log(rdata);
				if (rdata.page > rdata.allpage) {
					grid.toPage(1);
					return;
				}
				var tbody = _this.find("tbody");
				if (tbody.length < 1) {
					tbody = $("<tbody></tbody>");
					_this.append(tbody);
				}
				tbody.html("");
				var datalist = rdata.data;
				grid.datalist = datalist;
				grid.Length = datalist.length; // 记录当前页的数据行数
				for (var i = 0; i < datalist.length; i++) {
					var data = datalist[i];
					var dtr = $('<tr id="' + data.rowid + '"></tr>');
					var relations = grid.relations;
					var datatypes = grid.datatypes;
					if (grid.master == true || grid.master == "true") {
						var chtd = $('<td align="center"></td>');
						chtd.append('<input type="checkbox" id="' + data.rowid
								+ '_checkbox" rowid="' + data.rowid + '">');
						dtr.append(chtd);
					}
					if (grid.showindex == true || grid.showindex == "true") {
						var idxtd = $('<td align="center"></td>');
						dtr.append(idxtd);
						var idx = data.index || i + 1;
						idxtd.text(idx);
					}
					for (var r = 0; r < relations.length; r++) {
						var val = grid.encodeValue(data[relations[r]]);
						var vtd = $('<td></td>');
						vtd.attr("relation", relations[r]);
						vtd.attr("datatype", datatypes[r]);
						vtd.text(val);
						dtr.append(vtd);
					}
					dtr.get(0).data = data;
					tbody.append(dtr);
				}
				grid.setPageBar(rdata.page, rdata.allpage);
				grid.settoolbar("no", "readonly", "no", "no");
				tbody.find("tr").click(function(event) {
					grid.onselected(event, this);
				});
				tbody.find("td").click(function(event) {
					grid.editData(event, this);
				});
				try {
					// 默认选中第一行
					tbody.find("tr").get(0).click();
				} catch (e) {
				}
				var afterRefresh = _this.attr("afterRefresh");
				if (afterRefresh) {
					var arinFn = eval(afterRefresh);
					if (typeof (arinFn) == "function") {
						arinFn(grid);
					}
				}
			}
		};
		$.extend(grid, options);
		var gridview = $('<div id="'
				+ _this.attr("id")
				+ '_view" style="width:100%;height:100%;background:#fff;border:1px solid #eee;padding:5px;border-radius:5px;"></div>');
		_this.parent().append(gridview);
		// ////工具条处理start/////
		var toolsview = $('<div id="'
				+ _this.attr("id")
				+ '_tools" style="width:100%; height:40px;overflow:hidden;"></div>');
		gridview.append(toolsview);
		var listview = $('<div id="'
				+ _this.attr("id")
				+ '_grid" '
				+ ' style="width:100%;overflow:hidden;border:1px solid #eee;"></div>');
		gridview.append(listview);
		listview.height(gridview.height() - 80);
		_this.appendTo(listview);
		toolsview.append('<button style="float:left;margin-right:5px;" id="'
				+ _this.attr("id")
				+ '_insertItem" type="button"  class="btn btn-primary btn-sm">'
				+ '<span class="glyphicon glyphicon-plus"></span> 添加</button>');
		$("#" + _this.attr("id") + "_insertItem").click(function() {
			grid.insertData();
		});
		toolsview
				.append('<button style="float:left;margin-right:5px;" id="'
						+ _this.attr("id")
						+ '_saveItem" type="button"  class="btn btn-info btn-sm">'
						+ '<span class="glyphicon glyphicon-floppy-disk"></span> 保存</button>');
		$("#" + _this.attr("id") + "_saveItem").click(function() {
			grid.saveData();
		});
		toolsview
				.append('<button style="float:left;margin-right:5px;" id="'
						+ _this.attr("id")
						+ '_refreshItem" type="button"  class="btn btn-success btn-sm">'
						+ '<span class="glyphicon glyphicon-repeat"></span> 刷新</button>');
		$("#" + _this.attr("id") + "_refreshItem").click(function() {
			grid.refreshData();
		});
		toolsview
				.append('<button style="float:left;margin-right:5px;" id="'
						+ _this.attr("id")
						+ '_deleteItem" type="button"  class="btn btn-danger btn-sm">'
						+ '<span class="glyphicon glyphicon-trash"></span> 删除</button>');
		$("#" + _this.attr("id") + "_deleteItem").click(function() {
			grid.deleteData();
		});
		toolsview
				.append('<button style="float:left;margin-right:5px;" id="'
						+ _this.attr("id")
						+ '_queryItem" type="button"  class="btn btn-default btn-sm">'
						+ '<span class="glyphicon glyphicon-filter"></span> 高级查询</button>');
		$("#" + _this.attr("id") + "_queryItem").click(function() {
			grid.advancedQueryData();
		});
		toolsview
				.append('<div class="input-group input-group-sm" style="float:left; width:280px;">'
						+ '<input type="text" class="form-control" id="'
						+ _this.attr("id")
						+ '_quick_text" placeholder="快速查询... ...">'
						+ '<span class="input-group-btn">'
						+ '<button class="btn btn-default" type="button" id="'
						+ _this.attr("id")
						+ '_quick_button">'
						+ '<span class="glyphicon glyphicon-search"></span>查询</button></span></div>');
		$("#" + _this.attr("id") + "_quick_text").keyup(function(event) {
			if (event.keyCode == 13) {
				var value_text = $(this).val();
				grid.quickSearch(value_text);
			}
		});
		$("#" + _this.attr("id") + "_quick_button").click(function() {
			var value_text = $("#" + _this.attr("id") + "_quick_text").val();
			grid.quickSearch(value_text);
		});
		// ////工具条处理end/////
		var pagebarview = $('<div id="'
				+ _this.attr("id")
				+ '_pagebars" style="width:100%; height:40px;overflow:hidden;padding-top: 5px;"></div>');
		gridview.append(pagebarview);
		var pagebar = $('<ul class="pagination pagination-sm" id="'
				+ _this.attr("id") + '_pagebar" style="margin:0;"></ul>');
		pagebarview.append(pagebar);
		grid.setPageBar(1, 1);
		return grid;
	};
})(jQuery);

/**
 * 列表构建函数
 */
tlv8.grid = function(element, options) {
	if (typeof element == "string") {
		element = J$(element);
	}
	var labelids = [];
	var Labels = [];
	var datatypes = [];
	$(element).find("thead").find("th").each(function() {
		var ref = $(this).attr("data-ref");
		var txt = $(this).text();
		if (ref && ref != "") {
			labelids.push(ref);
			Labels.push(txt);
			var type = $(this).attr("data-type");
			if (!type || type == "") {
				type = "string";
			}
			datatypes.push(type);
		}
	});
	var moptions = {
		relations : labelids,
		lables : Labels,
		datatypes : datatypes
	};
	var dbkey = $(element).attr("dbkey");
	var table = $(element).attr("table");
	if (dbkey && table) {
		var data = new tlv8.Data();
		data.setDbkey(dbkey);
		data.setTable(table);
		moptions.data = data;
	}
	if (!options)
		options = {};
	$.extend(moptions, options);
	return $(element).tlv8DataGrid(moptions);
};