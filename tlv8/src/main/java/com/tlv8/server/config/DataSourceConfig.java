package com.tlv8.server.config;

import javax.sql.DataSource;

import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import com.tlv8.base.datasource.TLv8DataSource;
import com.tlv8.base.db.DBUtils;

/**
 * 数据源配置
 */
@Configuration
public class DataSourceConfig {

	@Bean
	@Primary
	DataSource dataSource() {
		TLv8DataSource oadb = DBUtils.getTLv8DataSource("oa");
		return DataSourceBuilder.create().driverClassName(oadb.getDriver()).url(oadb.getUrl())
				.username(oadb.getUsername()).password(oadb.getPassword()).build();
	}

	@Bean("systemDataSource")
	DataSource systemDataSource() {
		TLv8DataSource db = DBUtils.getTLv8DataSource("system");
		return DataSourceBuilder.create().driverClassName(db.getDriver()).url(db.getUrl()).username(db.getUsername())
				.password(db.getPassword()).build();
	}
}
