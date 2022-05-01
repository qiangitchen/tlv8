<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<html>
	<head>
		<title>导入成功</title>
		<link rel="stylesheet" type="text/css" href="../../comon/css/grid.main.css" >
		<script type="text/javascript" src="../../comon/js/jquery/jquery.min.js"></script>
		<script src="../../comon/js/comon.main.js" type="text/javascript"></script>
		<script src="js/imp.js" type="text/javascript"></script>
		<style type="text/css">
		<!--
		* {
			list-style: none;
			font-family: Tahoma, Arial, Helvetica, Sans-serif;
		}
		
		table {
			margin: 0px auto;
			padding: 0;
			font-size: 11pt;
			color: #333333;
			border-collapse: collapse;
			table-layout: fixed;
			word-break: break-all;
		}
		
		table tr {
			height: 25px;
		}
		-->
		</style>
	</head>
	<body style="text-align: center">
		<br />
		<a> ${requestScope.impStatus} </a>
		<div style="width: 100%; height: 200px;">
			<ul>
				<li>
					记录一共:
					${requestScope.totalCount}
					行
				</li>
				<li>
					成功导入:
					${requestScope.fokCount}
					行
				</li>
			</ul>
		</div>
		<input class="xui-button" name="submit" type="submit" value="确定" onClick="justep.yn.portal.dailog.dailogEngin()"
			style="font-size: 11pt; line-height: 18px; width: 60px; height: 25px;" />
	</body>
</html>

