<%@page import="java.sql.Connection"%>
<%@page import="com.alibaba.fastjson.JSONObject"%>
<%@page import="com.alibaba.fastjson.JSONArray"%>
<%@page import="oshi.util.FormatUtil"%>
<%@page import="com.tlv8.doc.clt.doc.DocDBHelper"%>
<%@page import="java.sql.PreparedStatement"%>
<%@page import="java.sql.ResultSet"%>
<%@page import="com.tlv8.base.db.DBUtils"%>
<%@page import="org.apache.ibatis.session.SqlSession"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
	String cellname = request.getParameter("cellname");
	String fid = request.getParameter("fid");
	String dbkey = request.getParameter("dbkey");
	String table = request.getParameter("table");
	String option = request.getParameter("option");
	String cell = "system".equals(dbkey)?"sid":"fid";
	String host = DocDBHelper.queryDocHost();
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
	    <script type="text/javascript">
	    	function deletefile(fileID,filename){
	    		tlv8.deletefile(fileID, filename, "<%=dbkey%>", "<%=table%>", "<%=cellname%>","<%=fid%>", function(){
	    			window.location.reload();
	    		});
	    	}
	    </script>
	</head>
	<body style="padding: 5px;">
	  <div class="layui-upload-list">
	    <table class="layui-table">
	      <thead>
	        <tr><th>文件名</th>
	        <th width="60px">大小</th>
	        <th>md5</th>
	        <th width="80px">操作</th>
	      </tr></thead>
	      <tbody id="demoList">
	      <%
	      	SqlSession sqlsession = DBUtils.getSession(dbkey);
	      	Connection conn = null;
	        PreparedStatement ps = null;
	      	ResultSet rs = null;
	      	try{
	      		String sql = "select " + cellname + " from " + table + " where " + cell + "=?";
	      		conn = sqlsession.getConnection();
	      		ps = conn.prepareStatement(sql);
	      		ps.setString(1, fid);
	      		rs = ps.executeQuery();
	      		if(rs.next()){
	      			JSONArray jsona = JSONArray.parseArray(rs.getString(1));
	      			for(int i =0; i<jsona.size(); i++){
	      				JSONObject json = jsona.getJSONObject(i);
	      				String fileid = json.getString("fileID");
	      				String filename = json.getString("filename");
	      				String durl = host + "/repository/file/view/" + fileid + "/last/content";
	      				%>
	      				<tr>
	      					<td><%=filename%></td>
	      					<td><%=FormatUtil.formatBytes(json.getLong("filesize"))%></td>
	      					<td><%=json.getString("md5")%></td>
	      					<td>
	      						<a href="<%=durl%>" target="_blank">查看</a>
	      						<%if("edit".equals(option)){%>
	      							&nbsp;&nbsp;
	      							<a href="javascript:deletefile('<%=fileid%>','<%=filename%>');">删除</a>
	      						<%}%>
	      					</td>
	      				</tr>
	      				<%
	      			}
	      		}
	      	}catch(Exception e){
	      		e.printStackTrace();
	      	}finally{
	      		DBUtils.CloseConn(sqlsession, conn, ps, rs);
	      	}
	      %>
	      </tbody>
	    </table>
	  </div>
	</body>
</html>