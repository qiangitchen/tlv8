var mark="showDay";//记录显示视图类型：日、周、月
var affairsInquire = {
	dayInquire : function() {
		var myDate = new Date();
		var specifiedMonths = myDate.getMonth() + 1;//日期控件月份
		if (specifiedMonths < 10) {
			specifiedMonths = "0" + specifiedMonths.toString();
		}
		var specifiedDates = myDate.getDate();//日期控件日
		if (specifiedDates < 10) {
			specifiedDates = "0" + specifiedDates.toString();
		}
		document.getElementById("specified").value = myDate.getFullYear() + "-"
			+ specifiedMonths + "-" + specifiedDates;//日期控件载入日期为当天
		
		var jq = jQuery.noConflict();
		var setDate = "now";
		mark = "showDay";
		jq("#iframeInquire").attr("src","/tlv8/system/office/inquire_schedule/day_inquire.html?setDate="+ setDate);
	},
	weekInquire : function() {
		var jq = jQuery.noConflict();
		var setDate = "now";
		mark = "showWeek";
		jq("#iframeInquire").attr("src","/tlv8/system/office/inquire_schedule/week_inquire.html?setDate="+ setDate);
		//		var iframeLink = document.getElementById("iframeInquire");iframeLink.src = "/tlv8/system/office/inquire_schedule/week_inquire.html";
	},
	monthInquire : function() {
		var jq = jQuery.noConflict();
		var setDate = "now";
		mark = "showMonth";
		jq("#iframeInquire").attr("src","/tlv8/system/office/inquire_schedule/month_inquire.html?setDate="+ setDate);
	}
}
	/*指定日期-确定*/
var specifiedDate = function(v){
	var setDate;
	if (v=="now"){
		setDate = "now"
		var myDate = new Date();
		var specifiedMonths = myDate.getMonth() + 1;//日期控件月份
		if (specifiedMonths < 10) {
			specifiedMonths = "0" + specifiedMonths.toString();
		}
		var specifiedDates = myDate.getDate();//日期控件日
		if (specifiedDates < 10) {
			specifiedDates = "0" + specifiedDates.toString();
		}
		document.getElementById("specified").value = myDate.getFullYear() + "-"	+ specifiedMonths + "-" + specifiedDates;//日期控件载入日期为当天
	}
	if (v=="ok"){setDate = document.getElementById("specified").value;}
	
	var jq = jQuery.noConflict();
	if(mark=="showDay"){
		jq("#iframeInquire").attr("src","/tlv8/system/office/inquire_schedule/day_inquire.html?setDate="+ setDate);
	}else if(mark=="showWeek"){
		jq("#iframeInquire").attr("src","/tlv8/system/office/inquire_schedule/week_inquire.html?setDate="+ setDate);
	}else if(mark=="showMonth"){
		jq("#iframeInquire").attr("src","/tlv8/system/office/inquire_schedule/month_inquire.html?setDate="+ setDate);
	}
}
function weekInquire() {
//	var dbkey = "system";
//	var sql = "SELECT sa_psnschedule.SWHOUSER id,sa_opperson.SNAME name FROM "+
//		"sa_psnschedule ,sa_opperson "+
//		"WHERE sa_psnschedule.SWHOUSER = sa_opperson.SID GROUP BY sa_psnschedule.SWHOUSER ORDER BY sa_psnschedule.SWHOUSER";
//	var depPenson = tlv8.sqlQueryActionforJson(dbkey,sql);
//	alert(depPenson.data[0].name);
	
	var dbkey = "system";
	var sql = "SELECT sa_psnschedule.SID id,"+
		"sa_psnschedule.SCAPTION caption,"+
		"sa_psnschedule.SSTARTDATE statrtdate,"+
		"sa_psnschedule.SENDDATE enddate,"+
		"sa_psnschedule.SCONTENT content,"+
		"sa_psnschedule.SPRIORITY priority,"+
		"sa_psnschedule.SSTATUS status,"+
		"sa_psnschedule.SSTARTDATE_AXIS,"+
		"sa_psnschedule.SSENDDATE_AXIS,"+
		"sa_psnschedule.SWHOUSER,"+
		"sa_opperson.SNAME FROM "+
		"sa_psnschedule ,sa_opperson "+
		"WHERE sa_psnschedule.SWHOUSER = sa_opperson.SID AND sa_psnschedule.SSTARTDATE_AXIS=sa_psnschedule.SSENDDATE_AXIS "+
		"ORDER BY sa_psnschedule.SSTARTDATE ASC";
	var mydata = tlv8.sqlQueryActionforJson(dbkey,sql);
//		alert(mydata.data[0].id);//.getDate()
		
//	var myDate = new Date("2011/7/7 20:50:59");
//	var newdate = new Date(myDate.getFullYear(),(myDate.getMonth()),myDate.getDate(),20,50,55);
}
