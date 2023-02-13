package com.tlv8.mobile.log;

import java.sql.Connection;
import java.sql.PreparedStatement;

import javax.servlet.http.HttpServletRequest;

import org.apache.ibatis.session.SqlSession;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tlv8.base.db.DBUtils;
import com.tlv8.base.ActionSupport;
import com.tlv8.system.bean.ContextBean;

/**
 * @category 手机访问日志
 * @author ChenQian
 */
@Controller
@Scope("prototype")
public class MobileAppLogAction extends ActionSupport {

	@ResponseBody
	@RequestMapping("/mobileAppLogAction")
	public Object execute() throws Exception {
		SqlSession session = DBUtils.getSession("system");
		Connection conn = null;
		PreparedStatement ps = null;
		String sql = "insert into SA_OPMOBILELOG(SID,SUSERID,SUSERNAME,SIP,SDATE,SMODE,VERSION)"
				+ "values(sys_guid(),?,?,?,sysdate,?,0)";
		ContextBean context = ContextBean.getContext(request);
		try {
			String sIP = getRemoteAddr(request);
			conn = session.getConnection();
			ps = conn.prepareStatement(sql);
			ps.setString(1, context.getCurrentPersonID());
			ps.setString(2, context.getCurrentPersonName());
			ps.setString(3, sIP);
			ps.setString(4, "手机页面访问");
			ps.executeUpdate();
			session.commit(true);
		} catch (Exception e) {
			session.rollback(true);
			e.printStackTrace();
		} finally {
			DBUtils.closeConn(session, conn, ps, null);
		}
		return this;
	}

	public static String getRemoteAddr(HttpServletRequest req) {
		String ip = req.getHeader("X-Forwarded-For");
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = req.getHeader("Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = req.getHeader("WL-Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = req.getHeader("HTTP_CLIENT_IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = req.getHeader("HTTP_X_FORWARDED_FOR");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = req.getRemoteAddr();
		}
		if (ip == null || "0:0:0:0:0:0:0:1".equals(ip))
			ip = "127.0.0.1";
		return ip;
	}
}
