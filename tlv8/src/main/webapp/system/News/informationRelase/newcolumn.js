//发布新闻
//var xheditor = null;
var random = Math.random();
var refresh = random + "=" + random;// 页面刷新

var data = new tlv8.Data();
data.setTable("cyea_news_release");
data.setDbkey("system");
var currentgrid = null;

var kindEditor1;
function creatTextEditor(){
	kindEditor1 = KindEditor.create('textarea[name="FRELEASECONNEXT"]', {
		cssPath : '/tlv8/comon/kindeditor/plugins/code/prettify.css',
		uploadJson : '/tlv8/kindEditorUploadAction',
		fileManagerJson : '/tlv8/comon/kindeditor/jsp/file_manager_json.jsp',
		allowFileManager : true,
		afterCreate : function() {
			var self = this;
			KindEditor.ctrl(document, 13, function() {
				self.sync();
			});
			KindEditor.ctrl(self.edit.doc, 13, function() {
				self.sync();
			});
		}
	});
	prettyPrint();
}

function news_load() {
	data.setFormId("news_release");// 设置提交的表单
	$("#FNEWSTITLE").keyup(function() {
		var value = $(this).val();
		if (value == "") {
			$(this).addClass("ReadOnly");
		} else {
			$(this).removeClass("ReadOnly");
		}
	});
	
	// 初始化富文本域组件
//	var htmlediterparam = {
//		tools : "simple",
//		upImgUrl : "xhUpload",
//		html5Upload: false,
//		upImgExt : "jpg,jpeg,gif,png"
//	};
//	xheditor = $("#FRELEASECONNEXT").xheditor(htmlediterparam);
	
	creatTextEditor();
	
	var rowid = tlv8.RequestURLParam.getParam("rowid");
	if (rowid && rowid != "") {
		maindata_refersh("SID = '" + rowid + "'" + " and " + refresh);
		document.getElementById("news_release").rowid = rowid;
		data.setFilter("SID='" + rowid + "'");
		data.refreshData();
		// 动态加载附件组件
		var cellname = "FACCESSORIES";// 设置附件相关字段
		var fileComp = new tlv8.fileComponent(document
				.getElementById("fileCompDiv"), data, cellname);
		// 动态加载栏目信息
		var gdiv = document.getElementById("FCOLUMNNAME");
		var sql = "select FCOLUMNNAME from cyea_newscolumn where FCOLUMNSTATE='已启用'"
				+ " and " + refresh;// TMJ_NEWSCOLUMN
		var GridSelect = new tlv8.GridSelect(gdiv, "system", sql);// tmjcrm
		return;
	} else {
		// 初始化附件组件
		var cellname = "FACCESSORIES";// 设置附件相关字段
		var fileComp = new tlv8.fileComponent(document
				.getElementById("fileCompDiv"), data, cellname);
	}
	document.getElementById("news_release").reset();
	var FRELEASEDEPARTMENT = tlv8.Context.getCurrentDeptName();
	if (FRELEASEDEPARTMENT != "null") {
		document.getElementById("FRELEASEDEPARTMENT").value = FRELEASEDEPARTMENT;
	}
	document.getElementById("FPEOPLE").value = tlv8.Context
			.getCurrentPersonName();
	document.getElementById("FTIME").value = tlv8.System.Date
			.sysDateTime();
	document.getElementById("FSETTOPTIME").value = tlv8.System.Date
			.sysDateTime().substring(0, 10);
	document.getElementById("FSTATE").value = "编辑中";
	// 初始化加载栏目信息
	var gdiv = document.getElementById("FCOLUMNNAME");
	var sql = "select FCOLUMNNAME from cyea_newscolumn where FCOLUMNSTATE='已启用'"
			+ " and " + refresh;// TMJ_NEWSCOLUMN
	var GridSelect = new tlv8.GridSelect(gdiv, "system", sql);// tmjcrm
	// 验证必填样式
}


//数据刷新之后
function afterDatarefresh(event){
	//creatTextEditor();
	kindEditor1.html($("#FRELEASECONNEXT").val());
}

/*
 * 缩略图
 */
function minimap() {
	var rowid = trim(document.getElementById("news_release").rowid);
	if (rowid == "" || typeof (rowid) == "undefined" || rowid == null) {
		alert("请先保存新闻！");
	} else {
		var saverowid = document.getElementById("news_release").rowid;
		tlv8.portal.dailog.openDailog('添加缩略图',
				'/system/News/informationRelase/newsMiniMap.html?rowid='
						+ saverowid, 600, 460, null, null);

	}
}

/*
 * 保存
 */
function save_news() {
//	var content = kindEditor1.html(); //xheditor.getSource();
//	document.getElementById("FRELEASECONNEXT").value = content;
	
	$("#FRELEASECONNEXT").val(kindEditor1.html());
	
	if (document.getElementById("FNEWSTITLE").value == ""
			|| document.getElementById("FRELEASECONNEXT").value == "") {
		alert("新闻标题和内容不能为空！！！");
		document.getElementById("FNEWSTITLE").focus();
		return false;
	} else {
		var savarowid = data.saveData();
		document.getElementById("news_release").rowid = savarowid;// 记住当前rowid;避免重复创建
	}
	return true;
}

/*
 * 查询新闻
 */
function news_select() {
	var maingrid = document.getElementById("news_list_grid").grid;
	var srcFNEWSTITLE = document.getElementById("srcFNEWSTITLE").value;
	var srcFPEOPLE = document.getElementById("srcFPEOPLE").value;
	var srcFTIME = document.getElementById("srcFTIME").value;
	var filter = " 1=1 ";
	if (srcFNEWSTITLE && srcFNEWSTITLE != "") {
		filter += " and FNEWSTITLE like '%" + trim(srcFNEWSTITLE) + "%' ";
	}
	if (srcFPEOPLE && srcFPEOPLE != "") {
		filter += " and FPEOPLE like '%" + trim(srcFPEOPLE) + "%' ";
	}
	if (srcFTIME && srcFTIME != "") {
		filter += " and  FTIME>='" + trim(srcFTIME) + "'";
	}
	maingrid.refreshData(filter);
}

/*
 * 数据刷新 @param {Object} filter
 */
function maindata_refersh(filter) {
	var maindata = new tlv8.Data();
	maindata.setTable("CYEA_NEWS_RELEASE");// 设置保存的表TMJ_NEWS_RELEASE
	maindata.setFormId("news_release");// 设置提交的表单
	maindata.setDbkey("system");// 指定使用数据库连接tmjcrm
	document.getElementById("news_release").rowid = "";// 清空ID
	document.getElementById("news_release").reset();// 重置表单
	filter = filter ? filter : "";
	maindata.setFilter(filter);
	maindata.refreshData();
}

// 新闻发布
function publishServey() {
	var rowid = trim(document.getElementById("news_release").rowid);
	if (rowid == "" || typeof (rowid) == "undefined" || rowid == null) {
		alert("请先保存新闻！");
	} else {
		var sqlStr = "update CYEA_NEWS_RELEASE set FSTATE='已发布' where SID='"
				+ rowid + "'";
		var pam = new tlv8.RequestParam();
		pam.set("sql", sqlStr);
		tlv8
				.XMLHttpRequest(
						"updateCeaseOrganAction",
						pam,
						"post",
						true,
						function(data) {
							var r = eval(data.data);
							if (r.flag == "true") {
								document.getElementById("FSTATE").value = "已发布";
								document.getElementById("btnSueveyPublish").disabled = true;
							} else {
								alert("结束新闻操作失败：");
							}
						});
	}
}

// 栏目信息设置对话框
function getcolumnDailog() {
	tlv8.portal.dailog.openDailog('选择/添加栏目',
			'/system/News/informationRelase/newColumnDilay.html', 650, 450,
			null, null);
}

// 选择是否置顶
function gettop() {
	var rt = document.getElementById("FSETTOPWHETHER");
	if (rt[rt.selectedIndex].value == "0") {
		document.getElementById("kk").style.display = "none";
		document.getElementById("FSETTOPTIME").style.display = "none";
		document.getElementById("FSETENDTIME").style.display = "none";
		document.getElementById("yy").style.display = "none";
		document.getElementById("FSETTOPTIME").value = "";
	} else {
		document.getElementById("kk").style.display = "";
		document.getElementById("FSETTOPTIME").style.display = "";
		document.getElementById("FSETENDTIME").style.display = "";
		document.getElementById("yy").style.display = "";
	}
}

// 置顶判断设置
function settime() {
	var selectsql = "select FSETENDTIME from CYEA_NEWS_RELEASE where FSETTOPWHETHER='1'";
	var res = tlv8.sqlQueryAction("system", selectsql);
	if (res.getValueByName("FSETENDTIME").substring(0, 10) == tlv8.System.Date
			.sysDateTime().substring(0, 10)) {
		var sqlstr = "update CYEA_NEWS_RELEASE set FSETTOPWHETHER = '0' where FSETTOPWHETHER='1'";
		var res = tlv8.sqlUpdateAction("system", sqlstr);
	}
}

// 发布范围
function SelectDept() {
	var FOPENSCOPEID = $("#FOPENSCOPEID").val();
	var FOPENSCOPE = $("#FOPENSCOPE").val();
	var param = {
		FOPENSCOPEID : FOPENSCOPEID,
		FOPENSCOPE : FOPENSCOPE
	};
	tlv8.portal.dailog.openDailog('选择范围',
			"/comon/SelectDialogPsn/select-org-dialog.html", 650, 420,
			DepScopeCallback, null, null, param);
}

// 回调函数
function DepScopeCallback(reDataMap) {
	var keySet = reDataMap.keySet();
	var FOPENSCOPEID = "";
	var FOPENSCOPE = "";
	for ( var i in keySet) {
		if (i > 0) {
			FOPENSCOPEID += ",";
			FOPENSCOPE += ",";
		}
		FOPENSCOPEID += keySet[i];
		FOPENSCOPE += reDataMap.get(keySet[i]);
	}
	$("#FOPENSCOPE").val(FOPENSCOPE);
	$("#FOPENSCOPEID").val(FOPENSCOPEID);
}

// 预览
function viewSurvey() {
	if (save_news()) {
		tlv8.portal.openWindow("新闻预览",
				"/system/News/informationRelase/news.html?rowid="
						+ data.rowid);
	}
}