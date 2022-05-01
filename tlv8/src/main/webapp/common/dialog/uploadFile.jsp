<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
	String cellname = request.getParameter("cellname");
	String fid = request.getParameter("fid");
	String dbkey = request.getParameter("dbkey");
	String table = request.getParameter("table");
	String docPath = request.getParameter("docPath");
%>
<!DOCTYPE html>
<html>
	<head>
  		<meta charset="utf-8">
		<title>文件上传HTML5</title>
		<meta name="renderer" content="webkit">
  		<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
  		<meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1">
		<link rel="stylesheet" href="../../resources/layui/css/layui.css" media="all">
	    <script src="../../comon/js/jquery/jquery.min.js" charset="utf-8"></script>
	    <script src="../../comon/js/comon.main.js" charset="utf-8"></script>
	    <script src="../../resources/layui/layui.js" charset="utf-8"></script>
	</head>
	<body style="padding: 5px;">
		<div class="layui-upload">
		  <button type="button" class="layui-btn layui-btn-normal" id="testList">选择多文件</button> 
		  <div class="layui-upload-list">
		    <table class="layui-table">
		      <thead>
		        <tr><th>文件名</th>
		        <th>大小</th>
		        <th>状态</th>
		        <th>操作</th>
		      </tr></thead>
		      <tbody id="demoList"></tbody>
		    </table>
		  </div>
		  <!-- 
		  <button type="button" class="layui-btn" id="testListAction">开始上传</button>
		   -->
	     </div>
	     <script type="text/javascript">
	     	var cellname = "<%=cellname%>";
	     	var fid = "<%=fid%>";
	     	var dbkey = "<%=dbkey%>";
	     	var table = "<%=table%>";
	     	var docPath = "<%=docPath%>";
	     </script>
	     <script src="uploadFile.js" charset="utf-8"></script>
	</body>
</html>