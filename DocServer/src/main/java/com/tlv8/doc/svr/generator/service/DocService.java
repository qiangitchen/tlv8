package com.tlv8.doc.svr.generator.service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

import com.tlv8.doc.svr.generator.dao.IConnectionDao;

public class DocService {
	private static long docIndex = 1;
	/*
	 * 其他辅助表的操作<使用spring SqlConnection>
	 */
	private static IConnectionDao connectiondao;

	public void setConnectiondao(IConnectionDao connectiondao) {
		DocService.connectiondao = connectiondao;
		Connection conn = null;
		Statement stm = null;
		ResultSet rs = null;
		try {
			conn = connectiondao.getConnection();
			stm = conn.createStatement();
			rs = stm.executeQuery("select fMax from Doc_Index");
			if (rs.next()) {
				docIndex = rs.getLong(1);
				ResultSet rs1 = stm.executeQuery("select count(*) from Doc_Document");
				if (rs1.next()) {
					long cn = rs1.getLong(1);
					if (docIndex < cn) {
						docIndex = cn * 10;
						stm.executeUpdate("update Doc_Index set fMax=" + docIndex);
					}
				}
			} else {
				stm.executeUpdate("insert into Doc_Index(fMax)values(1)");
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			connectiondao.CloseConnection(conn, stm, rs);
		}
	}

	/*
	 * 获取新的文档id（兼容老系统）
	 */
	public synchronized static String getNewDocID() {
		docIndex++;
		String docid = "";
		Connection conn = null;
		Statement stm = null;
		try {
			conn = connectiondao.getConnection();
			stm = conn.createStatement();
			stm.executeUpdate("update Doc_Index set fMax = " + docIndex);
			docid = docIndex + "-root";
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			connectiondao.CloseConnection(conn, stm, null);
		}
		return docid;
	}

	/*
	 * 保留老系统逻辑
	 */
	public synchronized static String getNewFileID() {
		docIndex++;
		String docid = "";
		Connection conn = null;
		Statement stm = null;
		try {
			conn = connectiondao.getConnection();
			stm = conn.createStatement();
			stm.executeUpdate("update Doc_Index set fMax = " + docIndex);
			docid = docIndex + "-DOC";
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			connectiondao.CloseConnection(conn, stm, null);
		}
		return docid;
	}

	/*
	 * 根据位置回去ID（预留）
	 */
	public synchronized static String getNewDocIDBydocPath(String docPath) {
		String docid = "";
		Connection conn = null;
		Statement stm = null;
		ResultSet rs = null;
		try {
			conn = connectiondao.getConnection();
			stm = conn.createStatement();
			long cuindex = 1;
			rs = stm.executeQuery("select fMax from Doc_PathIndex fDocPath ='" + docPath + "'");
			if (rs.next()) {
				cuindex = rs.getLong(1) + 1;
				stm.executeUpdate("update Doc_PathIndex set fMax = " + cuindex + " where fDocPath = '" + docPath + "'");
			} else {
				stm.executeUpdate("insert into Doc_PathIndex(fDocPath,fMax)values('" + docPath + "',1)");
			}
			docid = cuindex + "-root";
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			connectiondao.CloseConnection(conn, stm, rs);
		}
		return docid;
	}

	/*
	 * 存储键值信息
	 */
	public synchronized static void setCustomField(String filedID, String text) {
		Connection conn = null;
		PreparedStatement ps = null;
		try {
			conn = connectiondao.getConnection();
			ps = conn.prepareStatement("insert into Doc_Result(resultID,resultContent)values(?,?)");
			ps.setString(1, filedID);
			ps.setString(2, text);
			ps.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			connectiondao.CloseConnection(conn, ps, null);
		}
	}

	/*
	 * 获取信息
	 */
	public static String getCustomField(String filedID) {
		String resultText = "";
		Connection conn = null;
		Statement stm = null;
		ResultSet rs = null;
		try {
			conn = connectiondao.getConnection();
			stm = conn.createStatement();
			rs = stm.executeQuery("select resultContent from Doc_Result resultID = ?");
			if (rs.next()) {
				resultText = rs.getString(1);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			connectiondao.CloseConnection(conn, stm, rs);
		}
		return resultText;
	}
}
