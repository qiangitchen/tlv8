package com.tlv8.doc.clt.upload;

import java.net.URLDecoder;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import org.apache.ibatis.jdbc.SQL;
import org.apache.ibatis.session.SqlSession;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tlv8.base.Data;
import com.tlv8.base.db.DBUtils;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.tlv8.base.ActionSupport;
import com.tlv8.doc.clt.doc.Doc;
import com.tlv8.doc.clt.doc.DocDBHelper;
import com.tlv8.doc.clt.doc.DocUtils;
import com.tlv8.doc.clt.doc.Docs;

@Controller
@Scope("prototype")
public class WriteUploadDataAction extends ActionSupport {
	private String writelog;
	private Data data = new Data();

	public void setData(Data data) {
		this.data = data;
	}

	public Data getData() {
		return data;
	}

	@ResponseBody
	@RequestMapping("/writeUploadDataAction")
	@Override
	public Object execute() throws Exception {
		try {
			if (writedata(writelog))
				data.setFlag("true");
			else {
				data.setFlag("false");
				data.setMessage("WriteUploadDataAction: 参数错误!");
			}
		} catch (Exception e) {
			data.setFlag("false");
			data.setMessage(e.getMessage());
			e.printStackTrace();
		}
		return data;
	}

	public void setWritelog(String writelog) {
		try {
			this.writelog = URLDecoder.decode(writelog, "UTF-8");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public String getWritelog() {
		return writelog;
	}

	public boolean writedata(String param) throws Exception {
		JSONArray jarr = JSON.parseArray(param);
		for (int i = 0; i < jarr.size(); i++) {
			JSONObject json = jarr.getJSONObject(i);
			if (json.getString("docPath") != null) {
				String dbkey = json.getString("dbkey");
				String docPath = json.getString("docPath");
				String tablename = json.getString("tablename");
				String cellname = json.getString("cellname");
				String rowid = json.getString("rowid");
				String docName = json.getString("docName");
				String kind = json.getString("kind");
				String size = json.getString("size");
				String cacheName = json.getString("cacheName");
				String docID = DocDBHelper.addDocData(docPath, docName, kind, size, cacheName);
				String fileID = cacheName;
				SqlSession session = DBUtils.getSession(dbkey);
				Connection conn = null;
				PreparedStatement ps1 = null;
				PreparedStatement ps2 = null;
				ResultSet rs = null;
				try {
					conn = session.getConnection();
					Doc doc = Docs.queryDocById(docID);
					doc.commitFile();
					DocUtils.saveDocFlag(docPath, doc.getsKind(), doc.getsFileID(), doc.getScacheName(), false);
					fileID = doc.getsFileID();
					SQL sql1 = new SQL();
					sql1.UPDATE("SA_docNode");
					sql1.SET("SFILEID=?");
					sql1.SET("SCACHENAME=''");
					sql1.WHERE("sID=?");
					List<String> upparam = new ArrayList<>();
					upparam.add(fileID);
					upparam.add(docID);
					DBUtils.execUpdate("system", sql1.toString(), upparam);
					String keyfield = (("system".equals(dbkey)) ? "sID" : "fID");
					if (rowid != null && !"".equals(rowid) && !"undefined".equals(rowid)) {
						SQL querySql = new SQL();
						querySql.SELECT(cellname);
						querySql.FROM(tablename);
						querySql.WHERE(keyfield + "=?");
						JSONArray jsona = new JSONArray();
						ps1 = conn.prepareStatement(querySql.toString());
						ps1.setString(1, rowid);
						rs = ps1.executeQuery();
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
						JSONObject jsono = new JSONObject();
						jsono.put("fileID", fileID);
						jsono.put("filename", docName);
						jsono.put("filesize", size);
						jsona.add(jsono);
						SQL updateSql = new SQL();
						updateSql.UPDATE(tablename);
						updateSql.SET(cellname + "=?");
						updateSql.WHERE(keyfield + "=?");
						ps2 = conn.prepareStatement(updateSql.toString());
						ps2.setString(1, jsona.toString());
						ps2.setString(2, rowid);
						ps2.executeUpdate();
						session.commit(true);
					}
				} catch (Exception e) {
					session.rollback(true);
					e.printStackTrace();
				} finally {
					DBUtils.closeConn(null, null, ps1, rs);
					DBUtils.closeConn(session, conn, ps2, null);
				}
			} else {
				return false;
			}
		}
		return true;
	}

	public static String transeJson(String str) {
		str = str.replace(":", ":\"");
		str = str.replace(",", "\",");
		str = str.replace("}", "\"}");
		str = str.replace("}{", "},{");
		str = str.replace(";", "\",");
		// var filelist = eval("([" + str + "])");
		return str;
	};
}
