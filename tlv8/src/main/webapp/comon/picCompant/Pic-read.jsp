<%@page import="com.tlv8.base.db.DBUtils"%>
<%@ page language="java" import="java.util.*"
	contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ page language="java" import="java.io.*"%>
<%@ page language="java" import="java.sql.*"%>
<%@ page language="java" import="java.awt.image.BufferedImage"%>
<%@ page language="java" import="javax.imageio.ImageIO"%>
<html>
	<head>
		<META http-equiv="Content-Type" content="text/html; charset=utf-8"/>
		<script type="text/javascript" src="../../comon/js/jquery/jquery.min.js"></script>
	</head>
	<body>
		This is my PIC page.
		<br>
		<%
			String dbkey = request.getParameter("dbkey");
			String keyCell = ("system".equals(dbkey)) ? "sID" : "fID";
			String cellname = request.getParameter("cellname");
			String showImage = "select " + cellname + " from "
					+ request.getParameter("tablename") + " " + " where "
					+ keyCell + "='" + request.getParameter("fID").trim() + "' ";
			BufferedInputStream inputImage = null;
			Connection conn = null;
			Statement st = null;
			ResultSet rs = null;
			//System.out.println(showImage);
			try {
				conn = DBUtils.getAppConn(dbkey);
				st = conn.createStatement();
				rs = st.executeQuery(showImage);
				if (rs.next()) {
					Blob blob = (Blob) rs.getBlob(1);
					long size = blob.length();
					byte[] bs = blob.getBytes(1, (int) size);
					response.setContentType("image/jpeg");
					OutputStream outs = response.getOutputStream();
					outs.write(bs);
					outs.flush();
				}
			} catch (Exception e) {
				//e.printStackTrace();
			} finally {
				DBUtils.CloseConn(conn, st, rs);
			}
			out.clear();
			out = pageContext.pushBody();
		%>
	</body>
</html>
