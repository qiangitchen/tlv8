package com.tlv8.server.config;

import javax.sql.DataSource;

import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

@Configuration
public class DataSourceConfig {
	@Bean
	@Primary
	public DataSource dataSource() {
		return DataSourceBuilder.create().build();
	}
}
