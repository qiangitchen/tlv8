package com.tlv8.interceptor;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.tlv8.base.db.DBUtils;
import com.tlv8.interceptor.echat.EChatExecuteFilter;
import com.tlv8.opm.inter.OrgAdmAuthority;
import com.tlv8.opm.inter.impl.OrganizationAdministrativeAuthority;
import com.tlv8.system.bean.ContextBean;
import com.tlv8.system.help.SessionHelper;

public class TLv8HandlerInterceptor implements HandlerInterceptor {
	// 未登录时跳转页面
	public static String SessionerrPage = "/Sessionerr.jsp";
	// 没有权限时跳转页面
	public static String SessionauthorPage = "/Sessionauthor.jsp";

	/**
	 * Handler执行完成之后调用这个方法
	 */
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception exc)
			throws Exception {
		response.addHeader("x-frame-options", "SAMEORIGIN");
		response.setHeader("Set-Cookie", "cookiename=value;Path=/;Domain=domainvalue;Max-Age=seconds;Secure;HTTPOnly");
	}

	/**
	 * Handler执行之后，ModelAndView返回之前调用这个方法
	 */
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
		response.addHeader("x-frame-options", "SAMEORIGIN");
		response.setHeader("Set-Cookie", "cookiename=value;Path=/;Domain=domainvalue;Max-Age=seconds;Secure;HTTPOnly");
	}

	/**
	 * Handler执行之前调用这个方法
	 */
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		// 获取请求的URL
		String requestURI = request.getRequestURI();
		String ContextPath = request.getContextPath();
		String patex = requestURI.toLowerCase();

		//System.out.println(requestURI);
		//System.out.println(ContextPath);

		if (EChatExecuteFilter.doFilter(request, response)) {
			return false; // 返回false就是不需要再做处理
		} else if (isPage(patex) && !isLoginPage(patex) && !isWelcomePage(ContextPath, requestURI)
				&& !isIcoPage(requestURI)) {
			/*
			 * 页面访问登录控制
			 */
			ContextBean context = SessionHelper.getContext(request);
			// 判断是否已登录
			if (context.isLogin()) {
				OrgAdmAuthority author = new OrganizationAdministrativeAuthority(context, request.getServletContext());
				String taskID = request.getParameter("taskID");
				String epersonid = getCurrentTaskExcutorID(taskID);
				String cupersonid = context.getCurrentPersonID();// 待办任务判断是否执行人是自己
				// author.isHaveAutority(requestURI)自己拥有的权限
				// author.isHaveAgentAuthor(requestURI)代理的权限
				String authorUrl = request.getParameter("process") + request.getParameter("activity");
				if (authorUrl == null || "".equals(authorUrl)) {
					authorUrl = requestURI;
				}
				if (!author.isHaveAutority(authorUrl) && !author.isHaveAgentAuthor(authorUrl)
						&& !epersonid.equals(cupersonid)) {
					// response.sendRedirect(ContextPath +
					// SessionauthorPage);// 显示跳转
					request.getRequestDispatcher(SessionauthorPage).forward(request, response);// 隐式跳转
					return false;
				}
			} else {
				// 未正常登录的自动回到首页(登录页面)
				request.getRequestDispatcher(SessionerrPage).forward(request, response);// 隐式跳转
				return false;
			}
		}
		return true;
	}

	/*
	 * 判断是否为网页
	 */
	private boolean isPage(String patex) {
		boolean rsps = false;
		if (patex.endsWith(".html") || patex.endsWith(".htm") || patex.endsWith(".jsp") || patex.endsWith(".jspx")
				|| patex.endsWith(".xhtml")) {
			rsps = true;
		}
		return rsps;
	}

	/*
	 * 判断是否为登录页面
	 */
	private boolean isLoginPage(String patex) {
		boolean isre = false;
		if (isPage(patex) && patex.contains("login.")) {
			isre = true;
		}
		if (patex.endsWith(SessionerrPage.toLowerCase())) {
			isre = true;
		}
		return isre;
	}

	/*
	 * 判断是否为工程首页
	 */
	private boolean isWelcomePage(String contextpath, String url) {
		return url.equals(contextpath + "/index.jsp") || url.equals(contextpath + "/index.html");
	}

	/*
	 * 判断是否为忽略的页面
	 */
	private boolean isIcoPage(String url) {
		return url.contains("/comon/picCompant/Pic-read.jsp");
	}

	/*
	 * 获取当前任务的执行人ID
	 */
	public static String getCurrentTaskExcutorID(String taskID) {
		String sql = "select SEPERSONID from SA_TASK where SID = '" + taskID + "'";
		String ptid = "";
		try {
			List<Map<String, String>> li = DBUtils.execQueryforList("system", sql);
			if (li.size() > 0) {
				ptid = li.get(0).get("SEPERSONID");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return ptid;
	}
}
