var affairsID;
//页面载入执行
function onloadset(){//sstartdate,senddate,scaption,scoordinate_id,sid,sstatus,spriority
	affairsID = decodeURIComponent(tlv8.RequestURLParam.getParam("sid"));
	document.getElementById("scaption").value = decodeURIComponent(tlv8.RequestURLParam.getParam("scaption"));//标题
	var priority = decodeURIComponent(tlv8.RequestURLParam.getParam("spriority"));//优先级
	document.getElementById("spriority").value = priority;//
	var startdate = decodeURIComponent(tlv8.RequestURLParam.getParam("sstartdate")).substring(0, 19);
	document.getElementById("sstartdate").value = startdate;//开始时间
	var enddate = decodeURIComponent(tlv8.RequestURLParam.getParam("senddate")).substring(0, 19);
	document.getElementById("senddate").value = enddate;//结束时间
	document.getElementById("scontent").value = decodeURIComponent(tlv8.RequestURLParam.getParam("scontent"));//内容
	document.getElementById("scaption").focus();
}
//确定按钮
function dailogEngin(){
//	if (confirm("您确定?取消？")){
//		alert("OK");
//	}
//	else{alert("Cancel");}
}

//修改更新
function affairsUpdate(){
	var caption = document.getElementById("scaption").value;//标题
	var priority = document.getElementById("spriority").value;//优先级
	var statertime = document.getElementById("sstartdate").value;//开始时间
	var endtime = document.getElementById("senddate").value;//结束时间
	var content = document.getElementById("scontent").value;//内容
	var stime_a,stime_b,stime_c,stime_d;
	var etime_a,etime_b,etime_c,etime_d;
	var coordinate_id;
	
	if(caption==""||caption==null||caption=="null"){alert("亲，请填写标题哦！^_^");event.cancelBubble=true;return;}
	if(statertime==""||statertime==null||statertime=="null"){alert("亲，要选择开始时间哦！^_^");event.cancelBubble=true;return;}
	if(endtime==""||endtime==null||endtime=="null"){alert("亲，要选择结束时间哦！^_^");event.cancelBubble=true;return;}
	if(content==""||content==null||content=="null"){alert("亲，还没有填写内容哦！^_^");event.cancelBubble=true;return;}

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
	var newSdateA = statertime.toString().substring(0, 10);
	var newSdateB = statertime.toString().substring(11, 19);
	var newEdateA = endtime.toString().substring(0, 10);
	var newEdateB = endtime.toString().substring(11, 19);
	var sqlUP="update sa_psnschedule set SCAPTION='"+caption+"',SPRIORITY='"+priority+
			"',SSTARTDATE='"+newSdateA+" "+newSdateB+"'"+
			",SENDDATE='"+newEdateA+" "+newEdateB+"'"+
			",SCONTENT='"+content+"',SSTARTDATE_AXIS="+stime_a+",SSENDDATE_AXIS="+etime_a+" where SID='"+affairsID+"' ";
	var result=tlv8.sqlUpdateAction("system",sqlUP);
	if(true==result.data||"true"==result.data){
		alert("不明错误，修改失败");
	}
	else{alert("修改成功！");}
}
//删除按钮
function affairsDelete(){
	var dataDelete = new tlv8.Data();
	dataDelete.setDbkey("system");
	dataDelete.setTable("SA_PSNSCHEDULE");
	dataDelete.setFormId("affairslooks");
	dataDelete.rowid = affairsID;
	var result = dataDelete.deleteData();
}
