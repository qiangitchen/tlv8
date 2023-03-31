<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
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
	</head>
	<body style="text-align: center;">
		<div id="impMainview">
			<table align="center" style="border:0; width:100%; height:100%; text-align: center">
				<tr>
					<td colspan="2" height="98%" valign="top">
						<div id="impstatesetting">
							<h1>导出失败！</h1>
							<h3>${expStatus}</h3>
						</div>
					</td>
				</tr>
				<tr>
					<td align="center" colspan="2" height="25" style="padding-bottom: 5px;">
						<input class="xui-button" name="ensure" type="button" value="确定" onClick="tlv8.portal.dailog.dailogCancel()"
							style="font-size: 11pt; line-height: 18px;" />
					</td>
				</tr>
			</table>
		</div>
	</body>
</html>
