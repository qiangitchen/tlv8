<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
	<head>
  		<meta charset="utf-8">
		<title>图片上传</title>
		<meta name="renderer" content="webkit">
  		<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
  		<meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1">
		<link rel="stylesheet" href="../../resources/layui/css/layui.css" media="all">
		<script type="text/javascript" src="../../comon/js/jquery/jquery.min.js"></script>
		<script type="text/javascript" src="../../comon/js/comon.main.js"></script>
		<script type="text/javascript" src="../../resources/layui/layui.js"></script>
		<%String cpath = request.getContextPath();%>
		<script type="text/javascript">
			function initUpload(){
				var dbkey = tlv8.RequestURLParam.getParam("dbkey");
				var tablename = tlv8.RequestURLParam.getParam("tablename");
				var cellname = tlv8.RequestURLParam.getParam("cellname");
				var rowid = tlv8.RequestURLParam.getParam("rowid");
				layui.upload.render({
				    elem: '#draguploadview'
				    ,url: '<%=cpath%>/utils/layuiImageWriteAction'
				    ,data: {dbkey: dbkey, tablename: tablename, cellname: cellname, rowid: rowid}
				    ,done: function(res){
				    	if(res.code != '-1'){
				      		layer.msg('上传成功');
				      		tlv8.portal.dailog.dailogEngin();
				    	}else{
				      		layer.msg(res.msg);
				    	}
				    }
				});
			}
		</script>
	</head>
	<body onload="initUpload()" style="width: 100%; height: 100%; text-align: center;">
		<div class="layui-upload-drag" id="draguploadview" style="margin: auto;">
		  <i class="layui-icon"></i>
		  <p>点击上传，或将文件拖拽到此处</p>
	   </div>
	</body>
</html>
