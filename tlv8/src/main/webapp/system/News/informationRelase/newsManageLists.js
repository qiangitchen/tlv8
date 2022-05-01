var currentgrid = "";
var data = new tlv8.Data();
data.setDbkey("system");
data.setTable("news_tables");
var $maingridview;
$(document)
		.ready(
				function() {
					$maingridview = $("#gridmainView");
					var labelid = "No,FNEWSTITLE,FSTATE,FPEOPLE,FTIME,FOPENSCOPE,NEWS_NUMBER,NEWS_PERSON,YETPERSON";
					var labels = "No.,新闻标题,状态,发布人,发布时间,发布范围,浏览次数,最后浏览者,已阅者";
					var labelwidth = "40,200,100,150,150,150,80,120,120";
					var datatype = "ro,html:reader,ro,ro,ro,ro,ro,ro,ro";//设置字段类型
					var dataAction = {
						"queryAction" : "getPersonInfo",
						"savAction" : "savePersonInfo",
						"deleteAction" : "deletePersonInfo"
					};
					var psnfid = tlv8.Context.getCurrentPersonFID();
					var filter = "FSTATE='已发布' and EXISTS(select 1 from SA_OPORG_VIEW o where FOPENSCOPEID like '%'||o.SID||'%' and '"
							+ psnfid + "' like o.SFID||'%')";
					var maingrid = new tlv8.createGrid($maingridview[0],
							labelid, labels, labelwidth, dataAction, "100%",
							"100%", data, 20, filter, "", "", datatype,
							"false", "true");
					maingrid.grid.settoolbar(false, false, true, false);
					maingrid.grid.seteditModel(false);
					currentgrid = maingrid.grid;
					maingrid.grid.refreshData();
					$("#looks").bind("keydown", function(event) {
						if (event.keyCode == 13)
							lookselect();

					});
				});
//浏览历史
function velocity() {
	var rowid = currentgrid.getCurrentRowId();
	var title = currentgrid.getValueByName("FNEWSTITLE", rowid);
	var name = currentgrid.getValueByName("FPEOPLE", rowid);
	var scope = currentgrid.getValueByName("FOPENSCOPE", rowid);
	var lookperson = currentgrid.getValueByName("YETPERSON", rowid);
	var number = currentgrid.getValueByName("NEWS_NUMBER", rowid);
	tlv8.portal.dailog.openDailog('历史记录',
			'/system/News/informationRelase/history.html?rowid=' + rowid
					+ "&title=" + title + "&name=" + name + "&scope=" + scope
					+ "&lookperson=" + lookperson + "&number=" + number, 550,
			420, null, null);
}
function reader(event) {
	var html = "<a href='javascript:void(0);' onclick='news_griddbclick(this)'>"
			+ event.value + "</a>";
	return html;
}
function news_griddbclick(obj) {
	setTimeout('QueryDetailAction()', 5);
}
function QueryDetailAction() {
	var rowid = currentgrid.CurrentRowId;
	tlv8.portal.openWindow('新闻浏览',
			'/system/News/informationRelase/news.html?rowid=' + rowid);
}
//根据标题查询
function lookselect() {
	var text = $("#looks").val();
	var filter = "FNEWSTITLE like '%" + trim(text) + "%' ";
	currentgrid.refreshData(filter);
}
