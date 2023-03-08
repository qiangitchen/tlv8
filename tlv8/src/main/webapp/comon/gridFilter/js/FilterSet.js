//初始化页面
var relationSelectHtml, normalFilterline, base_line, selectLine;

var labelids = justep.yn.RequestURLParam.getParam("labelid");
var labels = justep.yn.RequestURLParam.getParam("labels");
var labelwidth = justep.yn.RequestURLParam.getParam("labelwidth");

function getUrlParam(param) {
	labelids = param.labelids;
	labels = param.labels;
	labelwidth = param.labelwidth;

	initBody();
}

function initBody(event) {
	var labIDs = labelids.split(",");
	var labeLs = labels.split(",");
	var labelwidths = labelwidth.split(",");
	if (labIDs.length > 0) {
		if (labIDs[0] == "master_check" || labIDs[0] == "No") {
			labIDs.splice(0, 1);
			labeLs.splice(0, 1);
			labelwidths.splice(0, 1);
		}
		if (labIDs[0] == "No") {
			labIDs.splice(0, 1);
			labeLs.splice(0, 1);
			labelwidths.splice(0, 1);
		}
	}
	relationSelectHtml = "";
	for ( var i in labIDs) {
		if (labelwidths[i] > 0 && labeLs[i])
			relationSelectHtml += "<option value='" + labIDs[i] + "'>"
					+ labeLs[i] + "</option>";
	}
	$("#relation").html(relationSelectHtml);
	$("#relation")[0].style.width = "100%";
	base_line = $("#filter_select_data_tab").html();
	normalFilterline = $("#filter_select_data_tab").html();
	try {
		selectLine = $("tr[id='filter_base_line']")[0];
		$("#relationvalue")[0].focus();
	} catch (e) {
	}
}

// 回传
function dailogEngin(event) {
	try {
		var filter_select_data = $("#filter_select_data_tab").html();
		if (!filter_select_data || filter_select_data == "") {
			return "1=1";
		}
	} catch (e) {
		return "1=1";
	}
	return get_Filter_Value();
}

// 增加条件栏
function addLine(event) {
	$("#filter_select_data_tab").append(base_line);
}

// 删除条件栏
function removeLine(event) {
	if (selectLine) {
		$(selectLine).remove();
	}
}

// 清空
function clearFilter(event) {
	$("#filter_select_data_tab").html("");
}

// 重置
function filter_reset(event) {
	$("#filter_select_data_tab").html(normalFilterline);
}

// 选中行
function select_line(obj) {
	if (selectLine) {
		selectLine.style.background = "#FFFFFF";
	}
	if (obj) {
		obj.style.background = "#FFF4C1";
		selectLine = obj;
	}
}

// 拼接条件
function get_Filter_Value() {
	var no_table = $("#filter_select_data_tab").get(0);
	if (no_table) {
		var filter_trs = no_table.rows;
		var filter = "";
		for (var i = 0; i < filter_trs.length; i++) {
			try {
				var filter_tds = filter_trs[i].cells;
				var ref = $(filter_tds[0]).find("select").val();
				var opt = $(filter_tds[1]).find("select").val();
				var vale = $(filter_tds[2]).find("input").val();
				var rl = $(filter_tds[3]).find("select").val();
				if (IsDate(vale)) {
					vale = "to_date('" + vale + "','yyyy-MM-dd')";// oralce专用
				} else {
					if (opt == "like")
						vale = "'%" + vale + "%'";
					else
						vale = "'" + vale + "'";
				}
				if (opt == "is null" || opt == "is not null") {
					filter += " " + ref + " " + opt + " " + rl;
				} else {
					filter += " " + ref + " " + opt + " " + vale + " " + rl;
				}
			} catch (e) {
				// alert(e.message);
			}
		}
		filter = filter.trim();
		filter = filter.repalceLastStr("and");
		filter = filter.repalceLastStr("or");
		// alert(filter);
		return filter.trim();
	} else {
		return "1=1";
	}
}

String.prototype.repalceLastStr = function(str) {
	var olv = this;
	if (olv.isLast(str)) {
		return olv.substring(0, olv.length - str.length);
	}
	return olv;
};

String.prototype.isLast = function(str) {
	var olv = this;
	if (this.indexOf(str) > 0) {
		return (olv.substring(olv.length - str.length) == str);
	}
	return false;
};

function IsDate(mystring) {
	var reg = /^(\d{4})-(\d{2})-(\d{2})$/;
	var str = mystring;
	var arr = reg.exec(str);
	if (!reg.test(str) || RegExp.$2 > 12 || RegExp.$3 > 31) {
		return false;
	}
	return true;
}

function ensureKey(event) {
	event = event || window.event;
	if (event.keyCode == 13) {
		justep.yn.portal.dailog.dailogEngin(dailogEngin());
	}
}