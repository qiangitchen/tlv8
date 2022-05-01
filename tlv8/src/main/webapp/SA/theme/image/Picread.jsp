<%@page import="com.tlv8.docs.DocSvrUtils"%>
<%@ page language="java" import="java.util.*"
	contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ page language="java" import="java.io.*"%>
<html>
	<head>
		<META http-equiv="Content-Type" content="text/html; charset=utf-8">
	</head>
	<body>
		This is my PIC page.
		<br>
		<%
			try {
				String fileID = request.getParameter("fileID");
				response.setContentType("image/jpeg");
				OutputStream outs = response.getOutputStream();
				DocSvrUtils.downloadFile(fileID, outs);
				outs.flush();
			} catch (Exception e) {
				e.printStackTrace();
			}
			out.clear();
			out = pageContext.pushBody();
		%>
	</body>
</html>