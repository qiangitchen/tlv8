<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<title>老文档服务文件迁移</title>
		<script type="text/javascript" src="js/jquery.js"></script>
		<script type="text/javascript" src="My97DatePicker/WdatePicker.js"></script>
		<style type="text/css">
			td{
				font-size: 12px;
			}
		</style>
		<script type="text/javascript">
			$(document).ready(function(){
				$("#driver").bind("change",function(){
					var driver = $(this).val();
					if(driver=="oracle.jdbc.driver.OracleDriver"){
						$("#url").val("jdbc:oracle:thin:@127.0.0.1:1521:orcl");
					}else if(driver=="net.sourceforge.jtds.jdbc.Driver"){
						$("#url").val("jdbc:jtds:sqlserver://127.0.0.1:1433/yjsys");
					}else if(driver=="com.mysql.jdbc.Driver"){
						$("#url").val("jdbc:mysql://127.0.0.1/yjsys?characterEncoding=UTF-8");
					}else{
						$("#url").val("");
					}
				});
			});
		</script>
	</head>
	<body style="text-align: center;">
		<form action="/DocServer/repository/doc/init" method="post" name="importform" id="importform">
			<table border="0" width="560px" height="300px" align="center" style="text-align: center; table-layout: fixed;">
				<tr>
					<td style="width: 80px;">驱动类型:</td>
					<td align="left">
						<select id="driver" name="driver" value="oracle.jdbc.driver.OracleDriver">
							<option value="oracle.jdbc.driver.OracleDriver">Oracle</option>
							<option value="net.sourceforge.jtds.jdbc.Driver">SqlServer</option>
							<option value="com.mysql.jdbc.Driver">MySql</option>
						</select>
					</td>
				</tr>
				<tr>
					<td>数据库链接:</td>
					<td align="left">
						<input type="text" value="jdbc:oracle:thin:@127.0.0.1:1521:orcl" id="url" name="url" style="width: 100%"/>
					</td>
				</tr>
				<tr>
					<td>用户名:</td>
					<td align="left">
						<input type="text" value="" id="username" name="username" />
					</td>
				</tr>
				<tr>
					<td>密码:</td>
					<td align="left">
						<input type="password" value="" id="password" name="password" />
					</td>
				</tr>
				<tr>
					<td colspan="2"><hr/></td>
				</tr>
				<tr>
					<td>开始时间</td>
					<td align="left">
						<input type="text" class="Wdate" 
							onClick="WdatePicker({dateFmt:'yyyy-MM-dd'})" id="startTime" name="startTime" />
					</td>
				</tr>
				<tr>
					<td>结束时间</td>
					<td align="left">
						<input type="text" class="Wdate" 
							onClick="WdatePicker({dateFmt:'yyyy-MM-dd'})" id="endTime" name="endTime" />
					</td>
				</tr>
				<tr>
					<td align="center" colspan="2">
						<input id="submit" name="submit" type="submit" value="确定" 
							style="font-size: 12px; line-height: 18px;" />
						<input id="reset" name="reset" type="reset" value="取消" 
							style="font-size: 12px; line-height: 18px;" />
					</td>
				</tr>
			</table>
		</form>
	</body>
</html>