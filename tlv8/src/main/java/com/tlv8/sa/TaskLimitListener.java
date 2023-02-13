package com.tlv8.sa;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.apache.ibatis.session.SqlSession;

import com.tlv8.base.Sys;
import com.tlv8.base.db.DBUtils;

public class TaskLimitListener implements ServletContextListener {
	String sql = "select SID,SPROCESS,SACTIVITY,SCREATETIME from sa_task t where t.SLIMITTIME is null and EXISTS(select sid from sa_task_timelimit l where l.SPROCESSID=t.SPROCESS and l.SACTIVITY=t.SACTIVITY) and SSTATUSID='tesReady'";
	String lsql = "select SDLIMIT from sa_task_timelimit l where l.SPROCESSID=? and l.SACTIVITY=?";
	String usql = "update sa_task set SLIMITTIME = ? where sid = ?";

	@Override
	public void contextDestroyed(ServletContextEvent arg0) {

	}

	@Override
	public void contextInitialized(ServletContextEvent arg0) {
		Sys.printMsg("启动任务时限监听");
		start();
	}

	public void start() {
		new Thread(new Runnable() {
			public void run() {
				while (true) {
					try {
						Thread.sleep(1000);// 间隔1秒
					} catch (Exception e) {
					}
					towork();
				}
			}
		}).start();
	}

	@SuppressWarnings({ "rawtypes" })
	private void towork() {
		SqlSession session = DBUtils.getSession("system");
		Connection conn = null;
		PreparedStatement ps = null;
		try {
			List<Map<String, Object>> resultCount = DBUtils.selectList(session, sql);
			if (resultCount.size() > 0) {
				Map data = resultCount.get(0);
				String taskid = data.get("SID").toString();
				String peocess = data.get("SPROCESS").toString();
				String activity = data.get("SACTIVITY").toString();
				Date sdate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(data.get("SCREATETIME").toString());
				
				List<Object> pa = new ArrayList<Object>();
				pa.add(peocess);
				pa.add(activity);
				List<Map<String, String>> ll = DBUtils.selectStringList(session, lsql, pa);
				int d = Integer.valueOf(ll.get(0).get("SDLIMIT"));

				Calendar calendar = Calendar.getInstance();
				calendar.setTime(sdate);
				calendar.add(Calendar.DATE, d);

				conn = session.getConnection();
				ps = conn.prepareStatement(usql);
				ps.setTimestamp(1, new Timestamp(calendar.getTime().getTime()));
				ps.setString(2, taskid);
				ps.executeUpdate();
				session.commit(true);
			}
		} catch (Exception e) {
			session.rollback(true);
		}finally {
			DBUtils.closeConn(session, conn, ps, null);
		}
	}
}
