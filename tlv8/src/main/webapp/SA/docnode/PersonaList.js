var data = new tlv8.Data();
data.setTable("PERSONAL_FILE");
data.setDbkey("system");
var currentgrid = null;
var $maingridview;
var doc_dowload_item;
// document元素加载完成之后
$(document).ready(function() {
	creategridFn();
});

function creategridFn() {
	$maingridview = $("#maingridview");
	var labelid = "No,SFILENAME,SFILESIZE,SDOCPATH,SCREATORNAME,SFILEID";
	var labels = "No.,文件名,文件大小,下载路径,上传人员,SFILEID";
	var labelwidth = "40,200,120,200,150,0";
	var datatype = "ro,html:readTitle,ro,ro,ro,ro";// 设置字段类型
	var dataAction = {
		"queryAction" : "getGridAction",
		"savAction" : "",
		"deleteAction" : ""
	};
	var maingrid = new tlv8.createGrid($maingridview[0], labelid, labels,
			labelwidth, dataAction, "100%", "100%", data, 20, "", "", "",
			datatype, "false", "true");
	$("#" + $maingridview[0].id + "_toolbar").css("display", "none");// 禁用工具条
	// maingrid.grid.settoolbar(false, false, false, false);
	maingrid.grid.seteditModel(false);
	currentgrid = maingrid.grid;
	var portalrowid = tlv8.RequestURLParam.getParam("portalrowid");
	if (portalrowid == undefined) {
		var loginId = tlv8.Context.getCurrentPersonID();
		var filter = "SCREATORID like '%" + loginId
				+ "%' or SACCESSCURRENTID in ('" + loginId + "')";
		currentgrid.refreshData(filter);
	} else {
		var filter = "SID like '%" + portalrowid + "%'";
		currentgrid.refreshData(filter);
	}
	$("#looks").bind("keydown", function(event) {
		if (event.keyCode == 13)
			lookselect();
	});
}

function readTitle(event){
	return event.value;
}

// 下载
function downloadFile() {
	var rowid = currentgrid.CurrentRowId;
	var SFILEID = currentgrid.getValueByName("SFILEID", rowid);
	if (!SFILEID || SFILEID == "")
		return;
	justep.Doc.downloadDocByFileID("/root", SFILEID);
}

// 查询
function lookselect() {
	var text = $("#looks").val();
	var loginId = tlv8.Context.getCurrentPersonID();
	var filter = "SFILENAME like '%" + trim(text) + "%' and SCREATORID like '%"
			+ loginId + "%' or SACCESSCURRENTID in ('" + loginId + "')";
	currentgrid.refreshData(filter);
}