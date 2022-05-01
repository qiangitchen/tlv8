<%@ page language="java" import="java.util.*"
	contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%
	String context = request.getContextPath();
%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
</head>
<body>
<h3>你还没有登录或登录已失效，请您重新登录!</h3>
<script type="text/javascript">
window.top.location.href = "<%=context%>/alertlogin.jsp";
</script>
</body>
</html>