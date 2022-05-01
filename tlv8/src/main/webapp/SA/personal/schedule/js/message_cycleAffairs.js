//确定按钮，存储数据
function dailogEngin() {
	var capt=document.getElementById("scaption").value;//标题
	var cont=document.getElementById("scontent").value;//内容
	var statertime=document.getElementById("sstartdate").value;//开始时间
	var endtime=document.getElementById("senddate").value;//结束时间
	var stime_a,stime_b,stime_c,stime_d;
	var etime_a,etime_b,etime_c,etime_d;
	if(capt==""||capt==null||capt=="null"){alert("亲，请填写标题哦！^_^");event.cancelBubble=true;return;}
	if(statertime==""||statertime==null||statertime=="null"){alert("亲，要选择开始时间哦！^_^");event.cancelBubble=true;return;}
	if(endtime==""||endtime==null||endtime=="null"){alert("亲，要选择结束时间哦！^_^");event.cancelBubble=true;return;}
	if(!cont){alert("亲，还没有填写内容哦！^_^");event.cancelBubble=true;return;}

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
	maindata.setTable("SA_PSNSCHEDULE");// 设置保存的表
	maindata.setFormId("affairsdata");// 设置提交的表单
	maindata.setDbkey("system");// 指定使用数据库连接

	var rowid = maindata.saveData();
}
//日期框失去焦点时执行
function setTextdata() {
//	var coor_rd=tlv8.RequestURLParam.getParam("coordinate_id");
	var statertime = document.getElementById("sstartdate").value;//开始时间
	var endtime = document.getElementById("senddate").value;//结束时间
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
		document.getElementById("sstartdate_axis").value = stime_a;
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
		document.getElementById("ssenddate_axis").value = etime_a;
		}
	}catch(e){}
	document.getElementById("SAFFAIRSTYPE").value=3;
//	try{
//		if((stime_a)==(etime_a)){
//			var coorStime = statertime.toString().substring(0, 10);
//			var coortime=Number(stime_a = statertime.toString().substring(11, 13));
//			var coorStime_a = coorStime.split("-");
//			var coordate = new Date(coorStime_a[0], coorStime_a[1]-1, coorStime_a[2]);
//			var coorday=coordate.getDay();
//			if(coorday==0){coorday=7;}
//			document.getElementById("scoordinate_id").value="w"+coorday+"t"+coortime;
//		}
//		else if(stime_a != etime_a){document.getElementById("scoordinate_id").value="tdndays";}
//	}catch(e){}
}
//页面载入执行
function onloadAction(){
	var nowDate = new Date();
	var nowMonths = nowDate.getMonth()+1;//日期控件月份
	if(nowMonths < 10){nowMonths = "0"+ nowMonths.toString();}
	var nowDates = nowDate.getDate();//日期控件日
	if(nowDates < 10){nowDates = "0"+ nowDates.toString();}
	document.getElementById("sstartdate").value = nowDate.getFullYear()+"-"+nowMonths+"-"+nowDates+" "+nowDate.toLocaleTimeString();
	document.getElementById("SWHOUSER").value = tlv8.Context.getCurrentPersonID();
//	var t=nowDate.getFullYear()+"-"+nowMonths+"-"+nowDates+" "+nowDate.toLocaleTimeString();
	document.getElementById("scaption").focus();
}