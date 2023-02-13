package com.tlv8.doc.clt.upload;

import java.net.URLDecoder;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

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
				try {
					Doc doc = Docs.queryDocById(docID);
					doc.commitFile();
					DocUtils.saveDocFlag(docPath, doc.getsKind(), doc.getsFileID(), doc.getScacheName(), false);
					fileID = doc.getsFileID();
					DBUtils.execUpdateQuery("system", "update SA_docNode set SFILEID = '" + fileID
							+ "',SCACHENAME='' where sID = '" + docID + "'");
				} catch (Exception e) {
					e.printStackTrace();
				}
				if (rowid != null && !"".equals(rowid) && !"undefined".equals(rowid)) {
					String querySql = "select " + cellname + " from " + tablename + " where :KEYCELL = '" + rowid + "'";
					String updateSql = "update " + tablename + " set " + cellname + " = ? where :KEYCELL = '" + rowid
							+ "'";
					if ("system".equals(dbkey)) {
						querySql = querySql.replace(":KEYCELL", "SID");
						updateSql = updateSql.replace(":KEYCELL", "SID");
					} else {
						querySql = querySql.replace(":KEYCELL", "FID");
						updateSql = updateSql.replace(":KEYCELL", "FID");
					}
					SqlSession session = DBUtils.getSession(dbkey);
					Connection conn = null;
					PreparedStatement ps = null;
					Statement stm = null;
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
						JSONObject jsono = new JSONObject();
						jsono.put("fileID", fileID);
						jsono.put("filename", docName);
						jsono.put("filesize", size);
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
