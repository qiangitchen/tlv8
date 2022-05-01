function onItemBeforeCheck(node, checked) {
	if (node.SORGKINDID == "psm" || node.children) {
		return true;
	}
	return false;
}

function onItemCheck(node, checked) {
	// console.log(node);
}

function dosearchText(value){
	// alert(value);
	$("#maintree").tree("search", value);   
}

$(document).ready(function(){
	// 只显示当前机构下的数据
	var rootfilter = "SID='"+justep.Context.getCurrentOgnID()+"'";
	tlv8.showModelState(true);
	$("#maintree").tree({
		url:cpath+'/getPersonSelecteTree?rootFilter='+J_u_encode(rootfilter),
		checkbox:true,
		onBeforeCheck:onItemBeforeCheck,
		onCheck:onItemCheck,
		onLoadSuccess:function(node, data){
			tlv8.showModelState(false);
		}
	});
});

function selectPsn() {
	var nodes = $("#maintree").tree("getChecked","checked");
	var re = {
		"id" : getMapValue(nodes, "SID"),
		"name" : getMapValue(nodes, "SNAME"),
		"sfid" : getMapValue(nodes, "SFID"),
		"sfname" : getMapValue(nodes, "SFNAME")
	};
	// console.log(re);
	justep.dialog.ensureDialog(re);
}

function getMapValue(obj, celname) {
	var re = "";
	for (var int = 0; int < obj.length; int++) {
		if (obj[int].SORGKINDID == "psm") {
			re += "," + obj[int][celname];
		}
	}
	re = re.replace(",", "");
	return re;
}

function windowCancel() {
	justep.dialog.closeDialog();
}