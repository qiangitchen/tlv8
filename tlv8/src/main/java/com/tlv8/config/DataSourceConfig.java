package com.tlv8.config;

import java.sql.SQLException;
import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.baomidou.dynamic.datasource.DynamicRoutingDataSource;
import com.baomidou.dynamic.datasource.provider.DynamicDataSourceProvider;
import com.tlv8.base.db.DBUtils;

/**
 * 数据源配置
 */
@Configuration
public class DataSourceConfig {

//	@Bean(name = "oa")
//	@Primary
//	DataSource dataSource() {
//		return DBUtils.getHikariDataSource("oa");
//	}
//
//	@Bean(name = "system")
//	DataSource systemDataSource() {
//		return DBUtils.getTLv8DataSource("system");
//	}

	/**
	 * 动态数据源
	 */
	@Bean
	DynamicRoutingDataSource dynamicRoutingDataSource() throws SQLException {
		List<DynamicDataSourceProvider> prList = DBUtils.getDynamicDataSourceProviderList();
		DynamicRoutingDataSource dynamicRoutingDataSource = new DynamicRoutingDataSource(prList);
		// 指定主数据源（没有注释@DS时默认的数据源）
		dynamicRoutingDataSource.addDataSource("master", DBUtils.getHikariDataSource("oa"));
		dynamicRoutingDataSource.afterPropertiesSet();
		return dynamicRoutingDataSource;
	}

}
