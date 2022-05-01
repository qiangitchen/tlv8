<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<title>import</title>
		<link rel="stylesheet" type="text/css" href="../../resources/layui/css/layui.css" >
		<link rel="stylesheet" type="text/css" href="../../comon/css/grid.main.css" >
		<script type="text/javascript" src="../../comon/js/jquery/jquery.min.js"></script>
		<script type="text/javascript" src="../../comon/js/comon.main.js"></script>
		<script type="text/javascript" src="../../resources/layui/layui.js"></script>
		<script src="js/imp.js" type="text/javascript"></script>
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
		-->
		</style>
		<script type="text/javascript">
			var path = '<%=request.getRequestURI()%>'; 
		</script>
	</head>
	<body style="text-align: center" onload="initIMP()">
		<a style="font-color: red; color: red; font-size: 11pt;height:25px;"> 
				${requestScope.impStatus}
		</a>
		<div style="display: none;position: absolute;left:10px;top:100px;" id="impstatesetting">
			<img src="image/loading.gif">
			正在导入请稍候... ...
		</div>
		<div id="impMainview" style="align:center; padding: 5px;">
			<form action="importAction" method="post" enctype="multipart/form-data" name="importform" id="importform">
					<table border="0" align="center" style="table-layout:fixed;">
						<tr>
							<td width="80px" height="35px">
								选择文件：
							</td>
							<td align="left" style="width:200px">
								<input id="upload" name="upload" type="file" style="width:100%"/>
							</td>
						</tr>
						<tr>
							<td colspan="2" height="98%" style="padding:10px;">
								<fieldset style="width: 99%; height: 170px; margin: 0;padding: 0;">
									<legend>
										&nbsp;设置&nbsp;
									</legend>
									<br />
									<table style="width: 100%; height: 90%;">
										<tr>
											<td width="80px" align="center" height="20px">
												默认设置：
											</td>
											<td align="left">
												<input name="nomalset" type="checkbox" id="nomalset"
													onclick="checknormalset(this)" />
												<font style="font-color: #009999;">默认设置自动获取所有数据</font>
											</td>
										</tr>
										<tr>
											<td width="80px" align="center" height="20px">
												开始行：
											</td>
											<td align="left">
												<input name="startLine" type="text" id="startLine" value="2"
													style="width: 30%" />
												<sapn>
												excel中数据开始的行
												</span>
											</td>
										</tr>
										<tr>
											<td align="center" height="20px">
												结束行：
											</td>
											<td align="left">
												<input name="endLine" type="text" id="endLine"
													style="width: 30%" value="-1" />
												<sapn>
												-1代表所有行
												</span>
											</td>
										</tr>
									</table>
								</fieldset>
								<br/>
							</td>
						</tr>
						<tr style="display: none">
							<td colspan="2">
								<input type="text" value="" id="personID" name="personID" />
								<input type="text" value="" id="useNormal" name="useNormal" />
								<input type="text" value="" id="srcPath" name="srcPath" />
								<input type="text" value="" id="dbkey" name="dbkey" />
								<input type="text" value="" id="table" name="table" />
								<input type="text" value="" id="relation" name="relation" />
								<input type="text" value="" id="confirmXmlName"
									name="confirmXmlName" />
							</td>
						</tr>
						<tr>
							<td align="center" colspan="2">
								<input class="layui-btn layui-btn-sm" name="submit" type="submit" value="确定" onClick="impinit()"/>
								<input class="layui-btn layui-btn-primary layui-btn-sm" name="reset" type="reset" value="取消" onclick="cancell()"/>
							</td>
						</tr>
					</table>
			</form>
		</div>
	</body>
</html>
