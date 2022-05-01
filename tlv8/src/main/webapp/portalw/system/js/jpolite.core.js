/**
 * @class Map
 * @description jsMap对象
 */
var Map = function() {
	var struct = function(key, value) {
		this.key = key;
		this.value = value;
	};
	var put = function(key, value) {
		for (var i = 0; i < this.arr.length; i++) {
			if (this.arr[i].key === key) {
				this.arr[i].value = value;
				return;
			}
		}
		this.arr[this.arr.length] = new struct(key, value);
	};
	var get = function(key) {
		for (var i = 0; i < this.arr.length; i++) {
			if (this.arr[i].key === key) {
				return this.arr[i].value;
			}
		}
		return null;
	};
	var remove = function(key) {
		var v;
		for (var i = 0; i < this.arr.length; i++) {
			v = this.arr.pop();
			if (v.key === key) {
				continue;
			}
			this.arr.unshift(v);
		}
	};
	var size = function() {
		return this.arr.length;
	};
	var isEmpty = function() {
		return this.arr.length <= 0;
	};
	var containsKey = function(key) {
		var result = false;
		for (var i = 0; i < this.arr.length; i++) {
			if (this.arr[i].key === key) {
				result = true;
			}
		}
		return result;
	};
	var containsValue = function(value) {
		var result = false;
		for (var i = 0; i < this.arr.length; i++) {
			if (this.arr[i].value === value) {
				result = true;
			}
		}
		return result;
	};
	var keySet = function() {
		var result = new Array();
		for (var i = 0; i < this.arr.length; i++) {
			result.push(this.arr[i].key);
		}
		return result;
	};
	this.arr = new Array();
	this.struct = struct;
	this.get = get;
	this.put = put;
	this.remove = remove;
	this.size = size;
	this.isEmpty = isEmpty;
	this.containsKey = containsKey;
	this.containsValue = containsValue;
	this.keySet = keySet;
	return this;
};
/**
 * @name toString
 * @description Map对象转换为字符串
 * @returns {string}
 */
Map.prototype.toString = function() {
	var rs = "{";
	for (var i = 0; i < this.arr.length; i++) {
		if (i > 0)
			rs += ",";
		rs += "\"" + this.arr[i].key + "\":\"" + this.arr[i].value + "\"";
	}
	rs += "}";
	return rs;
};
/*
/*
 * JPolite Core Features and Functions
 */
if (!$.jpolite) $.jpolite = {};
$.extend($.jpolite, {
	tabs : new Map(),
	getTab : function(tId){
	},
	addTab : function(tId, p){
		$.jpolite.tabs.put(tId,p);
		$('body').desktop('openApp', {
			id: p.id,
			name: p.name,
			width : Math.min($(window).width()-20,1200),
			height : Math.min($(window).height()-40,660),
			url: p.url
		});
	},
	getTabID : function(){
		var tid = $('body').attr("cwin");
		return tid;
	},
	removeTab : function(tId){
		if(tId){
			$('#'+tId).window("close");
		}
	},
	gotoTab : function(tId){
	},
	ontabclose : function(title, index){
	},
	ontabselected : function(title, index){
	},
	//初始化导航标签
	init: function(options){
		//初始化连接Portal信息；
		$.jpolite.clientInfo.init();
		
		this.Content.init(s.widgetSortable);
		//初始化桌面信息
		this.clientInfo.initClientInfo();
	},
	//初始化用户信息与服务器信息
	clientInfo:{
	    init:function(options){
	    	var o ={};
			$.extend(o,options,{
				url:cpath+"/system/User/initPortalInfo",
				async:false,
				type:"POST",
				dataFilter: function(data, type){
					var items = window["eval"]("(" + data+ ")");
					return items;
				},
				error:function(XMLHttpRequest, textStatus, errorThrown){
					//alert("error");
				},
				success:function(data, textStatus){
					var datainfo = window["eval"]("(" + data[0].data + ")");
					$.extend($.jpolite.clientInfo, datainfo);
					$.jpolite.clientInfo.orgfullname = datainfo.orgFullName;
					$.jpolite.clientInfo.personname = datainfo.personName;
				}
			});
			$.ajax(o);
		},
		initClientInfo:function(){
			$("#footer_status_info").html($.jpolite.clientInfo.personfname);
			$("#username_layout").html($.jpolite.clientInfo.personname);
		}
	}
});
