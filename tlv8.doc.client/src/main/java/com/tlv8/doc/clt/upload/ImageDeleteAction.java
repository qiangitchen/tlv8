package com.tlv8.doc.clt.upload;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tlv8.base.db.DBUtils;
import com.tlv8.base.ActionSupport;

/**
 * @P 删除图片
 * @author ChenQain
 * @C 2011-12-8
 */
@Controller
@Scope("prototype")
public class ImageDeleteAction extends ActionSupport {
	private String fileName;
	private String caption;
	private String flag;
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

	@ResponseBody
	@RequestMapping("/ImageDeleteAction")
	@Override
	@SuppressWarnings("deprecation")
	public Object execute() throws Exception {
		try {
			Connection conn = DBUtils.getAppConn(dbkey);
			String uSQL = "update " + tablename + " set " + cellname
					+ "=null where "
					+ (("system".equals(dbkey)) ? "sID" : "fID") + "=?";
			PreparedStatement pstmt = conn.prepareStatement(uSQL);
			pstmt = conn.prepareStatement(uSQL);
			// Sys.printMsg(uSQL);
			pstmt.setString(1, rowid);
			pstmt.executeUpdate();
			pstmt.close();
			//conn.commit();
			conn.close();
		} catch (SQLException e) {
			System.err.println(e.getMessage());
			e.printStackTrace();
			setCaption("错误:" + e.toString());
			setFlag("false");
		} catch (Exception e) {
			setCaption("错误:" + e.toString());
			e.printStackTrace();
			setFlag("false");
		}
		setFlag("true");
		return this;
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

	public void setFlag(String flag) {
		this.flag = flag;
	}

	public String getFlag() {
		return flag;
	}
}
