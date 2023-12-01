package com.tlv8.system.service;

import java.util.concurrent.TimeUnit;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.tlv8.base.redis.RedisCache;
import com.tlv8.base.utils.StringUtils;
import com.tlv8.system.bean.ContextBean;

import cn.dev33.satoken.stp.StpUtil;

@Component
public class TokenService {
	// 令牌有效期（默认30分钟）
	private int expireTime = 30;

	protected static final long MILLIS_SECOND = 1000;

	protected static final long MILLIS_MINUTE = 60 * MILLIS_SECOND;

	private static final Long MILLIS_MINUTE_TEN = 20 * 60 * 1000L;

	@Autowired
	private RedisCache redisCache;

	/**
	 * 获取用户身份信息
	 *
	 * @return 用户信息
	 */
	public ContextBean getContextBean(HttpServletRequest request) {
		// 获取请求携带的令牌
		String token = StpUtil.getTokenValue();
		ContextBean contextBean = null;
		if (StringUtils.isNotEmpty(token)) {
			try {
				contextBean = redisCache.getCacheObject(token);
			} catch (Exception e) {
				// e.printStackTrace();
			}
		}
		if (contextBean == null) {
			contextBean = new ContextBean();
		}
		return contextBean;
	}

	/**
	 * 设置用户身份信息
	 */
	public void setContextBean(ContextBean contextBean) {
		if (StringUtils.isNotNull(contextBean) && StringUtils.isNotEmpty(contextBean.getToken())) {
			refreshToken(contextBean);
		}
	}

	public void deleteContextBean(String token) {
		if (StringUtils.isNotEmpty(token)) {
			String userKey = StpUtil.getTokenValue();
			redisCache.deleteObject(userKey);
		}
	}

	/**
	 * 验证令牌有效期，相差不足20分钟，自动刷新缓存
	 *
	 * @param contextBean
	 * @return 令牌
	 */
	public void verifyToken(ContextBean contextBean) {
		long expireTime = contextBean.getExpireTime();
		long currentTime = System.currentTimeMillis();
		if (expireTime - currentTime <= MILLIS_MINUTE_TEN) {
			refreshToken(contextBean);
		}
	}

	/**
	 * 刷新令牌有效期
	 *
	 * @param contextBean 登录信息
	 */
	public void refreshToken(ContextBean contextBean) {
		contextBean.setLoginTime(System.currentTimeMillis());
		contextBean.setExpireTime(contextBean.getLoginTime() + expireTime * MILLIS_MINUTE);
		String userKey = StpUtil.getTokenValue();
		redisCache.setCacheObject(userKey, contextBean, expireTime, TimeUnit.MINUTES);
	}

}
