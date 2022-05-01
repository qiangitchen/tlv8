//频率 
var frequency = 50;
// 步长
var step = 3;
// 背景颜色
var loadingBgcolor = "#ffffff";
// 宽度
var loadingWidth = 320;

/*
 * 参数说明: content：显示内容，可以为空； imageURL：将引用JS文件的路径设置即可； left：进度条显示位置left
 * top：进度条显示位置top
 */
function Loading(content, imageURL, left, top) {
	imageURL = imageURL + "status.png";
	LoadTable(content, imageURL, left, top);
	showimage.style.display = "";
	window.setInterval("RefAct();", frequency);
}

function RefAct() {
	var imgAct = document.getElementById("imgAct");
	imgAct.width += step;
	if (imgAct.width > loadingWidth - 4) {
		imgAct.width = loadingWidth;
	}
}

function LoadTable(content, imageURL, left, top) {
	var strLoading;
	strLoading = "";
	strLoading += "<div id=\"showimage\" style=\"DISPLAY:none;Z-INDEX:100;LEFT:"
			+ left
			+ "px;POSITION:absolute;TOP:"
			+ top
			+ "px;\" align=\"center\">";
	strLoading += "<TABLE id=\"Table1\" cellSpacing=\"0\" cellPadding=\"0\" width=\""
			+ loadingWidth
			+ "\" height='30px' border=\"0\" bgcolor=\""
			+ loadingBgcolor
			+ "\">";
	if (content != "") {
		strLoading += "<tr>";
		strLoading += "<td align=\"center\" height='10px'>";
		strLoading += "<font size=\"4\" face=\"Courier New, Courier, mono\"><strong>"
				+ content + "</strong></font>";
		strLoading += "</td>";
		strLoading += "</tr>";
		strLoading += "<TR>";
	}
	strLoading += "<TD class=\"Loading\" height=\"20\">";
	strLoading += "<IMG id=\"imgAct\" height=\"20\" alt=\"\" src=\"" + imageURL
			+ "\" width=\"0\">";
	strLoading += "</TD>";
	strLoading += "</TR>";
	strLoading += "</TABLE>";
	strLoading += "</div>";

	document.getElementById("loading_div").innerHTML = strLoading;
}