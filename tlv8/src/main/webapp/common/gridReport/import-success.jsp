<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
	String context = request.getContextPath();
%>
<html>
	<head>
		<title>导入成功</title>
		<link type="text/css" rel="stylesheet" href="<%=context%>/resources/layui/css/layui.css" />
		<link rel="stylesheet" href="<%=context%>/resources/bootstrap/css/bootstrap.min.css" media="all">
		<script type="text/javascript" src="<%=context%>/common/jQuery/jquery.min.js"></script>
		<script type="text/javascript" src="<%=context%>/common/js/base.js" ></script>
		<script type="text/javascript" src="<%=context%>/resources/bootstrap/js/bootstrap.min.js"></script>
		<script type="text/javascript" src="<%=context%>/resources/layui/layui.js"></script>
	</head>
	<body style="text-align: center">
		<br />
		<a> ${impStatus} </a>
		<div style="width: 100%; height: 200px;">
			<ul>
				<li>
					记录一共:
					${totalCount}
					行
				</li>
				<li>
					成功导入:
					${fokCount}
					行
				</li>
			</ul>
		</div>
		<div style="width: 100%; text-align: center;">
			<button class="btn btn-primary" name="submit" type="submit" onClick="tlv8.portal.dailog.dailogEngin()">确定</button>
		</div>
	</body>
</html>