package com.tlv8.web.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.tlv8.common.config.Constants;

public class AjaxSessionTimeoutFilter implements Filter {
	@Override
	public void destroy() {

	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		HttpServletRequest req = (HttpServletRequest) request;
		HttpServletResponse res = (HttpServletResponse) response;
		// 判断session里是否为空 如果没有session 不创建新的session
		if (req.getSession(false) == null) {
			// 如果是ajax请求响应头会有，x-requested-with；
			if (req.getHeader("x-requested-with") != null
					&& req.getHeader("x-requested-with").equalsIgnoreCase("XMLHttpRequest")) {
				// 如果是ajax访问 设置响应的http status状态码为408
				res.setStatus(Constants.AJAX_SESSION_TIMEOUT);// 表示session timeout
			} else {
				chain.doFilter(req, res);
			}
		} else {
			chain.doFilter(req, res);
		}
	}

	@Override
	public void init(FilterConfig chain) throws ServletException {

	}
}
