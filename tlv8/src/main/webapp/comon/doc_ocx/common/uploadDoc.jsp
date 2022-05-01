<%@page import="com.tlv8.system.help.ResponseProcessor"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
	System.out.println("upload... ...sucecess");
	String docName = request.getParameter("fileName");
	String kind = request.getParameter("Kind");
	String size = request.getParameter("Size");
	String cacheName = request.getParameter("fileID");
	System.out.println(response.toString());
%>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<%
			String text = "{docName:\"" + docName + "\",kind:\"" + kind
					+ "\",size:\"" + size + "\",cacheName:\"" + cacheName
					+ "\"}";
			ResponseProcessor.renderText(response, text);
		%>
	</head>

	<body>
	</body>
</html>
