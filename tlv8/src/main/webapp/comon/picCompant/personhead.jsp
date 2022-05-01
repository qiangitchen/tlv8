<%@page import="com.tlv8.docs.DocSvrUtils"%>
<%@page import="com.tlv8.base.db.DBUtils"%>
<%@page import="com.tlv8.system.controller.UserController"%>
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
		<%
			String psmid = request.getParameter("id");
			if(psmid==null){
				psmid = new UserController().getContext(request.getSession().getId()).getCurrentPersonID();
			}else{
				psmid = psmid.trim();
			}
		    String showImage = "select SPHOTO from SA_OPPERSON where SID ='" + psmid + "' ";
			BufferedInputStream inputImage = null;
			Connection conn = null;
			Statement st = null;
			ResultSet rs = null;
			//System.out.println(showImage);
			response.setContentType("image/jpeg");
			boolean havimg = false;
			try {
				conn = DBUtils.getAppConn("system");
				st = conn.createStatement();
				rs = st.executeQuery(showImage);
				if (rs.next()) {
					Blob blob = (Blob) rs.getBlob(1);
					long size = blob.length();
					if(size>0){
						byte[] bs = blob.getBytes(1, (int) size);
						OutputStream outs = response.getOutputStream();
						outs.write(bs);
						outs.flush();
						havimg = true;
					}
				}
			} catch (Exception e) {
				//e.printStackTrace();
			} finally {
				DBUtils.CloseConn(conn, st, rs);
			}
			if(!havimg){
				OutputStream outs = response.getOutputStream();
				File file = new File(request.getRealPath("comon/picCompant/image/shead.jpg"));
				DocSvrUtils.downloadFile(file, outs);
				outs.flush();
			}
			out.clear();
			out = pageContext.pushBody();
		%>
	</body>
</html>
