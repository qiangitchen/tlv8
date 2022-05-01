var map;
function addMenu() {
	var div = document.createElement("div");
	div.appendChild(document.createTextNode("打卡"));
	div.style.cursor = "pointer";
	div.style.border = "1px solid gray";
	div.style.backgroundColor = "white";
	div.style.padding = "10px";
	div.style.borderRadius = "5px";
	div.onclick = function(e) {
		history.go(-1);
		return false;
	};
	map.getContainer().appendChild(div);
}

function creatMaplay() {
	alert(1);
	map = new BMap.Map("container"); // 创建地图实例
	map.addControl(new BMap.NavigationControl());// 地图平移缩放控件
	map.addControl(new BMap.ScaleControl());// 比例尺控件
	map.addControl(new BMap.OverviewMapControl());// 缩略地图控件，默认位于地图右下方
	map.addControl(new BMap.MapTypeControl());// 地图类型控件，默认位于地图右上方
	map.enableScrollWheelZoom(); // 启用滚轮放大缩小，默认禁用
	map.enableScrollWheelZoom(true);// 允许缩放
	map.enableContinuousZoom(); // 启用地图惯性拖拽，默认禁用
	function myFun(result) {
		var cityName = result.name;
		map.setCenter(cityName);
	}
	var myCity = new BMap.LocalCity();// IP定位获取当前城市
	myCity.get(myFun);
	var mylocation = new MyLocation();
	map.addControl(mylocation); // 添加按钮
}

function pageInit() {
	var point = new BMap.Point(102.695675, 25.021476);
	map.centerAndZoom(point, 12);// 设置中心位置
}

$(document).ready(function() {
	creatMaplay();
	pageInit();
});