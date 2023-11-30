package com.tlv8.server.config;

import javax.sql.DataSource;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import com.tlv8.base.db.DBUtils;

/**
 * 数据源配置
 */
@Configuration
public class DataSourceConfig {

	@Bean
	@Primary
	DataSource dataSource() {
		return DBUtils.getHikariDataSource("oa");
	}

	@Bean("systemDataSource")
	DataSource systemDataSource() {
		return DBUtils.getTLv8DataSource("system");
	}
	
}
