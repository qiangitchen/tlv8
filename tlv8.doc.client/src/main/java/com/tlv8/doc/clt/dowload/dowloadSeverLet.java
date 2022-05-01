package com.tlv8.doc.clt.dowload;

import java.io.*;
import java.net.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.*;
import javax.servlet.http.*;

import com.tlv8.base.Sys;
import com.tlv8.base.db.DBUtils;
import com.tlv8.doc.clt.doc.*;

public class dowloadSeverLet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4381669016388444989L;

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String contentType = null;
		String DocPath = null;
		String fileID = request.getParameter("fileID");
		String filename = request.getParameter("filename");
		OutputStream outs = response.getOutputStream();
		response.setContentType(contentType);
		try {
			filename = URLDecoder.decode(filename, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		System.out.println(filename);
		response.setHeader("Content-disposition", "attachment; filename="
				+ URLEncoder.encode(filename, "UTF-8"));
		try {
			String SQL = "select SKIND,SDOCNAME,SDOCDISPLAYPATH from SA_DOCNODE where SFILEID = '"
					+ fileID + "'";
			List Li = DBUtils.execQueryforList("system", SQL);
			if (Li.size() > 0) {
				Map rs = (Map) Li.get(0);
				contentType = (String) rs.get("SKIND");
				filename = (String) rs.get("SDOCNAME");
				DocPath = (String) rs.get("SDOCDISPLAYPATH");
				Map m = new HashMap();
				m.put("sDocPath", DocPath);
				m.put("sFileID", fileID.split("-")[0]);
				Docinfo di = new Docinfo(m);
				AbstractDoc doca = new AbstractDoc(di);
				doca.download(false, outs, null, null);
			}
			if (Li.size() == 0) {
				Sys.packErrMsg("给定fileID不存在!");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
}
