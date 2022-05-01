var agentItem, recyleItem, angentgrid;

var setting = {
	view : {
		selectedMulti : false,
		autoCancelSelected : false,
		dblClickExpand : true
	},
	data : {
		simpleData : {
			enable : true,
			idKey : "id",
			pIdKey : "parent",
			rootPId : null
		}
	},
	callback : {
		onClick : treeSelect
	}
};

var sql = "select SID,SPARENT,SORGKINDID,SCODE,SNAME,SFID,SFCODE,SFNAME,SPERSONID from SA_OPORG where '"
		+ tlv8.Context.getCurrentPersonFID() + "' like concat(SFID,'%')";
$(document).ready(function() {
	createAgentGrid();
	var zNodes = [ {
		id : "root",
		name : "根目录",
		parent : ""
	}, {
		id : "children1",
		name : "子目录1",
		parent : "root"
	} ];
	var r = tlv8.sqlQueryActionforJson("system", sql);
	var zNodes = r.data;
	for ( var i in zNodes) {
		zNodes[i].id = zNodes[i].SPERSONID || zNodes[i].SID;
		zNodes[i].name = zNodes[i].SNAME;
		zNodes[i].parent = zNodes[i].SPARENT;
		var kind = zNodes[i].SORGKINDID;
		if (("ogn" == (kind)) || ("org" == (kind)))
			zNodes[i].icon = "toolbar/org/org.gif";
		if ("dpt" == (kind))
			zNodes[i].icon = "toolbar/org/dept.gif";
		if ("pos" == (kind))
			zNodes[i].icon = "toolbar/org/pos.gif";
		if ("psm" == (kind)) {
			zNodes[i].icon = "toolbar/org/person.gif";
		}
	}
	var zTreeTools = $.fn.zTree.init($("#JtreeOrgPsm"), setting, zNodes);
	zTreeTools.expandAll(true);
	var node = zTreeTools.getNodeByTId(tlv8.Context.getCurrentPersonID());
	zTreeTools.selectNode(node, true);
});

/*
 * 创建grid
 */
function createAgentGrid() {
	var data = new tlv8.Data();
	data.setDbkey("system");
	data.setTable("SA_OPAgent");
	var det = document.getElementById("agentGridView");
	var labelid = "No,sValidState,sAgentID,sOrgFName,SPROCESS,sStartTime,sFinishTime,sCanTranAgent,sCreatorName,sCreateTime,SORGFID,SCREATORID";
	var labels = "No.,状态,代理人,被代理的岗位,代理权限,开始时间,结束时间,允许转交,创建人,创建时间,SORGFID,SCREATORID";
	var datatype = "ro,html:StateRender,html:readagentor,ro,ro,date,date,html:readtranseOther,ro,ro,ro,ro";
	var labelwidth = "40,40,80,200,300,100,100,55,60,110,0,0";
	var dataAction = {
		"queryAction" : "getGridAction",
		"savAction" : "saveAction",
		"deleteAction" : "deleteAction"
	};
	var maingrid = new tlv8.createGrid(det, labelid, labels, labelwidth,
			dataAction, "100%", "100%", data, 20, "SVALIDSTATE='1' and ",
			"treeSelectNodeForm", "SORGFID", datatype, "false", "true");
	maingrid.grid.settoolbar(false, true, true, false);
	maingrid.grid.seteditModel(true);
	agentItem = "<a href='javascript:void(0)' class='toobar_item' id='agentItem' "
			+ " style='width:80px;float:left' onclick='agentPsm()'>"
			+ "<p><img src='images/newAgent.gif' style='float:left'/>代理给...</p></a>";
	recyleItem = "<a href='javascript:void(0)' class='toobar_item' id='recyleItem' "
			+ " style='width:100px;float:left' onclick='recyleagentPsm()'>"
			+ "<p><img src='images/fetchBackAll.gif' style='float:left'/>收回代理...</p></a>";
	document.getElementById('agentGridView-grid-item').innerHTML = agentItem
			+ recyleItem;
	angentgrid = maingrid.grid;
}

function StateRender(event) {
	if (event.value == 1)
		return "有效";
	else
		return "取消";
}

function readagentor(event) {
	var sql = "select SNAME from SA_OPORG where SID = '" + event.value + "'";
	var r = tlv8.sqlQueryAction("system", sql);
	if (r.getCount() > 0) {
		return r.getValueByName("SNAME");
	} else {
		return "";
	}
}

function readtranseOther(event) {
	var ista = event.value;
	var cxchecked = "";
	if (ista == 1) {
		cxchecked = "checked='true'";
	}
	return "<div style='width:100%;text-align:center;'><input type='checkbox' "
			+ cxchecked + " onclick='changeTranse(this,\"" + event.rowid
			+ "\")'/></div>";
}

function changeTranse(obj, rowid) {
	var va = obj.checked ? 1 : 0;
	angentgrid.setValueByName("sCanTranAgent", rowid, va);
}

/*
 * 树选择 @param {Object} event @param {Object} treeId @param {Object} node @param
 * {Object} clickFlag
 */
function treeSelect(event, treeId, node, clickFlag) {
	J$("treeSelectNodeForm").rowid = node.SFID;
	J$("agentGridView").grid.refreshData();
	if (node.SORGKINDID != "psm") {
		J$("agentItem").disabled = true;
		J$("recyleItem").disabled = true;
	} else {
		J$("agentItem").disabled = false;
	}
}

function selectedItem() {
	J$("recyleItem").disabled = false;
}

/*
 * 代理
 */
function agentPsm() {
	tlv8.portal.dailog.openDailog("选择代理人",
			"/comon/SelectDialogPsn/singleSelectPsn.html", 500, 400,
			function(r) {
				var cGrid = angentgrid;
				var newID = cGrid.insertData();
				cGrid.setValueByName("sValidState", newID, "1");
				cGrid.setValueByName("sAgentID", newID, r.rowid);
				cGrid.setValueByName("sOrgFName", newID, tlv8.Context
						.getCurrentPersonFName());
				cGrid.setValueByName("SORGFID", newID, tlv8.Context
						.getCurrentPersonFID());
				cGrid.setValueByName("SCREATORID", newID, tlv8.Context
						.getCurrentPersonID());
				cGrid.setValueByName("sCreatorName", newID, tlv8.Context
						.getCurrentPersonName());
				cGrid.setValueByName("sCreateTime", newID,
						tlv8.System.Date.sysDateTime());
			});
}

/*
 * 收回代理
 */
function recyleagentPsm() {
	var rowID = angentgrid.getCurrentRowId();
	if (rowID && rowID != "") {
		angentgrid.setValueByName("sValidState", rowID, "0");
		angentgrid.saveData();
	}
}

// 拖动分隔线事件
function standardPartitionResize(event) {
	$("#agentGridView_grid_label").fixTable({
		fixColumn : 0,// 固定列数
		fixColumnBack : "#ccc",// 固定列数
		width : $("#agentGridView_body_layout").width(),// 显示宽度
		height : $("#agentGridView_body_layout").height()
	// 显示高度
	});
}