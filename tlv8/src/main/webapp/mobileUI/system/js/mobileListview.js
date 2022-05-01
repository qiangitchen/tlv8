/**
 * 手机版页面列表组件
 * @author 陈乾
 */
tlv8.MobileList = function(div, zSetting) {
	this.div = div;
	var _setting = {
		databaseName : "",
		tableName : "",
		idcolumn : "",
		title : {
			label : "",
			column : ""
		},
		texts : {
			label : "",
			column : ""
		},
		ellips : {
			label : "",
			column : ""
		},
		staticfilter : "",
		orderby : "",
		pagelimit : 10,
		onclick : function(id) {
		}
	};
	$.extend(true, _setting, zSetting);
	_setting.divID = $(div).attr("id");
	_setting.divObj = $(div);
	this.setting = _setting;
	this.currentpage = 1;
	this.searchfilter = "";
	this.createList = function(data) {
		var mlist = this;
		if (typeof data == "string") {
			data = window.eval("(" + data + ")");
		}
		var html = '<ul class="mui-table-view">';
		for (var i = 0; i < data.length; i++) {
			html += '<li class="mui-table-view-cell" id="'
					+ data[i][mlist.setting.idcolumn] + '" rel="list" >';
			html += '<div class="mui-table">';
			html += '<div class="mui-table-cell mui-col-xs-12" style="position:relative;">';
			html += '<h5 class="mui-ellipsis">';
			html += '<a style="text-decoration: none;">'
					+ mlist.setting.title.label
					+ data[i][mlist.setting.title.column] + '</a></h5>';
			html += '<div class="mui-table-cell mui-col-xs-6">';
			html += '<p style="color:#666;"><strong style="font-size:14px;">'
					+ mlist.setting.texts.label
					+ data[i][mlist.setting.texts.column] + '</strong>';
			html += '</p></div>';
			html += '<div class="mui-table-cell mui-col-xs-6" style="position:absolute;right:0px;bottom:0px;text-align:right;">';
			html += '<p class="mui-h6 mui-ellipsis">'
					+ mlist.setting.ellips.label + '<strong>'
					+ data[i][mlist.setting.ellips.column] + '</strong></p>';
			html += '</div>';
			html += '</div>';
			html += '</div></li>';
		}
		if (data.length >= mlist.setting.pagelimit && mlist.currentpage==1) {
			html += '<li class="mui-table-view-cell" style="align:center;" id="'
					+ mlist.setting.divID + '_moreBtn">';
			html += '<p><a class="ui-link" style="margin-left:40%;text-decoration: none;" href="#">加载更多...</a></p></li>';
		}else if(data.length >= mlist.setting.pagelimit){
			$("#" + mlist.setting.divID + "_moreBtn").show();
		}else{
			$("#" + mlist.setting.divID + "_moreBtn").hide();
		}
		html += '</ul>';
		return html;
	};
	this.init = function() {
		var mlist = this;
		this.currentpage = 1;
		$.ajax({
			type : "post",
			async : true,
			url : cpath+"/mobileListLoadAction",
			data : {
				databaseName : mlist.setting.databaseName,
				tableName : mlist.setting.tableName,
				idcolumn : mlist.setting.idcolumn,
				title : mlist.setting.title.column,
				centexts : mlist.setting.texts.column,
				ellips : mlist.setting.ellips.column,
				staticfilter : mlist.setting.staticfilter,
				orderby : mlist.setting.orderby,
				pagelimit : mlist.setting.pagelimit
			},
			dataType: "json",
			success : function(result, textStatus) {
				if (result.count) {
					$("#list_count").text(result.count);
				}
				var data = result.data.data;
				if (result.count > 0) {
					mlist.setting.divObj.html(mlist.createList(data));
					$("li[rel='list']").click(function() {
						var m = $(this).attr("id");
						mlist.setting.onclick(m);
					});
					if (result.count > mlist.setting.pagelimit) {
						$("#" + mlist.setting.divID + "_moreBtn").click(
								function() {
									mlist.loadMore();
								});
					}
				} else {
					mlist.setting.divObj
							.html("<div style='color:red;width:100%;padding:10px;text-align:center;'>没有任何数据!</div>");
				}
			},
			error : function() {
				// 请求出错处理
			}
		});
	};
	this.doSearch = function(searchtext) {
		var mlist = this;
		this.currentpage = 1;
		if (!searchtext || searchtext == "") {
			searchtext = "";
		}
		mlist.searchfilter = mlist.setting.title.column + " like '%"
				+ searchtext + "%'  or " + mlist.setting.texts.column
				+ " like '%" + searchtext + "%'";
		$.mobile.showPageLoadingMsg();
		$.ajax({
			type : "post",
			async : true,
			url : cpath+"/mobileListLoadAction",
			data : {
				databaseName : mlist.setting.databaseName,
				tableName : mlist.setting.tableName,
				idcolumn : mlist.setting.idcolumn,
				title : mlist.setting.title.column,
				centexts : mlist.setting.texts.column,
				ellips : mlist.setting.ellips.column,
				staticfilter : mlist.setting.staticfilter,
				orderby : mlist.setting.orderby,
				pagelimit : mlist.setting.pagelimit,
				filter : mlist.searchfilter
			},
			dataType: "json",
			success : function(result, textStatus) {
				if (result.count) {
					$("#list_count").text(result.count);
				}
				var data = result.data.data;
				if (result.count > 0) {
					mlist.setting.divObj.html(mlist.createList(data));
					$("li[rel='list']").click(function() {
						var m = $(this).attr("id");
						mlist.setting.onclick(m);
					});
					if (result.count > mlist.setting.pagelimit) {
						$("#" + mlist.setting.divID + "_moreBtn").click(
								function() {
									mlist.loadMore();
								});
					}
				} else {
					mlist.setting.divObj
							.html("<div style='color:red;width:100%;padding:10px;text-align:center;'>没有任何数据!</div>");
				}
				$.mobile.hidePageLoadingMsg();
			},
			error : function() {
			}
		});
	};
	this.loadMore = function() {
		var mlist = this;
		mlist.currentpage++;
		$.ajax({
			type : "post",
			async : true,
			url : cpath+"/mobileListLoadAction",
			data : {
				databaseName : mlist.setting.databaseName,
				tableName : mlist.setting.tableName,
				idcolumn : mlist.setting.idcolumn,
				title : mlist.setting.title.column,
				centexts : mlist.setting.texts.column,
				ellips : mlist.setting.ellips.column,
				staticfilter : mlist.setting.staticfilter,
				orderby : mlist.setting.orderby,
				pagelimit : mlist.setting.pagelimit,
				currentpage : mlist.currentpage,
				filter : mlist.searchfilter
			},
			dataType: "json",
			success : function(result, textStatus) {
				var data = result.data.data;
				if (result.count > 0) {
					$(mlist.createList(data)).insertBefore(
							$("#" + mlist.setting.divID + "_moreBtn"));
					$("li[rel='list']").click(function() {
						var m = $(this).attr("id");
						mlist.setting.onclick(m);
					});
					if (result.count > mlist.setting.pagelimit) {
						$("#" + mlist.setting.divID + "_moreBtn").click(
								function() {
									mlist.loadMore();
								});
					}
				} else {
					mlist.setting.divObj
							.html("<div style='color:red;width:100%;padding:10px;text-align:center;'>没有任何数据!</div>");
				}
			},
			error : function() {
				// 请求出错处理
			}
		});
	};
};

/**
*以下为了兼容云捷代码
*/
justep.yn = tlv8;