package com.tlv8.base.redis;

import org.springframework.data.redis.connection.RedisPassword;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;

/**
 * 自定义redis连接配置
 * 
 * @author chenqian
 *
 */
public class MyRedisStandaloneConfiguration extends RedisStandaloneConfiguration {

	public MyRedisStandaloneConfiguration(String hostName, int port, String password) {
		super(hostName, port);
		setPassword(RedisPassword.of(password));
	}
}
