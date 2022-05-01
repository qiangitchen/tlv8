/**
 * 手机版页面树形组件
 * @author 陈乾
 */
tlv8.MobileTree = function() { /* 构建树对象 */
	this.treeId; /* 树的ID */
	this.treeNode = function() { /* 树的节点对象 */
		this.id; /* id */
		this.parentId; /* 父id */
		this.name; /* 内容 */
		this.isExPand = false; /* 展开状态 */
		this.icon; /* 图标 */
		this.parent = null; /* 父亲 */
		this.children = new Array(); /* 孩子 */
		this.hasChildren = 0;

		if (typeof this._isRoot == "undefined") {
			this.isRoot = function() { /* 判断是不是根节点 */
				return this.parent == null;
			};
			this._isRoot = true;
		}

		if (typeof this._isParentExpand == "undefined") {
			this.isParentExpand = function() { /* 判断父节点是否展开 */
				if (this.parent == null)
					return false;
				return this.parent.isExPand;
			};
			this._isParentExpand = true;
		}

		if (typeof this._isLeaf == "undefined") {
			this.isLeaf = function() { /* 判断是不是叶子节点 */
				if (this.children.length <= 0) {
					return true;
				} else {
					return false;
				}
			};
			this._isLeaf = true;
		}

		if (typeof this._getLevel == "undefined") {
			this.getLevel = function() { /* 得到当前层级 */
				return this.parent == null ? 0 : this.parent.getLevel() + 1;
			};
			this._getLevel = true;
		}

		if (typeof this._isVisible == "undefined") {
			this.isVisible = function() { /* 判断是否可见 */
				if (this.isRoot() || this.isParentExpand()) {
					return true;
				} else {
					return false;
				}
				;
			};
			this._isVisible = true;
		}
	};
};

tlv8.MobileTree.prototype.initTree = function(setting) { /* 初始化 */
	if (typeof setting.id == "string") { /* id */
		this.id = setting.id;
	} else {
		throw new Error("setting id set error,Please Set type to String ");
	}

	if (typeof setting.parentid == "string") { /* parentid */
		this.parentid = setting.parentid;
	} else {
		throw new Error("setting parentid set error,Please Set type to String ");
	}

	if (typeof setting.name == "string") { /* name */
		this.name = setting.name;
	} else {
		throw new Error("setting name set error,Please Set type to String ");
	}

	if (typeof setting.other == "string") { /* 其他字段 */
		this.other = setting.other;
	} else {
		throw new Error("setting other set error,Please Set type to String ");
	}

	if (typeof setting.databaseName == "string") { /* 数据库名 */
		this.databaseName = setting.databaseName;
	} else {
		throw new Error(
				"setting databaseName set error,Please Set type to String");
	}

	if (typeof setting.tableName == "string") { /* 表名 */
		this.tableName = setting.tableName;
	} else {
		throw new Error("setting tableName set error,Please Set type to String");
	}

	if (typeof setting.rootFilter == "string") { /* 根节点条件 */
		this.rootFilter = setting.rootFilter;
	} else {
		throw new Error(
				"setting rootFilter set error,Please Set type to String");
	}

	if (typeof setting.otherConditions == "string") { /* 其他条件 */
		this.otherConditions = setting.otherConditions;
	} else if (typeof setting.otherConditions == "undefined"
			|| setting.otherConditions == "" || setting.otherConditions == null) {
		this.otherConditions = "";
	} else {
		throw new Error(
				"setting otherConditions set error,Please Set type to String");
	}

	if (typeof setting.orderby == "string") { /* 排序字段 */
		this.orderby = setting.orderby;
	} else {
		throw new Error("setting orderby set error,Please Set type to String");
	}

	if (setting.rootDefaulExpand == 1 || setting.rootDefaulExpand == true) { /* rootDefaulExpand根节点是否默认展开 */
		this.rootDefaulExpand = 1;
	} else {
		this.rootDefaulExpand = 0;
		if (isNaN(setting.rootDefaulExpand) == true
				&& typeof setting.rootDefaulExpand != "boolean") { /* 如果是非数字并且不是boolean型 */
			this.rootDefaulExpand = 1;
			console
					.error("setting rootDefaulExpand set error, seted to 1 by default,Please type Set to number or boolean");
		}
	}

	if (typeof setting.context == "string") { /* 传入数据的div */
		this.context = setting.context;
	} else {
		throw new Error(
				"setting context set error,Please Set type to String Div #id");
	}

	if (typeof setting.closeicon == "string") { /* 关闭图标 */
		this.closeicon = setting.closeicon;
	} else {
		this.openicon = "images/tree_ez.png";
		console
				.error("setting closeicon set undefined,Please type Set to url ");
	}

	if (typeof setting.openicon == "string") { /* 展开的图标 */
		this.openicon = setting.openicon;
	} else {
		this.openicon = "images/tree_ex.png";
		console
				.error("setting openicon set undefined,Please type Set to url String");
	}

	this.path = setting.path;

	if (setting.callback) { /* 回调函数 */
		this.callback = setting.callback;
	}

	// var treeSql = "select ";
	// treeSql += this.id + "," + this.parentid + "," + this.name;
	// if(this.other && this.other != "") {
	// treeSql = treeSql + "," + this.other;
	// }
	// treeSql += " from " + this.tableName;
	// treeSql += " where ";
	// treeSql += this.rootFilter;
	// if(this.otherConditions != "") {
	// treeSql += " and " + this.otherConditions;
	// }
	// treeSql += " order by " + this.orderby;
	var mtree = this;
	$.ajax({
		type : "post",
		async : false,
		url : cpath+"/MobileTreeLoadAction",
		dataType: "json",
		data : {
			id : mtree.id,
			name : mtree.name,
			other : mtree.other,
			parentid : mtree.parentid,
			tablename : mtree.tableName,
			dataSource : mtree.databaseName,
			orderby : mtree.orderby,
			nowId : "",
			rootFilter : mtree.rootFilter,
			conditions : mtree.otherConditions
		},
		success : function(result) {
			var treeDatas = result.data.data;
			var nodes = mtree.datasToNodes(treeDatas, mtree.id, mtree.parentid,
					mtree.name);
			var rnodes = new Array();
			if (mtree.rootDefaulExpand >= 1) { /* 设置默认展开 */
				for (var j = 0; j < nodes.length; j++) {
					rnodes.push(nodes[j]);
					var foliageId = nodes[j].id;
					if (foliageId != "" && foliageId != undefined
							&& foliageId != null) {
						nodes[j].isExPand = true;
						mtree.setNodeIcon(nodes[j], mtree.closeicon,
								mtree.openicon, nodes[j].hasChildren);
						mtree.loadFoliage(foliageId, mtree, nodes[j], function(
								pnode, foliageDatas) {
							var foliagNode = mtree.datasToNodes(foliageDatas,
									mtree.id, mtree.parentid, mtree.name);
							pnode.hasChildren = foliagNode.length;
							var mhtml = "";
							for (var n = 0; n < foliagNode.length; n++) {
								if (foliagNode[n].id != undefined) {
									mtree.setNodeIcon(foliagNode[n],
											mtree.closeicon, mtree.openicon,
											foliagNode[n].hasChildren);
									rnodes.push(foliagNode[n]);
									foliagNode[n].parent = pnode;
									pnode.children.push(foliagNode[n]);
									mhtml += mtree
											.createFoliagHtml(foliagNode[n]);
								}
							}
							jQuery("#" + pnode.id).after(mhtml);
							for (var j = 0; j < rnodes.length; j++) { /* 看我有没有孩子 */
								var foliageId = rnodes[j].id;
								if (foliageId != "" && foliageId != undefined
										&& foliageId != null) {
									var count = rnodes[j].hasChildren;
									if (count > 0 && count != undefined) {
										mtree.setNodeIcon(rnodes[j],
												mtree.closeicon,
												mtree.openicon, count);
									}
								}
							}
							var mVisibleNodes = mtree.filterVisibleNodes(
									rnodes, mtree.closeicon, mtree.openicon);
							mtree.createTreeView(mtree.context, mVisibleNodes,
									rnodes, mtree.closeicon, mtree.openicon);
							mtree.listenTree(mtree.context, rnodes, mtree,
									mtree.closeicon, mtree.openicon);
						});
					}
				}
			} else {
				for (var j = 0; j < nodes.length; j++) { /* 看我有没有孩子 */
					var foliageId = nodes[j].id;
					if (foliageId != "" && foliageId != undefined
							&& foliageId != null) {
						var count = nodes[j].hasChildren;
						if (count > 0 && count != undefined) {
							mtree.setNodeIcon(nodes[j], mtree.closeicon,
									mtree.openicon, count);
						}
					}
				}
				var mVisibleNodes = mtree.filterVisibleNodes(nodes,
						mtree.closeicon, mtree.openicon);
				mtree.createTreeView(mtree.context, mVisibleNodes, nodes,
						mtree.closeicon, mtree.openicon);
				mtree.listenTree(mtree.context, nodes, mtree, mtree.closeicon,
						mtree.openicon);
			}
		},
		error : function() {
			alert("请求出错处理");
			// 请求出错处理
		}
	});
};

tlv8.MobileTree.prototype.loadFoliage = function(nowId, tree, nnode,
		loadcallback) { /* 加载枝叶 */
	// var treeSql = "select ";
	// treeSql += tree.id + "," + tree.parentid + "," + tree.name;
	// if(tree.other && tree.other != "") {
	// treeSql = treeSql + "," + tree.other;
	// }
	// treeSql += " from " + tree.tableName;
	// treeSql += " where ";
	// treeSql += tree.parentid + "='" + nowId + "'";
	// if(tree.otherConditions != "") {
	// treeSql += " and " + tree.otherConditions;
	// }
	// treeSql += " order by " + tree.orderby;
	var mtree = this;
	$.ajax({
		type : "post",
		url : cpath+"/MobileTreeLoadAction",
		dataType: "json",
		async : false,
		data : {
			id : mtree.id,
			name : mtree.name,
			other : mtree.other,
			parentid : mtree.parentid,
			tablename : mtree.tableName,
			dataSource : mtree.databaseName,
			orderby : mtree.orderby,
			nowId : nowId,
			rootFilter : mtree.rootFilter,
			conditions : mtree.otherConditions
		},
		success : function(result) {
			var foliageDatas = result.data.data;
			loadcallback(nnode, foliageDatas);
		},
		error : function() {
			alert("请求出错处理");
			// 请求出错处理
		}
	});
};

tlv8.MobileTree.prototype.datasToNodes = function(tdatas, id, pid, name) { /* datas数据转节点的方法 */
	var nodes = new Array();
	var jsonObj = jQuery.parseJSON(tdatas);
	for (var i = 0; i < jsonObj.length; i++) {
		var node = new this.treeNode();
		node.id = jsonObj[i][id];
		node.parentId = jsonObj[i][pid];
		node.name = jsonObj[i][name];
		node.hasChildren = jsonObj[i].COUNT; // jsonObj[i].COUNT;默认为都有子
		$.extend(node, jsonObj[i]); // 将查询返回的字段赋给node
		nodes.push(node);
	}
	for (var i = 0; i < nodes.length; i++) { /* 确定节点间的关系 */
		var n = nodes[i];
		n.children = new Array();
		var m = new this.treeNode();
		m.children = new Array();
		for (var j = i + 1; j < nodes.length; j++) {
			m = nodes[j];
			if (n.id == m.parentId) {
				m.parent = n;
				n.children.push(m);
			} else if (m.id == n.parentId) {
				n.parent = m;
				m.children.push(n);
			}
		}
	}
	return nodes;
};

tlv8.MobileTree.prototype.getRootNodes = function(nodes) { /* 得到树里面所有根节点的方法 */
	var roots = new Array(Node);
	for (var i = 0; i < nodes.length; i++) {
		if (nodes[i].parent == null) {
			roots.push(nodes[i]);
		}
	}
	return roots;
};

tlv8.MobileTree.prototype.sortedNodes = function(nodes, defaultExpandLevel) { /* 分类 */
	var roots = this.getRootNodes(nodes);
	var result = new Array();
	for (var i = 0; i < roots.length; i++) { /* 遍历根节点 */
		if (roots[i].id != undefined) {
			this.addNode(result, roots[i], defaultExpandLevel, 1);
		}
	}
	return result;
};

tlv8.MobileTree.prototype.addNode = function(result, rootNode,
		defaultExpandLevel, currentLevel) { /* 往根节点后追加节点 */
	if (rootNode.id != undefined) {
		result.push(rootNode);
		if (defaultExpandLevel >= currentLevel) { /* 如果默认展开层级大于等于当前层级，设置展开状态为true */
			rootNode.isExPand = true;
		}
		if (rootNode.isLeaf()) {
			return false;
		}
		/* 如果是叶子节点终止 */
		for (var i = 0; i < rootNode.children.length; i++) {
			if (rootNode.children[i].id != undefined) {
				this.addNode(result, rootNode.children[i], defaultExpandLevel,
						currentLevel + 1);
			}
		}
	}
};

tlv8.MobileTree.prototype.filterVisibleNodes = function(nodes, closeicon,
		openicon) { /* 过滤出树里面可见节点的方法 */
	var result = new Array();
	for (var i = 0; i < nodes.length; i++) {
		if (nodes[i].id != undefined) {
			if (nodes[i].isRoot() || nodes[i].isParentExpand()) {
				result.push(nodes[i]);
			}
		}
	}
	return result;
};

/* 为Node设置图标 */
tlv8.MobileTree.prototype.setNodeIcon = function(node, closeicon,
		openicon, childrenCount) {
	if (node.id != undefined) {
		if (childrenCount > 0 && node.isExPand) {
			node.icon = openicon;
		} else if (childrenCount > 0 && !node.isExPand) {
			node.icon = closeicon;
		} else {
			node.icon = -1;
		}
	}
};

tlv8.MobileTree.prototype.createTreeView = function(context,
		mVisibleNodes, allnodes, closeicon, openicon) {
	var treeHtml = this.createTreeHtml(mVisibleNodes, context);
	jQuery(context).html(treeHtml);
	var tree = this; /* 当前树对象 */
	this.listenTree(context, allnodes, tree, closeicon, openicon);
};

tlv8.MobileTree.prototype.listenTree = function(context, mAllNOdes, tree,
		closeicon, openicon) {
	$(context + " li").unbind("click");
	var mtree = this;
	jQuery(context + " li").bind(
			"click",
			function() {
				var nodeid = this.id;
				var nownow = new mtree.treeNode();
				for (var i = 0; i < mAllNOdes.length; i++) {
					if (nodeid == mAllNOdes[i].id) {
						nownow = mAllNOdes[i];
					}
				}
				nownow.isExPand = !nownow.isExPand;
				if (nownow.isExPand == true) {
					tree.setNodeIcon(nownow, tree.closeicon, tree.openicon, 1);
					tree.changeImg(nownow);
					if (nownow.children.length <= 0) {
						var nodeObj = this;
						tree.loadFoliage(nownow.id, tree, nownow, function(
								pnode, loadNodes) {
							var foliagNodes = tree.datasToNodes(loadNodes,
									tree.id, tree.parentid, tree.name);
							nownow.children = foliagNodes;
							var foliagHtml = "";
							for (var n = 0; n < foliagNodes.length; n++) {
								if (foliagNodes[n].id != undefined) {
									tree.setNodeIcon(foliagNodes[n],
											tree.closeicon, tree.openicon,
											foliagNodes[n].hasChildren);
									foliagNodes[n].parent = nownow;
									foliagHtml += tree
											.createFoliagHtml(foliagNodes[n]);
									mAllNOdes.push(foliagNodes[n]);
								}
							}
							jQuery(nodeObj).after(foliagHtml);
							tree.listenTree(context, mAllNOdes, tree,
									closeicon, openicon);
							tree.showTree(nownow, tree);
						});
					} else {
						tree.showTree(nownow, tree);
					}
				} else if (nownow.isExPand == false) {
					tree.closeTree(nownow, tree);
					tree.setNodeIcon(nownow, tree.closeicon, tree.openicon, 1);
					tree.changeImg(nownow);
				}
				if (mtree.callback && mtree.callback.onClick) {
					mtree.callback.onClick(event, mtree.treeId, nownow);
				}
			});
};

tlv8.MobileTree.prototype.changeImg = function(node) {
	if (node.isLeaf())
		return;
	jQuery("#" + node.id).find("img").attr("src", node.icon);
};

tlv8.MobileTree.prototype.showTree = function(node, tree) {
	if (node.children.length <= 0)
		return;
	for (var i = 0; i < node.children.length; i++) {
		jQuery("#" + node.children[i].id).show();
	}
	node.icon = tree.openicon;
	jQuery("#" + node.id).find("img").attr("src", node.icon);
};

tlv8.MobileTree.prototype.closeTree = function(node, tree) { /* 关闭树的方法 */
	if (node.children.length <= 0)
		return;
	for (var j = 0; j < node.children.length; j++) {
		node.children[j].isExPand = false;
		jQuery("#" + node.children[j].id).hide();
		node.children[j].icon = tree.closeicon;
		jQuery("#" + node.children[j].id).find("img").attr("src",
				node.children[j].icon);
		tree.closeTree(node.children[j], tree);
	}
	;
	jQuery("#" + node.id).find("img").attr("src", node.icon);
};

tlv8.MobileTree.prototype.createFoliagHtml = function(node) {
	var html = "";
	if (node.id != undefined) {
		var position = node.getLevel() * 30 + 15;
		html += "<li id='" + node.id
				+ "' class='mui-table-view-cell'  style='padding-left:"
				+ position + "px;' onclick=''>";
		if (node.icon != -1) {
			html += "<img src=" + node.icon + " />";
		}
		html += node.name;
		html += "</li>";
	}
	;
	return html;
};

tlv8.MobileTree.prototype.createTreeHtml = function(mVisibleNodes, context) {
	var html = "<ul class='mui-table-view'>";
	for (var i = 0; i < mVisibleNodes.length; i++) {
		if (mVisibleNodes[i].id != undefined) {
			var position = mVisibleNodes[i].getLevel() * 30 + 15;
			var node = new this.treeNode();
			node = mVisibleNodes[i];
			html += "<li id='" + mVisibleNodes[i].id
					+ "' class='mui-table-view-cell'  style='padding-left:"
					+ position + "px;' >";
			if (node.icon != -1 && node.icon != undefined) {
				html += "<img src=" + node.icon + " />";
			}
			html += mVisibleNodes[i].name;
			html += "</li>";
		}
		;
	}
	html += "</ul>";
	return html;
};

/*
 * 快速定位 @param {Object} expandFlag @memberOf {TypeName}
 */
tlv8.MobileTree.prototype.quickPosition = function(text) {
	if (!text || text == "")
		return;
	var qNode = new Array();
	try {
		var action = "QuickTreeAction";
		var path = this.path;
		var quickCells = this.name;
		var param = new tlv8.RequestParam();
		var quicktext = this.id + "," + this.name + "," + this.tableName + ","
				+ this.databaseName + "," + this.parentid + "," + text + ","
				+ path + "," + (this.rootFilter ? this.rootFilter : "") + ","
				+ (this.otherConditions ? this.otherConditions : "");
		param.set("quicktext", quicktext);
		param.set("quickCells", quickCells);
		param.set("cloums", this.other);
		var searchre = tlv8.XMLHttpRequest(action, param, "post", false,
				null);
		qNode = searchre.jsonResult;
		if (typeof qNode == "string") {
			qNode = window.eval("(" + qNode + ")");
		}
		if (qNode.length < 1) {
			alert("未找到[" + text + "]对应的内容!");
			return;
		}
		var node = qNode[0];

		var myparentsID = node[path].split("/");

		if (myparentsID.length < 1)
			return;

		if (myparentsID.length == 1) {
			if (node.id != "") {
				$("#" + node.id).click();
			}
		} else {
			for (var i = 0; i < myparentsID.length - 1; i++) {
				try {
					var nodeid = myparentsID[i].indexOf(".") > 0 ? myparentsID[i]
							.substring(0, myparentsID[i].indexOf("."))
							: myparentsID[i];
					if (nodeid != "") {
						$("#" + nodeid).click();
					}
				} catch (e) {
				}
			}
		}
	} catch (e) {
		alert("未找到[" + text + "]对应的内容!");
	}
};

/**
*以下为了兼容云捷代码
*/
justep.yn = tlv8;