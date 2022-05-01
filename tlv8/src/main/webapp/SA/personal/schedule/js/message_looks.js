var affairsID;
// 页面载入执行
function onloadset() {// sstartdate,senddate,scaption,scoordinate_id,sid,sstatus,spriority
	affairsID = decodeURIComponent(tlv8.RequestURLParam.getParam("sid"));
	document.getElementById("scaption").value = decodeURIComponent(tlv8.RequestURLParam
			.getParam("scaption"));// 标题
	var priority = decodeURIComponent(tlv8.RequestURLParam
			.getParam("spriority"));// 优先级
	document.getElementById("spriority").value = priority;//
	var startdate = decodeURIComponent(
			tlv8.RequestURLParam.getParam("sstartdate")).substring(0, 19);
	document.getElementById("sstartdate").value = startdate;// 开始时间
	var enddate = decodeURIComponent(tlv8.RequestURLParam.getParam("senddate"))
			.substring(0, 19);
	document.getElementById("senddate").value = enddate;// 结束时间
	var status = decodeURIComponent(tlv8.RequestURLParam.getParam("status"));// 状态
	if (status && status != "" && status != null && status != "null"
			&& status != "undefined") {
		document.getElementById("sstatus").value = status;
	}
	if (!status || status == "" || status == null || status == "null") {// 状态
		var stime, stime_a, stime_b, stime_c, stime_d;
		var etime, etime_a, etime_b, etime_c, etime_d;
		var nowDate = new Date();
		var nowYears = nowDate.getFullYear().toString();// 当前年份
		var nowMonths = nowDate.getMonth() + 1;// 当前月份
		if (nowMonths < 10) {
			nowMonths = "0" + nowMonths.toString();
		}
		var nowDates = nowDate.getDate();// 当前日
		if (nowDates < 10) {
			nowDates = "0" + nowDates.toString();
		}
		var nowTime = nowDate.toLocaleTimeString().split(":");// 获取当前时间,时分秒
		var mydate = nowYears + nowMonths + nowDates + nowTime[0].toString()
				+ nowTime[1].toString() + nowTime[2].toString();
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
		if (mydate < stime) {
			document.getElementById("sstatus").value = "未开始";
		}
		if (mydate > stime && mydate < etime) {
			document.getElementById("sstatus").value = "进行中";
		}
		if (mydate > etime) {
			document.getElementById("sstatus").value = "已过期";
		}
	}
	document.getElementById("scontent").value = decodeURIComponent(tlv8.RequestURLParam
			.getParam("scontent"));// 内容
	document.getElementById("scaption").focus();
}
// 确定按钮
function dailogEngin() {
	// if (confirm("您确定?取消？")){
	// alert("OK");
	// }
	// else{alert("Cancel");}
}
// 修改更新
function affairsUpdate() {

	// alert(affairsID);
	var caption = document.getElementById("scaption").value;// 标题
	var priority = document.getElementById("spriority").value;// 优先级
	var statertime = document.getElementById("sstartdate").value;// 开始时间
	var endtime = document.getElementById("senddate").value;// 结束时间
	var content = document.getElementById("scontent").value;// 内容
	var stime_a, stime_b, stime_c, stime_d;
	var etime_a, etime_b, etime_c, etime_d;
	var coordinate_id;

	if (caption == "" || caption == null || caption == "null") {
		alert("亲，请填写标题哦！^_^");
		event.cancelBubble = true;
		return;
	}
	if (statertime == "" || statertime == null || statertime == "null") {
		alert("亲，要选择开始时间哦！^_^");
		event.cancelBubble = true;
		return;
	}
	if (endtime == "" || endtime == null || endtime == "null") {
		alert("亲，要选择结束时间哦！^_^");
		event.cancelBubble = true;
		return;
	}
	if (content == "" || content == null || content == "null") {
		alert("亲，还没有填写内容哦！^_^");
		event.cancelBubble = true;
		return;
	}

	stime_a = statertime.toString().substring(0, 10);
	stime_b = statertime.toString().substring(11, 19);
	stime_c = stime_a.split("-");
	stime_a = stime_c[0].toString() + stime_c[1].toString()
			+ stime_c[2].toString();
	stime_d = stime_b.split(":");
	stime_b = stime_d[0].toString() + stime_d[1].toString()
			+ stime_d[2].toString();
	etime_a = endtime.toString().substring(0, 10);
	etime_b = endtime.toString().substring(11, 19);
	etime_c = etime_a.split("-");
	etime_a = etime_c[0].toString() + etime_c[1].toString()
			+ etime_c[2].toString();
	etime_d = etime_b.split(":");
	etime_b = etime_d[0].toString() + etime_d[1].toString()
			+ etime_d[2].toString();
	if ((stime_a + stime_b) > (etime_a + etime_b)) {
		alert("亲，结束时间不要早于开始时间哦！(*^__^*) 嘻嘻……");
		event.cancelBubble = true;
		return;
	}
	// if((stime_a)==(etime_a)){
	// var coorStime = statertime.toString().substring(0, 10);
	// var coortime=Number(stime_a = statertime.toString().substring(11, 13));
	// var coorStime_a = coorStime.split("-");
	// var coordate = new Date(coorStime_a[0], coorStime_a[1]-1,
	// coorStime_a[2]);
	// var coorday=coordate.getDay();
	// if(coorday==0){coorday=7;}
	// coordinate_id="w"+coorday+"t"+coortime;
	// }
	// else if(stime_a != etime_a){coordinate_id="tdndays";}
	var newSdateA = statertime.toString().substring(0, 10);
	var newSdateB = statertime.toString().substring(11, 19);
	var newEdateA = endtime.toString().substring(0, 10);
	var newEdateB = endtime.toString().substring(11, 19);
	var sqlUP = "update sa_psnschedule set SCAPTION='" + caption
			+ "',SPRIORITY='" + priority + "',SSTARTDATE='" + newSdateA + " "
			+ newSdateB + "',SENDDATE='" + newEdateA + " " + newEdateB
			+ "',SCONTENT='" + content + "',SSTARTDATE_AXIS=" + stime_a
			+ ",SSENDDATE_AXIS=" + etime_a + " where SID='" + affairsID + "' ";
	var result = tlv8.sqlUpdateAction("system", sqlUP);
	if (true == result.data || "true" == result.data) {
		alert("不明错误，修改失败");
	} else {
		alert("修改成功！");
	}
}
// 已完成按钮
function affairsCompleted() {
	var sqlUP = "update sa_psnschedule set SSTATUS='已完成' where SID='"
			+ affairsID + "' ";
	var result = tlv8.sqlUpdateAction("system", sqlUP);
	if (true == result.data || "true" == result.data) {
		alert("不明错误，修改失败");
	} else {
		alert("终于结束了！");
		document.getElementById("sstatus").value = "已完成";
	}
}
// 删除按钮
function affairsDelete() {
	var dataDelete = new tlv8.Data();
	dataDelete.setDbkey("system");
	dataDelete.setTable("SA_PSNSCHEDULE");
	dataDelete.setFormId("affairslooks");
	dataDelete.rowid = affairsID;
	var result = dataDelete.deleteData();
}
// function myCancal(){
// tlv8.portal.dailog.dailogCancel();
// alert("123");
// }

// function conversionpriority(num) {
// var character;
// switch(num){
// case 0: character = "普通"; break;
// case 1: character = "不重要/不紧急"; break;
// case 2: character = "不重要/紧急"; break;
// case 3: character = "重要/不紧急"; break;
// case 4: character = "重要/紧急"; break;
// }
// return character;
// }
