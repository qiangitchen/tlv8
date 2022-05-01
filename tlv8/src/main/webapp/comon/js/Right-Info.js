//创建信息层主函数
tlv8.creatRightInfoView = function(title,html,lay_title) {
	var clientWidth = document.body.clientWidth;
	var menu_body_view = "<table width=\"100%\" class=\"TableTop\">"
			+ "<tbody><tr><td class=\"left\"></td><td class=\"center\">"
			+ "<div align=\"left\">"+title+"</div></td><td class=\"right\"></td>"
			+ "</tr></tbody></table><table width=\"100%\" height=\"92%\"" +
			"class=\"TableBlock no-top-border\" style='background:#ffffff;'>"
			+ "<tbody><tr><td valing='top'>";
	menu_body_view += html;
	menu_body_view += "</td></tr></tbody></table>";
	var b_view = document.createElement("div");
	b_view.id = "Right_Menu_View";
	b_view.style.width = "200px";
	b_view.style.height = "90%";
	b_view.style.top = "1%";
	b_view.style.left = clientWidth - 205;
	b_view.style.position = "absolute";
	b_view.style.zIndex = "999";
	//b_view.style.border = "1px solid #eee";
	b_view.style.display = "none";
	//b_view.style.background = "#ffffff";
	b_view.innerHTML = menu_body_view;
	document.body.appendChild(b_view);

	var butn_html = "<table style='height:100%;'><tr><td valign='middle'>"
			+ "<a id='lay_btn_dis' href='javascript:tlv8.show_Right_Menu(true)' style='text-decoration: none;' title='"+(lay_title?lay_title:"")+"'>"
			+ "<img src='"+cpath+"/comon/image/csbar/direction.gif'/>" + "</a></td></tr></table>";
	b_view = document.createElement("div");
	b_view.id = "Right_Menu_View_Button";
	b_view.style.width = "5px";
	b_view.style.height = "150px";
	b_view.style.top = "30%";
	b_view.style.left = clientWidth - 20;
	b_view.style.position = "absolute";
	b_view.style.zIndex = "999";
	//b_view.style.border = "1px solid #eee";
	//b_view.style.background = "#ffffff";
	b_view.innerHTML = butn_html;
	document.body.appendChild(b_view);
	document.getElementById("Right_Menu_View_Button").onmouseover = function(){
		tlv8.show_Right_Menu(true);
	};
	document.body.onclick = function(event){
		event = event|| window.event;
		var objEdit = event.srcElement ? event.srcElement : event.target;
		var Right_Menu_View = document.getElementById("Right_Menu_View");
		if(Right_Menu_View != objEdit){
			tlv8.show_Right_Menu(false);
		}
	};
};

//显示/掩藏菜单
var proy,croy;
tlv8.show_Right_Menu = function(lay) {
	var clientWidth = document.body.clientWidth;
	var Right_Menu_View_Button = document
			.getElementById("Right_Menu_View_Button");
	var Right_Menu_View = document.getElementById("Right_Menu_View");
	var lay_btn_dis = document.getElementById("lay_btn_dis");
	if (lay) {
		var le_t = clientWidth - 205;
		Right_Menu_View.style.left = clientWidth;
		Right_Menu_View_Button.style.left = clientWidth - 205 - 19;
		//lay_btn_dis.href = "javascript:tlv8.show_Right_Menu(false)";
		lay_btn_dis.innerHTML = "";
		Right_Menu_View.style.display = "";
		Right_Menu_View_Button.style.display = "none";
		document.body.style.overflow = "hidden";
		proy = setInterval(function() {
				openy(Right_Menu_View, le_t);
			}, 10);
		var openy = function(o, l){
			var cy = parseInt(o.style.left);
			//alert(cy+":"+l);
			if (cy > l) {
				o.style.left = (cy - Math.ceil(l / 80)) + "px";
			} else {
				//alert(0);
				clearInterval(proy);
				document.getElementById("Right_Menu_View").focus();
				document.body.style.overflow = "auto";
			}
		};
	} else {
		Right_Menu_View_Button.style.left = clientWidth - 20;
		document.body.style.overflow = "hidden";
		croy = setInterval(function() {
				cleny(Right_Menu_View, le_t);
			}, 10);
		var cleny = function(o){
			var cy = parseInt(o.style.left);
			//alert(cy+":"+l);
			if (cy < clientWidth) {
				o.style.left = (cy + Math.ceil(clientWidth / 80)) + "px";
			} else {
				//alert(0);
				clearInterval(croy);
				Right_Menu_View.style.display = "none";
				document.body.style.overflow = "auto";
				Right_Menu_View_Button.style.display = "";
				lay_btn_dis.innerHTML = "<img src='"+cpath+"/comon/image/csbar/direction.gif'/>";
			}
		};
	}
};

tlv8.lay_sloly = function(){
	
};

//加载相关文件
try{
if (!checkPathisHave($dpcsspath + "formDetail/formDetail.css"))
	createStyleSheet($dpcsspath + "formDetail/formDetail.css");
if (!checkPathisHave(cpath+"/portal/qface/style/style.css"))
	createStyleSheet(cpath+"/portal/qface/style/style.css");
}catch(e){
}


/**
*以下为了兼容云捷代码
*/
justep.yn = tlv8;