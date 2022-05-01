$(document).bind("pagebeforecreate", function(event, data) {
initPage();
});

var mainData = new tlv8.Data();
mainData.setDbkey("wq");
mainData.setTable("WQ_EBILL");
// 页面初始化
var option = getParamValueFromUrl("option");
function initPage() {
	mainData.setFormId("replenishment_form");
	var rowid = getParamValueFromUrl("sData1");
	if (rowid && rowid != "") {
		$("#replenishment_form").attr("rowid", rowid);
		mainData.refreshData();
		if (option && option == "view") {
//			$("#addphoto").hide();
			$("#submitItem").hide();
			$("#scan").hide();
//			$("#viewphoto").show();
			
		}
	} else {
		var deptID = justep.Context.getCurrentDeptID();
		var deptName = justep.Context.getCurrentDeptName();
		if (!deptID || deptID == "") {
			deptID = justep.Context.getCurrentOrgID();
			deptName = justep.Context.getCurrentOrgName();
		}
		$("#FCREATORID").val(justep.Context.getCurrentPersonID());
		$("#FCREATORNAME").val(justep.Context.getCurrentPersonName());
		$("#FCREATORDEPTID").val(deptID);
		$("#FCREATORDEPT").val(deptName);
		$("#FCREATEDATE").val(tlv8.System.Date.sysDateTime());
		$("#FNEEDDATE").val(tlv8.System.Date.sysDate());
		try {
			var location = justepYnApp.getLocatin();
			var locobj;
			if (typeof location == "string") {
				locobj = window.eval("(" + location + ")");
			}
			$("#FADDRESS").val(locobj.addr);
		} catch (e) {
		}
	}
}

// 保存
function saveData(goback) {

	if($("#FCOMMODITYNAME").val()==""){
		alert("请填写商品名称");
		return;
	}
	if($("#FCOMMODITYCOUNT").val()==""){
		alert("请填写商品数量");
		return;
	}
	var rowid = mainData.saveData();
	$("#replenishment_form").attr("rowid", rowid);
	//if (goback) {
		history.go(-1);
//		var url = "../scanindex.html";
//		window.open(url, '_self');
	//}
	return true;
}

// 是这所有字段为只读
function setReadonly() {
	//$("#needdate").html("<input name=\"FNEEDDATE\"  id=\"FNEEDDATE\" type=\"text\" format=\"yyyy-MM-dd\" />");
}

function photograph() {
	var rowid = mainData.saveData();
	$("#replenishment_form").attr("rowid", rowid);
//	if (saveData()) {
//		var rowid = $("#replenishment_form").attr("rowid");
		var url = "camera.html?rowid=" + rowid;
		window.open(url, '_self');
//	}
}
/**
 * 扫描条码
 */
function scanBarCode() {
	try {
		var code = justepYnApp.scanBarCode("scanBarCodeCalback");
	} catch (e) {
	}
}
// 扫描条码{回调}
function scanBarCodeCalback(result) {
	var sql="select FNAME from wq_base_barcode where FBARCODE='"+result+"'";
	var res=tlv8.sqlQueryActionforJson("wq", sql);
	res=res.data;
	if(res.length>0){
		$("#FCOMMODITYNAME").val(res[0].FNAME);
	}


}
function lookphotograph() {
	var rowid = $("#replenishment_form").attr("rowid");
	var url = "camera.html?rowid=" + rowid + "&option=view";
	window.open(url, '_self');
}
function backhistory(){
	history.go(-1);
//if (option && option == "view") {
//		var url = "listActivity.html";
//		window.open(url, '_self');
//	}else{
//		var url = "../scanindex.html";
//		window.open(url, '_self');
//	}
}
//计算
function rodm(id){
//	if(isNaN($("#"+id).val())){
//		alert("请输入正确的数字");
//		$("#"+id).val("");
//	}else{
//		//alert("是数字");
//	}
	//计算总价
	if($("#FPRICE").val()!=""&&$("#FCOMMODITYCOUNT").val()!=""){
	var price=$("#FPRICE").val();
	var count=$("#FCOMMODITYCOUNT").val();
	price=parseFloat(price);
	count=parseFloat(count);
	$("#FSUM").val(price*count);
	}
}

