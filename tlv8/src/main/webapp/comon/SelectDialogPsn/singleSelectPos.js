var currentgrid = null, prams;
/* ==数据源===此项为必须定义== */
var data = new tlv8.Data();
data.setDbkey("system");
data.setTable("sa_oporg");

/**
 * 
 * 组织机构树构建
 * 
 */
var param = {
	cell : {
		id : "SID",// 设置构建树的id
		name : "SNAME",// 树显示的名称
		parent : "SPARENT",// 表示树的层级
		other : "SFID,SFNAME,SORGKINDID,SFAX",
		tableName : "SA_OPOrg",// 对应的表名
		databaseName : "system",// 数据库
		rootFilter : " SORGKINDID<>'psm' and SPARENT is null", // 跟节点条件
		orderby : "SSEQUENCE asc" // 排序字段
	}
};
var setting = {
	data : {
		simpleData : {
			enable : true

		}
	},
	async : {
		enable : true,// 异步
		url : "TreeSelectAction",// 异步交互的action getTreeSourAction
									// GetTreeOrgSelectAction
		autoParam : [ "id=currenid" ],// 异步需要的参数
		type : "post"

	},
	isquickPosition : {
		enable : true, // 是否有快速查询框
		url : "QuickTreeAction",
		quickCells : "SCODE,SNAME",// 用于快速查询的字段
		path : "SFID"// 查询路径字段
	},
	callback : {
		beforeClick : beforeClick
	}
};

/* ==== */
var JtreeOrg = new Jtree();
function Init() {
	JtreeOrg.init("treeDemo", setting, param);
	var d = document.getElementById('OrgSelect_Grid');
	var labelid = "No,SNAME,SCODE,SFCODE,SFNAME";
	var labels = "序号,名称,编号,全编号,全名称";
	var labelwidth = "40,100,120,120,120";
	var datatype = "ro,ro,ro,ro,ro";// 设置字段类型
	var dataAction = {
		"queryAction" : "getOrgGridInfo",
		"savAction" : "saveOrgGridInfo",
		"deleteAction" : "deleteOrgGridInfo"
	};
	var maingrid = new tlv8.createGrid(d, labelid, labels, labelwidth,
			dataAction, "100%", "100%", data, 10, "SORGKINDID!='psm'", "", "",
			datatype, false, true);
	// 设置按钮显示{新增、保存、刷新、删除}
	maingrid.grid.settoolbar(false, false, true, false);
	// 设置是否可编辑
	maingrid.grid.seteditModel(false);
	currentgrid = maingrid.grid;
}

// Grid点击事件
function selectedItem(ev) {
	prams = {};
	var rowid = ev.RowId;
	var maingrid = document.getElementById("OrgSelect_Grid");
	var name = maingrid.grid.getValueByName("SNAME", rowid);
	var code = maingrid.grid.getValueByName("SCODE", rowid);
	if ((name != null && name != 'null') && (code != null && code != 'null')) {
		prams = {
			"code" : code,
			"name" : name
		};
	}
}

// 树点击事件
function beforeClick(treeId, treeNode) {
	var FullId = treeNode.SFID;
	currentgrid.refreshData("SFID like '" + FullId + "%'");
}

// 对话框确定返回值
function dailogEngin() {
	if (prams.id && prams.name) {
		return prams;
	} else {
		alert('请选择数据！！');
	}
}