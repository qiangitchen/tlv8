var pass_word_action = {};
pass_word_action.change = function(old_pass_word, new_pass_word) {
	var param = new tlv8.RequestParam();
	var username = tlv8.Context.getCurrentPersonCode();
	param.set("username", username);
	param.set("password", hex_md5(old_pass_word));
	param.set("new_password", hex_md5(new_pass_word));
	var r = tlv8.XMLHttpRequest("system/User/changePassword", param, "POST", false,
			null);
	if (typeof r == "string") {
		r = window.eval("(" + r + ")");
	}
	return r[0];
};

/*
 * 重置密码修改框
 */
function reset_table() {
	document.getElementById("resetPassWord").reset();
	document.getElementById("changed_status_show").innerHTML = "";
}

/*
 * 修改密码
 */
function change_password() {
	var old_pass_word = $("#old_pass_word").val();
	var new_pass_word = $("#new_pass_word").val();
	var new_pass_word_ord = $("#new_pass_word_ord").val();
	var msg;
	if (new_pass_word != new_pass_word_ord) {
		msg = "<font color='red'>两次输入的密码不一致,请重新输入!</font>";
	} else {
		msg = pass_word_action.change(old_pass_word, new_pass_word);
		msg = eval('(' + msg.data + ')');
		msg = msg.msg;
	}
	if (msg) {
		$("#changed_status_show").html(msg);
	} else {
		$("#changed_status_show").html("修改成功!");
	}
}