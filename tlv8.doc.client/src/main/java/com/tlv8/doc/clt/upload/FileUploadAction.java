package com.tlv8.doc.clt.upload;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.tlv8.base.Sys;
import com.tlv8.base.db.DBUtils;
import com.tlv8.core.utils.UserFullInfo;
import com.tlv8.base.ActionSupport;
import com.tlv8.doc.clt.doc.AbstractDoc;
import com.tlv8.doc.clt.doc.Doc;
import com.tlv8.doc.clt.doc.DocUtils;
import com.tlv8.doc.clt.doc.Docinfo;
import com.tlv8.doc.clt.doc.Docs;

/**
 * Show case File Upload example's action. <code>FileUploadAction</code>
 * 
 */
@Controller
@Scope("prototype")
@SuppressWarnings("rawtypes")
public class FileUploadAction extends ActionSupport {
	private String contentType;
	private File upload;
	private String fileName;
	private String caption;
	private String dbkey;
	private String docPath;
	private String tablename;
	private String cellname;
	private String rowid;
	private String size;
	private String personID;

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

	public String input() throws Exception {
		return SUCCESS;
	}

	@RequestMapping(value = "/upload", method = RequestMethod.POST)
	public String upload() throws Exception {
		String docNamePath = (getDocPath() == null || "".equals(getDocPath())
				|| "NULL".equals(getDocPath().toUpperCase()) || "undefined"
				.equals(getDocPath())) ? "/" : getDocPath();
		if ("/".equals(docNamePath)) {
			docNamePath = "/root";
		}
		Map docinfo = upLoadFiletoDaisy(docNamePath, getUploadFileName(),
				getUpload());
		String cacheName = (String) docinfo.get("cacheName");
		Float si = (Float) docinfo.get("Size") / 1024;
		if (si > 0.01) {
			si = Float.valueOf(Math.round(si * 100)) / 100;
		}
		setSize(String.valueOf(si));// 按k计算并保留两位小数
		HashMap<String, String> user = UserFullInfo.get(this.getPersonID());
		String PersonName = user.get("currentPersonName") == null ? "" : user
				.get("currentPersonName");
		String PersonFID = user.get("currentPersonFullID") == null ? "" : user
				.get("currentPersonFullID");
		String CREATORDEPTNAME = user.get("currentDeptName") == null ? ""
				: user.get("currentDeptName");
		String sql = ("insert into SA_DOCNODE(sID,SPARENTID,SDOCNAME,SSIZE,SKIND,SDOCPATH,SDOCDISPLAYPATH,SFILEID,"
				+ "SKEYWORDS,SCREATORID,SCREATORFID,SCREATORNAME,SCREATORDEPTNAME,SCREATETIME,version,sFlag,SCACHENAME) ")
				.toUpperCase();
		String sID = UUID.randomUUID().toString().toUpperCase()
				.replaceAll("-", "");
		if (DBUtils.IsOracleDB("system")) {
			sql += " select '" + sID + "',t.SID,'" + getUploadFileName()
					+ "','" + getSize() + "','" + getUploadContentType()
					+ "',SDOCPATH||'/" + sID + "'" + ",SDOCDISPLAYPATH||'/"
					+ getUploadFileName() + "',''," + "'" + getUploadFileName()
					+ "','" + getPersonID() + "','" + PersonFID + "','"
					+ PersonName + "','" + CREATORDEPTNAME + "',sysdate,0,1,'"
					+ cacheName
					+ "' from SA_DOCNODE t where SDOCDISPLAYPATH like '"
					+ docNamePath + "'";
		} else if (DBUtils.IsMySQLDB("system")) {
			sql += " select '" + sID + "',t.SID,'" + getUploadFileName()
					+ "','" + getSize() + "','" + getUploadContentType()
					+ "',concat(SDOCPATH,'/" + sID + "')"
					+ ",concat(SDOCDISPLAYPATH,'/" + getUploadFileName()
					+ "'),''," + "'" + getUploadFileName() + "','"
					+ getPersonID() + "','" + PersonFID + "','" + PersonName
					+ "','" + CREATORDEPTNAME + "',now(),0,1,'" + cacheName
					+ "' from SA_DOCNODE t where SDOCDISPLAYPATH like '"
					+ docNamePath + "'";
		} else {
			sql += " select '" + sID + "',t.SID,'" + getUploadFileName()
					+ "','" + getSize() + "','" + getUploadContentType()
					+ "',concat(SDOCPATH,'/" + sID + "')"
					+ ",concat(SDOCDISPLAYPATH,'/" + getUploadFileName()
					+ "'),''," + "'" + getUploadFileName() + "','"
					+ getPersonID() + "','" + PersonFID + "','" + PersonName
					+ "','" + CREATORDEPTNAME + "',getdate(),0,1,'" + cacheName
					+ "' from SA_DOCNODE t where SDOCDISPLAYPATH like '"
					+ docNamePath + "'";
		}
		try {
			DBUtils.execUpdateQuery("system", sql);
		} catch (Exception e) {
			setCaption(e.toString());
			System.out.println(e.toString() + sql);
			return "/comon/fileupload/upload";
		}
		String fileID = cacheName;
		try {
			Doc doc = Docs.queryDocById(sID);
			doc.commitFile();
			DocUtils.saveDocFlag(docNamePath, doc.getsKind(), doc.getsFileID(),
					doc.getScacheName(), false);
			fileID = doc.getsFileID();
			DBUtils.execUpdateQuery("system",
					"update SA_docNode set SFILEID = '" + fileID
							+ "' where sID = '" + sID + "'");
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (rowid != null && !"".equals(rowid) && !"undefined".equals(rowid)) {
			if (DBUtils.IsOracleDB("system")) {
				sql = "update " + tablename + " set " + cellname + " = nvl("
						+ cellname + ",'')||'{filename:" + getUploadFileName()
						+ ";fileID:" + fileID + "}' where FID = '" + rowid
						+ "'";
			} else if (DBUtils.IsMySQLDB("system")) {
				sql = "update " + tablename + " set " + cellname
						+ " = concat(ifNull(" + cellname + ",''),'{filename:"
						+ getUploadFileName() + ";fileID:" + fileID
						+ "}') where FID = '" + rowid + "'";
			} else {
				sql = "update " + tablename + " set " + cellname + " = isNull("
						+ cellname + ",'')+'{filename:" + getUploadFileName()
						+ ";fileID:" + fileID + "}' where FID = '" + rowid
						+ "'";
			}
			if ("system".equals(getDbkey()) || getDbkey() == null)
				sql = sql.replace("FID", "SID");
			// Sys.printMsg(sql);
			try {
				DBUtils.execUpdateQuery(getDbkey(), sql);
			} catch (Exception e) {
				setCaption(e.toString());
				Sys.printMsg(sql);
				e.printStackTrace();
				return "/comon/fileupload/upload-success";
			}
		}
		String nsSQL = ("update SA_DOCNODE set SNAMESPACE = (select sID from sa_docnamespace where sFlag = 1),SFLAG = '1' where sID = '"
				+ sID + "'").toUpperCase();
		try {
			DBUtils.execUpdateQuery("system", nsSQL);
		} catch (Exception e) {
			setCaption(e.toString());
			Sys.printMsg(sql);
			e.printStackTrace();
			return "/comon/fileupload/upload";
		}
		return "/comon/fileupload/upload";
	}

	@SuppressWarnings("unchecked")
	private Map upLoadFiletoDaisy(String DocPath, String DocName, File file)
			throws Exception {
		Map m = new HashMap();
		m.put("sDocPath", DocPath);
		m.put("sDocName", DocName);
		Docinfo di = new Docinfo(m);
		AbstractDoc doca = new AbstractDoc(di);
		doca.upload(false, file);
		Map rem = new HashMap();
		rem.put("Kind", doca.getsKind());
		rem.put("cacheName", doca.getScacheName());
		rem.put("Size", doca.getsSize());
		return rem;
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

	public void setSize(String size) {
		this.size = size;
	}

	public String getSize() {
		return size;
	}

	public void setDocPath(String docPath) {
		this.docPath = docPath;
	}

	public String getDocPath() {
		return docPath;
	}

	public void setDbkey(String dbkey) {
		this.dbkey = dbkey;
	}

	public String getDbkey() {
		return dbkey;
	}

	public void setPersonID(String personID) {
		this.personID = personID;
	}

	public String getPersonID() {
		return personID;
	}
}
