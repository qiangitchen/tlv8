
function stopEvent(event){
	event=event?event:window.event;
    event.cancelBubble=true;
}
//确定按钮，存储数据
function dailogEngin() {
	var capt=document.getElementById("SCAPTION").value;//标题
	var cont=document.getElementById("SCONTENT").value;//内容
	var statertime=document.getElementById("SSTARTDATE").value;//开始时间
	var endtime=document.getElementById("SENDDATE").value;//结束时间TDscaption
	var stime_a,stime_b,stime_c,stime_d;
	var etime_a,etime_b,etime_c,etime_d;
	if(capt==""||capt==null||capt=="null"){alert("亲，请填写标题哦！^_^");stopEvent(event);return;}
	if(statertime==""||statertime==null||statertime=="null"){alert("亲，要选择开始时间哦！^_^");stopEvent(event);return;}
	if(endtime==""||endtime==null||endtime=="null"){alert("亲，要选择结束时间哦！^_^");stopEvent(event);return;}
	if(cont==""||cont==null||cont=="null"){alert("亲，还没有填写内容哦！^_^");stopEvent(event);return;}

		stime_a = statertime.toString().substring(0, 10);
		stime_b = statertime.toString().substring(11, 19);
		stime_c = stime_a.split("-");
		stime_a = stime_c[0].toString() + stime_c[1].toString()+ stime_c[2].toString();
		stime_d = stime_b.split(":");
		stime_b = stime_d[0].toString() + stime_d[1].toString() + stime_d[2].toString();
		etime_a = endtime.toString().substring(0, 10);
		etime_b = endtime.toString().substring(11, 19);
		etime_c = etime_a.split("-");
		etime_a = etime_c[0].toString() + etime_c[1].toString()+ etime_c[2].toString();
		etime_d = etime_b.split(":");
		etime_b = etime_d[0].toString() + etime_d[1].toString() + etime_d[2].toString();
		if((stime_a+stime_b)>(etime_a+etime_b)){
			alert("亲，结束时间不要早于开始时间哦！(*^__^*) 嘻嘻……");
			event.cancelBubble=true;return;
		}
	var maindata = new tlv8.Data();
	maindata.setTable("sa_psnschedule");// 设置保存的表
	maindata.setFormId("affairsdata");// 设置提交的表单
	maindata.setDbkey("system");// 指定使用数据库连接

	var rowid = maindata.saveData();
//		var datas=new Array();
//      return(datas);
	//			if(rowid!="undefined"||rowid!=""||rowid!=null){
	//			document.getElementById("SA_PSNSCHEDULE").rowid = rowid;// 记住当前rowid,避免重复创建
	//			}
}
//日期框失去焦点时执行
function setTextdata() {
//	var coor_rd=tlv8.RequestURLParam.getParam("coordinate_id");
	var statertime = document.getElementById("SSTARTDATE").value;//开始时间
	var endtime = document.getElementById("SENDDATE").value;//结束时间
	var stime_a,stime_b,stime_c,stime_d;
	var etime_a,etime_b,etime_c,etime_d;
	try{
		if(statertime!=""||statertime!=null||statertime!="null"){
		stime_a = statertime.toString().substring(0, 10);
		stime_b = statertime.toString().substring(11, 19);
		stime_c = stime_a.split("-");
		stime_a = stime_c[0].toString() + stime_c[1].toString()+ stime_c[2].toString();
		stime_d = stime_b.split(":");
		stime_b = stime_d[0].toString() + stime_d[1].toString() + stime_d[2].toString();
		document.getElementById("SSTARTDATE_AXIS").value = stime_a;
		}
	}catch(e){}
	
	try{
		if(endtime!=""||endtime!=null||endtime!="null"){
		etime_a = endtime.toString().substring(0, 10);
		etime_b = endtime.toString().substring(11, 19);
		etime_c = etime_a.split("-");
		etime_a = etime_c[0].toString() + etime_c[1].toString()+ etime_c[2].toString();
		etime_d = etime_b.split(":");
		etime_b = etime_d[0].toString() + etime_d[1].toString() + etime_d[2].toString();
		document.getElementById("SSENDDATE_AXIS").value = etime_a;
		}
	}catch(e){}
	document.getElementById("SAFFAIRSTYPE").value=2;
	try{
		if((stime_a)==(etime_a)){
			var coorStime = statertime.toString().substring(0, 10);
			var coortime=Number(stime_a = statertime.toString().substring(11, 13));
			var coorStime_a = coorStime.split("-");
			var coordate = new Date(coorStime_a[0], coorStime_a[1]-1, coorStime_a[2]);
			var coorday=coordate.getDay();
			if(coorday==0){coorday=7;}
			document.getElementById("SAFFAIRSTYPE").value=1;
		}
		else if(stime_a != etime_a){document.getElementById("SAFFAIRSTYPE").value=2;}
	}catch(e){}
}
//页面载入执行
function onloadset(){
	var coordinate_id = tlv8.RequestURLParam.getParam("coordinate_id");//单元格ID
	var loadMondaydate = tlv8.RequestURLParam.getParam("loadMondaydate");//周一日期毫秒数
	var weekday = coordinate_id.toString().substring(1, 2);
	var needHours = coordinate_id.toString().substring(3);
	if(needHours < 10){needHours = "0"+ needHours.toString();}
	var gap = weekday-1;//与周一相差天数
	var needDate = new Date(loadMondaydate*1+86400000*gap);
	var needYears = needDate.getFullYear().toString();//年份
	var needMonths = needDate.getMonth()+1;//月份
	if(needMonths < 10){needMonths = "0"+ needMonths.toString();}
	var needDates = needDate.getDate();//日
	if(needDates < 10){needDates = "0"+ needDates.toString();}
	var startDatetime = needYears+"-"+needMonths+"-"+needDates+" "+needHours+":00:00";
	var endDatetime = needYears+"-"+needMonths+"-"+needDates+" "+needHours+":59:00";
	document.getElementById("SSTARTDATE").value = startDatetime;
	document.getElementById("SENDDATE").value = endDatetime;
	document.getElementById("SWHOUSER").value = tlv8.Context.getCurrentPersonID();
	document.getElementById("SCAPTION").focus();
}




