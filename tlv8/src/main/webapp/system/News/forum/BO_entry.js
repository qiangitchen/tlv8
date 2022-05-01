var currentgrid="";
var data=new tlv8.Data();
data.setDbkey("system");
data.setTable("bo_entry");
var $maingridview;
var gridrowid;var name;
$(document).ready(function(){
	gridrowid=tlv8.RequestURLParam.getParam("gridrowid");
	name=tlv8.RequestURLParam.getParam("name");//讨论标题
	$maingridview=$("#gridmainView");
	var labelid = "No,TITLE,CONTENT,CATEGORY_NAME,CREATED_TIME,ONESELF,STATUS";
	var labels = "No.,标题,内容,分类,发表时间,发贴人,是否锁定";
	var labelwidth = "40,250,320,200,150,120,50";
	var datatype = "ro,ro,ro,ro,ro,ro,ro";//设置字段类型
	var dataAction = {
		"queryAction" : "getPersonInfo",
		"savAction" : "savePersonInfo",
		"deleteAction" : "deletePersonInfo"
	};
	//创建grid
	var gridmain=new tlv8.createGrid($maingridview[0], labelid, labels, labelwidth,
		dataAction, "100%", "100%", data, 20, "", "", "", datatype, "false","true");
	gridmain.grid.settoolbar(false, false, true, false);
	$("#"+$maingridview[0].id+"_toolbar").css("display","none");//禁用工具条
	
	//设置是否可编辑
	gridmain.grid.seteditModel(false);
	currentgrid = gridmain.grid;
	var filter="BO_BLOGID like '%"+trim(gridrowid)+"%' ";
	gridmain.grid.refreshData(filter);
	//currentgrid.refreshData();
	 $("#looks").bind("keydown", function(event){
       if (event.keyCode == 13) 
		lookselect();

    });
});

//删除操作
function deleteEntry(){
	var rowid=currentgrid.getCurrentRowId();
	var sql="DELETE FROM bo_entry WHERE SID='"+rowid+"'";
	var pam = new tlv8.RequestParam();
	pam.set("sql",sql);
	tlv8.XMLHttpRequest("updateCeaseOrganAction", pam, "post", true, function(data){
		var r=eval(data.data);
		if (r.flag == "false") {
			alert(r.message);
		} else {
			sAlert("操作成功!", 500);
		}
	});
	currentgrid.refreshData();
}
//锁帖
function lockEntry(){
	var rowid=currentgrid.getCurrentRowId();
	var sql="UPDATE BO_ENTRY SET STATUS=1 WHERE SID='"+rowid+"'";
	var pam = new tlv8.RequestParam();
		pam.set("sql",sql);
		tlv8.XMLHttpRequest("updateCeaseOrganAction", pam, "post", true, function(data){
			var r=eval(data.data);
			if (r.flag == "false") {
				alert(r.message);
			} else {
				sAlert("操作成功!", 500);
				 $("#OPENSCOPE").removeAttr("onclick");
				 currentgrid.refreshData();
			}
		});
}
//解锁
function unbindEntry(){
	var rowid=currentgrid.getCurrentRowId();
	var sql="UPDATE BO_ENTRY SET STATUS=0 WHERE SID='"+rowid+"'";
	var pam = new tlv8.RequestParam();
		pam.set("sql",sql);
		tlv8.XMLHttpRequest("updateCeaseOrganAction", pam, "post", true, function(data){
			var r=eval(data.data);
			if (r.flag == "false") {
				alert(r.message);
			} else {
				sAlert("操作成功!", 500);
				currentgrid.refreshData();
			}
		});
}
//查询操作
function lookselect()
{
	var text=$("#looks").val();
	var filter="BO_BLOGID like '%"+trim(gridrowid)+"%' and TITLE like '%"+text+"%'";
	currentgrid.refreshData(filter);
}
//查看操作
function lookEntry(){
	var rowid=currentgrid.getCurrentRowId();
	var name=currentgrid.getValueByName("ONESELF", rowid);
	var time=currentgrid.getValueByName("CREATED_TIME", rowid);
	tlv8.portal.openWindow('帖子详情','/system/News/forum/lookentry.html?rowid=' + rowid+"&name="+name+"&time="+time);
}
//修改内容
function editOrgData(event){
	tlv8.portal.openWindow("修改","/system/News/forum/BO_entryPublish.html?gridrowid="+ event.CurrentRowId,null); 
}
//快速回复
function velocity(){
	var rowid=currentgrid.getCurrentRowId();
	tlv8.portal.openWindow("快速回复","/system/News/forum/velocityentry.html?rowid="+rowid+"&title="+name,null);  
}
function openview(){
	tlv8.portal.openWindow("发帖","/system/News/forum/BO_entryPublish.html?rowid="+ gridrowid+ "&tabID="+tlv8.portal.currentTabId(),null);
}
function callBackFn(){
	$maingridview[0].grid.refreshData();
}
