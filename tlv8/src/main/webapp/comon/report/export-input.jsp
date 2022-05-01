<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
	String context = request.getContextPath();
%>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<link rel="stylesheet" type="text/css" href="<%=context%>/comon/css/grid.main.css" >
		<style type="text/css">
		<!--
		* {
			margin: 0;
			padding: 0;
			list-style: none;
			font-family: Tahoma, Arial, Helvetica, Sans-serif;
		}
		
		table {
			margin: 0px auto;
			padding: 0;
			font-size: 11pt;
			border-collapse: collapse;
			table-layout: fixed;
			word-break: break-all;
		}
		
		table tr {
			height: 25px;
		}
		.grid td{
			border: 1px solid #eee;
		}
		-->
		</style>
		<script type="text/javascript" src="<%=context%>/comon/js/jquery/jquery.min.js"></script>
		<script type="text/javascript" src="<%=context%>/comon/js/comon.main.js"></script>
	</head>
	<body style="text-align: center;">
		<div id="impMainview">
			<table align="center" style="border:0; width:100%; height:100%; text-align: center">
				<tr>
					<td colspan="2" height="98%" valign="top">
						<div id="impstatesetting">
							<h1>导出失败！</h1>
							<h3>${requestScope.expStatus}</h3>
						</div>
					</td>
				</tr>
				<tr>
					<td align="center" colspan="2" height="25" style="padding-bottom: 5px;">
						<input class="xui-button" name="ensure" type="button" value="确定" onClick="justep.yn.portal.dailog.dailogCancel()"
							style="font-size: 11pt; line-height: 18px;" />
					</td>
				</tr>
			</table>
		</div>
	</body>
</html>
