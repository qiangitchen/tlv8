package com.tlv8.flw.base;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;

import com.tlv8.base.Sys;
import com.tlv8.base.db.DBUtils;
import com.tlv8.system.bean.ContextBean;
import com.tlv8.system.utils.ContextUtils;

@SuppressWarnings({ "rawtypes" })
public class FlowFile {
	public static final String folder_table = "SA_FLOWFOLDER";// 流程目录信息
	public static final String drawlg_table = "SA_FLOWDRAWLG";// 流程图信息
	public static final String get_drawlg_sql = "select SDRAWLG,SPROCESSACTY,SPROCESSNAME,SPROCESSID from SA_FLOWDRAWLG where SPROCESSID = '%s'";

	/*
	 * @获取流程图信息方法
	 */
	public static Map getFlowDraw(String processID) {
		Map result = new HashMap();
		String sql = String.format(get_drawlg_sql, processID);
		try {
			List list = DBUtils.execQueryforList("system", sql);
			if (list.size() > 0)
				result = (Map) list.get(0);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return result;
	}

	/*
	 * @保存流程图信息方法
	 */
	public static void saveFlowDraw(String processID, String sprocessname, String sdranwlg, String sprocessacty) {
		String sql = String.format(get_drawlg_sql, processID);
		SqlSession session = DBUtils.getSession("system");
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		ContextBean context = ContextUtils.getContext();
		String userID = context.getCurrentPersonID();
		String userName = context.getCurrentPersonName();
		try {
			List list = DBUtils.execQueryforList("system", sql);
			String acSql = "";
			if (list.size() == 0) {
				String id = Sys.getUUID();
				acSql = "insert into " + drawlg_table
						+ "(SID,SPROCESSNAME,SDRAWLG,SPROCESSACTY,SCREATORID,SCREATORNAME,SCREATETIME,VERSION,SPROCESSID)values('"
						+ id + "',?,?,?,?,?,?,0,?)";
			} else {
				acSql = "update " + drawlg_table
						+ " set SPROCESSNAME=?,SDRAWLG=?,SPROCESSACTY=?,SUPDATORID=?,SUPDATORNAME=?,SUPDATETIME=? where SPROCESSID=?";
			}
			conn = session.getConnection();
			ps = conn.prepareStatement(acSql);
			ps.setString(1, sprocessname);
			ps.setString(2, sdranwlg);
			ps.setString(3, sprocessacty);
			ps.setString(4, userID);
			ps.setString(5, userName);
			ps.setTimestamp(6, new Timestamp(new Date().getTime()));
			ps.setString(7, processID);
			ps.executeUpdate();
			session.commit(true);
		} catch (SQLException e) {
			session.rollback(true);
			e.printStackTrace();
		} finally {
			DBUtils.closeConn(session, conn, ps, rs);
		}
	}

	/*
	 * @删除流程图信息方法
	 */
	public static void deleteFlowDraw(String processID) {
		try {
			String sql = "delete from " + drawlg_table + "  where SPROCESSID ='" + processID + "'";
			DBUtils.execdeleteQuery("system", sql);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		Sys.printMsg(getFlowDraw("test1"));
	}
}
