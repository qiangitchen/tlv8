package com.tlv8.core.controller;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.Timestamp;
import java.util.Date;
import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;

import org.apache.ibatis.session.SqlSession;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tlv8.base.ActionSupport;
import com.tlv8.base.Data;
import com.tlv8.base.Sys;
import com.tlv8.base.db.DBUtils;
import com.tlv8.core.utils.GetProcessFullName;
import com.tlv8.core.utils.UserFullInfo;

/**
 * @P 写系统日志
 * @author ChenQain
 * @C 2011-12-20
 */
@Controller
@Scope("prototype")
public class WriteSystemLogAction extends ActionSupport {
	private Data data;
	private String personID;
	private String srcPath;
	private String discription;
	private String processName;
	private String activateName;
	private String actionName;

	public Data getdata() {
		return this.data;
	}

	public void setdata(Data data) {
		this.data = data;
	}

	@ResponseBody
	@RequestMapping(value = "/WriteSystemLogAction", method = RequestMethod.POST)
	public Object execute() throws Exception {
		// System.out.println("begin...");
		data = new Data();
		if (personID == null || "".equals(personID)) {
			data.setFlag("false");
			data.setMessage("用户ID为空!");
			return data;
		}
		String cpath = request.getContextPath();
		if (srcPath.startsWith(cpath)) {
			srcPath = srcPath.substring(cpath.length());
		}
		// System.out.println(srcPath);
		HashMap<String, String> user = UserFullInfo.get(personID);
		String SCREATORFID = user.get("currentPersonFullID") == null ? "" : user.get("currentPersonFullID");
		String SCREATORFNAME = user.get("currentPersonName") == null ? "" : user.get("currentPersonName");
		String SCREATORPERSONID = user.get("currentPersonID") == null ? "" : user.get("currentPersonID");
		String SCREATORPERSONNAME = user.get("currentPersonName") == null ? "" : user.get("currentPersonName");
		String SCREATORPOSID = user.get("currentPositionID") == null ? "" : user.get("currentPositionID");
		String SCREATORPOSNAME = user.get("currentPositionName") == null ? "" : user.get("currentPositionName");
		String SCREATORDEPTID = user.get("currentDeptID") == null ? "" : user.get("currentDeptID");
		String SCREATORDEPTNAME = user.get("currentDeptName") == null ? "" : user.get("currentDeptName");
		String SCREATOROGNID = user.get("currentOgnID") == null ? "" : user.get("currentOgnID");
		String SCREATOROGNNAME = user.get("currentOgnName") == null ? "" : user.get("currentOgnName");
		String sIP = "";// IPConfig.getIP();
		sIP = getRemoteAddr(request);
		if (sIP.equals("0:0:0:0:0:0:0:1"))
			sIP = "127.0.0.1";
		String customer = "";
		try {
			if (srcPath != null && !"".equals(srcPath)) {
				if (srcPath.indexOf("/mobileUI/") > 0) {
					srcPath = srcPath.replace("/mobileUI/", "/");
					customer = "手机端访问";
				}
				GetProcessFullName gpf = new GetProcessFullName();
				processName = gpf.getFullName(srcPath);
				activateName = gpf.getNameByPath(srcPath);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		// System.out.println(activateName);
		if (activateName == null || "".equals(activateName)) {
			data.setFlag("false");
			data.setMessage("不是已知的功能!");
			return data;
		}
		String sql = "insert into SA_LOG(sID,version,SDESCRIPTION,"
				+ "SCREATORFID,SCREATORFNAME,SCREATORPERSONID,SCREATORPERSONNAME,SCREATORPOSID,SCREATORPOSNAME,"
				+ "SCREATORDEPTID,SCREATORDEPTNAME,SCREATOROGNID,SCREATOROGNNAME,SCREATETIME,"
				+ "SPROCESSNAME,SACTIVITYNAME,SACTIONNAME,SIP)values(" + "?,0,?,?,?,'" + SCREATORPERSONID + "','"
				+ SCREATORPERSONNAME + "','" + SCREATORPOSID + "','" + SCREATORPOSNAME + "'," + "'" + SCREATORDEPTID
				+ "','" + SCREATORDEPTNAME + "','" + SCREATOROGNID + "','" + SCREATOROGNNAME + "',?," + "'"
				+ processName + "','" + activateName + "','" + actionName + "-" + customer + "','" + sIP + "')";
		SqlSession session = DBUtils.getSession("system");
		Connection conn = null;
		PreparedStatement ps = null;
		try {
			conn = session.getConnection();
			ps = conn.prepareStatement(sql);
			ps.setString(1, Sys.getUUID());
			ps.setString(2, discription);
			ps.setString(3, SCREATORFID);
			ps.setString(4, SCREATORFNAME);
			ps.setTimestamp(5, new Timestamp(new Date().getTime()));
			ps.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DBUtils.closeConn(session, conn, ps, null);
		}
		data.setFlag("true");
		return data;
	}

	private static String getRemoteAddr(HttpServletRequest req) {
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
		return ip;
	}

	public void setActionName(String actionName) {
		try {
			this.actionName = URLDecoder.decode(actionName, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
	}

	public String getActionName() {
		return actionName;
	}

	public void setProcessName(String processName) {
		try {
			this.processName = URLDecoder.decode(processName, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
	}

	public String getProcessName() {
		return processName;
	}

	public void setActivateName(String activateName) {
		try {
			this.activateName = URLDecoder.decode(activateName, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
	}

	public String getActivateName() {
		return activateName;
	}

	public String getPersonID() {
		return personID;
	}

	public void setPersonID(String personID) {
		try {
			this.personID = URLDecoder.decode(personID, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
	}

	public String getSrcPath() {
		return srcPath;
	}

	public void setSrcPath(String srcPath) {
		try {
			this.srcPath = URLDecoder.decode(srcPath, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
	}

	public String getDiscription() {
		return discription;
	}

	public void setDiscription(String discription) {
		try {
			this.discription = URLDecoder.decode(discription, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
	}
	
}
