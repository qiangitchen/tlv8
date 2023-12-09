var docHostPath;
var fileID;
var docAccess;
var isPrintDoc = true;
var currDocVersion;
var docID;
var staticGrid;
var firsetRowid;
var getUrlParam = function(data) {
	fileID = data.fileID;
	docHostPath = data.docPath;
	docID = data.docID;
	if (staticGrid) {
		var param = new tlv8.RequestParam();
		param.set("docID",docID);
		var r = tlv8.XMLHttpRequest("queryDocHistoryAction", param, "POST", false);
		var response = r.data.data;
		if (r.data.flag == "false") {
			alert(r.messgae);
			return;
		}
		if (response) {
			staticGrid.clearData();
			for (var i = 0; i < response.length; i++) {
				var json = {};
				json.rowid = new UUID().toString();
				if (i == 0) {
					firsetRowid = json.rowid;
				}
				json.number = i + 1;
				json.SDOCVERSIONID = response[i].SDOCVERSIONID;
				json.SDOCNAME = response[i].SDOCNAME;
				json.SSIZE = response[i].SSIZE;
				json.SPERSONNAME = response[i].SPERSONNAME;
				json.SDEPTNAME = response[i].SDEPTNAME;
				json.STIME = response[i].STIME;
				staticGrid.addData(json);
			}
			staticGrid.selectRowById(firsetRowid);
		}
	}
	initParam(data.access);
	if (!(typeof data.isPrint === "undefined" || data.isPrint == null))
		isPrintDoc = data.isPrint;
};

function refreshData() {
	var response = tlv8.Doc.getAccessRecord(docID, false, true, true);
	if (response) {
		staticGrid.clearData();
		for (var i = 0; i < response.length; i++) {
			var json = {};
			json.rowid = new UUID().toString();
			json.number = i + 1;
			json.SDOCVERSIONID = response[i].SDOCVERSIONID;
			json.SDOCNAME = response[i].SDOCNAME;
			json.SSIZE = response[i].SSIZE;
			json.SPERSONNAME = response[i].SPERSONNAME;
			json.SDEPTNAME = response[i].SDEPTNAME;
			json.STIME = response[i].STIME;
			staticGrid.addData(json);
		}
	}
}
function initParam(access) {
	docAccess = typeof access === "undefined" || access == null ? 32768
			: access;
	if (!((docAccess % 8) >= 4)) {
		J$("downloadDoc").disabled = true;
	}
}

function browseDocByType(type) {
	try {
		var historyRecord = staticGrid;
		var rowId = historyRecord.selectedRow.id;
		var docVersion = historyRecord.getValue(rowId, "SDOCVERSIONID");
		var docName = historyRecord.getValue(rowId, "SDOCNAME");
		tlv8.Doc.browseDocByFileID("/root", docName, fileID, docVersion,
				type, 'History', isPrintDoc);
	} catch (e) {
		alert("没有选中版本!" + e.message);
	}
}

function browseDoc() {
	browseDocByType("content");
}

$(document).ready(function() {
	staticGrid = new StaticGrid("historyRecordNav"); // 实例化类
	var labs = [ {
		id : 'number',
		name : '版本',
		width : 60
	}, {
		id : 'SDOCVERSIONID',
		name : 'DocVersion',
		width : 0
	}, {
		id : 'SDOCNAME',
		name : '名称',
		width : 220
	}, {
		id : 'SSIZE',
		name : '大小(字节)',
		width : 100
	}, {
		id : 'SPERSONNAME',
		name : '提交人',
		width : 80
	}, {
		id : 'SDEPTNAME',
		name : '提交人部门',
		width : 100
	}, {
		id : 'STIME',
		name : '提交时间',
		width : 140
	} ];
	staticGrid.init(labs); // 初始化
});

function browseRevisionDoc() {
	try {
		var historyRecord = staticGrid;
		var rowId = historyRecord.selectedRow.id;
		var docName = historyRecord.getValue(rowId, "SDOCNAME");
		var isDoc = '.doc.docx.xls.xlsx.ppt.mpp.vsd.'
				.indexOf(String(/\.[^\.]+$/.exec(docName)) + '.') >= 0;
		if (isDoc) {
			browseDocByType("revision");
		} else {
			alert("“" + docName + "”不包含修订类型的文件");
		}
	} catch (e) {
		alert("没有选中查看版本!");
	}
}

function deleteVersion() {
	var historyRecord = staticGrid;
	var rowId = historyRecord.selectedRow.id;
	tlv8.Doc.deleteVersion(docHostPath, fileID, rowId, '-1');
	refreshData();
}
function deleteCurrentVersion() {
	var historyRecord = staticGrid;
	var rowId = historyRecord.selectedRow.id;
	var docVersion = historyRecord.getValue(rowId, "sDocVersionID");
	tlv8.Doc.deleteVersion(docHostPath, fileID, rowId, docVersion);
	refreshData();
}

function downloadDoc() {
	try {
		var historyRecord = staticGrid;
		var rowId = historyRecord.selectedRow.id;
		var docVersion = historyRecord.getValue(rowId, "sDocVersionID");
		tlv8.Doc.downloadDocByFileID(docHostPath, fileID, docVersion);
	} catch (e) {
		alert("没有选中版本!");
	}
}

function listSelect() {

}

function tabPage1Select() {
	try {
		var historyRecord = staticGrid;
		var rowId = historyRecord.selectedRow.id;
		var docVersion = historyRecord.getValue(rowId, "SDOCVERSIONID");
		if (!rowId || docVersion == currDocVersion)
			return true;
		currDocVersion = docVersion;
	} catch (e) {
		return;
	}
	var a = "";
	try {
		var arr = tlv8.Doc.browseFileComment("/root", fileID, docVersion);
		arr = JSON.parse(arr);
		for (var i = 0; i < arr.length; i++) {
			var item = arr[i];
			var revisionType = "其他操作";
			if (item.Type == 1) {
				revisionType = "插入";
			} else if (item.Type == 2) {
				revisionType = "删除";
			}
			var content = item.Text ? ("\r\n内容:" + item.Text) : '';
			a += "序号:" + item.Index + "; 操作类型:" + revisionType + "; 修改人:"
					+ item.Author + "; 修改时间:" + item.Date + ";" + content
					+ "\r\n\r\n";
		}
	} catch (e) {
	}
	$("#commentArea").val(a);
}

function tabselected(title, index) {
	switch (index) {
	case 0:
		listSelect();
		break;
	case 1:
		tabPage1Select();
		break;
	default:
		break;
	}
}
