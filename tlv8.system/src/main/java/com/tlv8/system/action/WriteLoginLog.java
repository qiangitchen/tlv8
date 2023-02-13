package com.tlv8.system.action;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.apache.ibatis.session.SqlSession;

import com.tlv8.base.db.DBUtils;
import com.tlv8.base.utils.IDUtils;
import com.tlv8.base.utils.IPUtils;
import com.tlv8.system.BaseController;
import com.tlv8.system.help.PassCodeHelper;

public class WriteLoginLog extends BaseController {

	public static void write(String userID, String userName, String sIP, String password, HttpServletRequest request) {
		if (sIP == null || "0:0:0:0:0:0:0:1".equals(sIP))
			sIP = "127.0.0.1";
		String logPassword = PassCodeHelper.enCode(password);
		String serverip = IPUtils.getPermisServerIP(request);
		String sql = "insert into SA_LOGINLOG(SID,SUSERID,SUSERNAME,SLOGINIP,SLOGINTIME,PASSWORD,VERSION,SSERVICEIP,SDAY,SDAYNUM)values(?,?,?,?,?,?,?,?,?,?)";
		SqlSession session = DBUtils.getSession("system");
		Connection conn = null;
		PreparedStatement ps = null;
		try {
			conn = session.getConnection();
			ps = conn.prepareStatement(sql);
			ps.setString(1, IDUtils.getGUID());
			ps.setString(2, userID);
			ps.setString(3, userName);
			ps.setString(4, sIP);
			ps.setTimestamp(5, new Timestamp(new Date().getTime()));
			ps.setString(6, logPassword);
			ps.setInt(7, 0);
			ps.setString(8, serverip);
			Calendar calendar = Calendar.getInstance();
			ps.setString(9, dateToWeek(new Date()));
			ps.setInt(10, calendar.get(Calendar.DAY_OF_WEEK));
			ps.executeUpdate();
			session.commit(true);
		} catch (SQLException e) {
			session.rollback(true);
			e.printStackTrace();
		} finally {
			DBUtils.closeConn(session, conn, ps, null);
		}
	}

	/**
	 * 日期转星期
	 *
	 * @param datet
	 * @return
	 */
	public static String dateToWeek(Date datet) {
		String[] weekDays = { "星期日", "星期一", "星期二", "星期三", "星期四", "星期五", "星期六" };
		// 获得一个日历
		Calendar cal = Calendar.getInstance();
		cal.setTime(datet);
		// 指示一个星期中的某天。
		int w = cal.get(Calendar.DAY_OF_WEEK) - 1;
		if (w < 0) {
			w = 0;
		}
		return weekDays[w];
	}

}
