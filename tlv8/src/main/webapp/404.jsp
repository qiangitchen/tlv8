<%@page import="java.text.SimpleDateFormat"%>
<%@ page contentType="text/html; charset=UTF-8" %>
<%@ page import="java.io.*" %>
<%@ page import="java.util.*" %>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<title>404错误</title>
		<style><!--H1 {font-family:Tahoma,Arial,sans-serif;color:white;background-color:#003366;font-size:22px;} H2 {font-family:Tahoma,Arial,sans-serif;color:white;background-color:#003366;font-size:16px;} H3 {font-family:Tahoma,Arial,sans-serif;color:white;background-color:#003366;font-size:14px;} BODY {font-family:Tahoma,Arial,sans-serif;color:black;background-color:white;} B {font-family:Tahoma,Arial,sans-serif;color:white;background-color:#003366;} P {font-family:Tahoma,Arial,sans-serif;background:white;color:black;font-size:12px;}A {color : black;}A.name {color : black;}HR {color : #003366;}--></style>
	</head>
	<body>
	<h1>404错误</h1>
	<h2>请求的页面不存在!</h2>
	<HR size="1" noshade="noshade">
	<h3>请求信息</h3>
	<%
		String einfo = "request.attribute[javax.servlet.forward.request_uri']="+request.getAttribute("javax.servlet.forward.request_uri");
		out.println(einfo);
		/*
		Enumeration<String> attributeNames = request.getAttributeNames();
		String datestart = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()) + " ";
		System.err.println(datestart + request.getAttribute("javax.servlet.error.status_code")+"错误! 请求信息:"); 
	    while (attributeNames.hasMoreElements())
	    {
	        String attributeName = attributeNames.nextElement();
	        Object attribute = request.getAttribute(attributeName);
	   		System.err.println("request.attribute['" + attributeName + "']=" + attribute); 
	    }
	    */
	%>
	<HR size="1" noshade="noshade"><h3>TLv8 Web Server</h3>
	</body>
</html>