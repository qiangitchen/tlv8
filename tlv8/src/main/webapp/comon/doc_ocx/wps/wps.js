var obj = null;
var app;
var dataObj = new Object;

function verify(obj)
{
    console.info(obj);
}

function init(tagID, w, h) {
    var iframe;

    iframe = document.getElementById(tagID);
    if (iframe.innerHTML.indexOf("application/x-wps") > -1) {
        iframe.innerHTML = "";
    }

    var codes = [];
    codes.push("<object  name='webwps' id='webwps_id' type='application/x-wps' uistyle='ribbon'  width='100%'  height='100%'> <param name='Enabled' value='1' />  </object>");
    iframe.innerHTML = codes.join("");
    obj = document.getElementById("webwps_id");
    window.onbeforeunload = function () {
        obj.Application.Quit();
    };

    return obj.Application;
}

function InitFrame() {
    app = init("wps", "100%", "100%");
    if (!app) {
        // 为了兼容
        obj.setAttribute('data', 'Normal.dotm');
        var Interval_control = setInterval(
            function () {
                app = obj.Application;
                if (app && app.IsLoad()) {
                    clearInterval(Interval_control);
                    createDocument();
                }
                if (!app) {
                	var fileid = tlv8.RequestURLParam.getParam("fileid");
                	var fileName = tlv8.RequestURLParam.getParam("fileName");
                	window.location.href = "../tangerOffice/pspdfview.jsp?fileid="+fileid+"&fileName="+fileName;
                }
            }, 500);
    } else {
        createDocument();
    }
}

function CreateFunction(){
	
}

var _InitFrame = new CreateFunction("初始化插件", InitFrame, []);

function createDocument() {
    app.createDocument("wps");
}
var _createDocument = new CreateFunction("创建新文档", createDocument, []);

function openDocumentF() {
    var aa = app.openDocument("/home/wps/桌面/001.wps", false);
    alert(aa);
}
var _openDocumentF = new CreateFunction("可编辑打开本地文档", openDocumentF, []);

function openDocumentF2() {
    var bb = app.ShowLocalDocumentDialog();
    var aa = app.openDocument(bb, false);
    alert(aa);
}
var _openDocumentF2 = new CreateFunction("可编辑打开本地文档_通过本地文件对话框选择文件的方式打开", openDocumentF2, []);


function openDocumentT() {
    app.openDocument("/home/wps/桌面/001.wps", true, '123');
}
var _openDocumentT = new CreateFunction("只读模式打开本地文档", openDocumentT, []);

function openDocumentRemote() {
    var aa = app.openDocumentRemote("http://10.90.128.210:8080/wps/DOC_100KB.doc", false);
    alert(aa);
}
var _openDocumentRemote = new CreateFunction("打开远程文档", openDocumentRemote, []);

function saveAsQ() {
    var aa = app.saveAs("/home/wps/桌面/001.doc");
    alert(aa);
}
var _saveAsQ = new CreateFunction("保存本地不弹框", saveAsQ, []);

function saveAs() {
    var aa = app.saveAs();
    alert(aa);
}
var _saveAs = new CreateFunction("保存本地弹框", saveAs, []);

function saveURL() {
    var aa = app.saveURL("http://10.90.128.210:8080/wps/upload_l.jsp", "测试0320.wps");
    alert(aa);
}
var _saveURL = new CreateFunction("保存到远程", saveURL, []);


function SendDataToServer() {
    //alert(12);
    var aa = obj.SendDataToServer("http://10.90.128.210:8080/wps/upload_l.jsp", "/home/wps/桌面/123.wps", "测试.wps", false);
    alert(aa);
}
var _SendDataToServer = new CreateFunction("本地文档上传到远程", SendDataToServer, []);



function DownLoadServerFile() {
    //alert(123);
    var aa = obj.DownLoadServerFile("http://10.90.128.210:8080/wps/DOC_100KB.doc", "/home/wps/桌面/测试下载.docx");
    alert(aa);
}
var _DownLoadServerFile = new CreateFunction("下载", DownLoadServerFile, []);

function saveURL4() {
    var aa = app.saveURL("http://10.90.128.210:8080/wps/upload_l.jsp", "测试.ofd", "", "", true);
    alert(aa);
}
var _saveURL4 = new CreateFunction("保存到远程_导出ofd嵌入字体", saveURL4, []);

function saveURL2() {
    var name = prompt("请输入要保存的名称", "test1.pdf")
    if (name != null && name != "") {
        var ret = app.saveURL("http://10.90.128.210:8080/wps/upload_l.jsp", name, "aa", "bb");
        alert(ret);
    }
}
var _saveURL2 = new CreateFunction("保存到远程_保存为pdf并加密", saveURL2, []);

function saveURL3() {
    //通过设置ConvertSummaryInfo的值控制是否导出文档信息
    var pdfOptions = app.get_PdfExportOptions();
    var ret = pdfOptions.put_ConvertSummaryInfo(true);
	//var ret = pdfOptions.put_ConvertSummaryInfo(false);
    var name = prompt("请输入要保存的名称", "test1.pdf")
    if (name != null && name != "") {
        var ret = app.saveURL("http://10.90.128.210:8080/wps/upload_l.jsp", name, "aa", "bb");
        alert(ret);
    }
}
var _saveURL3 = new CreateFunction("保存到远程_保存成pdf并设置是否导出文档信息", saveURL3, []);

function printwps() {
    app.print();
}
var _printwps = new CreateFunction("打印文档", printwps, []);

function closewps() {
    app.close();
}
var _closewps = new CreateFunction("关闭文档", closewps, []);

function setToolbarAllVisibleT() {
    var aa = app.setToolbarAllVisible(true);
    alert(aa);
}
var _setToolbarAllVisibleT = new CreateFunction("显示工具条", setToolbarAllVisibleT, []);

function setToolbarAllVisibleF() {
    var aa = app.setToolbarAllVisible(false);
    alert(aa);
}
var _setToolbarAllVisibleF = new CreateFunction("隐藏工具条", setToolbarAllVisibleF, []);

function enableRevision() {
    if (dataObj.enableRevision == undefined)
        dataObj.enableRevision = true;
    app.enableRevision(dataObj.enableRevision);
    dataObj.enableRevision = !dataObj.enableRevision;
}
var _enableRevision = new CreateFunction("开启或者停止修订，点一次开启，在点一次停止", enableRevision, []);

function insertPicture() {
    var aa = app.insertPicture("/home/wps/桌面/001.png");
    alert(aa);
}
var _insertPicture = new CreateFunction("当前光标处插入图片", insertPicture, []);

function insertPicture2() {
    var aa = app.insertPicture("/home/wps/桌面/001.png", false);
    alert(aa);
}
var _insertPicture2 = new CreateFunction("当前光标处插入图片_浮于文字上方", insertPicture2, []);

function insertPictureSize() {
    var aa = app.insertPicture("/home/wps/桌面/001.png", 10, 20, 80, 100);
    alert(aa);
}
var _insertPictureSize = new CreateFunction("插入图片_带尺寸坐标", insertPictureSize, []);

function getText() {
    var ret = app.ActiveDocument.Content.Text;
    ret = ret.replace(/\r/g, '\r\n');
    alert(ret);
}
var _getText = new CreateFunction("获取文本内容", getText, []);

function setUserName() {
    app.setUserName("shenpiyonghu123");
}
var _setUserName = new CreateFunction("设置审批用户名", setUserName, []);

function getUserName() {
    alert(app.getUserName());
}
var _getUserName = new CreateFunction("获取审批用户名", getUserName, []);

function showRevision0() {
    app.showRevision(0);
}
var _showRevision0 = new CreateFunction("显示标记的原始修订", showRevision0, []);

function showRevision1() {
    app.showRevision(1);
}
var _showRevision1 = new CreateFunction("显示原始修订", showRevision1, []);

function showRevision2() {
    app.showRevision(2);
}
var _showRevision2 = new CreateFunction("显示最终修订", showRevision2, []);

function printRevision0() {
    app.printRevision(0);
}
var _printRevision0 = new CreateFunction("打印修订", printRevision0, []);

function printRevision1() {
    app.printRevision(1);
}
var _printRevision1 = new CreateFunction("打印原始修订", printRevision1, []);

function printRevision2() {
    app.printRevision(2);
}
var _printRevision2 = new CreateFunction("打印最终修订", printRevision2, []);

function enableRevisionAcceptCommand() {
    if (dataObj.enableRevisionAcceptCommand == undefined)
        dataObj.enableRevisionAcceptCommand = false;

    var ret = app.enableRevisionAcceptCommand(dataObj.enableRevisionAcceptCommand);
    if (ret)
        dataObj.enableRevisionAcceptCommand = !dataObj.enableRevisionAcceptCommand;
    alert(ret);
}
var _enableRevisionAcceptCommand = new CreateFunction("启用或者禁用接受修订按钮，点一次禁用，再点一次启用", enableRevisionAcceptCommand, []);

function enableRevisionRejectCommand() {
    if (dataObj.enableRevisionRejectCommand == undefined)
        dataObj.enableRevisionRejectCommand = false;

    var ret = app.enableRevisionRejectCommand(dataObj.enableRevisionRejectCommand);
    if (ret)
        dataObj.enableRevisionRejectCommand = !dataObj.enableRevisionRejectCommand;
    alert(ret);
}
var _enableRevisionRejectCommand = new CreateFunction("启用或者禁用拒绝修订按钮，点一次禁用，再点一次启用", enableRevisionRejectCommand, []);

function enableProtectT() {
    app.enableProtect(true);
}
var _enableProtectT = new CreateFunction("保护文档", enableProtectT, []);

function enableProtectF() {
    app.enableProtect(false);
}
var _enableProtectF = new CreateFunction("停止保护", enableProtectF, []);

function enableCut() {
    var aa = app.enableCut(false);
    alert(aa);
}
var _enableCut = new CreateFunction("禁止剪切", enableCut, []);

function enableCopy() {
    var aa = app.enableCopy(false);
    alert(aa);

}
var _enableCopy = new CreateFunction("禁止复制", enableCopy, []);

function ExportToOfd() {
    var aa = app.ExportToOfd("/home/wps/桌面/001.ofd");
    alert(aa);
}
var _ExportToOfd = new CreateFunction("本地导出ofd", ExportToOfd, []);

function insertDocumentField() {
    var ret = app.insertDocumentField("wps1");

}
var _insertDocumentField = new CreateFunction("插入一个公文域", insertDocumentField, []);

function insertDocumentFieldMultiField() {
    app.insertDocumentFieldMultiField("份号,密级,保密期限,紧急程度,后缀标志,发文机关名称,发文顺序号,签发人_1,签发人_2,标题,主送机关,正文,发文机关署名,成文日期,抄送机关_11,抄送机关_12,抄送机关_13,抄送机关_14,抄送机关_15,印发机关,印发日期", "，", ",");

}
var _insertDocumentFieldMultiField = new CreateFunction("插入多个公文域", insertDocumentFieldMultiField, []);

function getAllDocumentField() {
    var fieldsname = app.getAllDocumentField();
    var strs = new Array();
    strs = fieldsname.split(",");
    alert(strs);
}
var _getAllDocumentField = new CreateFunction("获取公文域列表", getAllDocumentField, []);

function deleteDocumentField() {
    var ret = app.deleteDocumentField("wps1");
    alert(ret);
}
var _deleteDocumentField = new CreateFunction("公文域删除", deleteDocumentField, []);

function showDocumentField() {
    app.showDocumentField("wps1", true);
}
var _showDocumentField = new CreateFunction("公文域显示", showDocumentField, []);

function showDocumentField2() {
    app.showDocumentField("wps1", false);
}
var _showDocumentField2 = new CreateFunction("公文域不显示", showDocumentField2, []);

function getDocumentFieldValue() {
    var ret = app.getDocumentFieldValue("wps1");
    alert(ret);
}
var _getDocumentFieldValue = new CreateFunction("查询公文域内容", getDocumentFieldValue, []);

function enableDocumentField() {
    app.enableDocumentField("wps1", true);
}
var _enableDocumentField = new CreateFunction("公文域可编辑", enableDocumentField, []);

function enableDocumentField2() {
    app.enableDocumentField("wps1", false);
}
var _enableDocumentField2 = new CreateFunction("公文域不可编辑", enableDocumentField2, []);

function insertDocument() {
    var aa = app.insertDocument("wps1", "/home/wps/桌面/001.wps");
    alert(aa);
}
var _insertDocument = new CreateFunction("公文域插入文档内容", insertDocument, []);

function insertDocument2() {
    var aa = app.insertDocument("正文", "ftp://wps:wpsgch@10.90.128.210/ftp.doc");
    alert(aa);
}
var _insertDocument2 = new CreateFunction("公文域插入文档内容_ftp", insertDocument2, []);

function isExists() {
    var aa = app.isExists("wps1");
    alert(aa);
}
var _isExists = new CreateFunction("判断公文域是否存在", isExists, []);

function backspace() {

    app.backspace();
}
var _backspace = new CreateFunction("删除", backspace, []);

function cursorToDocumentField() {
    app.cursorToDocumentField("wps1", 4);
}
var _cursorToDocumentField = new CreateFunction("光标移动到公文域指定位置", cursorToDocumentField, []);

function cursorToDocumentField5() {
    app.cursorToDocumentField("wps1", 5);
}
var _cursorToDocumentField5 = new CreateFunction("光标选中指定公文域", cursorToDocumentField5, []);

function insertText() {
	var aa = app.insertText("kingsoft");
	alert(aa);	
}
var _insertText = new CreateFunction("光标位置插入文本", insertText, []);

function setDocumentId() {
    app.setDocumentId("kingsoft");
}
var _setDocumentId = new CreateFunction("设置公文标识", setDocumentId, []);

function getDocumentId() {
    var id = app.getDocumentId();
    alert(id);
}
var _getDocumentId = new CreateFunction("获取公文标识", getDocumentId, []);

function setDocumentType() {
    app.setDocumentType("纪要");
}
var _setDocumentType = new CreateFunction("设置文种", setDocumentType, []);

function getDocumentType() {
    var type = app.getDocumentType();
    alert(type);
}
var _getDocumentType = new CreateFunction("获取文种", getDocumentType, []);

function showDocumentMultiField() {
    var ret = app.showDocumentMultiField("份号,密级,保密期限,紧急程度,后缀标志,发文机关名称,发文机关代字,年份,发文顺序号,签发人_1,签发人_2,标题,主送机关,正文,发文机关署名,成文日期,抄送机关_1,抄送机关_2,抄送机关_3,抄送机关_4,抄送机关_5,印发机关,印发日期", ",", true);
    alert(ret);
}
var _showDocumentMultiField = new CreateFunction("隐藏多个公文域", showDocumentMultiField, []);

function deleteDocumentMultiField() {
    app.deleteDocumentMultiField("份号,密级,保密期限,紧急程度,后缀标志,发文机关名称,发文机关代字,年份,发文顺序号,签发人_1,签发人_2,标题,主送机关,正文,发文机关署名,成文日期,抄送机关_1,抄送机关_2,抄送机关_3,抄送机关_4,抄送机关_5,印发机关,印发日期", ",", true);
}
var _deleteDocumentMultiField = new CreateFunction("删除多个公文域", deleteDocumentMultiField, []);

function setDocumentField1() {
    var ret = app.getAllDocumentField();

    var fieldArr = ret.split(",");
    var yus = "";
    var values = "bbbb\n\n";
    for (var i = 0; i < fieldArr.length; i++) {
        var yu = fieldArr[i];
        if (yu == '正文') {
            values += '正文'
        } else {
            values += (i + 1);

        }
        yus += yu;
        if (i != fieldArr.length - 1) {
            yus += "@#_*@";
            values += "@#_*@";
        }
    }
    var aa = app.setDocumentMultiField(yus, values, true);
    //  alert (aa);
}
var _setDocumentField1 = new CreateFunction("修改公文域内容", setDocumentField1, []);

function setDocumentField2() {
    var ret = app.getAllDocumentField();
    var fieldArr = ret.split(",");
    var yus = "";
    var values = "";
    for (var i = 0; i < fieldArr.length; i++) {
        var yu = fieldArr[i];
        if (yu == '正文') {
            continue;
        } else {
            values += (i + 100);

        }
        yus += yu;
        if (i != fieldArr.length - 1) {
            yus += "@#_*@";
            values += "@#_*@";
        }
    }

    var aa = app.setDocumentMultiField(yus, values, true);
    // alert (aa);

}
var _setDocumentField2 = new CreateFunction("修改公文域内容_除正文", setDocumentField2, []);

function fileVisible() {
    var ret = app.showCommandByName("Menu Bar", 1, false);
    alert(ret);
}
var _fileVisible = new CreateFunction("隐藏文件菜单", fileVisible, []);

function menuvisible() {
    var Bars = app.get_CommandBars();
    var filebar = Bars.get_Item("file");
    var spControls = filebar.get_Controls();

    var Controlpdf = spControls.get_Item(25);
    alert(Controlpdf.get_Caption());
    Controlpdf.put_Visible(false);

    var Control = spControls.get_Item(26);
    alert(Control.get_Caption());
    Control.put_Caption("输出为OFD格式(&G)...");
    alert(Control.get_Caption());
    Control.put_Visible(false);
}
var _menuvisible = new CreateFunction("隐藏输出ofd和pdf按钮", menuvisible, []);

function Bulid() {
    var ret = app.get_Build();
    // var ret = app.Bulid;
    alert(ret);
}
var _Bulid = new CreateFunction("获取版本", Bulid, []);

function getPluginVersion() {
    var ret = app.getPluginVersion();
    // var ret = app.Bulid;
    alert(ret);
}
var _getPluginVersion = new CreateFunction("获取插件版本号", getPluginVersion, []);

function Clipboardcallback() {
    alert("Clipboardcallback");
}

function ClipboardRegister() {
    obj.ClipbroadCheck();
    alert(app.registerEvent("DIID_ApplicationEvents4", "ClipboardChange", "Clipboardcallback"));
}
var _ClipboardRegister = new CreateFunction("复制监听", ClipboardRegister, []);

function put_PrintDrawingObjects() {
    var aa = app.Options.put_PrintDrawingObjects(true);
    alert(aa);
}
var _put_PrintDrawingObjects = new CreateFunction("设置打印图形对象", put_PrintDrawingObjects, []);


function put_PrintHiddenText() {
    var aa = app.Options.put_PrintHiddenText(true);
    alert(aa);
}
var _put_PrintHiddenText = new CreateFunction("设置打印隐藏文字", put_PrintHiddenText, []);

function put_PrintHiddenTextMode1() {
    var aa = app.Options.put_PrintHiddenTextMode(0);
    alert(aa);
}
var _put_PrintHiddenTextMode1 = new CreateFunction("设置打印隐藏文字模式1_不打印", put_PrintHiddenTextMode1, []);

function put_PrintHiddenTextMode2() {
    var aa = app.Options.put_PrintHiddenTextMode(1);
    alert(aa);
}
var _put_PrintHiddenTextMode2 = new CreateFunction("设置打印隐藏文字模式2_打印", put_PrintHiddenTextMode2, []);

function put_PrintHiddenTextMode3() {
    var aa = app.Options.put_PrintHiddenTextMode(2);
    alert(aa);
}
var _put_PrintHiddenTextMode3 = new CreateFunction("设置打印隐藏文字模式3_套打", put_PrintHiddenTextMode3, []);

function setOfdServiceProviderSuwell() {
    var a = app.get_OfdExportOptions();
    var ret = a.put_SelectServiceProvider(0);
    alert(ret);
}
var _setOfdServiceProviderSuwell = new CreateFunction("ofd厂商设置为Suwell", setOfdServiceProviderSuwell, []);

function setServiceProviderFoxit() {
    var a = app.get_OfdExportOptions();
    var ret = a.put_SelectServiceProvider(1);
    alert(ret);
}
var _setServiceProviderFoxit = new CreateFunction("ofd厂商设置为Foxit", setServiceProviderFoxit, []);


function RegisterBeforeCloseEvent() {
    var app = obj.Application;
    var ret = app.registerEvent("DIID_ApplicationEvents4", "DocumentBeforeClose", "EventCallBack2");
    alert("ret--" + ret);
}
var _RegisterBeforeCloseEvent = new CreateFunction("注册关闭事件", RegisterBeforeCloseEvent, []);

function UnRegisterBeforeCloseEvent() {
    var app = obj.Application;
    var ret = app.unRegisterEvent("DIID_ApplicationEvents4", "DocumentBeforeClose", "EventCallBack2");
    alert("ret--" + ret);
}
var _UnRegisterBeforeCloseEvent = new CreateFunction("取消注册关闭事件", UnRegisterBeforeCloseEvent, []);

function getFileSize() {
    var filename = prompt("请输入文件路径", "");
    var saveformat = prompt("请输入文件后缀，为空默认为wps", "")
    alert(app.getFileSize(filename, saveformat));
}
var _getFileSize = new CreateFunction("获取文件大小", getFileSize, []);


function setForceBackUpEnabled1() {
    app.setForceBackUpEnabled(false);
}
var _setForceBackUpEnabled1 = new CreateFunction("关闭自动备份", setForceBackUpEnabled1, []);

function setForceBackUpEnabled2() {
    app.setForceBackUpEnabled(true);
}
var _setForceBackUpEnabled2 = new CreateFunction("开启自动备份", setForceBackUpEnabled2, []);

function FullScreen() {
    app.FullScreen();
}
var _FullScreen = new CreateFunction("全屏", FullScreen, []);

function disable_ctrl_p() {
    app.DisabledHotKeys = "(ctrl+p)";
}
var _disable_ctrl_p = new CreateFunction("禁用ctrl+p", disable_ctrl_p, []);

function enable_ctrl_p() {
    app.DisabledHotKeys = "";
}
var _enable_ctrl_p = new CreateFunction("启用ctrl+p", enable_ctrl_p, []);

function insert_document() {
    alert(app.insertDocument('正文', 'ftp://wps:wpsgch@10.90.128.210/ftp.doc'));
}

function Addbutton() {
    commbar = app.CommandBars.Add("1111", 1).Controls.Add(1, 1, "1111", 1, "1111");
    commbar.put_Visible(true);
    commbar.put_Caption("11111");
}
var _Addbutton = new CreateFunction("添加一个按钮", Addbutton, []);

function RegisterCommandbar() {
    if (typeof (commbar) == 'undefined') {
        alert('请先调用"添加一个按钮"功能！！！');
        return;
    }
    var ret = commbar.registerEvent("DIID__CommandBarButtonEvents", "Click", "EventCallBack3");
    alert("ret--" + ret);
}
var _RegisterCommandbar = new CreateFunction("注册按钮事件", RegisterCommandbar, []);

function UnRegisterCommandbar() {
    var ret = commbar.unRegisterEvent("DIID__CommandBarButtonEvents", "Click", "EventCallBack3");
    alert("ret--" + ret);
}
var _UnRegisterCommandbar = new CreateFunction("取消注册按钮事件", UnRegisterCommandbar, []);

function EventCallBack3() {
    alert("这是一个按钮哦");
}



function Inserttable() {
    var range = app.ActiveDocument.get_Content();
    var table = app.ActiveDocument.Tables.Add(range, 3, 5); //创建三行五列的表格
}
var _Inserttable = new CreateFunction("插入表格", Inserttable, []);

function handwritting_unvisable() {

    var Bars = app.get_CommandBars();
    var ReverVing = Bars.get_Item("Reviewing");
    var reverVingControls = ReverVing.get_Controls();
    alert(reverVingControls);

    var Item = reverVingControls.get_Item(15);
    alert(Item);
    Item.put_Visible(false);
}
var _handwritting_unvisable = new CreateFunction("隐藏手写签批", handwritting_unvisable, []);

function FitText() {
    var aa = app.get_Selection().get_Cells().Item(1).put_FitText(true);
    alert(aa);
}
var _FitText = new CreateFunction("适应文字1_选定区域的单元格", FitText, []);

function FitText2() {

    var aa = app.GetDocumentField("发文机关名称").get_Range().get_Cells().Item(1).put_FitText(true);
    alert(aa);
}
var _FitText2 = new CreateFunction("适应文字2_公文域所在单元格", FitText2, []);

function ShowDocumentFieldTarget1() {
    var bb = app.put_ShowDocumentFieldTarget(false);
    alert(bb);

}
var _ShowDocumentFieldTarget1 = new CreateFunction("隐藏公文域底纹_函数调用方式", ShowDocumentFieldTarget1, []);

function ShowDocumentFieldTarget2() {
    var bb = app.ShowDocumentFieldTarget = true;
    alert(bb);
}
var _ShowDocumentFieldTarget2 = new CreateFunction("隐藏公文域底纹_属性调用方式", ShowDocumentFieldTarget2, []);

function get_ShowDocumentFieldTarget() {
    var bb = app.get_ShowDocumentFieldTarget();
    alert(bb);
}
var _get_ShowDocumentFieldTarget = new CreateFunction("判断公文域底纹是否存在", get_ShowDocumentFieldTarget, []);

function openDocumentRemote_s() {
    var aa = app.openDocumentRemote_s("http://10.90.128.210:8080/wps/DOC_100KB.doc", false);
    alert(aa);
}
var _openDocumentRemote_s = new CreateFunction("打开远程文档不落地", openDocumentRemote_s, []);

function saveURL_s() {
    var aa = app.saveURL_s("http://10.90.128.210:8080/wps/upload_l.jsp", "aa不落地测试.doc");
    alert(aa);
}
var _saveURL_s = new CreateFunction("保存到远程不落地", saveURL_s, []);

function saveAsBase64Str() {
    base64str_wps = app.saveAsBase64Str("ofd");
    alert(base64str_wps);
}
var _saveAsBase64Str = new CreateFunction("保存到base64", saveAsBase64Str, []);

function openDocumentFromBase64Str() {
    if (typeof base64str_wps != "undefined" && base64str_wps != "") {
        var aa = app.openDocumentFromBase64Str(base64str_wps, false);
        alert(aa);
    } else {
        alert("请先调用saveasbase64str");
    }
}
var _openDocumentFromBase64Str = new CreateFunction("从base64打开", openDocumentFromBase64Str, []);

//手写批注相关接口

function EnterInkDraw() {
    aa = app.EnterInkDraw();
    alert(aa);
}
var _EnterInkDraw = new CreateFunction("进入手写批注状态", EnterInkDraw, []);

function ExitInkDraw() {
    aa = app.ExitInkDraw();
    alert(aa);
}
var _ExitInkDraw = new CreateFunction("退出手写批注状态", ExitInkDraw, []);

function SetInkDrawColor() {
    aa = app.SetInkDrawColor("#BBFFFF");
    alert(aa);
}
var _SetInkDrawColor = new CreateFunction("设置当前批注颜色", SetInkDrawColor, []);

function SetInkDrawStyle1() {
    aa = app.SetInkDrawStyle(0);
    alert(aa);
}
var _SetInkDrawStyle1 = new CreateFunction("设置使用笔", SetInkDrawStyle1, []);

function SetInkDrawStyle2() {
    aa = app.SetInkDrawStyle(1);
    alert(aa);
}
var _SetInkDrawStyle2 = new CreateFunction("设置使用形状", SetInkDrawStyle2, []);

function SetInkDrawShapeStyle1() {
    aa = app.SetInkDrawShapeStyle(0);
    alert(aa);
}
var _SetInkDrawShapeStyle1 = new CreateFunction("设置批注形状类型_直线", SetInkDrawShapeStyle1, []);

function SetInkDrawShapeStyle2() {
    aa = app.SetInkDrawShapeStyle(1);
    alert(aa);
}
var _SetInkDrawShapeStyle2 = new CreateFunction("设置批注形状类型_波浪线", SetInkDrawShapeStyle2, []);

function SetInkDrawShapeStyle3() {
    aa = app.SetInkDrawShapeStyle(2);
    alert(aa);
}
var _SetInkDrawShapeStyle3 = new CreateFunction("设置批注形状类型_矩形", SetInkDrawShapeStyle3, []);

function SetInkDrawPenStyle1() {
    aa = app.SetInkDrawPenStyle(0);
    alert(aa);
}
var _SetInkDrawPenStyle1 = new CreateFunction("设置批注笔类型_圆珠笔", SetInkDrawPenStyle1, []);

function SetInkDrawPenStyle2() {
    aa = app.SetInkDrawPenStyle(1);
    alert(aa);
}
var _SetInkDrawPenStyle2 = new CreateFunction("设置批注笔类型_水彩笔", SetInkDrawPenStyle2, []);

function SetInkDrawPenStyle3() {
    aa = app.SetInkDrawPenStyle(2);
    alert(aa);
}
var _SetInkDrawPenStyle3 = new CreateFunction("设置批注笔类型_荧光笔", SetInkDrawPenStyle3, []);

function SetInkDrawLineStyle1() {
    aa = app.SetInkDrawLineStyle(0);
    alert(aa);
}
var _SetInkDrawLineStyle1 = new CreateFunction("设置批注磅值_0.25", SetInkDrawLineStyle1, []);

function SetInkDrawLineStyle2() {
    aa = app.SetInkDrawLineStyle(4);
    alert(aa);
}
var _SetInkDrawLineStyle2 = new CreateFunction("设置批注磅值_4", SetInkDrawLineStyle2, []);

function SetInkDrawLineStyle3() {
    aa = app.SetInkDrawLineStyle(8);
    alert(aa);
}
var _SetInkDrawLineStyle3 = new CreateFunction("设置批注磅值_8", SetInkDrawLineStyle3, []);

function SetInkDrawEraser() {
    aa = app.SetInkDrawEraser(true);
    alert(aa);
}
var _SetInkDrawEraser = new CreateFunction("进入橡皮擦状态", SetInkDrawEraser, []);

function SetInkDrawEraser2() {
    aa = app.SetInkDrawEraser(false);
    alert(aa);
}
var _SetInkDrawEraser2 = new CreateFunction("退出橡皮擦状态", SetInkDrawEraser2, []);

function InsertImageFromScanner() {
    aa = app.InsertImageFromScanner();
    alert(aa);
}
var _InsertImageFromScanner = new CreateFunction("插入图片来自扫描仪", InsertImageFromScanner, []);

function CommandBarsT() {
    var aa = app.ActiveDocument.CommandBars.get_Item("File").Controls.get_Item("转换(&B)").Visible = true;
    alert(aa);
}
var _CommandBarsT = new CreateFunction("显示转换按钮", CommandBarsT, []);

function CommandBarsF() {
    var aa = app.ActiveDocument.CommandBars.get_Item("File").Controls.get_Item("转换(&B)").Visible = false;
    alert(aa);
}
var _CommandBarsF = new CreateFunction("隐藏转换按钮", CommandBarsF, []);

function Documents() {
    var aa = app.Documents.Open("/home/wps/桌面/001.wps", false, false, false, "", "", false, "", "", 0, 0, false, false, false, 0, false);
    alert(aa);
}
var _Documents = new CreateFunction("打开隐藏文档", Documents, []);

function retitle1() {
    var aa = app.Selection.Rows.put_HeadingFormat(1);
    alert(aa);
}
var _retitle1 = new CreateFunction("设置标题行重复", retitle1, []);

function retitle2() {
    var aa = app.Selection.Rows.put_HeadingFormat(0);
    alert(aa);
}
var _retitle2 = new CreateFunction("取消标题行重复", retitle2, []);


function RegisterDocumentBrforeSaveEvent() {
    var app = obj.Application;
    var ret = app.registerEvent("DIID_ApplicationEvents4", "DocumentBeforeSave", "DocumentBeforeSaveCallBack");
    alert("监控");
    return ret;
}
var _RegisterDocumentBrforeSaveEvent = new CreateFunction("注册保存监控事件", RegisterDocumentBrforeSaveEvent, []);

function unRegisterDocumentBrforeSaveEvent() {
    var app = obj.Application;
    var ret = app.unregisterEvent("DIID_ApplicationEvents4", "DocumentBeforeSave", "DocumentBeforeSaveCallBack");
    alert("取消监控");
    return ret;
}

function insertMultiBookMarks() {
    var a = app.insertMultiBookMarks("book1,book2,book3,book4", ",");
    alert(a);

}
var _insertMultiBookMarks = new CreateFunction("批量插入书签", insertMultiBookMarks, []);

function deleteMultiBookMarks() {
    var a = app.deleteMultiBookMarks("book1,book2,book3,book4", ",");
    alert(a);
}
var _deleteMultiBookMarks = new CreateFunction("批量删除书签", deleteMultiBookMarks, []);

function getAllBookMarks() {
    alert(app.getAllBookMarks());
}
var _getAllBookMarks = new CreateFunction("获取全部书签", getAllBookMarks, []);

function replaceBookMarksText() {
    var a = app.replaceBookMarksText("book1", "组织部,企划部","/","/");
    alert(a);
}
var _replaceBookMarksText = new CreateFunction("替换书签内容", replaceBookMarksText, []);

function saveURL_GetData() {
    var aa = app.saveURL_GetData("http://10.90.128.210:8080/wps/upload_l.jsp", "test.wps");
    alert(aa);
}
var _saveURL_GetData = new CreateFunction("保存到远程_返回自定义标识", saveURL_GetData, []);

function RegisterDocumentBeforeSaveEventUnprotect() {
    var app = obj.Application;
    var ret = app.registerEvent("DIID_ApplicationEvents4", "DocumentBeforeSave", "DocumentBeforeSaveCallBackUnprotect");
    alert("RegisterDocumentBeforeSave:" + ret);
}

function RegisterAfterSaveEvent() {
    var appex = obj.Application.ApplicationEx;
    var ret = appex.registerEvent("DIID_WpsApplicationEventsEx", "DocumentAfterSave", "EventCallBackAfterSaveProtect");
    alert("RegisterAfterSave--" + ret);
}

function UnRegisterAfterSaveEvent() {
    var appex = obj.Application.ApplicationEx;
    var ret = appex.unRegisterEvent("DIID_WpsApplicationEventsEx", "DocumentAfterSave", "EventCallBackAfterSaveProtect");
    alert("UnRegisterAfterSave--" + ret);
}

function RegisterSaveEvent() {
    obj.Application.ApplicationEx.put_SaveRememberStatus(false); //true，代表另存后当前显示文档为另存得到的新文档；false，代表另存后当前显示文档为原文档
    RegisterDocumentBeforeSaveEventUnprotect();
    RegisterAfterSaveEvent();
}
var _RegisterSaveEvent = new CreateFunction("注册只读文档另存后的文档为可编辑状态", RegisterSaveEvent, []);

// >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>> 函数调用区 >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
// 加载后执行
window.onload = function () {
    InitLayui();
}

// <<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<< 回调函数区 <<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<
//关闭回调事件
function EventCallBack2(doc, cancel) {
    alert("EventCallBack2");
    cancel.SetValue(true);
    alert(cancel.GetValue());
}

//保存回调事件
function DocumentBeforeSaveCallBack(Doc, SaveAsUI, Cancel) {
    if (SaveAsUI.GetValue()) {
        //alert("另存为");
		alert(SaveAsUI.GetValue());
    } else {
		alert(SaveAsUI.GetValue());
        alert("保存");
    }
    // 如果该值设置为true，则表示取消当前保存操作。
    // 如果该值设置为false，则表示正常保存。
    Cancel.SetValue(true);
    alert("保存监控");
}

var wpsIsProtect = 0;
// 打开文档前，回调函数
function DocumentBeforeSaveCallBackUnprotect(Doc, SaveAsUI, Cancel) {
    wpsIsProtect = Doc.ProtectionType;
    if (wpsIsProtect == 3) {
        Doc.Unprotect("123");
    }
}

// 打开文档后，回调函数
function EventCallBackAfterSaveProtect(doc, saveResult) {
    if (wpsIsProtect == 3) {
        alert("AfterSave:" + doc.Protect(wpsIsProtect, true, "123"));
        wpsIsProtect = 0;
    }
}

//按行拆分
function SplitRow() {
    var tb = app.ActiveDocument.Tables.Item(1);
    //alert(tb.Rows.get_Count());
    var row = tb.Rows.Item(1);
    //alert(row.Cells.get_Count());
    row.Cells.Split(3, 2);
}
var _SplitRow = new CreateFunction("按行拆分", SplitRow, []);

//按列拆分
function SplitColumn() {
    var tb = app.ActiveDocument.Tables.Item(1);
    //alert(tb.Columns.get_Count());
    var columns = tb.Columns.Item(2);
    //alert(columns.Cells.get_Count());
    aa = columns.Cells.Split(10, 13);
    //alert(columns.Cells.Item(1));
    //alert(aa);
}
var _SplitColumn = new CreateFunction("按列拆分", SplitColumn, []);

//拆分单元格
function SplitCell() {
    var tb = app.ActiveDocument.Tables.Item(1);
    alert(tb.Rows.get_Count());
    var row = tb.Rows.Item(2);
    alert(row.Cells.get_Count());
    aa = row.Cells.Item(1).Split(2, 3);
    alert(aa);
}
var _SplitCell = new CreateFunction("拆分单元格", SplitCell, []);

//重定向
function openDocumentRemote0() {
    var aa = app.openDocumentRemote("http://10.90.128.210:8080/wps/reurl_download.jsp", false);
    alert(aa);
}
var _openDocumentRemote0 = new CreateFunction("重定向_打开远程文档", openDocumentRemote0, []);

function saveURL0() {
    var aa = app.saveURL("http://10.90.128.210:8080/wps/reurl_upload.jsp", "测试0320.wps");
    alert(aa);
}
var _saveURL0 = new CreateFunction("重定向_保存到远程", saveURL0, []);

function openDocumentRemote_s0() {
    var aa = app.openDocumentRemote_s("http://10.90.128.210:8080/wps/reurl_download.jsp", false);
    alert(aa);
}
var _openDocumentRemote_s0 = new CreateFunction("重定向_打开远程文档不落地", openDocumentRemote_s0, []);

function saveURL_s0() {
    var aa = app.saveURL_s("http://10.90.128.210:8080/wps/reurl_upload.jsp", "aa不落地测试重定向.doc");
    alert(aa);
}
var _saveURL_s0 = new CreateFunction("重定向_保存到远程不落地", saveURL_s0, []);

function SendDataToServer0() {
    alert(12);
    var aa = obj.SendDataToServer("http://10.90.128.210:8080/wps/reurl_upload.jsp", "/home/wps/桌面/aaa.wps", "测试.wps", false);
    alert(aa);
}
var _SendDataToServer0 = new CreateFunction("重定向_本地文档上传到远程", SendDataToServer0, []);

function DownLoadServerFile0() {
    var aa = obj.DownLoadServerFile("http://10.90.128.210:8080/wps/reurl_download.jsp", "/home/wps/桌面/测试下载.wps");
    alert(aa);
}
var _DownLoadServerFile0 = new CreateFunction("重定向_下载", DownLoadServerFile0, []);

function saveURL_GetData0() {
    var aa = app.saveURL_GetData("http://10.90.128.210:8080/wps/reurl_upload.jsp", "test.wps");
    alert(aa);
}
var _saveURL_GetData0 = new CreateFunction("重定向_保存到远程_返回自定义标识", saveURL_GetData0, []);

function saveURL_CustomParam0() {
	var jsondata = {key1:"value1"};
    var aa = app.saveURL_CustomParam("http://10.90.128.210:8080/wps/reurl_upload.jsp", "test.wps", JSON.stringify(jsondata));
    alert(aa);
}
var _saveURL_CustomParam0 = new CreateFunction("保存到远程_发送/返回自定义数据", saveURL_CustomParam0, []);

//带session的新接口
function saveURL_FormData1() {
    var aa = app.saveURL_FormData("http://10.90.128.210:8080/servletTest/HelloServlet", "测试0320.wps");
    alert(aa);
}
var _saveURL_FormData1 = new CreateFunction("保存到远程_session", saveURL_FormData1, []);

function saveURL_s_FormData1() {
    var aa = app.saveURL_s_FormData("http://10.90.128.210:8080/servletTest/HelloServlet", "aa不落地测试.doc");
    alert(aa);
}
var _saveURL_s_FormData1 = new CreateFunction("保存到远程不落地_session", saveURL_s_FormData1, []);

function SendDataToServer_FormData1() {
    var headData = {};
    headData.filename = "本地上传到远程.wps";
    var aa = obj.SendDataToServer_FormData("http://10.90.128.210:8080/servletTest/HelloServlet", "/home/wps/桌面/123.wps", JSON.stringify(headData), false);
    alert(aa);
}
var _SendDataToServer_FormData1 = new CreateFunction("本地文档保存到远程_session", SendDataToServer_FormData1, []);

function saveURL_GetData_FormData1() {
    var aa = app.saveURL_GetData_FormData("http://10.90.128.210:8080/servletTest/HelloServlet", "test.wps");
    alert(aa);
}
var _saveURL_GetData_FormData1 = new CreateFunction("保存到远程_返回自定义标识_session", saveURL_GetData_FormData1, []);

function saveURL_CustomParam_FormData1() {
    var jsondata = {key1:"value1"}; 
    var aa = app.saveURL_CustomParam_FormData("http://10.90.128.210:8080/servletTest/HelloServlet", "test.wps", JSON.stringify(jsondata)); 
    alert(aa);
}
var _saveURL_CustomParam_FormData1 = new CreateFunction("保存到远程_发送/返回自定义数据_session", saveURL_CustomParam_FormData1, []);

//https协议
function saveURL_FormData2() {
    var aa = app.saveURL_FormData("https://10.90.128.210:8443/servletTest/HelloServlet", "测试0320.wps");
    alert(aa);
}
var _saveURL_FormData2 = new CreateFunction("https保存到远程", saveURL_FormData2, []);

function saveURL_s_FormData2() {
    var aa = app.saveURL_s_FormData("https://10.90.128.210:8443/servletTest/HelloServlet", "aa不落地测试.doc");
    alert(aa);
}
var _saveURL_s_FormData2 = new CreateFunction("https保存到远程不落地", saveURL_s_FormData2, []);

function SendDataToServer_FormData2() {
    var headData = {};
    headData.filename = "本地上传到远程.wps";
    var aa = obj.SendDataToServer_FormData("https://10.90.128.210:8443/servletTest/HelloServlet", "/home/wps/桌面/123.wps", JSON.stringify(headData), false);
    alert(aa);
}
var _SendDataToServer_FormData2 = new CreateFunction("https本地文档上传到远程", SendDataToServer_FormData2, []);

function saveURL_GetData_FormData2() {
    var aa = app.saveURL_GetData_FormData("https://10.90.128.210:8443/servletTest/HelloServlet", "test.wps");
    alert(aa);
}
var _saveURL_GetData_FormData2 = new CreateFunction("https保存到远程_返回自定义标识", saveURL_GetData_FormData2, []);

function saveURL_CustomParam_FormData2() {
    var jsondata = {key1:"value1"}; 
    var aa = app.saveURL_CustomParam_FormData("https://10.90.128.210:8443/servletTest/HelloServlet", "test.wps", JSON.stringify(jsondata)); 
    alert(aa);
}
var _saveURL_CustomParam_FormData2 = new CreateFunction("https保存到远程_发送/返回自定义数据", saveURL_CustomParam_FormData2, []);

//重定向、session
function saveURL_FormData3() {
    var aa = app.saveURL_FormData("http://10.90.128.210:8080/servletTest/reurl_session_upload.jsp", "测试0320.wps");
    alert(aa);
}
var _saveURL_FormData3 = new CreateFunction("重定向_保存到远程_session", saveURL_FormData3, []);

function saveURL_s_FormData3() {
    var aa = app.saveURL_s_FormData("http://10.90.128.210:8080/servletTest/reurl_session_upload.jsp", "aa不落地测试.doc");
    alert(aa);
}
var _saveURL_s_FormData3 = new CreateFunction("重定向_保存到远程不落地_session", saveURL_s_FormData3, []);

function SendDataToServer_FormData3() {
    var headData = {};
    headData.filename = "本地上传到远程.wps";
    var aa = obj.SendDataToServer_FormData("http://10.90.128.210:8080/servletTest/reurl_session_upload.jsp", "/home/wps/桌面/123.wps", JSON.stringify(headData), false);
    alert(aa);
}
var _SendDataToServer_FormData3 = new CreateFunction("重定向_本地文档上传到远程_session", SendDataToServer_FormData3, []);

function saveURL_GetData_FormData3() {
    var aa = app.saveURL_GetData_FormData("http://10.90.128.210:8080/servletTest/reurl_session_upload.jsp", "test.wps");
    alert(aa);
}
var _saveURL_GetData_FormData3 = new CreateFunction("重定向_保存到远程_返回自定义标识_session", saveURL_GetData_FormData3, []);

function saveURL_CustomParam_FormData3() {
    var jsondata = {key1:"value1"}; 
    var aa = app.saveURL_CustomParam_FormData("http://10.90.128.210:8080/servletTest/reurl_session_upload.jsp", "test.wps", JSON.stringify(jsondata)); 
    alert(aa);
}
var _saveURL_CustomParam_FormData3 = new CreateFunction("重定向_保存到远程_发送/返回自定义数据_session", saveURL_CustomParam_FormData3, []);

//session、https、重定向组合接口
function saveURL_FormData4() {
    var aa = app.saveURL_FormData("https://10.90.128.210:8443/servletTest/reurl_https_upload.jsp", "测试0320.wps");
    alert(aa);
}
var _saveURL_FormData4 = new CreateFunction("组合接口_保存到远程", saveURL_FormData4, []);

function saveURL_s_FormData4() {
    var aa = app.saveURL_s_FormData("https://10.90.128.210:8443/servletTest/reurl_https_upload.jsp", "aa不落地测试.doc");
    alert(aa);
}
var _saveURL_s_FormData4 = new CreateFunction("组合接口_保存到远程不落地", saveURL_s_FormData4, []);

function SendDataToServer_FormData4() {
    var headData = {};
    headData.filename = "本地上传到远程.wps";
    var aa = obj.SendDataToServer_FormData("https://10.90.128.210:8443/servletTest/reurl_https_upload.jsp", "/home/wps/桌面/123.wps", JSON.stringify(headData), false);
    alert(aa);
}
var _SendDataToServer_FormData4 = new CreateFunction("组合接口_本地文档上传到远程", SendDataToServer_FormData4, []);

function saveURL_GetData_FormData4() {
    var aa = app.saveURL_GetData_FormData("https://10.90.128.210:8443/servletTest/reurl_https_upload.jsp", "test.wps");
    alert(aa);
}
var _saveURL_GetData_FormData4 = new CreateFunction("组合接口_保存到远程_返回自定义标识", saveURL_GetData_FormData4, []);

function saveURL_CustomParam_FormData4() {
    var jsondata = {key1:"value1"}; 
    var aa = app.saveURL_CustomParam_FormData("https://10.90.128.210:8443/servletTest/reurl_https_upload.jsp", "test.wps", JSON.stringify(jsondata)); 
    alert(aa);
}
var _saveURL_CustomParam_FormData4 = new CreateFunction("组合接口_保存到远程_发送/返回自定义数据", saveURL_CustomParam_FormData4, []);

//设置远程文档另存路径
function setTmpFilepath() {
    var aa = obj.setTmpFilepath("/home/wps/桌面");
    alert(aa);
}
var _setTmpFilepath = new CreateFunction("设置远程文档临时文件路径", setTmpFilepath, []);

//获取打印范围
function RegisterPrintOutPageSetEvent() {
    var appex = obj.Application.ApplicationEx;
    var ret = appex.registerEvent("DIID_ApplicationEventsEx", "DocumentAfterPrint", "EventCallBackPrintOutPageSet");
    alert("PrintOutPageSet--" + ret);
}
var _RegisterPrintOutPageSetEvent = new CreateFunction("获取打印范围", RegisterPrintOutPageSetEvent, []);

function EventCallBackPrintOutPageSet(doc, pageset) {
    var range = pageset.get_PrintOutRange();
    if (range == 0) {
        alert("全选");
    } else if (range == 1) {
        alert("当前选中");
    } else if (range == 2) {
        alert("当前页");
    } else if (range == 4) {
        alert("页码范围");
        alert(pageset.get_PrintOutPages());
    }
}
//多行水印
function insertWaterMark() {
    app.ActiveWindow.ActivePane.View.SeekView = 9;

    app.Selection.HeaderFooter.Shapes.AddTextEffect(0, "公司绝密", "华文新魏", 36, false, false, 0, 0).Select();
    app.Selection.ShapeRange.TextEffect.NormalizedHeight = false;
    app.Selection.ShapeRange.Line.Visible = false;
    app.Selection.ShapeRange.Fill.Visible = true;
    app.Selection.ShapeRange.Fill.Solid();
    app.Selection.ShapeRange.Fill.ForeColor.RGB = 12632256;
    app.Selection.ShapeRange.Fill.Transparency = 0.5;

    app.Selection.ShapeRange.LockAspectRatio = true;
    app.Selection.ShapeRange.Height = 4.58 * 28.346;
    app.Selection.ShapeRange.Width = 28.07 * 28.346;
    app.Selection.ShapeRange.Rotation = 315; //控制旋转
    app.Selection.ShapeRange.WrapFormat.AllowOverlap = true;
    app.Selection.ShapeRange.WrapFormat.Side = 3;
    app.Selection.ShapeRange.WrapFormat.Type = 3;
    app.Selection.ShapeRange.RelativeHorizontalPosition = 0;
    app.Selection.ShapeRange.RelativeVerticalPosition = 0;
    app.Selection.ShapeRange.Left = '-999995'; /**/
    app.Selection.ShapeRange.Top = '-999995';
    app.ActiveWindow.ActivePane.View.SeekView = 0;
}
var _insertWaterMark = new CreateFunction("添加水印", insertWaterMark, []);

function insertMutilWatermarks() {
    var WaterMarks = app.ActiveDocument.DocumentEx.WaterMarks;
    WaterMarks.AddTiledWaterMarks(1, "金山软件kingsoft", "宋体", 20, true, 12);
}
var _insertMutilWatermarks = new CreateFunction("添加多行水印", insertMutilWatermarks, []);

function deleteWatermarks() {
    var WaterMarks = app.ActiveDocument.DocumentEx.WaterMarks;
	WaterMarks.DeleteAll(2);
}
var _deleteWatermarks = new CreateFunction("删除所有文字水印", deleteWatermarks, []);
//蓝底白字
function setBlueScreenT() {
	app.Options.BlueScreen = true;
}
var _setBlueScreenT = new CreateFunction("勾选蓝底白字", setBlueScreenT, []);

function setBlueScreenF() {
	app.Options.BlueScreen = false;
}
var _setBlueScreenF = new CreateFunction("不勾选蓝底白字", setBlueScreenF, []);
//2019年11月14日新增：获取/设置段落属性、获取/设置当前位置属性
function get_ParagraphProperties() {
	var aa = app.get_ParagraphProperties();
	alert(aa);
}
var _get_ParagraphProperties = new CreateFunction("获取段落属性", get_ParagraphProperties, []);

function put_ParagraphProperties() {
	var jsondata = {
		fontKeys:[], 
		pgFormatKeys:[]
		};
	var fontdata = {	
		index:1,
		Size:5
		};
	var pgFormatdata = {
		index:1,
		LineSpacing:20
		};
	jsondata.fontKeys[jsondata.fontKeys.length] = fontdata;
	jsondata.pgFormatKeys[jsondata.pgFormatKeys.length] = pgFormatdata;
	var b = app.put_ParagraphProperties(JSON.stringify(jsondata));
	alert(b);
}
var _put_ParagraphProperties = new CreateFunction("设置段落属性", put_ParagraphProperties, []);

function get_SelectionProperties() {
	var aa = app.get_SelectionProperties();
	alert(aa);
}
var _get_SelectionProperties = new CreateFunction("获取选中区域属性", get_SelectionProperties, []);

function put_SelectionProperties() {
	var jsondata = {};
	jsondata.fontKeys = {	
		Size:5
	};
	jsondata.pgFormatKeys= {
		LineSpacing:20
	};
	var b = app.put_SelectionProperties(JSON.stringify(jsondata));
	alert(b);
	}
var _put_SelectionProperties = new CreateFunction("设置选中区域属性", put_SelectionProperties, []);

function GetName() 
{
    var app = obj.Application;
    //verify(app);
    alert(app.Name);
}
var _GetName = new CreateFunction("获取APP名", GetName, []);

//20191114新增：保存到远程_上传/返回自定义数据
//基础接口：
function saveURL_CustomParam() {
	var jsondata = {key1:"value1"};
    var aa = app.saveURL_CustomParam("http://10.90.128.210:8080/servletTest/upload_l.jsp", "test.wps", JSON.stringify(jsondata));
    alert(aa);
}
var _saveURL_CustomParam = new CreateFunction("保存到远程_发送/返回自定义数据", saveURL_CustomParam, []);

//20191206新增：禁用接受&拒绝修订按钮
function enableRevisionViewCommandF()
{
	var aa = app.enableRevisionViewCommand(false);
	alert(aa);
}
var _enableRevisionViewCommandF = new CreateFunction("禁用接受&拒绝修订按钮", enableRevisionViewCommandF, []);
function enableRevisionViewCommandT()
{
	var aa = app.enableRevisionViewCommand(true);
	alert(aa);
}
var _enableRevisionViewCommandT = new CreateFunction("启用接受&拒绝修订按钮", enableRevisionViewCommandT, []);

//20191220新增：上传、下载、保存到远程、打开远程文档组合接口
//上传
function UploadFileToServer()
{
	var jsondata = {
		fileName:"上传.doc",
		isGetBodyResult:true,
		isGetResponseHead:true,
		bDelLocalFile:false,
		customFromData:{
			"key1":"金山办公kingsoft123",
			"key2":"金山办公kingsoft777",
			"key3":"金山办公kingsoft777",
		},
		customHeadData:{
			"key1":"金山办公头kingsoft123",
			"key2":"金山办公头kingsoft777",
			"key3":"金山办公头kingsoft777",
			"Cookie":"upload123;uploadqwerty"
		}
	};
	var aa = obj.UploadFileToServer("http://10.90.128.210:8080/servletTest/HelloServlet","/home/wps/桌面/性能测试样张161009/DOC_100KB.doc",JSON.stringify(jsondata));
	alert(aa);
}
var _UploadFileToServer = new CreateFunction("上传本地文档_NEW", UploadFileToServer, []);
//下载
function DownloadFileFromServer()
{
	var jsondata = {
		isGetResponseHead:true,
		customHeadData:{
			"key1":"金山办公头kingsoft123",
			"key2":"金山办公头kingsoft777",
			"key3":"金山办公头kingsoft777",
			"Cookie":"download456;uploadqwerty"
		}
	};
	var aa = obj.DownloadFileFromServer("http://10.90.128.210:8080/wps/DOC_100KB.doc","/home/wps/桌面/测试下载test123.doc",JSON.stringify(jsondata));
	alert(aa);
}
var _DownloadFileFromServer = new CreateFunction("下载远程文档_NEW", DownloadFileFromServer, []);
//保存到远程
function SaveDocumentToServer()
{
	var jsondata = {
		fileName:"保存到远程.doc",
		isGetBodyResult:true,
		isGetResponseHead:true,
		isNoTmpFile:true,
		customFromData:{
			"key1":"金山办公kingsoft44444444444444444444123",
			"key2":"金山办公kingsoft777",
			"key3":"金山办公kingsoft777",
		},
		customHeadData:{
			"key1":"金山办公头kingsoft123123123123",
			"key2":"金山办公头kingsoft777",
			"key3":"金山办公头kingsoft777",
			"Cookie":"saveurl123;uploadqwerty"
		}
	};
	var aa = app.SaveDocumentToServer("http://10.90.128.210:8080/servletTest/HelloServlet",JSON.stringify(jsondata));
	alert(aa);
}
var _SaveDocumentToServer = new CreateFunction("保存到远程_NEW", SaveDocumentToServer, []);
//打开远程文档
function OpenDocumentFromServer()
{
	var jsondata = {
		password:"123",
		readOnly:true,
		isGetResponseHead:true,
		isNoTmpFile:true,
		customHeadData:{
			"key1":"金山办公头kingsoft123",
			"key2":"金山办公头kingsoft777",
			"key3":"金山办公头kingsoft777",
			"Cookie":"openurl123;uploadqwerty"
		}
	};
	var aa = app.OpenDocumentFromServer("http://10.90.128.210:8080/wps/DOC_100KB.doc",JSON.stringify(jsondata));
	alert(aa);
}
var _OpenDocumentFromServer = new CreateFunction("打开远程文档_NEW", OpenDocumentFromServer, []);

//20191220新增：上传、下载、保存到远程、打开远程文档组合接口https+重定向
//上传
function UploadFileToServer0()
{
	var jsondata = {
		fileName:"上传https重定向.doc",
		isGetBodyResult:true,
		isGetResponseHead:true,
		bDelLocalFile:false,
		customFromData:{
			"key1":"金山办公kingsoft123",
			"key2":"金山办公kingsoft777",
			"key3":"金山办公kingsoft777",
		},
		customHeadData:{
			"key1":"金山办公头kingsoft123",
			"key2":"金山办公头kingsoft777",
			"key3":"金山办公头kingsoft777",
			"Cookie":"upload123;uploadqwerty_https_redirect"
		}
	};
	var aa = obj.UploadFileToServer("https://10.90.128.210:8443/servletTest/new_reurl_https_upload.jsp","/home/wps/桌面/性能测试样张161009/DOC_100KB.doc",JSON.stringify(jsondata));
	alert(aa);
}
var _UploadFileToServer0 = new CreateFunction("上传本地文档_NEW_https_重定向", UploadFileToServer0, []);
//下载
function DownloadFileFromServer0()
{
	var jsondata = {
		isGetResponseHead:true,
		customHeadData:{
			"key1":"金山办公头kingsoft123",
			"key2":"金山办公头kingsoft777",
			"key3":"金山办公头kingsoft777",
			"Cookie":"download456;uploadqwerty_https_redirect"
		}
	};
	var aa = obj.DownloadFileFromServer("https://10.90.128.210:8443/servletTest/new_reurl_https_download.jsp","/home/wps/桌面/测试下载https重定向.doc",JSON.stringify(jsondata));
	alert(aa);
}
var _DownloadFileFromServer0 = new CreateFunction("下载远程文档_NEW_https_重定向", DownloadFileFromServer0, []);
//保存到远程
function SaveDocumentToServer0()
{
	var jsondata = {
		fileName:"保存到远程https重定向.doc",
		isGetBodyResult:true,
		isGetResponseHead:true,
		isNoTmpFile:true,
		customFromData:{
			"key1":"金山办公kingsoft44444444444444444444123",
			"key2":"金山办公kingsoft777",
			"key3":"金山办公kingsoft777",
		},
		customHeadData:{
			"key1":"金山办公头kingsoft123123123123",
			"key2":"金山办公头kingsoft777",
			"key3":"金山办公头kingsoft777",
			"Cookie":"saveurl123;uploadqwerty_https_redirect"
		}
	};
	var aa = app.SaveDocumentToServer("https://10.90.128.210:8443/servletTest/new_reurl_https_upload.jsp",JSON.stringify(jsondata));
	alert(aa);
}
var _SaveDocumentToServer0 = new CreateFunction("保存到远程_NEW_https_重定向", SaveDocumentToServer0, []);
//打开远程文档
function OpenDocumentFromServer0()
{
	var jsondata = {
		password:"123",
		readOnly:true,
		isGetResponseHead:true,
		isNoTmpFile:true,
		customHeadData:{
			"key1":"金山办公头kingsoft123",
			"key2":"金山办公头kingsoft777",
			"key3":"金山办公头kingsoft777",
			"Cookie":"openurl123;uploadqwerty_https_redirect"
		}
	};
	var aa = app.OpenDocumentFromServer("https://10.90.128.210:8443/servletTest/new_reurl_https_download.jsp",JSON.stringify(jsondata));
	alert(aa);
}
var _OpenDocumentFromServer0 = new CreateFunction("打开远程文档_NEW_https_重定向", OpenDocumentFromServer0, []);

//新增隐藏ofd预览接口
function menuvisible1()
{
	var bar1 = app.CommandBars.get_Item('File'); //文件菜单 
	var controls1 = bar1.Controls; 
	var control1 = controls1.get_Item(33); 
	var aa = control1.put_Visible(false);
	alert("隐藏文件菜单的ofd预览："+aa);
	var bar2 = app.CommandBars.get_Item('Standard'); //常用工具栏
	var controls2 = bar2.Controls; 
	var control2 = controls2.get_Item(52); 
	var bb = control2.put_Visible(false);
	alert("隐藏常用工具栏的ofd预览："+bb);
}
var _menuvisible1 = new CreateFunction("隐藏ofd预览", menuvisible1, []);

//ribbon界面禁用/启用指定按钮
function SetEnabledMso() {
	var aa = app.CommandBars.SetEnabledMso('Copy',false);
	alert(aa);
}
var _SetEnabledMso = new CreateFunction("ribbon界面禁用/启用复制", SetEnabledMso, []);

//ribbon界面隐藏/显示指定按钮
function SetVisibleMso() {
	var aa = app.CommandBars.SetVisibleMso('Copy',false);
	alert(aa);
}
var _SetVisibleMso = new CreateFunction("ribbon界面隐藏/显示复制", SetVisibleMso, []);

//经典界面隐藏/显示关闭所有文档、保存所有文档按钮
function menuvisibleF() {
	var aa = app.ActiveDocument.CommandBars.get_Item("File").Controls.get_Item("关闭所有文档(&L)").Visible = false;
	var bb = app.ActiveDocument.CommandBars.get_Item("File").Controls.get_Item("保存所有文档(&E)").Visible = false;
}
var _menuvisibleF = new CreateFunction("隐藏关闭/保存所有文档按钮", menuvisibleF, []);

function menuvisibleT() {
	var aa = app.ActiveDocument.CommandBars.get_Item("File").Controls.get_Item("关闭所有文档(&L)").Visible = true;
	var bb = app.ActiveDocument.CommandBars.get_Item("File").Controls.get_Item("保存所有文档(&E)").Visible = true;
}
var _menuvisibleT = new CreateFunction("显示关闭/保存所有文档按钮", menuvisibleT, []);

//插入本地/远程ofd文档图片
function insertOfdPicture() {
	var aa = app.MergeOfdDocument('/home/wps/桌面/数科.ofd');
	alert(aa);
}
var _insertOfdPicture = new CreateFunction("插入本地ofd文档图片", insertOfdPicture, []);

function insertOfdPicture0()
{
	var jsondata = {
		password:"123",
		isGetResponseHead:true,
		customHeadData:{
			"key1":"插入ofd文档图片1",
			"key2":"插入ofd文档图片2"
		}
	};
	var aa = app.MergeOfdDocument("http://10.90.128.242:8080/wps/测试.ofd",JSON.stringify(jsondata));
	alert(aa);
}
var _insertOfdPicture0 = new CreateFunction("插入远程ofd文档图片", insertOfdPicture0, []);