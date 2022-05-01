package com.tlv8.system.action;

import java.io.File;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Date;
import java.util.Enumeration;

import javax.servlet.http.HttpServletRequest;

import org.apache.ibatis.session.SqlSession;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import com.tlv8.base.db.DBUtils;
import com.tlv8.base.utils.IDUtils;
import com.tlv8.system.BaseController;
import com.tlv8.system.help.PassCodeHelper;

public class WriteLoginLog extends BaseController {
	private static String IPSTART;

	public static void write(String userID, String userName, String sIP, String password, HttpServletRequest request) {
		if (sIP == null || "0:0:0:0:0:0:0:1".equals(sIP))
			sIP = "127.0.0.1";
		String logPassword = PassCodeHelper.enCode(password);
		String serverip = getPermisServerIP(request);
		String sql = "insert into SA_LOGINLOG(SID,SUSERID,SUSERNAME,SLOGINIP,SLOGINTIME,PASSWORD,VERSION,SSERVICEIP,SDAY,SDAYNUM)values(?,?,?,?,?,?,?,?,?,?)";
		SqlSession session = DBUtils.getSession("system");
		PreparedStatement ps = null;
		try {
			ps = session.getConnection().prepareStatement(sql);
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
			DBUtils.CloseConn(session, null, ps, null);
		}
	}

	public static String getPermisServerIP(HttpServletRequest request) {
		String serverip = "";
		String serverperm = request.getServletContext().getRealPath("/WEB-INF/serverip.xml");
		SAXReader saxreader = new SAXReader();
		try {
			File configfile = new File(serverperm);
			if (!configfile.exists()) {
				serverip = getHostIP();
			} else if (IPSTART == null) {
				Document doc = saxreader.read(configfile);
				Element root = doc.getRootElement();
				Element server = root.element("server");
				IPSTART = server.attributeValue("ipstart");
			}
			serverip = getLocalIPForJava(IPSTART);
		} catch (Exception e) {
			serverip = getHostIP();
		}
		return serverip;
	}

	public static String getHostIP() {
		String ip = "127.0.0.1";
		InetAddress addr = null;
		try {
			addr = InetAddress.getLocalHost();
			addr.getHostAddress().toString(); // 获得本机IP
		} catch (UnknownHostException e) {
		}
		return ip;
	}

	public static String getLocalIPForJava(String startm) throws Exception {
		String svip = null;
		try {
			Enumeration<NetworkInterface> en = NetworkInterface.getNetworkInterfaces();
			while (en.hasMoreElements()) {
				NetworkInterface intf = en.nextElement();
				Enumeration<InetAddress> enumIpAddr = intf.getInetAddresses();
				while (enumIpAddr.hasMoreElements()) {
					InetAddress inetAddress = enumIpAddr.nextElement();
					if (!inetAddress.isLoopbackAddress() && !inetAddress.isLinkLocalAddress()) {
						String servIP = inetAddress.getHostAddress();
						if (!"".equals(servIP) && servIP.startsWith(startm)) {
							svip = servIP;
							break;
						}
					}
				}
			}
		} catch (SocketException e) {
		}
		if (svip == null) {
			throw new Exception("没有找到配置相关的IP");
		}
		return svip;
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

	public static void main(String[] args) throws Exception {
		InetAddress addr = InetAddress.getLocalHost();
		String ip = addr.getHostAddress().toString(); // 获得本机IP
		String address = addr.getHostName().toString();// 获得本机名称
		System.out.println("ip:" + ip);
		System.out.println("address:" + address);
		System.out.println("java ip:" + getLocalIPForJava("192.168.1."));
	}
}
