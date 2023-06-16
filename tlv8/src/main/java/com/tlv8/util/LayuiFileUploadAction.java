package com.tlv8.util;

import java.io.InputStream;
import java.net.URLDecoder;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.tlv8.base.db.DBUtils;
import com.tlv8.doc.clt.doc.AbstractDoc;
import com.tlv8.doc.clt.doc.Doc;
import com.tlv8.doc.clt.doc.DocDBHelper;
import com.tlv8.doc.clt.doc.DocUtils;
import com.tlv8.doc.clt.doc.Docinfo;
import com.tlv8.doc.clt.doc.Docs;

@Controller
@RequestMapping("/utils")
@SuppressWarnings({ "unchecked", "rawtypes" })
public class LayuiFileUploadAction {

	@ResponseBody
	@RequestMapping(value = "/layuiFileUploadAction", method = RequestMethod.POST)
	public synchronized Object execute(@RequestParam("file") MultipartFile file, String docPath, String dbkey, String rowid,
			String tablename, String cellname) throws Exception {
		JSONObject res = new JSONObject();
		if (docPath == null || "".equals(docPath)) {
			docPath = "/root";
		} else {
			try {
				docPath = URLDecoder.decode(docPath, "UTF-8");
			} catch (Exception e) {
			}
		}
		try {
			String fileName = file.getOriginalFilename();
			Map<String, String> m = upLoadFiletoDaisy("/", fileName, file.getInputStream());
			String docID = DocDBHelper.addDocData(docPath, fileName, file.getContentType(),
					String.valueOf(m.get("Size")), m.get("cacheName"));
			String fileID = m.get("cacheName");
			Doc doc = Docs.queryDocById(docID);
			doc.commitFile();
			DocUtils.saveDocFlag(docPath, doc.getsKind(), doc.getScacheName(), doc.getScacheName(), false);
			fileID = doc.getsFileID();
			DBUtils.execUpdateQuery("system",
					"update SA_docNode set SFILEID = '" + fileID + "',SCACHENAME='' where sID = '" + docID + "'");
			if (rowid != null && !"".equals(rowid) && !"undefined".equals(rowid)) {
				String querySql = "select " + cellname + " from " + tablename + " where :KEYCELL = '" + rowid + "'";
				String updateSql = "update " + tablename + " set " + cellname + " = ? where :KEYCELL = '" + rowid + "'";
				if ("system".equals(dbkey)) {
					querySql = querySql.replace(":KEYCELL", "SID");
					updateSql = updateSql.replace(":KEYCELL", "SID");
				} else {
					querySql = querySql.replace(":KEYCELL", "FID");
					updateSql = updateSql.replace(":KEYCELL", "FID");
				}
				SqlSession session = DBUtils.getSession(dbkey);
				Connection conn = null;
				Statement stm = null;
				PreparedStatement ps = null;
				ResultSet rs = null;
				try {
					conn = session.getConnection();
					JSONArray jsona = new JSONArray();
					stm = conn.createStatement();
					rs = stm.executeQuery(querySql);
					if (rs.next()) {
						String fileinfo = rs.getString(1);
						try {
							jsona = JSON.parseArray(fileinfo);
						} catch (Exception e) {
							try {
								jsona = JSON.parseArray(transeJson(fileinfo));
							} catch (Exception er) {
							}
						}
					}
					if(jsona == null) {
						jsona = new JSONArray();
					}
					JSONObject jsono = new JSONObject();
					jsono.put("fileID", fileID);
					jsono.put("filename", doc.getsDocName());
					jsono.put("filesize", doc.getsSize());
					jsono.put("md5", DigestUtils.md5Hex(file.getBytes()));
					jsona.add(jsono);
					ps = conn.prepareStatement(updateSql);
					ps.setString(1, jsona.toString());
					ps.executeUpdate();
					session.commit(true);
				} catch (Exception e) {
					session.rollback(true);
					e.printStackTrace();
				} finally {
					DBUtils.closeConn(null, null, stm, rs);
					DBUtils.closeConn(session, conn, ps, null);
				}
			}
			res.put("code", 0);
			res.put("msg", "上传成功！");
		} catch (Exception e) {
			res.put("code", -1);
			res.put("msg", "上传失败！");
			e.printStackTrace();
		}
		return res;
	}

	public static String transeJson(String str) {
		str = str.replace(":", ":\"");
		str = str.replace(",", "\",");
		str = str.replace("}", "\"}");
		str = str.replace("}{", "},{");
		str = str.replace(";", "\",");
		return str;
	};

	private Map<String, String> upLoadFiletoDaisy(String DocPath, String DocName, InputStream file) throws Exception {
		Map m = new HashMap();
		m.put("sDocPath", DocPath);
		m.put("sDocName", DocName);
		Docinfo di = new Docinfo(m);
		AbstractDoc doca = new AbstractDoc(di);
		doca.upload(false, file);
		DocUtils.saveDocFlag(DocPath, doca.getsKind(), doca.getScacheName(), doca.getScacheName(), false);
		Map rem = new HashMap();
		rem.put("Kind", doca.getsKind());
		rem.put("cacheName", doca.getScacheName());
		rem.put("Size", doca.getsSize());
		rem.put("sDocName", DocName);
		return rem;
	}
}
