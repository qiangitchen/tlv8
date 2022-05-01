package com.tlv8.doc.clt.upload;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

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
	@SuppressWarnings("deprecation")
	public Object upload() throws Exception {
		try {
			if (dbkey == null || "".equals(dbkey))
				dbkey = "system";
			Connection conn = DBUtils.getAppConn(dbkey);
			FileInputStream fin = new FileInputStream(upload);
			// System.out.println("file size = " + fin.available());
			String uSQL = "update " + tablename + " set " + cellname + "=? where "
					+ (("system".equals(dbkey)) ? "sID" : "fID") + "=?";
			PreparedStatement pstmt = conn.prepareStatement(uSQL);
			pstmt = conn.prepareStatement(uSQL);
			// Sys.printMsg(uSQL);
			pstmt.setBinaryStream(1, fin, fin.available());
			pstmt.setString(2, rowid);
			pstmt.executeUpdate();
			pstmt.close();
			// conn.commit();
			conn.close();
		} catch (SQLException e) {
			System.err.println(e.getMessage());
			e.printStackTrace();
			setCaption("错误:" + e.toString());
			return "/comon/picCompant/Imag-upload";
		} catch (IOException e) {
			System.err.println(e.getMessage());
			setCaption("错误:" + e.toString());
			return "/comon/picCompant/Imag-upload";
		} catch (Exception e) {
			setCaption("错误:" + e.toString());
			e.printStackTrace();
			return "/comon/picCompant/Imag-upload";
		}
		return "/comon/picCompant/upload-success";
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
