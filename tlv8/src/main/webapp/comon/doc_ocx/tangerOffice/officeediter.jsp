<%@page import="com.alibaba.fastjson.JSON"%>
<%@page import="com.alibaba.fastjson.JSONArray"%>
<%@page import="com.tlv8.doc.clt.doc.DocUtils"%>
<%@page import="com.tlv8.system.bean.ContextBean"%>
<%@page import="com.tlv8.base.db.DBUtils"%>
<%@page import="java.util.ArrayList"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.Map"%>
<%@ page import="java.util.List"%>
<%@ page import="java.net.URLDecoder"%>
<%
	String docHost = "";
	String docHostSql = "select SURL from SA_DOCNAMESPACE where SID = 'defaultDocNameSpace'";
	List<Map<String,String>> dochostList = DBUtils.execQueryforList("system", docHostSql);
	if(dochostList.size()>0){
		Map<String,String> docMap = dochostList.get(0);
		docHost = docMap.get("SURL");
	}
	ContextBean contextbean = ContextBean.getContext(request);
	String loginName = contextbean.getPersonName();
	String titleName = "";
	String fileID = request.getParameter("fileID");
	String fileName = request.getParameter("fileName");
	String option = request.getParameter("option");
	/*非IE浏览器跳转*/
	String browser = request.getHeader( "USER-AGENT" );
	if(browser.contains("Windows")){
		//Windows将文件转换成pdf查看
		if (browser.indexOf( "Chrome" ) > 0 || browser.indexOf( "Firefox") > 0 || browser.indexOf("AppleWebKit")>0) {
			response.sendRedirect("pspdfview.jsp?fileid="+fileID+"&fileName="+fileName+"&option="+option);
		}
	}else {
		String nodeName = fileName;
		try{
			nodeName = URLDecoder.decode(nodeName,"UTF-8");
		}catch(Exception e){
		}
		try{
			nodeName = nodeName.toLowerCase();
		}catch(Exception e){
		}
		if(nodeName.endsWith(".xls")||nodeName.endsWith(".xlsx")||nodeName.endsWith(".et")){
			response.sendRedirect("../wps/etfileediter.jsp?fileid="+fileID+"&fileName="+fileName+"&option="+option);
			return;
		}if(nodeName.endsWith(".ppt")||nodeName.endsWith(".pptx")||nodeName.endsWith(".dps")){
			response.sendRedirect("../wps/dpsfileediter.jsp?fileid="+fileID+"&fileName="+fileName+"&option="+option);
			return;
		}else{
			response.sendRedirect("../wps/fileediter.jsp?fileid="+fileID+"&fileName="+fileName+"&option="+option);
			return;
		}
	}
	String comiturl = docHost+"/repository/file/edit/"+fileID+"/"+contextbean.getPersonID()+"?"+DocUtils.getBizSecureParams();
	try{
		fileName = URLDecoder.decode(fileName,"UTF-8");
	}catch(Exception e){
	}
	if(fileName.lastIndexOf(".")>0){
		titleName = fileName.substring(0,fileName.lastIndexOf("."));
	}else{
		titleName = fileName;
	}
	String dbkey = request.getParameter("dbkey");
	String tablename = request.getParameter("tablename");
	String billid = request.getParameter("billid");
	String cellname = request.getParameter("cellname");
	String sql = "update SA_DOCNODE set SEDITORFID='"
		+ contextbean.getCurrentPersonFullID()
		+ "',SEDITORNAME='"
		+ contextbean.getCurrentPersonName()
		+ "',SEDITORDEPTNAME='"
		+ contextbean.getCurrentDeptName()
		+ "' where SFILEID = '" + fileID + "'";
	DBUtils.execUpdateQuery("system", sql);
	String	readModelRealPath="FRHFILE";////红头模板的实际保存路径 如E:\\doc...
	String  readModelName="FNAME";////红头模板的显示名 如州政府办公室文件
	List<Map<String,String>> readModelList = new ArrayList<Map<String,String>>();
	try{
		String orgid = contextbean.getCurrentOgnID();
		String  sqlRedModel="select t."+readModelName+",t."+readModelRealPath+" from OA_DOC_REDHEADPERM t where t.FOGNID ='"+orgid+"'";
		//System.out.println("查询模板SQL："+sqlRedModel);
		readModelList = DBUtils.execQueryforList("oa", sqlRedModel);
	}catch(Exception eh){
	}
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
		<meta http-equiv="content-type" content="text/html;charset=utf-8" />
		<title>文件编辑-阮航控件</title>
		<meta HTTP-EQUIV="Pragma" content="no-cache" />
		<meta HTTP-EQUIV="Cache-Control" content="no-cache" />
		<meta HTTP-EQUIV="Expires" content="0" />
		<script LANGUAGE="JavaScript" src="../../js/jquery/jquery.min.js"></script>
		<script LANGUAGE="JavaScript" src="../../js/comon.main.js"></script>
		<script LANGUAGE="JavaScript" src="ocx.js"></script>
		<script type="text/javascript">
			var TANGER_OCX_OBJ;
			//打开正文 需要返回文件流
			function openDoc(){ 
				$("#fileID").val("<%=fileID%>"); 
				$("#dbkey").val("<%=dbkey%>"); 
				$("#tablename").val("<%=tablename%>"); 
				$("#billid").val("<%=billid%>");
				$("#cellname").val("<%=cellname%>");
				TANGER_OCX_OBJ = document.getElementById("TANGER_OCX");
				if("<%=option%>" != "view") {
					TANGER_OCX_OBJ.FullScreenMode=true;
				}
				//var template = getdocServerAction('/root','/repository/file/view/<%=fileID%>/last/content',true);
				var template = getdocServerAction('/root','/repository/file/download/<%=fileID%>/last/content',true);
				//console.log(template);
				TANGER_OCX_OBJ.BeginOpenFromURL(template,true);	
			}
			
			function closeObject(){
				TANGER_OCX_OBJ.FullScreenMode=false;
				TANGER_OCX_OBJ.close();//关闭word 进程
			}
			
			function colseWindow(){
				TANGER_OCX_OBJ.FullScreenMode=false;
				TANGER_OCX_OBJ.close();//关闭word 进程
				if("<%=option%>" != "view") {
					try{
						def_callBackFun("");
						tlv8.portal.closeWindow();
					}catch (e) {
					}
				}
				if("<%=option%>" == "view") {
					window.opener = null;
					window.open('', '_self');
					window.close();
				}
			}
			function def_callBackFun(a) {
				var callerName = tlv8.RequestURLParam.getParam("callerName");
				try {
					var t = topparent.$.jpolite.getTab(callerName);
					var containers = t.tabLayoutDiv.get(0);
					var pTabIframe = containers.getElementsByTagName("iframe")[0].contentWindow;
					//pTabIframe.location.reload();
					pTabIframe.rahgereditercalback();//编辑回调
				} catch (e) {
				}
			}
			function saveToServer() {
				var f = document.getElementById('myForm');
				var url = "<%=comiturl%>";
				console.log(url);
				try {
					f.setAttribute('action', url);
				} catch (e) {
					f.attributes['action'].value = url;
				}
				TANGER_OCX_OBJ.SaveToURL(url, "upload", "", "newdoc.doc", 0);
			}
		
			function redHead() {
				//判断是否选择红头模板
				if (readModelRealPath == "" || readModelRealPath.length == 0) {
					alert("请选择红头模板");
					return;
				}
		
				try {
					//选择对象当前文档的所有内容
					var curSel = TANGER_OCX_OBJ.ActiveDocument.Application.Selection;
					TANGER_OCX_SetMarkModify(false);
					curSel.WholeStory();
					curSel.Cut();
		
					//插入模板
					//TANGER_OCX_OBJ.activeDocument.PageSetup.DifferentFirstPageHeaderFooter = true; //设置页眉首页不同
					TANGER_OCX_OBJ.AddTemplateFromURL(readModelRealPath);
		
					var BookMarkName = "docContext";
					if (!TANGER_OCX_OBJ.ActiveDocument.BookMarks.Exists(BookMarkName)) {
						alert("Word 模板中不存在名称为：docContext的书签！请在模板中的正文开始处添加一个docContext的书签！");
						return;
					}
		
					var bkmkObj = TANGER_OCX_OBJ.ActiveDocument.BookMarks(BookMarkName);
					var saverange = bkmkObj.Range
					saverange.Paste();
					TANGER_OCX_OBJ.ActiveDocument.Bookmarks
							.Add(BookMarkName, saverange);
		
					// 替换所有域内容的书签
		
					//if(BookMarkName=="yinfa_date" ){
					var myDate = new Date();
					TANGER_OCX_OBJ.ActiveDocument.Bookmarks("yinfa_date").Range.Text = myDate
							.getFullYear()
							+ '年'
							+ (parseInt(myDate.getMonth()) + 1)
							+ '月'
							+ myDate.getDate() + '日';
					//}  
					TANGER_OCX_SetMarkModify(true);
				} catch (err) {
					//alert("错误：" + err.number + ":" + err.description);
				}
				issetRedHead = true;
			}
		</script>
		<style>
			button.op {
				width: 120px;
				background-color: #9DC2DB;
				border: 1px #EEEEEE solid;
				cursor: pointer;
			}
		</style>
	</head>
	<body onLoad="openDoc();AddMyMenuItems();" style="overflow: hidden;" onunload="closeObject()">
		<form id="myForm" METHOD="POST" ENCTYPE="multipart/form-data">
			<table width="100%" border="0">
				<tr>
					<td>
					<input type="hidden" id="fileID" name="fileID" /> 
					<input type="hidden" id="dbkey" name="dbkey" /> 
					<input type="hidden" id="tablename" name="tablename" /> 
					<input type="hidden" id="billid" name="billid"  /> 
					<input type="hidden" id="cellname" name="cellname" />
					</td>
				</tr>
			</table>
			<table width=100% height=100% border=1 cellpadding=0 cellspacing=0
				style="border: 1px #9dc2db solid">
				<tr>
					<td valign=top width=100%>
						<object id="TANGER_OCX"
							classid="clsid:C9BC4DFF-4248-4a3c-8A49-63A7D317F404"
							codebase="OfficeControl.cab#version=5,0,2,1" width="100%"
							height="100%">
							<param name="BorderStyle" value="1">
							<param name="CustomToolBar" value="1">
							<param name="BorderColor" value="14402205">
							<param name="TitlebarColor" value="14402205">
							<param name="TitlebarTextColor" value="0">
							<param name="Menubar" value="true">
							<param name="Caption" value="痕迹保留组件">
							<param name="MenubarColor" value="14402205">
							<param name="MenuBarStyle" value="3">
							<param name="MenuButtonStyle" value="7">
							<param name="CustomMenuCaption" value="套红模板">
							<param name="filenew" value="0">
							<param name="IsNoCopy" value="0">
							<param name="ProductCaption" value="北京起步科技有限公司">
							<param name="ProductKey"
								value="82A976172B476F3EEFFDBC144588EB4378D6A76E">
							<SPAN STYLE="color: red">不能装载文档控件。请在检查浏览器的选项中检查浏览器的安全设置。</SPAN>
						</object> 
						<script language="JScript">
							//红头模板的实际保存路径 全局js变量。
							var readModelRealPath="";
							var issetRedHead=false;
							var issetYZPD=false;
						</script> 
						<script language="JScript" for="TANGER_OCX" event="AfterOpenFromURL(doc)">
							try{
							   //设置试图显示100%
							   TANGER_OCX_OBJ.ActiveDocument.ActiveWindow.ActivePane.View.Zoom.Percentage = 100;
							   //设置试图为页面试图
							   TANGER_OCX_OBJ.ActiveDocument.ActiveWindow.ActivePane.View.Type =3;
							   //设置悬停显示工具条
							   //TANGER_OCX_OBJ.IsShowToolMenu = false;
							   TANGER_OCX_OBJ.IsShowFullScreenButton=false; //全屏按钮
							   TANGER_OCX_OBJ.ActiveDocument.ActiveWindow.DisplayScreenTips =1;
							   TANGER_OCX_ShowRevisions(false);
							   TANGER_OCX_SetMarkModify(true); 
							   TANGER_OCX_OBJ.EnableFileCommand(2) =false;
							   //TANGER_OCX_OBJ.Titlebar=false;
							}catch(e){
						
							}

						</script> 
						<script language="JScript" for=TANGER_OCX event="OnDocumentClosed()">
							TANGER_OCX_bDocOpen = false;
						</script> 
						<script language="JScript">
	
							var TANGER_OCX_str;
							var TANGER_OCX_obj;
						
						</script> 
						<script language="JScript" for=TANGER_OCX
							event="OnDocumentOpened(TANGER_OCX_str,TANGER_OCX_obj)">
							TANGER_OCX_OnDocumentOpened(TANGER_OCX_str,TANGER_OCX_obj)
							TANGER_OCX_SetDocUser('<%=java.net.URLDecoder.decode(loginName,"UTF-8")%>');
							//没有发文机要文书权限的人，隐藏盖章按钮
							var strURL=window.location.href; 
						    var rolesMatch=strURL.match(/roles=(.[^&]+)/);
						    var roles="";
						    if(rolesMatch!=null){
								roles=rolesMatch[1];
						    }
						    if(roles.indexOf("send_confidential")<0 || rolesMatch==null){
						    	if("<%=option%>"!="view"){
						      	 	SetCustomToolButtonStatus(0,false,false);
						       		SetCustomToolButtonStatus(1,false,false);
						    	}else{
						    		TANGER_OCX_AcceptAllRevisions();
						    	}
							}
						    /*
						    try
							{
								with(TANGER_OCX_OBJ.ActiveDocument.PageSetup)
								{
									TopMargin = TANGER_OCX_OBJ.ActiveDocument.Application.CentimetersToPoints(3.2);//页面上边距
									BottomMargin =TANGER_OCX_OBJ.ActiveDocument.Application.CentimetersToPoints(2.2);//页面下边距
									LeftMargin = TANGER_OCX_OBJ.ActiveDocument.Application.CentimetersToPoints(2.8);//页面左边距
									RightMargin = TANGER_OCX_OBJ.ActiveDocument.Application.CentimetersToPoints(2.6);//页面右边距
								
								} 
						        var font=TANGER_OCX_OBJ.ActiveDocument.Styles(-1).Font;
						        font.NameFarEast = "仿宋_GB2312";
						        font.Size =16;
								with(TANGER_OCX_OBJ.ActiveDocument.Application)
								{				
									 //Selection.WholeStory();
									 //Selection.Font.Name = "仿宋_GB2312";
									 //Selection.Font.Size = 16;
									 
								}      
								checkDoc();
							}catch(e){
							}
							*/
							</script> 
							<script language="JScript" for="TANGER_OCX" event="OnCustomMenuCmd(btnPos,btnCaption,btnCmdid)">
								//alert("第" + btnPos +"个菜单项,menuID="+btnCmdid+",菜单标题为\""+btnCaption+"\"的命令被执行.");
								//动态添加红头模板点击选择后的相应函数
							      switch(btnCmdid){	  
									  <%for(int i=0;i<readModelList.size();i++){
										Map<String,String> readModel_Map = readModelList.get(i); 
										try{
											String fileInfo = readModel_Map.get(readModelRealPath).toString();
											JSONArray json = JSON.parseArray(fileInfo);
											String hfileID = json.getJSONObject(0).getString("fileID");
									  %>
									    case <%=i%>:
										    readModelRealPath = getdocServerAction('/root','/repository/file/view/<%=hfileID%>/last/content',true);;
											break;
									  <%
										}catch(Exception re){
										}
									  }%>
								  		default: 
									  		break;
								  }
							</script>
							<script language="JScript" for="TANGER_OCX" event="OnCustomToolBarCommand(btnIdx)">
								switch(btnIdx)//去掉盖章处理
								{
									case 0://盖章
										if("<%=option%>"=="view"){
											colseWindow();
											break;
										}
										addSeal();
										break;
									case 1://手写
										handWrite();
										break;
									case 2://显示痕迹
										TANGER_OCX_ShowRevisions(true);
									break;
									case 3://隐藏痕迹
										TANGER_OCX_ShowRevisions(false);
									break;
									case 4://另存为
									      savetoredfile();
									break;
									case 5://打印
									     PrintRedDoc();;
									break;
									case 6://套红	
										redHead();
									break;
									case 7://保存服务器
										saveToServer();
									break;
									case 8://关闭
										colseWindow();
										break;
									break;
									default: break;
}

							</script> 
							<script language="JScript">
					var i=0;
					function bShow()
					{
					  if(i++%2==0){
					  	TANGER_OCX_OBJ.TitleBar = false;
					  }
					  else
					    TANGER_OCX_OBJ.TitleBar = true;       
					}
					function TANGER_OCX_SetDocUser(cuser)
					{
						try{
							with(TANGER_OCX_OBJ.ActiveDocument.Application)
							{
								UserName = cuser;
							}	
						}catch(e){
						}
					}
					 //打印原文件
					 function PrintRedDoc()
					  {
					       var protectType=TANGER_OCX_OBJ.ActiveDocument.ProtectionType;
						   if(protectType==2)
						   {
						       TANGER_OCX_OBJ.EnableFileCommand(5) =true; 
						       TANGER_OCX_OBJ.EnableFileCommand(7) =true;
						       TANGER_OCX_OBJ.EnableFileCommand(6) =true;
							   TANGER_OCX_PrintDoc();
						   }
						   else
						   {
							TANGER_OCX_ShowRevisions(false);
							//TANGER_OCX_ChgLayout();
							TANGER_OCX_PrintRevisions(false);
							TANGER_OCX_OBJ.EnableFileCommand(5) =true; 
						    TANGER_OCX_OBJ.EnableFileCommand(7) =true;
						    TANGER_OCX_OBJ.EnableFileCommand(6) =true;
							TANGER_OCX_PrintDoc();
							}    
					  }
					  //打印黑文件
					 function PrintBlackDoc()
					  {
						 var protectType=TANGER_OCX_OBJ.ActiveDocument.ProtectionType;	   
							   if(protectType==2)
							   {
								  try
								  {
										TANGER_OCX_OBJ.ActiveDocument.Unprotect();
										protectType=0;
								  }
								  catch(error)
								  {
									  alert("文件受密码保护,不能另存");
								  }
							   }
							   
							   if(protectType==2)
							   {
								  return;
							   }
							   var ShapesCount=TANGER_OCX_OBJ.ActiveDocument.Shapes.Count; 
							   var i; 
							   for (i = 1; i <= ShapesCount; i++) 
							   { 
								 try 
								   { 
									 TANGER_OCX_OBJ.ActiveDocument.Shapes(i).PictureFormat.ColorType =2;
									
								   } 
								 catch(ex) {} 
							   } 
							   var InlineShapesCount=TANGER_OCX_OBJ.ActiveDocument.InlineShapes.Count; 
							   for (i = 1; i <= InlineShapesCount; i++) 
							   { 
								  try 
								  { 
									TANGER_OCX_OBJ.ActiveDocument.InlineShapes(i).PictureFormat.ColorType =2; 
								  } 
								  catch(ex) {} 
							   } 
							   
							   //设置文本框内的字体颜色为黑色
							   var app=TANGER_OCX_OBJ.activeDocument.Application;
							   var scount = TANGER_OCX_OBJ.activeDocument.Shapes.Count;
							   for(var i=1;i<=scount;i++){
								   if(TANGER_OCX_OBJ.activeDocument.Shapes(i).TYPE==17){
									   TANGER_OCX_OBJ.ActiveDocument.Shapes(i).Select();
									   app.Selection.Font.Color = 0;
								   }
							   }
							   TANGER_OCX_OBJ.EnableFileCommand(3) =true; 
							   TANGER_OCX_OBJ.EnableFileCommand(4) =true;
							   TANGER_OCX_OBJ.EnableFileCommand(5) =true; 
							   TANGER_OCX_OBJ.EnableFileCommand(7) =true;
							   TANGER_OCX_OBJ.EnableFileCommand(6) =true;
							   TANGER_OCX_OBJ.SetReadOnly(false);
							   TANGER_OCX_OBJ.ActiveDocument.AcceptAllRevisions();	  
							   TANGER_OCX_OBJ.ActiveDocument.Protect(3,false,"QNJ&^%GHJI*()KLOI*&^",false,true); 
							   TANGER_OCX_PrintDoc();
							   openDoc();
					  }
					
					 //保存副本
					  function savetolocalfile() 
					  {  
					   var protectType=TANGER_OCX_OBJ.ActiveDocument.ProtectionType;
					   if(protectType==2)
					   {
					      try
						  {
					      		TANGER_OCX_OBJ.ActiveDocument.Unprotect();
								protectType=0;
						  }
						  catch(error)
						  {
						      alert("文件受密码保护,不能另存");
						  }
					   }
					   
					   if(protectType==2)
					   {
					      return;
					   }
					   var ShapesCount=TANGER_OCX_OBJ.ActiveDocument.Shapes.Count; 
					   var i; 
					   for (i = 1; i <= ShapesCount; i++) 
					   { 
					     try 
					       { 
					         TANGER_OCX_OBJ.ActiveDocument.Shapes(i).PictureFormat.ColorType =2;
							
					       } 
					     catch(ex) {} 
					   } 
					   var InlineShapesCount=TANGER_OCX_OBJ.ActiveDocument.InlineShapes.Count; 
					   for (i = 1; i <= InlineShapesCount; i++) 
					   { 
					      try 
					      { 
					        TANGER_OCX_OBJ.ActiveDocument.InlineShapes(i).PictureFormat.ColorType =2; 
					      } 
					      catch(ex) {} 
					   } 
					   
					   //设置文本框内的字体颜色为黑色
					   var app=TANGER_OCX_OBJ.activeDocument.Application;
					   var scount = TANGER_OCX_OBJ.activeDocument.Shapes.Count;
					   for(var i=1;i<=scount;i++){
					       if(TANGER_OCX_OBJ.activeDocument.Shapes(i).TYPE==17){
						       TANGER_OCX_OBJ.ActiveDocument.Shapes(i).Select();
							   app.Selection.Font.Color = 0;
						   }
					   }
					   
					   TANGER_OCX_OBJ.EnableFileCommand(3) =true; 
					   TANGER_OCX_OBJ.EnableFileCommand(4) =true;
					   TANGER_OCX_OBJ.SetReadOnly(false);
					   TANGER_OCX_OBJ.ActiveDocument.AcceptAllRevisions();
					   if(TANGER_OCX_OBJ.DocType==1){
						   TANGER_OCX_OBJ.ActiveDocument.Protect(3,false,"QNJ&^%GHJI*()KLOI*&^",false,true); 
						   var dg = TANGER_OCX_OBJ.ActiveDocument.Application.Dialogs(84);
						   var title="<%=java.net.URLDecoder.decode(titleName,"UTF-8")%>";
						   dg.Name=title+"副本.doc";
						   dg.show();
						   openDoc();
					   }
					 } 
					
					  
					  //保存原本
					  function savetoredfile() 
					  {  
						   var protectType=TANGER_OCX_OBJ.ActiveDocument.ProtectionType;
						   if(protectType==2)
						   {
							  try
							  {
									TANGER_OCX_OBJ.ActiveDocument.Unprotect("1");
									protectType=0;
							  }
							  catch(error)
							  {
								  alert("文件受密码保护,不能另存");
							  }
						   }else if(protectType == 3){
					
							   try{
					
								TANGER_OCX_OBJ.ActiveDocument.Unprotect("QNJ&^%GHJI*()KLOI*&^");//这里传保护文档的密码 
							   }
							   catch(error){
					
								  alert("文件受密码保护,不能另存");
							   }
						   }
						   if(protectType==2 || protectType == 3)
						   {
							  return;
						   }		   
						   TANGER_OCX_OBJ.EnableFileCommand(3) =true; 
						   TANGER_OCX_OBJ.EnableFileCommand(4) =true;
						   TANGER_OCX_OBJ.ActiveDocument.AcceptAllRevisions();
						   TANGER_OCX_OBJ.SetReadOnly(false);
						   TANGER_OCX_OBJ.ActiveDocument.Protect(3,false,"QNJ&^%GHJI*()KLOI*&^",false,true); 
						   var title="<%=java.net.URLDecoder.decode(titleName,"UTF-8")%>";
						   if(TANGER_OCX_OBJ.DocType==1)
						   {
							   if(protectType== -1){
								   try {
									   TANGER_OCX_OBJ.ActiveDocument.Unprotect("QNJ&^%GHJI*()KLOI*&^");
								   }
								   catch(error){}
							   }
								var dg = TANGER_OCX_OBJ.ActiveDocument.Application.Dialogs(84);
								dg.Name=title+"原本.doc";
								dg.show();  
						   }else{
								TANGER_OCX_OBJ.WebFileName = title+"原本.doc";
								var dg = TANGER_OCX_OBJ.ShowDialog(3);
						   }  
						   openDoc();
					 } 
					 //添加自定义菜单子项
					function AddMyMenuItems()
					{
						 var option = "<%=option%>";
					 	try
						{
							//在自定义主菜单中增加菜单项目
							if(option == "edit"){
								TANGER_OCX_OBJ.AddCustomToolButton("电子盖章",-1);
								TANGER_OCX_OBJ.AddCustomToolButton("全屏手写",-1);
								TANGER_OCX_OBJ.AddCustomToolButton("显示痕迹",6);
								TANGER_OCX_OBJ.AddCustomToolButton("隐藏痕迹",0);
								TANGER_OCX_OBJ.AddCustomToolButton("另存为",4);
								TANGER_OCX_OBJ.AddCustomToolButton("打印",9);
								TANGER_OCX_OBJ.AddCustomToolButton("套红",11);
								TANGER_OCX_OBJ.AddCustomToolButton("保存服务器",5);
							}
							TANGER_OCX_OBJ.AddCustomToolButton("关闭",2);
							//动态加载红头模板选择菜单
							<%for(int i=0;i<readModelList.size();i++){
								Map<String,String> redMapData=readModelList.get(i);		
							%>		
							TANGER_OCX_OBJ.AddCustomMenuItem("<%=redMapData.get(readModelName)%>",false,true,<%=i%>);
							<%	
							}
							%>
						}
					   	catch(err){
							alert("不能创建新对象："+ err.number +":" + err.description);
						}
						finally{
						}
						
					}
					//盖章
					function addSeal(){
					  try{
						   //TANGER_OCX_OBJ.ActiveDocument.AcceptAllRevisions();
						   document.all.DES1.DoAction(1);
						   issetYZPD=true;
						   TANGER_OCX_OBJ.ActiveDocument.AcceptAllRevisions();
					  }catch(e){
						 alert("盖章失败！");
					  }
					}
					//手写
					function handWrite ()
					{
					  try{
					       TANGER_OCX_OBJ.ActiveDocument.AcceptAllRevisions();
						   document.all.DES1.DoAction(2);
					    }catch(e){
						   alert("手写失败！");
					    }
					}
					function getdocServerAction(docPath, urlPattern, isFormAction){
							return "<%=docHost%>" + urlPattern;
					}
					function checkDoc(){
						var sealName;
						 sealName=document.all.DES1.GetNextSeal("");
						while(sealName  != ""){ 
							document.all.DES1.SealDocVerify(sealName, 0);  //文档验证方法
							sealName = document.all.DES1.GetNextSeal(sealName);
						}	
					}
					</script>
					</td>
				</tr>
			</table>
		</form>
	</body>
</html>