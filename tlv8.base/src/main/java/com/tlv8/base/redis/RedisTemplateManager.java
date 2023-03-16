package com.tlv8.base.redis;

import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;

import com.tlv8.base.bean.utils.SpringBeanFactoryUtils;

public class RedisTemplateManager {

	@SuppressWarnings({ "rawtypes" })
	public static RedisTemplate getRedisTemplate() {
		RedisTemplate redisTemplate = new RedisTemplate();
		MyRedisStandaloneConfiguration redisSentinelConfiguration = (MyRedisStandaloneConfiguration) SpringBeanFactoryUtils
				.getBean("redisSentinelConfiguration");
		LettuceConnectionFactory lettuceConnFactory = new LettuceConnectionFactory(redisSentinelConfiguration);
		lettuceConnFactory.afterPropertiesSet();
		redisTemplate.setConnectionFactory(lettuceConnFactory);
		redisTemplate.afterPropertiesSet();
		return redisTemplate;
	}

}
