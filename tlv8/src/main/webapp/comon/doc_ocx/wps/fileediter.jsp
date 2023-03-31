<%@page import="com.tlv8.doc.clt.doc.DocUtils"%>
<%@page import="com.tlv8.system.bean.ContextBean"%>
<%@page import="com.tlv8.system.controller.UserController"%>
<%@page import="com.tlv8.base.db.DBUtils"%>
<%@page import="java.util.ArrayList"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="java.util.Map"%>
<%@ page import="java.util.List"%>
<%@ page import="java.net.URLDecoder"%>
<%
	String docHost = "";
	String docHostSql = "select SURL from SA_DOCNAMESPACE where SID = 'defaultDocNameSpace'";
	List<Map<String,String>> dochostList = DBUtils.execQueryforList("system", docHostSql);
	if(dochostList.size()>0){
		Map docMap = dochostList.get(0);
		docHost = docMap.get("SURL").toString();
	}
	ContextBean contextbean = ContextBean.getContext(request);
	String loginName = contextbean.getPersonName();
	String titleName = "";
	String fileID = request.getParameter("fileid");
	String fileName = request.getParameter("fileName");
	String option = request.getParameter("option");
	/*
	if("view".equalsIgnoreCase(option)){
		response.sendRedirect("../tangerOffice/pspdfview.jsp?fileid="+fileID);
		return;
	}
	*/
	String loadurl = docHost+"/repository/file/download/"+fileID+"/"+contextbean.getPersonID()+"?"+DocUtils.getBizSecureParams();
	String comiturl = docHost+"/repository/file/wpsedit/"+fileID+"/"+contextbean.getPersonID()+"?"+DocUtils.getBizSecureParams();
	try{
		fileName = URLDecoder.decode(fileName,"UTF-8");
	}catch(Exception e){
	}
	if(fileName.lastIndexOf(".")>0){
		titleName = fileName.substring(0,fileName.lastIndexOf("."));
	}else{
		titleName = fileName;
	}
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html id="itemhtml">
	<head>
    	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
    	<title>WPS-Plugin For Linux</title>
    	<meta http-equiv="Pragma" content="no-cache" />
		<meta http-equiv="Cache-Control" content="no-cache" />
		<meta http-equiv="Expires" content="0" />
		<link type="text/css" rel="stylesheet" href="../../../resources/bootstrap/css/bootstrap.min.css"/> 
		<link type="text/css" rel="stylesheet" href="../../../resources/layui/css/layui.css"/>
		<link rel="stylesheet" type="text/css" href="wps.css"/>
		<script language="JavaScript" src="../../js/jquery/jquery.min.js"></script>
		<script language="JavaScript" src="../../js/comon.main.js"></script>
		<script type="text/javascript" src="../../../resources/bootstrap/js/bootstrap.min.js"></script> 
		<script type="text/javascript" src="../../../resources/layui/layui.js"></script>
		<script language="JavaScript" src="fileediter.js"></script>
		<script type="text/javascript">
			function colseWindow(){
				try{
					app.close();
					obj.Application.Quit();
				}catch (e) {
				}
				if("<%=option%>" != "view") {
					try{
						def_callBackFun("");
						tlv8.portal.closeWindow();
					}catch (e) {
					}
				}
				if("<%=option%>" == "view") {
					try{
						tlv8.portal.closeWindow();
					}catch (e) {
						window.opener = null;
						window.open('', '_self');
						window.close();
					}
				}
			}
			function saveToServer() {
				try{
					var aa = app.saveURL_s("<%=comiturl%>","<%=fileName%>");
					if(aa==true){
						layui.layer.msg("保存成功.");
					}else{
						layui.layer.alert("保存失败！");
					}
				}catch (e) {
					layui.layer.alert(e.message);
				}
			}
			var template = "<%=loadurl%>";
			var username = "<%=loginName%>";
		</script>
    </head>
    <body onLoad="InitFrame();" style="overflow: hidden;" id="demobody">
    	<%if(!"view".equalsIgnoreCase(option)){ %>
    	<div class="layui-row layui-col-space10" id="divLayout_bar">
    		<div class="layui-btn-container">
    			<button class="layui-btn layui-btn-xs" onclick="showRevision0()"><i class="layui-icon layui-icon-search"></i>显示痕迹</button>
				<button type="button" class="layui-btn layui-btn-xs" onclick="showRevision2()"><i class="layui-icon layui-icon-menu-fill"></i>隐藏痕迹</button>
				<button type="button" class="layui-btn layui-btn-xs layui-btn-normal" onclick="saveAs()"><i class="layui-icon layui-icon-export"></i>另存为</button>
				<button type="button" class="layui-btn layui-btn-xs layui-btn-warm" onclick="printwps()"><i class="layui-icon layui-icon-print"></i>打印</button>
				<button type="button" class="layui-btn layui-btn-xs layui-btn-disabled"><i class="layui-icon layui-icon-list"></i>套红</button>
				<button type="button" class="layui-btn layui-btn-xs layui-btn-normal" onclick="saveToServer()"><i class="layui-icon layui-icon-ok"></i>保存到服务器</button>
				<button type="button" class="layui-btn layui-btn-xs layui-btn-danger" onclick="colseWindow()"><i class="layui-icon layui-icon-close"></i>关闭</button>
    			<!-- 
    			<button type="button" class="layui-btn layui-btn-xs layui-btn-primary layui-border-black" style="float: right;" onclick="togofullScreen(this)"><i class="layui-icon layui-icon-screen-full"></i></button>
    			 -->
    		</div>
    	</div>
		<%} %>
    	<div id="iframediv">
			<div id="wps"></div>
    	</div>
    </body>
</html>