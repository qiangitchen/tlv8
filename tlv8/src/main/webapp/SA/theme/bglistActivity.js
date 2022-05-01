/*==数据源===此项为必须定义==*/
var data = new tlv8.Data();
data.setDbkey("system");// 指定使用数据库连接
data.setTable("SA_POTALTHEMELGBG");// 指定grid对应的表

/* ==== */
var currentgrid;
var enablebutton, disablebutton;
function getData() {
	var d = document.getElementById("main-grid-view");
	var labelid = "No,SNAME,SIMAGE,SENABLED";// 设置字段
	var labels = "No.,名称,图片文件,状态";// 设置标题
	var labelwidth = "40,180,280,80";// 设置宽度
	var datatype = "ro,string,html:readImageInfo,html:readstatus";// 设置字段类型
	var dataAction = {
		"queryAction" : "getGridAction",// 查询动作
		"savAction" : "saveAction",// 保存动作
		"deleteAction" : "deleteAction"// 删除动作
	};
	var maingrid = new tlv8.createGrid(d, labelid, labels, labelwidth,
			dataAction, "100%", "100%", data, 20, "", "", "", datatype,
			"false", "true");
	// 设置按钮显示{新增、保存、刷新、删除}
	maingrid.grid.settoolbar(true, false, true, "readonly");
	// 设置是否可编辑
	maingrid.grid.seteditModel(false);
	currentgrid = maingrid.grid;
	currentgrid.refreshData();// 刷新数据

	document.getElementById(d.id + "_insertItem").onclick = function() {
		tlv8.portal.openWindow('添加背景图片',
				'/SA/theme/bglistActivityDetail.html?option=new');
	};

	enablebutton = currentgrid.insertSelfBar("启用", "40px", "",
			"../../comon/image/toolbar/standard_toolbar/image/un_enable.gif");
	disablebutton = currentgrid.insertSelfBar("停用", "40px", "",
			"../../comon/image/toolbar/standard_toolbar/image/un_disable.gif");
}

function griddbclick(event) {
	var rowid = currentgrid.getCurrentRowId();
	tlv8.portal.openWindow('列表详细',
			'/SA/theme/bglistActivityDetail.html?rowid=' + rowid);
}

function readstatus(event) {
	if (event.value == "1") {
		return "已启用";
	} else {
		return "未启用";
	}
}

function readImageInfo(event) {
	try {
		var filejson = transeJson(event.value);
		var url = "image/Picread.jsp?fileID=" + filejson[0].fileID;
		return "<div class='shortImage' src='" + url
				+ "' style='width:98%;height:98%;'>" + filejson[0].filename
				+ "</div>";
	} catch (e) {
		return "没有上传图片";
	}
}
var transeJson = function(str) {
	str = str.toString().replaceAll(":", ":\"");
	str = str.toString().replaceAll(",", "\",");
	str = str.toString().replaceAll("}", "\"}");
	str = str.toString().replaceAll("}{", "},{");
	str = str.toString().replaceAll(";", "\",");
	var filelist = eval("([" + str + "])");
	return filelist;
};

function afterdatarefreshed(event) {
	var x = 10;
	var y = 20;
	$(".shortImage").mouseover(
			function(e) {
				this.myTitle = this.title || "";
				this.title = "";
				var imgTitle = this.myTitle ? "<br/>" + this.myTitle : "";
				var imgUrl = $(this).attr("src");
				var tooltip = "<div id='tooltip'><img src='" + imgUrl
						+ "' alt='模板预览图'/>" + imgTitle + "</div>"; // 创建 div 元素
				$("body").append(tooltip); // 把它追加到文档中
				$("#tooltip").css({
					"width" : "500px",
					"height" : "200px",
					"top" : (e.pageY + y) + "px",
					"left" : (e.pageX + x) + "px"
				}).show("fast"); // 设置x坐标和y坐标，并且显示
			}).mouseout(function() {
		this.title = this.myTitle;
		$("#tooltip").remove(); // 移除
	}).mousemove(function(e) {
		$("#tooltip").css({
			"top" : (e.pageY + y) + "px",
			"left" : (e.pageX + x) + "px"
		});
	});
}

/**
 * @param {object}
 *            event
 */
function gridrowselected(event) {
	var enable = event.getValueByName("SENABLED", event.getCurrentRowId());
	if (enable == "1") {
		$("#" + enablebutton)
				.attr("src",
						"../../comon/image/toolbar/standard_toolbar/image/un_enable.gif");
		$("#" + disablebutton).attr("src",
				"../../comon/image/toolbar/standard_toolbar/image/disable.gif");
		$("#" + disablebutton).click(
				function() {
					currentgrid.setValueByName("SENABLED", currentgrid
							.getCurrentRowId(), "-1");
					currentgrid.saveData();
				});
		currentgrid.settoolbar("no", false, "no", "readonly");
	} else {
		$("#" + enablebutton).attr("src",
				"../../comon/image/toolbar/standard_toolbar/image/enable.gif");
		$("#" + enablebutton).click(
				function() {
					currentgrid.setValueByName("SENABLED", currentgrid
							.getCurrentRowId(), "1");
					currentgrid.saveData();
				});
		$("#" + disablebutton)
				.attr("src",
						"../../comon/image/toolbar/standard_toolbar/image/un_disable.gif");
		currentgrid.settoolbar("no", false, "no", true);
	}
}