function viewimg() {
	var imgup = document.getElementById("upload");
	var imgpath = getPath(imgup);
	// 判断是否是图片格式
	var imgname = imgup.value.substring(imgup.value.lastIndexOf("."),
			imgup.value.length)
	imgname = imgname.toLowerCase()
	if ((imgname != '.jpg') && (imgname != '.gif') && (imgname != '.jpeg')
			&& (imgname != '.png') && (imgname != '.bmp')) {
		alert("请选择图片文件，谢谢!");
		imgup.focus();
		// 清空file里面的值
		imgup.select();
		document.selection.clear();
		document.getElementById("sig_preview").innerHTML = "";
	} else {
		// 显示图片
		document.getElementById("sig_preview").innerHTML = "<img src='"
				+ imgpath + "' style='border:0; width:200px; height:100px'>"
	}

}

function getPath(obj) {
	if (obj) {
		if (window.navigator.userAgent.indexOf("MSIE") >= 1) {
			obj.select();
			return document.selection.createRange().text;
		} else if (window.navigator.userAgent.indexOf("Firefox") >= 1) {
			if (obj.files) {
				return obj.files.item(0).getAsDataURL();
			}
			return obj.value;
		}
		return obj.value;
	}
}