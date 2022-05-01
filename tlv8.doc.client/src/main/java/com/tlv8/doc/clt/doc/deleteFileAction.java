package com.tlv8.doc.clt.doc;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.List;
import java.util.Map;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tlv8.base.Data;
import com.tlv8.base.db.DBUtils;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.tlv8.base.ActionSupport;

@Controller
@Scope("prototype")
public class deleteFileAction extends ActionSupport {
	/**
	 * @P 删除文件
	 * @author ChenQain
	 * @C 2011-12-7
	 */
	private Data data = new Data();
	private String fileID;
	private String filename;
	private String dbkey;
	private String tablename;
	private String cellname;
	private String rowid;

	public Data getdata() {
		return this.data;
	}

	public void setdata(Data data) {
		this.data = data;
	}

	public void setFileID(String fileID) {
		this.fileID = fileID;
	}

	@ResponseBody
	@RequestMapping("/deleteFileAction")
	@Override
	public Object execute() throws Exception {
		data = new Data();
		String r = "";
		String m = "success";
		String f = "";
		try {
			r = deleteData();
			f = "true";
		} catch (Exception e) {
			m = "操作失败：" + e.toString();
			f = "false";
			e.printStackTrace();
		}
		data.setData(r);
		data.setFlag(f);
		data.setMessage(m);
		return data;
	}

	private String deleteData() throws Exception {
		String dSQL = "delete from SA_DOCNODE where SFILEID = '" + fileID + "'";
		String qSQL = "select " + cellname.toUpperCase() + " from " + tablename + " where fID = '" + rowid + "'";
		String item = "{filename:" + filename + ";fileID:" + fileID + "}";
		String uSQL = "update " + tablename + " set " + cellname + "=replace(" + cellname + ",'" + item
				+ "','') where fID='" + rowid + "'";
		if (dbkey == null || "system".equals(dbkey)) {
			qSQL = qSQL.replace("fID", "sID");
		}
		try {
			List<Map<String, String>> list = DBUtils.execQueryforList(dbkey, qSQL);
			if (list.size() > 0) {
				String fileInfo = list.get(0).get(cellname.toUpperCase());
				JSONArray json = JSON.parseArray(fileInfo);
				for (int i = 0; i < json.size(); i++) {
					if (json.getJSONObject(i).getString("fileID").equals(fileID)) {
						json.remove(i);
					}
				}
				uSQL = "update " + tablename + " set " + cellname + "='" + json.toString() + "' where fID='" + rowid
						+ "'";
			}
		} catch (Exception e) {
		}
		if (dbkey == null || "system".equals(dbkey)) {
			uSQL = uSQL.replace("fID", "sID");
		}
		String docID = null;
		List<Map<String, String>> list = DBUtils.execQueryforList("system",
				"select SID,SDOCNAME from SA_DOCNODE where SFILEID = '" + fileID + "'");
		if (list.size() > 0) {
			docID = list.get(0).get("SID").toString();
		}
		try {
			Doc doc = Docs.queryDocById(docID);
			doc.deleteFile();
		} catch (Exception e) {
		}
		try {
			DBUtils.execUpdateQuery(dbkey, uSQL);
		} catch (Exception e) {
			throw new Exception(e + uSQL);
		}
		try {
			DBUtils.execUpdateQuery("system", dSQL);
		} catch (Exception e) {
			throw new Exception(e + dSQL);
		}
		return cellname;
	}

	public String getFileID() {
		return fileID;
	}

	public void setFilename(String filename) {
		try {
			this.filename = URLDecoder.decode(filename, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
	}

	public String getFilename() {
		return filename;
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
}
