<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
	String context = request.getContextPath();
	String impStatus = (String)request.getAttribute("impStatus");
	if(impStatus==null){
		impStatus = "";
	}
%>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<title>grid import</title>
		<link type="text/css" rel="stylesheet" href="<%=context%>/resources/layui/css/layui.css" />
		<link rel="stylesheet" href="<%=context%>/resources/bootstrap/css/bootstrap.min.css" media="all">
		<script type="text/javascript" src="<%=context%>/common/jQuery/jquery.min.js"></script>
		<script type="text/javascript" src="<%=context%>/common/js/base.js" ></script>
		<script type="text/javascript" src="<%=context%>/resources/bootstrap/js/bootstrap.min.js"></script>
		<script type="text/javascript" src="<%=context%>/resources/layui/layui.js"></script>
		<script type="text/javascript" src="<%=context%>/common/gridReport/js/imp.js"></script>
		<script type="text/javascript">
			var path = '<%=request.getRequestURI()%>'; 
		</script>
	</head>
	<body>
		<a style="font-color: red; color: red; font-size: 11pt;height:25px;"> 
			<%=impStatus%>
		</a>
		<div style="display: none;position: absolute;left:10px;top:100px;" id="impstatesetting">
			<img src="image/loading.gif">
			正在导入请稍候... ...
		</div>
		<div id="impMainview" style="padding: 5px;">
			<form action="<%=context%>/core/importGridData" method="post" class="bs-example bs-example-form" role="form" 
				enctype="multipart/form-data" name="importform" id="importform">
				<br>
				<div class="input-group">
		            <span class="input-group-addon">选择文件：</span>
		            <input id="upload" name="upload" type="file" class="form-control" placeholder="选择文件...">
		        </div>
				<br>
				<div class="panel panel-default">
				    <div class="panel-heading">
				        <h3 class="panel-title">
				            设置
				        </h3>
				    </div>
				    <div class="panel-body">
				        <div class="input-group">
				            <span class="input-group-addon">默认设置：</span>
				            <input id="nomalset" type="checkbox" onclick="checknormalset(this)" class="form-control">
				            <span class="input-group-addon">默认设置自动获取所有数据</span>
				        </div>
				        <br>
				        <div class="input-group">
				            <span class="input-group-addon">开始行：</span>
				            <input id="startLine" name="startLine" type="number" value="2" class="form-control">
				            <span class="input-group-addon">excel中数据开始的行</span>
				        </div>
				        <br>
				        <div class="input-group">
				            <span class="input-group-addon">结束行：</span>
				            <input id="endLine" name="endLine" type="number" value="-1" class="form-control">
				            <span class="input-group-addon">-1代表所有行</span>
				        </div>
				    </div>
				</div>
				<div style="display: none">
					<input type="text" value="" id="personID" name="personID" />
					<input type="text" value="" id="useNormal" name="useNormal" />
					<input type="text" value="" id="srcPath" name="srcPath" />
					<input type="text" value="" id="dbkey" name="dbkey" />
					<input type="text" value="" id="table" name="table" />
					<input type="text" value="" id="relation" name="relation" />
					<input type="text" value="" id="confirmXmlName" name="confirmXmlName" />
				</div>
				<div style="width: 100%; text-align: center;">
					<button class="btn btn-primary" name="submit" type="submit" onClick="impinit()">确定</button>
					<button class="btn btn-default" name="reset" type="reset" onclick="cancell()">取消</button>
				</div>
			</form>
		</div>
	</body>
</html>
