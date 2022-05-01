/*页面载入*/
var onloadrun = function(v) {
	document.getElementById("affairsLoad").innerHTML = "";
	if (v == "day") {
		document.getElementById("a_week").style.color = "black";
		document.getElementById("a_day").style.color = "red";
		getDayData("now");
	}
	if (v == "week") {
		getWeekData("now");
		document.getElementById("a_day").style.color = "black";
		document.getElementById("a_week").style.color = "red";
	}
	setTableData();
};

/* 为表格装载数据 */
var loaddate;// 页面载入日期毫秒
var loadMondaydate;// 页面载入星期一日期毫秒
var setTableData = function() {
	//var jq = jQuery.noConflict();
	for (var i = 0; i < 1; i++) {
		try {
			var personID = weekData.data[i].id, personName = weekData.data[i].name;
		} catch (e) {
			return;
		}
		var nDays = weekData.data[i].nDays;
		var nDaytxt = "";
		/* =====================跨天*********************************** */
		var nDayHtml = document.getElementById("affairsLoad");
		for (var j = 0; j < nDays.length; j++) {
			try {
				var sid = nDays[j].ID;
				var caption = nDays[j].CAPTION;
				var startdate = nDays[j].STARTDATE;
				var enddate = nDays[j].ENDDATE;
				var content = nDays[j].CONTENT;
				var priority = nDays[j].PRIORITY;
				var status = nDays[j].STATUS;
				var affairstype = nDays[j].AFFAIRSTYPE;
				var statusColor = "";

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

				if (!status || status == null || status == "null") {
					if (nowDatetimeNum > etime) {
						status = "已过期";
					}
					if (nowDatetimeNum > stime && nowDatetimeNum < etime) {
						status = "进行中";
					}
					if (nowDatetimeNum < stime) {
						status = "未开始";
					}
				}

				if (status == "已完成") {
					statusColor = "#3CB371";
				} else if (status == "已过期") {
					statusColor = "red";
				} else if (status == "进行中") {
					statusColor = "#0000FF";
				} else if (status == "未开始") {
					statusColor = "#B5005A";
				}
				var Priority = convertPriority(priority);// 优先级数字转为文字
				var priorityColor;
				if (priority == 0) {
					priorityColor = "#000000";
				} else if (priority == 1) {
					priorityColor = "#6F7274";
				} else if (priority == 2) {
					priorityColor = "#00AA00";
				} else if (priority == 3) {
					priorityColor = "#FF9933";
				} else if (priority == 4) {
					priorityColor = "#FF0000";
				}
				var title = "";
				if (affairstype == 3) {
					title = "周期" + "(" + Priority + "/" + status + ")";
				} else if (affairstype == 2) {
					title = "跨天" + "(" + Priority + "/" + status + ")";
				}

				nDaytxt = nDaytxt
						+ "<div title="
						+ title
						+ " style=\"line-height:1.5; margin-top:1; border:0 solid #CDCDCD;background-color:#FFFFFF;\" id='div"
						+ sid
						+ "'>&nbsp;"
						+ "<span class='symbol' width='10px'>&nbsp;&nbsp;&nbsp;</span>&nbsp;<a style=\"text-decoration:'none'; \" href='javascript:void(0);' "
						+ "sstartdate='" + startdate + "' senddate='" + enddate
						+ "' scaption='" + caption + "' sid='" + sid
						+ "' sstatus='" + status + "' scontent='" + content
						+ "' spriority='" + priority
						+ "' onclick='message_affairsLooks(this)'>"
						+ startdate.substring(0, 16) + " - "
						+ enddate.substring(0, 16) + " " + "<font color="
						+ statusColor + ">" + caption + "</font></a></div>";
				nDayHtml.innerHTML = nDayHtml.innerHTML + nDaytxt;
				nDaytxt = "";
			} catch (e) {
				continue;
			}
		}

		/* ====================单天====================== */
		var singleDaytxt = "";
		var singleDay = weekData.data[i].singleDay;
		for (var k = 0; k < singleDay.length; k++) {
			try {
				var sid = singleDay[k].ID, caption = singleDay[k].CAPTION, startdate = singleDay[k].STARTDATE, enddate = singleDay[k].ENDDATE, content = singleDay[k].CONTENT, priority = singleDay[k].PRIORITY, status = singleDay[k].STATUS;
				var statusColor = "";
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

				if (!status || status == null || status == "null") {
					if (nowDatetimeNum > etime) {
						status = "已过期";
					}
					if (nowDatetimeNum > stime && nowDatetimeNum < etime) {
						status = "进行中";
					}
					if (nowDatetimeNum < stime) {
						status = "未开始";
					}
				}

				if (status == "已完成") {
					statusColor = "#3CB371";
				} else if (status == "已过期") {
					statusColor = "red";
				} else if (status == "进行中") {
					statusColor = "#0000FF";
				} else if (status == "未开始") {
					statusColor = "#B5005A";
				}
				var Priority = convertPriority(priority);// 优先级数字转为文字
				var priorityColor;
				if (priority == 0) {
					priorityColor = "#000000";
				} else if (priority == 1) {
					priorityColor = "#6F7274";
				} else if (priority == 2) {
					priorityColor = "#00AA00";
				} else if (priority == 3) {
					priorityColor = "#FF9933";
				} else if (priority == 4) {
					priorityColor = "#FF0000";
				}
				var title = "单天" + "(" + Priority + "/" + status + ")";
				singleDaytxt = singleDaytxt
						+ "<div title="
						+ title
						+ " style=\"line-height:1.5;margin-top:1; border:0 solid #CDCDCD;background-color:#FFFFFF;\" id='div"
						+ sid
						+ "'>&nbsp;"
						+ "<span class='symbol' width='10px'>&nbsp;&nbsp;&nbsp;</span>&nbsp;<a style=\"text-decoration:'none';\" href='javascript:void(0);' "
						+ "sstartdate='" + startdate + "' senddate='" + enddate
						+ "' scaption='" + caption + "' sid='" + sid
						+ "' sstatus='" + status + "' scontent='" + content
						+ "' spriority='" + priority
						+ "' onclick='message_affairsLooks(this)'>"
						+ startdate.substring(0, 16) + " - "
						+ enddate.substring(0, 16) + " " + "<font color="
						+ statusColor + ">" + caption + "</font></a></div>";
				var coordinateNumA = startdate.substring(0, 10).split("-");
				var coordinateDate = new Date(coordinateNumA[0],
						coordinateNumA[1] - 1, coordinateNumA[2]);
				var coordinateDay = coordinateDate.getDay();
				if (coordinateDay == 0) {
					coordinateDay = 7;
				}
				var coordinate = "affairsLoad";
				var singleDayHtml = document.getElementById(coordinate);
				singleDaytxt = singleDayHtml.innerHTML + singleDaytxt;
				singleDayHtml.innerHTML = singleDaytxt;
				singleDaytxt = "";
			} catch (e) {
				continue;
			}
		}
	}
};

// 将星期几数字转为中文
function conversionweek(day) {
	var dayweek;
	switch (day) {
	case 0:
		dayweek = "星期日";
		break;
	case 1:
		dayweek = "星期一";
		break;
	case 2:
		dayweek = "星期二";
		break;
	case 3:
		dayweek = "星期三";
		break;
	case 4:
		dayweek = "星期四";
		break;
	case 5:
		dayweek = "星期五";
		break;
	case 6:
		dayweek = "星期六";
		break;
	}
	return dayweek;
}
/* 将优先级数字转为文字 */
var convertPriority = function(num) {
	var character;
	num = Number(num);
	switch (num) {
	case 0:
		character = "普通";
		break;
	case 1:
		character = "不重要/不紧急";
		break;
	case 2:
		character = "不重要/紧急";
		break;
	case 3:
		character = "重要/不紧急";
		break;
	case 4:
		character = "重要/紧急";
		break;
	}
	return character;
};

/* ============================构建今天数据============构建今天数据================ */
var loaddate;// 页面载入日期毫秒
var loadMondaydate;// 页面载入星期一日期毫秒
var getDayData = function(v) {
	var needDate = new Date();
	if (v != "now") {
		var dateArray = v.split("-");
		needDate.setFullYear(dateArray[0], dateArray[1] - 1, dateArray[2]);
	}
	var needFirstnum = needDate.getFullYear();// 得到年份
	var needSecondnum = needDate.getMonth() + 1;// 得到月份
	if (needSecondnum < 10) {
		needSecondnum = "0" + needSecondnum.toString();
	}
	var needThirdnum = needDate.getDate();// 得到日期
	if (needThirdnum < 10) {
		needThirdnum = "0" + needThirdnum.toString();
	}
	var needNum = needFirstnum.toString() + needSecondnum.toString()
			+ needThirdnum.toString();// 时间轴数字

	weekData.data = [];
	var tempData = new Array();
	var nDaysTempData = new Array();
	var dbkey = "system";
	var depPersonSql = "SELECT distinct sa_psnschedule.SWHOUSER ID,sa_opperson.SNAME NAME FROM "
			+ "sa_psnschedule ,sa_opperson "
			+ "WHERE sa_psnschedule.SWHOUSER = sa_opperson.SID and sa_psnschedule.SWHOUSER='"
			+ tlv8.Context.getCurrentPersonID() + "'";
	var depPerson = tlv8.sqlQueryActionforJson(dbkey, depPersonSql);/* 人员ID、姓名 */
	for (var i = 0; i < depPerson.data.length; i++) {
		var nowID = depPerson.data[i].ID;
		var nowName = depPerson.data[i].NAME;
		/* 跨天数据 */
		var nDaysDataSql = "SELECT sa_psnschedule.SID ID,"
				+ "sa_psnschedule.SCAPTION CAPTION,"
				+ "sa_psnschedule.SSTARTDATE STARTDATE,"
				+ "sa_psnschedule.SENDDATE ENDDATE,"
				+ "sa_psnschedule.SCONTENT CONTENT,"
				+ "sa_psnschedule.SPRIORITY PRIORITY,"
				+ "sa_psnschedule.SSTATUS STATUS,"
				+ "sa_psnschedule.SSTARTDATE_AXIS STARTDATE_AXIS,"
				+ "sa_psnschedule.SSENDDATE_AXIS ENDDATE_AXIS,"
				+ "sa_psnschedule.SWHOUSER WHOUSER,sa_psnschedule.SAFFAIRSTYPE AFFAIRSTYPE,"
				+ "sa_opperson.SNAME NAME FROM "
				+ "sa_psnschedule ,sa_opperson "
				+ "WHERE sa_psnschedule.SWHOUSER='"
				+ nowID
				+ "' AND SSENDDATE_AXIS>="
				+ needNum
				+ " AND SSTARTDATE_AXIS<="
				+ needNum
				+ " AND sa_psnschedule.SWHOUSER = sa_opperson.SID "
				+ " AND sa_psnschedule.SSTARTDATE_AXIS!=sa_psnschedule.SSENDDATE_AXIS "
				+ " ORDER BY sa_psnschedule.SSTARTDATE ASC limit 0,5";
		var nDays = tlv8.sqlQueryActionforJson(dbkey, nDaysDataSql);
		// if(!nDays)nDays = [];
		/* 单天数据 */
		var singleDayDataSql = "SELECT sa_psnschedule.SID ID,"
				+ "sa_psnschedule.SCAPTION CAPTION,"
				+ "sa_psnschedule.SSTARTDATE STARTDATE,"
				+ "sa_psnschedule.SENDDATE ENDDATE,"
				+ "sa_psnschedule.SCONTENT CONTENT,"
				+ "sa_psnschedule.SPRIORITY PRIORITY,"
				+ "sa_psnschedule.SSTATUS STATUS,"
				+ "sa_psnschedule.SSTARTDATE_AXIS STARTDATE_AXIS,"
				+ "sa_psnschedule.SSENDDATE_AXIS SENDDATE_AXIS,"
				+ "sa_psnschedule.SWHOUSER WHOUSER,"
				+ "sa_opperson.SNAME NAME FROM "
				+ "sa_psnschedule ,sa_opperson "
				+ "WHERE sa_psnschedule.SWHOUSER='"
				+ nowID
				+ "' AND SSTARTDATE_AXIS>="
				+ needNum
				+ " AND SSENDDATE_AXIS<="
				+ needNum
				+ " AND sa_psnschedule.SWHOUSER = sa_opperson.SID AND sa_psnschedule.SSTARTDATE_AXIS=sa_psnschedule.SSENDDATE_AXIS "
				+ " AND sa_psnschedule.SAFFAIRSTYPE=1"
				+ " ORDER BY sa_psnschedule.SSTARTDATE ASC limit 0,5";
		var singleDay = tlv8
				.sqlQueryActionforJson(dbkey, singleDayDataSql);

		var temp = {
			id : nowID,
			name : nowName,
			nDays : nDays.data,
			singleDay : singleDay.data
		};
		tempData.push(temp);
	}
	weekData.data = tempData;
};

/* ============================构建本周数据============构建本周数据================ */
var getWeekData = function(parameter) {
	var myDate = new Date();
	if (parameter != "now") {
		var dateArray = parameter.split("-");
		myDate.setFullYear(dateArray[0], dateArray[1] - 1, dateArray[2]);
	}
	var week = myDate.getDay(); // 获取当前星期X(0-6,0代表星期天)
	var milliseconds = myDate.getTime(); // 获取当前时间(从1970.1.1开始的毫秒数)
	loaddate = myDate.getTime();
	if (week == 0) {
		week = 7;
	}
	var gap = week - 1;// 与周一相差天数
	if (gap != 0) {
		milliseconds = milliseconds - (86400000 * gap);
	}
	var mondaydate = new Date(milliseconds);// 星期一日期
	loadMondaydate = mondaydate.getTime();
	var sundaydate = new Date((milliseconds + 86400000 * 6));// 星期天日期
	// ↓计算周一时间轴
	var mFirstnum = mondaydate.getFullYear();// 得到周一年份
	var mSecondnum = mondaydate.getMonth() + 1;// 得到周一月份
	if (mSecondnum < 10) {
		mSecondnum = "0" + mSecondnum.toString();
	}
	var mThirdnum = mondaydate.getDate();// 得到周一日期
	if (mThirdnum < 10) {
		mThirdnum = "0" + mThirdnum.toString();
	}
	var mNum = mFirstnum.toString() + mSecondnum.toString()
			+ mThirdnum.toString();// 周一时间轴数字
	// ↓计算周日时间轴
	var sFirstnum = sundaydate.getFullYear();// 得到周日年份
	var sSecondnum = sundaydate.getMonth() + 1;// 得到周日月份
	if (sSecondnum < 10) {
		sSecondnum = "0" + sSecondnum.toString();
	}
	var sThirdnum = sundaydate.getDate();// 得到周日日期
	if (sThirdnum < 10) {
		sThirdnum = "0" + sThirdnum.toString();
	}
	var sNum = sFirstnum.toString() + sSecondnum.toString()
			+ sThirdnum.toString();// 周日时间轴数字
	weekData.data = [];
	var tempData = new Array();
	var nDaysTempData = new Array();
	var dbkey = "system";

	var depPersonSql = "SELECT distinct sa_psnschedule.SWHOUSER ID,sa_opperson.SNAME NAME FROM "
			+ "sa_psnschedule ,sa_opperson "
			+ "WHERE sa_psnschedule.SWHOUSER = sa_opperson.SID and sa_psnschedule.SWHOUSER='"
			+ tlv8.Context.getCurrentPersonID() + "'";
	var depPerson = tlv8.sqlQueryActionforJson(dbkey, depPersonSql);/* ID、姓名 */
	for (var i = 0; i < 1; i++) {
		try {
			var nowID = depPerson.data[i].ID;
			var nowName = depPerson.data[i].NAME;
		} catch (e) {
			break;
		}
		/* 跨天数据 */
		var nDaysDataSql = "SELECT sa_psnschedule.SID ID,"
				+ "sa_psnschedule.SCAPTION CAPTION,"
				+ "sa_psnschedule.SSTARTDATE STARTDATE,"
				+ "sa_psnschedule.SENDDATE ENDDATE,"
				+ "sa_psnschedule.SCONTENT CONTENT,"
				+ "sa_psnschedule.SPRIORITY PRIORITY,"
				+ "sa_psnschedule.SSTATUS STATUS,"
				+ "sa_psnschedule.SSTARTDATE_AXIS STARTDATE_AXIS,"
				+ "sa_psnschedule.SSENDDATE_AXIS ENDDATE_AXIS,"
				+ "sa_psnschedule.SWHOUSER WHOUSER,sa_psnschedule.SAFFAIRSTYPE AFFAIRSTYPE,"
				+ "sa_opperson.SNAME NAME FROM "
				+ "sa_psnschedule ,sa_opperson "
				+ "WHERE sa_psnschedule.SWHOUSER='"
				+ nowID
				+ "' AND SSENDDATE_AXIS>="
				+ mNum
				+ " AND SSTARTDATE_AXIS<="
				+ sNum
				+ " AND sa_psnschedule.SWHOUSER = sa_opperson.SID AND sa_psnschedule.SSTARTDATE_AXIS!=sa_psnschedule.SSENDDATE_AXIS "
				+ " ORDER BY sa_psnschedule.SSTARTDATE ASC limit 0,5";
		var nDays = tlv8.sqlQueryActionforJson(dbkey, nDaysDataSql);
		// if(!nDays)nDays = [];
		/* 单天数据 */
		var singleDayDataSql = "SELECT sa_psnschedule.SID ID,"
				+ "sa_psnschedule.SCAPTION CAPTION,"
				+ "sa_psnschedule.SSTARTDATE STARTDATE,"
				+ "sa_psnschedule.SENDDATE ENDDATE,"
				+ "sa_psnschedule.SCONTENT CONTENT,"
				+ "sa_psnschedule.SPRIORITY PRIORITY,"
				+ "sa_psnschedule.SSTATUS STATUS,"
				+ "sa_psnschedule.SSTARTDATE_AXIS STARTDATE_AXIS,"
				+ "sa_psnschedule.SSENDDATE_AXIS SENDDATE_AXIS,"
				+ "sa_psnschedule.SWHOUSER WHOUSER,"
				+ "sa_opperson.SNAME NAME FROM "
				+ "sa_psnschedule,sa_opperson "
				+ " WHERE sa_psnschedule.SWHOUSER='"
				+ nowID
				+ "' AND SSTARTDATE_AXIS>="
				+ mNum
				+ " AND SSENDDATE_AXIS<="
				+ sNum
				+ " AND sa_psnschedule.SWHOUSER = sa_opperson.SID "
				+ " AND sa_psnschedule.SSTARTDATE_AXIS=sa_psnschedule.SSENDDATE_AXIS "
				+ " AND sa_psnschedule.SAFFAIRSTYPE=1 "
				+ " ORDER BY sa_psnschedule.SSTARTDATE ASC limit 0,5";
		var singleDay = tlv8
				.sqlQueryActionforJson(dbkey, singleDayDataSql);

		var temp = {
			id : nowID,
			name : nowName,
			nDays : nDays.data,
			singleDay : singleDay.data
		};
		tempData.push(temp);
	}
	// debugger;
	weekData.data = tempData;
};

// 查看事务务对话框
function message_affairsLooks(obj) {
	//var jq = jQuery.noConflict();
	//obj$ = jq(obj);
	var cid = encodeURIComponent($(obj).attr("sid"));
	var cstartdate = encodeURIComponent($(obj).attr("sstartdate"));
	var cenddate = encodeURIComponent($(obj).attr("senddate"));
	var ccaption = encodeURIComponent($(obj).attr("scaption"));
	var cstatus = encodeURIComponent($(obj).attr("sstatus"));
	var ccontent = encodeURIComponent($(obj).attr("scontent"));
	var cpriority = encodeURIComponent($(obj).attr("spriority"));
	tlv8.portal.openWindow("事务详情",
			"/SA/personal/schedule/affairsLooks.html?sid=" + cid
					+ "&scaption=" + ccaption + "&sstartdate=" + cstartdate
					+ "&senddate=" + cenddate + "&spriority=" + cpriority
					+ "&sstatus=" + cstatus + "&scontent=" + ccontent);
	event.cancelBubble = true;// 阻止触发事件
}
function callbackAffairsLooks() {// 回调函数
// myTaskLoad();
}
// 转到个人事务管理页面
function gotoSchedule() {
	tlv8.portal.openWindow("日程安排",
			"/SA/personal/schedule/mainActivity.html");
}

var weekData = {
	/* 表头 */
	headerhtml : "<table id='tbweek' width='100%' border='1' align='center' cellpadding='0' cellspacing='1' bordercolor='#88C4FF' bgcolor='#49A9DA'>"
			+ "<tr align='center' valign='middle' style='color: #FFFFCC'>"
			+ "<td width='100' height='41' style=\"font-family: '宋体';font-size:14;\">姓  名</td>"
			+ "<td width='170' id='tdweek1'><font color='#0000CC'>星期一</font></td>"
			+ "<td width='170' id='tdweek2'><font color='#33CC00'>星期二</font></td>"
			+ "<td width='170' id='tdweek3'>星期三</td>"
			+ "<td width='170' id='tdweek4'>星期四</td>"
			+ "<td width='170' id='tdweek5'>星期五</td>"
			+ "<td width='170' id='tdweek6'>星期六</td>"
			+ "<td width='170' id='tdweek7'>星期日</td>	</tr></table>",
	data : [ {
		id : "PSN01",
		name : "system", // s.data[0].schedule.xingqiyi
		nDays : [ {
			id : "BA7489204B384E1FBCE0E493822841F9",
			caption : "今天的事1",// 标题
			startdate : "2012-03-26 07:00:00",// 开始日期时间
			enddate : "2012-04-01 07:59:00",// 结束日期时间
			content : "第一件事",// 内容
			priority : "4",// 优先级别
			status : "进行中"// 状态
		}, {
			id : "AAE10229CE36419FBF6131C424AB1951",
			caption : "今天的事2",// 标题
			startdate : "2012-03-27 08:00:00",// 开始日期时间
			enddate : "2012-03-30 22:59:00",// 结束日期时间
			content : "第二件事",// 内容
			priority : "0",
			status : "进行中"
		} ],
		singleDay : [ {
			id : "BA2389204B384E1FBCE0E493822841F9",
			caption : "今天的事1",// 标题
			startdate : "2012-03-27 07:00:00",// 开始日期时间
			enddate : "2012-03-27 07:59:00",// 结束日期时间
			content : "第1件事",// 内容
			priority : "0",// 优先级别
			status : "进行中"// 状态
		}, {
			id : "AAE15529CE36419FBF6131C424AB1951",
			caption : "今天的事2",// 标题
			startdate : "2012-03-28 08:00:00",// 开始日期时间
			enddate : "2012-03-28 22:59:00",// 结束日期时间
			content : "第2件事",// 内容
			priority : "0",
			status : "进行中"
		}, {
			id : "AAE16529CE36419FBF6131C424AB1951",
			caption : "今天的事3",// 标题
			startdate : "2012-03-29 08:00:00",// 开始日期时间
			enddate : "2012-03-29 22:59:00",// 结束日期时间
			content : "第3件事",// 内容
			priority : "0",
			status : "进行中"
		} ]

	}, {
		id : "PSN02",
		name : "企掌柜", // s.data[0].schedule.xingqiyi
		nDays : [ {
			id : "AA7489204B384E1FBCE0E493822841F9",
			caption : "今天的事1",// 标题
			startdate : "2012-03-27 07:00:00",// 开始日期时间
			enddate : "2012-04-01 07:59:00",// 结束日期时间
			content : "第一件事",// 内容
			priority : "4",// 优先级别
			status : "进行中"// 状态
		}, {
			id : "BAE10229CE36419FBF6131C424AB1951",
			caption : "今天的事2",// 标题
			startdate : "2012-03-28 08:00:00",// 开始日期时间
			enddate : "2012-03-30 22:59:00",// 结束日期时间
			content : "第二件事",// 内容
			priority : "0",
			status : "进行中"
		} ],
		singleDay : [ {
			id : "CA2389204B384E1FBCE0E493822841F9",
			caption : "今天的事1",// 标题
			startdate : "2012-03-27 07:00:00",// 开始日期时间
			enddate : "2012-03-27 07:59:00",// 结束日期时间
			content : "第1件事",// 内容
			priority : "0",// 优先级别
			status : "进行中"// 状态
		}, {
			id : "DAE15529CE36419FBF6131C424AB1951",
			caption : "今天的事2",// 标题
			startdate : "2012-03-28 08:00:00",// 开始日期时间
			enddate : "2012-03-28 22:59:00",// 结束日期时间
			content : "第2件事",// 内容
			priority : "0",
			status : "进行中"
		}, {
			id : "EAE16529CE36419FBF6131C424AB1951",
			caption : "今天的事3",// 标题
			startdate : "2012-03-29 08:00:00",// 开始日期时间
			enddate : "2012-03-29 22:59:00",// 结束日期时间
			content : "第3件事",// 内容
			priority : "0",
			status : "进行中"
		} ]
	} ]
};
