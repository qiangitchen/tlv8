var addOrgItem, deleteUserItem, refreshUserItem, disableUserItem, ableUserItem, moveOrgItem, viewOrg, sortOrgItem;// 新增，删除，禁用，启用，移动按钮，显示机构
var currentNode;
var currentgrid = null;
var currenttreeID = null;
var currenttreeName = null;
var sorgkindid = null;
var scode = null;
var sfname = null;
var sfcode = null;
var sparent = null;
var sfid = null;
/* ==数据源===此项为必须定义== */
var data = new tlv8.Data();
data.setTable("SA_OPOrg");
data.setOrderby("SLEVEL asc,SSEQUENCE asc");

var disassignPsmItem;// 取消分配按钮
/* =========创建树========== */

var param = {
	cell : {
		databaseName : "system",// 数据库
		tableName : "SA_OPORG",// 对应的表名
		id : "SID",// 设置构建树的id
		name : "SNAME",// 树显示的名称
		parent : "SPARENT",// 表示树的层级
		other : "SFID,SFNAME,SORGKINDID,SCODE,SFCODE",// 树中所带字段信息
		rootFilter : "SPARENT is null", // 跟节点条件
		filter : " SCODE != 'SYSTEM' and SVALIDSTATE != -1 ",
		orderby : "SSEQUENCE asc" // 排序字段
	}
};
var setting = {
	view : {
		selectedMulti : false, // 设置是否允许同时选中多个节点。默认值: true
		autoCancelSelected : false,
		dblClickExpand : true
	// 双击展开
	},
	data : {
		simpleData : {
			enable : true
		}
	},
	async : {
		enable : true, // 异步加载
		url : "TreeSelectAction",// 加载数据的Action,可以自定义
		autoParam : [ "id=currenid" ]
	},
	isquickPosition : {
		enable : true, // 是否有快速查询框
		url : "QuickTreeAction",
		quickCells : "SID,SCODE,SNAME",// 用于快速查询的字段
		path : "SFID"// 查询路径字段
	},
	callback : {
		beforeClick : beforeClick
	}
};

var MainJtree = new Jtree();
/*---------*/
var maingrid = null;
function getData() {
	MainJtree.init("JtreeView", setting, param);
	var d = document.getElementById("main-grid-view");
	var labelid = "No,SVALIDSTATE,SORGKINDID,SCODE,SNAME,SADDRESS,SDESCRIPTION,SFCODE,SFNAME,SPARENT,SPERSONID,SNODEKIND";
	var labels = "No.,state,,编号,名称,地址,描述,全编号,全名称,SPARENT,spersonid,SNODEKIND";
	var labelwidth = "40,0,30,100,120,120,200,120,120,0,0,0";
	var datatype = "ro,ro,html:readKindShow,string,html:org_name_html,string,string,string,string,ro,ro,ro";// 设置字段类型
	var dataAction = {
		"queryAction" : "getOrgGridInfo",
		"savAction" : "saveOrgGridInfo",
		"deleteAction" : "deleteOrgGridInfo"
	};
	maingrid = new tlv8.createGrid(d, labelid, labels, labelwidth, dataAction,
			"100%", "100%", data, 20,
			"upper(sName) != upper('system') and SVALIDSTATE <> -1", "", "",
			datatype, "false", "true");
	// 设置按钮显示{新增、保存、刷新、删除}
	maingrid.grid.settoolbar(false, false, false, false);
	// 设置是否可编辑
	maingrid.grid.seteditModel(false);
	currentgrid = maingrid.grid;
	currentgrid.setExcelexpBar(true);// 导出
	addOrgItem = currentgrid.insertSelfBar("新增", "42px", "addorgitem()",
			"../image/insertMore.gif");
	deleteUserItem = currentgrid.insertSelfBar("删除", "30px", "deleteorgitem()",
			"../image/delete.gif");
	refreshUserItem = currentgrid.insertSelfBar("刷新", "30px",
			"refreshorgitem()", "../image/refresh.gif");
	disableUserItem = currentgrid.insertSelfBar("禁用", "30px", "disabledUser()",
			"../image/disable.gif");
	ableUserItem = currentgrid.insertSelfBar("启用", "30px", "abledUser()",
			"../image/enable.gif");
	moveOrgItem = currentgrid.insertSelfBar("移动", "30px", "moveOrg()",
			"../image/moveOrg.gif");
	sortOrgItem = currentgrid.insertSelfBar("排序", "30px", "sortOrgAction()",
			"../image/sort.gif");
	resetPassItem = currentgrid.insertSelfBar("重置密码", "30px", "", "../../../comon/image/toolbar/action/un_enable.gif");
	var myItemH = "<table class='toolbar' style='width:290px;'><tr>";
	myItemH += "<td width='90px' style='padding-right:5px;'>"
			+ "<a href='javascript:void(0)' class='toobar_item' id='disassignPsmItem' title='取消分配' style='height:30px;padding:5px;border-radius: 3px;'>"
			+ "<img src='../image/un_masterPsm-disable.gif' id='disassignPsmItemImg' style='float:left;'/>"
			+ "<span style='float:left;cursor: pointer;margin-left:5px;color:#333;font-size:12px;line-height:1.5;'>取消分配</span></a></td>";
	myItemH += "<td width='100px' style='padding-right:5px;'>"
			+ "<button class='btn btn-default btn-sm' id='setMemberOrgItem' title='设置所属部门'>设置所属部门</button></td>";
	myItemH += "<td align='left' style='padding-right:5px;'><a class='toobar_item' style='height:30px;border-radius: 3px; padding:0 5px;'>"
			+ "<input type='checkbox' title='显示父机构信息'  checked onclick='checkViewOrg(this)' id='checkViewOrgBox' style='float:left;margin-top:-2px;'/>"
			+ "<span style='float:left;cursor: pointer;margin-left:5px;color:#333;font-size:12px;line-height:30px;'>显示父机构</span>"
			+ "</a></td>";
	myItemH += "</tr></table>";
	document.getElementById(d.id + '-grid-item').innerHTML = myItemH;
}

function readKindShow(event) {
	var disenable = currentgrid.getValueByName("SVALIDSTATE", event.rowid);
	var rehml = "<div style='width:100%;text-align:center;'>";
	if (event.value == "ogn" || event.value == "org") {
		if (disenable == "1") {
			rehml += "<img src='" + cpath
					+ "/comon/image/toolbar/org/org.gif' title='机构'/>";
		} else {
			rehml += "<img src='" + cpath
					+ "/comon/image/toolbar/org/org-disable.gif' title='机构'/>";
		}
	} else if (event.value == "dept" || event.value == "dpt") {
		if (disenable == "1") {
			rehml += "<img src='" + cpath
					+ "/comon/image/toolbar/org/dept.gif' title='部门'/>";
		} else {
			rehml += "<img src='" + cpath
					+ "/comon/image/toolbar/org/dept-disable.gif' title='部门'/>";
		}
	} else if (event.value == "pos") {
		if (disenable == "1") {
			rehml += "<img src='" + cpath
					+ "/comon/image/toolbar/org/pos.gif' title='岗位'/>";
		} else {
			rehml += "<img src='" + cpath
					+ "/comon/image/toolbar/org/pos-disable.gif' title='岗位'/>";
		}
	} else if (event.value == "psm") {
		if (disenable == "1") {
			rehml += "<img src='" + cpath
					+ "/comon/image/toolbar/org/person.gif' title='人员'/>";
		} else {
			rehml += "<img src='"
					+ cpath
					+ "/comon/image/toolbar/org/person-disable.gif' title='人员'/>";
		}
	}
	rehml += "</div>";
	return rehml;
}

var glob_CurrentRowSName;
function gridselect(grid) {
	var rowid = grid.CurrentRowId;
	glob_CurrentRowSName = grid.getValueByName("sName", rowid);
	var SVALIDSTATE = currentgrid.getValueByName("SVALIDSTATE", rowid);
	if (SVALIDSTATE == "0") {
		document.getElementById(disableUserItem).src = "../image/un_disable.gif";
		document.getElementById(disableUserItem).onclick = "";
		document.getElementById(ableUserItem).src = "../image/enable.gif";
		document.getElementById(ableUserItem).onclick = abledUser;
	} else {
		document.getElementById(disableUserItem).src = "../image/disable.gif";
		document.getElementById(disableUserItem).onclick = disabledUser;
		document.getElementById(ableUserItem).src = "../image/un_enable.gif";
		document.getElementById(ableUserItem).onclick = "";
	}
	var kind = currentgrid.getValueByName("SORGKINDID", rowid);
	if (kind == "psm") {
		document.getElementById("setMemberOrgItem").disabled = false;
		document.getElementById("setMemberOrgItem").onclick = setMemberOrg;
		document.getElementById(resetPassItem).src = "../../../comon/image/toolbar/action/enable.gif";
		document.getElementById(resetPassItem).disabled = false;
		document.getElementById(resetPassItem).onclick = resetPassword;
	} else {
		document.getElementById("setMemberOrgItem").disabled = true;
		document.getElementById("setMemberOrgItem").onclick = null;
		document.getElementById(resetPassItem).src = "../../../comon/image/toolbar/action/un_enable.gif";
		document.getElementById(resetPassItem).disabled = true;
		document.getElementById(resetPassItem).onclick = null;
	}
	var nodekind = currentgrid.getValueByName("SNODEKIND", rowid);
	if (kind == "psm" && nodekind == "nkLimb") {
		document.getElementById("disassignPsmItemImg").src = "../image/masterPsm-disable.gif";
		document.getElementById("disassignPsmItem").disabled = false;
		document.getElementById("disassignPsmItem").onclick = disassignPsmFn;
	} else {
		document.getElementById("disassignPsmItemImg").src = "../image/un_masterPsm-disable.gif";
		document.getElementById("disassignPsmItem").disabled = true;
		document.getElementById("disassignPsmItem").onclick = null;
	}
}

// 重置密码
function resetPassword() {
	var rowid = currentgrid.CurrentRowId;
	if (!rowid || rowid == "") {
		alert("请您选中需要重置的行！");
		return;
	} else if (confirm("确定将该用户密码设置为初始密码吗?")) {
		var param = new tlv8.RequestParam();
		var r = tlv8.XMLHttpRequest("ResetPassword?rowid="
				+ currentgrid.getValueByName("SPERSONID", rowid), param,
				"post", false, null);
		if (r.data.flag != "true") {
			alert(r.data.message);
		} else {
			currentgrid.refreshData();
		}
	}
}

// 取消人员分配
function disassignPsmFn() {
	var rowid = currentgrid.CurrentRowId;
	if (!rowid || rowid == "") {
		alert("请您选中需要取消的行！");
		return;
	} else if (confirm("取消后不能恢复，确认取消吗?")) {
		var param = new tlv8.RequestParam();
		param.set("rowid", rowid);
		var r = tlv8.XMLHttpRequest("disassignPsmAction", param, "post", false,
				null);
		if (r.data.flag != "true") {
			alert(r.data.message);
		} else {
			currentgrid.refreshData();
			creatPsm_dailogcallback(currenttreeID);
		}
	}
}

function org_name_html(event) {
	return "<a href='javascript:editOrgData()'>" + event.value + "</a>";
}

function beforeClick(treeId, treeNode) {
	currentNode = treeNode;
	var filter = "sParent = '" + treeNode.id + "'";
	if ($("#checkViewOrgBox").get(0).checked) {
		filter += " or sID = '" + treeNode.id + "'";
	}
	document.getElementById("main_org_trr").rowid = treeNode.id;
	currentgrid.setStaticFilter(filter);
	maingrid.grid.refreshData();
	currenttreeID = treeNode.id;
	currenttreeName = treeNode.name;
	sorgkindid = treeNode.SORGKINDID;
	scode = treeNode.SCODE;
	sfname = treeNode.SFNAME;
	sfcode = treeNode.SFCODE;
	sfid = treeNode.SFID;
	sparent = treeNode.parent;
	if ("org" == sorgkindid || sorgkindid == "ogn") {
		$(".buttnew").removeAttr("disabled");
	}
	if ("dpt" == sorgkindid) {
		$("#org").attr({
			disabled : "disabled"
		});
		$("#ogn").attr({
			disabled : "disabled"
		});
		// $("#dpt").attr({
		// disabled : "disabled"
		// });
		$("#dpt").removeAttr("disabled");
		$("#pos").removeAttr("disabled");
		$("#psm").removeAttr("disabled");
		$("#assignPerson").removeAttr("disabled");
	}
	if ("pos" == sorgkindid) {
		$("#org").attr({
			disabled : "disabled"
		});
		$("#dept").attr({
			disabled : "disabled"
		});
		$("#pos").attr({
			disabled : "disabled"
		});
		$("#psm").removeAttr("disabled");
		$("#assignPerson").removeAttr("disabled");
	}
	if ("psm" == sorgkindid) {
		$(".buttnew").attr({
			disabled : "disabled"
		});
	}
}

// 获取弹出菜单的位置
var count = 0;
function addorgitem() {
	count++;
	var aNode = $("#" + addOrgItem);
	// 找到当前节点的位置
	var offset = aNode.offset();
	// 设置弹出框的位置
	$("#showdivt").css("left", offset.left + "px").css("top",
			(offset.top + 22) + "px").slideDown("fast");
	// .slideDown("fast");
	$("body").bind("mousedown", onBodyDown);
	// if (count % 2 == 0) {
	// hideMenu();
	// }
}
// 隐藏树
function hideMenu() {
	$("#showdivt").fadeOut("fast");
	$("body").unbind("mousedown", onBodyDown);
}
function onBodyDown(event) {
	if (!(event.target.id == addOrgItem || event.target.id == "showdivt" || $(
			event.target).parents("#showdivt").length > 0)) {
		hideMenu();
	}
}
var BakData;
function creat_dailogcallback(data) {
	BakData = data;
	MainJtree.refreshJtree("JtreeView");
	setTimeout('int_tree()', 500);
}
function int_tree() {
	MainJtree.quickPosition(BakData);// 快速查询
}
// 添加组织
function newognData() {
	hideMenu();
	tlv8.portal.dailog.openDailog('新建组织',
			"/SA/OPM/organization/dialog/organ-org-createorg.html?rowid="
					+ currenttreeID + "&name=" + J_u_encode(currenttreeName)
					+ "&scode=" + scode + "&sfname=" + J_u_encode(sfname)
					+ "&sfcode=" + sfcode + "&sparent=" + sparent
					+ "&type=ogn&sfid=" + sfid, 700, 500, creat_dailogcallback,
			null);

}
// /新增部门
function newOrgData() {
	hideMenu();
	tlv8.portal.dailog.openDailog('新增部门',
			"/SA/OPM/organization/dialog/organ-org-createorg.html?rowid="
					+ currenttreeID + "&name=" + J_u_encode(currenttreeName)
					+ "&scode=" + scode + "&sfname=" + J_u_encode(sfname)
					+ "&sfcode=" + sfcode + "&sparent=" + sparent
					+ "&type=dpt&sfid=" + sfid, 700, 500, creat_dailogcallback,
			null);

}
// /新增岗位
function newPosData() {
	hideMenu();
	tlv8.portal.dailog.openDailog('新增部门',
			"/SA/OPM/organization/dialog/organ-org-createorg.html?rowid="
					+ currenttreeID + "&name=" + J_u_encode(currenttreeName)
					+ "&scode=" + scode + "&sfname=" + J_u_encode(sfname)
					+ "&sfcode=" + sfcode + "&sparent=" + sparent
					+ "&type=pos&sfid=" + sfid, 700, 500, creat_dailogcallback,
			null);

}
// 新增人员
function newPsmData() {
	hideMenu();
	tlv8.portal.dailog.openDailog('新建人员',
			"/SA/OPM/organization/dialog/organ-psm-createpsm.html?rowid="
					+ currenttreeID + "&name=" + J_u_encode(currenttreeName)
					+ "&scode=" + scode + "&sfname=" + J_u_encode(sfname)
					+ "&sfcode=" + sfcode + "&sparent=" + sparent + "&sfid="
					+ sfid, 800, 490, creatPsm_dailogcallback, null);

}
// 分配人员
function assignPsmData() {
	hideMenu();
	tlv8.portal.dailog.openDailog('分配人员',
			"/SA/OPM/dialogs/selectMultiPsm/SelectChPsm.html?rowid="
					+ currenttreeID, 800, 490, assign_dailogcallback, null);
}
function assign_dailogcallback(data) {
	var param = new tlv8.RequestParam();
	param.set("orgId", currenttreeID);
	param.set("personIds", data.id);
	tlv8.XMLHttpRequest("appendPersonMembers", param, "post", true, function(
			data) {
		var r = eval(data.data);
		if (r.flag == "false") {
			alert(r.message);
		} else {
			sAlert("操作成功!", 500);
			creat_dailogcallback(currenttreeID);// 分配完成刷新数据
		}
	});
}
// 新增、编辑人员信息回调
function creatPsm_dailogcallback(data) {
	MainJtree.refreshJtree("JtreeView");
	MainJtree.quickPosition(currenttreeID);
	// var filter = "sParent = '" + currenttreeID + "'";
	// if ($("#checkViewOrgBox").get(0).checked) {
	// filter += " or sID = '" + currenttreeID + "'";
	// }
	// document.getElementById("main_org_trr").rowid = currenttreeID;
	// currentgrid.setStaticFilter(filter);
	// currentgrid.refreshData();
}
// 双击grid
function editOrgData(event) {
	var rowid = currentgrid.getCurrentRowId();
	var SORGKINDID = currentgrid.getValueByName("SORGKINDID", rowid);
	var SPARENT = currentgrid.getValueByName("SPARENT", rowid);
	var SPERSONID = currentgrid.getValueByName("SPERSONID", rowid);
	if (SORGKINDID == "psm") {
		tlv8.portal.dailog
				.openDailog('人员信息',
						"/SA/OPM/organization/dialog/organ-psm-createpsm.html?gridrowid="
								+ rowid + "&name=" + currenttreeName
								+ "&scode=" + scode + "&sfname="
								+ J_u_encode(sfname) + "&sfcode=" + sfcode
								+ "&SPERSONID=" + SPERSONID + "&operator=edit",
						800, 490, creatPsm_dailogcallback, null);
	} else {
		tlv8.portal.dailog.openDailog('机构管理',
				"/SA/OPM/organization/dialog/organ-org-createorg.html?gridrowid="
						+ rowid + "&name=" + currenttreeName + "&sparent="
						+ SPARENT + "&operator=edit", 700, 500,
				creat_dailogcallback, null);
	}
}
// 禁用组织
function disabledUser() {
	if (confirm("禁用该组织，组织下的所有用户将不能使用系统，确定禁用吗?")) {
		if (confirm("禁用该组织，将保留组织信息，但无法使用系统，如需再次使用选择启用即可，确定禁用吗?")) {
			var rowid = currentgrid.CurrentRowId;
			if (rowid != "" && rowid != null) {
				var pam = new tlv8.RequestParam();
				pam.set("rowid", rowid);
				tlv8.XMLHttpRequest("disableOrganization", pam, "post", true,
						function(data) {
							var r = eval(data.data);
							if (r.flag == "false") {
								alert(r.message);
							} else {
								sAlert("操作成功!", 500);
								currentgrid.refreshData();
							}
						});

			} else {
				alert("请在列表中选择要禁用的组织！");
			}
		}
	} else {
		return;
	}
}

// 启用组织机构
function abledUser() {
	var rowid = currentgrid.CurrentRowId;
	if (rowid != "" && rowid != null) {
		var pam = new tlv8.RequestParam();
		pam.set("rowid", rowid);
		tlv8.XMLHttpRequest("enableOrganization", pam, "post", true, function(
				data) {
			var r = eval(data.data);
			if (r.flag == "false") {
				alert(r.message);
			} else {
				sAlert("操作成功!", 500);
				currentgrid.refreshData();
			}
		});
	} else {
		alert("请在列表中选择要启用的组织！");
	}
}

// 删除
function deleteorgitem() {
	if (confirm("删除组织将会删除组织下的所有机构及人员信息。\n已删除组织可在回收站中查询，回收站删除将彻底删除。\n确认删除吗?")) {
		currentgrid.deleteData(null, false);
	}
}

// 刷新
function refreshorgitem() {
	MainJtree.refreshJtree("JtreeView", function() {
		MainJtree.selectNode(currentNode, false);
	});
	// currentgrid.refreshData();
}

/*
 * 设置所属部门 @param {Object} psnid @param {Object} orgid
 */
function setMemberOrg() {
	var rowid = currentgrid.CurrentRowId;
	var name = currentgrid.getValueByName("SNAME", rowid);
	if (confirm("确认将【" + name + "】的主部门设置为【" + currentNode.SNAME + "】吗?")) {
		var param1 = new tlv8.RequestParam();
		param1.set("rowid", rowid);
		tlv8.XMLHttpRequest("setMemberOrgAction", param1, "post", true,
				function(r) {
					if (r.data.flag == "true") {
						alert("设置成功！");
						currentgrid.refreshData();
					} else {
						alert(r.data.message);
					}
				});
	}
}

// 移动数据回调
function dailogcallback(data) {
	var rowid = currentgrid.CurrentRowId;
	var param1 = new tlv8.RequestParam();
	param1.set("rowid", rowid);
	param1.set("orgID", data);
	var r = tlv8.XMLHttpRequest("moveOrgAction", param1, "post", false, null);
	if (r.data.flag != "true") {
		alert(r.data.message);
		return;
	}
	MainJtree.refreshJtree();
	BakData = currenttreeName;
	MainJtree.refreshJtree("JtreeView");
	setTimeout('int_tree()', 500);
}
// 移动
function moveOrg() {
	var rowid = currentgrid.CurrentRowId;
	if (!rowid || rowid == "") {
		alert("未选中数据!");
		return;
	}
	tlv8.portal.dailog.openDailog('移动机构',
			"/SA/OPM/organization/moveOrg.html?rowid=" + rowid, 400, 350,
			dailogcallback, null);
}
/*
 * 选择是否显示当前组织 @param {Object} obj
 */
function checkViewOrg(obj) {
	var parent = document.getElementById("main_org_trr").rowid;
	var filter = "SPARENT = '" + parent + "'";
	if (obj.checked) {
		filter = "SID = '" + parent + "' or SPARENT = '" + parent + "'";
	}
	currentgrid.setStaticFilter(filter);
	currentgrid.refreshData();
}

// 移动组织机构
var treeNoderowid, torowid;
function beforeDragOpm(treeId, treeNodes) {
	for (var i = 0, l = treeNodes.length; i < l; i++) {
		treeNoderowid = treeNodes[i].id;
	}
	return true;
}
// 排序
function sortOrgAction() {
	var rowid = currenttreeID;
	if (!rowid || rowid == "") {
		alert("未选中数据!");
		return;
	}
	tlv8.portal.dailog.openDailog('机构排序',
			"/SA/OPM/organization/sortOrgs.html?rowid=" + rowid + "&name="
					+ currenttreeName, 600, 350, creat_dailogcallback, null);
}
// 拖动分隔线事件
function standardPartitionResize(event) {
	$("#main-grid-view_grid_label").fixTable({
		fixColumn : 0,// 固定列数
		fixColumnBack : "#ccc",// 固定列数
		width : $("#main-grid-view_body_layout").width(),// 显示宽度
		height : $("#main-grid-view_body_layout").height()
	// 显示高度
	});
}