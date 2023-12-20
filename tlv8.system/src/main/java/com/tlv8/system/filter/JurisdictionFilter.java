package com.tlv8.system.filter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.ibatis.jdbc.SQL;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.tlv8.base.db.DBUtils;
import com.tlv8.base.utils.StringUtils;
import com.tlv8.system.bean.ContextBean;
import com.tlv8.system.echat.EChatExecuteFilter;
import com.tlv8.system.inter.OrgAdmAuthority;
import com.tlv8.system.inter.impl.OrganizationAdministrativeAuthority;
import com.tlv8.system.service.TokenService;

import cn.dev33.satoken.stp.StpUtil;

/**
 * 登录、权限拦截器
 * 
 * @author chenqian
 *
 */
@Component
public class JurisdictionFilter extends OncePerRequestFilter {
	// 未登录时跳转页面
	public static String SessionerrPage = "/Sessionerr.jsp";
	// 没有权限时跳转页面
	public static String SessionauthorPage = "/Sessionauthor.jsp";

	@Autowired
	private TokenService tokenService;

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
			throws ServletException, IOException {
		HttpServletRequest req = (HttpServletRequest) request;
		HttpServletResponse res = (HttpServletResponse) response;
		// 获取请求的URL
		String requestURI = req.getRequestURI();
		String ContextPath = req.getContextPath();
		String patex = requestURI.toLowerCase();

		if (isSource(patex)) {// 静态资源js、css等不拦截
			chain.doFilter(request, response);
			return;
		}
		
		ContextBean context = tokenService.getContextBean(req);
		if (StpUtil.isLogin() && StringUtils.isNotNull(context)) {
			tokenService.verifyToken(context);
		}
		if (EChatExecuteFilter.doFilter(req, res)) {
			// return false; // 返回false就是不需要再做处理
		} else if (isPage(patex) && !isLoginPage(patex) && !isWelcomePage(ContextPath, requestURI)
				&& !isIcoPage(requestURI) && !isWXApp(requestURI)) {
			// 判断是否已登录
			if (StpUtil.isLogin()) {
				OrgAdmAuthority author = new OrganizationAdministrativeAuthority(context, request.getServletContext());
				String taskID = request.getParameter("taskID");
				String epersonid = getCurrentTaskExcutorID(taskID);
				String cupersonid = context.getCurrentPersonID();// 待办任务判断是否执行人是自己
				// author.isHaveAutority(requestURI)自己拥有的权限
				// author.isHaveAgentAuthor(requestURI)代理的权限
				String authorUrl = requestURI.replace(ContextPath, "");
				if (request.getParameter("process") != null && request.getParameter("activity") != null) {
					authorUrl = request.getParameter("process") + request.getParameter("activity");
				}
				if (!author.isHaveAutority(authorUrl) && !author.isHaveAgentAuthor(authorUrl)
						&& !epersonid.equals(cupersonid)) {
					// response.sendRedirect(ContextPath +
					// SessionauthorPage);// 显示跳转
					request.getRequestDispatcher(SessionauthorPage).forward(request, response);// 隐式跳转
				} else {
					chain.doFilter(request, response);
				}
			} else {
				// 未正常登录的自动回到首页(登录页面)
				request.getRequestDispatcher(SessionerrPage).forward(request, response);// 隐式跳转
			}
		} else {
			chain.doFilter(request, response);
		}
	}

	/*
	 * 判断是否为网页
	 */
	private boolean isPage(String patex) {
		boolean rsps = false;
		if (patex.endsWith(".html") || patex.endsWith(".htm") || patex.endsWith(".jsp") || patex.endsWith(".jspx")
				|| patex.endsWith(".xhtml") || patex.contains("/ureport/")) {
			rsps = true;
		}
		return rsps;
	}

	/*
	 * 判断是否为静态资源
	 */
	private boolean isSource(String patex) {
		boolean rsps = false;
		if (patex.endsWith(".css") || patex.endsWith(".js") || patex.endsWith(".jpg") || patex.endsWith(".png")
				|| patex.endsWith(".gif") || patex.endsWith(".jpeg")) {
			rsps = true;
		}
		return rsps;
	}

	/*
	 * 判断是否为登录页面
	 */
	private boolean isLoginPage(String patex) {
		boolean isre = false;
		if (patex.equals("/index.jsp")) {// 允许访问系统首页
			return true;
		}
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
	 * 判断是否为小程序前端请求
	 */
	private boolean isWXApp(String url) {
		return url.contains("/api/wx/");
	}

	/*
	 * 获取当前任务的执行人ID
	 */
	public static String getCurrentTaskExcutorID(String taskID) {
		String ptid = "";
		try {
			SQL sql = new SQL().SELECT("SEPERSONID").FROM("SA_TASK").WHERE("SID = ?");
			List<Object> param = new ArrayList<>();
			param.add(taskID);
			List<Map<String, Object>> li = DBUtils.selectList("system", sql.toString(), param);
			if (li.size() > 0) {
				ptid = (String) li.get(0).get("SEPERSONID");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return ptid;
	}

}
