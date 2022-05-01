$(document).bind("pagebeforecreate", function(event, data) {
initOverTimeType();
});

var flwCompent;
var mainData = new tlv8.Data();
mainData.setDbkey("wq");
mainData.setTable("WQ_OVERTIME_APPLY");
// 页面初始化
var option = getParamValueFromUrl("option");
function initPage() {
	mainData.setFormId("overtime_form");
	var rowid = getParamValueFromUrl("sData1");
	if (rowid && rowid != "") {
		$("#overtime_form").attr("rowid", rowid);
		mainData.refreshData();
		getOpinionData(rowid);
		if (option && option == "view") {
			$("#submitItem").hide();
			setReadonly();
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
/*		$("#FCREATEDATE").val(tlv8.System.Date.sysDateTime());
		$("#FSTARTDATE").val(tlv8.System.Date.sysDate());
		$("#FENDDATE").val(tlv8.System.Date.sysDate());*/
	}
	(function($) {
				$.init();
				var btns = $('.dtpicker');
				btns.each(function(i, btn) {
					btn.addEventListener('tap', function() {
						var result=this;
						var optionsJson = this.getAttribute('data-options') || '{}';
						var options = JSON.parse(optionsJson);
						var id = this.getAttribute('id');
						/*
						 * 首次显示时实例化组件
						 * 示例为了简洁，将 options 放在了按钮的 dom 上
						 * 也可以直接通过代码声明 optinos 用于实例化 DtPicker
						 */
						var picker = new $.DtPicker(options);
						picker.show(function(rs) {
							/*
							 * rs.value 拼合后的 value
							 * rs.text 拼合后的 text
							 * rs.y 年，可以通过 rs.y.vaue 和 rs.y.text 获取值和文本
							 * rs.m 月，用法同年
							 * rs.d 日，用法同年
							 * rs.h 时，用法同年
							 * rs.i 分（minutes 的第二个字母），用法同年
							 */
							mui.toast("您选择了日期"+rs.value);
							jQuery(result).val(rs.value);
							/* 
							 * 返回 false 可以阻止选择框的关闭
							 * return false;
							 */
							/*
							 * 释放组件资源，释放后将将不能再操作组件
							 * 通常情况下，不需要示放组件，new DtPicker(options) 后，可以一直使用。
							 * 当前示例，因为内容较多，如不进行资原释放，在某些设备上会较慢。
							 * 所以每次用完便立即调用 dispose 进行释放，下次用时再创建新实例。
							 */
							picker.dispose();
						});
					}, false);
				});
			})(mui);
			(function($, doc) {
				$.init();
				var showUserPickerButton =  doc.getElementById('FOVERTIMETYPE');
					showUserPickerButton.addEventListener('tap', function(event) {
						var userPicker = new $.PopPicker();
					userPicker.setData([{
						value: 'ywj',
						text: '未完成工作'
					}, {
						value: 'aaa',
						text: '个人能力原因'
					}, {
						value: 'lj',
						text: '紧急'
					}, {
						value: 'ymt',
						text: '赶活'
					}, {
						value: 'shq',
						text: '自愿加班'
					}, {
						value: 'zhbh',
						text: '统一加班'
					}, {
						value: 'zhy',
						text: '调休加班'
					}, {
						value: 'gyf',
						text: '请假补 加班'
					}]);
						var nowpicker=this;
						userPicker.show(function(items) {
							mui.toast("您选择了："+JSON.stringify(items[0].text));
							jQuery(nowpicker).val(JSON.stringify(items[0].text));
							//返回 false 可以阻止选择框的关闭
							//return false;
						});
					}, false);
			})(mui, document);
}

// 保存
function saveData(goback) {
	if($("#FSTARTDATE").val()==""){
		alert("请填写开始日期");
		return;
	}
	if($("#FENDDATE").val()==""){
		alert("请填写结束日期");
		return;
	}
	if($("#FREASON").val()==""){
		alert("请填写加班事由");
		return;
	}
var rowid=$("#leave_form").attr("rowid");
if(rowid==undefined||rowid==""){
	rowid = mainData.saveData();
}
var flowID = getParamValueFromUrl("flowID");
if(flowID){
	processadvance();
}else{
	var param = new tlv8.RequestParam();
	param.set("sdata1", rowid);
	var srcPath = window.location.pathname;
	if (srcPath.indexOf("?") > 0)
		srcPath = srcPath.substring(0, srcPath.indexOf("?"));
	srcPath = srcPath.replace("/tlv8/mobileUI/", "/");
	param.set("srcPath", srcPath);
	tlv8.XMLHttpRequest("flowstartAction", param, "post", true,
					function(r) {
						tlv8.showModelState(false);
						if (r.data.flag == "false") {
							alert("操作失败:" + r.data.message);
						} else {
							var flwData = eval("(" + r.data.data + ")");
							processadvanceOut(flwData.flowID,"bizActivity2",flwData.taskID,rowid);
							}
						});
	}
	return true;
}
function initOverTimeType() {
	var html = "";
	var sql = "select FID,FTYPENAME from WQ_BASE_OVERTIMETYPE where FSTATUS=1";
	var result=tlv8.sqlQueryActionforJson("wq", sql)
		var data = result.data;
		if (typeof data == "string") {
			data = window.eval("(" + data + ")");
		}
		for (var i = 0; i < data.length; i++) {
			html += "<option value='" + data[i].FTYPENAME + "'>"
					+ data[i].FTYPENAME + "</option>";
		}
	$("#FOVERTIMETYPE").html(html);
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

