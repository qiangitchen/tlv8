var TITLE;
var ONESELF;
var CONTENT;
var CREATED_TIME;
var rowid;
$(document).ready(function(){
    rowid = tlv8.RequestURLParam.getParam("rowid");
     ONESELF=tlv8.RequestURLParam.getParam("name");
     CREATED_TIME=tlv8.RequestURLParam.getParam("time");
    var sqlstr="SELECT CONTENT FROM bo_entry WHERE SID='"+rowid+"'";
	var result=tlv8.sqlQueryAction("system",sqlstr);
	var result_budget=tlv8.strToXML(result.data);
	var cells=result_budget.getElementsByTagName('root/rows/row')[0].text;
	cells=cells.replaceAll("#lt;","<").replaceAll("#gt;",">").replaceAll("#160;","&nbsp;");
 	var sql="select SID,TITLE,CONTENT,ONESELF,CREATED_TIME from bo_entry where SID='"+rowid+"'";
	var news=tlv8.sqlQueryAction("system",sql);
	TITLE=news.getValueByName("TITLE");
	$("#TITLE").html(TITLE);//标题
	$("#ONESELF").html(ONESELF);//发布人
	$("#CREATED_TIME").html(CREATED_TIME);//时间
	$("#CONTENT").html(cells);//内容
});
//回复
function reply(){
	tlv8.portal.dailog.openDailog("回复","/system/News/forum/dialog/CommentDialog.html?title="+TITLE+"&oneself="+ONESELF+"&rowid="+rowid,600,420, Callback, null);
}
function Callback(event){
	$("#Comment_author").html("【姓名】"+event.author+"&nbsp;");
	$("#Comment_created_time").html(event.created_time.substring(0,19));
	$("#Comment_CONTENT").html(event.content);
	
}