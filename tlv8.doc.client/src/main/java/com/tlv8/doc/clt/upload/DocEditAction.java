package com.tlv8.doc.clt.upload;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URLDecoder;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tlv8.base.db.DBUtils;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.tlv8.base.ActionSupport;
import com.tlv8.doc.clt.doc.AbstractDoc;
import com.tlv8.doc.clt.doc.DocUtils;
import com.tlv8.doc.clt.doc.Docinfo;

/**
 * 文件编辑
 * 
 * @author 陈乾
 */
@Controller
@Scope("prototype")
@SuppressWarnings("rawtypes")
public class DocEditAction extends ActionSupport {
	private static final int BUFFER_SIZE = 16 * 1024;

	private String contentType;
	private File upload;
	private String fileName;
	private String caption;
	private String fileid;
	private String dbkey;
	private String tablename;
	private String relation;
	private String rowid;

	public String getUploadFileName() {
		return fileName;
	}

	public void setUploadFileName(String fileName) {
		this.fileName = fileName;
	}

	public String getUploadContentType() {
		return contentType;
	}

	public void setUploadContentType(String contentType) {
		this.contentType = contentType;
	}

	public File getUpload() {
		return upload;
	}

	public void setUpload(File upload) {
		this.upload = upload;
	}

	public String getCaption() {
		return caption;
	}

	public void setCaption(String caption) {
		this.caption = caption;
	}

	@ResponseBody
	@RequestMapping("/docEditAction")
	@Override
	public Object execute() throws Exception {
		Map m = getDocInfo();
		String docID = (String) m.get("docID");
		Map rem = uploadFile(m);
		updateData(docID, rem);
		return this;
	}

	public void setFileid(String fileid) {
		try {
			this.fileid = URLDecoder.decode(fileid, "UTF-8");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public String getFileid() {
		return fileid;
	}

	public String getDbkey() {
		return dbkey;
	}

	public void setDbkey(String dbkey) {
		try {
			this.dbkey = URLDecoder.decode(dbkey, "UTF-8");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public String getTablename() {
		return tablename;
	}

	public void setTablename(String tablename) {
		try {
			this.tablename = URLDecoder.decode(tablename, "UTF-8");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public String getRelation() {
		return relation;
	}

	public void setRelation(String relation) {
		try {
			this.relation = URLDecoder.decode(relation, "UTF-8");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void setRowid(String rowid) {
		try {
			this.rowid = URLDecoder.decode(rowid, "UTF-8");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public String getRowid() {
		return rowid;
	}

	/*
	 * 获取文档信息
	 */
	@SuppressWarnings({ "unchecked" })
	private Map getDocInfo() {
		Map m = new HashMap();
		String sql = "select SID,SDOCNAME,SDOCPATH,SKIND from SA_DOCNODE where SFILEID = '" + fileid + "'";
		try {
			List<Map<String, String>> li = DBUtils.execQueryforList("system", sql);
			if (li.size() > 0) {
				Map di = li.get(0);
				m.put("sDocPath", di.get("SDOCPATH"));
				m.put("sDocName", di.get("SDOCNAME"));
				m.put("sKind", di.get("SKIND"));
				m.put("docID", di.get("SID"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return m;
	}

	/*
	 * 上传文件到文档服务
	 */
	@SuppressWarnings("unchecked")
	private Map uploadFile(Map m) throws Exception {
		String DocName = (String) m.get("sDocName");
		String DocPath = (String) m.get("sDocPath");
		String sKind = (String) m.get("sKind");
		Docinfo di = new Docinfo(m);
		AbstractDoc doca = new AbstractDoc(di);
		String filePath = upload.getPath();
		String fileName = upload.getName();
		filePath = filePath.replace(fileName, DocName);
		File nfile = new File(filePath);
		InputStream in = null;
		OutputStream out = null;
		try {
			in = new BufferedInputStream(new FileInputStream(upload), BUFFER_SIZE);
			out = new BufferedOutputStream(new FileOutputStream(nfile), BUFFER_SIZE);
			byte[] buffer = new byte[BUFFER_SIZE];
			while (in.read(buffer) > 0) {
				out.write(buffer);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (null != in) {
					in.close();
				}
				if (null != out) {
					out.close();
				}
				upload.delete();
			} catch (Exception e) {
			}
		}
		doca.upload(false, nfile);
		DocUtils.saveDocFlag(DocPath, sKind, doca.getScacheName(), doca.getScacheName(), false);
		Map rem = new HashMap();
		rem.put("Kind", doca.getsKind());
		rem.put("fileID", doca.getScacheName());
		rem.put("Size", doca.getsSize());
		rem.put("sDocName", DocName);
		try {
			nfile.delete();
		} catch (Exception e) {

		}
		return rem;
	}

	/*
	 * 更新数据
	 */
	@SuppressWarnings("deprecation")
	private void updateData(String docID, Map rem) {
		String fileID = (String) rem.get("fileID");
		String fileSize = String.valueOf(rem.get("Size"));
		String qs = "select " + relation.toUpperCase() + " from " + tablename + " where FID = '" + rowid + "'";
		String edstr = "";
		Connection conn = null;
		Statement stm = null;
		ResultSet rs = null;
		try {
			conn = DBUtils.getAppConn(dbkey);
			stm = conn.createStatement();
			rs = stm.executeQuery(qs);
			if (rs.next()) {
				edstr = rs.getString(relation.toUpperCase());
			}
			JSONArray json = JSON.parseArray(edstr);
			if (json.size() > 0) {
				JSONObject jo = json.getJSONObject(0);
				jo.put("fileID", fileID);
				jo.put("size", fileSize);
				String udata = "update " + tablename + " set " + relation + " = '" + json.toString() + "' where FID = '"
						+ rowid + "'";
				DBUtils.execUpdateQuery(dbkey, udata);
				String udoc = "update SA_DocNode set sFileID = '" + fileID + "' where SID = '" + docID + "'";
				DBUtils.execUpdateQuery("system", udoc);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				DBUtils.closeConn(conn, stm, rs);
			} catch (SQLException e) {
			}
		}
	}

}
