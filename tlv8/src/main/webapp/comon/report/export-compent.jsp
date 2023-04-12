<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
	String context = request.getContextPath();
%>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<title>export</title>
		<link rel="stylesheet" type="text/css" href="../../resources/layui/css/layui.css" >
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
		<script type="text/javascript" src="<%=context%>/comon/js/comon.main.js" ></script>
		<script type="text/javascript" src="../../resources/layui/layui.js"></script>
		<script type="text/javascript" src="js/exp.js"></script>
		<script type="text/javascript">
			window.isWriteLog = false;
		</script>
	</head>
	<body style="text-align: center; overflow:hidden;">
		<div id="impMainview">
			<table align="center"
				style="border:0; width:100%; height:100%; text-align: center">
				<tr>
					<td colspan="2" height="98%" valign="top">
						<div style="width:100%;height:100%;overflow:auto;" id="selectCellView">
						</div>
					</td>
				</tr>
				<tr>
					<td align="center" colspan="2" height="25" style="padding-bottom: 5px;">
						<button class="layui-btn layui-btn-sm" name="ensure" type="button" onClick="expinit()">确定</button>
						<button class="layui-btn layui-btn-primary layui-btn-sm" name="cancell" type="button" onclick="cancell()">取消</button>
					</td>
				</tr>
			</table>
		</div>
		<div style="display: none; position: absolute; left: 10px; top: 100px;"
			id="impstatesetting">
			<img src="image/loading.gif">
			正在处理请稍候... ...
		</div>
		<div style="display: none;">
			<form action="<%=context%>/expportAction" method="post" name="importform" id="importform">
				<table align="center" style="border:0; text-align: center;">
					<tr>
						<td colspan="2">
							<input type="text" value="" id="dbkey" name="dbkey" />
							<input type="text" value="" id="table" name="table" />
							<input type="text" value="" id="relation" name="relation" />
							<input type="text" value="" id="labels" name="labels" />
							<input type="text" value="" id="where" name="where" />
							<input type="text" value="" id="orderby" name="orderby" />
						</td>
					</tr>
					<tr>
						<td align="center" colspan="2">
							<input id="submitItem" name="submitItem" type="submit" value="" 
								style="font-size: 11pt; line-height: 18px; width: 60px; height: 25px;" />
							<input id="resetItem" name="resetItem" type="reset" value="" 
								style="font-size: 11pt; line-height: 18px; width: 60px; height: 25px;" />
						</td>
					</tr>
				</table>
			</form>
		</div>
	</body>
</html>
