package com.tlv8.interceptors;

import java.util.Enumeration;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.tlv8.base.utils.IPUtils;

import cn.dev33.satoken.stp.StpUtil;

/**
 * 防止SQL注入的拦截器
 *
 */
public class SqlInjectInterceptor implements HandlerInterceptor {
	private static final Logger logger = LoggerFactory.getLogger(SqlInjectInterceptor.class);

	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object arg2, Exception arg3)
			throws Exception {
		// TODO Auto-generated method stub

	}

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object arg2, ModelAndView arg3)
			throws Exception {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object arg2) throws Exception {
		// 1.后台功能（有SQL值情况），针对数据库SQL操作，进行开发者权限控制，防止SQL注入
		String requestPath = request.getRequestURI();
		if (isBackAction(requestPath)) {
			/*
			 * 后台登录控制
			 */
			// 判断是否已登录
			if (StpUtil.isLogin()) {
				return true;
			} else {
				logger.info(" ---操作失败，当前用户未授权开发权限-------- 请求IP ---------+" + IPUtils.getRemoteAddr(request));
				response.setHeader("Cache-Control", "no-store");
				response.getWriter().print("操作失败，当前用户未授权开发权限！！");
				return false;
			}
		}

		// 2，常规业务操作（无SQL值情况）， 针对数据库SQL操作，进行开发者权限控制，防止SQL注入
		Enumeration<String> names = request.getParameterNames();
		while (names.hasMoreElements()) {
			String name = names.nextElement();
			String[] values = request.getParameterValues(name);
			for (String value : values) {
				// sql注入直接拦截
				if (judgeSQLInject(value.toLowerCase())) {
					System.out.println(requestPath);
					System.out.println("name: " + name + " -------------value:" + value);
					logger.info("-----------Sql注入拦截-----------name: " + name + " -------------value:" + value);
					response.setContentType("text/html;charset=UTF-8");
					response.getWriter().print("参数含有非法攻击字符,已禁止继续访问！");
					return false;
				}
				// 跨站xss清理
				clearXss(value);
			}
		}
		return true;
	}

	/**
	 * 判断参数是否含有攻击串
	 * 
	 * @param value
	 * @return
	 */
	public boolean judgeSQLInject(String value) {
		if (value == null || "".equals(value)) {
			return false;
		}
		String xssStr = "and |or |select |update |delete |drop |truncate |=|!=";// |%20 |--
		String[] xssArr = xssStr.split("\\|");
		for (int i = 0; i < xssArr.length; i++) {
			if (value.indexOf(xssArr[i]) > -1) {
				return true;
			}
		}
		return false;
	}

	/**
	 * 处理跨站xss字符转义
	 *
	 * @param value
	 * @return
	 */
	private String clearXss(String value) {
		logger.debug("----before--------处理跨站xss字符转义----------" + value);
		if (value == null || "".equals(value)) {
			return value;
		}
		value = value.replaceAll("<", "<").replaceAll(">", ">");
		value = value.replaceAll("\\(", "(").replace("\\)", ")");
		value = value.replaceAll("'", "'");
		value = value.replaceAll("eval\\((.*)\\)", "");
		value = value.replaceAll("[\\\"\\\'][\\s]*javascript:(.*)[\\\"\\\']", "\"\"");
		value = value.replace("script", "");

		// 为了用户密码安全，禁止列表查询展示用户密码----------
		value = value.replace(",password", "").replace("password", "");
		logger.debug("----end--------处理跨站xss字符转义----------" + value);
		return value;
	}

	/*
	 * 后台动作 权限控制
	 */
	private boolean isBackAction(String requestPath) {
		return requestPath.endsWith("/sqlQueryAction") || requestPath.endsWith("/sqlQueryActionforJson")
				|| requestPath.endsWith("/sqlUpdateAction") || requestPath.endsWith("/deleteSystemAction")
				|| requestPath.endsWith("/queryAction") || requestPath.endsWith("/saveAction")
				|| requestPath.endsWith("/deleteAction") || requestPath.endsWith("/getGridAction")
				|| requestPath.endsWith("/getGridSelectDataAction") || requestPath.endsWith("/TreeSelectAction")
				|| requestPath.endsWith("/QuickTreeAction") || requestPath.endsWith("/getOrgGridInfo")
				|| requestPath.endsWith("/saveOrgGridInfo") || requestPath.endsWith("/deleteOrgGridInfo")
				|| requestPath.endsWith("/saveGridAction") || requestPath.endsWith("/getExecutorTree")
				|| requestPath.endsWith("/saveFlowDrawLGAction") || requestPath.endsWith("/core/loadGridData")
				|| requestPath.endsWith("/core/saveGridDatas");
	}

}
