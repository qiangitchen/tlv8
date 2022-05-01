if (!justep) {
	justep = {};
	tlv8 = {};
}

/*
 * 自定义对话框 @param {Object} id @param {Object} title @param {Object} modal @param
 * {Object} width @param {Object} height @param {Object} onInit @param {Object}
 * onOpen @param {Object} onClose @memberOf {TypeName}
 */
tlv8.Dialog = function(id, title, modal, width, height, onInit, onOpen,
		onClose) {
	this.id = id;
	this.title = title;
	this.modal = modal;
	this.width = width || 400;
	this.height = height || 300;
	this.left;
	this.top;
	this.onInit = onInit;
	this.onOpen = onOpen;
	this.onClose = onClose;
	this.closeable = true;
	this.minmaxable = true;
	this.resizable = true;
	this.neighbor = null;
	this.autoSize = false;
	this.isShowTitle = true;
	this.msgElement;
	this.initDialog();
};

tlv8.Dialog.prototype.initDialog = function() {
	var self = this;
	try {
		if (!checkPathisHave($dpcsspath + "toolbar.main.css"))
			createStyleSheet($dpcsspath + "toolbar.main.css");
		if (!checkPathisHave(cpath+"/comon/js/jquery/jquery.min.js")
				&& !checkPathisHave($dpjspath + "jquery/jquery.min.js")) {
			createJSSheet($dpcsspath + "jquery/jquery.min.js");
		}
	} catch (e) {
		alert("必须引用js：/comon/js/comon.main.js");
		return;
	}
	self.createElement();
	self.addbarItem();
	if (self.onInit) {
		if (typeof self.onInit == "function") {
			var h_v = self.onInit();
		}
	}
};

tlv8.Dialog.prototype.open = function() {
	var self = this;
	self.createElement();
	if (self.modal) {
		self.showmodal(true);
	}
	$(self.msgElement).show();
	//self.bindEvent();
};

tlv8.Dialog.prototype.close = function(event) {
	try {
		event = event || window.event;
		var self;
		if (event) {
			var _obj = event.srcElement || event.target;
			self = _obj.dialog || this;
		} else {
			self = this;
		}
		self.showmodal(false);
		$(self.msgElement).hide();
	} catch (e) {
	}
};

tlv8.Dialog.prototype.toMax = function(event) {
	try {
		event = event || window.event;
		var _obj = event.srcElement || event.target;
		var self = _obj.dialog || this;
		$(self.msgElement).width(document.body.clientWidth);
		$(self.msgElement).height(document.body.clientHeight);
		self.msgElement.style.left = 0 + "px";
		self.msgElement.style.top = 0 + "px";
		$(_obj).hide();
		$("#" + self.id + "_Nor").show();
		$("#" + self.id + "_Min").show();
	} catch (e) {
		alert(e.message);
	}
};

tlv8.Dialog.prototype.toNor = function(event) {
	try {
		event = event || window.event;
		var _obj = event.srcElement || event.target;
		var self = _obj.dialog || this;
		$(self.msgElement).width(self.width);
		$(self.msgElement).height(self.height);
		self.msgElement.style.left = self.left + "px";
		self.msgElement.style.top = self.top + "px";
		$(_obj).hide();
		$("#" + self.id + "_Max").show();
		$("#" + self.id + "_Min").show();
	} catch (e) {
		alert(e.message);
	}
};

tlv8.Dialog.prototype.toMin = function(event) {
	try {
		event = event || window.event;
		var _obj = event.srcElement || event.target;
		var self = _obj.dialog || this;
		$(self.msgElement).height(25);
		$(_obj).hide();
		$("#" + self.id + "_Max").hide();
		$("#" + self.id + "_Nor").show();
	} catch (e) {
		alert(e.message);
	}
};

tlv8.Dialog.prototype.createElement = function() {
	var self = this;
	var msg_Obj = (J$(self.id + "_dialog")) ? J$(self.id + "_dialog")
			: document.createElement("div");
	msg_Obj.setAttribute("id", self.id + "_dialog");
	msg_Obj.setAttribute("class", "panel window");
	msg_Obj.style.background = "#fff";
	msg_Obj.style.position = "absolute";
	// msg_Obj.style.border = "0px solid #73A2d6";
	msg_Obj.style.width = self.width + "px";
	msg_Obj.style.height = self.height + "px";
	var cuScrollTop = (document.body.scrollTop || document.documentElement.scrollTop);
	var myLeft = (document.body.offsetWidth - self.width) / 2;
	var myTop = (document.body.offsetHeight + cuScrollTop - self.height - 10) / 2;
	self.left = myLeft;
	self.top = myTop;
	msg_Obj.style.left = myLeft
			+ (document.body.scrollLeft || document.documentElement.scrollLeft);
	msg_Obj.style.top = (myTop < 0) ? 5 : (document.body.offsetHeight
			- self.height - 10)
			/ 2 + cuScrollTop;
	msg_Obj.style.zIndex = "9999";
	self.msgElement = msg_Obj;
	document.body.appendChild(self.msgElement);
};

tlv8.Dialog.prototype.addbarItem = function() {
	var self = this;
	var msghead = $("<div id='dialoghead' class='panel-header panel-header-noborder window-header'></div>");
	$(self.msgElement).append(msghead);
	var labelhtml = "<div class='panel-title' id='" + self.id + "_title_lab'>"
			+ self.title + "</div>";
	msghead.append(labelhtml);
	var ptool = $("<div class='panel-tool'></div>");
	var closeitem = $("<a href='javascript:void(0);' id='msgclosebtn' class='panel-tool-close' title='关闭'></a>");
	ptool.append(closeitem);
	msghead.append(ptool);
	$("#msgclosebtn").click(function() {
		self.close();
	});
	var msgbody = $("<div id='dialogbody' class='panel-body panel-body-noborder window-body'></div>");
	msgbody.height(self.height - 25);
	msgbody.css("overflow", "hidden");
	$(self.msgElement).append(msgbody);
	var bodyform = $("<div id='" + self.id
			+ "_msgTxt' class='panel' style='border:0px none;'></div>");
	bodyform.width(self.width - 2);
	bodyform.height(msgbody.height());
	msgbody.append(bodyform);
	var T_d = J$(self.id + "_title_lab");
	T_d.dialog = self;
};

tlv8.Dialog.prototype.vDrag = function(o, ho, initArr) {
	ho = ho || o;
	var self = o.dialog;
	o.style.position = "absolute";
	if (!isIE) {
		ho.firstChild.onmousedown = function() {
			return false;
		};
	}
	ho.onmousedown = function(a) {
		var d = document;
		if (!a)
			a = window.event;
		var x = a.layerX ? a.layerX : a.offsetX, y = a.layerY ? a.layerY
				: a.offsetY;
		if (ho.setCapture)
			ho.setCapture();
		else if (window.captureEvents)
			window.captureEvents(Event.MOUSEMOVE | Event.MOUSEUP);
		d.onmousemove = function(a) {
			var W_H = getBodyWH();
			if (!a)
				a = window.event;
			var mus = getMouseCoords(a);
			if (!a.pageX)
				a.pageX = mus.x;
			if (!a.pageY)
				a.pageY = mus.y;
			var tx = a.pageX - x, ty = a.pageY - y;
			if (initArr) {
				o.style.left = (tx < initArr[0] ? initArr[0]
						: tx > initArr[2] ? initArr[2] : tx)
						+ "px";
				o.style.top = (ty < initArr[1] ? initArr[1]
						: ty > initArr[3] ? initArr[3] : ty)
						+ "px";
			} else {
				var newW = parseInt(o.style.width);
				var newH = parseInt(o.style.height);
				if (tx > (W_H[0] - newW))
					tx = (W_H[0] - newW);
				if (tx < 0)
					tx = 0;
				var Height = (W_H[1] > document.body.offsetHeight) ? W_H[1]
						: document.body.offsetHeight;
				if (ty > (Height - newH))
					ty = (Height - newH);
				if (ty < 0)
					ty = 0;
				o.style.left = tx + "px";
				o.style.top = ty + "px";
			}
		};
		d.onmouseup = function() {
			if (ho.releaseCapture)
				ho.releaseCapture();
			else if (window.captureEvents)
				window.captureEvents(Event.MOUSEMOVE | Event.MOUSEUP);
			d.onmousemove = null;
			d.onmouseup = null;
		};
	};
};

tlv8.Dialog.prototype.createWebWindow = function(o, intW, intH) {
	var self = this;
	var winSelf = o;
	var winTitle = J$(self.id + "_title_lab");
	var winContent = J$("dailog_table_trIf");
	var winDbox = J$(self.id + "_footer_cod");
	var winRDbox = J$(self.id + "_rightItem");
	var winBDbox = J$(self.id + "_BottomItem");
	var minW = 200, minH = 50;
	var _self = this;
	var wX = winSelf.offsetWidth - winContent.offsetWidth;
	var wH = winSelf.offsetHeight - winContent.offsetHeight;
	winDbox.onmousedown = function(e) {
		var d = document;
		if (!e)
			e = window.event;
		var x = e.layerX ? e.layerX : e.offsetX, y = e.layerY ? e.layerY
				: e.offsetY;
		var MCD = window.getMouseCoords(e);
		winSelf.startX = MCD.x;
		winSelf.startY = MCD.y;
		winSelf.startW = winSelf.offsetWidth;
		winSelf.startH = winSelf.offsetHeight;
		if (winDbox.setCapture)
			winDbox.setCapture();
		else if (window.captureEvents)
			window.captureEvents(Event.MOUSEMOVE | Event.MOUSEUP);
		d.onmousemove = function(e) {
			if (!e)
				e = window.event;
			var mus = getMouseCoords(e);
			var newW = (winSelf.startW + (mus.x - winSelf.startX));
			var newH = (winSelf.startH + (mus.y - winSelf.startY));
			resizeWin(newW, newH);
		};
		d.onmouseup = function() {
			if (winDbox.releaseCapture)
				winDbox.releaseCapture();
			else if (window.captureEvents)
				window.captureEvents(Event.MOUSEMOVE | Event.MOUSEUP);
			d.onmousemove = null;
			d.onmouseup = null;
		};
	};
	winBDbox.onmousedown = function(e) {
		var d = document;
		if (!e)
			e = window.event;
		var x = e.layerX ? e.layerX : e.offsetX, y = e.layerY ? e.layerY
				: e.offsetY;
		var MCD = window.getMouseCoords(e);
		winSelf.startX = MCD.x;
		winSelf.startY = MCD.y;
		winSelf.startW = winSelf.offsetWidth;
		winSelf.startH = winSelf.offsetHeight;
		if (winBDbox.setCapture)
			winBDbox.setCapture();
		else if (window.captureEvents)
			window.captureEvents(Event.MOUSEMOVE | Event.MOUSEUP);
		d.onmousemove = function(e) {
			if (!e)
				e = window.event;
			var mus = getMouseCoords(e);
			var newW = parseInt(winSelf.style.width);
			var newH = (winSelf.startH + (mus.y - winSelf.startY));
			resizeWin(newW, newH);
		};
		d.onmouseup = function() {
			if (winBDbox.releaseCapture)
				winBDbox.releaseCapture();
			else if (window.captureEvents)
				window.captureEvents(Event.MOUSEMOVE | Event.MOUSEUP);
			d.onmousemove = null;
			d.onmouseup = null;
		};
	};
	winRDbox.onmousedown = function(e) {
		var d = document;
		if (!e)
			e = window.event;
		var x = e.layerX ? e.layerX : e.offsetX, y = e.layerY ? e.layerY
				: e.offsetY;
		var MCD = window.getMouseCoords(e);
		winSelf.startX = MCD.x;
		winSelf.startY = MCD.y;
		winSelf.startW = winSelf.offsetWidth;
		winSelf.startH = winSelf.offsetHeight;
		if (winRDbox.setCapture)
			winRDbox.setCapture();
		else if (window.captureEvents)
			window.captureEvents(Event.MOUSEMOVE | Event.MOUSEUP);
		d.onmousemove = function(e) {
			if (!e)
				e = window.event;
			var mus = getMouseCoords(e);
			var newW = (winSelf.startW + (mus.x - winSelf.startX));
			var newH = parseInt(winSelf.style.height);
			resizeWin(newW, newH);
		};
		d.onmouseup = function() {
			if (winRDbox.releaseCapture)
				winRDbox.releaseCapture();
			else if (window.captureEvents)
				window.captureEvents(Event.MOUSEMOVE | Event.MOUSEUP);
			d.onmousemove = null;
			d.onmouseup = null;
		};
	};
	function resizeWin(newW, newH) {
		if (self.resizable == false)
			return;
		newW = newW < minW ? minW : newW;
		newH = newH < minH ? minH : newH;
		winSelf.style.width = newW + "px";
		winSelf.style.height = newH + "px";
	}
	{
		resizeWin(intW, intH);
		self.vDrag(o, winTitle);
	}
};

tlv8.Dialog.prototype.showmodal = function(state) {
	var self = this;
	if (state) {
		var sHeight;
		if (!document.body) {
			return;
		}
		if (window.innerHeight && window.scrollMaxY) {
			sHeight = window.innerHeight + window.scrollMaxY;
		} else if (document.body.scrollHeight > document.body.offsetHeight) {
			sHeight = document.body.scrollHeight;
		} else {
			sHeight = document.body.offsetHeight;
		}
		var d_m_v = (J$(self.id + "d_m_v")) ? J$(self.id + "d_m_v") : document
				.createElement("div");
		d_m_v.setAttribute("id", self.id + "d_m_v");
		d_m_v.style.left = "0px";
		d_m_v.style.top = "0px";
		d_m_v.style.width = document.body.clientWidth;
		d_m_v.style.height = sHeight;
		d_m_v.style.position = "absolute";
		d_m_v.style.background = "#eee";
		d_m_v.style.filter = "alpha(opacity=30)";
		d_m_v.style.opacity = 0.3;
		d_m_v.style.zIndex = "1";
		document.body.appendChild(d_m_v);
	} else {
		try {
			document.body.removeChild(J$(self.id + "d_m_v"));
		} catch (e) {
		}
	}
};

tlv8.Dialog.prototype.attachEvent = function(elm, evType, fn, useCapture) {
	if (elm.addEventListener) {
		elm.addEventListener(evType, fn, useCapture);
		return true;
	} else if (elm.attachEvent) {
		var r = elm.attachEvent('on' + evType, fn);
		return r;
	} else {
		elm['on' + evType] = fn;
	}
};

tlv8.Dialog.prototype.removeEvent = function(obj, type, fn, cap) {
	var cap = cap || false;
	if (obj.removeEventListener) {
		obj.removeEventListener(type, fn, cap);
	} else {
		obj.detachEvent("on" + type, fn);
	}
};

tlv8.Dialog.prototype.bindEvent = function() {
	var self = this;
	self.msgElement.dialog = self;
	J$(self.id + "_Cancel").dialog = self;
	J$(self.id + "_Max").dialog = self;
	J$(self.id + "_Nor").dialog = self;
	J$(self.id + "_Min").dialog = self;
	self.attachEvent(J$(self.id + "_Cancel"), "click", self.close, false);
	self.attachEvent(J$(self.id + "_Max"), "click", self.toMax, false);
	self.attachEvent(J$(self.id + "_Nor"), "click", self.toNor, false);
	self.attachEvent(J$(self.id + "_Min"), "click", self.toMin, false);
	self.createWebWindow(self.msgElement, self.width, self.height);
};

tlv8.Dialog.prototype.setMinmaxable = function(bl) {
	var self = this;
	self.minmaxable = bl;
	if (bl == true) {
		$("#" + self.id + "_Max").show();
		$("#" + self.id + "_Min").show();
	} else {
		$("#" + self.id + "_Max").hide();
		$("#" + self.id + "_Min").hide();
	}
};

tlv8.Dialog.prototype.setResizable = function(bl) {
	var self = this;
	self.resizable = bl;
};

tlv8.Dialog.prototype.setContentHTML = function(str) {
	var self = this;
	J$(self.id + "_msgTxt").innerHTML = str;
};

var isIE = window.Event ? false : true;
var getMouseCoords = function(e) {
	return {
		x : isIE ? e.clientX
				+ Math.max(document.body.scrollLeft,
						document.documentElement.scrollLeft) : e.pageX,
		y : isIE ? e.clientY
				+ Math.max(document.body.scrollTop,
						document.documentElement.scrollTop) : e.pageY
	};
};

var getBodyWH = function() {
	var sW, sH;
	sW = document.body.clientWidth;
	sH = document.body.clientHeight;
	if (window.innerHeight && window.scrollMaxY) {
		sH = window.innerHeight + window.scrollMaxY;
	} else if (document.body.scrollHeight > document.body.offsetHeight) {
		sH = document.body.scrollHeight;
	} else {
		sH = document.body.offsetHeight;
	}
	return [ sW, sH ];
};