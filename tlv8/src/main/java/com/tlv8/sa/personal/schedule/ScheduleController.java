package com.tlv8.sa.personal.schedule;

import java.net.URLDecoder;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.HashMap;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tlv8.base.db.DBUtils;

@Controller
@RequestMapping("/schedule")
public class ScheduleController {

	/**
	 * 更新日程数据
	 */
	@ResponseBody
	@RequestMapping("/updateScheduleData")
	public Object updateScheduleData(String caption, String priority, String newSdateA, String newSdateB,
			String newEdateA, String newEdateB, String content, String stime_a, String etime_a, String affairsID) {
		Map<String, Object> res = new HashMap<String, Object>();
		String sql = "update sa_psnschedule set SCAPTION=?,SPRIORITY=?,SSTARTDATE=?,SENDDATE=?,SCONTENT=?,SSTARTDATE_AXIS=?,SSENDDATE_AXIS=? where SID=?";
		SqlSession session = DBUtils.getSession("oa");
		Connection conn = null;
		PreparedStatement ps = null;
		try {
			conn = session.getConnection();
			ps = conn.prepareStatement(sql);
			ps.setString(1, deCode(caption));
			ps.setString(2, deCode(priority));
			ps.setString(3, deCode(newSdateA) + " " + deCode(newSdateB));
			ps.setString(4, deCode(newEdateA) + " " + deCode(newEdateB));
			ps.setString(5, deCode(content));
			ps.setString(6, deCode(stime_a));
			ps.setString(7, deCode(etime_a));
			ps.setString(8, deCode(affairsID));
			ps.executeUpdate();
			session.commit(true);
			res.put("state", true);
		} catch (Exception e) {
			res.put("state", false);
			session.rollback(true);
			e.printStackTrace();
		} finally {
			DBUtils.closeConn(session, conn, ps, null);
		}
		return res;
	}

	/**
	 * 完成日程安排
	 */
	@ResponseBody
	@RequestMapping("/finishSchedule")
	public Object finishSchedule(String affairsID) {
		Map<String, Object> res = new HashMap<String, Object>();
		String sql = "update sa_psnmytask set SSTATUS='已完成',SCOMPLETERATE='100' where SID=?";
		SqlSession session = DBUtils.getSession("oa");
		Connection conn = null;
		PreparedStatement ps = null;
		try {
			conn = session.getConnection();
			ps = conn.prepareStatement(sql);
			ps.setString(1, deCode(affairsID));
			ps.executeUpdate();
			session.commit(true);
			res.put("state", true);
		} catch (Exception e) {
			res.put("state", false);
			session.rollback(true);
			e.printStackTrace();
		} finally {
			DBUtils.closeConn(session, conn, ps, null);
		}
		return res;
	}

	String deCode(String p) {
		try {
			p = URLDecoder.decode(p, "UTF-8");
		} catch (Exception e) {
		}
		return p;
	}

}
