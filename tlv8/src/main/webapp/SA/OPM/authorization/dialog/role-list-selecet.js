var currentgrid = null;
var checkData = new Map();
// 角色列表
function getData() {
	var data = new tlv8.Data();
	data.setTable("SA_OPROLE");
	data.setDbkey("system");
	data.setOrderby("SSEQUENCE asc");
	var d = document.getElementById("main-grid-view");
	var labelid = "master_check,No,sName,sCode,SCATALOG,SDESCRIPTION";
	var labels = "master_check,No.,名称,编码,类型,描述";
	var labelwidth = "40,40,200,100,100,250";
	var datatype = "null,ro,string,string,string,ro";
	var dataAction = {
		"queryAction" : "getGridAction",
		"savAction" : "saveAction",
		"deleteAction" : "deleteAction"
	};
	maingrid = new tlv8.createGrid(d, labelid, labels, labelwidth,
			dataAction, "100%", "100%", data, 10, "", "", "", datatype, true,
			true);
	maingrid.grid.settoolbar(false, false, false, false);
	maingrid.grid.seteditModel(false);
	currentgrid = maingrid.grid;
	currentgrid.refreshData();
}

function bdlistresizefo(){
	getData();
}

function gridChecked(event) {
	if (event.checked) {
		checkData.put(event.rowid, event.getValueByName("sName", event.rowid));
	} else {
		checkData.remove(event.rowid);
	}
	var kset = checkData.keySet();
	var html = "";
	for ( var i in kset) {
		var value = checkData.get(kset[i]);
		html += "<span id='" + kset[i] + "'><input id='" + kset[i] + "' name='"
				+ kset[i] + ",' type='checkbox' value='" + value
				+ ",'  onclick='checkall()'>" + value + "</input></span>";
	}
	$("#Chtext").html(html);
}
function gridCheckedAll(event) {
	if (event.checked) {
		for ( var i in event.RowId) {
			checkData.put(event.RowId[i], event.getValueByName("sName",
					event.RowId[i]));
		}
	} else {
		for ( var i in event.RowId) {
			checkData.remove(event.RowId[i]);
		}
	}
	var kset = checkData.keySet();
	var html = "";
	for ( var i in kset) {
		var value = checkData.get(kset[i]);
		html += "<span id='" + kset[i] + "'><input id='" + kset[i] + "' name='"
				+ kset[i] + ",' type='checkbox' value='" + value
				+ ",'  onclick='checkall()'>" + value + "</input></span>";
	}
	$("#Chtext").html(html);
}

//删除
var chid = '';
function deleteBills() {
	$("span[id='" + chid + "']").remove();
	checkData.remove(chid);
}
function checkall() {
	$("input").bind("click", function() {
		chid = $(this).attr("id");
	});
}

function deleteAll() {
	$("#Chtext").html("");
	checkData = new Map();
}

//确定
function dailogEngin() {
//	var tid = '';
//	$("input[type='checkbox']").each(function() {
//		tid += ($(this).attr("name"));
//	});
//	var textid = tid.substring(0, tid.length - 1);
//	return textid;
	return checkData.keySet().join(",");
}