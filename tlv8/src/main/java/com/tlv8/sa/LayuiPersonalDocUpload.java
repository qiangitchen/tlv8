package com.tlv8.sa;

import java.io.InputStream;
import java.net.URLDecoder;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.HashMap;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.alibaba.fastjson.JSONObject;
import com.tlv8.base.db.DBUtils;
import com.tlv8.base.utils.IDUtils;
import com.tlv8.doc.clt.doc.AbstractDoc;
import com.tlv8.doc.clt.doc.Doc;
import com.tlv8.doc.clt.doc.DocDBHelper;
import com.tlv8.doc.clt.doc.DocUtils;
import com.tlv8.doc.clt.doc.Docinfo;
import com.tlv8.doc.clt.doc.Docs;
import com.tlv8.system.bean.ContextBean;
import com.tlv8.system.utils.ContextUtils;

@Controller
@RequestMapping("/sa")
@SuppressWarnings({ "unchecked", "rawtypes" })
public class LayuiPersonalDocUpload {

	@ResponseBody
	@RequestMapping(value = "/layuiPersonalDocUpload", method = RequestMethod.POST)
	public Object execute(@RequestParam("file") MultipartFile file, String docPath, String mainid) throws Exception {
		JSONObject res = new JSONObject();
		if (docPath == null || "".equals(docPath)) {
			docPath = "/root";
		} else {
			try {
				docPath = URLDecoder.decode(docPath, "UTF-8");
			} catch (Exception e) {
			}
		}
		SqlSession session = DBUtils.getSession("system");
		Connection conn = null;
		PreparedStatement ps = null;
		try {
			String fileName = file.getOriginalFilename();
			Map<String, String> m = upLoadFiletoDaisy("/", fileName, file.getInputStream());
			String docID = DocDBHelper.addDocData(docPath, fileName, file.getContentType(), String.valueOf(m.get("Size")),
					m.get("cacheName"));
			String fileID = m.get("cacheName");
			Doc doc = Docs.queryDocById(docID);
			doc.commitFile();
			DocUtils.saveDocFlag(docPath, doc.getsKind(), doc.getScacheName(), doc.getScacheName(), false);
			fileID = doc.getsFileID();
			DBUtils.execUpdateQuery("system",
					"update SA_docNode set SFILEID = '" + fileID + "',SCACHENAME='' where sID = '" + docID + "'");
			String sql = "insert into PERSONAL_FILE(SID,SFILENAME,SFILESIZE,SDOCPATH,SFILEID,SCREATORID,SCREATORNAME,SMASTERID,version)"
					+ "values(?,?,?,?,?,?,?,?,?)";
			ContextBean context = ContextUtils.getContext();
			conn = session.getConnection();
			ps = conn.prepareStatement(sql);
			ps.setString(1, IDUtils.getGUID());
			ps.setString(2, fileName);
			ps.setString(3, String.valueOf(m.get("Size")));
			ps.setString(4, doc.getsDocPath());
			ps.setString(5, fileID);
			ps.setString(6, context.getCurrentPersonID());
			ps.setString(7, context.getCurrentPersonName());
			ps.setString(8, mainid);
			ps.setInt(9, 0);
			ps.executeUpdate();
			conn.commit();
			res.put("code", 0);
			res.put("msg", "上传成功！");
		} catch (Exception e) {
			res.put("code", -1);
			res.put("msg", "上传失败！");
			e.printStackTrace();
		} finally {
			DBUtils.closeConn(session, conn, ps, null);
		}
		return res;
	}

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
