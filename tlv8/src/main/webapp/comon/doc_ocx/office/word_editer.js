
function insertValue() {
	var wordApp = exprotWord("http://localhost:8080/tlv8/comon/doc_ocx/office/test.doc");
	var selection = wordApp.selection; 
	//调用下面的方法  第二个参数是要替换的内容，第三个参数是新内容
	replaceText(selection, "${justep}", "哈哈");
	replaceText(selection, "${justep1}", "哈哈");
	replaceText(selection, "${justep2}", "哈哈");
	replaceText(selection, "${justep3}", "哈哈");
	wordApp.Visible = true; //显示文件
}

function exprotWord(url) {
	try{
		var wordApp = new ActiveXObject("Word.Application");
	}catch(e){
		alert("无法调用Office对象，请确保您的机器已安装了Office并已将IE安全级别调整为启用ActiveX！"); 
		return;
	}
	wordApp.Visible = false;
	try{
		//打开指定的WORD文档
		var wordDoc = wordApp.Documents.Open(url);
	}catch(e){
		alert(e.message);
		return;
	}
	//var selection = wordApp.Selection;
	return wordApp;
}

function replaceText(Selection, oldText, newText) {
	Selection.Find.Text = oldText;
	Selection.Find.wrap = 1;
	if (newText == "") {
		newText = " ";
	}
	while (Selection.Find.Execute()) {
		Selection.TypeText(newText);
	}
}
