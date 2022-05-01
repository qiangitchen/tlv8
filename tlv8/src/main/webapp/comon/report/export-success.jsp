<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.io.OutputStream"%>
<%@ page import="java.io.InputStream"%>
<%
	String context = request.getContextPath();
%>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<script type="text/javascript" src="<%=context%>/comon/js/jquery/jquery.min.js"></script>
		<script type="text/javascript" src="<%=context%>/comon/js/comon.main.js"></script>
		<SCRIPT type="text/javascript">
			window.isWriteLog = false;
			justep.yn.portal.dailog.dailogEngin();
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
