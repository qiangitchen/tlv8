<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.io.OutputStream"%>
<%@ page import="java.io.InputStream"%>
<%
	String context = request.getContextPath();
%>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<link type="text/css" rel="stylesheet" href="<%=context%>/resources/layui/css/layui.css" />
		<link rel="stylesheet" href="<%=context%>/resources/bootstrap/css/bootstrap.min.css" media="all">
		<script type="text/javascript" src="<%=context%>/common/jQuery/jquery.min.js"></script>
		<script type="text/javascript" src="<%=context%>/common/js/base.js" ></script>
		<script type="text/javascript" src="<%=context%>/resources/bootstrap/js/bootstrap.min.js"></script>
		<script type="text/javascript" src="<%=context%>/resources/layui/layui.js"></script>
		<SCRIPT type="text/javascript">
			window.isWriteLog = false;
			tlv8.portal.dailog.dailogEngin();
		</SCRIPT>
	</head>
	<body>
		<%
			try{
				response.setContentType("application/vnd.ms-excel");
				InputStream inpbs = (InputStream)request.getAttribute("inpbs");
				OutputStream outs = response.getOutputStream();
				int bytesRead;   
				while((bytesRead = inpbs.read())!=-1){   
					outs.write(bytesRead);   
	            } 
				outs.flush();
				outs.close();
				inpbs.close();
				out.clear();
				out = pageContext.pushBody();
			}catch(Exception e){
				
			}
		%>
	</body>
</html>
