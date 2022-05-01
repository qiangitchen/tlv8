
var monthDayGap;//月一号星期几
/*页面载入*/
var monthOnload = {
	onloadrun : function() {
	var setDate = tlv8.RequestURLParam.getParam("setDate");
	setTableHeaderDate(setDate);
	getMonthData(setDate);
	setTableData();
	}
}
//展开隐藏表格
function hidetable(id) {
	var jq = jQuery.noConflict();//visibility:hidden
	jq(id).toggle(700);
}
/*\****************判断当前浏览器类型*********/
function isIE(){   
	return navigator.appName.indexOf("Microsoft Internet Explorer")!=-1 && document.all;   
}   
function isIE6() {   
	return navigator.userAgent.split(";")[1].toLowerCase().indexOf("msie 6.0")=="-1"?false:true;   
}   
function isIE7(){   
	return navigator.userAgent.split(";")[1].toLowerCase().indexOf("msie 7.0")=="-1"?false:true;   
}   
function isIE8(){   
	return navigator.userAgent.split(";")[1].toLowerCase().indexOf("msie 8.0")=="-1"?false:true;   
}   
function isNN(){   
   return navigator.userAgent.indexOf("Netscape")!=-1;   
}   
function isOpera(){   
	return navigator.appName.indexOf("Opera")!=-1;   
}   
function isFirefox(){   
	return navigator.userAgent.indexOf("Firefox")!=-1;   
}   
function isChrome(){   
	return navigator.userAgent.indexOf("Chrome") > -1;     
} 

function showResult(){		
	if(isChrome()){
		return("chrome");	
	}
	
	else if(isIE()){
		return("ie");
	}
	
	else if(isNN()){
		return("nn");
	}
	
	else if(isOpera()){
		return("opera");
	}
	
	else if(isFirefox()){
		return("firefox");
	}
}
	
var loaddate;//页面载入日期毫秒
var loadMondaydate;//页面载入星期一日期毫秒
var setTableHeaderDate = function(v){
	var browserType = showResult();//判断浏览器类型
	var myDate = new Date();
//	var tdate = new Date("2011/13/0");
//	alert(tdate.toLocaleString());
	if (v != "now") {
		var dateArray = v.split("-");
		myDate.setFullYear(dateArray[0],dateArray[1]-1,dateArray[2]);
	}
	var initialYear = myDate.getFullYear(); //获取完整的年份(4位,1970-????) 
	var initialMonth = myDate.getMonth(); //获取当前月份(0-11,0代表1月) 
	var milliseconds = myDate.getTime(); //获取当前时间(从1970.1.1开始的毫秒数) 
	loaddate = myDate.getTime();
	var maxDays;
	if(browserType=="chrome"){
		maxDays = getLastDay(initialYear,initialMonth+1);
	}
	else{
		var maxDaysDate = new Date(initialYear+"/"+(initialMonth+2)+"/"+0);/*得到月天数*/
	 	maxDays = maxDaysDate.getDate();/*得到月天数*/
	}
	var oneWeekDayDate = new Date(initialYear+"/"+(initialMonth+1)+"/"+1);
	var initialWeek = oneWeekDayDate.getDay(); //获取当月一号星期X(0-6,0代表星期天)
	if (initialWeek == 0) {
		initialWeek = 7;
	}
	monthDayGap=initialWeek;
	for(var i=0;i<maxDays;i++){
		var tdID = "a"+(initialWeek+i);
		var titleDate = new Date(initialYear+"/"+(initialMonth+1)+"/"+(i+1));
		document.getElementById(tdID).innerHTML = "<div align='right' valign='top' title='"+ titleDate.toLocaleDateString() +
		"'><a href='#' style=\"text-decoration:'none';font-family:'宋体';font-style:normal;color:'#008800';font-size:14\"><strong>" + (i+1) +"</strong></a></div>";
	}
}
/*如果是谷歌浏览器时判断当月最大天数*/
function getLastDay(year,month)        
{        
 var new_year = year;    //取当前的年份        
 var new_month = month++;//取下一个月的第一天，方便计算（最后一天不固定）        
 if(month>12)            //如果当前大于12月，则年份转到下一年        
 {        
  new_month -=12;        //月份减        
  new_year++;            //年份增        
 }        
 var new_date = new Date(new_year,new_month,1);                //取当年当月中的第一天        
 return (new Date(new_date.getTime()-1000*60*60*24)).getDate();//获取当月最后一天日期        
} 
/*========================为表格装载数据================================*/
var setTableData = function(){
//	var personID = monthData.data[0].id,personName = monthData.data[0].name;
//	var nDays=monthData.data[0].nDays;
	var nDaytxt = "";
	for(var i=0;i<monthData.data.length;i++){
		var personID = monthData.data[i].id,personName = monthData.data[i].name;
		var nDays=monthData.data[i].nDays;
		/*===============跨天============*/
		if (nDays != null && nDays != undefined){
			for(var j=0;j<nDays.length;j++){
				if(nDays[j].ID != null && nDays[j].ID != undefined){
					var sid = nDays[j].ID;
					var caption = nDays[j].CAPTION;
					var startdate = nDays[j].STARTDATE;
					var enddate = nDays[j].ENDDATE;
					var content = nDays[j].CONTENT;
					var priority = nDays[j].PRIORITY;
					var status = nDays[j].STATUS;
					var statusColor="";
					
					var stime, stime_a, stime_b, stime_c, stime_d;
						var etime, etime_a, etime_b, etime_c, etime_d;
						var nowDate = new Date();
						var nowYears = nowDate.getFullYear().toString();//当前年份
						var nowMonths = nowDate.getMonth() + 1;//当前月份
						if (nowMonths < 10) {
							nowMonths = "0" + nowMonths.toString();
						}
						var nowDates = nowDate.getDate();//当前日
						if (nowDates < 10) {
							nowDates = "0" + nowDates.toString();
						}
						var nowTime = nowDate.toLocaleTimeString().split(":");//获取当前时间,时分秒
						var nowDatetimeNum = nowYears + nowMonths + nowDates
								+ nowTime[0].toString() + nowTime[1].toString()
								+ nowTime[2].toString();
						stime_a = startdate.toString().substring(0, 10);
						stime_b = startdate.toString().substring(11, 19);
						stime_c = stime_a.split("-");
						stime_a = stime_c[0].toString() + stime_c[1].toString()
								+ stime_c[2].toString();
						stime_d = stime_b.split(":");
						stime_b = stime_d[0].toString() + stime_d[1].toString()
								+ stime_d[2].toString();
			
						etime_a = enddate.toString().substring(0, 10);
						etime_b = enddate.toString().substring(11, 19);
						etime_c = etime_a.split("-");
						etime_a = etime_c[0].toString() + etime_c[1].toString()
								+ etime_c[2].toString();
						etime_d = etime_b.split(":");
						etime_b = etime_d[0].toString() + etime_d[1].toString()
								+ etime_d[2].toString();
						stime = stime_a + stime_b;
						etime = etime_a + etime_b;
						
						if(!status||status==null||status=="null"){
							if(nowDatetimeNum>etime){status = "已过期"; }
							if(nowDatetimeNum>stime && nowDatetimeNum<etime){status = "进行中";}
							if(nowDatetimeNum<stime){status = "未开始";}
						}
						
					if (status == "已完成") {
						statusColor = "#3CB371";
					}
					else if (status == "已过期") {
						statusColor = "red";
					}
					else if (status == "进行中") {
						statusColor = "#0000FF";
					}
					else if (status == "未开始") {
						statusColor = "#B5005A";
					}
					var	Priority = convertPriority(priority);//优先级数字转为文字
					var priorityColor;
						if(priority==0){priorityColor="#000000";}
						else if(priority==1){priorityColor="#6F7274";}
						else if(priority==2){priorityColor="#00AA00";}
						else if(priority==3){priorityColor="#FF9933";}
						else if(priority==4){priorityColor="#FF0000";}
					var title = Priority+"("+status+")";
						nDaytxt = nDaytxt + "<div title="+ title
							+ " style=\"position:relative;left:0;width :100%;line-height:1.5;margin-top:1; border:1 solid #CDCDCD;background-color:#F7F7F7;\" id='div"
							+ sid + "'>&nbsp"
							+ "<a style=\"text-decoration:'none';\" href='javascript:void(0);' " + "sstartdate='"+ startdate +"' senddate='"+ enddate
							+ "' scaption='"+ caption +"' sid='" + sid + "' sstatus='"+ status + "' scontent='" + content
							+ "' spriority='"+ priority +"' onclick='message_affairsLooks(this)'>"+
							startdate.substring(0, 16) +" - "+ enddate.substring(0, 16) +" " +"<font color="+ statusColor +">"+ caption +"</font></a></div>";
				}
			}
		}
		var nDayHtml = document.getElementById("tdnday");//填入跨天数据
		if(nDayHtml.innerHTML = "&nbsp;"){nDayHtml.innerHTML = "";}
			nDayHtml.innerHTML = nDayHtml.innerHTML + nDaytxt;
			
//			var nDayHtml = document.getElementById("tdnday"+ personID);
//			if(nDayHtml.innerHTML = "&nbsp;"){nDayHtml.innerHTML = "";}
//			nDayHtml.innerHTML = nDayHtml.innerHTML + nDaytxt;
			
		/*============单天===============*/
		var singleDaytxt = "";
		var singleDay = monthData.data[i].singleDay;
		for(var k=0;k<singleDay.length;k++){
			if(singleDay[k].ID != null && singleDay[k].ID != undefined){
				var sid = singleDay[k].ID, caption = singleDay[k].CAPTION, startdate = singleDay[k].STARTDATE,
				enddate = singleDay[k].ENDDATE, content = singleDay[k].CONTENT, priority = singleDay[k].PRIORITY,
				status = singleDay[k].STATUS;
				var statusColor=""
				
				var stime, stime_a, stime_b, stime_c, stime_d;
				var etime, etime_a, etime_b, etime_c, etime_d;
				var nowDate = new Date();
				var nowYears = nowDate.getFullYear().toString();//当前年份
				var nowMonths = nowDate.getMonth() + 1;//当前月份
				if (nowMonths < 10) {
					nowMonths = "0" + nowMonths.toString();
				}
				var nowDates = nowDate.getDate();//当前日
				if (nowDates < 10) {
					nowDates = "0" + nowDates.toString();
				}
				var nowTime = nowDate.toLocaleTimeString().split(":");//获取当前时间,时分秒
				var nowDatetimeNum = nowYears + nowMonths + nowDates
						+ nowTime[0].toString() + nowTime[1].toString()
						+ nowTime[2].toString();
				stime_a = startdate.toString().substring(0, 10);
				stime_b = startdate.toString().substring(11, 19);
				stime_c = stime_a.split("-");
				stime_a = stime_c[0].toString() + stime_c[1].toString()
						+ stime_c[2].toString();
				stime_d = stime_b.split(":");
				stime_b = stime_d[0].toString() + stime_d[1].toString()
						+ stime_d[2].toString();
	
				etime_a = enddate.toString().substring(0, 10);
				etime_b = enddate.toString().substring(11, 19);
				etime_c = etime_a.split("-");
				etime_a = etime_c[0].toString() + etime_c[1].toString()
						+ etime_c[2].toString();
				etime_d = etime_b.split(":");
				etime_b = etime_d[0].toString() + etime_d[1].toString()
						+ etime_d[2].toString();
				stime = stime_a + stime_b;
				etime = etime_a + etime_b;
				
				if(!status||status==null||status=="null"){
					if(nowDatetimeNum>etime){status = "已过期"; }
					if(nowDatetimeNum>stime && nowDatetimeNum<etime){status = "进行中";}
					if(nowDatetimeNum<stime){status = "未开始";}
				}
							
				if (status == "已完成") {
					statusColor = "#3CB371";
				}
				else if (status == "已过期") {
					statusColor = "red";
				}
				else if (status == "进行中") {
					statusColor = "#0000FF";
				}
				else if (status == "未开始") {
					statusColor = "#B5005A";
				}
			var	Priority = convertPriority(priority);//优先级数字转为文字
			var priorityColor;
				if(priority==0){priorityColor="#000000";}
				else if(priority==1){priorityColor="#6F7274";}
				else if(priority==2){priorityColor="#00AA00";}
				else if(priority==3){priorityColor="#FF9933";}
				else if(priority==4){priorityColor="#FF0000";}
			var title = Priority+"("+status+")";
				singleDaytxt = singleDaytxt + "<div title="+ title
				+ " style=' width:100%; line-height:1.5;margin-top:1; border:1 solid #CDCDCD;background-color:#F7F7F7;' id='div"
				+ sid + "'>"
				+ "<a style=\"text-decoration:'none';\" href='javascript:void(0);' " + "sstartdate='"+ startdate +"' senddate='"+ enddate
				+ "' scaption='"+ caption +"' sid='" + sid + "' sstatus='"+ status + "' scontent='" + content
				+ "' spriority='"+ priority +"' onclick='message_affairsLooks(this)'>"+ 
				 startdate.substring(11, 16) +" - "+ enddate.substring(11, 16) +" "+"<font color="+ statusColor +">"+ caption +"</font></a></div>";
			var coordinateNumA = startdate.substring(0, 10).split("-");
			var coordinateDate = new Date(coordinateNumA[0],coordinateNumA[1] - 1, coordinateNumA[2]);
			var coordinateDay = coordinateDate.getDate()+(monthDayGap-1);
			var coordinate = "a" +  coordinateDay;
			var singleDayHtml = document.getElementById(coordinate);
	//			if(singleDayHtml.innerHTML = "&nbsp;"){singleDayHtml.innerHTML = "";}
	//			if (singleDayHtml.innerText == "" || singleDayHtml.innerText == null
	//					|| singleDayHtml.innerText == "null") {
	//				singleDayHtml.innerHTML = singleDaytxt;
	//			} else {
	//			}
			singleDaytxt = singleDayHtml.innerHTML + singleDaytxt;
			singleDayHtml.innerHTML = singleDaytxt;
			singleDaytxt="";
			}
		}
	}
}
/*==========将优先级数字转为文字========*/
var convertPriority=function(num) {
	var character;
	num = Number(num);
	switch(num){
	case 0:	character = "普通";	break;
	case 1:	character = "不重要/不紧急";	break;
	case 2:	character = "不重要/紧急";	break;
	case 3:	character = "重要/不紧急";	break;
	case 4:	character = "重要/紧急";	break;
	}
	return character;
}

/*============================构建数据============构建数据================*/
var loaddate;//页面载入日期毫秒
var loadMondaydate;//页面载入星期一日期毫秒
var getMonthData = function(v){
	var myDate = new Date();
	if (v != "now") {
		var dateArray = v.split("-");
		myDate.setFullYear(dateArray[0],dateArray[1]-1,dateArray[2]);
	}
	var initialYear = myDate.getFullYear(); //获取完整的年份(4位,1970-????) 
	var initialMonth = myDate.getMonth(); //获取当前月份(0-11,0代表1月) 
	var milliseconds = myDate.getTime(); //获取当前时间(从1970.1.1开始的毫秒数) 
//	var maxDaysDate = new Date(initialYear+"/"+(initialMonth+2)+"/"+0);/*得到月天数*/
	var maxDays;
	var browserType = showResult();//判断浏览器类型
	if(browserType=="chrome"){
		maxDays = getLastDay(initialYear,initialMonth+1);
	}
	else{
		var maxDaysDate = new Date(initialYear+"/"+(initialMonth+2)+"/"+0);/*得到月天数*/
	 	maxDays = maxDaysDate.getDate();/*得到月天数*/
	}
	
	var monthNum = initialMonth+1;
	if (monthNum < 10) {
		monthNum = "0" + monthNum.toString();
	}
	var sMonthNum = initialYear.toString()+monthNum.toString()+"01";/*月第一天数字轴*/
	var eMonthNum = initialYear.toString()+monthNum.toString()+maxDays.toString();/*月最后一天数字轴*/
	monthData.data=[];
	var tempData = new Array();
	var nDaysTempData = new Array();
	var dbkey = "system";
	var depPersonSql = "SELECT distinct sa_psnschedule.SWHOUSER ID,sa_opperson.SNAME NAME FROM "+
		"sa_psnschedule ,sa_opperson "+
		"WHERE sa_psnschedule.SWHOUSER = sa_opperson.SID ORDER BY sa_psnschedule.SWHOUSER";
	var depPerson = tlv8.sqlQueryActionforJson(dbkey,depPersonSql);/*部门人数*/
	
	for(var i=0;i<depPerson.data.length;i++){
		var nowID=depPerson.data[i].ID;
		var nowName = depPerson.data[i].NAME;
		/*跨天数据*/
		var nDaysDataSql = "SELECT sa_psnschedule.SID ID,"+
		"sa_psnschedule.SCAPTION CAPTION,"+
		"sa_psnschedule.SSTARTDATE STARTDATE,"+
		"sa_psnschedule.SENDDATE ENDDATE,"+
		"sa_psnschedule.SCONTENT CONTENT,"+
		"sa_psnschedule.SPRIORITY PRIORITY,"+
		"sa_psnschedule.SSTATUS STATUS,"+
		"sa_psnschedule.SSTARTDATE_AXIS STARTDATE_AXIS,"+
		"sa_psnschedule.SSENDDATE_AXIS ENDDATE_AXIS,"+
		"sa_psnschedule.SWHOUSER WHOUSER,"+
		"sa_opperson.SNAME NAME FROM "+
		"sa_psnschedule ,sa_opperson "+
		"WHERE sa_psnschedule.SWHOUSER='"+ nowID +
		"' AND (sa_psnschedule.SSENDDATE_AXIS>="+ sMonthNum +
		" AND sa_psnschedule.SSTARTDATE_AXIS<="+ eMonthNum +
		")  AND sa_psnschedule.SWHOUSER = sa_opperson.SID AND sa_psnschedule.SSTARTDATE_AXIS!=sa_psnschedule.SSENDDATE_AXIS " +
		" ORDER BY sa_psnschedule.SSTARTDATE ASC ";
		var nDays = tlv8.sqlQueryActionforJson(dbkey,nDaysDataSql);
		/*单天数据*/
		var singleDayDataSql = "SELECT sa_psnschedule.SID ID,"+
		"sa_psnschedule.SCAPTION CAPTION,"+
		"sa_psnschedule.SSTARTDATE STARTDATE,"+
		"sa_psnschedule.SENDDATE ENDDATE,"+
		"sa_psnschedule.SCONTENT CONTENT,"+
		"sa_psnschedule.SPRIORITY PRIORITY,"+
		"sa_psnschedule.SSTATUS STATUS,"+
		"sa_psnschedule.SSTARTDATE_AXIS STARTDATE_AXIS,"+
		"sa_psnschedule.SSENDDATE_AXIS SENDDATE_AXIS,"+
		"sa_psnschedule.SWHOUSER WHOUSER,"+
		"sa_opperson.SNAME NAME FROM "+
		"sa_psnschedule ,sa_opperson "+
		"WHERE sa_psnschedule.SWHOUSER='"+ nowID +
		"' AND (sa_psnschedule.SSTARTDATE_AXIS>="+ sMonthNum +
		" AND sa_psnschedule.SSENDDATE_AXIS<="+ eMonthNum +
		")   AND sa_psnschedule.SWHOUSER = sa_opperson.SID AND sa_psnschedule.SSTARTDATE_AXIS=sa_psnschedule.SSENDDATE_AXIS " +
		" ORDER BY sa_psnschedule.SSTARTDATE ASC ";
		var singleDay = tlv8.sqlQueryActionforJson(dbkey,singleDayDataSql);
		
		var temp={id:nowID,name:nowName,nDays:nDays.data,singleDay:singleDay.data};
		tempData.push(temp);
	}
	monthData.data = tempData;
}

//查看事务务对话框
function message_affairsLooks(obj) {
	var jq = jQuery.noConflict();
	obj$=jq(obj); 
	var cid = encodeURIComponent(obj$.attr("sid"));
	var cstartdate = encodeURIComponent(obj$.attr("sstartdate"));
	var cenddate = encodeURIComponent(obj$.attr("senddate"));
	var ccaption = encodeURIComponent(obj$.attr("scaption"));
	var cstatus = encodeURIComponent(obj$.attr("sstatus"));
	var ccontent = encodeURIComponent(obj$.attr("scontent"));
	var cpriority = encodeURIComponent(obj$.attr("spriority"));
	var itemSetInit = {
		refreshItem : false,
		enginItem : true,
		CanclItem : false
	}
	tlv8.portal.dailog.openDailog("查看事务",
			"/system/office/inquire_schedule/message_affairsLooks.html?sid=" + cid + "&scaption=" + ccaption
					+ "&sstartdate=" + cstartdate + "&senddate=" + cenddate + "&spriority=" + cpriority  + "&sstatus=" + cstatus +
					"&scontent=" + ccontent, 520, 270,
			callbackAffairsLooks, itemSetInit)
	event.cancelBubble = true;//阻止触发事件
}
function callbackAffairsLooks() {//回调函数
//	myTaskLoad();
}

var monthData= {
	data : [ {
		id : "PSN02",
		name : "企掌柜", //s.data[0].schedule.xingqiyi
		nDays : [ {
			id : "AA7489204B384E1FBCE0E493822841F9",
			caption : "今天的事1",//标题
			startdate : "2012-03-27 07:00:00",//开始日期时间
			enddate : "2012-04-01 07:59:00",//结束日期时间
			content : "第一件事",//内容
			priority : "4",//优先级别
			status : "进行中"//状态
		}, {
			id : "BAE10229CE36419FBF6131C424AB1951",
			caption : "今天的事2",//标题
			startdate : "2012-03-28 08:00:00",//开始日期时间
			enddate : "2012-03-30 22:59:00",//结束日期时间
			content : "第二件事",//内容
			priority : "0",
			status : "进行中"
		} ],
		singleDay : [ {
			id : "CA2389204B384E1FBCE0E493822841F9",
			caption : "今天的事1",//标题
			startdate : "2012-03-27 07:00:00",//开始日期时间
			enddate : "2012-03-27 07:59:00",//结束日期时间
			content : "第1件事",//内容
			priority : "0",//优先级别
			status : "进行中"//状态
		},{
			id : "DAE15529CE36419FBF6131C424AB1951",
			caption : "今天的事2",//标题
			startdate : "2012-03-28 08:00:00",//开始日期时间
			enddate : "2012-03-28 22:59:00",//结束日期时间
			content : "第2件事",//内容
			priority : "0",
			status : "进行中"
		},{
			id : "EAE16529CE36419FBF6131C424AB1951",
			caption : "今天的事3",//标题
			startdate : "2012-03-29 08:00:00",//开始日期时间
			enddate : "2012-03-29 22:59:00",//结束日期时间
			content : "第3件事",//内容
			priority : "0",
			status : "进行中"
		} ]
	}]
}
