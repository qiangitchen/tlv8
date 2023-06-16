package com.tlv8.util;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.alibaba.fastjson.JSONObject;
import com.tlv8.base.db.DBUtils;

@Controller
@RequestMapping("/utils")
public class LayuiImageWriteAction {
	@ResponseBody
	@RequestMapping(value = "/layuiImageWriteAction", method = RequestMethod.POST)
	public synchronized Object execute(@RequestParam("file") MultipartFile file, String dbkey, String tablename, String cellname,
			String rowid) throws Exception {
		JSONObject res = new JSONObject();
		if (dbkey == null || "".equals(dbkey))
			dbkey = "system";
		SqlSession session = DBUtils.getSession(dbkey);
		Connection conn = null;
		PreparedStatement pstmt = null;
		try {
			conn = session.getConnection();
			InputStream fin = file.getInputStream();
			String uSQL = "update " + tablename + " set " + cellname + "=? where "
					+ (("system".equals(dbkey)) ? "sID" : "fID") + "=?";
			pstmt = conn.prepareStatement(uSQL);
			pstmt.setBinaryStream(1, fin, fin.available());
			pstmt.setString(2, rowid);
			pstmt.executeUpdate();
			conn.commit();
		} catch (SQLException e) {
			res.put("code", -1);
			res.put("msg", "错误:" + e.toString());
			e.printStackTrace();
		} catch (IOException e) {
			res.put("code", -1);
			res.put("msg", "错误:" + e.toString());
		} catch (Exception e) {
			res.put("code", -1);
			res.put("msg", "错误:" + e.toString());
			e.printStackTrace();
		} finally {
			DBUtils.closeConn(session, conn, pstmt, null);
		}
		return res;
	}
}
