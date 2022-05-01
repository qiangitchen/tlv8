var mainData;
function initBody() {
	mainData = new tlv8.Data();
	mainData.setDbkey("oa");
	mainData.setTable("oa_adm_mygroupmain");
	mainData.setFormId("MAIN_DATA_FORM");
}

$(document).ready(function() {
	initBody();
	var sData1 = getParamValueFromUrl("sData1");
	if (sData1 && sData1 != "") {
		$("#MAIN_DATA_FORM").attr("rowid", sData1);
		mainData.setRowId(sData1);
		mainData.setFilter("");
		mainData.refreshData();
	} else {
		$("#FCREATORID").val(justep.Context.getCurrentPersonID());
		$("#FCREATOR").val(justep.Context.getCurrentPersonName());
		$("#FCREATEDEPTID").val(justep.Context.getCurrentDeptID());
		$("#FCREATEDEPT").val(justep.Context.getCurrentDeptName());
		$("#FCREATEDATE").val(tlv8.System.Date.sysDateTime());
	}
});

function afRefresh() {
	loadGroupPersons(mainData.rowid);
}

function doDataSaveaAtion() {
	var rowid = mainData.saveData();
	$("#MAIN_DATA_FORM").attr("rowid", rowid);
	mainData.setRowId(rowid);
	return rowid;
}

function loadGroupPersons(groupid) {
	$.ajax({
		type : "post",
		async : true,
		url : "mobileListDataAction",
		data : {
			dbkey : "oa",
			tableName : "OA_ADM_MYGROUPFROM",
			idcolumn : "FID",
			queryColomn : "FPERSONID,FPERSONNAME",
			staticfilter : "FOUTKEY='" + groupid + "'",
			// orderby : "",
			pagelimit : 10
		},
		success : function(result, textStatus) {
			// console.log(result);
			createListView(result);
		}
	});
}

function createListView(result) {
	var data = result.data.data;
	if (result.count > 0) {
		var psmvis = [];
		psmvis.push('<ul class="mui-table-view">');
		data = JSON.parse(data);
		for (var i = 0; i < data.length; i++) {
			var psm = data[i];
			psmvis
					.push('<li class="mui-table-view-cell" id="' + psm.FID
							+ '">');
			psmvis.push(psm.FPERSONNAME);
			psmvis
					.push('<button type="button" class="mui-btn mui-btn-danger" onclick="removePerson(\''
							+ psm.FID + '\');">删除</button> ');
			psmvis.push('</li>');
		}
		psmvis.push('</ul>');
		$("#groupPersons").html(psmvis.join(""));
	}
}

function removePerson(sbid) {
	var subdata = new tlv8.Data();
	subdata.setDbkey("oa");
	subdata.setTable("OA_ADM_MYGROUPFROM");
	subdata.setFormId("SUB_DATA_FORM");
	subdata.setRowId(sbid);
	if(subdata.deleteData()){
		loadGroupPersons(mainData.rowid);
	}
}

// 添加成员
function addGroupPerson() {
	if (!doDataSaveaAtion()) {
		return;
	}
	justep.dialog.openFullScreenDialog("选择人员", {
		url : cpath+"/mobileUI/oa/common/dialog/psnSelect.html",
		callback : "selectPersonCallback"
	});
}

// 选择返回保存数据
function selectPersonCallback(re) {
	//console.log(re);
	$.ajax({
		type : "post",
		async : true,
		url : "addmyGroupPersonsAction",
		data : {
			groupid : mainData.rowid,
			personids : re.id,
			personnames : re.name
		},
		success : function(result, textStatus) {
			loadGroupPersons(mainData.rowid);
		}
	});
}

/**
@param {object} event 
*/
function maindatasvBefore(event){
	var FGROUPNAME = $("#FGROUPNAME").val();
	if(FGROUPNAME==""){
		alert("群组名词不能为空!");
		$("#FGROUPNAME").focus();
		return false;
	}
}