//选择"服务器属性"事件
function tab_MainForm() {
	tlv8.XMLHttpRequest("/monitor/getServicesProperty", null, "post",
			true, function(result) {
				$("#MainInfo").html(result);
			});
}

// 选择"服务器CPU信息"事件
function tab_CPUInfo() {
	tlv8.XMLHttpRequest("/monitor/getServicesCPU", null, "post", true,
			function(result) {
				$("#CPUInfo").html(result);
			});
}

// 选择"服务器内存信息"事件
function tab_MemoryInfo() {
	tlv8.XMLHttpRequest("/monitor/getServicesMemory", null, "post", true,
			function(result) {
				$("#MemoryInfo").html(result);
			});
}

// 选择"服务器硬盘信息"事件
function tab_FilesystemInfo() {
	tlv8.XMLHttpRequest("/monitor/getServicesFilesystem", null, "post",
			true, function(result) {
				$("#FilesystemInfo").html(result);
			});
}

// 选择"服务器网络信息"事件
function tab_NetInfo() {
	tlv8.XMLHttpRequest("/monitor/getServicesNet", null, "post", true,
			function(result) {
				$("#NetInfo").html(result);
			});
}

// 选择"服务器EtherNet信息"事件
function tab_EtherNetInfo() {
	tlv8.XMLHttpRequest("/monitor/getServicesEthNet", null, "post", true,
			function(result) {
				$("#EtherNetInfo").html(result);
			});
}

function tabsselected(title, index) {
	switch (index) {
	case 0:
		tab_MainForm();
		break;
	case 1:
		tab_CPUInfo();
		break;
	case 2:
		tab_MemoryInfo();
		break;
	case 3:
		tab_FilesystemInfo();
		break;
	case 4:
		tab_NetInfo();
		break;
	case 5:
		tab_EtherNetInfo();
		break;
	default:
		break;
	}
}