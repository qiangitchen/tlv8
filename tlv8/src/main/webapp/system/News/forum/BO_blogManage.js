var currentgrid="";
var data=new tlv8.Data();
data.setDbkey("system");
data.setTable("bo_blog");
var $maingridview;
$(document).ready(function(){
	$maingridview=$("#maingridview");
	//登录人员ID
	var personId=tlv8.Context.getCurrentPersonID();
	var labelid="No,NAME,DESCRIPTION,COMMENT_AUDIT,CHAIRMAN,BLOGCATEGORY,RECENT_ENTRY_NUMBER";
	var labels="No.,名称,简介描述,是否审核,版主,分类,主题数目";
	var labelwidth="40,150,180,120,120,150,100";
	var datatype = "ro,html:reader,ro,ro,ro,ro,ro";//设置字段类型
	var dataAction = {
		"queryAction" : "getGridAction",
		"savAction" : "saveAction",
		"deleteAction" : "deleteAction"
	};
	//创建grid
	var gridmain=new tlv8.createGrid($maingridview[0], labelid, labels, labelwidth,
		dataAction, "100%", "100%", data, 20, "", "", "", datatype, "false",
	"true");
	//设置toobar显示{新增、保存、刷新、删除}
	//gridmain.grid.settoolbar(false, false, true, false);
	//设置是否可编辑
	$("#"+$maingridview[0].id+"_toolbar").css("display","none");//禁用工具条
	currentgrid = gridmain.grid;
	currentgrid.setExcelimpBar(false);//导入
	currentgrid.setExcelexpBar(false);//导出
	var filter="CHAIRMAN_ID like '%"+trim(personId)+"%' ";
	//gridmain.grid.refreshData(filter);
	currentgrid.refreshData(filter);
	$("#looks").bind("keydown", function(event){
       if (event.keyCode == 13) 
		lookselect();

    });
	$("#" +$maingridview[0].id+ "_insertItem").click(function(){
		tlv8.portal.openWindow("新增讨论区",
			"/system/News/forum/BO_blogadd.html?tabID="+tlv8.portal.currentTabId(),null);
	});

});
function reader(event){
	 var html="<a href='javascript:void(0);' onclick='griddbclick(this)'>"+event.value+"</a>";
		 return html;
}
function griddbclick(obj){
setTimeout('QueryDetailAction()', 5);	
}
function QueryDetailAction(){
var rowid  = currentgrid.CurrentRowId;
var name=currentgrid.getValueByName("NAME", rowid);
tlv8.portal.openWindow("管理","/system/News/forum/BO_entry.html?gridrowid="+ rowid+"&name="+name,null);
}
function callBackFn(){
	$maingridview[0].grid.refreshData();
}
function editOrgData(event) {
	var name=currentgrid.getValueByName("NAME", event.CurrentRowId);
	tlv8.portal.openWindow("管理","/system/News/forum/BO_entry.html?gridrowid="+ event.CurrentRowId+"&name="+name,null);

}
//根据标题查询
function lookselect(){
	var text=$("#looks").val();
	var personId=tlv8.Context.getCurrentPersonID();
	var filter="NAME like '%"+trim(text)+"%' and CHAIRMAN_ID like '%"+personId+"%' ";
	currentgrid.refreshData(filter);
}