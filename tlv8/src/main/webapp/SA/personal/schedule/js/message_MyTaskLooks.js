var affairsID;
//页面载入执行
function onloadset(){//sstartdate,senddate,scaption,scoordinate_id,sid,sstatus,spriority
	affairsID = decodeURIComponent(tlv8.RequestURLParam.getParam("sid"));
	document.getElementById("SCAPTION").value = decodeURIComponent(tlv8.RequestURLParam.getParam("scaption"));//标题
	var priority = decodeURIComponent(tlv8.RequestURLParam.getParam("spriority"));//优先级
	document.getElementById("SPRIORITY").value = priority;//
	var startdate = decodeURIComponent(tlv8.RequestURLParam.getParam("sstartdate")).substring(0, 19);//开始时间
	document.getElementById("SSTARTDATE").value = startdate;
	var enddate = decodeURIComponent(tlv8.RequestURLParam.getParam("senddate")).substring(0, 19);//结束时间
	document.getElementById("SENDDATE").value = enddate;
	var affairstype = decodeURIComponent(tlv8.RequestURLParam.getParam("saffairstype"));//类型
	document.getElementById("SAFFAIRSTYPE").value = affairstype;
	var completerate = decodeURIComponent(tlv8.RequestURLParam.getParam("scompleterate"));//完成率
	document.getElementById("SCOMPLETERATE").value = completerate;
	var status = decodeURIComponent(tlv8.RequestURLParam.getParam("status"));//状态
	document.getElementById("SSTATUS").value = status;
	document.getElementById("SCONTENT").value = decodeURIComponent(tlv8.RequestURLParam.getParam("scontent"));//内容
	document.getElementById("SCAPTION").focus();
}
//确定按钮
function dailogEngin(){
}
//修改更新
function affairsUpdate(){
	var caption = document.getElementById("SCAPTION").value;/*标题*/
	var priority = document.getElementById("SPRIORITY").value;//优先级
	var statertime = document.getElementById("SSTARTDATE").value;//开始时间
	var endtime = document.getElementById("SENDDATE").value;//结束时间
	var affairstype = document.getElementById("SAFFAIRSTYPE").value;//类型
	var completerate = document.getElementById("SCOMPLETERATE").value;//完成率
	var status = document.getElementById("SSTATUS").value;/*状态*/
	var content = document.getElementById("SCONTENT").value;/*内容*/
	var stime_a,stime_b,stime_c,stime_d;
	var etime_a,etime_b,etime_c,etime_d;
	
	if(!caption||caption==""||caption==null||caption=="null"){alert("亲，请填写标题哦！^_^");event.cancelBubble=true;return;}
	if(!statertime||statertime==""||statertime==null||statertime=="null"){alert("亲，要选择开始时间哦！^_^");event.cancelBubble=true;return;}
	if(!endtime||endtime==""||endtime==null||endtime=="null"){alert("亲，要选择结束时间哦！^_^");event.cancelBubble=true;return;}
	if(!content||content==""||content==null||content=="null"){alert("亲，还没有填写内容哦！^_^");event.cancelBubble=true;return;}

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
	var sqlUP="update sa_psnmytask set SCAPTION='"+caption+"',SPRIORITY='"+priority+
			"',SSTARTDATE='"+newSdateA+" "+newSdateB+"',SENDDATE='"+newEdateA+" "+newEdateB+"'"+
			",SCONTENT='"+content+"',SSTARTDATE_AXIS="+stime_a+",SSENDDATE_AXIS="+etime_a+" where SID='"+affairsID+"' ";
	var result=tlv8.sqlUpdateAction("system",sqlUP);
	if(true==result.data||"true"==result.data){
		alert("不明错误，修改失败");
	}
	else{alert("修改成功！");}
}
//已完成按钮
function affairsCompleted(){
	var sqlUP = "update sa_psnmytask set SSTATUS='已完成',SCOMPLETERATE='100' where SID='"+affairsID+"' ";
	var result=tlv8.sqlUpdateAction("system",sqlUP);
	if(true==result.data||"true"==result.data){
		alert("不明错误，修改失败");
	}
	else{alert("终于结束了！");
		document.getElementById("sstatus").value ="已完成";
		document.getElementById("SCOMPLETERATE").value = "100"}
}
//删除按钮
function affairsDelete(){
	var dataDelete = new tlv8.Data();
	dataDelete.setDbkey("system");
	dataDelete.setTable("sa_psnmytask");
	dataDelete.setFormId("affairsdata");
	dataDelete.rowid = affairsID;
	var result = dataDelete.deleteData();
}