var write_data;
var type, rowid, Operator;
$(document).ready(
		function() {
			rowid = getParamValueFromUrl("rowid");
			type = getParamValueFromUrl("type");
			Operator = getParamValueFromUrl("Operator");
			if (!rowid || trim(rowid) == "") {
				rowid = new UUID().toString();
				$("#rowid").val(rowid);
			}
			loadMailSendInfo();
			write_data = new tlv8.Data();
			write_data.setDbkey("oa");
			write_data.setFormId("WRITE_DATA_FORM");
			write_data.setTable("OA_EM_SENDEMAIL");
			write_data.setRowId(rowid);
			var attfile = new tlv8.fileComponent(
					J$("FATTACHMENT_showdiv"), write_data, "FFJID", "/root/邮箱/"
							+ getCurentYearandMonth(), true, true);
		});

function loadMailSendInfo() {
	var param = new tlv8.RequestParam();
	param.set("rowid", rowid);
	param.set("type", type);
	var re = tlv8.XMLHttpRequest("LoadMailSendInfoAction", param, "post");
	if (re.data.flag == "true") {
		var r = re.data.data;
		if (typeof r == "string") {
			r = window.eval("(" + r + ")");
		}
		if (r.length > 0) {
			if (type == "receive") {
				$("#FCONSIGNEEID").val(r[0].FSENDPERID);
				$("#FCONSIGNEECODE").val(r[0].FSENDPERCODE);
				$("#FCONSIGNEE").val(r[0].FSENDPERNAME);
				$("#selectPsn").attr("disabled", "true");
				$("#FEMAILNAME").val("回复：" + r[0].FEMAILNAME);
				$("#FTEXT").focus();
			} else {
				if (Operator == "transmit") {
					$("#FEMAILNAME").val("转发：" + r[0].FEMAILNAME);
				} else {
					Operator = "send";
					$("#FEMAILNAME").val(r[0].FEMAILNAME);
					$("#FCONSIGNEEID").val(r[0].FCONSIGNEEID);
					$("#FCONSIGNEECODE").val(r[0].FCONSIGNEECODE);
					$("#FCONSIGNEE").val(r[0].FCONSIGNEE);
					$("#rowid").val(rowid);
				}
				$("#FTEXT").html(r[0].FTEXT);

			}
		}
	}
}

function writeMail(actype) {
	var recv = $("#FCONSIGNEE").val();
	var title = $("#FEMAILNAME").val();

	if (recv == null || recv.length == 0) {
		alert("收件人不能为空!");
		return;
	}
	if (title == null || title.length == 0) {
		alert("主题不能为空!");
		return;
	}
	$.ajax({
		type : "post",
		url : "sendMailAction",
		data : "actype=" + actype + "&fconsigneeid="
				+ enCode($("#FCONSIGNEEID").val()) + "&fconsigneecode="
				+ enCode($("#FCONSIGNEECODE").val()) + "&fconsignee="
				+ enCode($("#FCONSIGNEE").val()) + "&femailname="
				+ enCode($("#FEMAILNAME").val()) + "&ftext="
				+ enCode($("#FTEXT").val()) + "&rowid=" + $("#rowid").val(),
		success : function(result, textStatus) {
			var data = result.data;
			if (data.flag == "true") {
				$("#rowid").val(result.rowid);
				write_data.setRowId(result.rowid);
				if (actype == "send") {
					alert("发送成功!");
					goback();
				} else {
					alert("保存成功!");
				}
			} else {
				alert(data.message);
			}
		},
		error : function() {
			// 请求出错处理
		}
	});
}

function selectPerson() {
	justep.dialog.openFullScreenDialog("选择人员", {
		url : cpath+"/mobileUI/oa/common/dialog/psnSelect.html",
		callback : "selectPersonCallback"
	});

}

function selectPersonCallback(event) {
	$("#FCONSIGNEEID").val(event.id);
	$("#FCONSIGNEE").val(event.name);
}

function goback() {
	history.back();
}