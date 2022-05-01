/*
 * 加载菜单
 */
function loadMenu(contentviwid) {
	$.ajax({
		type : "post",
		async : true,
		url : cpath+"/getmobileFuncTree",
		success : function(result, textStatus) {
			console.log(result.data);
			if (result.status = "SUCCESS") {
				var menusdatas = result.data.childNodes[0];
				createPoMenus(menusdatas.childNodes);
			}
		},
		error : function() {
			// 请求出错处理
		}
	});
	loadTaskCount();
	// loadWaitCount();
}

function createPoMenus(menusdatas) {
	if (typeof menusdatas == "string") {
		menusdatas = window.eval("(" + menusdatas + ")");
	}
	var muhtml = "<ul class=\"mui-table-view mui-grid-view mui-grid-9\">";
	for (var i = 0; i < menusdatas.length; i++) {
		var mudat = menusdatas[i];
		muhtml += "<li class=\"mui-table-view-cell mui-media mui-col-xs-4 mui-col-sm-4\">";
		muhtml += "<div class=\"MenuItem\">";
		muhtml += "<div class=\"countTixview_un\" id=\"empty_" + mudat.id
				+ "\"></div>";
		muhtml += "<a data='item' href='" +cpath+"/"+ mudat.url + "' id='" + mudat.id
				+ "' rel='external'> <img src='"
				+ mudat.icon + "'> <p style='line-height:30px;color:#000;'>" + mudat.label + "</p></a>";
		muhtml += "</div></li>";
	}
	muhtml += "</ul>";
	$("#tabbar-contenView").html(muhtml);
	$(".MenuItem").corner();
//	loadWaitCount();
}

function loadTaskCount(){
	$.ajax({
		type : "post",
		async : true,
		url : cpath+"/getWaitTaskAction",
		dataType:"json",
		success : function(result, textStatus) {
			if (result.count>0) {
				$("#taskcount").text(result.count);
				$("#taskcount").show();
			}else{
				$("#taskcount").hide();
			}
		},
		error : function() {
			// 请求出错处理
		}
	});
}

$(document).ready(function() {
	tlv8.checkLogin();
	if (!tlv8.UserInfo.inited) {
		tlv8.UserInfo.init();
		//loadWaitCount();
	}
	loadMenu("contenView");
});

var offCount = 0;
function loadWaitCount() {
	var re = tlv8.XMLHttpRequest("getPortalCountAction", null, "post");
	if (re.data.flag == "true") {
		var taskcount = re.task_count;// 代办
		var maicount = re.mail_count;// 邮件
		var wpcount = re.wp_count;// 周计划
		var mpcount = re.mp_count;// 月计划
		var drcount = re.dr_count;// 日报
		var wrcount = re.wr_count;// 周报
		var mrcount = re.mr_count;// 月报
		var noticecount = re.notice_count;// 月报
		if (taskcount > 0) {
			$("#empty_waittask").remove();
			// $("<div class='countTixview'>" + taskcount + "</div>")
			// .insertBefore($("#waittask"));
			$("#waittask").parent().find(".countTixview_un").text(taskcount)
					.attr("class", "countTixview");
		}
		if (maicount > 0) {
			$("#empty_waitemail").remove();
			// $("<div class='countTixview'>" + maicount +
			// "</div>").insertBefore(
			// $("#waitemail"));
			$("#waitemail").parent().find(".countTixview_un").text(maicount)
					.attr("class", "countTixview");
		}
		if (wpcount > 0) {
			$("#empty_weekplan").remove();
			// $("<div class='countTixview'>" + wpcount +
			// "</div>").insertBefore(
			// $("#weekplan"));
			$("#weekplan").parent().find(".countTixview_un").text(wpcount)
					.attr("class", "countTixview");
		}
		if (mpcount > 0) {
			$("#empty_monplan").remove();
			// $("<div class='countTixview'>" + mpcount +
			// "</div>").insertBefore(
			// $("#monthplan"));
			$("#monthplan").parent().find(".countTixview_un").text(mpcount)
					.attr("class", "countTixview");
		}
		if (drcount > 0) {
			$("#empty_day_report").remove();
			// $("<div class='countTixview'>" + drcount +
			// "</div>").insertBefore(
			// $("#day_report"));
			$("#day_report").parent().find(".countTixview_un").text(drcount)
					.attr("class", "countTixview");
		}
		if (wrcount > 0) {
			$("#empty_week_report").remove();
			// $("<div class='countTixview'>" + wrcount +
			// "</div>").insertBefore(
			// $("#week_report"));
			$("#week_report").parent().find(".countTixview_un").text(wrcount)
					.attr("class", "countTixview");
		}
		if (mrcount > 0) {
			$("#empty_month_report").remove();
			// $("<div class='countTixview'>" + mrcount +
			// "</div>").insertBefore(
			// $("#month_report"));
			$("#month_report").parent().find(".countTixview_un").text(mrcount)
					.attr("class", "countTixview");
		}
		if (noticecount > 0) {
			$("#empty_notice").remove();
			// $("<div class='countTixview'>" + noticecount + "</div>")
			// .insertBefore($("#waitNotice"));
			$("#waitNotice").parent().find(".countTixview_un")
					.text(noticecount).attr("class", "countTixview");
		}
	}
}
