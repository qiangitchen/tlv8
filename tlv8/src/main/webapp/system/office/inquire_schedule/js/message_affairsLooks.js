/*页面载入*/
var onloadrun = function(){
		setTableData();
}
	/*页面载入获取设置数据*/
var setTableData = function(){
	affairsID = decodeURIComponent(tlv8.RequestURLParam.getParam("sid"));
	document.getElementById("scaption").value = decodeURIComponent(tlv8.RequestURLParam.getParam("scaption"));//标题
	var priority = decodeURIComponent(tlv8.RequestURLParam.getParam("spriority"));//优先级
	document.getElementById("spriority").value = priority;//
	var startdate = decodeURIComponent(tlv8.RequestURLParam.getParam("sstartdate")).substring(0, 19);//开始时间
	document.getElementById("sstartdate").value = startdate;//开始时间
	var enddate = decodeURIComponent(tlv8.RequestURLParam.getParam("senddate")).substring(0, 19);//结束时间
	document.getElementById("senddate").value = enddate;//结束时间
	var status = decodeURIComponent(tlv8.RequestURLParam.getParam("status"));//状态
	if(status!=""||status!=null||status!="null"){
		document.getElementById("sstatus").value = status;
	}
	if(status==""||status==null||status=="null"){//状态
		var stime,stime_a,stime_b,stime_c,stime_d;
		var etime,etime_a,etime_b,etime_c,etime_d;
		var nowDate = new Date();
		var nowYears = nowDate.getFullYear().toString();//当前年份
		var nowMonths = nowDate.getMonth()+1;//当前月份
		if(nowMonths < 10){nowMonths = "0"+ nowMonths.toString();}
		var nowDates = nowDate.getDate();//当前日
		if(nowDates < 10){nowDates = "0"+ nowDates.toString();}
		var nowTime = nowDate.toLocaleTimeString().split(":");//获取当前时间,时分秒
		var mydate = nowYears+nowMonths+nowDates+nowTime[0].toString()+nowTime[1].toString()+nowTime[2].toString();
		stime_a = startdate.toString().substring(0, 10);
		stime_b = startdate.toString().substring(11, 19);
		stime_c = stime_a.split("-");
		stime_a = stime_c[0].toString() + stime_c[1].toString()+ stime_c[2].toString();
		stime_d = stime_b.split(":");
		stime_b = stime_d[0].toString() + stime_d[1].toString() + stime_d[2].toString();
		
		etime_a = enddate.toString().substring(0, 10);
		etime_b = enddate.toString().substring(11, 19);
		etime_c = etime_a.split("-");
		etime_a = etime_c[0].toString() + etime_c[1].toString()+ etime_c[2].toString();
		etime_d = etime_b.split(":");
		etime_b = etime_d[0].toString() + etime_d[1].toString() + etime_d[2].toString();
		stime = stime_a+stime_b;
		etime = etime_a+etime_b;
		if(mydate<stime){
			document.getElementById("sstatus").value = "未开始";
		}
		if(mydate>stime && mydate<etime){
			document.getElementById("sstatus").value = "进行中";
		}
		if(mydate>etime){
			document.getElementById("sstatus").value = "已过期";
		}
	}
	document.getElementById("scontent").value = decodeURIComponent(tlv8.RequestURLParam.getParam("scontent"));//内容
	document.getElementById("scaption").focus();
}