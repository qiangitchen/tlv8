/*创建树*/
var param = {
	cell : {
		id : "SID",//设置构建树的id
		name : "SNAME",//树显示的名称
		parent : "SPARENT",//表示树的层级
		other : "SFID,SFNAME,SORGKINDID,SCODE,SFCODE",
		tableName : "SA_OPORG",//对应的表名
		databaseName : "system",//数据库
		rootFilter : "EXISTS(select SMANAGEORGFID from SA_OPMANAGEMENT m where SORGFID = '"
				+ tlv8.Context.getCurrentPersonFID()
				+ "' and m.SMANAGEORGID like SA_OPORG.SID||'%' "
				+ " and SMANAGETYPEID='systemManagement')", //跟节点条件
		orderby : "SSEQUENCE asc" //排序字段
	}
};

//设置树的属性
var setting = {
	view : {
		selectedMulti : false, //设置是否允许同时选中多个节点。默认值: true
		autoCancelSelected : false,
		dblClickExpand : true
	},
	data : {
		simpleData : {
			enable : true
		}
	},
	async : {
		enable : true,
		url : "TreeSelectAction",
		autoParam : [ "id=currenid" ],
		type : "post"
	},
	isquickPosition : {
		enable : true, //是否有快速查询框
		url : "QuickTreeAction",
		quickCells : "SCODE,SNAME",//用于快速查询的字段
		path : "SFID"//查询路径字段
	},
	check : {
		enable : true,
		chkboxType : {
			"Y" : "",
			"N" : "s"
		}
	},
	callback : {}
};

function dailogEngin() {
	var JtreeCheckObj = $.fn.zTree.getZTreeObj("JtreeDemo");
	var rData = JtreeCheckObj.getCheckedNodes();
	if (rData && rData.length > 0) {
		return rData;
	} else {
		alert("没有选择!");
		return false;
	}
}