var uploader = null;
var docAuthList = null;
var currentRootAccess = null;
var docAuthListArr = null;

var data = new tlv8.Data();
data.setTable("SA_DOCNODE");
data.setDbkey("system");
data.setOrderby("SCREATETIME desc");

var maingrid = null, currentgrid = null, doc_upload_item, doc_dowload_item;
var editer_item, docHistory_item, docDownloadRecord_item, browseDoc_item;

/* 创建树 */
var param = {
	cell : {
		id : "SID",// 设置构建树的id
		name : "SDOCNAME",// 树显示的名称
		parent : "SPARENTID",// 表示树的层级
		other : "SKIND,SDOCPATH,SDOCDISPLAYPATH,SCREATORNAME,SNAMESPACE,SCREATORFID",// "SFID,SFNAME,SORGKINDID",
		tableName : "SA_DOCNODE",// 对应的表名
		databaseName : "system",// 数据库
		rootFilter : "SPARENTID is null",// 跟节点条件
		filter : "SKIND = 'dir'"
	},
	action : "TreeSelectAction"
};
// 设置树的属性
var setting = {
	view : {
		selectedMulti : false, // 设置是否允许同时选中多个节点。默认值: true
		autoCancelSelected : false,
		dblClickExpand : true
	},
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
		path : "SDOCPATH"
	},
	callback : {
		onClick : treeselected
	}

};
function getData() {
	var d = J$("main-grid-view");
	var labelid = "No,SDOCNAME,SSIZE,SFILEID,SDOCPATH,SDOCDISPLAYPATH,SCREATORNAME,SCREATORDEPTNAME,SCREATETIME"
			+ ",SCACHENAME,SDOCLIVEVERSIONID,SNAMESPACE,SDOCSERIALNUMBER,SFINISHTIME,SKEYWORDS,SCLASSIFICATION,SDESCRIPTION,SKIND,SCREATORFID,SFLAG";
	var labels = "NO.,名称,文件大小(KB),文件ID,SDOCPATH,文件路径,创建人,创建部门,创建时间"
			+ ",SCACHENAME,SDOCLIVEVERSIONID,SNAMESPACE,SDOCSERIALNUMBER,SFINISHTIME,SKEYWORDS,SCLASSIFICATION,SDESCRIPTION,SKIND,SCREATORFID,SFLAG";
	var labelwidth = "40,100,110,100,0,200,100,100,120"
			+ ",0,0,0,0,0,0,0,0,0,0,0";
	var datatype = "ro,string,string,string,string,CLOB,string,string,datetime"
			+ ",ro,ro,ro,ro,ro,ro,ro,ro,ro,ro,ro";// 设置字段类型
	var dataAction = {
		"queryAction" : "getGridAction",
		"savAction" : "saveAction",
		"deleteAction" : "docCenterDeleteFileAction"
	};
	var where = " SKIND != 'dir' and sID != 'root' and (SFILEID is not null or SFILEID !='') and SDOCNAME !='Message' ";
	maingrid = new tlv8.createGrid(d, labelid, labels, labelwidth, dataAction,
			"100%", "100%", data, 20, where, "main_org_trr", "SPARENTID",
			datatype, false, true);
	// 设置按钮显示{新增、保存、刷新、删除}
	maingrid.grid.settoolbar(false, false, true, "readonly");
	// 设置是否可编辑
	maingrid.grid.seteditModel(false);
	currentgrid = maingrid.grid;
	J$("main-grid-view_insertItem").onclick = function() {
		// newOrgData();
	};
	doc_upload_item = currentgrid.insertSelfBar("上传文件", "70px", "",
			"../../../comon/image/toolbar/doc/newfile_g.gif");
	editer_item = currentgrid.insertSelfBar("编辑文件", "40px", "",
			"../../../comon/image/doc/edit_file_g.gif");
	doc_dowload_item = currentgrid.insertSelfBar("下载文件", "50px", "",
			"../../../comon/image/toolbar/doc/download_g.gif");
	browseDoc_item = currentgrid.insertSelfBar("查看文件", "30px", "",
			"../../../comon/image/doc/browse_g.gif");
	docDownloadRecord_item = currentgrid.insertSelfBar("下载记录", "30px", "",
			"../../../comon/image/doc/download_record_g.gif");
	docHistory_item = currentgrid.insertSelfBar("历史版本", "30px", "",
			"../../../comon/image/doc/modify_record_g.gif");
}

function upload(docName, kind, size, cacheName, revisionCacheName,
		commentFileContent) {
	var nRowId = currentgrid.insertData();
	currentgrid.setValueByName("SDOCNAME", nRowId, docName);
	currentgrid.setValueByName("SSIZE", nRowId, transB2KB(size));
	currentgrid.setValueByName("SCACHENAME", nRowId, cacheName);
	currentgrid.setValueByName("SKIND", nRowId, kind);
	currentgrid.setValueByName("SDOCPATH", nRowId, currenttreeSDOCPATH
			+ cacheName);
	currentgrid.setValueByName("SDOCDISPLAYPATH", nRowId,
			currenttreeSDOCPATHName + docName);
	currentgrid.setValueByName("SCREATORFID", nRowId, tlv8.Context
			.getCurrentPersonFID());
	currentgrid.setValueByName("SCREATORNAME", nRowId, tlv8.Context
			.getCurrentPersonName());
	currentgrid.setValueByName("SCREATORDEPTNAME", nRowId, tlv8.Context
			.getCurrentOrgName());
	currentgrid.setValueByName("SCREATETIME", nRowId, tlv8.System.Date
			.sysDateTime());
	currentgrid.setValueByName("SDOCLIVEVERSIONID", nRowId, "0");
	currentgrid
			.setValueByName("SNAMESPACE", nRowId, currenttreeNode.SNAMESPACE);
	currentgrid.setValueByName("SFLAG", nRowId, "1");
	currentgrid.saveData();
	var param = new tlv8.RequestParam();
	param.set("docid", nRowId);
	tlv8.XMLHttpRequest("docCommitFile", param, "post", true, function() {
		currentgrid.refreshData();
	});
}

function createUploadElement() {
	if (!uploader) {
		uploader = layui.upload.render({
			elem : '#' + doc_upload_item,
			url : cpath + '/utils/layuiFileUploadAction',
			accept : 'file',
			multiple : true,
			dataType : 'json',
			data : {
				docPath : currenttreeSDOCPATHName
			},
			done : function(res, index, upload) {
				if (res.code != '-1') {
					currentgrid.refreshData();
				} else {
					alert(res.msg);
				}
			},
			error : function(index, upload) {
				alert("上传失败！");
			}
		});
	} else {
		uploader.config.data = {
			docPath : currenttreeSDOCPATHName
		};
	}
}

function createTransportElement(containerID, objectID) {
	if (!J$(objectID)) {
		var transport = J$(containerID);
		transport.outerHTML = '<object id="'
				+ objectID
				+ '" classid="clsid:9E4A15C5-61F4-4EC7-9B5F-7DE2620629BB" style="width:100%"'
				+ 'codebase=' + cpath
				+ '"/comon/doc_ocx/transport/x5_file_mng.cab#version="'
				+ _ocx_version + ' style="display:none;" >' + '</object>';
	}
}

function refreshFile() {
	currentgrid.refreshData();
}

function doc_dowload() {
	var rowid = currentgrid.CurrentRowId;
	var SFILEID = currentgrid.getValueByName("SFILEID", rowid);
	var SDOCNAME = currentgrid.getValueByName("SDOCNAME", rowid);
	var docPath = currentgrid.getValueByName("SDOCPATH", rowid);
	if (!SFILEID || SFILEID == "")
		return;
	// tlv8.dowloadfile(SFILEID, SDOCNAME);
	justep.Doc.downloadDocByFileID(docPath, SFILEID);
}

function newFolderData() {
	tlv8.portal.dailog.openDailog('新建目录',
			"/SA/doc/docCenter/dialog/create-folder.html?sparent="
					+ currenttreeID + "&name=" + currenttreeName + "&pdocpath="
					+ currenttreeSDOCPATH + "&pdocpathname="
					+ currenttreeSDOCPATHName, 350, 300, dailogcallback, null);
}
function editOrgData() {
	tlv8.portal.dailog.openDailog('编辑目录',
			"/SA/doc/docCenter/dialog/create-folder.html?rowid="
					+ currenttreeID + "&name=" + currenttreeName + "&pdocpath="
					+ currenttreeSDOCPATH + "&pdocpathname="
					+ currenttreeSDOCPATHName, 350, 300, seaTreeItem, null);
}
function editPermision() {
	tlv8.portal.dailog.openDailog('分配权限',
			"/SA/doc/docCenter/dialog/doc-permision.html?rowid="
					+ currenttreeID + "&DOCPATH=" + currenttreeSDOCPATH, 750,
			500, null, null);
}
var backData;
function dailogcallback(data) {
	// alert("回调");
	backData = data;
	setTimeout('seaTreeItem()', 500);
}

function seaTreeItem(data) {
	data = data ? data : backData;
	maingrid.grid.refreshData("1=2");
	Jtree.refreshJtree("maintree");
	var orgTree = Jtree; // J$("maintree").tree;
	// orgTree.quickPosition(data);// 快速查询 (暂时不支持)
}

function afGridrefresh(r) {
}

var currenttreeNode = null;
var currenttreeID = null;
var currenttreeName = null;
var currenttreeSDOCPATH = null;
var currenttreeSDOCPATHName = null;
// 选中树
function treeselected(event, treeId, node, clickFlag) {
	var treeID = node.id;
	currenttreeNode = node;
	J$("main_org_trr").rowid = treeID;
	maingrid.grid.refreshData();
	currenttreeID = treeID;
	currenttreeName = node.name;
	currenttreeSDOCPATH = node.SDOCPATH;
	currenttreeSDOCPATHName = node.SDOCDISPLAYPATH;
	var r_data = getFileAuth(currenttreeSDOCPATH);
	if (treeID == "defaultDocNameSpace"
			|| tlv8.Context.getCurrentPersonID() == "PSN01") {
		J$("new_folder_item").src = "../../../comon/image/doc/newfolder.gif";
		J$("new_folder_item").onclick = newFolderData;// 新建目录
	} else {
		if ((!r_data || r_data < 16384)
				&& node.SCREATORFID != tlv8.Context.getCurrentPersonID()) {
			J$("new_folder_item").src = "../../../comon/image/doc/newfolder_g.gif";
			J$("new_folder_item").onclick = null;
		} else {
			J$("new_folder_item").src = "../../../comon/image/doc/newfolder.gif";
			J$("new_folder_item").onclick = newFolderData;// 新建目录
		}
	}
	if (treeID != "defaultDocNameSpace"
			&& currenttreeName != "Message"
			&& (node.SCREATORFID == tlv8.Context.getCurrentPersonID() || tlv8.Context
					.getCurrentPersonID() == "PSN01")) {
		J$("folder_pro_item").src = "../../../comon/image/doc/folder_pro.gif";
		J$("folder_pro_item").onclick = editOrgData;// 修改目录

		J$("folder_perm_item").src = "../../../comon/image/doc/folder_perm.gif";
		J$("folder_perm_item").onclick = editPermision;// 修改权限

		J$("deletefile_item").src = "../../../comon/image/doc/deletefile.gif";
		J$("deletefile_item").onclick = function(e) {
			data.setTable("SA_DOCNODE");
			data.setDbkey("system");
			data.setFormId("main_org_trr");
			data.setRowId(currenttreeID);
			data.deleteData();
			Jtree.refreshJtree("maintree");
		};// 删除目录
	} else if ((treeID == "defaultDocNameSpace" || currenttreeName == "Message")
			&& tlv8.Context.getCurrentPersonID() == "PSN01") {
		J$("folder_pro_item").src = "../../../comon/image/doc/folder_pro_g.gif";
		J$("folder_pro_item").onclick = null;// 修改目录

		J$("folder_perm_item").src = "../../../comon/image/doc/folder_perm.gif";
		J$("folder_perm_item").onclick = editPermision;// 修改权限

		J$("deletefile_item").src = "../../../comon/image/doc/deletefile_g.gif";
		J$("deletefile_item").onclick = null;
	} else {
		J$("folder_pro_item").src = "../../../comon/image/doc/folder_pro_g.gif";
		J$("folder_pro_item").onclick = null;

		if (!r_data || r_data < 16384) {
			J$("folder_perm_item").src = "../../../comon/image/doc/folder_perm_g.gif";
			J$("folder_perm_item").onclick = null;
		} else {
			J$("folder_perm_item").src = "../../../comon/image/doc/folder_perm.gif";
			J$("folder_perm_item").onclick = editPermision;// 修改权限
		}

		J$("deletefile_item").src = "../../../comon/image/doc/deletefile_g.gif";
		J$("deletefile_item").onclick = null;
	}
	if ((tlv8.Context.getCurrentPersonID() != "PSN01")
			&& (!r_data || r_data < 257)
			&& (node.SCREATORFID != tlv8.Context.getCurrentPersonID())) {
		can_upload_m(false);// 上传文件按钮 (无权限)
	} else {
		can_upload_m(true);// 上传文件按钮 (有权限)
	}
	can_browseDoc_m(true);
	setTimeout(can_downloadload_d, 300);
	if (!docAuthList) {
		getDocAuthList();
	}
}

/*
 * 下载按钮控制
 */
function can_downloadload_d() {
	var rowid_t = currentgrid.CurrentRowId;
	var SFILEID_t = currentgrid.getValueByName("SFILEID", rowid_t);
	if (!SFILEID_t || SFILEID_t == "")
		can_downloadload_m(false);
}
/*
 * 编辑按钮控制 @param {Object} flag
 */
function can_editer_m(flag) {
	if (flag) {
		J$(editer_item).src = "../../../comon/image/doc/edit_file.gif";
		J$(editer_item).onclick = function() {
			officeHandler("OpenOffice");
		};
	} else {
		J$(editer_item).src = "../../../comon/image/doc/edit_file_g.gif";
		J$(editer_item).onclick = null;
	}
}

/*
 * 下载记录 @param {Object} flag
 */
function can_docDownloadRecord_m(flag) {
	if (flag) {
		J$(docDownloadRecord_item).src = "../../../comon/image/doc/download_record.gif";
		J$(docDownloadRecord_item).onclick = function() {
			var docID = currentgrid.getCurrentRowId();
			justep.Doc.openDocDownloadHistoryDialog(docID);
		};
	} else {
		J$(docDownloadRecord_item).src = "../../../comon/image/doc/download_record_g.gif";
		J$(docDownloadRecord_item).onclick = null;
	}
}
/*
 * 版本历史 @param {Object} flag
 */
function can_docHistory_m(flag) {
	if (flag) {
		J$(docHistory_item).src = "../../../comon/image/doc/modify_record.gif";
		J$(docHistory_item).onclick = function() {
			var docID = currentgrid.getCurrentRowId();
			var fileID = currentgrid.getValueByName("SFILEID", docID);
			var docPath = currentgrid.getValueByName("SDOCPATH", docID);

			var docFullPath = justep.Doc.getDocFullPath(docID, docPath);
			var access = getAccessBysDocPath(docFullPath, currentRootAccess);
			justep.Doc.openDocHistoryDialog(docID, fileID, docPath, access,
					NotifyPrintEvent());
		};
	} else {
		J$(docHistory_item).src = "../../../comon/image/doc/modify_record_g.gif";
		J$(docHistory_item).onclick = null;
	}
}

/*
 * 上传按钮控制 @param {Object} flag
 */
function can_upload_m(flag) {
	if (flag) {
		J$(doc_upload_item).src = "../../../comon/image/toolbar/doc/newfile.gif";
		createUploadElement();
	} else {
		J$(doc_upload_item).src = "../../../comon/image/toolbar/doc/newfile_g.gif";
	}
}

/*
 * 下载按钮控制 @param {Object} flag
 */
function can_downloadload_m(flag) {
	if (flag) {
		J$(doc_dowload_item).src = "../../../comon/image/toolbar/doc/download.gif";
		J$(doc_dowload_item).onclick = doc_dowload;
	} else {
		J$(doc_dowload_item).src = "../../../comon/image/toolbar/doc/download_g.gif";
		J$(doc_dowload_item).onclick = null;
	}
}

/*
 * 查看文件 @param {Object} flag @return {TypeName}
 */
function can_browseDoc_m(flag) {
	if (flag) {
		J$(browseDoc_item).src = "../../../comon/image/doc/browse.gif";
		J$(browseDoc_item).onclick = function() {
			var rowId = currentgrid.getCurrentRowId();
			var docPath = currentgrid.getValueByName("SDOCPATH", rowId);
			var docFullPath = justep.Doc.getDocFullPath(rowId, docPath);
			var access = getAccessBysDocPath(docFullPath, currentRootAccess);
			if (!((access % 4) >= 2)) {
				// return;
			}

			var filename = currentgrid.getValueByName("SDOCNAME", rowId);
			var fileID = currentgrid.getValueByName("SFILEID", rowId);
			justep.Doc.browseDocByFileID(docPath, filename, fileID, "last",
					"content", 'OpenOffice', NotifyPrintEvent());
		};
	} else {
		J$(browseDoc_item).src = "../../../comon/image/doc/browse_g.gif.gif";
		J$(browseDoc_item).onclick = null;
	}
}

// 选中文件
function select_file(event) {
	var docpath = event.getValueByName("SDOCPATH", event.getCurrentRowId());
	var fileID = event.getValueByName("SFILEID", event.getCurrentRowId());
	var SCREATORFID = event.getValueByName("SCREATORFID", event
			.getCurrentRowId());
	var r_data = getFileAuth(docpath.replace(fileID, ""));

	if (r_data < 7 && SCREATORFID != tlv8.Context.getCurrentPersonFID()) {
		can_downloadload_m(false);
		can_docDownloadRecord_m(false);
		can_docHistory_m(false);
		can_editer_m(false);
		maingrid.grid.settoolbar(false, false, true, "readonly");
	} else {
		// 控制权限
		can_downloadload_m(true);
		can_docDownloadRecord_m(true);
		can_docHistory_m(true);
		if (r_data > 1543 || SCREATORFID == tlv8.Context.getCurrentPersonFID()) {
			maingrid.grid.settoolbar(false, false, true, true);
			can_editer_m(true);
		} else {
			maingrid.grid.settoolbar(false, false, true, "readonly");
			can_editer_m(false);
		}
	}
}

/*
 * grid行双击事件 @param {Object} event
 */
function griddbclickFN(event) {
	var docID = event.getCurrentRowId();
	var afterEnsureFun = function(event) {
		justep.Doc.syncCustomFileds(currentgrid.getCurrentRowId());
	};
	justep.Doc.openDocInfoDialog(docID, afterEnsureFun);
}

// 获取权限标识
function getFileAuth(DOCPATH) {
	var sql = "select SACCESS from SA_DOCAUTH where '" + DOCPATH
			+ "' = SDOCPATH  and (sAuthorizeeDeptName ='"
			+ tlv8.Context.getCurrentDeptName() + "' or sAuthorizeeName ='"
			+ tlv8.Context.getCurrentPersonName() + "')";
	var r = tlv8.sqlQueryActionforJson("system", sql, null);
	if (r.data && r.data != "")
		return r.data[0].SACCESS;
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

function NotifyPrintEvent() {
	var result = _office_isPrint;
	/*
	 * if (docCenter.checkEvent(ONGETISPRINT)) { var eventData = { 'isPrint' :
	 * _office_isPrint, 'docAuthListArr' : docAuthListArr };
	 * docCenter.callEvent(ONGETISPRINT, [ eventData ]); result =
	 * eventData.isPrint; }
	 */
	return result;
}

function officeHandler(type) {
	var rowid = currentgrid.getCurrentRowId();
	var docFullPath = currentgrid.getValueByName("SDOCPATH", rowid);
	var filename = currentgrid.getValueByName("SDOCNAME", rowid);
	var fileid = currentgrid.getValueByName("SFILEID", rowid);
	tlv8.trangereditfile(fileid, filename, docFullPath);
}

function update(docName, kind, size, cacheName, revisionCacheName,
		commentFileContent, createVersion) {
	var docID = currentgrid.getCurrentRowId();
	var version = currentgrid.getValueByName("VERSION", docID);
	var fileID = currentgrid.getValueByName("SFILEID", docID);
	var parentID = currenttreeID;
	var docPath = currentgrid.getValueByName("SDOCPATH", docID);
	var displayPath = currentgrid.getValueByName("SDOCDISPLAYPATH", docID);
	var description = currentgrid.getValueByName("SDESCRIPTION", docID);
	var classification = currentgrid.getValueByName("SCLASSIFICATION", docID);
	var keywords = currentgrid.getValueByName("SKEYWORDS", docID);
	var finishTime = currentgrid.getValueByName("SFINISHTIME", docID);
	var serialNumber = currentgrid.getValueByName("SDOCSERIALNUMBER", docID);
	var docVersionID = currentgrid.getValueByName("SDOCLIVEVERSIONID", docID);

	changeLog = {
		items : [],
		autoCreateVersion : true,
		"operate" : "",
		"url" : ""
	};
	justep.Doc.addChangeLog(changeLog, "edit", [ docID, version, fileID,
			docVersionID, docName, kind, size, parentID, docPath, displayPath,
			description, classification, keywords, finishTime, serialNumber ],
			[ "document", cacheName, revisionCacheName, commentFileContent ]);
	if ('W10=' != commentFileContent) {
		justep.Doc.commitDocCache(docID, changeLog);
		currentgrid.refreshData();
	}
	if (!!createVersion) {
		// ocx控件挡住了成文状态的提示信息，所以不提示原因
		justep.Doc.createVersion(docID);
		currentgrid.refreshData();
	}
}

function getAccessBysDocPath(docFullPath, defaultAccess) {
	var docAccess = null;
	for ( var item in docAuthListArr) {
		var itemAceess = defaultAccess;
		$.each(docAuthListArr[item], function(n, value) {
			if (value.sDocPath == docFullPath) {
				itemAceess = value.sAccess;
				return false;
			}
		});
		if (!docAccess)
			docAccess = itemAceess;
		if (itemAceess > docAccess)
			docAccess = itemAceess;
	}
	return (docAccess != null && docAccess) > defaultAccess ? docAccess
			: defaultAccess;
}

function getDocAuthList() {
	docAuthList = justep.Doc.getDocAuthList();
	if (!docAuthListArr)
		docAuthListArr = {};
	for ( var deptFID in docAuthList) {
		var authItems = docAuthList[deptFID];
		var deptAuth = new Array();
		var i = 0;
		for ( var authId in authItems) {
			var authItem = authItems[authId];
			deptAuth[i] = {
				"authId" : authId,
				"sDocPath" : authItem.sDocPath,
				"sAuthorizeeFID" : authItem.sAuthorizeeFID,
				"sAccess" : authItem.sAccess
			};
			i++;
		}
		docAuthListArr[deptFID] = deptAuth;
	}
}
// 拖动分隔线事件
function standardPartitionResize(event) {
	$("#main-grid-view_grid_label").fixTable({
		fixColumn : 0,// 固定列数
		fixColumnBack : "#ccc",// 固定列数
		width : $("#main-grid-view_body_layout").width(),// 显示宽度
		height : $("#main-grid-view_body_layout").height()
	// 显示高度
	});
}