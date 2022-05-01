//新闻页面
var newid;
function shows() {
	var rowid = tlv8.RequestURLParam.getParam("rowid");
	var diauth = tlv8.RequestURLParam.getParam("diauth");
	if (!diauth) {
		var id = tlv8.Context.getCurrentPersonID();
	}
	try {
		var hsql = "select FOPENSCOPEID from cyea_news_release where sID='"
				+ rowid + "'";
		var scope = tlv8.sqlQueryActionforJson("system", hsql);
		var fopenscopeid = scope.data;
		var array = fopenscopeid[0].FOPENSCOPEID.split(",");
		// 判断数组
		for ( var i in array) {
			if (array[i] == id) {
				newid = array[i];
			}
		}
		var sqlstr = "select FRELEASECONNEXT FCONTENT from cyea_news_release where SID='"
				+ rowid + "'";// TMJ_NEWS_RELEASE
		var result = tlv8.sqlQueryActionforJson("system", sqlstr);
		var cells = result.data[0].FCONTENT.replaceAll("#lt;", "<").replaceAll(
				"#gt;", ">").replaceAll("#160;", "&nbsp;");
		var sql = "select SID FID,FNEWSTITLE FTITLE,FPEOPLE,FTIME FPEOPLEDATE,FRELEASECONNEXT FCONTENT from cyea_news_release where SID='"
				+ rowid + "'";// TMJ_NEWS_RELEASE
		var news = tlv8.sqlQueryAction("system", sql);
		document.getElementById("FRELEASECONNEXT").innerHTML = cells;
		document.getElementById("FNEWSTITLE").innerHTML = news
				.getValueByName("FTITLE");
		document.getElementById("FPEOPLE").innerHTML = news
				.getValueByName("FPEOPLE");
		document.getElementById("FTIME").innerHTML = news.getValueByName(
				"FPEOPLEDATE").substring(0, 19);
		$("#NEWS_PERSON111").html(tlv8.Context.getCurrentPersonName());// 当前浏览者

		var datamian = new tlv8.Data();
		datamian.setTable("cyea_news_release");
		datamian.setDbkey("system");
		datamian.setFormId("news_form");
		document.getElementById("news_form").rowid = rowid;
		datamian.setFilter("SID='" + rowid + "'");
		var cellname = "FACCESSORIES";// 设置附件相关字段
		var fileComp = new tlv8.fileComponent(document
				.getElementById("fileCompDiv"), datamian, cellname, null,
				false, false);

		var Tsql = "select NEWS_NUMBER,NEWS_PERSON,NEWS_PERSONID,YETPERSON,YETPERSONID,NEWS_RELEASEID from  CYEA_NEWS_COUNT where NEWS_RELEASEID='"
				+ rowid + "'";
		var news_count = tlv8.sqlQueryAction("system", Tsql);
		var numberone = news_count.getCount();
		if (numberone == 0) {
			$("#NEWS_PERSON").val(tlv8.Context.getCurrentPersonName());
			$("#NEWS_PERSONID").val(tlv8.Context.getCurrentPersonID());
			$("#NEWS_RELEASEID").val(rowid);
			$("#NEWS_NUMBER").val(1);
			$("#YETPERSON").val(tlv8.Context.getCurrentPersonName() + ",");
			$("#YETPERSONID").val(tlv8.Context.getCurrentPersonID() + ",");
			var Datamain = new tlv8.Data();
			Datamain.setDbkey("system");
			Datamain.setTable("CYEA_NEWS_COUNT");
			Datamain.setFormId("cyea_news_count");
			Datamain.saveData();
		} else {
			var numbercount = parseInt(news_count.getValueByName("NEWS_NUMBER")) + 1;
			var oldyetperson = news_count.getValueByName("YETPERSON")
					+ tlv8.Context.getCurrentPersonName() + ",";
			var array1 = oldyetperson.split(",");
			// 判断数组，给数组去重
			var newarray = new Array();
			for (var i = 0; i < array1.length; i++) {
				for (var j = i + 1; j < array1.length; j++) {
					if (array1[i] == array1[j]) {
						j = ++i;
					}
				}
				newarray.push(array1[i]);
			}
			var oldyetpersonid = news_count.getValueByName("YETPERSONID")
					+ tlv8.Context.getCurrentPersonID() + ",";
			var array2 = oldyetpersonid.split(",");
			// 判断数组，给数组去重
			var newarray1 = new Array();
			for (var i = 0; i < array2.length; i++) {
				for (var j = i + 1; j < array2.length; j++) {
					if (array2[i] == array2[j]) {
						j = ++i;
					}
				}
				newarray1.push(array2[i]);
			}
			var Tupsql = "update CYEA_NEWS_COUNT set NEWS_NUMBER="
					+ numbercount + ",NEWS_PERSON='"
					+ tlv8.Context.getCurrentPersonName() + "',"
					+ "NEWS_PERSONID='"
					+ tlv8.Context.getCurrentPersonID() + "',YETPERSON='"
					+ newarray + "',YETPERSONID='" + newarray1
					+ "' WHERE NEWS_RELEASEID='" + rowid + "'";
			tlv8.sqlUpdateAction("system", Tupsql);
		}
		$("#NEWS_NUMBER111").html(
				news_count.getValueByName("NEWS_NUMBER") + "次");

		writeReadStateInfo();
	} catch (e) {
	}
}

/*
 * 已阅人员列表
 */
function readorList() {
	var rowid = tlv8.RequestURLParam.getParam("rowid");
	tlv8.portal.dailog.openDailog("已阅人员信息",
			"/system/News/informationRelase/notes-reader-list.html?rowid="
					+ rowid, 500, 400, null, {
				refreshItem : false,
				enginItem : false,
				CanclItem : true
			});
}

/*
 * 回写已阅
 */
function writeReadStateInfo() {
	var rowid = tlv8.RequestURLParam.getParam("rowid");
	var currentPsnId = tlv8.Context.getCurrentPersonID();
	var currentPsnName = tlv8.Context.getCurrentPersonName();
	if (rowid && rowid != "") {
		var sql = "select FID from OA_IS_READER where FMASTERID = '" + rowid
				+ "' and FPERSONID = '" + currentPsnId + "'";
		var r = tlv8.sqlQueryAction("infolink", sql);
		if (r.getCount() == 0) {
			sql = "insert into OA_IS_READER(FID,FMASTERID,FPERSONID,FPERSONNAME,FCREATETIME,VERSION)"
					+ "values('"+new UUID().toString()+"','"
					+ rowid
					+ "','"
					+ currentPsnId
					+ "','" + currentPsnName + "','"+tlv8.System.Date.sysDateTime()+"',0)";
			var uSQL = "update oa_is_info inf "
					+ "set fReadCount = (select count(*) from OA_IS_READER r "
					+ " where inf.fID = r.FMASTERID) where fID = '" + rowid
					+ "'";
			tlv8.sqlUpdateAction("infolink", sql, function(rd) {
				tlv8.sqlUpdateAction("infolink", uSQL);
			});
		}
	}
}
