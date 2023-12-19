package com.tlv8.docs;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import org.apache.ibatis.jdbc.SQL;
import org.apache.ibatis.session.SqlSession;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.tlv8.base.db.DBUtils;
import com.tlv8.docs.utils.ClientUtils;

public class DocSvrUtils {

	/**
	 * "附件"信息转换JSON
	 * 
	 * @param celltext
	 * @return
	 */
	public static JSONArray transeInfo(String celltext) {
		JSONArray json = new JSONArray();
		try {
			json = JSON.parseArray(celltext);
		} catch (Exception e) {
			try {
				json = JSON.parseArray(transeJson(celltext));
			} catch (Exception e1) {
			}
		}
		return json;
	}

	/**
	 * 内容转换
	 * 
	 * @param str
	 * @return
	 */
	private static String transeJson(String str) {
		str = str.toString().replace(":", ":\"");
		str = str.toString().replace(",", "\",");
		str = str.toString().replace("}", "\"}");
		str = str.toString().replace("}{", "},{");
		str = str.toString().replace(";", "\",");
		return "[" + str + "]";
	}

	public static String getDocServerUrl() {
		SQL sql = new SQL();
		sql.SELECT("SURL");
		sql.FROM("SA_DOCNAMESPACE");
		sql.WHERE("SID = 'defaultDocNameSpace'");
		String docHost = "";
		SqlSession session = DBUtils.getSession("system");
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			conn = session.getConnection();
			ps = conn.prepareStatement(sql.toString());
			rs = ps.executeQuery();
			if (rs.next()) {
				docHost = rs.getString("SURL");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBUtils.closeConn(session, conn, ps, rs);
		}
		return docHost;
	}

	/**
	 * 根据fileID获取下载路径
	 * 
	 * @param fileID
	 * @return
	 */
	public static String getDownloadUrl(String fileID) {
		String urlPattern = "/repository/file/download/" + fileID + "/last/content";
		return getDocServerUrl() + urlPattern;
	}

	/**
	 * 根据fileID获取下载路径
	 * 
	 * @param fileID
	 * @return
	 */
	public static String getViewUrl(String fileID) {
		String urlPattern = "/repository/file/view/" + fileID + "/last/content";
		return getDocServerUrl() + urlPattern;
	}

	/**
	 * 根据文件fileID下载文件返回文件流
	 * 
	 * @param fileID
	 * @return
	 * @throws Exception
	 */
	public static InputStream downloadFile(String fileID) throws Exception {
		InputStream stream = null;
		String url = getDownloadUrl(fileID);
		Map<String, String> param = new HashMap<String, String>();
		stream = ClientUtils.connectStream(url, param);
		return stream;
	}

	/**
	 * 根据文件fileID下载文件
	 * 
	 * @param fileID
	 * @param outputStream
	 * @throws Exception
	 */
	public static void downloadFile(String fileID, OutputStream outputStream) throws Exception {
		InputStream inputStream = downloadFile(fileID);
		int bytesRead;
		byte[] buf = new byte[1024];
		while ((bytesRead = inputStream.read(buf)) != -1) {
			outputStream.write(buf, 0, bytesRead);
		}
		inputStream.close();
	}

	/**
	 * 读取指定的文件
	 * 
	 * @param file
	 * @param os
	 * @throws Exception
	 */
	public static void downloadFile(File file, OutputStream os) throws Exception {
		InputStream is = null;
		BufferedInputStream bis = null;
		BufferedOutputStream bos = null;
		is = new FileInputStream(file);
		bis = new BufferedInputStream(is);
		bos = new BufferedOutputStream(os);
		byte[] b = new byte[1024];
		int len = 0;
		while ((len = bis.read(b)) != -1) {
			bos.write(b, 0, len);
		}
		bis.close();
		is.close();
		bos.close();
	}

	/**
	 * 根据表单“附件”字段下载文件
	 * 
	 * @param info
	 * @param outputStream
	 * @throws Exception
	 */
	public static void downloadFileByinfo(String info, OutputStream outputStream) throws Exception {
		JSONArray json = transeInfo(info);
		if (json.size() > 0) {
			JSONObject file = json.getJSONObject(0);
			downloadFile(file.getString("fileID"), outputStream);
		}
	}
}
