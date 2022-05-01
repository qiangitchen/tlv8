$(document).bind("pagebeforecreate", function(event, data) {
});
//流程配置
var setting = {
  autosaveData : true, //自动保存数据
  autoclose : true, //自动关闭页面
  autofilter : true, //自动过滤数据
  autorefresh : true, //自动刷新数据
  autoselectext : true, //自动获取执行人
  item : {//按钮配置
    audit : false, //审批
    back : false,//流转按钮
    out : "readonly",//流转按钮
    transmit : false,//转发按钮
    pause : false,//暂停按钮
    stop : "readonly" //终止按钮 
  },
  auditParam : {//审批信息配置
    busiDataKey : "wq", //业务库数据连接
    busiTable : "WQ_TRIP_APPLY", //业务表名
    auditTable : "DYCRM_FLOWRECORD", //审核意见表
    billidRe : "FBILLID", //外键字段
    FAGREETEXTRe : "FAGREETEXT", //意见字段
    isRequired : false //是否为必须填写意见
  }
}
var flwCompent;
var mainData = new tlv8.Data();
mainData.setDbkey("wq");
mainData.setTable("WQ_TRIP_APPLY");
// 页面初始化
var option = getParamValueFromUrl("option");
function initPage() {
	mainData.setFormId("trip_form");
	var rowid = getParamValueFromUrl("sData1");
	if (rowid && rowid != "") {
		$("#trip_form").attr("rowid", rowid);
		mainData.refreshData();
		getOpinionData(rowid);
		if (option && option == "view") {
			$("#submitItem").hide();
			$("#readtitle").html("出差记录")
			setReadonly();
		}
	} else {
		flwCompent = new tlv8.flw("flowToolbar", mainData, setting);
		var deptID = justep.Context.getCurrentDeptID();
		var deptName = justep.Context.getCurrentDeptName();
		if (!deptID || deptID == "") {
			deptID = justep.Context.getCurrentOrgID();
			deptName = justep.Context.getCurrentOrgName();
		}
		try {
			var location = justepYnApp.getLocatin();
			var locobj;
			if (typeof location == "string") {
				locobj = window.eval("(" + location + ")");
			}
			$("#FSTARTADDR").val(locobj.addr);
		} catch (e) {
		}
		$("#FCREATORID").val(justep.Context.getCurrentPersonID());
		$("#FCREATORNAME").val(justep.Context.getCurrentPersonName());
		$("#FCREATORDEPTID").val(deptID);
		$("#FCREATORDEPT").val(deptName);
/*		$("#FCREATEDATE").val(tlv8.System.Date.sysDateTime());
		$("#FSTARTDATE").val(tlv8.System.Date.sysDate());
		$("#FENDDATE").val(tlv8.System.Date.sysDate());*/
	}
}

// 保存
function saveData(goback) {
mainData.saveData();
openProcessSelectDialog(true,false,true,false);
//	alert(flwCompent.taskID);
//	alert(result.taskID);
						
	return true;
}

// 是这所有字段为只读
function setReadonly() {
	$("#FCREATORNAME").attr("readonly", "readonly");
}

function backhistory(){
if (option && option == "view") {
		var url = "listActivity.html";
		window.open(url, '_self');
	}else{
		var url = "../attendindex.html";
		window.open(url, '_self');
	}
}

