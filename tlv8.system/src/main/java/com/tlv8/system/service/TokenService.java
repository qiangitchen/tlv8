package com.tlv8.system.service;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.tlv8.base.redis.RedisCache;
import com.tlv8.base.utils.IDUtils;
import com.tlv8.base.utils.StringUtils;
import com.tlv8.system.bean.ContextBean;
import com.tlv8.system.constant.CacheConstants;
import com.tlv8.system.constant.Constants;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Component
public class TokenService {
	// 令牌自定义标识
	private String header = "Authorization";

	// 令牌秘钥
	private String secret = "abcdefghijklmnopqrstuvwxyz";

	// 令牌有效期（默认30分钟）
	private int expireTime = 30;

	protected static final long MILLIS_SECOND = 1000;

	protected static final long MILLIS_MINUTE = 60 * MILLIS_SECOND;

	private static final Long MILLIS_MINUTE_TEN = 20 * 60 * 1000L;

	@Autowired
	private RedisCache redisCache;
	
	private static TokenService instance;
	
	public TokenService() {
		instance = this;
	}

	/**
	 * 获取用户身份信息
	 *
	 * @return 用户信息
	 */
	public ContextBean getContextBean(HttpServletRequest request) {
		// 获取请求携带的令牌
		String token = getToken(request);
		ContextBean contextBean = null;
		if (StringUtils.isNotEmpty(token)) {
			try {
				Claims claims = parseToken(token);
				// 解析对应的权限以及用户信息
				String uuid = (String) claims.get(Constants.LOGIN_USER_KEY);
				String userKey = getTokenKey(uuid);
				contextBean = redisCache.getCacheObject(userKey);
			} catch (Exception e) {
				e.printStackTrace();
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
			String userKey = getTokenKey(token);
			redisCache.deleteObject(userKey);
		}
	}

	/**
	 * 创建令牌
	 *
	 * @param contextBean 用户信息
	 * @return 令牌
	 */
	public String createToken(ContextBean contextBean) {
		String token = IDUtils.getUUID();
		contextBean.setToken(token);
		refreshToken(contextBean);
		Map<String, Object> claims = new HashMap<>();
		claims.put(Constants.LOGIN_USER_KEY, token);
		return createToken(claims);
	}

	/**
	 * 从数据声明生成令牌
	 *
	 * @param claims 数据声明
	 * @return 令牌
	 */
	private String createToken(Map<String, Object> claims) {
		String token = Jwts.builder().setClaims(claims).signWith(SignatureAlgorithm.HS512, secret).compact();
		return token;
	}

	/**
	 * 从令牌中获取数据声明
	 *
	 * @param token 令牌
	 * @return 数据声明
	 */
	private Claims parseToken(String token) {
		return Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody();
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
		// 根据uuid将loginUser缓存
		String userKey = getTokenKey(contextBean.getToken());
		redisCache.setCacheObject(userKey, contextBean, expireTime, TimeUnit.MINUTES);
	}

	/**
	 * 获取请求token
	 *
	 * @param request
	 * @return token
	 */
	private String getToken(HttpServletRequest request) {
		try {
			String token = request.getHeader(header);
			if (token != null && StringUtils.isNotEmpty(token) && token.startsWith(Constants.TOKEN_PREFIX)) {
				token = token.replace(Constants.TOKEN_PREFIX, "");
			}
			if (token == null) {
				token = getTokenByCookie(request.getCookies());
			}
			return token;
		} catch (Exception e) {
		}
		return null;
	}

	private String getTokenByCookie(Cookie[] cookies) {
		for (Cookie cookie : cookies) {
			if (cookie.getName().equals(header)) {
				return cookie.getValue();
			}
		}
		return null;
	}

	private String getTokenKey(String uuid) {
		return CacheConstants.LOGIN_TOKEN_KEY + uuid;
	}

	public static TokenService getTokenService() {
		return instance;
	}

}
