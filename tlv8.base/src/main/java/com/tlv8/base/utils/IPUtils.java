package com.tlv8.base.utils;

import java.io.File;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.Enumeration;

import javax.servlet.http.HttpServletRequest;

import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

public class IPUtils {
	/*
	 * 获取客户端IP
	 */
	private static String IPSTART;

	/*
	 * 获取客户端IP
	 */
	public static String getRemoteAddr(HttpServletRequest req) {
		String ip = req.getHeader("X-Forwarded-For");
		if ((ip == null) || (ip.length() == 0) || ("unknown".equalsIgnoreCase(ip))) {
			ip = req.getHeader("Proxy-Client-IP");
		}
		if ((ip == null) || (ip.length() == 0) || ("unknown".equalsIgnoreCase(ip))) {
			ip = req.getHeader("WL-Proxy-Client-IP");
		}
		if ((ip == null) || (ip.length() == 0) || ("unknown".equalsIgnoreCase(ip))) {
			ip = req.getHeader("HTTP_CLIENT_IP");
		}
		if ((ip == null) || (ip.length() == 0) || ("unknown".equalsIgnoreCase(ip))) {
			ip = req.getHeader("HTTP_X_FORWARDED_FOR");
		}
		if ((ip == null) || (ip.length() == 0) || ("unknown".equalsIgnoreCase(ip))) {
			ip = req.getRemoteAddr();
		}
		if (ip.equals("0:0:0:0:0:0:0:1"))
			ip = "127.0.0.1";
		return ip;
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
}
