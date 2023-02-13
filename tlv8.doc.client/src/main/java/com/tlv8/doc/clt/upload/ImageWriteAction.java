package com.tlv8.doc.clt.upload;

import java.io.File;
import java.io.FileInputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;

import org.apache.ibatis.session.SqlSession;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.tlv8.base.db.DBUtils;
import com.tlv8.base.ActionSupport;

/**
 * @author ChenQain
 * @C 2011-12-7
 * @P 图片上传到数据库
 */
@Controller
@Scope("prototype")
public class ImageWriteAction extends ActionSupport {
	private String contentType;
	private File upload;
	private String fileName;
	private String caption;
	private String dbkey;
	private String tablename;
	private String cellname;
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

	@RequestMapping(value = "/ImageWriteAction", method = RequestMethod.POST)
	public Object upload() throws Exception {
		SqlSession session = DBUtils.getSession(dbkey);
		Connection conn = null;
		PreparedStatement pstmt = null;
		String rs = "/comon/picCompant/Imag-upload";
		try {
			if (dbkey == null || "".equals(dbkey))
				dbkey = "system";
			conn = session.getConnection();
			FileInputStream fin = new FileInputStream(upload);
			String uSQL = "update " + tablename + " set " + cellname + "=? where "
					+ (("system".equals(dbkey)) ? "sID" : "fID") + "=?";
			pstmt = conn.prepareStatement(uSQL);
			pstmt = conn.prepareStatement(uSQL);
			pstmt.setBinaryStream(1, fin, fin.available());
			pstmt.setString(2, rowid);
			pstmt.executeUpdate();
			session.commit(true);
			rs = "/comon/picCompant/upload-success";
		} catch (Exception e) {
			session.rollback(true);
			setCaption("错误:" + e.toString());
			e.printStackTrace();
		} finally {
			DBUtils.closeConn(session, conn, pstmt, null);
		}
		return rs;
	}

	public void setDbkey(String dbkey) {
		this.dbkey = dbkey;
	}

	public String getDbkey() {
		return dbkey;
	}

	public void setTablename(String tablename) {
		this.tablename = tablename;
	}

	public String getTablename() {
		return tablename;
	}

	public void setCellname(String cellname) {
		this.cellname = cellname;
	}

	public String getCellname() {
		return cellname;
	}

	public void setRowid(String rowid) {
		this.rowid = rowid;
	}

	public String getRowid() {
		return rowid;
	}

	public void setCaption(String caption) {
		this.caption = caption;
	}

	public String getCaption() {
		return caption;
	}
}
