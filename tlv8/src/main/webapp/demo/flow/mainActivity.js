//工具条
var toolbarItem,flwCompent;
function init_toolbar() {
	var bardiv = J$("stander_bar");
	var toobar = new tlv8.toolbar(bardiv, false, true, "readonly",
			true);
	toolbarItem = toobar.items;
}

//流程配置
var setting = {
  autosaveData : true, //自动保存数据
  autoclose : true, //自动关闭页面
  autofilter : true, //自动过滤数据
  autorefresh : true, //自动刷新数据
  autoselectext : true, //自动获取执行人
  item : {//按钮配置
    audit : true, //审批
    back : false,//流转按钮
    out : true,//流转按钮
    transmit : false,//转发按钮
    pause : false,//暂停按钮
    stop : "readonly" //终止按钮 
  },
  auditParam : {//审批信息配置
    busiDataKey : "oa", //业务库数据连接
    busiTable : "oa_leave", //业务表名
    auditTable : "OA_FLOWRECORD", //审核意见表
    billidRe : "FBILLID", //外键字段
    FAGREETEXTRe : "FAGREETEXT", //意见字段
    isRequired : false //是否为必须填写意见
  }
}

//数据配置
var datamian;
function initDocumentPage() {
	datamian = new tlv8.Data();
	datamian.setDbkey("oa");
	datamian.setTable("oa_leave");
	datamian.setFormId("MAIN_DATA_FORM");
	
	init_toolbar();
	
	flwCompent = new tlv8.flw("flowToolbar", datamian, setting);
	
	var rowid = tlv8.RequestURLParam.getParam("sData1");
	if (rowid && rowid != "") {
		J$("MAIN_DATA_FORM").rowid = rowid;
		J$("MAIN_DATA_FORM").setAttribute("rowid", rowid);
		$("#MAIN_DATA_FORM").attr("rowid", rowid);
		dataRefresh();
	}else{
		initPsData();
	}
}

//新增数据
function dataInsert(){
	J$("MAIN_DATA_FORM").reset();
	J$("MAIN_DATA_FORM").rowid = "";
	J$("MAIN_DATA_FORM").setAttribute("rowid", "");
	$("#MAIN_DATA_FORM").attr("rowid", "");
}

//数据保存
function dataSave() {
	datamian.saveData();
}

//数据刷新
function dataRefresh(){
	datamian.refreshData();
}

//数据删除
function dataDeleted(){
	if(datamian.deleteData()){
		dataRefresh();
	}
}

function initPsData(){
	$("#FCREATORNAME").val(tlv8.Context.getCurrentPersonName());
	$("#FCREATORID").val(tlv8.Context.getCurrentPersonID());
	$("#FCREATORFID").val(tlv8.Context.getCurrentPersonFID());
	$("#FCREATORFNAME").val(tlv8.Context.getCurrentPersonFName());
	$("#FCREATEDATE").val(tlv8.System.Date.sysDateTime());
}