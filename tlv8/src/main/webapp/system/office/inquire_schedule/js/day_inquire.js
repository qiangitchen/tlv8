/*页面载入*/
var dayOnload = {
	onloadrun : function() {
		var setDate = tlv8.RequestURLParam.getParam("setDate");
		getData(setDate);
		
		var needDate = new Date();
		if (setDate!="now"){
		var dateArray = setDate.split("-");
		needDate.setFullYear(dateArray[0],dateArray[1]-1,dateArray[2]); 
	}
		document.getElementById("tdDate").innerHTML = needDate.toLocaleDateString();
		
		setTableHtml();
	}
}
	/*构建表格HTML*/
var setTableHtml = function() {
	var divHtml = "";
	for(var i=0;i<dayData.data.length;i++){
		var personID = dayData.data[i].id,personName = dayData.data[i].name;
		var affairs=dayData.data[i].affairsData;
		for(var j=0;j<affairs.length;j++){
			try{
				var sid = affairs[j].ID;
			var caption = affairs[j].CAPTION;
			var startdate = affairs[j].STARTDATE.substr(0,19);
			var enddate = affairs[j].ENDDATE.substr(0,19);
			var content = affairs[j].CONTENT;
			var priority = affairs[j].PRIORITY;
			var status = affairs[j].STATUS;
			var affairstype = affairs[j].AFFAIRSTYPE;
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
			var title = "("+Priority+"/"+status+")";
			if(affairstype==1){
				title = "单天"+title;
			}
			else if(affairstype==2){
				title = "跨天"+title;
			}
			else if(affairstype==3){
				title = "周期"+title;
			}
			var tableHtml;//<td rowspan="2">&nbsp;</td>
			if(j!=0){
				tableHtml = "<tr align='center' bgcolor='#FFFFFF' style=\"font-family: '宋体';font-size:12;text-decoration:none;\">"
					+"<td height='20'><a style=\"text-decoration:'none';\" title='"+ title +"' href='javascript:void(0);' onclick='message_affairsLooks(this)' sid='"+ sid
					+"' scaption='"+ caption +"' sstartdate='"+ startdate+"' senddate='"+ enddate+"' spriority='"+ priority
					+"' sstatus='"+ status +"' scontent='"+ content+"'>"+ caption +"</a></td>" 
					+"<td>"+ startdate +"</td>" + "<td>"+ enddate +"</td>" + "<td><font color="+ priorityColor+">"+ Priority +"</font></td>"
					+"<td><font color="+ statusColor+">"+ status +"</font></td>" + "<td>"+ content +"</td></tr>";
			}
			else if(j==0){
				tableHtml = "<tr align='center' bgcolor='#FFFFFF' style=\"font-family: '宋体';font-size:12;\"><td rowspan='"+ affairs.length +"' style=\"font-size:14\">"+ personName +"</td>"
					+"<td height='20'><a style=\"text-decoration:'none';\" title='"+ title +"' href='javascript:void(0);' onclick='message_affairsLooks(this)' sid='"+ sid
					+"' scaption='"+ caption +"' sstartdate='"+ startdate+"' senddate='"+ enddate+"' spriority='"+ priority
					+"' sstatus='"+ status +"' scontent='"+ content+"'>"+ caption +"</a></td>" 
					+"<td>"+ startdate +"</td>" + "<td>"+ enddate +"</td>" + "<td><font color="+ priorityColor+">"+ Priority +"</font></td>"
					+"<td><font color="+ statusColor+">"+ status +"</font></td>" + "<td>"+ content +"</td></tr>";
			}
			divHtml = divHtml + tableHtml;
			}catch(e){continue;}
		}
	}
	var divDayScheduleHtml = document.getElementById("divDaySchedule");
		divDayScheduleHtml.innerHTML = divDayScheduleHtml.innerHTML + dayData.headerhtml+divHtml+"</table>";
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
	/*将优先级数字转为文字*/
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
var getData = function(v){
	
	var needDate = new Date();
	if (v!="now"){
		var dateArray = v.split("-");
		needDate.setFullYear(dateArray[0],dateArray[1]-1,dateArray[2]); 
	}
	var needFirstnum = needDate.getFullYear();//得到年份
	var needSecondnum = needDate.getMonth() + 1;//得到月份
	if (needSecondnum < 10) {
		needSecondnum = "0" + needSecondnum.toString();
	}
	var needThirdnum = needDate.getDate();//得到日期
	if (needThirdnum < 10) {
		needThirdnum = "0" + needThirdnum.toString();
	}
	var needNum = needFirstnum.toString() + needSecondnum.toString()
			+ needThirdnum.toString();//时间轴数字
	
	dayData.data=[];
	var tempData = new Array();
	var dbkey = "system";
	var depPersonSql = "SELECT distinct sa_psnschedule.SWHOUSER ID,sa_opperson.SNAME NAME FROM "+
		"sa_psnschedule ,sa_opperson "+
		"WHERE sa_psnschedule.SWHOUSER = sa_opperson.SID ORDER BY sa_psnschedule.SWHOUSER";
	var depPerson = tlv8.sqlQueryActionforJson(dbkey,depPersonSql);
	for(var i=0;i<depPerson.data.length;i++){
//		var tempData =new Object();
		var nowID=depPerson.data[i].ID;
		var nowName = depPerson.data[i].NAME;
		var affairsDataSql = "SELECT sa_psnschedule.SID ID,"+
		"sa_psnschedule.SCAPTION CAPTION,"+
		"sa_psnschedule.SSTARTDATE STARTDATE,"+
		"sa_psnschedule.SENDDATE ENDDATE,"+
		"sa_psnschedule.SCONTENT CONTENT,"+
		"sa_psnschedule.SPRIORITY PRIORITY,"+
		"sa_psnschedule.SSTATUS STATUS,"+
		"sa_psnschedule.SSTARTDATE_AXIS STARTDATE_AXIS,"+
		"sa_psnschedule.SSENDDATE_AXIS SENDDATE_AXIS,"+//SAFFAIRSTYPE
		"sa_psnschedule.SAFFAIRSTYPE AFFAIRSTYPE,"+
		"sa_psnschedule.SWHOUSER WHOUSER,"+
		"sa_opperson.SNAME NAME FROM "+
		"sa_psnschedule ,sa_opperson "+
		"WHERE sa_psnschedule.SWHOUSER='"+ nowID +
		"' AND SSENDDATE_AXIS>="+ needNum +
		" AND SSTARTDATE_AXIS<="+ needNum +
		" AND sa_psnschedule.SWHOUSER = sa_opperson.SID " +
		" ORDER BY sa_psnschedule.SSTARTDATE ASC ";
		var affairs = tlv8.sqlQueryActionforJson(dbkey,affairsDataSql);
		var temp={id:nowID,name:nowName,affairsData:affairs.data};
		tempData.push(temp);
	}
	dayData.data = tempData;
}
var dayData = {
	/*数据*/
	data : [ {
		id : "PSN01",
		name : "system", //s.data[0].schedule.xingqiyi
		affairsData : [ {
			id : "BA7489204B384E1FBCE0E493822841F9",
			caption : "今天的事1",//标题
			startdate : "2012-03-26 07:00:00",//开始日期时间
			enddate : "2012-03-26 07:59:00",//结束日期时间
			content : "第一件事",//内容
			priority : "4",//优先级别
			status : "进行中"//状态
		},{
			id : "AAE10229CE36419FBF6131C424AB1951",
			caption : "今天的事2",//标题
			startdate : "2012-03-26 08:00:00",//开始日期时间
			enddate : "2012-03-26 22:59:00",//结束日期时间
			content : "第二件事",//内容
			priority : "0",
			status : "进行中"
		}]
	},{
		id : "PSN02",
		name : "哪一个", //s.data[0].schedule.xingqiyi
		affairsData : [ {
			id : "BA2389204B384E1FBCE0E493822841F9",
			caption : "今天的事1",//标题
			startdate : "2012-03-26 07:00:00",//开始日期时间
			enddate : "2012-03-26 07:59:00",//结束日期时间
			content : "第一件事",//内容
			priority : "0",//优先级别
			status : "进行中"//状态
		},{
			id : "AAE15529CE36419FBF6131C424AB1951",
			caption : "今天的事2",//标题
			startdate : "2012-03-26 08:00:00",//开始日期时间
			enddate : "2012-03-26 22:59:00",//结束日期时间
			content : "第二件事",//内容
			priority : "0",
			status : "进行中"
		},{
			id : "AAE16529CE36419FBF6131C424AB1951",
			caption : "今天的事2",//标题
			startdate : "2012-03-26 08:00:00",//开始日期时间
			enddate : "2012-03-26 22:59:00",//结束日期时间
			content : "第二件事",//内容
			priority : "0",
			status : "进行中"
		}]
	}],
	/*表头*/
	headerhtml : "<table width='100%' border='1' cellpadding='1' cellspacing='1' bordercolor='#88C4FF' bgcolor='#FEFEFE'>"
			+ "<tr align=\"center\"  valign=\"middle\" style=\"font-family: '宋体';font-size:14; color:'#0000CC'\">"
			+ "<td width='74' height='24'>姓名</td>"
			+ "<td width='123' >标题</td>"
			+ "<td width='125' >开始时间</td>"
			+ "<td width='125' >结束时间</td>"
			+ "<td width='84' >优先级别</td>"
			+ "<td width='54' >状态</td>"
			+ "<td width='450' >内容</td></tr>"
}



