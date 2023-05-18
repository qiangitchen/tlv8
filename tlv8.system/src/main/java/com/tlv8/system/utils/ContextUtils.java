package com.tlv8.system.utils;

import javax.servlet.http.HttpServletRequest;

import com.tlv8.base.spring.SpringUtils;
import com.tlv8.base.utils.ServletUtils;
import com.tlv8.system.bean.ContextBean;
import com.tlv8.system.service.TokenService;

/**
 * 系统上下文公用类
 * 
 * @author 陈乾
 *
 */
public class ContextUtils {

	/**
	 * 获取当前登录人信息
	 * 
	 * @param request
	 * @return com.tlv8.system.bean.ContextBean
	 */
	public static ContextBean getContext(HttpServletRequest request) {
		ContextBean contextBean = new ContextBean();
		try {
			TokenService tokenService = SpringUtils.getBean(TokenService.class);
			contextBean = tokenService.getContextBean(request);
		} catch (Exception e) {
		}
		return contextBean;
	}

	/**
	 * 静态获取当前登录人信息
	 * 
	 * @return ContextBean
	 */
	public static ContextBean getContext() {
		ContextBean contextBean = null;
		try {
			HttpServletRequest request = ServletUtils.getRequest();
			TokenService tokenService = SpringUtils.getBean(TokenService.class);
			contextBean = tokenService.getContextBean(request);
		} catch (Exception e) {
		}
		if (contextBean == null) {
			contextBean = new ContextBean();
		}
		return contextBean;
	}

}
