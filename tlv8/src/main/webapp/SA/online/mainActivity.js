var onlineGrid;

function pageInit() {
	var bardiv = document.getElementById("stander-bar");
	var toobar = new tlv8.toolbar(bardiv, false, false, false, true);
	$(
			"<td><span style='border:1px solid #dfe8f6;'> 当前在线人数：<span id='onlineCount'> <span></span></td>")
			.insertAfter($("#stander-barrefresh-item-tr"));

	onlineGrid = new StaticGrid("executorList"); // 实例化类
	var gridTop = [ {
		id : 'userid',
		name : '登陆者ID',
		width : 0
	}, {
		id : 'username',
		name : '登陆者',
		width : 100
	}, {
		id : 'userfullid',
		name : '登陆者FID',
		width : 0
	}, {
		id : 'userfullname',
		name : '登陆者全称',
		width : 160
	}, {
		id : 'loginIP',
		name : '登陆IP',
		width : 120
	}, {
		id : 'loginDate',
		name : '登陆时间',
		width : 0
	}, {
		id : 'sessionID',
		name : 'sessionID',
		width : 180
	} ];
	onlineGrid.init(gridTop); // 初始化
	loadData();
	// onlineGrid.setRowDbclick(staticGridDbclick); // 设置行双击事件
}

function loadData() {
	onlineGrid.clearData();
	tlv8.XMLHttpRequest("system/User/getOnlineUserInfo", null, "POST",
			true, function(re) {
				if (typeof re == "string") {
					re = window.eval("(" + re + ")");
				}
				re = re[0];
				var data;
				if (re.status == "SUCCESS") {
					data = re.data;
				}
				if (typeof data == "string") {
					data = window.eval("(" + data + ")");
				}
				data = data.data;
				$("#onlineCount").text(data.length);
				onlineGrid.initData(data);
			}, true);
}

function refreshMainData() {
	loadData();
}