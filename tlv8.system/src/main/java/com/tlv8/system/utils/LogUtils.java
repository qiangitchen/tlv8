package com.tlv8.system.utils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.Timestamp;
import java.util.Date;

import org.apache.ibatis.session.SqlSession;

import com.tlv8.base.Sys;
import com.tlv8.base.db.DBUtils;
import com.tlv8.system.BaseController;
import com.tlv8.system.bean.ContextBean;

public class LogUtils {

	/**
	 * @sActivityName 功能
	 * @sProcessName 功能模块
	 * @sActionName 操作
	 * @sTypeName 类别
	 * @sIP 访问IP
	 * @sDescription 描述
	 * @customer 客户终端（手机或电脑）
	 */
	public static void WriteActionLogs(String sActivityName, String sProcessName, String sActionName, String sIP,
			String sDescription, String customer, String typeName) {
		ContextBean context = new BaseController().getContext();
		if (typeName == null) {
			typeName = "功能";
			if (!sActionName.startsWith("查看")) {
				typeName = "动作";
			}
		}
		String sql = "insert into SA_LOG(sID,version,SDESCRIPTION,"
				+ "SCREATORFID,SCREATORFNAME,SCREATORPERSONID,SCREATORPERSONNAME,SCREATORPOSID,SCREATORPOSNAME,"
				+ "SCREATORDEPTID,SCREATORDEPTNAME,SCREATOROGNID,SCREATOROGNNAME,SCREATETIME,"
				+ "SPROCESSNAME,SACTIVITYNAME,SACTIONNAME,SIP,STYPENAME)values(" + "?,0,?,?,?,'"
				+ context.getCurrentPersonID() + "','" + context.getCurrentPersonName() + "','"
				+ context.getCurrentPositionID() + "','" + context.getCurrentPositionName() + "'," + "'"
				+ context.getCurrentDeptID() + "','" + context.getCurrentDeptName() + "','" + context.getCurrentOgnID()
				+ "','" + context.getCurrentOgnName() + "',?," + "'" + sProcessName + "','" + sActivityName + "','"
				+ sActionName + "-" + customer + "','" + sIP + "',?)";
		SqlSession session = DBUtils.getSession("system");
		Connection conn = null;
		PreparedStatement ps = null;
		try {
			conn = session.getConnection();
			ps = conn.prepareStatement(sql);
			ps.setString(1, Sys.getUUID());
			ps.setString(2, sDescription);
			ps.setString(3, context.getCurrentPersonFullID());
			ps.setString(4, context.getCurrentPersonFullName());
			ps.setTimestamp(5, new Timestamp(new Date().getTime()));
			ps.setString(6, typeName);
			ps.executeUpdate();
			session.commit(true);
		} catch (Exception e) {
			session.rollback(true);
			e.printStackTrace();
		} finally {
			DBUtils.CloseConn(session, conn, ps, null);
		}
	}
}
