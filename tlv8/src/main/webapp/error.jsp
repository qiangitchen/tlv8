<%@page import="java.util.Enumeration"%>
<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" isErrorPage="true"%>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<title><%=response.getStatus()%>错误</title>
		<style><!--H1 {font-family:Tahoma,Arial,sans-serif;color:white;background-color:#003366;font-size:22px;} H2 {font-family:Tahoma,Arial,sans-serif;color:white;background-color:#003366;font-size:16px;} H3 {font-family:Tahoma,Arial,sans-serif;color:white;background-color:#003366;font-size:14px;} BODY {font-family:Tahoma,Arial,sans-serif;color:black;background-color:white;} B {font-family:Tahoma,Arial,sans-serif;color:white;background-color:#003366;} P {font-family:Tahoma,Arial,sans-serif;background:white;color:black;font-size:12px;}A {color : black;}A.name {color : black;}HR {color : #003366;}--></style>
	</head>
	<body>
		<h1>出错了</h1>
		<h2>错误编号:<%=response.getStatus()%></h2>
		<h3>错误信息:</h3>
		<%=exception.getMessage()%>
		<br>
		<pre>
			<%
			//response.getWriter().println("Exception: " + exception); 
			//if(exception != null)
			//{
			   //response.getWriter().println("<pre>"); 
			   //exception.printStackTrace(response.getWriter()); 
			   //response.getWriter().println("</pre>"); 
			//}
			//Sys.packErrMsg(request.getAttribute("javax.servlet.forward.request_uri").toString());
			exception.printStackTrace();
			%>
		</pre>
		<h3>请求信息</h3>
		<%
			out.println("request.attribute[javax.servlet.forward.request_uri']="+request.getAttribute("javax.servlet.forward.request_uri"));    
		%>
		<HR size="1" noshade="noshade"><h3>TLv8 Web Server</h3>
	</body>
</html>