var mainData = new tlv8.Data();
mainData.setDbkey("wq");
mainData.setTable("WQ_BASE_BARCODE");
var barcode = getParamValueFromUrl("barcode");
// 页面初始化
function initPage() {
	mainData.setFormId("visit_SCENE_form");
	if (barcode && barcode != "") {
		mainData.setFilter("FBARCODE='"+barcode+"'");
		mainData.refreshData();
		$("#submitItem").hide();
	}
}
function afRefresh(){
	var barcode1=mainData.getValueByName("FBARCODE");
	if(barcode1==""){
		if(confirm("没有找到商品信息,是否填写商品信息？")){
			$("#submitItem").show();
			$("#FBARCODE").val(barcode);
			$("#FCREATORID").val(justep.Context.getCurrentPersonID());
			$("#FCREATOR").val(justep.Context.getCurrentPersonName());
			$("#FCREATEDATE").val(tlv8.System.Date.sysDateTime());
		}else{
			history.go(-1);
		}
	}else{
		$("#FBARCODE").attr("readonly", "readonly");
		$("#FNAME").attr("readonly", "readonly");
		$("#FPRICE").attr("readonly", "readonly");
		$("#FMANUFACTURER").attr("readonly", "readonly");
		$("#FFACTORYADDR").attr("readonly", "readonly");
	}
} 
 //保存
function saveData(goback) {
	var rowid = mainData.saveData();
	if (goback) {
		history.go(-1);
	}
	return true;
}
