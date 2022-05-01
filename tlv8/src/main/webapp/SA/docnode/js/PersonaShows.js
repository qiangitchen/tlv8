var random=Math.random();
var refresh=random+"="+random;
$(function(){
    
	var sqlStr="select SID,SFILENAME,SCREATORNAME,SFILESIZE FROM PERSONAL_FILE where "+refresh+" and SCREATORID LIKE '%"+tlv8.Context.getCurrentPersonID()+"%' limit 0,5";
//	var news=tlv8.sqlQueryAction("system",sqlStr);
//	var news_budget=tlv8.strToXML(news.data);
	var news = tlv8.sqlQueryActionforJson("system", sqlStr);
	var html="";
	for(var n=0;n<news.data.length;n++){
//		var cells=news_budget.getElementsByTagName('root/rows/row')[n].text.split(";");
//		html+="<div id='"+cells[0]+"' class='mydiv'>" +
//		"<img src='image/symbol.png'><a href='javascript:void(0);' onClick= tlv8.portal.openWindow('个人文件',cpath+'/system/docnode/PersonaList.html?portalrowid="+cells[0]+"')>" +
//		" "+cells[1]+"&nbsp;&nbsp;"+cells[2]+"&nbsp;&nbsp;大小："+cells[3]+" KB </a></div>";
		
		cells = news.data;
		html+="<div id='"+cells[n].SID+"' class='mydiv'>" +
		"<img src='image/symbol.png'><a href='javascript:void(0);' onClick= tlv8.portal.openWindow('个人文件',cpath+'/system/docnode/PersonaList.html?portalrowid="+cells[n].SID+"')>" +
		" "+cells[n].SFILENAME+"&nbsp;&nbsp;"+cells[n].SCREATORNAME+"&nbsp;&nbsp;大小："+cells[n].SFILESIZE+" KB </a></div>";
	}
	$("#addnewsload").html(html);
} )
function onhistory(){
	tlv8.portal.openWindow('文件列表',cpath+'/system/docnode/PersonaList.html');
}