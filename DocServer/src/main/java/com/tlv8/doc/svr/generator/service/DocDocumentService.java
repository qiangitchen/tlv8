package com.tlv8.doc.svr.generator.service;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.tlv8.doc.svr.generator.beans.DocDocument;
import com.tlv8.doc.svr.generator.beans.SqlParams;
import com.tlv8.doc.svr.generator.dao.IConnectionDao;
import com.tlv8.doc.svr.generator.dao.IDocDocumentDao;
import com.tlv8.doc.svr.generator.utils.IDUtils;

public class DocDocumentService {
	private static IConnectionDao connectiondao;

	public void setConnectiondao(IConnectionDao connectiondao) {
		DocDocumentService.connectiondao = connectiondao;
	}

	private static IDocDocumentDao documentdao;

	public void setDocumentdao(IDocDocumentDao documentdao) {
		DocDocumentService.documentdao = documentdao;
	}

	/*
	 * 添加文档信息
	 */
	public static String addDocument(String fDocID, String fDocName,
			String fExtName, float fDocSize, String fDocType) {
		String ndid = IDUtils.getGUID();
		DocDocument doc = new DocDocument();
		doc.setFID(ndid);
		doc.setFDocID(fDocID);
		doc.setFDocName(fDocName);
		doc.setFExtName(fExtName);
		doc.setFDocSize(fDocSize);
		doc.setFDocType(fDocType);
		doc.setFAddTime(new Date());
		doc.setVersion(0);
		documentdao.insert(doc);
		return ndid;
	}

	/*
	 * 添加文档信息
	 */
	public static void addDocument(DocDocument document) {
		documentdao.insert(document);
	}

	/*
	 * 数据更新
	 */
	public static void updateDocument(String fID, String fDocID,
			String fDocName, String fExtName, float fDocSize, String fDocType) {
		DocDocument doc = documentdao.getByPrimaryKey(fID);
		if (doc != null) {
			doc.setFDocID(fDocID);
			doc.setFDocName(fDocName);
			doc.setFExtName(fExtName);
			doc.setFDocSize(fDocSize);
			doc.setFDocType(fDocType);
			doc.setFUpdateTime(new Date());
			doc.setVersion(doc.getVersion() + 1);
			documentdao.update(doc);
		}
	}

	/*
	 * 数据更新
	 */
	public static void updateDocument(DocDocument document) {
		documentdao.update(document);
	}

	/*
	 * 根据fID获取文档信息
	 */
	public static DocDocument getDocumentByFID(String fID) {
		return documentdao.getByPrimaryKey(fID);
	}

	/*
	 * 根据fDocID获取文档信息
	 */
	public static DocDocument getDocumentByDocID(String fDocID) {
		return documentdao.getByDocID(fDocID);
	}

	/*
	 * 根据FID删除数据
	 */
	public static int deleteDocumentByFID(String fID) {
		return documentdao.deleteByPrimaryKey(fID);
	}

	/*
	 * 根据文档ID删除数据
	 */
	public static int deleteDocumentByDocID(String fDocID) {
		return documentdao.deleteByDocID(fDocID);
	}

	/*
	 * 获取所有文档信息
	 */
	public static List<DocDocument> getDocumentList() {
		return documentdao.getList();
	}

	public static List<DocDocument> getDocumentListByParam(String where) {
		List<DocDocument> rlist = new ArrayList<DocDocument>();
		SqlParams param = new SqlParams();
		param.setParam(where);
		Connection conn = null;
		Statement stm = null;
		ResultSet rs = null;
		try {
			conn = connectiondao.getConnection();
			stm = conn.createStatement();
			rs = stm.executeQuery("select * from Doc_Document "
					+ param.getParam());
			while (rs.next()) {
				DocDocument doc = new DocDocument();
				doc.setFID(rs.getString("fID"));
				doc.setFDocID(rs.getString("fDocID"));
				doc.setFDocName(rs.getString("fDocName"));
				doc.setFExtName(rs.getString("fExtName"));
				doc.setFDocSize(rs.getFloat("fDocSize"));
				doc.setFDocType(rs.getString("fDocType"));
				doc.setFAddTime(rs.getTimestamp("fAddTime"));
				doc.setFUpdateTime(rs.getTimestamp("fUpdateTime"));
				doc.setVersion(rs.getInt("version"));
				rlist.add(doc);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			connectiondao.CloseConnection(conn, stm, rs);
		}
		return rlist;
	}
}
