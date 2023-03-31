var cpath="/tlv8";
if (typeof Login == "undefined") Login = {
	items: {},
	onGetUserCheckMsgBefore: null,
	onGetUserCheckMsgAfter: null,
	initUsernameInput: function(inputID){
		this.items.usernameInput = $("#" + inputID).keydown(function(event){
			if(event.keyCode==13){
				 Login.doLogin();
				 return false;
			}	 
		});
	},
	initPasswordInput: function(inputID){
		this.items.passwordInput = $("#" + inputID).keydown(function(event){
			if(event.keyCode==13){
				 Login.doLogin();
				 return false;
			}	 
		});
	},
	initRememberCheckbox: function(checkID) {
		this.items.rememberCheckbox = $("#" + checkID).change(function(event) {
			$.cookie("tlv8-remember", document.getElementById(checkID).checked, {expires:7,path:'/'});
		});
	},
	initLoginButton: function(inputID){
		this.items.loginButton = $("#" + inputID).click(function(event){
			Login.doLogin();
		});
	},
	initHintLabel: function(id){
		this.items.hintLabel = $("#" + id);
	},
	initFormCookie: function(forced) {
		var remember = forced || $.cookie("tlv8-remember")=="true";
		var rememberElement = this.items.rememberCheckbox ? this.items.rememberCheckbox.get(0) : null;
		if (rememberElement) rememberElement.checked = remember;
		if (remember) {
			this.items.usernameInput.val($.cookie("tlv8-username") || "");
		}
	},
	initFocus: function(){
		$(this.items.usernameInput).val() ?
			$(this.items.passwordInput).focus() :
			$(this.items.usernameInput).focus();
	},
	doGetDefaultCheckMsg: function(){
		if (this.onGetUserCheckMsgBefore) {
			var msg = this.onGetUserCheckMsgBefore();
			if (msg) return msg;
		}
		var username = $.trim(Login.items.usernameInput.val());
		if (username=="") {
			Login.items.usernameInput.focus();
			return "请输入用户名！";
		}
		if (this.onGetUserCheckMsgAfter) {
			return this.onGetUserCheckMsgAfter();
		}
	},
	doDisabledInput: function(b){
		for (var i in this.items) {
			var element = this.items[i].get(0);
			if (element) {
				element.disabled = (this.items[i] == this.items.hintLabel ? false : b);
			}
		}
	},
	doSleep: function(n){
		var s = "您已经3次登录错误，请" + n + "秒后重新登录！";
		Login.items.hintLabel && Login.items.hintLabel.text(s).show(); 
		if (--n >= 0) {
			setTimeout("Login.doSleep(" + n + ")", 1000);
		} else {
			Login.doDisabledInput(false);
			Login.items.passwordInput && Login.items.passwordInput.focus();
			Login.items.hintLabel && Login.items.hintLabel.text("").show();
		}
	},
	doLogin: function(){
		var msg = this.doGetDefaultCheckMsg();
		if (msg) {
			this.items.hintLabel && this.items.hintLabel.text(msg).show();
			return;
		}
		this.doDisabledInput(true);
		if (typeof Login._error_count_ == "undefined") {
			Login._error_count_ = 0;
		}
		var username = $.trim(this.items.usernameInput.val().toLowerCase());
		var password = this.items.passwordInput.val();
		//var result = tlv8.mobile.Portal.login(username, password, true);
		$.ajax( {
			type : "post",
			dataType: 'json',
			async : true,
			data: "username="+username+"&password="+hex_md5(password)+"&captcha=no_captcha&language=zh_CN",
			url : cpath+"/system/User/MD5login",
			success : function(data, textStatus) {
				console.log(data);
				var res = data[0];
				if(typeof res =="string"){
					res = window.eval("("+res+")");
				}
				if(res && res.status =="SUCCESS"){
					$.cookie("tlv8-username", username, {expires:7,path:'/'});
					//$.cookie("tlv8-password",password, {expires:7,path:'/'});
					$.cookie("Authorization", data.token, {expires:7,path:'/'});
					window.loginSuccess = true;
					window.location.href = window.location.href.replace(/login.*\.html.*/,"index.html?timestamp="+ new Date().valueOf());
				}else{
					var k = res.data;
					console.log(k);
					try{
						k = JSON.parse(k);
						k = k.msg;
					}catch (e) {
					}
					if (k) {
						var colon = k.indexOf(":");
						if (colon != -1) {
							k = k.substring(colon+1, k.length);
						}
					}
					Login.items.hintLabel && Login.items.hintLabel.text(k).show();
					Login.items.passwordInput && Login.items.passwordInput.val("").focus();
					Login._error_count_++;
					if (Login._error_count_ == 3) {
						Login._error_count_ = 0;
						setTimeout("Login.doSleep(" + 10 + ")", 1000);
					} else {
						Login.doDisabledInput(false);
						Login.items.passwordInput && Login.items.passwordInput.focus();
					}
				}
			},
			error : function() {
			}
		});
	}
};