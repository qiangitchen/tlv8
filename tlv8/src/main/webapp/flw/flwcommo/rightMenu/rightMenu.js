/**
 * 右键菜单控件。<BR>
 * <P><B>参数</B><BR>
 * <CODE>para</CODE>: 建立右键菜单的初始参数<BR>
 * <P><B>适用于:</B> IE 6.0</P><BR><BR>
 * UI 面页控件
 * @param {JSON} para
 * @return {RigthMenu}
 * @author gugao
 * @version 1.0.081205
 * @since 1.0
 */
/* --------------------------------------------------	
	参数说明
	para: {width:Number, items:Array, onShow:Function, rule:JSON}
	成员语法(三种形式)	-- para.items
	-> {text:String, icon:String, type:String, alias:String, width:Number, items:Array}		--	菜单组
	-> {text:String, icon:String, type:String, alias:String, action:Function }				--	菜单项
	-> {type:String}																		--	菜单分隔线
   --------------------------------------------------*/
var RigthMenu = function(para) {
	
	var ruleName = null, show, showEvent,
	groups = {}, items = {}, actions = {}, showGroups = [],
	gTemplet, iTemplet, sTemplet, itemclass,
	itemTpl = "<div class='font j-m-$[type]' unselectable=on><nobr unselectable=on><img src='$[icon]' align='absmiddle'/><span unselectable=on>$[text]</span></nobr></div>",
	
	//创建菜单组
	buildGroup = function(obj) {
		this.gidx = obj.alias;
		(groups[obj.alias] = document.body.appendChild(this)).style.width = obj.width;
		obj = null;
	},
	//创建菜单项
	buildItem = function(obj) {
		var T = this;
		T.title = obj.text;
		T.idx = obj.alias;
		T.gidx = obj.gidx;
		T.innerHTML = itemTpl.replace(/\$\[([^\]]+)\]/g, function() {
			return obj[arguments[1]];
		});
		obj.items && (T.group = true);
		obj.action && (actions[obj.alias] = obj.action);
		items[obj.alias] = T;
		T = obj = null;
		return this;
	},
	//添加菜单项
	addItems = function(gidx, items) {
		var tmp = null;
		for(var i = 0; i < items.length; i++) {
			if(items[i].type == "splitLine") {
				//菜单分隔线
				tmp = sTemplet.cloneNode();
			}else {
				items[i].gidx = gidx;
				if(items[i].type == "group") {
					//菜单组
					buildGroup.apply(gTemplet.cloneNode(), [items[i]]);
					arguments.callee(items[i].alias, items[i].items);
					items[i].type = "arrow";
					(tmp = buildItem.apply(iTemplet.cloneNode(), [items[i]]))
						.onmousedown = function(){ window.event.cancelBubble = true;};
				}else {
					//菜单项
					items[i].type = "ibody";
					(tmp = buildItem.apply(iTemplet.cloneNode(), [items[i]])).onmousedown = function() {
						if(this.disable) window.event.cancelBubble = true; else actions[this.idx]();
					};
				}//Endif
				tmp.oncontextmenu = function(){window.event.cancelBubble = true;return false;}
				tmp.onmouseover = overItem;
				tmp.onmouseout = outItem;
			}//Endif
			groups[gidx].appendChild(tmp);
			tmp = items[i] = items[i].items = null;
		}//Endfor
		gidx = items = null;
	},
	//菜单项获得焦点
	overItem = function() {
		//如果菜单项不可用
		if(this.disable || this.contains(window.event.fromElement))
			return false;
		hideMenuPane.call(groups[this.gidx]);
		//如果是菜单组
		this.group && showMenuGruop.apply(groups[this.idx], [getDomAbsPos.call(this), this.offsetWidth]);
		this.className = "j-m-ifocus";
	},
	//菜单项失去焦点
	outItem = function() {
		//如果菜单项不可用
		if(this.disable || this.contains(window.event.toElement))
			return false;
		if(this.group) {
			//菜单组
			(groups[this.gidx].contains(window.event.toElement) ||
				(function(a, b) {
					return b ? a == b.gidx ? false : arguments.callee(a, b.offsetParent) : true;
				})(this.idx, window.event.toElement)
			) && hideMenuPane.call(groups[this.gidx]);
		}else {
			//菜单项
			this.className = "j-m-item";
		}//Endif
	},
	//在指定位置显示指定的菜单组
	showMenuGruop = function(pos, width) {
		var ts = this.style, B = document.body;
		ts.display = "";
		//检测显示位置
		ts.left = pos.x + width + this.offsetWidth > B.clientWidth ? 
			(pos.x -= this.offsetWidth - 2) < 0 ? 0 : pos.x : pos.x += width;
		ts.top = pos.y + this.offsetHeight > B.clientHeight ? 
			(pos.y -= this.offsetHeight - (!width || 25)) < 0 ? 0 : pos.y : pos.y;
		showGroups.push(this.gidx);
		pos = B = null;
	},
	//隐藏菜单组
	hideMenuPane = function() {
		var alias =  null;
		for(var i = showGroups.length - 1; i >= 0; i--) {
			if(showGroups[i] == this.gidx)
				break;
			alias = showGroups.pop();
			groups[alias].style.display = "none";
			i && (items[alias].className = "j-m-item");
		}//Endfor
		CollectGarbage();
	},
	//释放菜单
	release = function() {
		hideMenuPane();
		document.body.onmousedown = null;
	},
	//返回DOM标签在页面上的绝对坐标
	getDomAbsPos = function() {
		var tmp = this, X, Y;
		X = tmp.offsetLeft;
		Y = tmp.offsetTop;
		while((tmp = tmp.offsetParent)) {
			X += tmp.offsetLeft;
			Y += tmp.offsetTop;
		}//Endwhile
		tmp = this;
		while((tmp = tmp.parentNode)) {
			X -= tmp.scrollLeft || 0;
			Y -= tmp.scrollTop || 0;
		}//Endwhile
		tmp = null;
		return {x:X, y:Y};
	},
	//设置菜单项的有效性
	disable = function(alias, disabled) {
		items[alias].className = (items[alias].disable = items[alias]
			.lastChild.disabled = disabled) ? "j-m-idisable" : "j-m-item";
	};
	
	/** 应用菜单项显示规则 */
	this.applyRule = function(rule) {
		if(ruleName && ruleName == rule.name)
			return false;
		for(var i in items)
			disable(i, !rule.disable);
		for(var i = 0; i < rule.items.length; i++)
			disable(rule.items[i], rule.disable);
		ruleName = rule.name;
	}
	
	/** 右键菜单显示 */
	this.show = show = function(x, y) {
		document.body.onmousedown = release;
		showMenuGruop.apply(groups.rmRoot, [{x: x - 2, y: y - 2}, 0]);
	}
	
	/** 将右键菜单绑定到Obj对象上 */
	this.bind = function(obj) {
		obj.oncontextmenu = function() {
			window.event.cancelBubble = true;
			if(showEvent) {
				showEvent.call(window.event);
			}else {
				show(window.event.clientX, window.event.clientY);
			}//Endif
			return false;
		};
		obj = null;
	};
	
	//初始化
	(function() {
		//组件模板
		(gTemplet = document.createElement("div")).className = "j-m-mpanel";
		gTemplet.unselectable = "on";
		gTemplet.style.display = "none";
		(iTemplet = document.createElement("div")).className = "j-m-item";
		iTemplet.unselectable = "on";
		(sTemplet = document.createElement("div")).className = "j-m-split";
		
		//组织菜单
		para.alias = "rmRoot";
		buildGroup.apply(gTemplet.cloneNode(), [para]);
		addItems("rmRoot", para.items);
		showEvent = para.onShow;
	})();
	
	//设置显示规则
	para.rule && this.applyRule(para.rule);
	
	gTemplet = iTemplet = sTemplet = itemTpl = buildGroup = buildItem = null;
	addItems = overItem = outItem = para = para.items = null;
	CollectGarbage();
}