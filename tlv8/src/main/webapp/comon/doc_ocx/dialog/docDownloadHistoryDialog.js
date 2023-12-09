var docID;
var fileID;
var staticGrid;

var getUrlParam = function(data) {
	docID = data.docID;
	refresh();
};

$(document).ready(function() {
	staticGrid = new StaticGrid("historyRecordNav"); // 实例化类
	var labs = [ {
		id : 'SDOCVERSIONID',
		name : '版本',
		width : 60
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
		name : '下载人',
		width : 90
	}, {
		id : 'SDEPTNAME',
		name : '下载人部门',
		width : 100
	}, {
		id : 'STIME',
		name : '下载时间',
		width : 150
	} ];
	staticGrid.init(labs); // 初始化
});

function refresh() {
	if (staticGrid) {
		var response = tlv8.Doc.getAccessRecord(docID, true, false, false);
		if (response) {
			staticGrid.clearData();
			for (var i = 0; i < response.length; i++) {
				var json = {};
				json.rowid = new UUID().toString();
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
}