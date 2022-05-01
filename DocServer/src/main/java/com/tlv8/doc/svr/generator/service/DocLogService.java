package com.tlv8.doc.svr.generator.service;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.tlv8.doc.svr.generator.beans.DocLog;
import com.tlv8.doc.svr.generator.beans.SqlParams;
import com.tlv8.doc.svr.generator.dao.IConnectionDao;
import com.tlv8.doc.svr.generator.dao.IDocLogDao;
import com.tlv8.doc.svr.generator.utils.IDUtils;

public class DocLogService {
	private static IConnectionDao connectiondao;

	public void setConnectiondao(IConnectionDao connectiondao) {
		DocLogService.connectiondao = connectiondao;
	}

	private static IDocLogDao doclogdao;

	public void setDoclogdao(IDocLogDao doclogdao) {
		DocLogService.doclogdao = doclogdao;
	}

	/*
	 * 写操作日志
	 * 
	 * @param {fUserID:用户ID,fAction:执行的操作,fMessage 操作的信息}
	 * 
	 * @throws Exception
	 */
	public static String AddLog(String fUserID, String fAction, String fMessage)
			throws Exception {
		String nnid = IDUtils.getGUID();
		DocLog doclog = new DocLog();
		doclog.setFID(nnid);
		doclog.setFUserID(fUserID);
		doclog.setFAddTime(new Date());
		doclog.setFAction(fAction);
		doclog.setFMessage(fMessage);
		doclog.setVersion(0);
		doclogdao.insert(doclog);
		return nnid;
	}

	/*
	 * 获取指定的日志记录
	 */
	public static DocLog getDocLog(String fID) {
		return doclogdao.getByPrimaryKey(fID);
	}

	/*
	 * 获取所有日志记录
	 */
	public static List<DocLog> getDocLogList() {
		return doclogdao.getList();
	}

	/*
	 * 获取指定条件的日志记录
	 */
	public static List<DocLog> getDocLogListByParam(String where) {
		List<DocLog> rlist = new ArrayList<DocLog>();
		where = where.trim();
		if (!where.startsWith("where")) {
			where = " where " + where;
		}
		SqlParams param = new SqlParams();
		param.setParam(where);
		Connection conn = null;
		Statement stm = null;
		ResultSet rs = null;
		try {
			conn = connectiondao.getConnection();
			stm = conn.createStatement();
			rs = stm.executeQuery("select * from Doc_Log " + param.getParam());
			while (rs.next()) {
				DocLog doclog = new DocLog();
				doclog.setFID(rs.getString("fID"));
				doclog.setFUserID(rs.getString("fUserID"));
				doclog.setFAddTime(rs.getTimestamp("fAddTime"));
				doclog.setFAction(rs.getString("fAction"));
				doclog.setFMessage(rs.getString("fMessage"));
				doclog.setVersion(rs.getInt("version"));
				rlist.add(doclog);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			connectiondao.CloseConnection(conn, stm, rs);
		}
		return rlist;
	}

	/*
	 * 清除所有日志
	 */
	public static void ClearLog() throws Exception {
		doclogdao.clearData();
	}

}
