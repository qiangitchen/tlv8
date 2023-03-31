<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
	String context = request.getContextPath();
%>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<title>grid export</title>
		<link type="text/css" rel="stylesheet" href="<%=context%>/resources/layui/css/layui.css" />
		<link rel="stylesheet" href="<%=context%>/resources/bootstrap/css/bootstrap.min.css" media="all">
		<script type="text/javascript" src="<%=context%>/common/jQuery/jquery.min.js"></script>
		<script type="text/javascript" src="<%=context%>/common/js/base.js" ></script>
		<script type="text/javascript" src="<%=context%>/resources/bootstrap/js/bootstrap.min.js"></script>
		<script type="text/javascript" src="<%=context%>/common/gridReport/js/exp.js"></script>
		<script type="text/javascript" src="<%=context%>/resources/layui/layui.js"></script>
		<script type="text/javascript">
			window.isWriteLog = false;
		</script>
	</head>
	<body style="text-align: center; overflow:hidden;">
		<div id="impMainview">
			<table align="center" style="border:0; width:100%; height:100%; text-align: center">
				<tr>
					<td colspan="2" height="98%" valign="top">
						<div style="width:100%;height:100%;overflow:auto;" id="selectCellView">
						</div>
					</td>
				</tr>
				<tr>
					<td align="center" colspan="2" style="padding: 10px; border-top: 1px solid #eee;">
						<button class="btn btn-primary" name="ensure" type="button" onClick="expinit()">确定</button>
						<button class="btn btn-default" name="cancell" type="button" onclick="cancell()">取消</button>
					</td>
				</tr>
			</table>
		</div>
		<div style="display: none; position: absolute; left: 0px; top: 50%; margin-top: -40px; width:100%; height:40px; text-align: center;" id="impstatesetting">
			<img src="image/loading.gif">
			正在处理请稍候... ...
		</div>
		<div style="display: none;">
			<form action="<%=context%>/core/exportGridData" method="post" name="importform" id="importform">
				<table align="center" style="border:0; width:100%; height:200px; text-align: center;">
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
