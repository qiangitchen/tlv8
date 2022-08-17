<%@page import="com.tlv8.doc.clt.doc.DocDBHelper"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
	String fileid = request.getParameter("fileid");
	String host = DocDBHelper.queryDocHost();
	String url = host + "/repository/file/viewpdf/" + fileid + "/last/content";
	String durl = host + "/repository/file/view/" + fileid + "/last/content";
	String fileName = request.getParameter("fileName");
	if(fileName==null){
		fileName = "文件查看";
	}
%>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<title><%=fileName%></title>
		<link type="text/css" rel="stylesheet" href="../../../resources/jquery-easyui/themes/default/easyui.css" />
		<link type="text/css" rel="stylesheet" href="../../../resources/jquery-easyui/themes/icon.css" />
		<script type="text/javascript" src="../../../comon/js/jquery/jquery.min.js"></script>
		<script type="text/javascript" src="../../../comon/js/comon.main.js"></script>
		<script type="text/javascript" src="../../../resources/jquery-easyui/jquery.easyui.min.js"></script>
		<script type="text/javascript" src="../../../resources/jquery-easyui/locale/easyui-lang-zh_CN.js"></script>
	</head>
	<body style="overflow:hidden;">
		<div class="easyui-layout" fit="true">
			<div data-options="region:'north',split:false,border:false" style="height:35px; overflow: hidden; text-align: center; line-height: 35px;">
				<span style="color: red;">当前浏览器或操作系统不支持文件在线编辑，<a href="<%=durl%>" target="_blank">点击此处查看源文件</a></span>
			</div>
			<div data-options="region:'center',border:false">
				<iframe border="0" style="width: 100%; height: 100%;" frameborder="0" src="<%=url%>"></iframe>
			</div>
	</div>
</body>
</html>