var data = new tlv8.Data();
data.setTable("PERSONAL_FILE");
data.setDbkey("system");
var maingrid = null;
var currentgrid = null;
var $maingridview;
var doc_upload_item, doc_dowload_item, doc_delete_item, doc_share_item;
var uploader = null;

// 构建树
var param = {
	cell : {
		id : "SID",// 设置构建树的id
		name : "SPARENTNAME",// 树显示的名称
		parent : "SPARENTID",// 表示树的层级
		other : "SPATH",
		tableName : "PERSONALDOCNODE",// 对应的表名
		databaseName : "system",// 数据库
		rootFilter : " (SPARENTID is null or SPARENTID='root') and SCREATORID='"
				+ tlv8.Context.getCurrentPersonID() + "'",
		filter : "SCREATORID='" + tlv8.Context.getCurrentPersonID() + "'"
	}
};
// 设置树的属性
var setting = {
	data : {
		simpleData : {
			enable : true
		}
	},
	async : {
		enable : true,
		url : "TreeSelectAction",
		autoParam : [ "id=currenid" ],
		type : "post"
	},
	isquickPosition : {
		enable : false, // 是否有快速查询框
		url : "QuickTreeAction",
		path : "SPATH"// 查询路径字段

	},
	callback : {
		onClick : treeselected
	}

};
var sJtree = new Jtree();
// 页面初始化
$(function() {
	sJtree.init("maintree", setting, param);
	
	$maingridview = $("#gridmainView");
	var labelid = "No,SFILENAME,SFILESIZE,SDOCPATH,SCREATORNAME,SFILEID,SCREATORID";
	var labels = "No.,文件名,文件大小(KB),下载路径,上传人员,SFILEID,SCREATORID";
	var labelwidth = "40,200,120,200,150,0,0";
	var datatype = "ro,html:readerName,ro,ro,ro,ro,ro";// 设置字段类型
	var dataAction = {
		"queryAction" : "getGridAction",
		"savAction" : "saveAction",
		"deleteAction" : "deleteAction"
	};
	var maingrid = new tlv8.createGrid($maingridview[0], labelid, labels,
			labelwidth, dataAction, "100%", "100%", data, 20, "",
			"docnodeframe", "SMASTERID", datatype, "false", "true");
	maingrid.grid.settoolbar(false, false, true, false);
	maingrid.grid.seteditModel(false);
	currentgrid = maingrid.grid;
	doc_upload_item = currentgrid.insertSelfBar("上传文件", "30px", "",
			"../../comon/image/toolbar/doc/newfile.gif");
	doc_dowload_item = currentgrid.insertSelfBar("下载文件", "30px", "dowload()",
			"../../comon/image/toolbar/doc/download.gif");
	doc_delete_item = currentgrid.insertSelfBar("删除", "30px", "",
			"../../comon/image/toolbar/un_remove.gif");
	doc_share_item = currentgrid.insertSelfBar("共享", "30px", "shareview()",
			"image/browse.gif");
});

function readerName(event){
	return ""+event.value+"";
}

function selectedItem() {
	J$(doc_delete_item).onclick = deleteCnOde;
	$("#" + doc_delete_item)
			.attr("src", cpath+"/comon/image/toolbar/remove.gif");
}

// 单击树节点
var treeRowid = "", parentid = "";
function treeselected(event, treeId, treeNode) {
	$("#newedit").removeAttr("disabled");
	$("#editnew").removeAttr("disabled");
	$("#deletenew").removeAttr("disabled");
	$("#sharenew").removeAttr("disabled");
	treeRowid = treeNode.id;
	parentid = treeNode.SPATH;
	J$("docnodeframe").rowid = treeRowid;
	createUploadElement();
	currentgrid.refreshData();
}

var upfileCount = 0;
function createUploadElement() {
//	if (!uploader) {
//		var uploadDocItemDiv = document.createElement("div");
//		uploadDocItemDiv.setAttribute("id", "uploadDocItemDiv", null);
//		uploadDocItemDiv.style.position = "absolute";
//		var uploadMenuItem = J$(doc_upload_item);
//		uploadMenuItem.parentElement.insertBefore(uploadDocItemDiv,
//				uploadMenuItem);
//		uploader = tlv8.Doc.getUploader("uploadDocItemDiv", "/root", -1,
//				CimitDataParam, function() {
//				}, 60, 22, 1, undefined, true);
//		$('#uploadDocItemDiv').css('height', '22px');
//		$('#uploadDocItemDiv').css('width', '60px');
//	} else {
//		uploader.setDocPath("/root");
//	}
    var docPath = "/root/个人文件柜"+tlv8.Context.getCurrentPersonFName();
	if(!uploader){
		uploader = layui.upload.render({
			elem: '#'+doc_upload_item
			,url: cpath+'/sa/layuiPersonalDocUpload'
			,accept: 'file'
			,multiple: true
			,dataType: 'json'
			,data: {docPath:docPath,mainid:treeRowid}
			,done: function(res, index, upload){
				if(res.code != '-1'){
					currentgrid.refreshData();
				}else{
					alert(res.msg);
				}
			}
			,error: function(index, upload){
				alert("上传失败！");
			}
		});
	}else{
		uploader.config.data =  {docPath:docPath,mainid:treeRowid};
	}
}

function CimitDataParam(docName, kind, size, cacheName, revisionCacheName,
		commentFileContent, filecount) {
	var docPath = "/root/个人文件柜" + tlv8.Context.getCurrentPersonFName();
	var newid = currentgrid.insertData();
	currentgrid.setValueByName("SDOCPATH", newid, docPath);
	currentgrid.setValueByName("SFILENAME", newid, docName);
	currentgrid.setValueByName("SFILESIZE", newid, transB2KB(size));
	currentgrid.setValueByName("SFILEID", newid, cacheName);
	currentgrid.setValueByName("SCREATORID", newid, tlv8.Context
			.getCurrentPersonID());
	currentgrid.setValueByName("SCREATORNAME", newid, tlv8.Context
			.getCurrentPersonName());
	currentgrid.saveData();
	var pa_log = {};
	pa_log.dbkey = "system";
	pa_log.docPath = docPath;
	pa_log.tablename = "PERSONAL_FILE";
	pa_log.cellname = "SACCESSORY";
	pa_log.rowid = newid;
	pa_log.docName = docName;
	pa_log.kind = kind;
	pa_log.size = size;
	pa_log.cacheName = cacheName;
	var jd = [];
	jd.push(pa_log);
	var paramlog = JSON.stringify(jd);
	var pas = new tlv8.RequestParam();
	pas.set("writelog", paramlog);
	tlv8.XMLHttpRequest("writeUploadDataAction", pas, "POST", false);
	upfileCount++;
	if (upfileCount == filecount) {
		currentgrid.refreshData();
		upfileCount = 0;
	}
}

function transB2KB(aa) {
	if (aa == '') {
		return;
	}
	var tempValue = aa;
	var resultValue = "";
	var tempValueStr = new String(tempValue);
	if ((tempValueStr.indexOf('E') != -1) || (tempValueStr.indexOf('e') != -1)) {
		var regExp = new RegExp('^((\\d+.?\\d+)[Ee]{1}(\\d+))$', 'ig');
		var result = regExp.exec(tempValue);
		var power = "";
		if (result != null) {
			resultValue = result[2];
			power = result[3];
			result = regExp.exec(tempValueStr);
		}
		if (resultValue != "") {
			if (power != "") {
				var powVer = Math.pow(10, power);
				resultValue = resultValue * powVer / 1000;
			}
		}
		return parseInt(resultValue) + 1;
	} else {
		if (tempValue == 0)
			return parseInt(tempValue) + '';
		return parseInt(tempValue / 1000) + 1;
	}
}

// 新增
function openview() {
	tlv8.portal.dailog.openDailog('新建目录',
			"/SA/docnode/dialog/newEdit.html?treeRowid=" + treeRowid
					+ "&spath=" + parentid, 400, 240, creat_dailogcallback,
			null);
}
// 回调
function creat_dailogcallback(event) {
	sJtree.refreshJtree("maintree");

}
// 修改
function editview() {
	tlv8.portal.dailog.openDailog('修改目录',
			"/SA/docnode/dialog/newEdit.html?Rowid=" + treeRowid, 400, 240,
			creat_dailogcallback, null);
}
// 删除
function deleteview() {
	if (!confirm("删除文件夹，删除后不能恢复，确定删除吗?"))
		return;
	var pam = new tlv8.RequestParam();
	pam.set("treeRowid", treeRowid);
	tlv8.XMLHttpRequest("persondocnode/deleteFolder", pam, "post", false);
	sJtree.refreshJtree("maintree");
}

function resetForm() {
	document.getElementById("accessoryform").reset();
}

// 下载
function dowload() {
	var rowid = currentgrid.CurrentRowId;
	var SFILEID = currentgrid.getValueByName("SFILEID", rowid);
	if (!SFILEID || SFILEID == "")
		return;
	tlv8.Doc.downloadDocByFileID("/root", SFILEID);
}

// 删除
function deleteCnOde() {
	var rowid = currentgrid.getCurrentRowId();
	var SFILEID = currentgrid.getValueByName("SFILEID", rowid);
	if (rowid) {
		if (confirm("文件删除后不能撤销，确定删除文件吗?")) {
			var pam = new tlv8.RequestParam();
			pam.set("rowid", rowid);
			pam.set("fileid", SFILEID);
			tlv8.XMLHttpRequest("persondocnode/deleteFileData", pam, "post", false);
			currentgrid.refreshData();
		}
	}
}

// 共享
function shareview() {
	tlv8.portal.dailog.openDailog('共享范围',
			'/comon/SelectDialogPsn/SelectChPsm.html', 650, 420,
			DepScopeCallback, null);
}

// 回调
function DepScopeCallback(event) {
	var rowid = currentgrid.getCurrentRowId();
	var saccessid = event.id;
	var saccessname = event.name;
	var upsqlstr = "UPDATE  PERSONAL_FILE SET SACCESSCURRENTID='" + saccessid
			+ "',SACCESSCURRENTNAME='" + saccessname + "' WHERE SID='" + rowid
			+ "' ";
	var pam = new tlv8.RequestParam();
	pam.set("sql", upsqlstr);
	tlv8.XMLHttpRequest("updateCeaseOrganAction", pam, "post", true,
			function(data) {
				var r = eval(data.data);
				if (r.flag == "false") {
					alert(r.message);
				} else {
					sAlert("操作成功!", 500);
				}
			});
}